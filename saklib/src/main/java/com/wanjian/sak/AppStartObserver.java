package com.wanjian.sak;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by wanjian on 2018/2/7.
 */
@Deprecated
abstract class AppStartObserver extends ContentProvider {
    @Override
    public boolean onCreate() {
        onStart();
        return true;
    }

    protected abstract void onStart();

    @Override
    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public final String getType(Uri uri) {
        return null;
    }

    @Override
    public final Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public final int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public final int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
