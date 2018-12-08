/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.Client;
  
/**
 *
 * @author Abree
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import DatabaseConnection.*;
import beans.Client;
import beans.pages.EditClientPage;
import tmsclass.ProjectListing.TransactionWasNotSavedException;

public class sqlHelper {
    
    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;

    public sqlHelper() {
    }
    
    public void edit(Client myClient) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_client " +
                              "SET c_name=?, c_address=?, c_contact=?, " +
                              "    c_fax=?, c_contact_person=?, comment=? " +
                              "WHERE client_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myClient.getName());
                _MyPreparedStatement.setString(2, myClient.getAddress());
                _MyPreparedStatement.setString(3, myClient.getContact());
                _MyPreparedStatement.setString(4, myClient.getFax());
                _MyPreparedStatement.setString(5, myClient.getContactPerson());
                _MyPreparedStatement.setString(6, myClient.getComment());
                _MyPreparedStatement.setString(7, myClient.getClientId());
                _MyPreparedStatement.executeUpdate();
                
                _MyConnection.commit();
            }

        }catch(Exception e){
            System.out.println(e.toString());

            try{
                _MyConnection.rollback();
            }catch (Exception e2){
                System.out.println(e2.toString());
            }finally {
                hasError = true;
            }

        }finally{

            try{

                if(_MyResultSet != null){
                    _MyResultSet.close();
                }

                if(_MyStatement != null) {
                    _MyStatement.close();
                }

                if(_MyPreparedStatement != null) {
                    _MyPreparedStatement.close();
                }

                if(_MyConnection != null) {
                    _MyConnection.close();
                } 

            }catch(Exception e){
                System.out.println(e.toString());
            }

        }
        
        if(hasError) {
            throw new TransactionWasNotSavedException();
        }
    }
    
    public void delete(Client myClient) throws TransactionWasNotSavedException {
    
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "DELETE " +
                              "FROM a_client " +
                              "WHERE client_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myClient.getClientId());
                _MyPreparedStatement.executeUpdate();
                
                _MyConnection.commit();
            }

        }catch(Exception e){
            System.out.println(e.toString());

            try{
                _MyConnection.rollback();
            }catch (Exception e2){
                System.out.println(e2.toString());
            }finally {
                hasError = true;
            }

        }finally{

            try{

                if(_MyResultSet != null){
                    _MyResultSet.close();
                }

                if(_MyStatement != null) {
                    _MyStatement.close();
                }

                if(_MyPreparedStatement != null) {
                    _MyPreparedStatement.close();
                }

                if(_MyConnection != null) {
                    _MyConnection.close();
                } 

            }catch(Exception e){
                System.out.println(e.toString());
            }

        }
        
        if(hasError) {
            throw new TransactionWasNotSavedException();
        }
        
    }
    
    public void add(Client myClient) throws TransactionWasNotSavedException {
        
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "INSERT INTO a_client " +
                              "(client_id, c_name, c_address, " +
                              " c_contact, c_fax, c_contact_person, " +
                              " comment) " +
                              "VALUES(?,?,?,?,?,?,?)";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myClient.getClientId());
                _MyPreparedStatement.setString(2, myClient.getName());
                _MyPreparedStatement.setString(3, myClient.getAddress());
                _MyPreparedStatement.setString(4, myClient.getContact());
                _MyPreparedStatement.setString(5, myClient.getFax());
                _MyPreparedStatement.setString(6, myClient.getContactPerson());
                _MyPreparedStatement.setString(7, myClient.getComment());
                _MyPreparedStatement.executeUpdate();
                
                _MyConnection.commit();
            }

        }catch(Exception e){
            System.out.println(e.toString());

            try{
                _MyConnection.rollback();
            }catch (Exception e2){
                System.out.println(e2.toString());
            }finally {
                hasError = true;
            }

        }finally{

            try{

                if(_MyResultSet != null){
                    _MyResultSet.close();
                }

                if(_MyStatement != null) {
                    _MyStatement.close();
                }

                if(_MyPreparedStatement != null) {
                    _MyPreparedStatement.close();
                }

                if(_MyConnection != null) {
                    _MyConnection.close();
                } 

            }catch(Exception e){
                System.out.println(e.toString());
            }

        }
        
        if(hasError) {
            throw new TransactionWasNotSavedException();
        }
        
    }
    
}
