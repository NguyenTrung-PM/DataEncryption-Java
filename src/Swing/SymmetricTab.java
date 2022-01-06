package Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import Process.SymmetricConstant;
import Process.Symmetric;

public class SymmetricTab extends JPanel implements ActionListener {
    private JComboBox cbAlgorithm, cbMode, cbPadding, cbProcess, cbType;
    private JPanel pnOption, pnWrapperOption;
    private JPanel pnWrapperSetup, pnSetup;
    private JTextField tfFileIn, tfFileOut, tfFileKey, tfFileIv;
    private JButton btFileIn, btFileOut, btFileKey, btFileIv, btCopy, btPaste, btRun, btClearIn, btClearOut;
    private JFileChooser fileChooser;
    private JLabel lbAlgorithm;
    private JTextArea taContentIn, taContentOut;
    private JScrollPane spContentIn, spContentOut;
    private Clipboard clipboard;

    /* event variable */
    private String type, algorithm, mode, padding, process, pathFileIn, pathFileOut, pathFileKey, content, contentOut, pathFileIv;

    private Map<String, Integer[]> mapKeySize;
    private Map<String, String[]> mapPadding;
    private SymmetricConstant sc;
    private Notification push;

    public SymmetricTab() {
        this.sc = new SymmetricConstant();
        this.fileChooser = new JFileChooser("C:\\");
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        this.push = new Notification();

        this.pnWrapperOption = new JPanel(new BorderLayout());
        this.pnWrapperOption.add(option(), BorderLayout.NORTH);
        this.pnWrapperOption.setBorder(new TitledBorder("Options"));

        this.pnWrapperSetup = new JPanel(new BorderLayout());
        this.pnWrapperSetup.add(setup(), BorderLayout.NORTH);
        this.pnWrapperSetup.setBorder(new TitledBorder("Setup"));

        this.setLayout(new BorderLayout(10, 0));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(pnWrapperOption, BorderLayout.WEST);
        this.add(pnWrapperSetup, BorderLayout.CENTER);
    }

    private JPanel option() {
        String[] algorithms = sc.getAlgorithmGroup();
        this.mapKeySize = sc.getKeySizeGroup();
        this.mapPadding = sc.getPaddingGroup();

        this.cbAlgorithm = new JComboBox(algorithms);
        this.cbAlgorithm.setPrototypeDisplayValue("PBEWithMD5AndTripleDES");
        this.cbAlgorithm.addActionListener(this);

        this.cbMode = new JComboBox(this.sc.getModeGroup());
        this.cbMode.addActionListener(this);
        this.cbPadding = new JComboBox(this.sc.getpaddingDefault());

        this.cbType = new JComboBox(this.sc.getTypeGroup());
        this.cbType.addActionListener(this);

        this.pnOption = new JPanel();
        this.pnOption.setLayout(new GridBagLayout());
        GridBagConstraints gbcOption = gbc(10, 10, 10, 10);

        gbcOption.gridx = 0;
        gbcOption.gridy = 0;
        this.pnOption.add(new JLabel("Type:"), gbcOption);
        gbcOption.gridx = 1;
        this.pnOption.add(this.cbType, gbcOption);

        gbcOption.gridx = 0;
        gbcOption.gridy = 1;
        this.pnOption.add(new JLabel("Algorithm:"), gbcOption);
        gbcOption.gridx = 1;
        this.pnOption.add(this.cbAlgorithm, gbcOption);

        gbcOption.gridx = 0;
        gbcOption.gridy = 2;
        this.pnOption.add(new JLabel("Mode:"), gbcOption);
        gbcOption.gridx = 1;
        this.pnOption.add(this.cbMode, gbcOption);

        gbcOption.gridx = 0;
        gbcOption.gridy = 3;
        this.pnOption.add(new JLabel("Padding:"), gbcOption);
        gbcOption.gridx = 1;
        this.pnOption.add(this.cbPadding, gbcOption);

        return this.pnOption;
    }

