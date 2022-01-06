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

import Process.HashConstant;
import Process.Hash;

public class HashTab extends JPanel implements ActionListener {
    /* option variable */
    private JPanel pnWrapperOption, pnOption;
    private JComboBox cbAlgorithm, cbType;

    /* setup variable*/
    private JPanel pnWrapperSetup, pnSetup;
    private JLabel lbAlgorithm;
    private JTextArea taContent;
    private JTextField tfValue, tfCheck, tfFileIn;
    private JButton btCopy, btCheck, btFileIn, btPaste, btRun, btClear;
    private JScrollPane spContent;
    private JFileChooser fileChooser;
    private Clipboard clipboard;

    /* event variable */
    private String type, algorithm, hashValue, compareText, pathFileIn, content;
    private HashConstant hc;
    private Notification push;

    public HashTab() {
        this.hc = new HashConstant();
        this.fileChooser = new JFileChooser("C:\\");
        this.clipboard = getToolkit().getSystemClipboard();
        this.push = new Notification();

        this.pnWrapperOption = new JPanel(new BorderLayout());
        this.pnWrapperOption.add(options(), BorderLayout.NORTH);
        this.pnWrapperOption.setBorder(new TitledBorder("Options"));

        this.pnWrapperSetup = new JPanel(new BorderLayout());
        this.pnWrapperSetup.add(setup(), BorderLayout.NORTH);
        this.pnWrapperSetup.setBorder(new TitledBorder("Setup"));

        this.setLayout(new BorderLayout(10, 0));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(this.pnWrapperOption, BorderLayout.WEST);
        this.add(this.pnWrapperSetup, BorderLayout.CENTER);
    }

