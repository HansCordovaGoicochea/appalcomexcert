package app.sc.com.appalcomexcert.Clases;

import java.io.Serializable;

public class Persona implements Serializable {

    public int id_capacitacion;
    public String participante;
    public String participante_dni;
    public String fotocheck;

    public Persona() {
    }

    public Persona(int id_capacitacion, String participante, String participante_dni, String fotocheck) {
        this.id_capacitacion = id_capacitacion;
        this.participante = participante;
        this.participante_dni = participante_dni;
        this.fotocheck = fotocheck;
    }

    public int getId_capacitacion() {
        return id_capacitacion;
    }

    public void setId_capacitacion(int id_capacitacion) {
        this.id_capacitacion = id_capacitacion;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public String getParticipante_dni() {
        return participante_dni;
    }

    public void setParticipante_dni(String participante_dni) {
        this.participante_dni = participante_dni;
    }

    public String getFotocheck() {
        return fotocheck;
    }

    public void setFotocheck(String fotocheck) {
        this.fotocheck = fotocheck;
    }
}
