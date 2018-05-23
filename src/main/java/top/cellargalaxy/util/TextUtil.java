package top.cellargalaxy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cellargalaxy on 18-4-24.
 */
public class TextUtil {
	private static final Pattern pattern = Pattern.compile("(\\d)+([\\d.])*");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final Date getDateFromText(String string) {
		try {
			return DATE_FORMAT.parse(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String getIntFromText(String string) {
		if (string == null) {
			return null;
		}
		try {
			Matcher matcher = pattern.matcher(string);
			if (matcher.find()) {
				return matcher.group();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final int getIntFromText(String string, int defaultValue) {
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

	public static final double getDoubleFromText(String string, double defaultValue) {
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
