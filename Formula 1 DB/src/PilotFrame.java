import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PilotFrame extends JFrame {
    private Formula1DAO db;
    private JTextField txtFilter;
    private JButton btnFilter, btnPrev, btnNext, btnAdd, btnUpdate, btnDelete;
    private JTable tblPiloti;
    private int page = 1, pageSize = 5;

    public PilotFrame(Formula1DAO db) {
        super("Administrare Piloți");
        this.db = db;

        // Inițializare componente
        txtFilter = new JTextField(15);
        btnFilter = new JButton("Filtrează");
        btnPrev = new JButton("Pagina anterioară");
        btnNext = new JButton("Pagina următoare");
        btnAdd = new JButton("Adaugă");
        btnUpdate = new JButton("Editează");
        btnDelete = new JButton("Șterge");

        tblPiloti = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblPiloti);

        // Panel sus: căutare + filtre
        JPanel pnlTop = new JPanel();
        pnlTop.add(new JLabel("Caută pilot:"));
        pnlTop.add(txtFilter);
        pnlTop.add(btnFilter);

        // Panel jos: butoane
        JPanel pnlBottom = new JPanel();
        pnlBottom.add(btnPrev);
        pnlBottom.add(btnNext);
        pnlBottom.add(btnAdd);
        pnlBottom.add(btnUpdate);
        pnlBottom.add(btnDelete);

        // Aranjare fereastră
        this.setLayout(new BorderLayout());
        this.add(pnlTop, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(pnlBottom, BorderLayout.SOUTH);

        // Funcționalitate butoane
        btnFilter.addActionListener(e -> { page = 1; loadData(); });
        btnPrev.addActionListener(e -> { if (page > 1) { page--; loadData(); } });
        btnNext.addActionListener(e -> { page++; loadData(); });
        btnAdd.addActionListener(e -> editPilot(null));
        btnUpdate.addActionListener(e -> editSelected());
        btnDelete.addActionListener(e -> deleteSelected());

        loadData();

        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
    }

    private void loadData() {
        String filter = txtFilter.getText();
        String sql =
                "SELECT PilotID, CONCAT(Nume,' ',Prenume) AS NumeComplet, Nationalitate, DataNasterii " +
                        "FROM Pilot WHERE CONCAT(Nume,' ',Prenume) LIKE ? ORDER BY Nume LIMIT ? OFFSET ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + filter + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{"ID", "Nume Complet", "Naționalitate", "Data Nașterii"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("PilotID"),
                        rs.getString("NumeComplet"),
                        rs.getString("Nationalitate"),
                        rs.getDate("DataNasterii")
                });
            }
            tblPiloti.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la încărcarea datelor din DB.");
        }
    }

    private void editPilot(Integer pilotId) {
        // Deschide un dialog de editare/adăugare
        PilotDialog dlg = new PilotDialog(this, db, pilotId);
        dlg.setVisible(true);
        if (dlg.isSaved()) loadData();
    }

    private void editSelected() {
        int row = tblPiloti.getSelectedRow();
        if (row != -1) {
            int pilotId = (Integer) tblPiloti.getModel().getValueAt(row, 0);
            editPilot(pilotId);
        }
    }

    private void deleteSelected() {
        int row = tblPiloti.getSelectedRow();
        if (row != -1 && JOptionPane.showConfirmDialog(this, "Confirm ștergere?") == JOptionPane.YES_OPTION) {
            int id = (Integer) tblPiloti.getModel().getValueAt(row, 0);
            String sql = "DELETE FROM Pilot WHERE PilotID=?";
            try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                loadData();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la ștergere.");
            }
        }
    }
}