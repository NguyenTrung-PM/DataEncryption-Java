package Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Process.AsymmetricConstant;
import Process.Asymmetric;

public class AsymmetricTab extends JPanel implements ActionListener {
    private JComboBox cbPadding, cbProcess;
    private JPanel pnOption, pnWrapperOption;
    private JPanel pnSetup, pnWrapperSetup;
    private JTextField tfPublicKey, tfPrivateKey;
    private JButton btPublicKey, btPrivateKey, btCopy, btPaste, btRun, btClearIn, btClearOut;
    private JLabel lbAlgorithm;
    private JTextArea taContentIn, taContentOut;
    private JScrollPane spContentIn, spContentOut;

    private Clipboard clipboard;
    private Notification push;

    /* event variable */
    private String padding, process, pathFileIn, pathFileOut, pathPrivate, pathPublic, content, contentOut;

    private AsymmetricConstant ac;
    private JFileChooser fileChooser;

    public AsymmetricTab() {
        this.ac = new AsymmetricConstant();
        this.fileChooser = new JFileChooser("C:\\");
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        this.push = new Notification();

        this.pnWrapperOption = new JPanel(new BorderLayout());
        this.pnWrapperOption.add(options(), BorderLayout.NORTH);
        this.pnWrapperOption.setBorder(new TitledBorder("Options"));

        this.pnWrapperSetup = new JPanel(new BorderLayout());
        this.pnWrapperSetup.add(setup(), BorderLayout.NORTH);
        this.pnWrapperSetup.setBorder(new TitledBorder("Setup"));

        setLayout(new BorderLayout(10, 0));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(this.pnWrapperOption, BorderLayout.WEST);
        add(this.pnWrapperSetup, BorderLayout.CENTER);
    }

