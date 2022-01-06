package Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.SynthMenuBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import Process.SymmetricConstant;
import Process.AsymmetricConstant;
import Process.Mix;

public class MixTab extends JPanel implements ActionListener {
    private JPanel pnWrapperSetup, pnWrapperOption, pnSetup, pnOption;
    private JComboBox cbKeySizeA, cbAlgorithmS, cbKeySizeS, cbModeS, cbPaddingS, cbProcess;
    private JFileChooser fileChooser;
    private JTextField tfPublicKey, tfPrivateKey, tfFileIn, tfFileOut;
    private JButton btPublicKey, btPrivateKey, btFileIn, btFileOut, btRun;
    private JLabel lbAlgorithm;

    private SymmetricConstant sc;
    private AsymmetricConstant ac;
    private Map<String, Integer[]> mapKeySize;
    private Map<String, String[]> mapPadding;

    private String algorithmS, modeS, paddingS, process, pathFileIn, pathFileOut, pathPrivate, pathPublic;
    private int keySizeS, keySizeA;
    private Notification push;

    public MixTab() {
        this.fileChooser = new JFileChooser("C:\\");
        this.sc = new SymmetricConstant();
        this.ac = new AsymmetricConstant();
        this.push = new Notification();

        this.pnWrapperSetup = new JPanel(new BorderLayout());
        this.pnWrapperSetup.add(setup(), BorderLayout.NORTH);
        this.pnWrapperSetup.setBorder(new TitledBorder("Setup"));

        this.pnWrapperOption = new JPanel(new BorderLayout());
        this.pnWrapperOption.add(option(), BorderLayout.NORTH);
        this.pnWrapperOption.setBorder(new TitledBorder("Options"));

        this.setLayout(new BorderLayout(10, 0));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(pnWrapperOption, BorderLayout.WEST);
        this.add(pnWrapperSetup, BorderLayout.CENTER);
    }

