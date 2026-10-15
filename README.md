# Districast - Módulo de Productos (JDBC + MySQL)

Evidencia **GA7-220501096-AA2-EV01 – Codificación de módulos del software**
Autor: Luis Felipe Castro Varón – SENA, Análisis y Desarrollo de Software

Módulo de consola en Java que gestiona el inventario de productos de Districast
(distribuidora de ferretería y plomería) con operaciones **CRUD** sobre MySQL usando **JDBC**.

## Tecnologías
- Java 17
- Maven
- MySQL 8 + MySQL Connector/J 8.4
- Git / GitHub

## Estructura (paquetes)
```
com.districast.inventario
├── App.java                  -> punto de entrada
├── config/ConexionBD.java    -> conexión JDBC
├── modelo/Producto.java      -> entidad
├── dao/ProductoDao.java      -> interfaz CRUD (patrón DAO)
├── dao/ProductoDaoImpl.java  -> implementación con PreparedStatement
└── vista/MenuProductos.java  -> menú de consola
database/districast_inventario.sql -> script de la base de datos
```

## Funcionalidades
| Operación | Método DAO | SQL |
|---|---|---|
| Inserción | `insertar()` | INSERT |
| Consulta | `listarTodos()`, `buscarPorId()` | SELECT |
| Actualización | `actualizar()` | UPDATE |
| Eliminación | `eliminar()` | DELETE |

## Estándares de codificación aplicados
- **Paquetes:** minúsculas, dominio invertido (`com.districast.inventario.dao`).
- **Clases e interfaces:** PascalCase (`ProductoDaoImpl`, `ConexionBD`).
- **Métodos:** camelCase iniciando con verbo (`buscarPorId`, `obtenerConexion`).
- **Variables:** camelCase descriptivo (`idProducto`, `productoEncontrado`).
- **Constantes:** MAYÚSCULAS con guion bajo (`SQL_INSERTAR`, `URL`).
- **Tablas/columnas SQL:** snake_case (`id_producto`).

## Cómo ejecutar
1. Ejecutar `database/districast_inventario.sql` en MySQL Workbench.
2. Ajustar usuario y contraseña en `config/ConexionBD.java`.
3. Compilar y ejecutar:
   ```
   mvn compile exec:java
   ```
   o abrir el proyecto en NetBeans/IntelliJ/VS Code y ejecutar `App.java`.
