package com.example.final_exam;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.ViewHolder> {
    private Context context;
    private List<Cita> citas;


    public CitasAdapter(Context context, List<Cita> c) {
        this.context = context;
        this.citas = c;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cita, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Cita c = citas.get(i);
        viewHolder.bind(c);
    }

    @Override
    public int getItemCount() { return citas.size(); }


    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> d) {
        this.citas = d;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName,tvEdad, tvGenero, tvId, tvFecha, tvNotas;

        public ViewHolder( View view) {
            super(view);
            tvName= view.findViewById(R.id.tv_item_nombre);
            tvEdad= view.findViewById(R.id.tv_item_edad);
            tvGenero= view.findViewById(R.id.tv_item_genero);
            tvId= view.findViewById(R.id.tv_item_id);
            tvFecha= view.findViewById(R.id.tv_item_fecha);
            tvNotas= view.findViewById(R.id.tv_item_notas);
        }


        public void bind(final Cita c){
            tvName.setText(c.getUser().getName());
            tvEdad.setText(""+calculateAge(c.getUser().getDob()));
            tvGenero.setText(c.getUser().getGender());
            tvId.setText(c.getUser().getId());
            setDate(c.getFecha());
            tvNotas.setText("Alergico a: "+ c.getNotas());
        }

        private void setDate(String input){
            try {
                String[] date = input.split(" ");
                String[] hora = date[3].split(":");
                tvFecha.setText(hora[0]+":"+hora[1]+", "+date[0] +" " +date[1]+" " +date[2]+ ", "+date[date.length-1]);
            }catch (Exception e){
                tvFecha.setText(input);
            }
        }

        private int calculateAge(Date dob){
            LocalDate ldate = Instant.ofEpochMilli(dob.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now1 = LocalDate.now();
            Period diff1 = Period.between(ldate, now1);
            return diff1.getYears();
        }
    }
}
