/*
 * Created by JFormDesigner on Thu Apr 14 00:15:48 TRT 2022
 */

package views;

import java.awt.event.*;

import models.UserImpl;
import props.User;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class PasswordReset extends  Base{
    UserImpl us=new UserImpl();

    public PasswordReset() {
        initComponents();


      txtEmail.setText(us.emailaddress);
      txtEmail.setEditable(false);


    }

    private void btnSaveClick(ActionEvent e) {
        String password=String.valueOf(txtPassword.getPassword()).trim();
        User user=new User(us.emailaddress,password);


            if(password.equals("")){
                lblError2.setText("Password can not be empty !!!!!");
            }else if(!txtValidate.getText().equals(us.userGetVerification())){
                lblError2.setText("Enter the verification code sent to your e-mail");
            }
            else{
                int status=us.userUpdate(user);
                if(status>0){
                lblError2.setText("");
                JOptionPane.showMessageDialog(this,"Update password process is successuful");
                dispose();
                }

            }
        }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        txtEmail = new JTextField();
        btnSave = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        lblError2 = new JLabel();
        txtPassword = new JPasswordField();
        txtValidate = new JTextField();
        label6 = new JLabel();

        //======== this ========
        setIconImage(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("<<");
        Container contentPane = getContentPane();

        //---- txtEmail ----
        txtEmail.setFont(txtEmail.getFont().deriveFont(txtEmail.getFont().getSize() + 2f));

        //---- btnSave ----
        btnSave.setText("SAVE");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 23));
        btnSave.setForeground(Color.white);
        btnSave.setBackground(new Color(255, 154, 47));
        btnSave.addActionListener(e -> btnSaveClick(e));

        //---- label2 ----
        label2.setText("E-Mail ");
        label2.setFont(new Font("Segoe UI", Font.BOLD, 17));
        label2.setBackground(Color.lightGray);
        label2.setForeground(Color.darkGray);

        //---- label3 ----
        label3.setText("New Password");
        label3.setFont(new Font("Segoe UI", Font.BOLD, 17));
        label3.setBackground(Color.lightGray);
        label3.setForeground(Color.darkGray);

        //---- label4 ----
        label4.setText("PASSWORD");
        label4.setForeground(new Color(255, 149, 31));
        label4.setFont(new Font("Segoe UI", Font.BOLD, 23));

        //---- label5 ----
        label5.setText("RESET");
        label5.setForeground(new Color(62, 89, 223));
        label5.setFont(new Font("Segoe UI", Font.BOLD, 23));

        //---- lblError2 ----
        lblError2.setForeground(new Color(204, 0, 0));
        lblError2.setHorizontalAlignment(SwingConstants.CENTER);
        lblError2.setFont(lblError2.getFont().deriveFont(lblError2.getFont().getStyle() | Font.BOLD, lblError2.getFont().getSize() + 1f));

        //---- txtPassword ----
        txtPassword.setFont(txtPassword.getFont().deriveFont(txtPassword.getFont().getSize() + 2f));

        //---- txtValidate ----
        txtValidate.setFont(txtValidate.getFont().deriveFont(txtValidate.getFont().getSize() + 2f));

        //---- label6 ----
        label6.setText("Verification Code");
        label6.setFont(new Font("Segoe UI", Font.BOLD, 17));
        label6.setBackground(Color.lightGray);
        label6.setForeground(Color.darkGray);

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap(87, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addComponent(label4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label5)
                                .addGap(50, 50, 50))
                            .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtValidate, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblError2, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(107, 107, 107)
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(67, 67, 67)
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addComponent(label6, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
                    .addGap(86, 86, 86))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)
                    .addComponent(label2)
                    .addGap(18, 18, 18)
                    .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(txtValidate, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lblError2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(27, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextField txtEmail;
    private JButton btnSave;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel lblError2;
    private JPasswordField txtPassword;
    private JTextField txtValidate;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
