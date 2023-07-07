/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modele.Occuper;
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
public class OccuperDAO {
    Connection con;
    ConnectDB cn = new ConnectDB();
    PreparedStatement requeteSql;
    ResultSet resultat;
    
    public boolean Enregistrer(Occuper m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "INSERT INTO occuper (idReserve) VALUES (?)";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setInt(1,m.getIdReserve());
            requeteSql.executeUpdate();
            sql = "INSERT INTO solde (idReserve,soldeactuel) VALUES (?,((SELECT MAX(soldeActuel) FROM solde )+ (Select (c.prixNuit*r.nbrJour) from chambre c,reserver r where r.idReserve = ? and r.numChambr = c.numChambr)))";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setInt(1,m.getIdReserve());
            requeteSql.setInt(2,m.getIdReserve());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Ajouter avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Occuper.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(OccuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public ResultSet Lister(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT occuper.idOccup, occuper.idReserve, reserver.numChambr, reserver.dateEntree, DATE (reserver.dateEntree + MAKE_INTERVAL(days => reserver.nbrJour)) AS fin, reserver.nomClient FROM occuper , reserver WHERE reserver.idReserve = occuper.idReserve";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();            
        }
        catch (SQLException ex) {
            Logger.getLogger(Occuper.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(OccuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    public ResultSet ListerId(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT idOccup FROM occuper";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();            
        }
        catch (SQLException ex) {
            Logger.getLogger(Occuper.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(OccuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    public ResultSet ListerIdReserve(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT idreserve from reserver WHERE idReserve NOT IN (SELECT idReserve from occuper)";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();            
        }
        catch (SQLException ex) {
            Logger.getLogger(Occuper.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(OccuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    
    public boolean modifier(Occuper m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "UPDATE occuper SET idReserve = ? WHERE idOccup = ?";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setInt(1,m.getIdReserve());
            requeteSql.setInt(2, m.getIdOccup());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Modifier avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Occuper.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(OccuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public boolean supprimer(Occuper m){
        boolean succes = true;
        con = cn.dbConnection();
        String sql = "DELETE FROM occuper WHERE idOccup = ?";
        try {
            requeteSql = con.prepareStatement(sql);
            requeteSql.setInt( 1, m.getIdOccup());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Supprimer avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Occuper.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(OccuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
}
