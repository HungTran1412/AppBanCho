package dev.mhung.ltmobile.petapplication.database;

import static dev.mhung.ltmobile.petapplication.database.CartDatabase.COLUMN_IMAGE_URL;
import static dev.mhung.ltmobile.petapplication.database.CartDatabase.COLUMN_NAME;
import static dev.mhung.ltmobile.petapplication.database.CartDatabase.COLUMN_PRICE;
import static dev.mhung.ltmobile.petapplication.database.CartDatabase.COLUMN_QUANTITY;
import static dev.mhung.ltmobile.petapplication.database.CartDatabase.TABLE_CART;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dev.mhung.ltmobile.petapplication.model.CartItem;

public class CartDAO {
    private CartDatabase dbHelper;

    public CartDAO(Context context) {
        dbHelper = new CartDatabase(context);
    }

    public void addItem(CartItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put(COLUMN_PRICE, item.getPrice());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_IMAGE_URL, item.getImageUrl());
        db.insert(TABLE_CART, null, values);
        db.close();
    }
    public void updateItem(CartItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, item.getQuantity());
        db.update(TABLE_CART, values,
                CartDatabase.COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }
    public void updateAllItems(List<CartItem> cartItems) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (CartItem item : cartItems) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, item.getName());
                values.put(COLUMN_PRICE, item.getPrice());
                values.put(COLUMN_QUANTITY, item.getQuantity());
                values.put(COLUMN_IMAGE_URL, item.getImageUrl());
                db.update(TABLE_CART, values, CartDatabase.COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public CartItem getItemByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART,
                null, COLUMN_NAME + " = ?", new String[]{name}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            CartItem item = new CartItem();
            item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            item.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
            item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
            item.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
            cursor.close();
            db.close();
            return item;
        }
        cursor.close();
        db.close();
        return null;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_CART,
                CartDatabase.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<CartItem> getAllItems() {
        List<CartItem> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CartDatabase.COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                item.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                item.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void clearCart() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }
}
