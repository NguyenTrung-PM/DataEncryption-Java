package Swing;

import javax.swing.*;

public class Notification {

    public void success(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("/Image/check.png")
        ));
    }

    public void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Failed", JOptionPane.ERROR_MESSAGE, new ImageIcon(this.getClass().getResource("/Image/error.png")
        ));
    }

    public void warning(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE, new ImageIcon(this.getClass().getResource("/Image/warning.png")
        ));
    }

    public void status(boolean status) {
        if (status) {
            success("Success !!!");
        } else {
            error("Failed !!!");
        }
    }
}
