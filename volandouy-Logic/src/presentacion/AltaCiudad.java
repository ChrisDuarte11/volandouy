package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import datatypes.DTCiudad;
import excepciones.ExisteParCiudadPaisException;
import logica.enums.Pais;
import logica.fabrica.IRutaVuelo;

@SuppressWarnings("serial")
public class AltaCiudad extends JInternalFrame {
	
	private IRutaVuelo iRutaVuelo;

	private JTextArea areaDescripcion;
	private JComboBox<Pais> comboPais;
	private JTextField fieldNombreAeropuerto;
	private JTextField fieldNombreCiudad;
	private JTextField fieldWeb;
	
	public AltaCiudad(IRutaVuelo interfazRuta) {
		this.iRutaVuelo = interfazRuta;
		
		// Propiedades del internalFrame
    	setTitle("Alta de ciudad");
    	setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setClosable(true);
    	setBounds(0, 0, 600, 330);
    	
    	// WindowBuilder
    	
    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{25, 180, 80, 0, 120, 25, 0};
    	gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 80, 0, 0, 30, 0};
    	gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    	gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
    	getContentPane().setLayout(gridBagLayout);
    	
    	JLabel lblNombreCiudad = new JLabel("Nombre de la ciudad:");
    	GridBagConstraints gbc_lblNombreCiudad = new GridBagConstraints();
    	gbc_lblNombreCiudad.anchor = GridBagConstraints.EAST;
    	gbc_lblNombreCiudad.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombreCiudad.gridx = 1;
    	gbc_lblNombreCiudad.gridy = 1;
    	getContentPane().add(lblNombreCiudad, gbc_lblNombreCiudad);
    	
    	fieldNombreCiudad = new JTextField();
    	GridBagConstraints gbc_fieldNombreCiudad = new GridBagConstraints();
    	gbc_fieldNombreCiudad.gridwidth = 3;
    	gbc_fieldNombreCiudad.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldNombreCiudad.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldNombreCiudad.gridx = 2;
    	gbc_fieldNombreCiudad.gridy = 1;
    	getContentPane().add(fieldNombreCiudad, gbc_fieldNombreCiudad);
    	fieldNombreCiudad.setColumns(10);
    	
    	JLabel lblPais = new JLabel("País:");
    	GridBagConstraints gbc_lblPais = new GridBagConstraints();
    	gbc_lblPais.anchor = GridBagConstraints.EAST;
    	gbc_lblPais.insets = new Insets(0, 0, 5, 5);
    	gbc_lblPais.gridx = 1;
    	gbc_lblPais.gridy = 2;
    	getContentPane().add(lblPais, gbc_lblPais);
    	
    	comboPais = new JComboBox<Pais>(Pais.values());
    	GridBagConstraints gbc_comboPais = new GridBagConstraints();
    	gbc_comboPais.gridwidth = 3;
    	gbc_comboPais.insets = new Insets(0, 0, 5, 5);
    	gbc_comboPais.fill = GridBagConstraints.BOTH;
    	gbc_comboPais.gridx = 2;
    	gbc_comboPais.gridy = 2;
    	getContentPane().add(comboPais, gbc_comboPais);
    	
    	JLabel lblNombreAeropuerto = new JLabel("Nombre del aeropuerto:");
    	GridBagConstraints gbc_lblNombreAeropuerto = new GridBagConstraints();
    	gbc_lblNombreAeropuerto.anchor = GridBagConstraints.EAST;
    	gbc_lblNombreAeropuerto.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombreAeropuerto.gridx = 1;
    	gbc_lblNombreAeropuerto.gridy = 3;
    	getContentPane().add(lblNombreAeropuerto, gbc_lblNombreAeropuerto);
    	
    	fieldNombreAeropuerto = new JTextField();
    	GridBagConstraints gbc_fieldNombreAeropuerto = new GridBagConstraints();
    	gbc_fieldNombreAeropuerto.gridwidth = 3;
    	gbc_fieldNombreAeropuerto.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldNombreAeropuerto.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldNombreAeropuerto.gridx = 2;
    	gbc_fieldNombreAeropuerto.gridy = 3;
    	getContentPane().add(fieldNombreAeropuerto, gbc_fieldNombreAeropuerto);
    	fieldNombreAeropuerto.setColumns(10);
    	
