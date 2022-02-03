package table_models;

import entities.Player;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

public class PlayerTableModel extends AbstractTableModel {
    private static List<Player> playersList;

    private final String[] columnNames = new String[] {
            "Фамилия игрока", "Имя игрока", "Дата рождения", "Национальность", "Наименование клуба", "Игровой номер", "Позиция"
    };
    private final Class[] columnClass = new Class[] {
            String.class, String.class, LocalDate.class, String.class, String.class, Integer.class, String.class
    };

    public PlayerTableModel(List<Player> playersList)
    {
        this.playersList = playersList;
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
        return playersList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Player row = playersList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getSurname_of_player();
        }
        else if(1 == columnIndex) {
            return row.getName_of_player();
        }
        else if(2 == columnIndex) {
            return row.getDate_of_birth();
        }
        else if(3 == columnIndex) {
            return row.getNationality();
        }
        else if(4 == columnIndex) {
            return row.getClub_name();
        }
        else if(5 == columnIndex) {
            return row.getJersey_number();
        }
        else if(6 == columnIndex) {
            return row.getPosition();
        }
        return null;
    }
}
