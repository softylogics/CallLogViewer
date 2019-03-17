package saadcallog.softylogic.com.saadcalllogtestphase;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rehan on 8/6/2018.
 */

public class Incoming extends Fragment {
    ArrayList<DatabaseItems> list;
    Context c;
    CustomAdapter customAdapter;
    ArrayList<DatabaseItems> list2;

    public Incoming() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
list2 = new ArrayList<>();
        c = getActivity();

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incoming, container, false);


        ListView lv =  view.findViewById(R.id.listview);
        list = getDatabaseItemsList();
       
        customAdapter = new CustomAdapter(list2 , c);
        lv.setAdapter(customAdapter);
        return view;
    }

    private ArrayList<DatabaseItems> getDatabaseItemsList() {

        ArrayList<DatabaseItems> resultList = new ArrayList<DatabaseItems>();

        DBHelper db=new DBHelper(c, "saaddCallLog.db", null, 2);

        Cursor c = db.getData();
        try{
            while(c.moveToNext() ){
                String id = c.getString(c.getColumnIndex("id"));
                String number = c.getString(c.getColumnIndex("number"));
                String date = c.getString(c.getColumnIndex("date"));
                String type = c.getString(c.getColumnIndex("type"));
                String duration = c.getString(c.getColumnIndex("duration"));
                DatabaseItems di = new DatabaseItems();
                //if(c.getColumnIndex("type") == CallLog.Calls.INCOMING_TYPE){
                di.setDate(date);
                di.setId(id);
                di.setDuration(duration + " sec");
                di.setNumber(number);
                di.setType(type);
                resultList.add(di);

              //  }

            }
        }
        finally{
            if(c != null && !c.isClosed())
                c.close();
            db.close();
        }


        return resultList;
    }
}
