package dev.mhung.ltmobile.petapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import dev.mhung.ltmobile.petapplication.model.CartItem;

public class CartDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IMAGE_URL = "image_url";

    public CartDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void updateCartItem(CartItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, item.getId());
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_PRICE, item.getPrice());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_IMAGE_URL, item.getImageUrl());

        db.update(TABLE_CART, values, COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_CART + "'", null);
        if (cursor != null && cursor.getCount() > 0) {
            Log.i("CartDatabase", "Table " + TABLE_CART + " already exists.");
            cursor.close();
            return;
        }

        String sql = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_ID + " INTEGER," +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(sql);
        Log.i("CartDatabase", "Table " + TABLE_CART + " created successfully.");
        cursor.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }
}
