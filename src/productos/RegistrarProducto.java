package productos;

import conexion.ConexionBD;
import productos.ListaProductos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;




public class RegistrarProducto extends JFrame {

    JTextField txtNombre;
    JTextField txtPrecio;
    JTextField txtStock;

    private ListaProductos ventanaProductos;

    public RegistrarProducto(ListaProductos ventanaProductos) {
    	
    	this.ventanaProductos = ventanaProductos;

        setTitle("Registrar Producto");

        setSize(400,350);

        setLayout(null);

        setLocationRelativeTo(null);

        // NOMBRE

        JLabel lblNombre =
                new JLabel("Nombre:");

        lblNombre.setBounds(50,50,100,30);

        add(lblNombre);

        txtNombre = new JTextField();

        txtNombre.setBounds(150,50,180,30);

        add(txtNombre);

        // PRECIO

        JLabel lblPrecio =
                new JLabel("Precio:");

        lblPrecio.setBounds(50,100,100,30);

        add(lblPrecio);

        txtPrecio = new JTextField();

        txtPrecio.setBounds(150,100,180,30);

        add(txtPrecio);

        // STOCK

        JLabel lblStock =
                new JLabel("Stock:");

        lblStock.setBounds(50,150,100,30);

        add(lblStock);

        txtStock = new JTextField();

        txtStock.setBounds(150,150,180,30);

        add(txtStock);

        // BOTÓN

        JButton btnGuardar =
                new JButton("Guardar");

        btnGuardar.setBounds(120,230,140,40);

        add(btnGuardar);

        // EVENTO BOTÓN

        btnGuardar.addActionListener(e -> guardarProducto());

        setVisible(true);
    }

    private void guardarProducto() {

        String nombre =
                txtNombre.getText();

        double precio =
                Double.parseDouble(
                        txtPrecio.getText()
                );

        int stock =
                Integer.parseInt(
                        txtStock.getText()
                );

        Connection con =
                ConexionBD.conectar();

        try {

            String sql =
                    "INSERT INTO productos(nombre,precio,stock) VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, nombre);

            ps.setDouble(2, precio);

            ps.setInt(3, stock);

            ps.execute();
            System.out.println("Producto guardado");

            ventanaProductos.recargarTabla();
            dispose();
            JOptionPane.showMessageDialog(
                    null,
                    "Producto guardado"
            );

            limpiar();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        }
    }

    private void limpiar() {

        txtNombre.setText("");

        txtPrecio.setText("");

        txtStock.setText("");

    }
}