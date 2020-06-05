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
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;


public class CreateCitaFragment extends Fragment {
    private static String URL = "https://final-exam-mobile.herokuapp.com/";
    // 0=id, 1=name, 2=dob, 3=allergies
    private String[] content;

    private TextView tvNombre, tvEdad, tvAlergias;
    private EditText etHora, etFecha, etDescripcion;
    private Button btnSubmit;

    public CreateCitaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            content = getArguments().getString("content").split(",");
        } catch(Exception ex){
            Toast.makeText(getActivity(), "Porfavor verifique el codigo QR", Toast.LENGTH_LONG).show();
            ((StartActivity)getActivity()).replaceFragment(new StartFragment());
        }
        return inflater.inflate(R.layout.fragment_create_cita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNombre = view.findViewById(R.id.tv_cita_nombre);
        tvEdad = view.findViewById(R.id.tv_cita_edad);
        tvAlergias = view.findViewById(R.id.tv_cita_alergias);
        etDescripcion = view.findViewById(R.id.et_update_descripcion);
        etHora = view.findViewById(R.id.et_update_hora);
        etFecha = view.findViewById(R.id.et_update_fecha);
        btnSubmit = view.findViewById(R.id.btn_actualizar);

        try {
            tvNombre.setText(content[1]);
            tvEdad.setText("" + calculateAge(content[2]));
            tvAlergias.setText(content[3]);
        } catch(Exception ex){
            Toast.makeText(getActivity(), "Porfavor verifique el codigo QR", Toast.LENGTH_LONG).show();
            ((StartActivity)getActivity()).replaceFragment(new StartFragment());
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
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
                Cita cita = new Cita();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                cita.setUserId(content[0]);
                cita.setNotas(etDescripcion.getText().toString());
                JSONObject json;
                try {
                    Date d = dateFormat.parse(etFecha.getText().toString());
                    d.setHours(Integer.parseInt(etHora.getText().toString().split(":")[0]));
                    d.setMinutes(Integer.parseInt(etHora.getText().toString().split(":")[1]));
                    cita.setFecha(outputFormat.format(d));
                    json = cita.getJSON();
                } catch(Exception ex){
                    Toast.makeText(getContext(),  getContext().getString(R.string.error5), Toast.LENGTH_LONG).show();
                    return;
                }



                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL + "appointment/", json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getContext(),  getContext().getString(R.string.success), Toast.LENGTH_LONG).show();
                                ((StartActivity)getActivity()).replaceFragment(new StartFragment());
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
    }

    private int calculateAge(String date) throws ParseException{
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dob = inputFormat.parse(date);
        LocalDate ldate = Instant.ofEpochMilli(dob.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(ldate, now1);
        return diff1.getYears();
    }
}
