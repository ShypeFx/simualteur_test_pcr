import jdk.jshell.Snippet;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Calendar;

public class DatabaseConnect {
    protected static String url = "jdbc:mysql://mysql-tbeaudoin.alwaysdata.net:3306/tbeaudoin_pcr_base";
    protected static String user = "tbeaudoin";
    protected static String pass = "ESME_Projets2022";
    protected static Connection con;

    public DatabaseConnect(String url, String user, String pass) throws SQLException {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public DatabaseConnect (){
    }

    public static void main(String[] args) throws ParseException {
        Check_Validity_Date("1791242",3);
    }


    public static Boolean Check_ID(String num) throws SQLException, ClassNotFoundException {
        Boolean value = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,user,pass);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM pcr_base WHERE id = ? ");
            ps.setString (1, num);
            ResultSet rs = ps.executeQuery();
            // Check the response of the SQL REQUEST
            if (rs.next()) {
                value = true;
            } else {
                value = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Boolean Check_Status(String num) throws SQLException, ClassNotFoundException {
        Boolean value = null;
        String stat = "NEGATIVE";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = con.prepareStatement("SELECT status FROM pcr_base WHERE id = ? and status = ? ");
            ps.setString(1, num);
            ps.setString(2, stat);
            ResultSet rs = ps.executeQuery();
            // Check the response of the SQL REQUEST
            if (rs.next()) {
                value = true;
            } else {
                value = false;
            }
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    public static Boolean Check_Validity_Date(String num, int time_validity) throws ParseException {
        // Current Date
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date current_date = getCurentDate();
        System.out.println(" current_date = "+formatter.format(current_date));

        // Date of the PCR TEST
        Date date_pcr_test = getDate(num);
        System.out.println(" date_pcr_test = " + date_pcr_test);

        // convert calendar to date
        Date date_plus_validity = getDatePlusValidity(date_pcr_test,time_validity);
        System.out.println(" date_plus_validity = "+formatter.format(date_plus_validity));

        // Check if the date of the PCR TEST is before or after the max date validity
        if(current_date.before(date_plus_validity) || current_date.compareTo(date_plus_validity) == 0){
            System.out.println("true");
            return true;
        }else{
            System.out.println("false");
            return false;
        }

    }

    // ************************************ GET DATA FROM DATA BASE *************************************************

    // Get delivery date of a PCR TEST
    public static Date getDate(String num){
        Date value = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,user,pass);
            PreparedStatement ps = con.prepareStatement("SELECT date FROM pcr_base WHERE id = ? ");
            ps.setString(1, num);
            ResultSet rs = ps.executeQuery();
            // Check the response of the SQL REQUEST
            if (rs.next()) {
                value = rs.getDate(1);
            } else {
                value = null;
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }



    public static void getAllValue(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM pcr_base");
            //étape 4: exécuter la requête
            System.out.println("---------- DATA BASE ----------");
            while(res.next())
                System.out.println(res.getInt(1)+"  "+res.getString(2) +"  "+res.getString(3));
            System.out.println("-------------------------------");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStatus(String num){
        String value = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,user,pass);
            PreparedStatement ps = con.prepareStatement("SELECT status FROM pcr_base WHERE id = ? ");
            ps.setString(1, num);
            ResultSet rs = ps.executeQuery();
            // Check the response of the SQL REQUEST
            if(rs.next()){
                value = rs.getString(1);
            }else{
                value = null;
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Date getDatePlusValidity(Date d, int validity){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, validity);
        return (Date) c.getTime();
    }

    public static String getDatePlusValidityFormater(Date d, int validity){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date current_date = getDatePlusValidity(d,validity);
        return formatter.format(current_date);
    }


    public static Date getCurentDate(){
        Date current_date = new Date(System.currentTimeMillis());
        return current_date;
    }

    public static String getCurentDateFormater(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date current_date = getCurentDate();
        return formatter.format(current_date);
    }

}