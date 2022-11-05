import jdk.jshell.Snippet;

import java.sql.*;

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



    public static String Check_ID(String num) throws SQLException, ClassNotFoundException {
        String value = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,user,pass);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM pcr_base WHERE id = ? ");
            ps.setString (1, num);
            ResultSet rs = ps.executeQuery();
            // Check the response of the SQL REQUEST
            if (rs.next()) {
                value = num + " = ID EXIST";
            } else {
                value = num + " = ID DOESNT EXIST";
            }
            System.out.println(value);
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

}