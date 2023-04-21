package com.github.martinfrank.games.ninasgame.client.view;

import com.github.martinfrank.games.ninasgame.client.control.Control;
import com.github.martinfrank.games.ninasgame.client.model.Model;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewNotification;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewNotificationType;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewReference;
import com.github.martinfrank.ninasgame.model.account.AccountDetails;
import com.github.martinfrank.ninasgame.model.monster.Monster;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel implements ViewReference {

    private final Model model;
    private final Control control;
    private JPanel designerRoot;
    private JTextField userNameTextField;
    private JPasswordField userPassPasswordField;
    private JButton connectButton;
    private JPanel loginPanel;
    private JPanel accountDetailsPanel;
    private JTextField nameTextField;
    private JComboBox playersComboBox;
    private JButton playButton;

    public LoginView(Model model, Control control) {
        this.model = model;
        this.control = control;
        this.control.addViewReference(this);
        add(designerRoot);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameTextField.getText();
                String userPass = new String(userPassPasswordField.getPassword());
                connectButton.setEnabled(false);
                playButton.setEnabled(false);
                control.loadAccountDetails(userName, userPass);
            }
        });
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = playersComboBox.getSelectedItem();
                if (o == null || o instanceof String){
                    control.createNewCharacter();
                }
                if(o instanceof Monster character){
                    control.enterMap(character);
                }
            }
        });
    }

    @Override
    public void updateView(ViewNotification viewNotification) {
        if(viewNotification.type == ViewNotificationType.ACCOUNT_DETAILS){
            AccountDetails accountDetails = (AccountDetails) viewNotification.object;
            connectButton.setEnabled(true);
            if(accountDetails != null) {
                updateAccountDetails(accountDetails);
                playButton.setEnabled(true);
            }
        }
    }

    private void updateAccountDetails(AccountDetails accountDetails) {
        nameTextField.setText(accountDetails.getName());
        playersComboBox.removeAllItems();
        if (accountDetails.getPlayers().isEmpty()){
            playersComboBox.addItem("new player");
        }else{
            accountDetails.getPlayers().forEach(monster -> playersComboBox.addItem(monster));
        }
        playButton.setEnabled(true);
    }
}
