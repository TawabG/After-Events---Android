package com.tawab.fhict.afterevents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class getEventsJSONParser {

    public static List<EvenementenClass> parseFeed(String content) {
        try {
            JSONArray array = new JSONArray(content);
            List<EvenementenClass> evenementenClassList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                EvenementenClass events = new EvenementenClass();

                events.setEventId(obj.getInt("eventId"));
                events.setEventName(obj.getString("eventName"));
                events.setEventCategory(obj.getString("eventCategory"));
                events.setEventPlace(obj.getString("eventPlace"));
                events.setEventGenre(obj.getString("eventGenre"));
                events.setEventInfo(obj.getString("eventInfo"));
                events.setEventStart(obj.getString("eventStart"));
                events.setEventEnd(obj.getString("eventEnd"));
                events.setEventImage(obj.getString("eventImage"));

                evenementenClassList.add(events);
            }
            return evenementenClassList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}