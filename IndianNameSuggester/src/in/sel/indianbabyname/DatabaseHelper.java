package in.sel.indianbabyname;

import in.sel.exception.ValueNotInsertedException;
import in.sel.model.M_Name;
import in.sel.utility.AppConstants;
import in.sel.utility.AppLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	/** */
	String TAG = "DatabaseHelper";

	/** */
	SQLiteDatabase db;

	/**
	 * If you change the database schema, you must increment the database
	 * version.
	 */
	public static final int DATABASE_VERSION = 1;
	public static final String DB_NAME = "BabyName.sqlite";

	// private static String DB_PATH =
	// "/data/data/in.sel.indianbabyname/databases/";
	private static String DB_PATH="/data/data/in.sel.indianbabyname/databases/";
	Context myContext;

	public static boolean createDirIfNotExists(String path) {
		boolean ret = true;

		File file = new File(Environment.getExternalStorageDirectory(), path);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				Log.e("TravellerLog :: ", "Problem creating Image folder");
				ret = false;
			}
		}
		return ret;
	}

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		//createDirIfNotExists("Test/AndroidBabyName/");

		//DB_PATH = Environment.getExternalStorageDirectory()+ "/Test/AndroidBabyName/";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// db.execSQL(TableContract.TimeStamp.SQL_CREATE);
			// db.execSQL(TableContract.Name.SQL_CREATE);
			// Log.i(TAG, "success");
		} catch (Exception e) {
			if (AppConstants.DEBUG)
				Log.e(TAG, e.toString());
			AppLogger.WriteIntoFile("Class Name --> DBHelper -- "
					+ e.toString());
		}
	}

	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			Log.v("log_tag", "database does exist");
		} else {
			Log.v("log_tag", "database does not exist");
			//this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			File f = new File(myPath);
			if (f.exists()){
				checkDB = SQLiteDatabase.openDatabase(myPath, null,
						SQLiteDatabase.NO_LOCALIZED_COLLATORS);}
			else{
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				return false;
			}
		} catch (SQLiteException e) {
			Log.v(TAG, e.toString() + "   database doesn't exists yet..");
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return false;
	}

	public boolean openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		return db != null;
	}

	@Override
	public synchronized void close() {
		if (db != null)
			db.close();
		super.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/* general Method */
	/** */
	public void resetTable(String TableName) {
		try {
			db = getWritableDatabase();
			db.delete(TableName, null, null);
			String whereClause = "name" + " = " + "'" + TableName + "'";

			ContentValues cv = new ContentValues();
			cv.put("seq", 0);

			int rowId = db.update("sqlite_sequence", cv, whereClause, null);

			int s = (rowId < 0) ? Log.i(TAG, "Not reseted Table --> "
					+ TableName) : Log.i(TAG, "Reseted Table --> " + TableName);
		} catch (Exception e) {
			AppLogger.WriteIntoFile("Class Name --> DBHelper -- "
					+ e.toString());
			if (AppConstants.DEBUG)
				Log.e("dbHelper", e.toString());
		} finally {
			db.close();
		}
	}

	public Cursor getTableValue(String sqlQuery, String[] selectionArgs)
			throws Exception {
		Cursor c = null;
		try {
			db = this.getReadableDatabase();
			c = db.rawQuery(sqlQuery, selectionArgs);
		} catch (Exception e) {
			AppLogger.WriteIntoFile("Class Name --> DBHelper -- "
					+ e.toString());
			if (AppConstants.DEBUG)
				Log.e(TAG, e.toString() + "getTableValue()");
		} finally {
			if (c == null)
				db.close();
		}
		return c;
	}

	public Cursor getTableValue(String TableName, String[] columns, String where) {
		Cursor c = null;
		try {
			db = this.getReadableDatabase();
			c = db.query(TableName, columns, where, null, null, null, null,
					null);
		} catch (Exception e) {
			AppLogger.WriteIntoFile("Class Name --> DBHelper -- "
					+ e.toString());
			if (AppConstants.DEBUG)
				Log.e(TAG, e.toString() + "--> getTableValue()");
		} finally {
			if (c == null)
				db.close();
		}
		return c;
	}

	/** */
	public int getTableRowCount(String tableName, String where) {
		int count = 0;
		try {
			db = getReadableDatabase();
			Cursor c = db.query(tableName, null, where, null, null, null, null);
			if (c != null)
				count = c.getCount();
		} catch (Exception e) {
			AppLogger.WriteIntoFile("Class Name --> " + TAG + " "
					+ e.toString());
			if (AppConstants.DEBUG)
				Log.e(getClass().getName(), e.toString() + "-->");
		} finally {
			db.close();
		}
		return count;
	}

	/* */
	public void executeStatement(String sqlQuery) {
		try {
			db = this.getWritableDatabase();
		} catch (Exception e) {
			AppLogger.WriteIntoFile("Class Name --> " + TAG + " "
					+ e.toString());
			if (AppConstants.DEBUG)
				Log.e(getClass().getName(), e.toString() + "-->");
		} finally {
			db.close();
		}
	}

	/* Insert state */
	public long insertName(List<M_Name> lsData) {
		long rowid = 0;
		try {
			db = this.getWritableDatabase();

			for (M_Name obj : lsData) {
				ContentValues cv = new ContentValues();
				cv.put(TableContract.Name.NAME_EN, obj.getName_en());
				cv.put(TableContract.Name.NAME_MA, obj.getName_ma());
				cv.put(TableContract.Name.NAME_FRE, obj.getFrequency());

				rowid = db.insert(TableContract.Name.TABLE_NAME,
						TableContract.Name.NAME_EN, cv);

				if (rowid < 0) {
					throw new ValueNotInsertedException();
				}
			}

		} catch (Exception e) {
			AppLogger.WriteIntoFile(TAG + e.toString());
			if (AppConstants.DEBUG)
				Log.e("insertName", e.toString());
		} finally {

			db.close();
		}
		return rowid;
	}

	/* Insert state */
	public long insertNameTransaction(List<M_Name> lsData) {
		long rowid = 0;
		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			for (M_Name obj : lsData) {
				ContentValues cv = new ContentValues();
				cv.put(TableContract.Name.NAME_EN, obj.getName_en());
				cv.put(TableContract.Name.NAME_MA, obj.getName_ma());
				cv.put(TableContract.Name.NAME_FRE, obj.getFrequency());

				rowid = db.insert(TableContract.Name.TABLE_NAME,
						TableContract.Name.NAME_EN, cv);

			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			AppLogger.WriteIntoFile(TAG + e.toString());
			if (AppConstants.DEBUG)
				Log.e("insertName", e.toString());
		} finally {
			db.endTransaction();
			db.close();
		}
		return rowid;
	}

	/* Insert state */
	public long insertNameInsertHelper(List<M_Name> lsData) {
		long rowid = 0;
		InsertHelper ih = new InsertHelper(getWritableDatabase(),
				TableContract.Name.TABLE_NAME);
		try {
			final int nEn = ih.getColumnIndex(TableContract.Name.NAME_EN);
			final int nMa = ih.getColumnIndex(TableContract.Name.NAME_MA);
			final int nFre = ih.getColumnIndex(TableContract.Name.NAME_FRE);

			for (M_Name obj : lsData) {

				ih.prepareForInsert();
				ih.bind(nEn, obj.getName_en());
				ih.bind(nMa, obj.getName_ma());
				ih.bind(nFre, obj.getFrequency());
				ih.execute();
			}

		} catch (Exception e) {
			AppLogger.WriteIntoFile(TAG + e.toString());
			if (AppConstants.DEBUG)
				Log.e("insertName", e.toString());
		} finally {
			if (ih != null)
				ih.close();
			this.db.setLockingEnabled(true);
		}
		return rowid;
	}

	/* Insert state */
	public long insertNameInsertHelperLock(List<M_Name> lsData) {
		long rowid = 0;
		InsertHelper ih = new InsertHelper(getWritableDatabase(),
				TableContract.Name.TABLE_NAME);

		final int nEn = ih.getColumnIndex(TableContract.Name.NAME_EN);
		final int nMa = ih.getColumnIndex(TableContract.Name.NAME_MA);
		final int nFre = ih.getColumnIndex(TableContract.Name.NAME_FRE);
		try {

			this.db.setLockingEnabled(false);
			for (M_Name obj : lsData) {

				ih.prepareForInsert();
				ih.bind(nEn, obj.getName_en());
				ih.bind(nMa, obj.getName_ma());
				ih.bind(nFre, obj.getFrequency());
				ih.execute();
			}

		} catch (Exception e) {
			AppLogger.WriteIntoFile(TAG + e.toString());
			if (AppConstants.DEBUG)
				Log.e("insertName", e.toString());
		} finally {
			if (ih != null)
				ih.close();
			this.db.setLockingEnabled(true);
		}
		return rowid;
	}
}
