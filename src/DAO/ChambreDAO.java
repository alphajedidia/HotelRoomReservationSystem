/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modele.Chambre;
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
public class ChambreDAO {
    Connection con;
    ConnectDB cn = new ConnectDB();
    PreparedStatement requeteSql;
    ResultSet resultat;
    
    
    public boolean Enregistrer(Chambre m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "INSERT INTO chambre (numChambr,design,chambreType,prixNuit) VALUES (?,?,?,?)";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setString(1,m.getNumChambr());
            requeteSql.setString(2,m.getDesign());
            requeteSql.setString(3,m.getChambreType());
            requeteSql.setInt(4, m.getPrixNuit());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Ajouter avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Chambre.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChambreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public ResultSet Lister(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT * FROM chambre";
            requeteSql = con.prepareStatement(sql);            
            resultat = requeteSql.executeQuery();
        }
        catch (SQLException ex) {
            
            Logger.getLogger(Chambre.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChambreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    public ResultSet ListerId(){
        try{
            con = cn.dbConnection();
            String sql = "SELECT numChambr FROM chambre";
            requeteSql = con.prepareStatement(sql);
            resultat = requeteSql.executeQuery();
        }
        catch (SQLException ex) {
            Logger.getLogger(Chambre.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null , "error");

        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChambreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    public boolean modifier(Chambre m){
        boolean succes = true;
        try {
            con = cn.dbConnection();
            String sql = "UPDATE chambre SET design = ?, chambreType = ?, prixNuit = ? WHERE numChambr = ?";
            requeteSql = con.prepareStatement(sql);
            requeteSql.setString(1,m.getDesign());
            requeteSql.setString(2,m.getChambreType());
            requeteSql.setInt(3, m.getPrixNuit());
            requeteSql.setString(4,m.getNumChambr());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Modifier avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Chambre.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChambreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    public boolean supprimer(Chambre m){
        boolean succes = true;
        con = cn.dbConnection();
        String sql = "DELETE FROM chambre WHERE numChambr = ?";
        try {
            requeteSql = con.prepareStatement(sql);
            requeteSql.setString( 1, m.getNumChambr());
            requeteSql.executeUpdate();
            JOptionPane.showMessageDialog(null , "Supprimer avec Success");
            
        } catch (SQLException ex) {
            Logger.getLogger(Chambre.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChambreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return succes;
    }
    
    
}
