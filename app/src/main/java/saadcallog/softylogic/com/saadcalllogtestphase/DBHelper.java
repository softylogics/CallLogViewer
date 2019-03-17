package saadcallog.softylogic.com.saadcalllogtestphase;

/**
 * Created by Rehan on 8/6/2018.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists call_history (id integer primary key autoincrement" +
                " ,number text, date text, time text, duration text, type text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS call_history");
        onCreate(db);
    }

    public boolean insertdata(String number, String date, String time,String duration, String type)
    {
        SQLiteDatabase sdb=this.getWritableDatabase();
        sdb.execSQL("insert into call_history (number , date , time , duration, type) " +
                "values('"+number+"','"+date+"','"+time+"','"+duration+"','"+type+"')");
        return true;
    }

    public Cursor getData()
    {
        SQLiteDatabase sdb=this.getReadableDatabase();
        Cursor c=sdb.rawQuery("select * from call_history", null);
        return c;
    }
    public void deleteItem(String id)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("call_history" ,"id = " + id,null);
        db.close();

    }

}
