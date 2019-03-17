package saadcallog.softylogic.com.saadcalllogtestphase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {


    Button BtnCheckPermission;
    Button BtnSynchCallLogs;
    Button BtnViewCallLogs;
    Context c;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        getCalldetailsNow();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_LONG).show();
                }
            }
        }
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = getApplicationContext();
BtnCheckPermission = findViewById(R.id.btnchkper);
        BtnSynchCallLogs = findViewById(R.id.btnsynch);
        BtnViewCallLogs = findViewById(R.id.btnviewcall);
        BtnCheckPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CALL_LOG}, 1);

                } else {
                    Toast.makeText(MainActivity.this, "Permission Already Granted", Toast.LENGTH_LONG).show();
                }
            }
        });
        BtnSynchCallLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                    getCalldetailsNow();

                } else {
                    getCalldetailsNow();
                }
                Toast.makeText(MainActivity.this, "Database Synched", Toast.LENGTH_LONG).show();
            }
        });
        BtnViewCallLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                MainActivity.this.startActivity(intent);
            }
        });


    }

    private void getCalldetailsNow() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

        }

        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");
        managedCursor.moveToFirst();
        DBHelper db=new DBHelper(c, "saaddCallLog.db", null, 2);

        for (int i = 0 ; i < 50 ; i++){
            while( managedCursor.moveToNext()) {
        String phNumber = managedCursor.getString(managedCursor.getColumnIndex( CallLog.Calls.NUMBER ));
        String callDuration = managedCursor.getString(managedCursor.getColumnIndex( CallLog.Calls.DURATION));
        String type=managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.TYPE));
        String date=managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.DATE));
        String dir = null;
        int dircode = Integer.parseInt(type);
        switch (dircode)
        {
            case CallLog.Calls.OUTGOING_TYPE:
                dir = "OUTGOING";
                break;
            case CallLog.Calls.INCOMING_TYPE:
                dir = "INCOMING";
                break;
            case CallLog.Calls.MISSED_TYPE:
                dir = "MISSED";
                break;
            default:
                dir = "MISSED";
                break;
        }
        SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf_time = new SimpleDateFormat("h:mm a");
        String dateString = sdf_date.format(new Date(Long.parseLong(date)));
        String timeString = sdf_time.format(new Date(Long.parseLong(date)));
        db.insertdata(phNumber, dateString, timeString, callDuration, dir);

    }
        }

        db.close();

        managedCursor.close();

    }
}
