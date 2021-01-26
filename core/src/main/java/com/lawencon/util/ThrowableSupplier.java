package com.lawencon.util;

/**
 * @author Rian Rivaldo
 */
@FunctionalInterface
public interface ThrowableSupplier<T> {

  T get() throws Exception;

}
