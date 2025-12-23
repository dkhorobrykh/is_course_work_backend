package ru.itmo.is.course_work.util;

import java.util.Random;

public final class CargoNameGenerator {

  private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String DIGITS = "0123456789";
  private static final Random RANDOM = new Random();

  private CargoNameGenerator() {}

  public static String generate() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < 3; i++) {
      sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
    }

    sb.append('-');

    for (int i = 0; i < 5; i++) {
      sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
    }

    return sb.toString();
  }
}
