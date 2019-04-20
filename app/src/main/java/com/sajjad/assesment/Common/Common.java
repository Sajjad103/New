package com.sajjad.assesment.Common;

import android.location.Location;
import android.widget.ListView;

public class Common {
    public static final String API_ID = "8b406537bc28ce0f26065be8882f18d8";
    public static Location current_location = null;

    private ListView mListView;
    private static final String API_KEY = "YOUR_API_KEY";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String TYPE_SEARCH = "/nearbysearch";
    private static final String OUT_JSON = "/json?";
    private static final String LOG_TAG = "ListRest";
}
