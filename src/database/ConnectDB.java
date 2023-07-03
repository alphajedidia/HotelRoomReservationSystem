
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ALPHA JEDIDIA
 */
public class ConnectDB {
    Connection con;
    String url = "jdbc:postgresql://localhost:5432/hotel_database";
    String user = "postgres";
    String password = "123";
    public Connection dbConnection(){
        try {
            con = DriverManager.getConnection(url, user, password);
            //JOptionPane.showMessageDialog(null , "Connecté avec la base de donnée");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null , "échec de connexion avec la base de donnée");
        } 
        return null;
    }
    
}
