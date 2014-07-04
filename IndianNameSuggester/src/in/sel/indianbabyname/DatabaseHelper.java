package in.sel.indianbabyname;

import in.sel.exception.ValueNotInsertedException;
import in.sel.model.M_Name;
import in.sel.utility.AppConstants;
import in.sel.utility.AppLogger;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
	public static final String DATABASE_NAME = "BabyName2";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(TableContract.TimeStamp.SQL_CREATE);
			db.execSQL(TableContract.Name.SQL_CREATE);
			Log.i(TAG, "success");
		} catch (Exception e) {
			if (AppConstants.DEBUG)
				Log.e(TAG, e.toString());
			AppLogger.WriteIntoFile("Class Name --> DBHelper -- "
					+ e.toString());
		}
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
