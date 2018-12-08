/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package message;
 
/**
 *
 * @author jperalta
 */
  
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import DatabaseConnection.*;

public class ErrorMessage {

    private  String _ErrorNumber = " ";
    private  String _ErrorMessage = " ";
    private  boolean _WithError = false;
    private  ArrayList _MyArrayList = null;
    
    private  Connection _MyConnection = null;
    private  ResultSet _MyResultSet = null;
    private  Statement _MyStatement = null;
    private  PreparedStatement _MyPreparedStatement = null;
    private int _MessageType = MessageType.MESSAGE_TYPE_ERROR;
    
    public ErrorMessage(){
    
    }
    
    public void setMessageType(int value) {
        _MessageType = value;
    }
    
    /**
     * Set Message Type First To Non Error to confirm actions
     * @param value
     */
    public  void setMessageNumber(String value){
        
        if(_MessageType == MessageType.MESSAGE_TYPE_ERROR) {
            _WithError = true;
        }else{
            _WithError = false;
        }
        
        _ErrorNumber = value;
        _ErrorMessage = " ";
        _MyArrayList = new ArrayList();
        setErrorMessage();
    }
    
   
    public  String getMessage(){
        String myResult = " ";
        
        if(_WithError || _MessageType != MessageType.MESSAGE_TYPE_ERROR){
            
            if(_MyArrayList.size() > 0) {
                replaceParameter();
            }
            
            //Message was already displayed
            _WithError = false;
           
            myResult =  "<script>alert('" + _ErrorMessage + "');</script>";
            
            //return to default
            _MessageType = MessageType.MESSAGE_TYPE_ERROR;
            
        }
        
        return myResult;    
    }
    
    
    public  boolean hasError(){
        return _WithError;
    }
    
    public  void addParameter(String value){
        _MyArrayList.add(value);
    }
    
    public  void clearParameter(){
        _MyArrayList.clear();
    }
    
    
    
    
    private  void replaceParameter(){
        for(int iCtr = 0; iCtr < _MyArrayList.size(); iCtr++){
            if(_ErrorMessage.contains("%"+(iCtr+1))){
                _ErrorMessage = _ErrorMessage.replaceAll("%"+(iCtr+1), (_MyArrayList.get(iCtr)).toString());
            }
        }
    }
    
    private  void setErrorMessage(){
        
        try{
             tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            String query = "SELECT error_message " +
                           "FROM c_messages " +
                           "WHERE error_number=? ";
            
            _MyPreparedStatement = _MyConnection.prepareStatement(query);
            _MyPreparedStatement.setString(1, _ErrorNumber);
            
            _MyResultSet = _MyPreparedStatement.executeQuery();
            _MyResultSet.next();
            _ErrorMessage = _MyResultSet.getString(1);
            
        }catch(Exception e){
            System.out.println(e.toString());
        }finally{
        
            try{
                
                if(_MyResultSet != null){
                    _MyResultSet.close();
                }
                
                if(_MyStatement != null){
                    _MyStatement.close();
                }
                
                if(_MyPreparedStatement != null) {
                    _MyPreparedStatement.close();
                }
                
                if(_MyConnection != null){
                    _MyConnection.close();
                }
                
            }catch(Exception e){
                System.out.println(e.toString());
            }
            
        }
    
    }
    
    public static void main(String[] args) {
        ErrorMessage x = new ErrorMessage();
        x.setMessageNumber("10000");
        x.addParameter("Testing Field");
        System.out.println(x.getMessage());
    }
    
}
