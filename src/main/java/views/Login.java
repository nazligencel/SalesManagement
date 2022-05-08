/*
 * Created by JFormDesigner on Wed Apr 13 01:26:22 TRT 2022
 */

package views;

import models.UserImpl;
import utils.Mail;
import utils.Util;

import java.awt.*;
import java.awt.event.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Random;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class Login extends Base {
    UserImpl us = new UserImpl();
    boolean status = false;
    static String generatedString="";

    public static void main(String[] args) {
        new Login().setVisible(true);

    }

    public Login() {
        initComponents();

    }

    public void userLogin() {
        String email = txtEmail.getText().trim().toLowerCase();
        String password = String.valueOf(txtPassword.getPassword()).trim();
        if (email.equals("")) {
            lblError.setText("Email Empty");
            txtEmail.requestFocus();

        } else if (!Util.isValidEmailAddress(email)) {

            lblError.setText("E-mail Format Error");
            txtEmail.requestFocus();
        } else if (password.length() == 0) {
            lblError.setText("Password Warning");
            txtPassword.requestFocus();

        } else {
            lblError.setText("");

            status = us.userLogin(email, password);
            if (status) {
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
                dispose();
                lblError.setText("");
            } else {
                lblError.setText("Email or Password Fail");
            }

        }

    }


    private void btnlogin(ActionEvent e) {
        userLogin();
    }

    private void txtEmailKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            userLogin();
        }
    }

    private void txtPasswordKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            userLogin();
        }
    }

    private void btnResetPassword(ActionEvent e) {
        String email = txtEmail.getText().trim().toLowerCase(Locale.ROOT);
        status = us.userGetEmail(email);
        Boolean isSend=false;
        String date=us.date;
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println(formatter.format(now));
        String dateNow=formatter.format(now);


        if (status ) {
            lblError.setText("");
            Float differenceHour =  dateDifference(dateNow,date);
            if (differenceHour > 1) {
                generatingRandomStringUnbounded_thenCorrect();
                us.userUpdateVerification(dateNow, generatedString, email);
                isSend = Mail.send("nazligencel82@gmail.com", "Activation Code", generatedString);

                if (!isSend) {
                    lblError.setText("An error occurred while sending the verification code to your e-mail. Press  forgot password? again.");
                }else{
                    new PasswordReset().setVisible(true);
                }
            }else{
                new PasswordReset().setVisible(true);
            }

        }
         else {
            lblError.setText("E-mail address is wrong !!!");
        }

    }
   public float dateDifference(String start_date, String end_date)
    {
        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");
        float hourDifference =0;
        try {

           Date d1=simpleDateFormat.parse(start_date);
            Date d2 = simpleDateFormat.parse(end_date);
            float timeDifference = d1.getTime()-d2.getTime();
           hourDifference = (Float)(timeDifference / (1000 * 60 * 60)) % 24;

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return hourDifference;
    }

    public void generatingRandomStringUnbounded_thenCorrect() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


    }

    private void thisWindowClosing(WindowEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        txtEmail = new JTextField();
        btnlogin = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label1 = new JLabel();
        label9 = new JLabel();
        lblError = new JLabel();
        txtPassword = new JPasswordField();
        btnForgotPassword = new JButton();

        //======== this ========
        setIconImage(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();

        //---- txtEmail ----
        txtEmail.setFont(txtEmail.getFont().deriveFont(txtEmail.getFont().getSize() + 2f));
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtEmailKeyReleased(e);
            }
        });

        //---- btnlogin ----
        btnlogin.setText("LOG-IN");
        btnlogin.setFont(new Font("Segoe UI", Font.BOLD, 23));
        btnlogin.setForeground(Color.white);
        btnlogin.setBackground(new Color(255, 154, 47));
        btnlogin.addActionListener(e -> btnlogin(e));

        //---- label2 ----
        label2.setText("E-Mail ");
        label2.setFont(new Font("Segoe UI", Font.BOLD, 17));
        label2.setBackground(Color.lightGray);
        label2.setForeground(Color.darkGray);

        //---- label3 ----
        label3.setText("Password");
        label3.setFont(new Font("Segoe UI", Font.BOLD, 17));
        label3.setBackground(Color.lightGray);
        label3.setForeground(Color.darkGray);

        //---- label4 ----
        label4.setText("WEL");
        label4.setForeground(new Color(255, 149, 31));
        label4.setFont(new Font("Segoe UI", Font.BOLD, 28));

        //---- label5 ----
        label5.setText("COME");
        label5.setForeground(new Color(62, 89, 223));
        label5.setFont(new Font("Segoe UI", Font.BOLD, 28));

        //---- label6 ----
        label6.setText("Best Slogan Here");
        label6.setForeground(Color.darkGray);
        label6.setFont(label6.getFont().deriveFont(label6.getFont().getStyle() & ~Font.BOLD, label6.getFont().getSize() + 8f));
        label6.setBackground(Color.lightGray);

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/P-10.jpg")));

        //---- label9 ----
        label9.setText("TOOLSTORE");
        label9.setFont(new Font("Segoe UI", Font.BOLD, 37));
        label9.setForeground(Color.darkGray);

        //---- lblError ----
        lblError.setForeground(new Color(204, 0, 0));
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setFont(lblError.getFont().deriveFont(lblError.getFont().getStyle() | Font.BOLD, lblError.getFont().getSize() + 1f));

        //---- txtPassword ----
        txtPassword.setFont(txtPassword.getFont().deriveFont(txtPassword.getFont().getSize() + 2f));
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtPasswordKeyReleased(e);
            }
        });

        //---- btnForgotPassword ----
        btnForgotPassword.setText("Forgot password ?");
        btnForgotPassword.setForeground(Color.darkGray);
        btnForgotPassword.setFont(btnForgotPassword.getFont().deriveFont(Font.ITALIC, btnForgotPassword.getFont().getSize() + 3f));
        btnForgotPassword.setBorder(null);
        btnForgotPassword.setHorizontalAlignment(SwingConstants.LEFT);
        btnForgotPassword.setBackground(Color.lightGray);
        btnForgotPassword.addActionListener(e -> btnResetPassword(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label4)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label5)
                    .addGap(158, 158, 158))
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(93, 93, 93)
                            .addComponent(label6, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(btnlogin, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))))
                    .addGap(87, 87, 87))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(65, 65, 65)
                            .addComponent(label9, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(201, 201, 201)
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(93, 93, 93)
                                    .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(93, 93, 93)
                                    .addComponent(btnForgotPassword, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                            .addGap(165, 165, 165))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label9)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label6))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                            .addGap(27, 27, 27)
                            .addComponent(label2)
                            .addGap(18, 18, 18)
                            .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnForgotPassword, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnlogin, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
                    .addGap(18, 18, 18)
                    .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextField txtEmail;
    private JButton btnlogin;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label1;
    private JLabel label9;
    private JLabel lblError;
    private JPasswordField txtPassword;
    private JButton btnForgotPassword;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
