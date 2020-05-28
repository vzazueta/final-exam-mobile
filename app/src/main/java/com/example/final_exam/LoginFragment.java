package com.example.final_exam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class LoginFragment extends Fragment implements RequestLogin.Callback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    JSONObject json;
    JSONArray jsonArray;
    List<String[]> patient_list;
    private EditText userET, passET;
    private String URL = "https://final-exam-mobile.herokuapp.com/user/login";
    private boolean userExists = false, firstTry = true;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button loginBtn;

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginBtn = view.findViewById(R.id.login_ingresar_btn);
        loginBtn.setOnClickListener(clickSimple);

        userET = getView().findViewById(R.id.user_edit_text);
        passET = getView().findViewById(R.id.password_edit_text);
    }


    View.OnClickListener clickSimple = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                String ID = "MOA980107ABC";     // userET.getText().toString();
                String password = "ivan";       // passET.getText().toString();
                getRequestFrag(ID, password);
                Toast.makeText(view.getContext(), "Conectando a sistema. \nEspere unos segundos.", Toast.LENGTH_SHORT).show();
            }
    };

    private void getRequestFrag(String id, String password){
        RequestLogin.getRequest(getContext(), this, this.URL, id, password);
    }

    private void verifyUser(String id, String pass){
        if(!(id.equals("") || pass.equals(""))){
            Log.d("Success", "\nID: "+id+"\nPassword: "+pass);
            Intent i = new Intent(LoginFragment.this.getActivity(), MainActivity.class);
            LoginFragment.this.startActivity(i);
        }



    }


    @Override
    public void processJSON(JSONObject response) {
        try{
            this.json = response;

            Log.d("Callback json", json.toString());

            try {
                verifyUser(json.getString("id"), json.getString("password"));
            } catch(Exception e){
                Toast.makeText(getContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                Log.d("Error en verificacion", e.toString());
            }


        } catch(Exception e) {
            Log.d("Hubo un error", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {

    }
}
