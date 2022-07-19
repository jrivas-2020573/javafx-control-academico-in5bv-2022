package org.in5bv.juanrivas.tolchavalan.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.in5bv.juanrivas.models.Usuarios;
import org.in5bv.juanrivas.system.Principal;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 19 jul 2022
 * @time 13:50:15
 * Carné: 2020573
 * Grupo: 1
 * Codigo Tecnico: IN5BV
 */
public class LoginController implements Initializable {
    
    private final String PAQUETE_IMAGES = "org/in5bv/juanrivas/resources/images/";
    
    private Principal escenarioPrincipal;
    
    @FXML
    private TextField txtUserName;
    
    @FXML
    private PasswordField pswContrasena;
    
    @FXML
    private Button btnAcceder;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
       
    public Connection Conectar(){
        Connection con = null;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/db_control_academico_in5bv", "kinal" ,"admin");
        } catch (SQLException e) {
            System.err.println(e.toString());
            System.err.println("\nSe a producido un error al intentar conectar a la base de datos");
        }
        return con;
    }
    
    public void Ingresar(){
        Usuarios usuario = new Usuarios();
        Connection con1 = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String user = txtUserName.getText();
        String pass = pswContrasena.getText();
        if (user.equals("") || pass.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("\nRevise que todos los campos contengan datos");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
                    alert.show();
        } else {
            try {
                con1 = Conectar();
                //pst = con1.prepareStatement("select user, pass from usuarios where user='" + user + "' and pass ='" + pass + "'");
                pst = con1.prepareStatement("{CALL sp_read_usuario()}");
                rs = pst.executeQuery();
                if (rs.next()) {
                    escenarioPrincipal.mostrarEscenaPrincipal();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("\nCredenciales Incorrectas intentelo nuevamente");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
                    alert.show();
                }
            } catch (SQLException e) {
                System.err.println(e.toString());
                e.printStackTrace();
            }
        }
        
    }
    
    @FXML
    private void clicIngresar(){
        Ingresar();
    }
    
}
