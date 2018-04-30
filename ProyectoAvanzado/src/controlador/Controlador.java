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
    
    
    //definicion de los metodos de la vista
    
    
    this.vista.btn_consultar.addActionListener(this);
    this.vista.btn_editar_registro.addActionListener(this);
    
    /*this.vista.jl_clientesabuscar.add();
    this.vista.jl_listaclientes.add();
    this.vista.jl_listafacturas.add();
    this.vista.jl_listapolizas.add();
    this.vista.jl_listavehiculos.add();
    
    this.vista.lb_clientes_select.add();
    this.vista.lb_facturas_select.add();
    this.vista.lb_mostrar_consultas.add();
    this.vista.lb_polizas_select.add();
    this.vista.lb_proyectotitulo.add();
    this.vista.lb_resultado_consultas.add();
    this.vista.lb_usuarios_select.add();
    this.vista.lb_vehiculos_select.add();
   
            */
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
      
        vista.jl_listaclientes.addItem("-Todo-");
        vista.jl_listaclientes.addItem("Nombre");
        vista.jl_listaclientes.addItem("Dirección");

        vista.jl_listafacturas.addItem("Monto");
        
        vista.jl_listapolizas.addItem("-Todo-");
        vista.jl_listapolizas.addItem("Costo");
        vista.jl_listapolizas.addItem("Prima");
        vista.jl_listapolizas.addItem("Apertura");
        vista.jl_listapolizas.addItem("Vencimiento");
       
        vista.jl_listavehiculos.addItem("-Todo-");
        vista.jl_listavehiculos.addItem("Placas");
        vista.jl_listavehiculos.addItem("Marca");
        vista.jl_listavehiculos.addItem("Modelo");
        
        vista.jl_clientesabuscar.addItem("-Todos-"); //se debe elegir al menos un cliente
        
       ArrayList <String> llenado;
        llenado = modelo.llenarClientes();
       
        
        for( String agregar : llenado){
            vista.jl_clientesabuscar.addItem(agregar);
        }

       
}

    @Override
    public void actionPerformed(ActionEvent e) {
         
        String atrib_cliente = (String)vista.jl_listaclientes.getSelectedItem();
        String atrib_vehic = (String)vista.jl_listavehiculos.getSelectedItem();
        String atrib_poliza = (String)vista.jl_listapolizas.getSelectedItem();
        String atrib_factura = (String)vista.jl_listafacturas.getSelectedItem();
        
        int cli_abuscar = vista.jl_listaclientes.getSelectedIndex();
        
        //Modelo.hacerConsulta(atrib_cliente, atrib_vehic, atrib_poliza, atrib_factura, cli_abuscar);
        ArrayList <String> parametros_consulta = new ArrayList<String>();
        parametros_consulta.add(atrib_cliente);
        parametros_consulta.add(atrib_vehic);
        parametros_consulta.add(atrib_factura);
        parametros_consulta.add(atrib_poliza);
        String consulta_hecha=""; //va a almacenar todo en un solo parrafo
        
        ArrayList <String> consultas_imprimir;
        
        consultas_imprimir = modelo.hacerConsulta(parametros_consulta, cli_abuscar);
       
        
        for (String tupla : consultas_imprimir){
            consulta_hecha+=tupla;
            consulta_hecha+="\n";
        }
        
        
        
        vista.lb_mostrar_consultas.setText(consulta_hecha);
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
        
        
    
    

