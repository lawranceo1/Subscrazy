package com.example.subscrazy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("unused")
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
    private double total_expense;
        private double remainingExpense;
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT UNIQUE,"
                + PAYMENT_COL + " TEXT,"
                + RECURRENCE_COL + " TEXT,"
                + BILLING_COL + " TEXT,"
                + NOTES_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
        total_expense =0;
        remainingExpense=0;
    }

    public int addNewSubscription(Subscription s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, s.getName());
        values.put(PAYMENT_COL, s.getPayment());
        values.put(RECURRENCE_COL, s.getRecurrence());
        values.put(BILLING_COL, s.getBillingTime());
        values.put(NOTES_COL, s.getNotes());

        int result = (int) db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }



    public void updateSubscription(String originalSubName,
                                   String subscriptionName,
                                   String subscriptionPrice,
                                   String subscriptionRecurrence,
                                   String subscriptionBillDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, subscriptionName);
        values.put(PAYMENT_COL, subscriptionPrice);
        values.put(RECURRENCE_COL, subscriptionRecurrence);
        values.put(BILLING_COL, subscriptionBillDate);
        //values.put(NOTES_COL, subscriptionNotes);

        db.update(TABLE_NAME, values, "name=?", new String[]{originalSubName});
        db.close();
    }

    public ArrayList<Subscription> readSubscriptions() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorSubscriptions = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Subscription> subscriptionsArrayList = new ArrayList<>();

        if (cursorSubscriptions.moveToFirst()) {
            do {
                if(cursorSubscriptions.getString(3).compareTo("Monthly")==0) {
                    total_expense += Double.parseDouble(cursorSubscriptions.getString(2));
                }
                if(isPending(cursorSubscriptions.getString(4)) && (cursorSubscriptions.getString(3).compareTo("Monthly") == 0)){
                    remainingExpense += Double.parseDouble(cursorSubscriptions.getString(2));
                }
                subscriptionsArrayList.add(new Subscription(
                        cursorSubscriptions.getString(1), //name
                        cursorSubscriptions.getString(2),//payment
                        cursorSubscriptions.getString(3),//monthly/yearly
                        cursorSubscriptions.getString(4),//date
                        cursorSubscriptions.getString(5)));//notes
            } while (cursorSubscriptions.moveToNext());
        }

        cursorSubscriptions.close();
        return subscriptionsArrayList;
    }


    public void deleteSubscription(String subscriptionName) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "name=?", new String[]{subscriptionName});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public double getTotalSpending() {
        return this.total_expense;
    }

    public boolean isPending(String date){
        String []dateParts = date.split("/");
        int dayOfMonth = Integer.parseInt(dateParts[0]);
        Calendar c = Calendar.getInstance();
        return dayOfMonth > c.get(Calendar.DAY_OF_MONTH);
    }

    public double getRemainingExpense(){
        return remainingExpense;
    }


}

