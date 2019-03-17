package saadcallog.softylogic.com.saadcalllogtestphase;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rehan on 8/6/2018.
 */

public class Missed extends Fragment {
    ArrayList<DatabaseItems> list;
    ArrayList<DatabaseItems> list2;
    Context c;
    CustomAdapter customAdapter;
    public Missed() {
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
        View view = inflater.inflate(R.layout.missed, container, false);


        ListView lv =  view.findViewById(R.id.listview);
        list = getDatabaseItemsList();
        for(int i = list.size() - 1 ; i > 49 ; i--){
            list.remove(i);
        }
        for(int j = 0 ; j < list.size() ; j ++){

            if(list.get(j).getType().equals("MISSED")){
                list2.add(list.get(j));
            }

        }
        customAdapter = new CustomAdapter(list2 , c);

        lv.setAdapter(customAdapter);
        return view;
    }
    private ArrayList<DatabaseItems> getDatabaseItemsList() {

        ArrayList<DatabaseItems> resultList = new ArrayList<DatabaseItems>();

        DBHelper db=new DBHelper(c, "saaddCallLog.db", null, 2);

        Cursor c = db.getData();

        while(c.moveToNext() ){
            String id = c.getString(c.getColumnIndex("id"));
            String number = c.getString(c.getColumnIndex("number"));
            String date = c.getString(c.getColumnIndex("date"));
            String type = c.getString(c.getColumnIndex("type"));
            String duration = c.getString(c.getColumnIndex("duration"));
            DatabaseItems di = new DatabaseItems();
           // if(c.getColumnIndex("type") == CallLog.Calls.MISSED_TYPE){
            di.setDate(date);
            di.setId(id);
            di.setDuration(duration + " sec");
            di.setNumber(number);
            di.setType(type);
            resultList.add(di);

           //}

        }
        c.close();
        return resultList;
    }

}

