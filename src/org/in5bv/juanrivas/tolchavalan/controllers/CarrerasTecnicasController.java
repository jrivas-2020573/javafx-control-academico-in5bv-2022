
package org.in5bv.juanrivas.tolchavalan.controllers;

/**
 * @author Ulil Eduardo Tol Chavalan
 * @date 19/04/2022
 * 
 * Código técnico: IN5BV
 * Grupo: 2
 * Carné: 2020470
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
import javafx.stage.Stage;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import org.in5bv.juanrivas.db.Conexion;
import org.in5bv.juanrivas.models.CarrerasTecnicas;
import org.in5bv.juanrivas.system.Principal;

public class CarrerasTecnicasController implements Initializable {
    
    private enum Operacion{
       NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    private final String PAQUETE_IMAGE = "org/in5bv/juanrivas/resources/images/";
    
    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;
    
    private Principal escenarioPrincipal;
    
    @FXML
    private TextField txtTecnico;
    
    @FXML
    private TextField txtCarrera;
    
    @FXML
    private TextField txtGrado;
    
    @FXML
    private TextField txtSeccion;
    
    @FXML
    private TextField txtJornada;
           
    @FXML
    private Button btnNuevo;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnListar;
    
    @FXML
    private TableView tblCarrerasTecnicas;
    
    @FXML
    private TableColumn colCodigoTecnico;
            
    @FXML
    private TableColumn colCarrera;
            
    @FXML
    private TableColumn colGrado;
            
    @FXML
    private TableColumn colSeccion; 
            
    @FXML
    private TableColumn colJornada;
    
    @FXML
    private ImageView imgNuevo;
    
    @FXML
    private ImageView imgModificar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private ImageView imgListar;
    
    @Override
    public void initialize(URL location,ResourceBundle resource){    
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera")); 
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
    }
    
    private boolean existeElementoSeleccionado(){
        if (tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null) {
            return true;
        } else {
            return false;
        }
    }
    
    @FXML
    public void seleccionarElemento(){
        if(existeElementoSeleccionado() == true){
            txtTecnico.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtGrado.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtCarrera.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera()); 
            txtSeccion.setText(String.valueOf(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion()));
            txtJornada.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
        }          
    }
    
    private boolean eliminarCarrerasTecnicas(){
        if(existeElementoSeleccionado()){
            CarrerasTecnicas carrerastecnicas = (CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            System.out.println(carrerastecnicas);
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_delete(?)");
                pstmt.setString(1, carrerastecnicas.getCodigoTecnico());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e){
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + carrerastecnicas.toString());
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
            
    public ObservableList getCarrerasTecnicas(){
        
        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;      
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_read()");           
            rs = pstmt.executeQuery();        
            while(rs.next()){               
               CarrerasTecnicas carrerastecnicas = new CarrerasTecnicas();
               carrerastecnicas.setCodigoTecnico(rs.getString(1));
               carrerastecnicas.setCarrera(rs.getString(2));
               carrerastecnicas.setGrado(rs.getString(3));                           
               carrerastecnicas.setSeccion(rs.getString(4).charAt(0));
               carrerastecnicas.setJornada(rs.getString(5));
               lista.add(carrerastecnicas);
               System.out.println(carrerastecnicas.toString());
            }    
            
            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);
                
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }            
                if (pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                          
        }
        return listaCarrerasTecnicas;    
        
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private void habilitarCampos(){   
        txtTecnico.setEditable(true);
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);
        
        txtTecnico.setDisable(false);
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
    }
    
    private void limpiarCampos(){
        txtTecnico.clear();
        txtCarrera.clear();
        txtGrado.clear();
        txtSeccion.clear();
        txtJornada.clear();
    }
    
    private void deshabilitarCampos(){
        txtTecnico.setEditable(false);
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);
        
        txtTecnico.setDisable(true);
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);
    }
    
    
    
    
    @FXML
    private void clicNuevo(){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblCarrerasTecnicas.setDisable(true);
                
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "disco-flexible.png"));
                
                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));
                
                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);
                imgEliminar.setVisible(false);
                
                btnListar.setDisable(true);
                btnListar.setVisible(false);
                imgListar.setVisible(false);
                
                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                
                if (agregarCarreraTecnica()){
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "anadir.png"));
                    
                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));
                    
                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    imgEliminar.setVisible(true);
                    
                    btnListar.setDisable(false);
                    btnListar.setVisible(true);
                    imgListar.setVisible(true);
                    
                    tblCarrerasTecnicas.setDisable(false);
                    
                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }
    
    private boolean agregarCarreraTecnica(){
        CarrerasTecnicas carrerastecnicas = new CarrerasTecnicas();
        carrerastecnicas.setCodigoTecnico(txtTecnico.getText());
        carrerastecnicas.setCarrera(txtCarrera.getText());
        carrerastecnicas.setGrado(txtGrado.getText());              
        carrerastecnicas.setSeccion(txtSeccion.getText().charAt(0));
        carrerastecnicas.setJornada(txtJornada.getText());
        
        PreparedStatement pstmt = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_create(?, ?, ?, ?, ?)");

            pstmt.setString(1, carrerastecnicas.getCodigoTecnico());
            pstmt.setString(2, carrerastecnicas.getCarrera());  
            pstmt.setString(3, carrerastecnicas.getGrado());                     
            pstmt.setString(4, String.valueOf(carrerastecnicas.getSeccion()));
            pstmt.setString(5, carrerastecnicas.getJornada());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            
            listaCarrerasTecnicas.add(carrerastecnicas);
            
            return true;
        } catch (SQLException e){
            System.out.println("\nSe produjo un error al intentar agregar el siguiente registro" + carrerastecnicas.toString());
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
    private void clicModificar(){
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();
                    
                    txtTecnico.setDisable(true);
                    txtTecnico.setEditable(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "disco-flexible.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));

                    btnListar.setDisable(true);
                    btnListar.setVisible(false);
                    imgListar.setVisible(false);
                    
                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);
                    imgNuevo.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Control academico");
                    alert.setContentText("Para poder editar un registro, primero seleecione uno. ");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "logo-control-academico1.png"));
                    alert.show();
                }

                break;
            case GUARDAR:
                btnNuevo.setText("Insertar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "anadir.png"));

                btnModificar.setText("Actualizar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);
                imgEliminar.setVisible(true);

                btnListar.setDisable(false);
                btnListar.setVisible(true);
                imgListar.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblCarrerasTecnicas.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (actualizarCarrerasTs()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    btnNuevo.setDisable(false);
                    btnNuevo.setVisible(true);
                    imgNuevo.setVisible(true);
                    
                    btnModificar.setText("Actualizar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));
                    
                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));
                    
                    btnListar.setDisable(false);
                    btnListar.setVisible(true);
                    imgListar.setVisible(true);
                    
                    operacion = Operacion.NINGUNO;
                }
                break;          
        }
    }
    
    private boolean actualizarCarrerasTs(){
        if (existeElementoSeleccionado()) {
            
            CarrerasTecnicas carrerastecnicas = new CarrerasTecnicas();
            carrerastecnicas.setCodigoTecnico(txtTecnico.getText());      
            carrerastecnicas.setCarrera(txtCarrera.getText());  
            carrerastecnicas.setGrado(txtGrado.getText());
            carrerastecnicas.setSeccion(txtSeccion.getText().charAt(0));
            carrerastecnicas.setJornada(txtJornada.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_update(?, ?, ?, ?, ?)");

                pstmt.setString(1, carrerastecnicas.getCodigoTecnico());
                pstmt.setString(2, carrerastecnicas.getCarrera());  
                pstmt.setString(3, carrerastecnicas.getGrado());                     
                //pstmt.setString(4, carrerastecnicas.getSeccion());
                pstmt.setString(5, carrerastecnicas.getJornada());

                System.out.println(pstmt.toString());
                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.out.println("\nSe produjo un error al intentar actualizar el siguiente registro" + carrerastecnicas.toString());
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
        }     
        return false;
    }
    
    @FXML
    private void clicEliminar(){
        switch(operacion){
            case ACTUALIZAR:
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);
                
                btnModificar.setText("Actualizar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));
                
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));
                
                btnListar.setDisable(false);
                btnListar.setVisible(true);
                imgListar.setVisible(true);
                
                limpiarCampos();
                deshabilitarCampos();
                
                tblCarrerasTecnicas.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if(existeElementoSeleccionado()){
                    
                    Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control academico");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro que desea eliminar el registro seleccionado?"); 
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "logo-control-academico1.png"));
                    
                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (result.get().equals(ButtonType.OK)){
                        if (eliminarCarrerasTecnicas()){
                            limpiarCampos();
                            cargarDatos();
                            
                            Alert alertInformation = new Alert(Alert.AlertType.CONFIRMATION);
                            alertInformation.setTitle("Control Academico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro Eliminado");
                        }                     
                    } else {
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
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
    private void clicListar(){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("AVISO!!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta funcionalidad solo esta disponible en la versión PRO");
        
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGE + "logo-control-academico1.png"));
        alerta.show();
    }
    
    @FXML
    public void clicRegresar(){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
}
