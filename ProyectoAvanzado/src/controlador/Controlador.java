/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.Vista;
import modelo.Modelo;
/**
 *
 * @author emanuel
 */
public class Controlador implements ActionListener{
    private Vista vista;
    private Modelo modelo;
    
    public Controlador(Vista vista, Modelo modelo){
    this.vista = vista;
    this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
