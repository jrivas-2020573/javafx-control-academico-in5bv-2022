package org.in5bv.juanrivas.tolchavalan.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.in5bv.juanrivas.system.Principal;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 28/04/2022
 * 
 * Codigo Tecnico: IN5BV
 */
public class MenuPrincipalController implements Initializable {
    
    private Principal escenarioPrincipal;
  
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
    
}
