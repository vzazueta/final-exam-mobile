package com.example.final_exam;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_exam.Utils.MyApplication;
import com.example.final_exam.types.Cita;
import com.example.final_exam.types.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCitaLoggedInFragment extends Fragment {
    private static String URL = "https://final-exam-mobile.herokuapp.com/";


    private Bitmap bmp;
    private EditText etHora, etFecha, etDescripcion;
    private Button btnSubmit;
    User myUser;

    public CreateCitaLoggedInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myUser = MyApplication.getInstance().getMyUser();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String date = inputFormat.format(myUser.getDob());
        String qrContent = myUser.getId() + "," + myUser.getName() +"," + date + ","+myUser.getAllergies() ;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(qrContent, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ImageView) getView().findViewById(R.id.iv_qr_code)).setImageBitmap(bmp);

        etDescripcion = view.findViewById(R.id.et_cita_in_descripcion);
        etHora = view.findViewById(R.id.et_cita_in_hora);
        etFecha = view.findViewById(R.id.et_cita_in_fecha);
        btnSubmit = view.findViewById(R.id.btn_cita_in_submit);

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

                Cita cita = new Cita();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                cita.setUserId(myUser.getId());
                cita.setNotas(etDescripcion.getText().toString());
                JSONObject json;
                try {
                    Date d = dateFormat.parse(etFecha.getText().toString());
                    d.setHours(Integer.parseInt(etHora.getText().toString().split(":")[0]));
                    d.setMinutes(Integer.parseInt(etHora.getText().toString().split(":")[1]));
                    cita.setFecha(outputFormat.format(d));
                    json = cita.getJSON();
                } catch(Exception ex){
                    Toast.makeText(getContext(), "Verifique la fecha y hora introducidos", Toast.LENGTH_LONG).show();
                    return;
                }



                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL + "appointment/", json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getContext(), "Cita creada!", Toast.LENGTH_LONG).show();
                                ((MainActivity)getActivity()).setSelectedMenu(R.id.page_1);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Porfavor intente mas tarde", Toast.LENGTH_LONG).show();
                            }
                        });
                RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
                rq.add(request);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_cita_logged_in, container, false);
    }
}
