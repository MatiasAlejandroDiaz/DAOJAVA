/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import persona.FechaUtils;
import persona.Profesor;

/**
 *
 * @author nestor
 */
class DaoProfesorBD extends Dao<Profesor, Integer> {

    Connection con;
    List<Profesor> Todos = new ArrayList<>();

    public DaoProfesorBD(Map<String, String> config) throws DaoException {

        
        String server = config.get(DaoProfesorFactory.NOMBRE_ARCHIVO);
        String user = config.get(DaoProfesorFactory.NOMBRE_USER);
        String pass = config.get(DaoProfesorFactory.NOMBRE_PASS);
        
        try {
            con = DriverManager.getConnection(server, user, pass);

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }
    }

    @Override
    public void insertar(Profesor obj) throws DaoException {
        System.out.println("Insertando un profesor en BD.");

        if (con == null) {
            System.out.println("Coneccion No establecida");
            return;
        }

        String insert
                = "insert into profesores(legajo, dni, nombre, apellido, sexo, fechaNac, estado)\n"
                + "values(?, ?, ?, ?, ?, ?, ?)\n";

        try {
            PreparedStatement ps = con.prepareStatement(insert);

            int i = 1;
            ps.setInt(i++, obj.getLegajo());
            ps.setInt(i++, obj.getDni());
            ps.setString(i++, obj.getNombre());
            ps.setString(i++, obj.getApellido());
            ps.setString(i++, String.valueOf(obj.getSexo()));
            ps.setDate(i++, FechaUtils.calendarToSqlDate(obj.getFechaNac()));
            ps.setString(i++,obj.getEstado());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }
    }

    @Override
    public Profesor buscar(Integer id) throws DaoException {
        String busqueda = "SELECT legajo,nombre,apellido,dni,fechaNac,sexo,estado FROM profesores WHERE legajo = ?";
        Profesor prof = null;
        try {
            PreparedStatement prep = con.prepareStatement(busqueda);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();
            res.next();
            if(res.wasNull())
                return null;
            prof = new Profesor();
            prof.setLegajo(res.getInt("legajo"));
            prof.setNombre(res.getString("nombre"));
            prof.setApellido(res.getString("apellido"));
            prof.setDni(res.getInt("dni"));
            prof.setFechaNac(FechaUtils.dateToCalendar(res.getDate("fechaNac")));
            prof.setSexo(res.getString("sexo").charAt(0));
            prof.setEstado(res.getString("estado"));

        } catch (Exception ex) {
            throw new DaoException(ex.getMessage());
        } 
        
        return prof;
        
    }

    @Override
    public void actualizar(Profesor obj) throws DaoException {
        String update
                = "UPDATE profesores\n"
                + "SET\n"
                + "`dni` = ?,\n"
                + "`nombre` = ?,\n"
                + "`apellido` = ?,\n"
                + "`sexo` = ?,\n"
                + "`fechaNac` = ?,\n"
                + "`estado` = ?\n"
                + "WHERE `legajo` = ?\n";
        try {
            PreparedStatement psUpdate = con.prepareStatement(update);

            int i = 1;
            psUpdate.setInt(i++, obj.getDni());
            psUpdate.setString(i++, obj.getNombre());
            psUpdate.setString(i++, obj.getApellido());
            psUpdate.setString(i++, String.valueOf(obj.getSexo()));
            psUpdate.setDate(i++, FechaUtils.calendarToSqlDate(obj.getFechaNac()));
            psUpdate.setString(i++, obj.getEstado());
            psUpdate.setInt(i++,obj.getLegajo());
            psUpdate.executeUpdate();
            
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }
    }

    @Override
    public void guardar(Profesor obj) throws DaoException {
        System.out.println("Guardando un profesor en BD.");

        if (con == null) {
            System.out.println("Coneccion No establecida");
            return;
        }

        String insert
                = "insert into profesores(legajo, dni, nombre, apellido, sexo, fechaNac, estado)\n"
                + "values(?, ?, ?, ?, ?, ?, ?)\n";

        try {
            PreparedStatement ps = con.prepareStatement(insert);

            int i = 1;
            ps.setInt(i++, obj.getLegajo());
            ps.setInt(i++, obj.getDni());
            ps.setString(i++, obj.getNombre());
            ps.setString(i++, obj.getApellido());
            ps.setString(i++, String.valueOf(obj.getSexo()));
            ps.setDate(i++, FechaUtils.calendarToSqlDate(obj.getFechaNac()));
            ps.setString(i++,obj.getEstado());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }
    }

    @Override
    public List<Profesor> getTodos() throws DaoException {
        String todos = "SELECT * FROM profesores";
        try {
            PreparedStatement getAll = con.prepareStatement(todos);
            ResultSet res = getAll.executeQuery();

            while (res.next()) {
                Profesor prof = new Profesor();
                prof.setLegajo(res.getInt("legajo"));
                prof.setNombre(res.getString("nombre"));
                prof.setApellido(res.getString("apellido"));
                prof.setDni(res.getInt("dni"));
                prof.setFechaNac(FechaUtils.dateToCalendar(res.getDate("fechaNac")));
                prof.setSexo(res.getString("sexo").charAt(0));
                prof.setEstado(res.getString("estado"));
                Todos.add(prof);
            }

        } catch (Exception ex) {
            throw new DaoException(ex.getMessage());
        } finally {
            return Todos;
        }
    }

    @Override
    public void Cerrar() throws DaoException {
        try {
            con.close();
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }

    }

    @Override
    public void eliminar(Profesor obj) throws DaoException {
        String delete = "DELETE FROM profesores where legajo = ?";
        try{
            PreparedStatement EjecDel = con.prepareStatement(delete);
            EjecDel.setInt(1, obj.getLegajo()); 
            EjecDel.execute();
            System.out.println("Elemento Eliminado Correctamente."); 
            
        }
        catch(SQLException ex){
            throw new DaoException(ex.getMessage());
        }
    }

}
