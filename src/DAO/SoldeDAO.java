
package DAO;

import Modele.Solde;
import database.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ALPHA JEDIDIA
 */
public class SoldeDAO {
    Connection con;
    ConnectDB cn = new ConnectDB();
    PreparedStatement requeteSql;
    ResultSet resultat;
    
    
    public boolean Enregistrer(Solde m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "INSERT INTO solde (idReserve,soldeactuel) VALUES (?,((SELECT MAX(soldeActuel) FROM solde )+ (Select (c.prixNuit*r.nbrJour) from chambre c,reserver r where r.idReserve = ? and r.numChambr = c.numChambr)))";
            requeteSql = con.prepareStatement(sql);
            System.out.print(m.getIdReserve());
            requeteSql.setInt(1,m.getIdReserve());
            requeteSql.setInt(2,m.getIdReserve());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Ajouter avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Solde.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SoldeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public ResultSet Lister(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT s.idSolde, s.idReserve, r.numChambr, r.nbrJour, r.dateEntree,(c.prixNuit*r.nbrJour) as a_payer,s.soldeActuel FROM reserver r, solde s, chambre c WHERE s.idReserve = r.idReserve AND  r.numChambr = c.numChambr ";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();            
        }
        catch (SQLException ex) {
            Logger.getLogger(Solde.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SoldeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    public ResultSet ListerId(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT idsolde FROM solde WHERE idReserve IS NOT NULL";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();            
        }
        catch (SQLException ex) {
            Logger.getLogger(Solde.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null , "error");

        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SoldeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    
    public boolean modifier(Solde m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "UPDATE solde SET soldeactuel = ? , idReserve = ? WHERE idsolde = ?";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setInt(1,m.getSoldeActuel());
            requeteSql.setInt(2,m.getIdReserve());
            requeteSql.setInt(3, m.getIdSolde());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Modifier avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Solde.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SoldeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public boolean supprimer(Solde m){
        boolean succes = true;
        con = cn.dbConnection();
        String sql = "DELETE FROM solde WHERE idsolde = ?";
        try {
            requeteSql = con.prepareStatement(sql);
            requeteSql.setInt( 1, m.getIdSolde());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Supprimer avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Solde.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SoldeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
}
