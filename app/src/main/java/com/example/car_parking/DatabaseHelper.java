package com.example.car_parking;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserData.db";
    private static final int DATABASE_VERSION = 5;

    private static final String USER_TABLE_NAME = "User";
    private static final String RESERVATION_TABLE_NAME = "Reservation";
    private static final String CARD_TABLE_NAME = "CardDetails";
    private static final String PLACE_TABLE_NAME = "Place";

    private static final String USER_COLUMN_ID = "id";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_PHONE = "phone";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_PASSWORD = "pass";

    // Reservation table columns
    private static final String RESERVATION_COLUMN_ID = "id";
    private static final String RESERVATION_COLUMN_EMAIL = "email";
    private static final String RESERVATION_COLUMN_PLACE = "place";
    private static final String RESERVATION_COLUMN_PLACENAME = "placename";
    private static final String RESERVATION_COLUMN_CITY = "city";
    private static final String RESERVATION_COLUMN_FROMTIME = "fromtime";
    private static final String RESERVATION_COLUMN_TOTIME = "totime";
    private static final String RESERVATION_COLUMN_DATE = "date";
    private static final String RESERVATION_COLUMN_SLOTNUMBER = "slotnumber";
    private static final String RESERVATION_COLUMN_PRICE = "price";

    //Place table
    private static final String PLACE_COLUMN_ID = "id";
    private static final String PLACE_COLUMN_EMAIL = "email";
    private static final String PLACE_COLUMN_PLACE = "place";
    private static final String PLACE_COLUMN_CITY = "city";
    private static final String PLACE_COLUMN_PLACENAME = "placename";
    private static final String PLACE_COLUMN_SLOTNUMBERS = "slotnumbers";
    private static final String PLACE_COLUMN_IMAGE = "image";

    //card table
    private static final String CARD_COLUMN_ID = "id";
    private static final String CARD_COLUMN_EMAIL = "email"; // Foreign key
    private static final String CARD_COLUMN_BANK_NAME = "bank_name";
    private static final String CARD_COLUMN_CARD_NUMBER = "card_number";
    private static final String CARD_COLUMN_EXPIRE_DATE = "expire_date";
    private static final String CREATE_RESERVATION_TABLE = "CREATE TABLE " + RESERVATION_TABLE_NAME + " (" +
            RESERVATION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RESERVATION_COLUMN_EMAIL + " TEXT, " +
            RESERVATION_COLUMN_PLACE + " TEXT, " +
            RESERVATION_COLUMN_PLACENAME + " TEXT, " +
            RESERVATION_COLUMN_CITY + " TEXT, " +
            RESERVATION_COLUMN_FROMTIME + " TEXT, " +
            RESERVATION_COLUMN_TOTIME + " TEXT, " +
            RESERVATION_COLUMN_DATE + " TEXT, " +
            RESERVATION_COLUMN_SLOTNUMBER + " TEXT, " +
            RESERVATION_COLUMN_PRICE + " TEXT);";

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_COLUMN_NAME + " TEXT, " +
            USER_COLUMN_PHONE + " TEXT, " +
            USER_COLUMN_EMAIL + " TEXT, " + // Added "email" column
            USER_PASSWORD + " TEXT);";

    private static final String CREATE_CARD_TABLE = "CREATE TABLE " + CARD_TABLE_NAME + " (" +
            CARD_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CARD_COLUMN_EMAIL + " TEXT, " +
            CARD_COLUMN_BANK_NAME + " TEXT, " +
            CARD_COLUMN_CARD_NUMBER + " TEXT, " +
            CARD_COLUMN_EXPIRE_DATE + " TEXT);";

    private static final String CREATE_PLACE_TABLE = "CREATE TABLE " + PLACE_TABLE_NAME + " (" +
            PLACE_COLUMN_EMAIL + " TEXT, " +
            PLACE_COLUMN_PLACE + " TEXT, " +
            PLACE_COLUMN_CITY + " TEXT, " +
            PLACE_COLUMN_PLACENAME + " TEXT PRIMARY KEY, " +
            PLACE_COLUMN_SLOTNUMBERS + " TEXT, " +
            PLACE_COLUMN_IMAGE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RESERVATION_TABLE);
        db.execSQL(CREATE_CARD_TABLE);
        db.execSQL(CREATE_PLACE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLACE_TABLE_NAME);
        onCreate(db);
    }

    public long insertUserData(String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME, name);
        values.put(USER_COLUMN_EMAIL, email);
        values.put(USER_COLUMN_PHONE, phone);
        values.put(USER_PASSWORD, password);
        long newRowId = db.insert(USER_TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public long insertPlaceData(String email, String place, String city, String placename,String slotnumbers,String img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLACE_COLUMN_EMAIL, email);
        values.put(PLACE_COLUMN_PLACE, place);
        values.put(PLACE_COLUMN_CITY, city);
        values.put(PLACE_COLUMN_PLACENAME, placename);
        values.put(PLACE_COLUMN_SLOTNUMBERS, slotnumbers);
        values.put(PLACE_COLUMN_IMAGE, img);
        long newRowId = db.insert(PLACE_TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public String getUserInfoByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userInfo = null;
        String query = "SELECT " + USER_COLUMN_NAME + ", " + USER_COLUMN_EMAIL + ", " + USER_COLUMN_PHONE + ", " + USER_PASSWORD + " FROM " + USER_TABLE_NAME + " WHERE " + USER_COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(USER_COLUMN_NAME));
            String userEmail = cursor.getString(cursor.getColumnIndex(USER_COLUMN_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PHONE));
            String password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));

            userInfo = "Name: " + name + "\nEmail: " + userEmail + "\nPhone: " + phone + "\nPassword: " + password;
            cursor.close();
        }
        db.close();

        return userInfo;
    }

    @SuppressLint("Range")
    public Login loginfetch(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Login login = new Login();
        String query = "SELECT " + USER_COLUMN_EMAIL + ", " + USER_PASSWORD +
                " FROM " + USER_TABLE_NAME + " WHERE " + USER_COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            login.email = cursor.getString(cursor.getColumnIndex(USER_COLUMN_EMAIL));
            login.password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
            login.flag = true; // Email found in the database
            cursor.close();
        } else {
            login.flag = false; // Email not found in the database
        }

        db.close();
        return login;
    }

    // Inside DatabaseHelper.java
    public long insertReservation(String userEmail, String place, String placename, String city, String fromTime, String toTime, String date, String slotNumber, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RESERVATION_COLUMN_EMAIL, userEmail);
        values.put(RESERVATION_COLUMN_PLACE, place);
        values.put(RESERVATION_COLUMN_PLACENAME, placename);
        values.put(RESERVATION_COLUMN_CITY, city);
        values.put(RESERVATION_COLUMN_FROMTIME, fromTime);
        values.put(RESERVATION_COLUMN_TOTIME, toTime);
        values.put(RESERVATION_COLUMN_DATE, date);
        values.put(RESERVATION_COLUMN_SLOTNUMBER, slotNumber);
        values.put(RESERVATION_COLUMN_PRICE, price);

        long result = db.insert("Reservation", null, values);
        db.close();

        return result;
    }

    public List<CardItem> getCardDetailsByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String userName1 = null;
        String[] columns1 = {USER_COLUMN_NAME};
        String selection1 = USER_COLUMN_EMAIL + " = ?";
        String[] selectionArgs1 = {email};
        Cursor cursor1 = db.query(USER_TABLE_NAME, columns1, selection1, selectionArgs1, null, null, null);

        if (cursor1 != null && cursor1.moveToFirst()) {
            userName1 = cursor1.getString(cursor1.getColumnIndex(USER_COLUMN_NAME));
            cursor1.close();
        }


        List<CardItem> cardItems = new ArrayList<>();
        String[] columns = {CARD_COLUMN_BANK_NAME, CARD_COLUMN_CARD_NUMBER, CARD_COLUMN_EXPIRE_DATE};
        String selection = CARD_COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(CARD_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String bankName = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_BANK_NAME));
                String cardNumber = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_CARD_NUMBER));
                String expireDate = cursor.getString(cursor.getColumnIndex(CARD_COLUMN_EXPIRE_DATE));

                cardItems.add(new CardItem(userName1, bankName, cardNumber, expireDate, email));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return cardItems;
    }

    public void insCard(String email, String bankName, String cardNumber, String expireDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_COLUMN_EMAIL, email);
        values.put(CARD_COLUMN_BANK_NAME, bankName);
        values.put(CARD_COLUMN_CARD_NUMBER, cardNumber);
        values.put(CARD_COLUMN_EXPIRE_DATE, expireDate);
        db.insert(CARD_TABLE_NAME, null, values);
        db.close();
    }


    public List<CardItem1> getReservationsByEmailAndDate(String email) {
        List<CardItem1> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        // Define your database query to fetch reservations for the given user and date
        String query = "SELECT " +
                RESERVATION_COLUMN_PLACE + ", " +
                RESERVATION_COLUMN_FROMTIME + ", " +
                RESERVATION_COLUMN_TOTIME + ", " +
                RESERVATION_COLUMN_DATE + ", " +
                RESERVATION_COLUMN_SLOTNUMBER + ", " +
                RESERVATION_COLUMN_PRICE +
                " FROM " + RESERVATION_TABLE_NAME +
                " WHERE " + RESERVATION_COLUMN_EMAIL + " = ?" +
                " AND " + RESERVATION_COLUMN_DATE + " <= ?";

        Cursor cursor = db.rawQuery(query, new String[]{email, sdf.format(currentDate)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String place = cursor.getString(cursor.getColumnIndex(RESERVATION_COLUMN_PLACE));
                String fromTime = cursor.getString(cursor.getColumnIndex(RESERVATION_COLUMN_FROMTIME));
                String toTime = cursor.getString(cursor.getColumnIndex(RESERVATION_COLUMN_TOTIME));
                String date = cursor.getString(cursor.getColumnIndex(RESERVATION_COLUMN_DATE));
                String slotName = cursor.getString(cursor.getColumnIndex(RESERVATION_COLUMN_SLOTNUMBER));
                String price = cursor.getString(cursor.getColumnIndex(RESERVATION_COLUMN_PRICE));

                CardItem1 reservation = new CardItem1(place, fromTime, toTime, date, slotName, price);
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return reservations;
    }

    //Retreive parking area city and place
    public Cursor showParking_area(String city1, String place1) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + PLACE_TABLE_NAME +
                " WHERE " + PLACE_COLUMN_PLACE + " = ? AND " + PLACE_COLUMN_CITY + " = ?";
        String[] selectionArgs = {place1, city1};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        // db.close();  // It's not necessary to close the database here; let the calling code handle it.

        return cursor;
    }

    //Retreive parking area with the help of city
    public Cursor showParking_area(String city1) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + PLACE_TABLE_NAME +
                " WHERE " + PLACE_COLUMN_CITY + " = ?";
        String[] selectionArgs = {city1};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        // db.close();  // It's not necessary to close the database here; let the calling code handle it.

        return cursor;
    }

    public Cursor checkuser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE_NAME +
                " WHERE " + USER_COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        // db.close();  // It's not necessary to close the database here; let the calling code handle it.

        return cursor;
    }


    public Cursor checkAvailability(String date, String fromTime, String toTime, String place, String city) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + RESERVATION_TABLE_NAME +
                " WHERE " + RESERVATION_COLUMN_DATE + " = ?" +
                " AND " + RESERVATION_COLUMN_FROMTIME + " <= ?" +
                " AND " + RESERVATION_COLUMN_TOTIME + " >= ?" +
                " AND " + RESERVATION_COLUMN_PLACE + " = ?" +
                " AND " + RESERVATION_COLUMN_CITY + " = ?";

        String[] selectionArgs = {date, toTime, fromTime, place, city};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Check if the Cursor has data
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;  // Cursor has data, return it
        } else {
            return null;  // Cursor is empty
        }
    }

    public Cursor displsyPlace(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + PLACE_TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }

    //    Delete venue function
    public boolean delete(String email,String parking,String place,String city,String slot) {
        SQLiteDatabase db = this.getWritableDatabase();

        System.out.println(parking);

        try {
            String deleteQuery = "DELETE FROM " + PLACE_TABLE_NAME + " WHERE " + PLACE_COLUMN_EMAIL + " = '" + email + "'"+ " AND " + PLACE_COLUMN_PLACENAME + " = '" + parking + "'"+ " AND " + PLACE_COLUMN_PLACE + " = '" + place + "'"+ " AND " + PLACE_COLUMN_CITY + " = '" + city + "'"+ " AND " + PLACE_COLUMN_SLOTNUMBERS + " = '" + slot + "'";
            db.execSQL(deleteQuery);
            db.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }

    public Cursor update(String email,String parking,String location,String image,String slot,String newlocation){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "UPDATE " + PLACE_TABLE_NAME +
                " SET " + PLACE_COLUMN_PLACE + " = ?" +
                " , " + PLACE_COLUMN_IMAGE+ " = ?" +
                " , " + PLACE_COLUMN_SLOTNUMBERS + " = ?" +
                " WHERE " + PLACE_COLUMN_EMAIL + " = ?" +
                " AND " + PLACE_COLUMN_PLACE+ " = ?" +
                " AND " + PLACE_COLUMN_PLACENAME + " = ?";
        String[] selectionArgs = {newlocation,image,slot,email,location,parking};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        return cursor;
    }




    public UserInfo getUserInfoByEmail1(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserInfo userInfo = null;

        String[] columns = {USER_COLUMN_NAME, USER_COLUMN_PHONE};
        String selection = USER_COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(USER_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(USER_COLUMN_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PHONE));

            userInfo = new UserInfo(name, phone);
            System.out.println("name:"+name);
            System.out.println("phone:"+phone);

            cursor.close();
        }

        db.close();

        return userInfo;
    }
}


