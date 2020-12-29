/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Profesor;

/**
 *
 * @author nestor
 */
class DaoProfesorTxt extends Dao<Profesor, Integer>
{

    private final String nombreArchivo;

    DaoProfesorTxt(Map<String, String> config) throws DaoException {
        nombreArchivo = config.get(DaoProfesorFactory.NOMBRE_ARCHIVO);
        try {
            fileOpen();
        } catch (DaoException ex) {
            throw new DaoException(ex.getMessage());
        }
        todos = new ArrayList<>();
    }

    private void fileOpen() throws DaoException {
        try {
            raf = new RandomAccessFile(nombreArchivo, "rws");
        } catch (FileNotFoundException ex) {
            throw new DaoException(ex.getMessage());
        }
    }

    @Override
    public void insertar(Profesor obj) throws DaoException
    {
        try {
            fileOpen();
            raf.seek(0);
            String linea;
            Profesor profesor;
            linea = raf.readLine();
            while (linea != null && linea.length() > 0) {
                profesor = Profesor.fromString(linea);
                if (profesor.getLegajo() == obj.getLegajo()) {
                    System.out.println("Actualizado el Elemento");
                    raf.seek((raf.getFilePointer() - 2) - linea.length());
                    raf.writeBytes(obj.toString());
                    return;
                }
                linea = raf.readLine();
            }

            System.out.println("Insertando el Elemento");
            raf.seek(raf.length());
            raf.writeBytes(obj.toString());
            return;
        } catch (DaoException ex) {        
            throw new DaoException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                raf.close();
            } catch (IOException ex) {               
                throw new DaoException(ex.getMessage());
            }
        }
    }

    @Override
    public Profesor buscar(Integer id) throws DaoException {
        try {
            fileOpen();
            String linea;
            Profesor profesor;
            raf.seek(0);
            System.out.println(raf.getFilePointer());
            while ((linea = raf.readLine()) != null && linea.length() > 0) {
                profesor = Profesor.fromString(linea);
                if (profesor.getLegajo() == id) {
                    System.out.println("Elemento Encontrado");
                    return profesor;
                }
            }

        } catch (DaoException ex) {
            throw new DaoException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                raf.close();
            } catch (IOException ex) {
                Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("No Se Encontro El Elemento");
        }

        return null;
    }

    @Override
    public void actualizar(Profesor obj) {
        try {
            fileOpen();
            raf.seek(0);
            String linea;
            Profesor profesor;

            while ((linea = raf.readLine()) != null && linea.length() > 0) {
                profesor = Profesor.fromString(linea);
                if (profesor.getLegajo() == obj.getLegajo()) {
                    System.out.println("Actualizado el Elemento");
                    raf.seek((raf.getFilePointer() - 2) - linea.length());
                    raf.writeBytes(obj.toString());
                    return;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                raf.close();
            } catch (IOException ex) {
                Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void guardar(Profesor obj) {
        try {
            fileOpen();
            System.out.println("El Elemento se a Guardado");
            raf.seek(raf.length());
            raf.writeBytes(obj.toString());
        } catch (Exception ex) {
            Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                raf.close();
            } catch (IOException ex) {
                Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Profesor> getTodos() {
        try {
            fileOpen();
            String linea;
            Profesor profesor;
            todos.clear();
            raf.seek(0);
            linea = raf.readLine();

            while (linea != null && linea.length() > 0) {

                profesor = Profesor.fromString(linea);

                todos.add(profesor);

                linea = raf.readLine();
            }

            return todos;
        } catch (Exception ex) {
            Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                raf.close();
            } catch (IOException ex) {
                Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Exit2");
        return todos;
    }

    @Override
    public void Cerrar() {
        try {
            fileOpen();
        
        
            raf.close();
            todos.clear();
            System.out.println("Se Completo el guardado");

        } catch (DaoException ex) {
            Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                raf.close();
            } catch (IOException ex) {
                Logger.getLogger(DaoProfesorTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }

    List<Profesor> todos = null;
    private RandomAccessFile raf;

    @Override
    public void eliminar(Profesor obj) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
