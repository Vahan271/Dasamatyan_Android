package com.example.vahan.dasamatyan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vahan on 06.01.2019.
 */

public class DBHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";


    public DBHelper(Context context) {
        super(context, "matyan", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");

        db.execSQL("create table usanox ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "sname text,"
                + "hayranun text,"
                + "login text,"
                + "class_id text,"
                + "pass text);");

        db.execSQL("create table dasaxos ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "sname text,"
                + "hayranun text,"
                + "login text,"
                + "pass text);");

        db.execSQL("create table ararka ("
                + "id integer primary key autoincrement,"
                + "anun text);"
        );

        db.execSQL("create table dasaran ("
                + "id integer primary key autoincrement,"
                + "dasaran integer not null);"
        );

        db.execSQL("create table dasaran_ararka ("
                + "id integer primary key autoincrement,"
                + "dasaran_id integer not null,"
                + "ararka_id integer not null,"
                + "dasaxos_id integer not null);"
        );

        db.execSQL("create table gnahatakan ("
                + "id integer primary key autoincrement,"
                + "ashakert_id integer not null,"
                + "dasaran_ararka_id integer not null,"
                + "gnah integer not null,"
                + "date string not null" + ");"
        );

    }

    public static String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
