package com.example.evenyan.businesscard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by evenyan on 16/7/26.
 */
public class ContactDatebase extends SQLiteOpenHelper {

    public  static final String CREATE_CONTACT = "create table book ("
            + "card_id integer primary key autoincrement,"
            + "name text,"
            + "tel text,"
            + "fax,"
            + "phone text,"
            + "email text,"
            + "company_name text,"
            + "position text,"
            + "dept text,"
            + "website text,"
            + "addr text,"
            + "user_id integer)";

    private Context mContext;

    public ContactDatebase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CONTACT);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
