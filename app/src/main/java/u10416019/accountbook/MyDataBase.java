package u10416019.accountbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eva on 2017/12/28.
 */

public class MyDataBase extends SQLiteOpenHelper {
    public String DATABASE_TABLE = "ACCOUNT";
    public final String DB_CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE
            +"(_ID INTEGER PRIMARY KEY NOT NULL, "+"DATE DATETIME NOT NULL,"+"MONEY INTEGER ,"+
            "INCOME INTEGER,"+"NECESSARY INTEGER,"+"TYPE VARCHAR,"+"CONTENT VARCHAR,"+
            "FREQUENCY VARCHAR);";
    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }

}
