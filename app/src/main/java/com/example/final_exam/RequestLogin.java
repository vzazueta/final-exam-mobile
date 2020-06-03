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
    //private String URL = String.valueOf(R.string.LOGIN_URL);

    public RequestLogin()
    {

    }

    public static void getRequest(final Context t, final Callback callback, String URL, String id, String pass) {
        URL = URL+"/"+id+"/"+pass;
        activity = (Activity) t;

        Log.d(String.valueOf(R.string.NEW_URL_TAG), URL);
        RequestQueue queue = Volley.newRequestQueue(t);
        Log.d(String.valueOf(R.string.LOGIN_STATUS_TAG), String.valueOf(R.string.LOGIN_STATUS_MESSAGE));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(String.valueOf(R.string.REQUEST_RESPONSE_TAG), response.toString());
                        callback.processJSON(response);
                    }
                }, new Response.ErrorListener() {

                @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(String.valueOf(R.string.REQUEST_PROBLEM_TAG), error.toString());
            }
        });

        queue.add(request);
    }

    public interface Callback {
        void processJSON(JSONObject response);
        void onError();
    }
}