package usuario;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import usuario.EditarUsuario;

public class ListaUsuario extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;
    JCheckBox chkMostrarPassword;

    public ListaUsuario() {

        setTitle("Usuarios");
        setSize(700,500);
        setLocationRelativeTo(null);
        setLayout(null);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Usuario");
        modelo.addColumn("Password");
        modelo.addColumn("Rol");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20,20,640,300);
        add(scroll);
        
        chkMostrarPassword = new JCheckBox("Mostrar contraseñas");
        chkMostrarPassword.setBounds(20, 330, 180, 30);
        add(chkMostrarPassword);
        
        chkMostrarPassword.addActionListener(e -> {
            recargarTabla();
        });
        
        listarUsuarios();
        
        setVisible(true);
    }

    private void listarUsuarios() {

        Connection con = ConexionBD.conectar();

        try {

            String sql = "SELECT * FROM usuarios";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

            	String password;

            	if(chkMostrarPassword != null && chkMostrarPassword.isSelected()) {
            	    password = rs.getString("password");
            	} else {
            	    password = "******";
            	}

            	Object[] fila = {
            	    rs.getInt("id"),
            	    rs.getString("usuario"),
            	    password,
            	    rs.getString("rol")
            	};

                modelo.addRow(fila);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(80, 360, 120, 40);
        add(btnAgregar);
        
        btnAgregar.addActionListener(e -> {
            new RegistrarUsuario(this);
        });

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(260, 360, 120, 40);
        add(btnEditar);
        
        btnEditar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila == -1) {

                JOptionPane.showMessageDialog(null, "Selecciona un usuario");

            } else {

                int id = Integer.parseInt(
                        tabla.getValueAt(fila, 0).toString()
                );

                String usuario = tabla.getValueAt(fila, 1).toString();
                String password = tabla.getValueAt(fila, 2).toString();
                String rol = tabla.getValueAt(fila, 3).toString();

                new EditarUsuario(this, id, usuario, password, rol);
            }

        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(440, 360, 120, 40);
        add(btnEliminar);
        
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecciona un usuario"
                );

            } else {

                int confirmar = JOptionPane.showConfirmDialog(
                        null,
                        "¿Eliminar usuario?"
                );

                if(confirmar == 0) {

                    int id = Integer.parseInt(
                            tabla.getValueAt(fila, 0).toString()
                    );

                    eliminarUsuario(id);
                }
            }

        });
    }
    
    private void eliminarUsuario(int id) {

        // Evitar borrar al admin principal
        if(id == 1) {
            JOptionPane.showMessageDialog(
                    null,
                    "No puedes eliminar el administrador principal"
            );
            return;
        }

        Connection con = ConexionBD.conectar();

        try {

            String sql = "DELETE FROM usuarios WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Usuario eliminado"
            );

            recargarTabla();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }
    public void recargarTabla() {

        modelo.setRowCount(0);

        listarUsuarios();
    }
}
