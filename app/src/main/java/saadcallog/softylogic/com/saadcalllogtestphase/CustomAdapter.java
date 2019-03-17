package saadcallog.softylogic.com.saadcalllogtestphase;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rehan on 8/6/2018.
 */

public class CustomAdapter extends ArrayAdapter<DatabaseItems> {
    ArrayList<DatabaseItems> dataset;
    Context context;
    DBHelper db;
    DatabaseItems dbit;
    private static class ViewHolder{
        TextView number;
        TextView date;
        TextView duration;
        Button button;
    }

    public CustomAdapter(@NonNull ArrayList<DatabaseItems> data, Context context) {

        super(context, R.layout.rowitem , data);
        this.dataset = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        db=new DBHelper(context, "saaddCallLog.db", null, 2);
        dbit = getItem(position);
        ViewHolder vh;
        View result;
        if(convertView == null){
            vh = new ViewHolder();
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.rowitem ,parent, false);
            vh.number = convertView.findViewById(R.id.numbertv);
            vh.date = convertView.findViewById(R.id.datetv);
            vh.duration = convertView.findViewById(R.id.durationtv);
            vh.button = convertView.findViewById(R.id.delbutt);

            result = convertView;
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
            result = convertView;
        }


            vh.number.setText(dbit.getNumber());
            vh.date.setText(dbit.getDate());
            vh.duration.setText(dbit.getDuration());
        vh.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteItem(dbit.getId());
                dataset.remove(position);
                notifyDataSetChanged();
            }
        });


        return convertView;
    }
}
