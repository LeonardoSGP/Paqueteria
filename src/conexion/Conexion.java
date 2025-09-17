package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Conexión local con parámetros SSL
    public static Connection conectar() {
        try {
            // Añade useSSL=false para entornos de desarrollo/pruebas
            String url = "jdbc:mysql://localhost:3306/sistema_envios?allowPublicKeyRetrieval=true&useSSL=false";            
            Connection cn = DriverManager.getConnection(url, "root", "matrix469");
            return cn;
        } catch (SQLException e) {
            System.out.println("Error en la conexión local: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}