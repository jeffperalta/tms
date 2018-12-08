/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.WorkUtility;

/**
 *
 * @author Abree
 */
 
import DatabaseConnection.tmsConnection;
import beans.WorkUtility;
import beans.pages.WorkUtilityPage;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import tmsclass.ProjectListing.TransactionWasNotSavedException;

public class sqlHelper {
    
    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;

    public sqlHelper() {
    }
    
    public void edit(WorkUtility myBean) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_work_days " +
                              "SET DLofSubmission=?, " +
                              "    DaysAllowed=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myBean.getDeadline());
                _MyPreparedStatement.setInt(2, myBean.getAllowedDays());
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
     
    public String updateDateLine() throws TransactionWasNotSavedException {
        String myResult = " ";
        
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_work_days " +
                              "SET DLOfSubmission = DATE_ADD(DLofSubmission,INTERVAL DaysAllowed  DAY) " +
                              "WHERE DATE_ADD(DLOfSubmission, INTERVAL 1 DAY) < NOW() ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
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
        }else{
            return myResult;
        }
    }
    
    public void addUpdate(WorkUtilityPage myMessage, String CreatedBy) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "INSERT INTO c_updates " +
                              "(DateCreated, myTitle, CreatedBy, myBody, show_this) " +
                              "VALUES(?,?,?,?,?) ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, utilities.DateUtility.now());
                _MyPreparedStatement.setString(2, myMessage.getMessageTitle());
                _MyPreparedStatement.setString(3, CreatedBy);
                _MyPreparedStatement.setString(4, myMessage.getMessageBody());
                _MyPreparedStatement.setInt(5, 1);
                
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
    
    public void editUpdate(WorkUtilityPage myMessage) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE c_updates " +
                              "SET myTitle = ?, myBody=?, show_this=? " +
                              "WHERE id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myMessage.getMessageTitle());
                _MyPreparedStatement.setString(2, myMessage.getMessageBody());
                _MyPreparedStatement.setInt(3, Integer.parseInt(myMessage.getVisible()));
                _MyPreparedStatement.setInt(4, Integer.parseInt(myMessage.getMessageNumber()));
                
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
    
    public void deleteUpdate(WorkUtilityPage myMessage) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "DELETE FROM c_updates " +
                              "WHERE id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, Integer.parseInt(myMessage.getMessageNumber()));
                
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
