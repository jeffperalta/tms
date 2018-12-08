/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.Project;
  
/**
 *
 * @author jperalta
 */

import DatabaseConnection.tmsConnection;
import beans.Project;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
 
public class sqlHelper {
    
    private Connection _MyConnection = null;
    private ResultSet _MyResultSet = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;

    public sqlHelper() {
    }
    
    public void edit(Project myProject) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "UPDATE a_project " +
                              "SET project_name=?, start_date=?, " +
                              "    end_date=?, client_id=?, project_amount=?, " +
                              "    remarks=? " +
                              "WHERE project_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myProject.getProjectName());
                _MyPreparedStatement.setString(2, myProject.getStartDate());
                _MyPreparedStatement.setString(3, myProject.getEndDate());
                _MyPreparedStatement.setString(4, myProject.getClientID());
                _MyPreparedStatement.setDouble(5, myProject.getProjectAmount());
                _MyPreparedStatement.setString(6, myProject.getRemarks());
                _MyPreparedStatement.setString(7, myProject.getProjectID());
                _MyPreparedStatement.executeUpdate();
                
                query = "UPDATE a_activity " +
                        "SET activity_name=?,  " +
                        " remarks=? " +
                        "WHERE activity_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myProject.getProjectName());
                _MyPreparedStatement.setString(2, myProject.getRemarks());
                _MyPreparedStatement.setString(3, myProject.getProjectID());
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
    
    public void add(Project myProject) throws TransactionWasNotSavedException{
        
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "INSERT INTO a_project " +
                              "(project_id, project_name, start_date, " +
                              " end_date, client_id, project_amount, " +
                              " remarks) " +
                              "VALUES(?,?,?,?,?,?,?)";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myProject.getProjectID());
                _MyPreparedStatement.setString(2, myProject.getProjectName());
                _MyPreparedStatement.setString(3, myProject.getStartDate());
                _MyPreparedStatement.setString(4, myProject.getEndDate());
                _MyPreparedStatement.setString(5, myProject.getClientID());
                _MyPreparedStatement.setDouble(6, myProject.getProjectAmount());
                _MyPreparedStatement.setString(7, myProject.getRemarks());
                _MyPreparedStatement.executeUpdate();
                
                query = "INSERT INTO a_activity " +
                        "(activity_id, activity_name, a_type, status, " +
                        " remarks, project_id) " +
                        "VALUES (?,?,?,?,?,?) ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myProject.getProjectID());
                _MyPreparedStatement.setString(2, myProject.getProjectName());
                _MyPreparedStatement.setString(3, myProject.getProjectType());
                _MyPreparedStatement.setInt(4, 1); //status is ONGOING
                _MyPreparedStatement.setString(5, myProject.getRemarks());
                _MyPreparedStatement.setString(6, myProject.getProjectID());
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
    
    public void delete(Project myProject) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "DELETE " +
                              "FROM a_project " +
                              "WHERE project_id=?";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myProject.getProjectID());
                _MyPreparedStatement.executeUpdate();
                
                query = "DELETE " +
                        "FROM a_activity " +
                        "WHERE activity_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myProject.getProjectID());
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
    
    public void changeStatus(int iStatus, Project myProject) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "UPDATE a_activity " +
                              "SET status=? " +
                              "WHERE activity_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, iStatus);
                _MyPreparedStatement.setString(2, myProject.getProjectID());
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
