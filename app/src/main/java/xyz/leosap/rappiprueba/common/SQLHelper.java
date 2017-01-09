package xyz.leosap.rappiprueba.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LeoSap on 7/01/2017.
 */

class SQLHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_TEMA = "CREATE TABLE tema (id TEXT, title TEXT,header_title TEXT, display_name TEXT, description TEXT, created NUMBER, public_description TEXT, banner_img TEXT, header_img TEXT, icon_img TEXT, url TEXT, key_color TEXT, submit_text TEXT,subscribers NUMBER,public_description_html TEXT,description_html TEXT )";

    public SQLHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS tema");
        db.execSQL(CREATE_TABLE_TEMA);

    }
}