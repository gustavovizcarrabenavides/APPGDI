import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.math.*;
import java.time.LocalDate;
import java.util.stream.IntStream;


public class Lobitogamers extends JFrame {
    private DefaultTableModel model;
    private JPanel panelPrincipal;
    private JButton btnAgregarProducto;
    private JButton btnAgregarRegistroCompra;
    private JButton btnAgregarRegistroVenta;
    private JTree menuLateral;


    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JButton searchButton;
    public Lobitogamers() {
        setTitle("LOBITOGAMERS - Registros de Compra y Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        // Crear los nodos para el árbol
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Menú");
        DefaultMutableTreeNode productosNode = new DefaultMutableTreeNode("Productos");
        DefaultMutableTreeNode registroCompraNode = new DefaultMutableTreeNode("Registro de Compra");
        DefaultMutableTreeNode registroVentaNode = new DefaultMutableTreeNode("Registro de Venta");
        DefaultMutableTreeNode reportesNode = new DefaultMutableTreeNode("Reportes");

        // Añadiendo nodos hijos a reportes
        reportesNode.add(new DefaultMutableTreeNode("Consulta 1")); // Muestra las ventas totales para una fecha específica
        reportesNode.add(new DefaultMutableTreeNode("Consulta 2"));// Consulta 2
        reportesNode.add(new DefaultMutableTreeNode("Consulta 3"));
        reportesNode.add(new DefaultMutableTreeNode("Consulta 4"));
        reportesNode.add(new DefaultMutableTreeNode("Consulta 5"));
        reportesNode.add(new DefaultMutableTreeNode("Consulta 6"));
        reportesNode.add(new DefaultMutableTreeNode("Consulta 7"));
        reportesNode.add(new DefaultMutableTreeNode("Consulta 8"));
        reportesNode.add(new DefaultMutableTreeNode("Consulta 9"));
        reportesNode.add(new DefaultMutableTreeNode("Consulta 10"));


        // Añadiendo nodos al nodo raíz
        rootNode.add(productosNode);
        rootNode.add(registroCompraNode);
        rootNode.add(registroVentaNode);
        rootNode.add(reportesNode);

        menuLateral = new JTree(rootNode);
        menuLateral.setPreferredSize(new Dimension(350, 600));

        model = new DefaultTableModel();
        JTable table = new JTable(model);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(table), BorderLayout.CENTER);

        add(new JScrollPane(menuLateral), BorderLayout.WEST); // Agrega el árbol con un scroll
        add(panelPrincipal, BorderLayout.CENTER);

        menuLateral.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) menuLateral.getLastSelectedPathComponent();
            if (selectedNode == null) return;

