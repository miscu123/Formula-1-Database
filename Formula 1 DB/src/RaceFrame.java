import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RaceFrame extends JFrame {
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete;
    private Formula1DAO db;

    public RaceFrame(Formula1DAO db) {
        super("Curse");
        this.db = db;

        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);

        btnAdd = new JButton("Adaugă");
        btnEdit = new JButton("Editează");
        btnDelete = new JButton("Șterge");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            RaceDialog dialog = new RaceDialog(this, db, null);
            dialog.setVisible(true);
            if (dialog.isSaved()) refresh();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = Integer.valueOf(table.getValueAt(row, 0).toString());
                RaceDialog dialog = new RaceDialog(this, db, id);
                dialog.setVisible(true);
                if (dialog.isSaved()) refresh();
            } else {
                JOptionPane.showMessageDialog(this, "Selectați o cursă pentru editare.");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = Integer.valueOf(table.getValueAt(row, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(this, "Sigur doriți să ștergeți această cursă?",
                        "Confirmare ștergere", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (PreparedStatement ps = db.getConnection().prepareStatement("DELETE FROM Cursa WHERE CursaID=?")) {
                        ps.setInt(1, id);
                        ps.executeUpdate();
                        refresh();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Eroare la ștergere: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectați o cursă pentru ștergere.");
            }
        });

        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        refresh();
    }

    private void refresh() {
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT CursaID, CircuitID, DataCursa, NumeCursa FROM cursa ORDER BY DataCursa DESC")) {

            table.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
            table.setModel(new DefaultTableModel());
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }

        java.util.Vector<String[]> data = new java.util.Vector<>();
        while (rs.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getString(i + 1);
            }
            data.add(row);
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] rowData : data) {
            model.addRow(rowData);
        }
        return model;
    }
}
