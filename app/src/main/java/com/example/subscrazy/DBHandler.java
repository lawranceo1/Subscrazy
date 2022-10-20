package com.example.subscrazy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "subscrazydb";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "subscriptions";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String PAYMENT_COL = "payment";
    private static final String RECURRENCE_COL = "recurrence";
    private static final String BILLING_COL = "billingTime";
    private static final String NOTES_COL = "notes";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + PAYMENT_COL + " DATE,"
                + RECURRENCE_COL + " MONEY,"
                + BILLING_COL + " TEXT,"
                + NOTES_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    public void addNewSubscription(Subscription s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, s.getName());
        values.put(PAYMENT_COL, s.getPayment().toString());
        values.put(RECURRENCE_COL, s.getRecurrence());
        values.put(BILLING_COL, s.getBillingTime());
        values.put(NOTES_COL, s.getNotes());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
