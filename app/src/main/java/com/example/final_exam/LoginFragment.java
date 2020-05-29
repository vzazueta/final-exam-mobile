package com.example.final_exam;

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

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements RequestLogin.Callback {

    JSONArray jsonArray;
    List<String[]> patient_list;
    private EditText userET, passET;
    private String URL = "https://final-exam-mobile.herokuapp.com/user/login";
    private boolean userExists = false, firstTry = true;


    Button loginBtn;

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

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
        loginBtn.setEnabled(true);
    }

    View.OnClickListener clickSimple = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                String ID = userET.getText().toString();
                String password = passET.getText().toString();
                if(ID.equals("")){
                    ID = "MOA980107ABC";
                    password = "ivan";
                }
                view.setEnabled(false);
                getRequestFrag(ID, password);
                Toast.makeText(view.getContext(), "Conectando a sistema. \nEspere unos segundos.", Toast.LENGTH_SHORT).show();
            }
    };

    private void getRequestFrag(String id, String password){
        RequestLogin.getRequest(getContext(), this, this.URL, id, password);
    }

    private void verifyUser(JSONObject json) throws JSONException, ParseException {
        String id = json.getString("id");
        String pass = json.getString("password");
        if(!(id.equals("") || pass.equals(""))){
            setUser(json);
            Log.d("Success", "\nID: "+id+"\nPassword: "+pass);
            Intent i = new Intent(LoginFragment.this.getActivity(), MainActivity.class);
            LoginFragment.this.startActivity(i);
        }
    }

    private void setUser(JSONObject json)throws JSONException, ParseException{
        MyApplication app = MyApplication.getInstance();
        app.setMyUser(new User());
        User myUser = app.getMyUser();

        String strDob = json.getString("birthdate");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dob = inputFormat.parse(strDob);
        myUser.setDob(dob);

        myUser.setAllergic(json.getBoolean("allergic"));
        myUser.setAllergies(json.getString("allergic_description"));
        myUser.setGender(json.getString("gender"));
        myUser.setId(json.getString("id"));
        myUser.setName(json.getString("name"));

    }


    @Override
    public void processJSON(JSONObject response) {
        try{
            JSONObject json = response;

            Log.d("Callback json", json.toString());

            try {
                verifyUser(json);
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
