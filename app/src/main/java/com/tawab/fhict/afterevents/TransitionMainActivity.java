package com.tawab.fhict.afterevents;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TransitionMainActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_main);

        imgLogo = (ImageView) findViewById(R.id.tomorrowlandImage);
        text = (TextView) findViewById(R.id.tomorrowlandText);

    }

    public void sharedElementTransition(View view) {

        Pair[] pair = new Pair[2];
        pair[0] = new Pair<View, String>(imgLogo, "tomorrowlandImage");
        pair[1] = new Pair<View, String>(text, "tomorrowlandText");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
        Intent i = new Intent(TransitionMainActivity.this, SharedElementActivity.class);
        startActivity(i, options.toBundle());
    }
}
