package usuario;

import conexion.ConexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EditarUsuario extends JFrame {

    JTextField txtUsuario;
    JTextField txtPassword;
    JComboBox<String> cbRol;

    int idUsuario;
    ListaUsuario ventana;

    public EditarUsuario(ListaUsuario ventana, int id, String usuario, String password, String rol) {

        this.ventana = ventana;
        this.idUsuario = id;

        setTitle("Editar Usuario");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(40,40,100,30);
        add(lblUsuario);

        txtUsuario = new JTextField(usuario);
        txtUsuario.setBounds(140,40,180,30);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40,90,100,30);
        add(lblPassword);

        txtPassword = new JTextField(password);
        txtPassword.setBounds(140,90,180,30);
        add(txtPassword);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(40,140,100,30);
        add(lblRol);

        cbRol = new JComboBox<>();
        cbRol.addItem("admin");
        cbRol.addItem("usuario");
        cbRol.setSelectedItem(rol);
        cbRol.setBounds(140,140,180,30);
        add(cbRol);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(120,200,130,35);
        add(btnActualizar);

        btnActualizar.addActionListener(e -> actualizar());

        setVisible(true);
    }

    private void actualizar() {

        Connection con = ConexionBD.conectar();

        try {

            String sql = "UPDATE usuarios SET usuario=?, password=?, rol=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, txtUsuario.getText());
            ps.setString(2, txtPassword.getText());
            ps.setString(3, cbRol.getSelectedItem().toString());
            ps.setInt(4, idUsuario);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario actualizado");

            ventana.recargarTabla();

            dispose();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}