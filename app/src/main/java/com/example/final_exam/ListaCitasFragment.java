package com.example.final_exam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_exam.Utils.MyApplication;
import com.example.final_exam.Utils.ReqFromServer;
import com.example.final_exam.types.Cita;
import com.example.final_exam.types.User;

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
    private static String APPOINTMENT_ID;
    private static String APPOINTMENT_ID_USER;
    private static String APPOINTMENT_DATE;
    private static String APPOINTMENT_DESCRIPTION;
    private String category;
    private CitasAdapter adapter;
    RecyclerView rv_menu;
    JSONArray jsonArray;

    public ListaCitasFragment(Context context){
        APPOINTMENT_ID_USER = context.getString(R.string.JSON_APPOINTMENT_USER_ID);
        APPOINTMENT_DATE = context.getString(R.string.JSON_APPOINTMENT_DATE);
        APPOINTMENT_DESCRIPTION = context.getString(R.string.JSON_APPOINTMENT_DESCRITPION);
        APPOINTMENT_ID = context.getString(R.string.APPOINTMENT_ID);
    }

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
            SimpleDateFormat inputFormat = new SimpleDateFormat(getContext().getString(R.string.DATE_FORMAT));
            Date dateCita = inputFormat.parse(strDateCita);
            cita.setFecha(dateCita.toString());
            cita.setNotas(jsonObject.getString(APPOINTMENT_DESCRIPTION));
            cita.setId(jsonObject.getString(APPOINTMENT_ID));
            cita.setUserId(jsonObject.getString("user_id"));
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
        adapter = new CitasAdapter(getContext(), citas, (MainActivity) getActivity());
        rv_menu.setAdapter(adapter);
    }
}
