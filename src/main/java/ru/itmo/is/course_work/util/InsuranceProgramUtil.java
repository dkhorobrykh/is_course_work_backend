package ru.itmo.is.course_work.util;

import java.time.Instant;
import ru.itmo.is.course_work.model.InsuranceProgram;

public final class InsuranceProgramUtil {

  private InsuranceProgramUtil() {}

  public static boolean isActiveAtDatetime(InsuranceProgram insuranceProgram, Instant timestamp) {
    if (insuranceProgram.getStartDatetime() != null) {
      if (insuranceProgram.getEndDatetime() != null) {
        return insuranceProgram.getStartDatetime().isBefore(timestamp)
            && insuranceProgram.getEndDatetime().isAfter(timestamp);
      }
      return insuranceProgram.getStartDatetime().isBefore(timestamp);
    } else {
      if (insuranceProgram.getEndDatetime() != null) {
        return insuranceProgram.getEndDatetime().isAfter(timestamp);
      }
      return true;
    }
  }
}
