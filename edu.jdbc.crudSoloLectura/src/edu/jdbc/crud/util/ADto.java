package edu.jdbc.crud.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.jdbc.crud.dtos.LibroDto;
/**
 * Clase de utilidad que contiene los métodos de paso a DTO
 * 220923 - rfg
 */
public class ADto {
	
	/**
	 * Método que pasa un resultSet con libros a lista de LibroDto
	 * @param resultadoConsulta
	 * @author rfg - 220923
	 */
	public void resultsALibrosDto(ResultSet resultadoConsulta, List<LibroDto> listaLibrosObtenida){
		
		//Leemos el resultado de la consulta hasta que no queden filas
		try {
			while (resultadoConsulta.next()) {
				
				listaLibrosObtenida.add(new LibroDto(resultadoConsulta.getLong("id_libro"),
						resultadoConsulta.getString("titulo"),
						resultadoConsulta.getString("autor"),
						resultadoConsulta.getString("isbn"),
						resultadoConsulta.getInt("edicion"))
						);
			}
			
			int i = listaLibrosObtenida.size();
			System.out.println("[INFORMACIÓN-ADto-resultsALibrosDto] Número libros convertidos: "+i);
			
		} catch (SQLException e) {
			System.err.println("[ERROR-ADto-resultsALibrosDto] Error al pasar el result set a lista de LibroDto" + e);
		}
		
	}
}
