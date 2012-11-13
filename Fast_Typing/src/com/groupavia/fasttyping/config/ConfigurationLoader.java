package com.groupavia.fasttyping.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class ConfigurationLoader {
	private static String CONFIGURATION_ENV = "CONFIG_FILE";
	private static String DATA_ENV = "DATA_FILE";
	private static String WHOLE_TIME = "WHOLE_TIME";
	private static String TIME_PER_TEXT = "TIME_PER_TEXT";
	private static String WORNG_FACTOR = "WORNG_FACTOR";
	private long wholeTime = 600;
	private long timePerText = 10;
	private int wrongFactor = 1;
	private String[] data = new String[0];

	public ConfigurationLoader() {
		try {
			loadConfiguration();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Load configuration
	 * 
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 * @throws NoSuchFieldException
	 */
	private void loadConfiguration() throws FileNotFoundException,
			NullPointerException, NoSuchFieldException {
		String configPath = System.getenv(CONFIGURATION_ENV);
		File f = new File(configPath);
		f.exists();
		ConfigurationINIHandler handler = new ConfigurationINIHandler();
		handler.loadConfig(configPath);
		wholeTime = handler.getLongValue(WHOLE_TIME, 600);
		timePerText = handler.getLongValue(TIME_PER_TEXT, 10);
		wrongFactor = handler.getIntValue(WORNG_FACTOR, 1);

	}

	/**
	 * Load data
	 * 
	 * @throws IOException
	 * @throws NullPointerException
	 */
	private void loadData() throws IOException, NullPointerException {
		String dataPath = System.getenv(DATA_ENV);
		if (dataPath == null) {
			throw new NullPointerException();
		}
		FileReader input = new FileReader(dataPath);
		BufferedReader bufferedReader = new BufferedReader(input);
		Vector<String> data = new Vector<String>();
		String line = bufferedReader.readLine();
		while (line != null) {
			if (line.length() == 0) {
				line = bufferedReader.readLine();
				continue;
			}
			data.add(line);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		input.close();

		this.data = data.toArray(new String[0]);
	}

	public long getWholeTime() {
		return wholeTime;
	}

	public long getTimePerText() {
		return timePerText;
	}

	public int getWrongFactor() {
		return wrongFactor;
	}

	public String[] getData() {
		return data;
	}
}
