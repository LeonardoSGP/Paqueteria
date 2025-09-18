package vista;

import controlador.PaqueteController;
import modelo.Paquete;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Date;

public class PaqueteForm extends JPanel {
    
    private JTextField txtCodigoPaquete, txtDescripcion, txtPeso, txtLargo, txtAncho, txtAlto, txtValorDeclarado;
    private JComboBox<String> cbTipoContenido;
    private JCheckBox chkFragil, chkRequiereSeguro;
    private JTextArea txtObservaciones;
    private JButton btnGuardar, btnLimpiar, btnCalcularVolumen;
    private JLabel lblVolumen;
    
    public PaqueteForm() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        initComponents();
    }
    
    private void initComponents() {
        // Panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font fuente = new Font("Arial", Font.PLAIN, 14);
        
        // Título
        JLabel titulo = new JLabel("📦 Registro de Paquetes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        mainPanel.add(titulo, gbc);
        
        gbc.gridwidth = 1; // Resetear
        
        // Código de paquete
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Código Paquete:"), gbc);
        
        PaqueteController pc = new PaqueteController();
        txtCodigoPaquete = new JTextField(pc.generarSiguienteCodigoPaquete());
        txtCodigoPaquete.setEditable(false);
        txtCodigoPaquete.setFont(fuente);
        txtCodigoPaquete.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(txtCodigoPaquete, gbc);
        
        gbc.gridwidth = 1; 
        
        // Descripción
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Descripción:"), gbc);
        txtDescripcion = new JTextField();
        txtDescripcion.setFont(fuente);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(txtDescripcion, gbc);
        
        gbc.gridwidth = 1; 
        
        // Tipo de contenido
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Tipo de Contenido:"), gbc);
        cbTipoContenido = new JComboBox<>(new String[]{
            "DOCUMENTOS", "ROPA", "ELECTRODOMESTICOS", "JUGUETES", 
            "LIBROS", "MEDICAMENTOS", "ALIMENTOS", "OTROS"
        });
        cbTipoContenido.setFont(fuente);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(cbTipoContenido, gbc);
        
        gbc.gridwidth = 1; 
        
        // Peso
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Peso (kg):"), gbc);
        txtPeso = new JTextField();
        txtPeso.setFont(fuente);
        gbc.gridx = 1;
        mainPanel.add(txtPeso, gbc);
        
        // Dimensiones - Largo
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Largo (cm):"), gbc);
        txtLargo = new JTextField();
        txtLargo.setFont(fuente);
        gbc.gridx = 1;
        mainPanel.add(txtLargo, gbc);
        
        // Ancho
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Ancho (cm):"), gbc);
        txtAncho = new JTextField();
        txtAncho.setFont(fuente);
        gbc.gridx = 1;
        mainPanel.add(txtAncho, gbc);
        
        // Alto
        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Alto (cm):"), gbc);
        txtAlto = new JTextField();
        txtAlto.setFont(fuente);
        gbc.gridx = 1;
        mainPanel.add(txtAlto, gbc);
        
        // Botón calcular volumen
        btnCalcularVolumen = new JButton("📐 Calcular Volumen");
        btnCalcularVolumen.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCalcularVolumen.addActionListener(this::calcularVolumen);
        gbc.gridx = 2;
        mainPanel.add(btnCalcularVolumen, gbc);
        
        // Label volumen
        gbc.gridx = 0; gbc.gridy = 8;
        mainPanel.add(new JLabel("Volumen:"), gbc);
        lblVolumen = new JLabel("0.00 cm³");
        lblVolumen.setFont(new Font("Arial", Font.BOLD, 14));
        lblVolumen.setForeground(new Color(0, 150, 0));
        gbc.gridx = 1;
        mainPanel.add(lblVolumen, gbc);
        
        // Valor declarado
        gbc.gridx = 0; gbc.gridy = 9;
        mainPanel.add(new JLabel("Valor Declarado ($):"), gbc);
        txtValorDeclarado = new JTextField("0.00");
        txtValorDeclarado.setFont(fuente);
        gbc.gridx = 1;
        mainPanel.add(txtValorDeclarado, gbc);
        
        // Checkboxes
        gbc.gridx = 0; gbc.gridy = 10;
        chkFragil = new JCheckBox("📦 Frágil");
        chkFragil.setFont(fuente);
        chkFragil.setBackground(Color.WHITE);
        mainPanel.add(chkFragil, gbc);
        
        gbc.gridx = 1;
        chkRequiereSeguro = new JCheckBox("🛡️ Requiere Seguro");
        chkRequiereSeguro.setFont(fuente);
        chkRequiereSeguro.setBackground(Color.WHITE);
        mainPanel.add(chkRequiereSeguro, gbc);
        
        // Observaciones
        gbc.gridx = 0; gbc.gridy = 11;
        mainPanel.add(new JLabel("Observaciones:"), gbc);
        txtObservaciones = new JTextArea(4, 30);
        txtObservaciones.setFont(fuente);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollObs, gbc);
        
        // ---- Panel de botones (SE SACA DEL MAIN PANEL) ----
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(Color.WHITE);
        
        btnGuardar = new JButton("💾 Guardar Paquete");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(180, 40));
        btnGuardar.addActionListener(this::guardarPaquete);
        
        btnLimpiar = new JButton("🗑️ Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLimpiar.setBackground(new Color(108, 117, 125));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setPreferredSize(new Dimension(120, 40));
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        
        // Scroll del formulario
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Agregamos: formulario en el centro y botones abajo
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void calcularVolumen(ActionEvent evt) {
        try {
            double largo = Double.parseDouble(txtLargo.getText().trim());
            double ancho = Double.parseDouble(txtAncho.getText().trim());
            double alto = Double.parseDouble(txtAlto.getText().trim());
            
            double volumen = largo * ancho * alto;
            DecimalFormat df = new DecimalFormat("#,##0.00");
            lblVolumen.setText(df.format(volumen) + " cm³");
            
        } catch (NumberFormatException e) {
            lblVolumen.setText("Ingrese valores válidos");
            lblVolumen.setForeground(Color.RED);
            Timer timer = new Timer(3000, evt2 -> {
                lblVolumen.setForeground(new Color(0, 150, 0));
                lblVolumen.setText("0.00 cm³");
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    private void guardarPaquete(ActionEvent evt) {
        String codigoPaquete = txtCodigoPaquete.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String pesoStr = txtPeso.getText().trim();
        String largoStr = txtLargo.getText().trim();
        String anchoStr = txtAncho.getText().trim();
        String altoStr = txtAlto.getText().trim();
        String valorDeclaradoStr = txtValorDeclarado.getText().trim();
        String tipoContenido = (String) cbTipoContenido.getSelectedItem();
        String observaciones = txtObservaciones.getText().trim();
        
        if (descripcion.isEmpty() || pesoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "La descripción y el peso son obligatorios.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double peso, largo = 0, ancho = 0, alto = 0, valorDeclarado = 0;
        
        try {
            peso = Double.parseDouble(pesoStr);
            if (peso <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El peso debe ser un número válido mayor a 0.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try { if (!largoStr.isEmpty()) largo = Double.parseDouble(largoStr); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El largo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE); return; }
        
        try { if (!anchoStr.isEmpty()) ancho = Double.parseDouble(anchoStr); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ancho debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE); return; }
        
        try { if (!altoStr.isEmpty()) alto = Double.parseDouble(altoStr); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El alto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE); return; }
        
        try { valorDeclarado = Double.parseDouble(valorDeclaradoStr); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El valor declarado debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE); return; }
        
        PaqueteController paqueteCtrl = new PaqueteController();
        if (paqueteCtrl.existeCodigoPaquete(codigoPaquete)) {
            JOptionPane.showMessageDialog(this,
                "El código de paquete ya existe, genera uno nuevo.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Paquete p = new Paquete();
            p.setCodigoPaquete(codigoPaquete);
            p.setDescripcion(descripcion);
            p.setPeso(peso);
            p.setLargo(largo);
            p.setAncho(ancho);
            p.setAlto(alto);
            p.setTipoContenido(tipoContenido);
            p.setFragil(chkFragil.isSelected());
            p.setValorDeclarado(valorDeclarado);
            p.setRequiereSeguro(chkRequiereSeguro.isSelected());
            p.setObservaciones(observaciones);
            p.setFechaCreacion(new Date());
            p.setActivo(true);
            
            paqueteCtrl.insertar(p);
            
            JOptionPane.showMessageDialog(this,
                "Paquete registrado con éxito.\nCódigo: " + codigoPaquete,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            limpiarFormulario();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al guardar paquete: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        PaqueteController pc = new PaqueteController();
        txtCodigoPaquete.setText(pc.generarSiguienteCodigoPaquete());
        txtDescripcion.setText("");
        txtPeso.setText("");
        txtLargo.setText("");
        txtAncho.setText("");
        txtAlto.setText("");
        txtValorDeclarado.setText("0.00");
        txtObservaciones.setText("");
        cbTipoContenido.setSelectedIndex(0);
        chkFragil.setSelected(false);
        chkRequiereSeguro.setSelected(false);
        lblVolumen.setText("0.00 cm³");
        lblVolumen.setForeground(new Color(0, 150, 0));
    }
}
