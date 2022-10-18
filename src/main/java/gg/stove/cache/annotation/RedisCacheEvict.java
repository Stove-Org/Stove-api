package gg.stove.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface RedisCacheEvict {

    String key();

    boolean clearAll() default false;
}
