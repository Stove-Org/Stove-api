package gg.stove.cache.aop;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import gg.stove.cache.annotation.RedisCacheEvict;
import gg.stove.cache.annotation.RedisCacheEvicts;
import gg.stove.cache.annotation.RedisCacheable;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@RequiredArgsConstructor
@Component
public class RedisCacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;


    @Around("@annotation(gg.stove.cache.annotation.RedisCacheable)")
    public Object cacheableProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RedisCacheable redisCacheable = methodSignature.getMethod().getAnnotation(RedisCacheable.class);

        String cacheKey = redisCacheable.key() + Arrays.toString(joinPoint.getArgs());
        long expireSecond = redisCacheable.expireSecond();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) {
            return redisTemplate.opsForValue().get(cacheKey);
        }

        Object methodReturnValue = joinPoint.proceed();

        if (expireSecond < 0) {
            redisTemplate.opsForValue().set(cacheKey, methodReturnValue);
        } else {
            redisTemplate.opsForValue().set(cacheKey, methodReturnValue, expireSecond, TimeUnit.SECONDS);
        }
        return methodReturnValue;
    }

    @Around("@annotation(gg.stove.cache.annotation.RedisCacheEvicts)")
    public Object cacheEvictsProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object methodReturnValue = joinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RedisCacheEvicts redisCacheEvicts = methodSignature.getMethod().getAnnotation(RedisCacheEvicts.class);
        RedisCacheEvict[] evicts = redisCacheEvicts.evicts();

        for (RedisCacheEvict evict : evicts) {
            if (evict.clearAll()) {
                Set<String> keys = redisTemplate.keys(evict.key() + "*");
                redisTemplate.delete(keys);
            } else {
                String cacheKey = evict.key() + Arrays.toString(joinPoint.getArgs());
                redisTemplate.delete(cacheKey);
            }
        }

        return methodReturnValue;
    }

    @Around("@annotation(gg.stove.cache.annotation.RedisCacheEvict)")
    public Object cacheEvictProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object methodReturnValue = joinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RedisCacheEvict redisCacheEvict = methodSignature.getMethod().getAnnotation(RedisCacheEvict.class);

        if (redisCacheEvict.clearAll()) {
            Set<String> keys = redisTemplate.keys(redisCacheEvict.key() + "*");
            redisTemplate.delete(keys);
        } else {
            String cacheKey = redisCacheEvict.key() + Arrays.toString(joinPoint.getArgs());
            redisTemplate.delete(cacheKey);
        }

        return methodReturnValue;
    }
}
