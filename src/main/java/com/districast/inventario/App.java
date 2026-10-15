package com.districast.inventario;

import com.districast.inventario.dao.ProductoDaoImpl;
import com.districast.inventario.vista.MenuProductos;

/**
 * Punto de entrada del modulo de productos de Districast.
 */
public class App {

    public static void main(String[] args) {
        MenuProductos menuProductos = new MenuProductos(new ProductoDaoImpl());
        menuProductos.iniciar();
    }
}
