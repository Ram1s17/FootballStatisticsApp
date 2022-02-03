package table_models;

import entities.ClubStatistics;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StandingsTableModel extends AbstractTableModel {
    private static List<ClubStatistics> clubStatisticsList;

    private final String[] columnNames = new String[] {
            "Турнирное положение", "Наименование клуба", "Количество сыгранных матчей", "Победы",
            "Ничьи", "Поражения", "Забитые мячи", "Пропущенные мячи", "Разница мячей", "Очки"
    };
    private final Class[] columnClass = new Class[] {
            Integer.class, String.class, Integer.class, Integer.class, Integer.class,
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class
    };

    public StandingsTableModel(List<ClubStatistics> clubStatisticsList)
    {
        this.clubStatisticsList = clubStatisticsList;
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
        return clubStatisticsList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ClubStatistics row = clubStatisticsList.get(rowIndex);

        if(0 == columnIndex) {
            return row.getTournament_position();
        }
        else if(1 == columnIndex) {
            return row.getClub_name();
        }
        else if(2 == columnIndex) {
            return row.getCount_of_matches_played();
        }
        else if(3 == columnIndex) {
            return row.getWins();
        }
        else if(4 == columnIndex) {
            return row.getDraws();
        }
        else if(5 == columnIndex) {
            return row.getLosses();
        }
        else if(6 == columnIndex) {
            return row.getGoal_for();
        }
        else if(7 == columnIndex) {
            return row.getGoal_against();
        }
        else if(8 == columnIndex) {
            return row.getGoal_difference();
        }
        else if(9 == columnIndex) {
            return row.getPoints();
        }
        return null;
    }
}
