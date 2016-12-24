package com.tawab.fhict.afterevents;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class socketTest extends AppCompatActivity {

    private EditText inputMessage;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://85.214.118.187:8085");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSocket.on("receiveMessage", onNewMessage);

        mSocket.connect();



        Button clickButton = (Button) findViewById(R.id.sendSocketMsgBtn);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send message server
                inputMessage  = (EditText)findViewById(R.id.inputMsg);
                String message = inputMessage.getText().toString().trim();
                if(TextUtils.isEmpty(message)){
                    return;
                } else {
                    inputMessage.setText("");
                    mSocket.emit("message", message);


                }
            }
        });

    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            socketTest.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = (String) args[0];


                    System.out.println("your message is: " + str);

                    TextView myAwesomeTextView = (TextView)findViewById(R.id.messagesTV);
                    myAwesomeTextView.setText(str);


/*                    ArrayList<String> messages;
                    messages = new ArrayList<String>();
                    messages.add(str);

                    ListView lv = (ListView)findViewById(R.id.messagesListView);
                    ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(socketTest.this, android.R.layout.simple_list_item_1, messages);
                    lv.setAdapter(myarrayAdapter);*/

                }
            });
        }
    };



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }





}
