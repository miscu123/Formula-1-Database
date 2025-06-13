import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class TeamFrame extends JFrame {
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete;
    private Formula1DAO db;

    public TeamFrame(Formula1DAO db) {
        super("Echipe");
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

        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            TeamDialog dialog = new TeamDialog(this, db, null);
            dialog.setVisible(true);
            if (dialog.isSaved()) refresh();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = (Integer) table.getValueAt(row, 0);
                TeamDialog dialog = new TeamDialog(this, db, id);
                dialog.setVisible(true);
                if (dialog.isSaved()) refresh();
            } else {
                JOptionPane.showMessageDialog(this, "Selectați o echipă pentru editare.");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = (Integer) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Sigur doriți să ștergeți această echipă?", "Confirmare", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        PreparedStatement ps = db.getConnection().prepareStatement("DELETE FROM echipa WHERE EchipaID = ?");
                        ps.setInt(1, id);
                        ps.executeUpdate();
                        refresh();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Eroare la ștergere.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectați o echipă pentru ștergere.");
            }
        });

        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        refresh();
    }

    private void refresh() {
        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EchipaID, NumeEchipa, Sediu, AnInfiintare FROM echipa");
            table.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
            table.setModel(new DefaultTableModel());
        }
    }

    // Metodă utilitară pentru a construi un TableModel din ResultSet
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Numele coloanelor
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnLabel(i + 1);
        }

        // Datele
        java.util.Vector<Object[]> data = new java.util.Vector<>();
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            data.add(row);
        }

        // Vector de Vector pentru DefaultTableModel
        java.util.Vector<java.util.Vector<Object>> dataVector = new java.util.Vector<>();
        for (Object[] rowArray : data) {
            java.util.Vector<Object> rowVector = new java.util.Vector<>();
            for (Object o : rowArray) {
                rowVector.add(o);
            }
            dataVector.add(rowVector);
        }

        return new DefaultTableModel(dataVector, new java.util.Vector<>(java.util.Arrays.asList(columnNames))) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabelul nu e editabil direct
            }
        };
    }
}