    private JPanel setup() {
        this.lbAlgorithm = new JLabel("RSA");
        this.lbAlgorithm.setFont(new Font("", Font.BOLD, 18));
        this.lbAlgorithm.setForeground(new Color(74, 74, 74));
        this.lbAlgorithm.setPreferredSize(new Dimension(400, 35));

        /* text var */
        this.taContentIn = new JTextArea();
        this.taContentIn.setLineWrap(true);
        this.taContentIn.setWrapStyleWord(true);

        this.spContentIn = new JScrollPane(this.taContentIn);
        this.spContentIn.setPreferredSize(new Dimension(0, 90));

        this.taContentOut = new JTextArea();
        this.taContentOut.setLineWrap(true);
        this.taContentOut.setWrapStyleWord(true);

        this.spContentOut = new JScrollPane(this.taContentOut);
        this.spContentOut.setPreferredSize(new Dimension(0, 90));

        this.tfPublicKey = new JTextField(40);
        this.tfPublicKey.setEditable(false);

        this.tfPrivateKey = new JTextField(40);
        this.tfPrivateKey.setEditable(false);

        this.btPublicKey = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btPublicKey.setFocusable(false);
        this.btPublicKey.addActionListener(this);

        this.btPrivateKey = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btPrivateKey.setFocusable(false);
        this.btPrivateKey.addActionListener(this);

        this.btCopy = new JButton("Copy");
        this.btCopy.setFocusable(false);
        this.btCopy.addActionListener(this);

        this.btPaste = new JButton("Paste");
        this.btPaste.setFocusable(false);
        this.btPaste.addActionListener(this);

        this.btClearIn = new JButton("Clear");
        this.btClearIn.setFocusable(false);
        this.btClearIn.addActionListener(this);

        this.btClearOut = new JButton("Clear");
        this.btClearOut.setFocusable(false);
        this.btClearOut.addActionListener(this);

        this.cbProcess = new JComboBox(ac.getProcessGroup());

        this.btRun = new JButton(new ImageIcon(this.getClass().getResource("/Image/play.png")
        ));
        this.btRun.setFocusable(false);
        this.btRun.addActionListener(this);

        /* layout */
        this.pnSetup = new JPanel();
        this.pnSetup.setLayout(new GridBagLayout());
        GridBagConstraints gbc = gbc(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.pnSetup.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.lbAlgorithm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.pnSetup.add(new JLabel("Text here"), gbc);
        gbc.gridheight = 2;
        gbc.gridx = 1;
        this.pnSetup.add(this.spContentIn, gbc);
        gbc.gridheight = 1;
        gbc.gridx = 2;
        this.pnSetup.add(this.btPaste, gbc);
        gbc.gridy = 2;
        this.pnSetup.add(this.btClearIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.pnSetup.add(new JLabel("Result:"), gbc);
        gbc.gridheight = 2;
        gbc.gridx = 1;
        this.pnSetup.add(this.spContentOut, gbc);
        gbc.gridheight = 1;
        gbc.gridx = 2;
        this.pnSetup.add(this.btCopy, gbc);
        gbc.gridy = 4;
        this.pnSetup.add(this.btClearOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        this.pnSetup.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.pnSetup.add(new JLabel("Private key:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfPrivateKey, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btPrivateKey, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        this.pnSetup.add(new JLabel("Public key:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfPublicKey, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btPublicKey, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        this.pnSetup.add(new JLabel("Process:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.cbProcess, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btRun, gbc);

        return pnSetup;
    }

    private JPanel options() {
        JLabel lbAlgorithm = new JLabel("RSA");
        lbAlgorithm.setPreferredSize(new Dimension(190, 35));
        JLabel lbMode = new JLabel("ECB");
        lbMode.setPreferredSize(new Dimension(190, 35));

        this.cbPadding = new JComboBox(ac.getPaddingGroup());
        this.cbPadding.setPrototypeDisplayValue("PBEWithMD5AndTripleDES");

        this.pnOption = new JPanel();
        this.pnOption.setLayout(new GridBagLayout());
        GridBagConstraints gcOption = new GridBagConstraints();
        gcOption.insets = new Insets(10, 10, 10, 10);
        gcOption.anchor = GridBagConstraints.LINE_START;
        gcOption.fill = GridBagConstraints.HORIZONTAL;

        gcOption.gridx = 0;
        gcOption.gridy = 0;
        this.pnOption.add(new JLabel("Algorithm:"), gcOption);
        gcOption.gridx = 1;
        this.pnOption.add(lbAlgorithm, gcOption);

        gcOption.gridx = 0;
        gcOption.gridy = 1;
        this.pnOption.add(new JLabel("Mode:"), gcOption);
        gcOption.gridx = 1;
        this.pnOption.add(lbMode, gcOption);

        gcOption.gridx = 0;
        gcOption.gridy = 2;
        this.pnOption.add(new JLabel("Padding:"), gcOption);
        gcOption.gridx = 1;
        this.pnOption.add(this.cbPadding, gcOption);

        return this.pnOption;
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

    private static GridBagConstraints gbc(int top, int bottom, int right, int left) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(top, left, bottom, right);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        return gridBagConstraints;
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.btPublicKey) {
            String path = getPathOpen("Choose file public key");
            this.tfPublicKey.setText(path);
        }

        if (event.getSource() == this.btPrivateKey) {
            String path = getPathOpen("Choose file private key");
            this.tfPrivateKey.setText(path);
        }

        if (event.getSource() == this.btCopy) {
            this.contentOut = this.taContentOut.getText().toString();
            if (!this.contentOut.isEmpty()) {
                StringSelection ss = new StringSelection(this.contentOut);
                this.clipboard.setContents(ss, null);
            } else {
                this.push.warning("Empty !!!");
            }
        }

        if (event.getSource() == this.btPaste) {
            DataFlavor dataFlavor = DataFlavor.stringFlavor;
            if (this.clipboard.isDataFlavorAvailable(dataFlavor)) {
                try {
                    this.taContentIn.setText((String) this.clipboard.getData(dataFlavor));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (event.getSource() == this.btClearIn) {
            this.taContentIn.setText("");
        }

        if (event.getSource() == this.btClearOut) {
            this.taContentOut.setText("");
        }

        if (event.getSource() == btRun) {
            this.content = this.taContentIn.getText().toString();

            if (!this.content.isEmpty()) {
                this.process = this.cbProcess.getSelectedItem().toString();
                switch (this.process) {
                    case AsymmetricConstant.ENCRYPT:
                        this.pathPublic = tfPublicKey.getText().toString();
                        this.padding = this.cbPadding.getSelectedItem().toString();
                        if (!this.pathPublic.isEmpty()) {
                            Asymmetric asymmetric = new Asymmetric(AsymmetricConstant.RSA, 0, AsymmetricConstant.ECB, this.padding);

                            boolean checkPub = asymmetric.setPublicKeyFromFile(this.pathPublic);
                            if(checkPub){
                                taContentOut.setText(asymmetric.encrypt(this.content));
                            }else{
                                this.push.error("Wrong public key !!! \nPlease double check public key file.");
                            }
                        } else {
                            this.push.warning("Choose your public file key !!!");
                        }
                        break;
                    case AsymmetricConstant.DECRYPT:
                        this.pathPrivate = tfPrivateKey.getText().toString();
                        this.padding = this.cbPadding.getSelectedItem().toString();
                        if (!this.pathPrivate.isEmpty()) {
                            Asymmetric asymmetric = new Asymmetric(AsymmetricConstant.RSA, 0, AsymmetricConstant.ECB, this.padding);
                            boolean checkPri = asymmetric.setPrivateKeyFromFile(this.pathPrivate);
                            if(checkPri){
                                taContentOut.setText(asymmetric.decrypt(this.content));
                            }else{
                                this.push.error("Wrong private key !!! \nPlease double check private key file.");
                            }
                        } else {
                            this.push.warning("Choose your private file key !!!");
                        }
                        break;
                }
            } else {
                this.push.warning("Enter your text !!!");
            }
        }
    }
}
