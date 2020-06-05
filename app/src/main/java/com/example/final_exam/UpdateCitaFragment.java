package com.example.final_exam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_exam.types.Cita;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UpdateCitaFragment extends Fragment {
    private static String URL = "https://final-exam-mobile.herokuapp.com/";

    private Cita cita;

    private EditText etHora, etFecha, etDescripcion;
    private Button btnUpdate, btnRemove;

    public UpdateCitaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            cita = getArguments().getParcelable("cita");
        } catch(Exception ex){
            Toast.makeText(getActivity(), getContext().getString(R.string.mas_tarde), Toast.LENGTH_LONG).show();
            ((MainActivity)getActivity()).replaceFragment(new ListaCitasFragment());
        }
        return inflater.inflate(R.layout.fragment_update_cita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDescripcion = view.findViewById(R.id.et_update_descripcion);
        etHora = view.findViewById(R.id.et_update_hora);
        etFecha = view.findViewById(R.id.et_update_fecha);
        btnUpdate = view.findViewById(R.id.btn_actualizar);
        btnRemove = view.findViewById(R.id.btn_eliminar);


        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            Date date = defaultFormat.parse(cita.getFecha());
            etHora.setText(timeFormat.format(date));
            etFecha.setText(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        etDescripcion.setText(cita.getNotas());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean err = false;
                String message = getContext().getString(R.string.error1);
                if(etHora.getText().toString().equals("")){
                    err = true;
                    message += "\n"+getContext().getString(R.string.error2);
                }
                if(etFecha.getText().toString().equals("")){
                    err = true;
                    message += "\n"+getContext().getString(R.string.error3);
                }
                if(etDescripcion.getText().toString().equals("")){
                    err = true;
                    message += "\n"+getContext().getString(R.string.error4);
                }
                if(err){
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                cita.setNotas(etDescripcion.getText().toString());
                JSONObject json;
                try {
                    Date d = dateFormat.parse(etFecha.getText().toString());
                    d.setHours(Integer.parseInt(etHora.getText().toString().split(":")[0]));
                    d.setMinutes(Integer.parseInt(etHora.getText().toString().split(":")[1]));
                    cita.setFecha(outputFormat.format(d));
                    json = cita.getJSON();
                } catch(Exception ex){
                    Toast.makeText(getContext(), getContext().getString(R.string.error5), Toast.LENGTH_LONG).show();
                    return;
                }



                JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL + "appointment/" + cita.getId(), json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getContext(), getContext().getString(R.string.update), Toast.LENGTH_LONG).show();
                            ((MainActivity)getActivity()).replaceFragment(new ListaCitasFragment());
                        }
                    },
                    new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), getContext().getString(R.string.mas_tarde), Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
                rq.add(request);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, URL + "appointment/" + cita.getId(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(), getContext().getString(R.string.delete), Toast.LENGTH_LONG).show();
                        ((MainActivity)getActivity()).replaceFragment(new ListaCitasFragment());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), getContext().getString(R.string.mas_tarde), Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
                rq.add(request);
                ((MainActivity)getActivity()).replaceFragment(new ListaCitasFragment());
            }
        });
    }
}
