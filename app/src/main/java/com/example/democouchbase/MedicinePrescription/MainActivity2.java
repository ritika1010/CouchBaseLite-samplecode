package com.example.democouchbase.MedicinePrescription;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.example.democouchbase.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    EditText et_docid, et_medName, et_freq, et_pow, et_Getdocid;
    Button bt_add, bt_show;
    TextView textView;
    DatabaseManagerMedicine databaseManagerMedicine = DatabaseManagerMedicine.getSharedInstance();
    String TAG = "mainActivity2";
    String docId = "medicines";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         databaseManagerMedicine.initCouchbaseLite(getApplicationContext());
        databaseManagerMedicine.openOrCreateDatabase(getApplicationContext());
        databaseManagerMedicine.deleteDatabase();
        databaseManagerMedicine.openOrCreateDatabase(getApplicationContext());
        et_medName = findViewById(R.id.et_medName);
        et_freq = findViewById(R.id.et_freq);
        et_pow = findViewById(R.id.et_power);


        textView = findViewById(R.id.textView3);

        bt_add = findViewById(R.id.bt_add);
        bt_show = findViewById(R.id.bt_show);


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addToDatabase();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDatabase();
            }
        });
    }


    public void addToDatabase() throws CouchbaseLiteException {
        MedicineModel medicineModel = new MedicineModel(et_medName.getText().toString(), et_freq.getText().toString(), et_pow.getText().toString());

        databaseManagerMedicine.insertDocument(docId, medicineModel);
        Log.e(TAG, "addedToDatabase");

    }

    public void showFromDatabase() {
        Map<String, Object> med = databaseManagerMedicine.showDocument(docId);

        Log.e(TAG, "showFromDatabase--DATA:" + med);
        textView.setText("--DATA:" + med);
        Log.e(TAG, "showFromDatabase: "+ med.get("medicines") );

        ArrayList<Map> arrayList= (ArrayList) med.get("medicines");
        for(int i=0;i<arrayList.size();i++)
        {
            Log.e(TAG, "\narray"+i+arrayList.get(i).get("name") );
        }

    }

}