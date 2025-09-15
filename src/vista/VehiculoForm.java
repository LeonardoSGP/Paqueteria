package vista;

import controlador.VehiculoController;
import modelo.Vehiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VehiculoForm extends JPanel {

    private JTextField txtPlacas, txtMarca, txtModelo, txtCapacidad;
    private JCheckBox chkDisponible;
    private JButton btnGuardar;

    public VehiculoForm() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Arial", Font.PLAIN, 16);
        int fila = 0;

        // Placas
        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Placas:"), gbc);
        txtPlacas = new JTextField();
        txtPlacas.setFont(fuente);
        gbc.gridx = 1;
        add(txtPlacas, gbc);

        // Marca
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Marca:"), gbc);
        txtMarca = new JTextField();
        txtMarca.setFont(fuente);
        gbc.gridx = 1;
        add(txtMarca, gbc);

        // Modelo
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Modelo:"), gbc);
        txtModelo = new JTextField();
        txtModelo.setFont(fuente);
        gbc.gridx = 1;
        add(txtModelo, gbc);

        // Capacidad de carga
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Capacidad (kg):"), gbc);
        txtCapacidad = new JTextField();
        txtCapacidad.setFont(fuente);
        gbc.gridx = 1;
        add(txtCapacidad, gbc);

        // Disponible
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        add(new JLabel("Disponible:"), gbc);
        chkDisponible = new JCheckBox("Sí");
        chkDisponible.setSelected(true);
        chkDisponible.setBackground(Color.WHITE);
        gbc.gridx = 1;
        add(chkDisponible, gbc);

        // Botón Guardar
        fila++;
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        btnGuardar = new JButton("Registrar Vehículo");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(this::guardarVehiculo);
    }

    private void guardarVehiculo(ActionEvent evt) {
        String placas = txtPlacas.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String capacidadStr = txtCapacidad.getText().trim();
        boolean disponible = chkDisponible.isSelected();

        // Validaciones
        if (placas.isEmpty() || marca.isEmpty() || modelo.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (placas.length() != 6) {
            JOptionPane.showMessageDialog(this, "Las placas deben tener exactamente 6 caracteres.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double capacidad;
        try {
            capacidad = Double.parseDouble(capacidadStr);
            if (capacidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número positivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar en la base
        try {
            Vehiculo v = new Vehiculo();
            v.setPlacas(placas);
            v.setMarca(marca);
            v.setModelo(modelo);
            v.setCapacidadCarga(capacidad);
            v.setDisponible(disponible);
            v.setActivo(true);

            VehiculoController vc = new VehiculoController();
            vc.insertar(v);

            JOptionPane.showMessageDialog(this, "Vehículo registrado con éxito.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        txtPlacas.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtCapacidad.setText("");
        chkDisponible.setSelected(true);
    }
}
