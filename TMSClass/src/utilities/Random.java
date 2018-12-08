/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author jperalta
 */
public class Random {
    private static java.util.Random x = new java.util.Random();
    
    public Random() {
    }
 
    public static int getInt(){
        
        int y = 0;
        
        do{
            y=x.nextInt(14);
        }while(y<4);
        
        return y; 
    }
    
    public static String getDateGMT(){
        String tempno ="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
        Calendar c1 = Calendar.getInstance();
        tempno = sdf.format(c1.getTime());
        return tempno;
    }
    
    public static void main(String args[]) {
        for(int iCtr = 0; iCtr < 1000; iCtr++) {
            System.out.println(Random.getInt());
        }
    }
    
}
