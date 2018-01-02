package u10416019.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eva on 2017/12/28.
 */

public class MyDataBase extends SQLiteOpenHelper {
    private static MyDataBase instance=null;
    private static final String DATABASE_NAME="accountBook";
    public String TABLE_NAME = "ACCOUNT";

    public static final String KEY_ID = "_id";
    public static final String DATE="DATE";
    public static final String MONEY="MONEY";
    public static final String INCOME="INCOME";
    public static final String NECESSARY="NECESSARY";
    public static final String TYPE="TYPE";
    public static final String CONTENT="CONTENT";
    public static final String FREQUENCY="FREQUENCY";

    public static SQLiteDatabase database;

    public final String DB_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            +"("+KEY_ID+" INTEGER PRIMARY KEY NOT NULL, "+DATE +" DATETIME NOT NULL,"+MONEY+" VARCHAR ,"+
            INCOME+" INTEGER,"+NECESSARY+" INTEGER,"+TYPE+" VARCHAR,"+CONTENT+" VARCHAR,"+
            FREQUENCY+" VARCHAR);";

    public static MyDataBase getInstance(Context ctx){
        if (instance==null){
            instance = new MyDataBase(ctx, DATABASE_NAME, null, 1);
            database=getDatabase(ctx);
        }
        return instance;
    }

    public static SQLiteDatabase getDatabase (Context ctx){
        if (database == null || !database.isOpen()) {
            database = instance.getWritableDatabase();
        }
        return database;
    }

    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //Insert data into table
    public long insert(String date,String money,int income, int necessary, String typeItem,String content, String frequencyItem){
        ContentValues values = new ContentValues();
        values.put("DATE",date);
        values.put("MONEY",money);
        values.put("INCOME",income);
        values.put("NECESSARY",necessary);
        values.put("TYPE",typeItem);
        values.put("CONTENT",content);
        values.put("FREQUENCY",frequencyItem);

        return this.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public void update(long id,String date,String money,int income, int necessary, String typeItem,String content, String frequencyItem){
        ContentValues values = new ContentValues();
        values.put("DATE",date);
        values.put("MONEY",money);
        values.put("INCOME",income);
        values.put("NECESSARY",necessary);
        values.put("TYPE",typeItem);
        values.put("CONTENT",content);
        values.put("FREQUENCY",frequencyItem);

        this.getWritableDatabase().update(TABLE_NAME,values,KEY_ID+" = "+id,null);
    }


    public void delete(long id){
        String where = KEY_ID+" = "+id;
        database.delete(TABLE_NAME,where,null);
    }
}
