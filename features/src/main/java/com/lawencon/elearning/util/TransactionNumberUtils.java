package com.lawencon.elearning.util;

/**
 * @author Rian Rivaldo
 */
public class TransactionNumberUtils {

  public static String generateFileTrxNumber() {
    return String.format("FL-%s", System.currentTimeMillis());
  }

}
