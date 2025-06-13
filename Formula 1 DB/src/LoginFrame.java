import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginFrame() {
        super("Login");

        // Inițializare componente
        txtUser = new JTextField(15);
        txtPass = new JPasswordField(15);
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        // Panel principal cu BoxLayout vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel pentru username și password
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Username:"));
        formPanel.add(txtUser);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(txtPass);

        // Panel pentru butoane
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        // Adaugă panourile în cel principal
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(10)); // spațiu între formulare și butoane
        mainPanel.add(buttonPanel);

        this.add(mainPanel);

        // Evenimente
        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> new RegisterFrame().setVisible(true));

        // Configurare fereastră
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null); // centrează fereastra
        this.setVisible(true);
    }

    private void doLogin() {
        try {
            Formula1DAO db = new Formula1DAO();
            String sql = "SELECT * FROM Utilizator WHERE Username=? AND PasswordHash=SHA2(?,256)";
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, txtUser.getText());
            ps.setString(2, new String(txtPass.getPassword()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                new ChoiceFrame(db).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credentiale incorecte");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la conectare cu baza de date.");
        }
    }

}
