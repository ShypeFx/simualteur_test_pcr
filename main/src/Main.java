import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("Hello world!");

        DataBaseConnect db = new DataBaseConnect();
        db.Check_ID("000000");

    }
}