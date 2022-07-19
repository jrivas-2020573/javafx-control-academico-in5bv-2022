package org.in5bv.juanrivas.reports;

import java.net.URL;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.in5bv.juanrivas.db.Conexion;


/**
 *
 * @author informatica
 */
public class GenerarReporte {
    
    private static GenerarReporte instance;
    
    private final String PAQUETE_IMAGES = "org/in5bv/juanrivas/resources/images/";
    
    private JasperViewer jasperViewer;
    
    private GenerarReporte(){
        
        
    }
    
    public static GenerarReporte getInstance(){
        if(instance == null) {
            instance = new GenerarReporte();
        } 
        return instance;
    }
    
    public void mostrarReporte(String nombreReporte, Map<String, Object> parametros, String titulo){
        try {
            
            parametros.put("LOGO_HEADER", PAQUETE_IMAGES + "logo-control-academico1.png");
            parametros.put("LOGO_FOOTER", PAQUETE_IMAGES + "logokinal.png");
            
            URL urlFile = new URL(getClass().getResource(nombreReporte).toString());
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(urlFile);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, Conexion.getInstance().getConexion());
            
            jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle(titulo);
            jasperViewer.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
