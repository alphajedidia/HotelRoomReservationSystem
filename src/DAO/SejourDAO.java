/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modele.Reserver;
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
public class SejourDAO {
    Connection con;
    ConnectDB cn = new ConnectDB();
    PreparedStatement requeteSql;
    ResultSet resultat;
    
    public boolean Enregistrer(Reserver m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "INSERT INTO reserver (numChambr,nbrJour,nomClient,telephone) VALUES (?,?,?,?)";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setString(1,m.getNumChambr());
            requeteSql.setInt(2, m.getNbrJour());
            requeteSql.setString(3,m.getNomClient());
            requeteSql.setString(4,m.getTelephone());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Ajouter avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Reserver.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public ResultSet Lister(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT * FROM reserver WHERE dateEntree = dateReserve";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(Reserver.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    public ResultSet ListerId(){
        try{
            con = cn.dbConnection();
            String sql;
            sql = "SELECT idReserve FROM reserver WHERE dateEntree  = dateReserve ";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(Reserver.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    public ResultSet ListerIdChambreDispo( int nbrJourDemande){
        try{
            con = cn.dbConnection();
            String sql = "SELECT numChambr FROM chambre WHERE numChambr NOT IN (SELECT numChambr FROM reserver r WHERE (r.dateEntree BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '"+nbrJourDemande+" days') OR (CURRENT_DATE BETWEEN r.dateentree AND r.dateentree + MAKE_INTERVAL(days => r.nbrjour)))";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();
            
            
        }
        catch (SQLException ex) {
            Logger.getLogger(Reserver.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    
    public boolean modifier(Reserver m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "UPDATE reserver SET numChambr = ?, dateEntree = ?, nbrJour = ?, nomClient = ?, telephone = ?  WHERE idReserve = ?";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setString(1,m.getNumChambr());
            requeteSql.setDate(2,m.getDateEntree());
            requeteSql.setInt(3, m.getNbrJour());
            requeteSql.setString(4,m.getNomClient());
            requeteSql.setString(5,m.getTelephone());
            requeteSql.setInt(6,m.getIdReserve());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Modifier avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Reserver.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public boolean supprimer(Reserver m){
        boolean succes = true;
        con = cn.dbConnection();
        String sql = "DELETE FROM reserver WHERE idReserve = ?";
        try {
            requeteSql = con.prepareStatement(sql);
            requeteSql.setInt( 1, m.getIdReserve());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Supprimer avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Reserver.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
}
