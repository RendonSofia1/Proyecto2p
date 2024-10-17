/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author rendo
 */
public class Vivienda {
    
    private int id;
    private String calle;
    private String colonia;
    private String municipio;
    private String cp;

    public Vivienda() {
    }

    public Vivienda(int id, String calle, String colonia, String municipio, String cp) {
        this.id = id;
        this.calle = calle;
        this.colonia = colonia;
        this.municipio = municipio;
        this.cp = cp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "Vivienda{" + "id=" + id + ", calle=" + calle + ", colonia=" + colonia + ", municipio=" + municipio + ", cp=" + cp + '}';
    }
    
    
    
}
