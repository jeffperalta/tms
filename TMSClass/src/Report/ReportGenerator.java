/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Report;
 
/**
 *
 * @author jperalta
 */
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import DatabaseConnection.*;
import com.mysql.jdbc.ResultSetMetaData;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class ReportGenerator {

    private ReportData _MyReportData = null;
    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;
    
    private JasperReport _JasperReport;
    private JasperPrint _JasperPrint;
    private JRResultSetDataSource _JRDataSource;

    public ReportGenerator() {
    }

    public ReportGenerator(ReportData myData) {
        _MyReportData = myData;
    }
    
    /**
     * Create PDF File
     */
    public void dataOut() {
        try {
            
            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();
            _MyStatement = _MyConnection.createStatement();
            _MyResultSet = _MyStatement.executeQuery(_MyReportData.getSqlText());

            _JRDataSource = new JRResultSetDataSource(_MyResultSet);
            _JasperReport = JasperCompileManager.compileReport(_MyReportData.getJRXMLPath());
            _JasperPrint = JasperFillManager.fillReport(_JasperReport, new HashMap(), _JRDataSource);
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                
                if(_MyResultSet != null) {
                    _MyResultSet.close();
                }
                
                if(_MyStatement != null) {
                    _MyStatement.close();
                }
                
                if(_MyConnection != null) {
                    _MyConnection.close();
                }
                
            }catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public void genPdfReport() {
        try {
            String ReportFilePath = _MyReportData.getReportLocation() +  _MyReportData.getReportName() + ".pdf";
            JasperExportManager.exportReportToPdfFile(_JasperPrint, ReportFilePath);
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    
    public  void generateXLSOutput() {
        try {
            String ReportFilePath = _MyReportData.getReportLocation() +  _MyReportData.getReportName() + ".xls";
            FileOutputStream fos = new  FileOutputStream(ReportFilePath);
            JExcelApiExporter xls = new JExcelApiExporter();
            xls.setParameter(JRXlsExporterParameter.JASPER_PRINT, _JasperPrint);
            xls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            xls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
            xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            xls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, fos);
            xls.exportReport();
            fos.close();
        } catch (Exception e) {
            System.out.println(""+e);
        }
    }
    
    public void generateXLS() {
        
        String content="";
        try{
            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();
            
            _MyStatement =_MyConnection.createStatement();
            _MyResultSet = _MyStatement.executeQuery(_MyReportData.getSqlText());
            
            //Print Column
            ResultSetMetaData rsm = (ResultSetMetaData) _MyResultSet.getMetaData();
            int column_count = rsm.getColumnCount();
            for (int i = 1; i < column_count + 1; i++) {
                content += rsm.getColumnName(i) + "\t";
            }
            
            content += "\n";
            
            //Print Detail
            //rs.beforeFirst(); // just try if this is faster
            while (_MyResultSet.next()) {
                for (int i = 1; i < column_count + 1; i++) {
                    content += _MyResultSet.getString(i) + "\t";
                }
                content += "\n";
            }
            
            FileWriter excel = new FileWriter(_MyReportData.getReportLocation() +  _MyReportData.getReportName() + ".xls");
            excel.write(content);
            excel.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        
            try{
                if(_MyResultSet != null) {
                    _MyResultSet.close();
                }
                
                if(_MyStatement != null) {
                    _MyStatement.close();
                }
                
                if(_MyConnection != null) {
                    _MyConnection.close();
                }
                
            }catch(Exception e) {
                System.out.println(e.toString());
            }
            
        }
        
    }

}
