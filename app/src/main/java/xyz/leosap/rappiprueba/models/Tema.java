package xyz.leosap.rappiprueba.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import xyz.leosap.rappiprueba.common.Constants;

/**
 * Created by LeoSap on 7/01/2017.
 */

public class Tema {

    public static String tabla = "tema";

    //properties
    String id;
    String title;
    String headerTitle;
    String displayName;
    String description;
    long created;

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    int subscribers;
    String publicDescription;

    public String getPublicDescriptionHtml() {
        return publicDescriptionHtml;
    }

    public void setPublicDescriptionHtml(String publicDescriptionHtml) {
        this.publicDescriptionHtml = publicDescriptionHtml;
    }

    String publicDescriptionHtml;

    public String getDescriptionHtml() {
        return DescriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        DescriptionHtml = descriptionHtml;
    }

    String DescriptionHtml;
    String bannerImg;
    String headerImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getPublicDescription() {
        return publicDescription;
    }

    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(String keyColor) {
        this.keyColor = keyColor;
    }

    public String getSubmitText() {
        return submitText;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    String iconImg;
    String url;
    String keyColor;
    String submitText;


    public static ArrayList<Tema> getAll(SQLiteDatabase db) {

        ArrayList<Tema> list = new ArrayList();
        String query = "select * from " + tabla;

        if (Constants.debug) Log.d("LS query", query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(loadFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public static Tema find(SQLiteDatabase db, String id) {


        String query = "select * from " + tabla + " where id='" + id + "'";

        if (Constants.debug) Log.d("LS query", query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return loadFromCursor(cursor);
        } else return null;


    }


    public static void truncate(SQLiteDatabase db) {
        String query = "delete from " + tabla;
        if (Constants.debug) Log.d("LS query", query);
        db.execSQL(query);


    }


    public void save(SQLiteDatabase db) {
        db.insert(tabla, null, getContentValues());

    }

    public void update(SQLiteDatabase db) {
        String where = "id=?";
        String[] whereArgs = new String[]{getId()};

        db.update(tabla, getContentValues(), where, whereArgs);

    }


    public static Tema loadFromCursor(Cursor cursor) {
        Tema tema = new Tema();
        tema.setId(cursor.getString(0));
        tema.setTitle(cursor.getString(1));
        tema.setHeaderTitle(cursor.getString(2));
        tema.setDisplayName(cursor.getString(3));
        tema.setDescription(cursor.getString(4));
        tema.setCreated(cursor.getInt(5));
        tema.setPublicDescription(cursor.getString(6));
        tema.setBannerImg(cursor.getString(7));
        tema.setHeaderImg(cursor.getString(8));
        tema.setIconImg(cursor.getString(9));
        tema.setUrl(cursor.getString(10));
        tema.setKeyColor(cursor.getString(11));
        tema.setSubmitText(cursor.getString(12));
        tema.setSubscribers(cursor.getInt(13));
        tema.setPublicDescriptionHtml(cursor.getString(14));
        tema.setDescriptionHtml(cursor.getString(15));


        return tema;
    }

    private ContentValues getContentValues() {
        ContentValues content = new ContentValues();
        content.put("id", getId());
        content.put("title", getTitle());
        content.put("header_title", getHeaderTitle());
        content.put("display_name", getDisplayName());
        content.put("description", getDescription());
        content.put("created", getCreated());
        content.put("public_description", getPublicDescription());
        content.put("banner_img", getBannerImg());
        content.put("header_img", getHeaderImg());
        content.put("icon_img", getIconImg());
        content.put("url", getUrl());
        content.put("key_color", getKeyColor());
        content.put("submit_text", getSubmitText());
        content.put("subscribers", getSubscribers());
        content.put("description_html", getDescriptionHtml());
        content.put("public_description_html", getPublicDescriptionHtml());

        return content;
    }
}
