package com.districast.inventario.vista;

import com.districast.inventario.dao.ProductoDao;
import com.districast.inventario.modelo.Producto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menu de consola para gestionar productos (insertar, consultar,
 * actualizar y eliminar).
 */
public class MenuProductos {

    private final ProductoDao productoDao;
    private final Scanner lector;

    public MenuProductos(ProductoDao productoDao) {
        this.productoDao = productoDao;
        this.lector = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1 -> registrarProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarProducto();
                    case 4 -> actualizarProducto();
                    case 5 -> eliminarProducto();
                    case 0 -> System.out.println("Hasta pronto.");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println();
        System.out.println("===== DISTRICAST - GESTION DE PRODUCTOS =====");
        System.out.println("1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto por ID");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("0. Salir");
    }

    private void registrarProducto() throws SQLException {
        System.out.println("\n--- Registrar producto ---");
        Producto nuevoProducto = new Producto(
                leerTexto("Codigo: "),
                leerTexto("Nombre: "),
                leerTexto("Categoria: "),
                leerDecimal("Precio: "),
                leerEntero("Stock: "));
        int idGenerado = productoDao.insertar(nuevoProducto);
        System.out.println("Producto registrado con ID " + idGenerado);
    }

    private void listarProductos() throws SQLException {
        List<Producto> productos = productoDao.listarTodos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.printf("%n%-4s %-9s %-38s %-13s %13s %6s%n",
                "ID", "CODIGO", "NOMBRE", "CATEGORIA", "PRECIO", "STOCK");
        productos.forEach(System.out::println);
    }

    private void buscarProducto() throws SQLException {
        int idProducto = leerEntero("ID del producto: ");
        Optional<Producto> productoEncontrado = productoDao.buscarPorId(idProducto);
        if (productoEncontrado.isPresent()) {
            System.out.println(productoEncontrado.get());
        } else {
            System.out.println("No existe un producto con ID " + idProducto);
        }
    }

    private void actualizarProducto() throws SQLException {
        int idProducto = leerEntero("ID del producto a actualizar: ");
        Optional<Producto> productoEncontrado = productoDao.buscarPorId(idProducto);
        if (productoEncontrado.isEmpty()) {
            System.out.println("No existe un producto con ID " + idProducto);
            return;
        }
        Producto producto = productoEncontrado.get();
        System.out.println("Actual: " + producto);
        System.out.println("(Deje vacio para conservar el valor actual)");

        String codigo = leerTextoOpcional("Codigo [" + producto.getCodigo() + "]: ");
        String nombre = leerTextoOpcional("Nombre [" + producto.getNombre() + "]: ");
        String categoria = leerTextoOpcional("Categoria [" + producto.getCategoria() + "]: ");
        String precio = leerTextoOpcional("Precio [" + producto.getPrecio() + "]: ");
        String stock = leerTextoOpcional("Stock [" + producto.getStock() + "]: ");

        if (!codigo.isEmpty()) producto.setCodigo(codigo);
        if (!nombre.isEmpty()) producto.setNombre(nombre);
        if (!categoria.isEmpty()) producto.setCategoria(categoria);
        try {
            if (!precio.isEmpty()) producto.setPrecio(new BigDecimal(precio));
            if (!stock.isEmpty()) producto.setStock(Integer.parseInt(stock));
        } catch (NumberFormatException e) {
            System.out.println("Precio o stock invalido. No se actualizo.");
            return;
        }

        if (productoDao.actualizar(producto)) {
            System.out.println("Producto actualizado correctamente.");
        }
    }

    private void eliminarProducto() throws SQLException {
        int idProducto = leerEntero("ID del producto a eliminar: ");
        String confirmacion = leerTexto("Confirma eliminar el producto " + idProducto + "? (S/N): ");
        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operacion cancelada.");
            return;
        }
        if (productoDao.eliminar(idProducto)) {
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("No existe un producto con ID " + idProducto);
        }
    }

    // ---------- Metodos auxiliares de lectura ----------

    private String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = lector.nextLine().trim();
        } while (texto.isEmpty());
        return texto;
    }

    private String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return lector.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private BigDecimal leerDecimal(String mensaje) {
        while (true) {
            try {
                return new BigDecimal(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor numerico valido (use punto para decimales).");
            }
        }
    }
}
