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

import com.example.final_exam.Utils.MyApplication;
import com.example.final_exam.types.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;

public class CreateCitaLoggedInFragment extends Fragment {

    private Bitmap bmp;
    private EditText etHora, etFecha, etDescripcion;
    private Button btnSubmit;

    public CreateCitaLoggedInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User myUser = MyApplication.getInstance().getMyUser();
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
                // TODO: send data to server
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
