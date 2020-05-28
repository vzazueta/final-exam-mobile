package com.example.final_exam;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestLogin implements Serializable {
    private final long serialVersionUID = 1L;

    static private Activity activity;
    //private String URL = "https://final-exam-mobile.herokuapp.com/user/login";

    public RequestLogin()
    {

    }

    public static void getRequest(final Context t, final Callback callback, String URL, String id, String pass) {
        URL = URL+"/"+id+"/"+pass;
        activity = (Activity) t;

        Log.d("New URL", URL);
        RequestQueue queue = Volley.newRequestQueue(t);
        Log.d("Request status", "Getting data from server. . .");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("JSONObject", response.toString());
                        callback.processJSON(response);
                    }
                }, new Response.ErrorListener() {

                @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error in request", error.toString());
            }
        });

        queue.add(request);
    }

    public interface Callback {
        void processJSON(JSONObject response);
        void onError();
    }
}