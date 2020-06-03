package com.example.final_exam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;


public class CreateCitaFragment extends Fragment {

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
        content = getArguments().getString("content").split(",");
        return inflater.inflate(R.layout.fragment_create_cita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNombre = view.findViewById(R.id.tv_cita_nombre);
        tvEdad = view.findViewById(R.id.tv_cita_edad);
        tvAlergias = view.findViewById(R.id.tv_cita_alergias);
        etDescripcion = view.findViewById(R.id.et_cita_descripcion);
        etHora = view.findViewById(R.id.et_cita_hora);
        etFecha = view.findViewById(R.id.et_cita_fecha);
        btnSubmit = view.findViewById(R.id.btn_cita_submit);

        tvNombre.setText(content[1]);
        tvEdad.setText(""+calculateAge(content[2]));
        tvAlergias.setText(content[3]);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean err = false;
                String message = "Porfavor ingrese: ";
                if(etHora.getText().toString().equals("")){
                    err = true;
                    message += "\nHora de cita";
                }
                if(etFecha.getText().toString().equals("")){
                    err = true;
                    message += "\nFecha de cita";
                }
                if(etDescripcion.getText().toString().equals("")){
                    err = true;
                    message += "\nMotivo de cita";
                }
                if(err){
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    return;
                }
                // TODO: send data to server
            }
        });
    }

    private int calculateAge(String date){
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date dob = inputFormat.parse(date);
            LocalDate ldate = Instant.ofEpochMilli(dob.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now1 = LocalDate.now();
            Period diff1 = Period.between(ldate, now1);
            return diff1.getYears();
        } catch(ParseException ex){
            Log.e("CITA", ex.toString());
        }
        return 0;
    }
}