            String nodeName = selectedNode.getUserObject().toString();
            switch (nodeName) {
                case "Productos":
                    removeConsultas();
                    refreshPanel();
                    mostrarProductos();
                    configurarBotonProducto();
                    break;
                case "Registro de Compra":
                    mostrarRegistroCompra();
                    configurarBotonRegistroCompra();
                    break;
                case "Registro de Venta":
                    mostrarRegistroVenta();
                    configurarBotonRegistroVenta();
                    break;
                case "Consulta 1":
                    removeConsultas();
                    refreshPanel();
                    mostrarConsulta01();
                    break;
                case "Consulta 2":
                    removeConsultas();
                    refreshPanel();

                    mostrarConsulta02();
                    break;
                case "Consulta 3":
                    removeConsultas();
                    refreshPanel();
                    mostrarConsulta03();
                    break;
                case "Consulta 4":
                    removeConsultas();
                    refreshPanel();
                    mostrarConsulta04();
                    break;
                case "Consulta 5":
                    removeConsultas();
                    refreshPanel();
                    mostrarConsulta05();
                    break;
                case "Consulta 6":
                    removeConsultas();
                    refreshPanel();

                    mostrarConsulta06();
                    break;
                case "Consulta 7":
                    removeConsultas();
                    refreshPanel();
                    mostrarConsulta07();
                    break;
                case "Consulta 8":
                    removeConsultas();
                    refreshPanel();

                    mostrarConsulta08();
                    break;
                case "Consulta 9":
                    removeConsultas();
                    refreshPanel();

                    mostrarConsulta09();
                    break;
                case "Consulta 10":
                    removeConsultas();
                    refreshPanel();

                    mostrarConsulta10();
                    break;


            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void mostrarProductos() {
        removeConsultas();
        refreshPanel();
        String[] columnNames = {"Código", "Nombre", "Precio"};
        Object[][] data = {}; // Deberías llenar esto con los datos de tu base de datos
        model.setDataVector(data, columnNames);
        try {
            Connection conn = getConnection(); // Método para obtener la conexión
            Statement stmt = conn.createStatement();
            String query = "SELECT CPDT, NMBR, PRCO FROM Producto";
            ResultSet rs = stmt.executeQuery(query);

            Object[] rowData = new Object[3];
            while (rs.next()) {
                rowData[0] = rs.getInt("CPDT");
                rowData[1] = rs.getString("NMBR");
                rowData[2] = rs.getBigDecimal("PRCO");
                model.addRow(rowData);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }











    private void mostrarRegistroVenta() {
        String[] columnNames = {"Código de Registros Venta", "Fecha", "Cantidad", "Código Producto", "Importe", "Tipo de Pago", "Total"};
        Object[][] data = {}; // Deberías llenar esto con los datos de tu base de datos
        model.setDataVector(data, columnNames);
        try {
            Connection conn = getConnection(); // Método para obtener la conexión
            Statement stmt = conn.createStatement();

            // Asumiendo que quieres mostrar los datos de las ventas desde las tablas CabeceraRegistro y CuerpoRegistro
            String query = "SELECT CR.CRTO, CR.FECH, C.CANT, C.CPDT, C.IMPT, C.TDPG, CR.TOTL " +
                    "FROM CabeceraRegistro CR JOIN CuerpoRegistro C ON CR.CRTO = C.CRTO " +
                    "WHERE CR.TIPO = 'Venta'";
            ResultSet rs = stmt.executeQuery(query);

            Object[] rowData = new Object[7];
            while (rs.next()) {
                rowData[0] = rs.getInt("CR.CRTO");
                rowData[1] = rs.getDate("CR.FECH");
                rowData[2] = rs.getInt("C.CANT");
                rowData[3] = rs.getInt("C.CPDT");
                rowData[4] = rs.getBigDecimal("C.IMPT");
                rowData[5] = rs.getString("C.TDPG");
                rowData[6] = rs.getString("CR.TOTL");
                model.addRow(rowData);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarRegistroCompra() {
        removeConsultas();
        String[] columnNames = {"Código de Registros Compra", "Fecha", "Cantidad", "Código Producto", "Importe", "Tipo de Pago", "Total"};
        Object[][] data = {}; // Deberías llenar esto con los datos de tu base de datos
        model.setDataVector(data, columnNames);
        try {
            Connection conn = getConnection(); // Método para obtener la conexión
            Statement stmt = conn.createStatement();

            // Asumiendo que quieres mostrar los datos de las compras desde las tablas CabeceraRegistro y CuerpoRegistro
            String query = "SELECT CR.CRTO, CR.FECH, C.CANT, C.CPDT, C.IMPT, C.TDPG, CR.TOTL " +
                    "FROM CabeceraRegistro CR JOIN CuerpoRegistro C ON CR.CRTO = C.CRTO " +
                    "WHERE CR.TIPO = 'Compra'";

            ResultSet rs = stmt.executeQuery(query);

            Object[] rowData = new Object[7];
            while (rs.next()) {
                rowData[0] = rs.getInt("CR.CRTO");
                rowData[1] = rs.getDate("CR.FECH");
                rowData[2] = rs.getInt("C.CANT");
                rowData[3] = rs.getInt("C.CPDT");
                rowData[4] = rs.getBigDecimal("C.IMPT");
                rowData[5] = rs.getString("C.TDPG");
                rowData[6] = rs.getString("CR.TOTL");
                model.addRow(rowData);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void configurarBotonProducto() {
        removeExistingButton();
        btnAgregarProducto = new JButton("Agregar nuevo Producto");
        btnAgregarProducto.addActionListener(e -> mostrarVentanaAgregarProducto());
        panelPrincipal.add(btnAgregarProducto, BorderLayout.NORTH);
        refreshPanel();
    }

    private void configurarBotonRegistroCompra() {
        removeConsultas();
        removeExistingButton();
        btnAgregarRegistroCompra = new JButton("Agregar nuevo Registro de Compra");
        btnAgregarRegistroCompra.addActionListener(e -> mostrarVentanaAgregarCompra());
        panelPrincipal.add(btnAgregarRegistroCompra, BorderLayout.NORTH);
        refreshPanel();
    }

    private void configurarBotonRegistroVenta() {
        removeExistingButton();
        removeConsultas();
        btnAgregarRegistroVenta = new JButton("Agregar nuevo Registro de Venta");
        btnAgregarRegistroVenta.addActionListener(e -> mostrarVentanaAgregarVenta());
        panelPrincipal.add(btnAgregarRegistroVenta, BorderLayout.NORTH);
        refreshPanel();
    }

    private void removeExistingButton() {
        if (btnAgregarProducto != null) {
            panelPrincipal.remove(btnAgregarProducto);
            btnAgregarProducto = null; // Resetea el botón a null después de removerlo
        }
        if (btnAgregarRegistroCompra != null) {
            panelPrincipal.remove(btnAgregarRegistroCompra);
            btnAgregarRegistroCompra = null; // Resetea el botón a null después de removerlo
        }
        if (btnAgregarRegistroVenta != null) {
            panelPrincipal.remove(btnAgregarRegistroVenta);
            btnAgregarRegistroVenta = null; // Resetea el botón a null después de removerlo
        }
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    private void removeConsultas(){
        if (dayComboBox != null){
            panelPrincipal.remove(dayComboBox);
            dayComboBox = null;
        }
        if (monthComboBox != null) {
            panelPrincipal.remove(monthComboBox);
            monthComboBox = null; // Resetea el botón a null después de removerlo
        }
        if (yearComboBox != null) {
            panelPrincipal.remove(yearComboBox);
            yearComboBox = null; // Resetea el botón a null después de removerlo
        }
        if (searchButton != null) {
            panelPrincipal.remove(searchButton);
            searchButton = null; // Resetea el botón a null después de removerlo
        }
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    private void refreshPanel() {
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    private void removeOtherComponents() {
        // Remove or hide all the components that are not relevant to the current consulta
        // Example: panelPrincipal.remove(someComponent);
        // Reset the states or models of components if necessary
        // Example: someTableModel.setRowCount(0);

        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }


    private void mostrarVentanaAgregarProducto() {
        removeConsultas();
        JDialog dialogProducto = new JDialog(this, "Nuevo Producto", true);
        dialogProducto.setLayout(new GridLayout(4, 2, 10, 10));
        dialogProducto.setSize(300, 200);

        JTextField nombreProductoField = new JTextField();
        JTextField precioProductoField = new JTextField();

        dialogProducto.add(new JLabel("Nombre Producto:"));
        dialogProducto.add(nombreProductoField);
        dialogProducto.add(new JLabel("Precio:"));
        dialogProducto.add(precioProductoField);

        JButton btnConfirmarProducto = new JButton("Confirmar");
        btnConfirmarProducto.addActionListener(e -> {
            agregarProducto(nombreProductoField.getText(), precioProductoField.getText());
            dialogProducto.dispose();
        });

        dialogProducto.add(new JLabel()); // Espaciador
        dialogProducto.add(btnConfirmarProducto);

        dialogProducto.setLocationRelativeTo(this);
        dialogProducto.setVisible(true);
    }
    private void agregarProducto(String nombre, String precio) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String query = "CALL agregar_producto(?, ?)";
            //CALL agregar_producto(NMBR, PRCO);
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nombre);
            pstmt.setBigDecimal(2, new BigDecimal(precio));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void mostrarVentanaAgregarCompra() {
        removeConsultas();
        JDialog dialogCompra = new JDialog(this, "Nuevo Registro de Compra", true);
        dialogCompra.setLayout(new GridLayout(4, 2, 10, 10));
        dialogCompra.setSize(300, 200);

        JTextField codigoProductoField = new JTextField();
        JTextField cantidadProductoField = new JTextField();
        JTextField tipoDePagoField = new JTextField();

        dialogCompra.add(new JLabel("Código Producto:"));
        dialogCompra.add(codigoProductoField);
        dialogCompra.add(new JLabel("Cantidad:"));
        dialogCompra.add(cantidadProductoField);
        dialogCompra.add(new JLabel("Tipo de Pago:"));
        dialogCompra.add(tipoDePagoField);

        JButton btnConfirmarCompra = new JButton("Confirmar");
        btnConfirmarCompra.addActionListener(e -> {
            agregarCompra(codigoProductoField.getText(), cantidadProductoField.getText(), tipoDePagoField.getText());
            dialogCompra.dispose();
        });

        dialogCompra.add(new JLabel()); // Espaciador
        dialogCompra.add(btnConfirmarCompra);

        dialogCompra.setLocationRelativeTo(this);
        dialogCompra.setVisible(true);
    }

    private void agregarCompra(String codigoProducto, String cantidad, String tipoDePago) {
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall("{CALL InsertarRegistroCompra(?, ?, ?)}");
            cstmt.setInt(1, Integer.parseInt(codigoProducto));
            cstmt.setInt(2, Integer.parseInt(cantidad));
            cstmt.setString(3, tipoDePago);
            cstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Compra agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (cstmt != null) cstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void mostrarVentanaAgregarVenta() {
        JDialog dialogVenta = new JDialog(this, "Nuevo Registro de Venta", true);
        dialogVenta.setLayout(new GridLayout(4, 2, 10, 10));
        dialogVenta.setSize(300, 200);

        JTextField codigoProductoField = new JTextField();
        JTextField cantidadProductoField = new JTextField();
        JTextField tipoDePagoField = new JTextField();

        dialogVenta.add(new JLabel("Código Producto:"));
        dialogVenta.add(codigoProductoField);
        dialogVenta.add(new JLabel("Cantidad:"));
        dialogVenta.add(cantidadProductoField);
        dialogVenta.add(new JLabel("Tipo de Pago:"));
        dialogVenta.add(tipoDePagoField);

        JButton btnConfirmarVenta = new JButton("Confirmar");
        btnConfirmarVenta.addActionListener(e -> {
            agregarVenta(codigoProductoField.getText(), cantidadProductoField.getText(), tipoDePagoField.getText());
            dialogVenta.dispose();
        });

        dialogVenta.add(new JLabel()); // Espaciador
        dialogVenta.add(btnConfirmarVenta);

        dialogVenta.setLocationRelativeTo(this);
        dialogVenta.setVisible(true);
    }

    private void agregarVenta(String codigoProducto, String cantidad, String tipoDePago) {
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = getConnection();
            cstmt = conn.prepareCall("{CALL InsertarRegistroVenta(?, ?, ?)}");
            cstmt.setInt(1, Integer.parseInt(codigoProducto));
            cstmt.setInt(2, Integer.parseInt(cantidad));
            cstmt.setString(3, tipoDePago);
            cstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Venta agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (cstmt != null) cstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void mostrarConsulta(int numConsulta) {
        removeExistingButton(); // Llama a este método para eliminar cualquier botón existente
        removeConsultas();
        switch (numConsulta) {
            case 1:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta01();
                break;
            case 2:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta02();
                break;
            case 3:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta03();
                break;
            case 4:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta04();
            case 5:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta05();
                break;
            case 6:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta06();
                break;
            case 7:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta07();
                break;
            case 8:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta08();
                break;
            case 9:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta09();
                break;
            case 10:
                removeExistingButton();
                removeConsultas();
                refreshPanel();
                mostrarConsulta10();
                break;

}
    }



    private void mostrarConsulta01() {
        removeExistingButton();
        removeConsultas();
        refreshPanel();
        String[] columnNames = {"Fecha", "Cantidad", "Código Producto", "Nombre Producto",  "Precio Producto", "Importe"};
        model.setDataVector(new Object[][]{}, columnNames); // Clear table

        JPanel datePanel = new JPanel(new FlowLayout());

        // Create day, month, and year dropdowns
        dayComboBox = new JComboBox<>(IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new));
        monthComboBox = new JComboBox<>(IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
        yearComboBox = new JComboBox<>(IntStream.rangeClosed(2023, LocalDate.now().getYear()).boxed().toArray(Integer[]::new));
        searchButton = new JButton("Buscar");

        datePanel.add(new JLabel("Día:"));
        datePanel.add(dayComboBox);
        datePanel.add(new JLabel("Mes:"));
        datePanel.add(monthComboBox);
        datePanel.add(new JLabel("Año:"));
        datePanel.add(yearComboBox);
        datePanel.add(searchButton);

        panelPrincipal.add(datePanel, BorderLayout.NORTH);

        searchButton.addActionListener(e -> ResultadoConsulta01());
        refreshPanel();
    }
    private void ResultadoConsulta01() {
        Integer day = (Integer) dayComboBox.getSelectedItem();
        Integer month = (Integer) monthComboBox.getSelectedItem();
        Integer year = (Integer) yearComboBox.getSelectedItem();

        // Validate the input
        if (day == null || month == null || year == null) {
            // Handle invalid input, e.g., show a message to the user
            return;
        }

        String selectedDate = String.format("%04d-%02d-%02d", year, month, day);

        // Updated SQL query
        String sql = "SELECT " +
                "cr.FECH AS Fecha, " +
                "cp.CANT AS CantidadProducto, " +
                "p.CPDT AS CodigoProducto, " +
                "p.NMBR AS NombreProducto, " +
                "p.PRCO AS PrecioProducto, " +
                "cp.IMPT AS Importe " +
                "FROM CabeceraRegistro cr " +
                "JOIN CuerpoRegistro cp ON cr.CRTO = cp.CRTO " +
                "JOIN Producto p ON cp.CPDT = p.CPDT " +
                "WHERE cr.FECH = ? AND cr.TIPO = 'VENTA'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, selectedDate);
            try (ResultSet rs = stmt.executeQuery()) {
                // Clear previous results
                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getDate("Fecha"),
                            rs.getInt("CantidadProducto"),
                            rs.getInt("CodigoProducto"),
                            rs.getString("NombreProducto"),
                            rs.getBigDecimal("PrecioProducto"),
                            rs.getBigDecimal("Importe")
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Consider logging this error or showing a user-friendly message
        }
    }


    private void mostrarConsulta02() {
        removeExistingButton();
        removeConsultas();
        refreshPanel();
        String[] columnNames = {"Fechita", "Cantidad", "Código Producto", "Nombre Producto",  "Precio Producto", "Importe"};
        model.setDataVector(new Object[][]{}, columnNames); // Clear table

        JPanel datePanel = new JPanel(new FlowLayout());

        // Create day, month, and year dropdowns
        dayComboBox = new JComboBox<>(IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new));
        monthComboBox = new JComboBox<>(IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
        yearComboBox = new JComboBox<>(IntStream.rangeClosed(2022, LocalDate.now().getYear()).boxed().toArray(Integer[]::new));
        searchButton = new JButton("Buscar");

        datePanel.add(new JLabel("Día:"));
        datePanel.add(dayComboBox);
        datePanel.add(new JLabel("Mes:"));
        datePanel.add(monthComboBox);
        datePanel.add(new JLabel("Año:"));
        datePanel.add(yearComboBox);
        datePanel.add(searchButton);

        panelPrincipal.add(datePanel, BorderLayout.NORTH);

        searchButton.addActionListener(e -> ResultadoConsulta02());
        refreshPanel();
    }
    private void ResultadoConsulta02() {
        Integer day = (Integer) dayComboBox.getSelectedItem();
        Integer month = (Integer) monthComboBox.getSelectedItem();
        Integer year = (Integer) yearComboBox.getSelectedItem();

        // Validate the input
        if (day == null || month == null || year == null) {
            // Handle invalid input, e.g., show a message to the user
            return;
        }

        String selectedDate = String.format("%04d-%02d-%02d", year, month, day);

        // Updated SQL query
        String sql = "SELECT " +
                "cr.FECH AS Fecha, " +
                "cp.CANT AS CantidadProducto, " +
                "p.CPDT AS CodigoProducto, " +
                "p.NMBR AS NombreProducto, " +
                "p.PRCO AS PrecioProducto, " +
                "cp.IMPT AS Importe " +
                "FROM CabeceraRegistro cr " +
                "JOIN CuerpoRegistro cp ON cr.CRTO = cp.CRTO " +
                "JOIN Producto p ON cp.CPDT = p.CPDT " +
                "WHERE cr.FECH = ? AND cr.TIPO = 'COMPRA'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, selectedDate);
            try (ResultSet rs = stmt.executeQuery()) {
                // Clear previous results
                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getDate("Fecha"),
                            rs.getInt("CantidadProducto"),
                            rs.getInt("CodigoProducto"),
                            rs.getString("NombreProducto"),
                            rs.getBigDecimal("PrecioProducto"),
                            rs.getBigDecimal("Importe")
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Consider logging this error or showing a user-friendly message
        }
    }




    private void mostrarConsulta03() {
        String[] columnNames = {"Nombre Producto", "Unidades Ventidas"};
        Object[][] data = {}; // Deberías llenar esto con los datos de tu base de datos
        model.setDataVector(data, columnNames);
        String sql = "SELECT P.NMBR AS Nombre_Producto, SUM(C.CANT) AS Unidades_Vendidas " +
                "FROM Producto P " +
                "JOIN CuerpoRegistro C ON P.CPDT = C.CPDT " +
                "GROUP BY Nombre_Producto " +
                "ORDER BY Unidades_Vendidas DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            // Assuming 'model' is your table model
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("Nombre_Producto"),
                        rs.getInt("Unidades_Vendidas")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Consider showing a user-friendly error message
        }
    }
    private void mostrarConsulta04() {
        removeConsultas(); // Limpia cualquier componente anterior
        refreshPanel();

        String[] columnNames = {"Mes", "Tipo de Pago", "Número de Ventas", "Ingresos"};
        model.setDataVector(new Object[][]{}, columnNames);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String query = "SELECT MONTH(CR.FECH) AS Mes, CPR.TDPG, COUNT(*) AS Numero_Ventas, SUM(CPR.IMPT) AS Ingresos " +
                    "FROM CabeceraRegistro CR " +
                    "JOIN CuerpoRegistro CPR ON CR.CRTO = CPR.CRTO " +
                    "WHERE CR.TIPO = 'VENTA' " +
                    "GROUP BY MONTH(CR.FECH), CPR.TDPG";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("Mes"),
                        rs.getString("TDPG"),
                        rs.getInt("Numero_Ventas"),
                        rs.getBigDecimal("Ingresos")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Considera mostrar un mensaje de error amigable para el usuario
        }
    }
    private JTextField codigoProductoFieldConsulta05;
    private JComboBox<Integer> primerMesComboBoxConsulta05;
    private JComboBox<Integer> segundoMesComboBoxConsulta05;
    private JButton searchButtonConsulta05;

    private void mostrarConsulta05() {
        removeConsultas(); // Limpia cualquier componente anterior
        refreshPanel();

        String[] columnNames = {"Mes", "Año", "Nombre del Producto", "Total Importe", "Unidades Vendidas"};
        model.setDataVector(new Object[][]{}, columnNames);

        // Paneles y componentes para la entrada de mes y código del producto
        JPanel inputsPanel = new JPanel(new FlowLayout());
        codigoProductoFieldConsulta05 = new JTextField(5);
        primerMesComboBoxConsulta05 = new JComboBox<>(IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
        segundoMesComboBoxConsulta05 = new JComboBox<>(IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
        searchButtonConsulta05 = new JButton("Buscar");

        // Añadir componentes al panel
        inputsPanel.add(new JLabel("Código Producto:"));
        inputsPanel.add(codigoProductoFieldConsulta05);
        inputsPanel.add(new JLabel("Primer Mes:"));
        inputsPanel.add(primerMesComboBoxConsulta05);
        inputsPanel.add(new JLabel("Segundo Mes:"));
        inputsPanel.add(segundoMesComboBoxConsulta05);
        inputsPanel.add(searchButtonConsulta05);

        panelPrincipal.add(inputsPanel, BorderLayout.NORTH);

        searchButtonConsulta05.addActionListener(e -> realizarConsulta05());
        refreshPanel();
    }

    private void realizarConsulta05() {
        Integer primerMes = (Integer) primerMesComboBoxConsulta05.getSelectedItem();
        Integer segundoMes = (Integer) segundoMesComboBoxConsulta05.getSelectedItem();
        Integer codigoProducto;
        try {
            codigoProducto = Integer.parseInt(codigoProductoFieldConsulta05.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un código de producto válido.", "Código Inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT M.Mes, 2023 AS Año, P.NMBR AS Nombre_Producto, COALESCE(SUM(CPR.IMPT), 0) AS Total_Importe, COALESCE(SUM(CPR.CANT), 0) AS Unidades_Vendidas " +
                     "FROM (SELECT ? AS Mes UNION SELECT ?) M " +
                     "LEFT JOIN CabeceraRegistro CR ON M.Mes = MONTH(CR.FECH) AND YEAR(CR.FECH) = 2023 " +
                     "LEFT JOIN CuerpoRegistro CPR ON CR.CRTO = CPR.CRTO " +
                     "LEFT JOIN Producto P ON CPR.CPDT = P.CPDT AND P.CPDT = ? " +
                     "WHERE (CR.TIPO = 'VENTA' OR CR.TIPO IS NULL) " +
                     "GROUP BY M.Mes, P.NMBR")) {

            stmt.setInt(1, primerMes);
            stmt.setInt(2, segundoMes);
            stmt.setInt(3, codigoProducto);
            ResultSet rs = stmt.executeQuery();

            // Limpiar los resultados anteriores
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("Mes"),
                        rs.getInt("Año"),
                        rs.getString("Nombre_Producto"),
                        rs.getBigDecimal("Total_Importe"),
                        rs.getInt("Unidades_Vendidas")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la consulta: " + ex.getMessage(), "Error en la Consulta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JComboBox<Integer> dayComboBoxConsulta06;
    private JComboBox<Integer> monthComboBoxConsulta06;
    private JComboBox<Integer> yearComboBoxConsulta06;
    private JButton searchButtonConsulta06;

    private void mostrarConsulta06() {
        removeConsultas(); // Limpia cualquier componente anterior
        refreshPanel();

        String[] columnNames = {"Fecha", "Venta Máxima", "Productos"};
        model.setDataVector(new Object[][]{}, columnNames);

        // Paneles y componentes para la entrada de fecha
        JPanel datePanel = new JPanel(new FlowLayout());
        dayComboBoxConsulta06 = new JComboBox<>(IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new));
        monthComboBoxConsulta06 = new JComboBox<>(IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
        yearComboBoxConsulta06 = new JComboBox<>(IntStream.rangeClosed(2020, LocalDate.now().getYear()).boxed().toArray(Integer[]::new));
        searchButtonConsulta06 = new JButton("Buscar");

        // Añadir componentes al panel
        datePanel.add(new JLabel("Día:"));
        datePanel.add(dayComboBoxConsulta06);
        datePanel.add(new JLabel("Mes:"));
        datePanel.add(monthComboBoxConsulta06);
        datePanel.add(new JLabel("Año:"));
        datePanel.add(yearComboBoxConsulta06);
        datePanel.add(searchButtonConsulta06);

        panelPrincipal.add(datePanel, BorderLayout.NORTH);

        searchButtonConsulta06.addActionListener(e -> realizarConsulta06());
        refreshPanel();
    }

    private void realizarConsulta06() {
        Integer day = (Integer) dayComboBoxConsulta06.getSelectedItem();
        Integer month = (Integer) monthComboBoxConsulta06.getSelectedItem();
        Integer year = (Integer) yearComboBoxConsulta06.getSelectedItem();

        // Asegúrate de que los valores de la fecha sean válidos
        if (day == null || month == null || year == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha válida.", "Fecha Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month, day);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT CR.FECH AS Fecha, MAX(CR.TOTL) AS Venta_Máxima, GROUP_CONCAT(DISTINCT P.NMBR SEPARATOR ', ') AS Productos " +
                     "FROM CabeceraRegistro CR " +
                     "JOIN CuerpoRegistro CPR ON CR.CRTO = CPR.CRTO " +
                     "JOIN Producto P ON CPR.CPDT = P.CPDT " +
                     "WHERE CR.TIPO = 'VENTA' AND CR.FECH = ? " +
                     "GROUP BY CR.FECH " +
                     "ORDER BY Venta_Máxima DESC " +
                     "LIMIT 1")) {

            stmt.setString(1, fechaSeleccionada);
            ResultSet rs = stmt.executeQuery();

            // Limpiar los resultados anteriores
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getDate("Fecha"),
                        rs.getBigDecimal("Venta_Máxima"),
                        rs.getString("Productos")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la consulta: " + ex.getMessage(), "Error en la Consulta", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void mostrarConsulta07() {
        String[] columnNames = {"Año", "Mes", "Total de Ventas"};
        model.setDataVector(new Object[][]{}, columnNames);
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            String query = "SELECT YEAR(CR.FECH) AS Year, MONTH(CR.FECH) AS Mes, SUM(CR.TOTL) AS Total_Ventas " +
                    "FROM CabeceraRegistro CR " +
                    "WHERE CR.TIPO = 'Venta' " +
                    "GROUP BY YEAR(CR.FECH), MONTH(CR.FECH);";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] rowData = {
                        rs.getInt("Year"),
                        rs.getInt("Mes"),
                        rs.getBigDecimal("Total_Ventas")
                };
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Considera mostrar un mensaje de error amigable para el usuario
        }
    }

    private JComboBox<Integer> monthComboBoxConsulta08;
    private JComboBox<Integer> yearComboBoxConsulta08;
    private JButton searchButtonConsulta08;

    private void mostrarConsulta08() {
        removeConsultas(); // Limpia cualquier componente anterior
        refreshPanel();

        String[] columnNames = {"Nombre del Producto", "Precio del Producto"};
        model.setDataVector(new Object[][]{}, columnNames);

        // Paneles y componentes para la entrada de mes y año
        JPanel datePanel = new JPanel(new FlowLayout());
        monthComboBoxConsulta08 = new JComboBox<>(IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
        yearComboBoxConsulta08 = new JComboBox<>(IntStream.rangeClosed(2020, LocalDate.now().getYear()).boxed().toArray(Integer[]::new));
        searchButtonConsulta08 = new JButton("Buscar");

        // Añadir componentes al panel
        datePanel.add(new JLabel("Mes:"));
        datePanel.add(monthComboBoxConsulta08);
        datePanel.add(new JLabel("Año:"));
        datePanel.add(yearComboBoxConsulta08);
        datePanel.add(searchButtonConsulta08);

        panelPrincipal.add(datePanel, BorderLayout.NORTH);

        searchButtonConsulta08.addActionListener(e -> realizarConsulta08());
        refreshPanel();
    }

    private void realizarConsulta08() {
        Integer month = (Integer) monthComboBoxConsulta08.getSelectedItem();
        Integer year = (Integer) yearComboBoxConsulta08.getSelectedItem();

        // Asegúrate de que los valores de mes y año sean válidos
        if (month == null || year == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un mes y un año válidos.", "Fecha Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT P.NMBR, P.PRCO " +
                     "FROM Producto P " +
                     "LEFT JOIN CuerpoRegistro C ON P.CPDT = C.CPDT " +
                     "LEFT JOIN CabeceraRegistro CR ON C.CRTO = CR.CRTO " +
                     "WHERE CR.CRTO IS NULL OR (MONTH(CR.FECH) != ? OR YEAR(CR.FECH) != ?) " +
                     "GROUP BY P.CPDT " +
                     "HAVING COUNT(C.CRTO) = 0")) {

            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            // Limpiar los resultados anteriores
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("NMBR"),
                        rs.getBigDecimal("PRCO")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la consulta: " + ex.getMessage(), "Error en la Consulta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarConsulta09() {
        removeConsultas(); // Limpia cualquier componente anterior
        refreshPanel();

        String[] columnNames = {"Nombre del Producto", "Mes", "Año", "Unidades Vendidas"};
        model.setDataVector(new Object[][]{}, columnNames);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String query = "SELECT P.NMBR AS Nombre_Producto, " +
                    "MONTH(C.FECH) AS Mes, " +
                    "YEAR(C.FECH) AS ANIO, " +
                    "SUM(CR.CANT) AS Unidades_Vendidas " +
                    "FROM Producto P " +
                    "JOIN cuerporegistro CR ON P.CPDT = CR.CPDT " +
                    "JOIN Cabeceraregistro C ON CR.CRTO = C.CRTO " +
                    "WHERE C.TIPO = 'Venta' " +
                    "GROUP BY P.NMBR, MONTH(C.FECH), YEAR(C.FECH) " +
                    "ORDER BY P.NMBR, YEAR(C.FECH), MONTH(C.FECH);";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("Nombre_Producto"),
                        rs.getInt("Mes"),
                        rs.getInt("ANIO"),
                        rs.getInt("Unidades_Vendidas")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Considera mostrar un mensaje de error amigable para el usuario
        }
    }

    private JComboBox<Integer> mesComboBoxConsulta10;
    private JComboBox<Integer> anioComboBoxConsulta10;
    private JButton searchButtonConsulta10;

    private void mostrarConsulta10() {
        removeConsultas(); // Limpia cualquier componente anterior
        refreshPanel();

        String[] columnNames = {"Nombre del Producto", "Total Unidades Vendidas"};
        model.setDataVector(new Object[][]{}, columnNames);

        // Paneles y componentes para la entrada de mes y año
        JPanel inputsPanel = new JPanel(new FlowLayout());
        mesComboBoxConsulta10 = new JComboBox<>(IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
        anioComboBoxConsulta10 = new JComboBox<>(IntStream.rangeClosed(2020, LocalDate.now().getYear()).boxed().toArray(Integer[]::new));
        searchButtonConsulta10 = new JButton("Buscar");

        // Añadir componentes al panel
        inputsPanel.add(new JLabel("Mes:"));
        inputsPanel.add(mesComboBoxConsulta10);
        inputsPanel.add(new JLabel("Año:"));
        inputsPanel.add(anioComboBoxConsulta10);
        inputsPanel.add(searchButtonConsulta10);

        panelPrincipal.add(inputsPanel, BorderLayout.NORTH);

        searchButtonConsulta10.addActionListener(e -> realizarConsulta10());
        refreshPanel();
    }

    private void realizarConsulta10() {
        Integer mes = (Integer) mesComboBoxConsulta10.getSelectedItem();
        Integer anio = (Integer) anioComboBoxConsulta10.getSelectedItem();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT P.NMBR AS Nombre_Producto, SUM(CR.CANT) AS Total_Unidades_Vendidas " +
                     "FROM Producto P " +
                     "JOIN CuerpoRegistro CR ON P.CPDT = CR.CPDT " +
                     "JOIN CabeceraRegistro C ON CR.CRTO = C.CRTO " +
                     "WHERE MONTH(C.FECH) = ? AND YEAR(C.FECH) = ? AND C.TIPO = 'VENTA' " +
                     "GROUP BY P.CPDT, P.NMBR " +
                     "HAVING SUM(CR.CANT) < 5 " +
                     "ORDER BY Total_Unidades_Vendidas ASC")) {

            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            // Limpiar los resultados anteriores
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("Nombre_Producto"),
                        rs.getInt("Total_Unidades_Vendidas")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la consulta: " + ex.getMessage(), "Error en la Consulta", JOptionPane.ERROR_MESSAGE);
        }
    }








    private Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/lobito";
        String user = "root";
        String password = "6923";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static boolean testDatabaseConnection() {
        String url = "jdbc:mysql://localhost:3306/lobito";
        String user = "root";
        String password = "6923";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Si la conexión es exitosa, devuelve true
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Si ocurre una excepción, devuelve false
            return false;
        }
    }


    public static void main(String[] args) {

        if (testDatabaseConnection()) {
            System.out.println("Conexión exitosa a la base de datos!");
        } else {
            System.out.println("Fallo en la conexión a la base de datos.");
            return; // Sale de la aplicación si no se puede establecer una conexión
        }
        SwingUtilities.invokeLater(() -> new Lobitogamers());
    }

}


