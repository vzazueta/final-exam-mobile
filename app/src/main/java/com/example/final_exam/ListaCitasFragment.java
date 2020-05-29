package com.example.final_exam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    JSONArray jsonArray;

    ReqFromServer.Callback callbackServer = new ReqFromServer.Callback() {
        @Override
        public void processJSON(String response) {
            try{
                jsonArray = new JSONArray(response);
                for(int i = 0; i<jsonArray.length();i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    filterCitas( obj);
                }
                adapter.notifyDataSetChanged();
            }catch(JSONException e) {
                e.printStackTrace();
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onError() { }
    };

    public void filterCitas(JSONObject jsonObject) throws JSONException, ParseException {
        String idApp = jsonObject.getString(APPOINTMENT_ID_USER);
        User myUser = MyApplication.getInstance().getMyUser();
        if(idApp.equals(myUser.getId())){
            Cita cita = new Cita();
            cita.setUser(myUser);
            String strDateCita = jsonObject.getString(APPOINTMENT_DATE);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date dateCita = inputFormat.parse(strDateCita);
            cita.setFecha(dateCita.toString());
            cita.setNotas(jsonObject.getString(APPOINTMENT_DESCRIPTION));
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
        setAdapterToRV();
        ReqFromServer.getRequest(getActivity(), callbackServer);

    }

    private void setComponents(){
        citas = new ArrayList<>();
        rv_menu = getActivity().findViewById(R.id.rv_lista_citas);
        rv_menu.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAdapterToRV() {
        adapter = new CitasAdapter(getContext(), citas);
        rv_menu.setAdapter(adapter);
    }
}
