
package org.in5bv.juanrivas.tolchavalan.controllers;

/**
 *
 * @author Ulil Eduardo Tol Chavalan
 * @date 5/06/2022
 * @time 01:55:59
 * 
 * código técnico: IN5BV   
 */

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import java.time.LocalDate;

import javafx.stage.Stage;
        
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import org.in5bv.juanrivas.db.Conexion;
import org.in5bv.juanrivas.models.Instructores;
import org.in5bv.juanrivas.system.Principal;

public class InstructoresController implements Initializable{
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGE = "org/in5bv/juanrivas/resources/images/";

    private ObservableList<Instructores> listaObservableInstructores;

    private Principal escenarioPrincipal;
    
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private DatePicker dpkFechaNacimiento;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblInstructores;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colFechaNt;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgEditar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location,ResourceBundle resource){    
        cargarDatos();
    }
    
    
    public void cargarDatos(){
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<Instructores, String>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre2"));       
        colNombre3.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido1"));
	colApellido2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido2"));
	colDireccion.setCellValueFactory(new PropertyValueFactory<Instructores, String>("direccion"));
	colEmail.setCellValueFactory(new PropertyValueFactory<Instructores, String>("email"));
	colTelefono.setCellValueFactory(new PropertyValueFactory<Instructores, String>("telefono"));
	colFechaNt.setCellValueFactory(new PropertyValueFactory<Instructores, LocalDate>("fechaNacimiento"));
    }
    
    private boolean existeElementoSeleccionado(){
        return(tblInstructores.getSelectionModel().getSelectedItem() != null);
    }
    
    @FXML
    public void seleccionarElemento(){
        if(existeElementoSeleccionado()){
            txtId.setText(String.valueOf(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getId()));
            txtNombre1.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDireccion.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            dpkFechaNacimiento.setValue(((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getFechaNacimiento());
                   
        }          
    }
    
    private boolean eliminarInstructores(){
        if(existeElementoSeleccionado()){
            Instructores instructor = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            System.out.println(instructor);
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_instructores_delete(?)");
                pstmt.setInt(1, instructor.getId());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e){
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + instructor.toString());
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }      
        }
        return false;
    }
    
    
    public ObservableList getInstructores() {
        ArrayList<Instructores> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_instructores_read()");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());
                lista.add(instructor);
                System.out.println(instructor.toString());
            }
            listaObservableInstructores = FXCollections.observableArrayList(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return listaObservableInstructores;
    }

    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private void habilitarCampos(){   
        txtId.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);
        dpkFechaNacimiento.setDisable(false);
        
        txtId.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        
    }
    
    private void limpiarCampos(){
        txtId.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        dpkFechaNacimiento.getEditor().clear();
    }
     
    private void deshabilitarCampos(){   
        txtId.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        dpkFechaNacimiento.setDisable(true);
        
        txtId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        
    }
     
    @FXML
    private void clicNuevo(){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblInstructores.setDisable(true);
                
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "disco-flexible.png"));
                
                btnEditar.setText("Cancelar");
                imgEditar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));
                
                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);
                
                btnReporte.setDisable(true);
                btnReporte.setVisible(false);
                
                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                
                if (agregarInstructor()){
                        
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "anadir.png"));
                    
                    btnEditar.setText("Modificar");
                    imgEditar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));
                    
                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);
                    
                    tblInstructores.setDisable(false);
                    
                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }
    
    private boolean agregarInstructor(){
        Instructores instructor = new Instructores();
        instructor.setId(Integer.valueOf(txtId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());              
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        dpkFechaNacimiento.getValue().atStartOfDay();
        
        PreparedStatement pstmt = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("CALL sp_instructores_create(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, instructor.getId());
            pstmt.setString(1, instructor.getNombre1());  
            pstmt.setString(2, instructor.getNombre2());                     
            pstmt.setString(3, instructor.getNombre3());
            pstmt.setString(4, instructor.getApellido1());
            pstmt.setString(5, instructor.getApellido2());
            pstmt.setString(6, instructor.getDireccion());
            pstmt.setString(7, instructor.getEmail());
            pstmt.setString(8, instructor.getTelefono());
            pstmt.setDate(9, Date.valueOf(instructor.getFechaNacimiento()));
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            
            listaObservableInstructores.add(instructor);
            
            return true;
        } catch (SQLException e){
            System.out.println("\nSe produjo un error al intentar agregar el siguiente registro" + instructor.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }      
        return false;
    } 
    
    
    @FXML
    private void clicEditar(){
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnEditar.setText("Guardar");
                    imgEditar.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "Cancelar.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Control academico");
                    alert.setContentText("Para poder editar un registro, primero seleecione uno. ");
                    alert.show();
                }

                break;
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "anadir.png"));

                btnEditar.setText("Editar");
                imgEditar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblInstructores.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (actualizarInstructor()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    btnNuevo.setDisable(false);
                    btnNuevo.setVisible(true);
                    
                    btnEditar.setText("Modificar");
                    imgEditar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));
                    
                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));
                    
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);
                    
                    operacion = Operacion.NINGUNO;
                }
                break;          
        }
    }
    
    private boolean actualizarInstructor(){
        Instructores instructores = new Instructores();
        instructores.setId(Integer.parseInt(txtId.getText()));
        instructores.setNombre1(txtNombre1.getText());
        instructores.setNombre2(txtNombre2.getText());              
        instructores.setNombre3(txtNombre3.getText());
        instructores.setApellido1(txtApellido1.getText());
        instructores.setApellido2(txtApellido2.getText());
        instructores.setDireccion(txtDireccion.getText());
        instructores.setEmail(txtEmail.getText());
        instructores.setTelefono(txtTelefono.getText());
        dpkFechaNacimiento.getValue().atStartOfDay();

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("CALL sp_instructores_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, instructores.getId());
            pstmt.setString(2, instructores.getNombre1());  
            pstmt.setString(3, instructores.getNombre2());                     
            pstmt.setString(4, instructores.getNombre3());
            pstmt.setString(5, instructores.getApellido1());
            pstmt.setString(6, instructores.getApellido2());
            pstmt.setString(7, instructores.getDireccion());
            pstmt.setString(8, instructores.getEmail());
            pstmt.setString(9, instructores.getTelefono());
            pstmt.setDate(10, Date.valueOf(instructores.getFechaNacimiento()));

            System.out.println(pstmt.toString());
            pstmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("\nSe produjo un error al intentar actualizar el siguiente registro" + instructores.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    
    @FXML
    private void clicEliminar(){
        switch(operacion){
            case ACTUALIZAR:
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);
                
                btnEditar.setText("Modificar");
                imgEditar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));
                
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));
                
                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                
                limpiarCampos();
                deshabilitarCampos();
                
                tblInstructores.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if(existeElementoSeleccionado()){
                    
                    Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control academico");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro que desea eliminar el registro seleccionado?");                 
                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (result.get().equals(ButtonType.OK)){
                        if (eliminarInstructores()){
                            listaObservableInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();
                            /*
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Control academico");
                            alerta.setHeaderText(null);
                            alerta.setContentText("Registro eliminado exitosamente");

                            Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image(PAQUETE_IMAGE + "logo.JPG"));
                            alerta.show();
                            */
                        }                     
                    } else {
                        alert.close();
                        tblInstructores.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }                  
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Control academico");
                    alert.setContentText("Para poder eliminar un registro, seleccione un registro. ");
                    alert.show();
                }
                break;
        }
    }
     
    @FXML
    private void clicReporte(){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("AVISO!!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta funcionalidad solo esta disponible en la versión PRO");
        
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGE + "logo.JPG"));
        alerta.show();
    }
    
    @FXML
    public void clicRegresar(){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
}
