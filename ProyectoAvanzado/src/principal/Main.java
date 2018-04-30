/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import modelo.Modelo;
import vista.Vista;
/**
 *
 * @author emanuel
 */
public class Main {
    public static void main(String[] args) {
        Modelo model = new Modelo();
        
        model.subirXML();
    }
}
