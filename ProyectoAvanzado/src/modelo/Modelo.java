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
            File clientes = new File("/home/emanuel/Documentos/Java/JavaAvanzado/Clientes.xml");
            
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
            File facturas = new File("/home/emanuel/Documentos/Java/JavaAvanzado/Facturas.xml");
            
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
            File vehiculos = new File("/home/emanuel/Documentos/Java/JavaAvanzado/Vehiculos.xml");
            
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
    
    ////////////////ivy metodos para actualizar
    
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
        
        //actualizar fecha inicio
        
        query = "UPDATE poliza SET apertura = ? WHERE id_poliza  = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1,  f1);
            ps.setInt(2, indice);
            ps.executeUpdate();
            System.out.println("Update exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
        }
       
       //actualizar fecha fin
       
        query = "UPDATE poliza SET vencimiento = ? WHERE id_poliza  = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1,  f2);
            ps.setInt(2, indice);
            ps.executeUpdate();
            System.out.println("Update exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
        }
        
        //  actualizar placa
        
        query = "UPDATE vehiculo SET placas = ? WHERE id_vehiculo  = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1,  placa);
            ps.setInt(2, indice);
            ps.executeUpdate();
            System.out.println("Update exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
        }
    }
    
    public void borrarenBaseDatos(int indice){
        
        //borrar de cliente
        String query = "DELETE FROM cliente WHERE id_cliente  = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, indice);
            ps.executeUpdate();
            System.out.println("Delete exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
        }
        
        //borrar de  vehiculo
        query = "DELETE FROM vehiculo WHERE id_vehiculo  = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, indice);
            ps.executeUpdate();
            System.out.println("Delete exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
        }
        
        //borrar de poliza
        query = "DELETE FROM poliza WHERE id_poliza  = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, indice);
            ps.executeUpdate();
            System.out.println("Delete exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
        }
       
        //borrar de factura
       
        query = "DELETE FROM factura WHERE id_factura  = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, indice);
            ps.executeUpdate();
            System.out.println("Delete exitoso...");
        }catch(SQLException sqle){}catch(Exception e){
            System.out.println("ha ocurrido una excepcion, los cambios no se guardaran ");  
        }
    }
    
    // metodos para la vista
    
    
    public String[][] hacerConsulta(String cli, String veh, String fac, String po, int cliabuscar){
    
        // se va a definir que variables se buscan 
        // en el arraylist vienen  los string con el nombre de los atributos que se va a buscar en las tablas
        // con este array se van a hacer las consultas, pero como no existe una columna todos, se cambia por * para que
        //sea una consulta valida
        
        String[][] resultados = null;
        
        String query = "SELECT cliente.? , vehiculo.? , factura.?, poliza.? FROM cliente INNER JOIN poliza ON cliente.id_cliente = poliza.id_cliente"
                + " INNER JOIN vehiculo ON vehiculo.id_vehiculo = poliza.id_vehiculo"
                + " INNER JOIN factura ON vehiculo.id_factura = factura.id_factura"
                + "  WHERE id_cliente = ?;"; //definir consulta
        
        String query2 = "SELECT cliente."+cli+", vehiculo."+veh+" , factura."+fac+", poliza."+po+" FROM cliente INNER JOIN poliza ON cliente.id_cliente = poliza.id_cliente"
                + " INNER JOIN vehiculo ON vehiculo.id_vehiculo = poliza.id_vehiculo"
                + " INNER JOIN factura ON vehiculo.id_factura = factura.id_factura"
                + "  WHERE id_cliente ="+cliabuscar+";"; //definir consulta
        
        
        ResultSet rs; 
        
        /* ResultSet rs;
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
        } catch (Exception e) {}*/
        
        
        try {
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query2);
            rs = stmt.getResultSet();
            
            ps = conn.prepareStatement(query);
            ps.setString(1, cli);  // atrib de clientes
            ps.setString(2, veh); //atrib de vehiculo
            ps.setString(3, fac); //atrib de factura
            ps.setString(4, po); //atrib de poliza
            
            if(cliabuscar == 1){ //si seleccionaron todos los clientes
                ps.setString(5, "*");
            }else{
                ps.setInt(5, cliabuscar);
            }
           
            System.out.println(ps);
            System.out.println(query2);
            
            //rs = ps.executeQuery(query);
            //rs = ps.getResultSet();
            
            System.out.println("Consulta exitosa: ");
            System.out.println(rs);
            
            
            String datoscliente ="";
            String datosvehic="";
            String datosfact="";
            String datospoliza="";
            
            int i=0;
            System.out.println("REALIZANDO CONSULTA");
            
            while (rs.next()) {
                
                if(cli.equals("*")){ //si es select * de cliente
                    String datosclientenom = rs.getString("nombre");
                    String datosclientedir=rs.getString("direccion");
                    datoscliente= datosclientenom+" "+datosclientedir;
                }else{
                    datoscliente = rs.getString(cli); //el parametro es el que se haya pedido
                }
                
                
                
                if(veh.equals("*")){ //si es select * de vehiculos  
                    String datosvehicpl = rs.getString("placas");
                    String datosvehicma =rs.getString("marca");
                    String datosvehicmo =rs.getString("modelo");
                    datosvehic+=datosvehicpl+" "+datosvehicma+" "+datosvehicmo;
                    
                }else{
                    datosvehic = rs.getString(veh); //el parametro es el que se haya pedido
                }
                
               
                datosfact = rs.getString(fac); //el parametro es el que se haya pedido
                
                
                if(po.equals("*")){ //si es select * de poliza 
                    
                    String datospolizaco = rs.getString("costo");
                    String datospolizapr =rs.getString("prima");
                    String datospolizaap =rs.getString("apertura");
                    datospoliza=datospolizaco+" "+datospolizapr+" "+datospolizaap; 
                    
                }else{
                    datospoliza = rs.getString(po); //el parametro es el que se haya pedido
                }
                
                
                
                
               resultados[i][0]= datoscliente;
               resultados[i][1] = datosvehic;
               resultados[i][2] = datosfact;
               resultados[i][3] = datospoliza;
               
               i++;
        
                datosvehic="";
                datoscliente="";
                datosfact ="";
                datospoliza="";
                // print the results
            }
            
            
           rs.close();
            
        } catch (Exception e) {}
        
        return resultados;
    }
    
    public ArrayList llenarClientes(){ //EMANUEL hay que definir este metodo
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
    
    public void consultarClientes(){
        String query = "SELECT * FROM cliente";
        ResultSet rs;
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
            }
            rs.close();
        } catch (Exception e) {}
    }
    
    public void consultarFacturas(){
        String query = "SELECT * FROM factura";
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
            
            while(rs.next()){
                System.out.println("ID: " + rs.getInt("id_factura"));
                System.out.println("Costo: " + rs.getDouble("monto"));
                System.out.println();
            }
            rs.close();
        } catch (Exception e) {}
    }
    
    public void consultarNPMC(){ // Consultar por (nombre_cliente, vehiculo_placas, vehiculo_modelo, factura_costo)
        String query = "SELECT c.nombre, v.placas, v.modelo, f.monto FROM cliente c JOIN poliza ON c.id_cliente = poliza.id_cliente JOIN vehiculo v ON poliza.id_vehiculo = v.id_vehiculo JOIN factura f ON v.id_factura = f.id_factura;";
        ResultSet rs;
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
            }
            rs.close();
        } catch (Exception e) {}
    }
    
    public void consultarNDP(int id_cliente){ // Consultar por (nombre_cliente, direccion_cliente, placas_vehiculo) de un cliente
        String query = "SELECT c.nombre, c.direccion, v.placas FROM cliente c JOIN poliza p ON c.id_cliente = p.id_cliente JOIN vehiculo v ON p.id_vehiculo = v.id_vehiculo WHERE c.id_cliente = " + Integer.toString(id_cliente) + "; "; 
        ResultSet rs;
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
            }
            rs.close();
        } catch (Exception e) {}
    }
    
    public void consultarNPCP(int id_cliente){ // Consultar por (nombre_cliente, placas_vehiculo, monto_poliza, prima_poliza) de un cliente
        String query = "SELECT c.nombre, v.placas, p.monto, p.prima FROM cliente c JOIN poliza p ON c.id_cliente = p.id_cliente JOIN vehiculo v ON p.id_vehiculo = v.id_vehiculo WHERE c.id_cliente = " + Integer.toString(id_cliente) + ";"; 
        ResultSet rs;
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
            }
            rs.close();
        } catch (Exception e) {}
    }
    
    public void consultarFAFV(){ // Consultar por (fecha_apertura poliza, fecha_vencimiento poliza) de todas las polizas
        String query = "SELECT p.fecha_apertura, p.fecha_vencimiento FROM poliza p;"; 
        ResultSet rs;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs = stmt.getResultSet();
            System.out.println("Consulta exitosa: ");
          
            while(rs.next()){
                System.out.println("Fecha apertura: " + rs.getDate("fecha_apertura"));
                System.out.println("Fecha vencimiento: " + rs.getDate("fecha_vencimiento"));
                System.out.println();
            }
            rs.close();
        } catch (Exception e) {}
    }
}

