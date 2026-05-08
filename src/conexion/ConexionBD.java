package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/inventario";

    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    public static Connection conectar() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Conectado a MySQL");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return con;
    }
}