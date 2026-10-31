package com.alfredointerian.calculadoragrafica.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author José Alfredo Interián Polanco
 * Carga la base de datos como instancia durante toda la ejecución
 */
public class Loader {
    private static Loader instance;
    private Connection conn;
    
    private Loader () throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:info.db");
    }
    
    /**
     * Obtiene la instancia actual a la base de datos. Si no existe la crea y la devuelve.
     * @return Instancia hacia la base de datos
     * @throws SQLException Excepción para error en la conexión
     */
    public static Loader getInstance () throws SQLException {
        if (instance == null) instance = new Loader();
        return instance;
    }
}
