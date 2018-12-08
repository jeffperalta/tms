/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package tmsclass.Activity;
 
/**
 *
 * @author Abree
 */

import DatabaseConnection.tmsConnection;
import beans.Activity;
import beans.pages.DefaultActivityPage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import tmsclass.ProjectListing.TransactionWasNotSavedException;

public class sqlHelper {

    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;

    public sqlHelper() {
    }
    
    public void edit(Activity myActivity) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_activity " +
                              "SET activity_name=?, " +
                              "    a_type=?, " +
                              "    status=?, " +
                              "    remarks=?, " +
                              "    project_id=? " +
                              "WHERE activity_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myActivity.getActivityName());
                _MyPreparedStatement.setString(2, myActivity.getActivityType());
                _MyPreparedStatement.setInt(3, myActivity.getActivityStatus());
                _MyPreparedStatement.setString(4, myActivity.getRemarks());
                _MyPreparedStatement.setString(5, myActivity.getProjectID());
                _MyPreparedStatement.setString(6, myActivity.getActivityID());
                
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
    
    public void delete(Activity myActivity) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "DELETE " +
                              "FROM a_activity " +
                              "WHERE activity_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myActivity.getActivityID());
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
    
    public void add(Activity myActivity) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "INSERT INTO a_activity " +
                              "(activity_id, activity_name, a_type, status, remarks, project_id) " +
                              "VALUES (?,?,?,?,?,?) ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myActivity.getActivityID());
                _MyPreparedStatement.setString(2, myActivity.getActivityName());
                _MyPreparedStatement.setString(3, myActivity.getActivityType());
                _MyPreparedStatement.setInt(4, myActivity.getActivityStatus());
                _MyPreparedStatement.setString(5, myActivity.getRemarks());
                _MyPreparedStatement.setString(6, myActivity.getProjectID());
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
    
    public void add(DefaultActivityPage myPage) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "INSERT INTO a_def_activity " +
                              "(act_id, act_name, dept_id, status) " +
                              "VALUES (?,?,?,?) ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getActivityID());
                _MyPreparedStatement.setString(2, myPage.getActivityName());
                _MyPreparedStatement.setString(3, myPage.getDepartmentID());
                _MyPreparedStatement.setInt(4, 1);
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
    
    public void edit(DefaultActivityPage myPage) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_def_activity " +
                              "SET act_name=?, dept_id=?, status=? " +
                              "WHERE act_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getActivityName());
                _MyPreparedStatement.setString(2, myPage.getDepartmentID());
                _MyPreparedStatement.setInt(3, Integer.parseInt(myPage.getActivityStatus()));
                _MyPreparedStatement.setString(4, myPage.getActivityID());
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
    
    public void delete(DefaultActivityPage myPage) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "DELETE FROM a_def_activity " +
                              "WHERE act_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getActivityID());
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

