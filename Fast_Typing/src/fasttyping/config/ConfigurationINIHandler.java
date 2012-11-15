package fasttyping.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class ConfigurationINIHandler {
	private Hashtable<String, String> hashtable;
	private static final String DELIMITER = ",";

	public ConfigurationINIHandler() {
		hashtable = new Hashtable<String, String>();
	}

	public void loadConfig(String file) throws NoSuchFieldException,
			FileNotFoundException, NullPointerException {
		if (hashtable == null || file == null) {
			throw new NullPointerException("file config agrument is null");
		}
		FileReader input = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(input);
		hashtable.clear();
		try {
			String line = bufferedReader.readLine();
			while (line != null) {
				if (line.length() == 0) {
					line = bufferedReader.readLine();
					continue;
				}
				if (line.startsWith(";")) {
					line = bufferedReader.readLine();
					continue;
				}
				int index = line.indexOf('=');
				if (index < 0) {
					line = bufferedReader.readLine();
					continue;
				}

				try {
					String key = line.substring(0, index);
					String val = line.substring(index + 1);
					val = val.trim();

					if ((val.length() > 2) && val.startsWith("\"")
							&& val.endsWith("\"")) {
						val = val.substring(1, val.length() - 1);
					}
					if (val.endsWith(";")) {
						val = val.substring(0, val.length() - 1);
					}
					hashtable.put(key, val);
				} catch (Exception e) {
					e.printStackTrace();
				}
				line = bufferedReader.readLine();

			}
			bufferedReader.close();
			input.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (hashtable.isEmpty()) {
			throw new NoSuchFieldException("No configuration load!!!");
		}
	}

	public int getIntValue(String key, int defualtValue) {
		if (key == null) {
			return defualtValue;
		}
		Integer value = 0;

		if (hashtable.containsKey(key)) {
			try {
				value = Integer.parseInt(hashtable.get(key));

			} catch (NumberFormatException e) {
				// throw new IllegalArgumentException(
				// "Invalid value for int: value = " + hashtable.get(key));
				return defualtValue;
			}
			return value;
		} else {
			return defualtValue;
		}
	}

	public long getLongValue(String key, long defualtValue) {
		if (key == null) {
			return defualtValue;
		}
		long value = 0;

		if (hashtable.containsKey(key)) {
			try {
				value = Long.parseLong(hashtable.get(key));

			} catch (NumberFormatException e) {
				// throw new IllegalArgumentException(
				// "Invalid value for int: value = " + hashtable.get(key));
				return defualtValue;
			}
			return value;
		} else {
			return defualtValue;
		}
	}

	public double getDoubleValue(String key, double defualtValue) {
		if (key == null) {
			return defualtValue;
		}
		double value = 0;

		if (hashtable.containsKey(key)) {
			try {
				value = Double.parseDouble(hashtable.get(key));

			} catch (NumberFormatException e) {
				// throw new IllegalArgumentException(
				// "Invalid value for int: value = " + hashtable.get(key));
				return defualtValue;
			}
			return value;
		} else {
			return defualtValue;
		}
	}

	public String getStringValue(String key, String defualtValue) {
		if (key == null) {
			return defualtValue;
		}
		String value = "";
		if (hashtable.containsKey(key)) {
			value = hashtable.get(key);
			return value;
		} else {
			return defualtValue;
		}
	}

	public boolean getBooleanValue(String key, boolean defualtValue) {
		if (key == null) {
			return defualtValue;
		}
		if (hashtable.containsKey(key)) {
			String valueString = (hashtable.get(key));
			if (valueString.equalsIgnoreCase("true")) {
				return true;
			} else if (valueString.equalsIgnoreCase("false")) {
				return false;
			}
			return defualtValue;
		} else {
			return defualtValue;
		}
	}

	public int[] getDelimitedInt(String key, int[] defaultValue) {
		if (key == null) {
			return defaultValue;
		}
		if (hashtable.containsKey(key)) {
			Vector<String> strings = new Vector<String>();

			StringTokenizer stringTokenizer = new StringTokenizer(
					hashtable.get(key), DELIMITER);
			while (stringTokenizer.hasMoreElements()) {
				strings.addElement(stringTokenizer.nextToken());

			}
			try {

				return convertToIntArray(strings);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public String[] getDelimitedString(String key, String[] defaultValue) {
		if (key == null) {
			return defaultValue;
		}
		if (hashtable.containsKey(key)) {
			Vector<String> strings = new Vector<String>();

			StringTokenizer stringTokenizer = new StringTokenizer(
					hashtable.get(key), DELIMITER);
			while (stringTokenizer.hasMoreElements()) {
				strings.addElement(stringTokenizer.nextToken());

			}
			return convertToStrArray(strings);
		}
		return defaultValue;
	}

	private String[] convertToStrArray(Vector<String> vector) {
		String str[] = new String[vector.size()];
		vector.copyInto(str);
		return str;
	}

	private int[] convertToIntArray(Vector<String> vector)
			throws NumberFormatException {
		String str[] = new String[vector.size()];
		int value[] = new int[vector.size()];
		vector.copyInto(str);
		for (int i = 0; i < str.length; i++) {
			value[i] = Integer.parseInt(str[i]);
		}
		return value;
	}

	public void dispose() {
		if (hashtable != null) {
			hashtable.clear();
			hashtable = null;
		}

	}
}
