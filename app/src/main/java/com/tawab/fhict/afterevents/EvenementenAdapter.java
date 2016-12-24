package com.tawab.fhict.afterevents;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

class GetEventsAdapter extends ArrayAdapter<EvenementenClass> {

    private Context context;
    private List<EvenementenClass> evenementenClassList;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue queue;

    public GetEventsAdapter(Context context, int resource, List<EvenementenClass> objects) {
        super(context, resource, objects);
        this.context = context;
        this.evenementenClassList = objects;

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);

        queue = Volley.newRequestQueue(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // custom xml file --> list_get_events.xml
        View view = inflater.inflate(R.layout.list_get_events, parent, false);



        //display event name in textview (textViewGE1)
        final EvenementenClass event = evenementenClassList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textViewGE1);
        tv.setText(event.getEventName());

        //display event time in textview (textViewGE2)
        final EvenementenClass event2 = evenementenClassList.get(position);
        TextView tv2 = (TextView) view.findViewById(R.id.textViewGE2);
        //tv2.setText("Event Starts: " + event.getEventStart());
        tv2.setText(event.getEventPlace());

        //display event ending time in textview (textViewGE3)
        final EvenementenClass event3 = evenementenClassList.get(position);
        TextView tv3 = (TextView) view.findViewById(R.id.textViewGE3);
        //tv3.setText("Event Ends: " + event.getEventEnd());
        tv3.setText("");

        //display event place in textview (textviewGE4)
        final EvenementenClass event4 = evenementenClassList.get(position);
        TextView tv4 = (TextView) view.findViewById(R.id.textViewGE4);
        //tv4.setText(event.getEventPlace());
        tv4.setText("");


        //*********------display event photo in ImageView (imageViewGE1)------**************

        Bitmap bitmap = imageCache.get(event.getEventId()); //getEventImage
        final ImageView image = (ImageView) view.findViewById(R.id.imageViewGE1);

        if (bitmap != null) {

            image.setImageBitmap(bitmap);
        } else {
            String imageUrl = MainActivity.PHOTOS_BASE_URL + event.getEventImage();

            // image (80 width and 80height max)
            ImageRequest request = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    image.setImageBitmap(response);
                    getItem(position).setBitmap(response);
                    imageCache.put(event.getEventId(), response);
                }
            },400,400,Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("GetEventsAdapter", error.getMessage());
                        }
                    });
            queue.add(request);
        }

        return view;
    }

}
