package com.example.final_exam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.final_exam.Utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {
    Button btnLogin, btnMakeAppointment, btnEmergency;

    public StartFragment() {
        // Required empty public constructor
    }


    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        btnEmergency = view.findViewById(R.id.emergency_call_btn);
        btnLogin = view.findViewById(R.id.login_btn);
        btnMakeAppointment = view.findViewById(R.id.make_appointment_btn);

        btnMakeAppointment.setOnClickListener(onMakeAppointment);
        btnLogin.setOnClickListener(onLogin);
        btnEmergency.setOnClickListener(onEmergency);
    }

    View.OnClickListener onEmergency = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utils.callToEmergencies(getContext());
        }
    };

    View.OnClickListener onMakeAppointment = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((StartActivity)getActivity()).replaceFragment(new CreateCitaFragment());
        }
    };

    View.OnClickListener onLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((StartActivity)getActivity()).replaceFragment(new LoginFragment());
        }
    };
}
