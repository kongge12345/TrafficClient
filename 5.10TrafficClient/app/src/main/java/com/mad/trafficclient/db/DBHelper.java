package com.mad.trafficclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenping
 */

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "traffic.db";
	
	public static final String TABLE_ROADENVI = "account";

	public DBHelper(Context context) {
		//重点第二个是数据库名称
		super(context, DB_NAME, null, 1);
	}
// primary key autoincrement
	@Override
	public void onCreate(SQLiteDatabase db) {
		String table_trafficlight = "create table if not exists "+ TABLE_ROADENVI +
				"(id integer," +
				"car_id integer," +
				"money Integer," +
				"person varchar(20)," +
				"time varchar(20))";
		db.execSQL(table_trafficlight);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table is exists "+TABLE_ROADENVI);
		onCreate(db);
	}

}
