package org.in5bv.juanrivas.tolchavalan.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bv.juanrivas.system.Principal;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 11 jul 2022
 * @time 17:38:35
 * Carné: 2020573
 * Grupo: 1
 * Codigo Tecnico: IN5BV
 */
public class AcercaDeController implements Initializable{
    
    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
}
