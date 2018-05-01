/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import controlador.Controlador;
import modelo.Modelo;
import vista.PaginaActualizacion;
import vista.Vista;
/**
 *
 * @author emanuel
 */
public class Main {
    public static void main(String[] args) {
        Modelo model = new Modelo();
        //Vista view = new Vista();
        //PaginaActualizacion pag= new PaginaActualizacion();
        
        //Controlador cntr = new Controlador(view,model,pag );
        
        //model.subirXML();
        //model.consultarClientes();
        //model.consultarFacturas();
        model.actualizarDireccion(1, "Prolongacion Ocote No. 48");
        model.terminarConexion();
    }
}
