package com.districast.inventario.dao;

import com.districast.inventario.modelo.Producto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de operaciones CRUD para la entidad Producto
 * (patron DAO: Data Access Object).
 */
public interface ProductoDao {

    /** Inserta un producto y devuelve el id generado. */
    int insertar(Producto producto) throws SQLException;

    /** Consulta todos los productos. */
    List<Producto> listarTodos() throws SQLException;

    /** Consulta un producto por su id. */
    Optional<Producto> buscarPorId(int idProducto) throws SQLException;

    /** Actualiza un producto existente. Devuelve true si se modifico. */
    boolean actualizar(Producto producto) throws SQLException;

    /** Elimina un producto por su id. Devuelve true si se elimino. */
    boolean eliminar(int idProducto) throws SQLException;
}
