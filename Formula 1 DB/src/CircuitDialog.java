import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CircuitDialog extends JDialog {
    private JTextField txtName, txtLocation, txtLength;
    private JButton btnSave;
    private boolean saved = false;
    private Formula1DAO db;
    private Integer circuitId;

    public CircuitDialog(JFrame parent, Formula1DAO db, Integer circuitId) {
        super(parent, "Circuit", true);
        this.db = db;
        this.circuitId = circuitId;

        txtName = new JTextField(20);
        txtLocation = new JTextField(20);
        txtLength = new JTextField(10);

        btnSave = new JButton("Salvează");

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Nume:"));
        panel.add(txtName);
        panel.add(new JLabel("Tara:"));
        panel.add(txtLocation);
        panel.add(new JLabel("Lungime (km):"));
        panel.add(txtLength);
        panel.add(new JLabel());
        panel.add(btnSave);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(parent);

        if (circuitId != null) loadCircuit();

        btnSave.addActionListener(e -> doSave());
    }

    private void loadCircuit() {
        String sql = "SELECT * FROM circuit WHERE CircuitID = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, circuitId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtName.setText(rs.getString("Nume"));
                txtLocation.setText(rs.getString("Tara"));
                txtLength.setText(Double.toString(rs.getDouble("LungimeKM")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la încărcarea circuitului.");
        }
    }

    private void doSave() {
        String name = txtName.getText().trim();
        String location = txtLocation.getText().trim();
        double length;

        try {
            length = Double.parseDouble(txtLength.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lungimea trebuie să fie un număr valid!");
            return;
        }

        try {
            if (circuitId == null) {
                String sql = "INSERT INTO Circuit (Nume, Tara, LungimeKM) VALUES (?, ?, ?)";
                try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, location);
                    ps.setDouble(3, length);
                    ps.executeUpdate();
                }
            } else {
                String sql = "UPDATE Circuit SET Nume=?, Tara=?, LungimeKM=? WHERE CircuitID=?";
                try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, location);
                    ps.setDouble(3, length);
                    ps.setInt(4, circuitId);
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
