/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author emanuel
 */
public class Conexion {
    public  static void cargar(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver cargado...");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static Connection conectar(String url, String username, String passwd){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, passwd);
            System.out.println("Conexion a base realizada...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
