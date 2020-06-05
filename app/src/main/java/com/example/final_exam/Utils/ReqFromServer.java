package com.example.final_exam.Utils;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_exam.R;

import java.io.Serializable;

import static android.provider.Settings.System.getString;

public class ReqFromServer implements Serializable {
    private final long serialVersionUID = 1L;

    public static void getRequest(final Context t, final Callback callback) {

        RequestQueue queue = Volley.newRequestQueue(t);

        String URL = t.getString(R.string.APPOINTMENT_URL);
        StringRequest request = new StringRequest(Request.Method.GET,
                URL,
                response -> {
                    Log.d(t.getString(R.string.REQUEST_RESPONSE_TAG), response);
                    callback.processJSON(response);
                }, error -> {
            Log.d(t.getString(R.string.REQUEST_PROBLEM_TAG), t.getString(R.string.REQUEST_PROBLEM_MESSAGE));
            callback.onError();
            //Toast.makeText(t, error.toString(), Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }

    public interface Callback {
        void processJSON(String response);
        void onError();
    }
}
