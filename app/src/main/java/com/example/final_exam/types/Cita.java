package com.example.final_exam.types;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.final_exam.types.User;

import org.json.JSONException;
import org.json.JSONObject;

public class Cita implements Parcelable {


    private User user;
    private String notas, fecha, hora, id, userId;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cita createFromParcel(Parcel in) {
            return new Cita(in);
        }

        public Cita[] newArray(int size) {
            return new Cita[size];
        }
    };

    public Cita(){}

    public JSONObject getJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("user_id", userId);
        json.put("created_by", userId);
        json.put("date", fecha);
        json.put("description", notas);
        return json;
    }

    public User getUser() { return user;  }

    public void setUser(User user) { this.user = user; }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setId(String id){this.id = id;}
    public String getId(){return this.id;}

    public void setUserId(String userId){this.userId = userId;}
    public String getUserId(){return userId;}

    public Cita(Parcel in){
        this.id = in.readString();
        this.notas = in.readString();
        this.fecha = in.readString();
        this.hora = in.readString();
        this.userId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(notas);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(userId);
    }
}
