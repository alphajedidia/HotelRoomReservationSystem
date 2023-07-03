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
//import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author ALPHA JEDIDIA
 */
public class ReservationDAO {
    
    Connection con;
    ConnectDB cn = new ConnectDB();
    PreparedStatement requeteSql;
    ResultSet resultat;
    
    public boolean Enregistrer(Reserver m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "INSERT INTO reserver (numChambr,dateEntree,nbrJour,nomClient,mail) VALUES (?,?,?,?,?)";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setString(1,m.getNumChambr());
            requeteSql.setDate(2, m.getDateEntree());
            requeteSql.setInt(3, m.getNbrJour());
            requeteSql.setString(4,m.getNomClient());
            requeteSql.setString(5,m.getMail());
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
            String sql = "SELECT * FROM reserver WHERE dateEntree != dateReserve";
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
            sql = "SELECT idReserve FROM reserver WHERE dateEntree  != dateReserve ";
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
    public ResultSet ListerIdChambreDispo(String dateEntreeDemande , int nbrJourDemande){
        try{
            con = cn.dbConnection();
            String sql =  "SELECT numChambr FROM chambre WHERE numChambr NOT IN (SELECT numChambr FROM reserver r WHERE (r.dateEntree BETWEEN DATE '" + dateEntreeDemande +  "' AND DATE '"+dateEntreeDemande+"' + INTERVAL '"+nbrJourDemande+" days') OR (DATE '" +dateEntreeDemande+ "' BETWEEN r.dateentree AND r.dateentree + MAKE_INTERVAL(days => r.nbrjour)) )";
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
    public ResultSet rechercheChambre(String dateEntreeDemande){
        try{
            con = cn.dbConnection();
            String sql =  "SELECT * FROM chambre WHERE numChambr NOT IN (SELECT numChambr FROM reserver r WHERE (DATE '" +dateEntreeDemande+ "' BETWEEN r.dateentree AND r.dateentree + MAKE_INTERVAL(days => r.nbrjour)) )";
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
    
    public ResultSet ListerIdChambreDispoModifier(String dateEntreeDemande , int nbrJourDemande , int idReserveMod){
        try{
            con = cn.dbConnection(); 
            String sql = "SELECT numChambr FROM chambre WHERE numChambr NOT IN (SELECT numChambr FROM reserver r WHERE (r.dateEntree BETWEEN DATE '" + dateEntreeDemande +  "' AND DATE '"+dateEntreeDemande+"' + INTERVAL '"+nbrJourDemande+" days') OR (DATE '" +dateEntreeDemande+ "' BETWEEN r.dateentree AND r.dateentree + MAKE_INTERVAL(days => r.nbrjour)) ) OR numChambr IN (SELECT numChambr FROM chambre WHERE numChambr IN (SELECT numChambr FROM reserver WHERE reserver.idReserve = "+idReserveMod+"))";
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
            String sql = "UPDATE reserver SET numChambr = ?, dateEntree = ?, nbrJour = ?, nomClient = ?, mail = ?  WHERE idReserve = ?";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setString(1,m.getNumChambr());
            requeteSql.setDate(2, m.getDateEntree());
            requeteSql.setInt(3, m.getNbrJour());
            requeteSql.setString(4,m.getNomClient());
            requeteSql.setString(5,m.getMail());
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
