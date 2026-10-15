package com.districast.inventario.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase responsable de crear la conexion JDBC con la base de datos MySQL.
 *
 * Ajuste las constantes URL, USUARIO y CONTRASENA segun su instalacion local.
 */
public final class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/districast_db?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    // Constructor privado: esta clase solo ofrece metodos estaticos
    private ConexionBD() {
    }

    /**
     * Obtiene una nueva conexion a la base de datos.
     *
     * @return conexion abierta
     * @throws SQLException si no es posible conectarse
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
