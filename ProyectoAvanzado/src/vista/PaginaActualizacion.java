package vista;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class PaginaActualizacion extends javax.swing.JFrame {

    /**
     * Creates new form PaginaActualizacion
     */
    public PaginaActualizacion() {
        initComponents();
        this.initialize();
    }
    
    private void initialize(){ // al controlador
        listaclientes.removeAllItems();
        this.llenarClientesAct();
    }
    
    private void llenarClientesAct(){
         
        ArrayList <String> agregaralista = Modelo.llenarClientes();
        for( String cli : agregaralista){
        listaclientes.addItem(agregaralista);
        }
        /* 
        se llena la lista listaclientes con todos los clientes de la tabla para elegir uno para editar
        
        */
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lb_cliente = new javax.swing.JLabel();
        listaclientes = new javax.swing.JComboBox<>();
        btn_seleccionar = new javax.swing.JButton();
        jt_get_venc_nuevo = new javax.swing.JTextField();
        jt_get_dir_nueva = new javax.swing.JTextField();
        lb_direccion = new javax.swing.JLabel();
        lb_fechavenc = new javax.swing.JLabel();
        lb_placas = new javax.swing.JLabel();
        jt_get_placas_nuev = new javax.swing.JTextField();
        jt_get_fecha_nueva = new javax.swing.JTextField();
        lb_titulo_act = new javax.swing.JLabel();
        lb_fechainic = new javax.swing.JLabel();
        btn_guardar = new javax.swing.JButton();
        btn_borrar = new javax.swing.JButton();
        lb_eliminar = new javax.swing.JLabel();
        lb_fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_cliente.setFont(new java.awt.Font("WenQuanYi Micro Hei Mono", 1, 14)); // NOI18N
        lb_cliente.setText("Cliente a editar");
        jPanel1.add(lb_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, -1));

        listaclientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(listaclientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 160, -1));

        btn_seleccionar.setText("Seleccionar");
        btn_seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_seleccionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, -1, -1));

        jt_get_venc_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_get_venc_nuevoActionPerformed(evt);
            }
        });
        jPanel1.add(jt_get_venc_nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 130, -1));

        jt_get_dir_nueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_get_dir_nuevaActionPerformed(evt);
            }
        });
        jPanel1.add(jt_get_dir_nueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 130, -1));

        lb_direccion.setFont(new java.awt.Font("WenQuanYi Micro Hei Mono", 1, 14)); // NOI18N
        lb_direccion.setText("Dirección cliente");
        jPanel1.add(lb_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, 20));

        lb_fechavenc.setFont(new java.awt.Font("WenQuanYi Micro Hei Mono", 1, 14)); // NOI18N
        lb_fechavenc.setText("Fecha de vencimiento");
        jPanel1.add(lb_fechavenc, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, -1, -1));

        lb_placas.setFont(new java.awt.Font("WenQuanYi Micro Hei Mono", 1, 14)); // NOI18N
        lb_placas.setText("Placas del vehículo");
        jPanel1.add(lb_placas, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, -1));

        jt_get_placas_nuev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_get_placas_nuevActionPerformed(evt);
            }
        });
        jPanel1.add(jt_get_placas_nuev, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 130, -1));

        jt_get_fecha_nueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_get_fecha_nuevaActionPerformed(evt);
            }
        });
        jPanel1.add(jt_get_fecha_nueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 130, -1));

        lb_titulo_act.setFont(new java.awt.Font("WenQuanYi Micro Hei Mono", 1, 14)); // NOI18N
        lb_titulo_act.setText("Actualización de registros");
        jPanel1.add(lb_titulo_act, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        lb_fechainic.setFont(new java.awt.Font("WenQuanYi Micro Hei Mono", 1, 14)); // NOI18N
        lb_fechainic.setText("Fecha de inicio");
        jPanel1.add(lb_fechainic, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, -1, -1));

        btn_guardar.setText("Guardar cambios");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, -1, -1));

        btn_borrar.setText("Borrar");
        btn_borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_borrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        lb_eliminar.setFont(new java.awt.Font("WenQuanYi Micro Hei Mono", 1, 14)); // NOI18N
        lb_eliminar.setText("Eliminar este registro");
        jPanel1.add(lb_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        lb_fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/teal2.jpg"))); // NOI18N
        jPanel1.add(lb_fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 390));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionarActionPerformed
        // TODO add your handling code here:listaclientes
        String cliete_sel = (String)listaclientes.getSelectedItem();
        System.out.println("cliente_sel");
        //obtener mediante la base de datos los siguientes elementos
        String editdir = "nombre a editar";// obtener de db
        String  fechaini = "fechainicialanterior";
        String fechafin="fechafinanterior";
        String placaant = "placa anterior";
        
        // imprimir los valores en los text para que sean modificados
        
        jt_get_dir_nueva.setText(editdir);
        jt_get_fecha_nueva.setText(fechaini);
        jt_get_venc_nuevo.setText(fechafin);
        jt_get_placas_nuev.setText(placaant);
       
    }//GEN-LAST:event_btn_seleccionarActionPerformed

    private void jt_get_dir_nuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_get_dir_nuevaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_get_dir_nuevaActionPerformed

    private void jt_get_venc_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_get_venc_nuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_get_venc_nuevoActionPerformed

    private void jt_get_placas_nuevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_get_placas_nuevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_get_placas_nuevActionPerformed

    private void jt_get_fecha_nuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_get_fecha_nuevaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_get_fecha_nuevaActionPerformed

    private void btn_borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrarActionPerformed
        // TODO add your handling code here:
        int indice = listaclientes.getSelectedIndex();
        System.out.println("borrado cliente "+listaclientes.getSelectedItem()+"indice "+indice); 
       // Modelo.borrarenBaseDatos(indice);
        listaclientes.remove(indice);
    }//GEN-LAST:event_btn_borrarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        // TODO add your handling code here:
        
        String nuevadir = jt_get_dir_nueva.getText();
        String nuevoinicio = jt_get_fecha_nueva.getText();
        String nuevofin =jt_get_venc_nuevo.getText();
        String placanueva = jt_get_placas_nuev.getText();
        
        int ind  = listaclientes.getSelectedIndex();
        
        Modelo.agregaraBaseDatos(nuevadir,nuevoinicio,nuevofin,placanueva, ind);
  
        this.dispose();
    }//GEN-LAST:event_btn_guardarActionPerformed

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
            java.util.logging.Logger.getLogger(PaginaActualizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaginaActualizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaginaActualizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaActualizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaActualizacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_borrar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_seleccionar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jt_get_dir_nueva;
    private javax.swing.JTextField jt_get_fecha_nueva;
    private javax.swing.JTextField jt_get_placas_nuev;
    private javax.swing.JTextField jt_get_venc_nuevo;
    private javax.swing.JLabel lb_cliente;
    private javax.swing.JLabel lb_direccion;
    private javax.swing.JLabel lb_eliminar;
    private javax.swing.JLabel lb_fechainic;
    private javax.swing.JLabel lb_fechavenc;
    private javax.swing.JLabel lb_fondo;
    private javax.swing.JLabel lb_placas;
    private javax.swing.JLabel lb_titulo_act;
    private javax.swing.JComboBox<String> listaclientes;
    // End of variables declaration//GEN-END:variables
}
