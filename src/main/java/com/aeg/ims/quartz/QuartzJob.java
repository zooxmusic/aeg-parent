package com.aeg.ims.quartz;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzJob{

    String name();

    String group() default "DEFAULT_GROUP";

    String cronExp();

    String timeZone() default "America/New_York";
}