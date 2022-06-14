package org.in5bv.juanrivas.tolchavalan.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bv.juanrivas.db.Conexion;
import org.in5bv.juanrivas.models.Horarios;
import org.in5bv.juanrivas.system.Principal;
import com.jfoenix.controls.JFXTimePicker;
import java.sql.Time;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 7 jun 2022
 * @time 15:11:44
 * Carné: 2020573
 * Grupo: 1
 * Codigo Tecnico: IN5BV
 */
public class HorariosController implements Initializable{
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private final String PAQUETE_IMAGES = "org/in5bv/juanrivas/resources/images/";
    
    private Operacion operacion = Operacion.NINGUNO;
    
    private ObservableList<Horarios> listaHorarios;
    
    private Principal escenarioPrincipal;
    
    @FXML
    private  TextField txtID;
   
    @FXML
    private JFXTimePicker tpkHoraIngreso;
    
    @FXML
    private JFXTimePicker tpkHoraSalida;
    
    @FXML
    private CheckBox cbxLunes;
    
    @FXML
    private CheckBox cbxMartes;
    
    @FXML
    private CheckBox cbxMiercoles;
    
    @FXML
    private CheckBox cbxJueves;
    
    @FXML
    private CheckBox cbxViernes;
      
   
    @FXML
    private Button btnNuevo;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnListar;
    
    @FXML
    private TableView tblHorarios;
    
    @FXML
    private TableColumn colID;
    
    @FXML
    private TableColumn colHorarioEntrada;
    
    @FXML
    private TableColumn colHorarioSalida;
    
    @FXML
    private TableColumn colLunes;
    
    @FXML
    private TableColumn colMartes;
    
    @FXML
    private TableColumn colMiercoles;
    
    @FXML
    private TableColumn colJueves;
    
    @FXML
    private TableColumn colViernes;
    
    @FXML
    private ImageView imgNuevo;
    
    @FXML
    private ImageView imgModificar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private ImageView imgListar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public ObservableList getHorarios() {
        ArrayList<Horarios> Lista = new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horario_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorario_inicio(rs.getTime(2).toLocalTime());
                horario.setHorario_final(rs.getTime(3).toLocalTime());
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                System.out.println(horario.toString());
                
                Lista.add(horario);
            }
            
            listaHorarios = FXCollections.observableArrayList(Lista);
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la lista de horarios");
            System.err.println("Message: " + e.getMessage());
            System.err.println("error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            
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
        return listaHorarios;
    }
    
