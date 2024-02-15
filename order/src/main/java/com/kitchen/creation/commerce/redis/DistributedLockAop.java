package com.kitchen.creation.commerce.redis;

import com.kitchen.creation.commerce.global.exception.order.OrderFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(value = 1) // this has to be registered as a bean before the transaction interceptor
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";
    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.kitchen.creation.commerce.redis.DistributedLock)")
    public void lock(final ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String[] productIds = distributedLock.key().split(",", -1);

        for (String productId: productIds) {
            String key = REDISSON_LOCK_PREFIX +
                    CustomSpringELParser.getDynamicValue(
                            signature.getParameterNames(),
                            joinPoint.getArgs(),
                            productId
                    );

            log.info("lock on [method:{}] [key:{}]", method, key);

            lockHelper(key, distributedLock, joinPoint);
        }
    }

    private void lockHelper(
            String key,
            DistributedLock distributedLock,
            ProceedingJoinPoint joinPoint
    ) throws Throwable {
        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available = rLock.tryLock(
                distributedLock.waitTime(),
                distributedLock.leaseTime(),
                distributedLock.timeUnit()
            );

            if (!available) {
                return;
            }

            aopForTransaction.proceed(joinPoint);

        } catch (InterruptedException e) {
            // happens if interrupted while trying to obtain the lock
            throw new InterruptedException();

        } finally {
            try {
                rLock.unlock();
                log.info("unlocked Lock [Lock:{}]", rLock.getName());
            } catch (IllegalMonitorStateException e) {
                // lock is already unlocked if the lease time has ended
                log.info("Lock [Lock:{}] Already Unlocked", rLock.getName());
            }
        }
    }
}
