import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnRegister;

    public RegisterFrame() {
        super("Register");

        txtUser = new JTextField(15);
        txtPass = new JPasswordField(15);
        btnRegister = new JButton("Creează cont");

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Username:"));
        panel.add(txtUser);
        panel.add(new JLabel("Password:"));
        panel.add(txtPass);
        panel.add(new JLabel(""));
        panel.add(btnRegister);

        this.add(panel);

        btnRegister.addActionListener(e -> doRegister());

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void doRegister() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completează toate câmpurile.");
            return;
        }

        try {
            Formula1DAO db = new Formula1DAO();

            // verifică dacă userul există
            String checkSql = "SELECT * FROM Utilizator WHERE Username=?";
            PreparedStatement checkPs = db.getConnection().prepareStatement(checkSql);
            checkPs.setString(1, username);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Acest username există deja.");
                return;
            }

            // inserare user nou
            String insertSql = "INSERT INTO Utilizator (Username, PasswordHash) VALUES (?, SHA2(?,256))";
            PreparedStatement ps = db.getConnection().prepareStatement(insertSql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cont creat cu succes!");
            this.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la înregistrare.");
        }
    }
}
