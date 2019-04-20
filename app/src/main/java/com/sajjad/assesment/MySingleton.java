package com.sajjad.assesment;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mySingleton;
    private RequestQueue requestQueue;
    private static Context context;

    private  MySingleton(Context cont){

        context = cont;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getInstance(Context context){
        if(mySingleton==null){
            mySingleton= new MySingleton(context);
        }
        return mySingleton;
    }

    public <T>void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }
}

