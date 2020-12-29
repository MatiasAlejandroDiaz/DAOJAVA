/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileNotFoundException;
import java.util.Map;
import persona.Profesor;

/**
 *
 * @author nestor
 */
public class DaoProfesorFactory
{
	private DaoProfesorFactory()
	{
	}
	
	
	
	public Dao<Profesor, Integer> crearDao(String tipoDao, Map<String, String> config) throws DaoFactoryException, DaoException
	{
		try
		{
			switch(tipoDao)
			{
				case "TXT": return new DaoProfesorTxt(config);
				case "BD": return new DaoProfesorBD(config);
				default: throw new DaoFactoryException("El dao " + tipoDao + " no existe.");
			}
		}
		catch(DaoFactoryException ex)
		{
			throw new DaoFactoryException("El archivo no existe. " + ex.getMessage());
		}
	}
	
	
	public static DaoProfesorFactory getInstance()
	{
		if(instance == null)
			instance = new DaoProfesorFactory();
		
		return instance;
	}
	
	
	
	private static DaoProfesorFactory instance = null;
	
	
	public static final String NOMBRE_ARCHIVO = "nombre_archivo";
        public static final String NOMBRE_USER = "nombre_usuario";
        public static final String NOMBRE_PASS = "nombre_password";
}
