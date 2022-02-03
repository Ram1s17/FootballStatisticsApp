package table_models;

import entities.FootballClub;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FootballClubTableModel extends AbstractTableModel{
    private static List<FootballClub> footballClubsList;

    private final String[] columnNames = new String[] {
            "Наименование клуба", "Наименование лиги", "Количество игроков", "Фамилия тренара", "Имя тренера"
    };
    private final Class[] columnClass = new Class[] {
            String.class, String.class, Integer.class, String.class, String.class
    };

    public FootballClubTableModel(List<FootballClub> footballClubsList)
    {
        this.footballClubsList = footballClubsList;
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
        return footballClubsList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        FootballClub row = footballClubsList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getClub_name();
        }
        else if(1 == columnIndex) {
            return row.getLeague_name();
        }
        else if(2 == columnIndex) {
            return row.getCount_of_players();
        }
        else if(3 == columnIndex) {
            return row.getSurname_of_coach();
        }
        else if(4 == columnIndex) {
            return row.getName_of_coach();
        }
        return null;
    }
}
