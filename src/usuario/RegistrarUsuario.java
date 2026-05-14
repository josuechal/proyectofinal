package usuario;

import conexion.ConexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrarUsuario extends JFrame {

    JTextField txtUsuario;
    JTextField txtPassword;
    JComboBox<String> cbRol;

    ListaUsuario ventana;

    public RegistrarUsuario(ListaUsuario ventana) {

        this.ventana = ventana;

        setTitle("Registrar Usuario");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(40,40,100,30);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140,40,180,30);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40,90,100,30);
        add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setBounds(140,90,180,30);
        add(txtPassword);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(40,140,100,30);
        add(lblRol);

        cbRol = new JComboBox<>();
        cbRol.addItem("admin");
        cbRol.addItem("usuario");
        cbRol.setBounds(140,140,180,30);
        add(cbRol);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120,200,120,35);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardar());

        setVisible(true);
    }

    private void guardar() {

        Connection con = ConexionBD.conectar();

        try {

            String sql = "INSERT INTO usuarios(usuario,password,rol) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, txtUsuario.getText());
            ps.setString(2, txtPassword.getText());
            ps.setString(3, cbRol.getSelectedItem().toString());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario guardado");

            ventana.recargarTabla();

            dispose();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
}
