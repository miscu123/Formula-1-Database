import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TeamDialog extends JDialog {
    private JTextField txtNumeEchipa, txtSediu, txtAnInfiintare;
    private JButton btnSave;
    private boolean saved = false;
    private Formula1DAO db;
    private Integer teamId;

    public TeamDialog(JFrame parent, Formula1DAO db, Integer teamId) {
        super(parent, "Echipă", true);
        this.db = db;
        this.teamId = teamId;

        txtNumeEchipa = new JTextField(20);
        txtSediu = new JTextField(20);
        txtAnInfiintare = new JTextField(20);  // poți schimba cu JSpinner dacă vrei validare

        btnSave = new JButton("Salvează");

        JPanel form = new JPanel(new GridLayout(4, 2));
        form.add(new JLabel("Nume Echipa:"));
        form.add(txtNumeEchipa);
        form.add(new JLabel("Sediu:"));
        form.add(txtSediu);
        form.add(new JLabel("An Infiintare:"));
        form.add(txtAnInfiintare);
        form.add(new JLabel());
        form.add(btnSave);

        this.add(form);
        this.pack();
        this.setLocationRelativeTo(parent);

        btnSave.addActionListener(e -> save());

        if (teamId != null) loadTeam();
    }

    private void loadTeam() {
        String sql = "SELECT * FROM echipa WHERE EchipaID=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNumeEchipa.setText(rs.getString("NumeEchipa"));
                txtSediu.setText(rs.getString("Sediu"));
                txtAnInfiintare.setText(rs.getString("AnInfiintare"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void save() {
        String sql = (teamId == null)
                ? "INSERT INTO echipa (NumeEchipa, Sediu, AnInfiintare) VALUES (?, ?, ?)"
                : "UPDATE echipa SET NumeEchipa=?, Sediu=?, AnInfiintare=? WHERE EchipaID=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, txtNumeEchipa.getText());
            ps.setString(2, txtSediu.getText());
            ps.setString(3, txtAnInfiintare.getText());
            if (teamId != null) ps.setInt(4, teamId);
            ps.executeUpdate();
            saved = true;
            this.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la salvare");
        }
    }

    public boolean isSaved() {
        return saved;
    }
}