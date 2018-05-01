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
    
    
    this.vista.btn_consultar_indice.addActionListener(this);
    this.vista.btn_consultar_indice.setActionCommand("btn_vista_consult_ind");
    
    this.vista.btn_editar_registro.addActionListener(this);
    this.vista.btn_editar_registro.setActionCommand("btn_vista_editar");
    
    this.vista.btn_consultar_todos.addActionListener(this);
    this.vista.btn_consultar_todos.setActionCommand("btn_vista_consult_todo");
    
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
        
        vista.jl_listaconsultas.removeAllItems();
        vista.jl_listaconsultas.addItem("todos los clientes");
        vista.jl_listaconsultas.addItem("todas las facturas");
        vista.jl_listaconsultas.addItem("nombre, placas, modelo y costo");
        vista.jl_listaconsultas.addItem("fechas de todas las polizas ");
        
        vista.jl_listaconsultas_ind.removeAllItems();
        vista.jl_listaconsultas_ind.addItem("nombre, direccion, placas");
         vista.jl_listaconsultas_ind.addItem("nombre, placas, monto, prima");
        
        
       ArrayList <String> llenado;
        llenado = modelo.llenarClientes();
       
        
        for( String agregar : llenado){
            vista.jl_listaconsultas.addItem(agregar);
        }
       
}

    
    //funcion de vista para consultas
    
    public void actionPerformed(ActionEvent e) {
         
        String set_result ="";
                
                
        switch (e.getActionCommand()){
        
            case "btn_vista_consult_ind": //consultar elemento por indice
                
                int opcion1 = vista.jl_listaconsultas_ind.getSelectedIndex();
                int ind_cliente = vista.jl_listaclientes.getSelectedIndex();
                
                switch(opcion1){
                
                    case 0: // nombre dir placas
                   
                        ArrayList<String > ci_ndp = modelo.consultarNDP(ind_cliente);
                        
                        for(String s : ci_ndp){
                            set_result = set_result+" "+s+"\n"; //inserta el string y hace un salto de linea
                        }
                        vista.lb_mostrar_consultas.setText(set_result);
                        
                       break;
                       
                    case 1: //nombre placas monto prima
                        
                        ArrayList<String > ci_npmp = modelo.consultarNPCP(ind_cliente);
                        
                        for(String s : ci_npmp){
                            set_result = set_result+" "+s+"\n"; //concatena el string y hace un salto de linea
                        }
                        vista.lb_mostrar_consultas.setText(set_result); //lo imprime en la interfaz
                        
                        break;
                
                }
                
                break;
                
             case "btn_vista_editar":
                 pag_actualizacion.setVisible(true);
                break;
                
            case "btn_vista_consult_todo":
                int opcion2 = vista.jl_listaconsultas_ind.getSelectedIndex();
                
                switch(opcion2){
                
                    case 0: // todos los clientes
                        ArrayList<String > c_cli = modelo.consultarClientes();
                        
                        for(String s : c_cli){
                            set_result = set_result+" "+s+"\n"; //inserta el string y hace un salto de linea
                        }
                        vista.lb_mostrar_consultas.setText(set_result);
                        
                       break;
                       
                    case 1: //todas las facturas
                        
                        ArrayList<String > c_fac = modelo.consultarFacturas();
                        
                        for(String s : c_fac){
                            set_result = set_result+" "+s+"\n"; //concatena el string y hace un salto de linea
                        }
                        vista.lb_mostrar_consultas.setText(set_result); //lo imprime en la interfaz
                        
                        break;
                        
                    case 2: // nombre placas modelo y costo
                        ArrayList<String > c_npmc = modelo.consultarNPMC();
                        
                        for(String s : c_npmc){
                            set_result = set_result+" "+s+"\n"; //inserta el string y hace un salto de linea
                        }
                        vista.lb_mostrar_consultas.setText(set_result);
                        
                       break;
                       
                    case 3: // fechas 
                        
                        ArrayList<String > c_ff = modelo.consultarFAFV();
                        
                        for(String s : c_ff){
                            set_result = set_result+" "+s+"\n"; //concatena el string y hace un salto de linea
                        }
                        vista.lb_mostrar_consultas.setText(set_result); //lo imprime en la interfaz
                        
                        break;
                     
                }
                
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
                
                
             case "btn_pag_act":
                  int indice = pag_actualizacion.listaclientes.getSelectedIndex();
                  System.out.println("borrado cliente "+pag_actualizacion.listaclientes.getSelectedItem()+"indice "+indice); 
                  pag_actualizacion.listaclientes.remove(indice);

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
        
        
    
    