    public void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colID.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("id"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horarios, String>("horario_inicio"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horarios, String>("horario_final"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horarios, String>("Lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horarios, String>("Martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horarios, String>("Miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horarios, String>("Jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horarios, String>("Viernes"));
    }
    
    private boolean eliminarHorario() {
        if(existeElementoSeleccionado()) {
            Horarios horario = (Horarios) tblHorarios.getSelectionModel().getSelectedItem();
            
            PreparedStatement pstmt = null;
            
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horario_delete(?)}");
                pstmt.setInt(1, horario.getId());
                
                System.out.println(pstmt.toString());
                
                pstmt.execute();
                
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + horario.toString());
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
    
    private boolean existeElementoSeleccionado() {
       return (tblHorarios.getSelectionModel().getSelectedItem() != null); 
    }
    
    private boolean actualizarHorario() {
        if(existeElementoSeleccionado()) {
            
            Horarios horario = new Horarios();
            horario.setId(Integer.parseInt(txtID.getText()));
            horario.setHorario_inicio(tpkHoraIngreso.getValue());
            horario.setHorario_final(tpkHoraSalida.getValue());
            horario.setLunes(cbxLunes.isSelected());
            horario.setMartes(cbxMartes.isSelected());
            horario.setMiercoles(cbxMiercoles.isSelected());
            horario.setJueves(cbxJueves.isSelected());
            horario.setViernes(cbxViernes.isSelected());
            
            
            PreparedStatement pstmt = null;
            
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horario_update(?,?,?,?,?,?,?,?)}");
                pstmt.setInt(1, horario.getId());
                pstmt.setTime(2, Time.valueOf(horario.getHorario_inicio()));
                pstmt.setTime(3, Time.valueOf(horario.getHorario_final()));
                pstmt.setBoolean(4, horario.isLunes());
                pstmt.setBoolean(5, horario.isMartes());
                pstmt.setBoolean(6, horario.isMiercoles());
                pstmt.setBoolean(7, horario.isJueves());
                pstmt.setBoolean(8, horario.isViernes());
                
                System.out.println(pstmt.toString());
                pstmt.execute();
                listaHorarios.add(horario);
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un erro al actualizar el siguiente registro: " + horario.toString());
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
    
    private boolean agregarHorario() {
        
        Horarios horario = new Horarios();
        horario.setId(Integer.parseInt(txtID.getText()));
        horario.setHorario_inicio(tpkHoraIngreso.getValue());
        horario.setHorario_final(tpkHoraSalida.getValue());
        horario.setLunes(cbxLunes.isSelected());
        horario.setMartes(cbxMartes.isSelected());
        horario.setMiercoles(cbxMiercoles.isSelected());
        horario.setJueves(cbxJueves.isSelected());
        horario.setViernes(cbxViernes.isSelected());
       
        PreparedStatement pstmt = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_create(?,?,?,?,?,?,?,?)}");
            pstmt.setInt(1, horario.getId());
            pstmt.setTime(2, Time.valueOf(horario.getHorario_inicio()));
            pstmt.setTime(3, Time.valueOf(horario.getHorario_final()));
            pstmt.setBoolean(4, horario.isLunes());
            pstmt.setBoolean(5, horario.isMartes());
            pstmt.setBoolean(6, horario.isMiercoles());
            pstmt.setBoolean(7, horario.isJueves());
            pstmt.setBoolean(8, horario.isViernes());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaHorarios.add(horario);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + horario.toString());
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
    public void seleccionarElemento() {
        if(existeElementoSeleccionado() == true) {
           txtID.setText(String.valueOf(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getId()));
           tpkHoraIngreso.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorario_inicio());
           tpkHoraSalida.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorario_final());
           cbxLunes.setSelected((((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isLunes()));
           cbxMartes.setSelected((((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMartes()));
           cbxMiercoles.setSelected((((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles()));
           cbxJueves.setSelected((((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isJueves()));
           cbxViernes.setSelected((((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isViernes()));
           
        }
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private void habilitarCampos() {
        txtID.setEditable(true);
        txtID.setDisable(false);
        tpkHoraIngreso.setDisable(false);
        tpkHoraSalida.setDisable(false);
        cbxLunes.setDisable(false);
        cbxMartes.setDisable(false);
        cbxMiercoles.setDisable(false);
        cbxJueves.setDisable(false);
        cbxViernes.setDisable(false);

    }
    
    private void deshabilitarCampos() {
        txtID.setEditable(false);
        txtID.setDisable(true);
        tpkHoraIngreso.setDisable(true);
        tpkHoraSalida.setDisable(true);
        cbxLunes.setDisable(true);
        cbxMartes.setDisable(true);
        cbxMiercoles.setDisable(true);
        cbxJueves.setDisable(true);
        cbxViernes.setDisable(true);
        
    }
    
    private void limpiarCampos() {
        txtID.clear();
        tpkHoraIngreso.getEditor().clear();
        tpkHoraSalida.getEditor().clear();
        cbxLunes.setSelected(false);
        cbxMartes.setSelected(false);
        cbxMiercoles.setSelected(false);
        cbxJueves.setSelected(false);
        cbxViernes.setSelected(false);
        
        
    }
    
    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblHorarios.setDisable(true);
                
                
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "disco-flexible.png"));
                
                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);
                imgEliminar.setVisible(false);

                btnListar.setDisable(true);
                btnListar.setVisible(false);
                imgListar.setVisible(false);
                
                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if(agregarHorario()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Control Academico");
                    information.setHeaderText(null);
                    information.setContentText("Registro insertado con exito");
                    Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                    stageInformation.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
                    information.show();
                    
                    btnNuevo.setText("Insertar");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "anadir.png"));
                    
                    btnModificar.setText("Actualizar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "actualizar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    imgEliminar.setVisible(true);
                    
                    btnListar.setDisable(false);
                    btnListar.setVisible(true);
                    imgListar.setVisible(true);
                    
                    tblHorarios.setDisable(false);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }
    
    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if(existeElementoSeleccionado()) {
                    habilitarCampos();
                    
                    txtID.setDisable(true);
                    txtID.setEditable(false);
                    
                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "disco-flexible.png"));
                    
                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                    btnListar.setDisable(true);
                    btnListar.setVisible(false);
                    imgListar.setVisible(false);

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);
                    imgNuevo.setVisible(false);
                    
                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar seleccione un elemento :O");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
                    alert.show();
                }
                break;
            case GUARDAR: //cancelar una insercion
                habilitarCampos();
                limpiarCampos();
                
                tblHorarios.setDisable(false);
                
                btnNuevo.setText("Insertar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "anadir.png"));

                btnModificar.setText("Actualizar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "actualizar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);
                imgEliminar.setVisible(true);

                btnListar.setDisable(false);
                btnListar.setVisible(true);
                imgListar.setVisible(true);

                tblHorarios.getSelectionModel().clearSelection();
                
                operacion = Operacion.NINGUNO;
                deshabilitarCampos();
                break;
            case ACTUALIZAR: //Actualizar un registro
                if(actualizarHorario()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Control Academico");
                    information.setHeaderText(null);
                    information.setContentText("Registro Actualizado Exitosamente");
                    Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                    stageInformation.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
                    information.show();
                    
                    
                    btnModificar.setText("Actualizar");
                    imgModificar.setImage(new Image (PAQUETE_IMAGES + "actualizar.png"));
                    
                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));
                    
                    btnNuevo.setDisable(false);
                    btnNuevo.setVisible(true);
                    imgNuevo.setVisible(true);
                    
                    btnListar.setDisable(false);
                    btnListar.setVisible(true);
                    imgListar.setVisible(true);
                    
                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }
    
    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //CANCELAR UNA MODIFICACION
                habilitarCampos();
                limpiarCampos();

                btnModificar.setText("Actualizar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "actualizar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));
                
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);
                
                btnListar.setDisable(false);
                btnListar.setVisible(true);
                imgListar.setVisible(true);

                
                tblHorarios.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                deshabilitarCampos();
                break;
            case NINGUNO: //ELIMINAR REGISTROS
                if (existeElementoSeleccionado()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Estas seguro de querer eliminar este registro?");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
                    
                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarHorario()) {
                            listaHorarios.remove(tblHorarios.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();
                            
                            Alert alertInformation = new Alert(Alert.AlertType.CONFIRMATION);
                            alertInformation.setTitle("Control Academico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro Eliminado");
                        }   
                    } else {
                        limpiarCampos();
                        tblHorarios.getSelectionModel().clearSelection();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }
                break;
        }
    }
    
    
    
    
    
    @FXML
    private void clicListar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("AVISO");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta funcion solo esta disponible en la version Premium");

        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));

        alerta.show();
    }
    
    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
}
