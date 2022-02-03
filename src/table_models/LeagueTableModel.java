package table_models;

import entities.League;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LeagueTableModel extends AbstractTableModel {
    private static List<League> leaguesList;

    private final String[] columnNames = new String[] {
            "Наименование лиги", "Количество команд", "Количество сыгранных туров", "Место проведения"
    };
    private final Class[] columnClass = new Class[] {
            String.class, Integer.class, Integer.class, String.class
    };

    public LeagueTableModel(List<League> leaguesList)
    {
        this.leaguesList = leaguesList;
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return leaguesList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        League row = leaguesList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getLeague_name();
        }
        else if(1 == columnIndex) {
            return row.getCount_of_teams();
        }
        else if(2 == columnIndex) {
            return row.getCount_of_tours_played();
        }
        else if(3 == columnIndex) {
            return row.getCountry();
        }
        return null;
    }
}
