package org.in5bv.juanrivas.tolchavalan.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bv.juanrivas.db.Conexion;
import org.in5bv.juanrivas.models.Salones;
import org.in5bv.juanrivas.system.Principal;

/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 26 abr 2022
 * @time 22:54:59 Carné: 2020573 Grupo: 1 Codigo Tecnico: IN5BV
 */
public class SalonesController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private final String PAQUETE_IMAGES = "org/in5bv/juanrivas/resources/images/";

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private ObservableList<Salones> listaSalones;

    @FXML
    private TextField txtCodigoSalon;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtCapacidadMaxima;

    @FXML
    private TextField txtEdificio;

    @FXML
    private TextField txtNivel;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnListar;

    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapMaxima;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;

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

    public ObservableList getSalones() {
        ArrayList<Salones> Lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_read()");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                System.out.println(salon.toString());

                Lista.add(salon);
            }

            listaSalones = FXCollections.observableArrayList(Lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de salones");
            System.err.println("Message: " + e.getMessage());
            System.err.println("error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());

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
        return listaSalones;
    }

    public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapMaxima.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));

    }

    private boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println(salon);

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_delete(?)");
                pstmt.setString(1, salon.getCodigoSalon());

                System.out.println(pstmt);

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + salon.toString());
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
        if (tblSalones.getSelectionModel().getSelectedItem() != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean actualizarSalon() {
        if (existeElementoSeleccionado()) {

            Salones salon = new Salones();
            salon.setCodigoSalon(txtCodigoSalon.getText());
            salon.setDescripcion(txtDescripcion.getText());
            salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel(Integer.parseInt(txtNivel.getText()));

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_update(?,?,?,?,?)");
                pstmt.setString(1, salon.getCodigoSalon());
                pstmt.setString(2, salon.getDescripcion());
                pstmt.setInt(3, salon.getCapacidadMaxima());
                pstmt.setString(4, salon.getEdificio());
                pstmt.setInt(5, salon.getNivel());

                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al actualizar el siguiente registro: " + salon.toString());
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

    private boolean agregarSalon() {

        Salones salon = new Salones();
        salon.setCodigoSalon(txtCodigoSalon.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel(Integer.parseInt(txtNivel.getText()));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_create(?,?,?,?,?)");
            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaSalones.add(salon);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + salon.toString());
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
        if (existeElementoSeleccionado() == true) {
            txtCodigoSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            txtCapacidadMaxima.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));

        }
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void habilitarCampos() {
        txtCodigoSalon.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);

        txtCodigoSalon.setDisable(false);
        txtDescripcion.setDisable(false);
        txtCapacidadMaxima.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtCodigoSalon.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCapacidadMaxima.setEditable(false);
        txtEdificio.setEditable(false);
        txtNivel.setEditable(false);

        txtCodigoSalon.setDisable(true);
        txtDescripcion.setDisable(true);
        txtCapacidadMaxima.setDisable(true);
        txtEdificio.setDisable(true);
        txtNivel.setDisable(true);

    }

    private void limpiarCampos() {
        txtCodigoSalon.clear();
        txtDescripcion.clear();
        txtCapacidadMaxima.clear();
        txtEdificio.clear();
        txtNivel.clear();

    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();

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
                if (agregarSalon()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

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

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();
                    txtCodigoSalon.setDisable(true);
                    txtCodigoSalon.setEditable(false);

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
                    alert.show();
                }
                break;
            case GUARDAR:
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

                limpiarCampos();
                deshabilitarCampos();
                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (actualizarSalon()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setDisable(false);
                    btnNuevo.setVisible(true);
                    imgNuevo.setVisible(true);

                    btnModificar.setText("Actualizar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "actualizar.png"));

                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

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
            case ACTUALIZAR:
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);

                btnModificar.setText("Actualizar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "actualizar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnListar.setDisable(false);
                btnListar.setVisible(true);
                imgListar.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblSalones.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Estas seguro de querer eliminar este registro?");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
                    
                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarSalon()) {
                            limpiarCampos();
                            cargarDatos();
                            
                            Alert alertInformation = new Alert(Alert.AlertType.CONFIRMATION);
                            alertInformation.setTitle("Control Academico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro Eliminado");
                        }   
                    } else {
                        tblSalones.getSelectionModel().clearSelection();
                        limpiarCampos();
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
        //alerta.setHeaderText("Control Académico KINAL");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta funcion solo esta disponible en la version Premium");

        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image("org/in5bv/juanrivas/resources/images/logo-control-academico1.png"));

        alerta.show();
    }

    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

}
