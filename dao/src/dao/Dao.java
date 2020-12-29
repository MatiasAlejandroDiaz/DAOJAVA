/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author nestor
 */
public abstract class Dao<T, K>
{
	public abstract void insertar(T obj) throws DaoException;
	public abstract T buscar(K id)throws DaoException;
	public abstract void actualizar(T obj)throws DaoException;
	public abstract void guardar(T obj)throws DaoException;
	public abstract List<T> getTodos()throws DaoException;
        public abstract void eliminar (T obj) throws DaoException;
        public abstract void Cerrar()throws DaoException;
}
