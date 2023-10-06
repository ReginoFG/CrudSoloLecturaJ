package edu.jdbc.crud.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import edu.jdbc.crud.dtos.LibroDto;
import edu.jdbc.crud.util.ADto;

/**
 * Implementación de la interfaz consultas
 * @author rfg - 031023
 */
public class ConsultasPostgresqlImplementacion implements ConsultasInterfaz {

	@Override
	public List<LibroDto> listarLibros(Connection conexion, List<LibroDto> listaLibrosObtenida, String isbnAConsultar) {
		
		
		ResultSet resultadoConsulta = null;
		ADto adto = new ADto();
				
		if(isbnAConsultar.isEmpty()) {
			try {
				
				Statement declaracionSQL = null;
				//Se abre una declaración
				declaracionSQL = conexion.createStatement();
				//Se define la consulta de la declaración y se ejecuta
				resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM \"gbp_almacen\".\"gbp_alm_cat_libros\"");
				//Llamada a la conversión a dtoAlumno
				adto.resultsALibrosDto(resultadoConsulta, listaLibrosObtenida);
				
			    resultadoConsulta.close();
			    declaracionSQL.close();
				
			} catch (SQLException e) {				
				System.err.println("[ERROR-ConsultasImplementacion-listarLibros] Error generando o ejecutando el listado del catálogo: " + e);
			}
		}else {
			try {
				String query = "SELECT * FROM \"gbp_almacen\".\"gbp_alm_cat_libros\" WHERE isbn = ?";
				PreparedStatement sentencia = conexion.prepareStatement(query);
				sentencia.setString(1,isbnAConsultar);
				resultadoConsulta = sentencia.executeQuery();
				//Llamada a la conversión a dtoAlumno
				adto.resultsALibrosDto(resultadoConsulta, listaLibrosObtenida);
												
			    resultadoConsulta.close();
			    sentencia.close();
				
			} catch (SQLException e) {				
				System.err.println("[ERROR-ConsultasImplementacion-listarLibros] Error generando o ejecutando el listado del catálogo opcion particular: " + e);
			}
		}
	
		return listaLibrosObtenida;
	}


}
