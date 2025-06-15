import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RaceFrame extends JFrame {
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete, btnTop3;
    private Formula1DAO db;

    public RaceFrame(Formula1DAO db) {
        super("Curse");
        this.db = db;

        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);

        btnAdd = new JButton("Adaugă");
        btnEdit = new JButton("Editează");
        btnDelete = new JButton("Șterge");
        btnTop3 = new JButton("Top 3");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnTop3);

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

        btnTop3.addActionListener(e -> showTop3Piloti());

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

    private void showTop3Piloti() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int cursaId = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
            String sql = "{CALL CURSA_TOP3(?)}";

            try (CallableStatement cs = db.getConnection().prepareCall(sql)) {
                cs.setInt(1, cursaId);

                boolean hasResults = cs.execute();

                if (hasResults) {
                    try (ResultSet rs = cs.getResultSet()) {
                        if (rs.next()) { // doar un singur rând
                            String top3 = rs.getString("Top3"); // citește coloana Top3
                            JOptionPane.showMessageDialog(this, "Top 3 piloți pentru cursa selectată:\n" + top3);
                        } else {
                            JOptionPane.showMessageDialog(this, "Nu există rezultate pentru această cursă.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nu există rezultate pentru această cursă.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la obținerea topului.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectează o cursă pentru a vedea top 3 piloți.");
        }
    }
}