    	JLabel lblWeb = new JLabel("Sitio web:");
    	GridBagConstraints gbc_lblWeb = new GridBagConstraints();
    	gbc_lblWeb.anchor = GridBagConstraints.EAST;
    	gbc_lblWeb.insets = new Insets(0, 0, 5, 5);
    	gbc_lblWeb.gridx = 1;
    	gbc_lblWeb.gridy = 4;
    	getContentPane().add(lblWeb, gbc_lblWeb);
    	
    	fieldWeb = new JTextField();
    	GridBagConstraints gbc_fieldWeb = new GridBagConstraints();
    	gbc_fieldWeb.gridwidth = 3;
    	gbc_fieldWeb.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldWeb.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldWeb.gridx = 2;
    	gbc_fieldWeb.gridy = 4;
    	getContentPane().add(fieldWeb, gbc_fieldWeb);
    	fieldWeb.setColumns(10);
    	
    	JLabel lblDescripcion = new JLabel("Descripción:");
    	GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
    	gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
    	gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDescripcion.gridx = 1;
    	gbc_lblDescripcion.gridy = 5;
    	getContentPane().add(lblDescripcion, gbc_lblDescripcion);
    	
    	JScrollPane scrollDescripcion = new JScrollPane();
    	GridBagConstraints gbc_scrollDescripcion = new GridBagConstraints();
    	gbc_scrollDescripcion.gridwidth = 3;
    	gbc_scrollDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollDescripcion.fill = GridBagConstraints.BOTH;
    	gbc_scrollDescripcion.gridx = 2;
    	gbc_scrollDescripcion.gridy = 5;
    	getContentPane().add(scrollDescripcion, gbc_scrollDescripcion);
    	
    	areaDescripcion = new JTextArea();
    	areaDescripcion.setLineWrap(true);
    	scrollDescripcion.setViewportView(areaDescripcion);
    	
    	JButton btnCancelar = new JButton("Cancelar");
    	btnCancelar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			limpiarCampos();
    			setVisible(false);
    		}
    	});
    	GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
    	gbc_btnCancelar.anchor = GridBagConstraints.EAST;
    	gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnCancelar.gridx = 4;
    	gbc_btnCancelar.gridy = 7;
    	getContentPane().add(btnCancelar, gbc_btnCancelar);

    	JButton btnAceptar = new JButton("Aceptar");
    	btnAceptar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			altaCiudad();
    		}
    	});
    	GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
    	gbc_btnAceptar.anchor = GridBagConstraints.EAST;
    	gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnAceptar.gridx = 3;
    	gbc_btnAceptar.gridy = 7;
    	getContentPane().add(btnAceptar, gbc_btnAceptar);
	}
	
	private void altaCiudad() {
		String ciudad_nombre = this.fieldNombreCiudad.getText();
		Pais pais_nombre = (Pais) this.comboPais.getSelectedItem();
		String aeropuerto = this.fieldNombreAeropuerto.getText();
		String sitio_web = this.fieldWeb.getText();
		String descripcion = this.areaDescripcion.getText();
		LocalDate fecha_alta = LocalDate.now();

		if (!verificarCampos(ciudad_nombre, pais_nombre, aeropuerto, sitio_web, descripcion)) {
			JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Alta de ciudad", JOptionPane.ERROR_MESSAGE);
        	return;
		}
		
		DTCiudad ciudad = new DTCiudad(ciudad_nombre, descripcion, sitio_web, pais_nombre.getPais(), aeropuerto, fecha_alta);
		try {
			this.iRutaVuelo.agregarCiudad(ciudad);
			JOptionPane.showMessageDialog(this, "La ciudad se ha registrado exitosamente", "Alta de ciudad", JOptionPane.INFORMATION_MESSAGE);
			this.limpiarCampos();
		} catch (ExisteParCiudadPaisException e) {
			JOptionPane.showMessageDialog(this, "La ciudad ya está registrada", "Alta de ciudad", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void limpiarCampos() {
		this.fieldNombreCiudad.setText("");
		this.comboPais.setSelectedIndex(-1);
		this.fieldNombreAeropuerto.setText("");
		this.fieldWeb.setText("");
		this.areaDescripcion.setText("");
	}
	
	private boolean verificarCampos(String nombre_c, Pais nombre_p, String aeropuerto, String sitio_web, String descripcion) {
		if (nombre_c.isBlank() || nombre_p == null || aeropuerto.isBlank() || sitio_web.isBlank() || descripcion.isBlank()) {
			return false;
		}
		return true;
	}

}
