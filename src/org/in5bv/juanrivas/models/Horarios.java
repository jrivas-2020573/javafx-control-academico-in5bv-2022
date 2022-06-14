package org.in5bv.juanrivas.models;

import java.time.LocalTime;


/**
 *
 * @author informatica
 */
public class Horarios {
    private int id;
    private LocalTime horario_inicio;
    private LocalTime horario_final;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    
    public Horarios(){
        
    }
    
    public Horarios(int id){
        this.id = id;
    }

    public Horarios(int id, LocalTime horario_inicio, LocalTime horario_final) {
        this.id = id;
        this.horario_inicio = horario_inicio;
        this.horario_final = horario_final;
    }

    public Horarios(LocalTime horario_inicio, LocalTime horario_final, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes) {
        this.horario_inicio = horario_inicio;
        this.horario_final = horario_final;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
    }

    public Horarios(int id, LocalTime horario_inicio, LocalTime horario_final, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes) {
        this.id = id;
        this.horario_inicio = horario_inicio;
        this.horario_final = horario_final;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(LocalTime horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public LocalTime getHorario_final() {
        return horario_final;
    }

    public void setHorario_final(LocalTime horario_final) {
        this.horario_final = horario_final;
    }
    
    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    @Override
    public String toString() {
        return id + " | " + horario_inicio + " - " + horario_final;
    }

    
}
