package id.co.gitsolution.kamus.other;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import id.co.gitsolution.kamus.model.ModelKamus;

import static android.provider.BaseColumns._ID;
import static id.co.gitsolution.kamus.other.DatabaseContract.KamusColumns.KAMUS;
import static id.co.gitsolution.kamus.other.DatabaseContract.KamusColumns.TERJEMAHAN;
import static id.co.gitsolution.kamus.other.DatabaseContract.TABLE_ENGLISH;
import static id.co.gitsolution.kamus.other.DatabaseContract.TABLE_INDO;

public class KamusHelper {

    private static String DATABASE_TABLE_INDO = TABLE_INDO;
    private static String DATABASE_TABLE_ENG = TABLE_ENGLISH;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<ModelKamus> queryIndo() {
        ArrayList<ModelKamus> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_INDO, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ModelKamus modelKamus;
        if (cursor.getCount() > 0) {
            do {

                modelKamus = new ModelKamus();
                modelKamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                modelKamus.setKamus(cursor.getString(cursor.getColumnIndexOrThrow(KAMUS)));
                modelKamus.setTerjemahan(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));

                arrayList.add(modelKamus);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertIndo(ModelKamus modelKamus) {
        String sql = "INSERT INTO " + TABLE_INDO + " (" + KAMUS + ", " + TERJEMAHAN
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, modelKamus.getKamus());
        stmt.bindString(2, modelKamus.getTerjemahan());
        stmt.execute();
        stmt.clearBindings();
    }

    public ArrayList<ModelKamus> queryEng() {
        ArrayList<ModelKamus> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_ENG, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ModelKamus modelKamus;
        if (cursor.getCount() > 0) {
            do {

                modelKamus = new ModelKamus();
                modelKamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                modelKamus.setKamus(cursor.getString(cursor.getColumnIndexOrThrow(KAMUS)));
                modelKamus.setTerjemahan(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));

                arrayList.add(modelKamus);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertEng(ModelKamus modelKamus) {
        String sql = "INSERT INTO " + TABLE_ENGLISH + " (" + KAMUS + ", " + TERJEMAHAN
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, modelKamus.getKamus());
        stmt.bindString(2, modelKamus.getTerjemahan());
        stmt.execute();
        stmt.clearBindings();
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }
}
