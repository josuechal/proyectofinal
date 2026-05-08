package productos;

import conexion.ConexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EditarProducto extends JFrame {

    JTextField txtNombre;
    JTextField txtPrecio;
    JTextField txtStock;

    int idProducto;

    ListaProductos ventanaProductos;

    public EditarProducto(
            ListaProductos ventanaProductos,
            int id,
            String nombre,
            double precio,
            int stock
    ) {

        this.ventanaProductos = ventanaProductos;

        this.idProducto = id;

        setTitle("Editar Producto");

        setSize(400,350);

        setLayout(null);

        setLocationRelativeTo(null);

        // NOMBRE

        JLabel lblNombre =
                new JLabel("Nombre:");

        lblNombre.setBounds(50,50,100,30);

        add(lblNombre);

        txtNombre = new JTextField(nombre);

        txtNombre.setBounds(150,50,180,30);

        add(txtNombre);

        // PRECIO

        JLabel lblPrecio =
                new JLabel("Precio:");

        lblPrecio.setBounds(50,100,100,30);

        add(lblPrecio);

        txtPrecio =
                new JTextField(String.valueOf(precio));

        txtPrecio.setBounds(150,100,180,30);

        add(txtPrecio);

        // STOCK

        JLabel lblStock =
                new JLabel("Stock:");

        lblStock.setBounds(50,150,100,30);

        add(lblStock);

        txtStock =
                new JTextField(String.valueOf(stock));

        txtStock.setBounds(150,150,180,30);

        add(txtStock);

        // BOTÓN

        JButton btnActualizar =
                new JButton("Actualizar");

        btnActualizar.setBounds(120,230,140,40);

        add(btnActualizar);

        btnActualizar.addActionListener(e -> editar());

        setVisible(true);
    }

    private void editar() {

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
                    "UPDATE productos SET nombre=?, precio=?, stock=? WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, nombre);

            ps.setDouble(2, precio);

            ps.setInt(3, stock);

            ps.setInt(4, idProducto);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Producto actualizado"
            );

            ventanaProductos.recargarTabla();

            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        }
    }
}