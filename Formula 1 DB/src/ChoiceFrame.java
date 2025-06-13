import javax.swing.*;
import java.awt.*;

public class ChoiceFrame extends JFrame {
    private Formula1DAO db;

    public ChoiceFrame(Formula1DAO db) {
        super("Alege datele:");
        this.db = db;

        setLayout(new GridLayout(0, 1, 10, 10));
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnPiloti = new JButton("Piloti");
        JButton btnCircuite = new JButton("Circuite");
        JButton btnCurse = new JButton("Curse");
        JButton btnRezultate = new JButton("Rezultate");
        JButton btnEchipe = new JButton("Echipe");

        add(btnPiloti);
        add(btnCircuite);
        add(btnCurse);
        add(btnRezultate);
        add(btnEchipe);

        btnPiloti.addActionListener(e -> {
            new PilotFrame(db).setVisible(true);
        });
        btnCircuite.addActionListener(e -> {
            new CircuitFrame(db).setVisible(true);
        });
        btnCurse.addActionListener(e -> {
            new RaceFrame(db).setVisible(true);
        });
        btnRezultate.addActionListener(e -> {
            new ResultFrame(db).setVisible(true);
        });
        btnEchipe.addActionListener(e -> {
            new TeamFrame(db).setVisible(true);
        });
    }
}
