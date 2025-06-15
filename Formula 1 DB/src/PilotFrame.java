import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PilotFrame extends JFrame {
    private Formula1DAO db;
    private JTextField txtFilter;
    private JButton btnFilter, btnPrev, btnNext, btnAdd, btnUpdate, btnDelete, btnMediePuncte, btnNrCurse, btnPunctaj, btnRezultate;
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
        btnMediePuncte = new JButton("Medie puncte");
        btnNrCurse = new JButton("Nr Curse");
        btnPunctaj = new JButton("Punctaj total");
        btnRezultate = new JButton("Rezultate curse");

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
        pnlBottom.add(btnMediePuncte);
        pnlBottom.add(btnNrCurse);
        pnlBottom.add(btnPunctaj);
        pnlBottom.add(btnRezultate);

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
        btnMediePuncte.addActionListener(e -> showMediePuncte());
        btnNrCurse.addActionListener(e -> showNrCurseParticipate());
        btnPunctaj.addActionListener(e -> showPunctajTotal());
        btnRezultate.addActionListener(e -> showRezultate());

        loadData();

        this.setSize(1000, 400);
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

    private void showMediePuncte() {
        int row = tblPiloti.getSelectedRow();
        if (row != -1) {
            int pilotId = (Integer) tblPiloti.getModel().getValueAt(row, 0);
            String sql = "SELECT GET_MEDIE_PUNCTE(?) AS Medie";
            try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                ps.setInt(1, pilotId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    double medie = rs.getDouble("Medie");
                    JOptionPane.showMessageDialog(this,
                            "Media punctelor pentru pilotul selectat este: " + medie);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la obținerea mediei.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectează un pilot pentru a vedea media punctelor.");
        }
    }

    private void showNrCurseParticipate() {
        int row = tblPiloti.getSelectedRow();
        if (row != -1) {
            int pilotId = (Integer) tblPiloti.getModel().getValueAt(row, 0);
            String sql = "SELECT GET_NR_CURSE_PARTICIPATE(?) AS Numar";
            try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                ps.setInt(1, pilotId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    double numar = rs.getDouble("Numar");
                    JOptionPane.showMessageDialog(this,
                            "Numarul curselor participate pentru pilotul selectat este: " + numar);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la obținerea numarului.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectează un pilot pentru a vedea media punctelor.");
        }
    }

    private void showPunctajTotal() {
        int row = tblPiloti.getSelectedRow();
        if (row != -1) {
            int pilotId = (Integer) tblPiloti.getModel().getValueAt(row, 0);
            String sql = "SELECT GET_PUNCTAJ_TOTAL(?) AS Punctaj";
            try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                ps.setInt(1, pilotId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    double punctaj = rs.getDouble("Punctaj");
                    JOptionPane.showMessageDialog(this,
                            "Punctajul total pentru pilotul selectat este: " + punctaj);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la obținerea punctajului.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectează un pilot pentru a vedea media punctelor.");
        }
    }

    private void showRezultate() {
        int row = tblPiloti.getSelectedRow();
        if (row != -1) {
            int pilotId = (Integer) tblPiloti.getModel().getValueAt(row, 0);
            String sql = "{CALL AFISEAZA_REZULTATE_PILOT(?)}";

            try (CallableStatement cs = db.getConnection().prepareCall(sql)) {
                cs.setInt(1, pilotId);
                boolean hasResults = cs.execute();

                if (hasResults) {
                    ResultSet rs = cs.getResultSet();
                    DefaultTableModel model = new DefaultTableModel(
                            new Object[]{"Cursă", "Poziție Finală", "Puncte"}, 0
                    );

                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getString("NumeCursa"),
                                rs.getInt("PozitieFinala"),
                                rs.getInt("Puncte")
                        });
                    }

                    JTable table = new JTable(model);
                    JScrollPane scrollPane = new JScrollPane(table);

                    JDialog dialog = new JDialog(this, "Rezultate pilot", true);
                    dialog.setLayout(new BorderLayout());
                    dialog.add(scrollPane, BorderLayout.CENTER);
                    dialog.setSize(400, 300);
                    dialog.setLocationRelativeTo(this);
                    dialog.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(this, "Pilotul nu are rezultate înregistrate.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la obținerea rezultatelor.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectează un pilot pentru a vedea rezultatele.");
        }
    }
}
