import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class PilotDialog extends JDialog {
    private JTextField txtNume, txtPrenume, txtNationalitate, txtDataN;
    private JButton btnSave;
    private boolean saved = false;
    private Formula1DAO db;
    private Integer pilotId;

    public PilotDialog(JFrame parent, Formula1DAO db, Integer pilotId) {
        super(parent, "Pilot", true);
        this.db = db;
        this.pilotId = pilotId;

        // Inițializare componente
        txtNume = new JTextField(15);
        txtPrenume = new JTextField(15);
        txtNationalitate = new JTextField(15);
        txtDataN = new JTextField(10); // format: yyyy-MM-dd
        btnSave = new JButton("Salvează");

        // Layout
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Nume:"));
        panel.add(txtNume);
        panel.add(new JLabel("Prenume:"));
        panel.add(txtPrenume);
        panel.add(new JLabel("Naționalitate:"));
        panel.add(txtNationalitate);
        panel.add(new JLabel("Data nașterii (yyyy-MM-dd):"));
        panel.add(txtDataN);
        panel.add(new JLabel(""));
        panel.add(btnSave);

        this.add(panel);

        // Dacă este editare, încarcă datele
        if (pilotId != null) loadPilot();

        // Salvare
        btnSave.addActionListener(e -> doSave());

        this.pack();
        this.setLocationRelativeTo(parent);
    }

    private void loadPilot() {
        String sql = "SELECT * FROM Pilot WHERE PilotID=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, pilotId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNume.setText(rs.getString("Nume"));
                txtPrenume.setText(rs.getString("Prenume"));
                txtNationalitate.setText(rs.getString("Nationalitate"));
                txtDataN.setText(rs.getDate("DataNasterii").toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la încărcarea datelor pilotului.");
        }
    }

    private void doSave() {
        String sql = (pilotId == null)
                ? "INSERT INTO Pilot (Nume, Prenume, Nationalitate, DataNasterii, EchipaID) VALUES (?, ?, ?, ?, 1)"
                : "UPDATE Pilot SET Nume=?, Prenume=?, Nationalitate=?, DataNasterii=? WHERE PilotID=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, txtNume.getText());
            ps.setString(2, txtPrenume.getText());
            ps.setString(3, txtNationalitate.getText());
            ps.setDate(4, Date.valueOf(txtDataN.getText().trim()));
            if (pilotId != null) ps.setInt(5, pilotId);
            ps.executeUpdate();
            saved = true;
            this.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la salvarea pilotului.");
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
