/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import vista.Vista;
import vista.PaginaActualizacion;
import modelo.Modelo;
/**
 *
 * @author emanuel
 */
public class Controlador implements ActionListener{
    private Vista vista;
    private Modelo modelo;
    private PaginaActualizacion pag_actualizacion;
    
    public Controlador(Vista vista, Modelo modelo, PaginaActualizacion pag){
    this.vista = vista;
    this.modelo = modelo;
    this.pag_actualizacion =pag;
    this.iniciarvista();
    this.iniciarpagina();
    vista.setVisible(true);
    
    //definicion de los metodos de la vista
    
    
    this.vista.btn_consultar.addActionListener(this);
    this.vista.btn_consultar.setActionCommand("btn_vista_consult");
    
    this.vista.btn_editar_registro.addActionListener(this);
    this.vista.btn_editar_registro.setActionCommand("btn_vista_editar");
    
    this.pag_actualizacion.btn_borrar.addActionListener(this);
    this.pag_actualizacion.btn_borrar.setActionCommand("btn_pag_act");
   
    this.pag_actualizacion.btn_guardar.addActionListener(this);
    this.pag_actualizacion.btn_guardar.setActionCommand("btn_pag_guardar");
    
    this.pag_actualizacion.btn_seleccionar.addActionListener(this);
    this.pag_actualizacion.btn_seleccionar.setActionCommand("btn_pag_select");
    }
    
    
    private void iniciarpagina(){ // al controlador
        pag_actualizacion.listaclientes.removeAllItems();
        ArrayList <String> agregaralista = modelo.llenarClientes();
        for( String cli : agregaralista){
        pag_actualizacion.listaclientes.addItem(cli);
        }
        /* 
        se llena la lista listaclientes con todos los clientes de la tabla para elegir uno para editar
        
        */
    }

    
    private void iniciarvista(){ //incia los elementos de la vista, es llamado en el constructor
        
        vista.jl_listaclientes.removeAllItems();
        vista.jl_listafacturas.removeAllItems();
        vista.jl_listapolizas.removeAllItems();
        vista.jl_clientesabuscar.removeAllItems();
        vista.jl_listavehiculos.removeAllItems();
      
        vista.jl_listaclientes.addItem("*");
        vista.jl_listaclientes.addItem("nombre");
        vista.jl_listaclientes.addItem("dirección");

        vista.jl_listafacturas.addItem("monto");
        
        vista.jl_listapolizas.addItem("*");
        vista.jl_listapolizas.addItem("costo");
        vista.jl_listapolizas.addItem("prima");
        vista.jl_listapolizas.addItem("apertura");
        vista.jl_listapolizas.addItem("vencimiento");
       
        vista.jl_listavehiculos.addItem("*");
        vista.jl_listavehiculos.addItem("placas");
        vista.jl_listavehiculos.addItem("marca");
        vista.jl_listavehiculos.addItem("modelo");
        
        //vista.jl_clientesabuscar.addItem("*"); //se debe elegir al menos un cliente
        
       ArrayList <String> llenado;
        llenado = modelo.llenarClientes();
       
        
        for( String agregar : llenado){
            vista.jl_clientesabuscar.addItem(agregar);
        }

       
}

    
    //funcion de vista para consultas
    
    public void actionPerformed(ActionEvent e) {
         
        
        switch (e.getActionCommand()){
        
            case "btn_vista_consult":
                
        String atrib_cliente = (String)vista.jl_listaclientes.getSelectedItem();
        String atrib_vehic = (String)vista.jl_listavehiculos.getSelectedItem();
        String atrib_poliza = (String)vista.jl_listapolizas.getSelectedItem();
        String atrib_factura = (String)vista.jl_listafacturas.getSelectedItem();
        
        int cli_abuscar = vista.jl_listaclientes.getSelectedIndex();
        
        int indmax = vista.jl_listaclientes.getItemCount();
        
        String [][] consulta_hecha = modelo.hacerConsulta(atrib_cliente, atrib_vehic, atrib_poliza, atrib_factura, cli_abuscar, indmax);
        
       
        for (String[] consulta_hecha1 : consulta_hecha) {
            // imprime los resultados de las consultas en la consola
            for (String consulta_hecha11 : consulta_hecha1) {
                System.out.println(" ");
                System.out.println(consulta_hecha11);
            }
        }
       
        
        
        String consulta_imprimir ="";
        
        for(int i =0; i<consulta_hecha.length; i++){ //va a crear el texto
        
            for (int j=0; j<consulta_hecha[i].length ; i ++){
                
                consulta_imprimir= consulta_imprimir + consulta_hecha[i][j]; //agrega el elemento de la matriz
                consulta_imprimir =consulta_imprimir +" "; //le pone un espacio
            }
                consulta_imprimir= consulta_imprimir+"\n";
            
        }
                System.out.println(consulta_imprimir);
        
       vista.lb_mostrar_consultas.setText(consulta_imprimir);
       
        
                
                break;
                
             case "btn_vista_editar":
                 
                 pag_actualizacion.setVisible(true);
                
                break;
                
            case "btn_pag_act":
                 int indice = pag_actualizacion.listaclientes.getSelectedIndex();
                  System.out.println("borrado cliente "+pag_actualizacion.listaclientes.getSelectedItem()+"indice "+indice); 
                  pag_actualizacion.listaclientes.remove(indice);
                
                break;
                
             case "btn_pag_guardar":
                 String nuevadir = pag_actualizacion.jt_get_dir_nueva.getText();
                  String nuevoinicio = pag_actualizacion.jt_get_fecha_nueva.getText();
                    String nuevofin =pag_actualizacion.jt_get_venc_nuevo.getText();
                    String placanueva = pag_actualizacion.jt_get_placas_nuev.getText();

                    int ind  = pag_actualizacion.listaclientes.getSelectedIndex();

                    modelo.agregaraBaseDatos(nuevadir,nuevoinicio,nuevofin,placanueva, ind);

                    pag_actualizacion.dispose();
                
                break;
                
            case "btn_pag_select":
                        // TODO add your handling code here:listaclientes
                String cliete_sel = (String)pag_actualizacion.listaclientes.getSelectedItem();
                System.out.println("cliente_sel");
                //obtener mediante la base de datos los siguientes elementos
                String editdir = "nombre a editar";// obtener de db
                String  fechaini = "fechainicialanterior";
                String fechafin="fechafinanterior";
                String placaant = "placa anterior";

                // imprimir los valores en los text para que sean modificados

                pag_actualizacion.jt_get_dir_nueva.setText(editdir);
                pag_actualizacion.jt_get_fecha_nueva.setText(fechaini);
                pag_actualizacion.jt_get_venc_nuevo.setText(fechafin);
                pag_actualizacion.jt_get_placas_nuev.setText(placaant);
                break;
        
        
        }
        
    }                                
    
    
    //funcion de vista para editar
    
    //funcion de actualizar para guardar datos
    
    
private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        
        String nuevadir = pag_actualizacion.jt_get_dir_nueva.getText();
        String nuevoinicio = pag_actualizacion.jt_get_fecha_nueva.getText();
        String nuevofin =pag_actualizacion.jt_get_venc_nuevo.getText();
        String placanueva = pag_actualizacion.jt_get_placas_nuev.getText();
        
        int ind  = pag_actualizacion.listaclientes.getSelectedIndex();
        
        this.modelo.agregaraBaseDatos(nuevadir,nuevoinicio,nuevofin,placanueva, ind);
  
        pag_actualizacion.dispose();
    
    
}
    //main, no modificar
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    
}
        
        
    
    

