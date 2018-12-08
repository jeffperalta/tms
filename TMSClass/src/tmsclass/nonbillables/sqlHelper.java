/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.nonbillables;
 
/**
 *
 * @author jperalta
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DatabaseConnection.*;
import beans.LogInfo;
import beans.NonBillableType;
import beans.pages.NonBillableTimeLogPage;
import tmsclass.ProjectListing.TransactionWasNotSavedException;

public class sqlHelper {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public sqlHelper() {
    
    }
    
    public void save(NonBillableTimeLogPage mySched, LogInfo myLogInfo) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "INSERT INTO a_non_billable " +
                              "   (user_id, remarks, hrs_elapsed, trans_date, " +
                              "     non_bill_type, actual_date, locked) " +
                              "VALUES(?,?,?,?,?,?,?) ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myLogInfo.getUserId());
                _MyPreparedStatement.setString(2, mySched.getRemarks());
                _MyPreparedStatement.setDouble(3, Double.parseDouble(mySched.getHoursElapsed()));
                _MyPreparedStatement.setString(4, mySched.getTransactionDate());
                _MyPreparedStatement.setInt(5, Integer.parseInt(mySched.getType()));
                _MyPreparedStatement.setString(6, utilities.DateUtility.now());
                _MyPreparedStatement.setInt(7, 1);
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
    
    public void delete(NonBillableTimeLogPage mySched) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "DELETE FROM a_non_billable " +
                              "WHERE schedule_id = ? ";
               _MyPreparedStatement = _MyConnection.prepareStatement(query);
               
               for(int iCtr = 0; iCtr < mySched.getScheduleID().length; iCtr++) {
                _MyPreparedStatement.setInt(1, Integer.parseInt(mySched.getScheduleID()[iCtr]));
                _MyPreparedStatement.executeUpdate();
               }
               
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
    
    public void edit(NonBillableType myNonBillableType)throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_non_billable_list " +
                              "SET Non_bill_name=?, " +
                              "    remarks=?, " +
                              "    status=? " +
                              "WHERE non_bill_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myNonBillableType.getNonBillableTypeName());
                _MyPreparedStatement.setString(2, myNonBillableType.getRemarks());
                _MyPreparedStatement.setInt(3, myNonBillableType.getStatus());
                _MyPreparedStatement.setInt(4, Integer.parseInt(myNonBillableType.getNonBillableTypeID()));
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
    
    public void add(NonBillableType myNonBillableType)throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "INSERT INTO a_non_billable_list " +
                              "(non_bill_name, remarks, status) " +
                              "VALUES(?,?,?) ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myNonBillableType.getNonBillableTypeName());
                _MyPreparedStatement.setString(2, myNonBillableType.getRemarks());
                _MyPreparedStatement.setInt(3, myNonBillableType.getStatus());
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
    
    public void delete(NonBillableType myNonBillableType)throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "DELETE FROM a_non_billable_list " +
                              "WHERE non_bill_id = ?";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, Integer.parseInt(myNonBillableType.getNonBillableTypeID()));
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
    
    public void editStatus(String[] myData, String UserID) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_non_billable " +
                              "SET locked=0 " +
                              "WHERE user_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, UserID);
                _MyPreparedStatement.executeUpdate();
                
                if(myData != null) {
                     query  = "UPDATE a_non_billable " +
                              "SET locked=1 " +
                              "WHERE schedule_id=? ";
                    _MyPreparedStatement = _MyConnection.prepareStatement(query);
                    
                    for(int iCtr=0; iCtr < myData.length; iCtr++) {
                        if(myData[iCtr].trim().length() == 0)  break;
                        _MyPreparedStatement.setInt(1, Integer.parseInt(myData[iCtr]));
                        _MyPreparedStatement.executeUpdate();
                    }
                }

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
