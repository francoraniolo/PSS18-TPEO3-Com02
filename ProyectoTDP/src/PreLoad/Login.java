package PreLoad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.*;

public class Login {
    private static Login instance;
    private static boolean logged = false;
    private static boolean admin = false;

    public static Login getInstance() {
        instance = (instance == null) ? new Login() : instance;
        return instance;
    }

    private Login() {
        JFrame frame = new JFrame("Login");
        frame.setBounds(550,300,300,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        placeComponents(frame);
        frame.setVisible(true);
    }

    private static void placeComponents(JFrame frame){
        frame.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        frame.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        frame.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        frame.add(loginButton);

        JButton PlayButton = new JButton("Play");
        PlayButton.setBounds(180, 80, 80, 25);
        frame.add(PlayButton);

        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String thisLine = null;
                boolean logeado = false;
                try {
                    // open input stream test.txt for reading purpose.
                    BufferedReader br = new BufferedReader(new FileReader("login.txt"));
                    while ((thisLine = br.readLine()) != null && !logeado) {
                        String[] split = thisLine.split(" ");
                        String password = "";
                        char [] arrPassword = passwordText.getPassword();
                        for (int i = 0; i < arrPassword.length;i++)
                            password += arrPassword[i];
                        logeado = userText.getText().equals(split[0]) && password.equals(split[1]);
                        if (logeado && split.length > 2)
                            admin = split[2].equals("a") || split[2].equals("A");
                        else
                            admin = false;
                    }
                    if (!logeado)
                        JOptionPane.showMessageDialog(new JFrame(), "Nombre de usuario o Password Equivocado", "Datos Erróneos", JOptionPane.ERROR_MESSAGE);
                    else {
                        logged = true;
                        loginButton.setVisible(false);
                        if (admin)
                        JOptionPane.showMessageDialog(new JFrame(), "Usted acaba de loguearse como Admin", "Datos Erróneos", JOptionPane.INFORMATION_MESSAGE);
                        else
                        JOptionPane.showMessageDialog(new JFrame(), "Usted acaba de logearse como usuario regular", "Datos Erróneos", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }});

        PlayButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new Thread(LoadWindow::getInstance).start();
            }});

        }
    }