import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ResultDialog extends JDialog {
    private JComboBox<String> cbPilot, cbRace;
    private JTextField txtPosition, txtPoints, txtTime;
    private JButton btnSave, btnCancel;
    private boolean saved = false;
    private Formula1DAO db;
    private Integer resultId;

    // Maps pentru nume -> id
    private Map<String, Integer> pilotsMap = new HashMap<>();
    private Map<String, Integer> racesMap = new HashMap<>();

    public ResultDialog(Frame parent, Formula1DAO db, Integer resultId) {
        super(parent, true);
        this.db = db;
        this.resultId = resultId;
        setTitle(resultId == null ? "Adaugă Rezultat" : "Editează Rezultat");

        cbPilot = new JComboBox<>();
        cbRace = new JComboBox<>();
        txtPosition = new JTextField(5);
        txtPoints = new JTextField(5);
        txtTime = new JTextField(10);

        loadPilots();
        loadRaces();

        btnSave = new JButton("Salvează");
        btnCancel = new JButton("Renunță");

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(new JLabel("Pilot:"));
        panel.add(cbPilot);
        panel.add(new JLabel("Cursa:"));
        panel.add(cbRace);
        panel.add(new JLabel("Poziție finală:"));
        panel.add(txtPosition);
        panel.add(new JLabel("Timp final (ex: 1:23.456):"));
        panel.add(txtTime);
        panel.add(new JLabel("Puncte:"));
        panel.add(txtPoints);
        panel.add(btnSave);
        panel.add(btnCancel);

        add(panel);

        btnSave.addActionListener(e -> doSave());
        btnCancel.addActionListener(e -> dispose());

        if (resultId != null) loadResult();

        pack();
        setLocationRelativeTo(parent);
    }

    private void loadPilots() {
        String sql = "SELECT PilotID, Nume, Prenume FROM pilot ORDER BY Nume";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("PilotID");
                String name = rs.getString("Nume") + " " + rs.getString("Prenume");
                pilotsMap.put(name, id);
                cbPilot.addItem(name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadRaces() {
        String sql = "SELECT CursaID, NumeCursa FROM cursa ORDER BY DataCursa DESC";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("CursaID");
                String name = rs.getString("NumeCursa");
                racesMap.put(name, id);
                cbRace.addItem(name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadResult() {
        String sql = "SELECT RezultatID, CursaID, PilotID, PozitieFinala, TimpFinal, Puncte FROM rezultatcursa WHERE RezultatID=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, resultId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int pilotId = rs.getInt("PilotID");
                int raceId = rs.getInt("CursaID");
                txtPosition.setText(String.valueOf(rs.getInt("PozitieFinala")));
                txtPoints.setText(String.valueOf(rs.getInt("Puncte")));
                txtTime.setText(rs.getString("TimpFinal"));

                cbPilot.setSelectedItem(findKeyByValue(pilotsMap, pilotId));
                cbRace.setSelectedItem(findKeyByValue(racesMap, raceId));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private <K, V> K findKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) return entry.getKey();
        }
        return null;
    }

    private void doSave() {
        String pilotName = (String) cbPilot.getSelectedItem();
        String raceName = (String) cbRace.getSelectedItem();
        String posStr = txtPosition.getText().trim();
        String pointsStr = txtPoints.getText().trim();
        String timeStr = txtTime.getText().trim();

        if (pilotName == null || raceName == null || posStr.isEmpty() || pointsStr.isEmpty() || timeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completați toate câmpurile!");
            return;
        }

        int pilotId = pilotsMap.get(pilotName);
        int raceId = racesMap.get(raceName);
        int position, points;

        try {
            position = Integer.parseInt(posStr);
            points = Integer.parseInt(pointsStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Poziția și punctele trebuie să fie numere întregi!");
            return;
        }

        try {
            if (resultId == null) {
                String sql = "INSERT INTO rezultatcursa (CursaID, PilotID, PozitieFinala, TimpFinal, Puncte) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                    ps.setInt(1, raceId);
                    ps.setInt(2, pilotId);
                    ps.setInt(3, position);
                    ps.setString(4, timeStr);
                    ps.setInt(5, points);
                    ps.executeUpdate();
                }
            } else {
                String sql = "UPDATE rezultatcursa SET CursaID=?, PilotID=?, PozitieFinala=?, TimpFinal=?, Puncte=? WHERE RezultatID=?";
                try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
                    ps.setInt(1, raceId);
                    ps.setInt(2, pilotId);
                    ps.setInt(3, position);
                    ps.setString(4, timeStr);
                    ps.setInt(5, points);
                    ps.setInt(6, resultId);
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