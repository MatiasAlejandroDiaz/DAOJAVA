/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abmProfesores;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import persona.FechaUtils;
import persona.Profesor;

/**
 *
 * @author nestor
 */
public class MiModeloTabla extends AbstractTableModel
{
	public void setProfesores(List<Profesor> profesores)
	{
		this.profesores = profesores;
		refrescarTabla();
	}
	
	
	public void refrescarTabla()
	{
		fireTableDataChanged();
	}
	
	@Override
	public int getRowCount()
	{
            if(profesores == null)
                return 0;
            
            return profesores.size();
	}
	
	
	@Override
	public int getColumnCount()
	{
		return 7;
	}	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Profesor profe = profesores.get(rowIndex);
		
		switch(columnIndex)
		{
			case 0: return profe.getLegajo();
			case 1: return profe.getDni();
			case 2: return profe.getApellido();
			case 3: return profe.getNombre();
                        case 4: return profe.getSexo();
                        case 5:return FechaUtils.CalendarFormatoString(profe.getFechaNac());
                        case 6: return profe.getEstado();
			default: return null;
		}
	}
	
	
	@Override
	public String getColumnName(int column)
	{
		return NOMBRES_COL[column];
	}
	
	
	
	
	private List<Profesor> profesores;
	
	private static final String[] NOMBRES_COL = {"Legajo", "DNI", "Apellido", "Nombre","Sexo","Fecha de Nacimiento","Estado"};
}
