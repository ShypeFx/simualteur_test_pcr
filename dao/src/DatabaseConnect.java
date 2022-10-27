import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnect {
    protected static String url = "jdbc:mysql://mysql-tbeaudoin.alwaysdata.net:3306/tbeaudoin_pcr_base";
    protected static String user = "tbeaudoin";
    protected static String pass = "ESME_Projets2022";


    public static void main(String[] args) {
        getAllValue();
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
}