package Proveedores;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class proveedores extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;

    JTextField txtId;
    JTextField txtEmpresa;
    JTextField txtContacto;
    JTextField txtTelefono;
    JTextField txtCorreo;
    JTextField txtDireccion;

    JButton btnGuardar;
    JButton btnActualizar;
    JButton btnEliminar;
    JButton btnNuevo;

    String rol;

    public proveedores(String rol) {

        this.rol = rol;

        setTitle("Gestión de Proveedores");
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //---------------- PANEL IZQUIERDO ----------------

        JPanel panelFormulario = new JPanel();
        panelFormulario.setPreferredSize(new Dimension(300, 700));
        panelFormulario.setLayout(null);

        JLabel titulo = new JLabel("PROVEEDORES");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBounds(60, 20, 220, 30);
        panelFormulario.add(titulo);

        // ID
        JLabel lblId = new JLabel("ID");
        lblId.setBounds(20, 80, 100, 25);
        panelFormulario.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(20, 105, 220, 30);
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        // EMPRESA
        JLabel lblEmpresa = new JLabel("Empresa");
        lblEmpresa.setBounds(20, 145, 100, 25);
        panelFormulario.add(lblEmpresa);

        txtEmpresa = new JTextField();
        txtEmpresa.setBounds(20, 170, 220, 30);
        panelFormulario.add(txtEmpresa);

        // CONTACTO
        JLabel lblContacto = new JLabel("Contacto");
        lblContacto.setBounds(20, 210, 100, 25);
        panelFormulario.add(lblContacto);

        txtContacto = new JTextField();
        txtContacto.setBounds(20, 235, 220, 30);
        panelFormulario.add(txtContacto);

        // TELEFONO
        JLabel lblTelefono = new JLabel("Teléfono");
        lblTelefono.setBounds(20, 275, 100, 25);
        panelFormulario.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(20, 300, 220, 30);
        panelFormulario.add(txtTelefono);

        // CORREO
        JLabel lblCorreo = new JLabel("Correo");
        lblCorreo.setBounds(20, 340, 100, 25);
        panelFormulario.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(20, 365, 220, 30);
        panelFormulario.add(txtCorreo);

        // DIRECCION
        JLabel lblDireccion = new JLabel("Dirección");
        lblDireccion.setBounds(20, 405, 100, 25);
        panelFormulario.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(20, 430, 220, 30);
        panelFormulario.add(txtDireccion);

        // BOTONES
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(20, 490, 220, 35);
        panelFormulario.add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(20, 535, 220, 35);
        panelFormulario.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(20, 580, 220, 35);
        panelFormulario.add(btnEliminar);

        btnNuevo = new JButton("Limpiar");
        btnNuevo.setBounds(20, 625, 220, 35);
        panelFormulario.add(btnNuevo);

        add(panelFormulario, BorderLayout.WEST);

        //---------------- TABLA ----------------

        JPanel panelTabla = new JPanel(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Empresa");
        modelo.addColumn("Contacto");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        modelo.addColumn("Fecha");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        panelTabla.add(scroll, BorderLayout.CENTER);

        add(panelTabla, BorderLayout.CENTER);

        //---------------- EVENTO TABLA ----------------

        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila != -1) {

                txtId.setText(tabla.getValueAt(fila, 0).toString());
                txtEmpresa.setText(tabla.getValueAt(fila, 1).toString());
                txtContacto.setText(tabla.getValueAt(fila, 2).toString());
                txtTelefono.setText(tabla.getValueAt(fila, 3).toString());
                txtCorreo.setText(tabla.getValueAt(fila, 4).toString());

                cargarDireccion(
                    Integer.parseInt(
                        tabla.getValueAt(fila, 0).toString()
                    )
                );
            }
        });

        //---------------- PERMISOS ----------------

        if(!rol.equals("admin")) {
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        //---------------- EVENTOS ----------------

        btnGuardar.addActionListener(e -> guardarProveedor());
        btnActualizar.addActionListener(e -> actualizarProveedor());
        btnEliminar.addActionListener(e -> eliminarProveedor());
        btnNuevo.addActionListener(e -> limpiar());
        
       listarProveedores();

       setVisible(true);
        
    }
        private void listarProveedores() {
            modelo.setRowCount(0);

            Connection con = ConexionBD.conectar();

            try {
                String sql = "SELECT * FROM proveedores";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {

                    Object[] fila = {
                            rs.getInt("id"),
                            rs.getString("empresa"),
                            rs.getString("contacto"),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("fecha_registro")
                    };

                    modelo.addRow(fila);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
        private void guardarProveedor() {

            Connection con = ConexionBD.conectar();

            try {

                String sql = "INSERT INTO proveedores(empresa,contacto,telefono,direccion,correo) VALUES(?,?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtEmpresa.getText());
                ps.setString(2, txtContacto.getText());
                ps.setString(3, txtTelefono.getText());
                ps.setString(4, txtDireccion.getText());
                ps.setString(5, txtCorreo.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Proveedor guardado");

                limpiar();
                listarProveedores();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
        private void actualizarProveedor() {
            Connection con = ConexionBD.conectar();

            try {

                String sql = "UPDATE proveedores SET empresa=?, contacto=?, telefono=?, direccion=?, correo=? WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtEmpresa.getText());
                ps.setString(2, txtContacto.getText());
                ps.setString(3, txtTelefono.getText());
                ps.setString(4, txtDireccion.getText());
                ps.setString(5, txtCorreo.getText());
                ps.setInt(6, Integer.parseInt(txtId.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Proveedor actualizado");

                limpiar();
                listarProveedores();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
        private void eliminarProveedor() {
            Connection con = ConexionBD.conectar();

            try {

                String sql = "DELETE FROM proveedores WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(txtId.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Proveedor eliminado");

                limpiar();
                listarProveedores();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
        private void limpiar() {
            txtId.setText("");
            txtEmpresa.setText("");
            txtContacto.setText("");
            txtTelefono.setText("");
            txtCorreo.setText("");
            txtDireccion.setText("");
        }
        //METODO LLAMAR DIRECION PARA EDITAR
        private void cargarDireccion(int idProveedor) {

            Connection con = ConexionBD.conectar();

            try {

                String sql = "SELECT direccion FROM proveedores WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, idProveedor);

                ResultSet rs = ps.executeQuery();

                if(rs.next()) {

                    txtDireccion.setText(
                        rs.getString("direccion")
                    );
                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        e.getMessage()
                );
            }
        }
	}