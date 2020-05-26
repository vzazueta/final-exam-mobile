package com.example.final_exam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListaCitasFragment extends Fragment {
    List<Cita> citas;
    private static final String APPOINTMENT_ID_USER = "user_id";
    private static final String APPOINTMENT_DATE = "date";
    private static final String APPOINTMENT_DESCRIPTION = "description";
    private String category;
    private CitasAdapter adapter;
    RecyclerView rv_menu;
    private Handler handler = new Handler();
    JSONArray jsonArray;

    ReqFromServer.Callback callbackServer = new ReqFromServer.Callback() {
        @Override
        public void processJSON(String response) {
            try{
                jsonArray = new JSONArray(response);
                for(int i = 0; i<jsonArray.length();i++) {
                    filterCitas( jsonArray.getJSONArray(i));
                }
            }catch(JSONException e) {
                e.printStackTrace();
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onError() { }
    };

    public void filterCitas(JSONArray jsonObject) throws JSONException, ParseException {
        String idApp = jsonObject.getJSONObject(0).getString(APPOINTMENT_ID_USER);
        User myUser = ((MainActivity)getActivity()).user;
        if(idApp.equals(myUser.id)){
            Cita cita = new Cita();
            cita.setUser(myUser);
            String strDateCita = jsonObject.getJSONObject(0).getString(APPOINTMENT_DATE);
            SimpleDateFormat sdf = new SimpleDateFormat("EE yyyy-MM-dd HH:mm:ss");
            Date dateCita = sdf.parse(strDateCita);
            cita.setFecha(dateCita.toString());
            cita.setNotas(jsonObject.getJSONObject(0).getString(APPOINTMENT_DESCRIPTION));
            citas.add(cita);
        }
    }

    public ListaCitasFragment() { }

    public static ListaCitasFragment newInstance() {
        return  new ListaCitasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_citas, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        setComponents();
        ReqFromServer.getRequest(getActivity(), callbackServer);
        handler.postDelayed(() -> {
            try {
                adapter.notifyDataSetChanged();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }, 3000);
        populateRecyclerView();
    }

    private void setComponents(){
        citas = new ArrayList<>();
        rv_menu = getActivity().findViewById(R.id.rv_lista_citas);
        rv_menu.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void populateRecyclerView() {
        adapter = new CitasAdapter(getContext(), citas);
        rv_menu.setAdapter(adapter);
    }
}
