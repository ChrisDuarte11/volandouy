package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import datatypes.DTRutaVuelo;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloRegistradoException;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

@SuppressWarnings("serial")
public class AltaVuelo extends JInternalFrame {

	private IRutaVuelo iRutaVuelo;
	private IUsuario iUsuario;

	private JComboBox<String> aerolineaComboBox;
	private JComboBox<String> rutaVueloComboBox;
	private JTextField anioFechaAltaField;
	private JTextField anioFechaField;
	private JTextField cantMaxAsientosEjecutivosField;
	private JTextField cantMaxAsientosTuristasField;
	private JTextField diaFechaAltaField;
	private JTextField diaFechaField;
	private JTextField horaDuracionField;
	private JTextField mesFechaAltaField;
	private JTextField mesFechaField;
	private JTextField minDuracionField;
	private JTextField nombreField;

	public AltaVuelo(IUsuario iUsuario, IRutaVuelo iRutaVuelo) {
		this.iRutaVuelo = iRutaVuelo;
		this.iUsuario = iUsuario;

		setTitle("Alta de vuelo");
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 710, 350);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{25, 300, 30, 0, 30, 0, 50, 0, 0, 120, 25, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel aerolineaLabel = new JLabel("Seleccionar aerolínea:");
		GridBagConstraints gbc_aerolineaLabel = new GridBagConstraints();
		gbc_aerolineaLabel.anchor = GridBagConstraints.EAST;
		gbc_aerolineaLabel.insets = new Insets(0, 0, 5, 5);
		gbc_aerolineaLabel.gridx = 1;
		gbc_aerolineaLabel.gridy = 1;
		getContentPane().add(aerolineaLabel, gbc_aerolineaLabel);

		aerolineaComboBox = new JComboBox<String>();
		aerolineaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((String) aerolineaComboBox.getSelectedItem() != "") {
					try {
						inicializarRutaVuelos((String) aerolineaComboBox.getSelectedItem());
					} catch (UsuarioNoExisteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_aerolineaComboBox = new GridBagConstraints();
		gbc_aerolineaComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_aerolineaComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_aerolineaComboBox.gridwidth = 8;
		gbc_aerolineaComboBox.gridx = 2;
		gbc_aerolineaComboBox.gridy = 1;
		getContentPane().add(aerolineaComboBox, gbc_aerolineaComboBox);

		JLabel rutaVueloLabel = new JLabel("Seleccionar ruta de vuelo:");
		GridBagConstraints gbc_rutaVueloLabel = new GridBagConstraints();
		gbc_rutaVueloLabel.anchor = GridBagConstraints.EAST;
		gbc_rutaVueloLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rutaVueloLabel.gridx = 1;
		gbc_rutaVueloLabel.gridy = 2;
		getContentPane().add(rutaVueloLabel, gbc_rutaVueloLabel);

		rutaVueloComboBox = new JComboBox<String>();
		GridBagConstraints gbc_rutaVueloComboBox = new GridBagConstraints();
		gbc_rutaVueloComboBox.fill = GridBagConstraints.BOTH;
		gbc_rutaVueloComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_rutaVueloComboBox.gridwidth = 8;
		gbc_rutaVueloComboBox.gridx = 2;
		gbc_rutaVueloComboBox.gridy = 2;
		getContentPane().add(rutaVueloComboBox, gbc_rutaVueloComboBox);

		JLabel nombreLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_nombreLabel = new GridBagConstraints();
		gbc_nombreLabel.anchor = GridBagConstraints.EAST;
		gbc_nombreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nombreLabel.gridx = 1;
		gbc_nombreLabel.gridy = 3;
		getContentPane().add(nombreLabel, gbc_nombreLabel);

		nombreField = new JTextField();
		GridBagConstraints gbc_nombreField = new GridBagConstraints();
		gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreField.gridwidth = 8;
		gbc_nombreField.gridx = 2;
		gbc_nombreField.gridy = 3;
		getContentPane().add(nombreField, gbc_nombreField);
		nombreField.setColumns(10);

		JLabel fechaLabel = new JLabel("Fecha:");
		GridBagConstraints gbc_fechaLabel = new GridBagConstraints();
		gbc_fechaLabel.anchor = GridBagConstraints.EAST;
		gbc_fechaLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fechaLabel.gridx = 1;
		gbc_fechaLabel.gridy = 4;
		getContentPane().add(fechaLabel, gbc_fechaLabel);

		diaFechaField = new JTextField();
		GridBagConstraints gbc_diaFechaField = new GridBagConstraints();
		gbc_diaFechaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_diaFechaField.insets = new Insets(0, 0, 5, 5);
		gbc_diaFechaField.gridx = 2;
		gbc_diaFechaField.gridy = 4;
		getContentPane().add(diaFechaField, gbc_diaFechaField);
		diaFechaField.setColumns(10);

		JLabel fechaSeparadorLabel_1 = new JLabel("/");
		GridBagConstraints gbc_fechaSeparadorLabel_1 = new GridBagConstraints();
		gbc_fechaSeparadorLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_fechaSeparadorLabel_1.anchor = GridBagConstraints.EAST;
		gbc_fechaSeparadorLabel_1.gridx = 3;
		gbc_fechaSeparadorLabel_1.gridy = 4;
		getContentPane().add(fechaSeparadorLabel_1, gbc_fechaSeparadorLabel_1);

		mesFechaField = new JTextField();
		GridBagConstraints gbc_mesFechaField = new GridBagConstraints();
		gbc_mesFechaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_mesFechaField.insets = new Insets(0, 0, 5, 5);
		gbc_mesFechaField.gridx = 4;
		gbc_mesFechaField.gridy = 4;
		getContentPane().add(mesFechaField, gbc_mesFechaField);
		mesFechaField.setColumns(10);

		JLabel fechaSeparadorLabel_2 = new JLabel("/");
		GridBagConstraints gbc_fechaSeparadorLabel_2 = new GridBagConstraints();
		gbc_fechaSeparadorLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_fechaSeparadorLabel_2.gridx = 5;
		gbc_fechaSeparadorLabel_2.gridy = 4;
		getContentPane().add(fechaSeparadorLabel_2, gbc_fechaSeparadorLabel_2);

		anioFechaField = new JTextField();
		GridBagConstraints gbc_anioFechaField = new GridBagConstraints();
		gbc_anioFechaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_anioFechaField.insets = new Insets(0, 0, 5, 5);
		gbc_anioFechaField.gridx = 6;
		gbc_anioFechaField.gridy = 4;
		getContentPane().add(anioFechaField, gbc_anioFechaField);
		anioFechaField.setColumns(10);

		JLabel duracionLabel = new JLabel("Duración:");
		GridBagConstraints gbc_duracionLabel = new GridBagConstraints();
		gbc_duracionLabel.anchor = GridBagConstraints.EAST;
		gbc_duracionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_duracionLabel.gridx = 1;
		gbc_duracionLabel.gridy = 5;
		getContentPane().add(duracionLabel, gbc_duracionLabel);

		horaDuracionField = new JTextField();
		GridBagConstraints gbc_horaDuracionField = new GridBagConstraints();
		gbc_horaDuracionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_horaDuracionField.insets = new Insets(0, 0, 5, 5);
		gbc_horaDuracionField.gridx = 2;
		gbc_horaDuracionField.gridy = 5;
		getContentPane().add(horaDuracionField, gbc_horaDuracionField);
		horaDuracionField.setColumns(10);

		JLabel duracionSeparadorLabel = new JLabel(":");
		GridBagConstraints gbc_duracionSeparadorLabel = new GridBagConstraints();
		gbc_duracionSeparadorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_duracionSeparadorLabel.gridx = 3;
		gbc_duracionSeparadorLabel.gridy = 5;
		getContentPane().add(duracionSeparadorLabel, gbc_duracionSeparadorLabel);

		minDuracionField = new JTextField();
		GridBagConstraints gbc_minDuracionField = new GridBagConstraints();
		gbc_minDuracionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_minDuracionField.insets = new Insets(0, 0, 5, 5);
		gbc_minDuracionField.gridx = 4;
		gbc_minDuracionField.gridy = 5;
		getContentPane().add(minDuracionField, gbc_minDuracionField);
		minDuracionField.setColumns(10);

		JLabel maxTuristaLabel = new JLabel("Cantidad máxima de asientos turistas:");
		GridBagConstraints gbc_maxTuristaLabel = new GridBagConstraints();
		gbc_maxTuristaLabel.anchor = GridBagConstraints.EAST;
		gbc_maxTuristaLabel.insets = new Insets(0, 0, 5, 5);
		gbc_maxTuristaLabel.gridx = 1;
		gbc_maxTuristaLabel.gridy = 6;
		getContentPane().add(maxTuristaLabel, gbc_maxTuristaLabel);

		cantMaxAsientosTuristasField = new JTextField();
		GridBagConstraints gbc_cantMaxAsientosTuristasField = new GridBagConstraints();
		gbc_cantMaxAsientosTuristasField.gridwidth = 3;
		gbc_cantMaxAsientosTuristasField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cantMaxAsientosTuristasField.insets = new Insets(0, 0, 5, 5);
		gbc_cantMaxAsientosTuristasField.gridx = 2;
		gbc_cantMaxAsientosTuristasField.gridy = 6;
		getContentPane().add(cantMaxAsientosTuristasField, gbc_cantMaxAsientosTuristasField);
		cantMaxAsientosTuristasField.setColumns(10);

		JLabel maxEjecutivoLabel = new JLabel("Cantidad máxima de asientos ejecutivos:");
		GridBagConstraints gbc_maxEjecutivoLabel = new GridBagConstraints();
		gbc_maxEjecutivoLabel.anchor = GridBagConstraints.EAST;
		gbc_maxEjecutivoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_maxEjecutivoLabel.gridx = 1;
		gbc_maxEjecutivoLabel.gridy = 7;
		getContentPane().add(maxEjecutivoLabel, gbc_maxEjecutivoLabel);

		cantMaxAsientosEjecutivosField = new JTextField();
		GridBagConstraints gbc_cantMaxAsientosEjecutivosField = new GridBagConstraints();
		gbc_cantMaxAsientosEjecutivosField.gridwidth = 3;
		gbc_cantMaxAsientosEjecutivosField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cantMaxAsientosEjecutivosField.insets = new Insets(0, 0, 5, 5);
		gbc_cantMaxAsientosEjecutivosField.gridx = 2;
		gbc_cantMaxAsientosEjecutivosField.gridy = 7;
		getContentPane().add(cantMaxAsientosEjecutivosField, gbc_cantMaxAsientosEjecutivosField);
		cantMaxAsientosEjecutivosField.setColumns(10);

		JLabel fechaAltaLabel = new JLabel("Fecha de alta:");
		GridBagConstraints gbc_fechaAltaLabel = new GridBagConstraints();
		gbc_fechaAltaLabel.anchor = GridBagConstraints.EAST;
		gbc_fechaAltaLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fechaAltaLabel.gridx = 1;
		gbc_fechaAltaLabel.gridy = 8;
		getContentPane().add(fechaAltaLabel, gbc_fechaAltaLabel);

		diaFechaAltaField = new JTextField();
		GridBagConstraints gbc_diaFechaAltaField = new GridBagConstraints();
		gbc_diaFechaAltaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_diaFechaAltaField.insets = new Insets(0, 0, 5, 5);
		gbc_diaFechaAltaField.gridx = 2;
		gbc_diaFechaAltaField.gridy = 8;
		getContentPane().add(diaFechaAltaField, gbc_diaFechaAltaField);
		diaFechaAltaField.setColumns(10);

		JLabel fechaAltaSeparadorLabel_1 = new JLabel("/");
		GridBagConstraints gbc_fechaAltaSeparadorLabel_1 = new GridBagConstraints();
		gbc_fechaAltaSeparadorLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_fechaAltaSeparadorLabel_1.gridx = 3;
		gbc_fechaAltaSeparadorLabel_1.gridy = 8;
		getContentPane().add(fechaAltaSeparadorLabel_1, gbc_fechaAltaSeparadorLabel_1);

		mesFechaAltaField = new JTextField();
		GridBagConstraints gbc_mesFechaAltaField = new GridBagConstraints();
		gbc_mesFechaAltaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_mesFechaAltaField.insets = new Insets(0, 0, 5, 5);
		gbc_mesFechaAltaField.gridx = 4;
		gbc_mesFechaAltaField.gridy = 8;
		getContentPane().add(mesFechaAltaField, gbc_mesFechaAltaField);
		mesFechaAltaField.setColumns(10);

		JLabel fechaAltaSeparadorLabel_2 = new JLabel("/");
		GridBagConstraints gbc_fechaAltaSeparadorLabel_2 = new GridBagConstraints();
		gbc_fechaAltaSeparadorLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_fechaAltaSeparadorLabel_2.gridx = 5;
		gbc_fechaAltaSeparadorLabel_2.gridy = 8;
		getContentPane().add(fechaAltaSeparadorLabel_2, gbc_fechaAltaSeparadorLabel_2);

		anioFechaAltaField = new JTextField();
		GridBagConstraints gbc_anioFechaAltaField = new GridBagConstraints();
		gbc_anioFechaAltaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_anioFechaAltaField.insets = new Insets(0, 0, 5, 5);
		gbc_anioFechaAltaField.gridx = 6;
		gbc_anioFechaAltaField.gridy = 8;
		getContentPane().add(anioFechaAltaField, gbc_anioFechaAltaField);
		anioFechaAltaField.setColumns(10);
		
		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaVuelo();
			}
		});
		GridBagConstraints gbc_aceptarButton = new GridBagConstraints();
		gbc_aceptarButton.anchor = GridBagConstraints.EAST;
		gbc_aceptarButton.insets = new Insets(0, 0, 5, 5);
		gbc_aceptarButton.gridx = 8;
		gbc_aceptarButton.gridy = 10;
		getContentPane().add(aceptarButton, gbc_aceptarButton);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_cancelarButton = new GridBagConstraints();
		gbc_cancelarButton.anchor = GridBagConstraints.EAST;
		gbc_cancelarButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancelarButton.gridx = 9;
		gbc_cancelarButton.gridy = 10;
		getContentPane().add(cancelarButton, gbc_cancelarButton);
	}

	private void altaVuelo() {
		String aerolinea = (String) aerolineaComboBox.getSelectedItem();
		String rutaVuelo = (String) rutaVueloComboBox.getSelectedItem();

		String nombre = nombreField.getText();

		String dia = diaFechaField.getText();
		String mes = mesFechaField.getText();
		String anio = anioFechaField.getText();
		LocalDate fecha = null;

		String horaDuracion = horaDuracionField.getText();
		String minDuracion = minDuracionField.getText();
		LocalTime duracion = null;

		String cantMaxAsientosTuristas = cantMaxAsientosTuristasField.getText();
		String cantMaxAsientosEjecutivos = cantMaxAsientosEjecutivosField.getText();

		String diaFechaAlta = diaFechaAltaField.getText();
		String mesFechaAlta = mesFechaAltaField.getText();
		String anioFechaAlta = anioFechaAltaField.getText();
		LocalDate fechaAlta = null;

		if (!validarCampos(aerolinea, rutaVuelo, nombre, dia, mes, anio, diaFechaAlta, mesFechaAlta, anioFechaAlta, cantMaxAsientosTuristas,cantMaxAsientosEjecutivos, horaDuracion, minDuracion)) {
			JOptionPane.showMessageDialog(this, "Debe rellenar todos los campos", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		/**
		 * DISCUSS: ¿Son necesarias estas dos validaciones si los comboBox están creados correctamente y no pueden ser editables?
		 */
		if (!validarAerolinea(aerolinea)) {
			JOptionPane.showMessageDialog(this, "No existe una aerolínea con ese nombre", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!validarRutaVuelo(rutaVuelo)) {
			JOptionPane.showMessageDialog(this, "No existe una ruta de vuelo con ese nombre", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			fecha = LocalDate.of(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
		} catch (DateTimeException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ingrese una fecha válida", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			fechaAlta = LocalDate.of(Integer.valueOf(anioFechaAlta), Integer.valueOf(mesFechaAlta), Integer.valueOf(diaFechaAlta));
		} catch (DateTimeException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ingrese una fecha de alta válida", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			duracion = LocalTime.of(Integer.valueOf(horaDuracion), Integer.valueOf(minDuracion));
		} catch (DateTimeException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ingrese una duración de vuelo válida", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (duracion.equals(LocalTime.of(0, 0))) {
			JOptionPane.showMessageDialog(this, "La duración del vuelo no puede ser 00:00", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Integer.parseInt(cantMaxAsientosTuristas);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "La cantidad máxima de asientos turistas debe ser un número entero", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Integer.parseInt(cantMaxAsientosEjecutivos);
		} catch (NumberFormatException e ) {
			JOptionPane.showMessageDialog(this, "La cantidad máxima de asientos ejecutivos debe ser un número entero", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!validarCantAsientos(Integer.parseInt(cantMaxAsientosTuristas))) {
			JOptionPane.showMessageDialog(this, "La cantidad máxima de asientos turistas no puede ser negativa", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!validarCantAsientos(Integer.parseInt(cantMaxAsientosEjecutivos))) {
			JOptionPane.showMessageDialog(this, "La cantidad máxima de asientos ejecutivos no puede ser negativa", "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			this.iRutaVuelo.ingresarDatosVuelo(nombre, Integer.parseInt(cantMaxAsientosTuristas), Integer.parseInt(cantMaxAsientosEjecutivos), duracion, fecha, rutaVuelo, fechaAlta, null);
			JOptionPane.showMessageDialog(this, "El vuelo se ha creado exitosamente", "Alta de vuelo", JOptionPane.INFORMATION_MESSAGE);
			this.limpiarEntrada();
		} catch (VueloRegistradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de vuelo", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void inicializarAerolineas() {
		Set<String> aerolineasSet = iUsuario.obtenerAerolineas();
		String[] aerolineasArray = aerolineasSet.toArray(new String[0]);
		aerolineaComboBox.setModel(new DefaultComboBoxModel<String>(aerolineasArray));
		aerolineaComboBox.repaint();
	}

	public void inicializarRutaVuelos(String aerolinea) throws UsuarioNoExisteException {
		Set<String> nombresRutas = new HashSet<>();
		Set<DTRutaVuelo> dtRutaVuelos =  this.iUsuario.obtenerRutasAerolinea(aerolinea);
		for (DTRutaVuelo ruta : dtRutaVuelos) {
			nombresRutas.add(ruta.getNombre());
		}
		DefaultComboBoxModel<String> modelNick = new DefaultComboBoxModel<String>(nombresRutas.toArray(new String[0]));
		rutaVueloComboBox.setModel(modelNick);
		rutaVueloComboBox.repaint();
	}

	public void limpiarEntrada() {
		this.nombreField.setText("");
		this.aerolineaComboBox.setSelectedItem("");
		this.rutaVueloComboBox.setSelectedItem("");
		this.diaFechaField.setText("");
		this.mesFechaField.setText("");
		this.anioFechaField.setText("");
		this.horaDuracionField.setText("");
		this.minDuracionField.setText("");
		this.cantMaxAsientosTuristasField.setText("");
		this.cantMaxAsientosEjecutivosField.setText("");
		this.diaFechaAltaField.setText("");
		this.mesFechaAltaField.setText("");
		this.anioFechaAltaField.setText("");
	}

	private boolean validarAerolinea(String aerolinea) {
		Set<String> aerolineasSet = iUsuario.obtenerAerolineas();
		return aerolineasSet.contains(aerolinea);
	}

	private boolean validarCampos(String aerolinea, String rutaVuelo, String nombre, String dia, String mes, String anio, String diaFechaAlta, String mesFechaAlta, String anioFechaAlta, String cantMaxAsientosTuristas, String cantMaxAsientosEjecutivos, String horaDuracion, String minDuracion) {
		if ((aerolinea == null) || (rutaVuelo == null) ||  nombre.isBlank() || dia.isBlank() || mes.isBlank() || anio.isBlank() || diaFechaAlta.isBlank() || mesFechaAlta.isBlank() || anioFechaAlta.isBlank() || cantMaxAsientosTuristas.isBlank() || cantMaxAsientosEjecutivos.isBlank() || horaDuracion.isBlank() || minDuracion.isBlank()) {
			return false;
		}
		return true;
	}

	private boolean validarCantAsientos(int cantAsientos) {
		if (cantAsientos < 0) {
			return false;
		}
		return true;
	}

	private boolean validarRutaVuelo(String rutaVuelo) {
		Set<String> rutaVueloSet = iRutaVuelo.obtenerRutasVuelo();
		return rutaVueloSet.contains(rutaVuelo);
	}

}
