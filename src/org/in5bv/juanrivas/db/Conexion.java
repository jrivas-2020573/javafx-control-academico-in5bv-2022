package org.in5bv.juanrivas.db;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;


/**
 *
 * @author Juan Jose Rivas Alvarez
 * @date 3 may 2022
 * @time 16:59:11
 * Carné: 2020573
 * Grupo: 1
 * Codigo Tecnico: IN5BV
 */
public class Conexion {
    
    private final String IP_SERVER = "localhost";
    private final String PORT = "3306";
    private final String DB = "db_control_academico_in5bv";
    private final String USER = "kinal";
    private final String PASSWORD = "admin";
    private final String URL;
    private Connection conexion;
    
    private static Conexion instancia;
    
    public static Conexion getInstance() {
        
        if (instancia == null) {
            instancia = new Conexion();
        }
        
        return instancia;  
    }
    
    private  Conexion() {
        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB + "?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
        
        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();        
            //Class.forName("com.mysql.jdbc.Driver"); 
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //CREAMOS EL OBJETO DE LA BASE DE DATOS
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa");
            
            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion());
        /*    
        } catch (InstantiationException ex) {
            System.err.println("No se puede crear una instancia del objeto");
            ex.printStackTrace();
        } catch (IllegalAccessException ex){
            System.err.println("No se tiene los permisos para acceder al paquete");
            ex.printStackTrace();
       */
        } catch (ClassNotFoundException ex) {
            System.err.println("No se encuentra ninguna definicion para la clase");
            ex.printStackTrace();
        } catch (CommunicationsException e) { 
            System.err.println("No se pudo establecer comunicacion en el servidor del MYSQL"
                    +"Recomendación: " 
                    + "\nVerifique que el nombre del HOST o la IP_SERVER este correcta" 
                    +"\nVerifique que el numero del puerto este correcto."
                    + "\nVerifique que el servicio de MYSQL este levantado");
            e.printStackTrace();    
        } catch (SQLException ex) {
            System.err.println("\nSe produjo un error de tipo SQLException");
            System.err.println("Message: " + ex.getMessage());
            System.err.println("Error code: " + ex.getErrorCode());
            System.err.println("SQLEstate: " + ex.getSQLState());
            ex.printStackTrace();
        } catch (Exception e){
            System.err.println("La conexion a fallado");
            e.printStackTrace();
        }
    }
    
    public Connection getConexion() {
        return conexion;
    }
}
