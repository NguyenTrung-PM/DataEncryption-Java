package Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import Process.Asymmetric;
import Process.AsymmetricConstant;

public class AsymmetricKey extends JFrame implements ActionListener {
    public static final String RSA = "RSA";

    private JTextField tfFilePrivate, tfFilePublic;
    private JComboBox cbKeySize;
    private JButton btSavePrivate, btSavePublic, btRun;
    private JPanel pnMain;
    private JFileChooser fileChooser;

    private AsymmetricConstant ac;
    private Notification push;

    public AsymmetricKey() {
        fileChooser = new JFileChooser("C:\\");
        this.ac = new AsymmetricConstant();
        push = new Notification();

        tfFilePrivate = new JTextField(30);
        tfFilePrivate.setEditable(false);

        tfFilePublic = new JTextField(30);
        tfFilePublic.setEditable(false);

        cbKeySize = new JComboBox(this.ac.getKeyRSA());

        btSavePrivate = new JButton("Save private in");
        btSavePrivate.setFocusable(false);
        btSavePrivate.addActionListener(this);

        btSavePublic = new JButton("Save public in");
        btSavePublic.setFocusable(false);
        btSavePublic.addActionListener(this);

        btRun = new JButton(new ImageIcon(this.getClass().getResource("/Image/play.png")));
        btRun.setFocusable(false);
        btRun.addActionListener(this);

        JLabel lbTitle = new JLabel("Asymmetric and Mix key");
        lbTitle.setFont(new Font("", Font.BOLD, 20));

        pnMain = new JPanel();
        pnMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnMain.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnMain.add(lbTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnMain.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        pnMain.add(new JLabel("RSA"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnMain.add(new JLabel("Key size:"), gbc);
        gbc.gridx = 1;
        pnMain.add(cbKeySize, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnMain.add(tfFilePrivate, gbc);
        gbc.gridx = 1;
        pnMain.add(btSavePrivate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        pnMain.add(tfFilePublic, gbc);
        gbc.gridx = 1;
        pnMain.add(btSavePublic, gbc);

        gbc.gridy = 5;
        pnMain.add(btRun, gbc);

        add(pnMain);
        pack();
        setTitle("Generate asymmetric and mix key");
        setIconImage(new ImageIcon(this.getClass().getResource("/Image/asymmetric.png")).getImage());
        setLocationRelativeTo(null);
        setResizable(false);

    }

    private String getPathSave(String title) {
        String result = null;
        this.fileChooser.setDialogTitle(title);
        int response = this.fileChooser.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            result = this.fileChooser.getSelectedFile().getAbsolutePath();
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btSavePrivate) {
            String path = getPathSave("Save private key in");
            if (path != null) {
                tfFilePrivate.setText(path + ".enc");
            }
        }

        if (event.getSource() == btSavePublic) {
            String path = getPathSave("Save public key in");
            if (path != null) {
                tfFilePublic.setText(path + ".enc");
            }
        }

        if (event.getSource() == btRun) {

            String pathPrivate = tfFilePrivate.getText().toString();
            if (!pathPrivate.isEmpty()) {

                String pathPublic = tfFilePublic.getText().toString();
                if (!pathPublic.isEmpty()) {

                    int keySize = Integer.parseInt(cbKeySize.getSelectedItem().toString());
                    Asymmetric asymmetric = new Asymmetric("RSA", keySize);
                    asymmetric.initKeypair();

                    boolean resPrivate = asymmetric.exportPublic(asymmetric.generatePublicKey(), pathPublic);
                    if (resPrivate) {

                        boolean resPublic = asymmetric.exportPrivate(asymmetric.generatePrivateKey(), pathPrivate);
                        if (resPublic) {
                            this.dispose();
                            this.push.success("Generate key success !!!");
                        } else {
                            this.push.error("Generate public key failed !!!");
                        }
                    } else {
                        this.push.error("Generate private key failed !!!");
                    }
                } else {
                    this.push.warning("Choose your public path save !!!");
                }
            } else {
                this.push.warning("Choose your private path save !!!");
            }
        }
    }
}
