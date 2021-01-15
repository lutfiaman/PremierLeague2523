package com.example.premierleague2523.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.premierleague2523.data.Schedule;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "football";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAV_MATCH = "favourite_match";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_MATCH = "id_match";
    private static final String KEY_NAME_HOME = "name_home";
    private static final String KEY_NAME_AWAY = "name_away";
    private static final String KEY_STADIUM_NAME = "stadiumName";
    private static final String KEY_DATE_MATCH = "dateMatch";
    private static final String KEY_NAME_LEAGUE = "name_league";

    private static final String CREATE_TABLE_FAV_MATCH = "CREATE TABLE "
            + TABLE_FAV_MATCH + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ID_MATCH + " INTEGER,"
            + KEY_NAME_HOME + " TEXT,"
            + KEY_NAME_AWAY + " TEXT,"
            + KEY_STADIUM_NAME + " TEXT,"
            + KEY_DATE_MATCH + " TEXT,"
            + KEY_NAME_LEAGUE + " TEXT );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAV_MATCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_FAV_MATCH + "'");
        onCreate(sqLiteDatabase);
    }

    public long addFavMatch(Schedule schedule) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_MATCH, schedule.getId());
        values.put(KEY_NAME_HOME, schedule.getTeamHome());
        values.put(KEY_NAME_AWAY, schedule.getTeamAway());
        values.put(KEY_STADIUM_NAME, schedule.getStrVenue());
        values.put(KEY_DATE_MATCH, schedule.getScheduleDate());
        values.put(KEY_NAME_LEAGUE, schedule.getStrLeague());
        long addFavMatch = sqLiteDatabase.insert(TABLE_FAV_MATCH, null, values);

        return addFavMatch;
    }

    public void deleteFavMatch(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_FAV_MATCH, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<Schedule> getAllFavMatch() {
        List<Schedule> matches = new ArrayList<Schedule>();

        String query = "SELECT * FROM " + TABLE_FAV_MATCH;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID_MATCH)));
                schedule.setIdDb(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                schedule.setTeamHome(cursor.getString(cursor.getColumnIndex(KEY_NAME_HOME)));
                schedule.setTeamAway(cursor.getString(cursor.getColumnIndex(KEY_NAME_AWAY)));
                schedule.setStrVenue(cursor.getString(cursor.getColumnIndex(KEY_STADIUM_NAME)));
                schedule.setScheduleDate(cursor.getString(cursor.getColumnIndex(KEY_DATE_MATCH)));
                schedule.setStrLeague(cursor.getString(cursor.getColumnIndex(KEY_NAME_LEAGUE)));

                matches.add(schedule);
            } while (cursor.moveToNext());
        }

        return matches;
    }

    public Boolean isFavourite(int idMatch) {
        String query = "SELECT * FROM " + TABLE_FAV_MATCH + " WHERE " + KEY_ID_MATCH + " = " + idMatch;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }
}
