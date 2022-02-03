package table_models;

import entities.PlayerStatisticsForAdmin;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PlayerStatisticsForAdminTableModel extends AbstractTableModel {
    private static List<PlayerStatisticsForAdmin> playerStatisticsForAdminList;

    private final String[] columnNames = new String[] {
            "ID статистики игрока", "ID игрока", "Количество матчей", "Количество минут за матч", "Голы", "Ассисты",
            "Пропущенные голы", "Сухие матчи", "Желтые карточки", "Красные карточки"
    };
    private final Class[] columnClass = new Class[] {
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
            Integer.class, Integer.class, Integer.class, Integer.class
    };

    public PlayerStatisticsForAdminTableModel(List<PlayerStatisticsForAdmin> playerStatisticsForAdminList)
    {
        this.playerStatisticsForAdminList = playerStatisticsForAdminList;
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
        return playerStatisticsForAdminList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        PlayerStatisticsForAdmin row = playerStatisticsForAdminList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getPlayer_statistics_id();
        }
        else if(1 == columnIndex) {
            return row.getPlayer_id();
        }
        else if(2 == columnIndex) {
            return row.getCount_of_matches();
        }
        else if(3 == columnIndex) {
            return row.getCount_of_min_per_match();
        }
        else if(4 == columnIndex) {
            return row.getGoals();
        }
        else if(5 == columnIndex) {
            return row.getAssists();
        }
        else if(6 == columnIndex) {
            return row.getMissed_goals();
        }
        else if(7 == columnIndex) {
            return row.getClean_sheets();
        }
        else if(8 == columnIndex) {
            return row.getYellow_cards();
        }
        else if(9 == columnIndex) {
            return row.getRed_cards();
        }
        return null;
    }
}
