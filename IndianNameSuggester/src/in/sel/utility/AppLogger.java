package in.sel.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class AppLogger {
	/*
	 * Connect Plus Sales Planning Sales Manager Account Plus ProductPlus Docu
	 * Plus Contact Manager E-Mail Plus Calendar Plus Sales Target Lead Manager
	 * Expense Manager Invoice Manager Inventory Manager Accounts Receivables
	 * Document Management Opportunity Task Resources Lead Product Manager
	 */
	public static void WriteIntoFile(String meg) {
		File file = new File(Environment.getExternalStorageDirectory().toString() + "/AppLogger/file_of_errors.txt");
		if (file.exists()) {

			String[] arr = null;
			List<String> itemsSchool = new ArrayList<String>();

			try {
				FileInputStream fstream_school = new FileInputStream(Environment.getExternalStorageDirectory()
						.toString() + "/AppLogger/file_of_errors.txt");
				DataInputStream data_input = new DataInputStream(fstream_school);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
				String str_line;

				while ((str_line = buffer.readLine()) != null) {
					str_line = str_line.trim();
					if ((str_line.length() != 0)) {
						itemsSchool.add(str_line);

					}
				}

				itemsSchool.add(meg);

				arr = itemsSchool.toArray(new String[itemsSchool.size()]);
				save(arr);
			} catch (Exception e) {
				AppLogger.WriteIntoFile("Class Name --> ApplicationData -- " + e.toString());
			}
		} else {

		}
	}

	private static void save(String[] strings) throws IOException {
		BufferedWriter writer = null;
		try {

			File file = new File(Environment.getExternalStorageDirectory().toString() + "/AppLogger/file_of_errors.txt");

			writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < strings.length; i++) {

				writer.append("\r\n" + strings[i]);

			}
			writer.flush();
			writer.close();

		} catch (IOException ex) {
			AppLogger.WriteIntoFile("Class Name --> Logger -- " + ex.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
