package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GUI extends JFrame implements ActionListener {
    /* menu variable */
    private JMenuBar menuBar;
    private JMenu mnFile, mnGenerate;
    private JMenuItem iKeySymmetric, iKeyAsymmetric, iDocument, iExit;

    /* tab variable */
    private JTabbedPane tabbedPane;
    private JPanel symmetricTab, asymmetricTab, pbeTab, hashTab, mixTab;

    public GUI() {
        symmetricTab = new SymmetricTab();
        asymmetricTab = new AsymmetricTab();
        hashTab = new HashTab();
        mixTab = new MixTab();
        pbeTab = new PBETab();

        tabbedPane = new JTabbedPane();
        tabbedPane.add(symmetricTab, "Symmetric");
        tabbedPane.add(asymmetricTab, "Asymmetric");
        tabbedPane.add(mixTab, "Mix");
        tabbedPane.add(hashTab, "Hash");
        tabbedPane.add(pbeTab, "PBE");

        /* menu */
        iKeySymmetric = new JMenuItem("Symmetric key");
        iKeySymmetric.setMnemonic('S');
        iKeySymmetric.setIcon(new ImageIcon(this.getClass().getResource("/Image/symmetric.png")));
        iKeySymmetric.addActionListener(this);

        iKeyAsymmetric = new JMenuItem("Asymmetric and Mix key");
        iKeyAsymmetric.setMnemonic('A');
        iKeyAsymmetric.setMnemonic('M');
        iKeyAsymmetric.setIcon(new ImageIcon(this.getClass().getResource("/Image/asymmetric.png")));
        iKeyAsymmetric.addActionListener(this);

        iDocument = new JMenuItem("Document");
        iDocument.setIcon(new ImageIcon(this.getClass().getResource("/Image/document.png")));
        iDocument.addActionListener(this);

        iExit = new JMenuItem("Exit");
        iExit.setIcon(new ImageIcon(this.getClass().getResource("/Image/turn_off.png")));
        iExit.addActionListener(this);

        mnFile = new JMenu("Menu");
        mnFile.add(iDocument);
        mnFile.addSeparator();
        mnFile.add(iExit);

        mnGenerate = new JMenu("Generate");
        mnGenerate.add(iKeySymmetric);
        mnGenerate.addSeparator();
        mnGenerate.add(iKeyAsymmetric);

        menuBar = new JMenuBar();
        menuBar.add(mnFile);
        menuBar.add(mnGenerate);

        /* main frame */
        add(tabbedPane);
        setJMenuBar(menuBar);
    }

    public void createAndShowGUI() {
        setTitle("Protection Data");
        setIconImage(new ImageIcon(this.getClass().getResource("/Image/secure.png")).getImage());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == iKeyAsymmetric) {
            AsymmetricKey asymmetricKey = new AsymmetricKey();
            asymmetricKey.setVisible(true);
        }

        if (event.getSource() == iKeySymmetric) {
            SymmetricKey symmetricKey = new SymmetricKey();
            symmetricKey.setVisible(true);
        }

        if (event.getSource() == iDocument) {
            try {
                Desktop.getDesktop().browse(new URL("https://drive.google.com/uc?export=download&id=1l5zLk2FtpRKwOHyJhVFFQXgZMa6uHHUe").toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (event.getSource() == iExit) {
            System.exit(0);
        }
    }
}
