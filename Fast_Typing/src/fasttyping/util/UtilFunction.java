package fasttyping.util;

public class UtilFunction {
	private static int WRONG_FACTOR = 1;

	/**
	 * Calculate word per minute
	 * 
	 * @param nKeystroke
	 *            key stroke
	 * @param nWKeystroke
	 *            wrong key stroke
	 * @param millisec
	 *            time
	 * @return word per minute
	 */
	public static double calWPM(int nKeystroke, int nWKeystroke, long millisec) {
		double wpm = (((nKeystroke / 5.0) - (nWKeystroke * WRONG_FACTOR)) / (millisec / 60000.0));
		if (wpm < 0) {
			wpm = 0;
		}
		return wpm;
	}

	/**
	 * Set wrong factor
	 * 
	 * @param wrongFactor
	 *            wrong factor
	 */
	public static void setWrongFactor(int wrongFactor) {
		if (wrongFactor <= 0) {
			return;
		}
		WRONG_FACTOR = wrongFactor;
	}
}
