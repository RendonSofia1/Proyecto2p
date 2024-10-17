/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author rendo
 */
public class Becario {
    
    private String curp;
    private Date fecha_nacimiento;
    private String nombre;
    private String apellido_pat;
    private String apellido_mat;
    private char genero;
    private String url_foto;

    public Becario() {
    }

    public Becario(String curp, Date fecha_nacimiento, String nombre, String apellido_pat, String apellido_mat, char genero, String url_foto) {
        this.curp = curp;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nombre = nombre;
        this.apellido_pat = apellido_pat;
        this.apellido_mat = apellido_mat;
        this.genero = genero;
        this.url_foto = url_foto;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_pat() {
        return apellido_pat;
    }

    public void setApellido_pat(String apellido_pat) {
        this.apellido_pat = apellido_pat;
    }

    public String getApellido_mat() {
        return apellido_mat;
    }

    public void setApellido_mat(String apellido_mat) {
        this.apellido_mat = apellido_mat;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    @Override
    public String toString() {
        return "Becario{" + "curp=" + curp + ", fecha_nacimiento=" + fecha_nacimiento + ", nombre=" + nombre + ", apellido_pat=" 
                + apellido_pat + ", apellido_mat=" + apellido_mat + ", genero=" + genero + ", url_foto=" + url_foto + '}';
    }
    
    
}
