package productos;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import menu.MenuPrincipal;

public class ListaProductos extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;

    JTextField txtId;
    JTextField txtNombre;
    JTextField txtPrecioCompra;
    JTextField txtPrecioVenta;
    JTextField txtStock;
    JTextField txtCodigo;
    JTextArea txtDescripcion;

    JButton btnGuardar;
    JButton btnActualizar;
    JButton btnEliminar;
    JButton btnNuevo;

    public ListaProductos() {

        setTitle("Sistema de Productos");
        setSize(1200, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        //---------------- PANEL IZQUIERDO ----------------

        JPanel panelFormulario = new JPanel();
        panelFormulario.setPreferredSize(new Dimension(300, 850));
        panelFormulario.setLayout(null);

        JLabel titulo = new JLabel("PRODUCTOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBounds(60, 20, 200, 30);

        panelFormulario.add(titulo);

        // ID

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(20, 80, 100, 25);

        panelFormulario.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(20, 105, 220, 30);
        txtId.setEditable(false);

        panelFormulario.add(txtId);

        JLabel lblCodigo = new JLabel("Código");
        lblCodigo.setBounds(20, 145, 100, 25);

        panelFormulario.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(20, 170, 220, 30);

        panelFormulario.add(txtCodigo);
        
        // NOMBRE

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(20, 210, 100, 25);

        panelFormulario.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(20, 235, 220, 30);

        panelFormulario.add(txtNombre);
        
        // DECRIPCION
        
        JLabel lblDescripcion = new JLabel("Descripción");
        lblDescripcion.setBounds(20, 275, 100, 25);

        panelFormulario.add(lblDescripcion);

        txtDescripcion = new JTextArea();

        JScrollPane scrollDescripcion =
                new JScrollPane(txtDescripcion);

        scrollDescripcion.setBounds(20, 300, 220, 70);

        panelFormulario.add(scrollDescripcion);

        // PRECIO COMPRA	
        JLabel lblCompra = new JLabel("Precio Compra");
        lblCompra.setBounds(20, 380, 120, 25);

        panelFormulario.add(lblCompra);

        txtPrecioCompra = new JTextField();
        txtPrecioCompra.setBounds(20, 405, 220, 30);

        panelFormulario.add(txtPrecioCompra);
        
        // PRECIO VENTA

        JLabel lblVenta = new JLabel("Precio Venta");
        lblVenta.setBounds(20, 445, 120, 25);

        panelFormulario.add(lblVenta);

        txtPrecioVenta = new JTextField();
        txtPrecioVenta.setBounds(20, 470, 220, 30);

        panelFormulario.add(txtPrecioVenta);

        // STOCK

        JLabel lblStock = new JLabel("Stock");
        lblStock.setBounds(20, 510, 100, 25);

        panelFormulario.add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(20, 535, 220, 30);

        panelFormulario.add(txtStock);
        // BOTONES

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(20, 590, 220, 35);

        panelFormulario.add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(20, 635, 220, 35);
        
        panelFormulario.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(20, 680, 220, 35);

        panelFormulario.add(btnEliminar);

        btnNuevo = new JButton("Limpiar");

        btnNuevo.setBounds(20, 725, 220, 35);

        panelFormulario.add(btnNuevo);
        
        btnNuevo.addActionListener(e -> limpiar());

        add(panelFormulario, BorderLayout.WEST);

        //---------------- PANEL DERECHO ----------------

        JPanel panelTabla = new JPanel(new BorderLayout());

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("P. Compra");
        modelo.addColumn("P. Venta");
        modelo.addColumn("Stock");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        panelTabla.add(scroll, BorderLayout.CENTER);

        add(panelTabla, BorderLayout.CENTER);

        //---------------- EVENTO TABLA ----------------

        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila != -1) {

                txtId.setText(
                        tabla.getValueAt(fila, 0).toString()
                );

                txtCodigo.setText(
                        tabla.getValueAt(fila, 1).toString()
                );

                txtNombre.setText(
                        tabla.getValueAt(fila, 2).toString()
                );

                txtPrecioCompra.setText(
                        tabla.getValueAt(fila, 3).toString()
                );

                txtPrecioVenta.setText(
                        tabla.getValueAt(fila, 4).toString()
                );

                txtStock.setText(
                        tabla.getValueAt(fila, 5).toString()
                );
                cargarDescripcion(
                        Integer.parseInt(
                                tabla.getValueAt(fila,0).toString()
                        )
                );
            }
        });

        //---------------- BOTÓN GUARDAR ----------------

        btnGuardar.addActionListener(e -> guardarProducto());

        //---------------- BOTÓN ACTUALIZAR ----------------

        btnActualizar.addActionListener(e -> actualizarProducto());

        //---------------- BOTÓN ELIMINAR ----------------

        btnEliminar.addActionListener(e -> eliminarProducto());

        listarProductos();

        setVisible(true);
    }

    // LISTAR

    private void listarProductos() {

        modelo.setRowCount(0);

        Connection con = ConexionBD.conectar();

        try {

            String sql = "SELECT * FROM productos";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

            	Object[] fila = {

            	        rs.getInt("id"),

            	        rs.getString("codigo"),

            	        rs.getString("nombre"),

            	        rs.getDouble("precio_compra"),

            	        rs.getDouble("precio_venta"),

            	        rs.getInt("stock")
            	};

                modelo.addRow(fila);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void cargarDescripcion(int idProducto) {

        Connection con = ConexionBD.conectar();

        try {

            String sql =
                    "SELECT descripcion FROM productos WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idProducto);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                txtDescripcion.setText(
                        rs.getString("descripcion")
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }

    // GUARDAR

    private void guardarProducto() {

        String codigo = txtCodigo.getText();

        String nombre = txtNombre.getText();

        String descripcion = txtDescripcion.getText();

        double precioCompra =
                Double.parseDouble(
                        txtPrecioCompra.getText()
                );

        double precioVenta =
                Double.parseDouble(
                        txtPrecioVenta.getText()
                );

        int stock =
                Integer.parseInt(
                        txtStock.getText()
                );

        Connection con = ConexionBD.conectar();

        try {

            String sql =
                    "INSERT INTO productos(codigo,nombre,descripcion,precio_compra,precio_venta,stock) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, codigo);

            ps.setString(2, nombre);

            ps.setString(3, descripcion);

            ps.setDouble(4, precioCompra);

            ps.setDouble(5, precioVenta);

            ps.setInt(6, stock);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Producto guardado"
            );

            limpiar();

            listarProductos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }

    // ACTUALIZAR

    private void actualizarProducto() {

        int id = Integer.parseInt(txtId.getText());

        String codigo = txtCodigo.getText();

        String nombre = txtNombre.getText();

        double precioCompra =
                Double.parseDouble(
                        txtPrecioCompra.getText()
                );

        double precioVenta =
                Double.parseDouble(
                        txtPrecioVenta.getText()
                );

        int stock =
                Integer.parseInt(
                        txtStock.getText()
                );

        Connection con = ConexionBD.conectar();

        try {

            String sql =
                    "UPDATE productos SET codigo=?, nombre=?, precio_compra=?, precio_venta=?, stock=? WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, codigo);

            ps.setString(2, nombre);

            ps.setDouble(3, precioCompra);

            ps.setDouble(4, precioVenta);

            ps.setInt(5, stock);

            ps.setInt(6, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Producto actualizado"
            );

            limpiar();

            listarProductos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }

    // ELIMINAR

    private void eliminarProducto() {

        if(txtId.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Selecciona un producto"
            );

            return;
        }

        int id = Integer.parseInt(txtId.getText());

        Connection con = ConexionBD.conectar();

        try {

            String sql =
                    "DELETE FROM productos WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Producto eliminado"
            );

            limpiar();

            listarProductos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }

    // LIMPIAR

    private void limpiar() {

        txtId.setText("");

        txtCodigo.setText("");

        txtNombre.setText("");

        txtDescripcion.setText("");

        txtPrecioCompra.setText("");

        txtPrecioVenta.setText("");

        txtStock.setText("");

        tabla.clearSelection();

        txtCodigo.requestFocus();
    }
    
    
    
    public void recargarTabla() {

        modelo.setRowCount(0);

        listarProductos();

        

    }
}