    private JPanel option() {
        this.cbKeySizeA = new JComboBox(this.ac.getKeyRSA());
        this.cbKeySizeA.setPrototypeDisplayValue("PBEWithMD5AndTripleDES");

        this.mapKeySize = this.sc.getKeySizeGroup();
        this.mapPadding = this.sc.getPaddingGroup();

        this.cbAlgorithmS = new JComboBox(this.sc.getAlgorithmGroup());
        this.cbAlgorithmS.addActionListener(this);

        this.cbKeySizeS = new JComboBox(this.sc.getKeyDES());
        this.cbModeS = new JComboBox(this.sc.getModeGroup());
        this.cbModeS.addActionListener(this);
        this.cbPaddingS = new JComboBox(this.sc.getpaddingDefault());

        JLabel lbRSA = new JLabel("RSA");
        lbRSA.setPreferredSize(new Dimension(190, 35));
        JLabel lbECB = new JLabel("ECB");
        lbECB.setPreferredSize(new Dimension(190, 35));
        JLabel lbPKCS1 = new JLabel("PKCS1Padding");
        lbPKCS1.setPreferredSize(new Dimension(190, 35));

        this.pnOption = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = gbc(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.pnOption.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(lbRSA, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.pnOption.add(new JLabel("Key size:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(this.cbKeySizeA, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.pnOption.add(new JLabel("Mode:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(lbECB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.pnOption.add(new JLabel("Padding:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(lbPKCS1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        this.pnOption.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.pnOption.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(this.cbAlgorithmS, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.pnOption.add(new JLabel("Key size:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(this.cbKeySizeS, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        this.pnOption.add(new JLabel("Mode:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(this.cbModeS, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        this.pnOption.add(new JLabel("Padding:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(this.cbPaddingS, gbc);

        return this.pnOption;
    }

    private JPanel setup() {
        this.tfFileIn = new JTextField(40);
        this.tfFileIn.setEditable(false);

        this.tfFileOut = new JTextField(40);
        this.tfFileOut.setEditable(false);

        this.tfPrivateKey = new JTextField(40);
        this.tfPrivateKey.setEditable(false);

        this.tfPublicKey = new JTextField(40);
        this.tfPublicKey.setEditable(false);

        this.cbProcess = new JComboBox(this.sc.getProcessGroup());

        this.btFileIn = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btFileIn.setPreferredSize(new Dimension(95, 38));
        this.btFileIn.setFocusable(false);
        this.btFileIn.addActionListener(this);

        this.btFileOut = new JButton(new ImageIcon(this.getClass().getResource("/Image/save.png")));
        this.btFileOut.setFocusable(false);
        this.btFileOut.addActionListener(this);

        this.btPrivateKey = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btPrivateKey.setFocusable(false);
        this.btPrivateKey.addActionListener(this);

        this.btPublicKey = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btPublicKey.setFocusable(false);
        this.btPublicKey.addActionListener(this);

        this.btRun = new JButton(new ImageIcon(this.getClass().getResource("/Image/play.png")
        ));
        this.btRun.setFocusable(false);
        this.btRun.addActionListener(this);

        this.lbAlgorithm = new JLabel("RSAWithDES");

        this.lbAlgorithm.setFont(new Font("", Font.BOLD, 18));
        this.lbAlgorithm.setForeground(new Color(74, 74, 74));
        this.lbAlgorithm.setPreferredSize(new Dimension(400, 35));

        this.pnSetup = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = gbc(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.pnSetup.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.lbAlgorithm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.pnSetup.add(new JLabel("File in:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileIn, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.pnSetup.add(new JLabel("File out:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileOut, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        this.pnSetup.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.pnSetup.add(new JLabel("Private key:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfPrivateKey, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btPrivateKey, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.pnSetup.add(new JLabel("Public key:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfPublicKey, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btPublicKey, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.pnSetup.add(new JLabel("Process:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.cbProcess, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btRun, gbc);

        return this.pnSetup;
    }

    private static GridBagConstraints gbc(int top, int bottom, int right, int left) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(top, left, bottom, right);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        return gridBagConstraints;
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

    private String getPathOpen(String title) {
        String result = null;
        this.fileChooser.setDialogTitle(title);
        int response = this.fileChooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            result = this.fileChooser.getSelectedFile().getAbsolutePath();
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.cbAlgorithmS) {
            this.lbAlgorithm.setText("RSAWith" + cbAlgorithmS.getSelectedItem().toString());

            for (Map.Entry<String, Integer[]> entry : mapKeySize.entrySet()) {
                if (entry.getKey() == this.cbAlgorithmS.getSelectedItem().toString()) {
                    this.cbKeySizeS.removeAllItems();
                    for (Integer i : entry.getValue()) {
                        this.cbKeySizeS.addItem(i);
                    }
                }
            }
        }

        if (event.getSource() == this.cbModeS) {
            for (Map.Entry<String, String[]> entry : this.mapPadding.entrySet()) {
                if (entry.getKey() == this.cbModeS.getSelectedItem().toString()) {
                    this.cbPaddingS.removeAllItems();
                    for (String i : entry.getValue()) {
                        this.cbPaddingS.addItem(i);
                    }
                }
            }
        }

        if (event.getSource() == this.btFileIn) {
            String path = getPathOpen("Choose file input");
            this.tfFileIn.setText(path);
        }

        if (event.getSource() == this.btFileOut) {
            String path = getPathSave("Save file in");
            this.tfFileOut.setText(path);
        }

        if (event.getSource() == this.btPublicKey) {
            String path = getPathOpen("Choose file public key");
            this.tfPublicKey.setText(path);
        }

        if (event.getSource() == this.btPrivateKey) {
            String path = getPathOpen("Choose file private key");
            this.tfPrivateKey.setText(path);
        }

        if (event.getSource() == this.btRun) {

            this.pathFileIn = this.tfFileIn.getText().toString();
            if (!this.pathFileIn.isEmpty()) {

                this.pathFileOut = this.tfFileOut.getText().toString();
                if (!this.pathFileOut.isEmpty()) {

                    this.algorithmS = this.cbAlgorithmS.getSelectedItem().toString();
                    this.keySizeS = Integer.parseInt(this.cbKeySizeS.getSelectedItem().toString());
                    this.keySizeA = Integer.parseInt(this.cbKeySizeA.getSelectedItem().toString());
                    this.modeS = this.cbModeS.getSelectedItem().toString();
                    this.paddingS = this.cbPaddingS.getSelectedItem().toString();

                    this.process = this.cbProcess.getSelectedItem().toString();

                    Mix mix = new Mix();
                    mix.setSymmetric(this.algorithmS, this.keySizeS, this.modeS, this.paddingS);
                    mix.setAsymmetric("RSA", this.keySizeA, "ECB", "PKCS1Padding");

                    boolean response = false;
                    switch (this.process) {
                        case SymmetricConstant.ENCRYPT:
                            this.pathPrivate = this.tfPrivateKey.getText().toString();
                            if (!this.pathPrivate.isEmpty()) {

                                boolean checkPri = mix.setPrivateKeyFromFile(this.pathPrivate);
                                if (checkPri) {

                                    response = mix.encrypt(this.pathFileIn, this.pathFileOut);
                                    this.push.status(response);
                                } else {
                                    this.push.error("Wrong public key !!! \nPlease double check public key file.");
                                }
                            } else {
                                this.push.warning("Choose your private file key !!!");
                            }
                            break;
                        case SymmetricConstant.DECRYPT:
                            this.pathPublic = this.tfPublicKey.getText().toString();
                            if (!this.pathPublic.isEmpty()) {
                                boolean checkPub = mix.setPublicKeyFromFile(this.pathPublic);
                                if (checkPub) {
                                    response = mix.decrypt(this.pathFileIn, this.pathFileOut);
                                    this.push.status(response);
                                } else {
                                    this.push.error("Wrong public key !!! \nPlease double check private key file.");
                                }
                            } else {
                                this.push.warning("Choose your public file key !!!");
                            }
                            break;
                    }
                } else {
                    this.push.warning("Choose your file out !!!");
                }
            } else {
                this.push.warning("Choose your file in !!!");
            }
        }
    }
}
