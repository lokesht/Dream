package in.sel.utility;

public class Utility {
	long startTime;
	long endTime;

	public Utility() {
		/* start time analysis */
		startTime = System.currentTimeMillis();
	}

	public String endTime(Utility gm) {
		endTime = System.currentTimeMillis();
		/* end time of process */

		long millis = endTime - gm.startTime;

		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = (millis / (1000 * 60 * 60)) % 24;

		return hour + ":" + minute + ":" + second;
	}

	public String getTime(Utility gm) {
		endTime = System.currentTimeMillis();
		/* end time of process */

		long millis = endTime - gm.startTime;

		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = (millis / (1000 * 60 * 60)) % 24;

		return hour + ":" + minute + ":" + second;
	}
}
