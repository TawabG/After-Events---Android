package com.tawab.fhict.afterevents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EvenementDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenement_detail);

        final EvenementenClass event = (EvenementenClass) getIntent().getSerializableExtra("event");
        //System.out.println(event.getEventName());

        /** Event Image **/
        Bitmap image = getIntent().getParcelableExtra("image");
        ImageView img= (ImageView) findViewById(R.id.imageViewTestLayout);
        img.setImageBitmap(image);

        /** Event Name **/
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.flexible_example_collapsing);
        ctl.setTitle(event.getEventName());

        /** Event Ending **/
        TextView mTextView3 = (TextView) findViewById(R.id.EventEndingTV);
        mTextView3.setText(event.getEventEnd().substring(10));

        /** Event Place **/
        TextView mTextView = (TextView) findViewById(R.id.TestLayoutEventPlaatsTV);
        mTextView.setText(event.getEventPlace());

        /** Event Genre **/
        TextView mTextView2 = (TextView) findViewById(R.id.EventGenreTV);
        mTextView2.setText(event.getEventGenre());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.layoutTestFoodfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(EvenementDetailActivity.this, GoogleMaps.class);
                Bundle extras = new Bundle();
                extras.putString("EventName", event.getEventName());
                extras.putString("EventPlace", event.getEventPlace());
                extras.putString("EventGenre", event.getEventGenre());
                intent2.putExtras(extras);
                startActivity(intent2);

            }
        });



    }

}
