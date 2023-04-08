package app.openschool.common.utils;

public class MathUtil {

  private MathUtil() {}

  public static double getRoundedHours(double minutes) {

    return minutes == 0.0 ? 0.0 : Math.round((minutes / 60) * 10.0) / 10.0;
  }
}
