package com.example.democouchbase.MedicinePrescription;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManagerMedicine {
    private static Database database;
    private static DatabaseManagerMedicine instance = null;
    private static String dbName = "prescription";
    String TAG = "databasemanager";
    static int i = 1;

    protected DatabaseManagerMedicine() {

    }

    public static DatabaseManagerMedicine getSharedInstance() {
        if (instance == null) {
            instance = new DatabaseManagerMedicine();
        }

        return instance;
    }

    public static Database getDatabase() {
        return database;
    }

    public void initCouchbaseLite(Context context) {
        CouchbaseLite.init(context);
    }


    public void openOrCreateDatabase(Context context) {

        DatabaseConfiguration config = new DatabaseConfiguration();
        try {

            database = new Database(dbName, config);
            //registerForDatabaseChanges();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

//    public String createDocument() {
//        return database.createDocument().getId();
//    }

    public void insertDocument(String docId, MedicineModel m) {

        final Map<String, Object> med = new HashMap<String, Object>();
        med.put("name", m.getName());
        med.put("frequency", m.getFrequency());
        med.put("power", m.getPower());



        try {
            Document document = null;
            if (database.getDocument(docId) != null) {
                document = database.getDocument(docId);
            }
            // Update the document with more data
            Map<String, Object> updatedProperties = new HashMap<String, Object>();

            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            ArrayList arrayList=new ArrayList();

            if (document != null) {
                Map map=(document.toMap());
                arrayList= (ArrayList) map.get("medicines");
            }
            jsonObject.put("med" + i, m);
            jsonArray.put(jsonObject);

            arrayList.add(med);

            updatedProperties.put("medicines", arrayList);


            i++;
            MutableDocument mutableDocument = new MutableDocument(docId,updatedProperties );

            database.save(mutableDocument);
            //   mutableDocument.toMap();
            // Save to the Couchbase local Couchbase Lite DB
        } catch (CouchbaseLiteException | JSONException e) {
            Log.e(TAG, "Error putting", e);
        }


    }

    public Map<String, Object> showDocument(String docId) {
        Document document = database.getDocument(docId);

        Map<String, Object> med = document.toMap();
        return med;

    }


    public void deleteDatabase() {
        try {
            if (database != null) {
                database.delete();
                Log.e("deleted", "deleted database: ");
                database = null;
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

}


//JSONArray jsonArray=new JSONArray();
//arrayList=new ArrayList();
//n=jsonArray.length();
//for (int i=0;i<n;i++)
//     arrayList.add(jsonArray.get(i));

//ArrayList<ListItem> myCustomList = ArrayList<ListItem>();
//JSONArray jsonArray = new JSONArray();
//for (int i=0; i < myCustomList.size(); i++) {
//        jsonArray.put(myCustomList.get(i).getJSONObject());
//}