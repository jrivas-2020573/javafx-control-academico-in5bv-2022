package org.in5bv.juanrivas.models;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 19 jul 2022
 * @time 14:37:46
 * Carné: 2020573
 * Grupo: 1
 * Codigo Tecnico: IN5BV
 */
public class Usuarios {
    private String user;
    private String pass;
    private String nombre;
    private int rol_id;
    
    public Usuarios(){
        
    }

    public Usuarios(String user) {
        this.user = user;
    }

    public Usuarios(String user, String pass, String nombre, int rol_id) {
        this.user = user;
        this.pass = pass;
        this.nombre = nombre;
        this.rol_id = rol_id;
    }

    public Usuarios(String pass, String nombre, int rol_id) {
        this.pass = pass;
        this.nombre = nombre;
        this.rol_id = rol_id;
    }
    

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    @Override
    public String toString() {
        return "Usuario" + "nombre=" + nombre + ", rol_id=" + rol_id;
    }
    
    
}
