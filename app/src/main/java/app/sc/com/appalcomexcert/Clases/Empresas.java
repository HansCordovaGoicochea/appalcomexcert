package app.sc.com.appalcomexcert.Clases;

import java.io.Serializable;

public class Empresas implements Serializable {

    public int id_empresas;
    public String ruc;
    public String razon_social;

    public Empresas() {
    }

    public Empresas(int id_empresas, String ruc, String razon_social) {
        this.id_empresas = id_empresas;
        this.ruc = ruc;
        this.razon_social = razon_social;
    }

    public int getId_empresas() {
        return id_empresas;
    }

    public void setId_empresas(int id_empresas) {
        this.id_empresas = id_empresas;
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
        if(obj instanceof Empresas){
            Empresas c = (Empresas)obj;
            return c.getRazon_social().equals(razon_social) && c.getId_empresas() == id_empresas;
        }

        return false;
    }

}
