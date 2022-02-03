package table_models;

import entities.OwnerOfRedCards;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OwnerRCTableModel extends AbstractTableModel {
    private static List<OwnerOfRedCards> ownersOfRedCardsList;

    private final String[] columnNames = new String[] {
            "№", "Имя игрока", "Фамилия игрока", "Наименование клуба", "Позиция", "Количество матчей", "Красные карточки"
    };
    private final Class[] columnClass = new Class[] {
            Integer.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class
    };

    public OwnerRCTableModel(List<OwnerOfRedCards> ownersOfRedCardsList)
    {
        this.ownersOfRedCardsList = ownersOfRedCardsList;
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
        return ownersOfRedCardsList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        OwnerOfRedCards row = ownersOfRedCardsList.get(rowIndex);
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
            return row.getRed_cards();
        }
        return null;
    }
}
