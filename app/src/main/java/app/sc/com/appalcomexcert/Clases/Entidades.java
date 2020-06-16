package app.sc.com.appalcomexcert.Clases;

import java.io.Serializable;

public class Entidades implements Serializable {

    public int id_entidad;
    public String ruc;
    public String razon_social;

    public Entidades() {
    }

    public Entidades(int id_entidad, String ruc, String razon_social) {
        this.id_entidad = id_entidad;
        this.ruc = ruc;
        this.razon_social = razon_social;
    }

    public int getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(int id_entidad) {
        this.id_entidad = id_entidad;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    //to display object as a string in spinner
    @Override
    public String toString() {
        return razon_social;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Entidades){
            Entidades c = (Entidades )obj;
            return c.getRazon_social().equals(razon_social) && c.getId_entidad() == id_entidad;
        }

        return false;
    }

}
