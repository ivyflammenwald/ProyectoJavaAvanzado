/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author emanuel
 */
public class Modelo {
    private Statement stmt;
    private Connection conn;
    private PreparedStatement ps;
    
    /**
     * En el constructor de modelo iniciamos la conexion con nuestra base de datos.
     */
    public Modelo(){
        Conexion.cargar();
        conn =  Conexion.conectar("jdbc:mysql://localhost:3306/proyectojava","root","");
    }
    
    /**
     * En esta parte solo llamamos a las funciones que suben los datos XML
    */
    public void subirXML(){
        crearPoliza();      // Debemos crear la tabla que nos falta (poliza)
        subirClientes();    // Subimos los clientes de Clientes.xml
        subirFacturas();    // Subimos las facturas de Facturas.xml
        subirVehiculos();   // Subimos los vehiculos de Vehiculos.xml
        subirPolizas();     // Subimos las polizas de los XML
    }
    
    /**
     * Creamos la tabla Poliza para la base de datos
     */
    public void crearPoliza(){
        String query = "CREATE TABLE poliza(\n" +
                        "id_poliza INT NOT NULL,\n" +
                        "costo NUMERIC(10,2) NOT NULL,\n" +
                        "prima NUMERIC(10,2) NOT NULL,\n" +
                        "fecha_apertura DATE NOT NULL,\n" +
                        "fecha_vencimiento DATE NOT NULL, \n" + 
                        "id_cliente INT NOT NULL,\n" +
                        "id_vehiculo INT NOT NULL,\n" +
                        "FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),\n" +
                        "FOREIGN KEY (id_vehiculo) REFERENCES vehiculo(id_vehiculo)\n" +
                        ");";
        try {
            ps = conn.prepareStatement(query);  
            ps.executeUpdate(); 	           
        } catch (SQLException sqle) { 
            System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());    
        }
    }
    
    public void subirCliente(String nombre, String direccion){
        String query = "INSERT INTO cliente (nombre, direccion) VALUES (?,?)";
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.executeUpdate();
            System.out.println("Update exitoso...");
        }catch(SQLException sqle){}
    }
    
    public void subirFactura(double monto){
        String query = "INSERT INTO factura (monto) VALUES (?)";
        try{
            ps = conn.prepareStatement(query);
            ps.setDouble(1, monto);
            ps.executeUpdate();
            System.out.println("Update exitoso...");
        }catch(SQLException sqle){}
    }
    
    public void subirVehiculo(String placas, String marca, String modelo, int id_factura){
        String query = "INSERT INTO vehiculo (placas, marca, modelo, id_factura) VALUES (?,?,?,?)";
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, placas);
            ps.setString(2, marca);
            ps.setString(3, modelo);
            ps.setInt(4, id_factura);
            ps.executeUpdate();
            System.out.println("Update exitoso...");
        }catch(SQLException sqle){}
    }
    
    private void subirPoliza(int id, double monto){
        String query = "INSERT INTO poliza (id_poliza,costo, prima, fecha_apertura, fecha_vencimiento, id_cliente, id_vehiculo) VALUES (?,?,?,?,?,?,?)";
        try{
            double costo = monto * ((6.67/12)/100);
            double prima = monto * 0.85;
            int dia = getAleatorio(1,30);
            int mes = getAleatorio(1,12);
            int anio = getAleatorio(1985, 2018);
            ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ps.setDouble(2, costo);
            ps.setDouble(3, prima);
            ps.setDate(4, java.sql.Date.valueOf(getFecha(dia, mes, anio)));
            ps.setDate(5, java.sql.Date.valueOf(getFecha(dia, mes, anio+1)));
            ps.setInt(6, id);
            ps.setInt(7, id);
            ps.executeUpdate();
        }catch(SQLException sqle){}
    }
    
    private void subirClientes(){
        try{
            File clientes = new File("/home/ivy/NetBeansProjects/Clientes.xml");
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(clientes);
            NodeList listClientes = document.getElementsByTagName("cliente");
            
            for(int i = 0; i < listClientes.getLength(); i++){
		Node nodo = listClientes.item(i);
		System.out.println("Elemento : "+nodo.getNodeName());
		if(nodo.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) nodo;
                    String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                    String direccion = element.getElementsByTagName("direccion").item(0).getTextContent();
                    
                    subirCliente(nombre, direccion);
		}
            }
        }catch(Exception e){
            e.printStackTrace();
	}
    }
    
    private void subirFacturas(){
        try{
            File facturas = new File("/home/ivy/NetBeansProjects/Facturas.xml");
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(facturas);
            
            NodeList listFacturas = document.getElementsByTagName("factura");
            
            for(int i = 0; i < listFacturas.getLength(); i++){
		Node nodo = listFacturas.item(i);
		System.out.println("Elemento : "+nodo.getNodeName());
		if(nodo.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) nodo;
                    String monto = element.getElementsByTagName("costo_total").item(0).getTextContent();
                    
                    subirFactura(Double.parseDouble(monto));
		}
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void subirVehiculos(){
        try {
            File vehiculos = new File("/home/ivy/NetBeansProjects/Vehiculos.xml");
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(vehiculos);
            
            NodeList listVehiculos = document.getElementsByTagName("vehiculo");
            
            for(int i = 0; i < listVehiculos.getLength(); i++){
		Node nodo = listVehiculos.item(i);
		System.out.println("Elemento : "+nodo.getNodeName());
		if(nodo.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) nodo;
                    String placas = element.getElementsByTagName("placas").item(0).getTextContent();
                    String marca = element.getElementsByTagName("marca").item(0).getTextContent();
                    String modelo = element.getElementsByTagName("modelo").item(0).getTextContent();
                    
                    subirVehiculo(placas, marca, modelo, i+1);
		}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void subirPolizas(){
        String query = "SELECT * FROM factura";
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
            
            while(rs.next()){
                int id = rs.getInt("id_factura");
                double monto = rs.getDouble("monto");
                subirPoliza(id, monto);
            }
            rs.close();
        } catch (Exception e) {}
    }
    
    private int getAleatorio(int menor, int mayor){
        return (int)Math.floor(Math.random()*(mayor-menor+1)+menor); 
    }
    
    private String getFecha(int dia, int mes, int anio){
        String fecha = Integer.toString(anio) + "-";
        if(mes < 10){
            fecha += "0" + Integer.toString(mes) + "-";
        }else
            fecha += Integer.toString(mes) + "-";
        if(dia < 10){
            fecha += "0" + Integer.toString(dia);
        }else
            fecha += Integer.toString(dia);
        
        return fecha;
    }
    
    public void terminarConexion(){
        try{
            stmt.close();
            ps.close();
            conn.close();
        }catch(SQLException sqle){
            
        }
    }
   
    public ArrayList consultarClientes(){
        String query = "SELECT * FROM cliente;";
        ResultSet rs;
        ArrayList <String> resultado = new ArrayList<String>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
            
            while(rs.next()){
                System.out.println("ID: " + rs.getInt("id_cliente"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Direccion: " + rs.getString("direccion"));
                System.out.println();
                String agregar = rs.getInt("id_cliente")+" "+ rs.getString("nombre")+" "+ rs.getString("direccion");
                resultado.add(agregar);
            }
            rs.close();
        } catch (Exception e) {}
        return resultado;
    }
    
    public ArrayList consultarFacturas(){
        String query = "SELECT * FROM factura;";
        ResultSet rs;
        ArrayList <String> resultado = new ArrayList<String>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
            
            while(rs.next()){
                System.out.println("ID: " + rs.getInt("id_factura"));
                System.out.println("Costo: " + rs.getDouble("monto"));
                System.out.println();
                String agregar = rs.getInt("id_factura")+" "+ rs.getDouble("monto");
                resultado.add(agregar);
            }
            rs.close();
        } catch (Exception e) {}
        return resultado;
    }
    
    public ArrayList consultarNPMC(){ // Consultar por (nombre_cliente, vehiculo_placas, vehiculo_modelo, factura_costo)
        String query = "SELECT c.nombre, v.placas, v.modelo, f.monto FROM cliente c JOIN poliza ON c.id_cliente = poliza.id_cliente JOIN vehiculo v ON poliza.id_vehiculo = v.id_vehiculo JOIN factura f ON v.id_factura = f.id_factura;";
        ResultSet rs;
        ArrayList <String> resultado = new ArrayList<String>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
            
            while(rs.next()){
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Placas: " + rs.getString("placas"));
                System.out.println("Modelo: " + rs.getString("modelo"));
                System.out.println("Costo: " + rs.getDouble("monto"));
                System.out.println();
                String agregar = rs.getString("nombre")+" "+rs.getString("placas")+" "+ rs.getString("modelo")+" "+rs.getDouble("monto");
                resultado.add(agregar);
            }
            rs.close();
        } catch (Exception e) {}
        return resultado;
    }
    
    public ArrayList consultarNDP(int id_cliente){ // Consultar por (nombre_cliente, direccion_cliente, placas_vehiculo) de un cliente
        String query = "SELECT c.nombre, c.direccion, v.placas FROM cliente c JOIN poliza p ON c.id_cliente = p.id_cliente JOIN vehiculo v ON p.id_vehiculo = v.id_vehiculo WHERE c.id_cliente = " + Integer.toString(id_cliente) + "; "; 
        ResultSet rs;
        ArrayList <String> resultado = new ArrayList<String>();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
            
            while(rs.next()){
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Direccion: " + rs.getString("direccion"));
                System.out.println("Placas: " + rs.getString("placas"));
                System.out.println();
                String agregar = rs.getString("nombre")+" "+rs.getString("direccion")+" "+ rs.getString("placas");
                resultado.add(agregar);
            }
            rs.close();
        } catch (Exception e) {}
        return resultado;
    }
    
    public ArrayList consultarNPCP(int id_cliente){ // Consultar por (nombre_cliente, placas_vehiculo, monto_poliza, prima_poliza) de un cliente
        String query = "SELECT c.nombre, v.placas, p.costo, p.prima FROM cliente c JOIN poliza p ON c.id_cliente = p.id_cliente JOIN vehiculo v ON p.id_vehiculo = v.id_vehiculo WHERE c.id_cliente = " + Integer.toString(id_cliente) + ";"; 
        ResultSet rs;
        ArrayList <String> resultado = new ArrayList<String>();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
            
            while(rs.next()){
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Placas: " + rs.getString("placas"));
                System.out.println("Monto Poliza: " + rs.getString("monto"));
                System.out.println("Prima asegurada: " + rs.getString("prima"));
                System.out.println();
                String agregar = rs.getString("nombre")+" "+rs.getString("placas")+" "+ rs.getString("monto")+" "+rs.getString("prima");
                resultado.add(agregar);
            }
            rs.close();
        } catch (Exception e) {}
        return resultado;
    }
    
    public ArrayList consultarFAFV(){ // Consultar por (fecha_apertura poliza, fecha_vencimiento poliza) de todas las polizas
        String query = "SELECT p.fecha_apertura, p.fecha_vencimiento FROM poliza p;"; 
        ResultSet rs;
        ArrayList <String> resultado = new ArrayList<String>();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
          
            while(rs.next()){
                System.out.println("Fecha apertura: " + rs.getDate("fecha_apertura"));
                System.out.println("Fecha vencimiento: " + rs.getDate("fecha_vencimiento"));
                System.out.println();
                String agregar =  rs.getDate("fecha_apertura")+" "+ rs.getDate("fecha_vencimiento");
                resultado.add(agregar);
            }
            rs.close();
        } catch (Exception e) {}
        return resultado;
    }
    
    
    public void actualizarDireccion(int id_cliente, String direccion){
        String query = "UPDATE cliente SET direccion = ? WHERE id_cliente = ?;";
        
        try {
            ps = conn.prepareCall(query);
            ps.setString(1, direccion);
            ps.setInt(2, id_cliente);
            int renglones_afectados = ps.executeUpdate();
            System.out.println("Número de renglones actualizados: " + renglones_afectados);
            ps.close();
            conn.close();
        } catch (Exception e) {}
    }
    
    public void actualizarModeloMarca(int id_vehiculo, String modelo, String marca){
        String query = "UPDATE vehiculo SET  modelo = ?, marca = ? WHERE id_vehiculo = ?;";
        
        try {
            ps = conn.prepareCall(query);
            ps.setString(1, modelo);
            ps.setString(2, marca);
            ps.setInt(3, id_vehiculo);
            int renglones_afectados = ps.executeUpdate();
            System.out.println("Número de renglones actualizados: " + renglones_afectados);
            ps.close();
            conn.close();
        } catch (Exception e) {}
    }
    
    public void actualizarModelo(int id_vehiculo, String modelo){
        String query = "UPDATE vehiculo SET  modelo = ? WHERE id_vehiculo = ?;";
        
        try {
            ps = conn.prepareCall(query);
            ps.setString(1, modelo);
            ps.setInt(2, id_vehiculo);
            int renglones_afectados = ps.executeUpdate();
            System.out.println("Número de renglones actualizados: " + renglones_afectados);
            ps.close();
            conn.close();
        } catch (Exception e) {}
    }
    
    public void actualizarMarca(int id_vehiculo, String marca){
        String query = "UPDATE vehiculo SET marca = ? WHERE id_vehiculo = ?;";
        
        try {
            ps = conn.prepareCall(query);
            ps.setString(1, marca);
            ps.setInt(2, id_vehiculo);
            int renglones_afectados = ps.executeUpdate();
            System.out.println("Número de renglones actualizados: " + renglones_afectados);
            ps.close();
            conn.close();
        } catch (Exception e) {}
    }
    
    public void actualizarFactura(int id_factura, double monto){
        String query = "UPDATE factura SET monto = ? WHERE id_factura = ?;";
        
        try {
            ps = conn.prepareCall(query);
            ps.setDouble(1, monto);
            ps.setInt(2, id_factura);
            int renglones_afectados = ps.executeUpdate();
            System.out.println("Número de renglones actualizados: " + renglones_afectados);
            ps.close();
            conn.close();
            actualizarPoliza(id_factura, monto);
        } catch (Exception e) {}
    }
    
    private void actualizarPoliza(int id, double monto){
        String query = "UPDATE costo SET costo = ?, prima = ? WHERE id_poliza = ?;";
        double costo = monto * ((6.67/12)/100);
        double prima = monto * 0.85;
        try {
            ps = conn.prepareCall(query);
            ps.setDouble(1, costo);
            ps.setDouble(2, prima);
            ps.setInt(3, id);
            int renglones_afectados = ps.executeUpdate();
            System.out.println("Número de renglones actualizados: " + renglones_afectados);
            ps.close();
            conn.close();
        } catch (Exception e) {}
    }
    
     public ArrayList llenarClientes(){ //
        //esta funcion toma los clientes de las tablas sql y los inserta en la ultima lista de la  interfaz, con esa lista se selecciona para que clientes se desea hacer la consulta
        String query = "SELECT nombre FROM cliente";
        ArrayList <String> clientes_agregar = new ArrayList<String>();
        
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("llenado con exito ");
            
            while(rs.next()){
                String nomb = rs.getString("nombre");
                clientes_agregar.add(nomb);
            }
            rs.close();
        } catch (Exception e) {}
        return clientes_agregar;
}
     
     public void agregaraBaseDatos(String dir, String f1, String f2, String placa, int indice){ //AL MODELO O AL CONTROLADOR?
        /*este metodo se invoca cuando se actualiza un usuario,  se usa para anexar los datos a la base de datos  en la consulta que se esta alterando  */
        System.out.println(dir+f1+f2+placa); 
              
        
            //actualizar direccion
        String query = "UPDATE cliente SET direccion = ? WHERE id_cliente = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1,  dir);
            ps.setInt(2, indice);
            ps.executeUpdate();
            System.out.println("Update exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
}
     }}

