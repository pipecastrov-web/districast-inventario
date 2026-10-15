package com.districast.inventario.dao;

import com.districast.inventario.config.ConexionBD;
import com.districast.inventario.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion JDBC de ProductoDao.
 * Usa PreparedStatement para evitar inyeccion SQL y
 * try-with-resources para cerrar conexiones automaticamente.
 */
public class ProductoDaoImpl implements ProductoDao {

    private static final String SQL_INSERTAR =
            "INSERT INTO producto (codigo, nombre, categoria, precio, stock) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR_TODOS =
            "SELECT id_producto, codigo, nombre, categoria, precio, stock FROM producto ORDER BY id_producto";
    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id_producto, codigo, nombre, categoria, precio, stock FROM producto WHERE id_producto = ?";
    private static final String SQL_ACTUALIZAR =
            "UPDATE producto SET codigo = ?, nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id_producto = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM producto WHERE id_producto = ?";

    @Override
    public int insertar(Producto producto) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, producto.getCodigo());
            sentencia.setString(2, producto.getNombre());
            sentencia.setString(3, producto.getCategoria());
            sentencia.setBigDecimal(4, producto.getPrecio());
            sentencia.setInt(5, producto.getStock());
            sentencia.executeUpdate();

            try (ResultSet llavesGeneradas = sentencia.getGeneratedKeys()) {
                if (llavesGeneradas.next()) {
                    int idGenerado = llavesGeneradas.getInt(1);
                    producto.setIdProducto(idGenerado);
                    return idGenerado;
                }
            }
            return -1;
        }
    }

    @Override
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }
        }
        return productos;
    }

    @Override
    public Optional<Producto> buscarPorId(int idProducto) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_POR_ID)) {

            sentencia.setInt(1, idProducto);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearProducto(resultado));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean actualizar(Producto producto) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            sentencia.setString(1, producto.getCodigo());
            sentencia.setString(2, producto.getNombre());
            sentencia.setString(3, producto.getCategoria());
            sentencia.setBigDecimal(4, producto.getPrecio());
            sentencia.setInt(5, producto.getStock());
            sentencia.setInt(6, producto.getIdProducto());
            return sentencia.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int idProducto) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {

            sentencia.setInt(1, idProducto);
            return sentencia.executeUpdate() > 0;
        }
    }

    /** Convierte la fila actual del ResultSet en un objeto Producto. */
    private Producto mapearProducto(ResultSet resultado) throws SQLException {
        return new Producto(
                resultado.getInt("id_producto"),
                resultado.getString("codigo"),
                resultado.getString("nombre"),
                resultado.getString("categoria"),
                resultado.getBigDecimal("precio"),
                resultado.getInt("stock"));
    }
}
