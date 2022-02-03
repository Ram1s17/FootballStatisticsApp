package table_models;

import entities.ClubStatisticsForAdmin;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClubStatisticsForAdminTableModel extends AbstractTableModel {
    private static List<ClubStatisticsForAdmin> clubStatisticsForAdminList;

    private final String[] columnNames = new String[] {
            "ID статистики клуба", "Наименование клуба", "Турнирное положение", "Количество сыгранных матчей", "Победы",
            "Ничьи", "Поражения", "Забитые мячи", "Пропущенные мячи", "Разница мячей", "Очки"
    };
    private final Class[] columnClass = new Class[] {
            String.class, Integer.class, Integer.class, Integer.class, Integer.class,
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class
    };

    public ClubStatisticsForAdminTableModel(List<ClubStatisticsForAdmin> clubStatisticsForAdminList)
    {
        this.clubStatisticsForAdminList = clubStatisticsForAdminList;
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
        return clubStatisticsForAdminList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ClubStatisticsForAdmin row = clubStatisticsForAdminList.get(rowIndex);
        if (0 == columnIndex)
        {
            return row.getClub_statistics_id();
        }
        else if(1 == columnIndex) {
            return row.getClub_name();
        }
        else if(2 == columnIndex) {
            return row.getTournament_position();
        }
        else if(3 == columnIndex) {
            return row.getCount_of_matches_played();
        }
        else if(4 == columnIndex) {
            return row.getWins();
        }
        else if(5 == columnIndex) {
            return row.getDraws();
        }
        else if(6 == columnIndex) {
            return row.getLosses();
        }
        else if(7 == columnIndex) {
            return row.getGoal_for();
        }
        else if(8 == columnIndex) {
            return row.getGoal_against();
        }
        else if(9 == columnIndex) {
            return row.getGoal_difference();
        }
        else if(10 == columnIndex) {
            return row.getPoints();
        }
        return null;
    }
}
