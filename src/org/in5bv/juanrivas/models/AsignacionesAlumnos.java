package org.in5bv.juanrivas.models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 7 jun 2022
 * @time 13:15:36
 * Carné: 2020573
 * Grupo: 1
 * Codigo Tecnico: IN5BV
 */
public class AsignacionesAlumnos {
    private IntegerProperty id;
    private ObjectProperty<LocalDateTime> fechaAsignacion;
    private StringProperty alumnosId;
    private IntegerProperty cursoId;
    
    public AsignacionesAlumnos(){
        this.id = new SimpleIntegerProperty();
        this.alumnosId = new SimpleStringProperty();
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty<>();
    }
    
    public AsignacionesAlumnos(String alumnosId, int cursoId, LocalDateTime fechaAsignacion){
        this.alumnosId = new SimpleStringProperty(alumnosId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }
    
    public AsignacionesAlumnos(int id, String alumnosId, int cursoId, LocalDateTime fechaAsignacion){
        this.id = new SimpleIntegerProperty(id);
        this.alumnosId = new SimpleStringProperty(alumnosId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }
    
    public IntegerProperty id(){
        return id;
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    
    public StringProperty alumnosId(){
        return alumnosId;
    }
    
    public String getAlumnoId(){
        return alumnosId.get();
    }
    
    public void setAlumnos(String alumnosId) {
        this.alumnosId.set(alumnosId);
    }
    
    
    public IntegerProperty cursoId() {
        return cursoId;
    }
    
    public int getCursoId(){
        return cursoId.get();
    }
    
    public void setCursoId(int cursoId){
        this.cursoId.set(cursoId);
    }
    
    
    public ObjectProperty<LocalDateTime> fechaAsignacion() {
        return fechaAsignacion;
    }

   public LocalDateTime getFechaAsignacion() {
       return fechaAsignacion.get();
   }
   
   public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
       this.fechaAsignacion.set(fechaAsignacion);
   }
   
   @Override
   public String toString(){
       return id + " | " + fechaAsignacion;
   }

}
