package app.sc.com.appalcomexcert.Clases;

import java.io.Serializable;

public class Certificado implements Serializable {

    public int id_capacitacion;
    public String instructor;
    public String instructor_dni;
    public String participante;
    public String participante_dni;
    public String fecha_hora_sesion;
    public double horas_teorias;
    public double horas_practicas;
    public String fotocheck;
    public String registro_certificado;
    public String area;
    public String situacion_final;
    public String curso;
    public String tema;
    public String empresa;

    public Certificado() {
    }

    public Certificado(int id_capacitacion, String instructor, String instructor_dni, String participante, String participante_dni, String fecha_hora_sesion, double horas_teorias, double horas_practicas, String fotocheck, String registro_certificado, String area, String situacion_final, String curso, String tema, String empresa) {
        this.id_capacitacion = id_capacitacion;
        this.instructor = instructor;
        this.instructor_dni = instructor_dni;
        this.participante = participante;
        this.participante_dni = participante_dni;
        this.fecha_hora_sesion = fecha_hora_sesion;
        this.horas_teorias = horas_teorias;
        this.horas_practicas = horas_practicas;
        this.fotocheck = fotocheck;
        this.registro_certificado = registro_certificado;
        this.area = area;
        this.situacion_final = situacion_final;
        this.curso = curso;
        this.tema = tema;
        this.empresa = empresa;
    }

    public int getId_capacitacion() {
        return id_capacitacion;
    }

    public void setId_capacitacion(int id_capacitacion) {
        this.id_capacitacion = id_capacitacion;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructor_dni() {
        return instructor_dni;
    }

    public void setInstructor_dni(String instructor_dni) {
        this.instructor_dni = instructor_dni;
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

    public String getFecha_hora_sesion() {
        return fecha_hora_sesion;
    }

    public void setFecha_hora_sesion(String fecha_hora_sesion) {
        this.fecha_hora_sesion = fecha_hora_sesion;
    }

    public double getHoras_teorias() {
        return horas_teorias;
    }

    public void setHoras_teorias(double horas_teorias) {
        this.horas_teorias = horas_teorias;
    }

    public double getHoras_practicas() {
        return horas_practicas;
    }

    public void setHoras_practicas(double horas_practicas) {
        this.horas_practicas = horas_practicas;
    }

    public String getFotocheck() {
        return fotocheck;
    }

    public void setFotocheck(String fotocheck) {
        this.fotocheck = fotocheck;
    }

    public String getRegistro_certificado() {
        return registro_certificado;
    }

    public void setRegistro_certificado(String registro_certificado) {
        this.registro_certificado = registro_certificado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSituacion_final() {
        return situacion_final;
    }

    public void setSituacion_final(String situacion_final) {
        this.situacion_final = situacion_final;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
