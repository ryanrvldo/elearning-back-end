package com.lawencon.elearning.util;

/**
 * @author Rian Rivaldo
 */
public class TransactionNumberUtils {

  public static String generateFileTrxNumber() {
    return String.format("FL-%s", System.currentTimeMillis());
  }

  public static String generateAttendanceTrxNumber() {
    return String.format("AT-%s", System.currentTimeMillis());
  }

  public static String generateDtlExamTrxNumber() {
    return String.format("DT-%s", System.currentTimeMillis());
  }

}
