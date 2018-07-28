package com.example.vadym.thetrainingproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vadym.thetrainingproject.database.model.FirstItem;
import com.example.vadym.thetrainingproject.database.model.SecondItem;
import com.example.vadym.thetrainingproject.database.model.ThirdItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db1";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FirstItem.TABLE_NAME + "("
                + FirstItem.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FirstItem.COLUMN_TEXT + " TEXT,"
                + FirstItem.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")");

        db.execSQL("CREATE TABLE " + SecondItem.TABLE_NAME + "("
                + SecondItem.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SecondItem.COLUMN_TEXT + " TEXT,"
                + SecondItem.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")");

        db.execSQL("CREATE TABLE " + ThirdItem.TABLE_NAME + "("
                + ThirdItem.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ThirdItem.COLUMN_TEXT + " TEXT,"
                + ThirdItem.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")");
    }

    public Object getItem(long id, int whichTable) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        switch (whichTable) {
            case 0:
                cursor = db.query(FirstItem.TABLE_NAME, new String[]{FirstItem.COLUMN_ID,FirstItem.COLUMN_TEXT,FirstItem.COLUMN_TIMESTAMP}, FirstItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
                cursor.moveToFirst();
                FirstItem firstItem = new FirstItem(
                        cursor.getInt(cursor.getColumnIndex(FirstItem.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(FirstItem.COLUMN_TEXT)),
                        cursor.getString(cursor.getColumnIndex(FirstItem.COLUMN_TIMESTAMP))
                );
                cursor.close(); db.close();
                return firstItem;
            case 1:
                cursor = db.query(SecondItem.TABLE_NAME, new String[]{SecondItem.COLUMN_ID, SecondItem.COLUMN_TEXT, SecondItem.COLUMN_TIMESTAMP}, SecondItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
                cursor.moveToFirst();
                SecondItem secondItem = new SecondItem(
                        cursor.getInt(cursor.getColumnIndex(SecondItem.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(SecondItem.COLUMN_TEXT)),
                        cursor.getString(cursor.getColumnIndex(SecondItem.COLUMN_TIMESTAMP))
                );
                cursor.close(); db.close();
                return secondItem;
            case 2:
                cursor = db.query(ThirdItem.TABLE_NAME, new String[]{ThirdItem.COLUMN_ID, ThirdItem.COLUMN_TEXT, ThirdItem.COLUMN_TIMESTAMP}, ThirdItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
                cursor.moveToFirst();
                ThirdItem thirdItem = new ThirdItem(
                        cursor.getInt(cursor.getColumnIndex(ThirdItem.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(ThirdItem.COLUMN_TEXT)),
                        cursor.getString(cursor.getColumnIndex(ThirdItem.COLUMN_TIMESTAMP))
                );
                cursor.close(); db.close();
                return thirdItem;
        }
        return null;
    }

    public List<?> getAllItems(int whichTable) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        switch (whichTable) {
            case 0:
                List<FirstItem> firstItems = new ArrayList<>();
                cursor = db.rawQuery("SELECT * FROM " + FirstItem.TABLE_NAME +
                        " ORDER BY " + FirstItem.COLUMN_ID + " DESC", null);
                if (cursor.moveToFirst()) {
                    do {
                        FirstItem firstItem = new FirstItem(
                                cursor.getInt(cursor.getColumnIndex(FirstItem.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(FirstItem.COLUMN_TEXT)),
                                cursor.getString(cursor.getColumnIndex(FirstItem.COLUMN_TIMESTAMP))
                        );
                        firstItems.add(firstItem);
                    } while (cursor.moveToNext());
                }
                cursor.close(); db.close();
                return firstItems;
            case 1:
                List<SecondItem> secondItems = new ArrayList<>();
                cursor = db.rawQuery("SELECT * FROM " + SecondItem.TABLE_NAME +
                        " ORDER BY " + SecondItem.COLUMN_ID + " DESC", null);
                if (cursor.moveToFirst()) {
                    do {
                        SecondItem secondItem = new SecondItem(
                                cursor.getInt(cursor.getColumnIndex(SecondItem.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(SecondItem.COLUMN_TEXT)),
                                cursor.getString(cursor.getColumnIndex(SecondItem.COLUMN_TIMESTAMP))
                        );
                        secondItems.add(secondItem);
                    } while (cursor.moveToNext());
                }
                cursor.close(); db.close();
                return secondItems;
            case 2:
                List<ThirdItem> thirdItems = new ArrayList<>();
                cursor = db.rawQuery("SELECT * FROM " + ThirdItem.TABLE_NAME +
                        " ORDER BY " + ThirdItem.COLUMN_ID + " DESC", null);
                if (cursor.moveToFirst()) {
                    do {
                        ThirdItem thirdItem = new ThirdItem(
                                cursor.getInt(cursor.getColumnIndex(FirstItem.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(FirstItem.COLUMN_TEXT)),
                                cursor.getString(cursor.getColumnIndex(FirstItem.COLUMN_TIMESTAMP))
                        );
                        thirdItems.add(thirdItem);
                    } while (cursor.moveToNext());
                }
                cursor.close(); db.close();
                return thirdItems;
        }
        return null;
    }

    public int getItemsCount(int whichTable) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        int count;
        switch (whichTable) {
            case 0:
                cursor = db.rawQuery("SELECT  * FROM " + FirstItem.TABLE_NAME, null);
                count = cursor.getCount();
                cursor.close(); db.close();
                return count;
            case 1:
                cursor = db.rawQuery("SELECT  * FROM " + SecondItem.TABLE_NAME, null);
                count = cursor.getCount();
                cursor.close(); db.close();
                return count;
            case 2:
                cursor = db.rawQuery("SELECT  * FROM " + ThirdItem.TABLE_NAME, null);
                count = cursor.getCount();
                cursor.close(); db.close();
                return count;
        }
        return 0;
    }

    public long insertItem(int whichTable, String text) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        long id = 0;
        switch (whichTable) {
            case 0:
                values.put(FirstItem.COLUMN_TEXT, text);
                id = db.insert(FirstItem.TABLE_NAME, null, values);
                break;
            case 1:
                values.put(SecondItem.COLUMN_TEXT, text);
                id = db.insert(SecondItem.TABLE_NAME, null, values);
                break;
            case 2:
                values.put(ThirdItem.COLUMN_TEXT, text);
                id = db.insert(ThirdItem.TABLE_NAME, null, values);
                break;
        }

        db.close();
        return id;
    }

    public void updateItem(int whichTable, long id, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        switch(whichTable) {
            case 0:
                values.put(FirstItem.COLUMN_TEXT, text);

                db.update(FirstItem.TABLE_NAME, values, FirstItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                break;
            case 1:
                values.put(SecondItem.COLUMN_TEXT, text);

                db.update(SecondItem.TABLE_NAME, values, SecondItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                break;
            case 2:
                values.put(ThirdItem.COLUMN_TEXT, text);

                db.update(ThirdItem.TABLE_NAME, values, ThirdItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                break;
        }
        db.close();
    }

    public void deleteItem(int whichTable, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        switch(whichTable) {
            case 0:
                db.delete(FirstItem.TABLE_NAME, FirstItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                break;
            case 1:
                db.delete(SecondItem.TABLE_NAME, SecondItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                break;
            case 2:
                db.delete(ThirdItem.TABLE_NAME, ThirdItem.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                break;
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}