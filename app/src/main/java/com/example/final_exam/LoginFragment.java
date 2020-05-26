package com.example.final_exam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
<<<<<<< HEAD
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MainActivity ma = (MainActivity) getActivity();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

=======
    Button loginBtn;
>>>>>>> a51851edaf8db1f8bca9a16bda1647978d96042b
    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


=======
>>>>>>> a51851edaf8db1f8bca9a16bda1647978d96042b
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
    }

    /* ------------------------TEST FASE 1 -----------------------------------*/
    View.OnClickListener clickSimple = view -> {
        Intent i = new Intent(getActivity(),MainActivity.class);
        startActivity(i);
    };
    /* ---------------------Borrar en un futuro ------------------------------*/

}
