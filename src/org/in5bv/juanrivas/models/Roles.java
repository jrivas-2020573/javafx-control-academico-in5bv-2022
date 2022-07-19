package org.in5bv.juanrivas.models;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 19 jul 2022
 * @time 14:37:28
 * Carné: 2020573
 * Grupo: 1
 * Codigo Tecnico: IN5BV
 */
public class Roles {
    private int id;
    private String descripcion;
    
    
    public Roles(){
        
    }

    public Roles(int id) {
        this.id = id;
    }

    public Roles(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Roles(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Roles: " + "id=" + id + ", descripcion=" + descripcion;
    }
    
    
    
}
