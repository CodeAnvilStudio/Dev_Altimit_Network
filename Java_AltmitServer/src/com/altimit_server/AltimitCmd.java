package com.altimit_server;

/**
 * Created by Zach on 1/7/16.
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Interface used to mark methods that can be called by the client's.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AltimitCmd {
}

