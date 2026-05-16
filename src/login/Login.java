package login;

import conexion.ConexionBD;
import menu.MenuPrincipal;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    JTextField txtUsuario;
    JPasswordField txtPassword;
    JCheckBox verPassword;

    public Login() {

        setTitle("Sistema Inventario");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        // PANEL IZQUIERDO
        JPanel panelIzq = new JPanel();
        panelIzq.setBounds(0, 0, 360, 450);
        panelIzq.setBackground(new Color(0, 102, 204));
        panelIzq.setLayout(null);
        add(panelIzq);

        JLabel titulo = new JLabel("Sistema de Inventario");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBounds(45, 130, 300, 40);
        panelIzq.add(titulo);

        JLabel subtitulo = new JLabel("Programación & Diseño");
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 22));
        subtitulo.setBounds(60, 190, 260, 30);
        panelIzq.add(subtitulo);

        JLabel subtitulo2 = new JLabel("En General");
        subtitulo2.setForeground(Color.WHITE);
        subtitulo2.setFont(new Font("Arial", Font.BOLD, 30));
        subtitulo2.setBounds(95, 235, 220, 40);
        panelIzq.add(subtitulo2);

        // PANEL DERECHO
        JPanel panelDer = new JPanel();
        panelDer.setBounds(360, 0, 440, 450);
        panelDer.setBackground(Color.WHITE);
        panelDer.setLayout(null);
        add(panelDer);

        JLabel lblLogin = new JLabel("INICIAR SESIÓN");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 22));
        lblLogin.setBounds(130, 50, 220, 40);
        panelDer.add(lblLogin);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(70, 130, 100, 25);
        panelDer.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(70, 160, 280, 35);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        panelDer.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setBounds(70, 215, 100, 25);
        panelDer.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(70, 245, 280, 35);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        panelDer.add(txtPassword);

        verPassword = new JCheckBox("Mostrar contraseña");
        verPassword.setBounds(70, 290, 180, 25);
        verPassword.setBackground(Color.WHITE);
        panelDer.add(verPassword);

        verPassword.addActionListener(e -> {
            if (verPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(130, 340, 170, 40);
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        panelDer.add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String usuario = txtUsuario.getText();
        String password = String.valueOf(txtPassword.getPassword());

        Connection con = ConexionBD.conectar();

        try {

            String sql = "SELECT * FROM usuarios WHERE usuario=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String rol = rs.getString("rol");

                new MenuPrincipal(rol);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Usuario o contraseña incorrectos"
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