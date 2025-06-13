import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class CircuitFrame extends JFrame {
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete;
    private Formula1DAO db;

    public CircuitFrame(Formula1DAO db) {
        super("Circuite");
        this.db = db;

        table = new JTable(loadCircuits());
        JScrollPane scroll = new JScrollPane(table);

        btnAdd = new JButton("Adaugă");
        btnEdit = new JButton("Editează");
        btnDelete = new JButton("Șterge");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        this.add(scroll, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            CircuitDialog dialog = new CircuitDialog(this, db, null);
            dialog.setVisible(true);
            if (dialog.isSaved()) refresh();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = (Integer) table.getValueAt(row, 0);
                CircuitDialog dialog = new CircuitDialog(this, db, id);
                dialog.setVisible(true);
                if (dialog.isSaved()) refresh();
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = (Integer) table.getValueAt(row, 0);
                try {
                    PreparedStatement ps = db.getConnection().prepareStatement("DELETE FROM Circuit WHERE CircuitID=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    refresh();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Eroare la ștergere: " + ex.getMessage());
                }
            }
        });

        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private TableModel loadCircuits() {
        try {
            Statement stmt = db.getConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery("SELECT CircuitID, Nume, Tara, LungimeKM FROM circuit");
            return new ResultSetTableModel(rs);  // presupunem că ai clasa asta helper deja
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new DefaultTableModel();
        }
    }

    private void refresh() {
        table.setModel(loadCircuits());
    }
}