    private JPanel setup() {
        this.lbAlgorithm = new JLabel("MD5");
        this.lbAlgorithm.setFont(new Font("", Font.BOLD, 18));
        this.lbAlgorithm.setForeground(new Color(74, 74, 74));
        this.lbAlgorithm.setPreferredSize(new Dimension(400, 35));

        /* text var */
        this.taContent = new JTextArea();
        this.taContent.setLineWrap(true);
        this.taContent.setWrapStyleWord(true);

        this.spContent = new JScrollPane(taContent);
        this.spContent.setPreferredSize(new Dimension(0, 90));

        /* file var */
        this.tfFileIn = new JTextField(40);
        this.tfFileIn.setEditable(false);

        this.btFileIn = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btFileIn.setFocusable(false);
        this.btFileIn.setEnabled(false);
        this.btFileIn.addActionListener(this);

        this.tfValue = new JTextField(40);

        this.tfCheck = new JTextField(40);

        this.btCopy = new JButton("Copy");
        this.btCopy.setFocusable(false);
        this.btCopy.addActionListener(this);

        this.btPaste = new JButton("Paste");
        this.btPaste.setFocusable(false);
        this.btPaste.addActionListener(this);

        this.btClear = new JButton("Clear");
        this.btClear.setFocusable(false);
        this.btClear.addActionListener(this);

        this.btCheck = new JButton(new ImageIcon(this.getClass().getResource("/Image/check_mini.png")
        ));
        this.btCheck.setFocusable(false);
        this.btCheck.addActionListener(this);

        this.btRun = new JButton(new ImageIcon(this.getClass().getResource("/Image/play.png")
        ));
        this.btRun.setFocusable(false);
        this.btRun.addActionListener(this);

        this.pnSetup = new JPanel();
        this.pnSetup.setLayout(new GridBagLayout());
        GridBagConstraints gbc = gc(10, 10, 10, 10);

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
        this.pnSetup.add(this.spContent, gbc);
        gbc.gridheight = 1;
        gbc.gridx = 2;
        this.pnSetup.add(this.btPaste, gbc);
        gbc.gridy = 2;
        this.pnSetup.add(this.btClear, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        this.pnSetup.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.pnSetup.add(new JLabel("File in:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileIn, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        pnSetup.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.pnSetup.add(new JLabel("Hash value:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfValue, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btCopy, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        this.pnSetup.add(new JLabel("Compare:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfCheck, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        this.pnSetup.add(new JLabel("Process:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(new JLabel("Hash"), gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btRun, gbc);

        return pnSetup;
    }

    private JPanel options() {
        /* option */
        this.cbType = new JComboBox(this.hc.getTypeGroup());
        this.cbType.addActionListener(this);

        this.cbAlgorithm = new JComboBox(this.hc.getAlgorithmGroup());
        this.cbAlgorithm.setPrototypeDisplayValue("PBEWithMD5AndTripleDES");
        this.cbAlgorithm.addActionListener(this);

        this.pnOption = new JPanel();
        this.pnOption.setLayout(new GridBagLayout());
        GridBagConstraints gcOption = new GridBagConstraints();
        gcOption.insets = new Insets(10, 10, 10, 10);
        gcOption.anchor = GridBagConstraints.LINE_START;
        gcOption.fill = GridBagConstraints.HORIZONTAL;

        gcOption.gridx = 0;
        gcOption.gridy = 0;
        this.pnOption.add(new JLabel("Type:"), gcOption);

        gcOption.gridx = 1;
        this.pnOption.add(this.cbType, gcOption);

        gcOption.gridx = 0;
        gcOption.gridy = 1;
        this.pnOption.add(new JLabel("Algorithm:"), gcOption);

        gcOption.gridx = 1;
        this.pnOption.add(this.cbAlgorithm, gcOption);

        return pnOption;
    }

    private GridBagConstraints gc(int top, int bottom, int right, int left) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(top, left, bottom, right);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        return gridBagConstraints;
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
        if (event.getSource() == this.cbType) {
            this.type = cbType.getSelectedItem().toString();
            switch (type) {
                case HashConstant.TEXT:
                    /* disable */
                    this.btFileIn.setEnabled(false);

                    /* enable */
                    this.taContent.setEditable(true);
                    this.btPaste.setEnabled(true);
                    this.btClear.setEnabled(true);
                    break;
                case HashConstant.FILE:
                    /* disable */
                    this.taContent.setEditable(false);
                    this.btClear.setEnabled(false);

                    /* enable */
                    this.btFileIn.setEnabled(true);
                    this.btPaste.setEnabled(false);
                    break;
            }
        }

        if (event.getSource() == this.btCopy) {
            this.hashValue = this.tfValue.getText().toString();
            if (!this.hashValue.isEmpty()) {
                StringSelection ss = new StringSelection(this.hashValue);
                this.clipboard.setContents(ss, null);
            } else {
                this.push.warning("Hash value is empty !!!");
            }
        }

        if (event.getSource() == this.btPaste) {
            DataFlavor dataFlavor = DataFlavor.stringFlavor;
            if (this.clipboard.isDataFlavorAvailable(dataFlavor)) {
                try {
                    this.taContent.setText((String) this.clipboard.getData(dataFlavor));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (event.getSource() == cbAlgorithm) {
            this.lbAlgorithm.setText(cbAlgorithm.getSelectedItem().toString());
        }

        if (event.getSource() == this.btFileIn) {
            String path = getPathOpen("Choose file input");
            tfFileIn.setText(path);
            this.pathFileIn = path;
        }

        if (event.getSource() == this.btCheck) {
            this.hashValue = this.tfValue.getText().toString();
            this.compareText = this.tfCheck.getText().toString();

            if (!this.hashValue.isEmpty()) {
                if (!this.compareText.isEmpty()) {
                    Hash hash = new Hash();
                    boolean response = hash.check(this.hashValue, this.compareText);
                    if (response) {
                        this.push.success("Match !!!");
                    } else {
                        this.push.error("Not match !!!");
                    }
                } else {
                    this.push.warning("Enter your compare value !!!");
                }
            } else {
                this.push.warning("Enter your hash value !!!");
            }
        }

        if (event.getSource() == this.btRun) {
            this.type = this.cbType.getSelectedItem().toString();
            this.algorithm = this.cbAlgorithm.getSelectedItem().toString();

            switch (this.type) {
                case HashConstant.TEXT:
                    this.content = this.taContent.getText().toString();

                    if (!this.content.isEmpty()) {
                        Hash hash = new Hash(this.algorithm);
                        this.hashValue = hash.text(this.content);
                        this.tfValue.setText(this.hashValue);
                    } else {
                        this.push.warning("Enter your text !!!");
                    }
                    break;
                case HashConstant.FILE:
                    this.pathFileIn = this.tfFileIn.getText().toString();

                    if (!this.pathFileIn.isEmpty()) {
                        Hash hash = new Hash(algorithm);
                        this.hashValue = hash.file(this.pathFileIn);
                        this.tfValue.setText(this.hashValue);
                    } else {
                        this.push.warning("Choose your file in !!!");
                    }
                    break;
            }
        }

        if (event.getSource() == this.btClear){
            this.taContent.setText("");
        }
    }
}
