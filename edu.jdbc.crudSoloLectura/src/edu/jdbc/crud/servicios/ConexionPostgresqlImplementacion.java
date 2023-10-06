package edu.jdbc.crud.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Implementación de la interfaz de conexion
 * @author 031023 - rfg
 */
public class ConexionPostgresqlImplementacion implements ConexionInterfaz {

	@Override
	public Connection generaConexion() {
		
		System.out.println("[INFO-ConexionImplementacion-generaConexion] CONEXION POSTGRESQL");
		Connection conexion = null;
		String[] parametrosConexion = configuracionConexion(); //url, user, pass
		
		if(!parametrosConexion[2].isEmpty()) { //Se controla que los parámetros de conexión se completen
			try {
				//Instancia un objeto de la clase interfaz que se le pasa
				Class.forName("org.postgresql.Driver");
				
				//Se establece la conexión
				//Si pgadmin no tiene abierta la bd, no será posible establecer conexion contra ella
				conexion = DriverManager.getConnection(parametrosConexion[0],parametrosConexion[1],parametrosConexion[2]);
				boolean esValida = conexion.isValid(50000);
				if(esValida == false) {
					conexion = null;
					System.err.println("[ERROR-ConexionPostgresqlImplementacion-generaConexion] Conexion no valida");
					
				}else {
					System.out.println("[INFO-ConexionPostgresqlImplementacion-generaConexion] Conexion valida: " + parametrosConexion[0]);
				}
	            
			} catch (ClassNotFoundException cnfe) {
				System.err.println("[ERROR-ConexionPostgresqlImplementacion-generaConexion] Error en registro driver: " + cnfe);
				conexion = null;
			} catch (SQLException jsqle) {
				System.err.println("[ERROR-ConexionPostgresqlImplementacion-generaConexion] Error en conexión (" + parametrosConexion[0] + "): " + jsqle);
				conexion = null;
			}
			
		}else {
			System.out.println("[ERROR-ConexionPostgresqlImplementacion-generaConexion] Los parametros de conexion no se han establecido correctamente");	
			conexion = null;
		}
		
		return conexion;
		
	}

	
	/**
	 * Método configura los parámetros de la conexión de conexion_postgresql.properties
	 * 031023 - rfg
	 * return ventor de string con: url, user, pass
	 */
	private String[] configuracionConexion() {
		
		String user="", pass="", port="", host="", db="", url="";
		String[] stringConfiguracion = {"","",""};
		
		Properties propiedadesConexion = new Properties();
		try {
			propiedadesConexion.load(new FileInputStream(new File("C:\\Users\\Trabajo\\dws-workspace\\edu.jdbc.crud\\src\\edu\\jdbc\\crud\\util\\conexion_postgresql.properties")));
			user = propiedadesConexion.getProperty("user");
			pass = propiedadesConexion.getProperty("pass");
			port = propiedadesConexion.getProperty("port");
			host = propiedadesConexion.getProperty("host");
			db = propiedadesConexion.getProperty("db");
			url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
			stringConfiguracion[0] = url;
			stringConfiguracion[1] = user;
			stringConfiguracion[2] = pass;
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR-ConexionPostgresqlImplementacion-configuracionConexion] - Error al acceder al fichero propiedades de conexion: " + e);
			stringConfiguracion[0] = "";
			stringConfiguracion[1] = "";
			stringConfiguracion[2] = "";
		} catch (IOException e) {
			System.err.println("[ERROR-ConexionPostgresqlImplementacion-configuracionConexion] - Error al acceder al fichero propiedades de conexion: " + e);
			stringConfiguracion[0] = "";
			stringConfiguracion[1] = "";
			stringConfiguracion[2] = "";
		}

		return stringConfiguracion;
	}


	@Override
	public void cerrarConexion(Connection conexion) {
		
		try {
			
			conexion.close();
			
		} catch (SQLException e) {
			
			System.err.println("[ERROR-ConexionPostgresqlImplementacion-cerrarConexion] - La conexion a bd no se ha cerrado correctamente: " + e);
			
			
		}
		
	}
}
