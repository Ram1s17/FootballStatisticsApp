package table_models;

import entities.OwnerOfYellowCards;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OwnerYCTableModel extends AbstractTableModel {
    private static List<OwnerOfYellowCards> ownersOfYellowCardsList;

    private final String[] columnNames = new String[] {
            "№", "Имя игрока", "Фамилия игрока", "Наименование клуба", "Позиция", "Количество матчей", "Желтые карточки"
    };
    private final Class[] columnClass = new Class[] {
            Integer.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class
    };

    public OwnerYCTableModel(List<OwnerOfYellowCards> ownersOfYellowCardsList)
    {
        this.ownersOfYellowCardsList = ownersOfYellowCardsList;
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
        return ownersOfYellowCardsList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        OwnerOfYellowCards row = ownersOfYellowCardsList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getPlace();
        }
        else if(1 == columnIndex) {
            return row.getName_of_player();
        }
        else if(2 == columnIndex) {
            return row.getSurname_of_player();
        }
        else if(3 == columnIndex) {
            return row.getClub_name();
        }
        else if(4 == columnIndex) {
            return row.getPosition();
        }
        else if(5 == columnIndex) {
            return row.getCount_of_matches();
        }
        else if(6 == columnIndex) {
            return row.getYellow_cards();
        }
        return null;
    }
}
