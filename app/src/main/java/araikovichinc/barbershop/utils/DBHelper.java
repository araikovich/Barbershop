package araikovichinc.barbershop.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

/**
 * Created by Tigran on 12.02.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    final public static String DATA_BASE_NAME = "barbershop_db";

    @Inject
    public DBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table genders ("
                + "id,"
                + "title text,"
                + "imageUrl text" + ");");

        db.execSQL("create table category ("
                + "id,"
                + "genderId,"
                + "title text,"
                + "imageUrl text" + ");");

        db.execSQL("create table detail ("
                + "hairstyleId,"
                + "imageUrl text" + ");");
        db.execSQL("create table reservation ("
                + "reservationId,"
                + "day,"
                + "month,"
                + "year,"
                + "timeFromHour,"
                + "timeFromMin,"
                + "timeToMin,"
                + "timeToHour,"
                + "totalSum,"
                + "hairdresserName,"
                + "hairdresserPhoto" + ");");
        db.execSQL("create table my_services ("
                + "reservationId,"
                + "title,"
                + "price,"
                + "time" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
