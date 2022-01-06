package Swing;

import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import Process.Symmetric;
import Process.SymmetricConstant;

public class SymmetricKey extends JFrame implements ActionListener {

    private JTextField tfFileKey, tfFileIv;
    private JComboBox cbAlgorithm, cbKeySize;
    private JButton btSaveKey, btSaveIv, btRun;
    private JPanel pnMain;
    private JFileChooser fileChooser;

    private Notification push;

    private Map<String, Integer[]> keySizeGroup;

    private SymmetricConstant sc;

    public SymmetricKey() {
        sc = new SymmetricConstant();
        fileChooser = new JFileChooser("C:\\");
        push = new Notification();

        String[] algorithms = sc.getAlgorithmGroup();
        keySizeGroup = sc.getKeySizeGroup();

        tfFileKey = new JTextField(30);
        tfFileKey.setEditable(false);

        tfFileIv = new JTextField(30);
        tfFileIv.setEditable(false);

        cbAlgorithm = new JComboBox(algorithms);
        cbAlgorithm.addActionListener(this);
        cbKeySize = new JComboBox(sc.getKeyDES());

        btSaveKey = new JButton("Save key");
        btSaveKey.setFocusable(false);
        btSaveKey.addActionListener(this);

        btSaveIv = new JButton("Save iv");
        btSaveIv.setFocusable(false);
        btSaveIv.addActionListener(this);

        btRun = new JButton(new ImageIcon(this.getClass().getResource("/Image/play.png")));
        btRun.setFocusable(false);
        btRun.addActionListener(this);

        JLabel lbTitle = new JLabel("Symmetric key");
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
        pnMain.add(cbAlgorithm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnMain.add(new JLabel("Key size:"), gbc);
        gbc.gridx = 1;
        pnMain.add(cbKeySize, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnMain.add(tfFileKey, gbc);
        gbc.gridx = 1;
        pnMain.add(btSaveKey, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        pnMain.add(tfFileIv, gbc);
        gbc.gridx = 1;
        pnMain.add(btSaveIv, gbc);

        gbc.gridy = 5;
        pnMain.add(btRun, gbc);

        add(pnMain);
        pack();
        setTitle("Generate symmetric key");
        setIconImage(new ImageIcon(this.getClass().getResource("/Image/symmetric.png")).getImage());
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
        if (event.getSource() == btSaveKey) {
            String path = getPathSave("Save key file");
            if (path != null) {
                tfFileKey.setText(path + ".enc");
            }
        }

        if (event.getSource() == btSaveIv) {
            String path = getPathSave("Save iv file");
            if (path != null) {
                tfFileIv.setText(path + ".enc");
            }
        }

        if (event.getSource() == cbAlgorithm) {
            for (Map.Entry<String, Integer[]> entry : keySizeGroup.entrySet()) {
                if (entry.getKey() == cbAlgorithm.getSelectedItem().toString()) {
                    cbKeySize.removeAllItems();
                    for (Integer i : entry.getValue()) {
                        cbKeySize.addItem(i);
                    }
                }
            }
        }

        if (event.getSource() == btRun) {
            String pathKey = tfFileKey.getText().toString();
            if (!pathKey.isEmpty()) {

                String pathIv = tfFileIv.getText().toString();
                if (!pathIv.isEmpty()) {
                    String algorithm = cbAlgorithm.getSelectedItem().toString();
                    int keySize = Integer.parseInt(cbKeySize.getSelectedItem().toString());
                    Symmetric symmetric = new Symmetric(algorithm, keySize);

                    boolean responseKey = symmetric.exportKey(pathKey, symmetric.generateKey());
                    if (responseKey) {

                        boolean responseIv = symmetric.exportIv(pathIv, symmetric.generateIv());
                        if (responseIv) {
                            this.dispose();
                            this.push.success("Generate success !!!");
                        } else {
                            this.push.error("Generate iv failed !!!");
                        }
                    } else {
                        this.push.error("Generate key failed !!!");
                    }
                } else {
                    push.warning("Choose your file iv save !!!");
                }
            } else {
                push.warning("Choose your file key save !!!");
            }

        }
    }
}
