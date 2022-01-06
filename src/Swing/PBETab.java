package Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

import Process.PBEConstant;
import Process.PBE;

public class PBETab extends JPanel implements ActionListener, MouseListener {
    private JPanel pnWrapperSetup, pnWrapperOption, pnOption, pnSetup;

    private JPasswordField pfPassword;
    private JTextField tfFileIn, tfFileOut;
    private JButton btFileIn, btFileOut, btShowPass, btRun;
    private JComboBox cbAlgorithm, cbProcess;
    private JFileChooser fileChooser;
    private JLabel lbAlgorithm;

    private PBEConstant pc;
    private Notification push;

    private String pathFileIn, pathFileOut, password, process, algorithm;

    public PBETab() {
        this.fileChooser = new JFileChooser("C:\\");
        this.pc = new PBEConstant();
        push = new Notification();

        this.pnWrapperOption = new JPanel(new BorderLayout());
        this.pnWrapperOption.add(option(), BorderLayout.NORTH);
        this.pnWrapperOption.setBorder(new TitledBorder("Options"));

        this.pnWrapperSetup = new JPanel(new BorderLayout());
        this.pnWrapperSetup.setBorder(new TitledBorder("Setup"));
        this.pnWrapperSetup.add(setup(), BorderLayout.NORTH);

        this.setLayout(new BorderLayout(10, 0));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(pnWrapperOption, BorderLayout.WEST);
        this.add(pnWrapperSetup, BorderLayout.CENTER);
    }

    private JPanel option() {
        this.cbAlgorithm = new JComboBox(this.pc.getAlgorithmGroup());
        this.cbAlgorithm.addActionListener(this);

        this.pnOption = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = gbc(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.pnOption.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        this.pnOption.add(cbAlgorithm, gbc);

        return this.pnOption;
    }

    private JPanel setup() {
        this.lbAlgorithm = new JLabel("PBEWithDESAndMD5");
        this.lbAlgorithm.setFont(new Font("", Font.BOLD, 18));
        this.lbAlgorithm.setForeground(new Color(74, 74, 74));
        this.lbAlgorithm.setPreferredSize(new Dimension(400, 35));

        this.pfPassword = new JPasswordField(40);

        this.tfFileIn = new JTextField(40);
        this.tfFileIn.setEditable(false);

        this.tfFileOut = new JTextField(40);
        this.tfFileOut.setEditable(false);

        this.btFileIn = new JButton(new ImageIcon(this.getClass().getResource("/Image/browse.png")));
        this.btFileIn.setPreferredSize(new Dimension(95, 38));
        this.btFileIn.setFocusable(false);
        this.btFileIn.addActionListener(this);

        this.btFileOut = new JButton(new ImageIcon(this.getClass().getResource("/Image/save.png")));
        this.btFileOut.setFocusable(false);
        this.btFileOut.addActionListener(this);

        this.btShowPass = new JButton();
        this.btShowPass.setIcon(new ImageIcon(this.getClass().getResource("/Image/view.png")));
        this.btShowPass.setFocusable(false);
        this.btShowPass.addMouseListener(this);

        this.cbProcess = new JComboBox(this.pc.getProcessGroup());

        this.btRun = new JButton(new ImageIcon(this.getClass().getResource("/Image/play.png")));
        this.btRun.setFocusable(false);
        this.btRun.addActionListener(this);

        this.pnSetup = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = gbc(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.pnSetup.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.lbAlgorithm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.pnSetup.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.pfPassword, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btShowPass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        this.pnSetup.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.pnSetup.add(new JLabel("File in:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileIn, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.pnSetup.add(new JLabel("File out:"), gbc);
        gbc.gridx = 1;
        this.pnSetup.add(this.tfFileOut, gbc);
        gbc.gridx = 2;
        this.pnSetup.add(this.btFileOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        this.pnSetup.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

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
        if (event.getSource() == this.btFileIn) {
            String path = getPathOpen("Choose file input");
            this.tfFileIn.setText(path);
        }

        if (event.getSource() == this.btFileOut) {
            String path = getPathSave("Save file in");
            this.tfFileOut.setText(path);
        }

        if (event.getSource() == this.cbAlgorithm) {
            this.lbAlgorithm.setText(this.cbAlgorithm.getSelectedItem().toString());
        }

        if (event.getSource() == this.btRun) {
            this.password = String.valueOf(this.pfPassword.getPassword());
            if (!this.password.isEmpty()) {

                this.pathFileIn = this.tfFileIn.getText().toString();
                if (!this.pathFileIn.isEmpty()) {

                    this.pathFileOut = this.tfFileOut.getText().toString();
                    if (!this.pathFileOut.isEmpty()) {

                        this.process = this.cbProcess.getSelectedItem().toString();
                        this.algorithm = this.cbAlgorithm.getSelectedItem().toString();
                        PBE pbe = new PBE(this.algorithm, this.password);
                        boolean response = false;
                        switch (this.process) {
                            case PBEConstant.ENCRYPT:
                                response = pbe.encrypt(this.pathFileIn, this.pathFileOut);
                                this.push.status(response);
                                break;
                            case PBEConstant.DECRYPT:
                                response = pbe.decrypt(this.pathFileIn, this.pathFileOut);
                                this.push.status(response);
                                break;
                        }
                    } else {
                        this.push.warning("Choose your file out !!!");
                    }
                } else {
                    this.push.warning("Choose your file in !!!");
                }
            } else {
                push.warning("Enter your password !!!");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void mouseEntered(MouseEvent event) {
        if (event.getSource() == btShowPass) {
            this.btShowPass.setIcon(new ImageIcon(this.getClass().getResource("/Image/hide.png")));
            this.pfPassword.setEchoChar((char) 0);
        }
    }

    @Override
    public void mouseExited(MouseEvent event) {
        if (event.getSource() == btShowPass) {
            this.btShowPass.setIcon(new ImageIcon(this.getClass().getResource("/Image/view.png")));
            this.pfPassword.setEchoChar('*');
        }
    }
}
