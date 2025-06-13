import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ResultFrame extends JFrame {
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete;
    private Formula1DAO db;

    public ResultFrame(Formula1DAO db) {
        super("Rezultate");
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
            ResultDialog dialog = new ResultDialog(this, db, null);
            dialog.setVisible(true);
            if (dialog.isSaved()) refresh();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = Integer.valueOf(table.getValueAt(row, 0).toString());
                ResultDialog dialog = new ResultDialog(this, db, id);
                dialog.setVisible(true);
                if (dialog.isSaved()) refresh();
            } else {
                JOptionPane.showMessageDialog(this, "Selectați un rezultat pentru editare.");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = Integer.valueOf(table.getValueAt(row, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(this, "Sigur doriți să ștergeți acest rezultat?",
                        "Confirmare ștergere", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (PreparedStatement ps = db.getConnection().prepareStatement("DELETE FROM rezultatcursa WHERE RezultatID=?")) {
                        ps.setInt(1, id);
                        ps.executeUpdate();
                        refresh();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Eroare la ștergere: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectați un rezultat pentru ștergere.");
            }
        });

        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        refresh();
    }

    private void refresh() {
        String sql = "SELECT r.RezultatID, " +
                "CONCAT(p.Nume, ' ', p.Prenume) AS Pilot, " +
                "cu.NumeCursa AS Cursa, " +
                "r.PozitieFinala AS Pozitie, " +
                "r.TimpFinal, " +
                "r.Puncte " +
                "FROM rezultatcursa r " +
                "JOIN pilot p ON r.PilotID = p.PilotID " +
                "JOIN cursa cu ON r.CursaID = cu.CursaID " +
                "ORDER BY r.RezultatID";

        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
            columnNames[i] = metaData.getColumnLabel(i + 1);
        }

        java.util.Vector<Object[]> data = new java.util.Vector<>();
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            data.add(row);
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }
        return model;
    }
}