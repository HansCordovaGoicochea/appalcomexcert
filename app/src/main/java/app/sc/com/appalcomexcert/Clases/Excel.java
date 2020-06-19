package app.sc.com.appalcomexcert.Clases;

import java.io.Serializable;

public class Excel implements Serializable {

    public int id_capacitacion;
    public int id_curso;
    public String nombre_curso;
    public int id_tema;
    public String detalle_tema;
    public String fecha_hora_sesion;
    public String horas_teoricas;
    public String horas_practicas;
    public int id_instructor;
    public String fotocheck_inst;
    public String instructor_dni;
    public String instructor;
    public String fotocheck_part;
    public String participante_dni;
    public String participante;
    public String nota_pre_test;
    public String nota_pos_test;
    public String nota_oral;
    public String aprobo_sesion;
    public String nota_caso_estudio;
    public String aprobo_caso_estudio;
    public String situacion_final;
    public String empresa;
    public String area;
    public String cargo;
    public String vehiculo;
    public String marca;
    public String modelo;
    public String tm;
    public String oportunidad;
    public String observacion;
    public String registro_certificado;

    public Excel() {
    }

    public Excel(int id_capacitacion, int id_curso, String nombre_curso, int id_tema, String detalle_tema, String fecha_hora_sesion, String horas_teoricas, String horas_practicas, int id_instructor, String fotocheck_inst, String instructor_dni, String instructor, String fotocheck_part, String participante_dni, String participante, String nota_pre_test, String nota_pos_test, String nota_oral, String aprobo_sesion, String nota_caso_estudio, String aprobo_caso_estudio, String situacion_final, String empresa, String area, String cargo, String vehiculo, String marca, String modelo, String tm, String oportunidad, String observacion, String registro_certificado) {
        this.id_capacitacion = id_capacitacion;
        this.id_curso = id_curso;
        this.nombre_curso = nombre_curso;
        this.id_tema = id_tema;
        this.detalle_tema = detalle_tema;
        this.fecha_hora_sesion = fecha_hora_sesion;
        this.horas_teoricas = horas_teoricas;
        this.horas_practicas = horas_practicas;
        this.id_instructor = id_instructor;
        this.fotocheck_inst = fotocheck_inst;
        this.instructor_dni = instructor_dni;
        this.instructor = instructor;
        this.fotocheck_part = fotocheck_part;
        this.participante_dni = participante_dni;
        this.participante = participante;
        this.nota_pre_test = nota_pre_test;
        this.nota_pos_test = nota_pos_test;
        this.nota_oral = nota_oral;
        this.aprobo_sesion = aprobo_sesion;
        this.nota_caso_estudio = nota_caso_estudio;
        this.aprobo_caso_estudio = aprobo_caso_estudio;
        this.situacion_final = situacion_final;
        this.empresa = empresa;
        this.area = area;
        this.cargo = cargo;
        this.vehiculo = vehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.tm = tm;
        this.oportunidad = oportunidad;
        this.observacion = observacion;
        this.registro_certificado = registro_certificado;
    }

    public int getId_capacitacion() {
        return id_capacitacion;
    }

    public void setId_capacitacion(int id_capacitacion) {
        this.id_capacitacion = id_capacitacion;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombre_curso() {
        return nombre_curso;
    }

    public void setNombre_curso(String nombre_curso) {
        this.nombre_curso = nombre_curso;
    }

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(int id_tema) {
        this.id_tema = id_tema;
    }

    public String getDetalle_tema() {
        return detalle_tema;
    }

    public void setDetalle_tema(String detalle_tema) {
        this.detalle_tema = detalle_tema;
    }

    public String getFecha_hora_sesion() {
        return fecha_hora_sesion;
    }

    public void setFecha_hora_sesion(String fecha_hora_sesion) {
        this.fecha_hora_sesion = fecha_hora_sesion;
    }

    public String getHoras_teoricas() {
        return horas_teoricas;
    }

    public void setHoras_teoricas(String horas_teoricas) {
        this.horas_teoricas = horas_teoricas;
    }

    public String getHoras_practicas() {
        return horas_practicas;
    }

    public void setHoras_practicas(String horas_practicas) {
        this.horas_practicas = horas_practicas;
    }

    public int getId_instructor() {
        return id_instructor;
    }

    public void setId_instructor(int id_instructor) {
        this.id_instructor = id_instructor;
    }

    public String getFotocheck_inst() {
        return fotocheck_inst;
    }

    public void setFotocheck_inst(String fotocheck_inst) {
        this.fotocheck_inst = fotocheck_inst;
    }

    public String getInstructor_dni() {
        return instructor_dni;
    }

    public void setInstructor_dni(String instructor_dni) {
        this.instructor_dni = instructor_dni;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getFotocheck_part() {
        return fotocheck_part;
    }

    public void setFotocheck_part(String fotocheck_part) {
        this.fotocheck_part = fotocheck_part;
    }

    public String getParticipante_dni() {
        return participante_dni;
    }

    public void setParticipante_dni(String participante_dni) {
        this.participante_dni = participante_dni;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public String getNota_pre_test() {
        return nota_pre_test;
    }

    public void setNota_pre_test(String nota_pre_test) {
        this.nota_pre_test = nota_pre_test;
    }

    public String getNota_pos_test() {
        return nota_pos_test;
    }

    public void setNota_pos_test(String nota_pos_test) {
        this.nota_pos_test = nota_pos_test;
    }

    public String getNota_oral() {
        return nota_oral;
    }

    public void setNota_oral(String nota_oral) {
        this.nota_oral = nota_oral;
    }

    public String getAprobo_sesion() {
        return aprobo_sesion;
    }

    public void setAprobo_sesion(String aprobo_sesion) {
        this.aprobo_sesion = aprobo_sesion;
    }

    public String getNota_caso_estudio() {
        return nota_caso_estudio;
    }

    public void setNota_caso_estudio(String nota_caso_estudio) {
        this.nota_caso_estudio = nota_caso_estudio;
    }

    public String getAprobo_caso_estudio() {
        return aprobo_caso_estudio;
    }

    public void setAprobo_caso_estudio(String aprobo_caso_estudio) {
        this.aprobo_caso_estudio = aprobo_caso_estudio;
    }

    public String getSituacion_final() {
        return situacion_final;
    }

    public void setSituacion_final(String situacion_final) {
        this.situacion_final = situacion_final;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getOportunidad() {
        return oportunidad;
    }

    public void setOportunidad(String oportunidad) {
        this.oportunidad = oportunidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRegistro_certificado() {
        return registro_certificado;
    }

    public void setRegistro_certificado(String registro_certificado) {
        this.registro_certificado = registro_certificado;
    }
}
