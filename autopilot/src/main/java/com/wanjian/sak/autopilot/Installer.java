package com.wanjian.sak.autopilot;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wanjian.sak.SAK;

public class Installer extends ContentProvider {
    @Override
    public boolean onCreate() {
        Context context = getContext();
        context.registerReceiver(new BroadcastReceiver() {
            boolean installed = false;

            @Override
            public void onReceive(Context context, Intent intent) {
                if (installed) {
                    SAK.unInstall();
                } else {
                    SAK.init((Application) context, null);
                }
                installed = !installed;
            }
        }, new IntentFilter("com.sak"));
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
