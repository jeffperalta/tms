/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;
 
/**
 *
 * @author jperalta
 */
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

public class DateUtility {

    public DateUtility(){
     
    }
   
    /**
     * Converts a Date String value to Date 
     * The Result is based on the Default Date Format
     * @param DateString
     * @param MyFormat
     * @return
     * @throws java.text.ParseException
     */
    public static Date ToDate(String DateString, String MyFormat) throws ParseException{
        DateFormat _MyDf = new SimpleDateFormat(MyFormat);
        Date _MyDate = _MyDf.parse(DateString);
        return _MyDate;
    }
    
    /**
     * Return a value 0 if the the two dates are equal; 
     * Return a value less than zero if the first date is before the second date argument; 
     * Return a value greater than zero if the first date is after the second date argument;
     * @param Date1
     * @param Date2
     * @param Format
     * @return
     * @throws java.text.ParseException
     */
    public static int DateCompare(String Date1, String Date2, String Format) throws ParseException{
        Date myDate1 = ToDate(Date1, Format);
        return myDate1.compareTo(ToDate(Date2, Format));
    }
    
    /**
     * Returns the difference between the two dates in days
     * @param Date1
     * @param Date2
     * @return
     */
    public static long DateDifference(String Date1, String Date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Integer.parseInt(getYear(Date1)), Integer.parseInt(getPeriod(Date1))-1, Integer.parseInt(getDay(Date1)));
        calendar2.set(Integer.parseInt(getYear(Date2)), Integer.parseInt(getPeriod(Date2))-1, Integer.parseInt(getDay(Date2)));
        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        
        return diffDays;
    }

    /**
     * Add days to a given date string and returns the resulting date in the predefined date format
     * @param myDate
     * @param DayToAdd
     * @return
     */
    public static String addDays(String myDate, int DayToAdd) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(constants.DateFormat.DATE_FORMAT);
        Calendar Calendar1 = Calendar.getInstance(); 
        Calendar1.set(Integer.parseInt(getYear(myDate)), Integer.parseInt(getPeriod(myDate))-1, Integer.parseInt(getDay(myDate)));
        Calendar1.add(Calendar.DATE, DayToAdd);
        return sdf.format(Calendar1.getTime());
    }
    
    /**
     * Add months to a given date string and returns the resulting date in the predefined date format
     * @param myDate
     * @param MonthToAdd
     * @return
     */
    public static String addMonths(String myDate, int MonthToAdd) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(constants.DateFormat.DATE_FORMAT);
        Calendar Calendar1 = Calendar.getInstance(); 
        Calendar1.set(Integer.parseInt(getYear(myDate)), Integer.parseInt(getPeriod(myDate))-1, Integer.parseInt(getDay(myDate)));
        Calendar1.add(Calendar.MONTH, MonthToAdd);
        return sdf.format(Calendar1.getTime());
    }
    
    /**
     * Returns the Current Date String Based on the FormatString Parameter
     * @param FormatString: eg. yyyy-MM-dd hh:mm:ss (2008-05-19 01:30:59)
     * @return
     */
    public static String now(String FormatString){
        Calendar _MyCalendar = Calendar.getInstance();
        SimpleDateFormat _MyFormat = new SimpleDateFormat(FormatString);
        return _MyFormat.format(_MyCalendar.getTime());
    }
    
    /**
     * Returns the Current Date Using the Default Date Format
     * @return
     */
    public static String now(){
        return now(constants.DateFormat.DATE_FORMAT);
    }
    
    /**
     * Changes the Date Representation
     * @param MyDate
     * @param NewFormat
     * @return
     * @throws java.text.ParseException
     */
    public static String FormatDate(String MyDate, String OldFormat, String NewFormat ) throws Exception{
       
        String myResult="";
        
        DateFormat DF  = new SimpleDateFormat(OldFormat);
        Date tempDate = DF.parse(MyDate);
        SimpleDateFormat SDF = new SimpleDateFormat(NewFormat);
        myResult = SDF.format(tempDate);
        
        return myResult;
        
    }
    
    public static String getYear(String Date){
        return ParseDate(Date, "yyyy");
    }
    
    public static String getPeriod(String Date){
        return ParseDate(Date, "MM");
    }
    
    public static String getDay(String Date){
        return ParseDate(Date, "dd");
    }
    
    /**
     * Converts the Month Integer Value to Words
     * @param monthNumber
     * @return
     */
    public String getMonthName(Integer monthNumber){   
        String month = constants.DateFormat.FISCAL_MONTH[monthNumber-1];
        return month;
    }
    
    public String getLastDay(Integer month, Integer yr){
        String lastday = " ";
        switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    lastday = "31";
                    break;
                    
                case 4:
                case 6:
                case 9:
                case 11:
                    lastday = "30";
                    break;
                    
                case 2:
                    if ( ((yr % 4 == 0) && !(yr % 100 == 0)) || (yr % 400 == 0) )
                        lastday = "29";
                    else
                        lastday = "28";
                    break;                       
            }
        
        return lastday;
        
    }
    
    /**
     * Determines the Last Day of the Month
     * @param month
     * @param yr
     * @return
     */
    public String getEndDate(Integer month, Integer yr) {
            String lastday = getLastDay(month, yr);
            String enddate = " ";  
            
            enddate = yr + constants.DateFormat.DATE_SEPARATOR + 
                      month + constants.DateFormat.DATE_SEPARATOR + 
                      lastday;
            
            try {
                enddate = FormatDate(enddate, "yyyy" + constants.DateFormat.DATE_SEPARATOR + "MM" + constants.DateFormat.DATE_SEPARATOR + "dd", constants.DateFormat.DATE_FORMAT);
            }catch(Exception e){
                System.out.println(e.toString());
            }
            
            return enddate;
        }
     
    public boolean isProperDate(String value){
        boolean myResult = false;
        
        try{
            DateFormat DF  = new SimpleDateFormat(constants.DateFormat.DATE_FORMAT);
            Date tempDate = DF.parse(value);
            SimpleDateFormat SDF = new SimpleDateFormat("yyyy");
            int intYear = Integer.parseInt(SDF.format(tempDate));
            
            SDF = new SimpleDateFormat("MM");
            int intMonth = Integer.parseInt(SDF.format(tempDate));
            
            SDF = new SimpleDateFormat("dd");
            int intDays = Integer.parseInt(SDF.format(tempDate));
            
            String[] temp = value.split(constants.DateFormat.DATE_SEPARATOR);
            
            if((constants.DateFormat.DATE_FIRST.compareToIgnoreCase("DATE") == 0 && intDays ==Integer.parseInt(temp[0])) ||
                    (constants.DateFormat.DATE_SECOND.compareToIgnoreCase("DATE") == 0 && intDays ==Integer.parseInt(temp[1])) ||
                    (constants.DateFormat.DATE_THIRD.compareToIgnoreCase("DATE") == 0 && intDays ==Integer.parseInt(temp[2])))
              if((constants.DateFormat.DATE_FIRST.compareToIgnoreCase("MONTH") == 0 && intMonth ==Integer.parseInt(temp[0])) ||
                        (constants.DateFormat.DATE_SECOND.compareToIgnoreCase("MONTH") == 0 && intMonth ==Integer.parseInt(temp[1])) ||
                        (constants.DateFormat.DATE_THIRD.compareToIgnoreCase("MONTH") == 0 && intMonth ==Integer.parseInt(temp[2])))
                if((constants.DateFormat.DATE_FIRST.compareToIgnoreCase("YEAR") == 0 && intYear ==Integer.parseInt(temp[0])) ||
                          (constants.DateFormat.DATE_SECOND.compareToIgnoreCase("YEAR") == 0 && intYear ==Integer.parseInt(temp[1])) ||
                          (constants.DateFormat.DATE_THIRD.compareToIgnoreCase("YEAR") == 0 && intYear ==Integer.parseInt(temp[2])))
                    myResult=true;
       
            
        }catch(Exception e){
            myResult = false;
        }
        
        return myResult;
    }
    
     /**
     * Retrieves the date element
     * @param Date -> The Transaction Date
     * @param GetElement -> MM for getting the Month Integer Value
     *                   -> MMMM for getting the Month Name
     *                   -> dd for the days
     *                   -> yyyy for the year
     * @param DateFormat -> Format of date
     * @return
     */
    public static String ParseDate(String Date, String GetElement, String DateFormat){
        String myResult = "";
        
        try {
            
            DateFormat DF  = new SimpleDateFormat(DateFormat);
            Date tempDate = DF.parse(Date);
            SimpleDateFormat SDF = new SimpleDateFormat(GetElement);
            myResult = SDF.format(tempDate);
             
        } catch (Exception e){
            System.out.println(e.toString());
        }
 
        return myResult;
    }
    
     /**
     * Retrieves the date element using the default date format
     * @param Date -> The Transaction Date
     * @param GetElement -> MM for getting the Month Integer Value
     *                   -> MMMM for getting the Month Name
     *                   -> dd for the days
     *                   -> yyyy for the year
     * @return
     */
    public static String ParseDate(String Date, String GetElement){
        return ParseDate(Date, GetElement, constants.DateFormat.DATE_FORMAT);
    }
    
    public static void main(String args[]) {
        System.out.println(DateUtility.DateDifference("2008-02-01", "2008-03-01"));
        System.out.println(DateUtility.addDays(DateUtility.addDays("2008-05-19", 1), 1));
        System.out.println(DateUtility.addMonths("2008-05-19", 1));
        
        Calendar y = Calendar.getInstance();
        y.set(2000, 01, 8);
        System.out.println("MAX: " + y.getActualMaximum(Calendar.DATE));
    }
    
}
