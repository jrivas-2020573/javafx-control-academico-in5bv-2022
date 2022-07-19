
package org.in5bv.juanrivas.tolchavalan.controllers;

/**
 *
 * @author Ulil Eduardo Tol Chavalan
 * @date 5/06/2022
 * @time 01:56:23
 * 
 * código técnico: IN5BV   
 */
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.in5bv.juanrivas.models.Instructores;
import org.in5bv.juanrivas.models.Salones;
import org.in5bv.juanrivas.models.CarrerasTecnicas;
import org.in5bv.juanrivas.models.Horarios;
import org.in5bv.juanrivas.models.Cursos;
import org.in5bv.juanrivas.db.Conexion;
import org.in5bv.juanrivas.reports.GenerarReporte;

import org.in5bv.juanrivas.system.Principal;

public class CursosController implements Initializable {
    
    private enum Operacion{
       NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    private final String PAQUETE_IMAGE = "org/in5bv/juanrivas/resources/images/";
    
    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<Instructores> listaObservableInstructores;
    private ObservableList<Salones> listaObservableSalones;
    private ObservableList<CarrerasTecnicas> listaObersavableCarrerasTecnicas;
    private ObservableList<Horarios> listaObservableHorarios;
    
    private Principal escenarioPrincipal;
    
    //<>
    @FXML
    private TextField txtRegistros;
    
    @FXML
    private TextField txtId;
    
    @FXML
    private TextField txtNombreCurso;
    
    
    @FXML
    private Spinner<Integer> spnCiclo;
    
    private SpinnerValueFactory<Integer> valueFactoryCiclo;
    
    @FXML
    private Spinner<Integer> spnCupoMaximo;
    
    private SpinnerValueFactory<Integer> valueFactoryCupoMaximo;
    
    @FXML
    private Spinner<Integer> spnCupoMinimo;
    
    private SpinnerValueFactory<Integer> valueFactoryCupoMinimo;
    
    
    @FXML
    private ComboBox<CarrerasTecnicas> cmbCarreraTecnica;
    
    @FXML
    private ComboBox<Horarios> cmbHorario;
    
    @FXML
    private ComboBox<Instructores> cmbInstructor;
    
    @FXML
    private ComboBox<Salones> cmbSalon;
    
    
    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;
    
    
    @FXML
    private TableView<Cursos> tblCursos;
    
    @FXML
    private TableColumn<Cursos, Integer> colId;
    
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    
    @FXML
    private TableColumn<Cursos, Integer> colCiclo;
    
    @FXML
    private TableColumn<Cursos, Integer> colCupoMaximo;
    
    @FXML
    private TableColumn<Cursos, Integer> colCupoMinimo;
    
    @FXML
    private TableColumn<Cursos, String> colCodigoTecnico;
    
    @FXML 
    private TableColumn<Cursos, Integer> colHorarioId;
    
    @FXML
    private TableColumn<Cursos, Integer> colInstructorId;
    
    @FXML
    private TableColumn<Cursos, String> colSalonId;
    
    
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
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2050, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);
        
        valueFactoryCupoMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20);
        spnCupoMaximo.setValueFactory(valueFactoryCupoMaximo);
        
        valueFactoryCupoMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 5);
        spnCupoMinimo.setValueFactory(valueFactoryCupoMinimo);
        
        cargarDatos();
    }
    
    private void deshabilitarCampos(){
        txtId.setEditable(false);
        txtNombreCurso.setEditable(false);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorario.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);
        
        txtId.setDisable(true);
        txtNombreCurso.setDisable(true);
        spnCiclo.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        cmbCarreraTecnica.setDisable(true);
        cmbHorario.setDisable(true);
        cmbInstructor.setEditable(true);
        cmbSalon.setEditable(true);
        
        
    }
    
    private void habilitarCampos(){   
        txtId.setEditable(true);
        txtNombreCurso.setEditable(true);
        spnCiclo.setEditable(true);
        spnCupoMaximo.setEditable(true);
        spnCupoMinimo.setEditable(true);
        //cmbCarreraTecnica.setEditable(true);
        //cmbHorario.setEditable(true);
        //cmbInstructor.setEditable(true);
        //cmbSalon.setEditable(true);
        
        txtId.setDisable(false);
        txtNombreCurso.setDisable(false);
        spnCiclo.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        cmbCarreraTecnica.setDisable(false);
        cmbHorario.setDisable(false);
        cmbInstructor.setDisable(false);
        cmbSalon.setDisable(false);
    }
    
    private void limpiarCampos(){
        txtId.clear();
        txtNombreCurso.clear();
        spnCiclo.getValueFactory().setValue(2022);
        spnCupoMaximo.getValueFactory().setValue(0);
        spnCupoMinimo.getValueFactory().setValue(0);
        cmbCarreraTecnica.valueProperty().set(null);
        cmbHorario.valueProperty().set(null);
        cmbInstructor.valueProperty().set(null);
        cmbSalon.valueProperty().set(null);
    }
    
    // read → Listar todos los registros
    int contador = 0;
    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_read()}");
            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setInstructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());

                arrayListCursos.add(curso);
                
                for (int i = 0; i <= arrayListCursos.size() ; i++){
                    contador = 1 + i;
                }
                
            }
            txtRegistros.setText(String.valueOf(contador -1));

            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);

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
        return listaObservableCursos;
    }
    
    private ObservableList getCarrerasTecnicas(){
        ArrayList<CarrerasTecnicas> arrayListCarreras = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_read()}");
            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
                
                carreraTecnica.setCodigoTecnico(rs.getString("codigo_tecnico"));
                carreraTecnica.setCarrera(rs.getString("carrera"));
                carreraTecnica.setGrado(rs.getString("grado"));
                carreraTecnica.setSeccion(rs.getString("seccion").charAt(0));
                carreraTecnica.setJornada(rs.getString("jornada"));
                
                System.out.println(carreraTecnica);
                
                arrayListCarreras.add(carreraTecnica);
            }
            
            listaObersavableCarrerasTecnicas = FXCollections.observableArrayList(arrayListCarreras);
        } catch (SQLException e){
            System.err.println("\nSe produjo un error al intentar listar la tabla de Carreras Técnicas");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
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
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return listaObersavableCarrerasTecnicas;
    }
    
    private ObservableList getHorarios() {
        ArrayList<Horarios> arrayListHorarios = new ArrayList<>();
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
                arrayListHorarios.add(horario);
            }
            listaObservableHorarios = FXCollections.observableArrayList(arrayListHorarios);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Horarios");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if (pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return listaObservableHorarios;
    }
    
    private ObservableList getSalones(){
        ArrayList<Salones> arrayListSalones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                System.out.println(salon.toString());
                arrayListSalones.add(salon);
            }
            listaObservableSalones = FXCollections.observableArrayList(arrayListSalones);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Salones");
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
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return listaObservableSalones;
    }
    
    private ObservableList getInstructores(){
        ArrayList<Instructores> arrayListInstructores = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            
            while (rs.next()){
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
                instructor.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                System.out.println(instructor);
                arrayListInstructores.add(instructor);
            }
            listaObservableInstructores = FXCollections.observableArrayList(arrayListInstructores);
            
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if (pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return listaObservableInstructores;
    }
    
    
    public void cargarDatos(){
        tblCursos.setItems(getCursos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory <>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colCupoMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colCupoMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colHorarioId.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colSalonId.setCellValueFactory(new PropertyValueFactory<>(""));
        
        cmbCarreraTecnica.setItems(getCarrerasTecnicas());
        cmbHorario.setItems(getHorarios());
        cmbInstructor.setItems(getInstructores());
        cmbSalon.setItems(getSalones());
    }
    
    
    public boolean agregarCursos(){
       Cursos curso = new Cursos();
       curso.setId(Integer.parseInt(txtId.getText()));
       curso.setNombreCurso(txtNombreCurso.getText());
       curso.setCiclo(spnCiclo.getValue());
       curso.setCupoMaximo(spnCupoMaximo.getValue());
       curso.setCupoMinimo(spnCupoMinimo.getValue());
       curso.setCarreraTecnicaId(((CarrerasTecnicas)cmbCarreraTecnica.getSelectionModel().getSelectedItem()).getCodigoTecnico());
       curso.setHorarioId(((Horarios)cmbHorario.getSelectionModel().getSelectedItem()).getId());
       curso.setInstructorId(((Instructores)cmbInstructor.getSelectionModel().getSelectedItem()).getId());
       curso.setSalonId(((Salones)cmbSalon.getSelectionModel().getSelectedItem()).getCodigoSalon());
       
       PreparedStatement pstmt = null;
       
       try {
           pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_create(?,?,?,?,?,?,?,?,?)}");
           pstmt.setInt(1, curso.getId());
           pstmt.setString(2, curso.getNombreCurso());
           pstmt.setInt(3, curso.getCiclo());
           pstmt.setInt(4, curso.getCupoMaximo());
           pstmt.setInt(5, curso.getCupoMinimo());
           pstmt.setString(6, curso.getCarreraTecnicaId());
           pstmt.setInt(7, curso.getHorarioId());
           pstmt.setInt(8, curso.getInstructorId());
           pstmt.setString(9, curso.getsalonId());
           System.out.println(pstmt.toString());
           
           pstmt.execute();
           listaObservableCursos.add(curso);
           return true;
       } catch (SQLException e) {
           System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + curso.toString());
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
        
    
    public boolean actualizarCursos(){
        
        Cursos curso = new Cursos();
        curso.setId(Integer.parseInt(txtId.getText()));
        curso.setNombreCurso(txtNombreCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());
        curso.setCarreraTecnicaId(((CarrerasTecnicas)cmbCarreraTecnica.getSelectionModel().getSelectedItem()).getCodigoTecnico());
        curso.setHorarioId(((Horarios)cmbHorario.getSelectionModel().getSelectedItem()).getId());
        curso.setInstructorId(((Instructores)cmbInstructor.getSelectionModel().getSelectedItem()).getId());
        curso.setSalonId(((Salones) cmbSalon.getSelectionModel().getSelectedItem()).getCodigoSalon());
        
        PreparedStatement pstmt = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_update(?,?,?,?,?,?,?,?,?)}");
            pstmt.setInt(1, curso.getId());
            pstmt.setString(2, curso.getNombreCurso());
            pstmt.setInt(3, curso.getCiclo());
            pstmt.setInt(4, curso.getCupoMaximo());
            pstmt.setInt(5, curso.getCupoMinimo());
            pstmt.setString(6, curso.getCarreraTecnicaId());
            pstmt.setInt(7, curso.getHorarioId());
            pstmt.setInt(8, curso.getInstructorId());
            pstmt.setString(9, curso.getsalonId());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaObservableCursos.add(curso);
            return true;
        } catch (SQLException e){
            System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: " + curso.toString());
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    
    public boolean eliminarCursos(){
        Cursos curso = (Cursos)tblCursos.getSelectionModel().getSelectedItem();
        System.out.println(curso.toString());
        PreparedStatement pstmt = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_delete(?)}");
            pstmt.setInt(1, curso.getId());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
            return true;
        } catch (SQLException e){
            System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + curso.toString());
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
    
    private boolean existeElementoSeleccionado(){      
        return(tblCursos.getSelectionModel().getSelectedItem() != null);
    }
    
    
    @FXML
    private void clicNuevo(ActionEvent event){
        switch(operacion){
            case NINGUNO:
                               
                habilitarCampos();
                limpiarCampos();  
                tblCursos.setDisable(true);
                
                txtId.setEditable(true);
                txtId.setDisable(false);
                              
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
                if(agregarCursos()){
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
                    
                    tblCursos.setDisable(false);
                    
                    operacion = Operacion.NINGUNO;
                }
                
                break;
            
        }
    
    }
    
    @FXML
    private void clicEditar(ActionEvent event){
        switch(operacion){
            case NINGUNO:
                
                if (existeElementoSeleccionado()) {
                    habilitarCampos();                   
                    
                    txtId.setEditable(true);
                    txtId.setDisable(false);
                    
                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnEditar.setText("Guardar");
                    imgEditar.setImage(new Image(PAQUETE_IMAGE + "disco-flexible.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancelar.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Control academico");
                    alert.setContentText("Para poder actualizar, seleccione un registro. ");
                    alert.show();
                }

            break;
            case GUARDAR :
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
                
                tblCursos.setDisable(false);
                
                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR :
                if(existeElementoSeleccionado()){
                    if(actualizarCursos()){
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();
                        tblCursos.setDisable(false);
                        tblCursos.getSelectionModel().clearSelection();
                        
                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGE + "anadir.png"));
                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);
                        
                        btnEditar.setText("Modificar");
                        imgEditar.setImage(new Image(PAQUETE_IMAGE + "actualizar.png"));
                        
                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGE + "eliminar.png"));
                        btnEliminar.setDisable(false);
                        btnEliminar.setVisible(true);
                        
                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);
                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
        }
    }
    
    @FXML
    private void clicEliminar(ActionEvent event){
        switch(operacion){
            case ACTUALIZAR: //Cancelar una modificaion 
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
                    
                    tblCursos.getSelectionModel().clearSelection();                
                    operacion = Operacion.NINGUNO;

                break;
            case NINGUNO: // Eliminar un registro   Agregar un alert para hacer si estoy seguro de que pregunte si deseo eliminar             
                if (existeElementoSeleccionado()) {
                    
                    Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control academico");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro que desea eliminar el registro seleccionado?"); 
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "logo-control-academico1.png"));
                    
                    Optional<ButtonType> result = alert.showAndWait();                   
                    if (result.get().equals(ButtonType.OK)){
                        if (eliminarCursos() ) {
                            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();
                            
                            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                            alertInformation.setTitle("Control Académico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro eliminado exitosamente");
                            alertInformation.show();
                        }
                    } else if(result.get().equals(ButtonType.CANCEL)) {     
                        alert.close();
                        tblCursos.getSelectionModel().clearSelection();
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
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGE + "Cursos.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteCursos.jasper", parametros, "Reporte de Cursos");
    }
    
    private Cursos buscarCurso(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_read_by_id(?)}");
            pstmt.setInt(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                curso = new Cursos(
                        rs.getInt("id"),
                        rs.getString("nombre_curso"),
                        rs.getInt("ciclo"),
                        rs.getInt("cupo_maximo"),
                        rs.getInt("cupo_minimo"),
                        rs.getString("carrera_tecnica_id"),
                        rs.getInt("horario_id"),
                        rs.getInt("instructor_id"),
                        rs.getString("salon_id")
                );
                System.out.println(curso.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return curso;
    }
    
    private CarrerasTecnicas buscarCarrerasTecnicas(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CarrerasTecnicas carrera = null;
        
        try{
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_read_by_id(?)}");
            pstmt.setString(1, id);
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                carrera = new CarrerasTecnicas(
                        rs.getString("codigo_tecnico"),
                        rs.getString("carrera"),
                        rs.getString("grado"),
                        rs.getString("seccion").charAt(0),
                        rs.getString("jornada")
                );
                System.out.println(carrera.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Carrera");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
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
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return carrera;
    }
    
    private Horarios buscarHorarios(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Horarios horario = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horario_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                horario = new Horarios(
                        rs.getInt("id"), 
                        rs.getTime("horario_inicio").toLocalTime(), 
                        rs.getTime("horario_final").toLocalTime(), 
                        rs.getBoolean("lunes"), 
                        rs.getBoolean("martes"), 
                        rs.getBoolean("miercoles"), 
                        rs.getBoolean("jueves"), 
                        rs.getBoolean("viernes")
                );

                System.out.println(horario.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Horarios");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
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

        return horario;
    }
    
    private Instructores buscarInstructor(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Instructores instructor = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                instructor = new Instructores(
                        rs.getInt("id"), 
                        rs.getString("nombre1"), 
                        rs.getString("nombre2"), 
                        rs.getString("nombre3"), 
                        rs.getString("apellido1"), 
                        rs.getString("apellido2"), 
                        rs.getString("direccion"), 
                        rs.getString("email"), 
                        rs.getString("telefono"), 
                        rs.getDate("fecha_nacimiento").toLocalDate()
                );

                System.out.println(instructor.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Instructores");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
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

        return instructor;
    }
    
     private Salones buscarSalon(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Salones salon = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                salon = new Salones(
                        rs.getString("codigo_salon"), 
                        rs.getString("descripcion"), 
                        rs.getInt("capacidad_maxima"), 
                        rs.getString("edificio"), 
                        rs.getInt("nivel")
                );

                System.out.println(salon.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Salones");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
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

        return salon;
    }    
    
    @FXML
    private void seleccionarElemento(){
        if (existeElementoSeleccionado()){
            txtId.setText(String.valueOf(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getId()));
            txtNombreCurso.setText(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getNombreCurso());
            cmbCarreraTecnica.getSelectionModel().select(buscarCarrerasTecnicas(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getCarreraTecnicaId()));
            cmbHorario.getSelectionModel().select(buscarHorarios(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getHorarioId()));
            cmbInstructor.getSelectionModel().select(buscarInstructor(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getInstructorId()));
            cmbSalon.getSelectionModel().select(buscarSalon(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getsalonId()));          
          
        }
    }
     
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
        
    @FXML
    public void clicRegresar(MouseEvent event){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    
    
       
    
}
