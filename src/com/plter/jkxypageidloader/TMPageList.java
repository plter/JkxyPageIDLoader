package com.plter.jkxypageidloader;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plter on 7/6/15.
 */
public class TMPageList extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "ID";
            case 1:
                return "标题";
        }
        return "None";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CellData data = list.get(rowIndex);

        switch (columnIndex){
            case 0:
                return data.getPageId();
            case 1:
                return data.getTitle();
        }
        return null;
    }

    private List<CellData> list = new ArrayList<>();

    public void clear(){
        list.clear();
        fireTableDataChanged();
    }

    public void add(CellData data){
        list.add(data);
        fireTableDataChanged();
    }

    public CellData getCellData(int index){
        return list.get(index);
    }

    @Override
    public String toString() {

        StringBuffer content = new StringBuffer();

        for (CellData data:list){
            content.append(String.format("%d\t%s\t%s\n",data.getPageId(),data.getUrl(),data.getTitle()));
        }

        return content.toString();
    }
}
