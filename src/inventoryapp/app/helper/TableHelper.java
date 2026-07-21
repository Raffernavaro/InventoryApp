/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryapp.app.helper;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Indruyy
 */
public class TableHelper {

    public static DefaultTableModel getModel(JTable table) {
        return (DefaultTableModel) table.getModel();
    }

    public static void clearTable(JTable table) {
        getModel(table).setRowCount(0);
    }

    public static void addRow(JTable table, Object... data) {
        getModel(table).addRow(data);
    }

    public DefaultTableModel getTableModel(JTable tbl) {
        return TableHelper.getModel(tbl);
    }
}
