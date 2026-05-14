package menu;
import productos.ListaProductos;
import login.Login;
import usuario.ListaUsuario;

import javax.swing.*;
import java.awt.*;


public class MenuPrincipal extends JFrame {
	

    public MenuPrincipal(String rol) {

        setTitle("Sistema de Inventario");

        setSize(1000,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(null);

        getContentPane().setBackground(
                new Color(35,35,35)
        );

        // TÍTULO

        JLabel titulo =
                new JLabel("SISTEMA INVENTARIO");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(
                new Font("Arial",
                        Font.BOLD,
                        28)
        );

        titulo.setBounds(330,20,400,40);

        add(titulo);

        // PANEL LATERAL

        JPanel menuLateral =
                new JPanel();

        menuLateral.setLayout(null);

        menuLateral.setBackground(
                new Color(25,25,25)
        );

        menuLateral.setBounds(0,0,220,600);

        add(menuLateral);

        // BOTONES

        JButton btnProductos =
                new JButton("Productos");

        btnProductos.setBounds(30,80,150,40);
        btnProductos.addActionListener(e -> {

        	new ListaProductos();

        });
        menuLateral.add(btnProductos);
        
        JButton btnCategorias =
                new JButton("Categorías");

        btnCategorias.setBounds(30,140,150,40);

        menuLateral.add(btnCategorias);

        JButton btnEntradas =
                new JButton("Entradas");

        btnEntradas.setBounds(30,200,150,40);

        menuLateral.add(btnEntradas);

        JButton btnSalidas =
                new JButton("Salidas");

        btnSalidas.setBounds(30,260,150,40);

        menuLateral.add(btnSalidas);

        JButton btnUsuarios =
                new JButton("Usuarios");

        btnUsuarios.setBounds(30,320,150,40);

        if(rol.equals("admin")) {
            menuLateral.add(btnUsuarios);
        }
        btnUsuarios.addActionListener(e -> {

            new ListaUsuario();

        });
        
        JButton btnCerrar =
                new JButton("Cerrar sesión");

        btnCerrar.setBounds(30,450,150,40);

        menuLateral.add(btnCerrar);

        btnCerrar.addActionListener(e -> {

            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Cerrar sesión?"
            );

            if(opcion == 0) {

                new Login();

                dispose();
            }

        });
        
        setVisible(true);
    }
    
    
}