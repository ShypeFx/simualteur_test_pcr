import jdk.jshell.Snippet;

import java.sql.*;

public class DataBaseConnect {
    protected static String url = "jdbc:mysql://mysql-tbeaudoin.alwaysdata.net:3306/tbeaudoin_pcr_base";
    protected static String user = "tbeaudoin";
    protected static String pass = "ESME_Projets2022";
    protected static Connection con;

    public DataBaseConnect() throws SQLException {
        this.con=DriverManager.getConnection(url,user,pass);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Check_ID("1133301");
    }

    public static String Check_ID(String num) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection(url,user,pass);
        PreparedStatement ps = con.prepareStatement("SELECT * FROM pcr_base WHERE id = ? ");
        ps.setString (1, num);
        ResultSet rs = ps.executeQuery();
        String value = "";

        if (rs.next()) {
            value = "ID EXIST";
        } else {
            value = "ID DOESNT EXIST";
        }
        System.out.println(value);
        return value;
    }


    public static void getAllValue(){
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM pcr_base");
            //étape 4: exécuter la requête
            while(res.next())
                System.out.println(res.getInt(1)+"  "+res.getString(2)
                        +"  "+res.getString(3));
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }

    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
