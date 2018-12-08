/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;
 
/**
 *
 * @author jperalta
 */

import java.text.NumberFormat;
import java.text.DecimalFormat;

public class Formatter {

    public Formatter(){
     
    }
     
     private static NumberFormat _Formatter;
    
    public static String toCurrency(double money){
        _Formatter = new DecimalFormat(",##0.00");
        return _Formatter.format(money);
    }
    
    public static String toDouble(double money){
        _Formatter = new DecimalFormat(",##0.00");
        return _Formatter.format(money);
    }
    
    public static String toProperString(String Entry){
        for(int iCtr = 0; iCtr < constants.TextFormat.FORBIDDEN_CHARACTERS.length(); iCtr++) {
            if(Entry.contains(constants.TextFormat.FORBIDDEN_CHARACTERS.substring(iCtr, iCtr+1))) {
                Entry = Entry.replaceAll(constants.TextFormat.FORBIDDEN_CHARACTERS.substring(iCtr, iCtr+1), 
                                         " ");
            }
        }
        return Entry;
    }
    
    public static String toProperSqlWhereClause(String Entry) {
        for(int iCtr = 0; iCtr < constants.TextFormat.FORBIDDEN_SQL_CHARACTERS_IN_WHERE_CLAUSE.length(); iCtr++) {
            if(Entry.contains(constants.TextFormat.FORBIDDEN_SQL_CHARACTERS_IN_WHERE_CLAUSE.substring(iCtr, iCtr+1))) {
                Entry = Entry.replaceAll(constants.TextFormat.FORBIDDEN_SQL_CHARACTERS_IN_WHERE_CLAUSE.substring(iCtr, iCtr+1), 
                                         " ");
            }
        }
        return Entry;
    }
    
    /**
     * Removes Commas(,)
     * @param Entry
     * @return
     */
    public static double toProperNumber(String Entry){
        return Double.parseDouble(Entry.replaceAll(",", ""));
    }
    
    public static boolean isNumber(String Entry) {
        Entry = Entry.replaceAll(",", "");
        
        boolean myResult = false;
        try{
            double Temp = Double.parseDouble(Entry);
            myResult = true;
        }catch(NumberFormatException e){
            myResult = false;
        }
        return myResult;
    }
    
    public static boolean isInteger(String Entry) {
        boolean myResult = true;
        
        if(isNumber(Entry)) {
            if(Entry.contains(".")) {
                myResult = false;
            }
        }else {
            myResult = false;
        }
        
        return myResult;
    }
    
}
