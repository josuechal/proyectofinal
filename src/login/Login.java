package login;
import javax.swing.*;
import menu.MenuPrincipal;

import conexion.ConexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Login extends JFrame {

    JTextField txtUsuario;
    JPasswordField txtPassword;

    public Login() {

        setTitle("Login");

        setSize(400,300);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUsuario =
                new JLabel("Usuario");

        lblUsuario.setBounds(50,50,100,30);

        add(lblUsuario);

        txtUsuario = new JTextField();

        txtUsuario.setBounds(150,50,150,30);

        add(txtUsuario);

        JLabel lblPassword =
                new JLabel("Contraseña");

        lblPassword.setBounds(50,100,100,30);

        add(lblPassword);

        txtPassword = new JPasswordField();

        txtPassword.setBounds(150,100,150,30);

        add(txtPassword);

        JButton btnLogin =
                new JButton("Ingresar");

        btnLogin.setBounds(120,180,120,35);

        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String usuario =
                txtUsuario.getText();

        String password =
                String.valueOf(
                        txtPassword.getPassword()
                );

        Connection con =
                ConexionBD.conectar();

        try {

            String sql =
                    "SELECT * FROM usuarios WHERE usuario=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, usuario);

            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                String rol = rs.getString("rol");

                new MenuPrincipal(rol);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Usuario incorrecto"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        }
    }

    public static void main(String[] args) {

        new Login();

    }
}