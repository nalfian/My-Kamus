package id.co.gitsolution.kamus.other;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static id.co.gitsolution.kamus.other.DatabaseContract.KamusColumns.KAMUS;
import static id.co.gitsolution.kamus.other.DatabaseContract.KamusColumns.TERJEMAHAN;
import static id.co.gitsolution.kamus.other.DatabaseContract.TABLE_ENGLISH;
import static id.co.gitsolution.kamus.other.DatabaseContract.TABLE_INDO;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbkamusapp";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_TABLE_INDO = "create table " + TABLE_INDO +
            " (" + _ID + " integer primary key autoincrement, " +
            KAMUS + " text not null, " +
            TERJEMAHAN + " text not null);";

    private static final String SQL_CREATE_TABLE_ENG = "create table " + TABLE_ENGLISH +
            " (" + _ID + " integer primary key autoincrement, " +
            KAMUS + " text not null, " +
            TERJEMAHAN + " text not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_INDO);
        db.execSQL(SQL_CREATE_TABLE_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_INDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH);
        onCreate(db);
    }
}