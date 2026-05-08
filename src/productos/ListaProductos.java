package productos;

import conexion.ConexionBD;
import productos.EditarProducto;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListaProductos extends JFrame {

    JTable tabla;

    DefaultTableModel modelo;

    public ListaProductos() {

        setTitle("Productos");

        setSize(800,500);

        setLocationRelativeTo(null);

        setLayout(null);

        // MODELO TABLA

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");

        tabla = new JTable(modelo);

        JScrollPane scroll =
                new JScrollPane(tabla);

        scroll.setBounds(20,20,740,300);

        add(scroll);

        // BOTÓN AGREGAR

        JButton btnAgregar =
                new JButton("Agregar");

        btnAgregar.setBounds(100,360,150,40);

        add(btnAgregar);

        btnAgregar.addActionListener(e -> {

            new RegistrarProducto(this);

        });
        
        // BOTÓN EDITAR

        JButton btnEditar =
                new JButton("Editar");

        btnEditar.setBounds(310,360,150,40);

        add(btnEditar);
        
        btnEditar.addActionListener(e -> {

            int fila =
                    tabla.getSelectedRow();

            if(fila == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecciona un producto"
                );

            } else {

                int id =
                        Integer.parseInt(
                                tabla.getValueAt(fila,0).toString()
                        );

                String nombre =
                        tabla.getValueAt(fila,1).toString();

                double precio =
                        Double.parseDouble(
                                tabla.getValueAt(fila,2).toString()
                        );

                int stock =
                        Integer.parseInt(
                                tabla.getValueAt(fila,3).toString()
                        );

                new EditarProducto(
                        this,
                        id,
                        nombre,
                        precio,
                        stock
                );
            }

        });
        // BOTÓN ELIMINAR

        JButton btnEliminar =
                new JButton("Eliminar");

        btnEliminar.setBounds(520,360,150,40);

        add(btnEliminar);

        btnEliminar.addActionListener(e -> {

            int fila =
                    tabla.getSelectedRow();

            if(fila == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecciona un producto"
                );

            } else {

                int confirmar =
                        JOptionPane.showConfirmDialog(
                                null,
                                "¿Eliminar producto?"
                        );

                if(confirmar == 0) {

                    int id =
                            Integer.parseInt(
                                    tabla.getValueAt(fila,0).toString()
                            );

                    eliminarProducto(id);

                }
            }

        });
        // EVENTO AGREGAR

        btnAgregar.addActionListener(e -> {

            new RegistrarProducto(this);

        });

        // CARGAR DATOS

        listarProductos();

        setVisible(true);
    }

    public void recargarTabla() {

        modelo.setRowCount(0);

        listarProductos();

    }
    private void listarProductos() {

        Connection con =
                ConexionBD.conectar();

        try {

            String sql =
                    "SELECT * FROM productos";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                Object[] fila = {

                        rs.getInt("id"),

                        rs.getString("nombre"),

                        rs.getDouble("precio"),

                        rs.getInt("stock")
                };

                modelo.addRow(fila);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        }
    }
    
    private void eliminarProducto(int id) {

        Connection con =
                ConexionBD.conectar();

        try {

            String sql =
                    "DELETE FROM productos WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Producto eliminado"
            );

            recargarTabla();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        }
    }
}