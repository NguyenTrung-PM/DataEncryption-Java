package Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoadingGUI extends JFrame {
    public LoadingGUI() {
        JLabel lbIcon = new JLabel(new ImageIcon(this.getClass().getResource("/Image/protection.png")));

        JLabel lbRow_0 = new JLabel("DATA ENCRYPTION TOOL");
        lbRow_0.setFont(new Font("", Font.BOLD, 30));
        lbRow_0.setForeground(Color.WHITE);

        JLabel lbRow_1 = new JLabel("By");
        lbRow_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbRow_1.setForeground(Color.WHITE);

        JLabel lbRow_2 = new JLabel("NGUYEN NGOC TRUNG");
        lbRow_2.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lbRow_2.setForeground(Color.WHITE);

        JPanel pnContent = new JPanel();
        pnContent.setLayout(new GridBagLayout());
        pnContent.setBackground(new Color(0, 40, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnContent.add(lbRow_0, gbc);

        gbc.gridy = 1;
        pnContent.add(lbRow_1, gbc);

        gbc.gridy = 2;
        pnContent.add(lbRow_2, gbc);

        JPanel pnContentWrapper = new JPanel(new BorderLayout(0, 0));
        pnContentWrapper.setBackground(new Color(0, 40, 50));
        pnContentWrapper.add(pnContent, BorderLayout.WEST);

        JPanel pnMain = new JPanel(new BorderLayout(80, 40));
        pnMain.setBackground(new Color(0, 40, 50));
        pnMain.setBorder(new EmptyBorder(20, 40, 20, 40));
        pnMain.add(lbIcon, BorderLayout.WEST);
        pnMain.add(pnContentWrapper, BorderLayout.CENTER);

        this.setLayout(new BorderLayout(0, 0));
        this.add(pnMain, BorderLayout.CENTER);
        this.setSize(800, 350);
        this.setIconImage(new ImageIcon(this.getClass().getResource("/Image/protection.png")).getImage());
        this.setUndecorated(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
