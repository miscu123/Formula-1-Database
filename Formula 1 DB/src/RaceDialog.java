import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class RaceDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtDate;  // Format: yyyy-mm-dd
    private JButton btnSave, btnCancel;
    private boolean saved = false;
    private Formula1DAO db;
    private Integer raceId;

    public RaceDialog(Frame parent, Formula1DAO db, Integer raceId) {
        super(parent, true);
        this.db = db;
        this.raceId = raceId;
        setTitle(raceId == null ? "Adaugă Cursă" : "Editează Cursă");

        txtName = new JTextField(20);
        txtDate = new JTextField(10);

        btnSave = new JButton("Salvează");
        btnCancel = new JButton("Renunță");

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(new JLabel("Nume cursă:"));
        panel.add(txtName);
        panel.add(new JLabel("Data (YYYY-MM-DD):"));
        panel.add(txtDate);
        panel.add(btnSave);
        panel.add(btnCancel);

        add(panel);

        btnSave.addActionListener(e -> doSave());
        btnCancel.addActionListener(e -> dispose());

        if (raceId != null) loadRace();

        pack();
        setLocationRelativeTo(parent);
    }

    private void loadRace() {
        String sql = "SELECT NumeCursa, DataCursa FROM Cursa WHERE CursaID=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, raceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtName.setText(rs.getString("NumeCursa"));
                txtDate.setText(rs.getDate("DataCursa").toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void doSave() {
        String name = txtName.getText().trim();
        String dateStr = txtDate.getText().trim();

        if (name.isEmpty() || dateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completați toate câmpurile!");
            return;
        }

        try {
            LocalDate.parse(dateStr); // Validare format data
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data este invalidă!");
            return;
        }

        try {
            if (raceId == null) {
                String sql = "INSERT INTO Cursa (NumeCursa, DataCursa) VALUES (?, ?)";
                try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setDate(2, Date.valueOf(dateStr));
                    ps.executeUpdate();
                }
            } else {
                String sql = "UPDATE Cursa SET NumeCursa=?, DataCursa=? WHERE CursaID=?";
                try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setDate(2, Date.valueOf(dateStr));
                    ps.setInt(3, raceId);
                    ps.executeUpdate();
                }
            }
            saved = true;
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la salvare: " + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
