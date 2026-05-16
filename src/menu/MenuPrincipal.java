package menu;

import productos.ListaProductos;
import usuario.ListaUsuario;
import login.Login;
import categorias.Categoria;
import Proveedores.proveedores;
import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private JPanel sidebar;
    private JPanel topbar;
    private JDesktopPane desktopPane;
    private JPanel dashboard;
    private JLabel lblProductos;
    private JLabel lblClientes;
    private JLabel lblVentas;
    private JLabel lblStock;
    private JLabel lblUsuarios;
    private JLabel lblCategorias;
    private JLabel lblProveedores;
    private JLabel lblGanancias;
    private String rol;

    public MenuPrincipal(String rol) {
    	
    	this.rol = rol;

        setTitle("Sistema de Inventario");

        setSize(1200, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        //---------------- TOPBAR ----------------

        topbar = new JPanel();

        topbar.setBackground(new Color(30,30,30));

        topbar.setPreferredSize(new Dimension(1200,70));

        topbar.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titulo = new JLabel("SISTEMA DE INVENTARIO");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        topbar.add(titulo);

        add(topbar, BorderLayout.NORTH);

        //---------------- SIDEBAR ----------------

        sidebar = new JPanel();

        sidebar.setBackground(new Color(20,20,20));

        sidebar.setPreferredSize(new Dimension(220,700));

        sidebar.setLayout(null);

        add(sidebar, BorderLayout.WEST);

        //---------------- DESKTOP ----------------

        desktopPane = new JDesktopPane();

        desktopPane.setBackground(new Color(45,45,45));

        add(desktopPane, BorderLayout.CENTER);
        
      //---------------- DASHBOARD ----------------

        dashboard = new JPanel();

        dashboard.setLayout(new GridLayout(2, 4, 20, 20));

        dashboard.setBackground(new Color(45,45,45));

        dashboard.setBounds(20,20,900,500);

        lblProductos = crearCard(
                "Productos",
                "0",
                new Color(52,152,219)
        );

        lblClientes = crearCard(
                "Clientes",
                "0",
                new Color(46,204,113)
        );

        lblVentas = crearCard(
                "Ventas",
                "S/ 0",
                new Color(231,76,60)
        );

        lblStock = crearCard(
                "Bajo Stock",
                "0",
                new Color(155,89,182)
        );

        lblUsuarios = crearCard(
                "Usuarios",
                "0",
                new Color(241,196,15)
        );

        lblCategorias = crearCard(
                "Categorías",
                "0",
                new Color(230,126,34)
        );

        lblProveedores = crearCard(
                "Proveedores",
                "0",
                new Color(26,188,156)
        );

        lblGanancias = crearCard(
                "Ganancias",
                "S/ 0",
                new Color(52,73,94)
        );

        desktopPane.add(dashboard);

        //---------------- BOTONES ----------------

        JButton btnProductos = crearBoton("Productos", 50);

        btnProductos.addActionListener(e -> abrirProductos());

        sidebar.add(btnProductos);

        JButton btnCategorias = crearBoton("Categorías", 110);
        btnCategorias.addActionListener(e -> abrirCategorias());
        
        sidebar.add(btnCategorias);

        JButton btnProveedores = crearBoton("Proveedores", 170);

        btnProveedores.addActionListener(e -> abrirProveedores());

        sidebar.add(btnProveedores);
        
        JButton btnEntradas = crearBoton("Entradas", 230);

        sidebar.add(btnEntradas);

        JButton btnSalidas = crearBoton("Salidas", 290);

        sidebar.add(btnSalidas);

        if(rol.equals("admin")) {

            JButton btnUsuarios = crearBoton("Usuarios", 350);

            btnUsuarios.addActionListener(e -> abrirUsuarios());

            sidebar.add(btnUsuarios);
        }

        JButton btnCerrar = crearBoton("Cerrar sesión", 520);

        btnCerrar.addActionListener(e -> cerrarSesion());

        sidebar.add(btnCerrar);
       
        cargarDashboard();

        new Timer(3000, e -> cargarDashboard()).start();

        setVisible(true);
    }
    private void abrirCategorias() {
        new Categoria();
    }
    
    private void abrirProveedores() {
        new proveedores(rol);
    }
    
    //CARGAR DASHBOARD
    public void cargarDashboard() {

        Connection con = ConexionBD.conectar();

        try {

            // PRODUCTOS
            String sqlProductos = "SELECT COUNT(*) AS total FROM productos";
            PreparedStatement psProductos = con.prepareStatement(sqlProductos);
            ResultSet rsProductos = psProductos.executeQuery();

            if(rsProductos.next()) {
                lblProductos.setText(rsProductos.getString("total"));
            }
            
            // STOCK BAJO
            String sqlStock = "SELECT COUNT(*) AS total FROM productos WHERE stock < 10";
            PreparedStatement psStock = con.prepareStatement(sqlStock);
            ResultSet rsStock = psStock.executeQuery();
            
            // CATEGORIAS
            
            String sqlCategorias = "SELECT COUNT(*) AS total FROM categorias";

            PreparedStatement psCategorias = con.prepareStatement(sqlCategorias);

            ResultSet rsCategorias = psCategorias.executeQuery();

            if(rsCategorias.next()) {
                lblCategorias.setText(rsCategorias.getString("total"));
            }
            // CARGAR PROVEEDORES
            
            String sqlProveedores = "SELECT COUNT(*) AS total FROM proveedores";

            PreparedStatement psProveedores = con.prepareStatement(sqlProveedores);

            ResultSet rsProveedores = psProveedores.executeQuery();

            if(rsProveedores.next()) {
                lblProveedores.setText(rsProveedores.getString("total"));
            }
            
            ///////////
            
            if(rsStock.next()) {

                int bajos = rsStock.getInt("total");

                lblStock.setText(String.valueOf(bajos));

                if(bajos > 0) {
                    lblStock.getParent().setBackground(Color.RED);
                } else {
                    lblStock.getParent().setBackground(new Color(155,89,182));
                }
            }

            // USUARIOS
            String sqlUsuarios = "SELECT COUNT(*) AS total FROM usuarios";
            PreparedStatement psUsuarios = con.prepareStatement(sqlUsuarios);
            ResultSet rsUsuarios = psUsuarios.executeQuery();

            if(rsUsuarios.next()) {
                lblUsuarios.setText(rsUsuarios.getString("total"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    //---------------- MÉTODO CREAR BOTÓN ----------------

    private JButton crearBoton(String texto, int y) {

        JButton boton = new JButton(texto);

        boton.setBounds(25, y, 170, 45);

        boton.setFocusPainted(false);

        boton.setBackground(new Color(240,240,240));

        boton.setFont(new Font("Arial", Font.BOLD, 14));

        return boton;
    }

    //---------------- ABRIR PRODUCTOS ----------------

    private void abrirProductos() {

        ListaProductos ventana = new ListaProductos();

        ventana.setVisible(true);
    }

    //---------------- ABRIR USUARIOS ----------------

    private void abrirUsuarios() {

        ListaUsuario ventana = new ListaUsuario();

        ventana.setVisible(true);
    }

    //---------------- CERRAR SESIÓN ----------------

    private void cerrarSesion() {

        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Cerrar sesión?"
        );

        if(opcion == 0) {

            new Login();

            dispose();
        }
    }
    private JLabel crearCard(
            String titulo,
            String valor,
            Color color
    ) {

        JPanel card = new JPanel();

        card.setBackground(color);

        card.setLayout(new BorderLayout());

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setForeground(Color.WHITE);

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        JLabel lblValor = new JLabel(valor);

        lblValor.setForeground(Color.WHITE);

        lblValor.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        card.add(lblTitulo, BorderLayout.NORTH);

        card.add(lblValor, BorderLayout.CENTER);

        dashboard.add(card);
        
        return lblValor;
        
        
    }
}
