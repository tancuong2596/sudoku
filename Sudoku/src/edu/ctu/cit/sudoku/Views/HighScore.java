/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ctu.cit.sudoku.Views;

import edu.ctu.cit.sudoku.Databases.HighScoreDbHelper;
import edu.ctu.cit.sudoku.Databases.HighScoreSchema;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author charlie
 */
public class HighScore extends javax.swing.JDialog {
    /**
     * Creates new form HighScore
     */
    public HighScore(java.awt.Frame parent, boolean modal, HighScoreDbHelper dbHelper, WindowAdapter wa) {
        super(parent, modal);
        initComponents();
        this.dbHelper = dbHelper;
        this.addWindowListener(wa);
        updateInfo();
    }
    
    public void updateInfo() {
        try {
            Object[] columnNames = {
                "Player",
                "Time"
            };
            ArrayList<Object[]> selectAllData = dbHelper.selectAll();
            Object[][] rowData = new Object[selectAllData.size()][];
            for (int i = 0; i < selectAllData.size(); i++) {
                Object[] row = selectAllData.get(i);
                Integer time = (Integer) row[2];
                rowData[i] = new Object[] {
                    row[1],
                    String.format("%02d:%02d", time / 60, time % 60)
                };
            }
            JTable table = new JTable(rowData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane, BorderLayout.CENTER);
            this.setSize(300, 150);
            this.setLocationRelativeTo(this.getParent());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("High Score");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    HighScoreDbHelper dbHelper;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
