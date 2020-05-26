package com.example.final_exam;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;

public class ReqFromServer implements Serializable {
    private final long serialVersionUID = 1L;

    public static void getRequest(final Context t, final Callback callback) {

        RequestQueue queue = Volley.newRequestQueue(t);

        String URL = "https://final-exam-mobile.herokuapp.com/appointment";
        StringRequest request = new StringRequest(Request.Method.GET,
                URL,
                response -> {
                    Log.d("response:", response);
                    callback.processJSON(response);
                }, error -> {
            Log.d("Problem: ", "Can't connect to server");
            callback.onError();
        });
        queue.add(request);
    }

    public interface Callback {
        void processJSON(String response);
        void onError();
    }
}
