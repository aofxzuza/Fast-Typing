package fasttyping.main;


import fasttyping.config.ConfigurationLoader;
import fasttyping.ui.MainFrame;
import fasttyping.util.UtilFunction;

public class Main {
	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		/**
		 * load configuration
		 */
		ConfigurationLoader configurationLoader = new ConfigurationLoader();
		/**
		 * Set data to main frame
		 */
		mainFrame.setData(configurationLoader.getData());
		mainFrame.setTime(configurationLoader.getWholeTime(),
				configurationLoader.getTimePerText());
		UtilFunction.setWrongFactor(configurationLoader.getWrongFactor());
		/**
		 * init main frame
		 */
		mainFrame.init();
		/**
		 * Start
		 */
		mainFrame.setVisible(true);
	}
}
