package top.cellargalaxy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cellargalaxy on 18-5-21.
 */
public class StringUtil {
	private static final Pattern pattern = Pattern.compile("(\\d)+([\\d.])*");

	public static final int valueOfIntFromString(String string, int defaultValue) {
		if (string == null) {
			return defaultValue;
		}
		try {
			Matcher matcher = pattern.matcher(string);
			if (matcher.find()) {
				return Integer.valueOf(matcher.group());
			} else {
				return defaultValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static final double valueOfDoubleFromString(String string, double defaultValue) {
		if (string == null) {
			return defaultValue;
		}
		try {
			Matcher matcher = pattern.matcher(string);
			if (matcher.find()) {
				return Double.valueOf(matcher.group());
			} else {
				return defaultValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}
}
