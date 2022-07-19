package org.in5bv.juanrivas.tolchavalan.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import org.in5bv.juanrivas.db.Conexion;
import org.in5bv.juanrivas.models.Usuarios;
import org.in5bv.juanrivas.system.Principal;
import org.in5bv.juanrivas.reports.GenerarReporte;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 28/04/2022
 * 
 * Codigo Tecnico: IN5BV
 */
public class MenuPrincipalController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    private final String PAQUETE_IMAGES = "org/in5bv/juanrivas/resources/images/";
  
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicSalir(){
        System.exit(0);
    }
    
    @FXML
    public void clicLogoff(){
        escenarioPrincipal.mostrarEscenaLogin();
    }
    
    @FXML
    public void clicAlumnos() {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }
    
    @FXML
    public void clicCarreras() {
        escenarioPrincipal.mostrarEscenaCarreras();
    }
    
    @FXML
    public void clicSalones() {
        escenarioPrincipal.mostarSalones();
    }
    
    @FXML
    public void clicHorario() {
        escenarioPrincipal.mostrarEscenaHorarios();
    }
    
    @FXML
    public void clicAsignaciones() {
        escenarioPrincipal.mostarEscenaAsignacionesAlumnos();
    }
    
    @FXML
    public void clicCursos(){
        escenarioPrincipal.mostarEscenaCursos();
    }
    
    @FXML
    public void clicInstructores(){
        escenarioPrincipal.mostrarEscenaInstructores();
    }
    
    @FXML
    public void clicAcercaDe(){
        escenarioPrincipal.mostrarAcercaDe();
    }
    
    @FXML
    public void clicReporteAsignaciones(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        
        GenerarReporte.getInstance().mostrarReporte("ReporteAsignacionesAlumnos.jasper", 
                parametros, "Reporte de Asignaciones");
        
    }
    
    @FXML
    public void clicReporteAlumnos(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        parametros.put("SALUDO", PAQUETE_IMAGES + "graduado (1).png");
        GenerarReporte.getInstance().mostrarReporte("ReporteAlumnos.jasper", 
                parametros, "Reporte de alumnos");
    }
    
    @FXML
    public void clicReporteAsignacionesPorid(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        //parametros.put("idAsignacion", 3);
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Busqueda por ID");
        dialog.setHeaderText("");
        dialog.setContentText("Ingrese un id para buscar");
        
        //Capturar el dato 
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            System.out.println("Numero: " + result.get());
            parametros.put("idAsignacion", Integer.parseInt(result.get()));
        }
        
        GenerarReporte.getInstance().mostrarReporte("ReporteAsignacionAlumnosPorId.jasper", parametros, "Reporte de Asignaciones");
    }
    
    @FXML
    public void clicReporteCursosPorid(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        //parametros.put("idCurso", 6);
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Busqueda por ID");
        dialog.setHeaderText("");
        dialog.setContentText("Ingrese un id para buscar");
        
        //Capturar el dato 
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            System.out.println("Numero: " + result.get());
            parametros.put("idCurso", Integer.parseInt(result.get()));
        }
        
        GenerarReporte.getInstance().mostrarReporte("ReporteCursosPorId.jasper", 
                parametros, "Reporte de Cursos");
    }
    
    @FXML
    public void clicReporteCursos(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        parametros.put("SALUDO", PAQUETE_IMAGES + "Cursos.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteCursos.jasper", 
                parametros, "Reporte de Cursos");
    }
    
    @FXML
    public void clicReporteHorarios(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        parametros.put("SALUDO", PAQUETE_IMAGES + "calendario.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteHorarios.jasper", 
                parametros, "Reporte de horarios");
    }
    
    @FXML
    public void clicReporteCarreras(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        parametros.put("SALUDO", PAQUETE_IMAGES + "CarrerasT.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteCarreras.jasper", 
                parametros, "Reporte de carreras");
    }
    
    @FXML
    public void clicReporteSalones(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        parametros.put("SALUDO", PAQUETE_IMAGES + "salon-de-clases.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper", 
                parametros, "Reporte de salones");
    }
    
    @FXML
    public void clicReporteInstructores(){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
        parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
        parametros.put("SALUDO", PAQUETE_IMAGES + "Instructores.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteInstructores.jasper", 
                parametros, "Reporte de instructores");
    }


    
    
}
