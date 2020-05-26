package com.example.final_exam;

public class Cita {

    private User user;
    private String notas, fecha, hora;

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
}
