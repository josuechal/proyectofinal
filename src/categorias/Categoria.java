package categorias;

import conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Categoria extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;

    public Categoria() {

        setTitle("Lista de Categorías");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 20, 640, 280);
        add(scroll);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(80, 340, 120, 40);
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(260, 340, 120, 40);
        add(btnEliminar);

        listarCategorias();

        setVisible(true);
    }

    private void listarCategorias() {

        Connection con = ConexionBD.conectar();

        try {

            String sql = "SELECT * FROM categorias";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                };

                modelo.addRow(fila);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
