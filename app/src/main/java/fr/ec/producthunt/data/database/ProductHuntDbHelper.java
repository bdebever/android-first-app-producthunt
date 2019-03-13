package fr.ec.producthunt.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static fr.ec.producthunt.data.database.DataBaseContract.DATABASE_NAME;
import static fr.ec.producthunt.data.database.DataBaseContract.DATABASE_VERSION;
import static fr.ec.producthunt.data.database.DataBaseContract.PostTable.SQL_CREATE_POST_TABLE;
import static fr.ec.producthunt.data.database.DataBaseContract.PostTable.SQL_DROP_POST_TABLE;

public class ProductHuntDbHelper extends SQLiteOpenHelper {

    public ProductHuntDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_POST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        db.execSQL(SQL_DROP_POST_TABLE);
        onCreate(db);
    }

}
