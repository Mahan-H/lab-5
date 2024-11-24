package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupViewModel;
import interface_adapter.verify.VerifyController;
import interface_adapter.verify.VerifyState;
import interface_adapter.verify.VerifyViewModel;

/**
 * The View for when the user is logged into the program.
 */

public class VerifyView extends JPanel implements PropertyChangeListener {

    private final String viewName = "verify";
    private final VerifyViewModel verifyViewModel;
    private final JTextField verifyCodeField = new JTextField(15);
    private VerifyController verifyController;
    private final JButton goBack;
    private final JButton verifyButton;
    private final JButton resendButton;

    public VerifyView(VerifyViewModel verifyViewModel) {
        this.verifyViewModel = verifyViewModel;
        this.verifyViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(VerifyViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        LabelTextPanel verifyCodeInfo = new LabelTextPanel(
                new JLabel(verifyViewModel.VERIFY_LABEL), verifyCodeField);

        JPanel buttons = new JPanel();
        verifyButton = new JButton(VerifyViewModel.VERIFY_BUTTON_LABEL);
        goBack = new JButton(VerifyViewModel.RETURN_BUTTON_LABEL);
        resendButton = new JButton("Resend Verification Email");

        buttons.add(verifyButton);
        buttons.add(resendButton);
        buttons.add(goBack);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        verifyButton.addActionListener(evt -> {
            if (evt.getSource().equals(verifyButton)) {
                VerifyState currentState = verifyViewModel.getState();
                String username = currentState.getUsername();
                String verifyCode = verifyCodeField.getText().trim();

                if (username == null || username.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Username is not available. Please go back and sign up again.");
                    return;
                }

                if (verifyCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter the verification code.");
                    return;
                }

                verifyController.execute(username, verifyCode);
            }
        });

        goBack.addActionListener(evt -> verifyController.switchToSignUpView());

        resendButton.addActionListener(evt -> handleResendVerificationEmail());

        this.add(title);
        this.add(verifyCodeInfo);
        this.add(buttons);
    }

    private void handleResendVerificationEmail() {
        VerifyState currentState = verifyViewModel.getState();
        String username = currentState.getUsername();
        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username is not available. Please go back and sign up again.");
            return;
        }
        verifyController.resendVerificationEmail(username);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final VerifyState state = (VerifyState) evt.getNewValue();
        }
        else if (evt.getPropertyName().equals("password")) {
            final VerifyState state = (VerifyState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "Password updated for " + state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setVerifyController(VerifyController verifyController) {
        this.verifyController = verifyController;
    }
}

