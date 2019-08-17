/**
 * 
 */
package com.boot.mq.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import org.slf4j.LoggerFactory;



import net.bytebuddy.asm.Advice.This;

@Documented
@Retention(CLASS)
@Target({ TYPE, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE, TYPE_PARAMETER, TYPE_USE })
/**
 * @author tangyang
 * @catetory 日志 logger
 */
public @interface Logger {
    public final static org.slf4j.Logger logger = LoggerFactory.getLogger(This.class);
}
