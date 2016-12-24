package com.tawab.fhict.afterevents;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TextView getEventsOutput;
    ProgressBar pb;
    SearchView sv;
    CoordinatorLayout coordinatorLayout;
    public static final String PHOTOS_BASE_URL = "http://afty.nl/SMAN7/photos/";
    List<EvenementenClass> eventList;
    List<EvenementenClass> searchedEvents;
    public static final String EVENT_ID = "EVENT_ID";
    public static final int DETAIL_REQUEST = 1111;
    public static final String TAG = "TESTINGGGGGG: ";

    private boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getEventsOutput = (TextView) findViewById(R.id.getEventsTV);
        getEventsOutput.setMovementMethod(new ScrollingMovementMethod());


        requestData("http://afty.nl/SMAN7/index.php");

        //GetEventsAdapter adapter = new GetEventsAdapter(this, R.layout.fragment_main, eventList);
        ListView lv = (ListView) findViewById(R.id.listViewGetEvents);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                System.out.println("ON LISTVIEW CLICKED@@@@@@@@@@@@@@@@@" + position + id);

                EvenementenClass clickedEvent;
                if (isSearching) {
                    clickedEvent = searchedEvents.get(position);
                } else {
                    clickedEvent = eventList.get(position);
                }

                Intent intent = new Intent(MainActivity.this, EvenementDetailActivity.class);
                intent.putExtra("event", clickedEvent);
                intent.putExtra("image", clickedEvent.getBitmap());

                System.out.println("@@@@@@@@bitmappie@@@@@ " + clickedEvent.getBitmap());

                startActivityForResult(intent, DETAIL_REQUEST);



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        sv = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        sv.setOnQueryTextListener(MainActivity.this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

/*        if (id == R.id.SQLite) {
            Intent intent1 = new Intent(this, TestSQLite.class);
            startActivity(intent1);
            return true;
        } */

        if (id == R.id.fragments){
            Intent intent1 = new Intent(this, socketTest.class);
            startActivity(intent1);
            return true;
        } else if (id == R.id.transitions){
            Intent intent2 = new Intent(this, TransitionMainActivity.class);
            startActivity(intent2);
            return true;
        } else if (id == R.id.webviewMenu) {
            Intent intent3 = new Intent(this, Webview.class);
            startActivity(intent3);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void requestData(String uri) {

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        // volley request
        StringRequest request = new StringRequest(uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                eventList = getEventsJSONParser.parseFeed(response);
                updateDisplay(eventList);
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    protected void updateDisplay(List<EvenementenClass> events) {
        //Use adapter to display data

        GetEventsAdapter adapter = new GetEventsAdapter(this, R.layout.activity_main, events);
        ListView lv = (ListView) findViewById(R.id.listViewGetEvents);
        lv.setAdapter(adapter);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Snackbar.make(findViewById(R.id.action_search), "Aan het zoeken: " + query, Snackbar.LENGTH_LONG).show();
        System.out.println(query);
        sv.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        System.out.println("TYPING ============" + newText);
        searchedEvents = new ArrayList<>();

        if(newText.length() == 0) {
            isSearching = false;
        } else {
            isSearching = true;
        }

        for(EvenementenClass event : eventList) {
            if(event.getEventName().toLowerCase().contains(newText.toLowerCase())) {
                searchedEvents.add(event);
            }
        }

        updateDisplay(searchedEvents);

        return false;
    }

/*    // broadcast a custom intent.
    static MyReceiver tickReceiver = new MyReceiver();

    void setTimeTick()
    {
        registerReceiver(tickReceiver, new IntentFilter(
                "android.intent.action.TIME_TICK"));
        Toast.makeText(this, "Registered broadcast receiver", Toast.LENGTH_SHORT)
                .show();

        //Register the broadcast receiver to receive TIME_TICK
        registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }*/

}