    private JPanel setup() {
        this.lbAlgorithm = new JLabel("DES");
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

        /* file var */
        this.tfFileIn = new JTextField(40);
        this.tfFileIn.setEditable(false);

        this.tfFileOut = new JTextField(40);
        this.tfFileOut.setEditable(false);

        this.btFileIn = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btFileIn.setFocusable(false);
        this.btFileIn.setEnabled(false);
        this.btFileIn.addActionListener(this);

        this.btFileOut = new JButton(new ImageIcon(this.getClass().getResource("/Image/save.png")));
        this.btFileOut.setFocusable(false);
        this.btFileOut.setEnabled(false);
        this.btFileOut.addActionListener(this);

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

        /* process var */
        this.tfFileKey = new JTextField(40);
        this.tfFileKey.setEditable(false);

        this.tfFileIv = new JTextField(40);
        this.tfFileIv.setEditable(false);

        this.btFileKey = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btFileKey.setFocusable(false);
        this.btFileKey.addActionListener(this);

        this.btFileIv = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btFileIv.setFocusable(false);
        this.btFileIv.addActionListener(this);

        this.cbProcess = new JComboBox(this.sc.getProcessGroup());

        this.btRun = new JButton(new ImageIcon(this.getClass().getResource("/Image/play.png")));
        this.btRun.setFocusable(false);
        this.btRun.addActionListener(this);

        /* layout */
        this.pnSetup = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = gbc(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.pnSetup.add(new JLabel("Algorithm: "), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.lbAlgorithm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.pnSetup.add(new JLabel("Text here:"), gbc);
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
        this.pnSetup.add(new JLabel("File in:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileIn, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        this.pnSetup.add(new JLabel("File out:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileOut, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        this.pnSetup.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 9;
        this.pnSetup.add(new JLabel("File key:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileKey, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileKey, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        this.pnSetup.add(new JLabel("File iv:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileIv, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileIv, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        this.pnSetup.add(new JLabel("Process:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.cbProcess, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btRun, gbc);

        return pnSetup;
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

    private void getValue() {
        this.algorithm = cbAlgorithm.getSelectedItem().toString();
        this.mode = this.cbMode.getSelectedItem().toString();
        this.padding = cbPadding.getSelectedItem().toString();
        this.process = this.cbProcess.getSelectedItem().toString();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.cbType) {
            this.type = this.cbType.getSelectedItem().toString();
            switch (this.type) {
                case SymmetricConstant.TEXT:
                    /* disable */
                    this.btFileIn.setEnabled(false);
                    this.btFileOut.setEnabled(false);

                    /* enable */
                    this.btCopy.setEnabled(true);
                    this.btPaste.setEnabled(true);
                    this.taContentIn.setEditable(true);
                    this.taContentOut.setEditable(true);
                    this.btClearIn.setEnabled(true);
                    this.btClearOut.setEnabled(true);
                    break;
                case SymmetricConstant.FILE:
                    /* disable */
                    this.taContentIn.setEditable(false);
                    this.taContentOut.setEditable(false);
                    this.btCopy.setEnabled(false);
                    this.btPaste.setEnabled(false);
                    this.btClearIn.setEnabled(false);
                    this.btClearOut.setEnabled(false);

                    /* enable */
                    this.btFileIn.setEnabled(true);
                    this.btFileOut.setEnabled(true);
                    break;
            }
        }

        if (event.getSource() == this.btClearIn) {
            this.taContentIn.setText("");
        }

        if (event.getSource() == this.btClearOut) {
            this.taContentOut.setText("");
        }

        if (event.getSource() == this.cbAlgorithm) {
            this.lbAlgorithm.setText(cbAlgorithm.getSelectedItem().toString());
        }

        if (event.getSource() == this.cbMode) {
            for (Map.Entry<String, String[]> entry : this.mapPadding.entrySet()) {
                if (entry.getKey() == this.cbMode.getSelectedItem().toString()) {
                    this.cbPadding.removeAllItems();
                    for (String i : entry.getValue()) {
                        this.cbPadding.addItem(i);
                    }
                }
            }
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

        if (event.getSource() == this.btFileIn) {
            String path = getPathOpen("Choose file input");
            this.tfFileIn.setText(path);
            this.pathFileIn = this.tfFileIn.getText().toString();
        }

        if (event.getSource() == this.btFileOut) {
            String path = getPathSave("Save file in");
            this.tfFileOut.setText(path);
            this.pathFileOut = this.tfFileOut.getText().toString();
        }

        if (event.getSource() == this.btFileKey) {
            String path = getPathOpen("Choose file key");
            this.tfFileKey.setText(path);
        }

        if (event.getSource() == this.btFileIv) {
            String path = getPathOpen("Choose file iv");
            this.tfFileIv.setText(path);
        }

        if (event.getSource() == this.btRun) {
            this.type = cbType.getSelectedItem().toString();
            switch (this.type) {
                case SymmetricConstant.TEXT:

                    this.content = taContentIn.getText().toString();
                    if (!this.content.isEmpty()) {

                        this.pathFileKey = this.tfFileKey.getText().toString();
                        if (!this.pathFileKey.isEmpty()) {

                            this.pathFileIv = this.tfFileIv.getText().toString();
                            if (!this.pathFileIv.isEmpty()) {
                                this.getValue();
                                Symmetric symmetric = new Symmetric(this.algorithm, 0, this.mode, this.padding);

                                boolean checkKey = symmetric.setKeyFromFile(this.pathFileKey);
                                if (checkKey) {

                                    boolean checkIv = symmetric.setIvSpecFromFile(this.pathFileIv);
                                    if (checkIv) {

                                        String result = "";
                                        switch (this.process) {
                                            case SymmetricConstant.ENCRYPT:
                                                result = symmetric.encrypt(this.content);
                                                break;
                                            case SymmetricConstant.DECRYPT:
                                                result = symmetric.decrypt(this.content);
                                                break;
                                        }
                                        taContentOut.setText(result);
                                    } else {
                                        this.push.error("Wrong iv file !!! \nPlease double check your iv file.");
                                    }
                                } else {
                                    this.push.error("Wrong key file !!! \nPlease double check your key file.");
                                }
                            } else {
                                this.push.warning("Choose your file iv !!!");
                            }
                        } else {
                            this.push.warning("Choose your file key !!!");
                        }
                    } else {
                        this.push.warning("Enter your text !!!");
                    }
                    break;
                case SymmetricConstant.FILE:
                    this.pathFileIn = this.tfFileIn.getText().toString();
                    if (!this.pathFileIn.isEmpty()) {

                        this.pathFileOut = this.tfFileOut.getText().toString();
                        if (!this.pathFileOut.isEmpty()) {

                            this.pathFileKey = this.tfFileKey.getText().toString();
                            if (!this.pathFileKey.isEmpty()) {

                                this.pathFileIv = this.tfFileIv.getText().toString();
                                if (!this.pathFileIv.isEmpty()) {
                                    this.getValue();
                                    Symmetric symmetric = new Symmetric(this.algorithm, 0, this.mode, this.padding);

                                    boolean checkKey = symmetric.setKeyFromFile(this.pathFileKey);
                                    if (checkKey) {

                                        boolean checkIv = symmetric.setIvSpecFromFile(this.pathFileIv);
                                        if (checkIv) {

                                            boolean response = false;
                                            switch (this.process) {
                                                case SymmetricConstant.ENCRYPT:
                                                    response = symmetric.encrypt(this.pathFileIn, this.pathFileOut);
                                                    this.push.status(response);
                                                    break;
                                                case SymmetricConstant.DECRYPT:
                                                    response = symmetric.decrypt(this.pathFileIn, this.pathFileOut);
                                                    this.push.status(response);
                                                    break;
                                            }
                                        } else {
                                            this.push.error("Double check your iv file !!!");
                                        }
                                    } else {
                                        this.push.error("Wrong key file !!! \nPlease double check your key file.");
                                    }
                                } else {
                                    this.push.warning("Choose your file iv !!!");
                                }
                            } else {
                                this.push.warning("Choose your file key !!!");
                            }
                        } else {
                            this.push.warning("Choose your file out !!!");
                        }
                    } else {
                        this.push.warning("Choose your file in !!!");
                    }
                    break;
            }
        }
    }
}
