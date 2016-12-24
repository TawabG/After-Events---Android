package com.tawab.fhict.afterevents;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

public class TestSQLite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sqlite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(this);

        // Inserting Contacts
//        Log.d("Insert: ", "Inserting ..");
//        db.addAfters(new Afters(0, "After1", "Restaurant", 123.123, 123.123));
//        db.addAfters(new Afters(0, "After2", "After Party", 123.123, 123.123));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Afters> afterparties = db.getAllAfters();

        for (Afters cn : afterparties) {
            String log = "Id: "+cn.get_afterid()+" ,Name: " + cn.get_afterNaam() + " ,Soort: " + cn.get_afterSoort() + " ,Lat: " + cn.get_lat() + " ,Lon: " + cn.get_lon();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }



    }


}
