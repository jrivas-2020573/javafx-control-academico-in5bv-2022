package org.in5bv.juanrivas.system;

import org.in5bv.juanrivas.tolchavalan.controllers.CarrerasTecnicasController;
import org.in5bv.juanrivas.tolchavalan.controllers.AlumnosController;
import org.in5bv.juanrivas.tolchavalan.controllers.SalonesController;
import org.in5bv.juanrivas.tolchavalan.controllers.HorariosController;
import org.in5bv.juanrivas.tolchavalan.controllers.MenuPrincipalController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bv.juanrivas.tolchavalan.controllers.AcercaDeController;
import org.in5bv.juanrivas.tolchavalan.controllers.AsignacionesAlumnosController;
import org.in5bv.juanrivas.tolchavalan.controllers.CursosController;
import org.in5bv.juanrivas.tolchavalan.controllers.InstructoresController;
import org.in5bv.juanrivas.tolchavalan.controllers.LoginController;

/**
 *
 * @author juan jose
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGES = "org/in5bv/juanrivas/resources/images/";
    private final String PAQUETE_VIEW = "../views/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Academico KINAL");
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGES + "logo-control-academico1.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        mostrarEscenaLogin();
    }
    
    public void mostrarAcercaDe(){
        try{
            AcercaDeController acercaController = (AcercaDeController) cambiarEscena(
                    "AcercaDe.fxml", 665, 721);
            acercaController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe a producido un error al intentar mostar la vista de Acerca De");
            
        }
    }
    
    public void mostrarEscenaCarreras() {
        try {
            CarrerasTecnicasController carrerasController = (CarrerasTecnicasController) cambiarEscena(
                    "CarrerasTecnicasView.fxml", 1280, 600);
            carrerasController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe a producido un error al intentar mostar la vista de carreras tecnicas");
        }
    }

    public void mostarSalones() {
        try {
            SalonesController salonController = (SalonesController) cambiarEscena("SalonesView.fxml", 1280, 600);
            salonController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe a producido un error al intentar mostar la vista de salones");
        }
    }

    public void mostrarEscenaAlumnos() {
        try {
            AlumnosController alumnosController = (AlumnosController) cambiarEscena("AlumnosView.fxml", 1280, 600);
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe a producido un error al intentar mostrar la vista de alumnos");
        }
    }

    public void mostrarEscenaHorarios() {
        try {
            HorariosController horarioController = (HorariosController) cambiarEscena("HorariosView.fxml", 1280, 600);
            horarioController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe a producido un error al intentar mostrar la vista de horarios");
        }
    }

    public void mostrarEscenaPrincipal() {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",
                    1280, 600);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe a producido un error al intentar mostrar la vista de menu principal");
        }
    }

    public void mostarEscenaAsignacionesAlumnos() {
        try {
            AsignacionesAlumnosController asignacionController = (AsignacionesAlumnosController) cambiarEscena(
                    "AsignacionesAlumnosView.fxml", 1280, 600);
            asignacionController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe a producido un error al intentar mostrar la vista de Asignaciones Alumnos");
        }
    }

    public void mostarEscenaCursos() {
        try {
            CursosController cursoController = (CursosController) cambiarEscena("CursosView.fxml", 1306, 586);
            cursoController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe a producido un error al intentar mostrar la vista de Cursos");
        }
    }

    public void mostrarEscenaInstructores() {
        try {
            InstructoresController instructorContoller = (InstructoresController) cambiarEscena("InstructoresView.fxml", 1431, 586);
            instructorContoller.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe a producido un error al intentar mostrar la vista de Instructores");
            e.printStackTrace();
        }
        
    }
    
    public void mostrarEscenaLogin(){
        try{
            LoginController loginController = (LoginController) cambiarEscena("LoginView.fxml", 862, 555);
            loginController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe a producido un error al intentar mostrar la vista de Login");
            e.printStackTrace();
        }
    }

    public Initializable cambiarEscena(String vistaFxml, int ancho, int altura) throws IOException {
        System.out.println(PAQUETE_VIEW + vistaFxml);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, altura);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }

    /*
     * public Initializable cambiarEscena(String vistaFxml, int ancho, int alto)
     * throws IOException {
     * System.out.println(PAQUETE_VIEW + vistaFxml);
     * Initializable resultado = null;
     * 
     * //CARGADOR DEL ARCHIVO FXML
     * FXMLLoader cargadorFXML = new FXMLLoader();
     * 
     * //CARGADOR DE FABRICA PARA CARGAR EL ARCHIVO FXML
     * cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
     * 
     * //UBICACION DEL ARCHIVO FXML QUE SE PINTARA EN EL ESCENARIO
     * cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VIEW +
     * vistaFxml));
     * 
     * //ASIGNACION DE LA LOGICA DEL ARCHIVO
     * InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VIEW +
     * vistaFxml);
     * 
     * //CREACION DEL NODO RAIZ
     * AnchorPane root = cargadorFXML.load(archivo);
     * 
     * //CREACION DE LA ESCENA
     * Scene nuevaEscena = new Scene(root, ancho, alto);
     * 
     * //CARGAR LA ESCENA EN EL ESCENARIO PRINCIPAL
     * this.escenarioPrincipal.setScene(nuevaEscena);
     * 
     * //OBTENER EL CONTROLADOR DEL ARCHIVO FXML
     * resultado = (Initializable) cargadorFXML.getController();
     * 
     * //DEOLVER EL CONTROLADOR
     * return resultado;
     * }
     */

}
