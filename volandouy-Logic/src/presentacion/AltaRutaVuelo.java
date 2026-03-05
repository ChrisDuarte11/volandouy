package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import datatypes.DTCiudad;
import datatypes.DTCostos;
import excepciones.RutaVueloExistenteException;
import excepciones.UsuarioNoExisteException;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;
import logica.vuelo.categoria.Categoria;

@SuppressWarnings("serial")
public class AltaRutaVuelo extends JInternalFrame {

	private IRutaVuelo iRutaVuelo;
	private IUsuario iUsuario;

	private JTextArea areaDescripcion;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JComboBox<String> comboAerolineas;
	private JComboBox<String> comboCiudadDestino;
	private JComboBox<String> comboCiudadOrigen;
	private JComboBox<String> comboPaisDestino;
	private JComboBox<String> comboPaisOrigen;
	private JTextField fieldAnio;
	private JTextField fieldCostoEjecutivo;
	private JTextField fieldCostoTurista;
	private JTextField fieldDia;
	private JTextField fieldEquipajeExtra;
	private JTextField fieldHora;
	private JTextField fieldMes;
	private JTextField fieldMinuto;
	private JTextField fieldNombre;
	private JLabel lblEquipajeExtra;
	private JLabel lblFechaSeparador_1;
	private JLabel lblFechaSeparador_2;
	private JLabel lblHoraSeparador;
	private JList<String> listCategorias;
	private JScrollPane scrollCategorias;
	private JScrollPane scrollDescripcion;
	private JLabel lblDescripcionCorta;
	private JTextField txtDescCorta;

    public AltaRutaVuelo(IUsuario interfazUsuario, IRutaVuelo interfazRutaVuelo) {
		this.iUsuario = interfazUsuario;
		this.iRutaVuelo = interfazRutaVuelo;

    	setTitle("Alta de Ruta de Vuelo");
    	setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setClosable(true);
    	setBounds(0, 0, 800, 540);

    	// WindowBuilder

    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{25, 180, 30, 0, 30, 0, 50, 0, 150, 0, 0, 120, 25};
    	gridBagLayout.rowHeights = new int[]{30, 0, 80, 0, 80, 0, 0, 0, 0, 0, 0, 0, 30, 0, 30, 0};
    	gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
    	gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
    	getContentPane().setLayout(gridBagLayout);

    	// Seleccionar aerolínea

    	JLabel lblAerolineas = new JLabel("Seleccionar aerolínea:");
    	GridBagConstraints gbc_lblAerolineas = new GridBagConstraints();
    	gbc_lblAerolineas.insets = new Insets(0, 0, 5, 5);
    	gbc_lblAerolineas.anchor = GridBagConstraints.EAST;
    	gbc_lblAerolineas.gridx = 1;
    	gbc_lblAerolineas.gridy = 1;
    	getContentPane().add(lblAerolineas, gbc_lblAerolineas);

    	comboAerolineas = new JComboBox<String>();
    	GridBagConstraints gbc_comboAerolineas = new GridBagConstraints();
    	gbc_comboAerolineas.gridwidth = 10;
    	gbc_comboAerolineas.insets = new Insets(0, 0, 5, 5);
    	gbc_comboAerolineas.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboAerolineas.gridx = 2;
    	gbc_comboAerolineas.gridy = 1;
    	getContentPane().add(comboAerolineas, gbc_comboAerolineas);

    	// Seleccionar categoría

    	JLabel lblCategorias = new JLabel("Seleccionar categorías:");
    	GridBagConstraints gbc_lblCategorias = new GridBagConstraints();
    	gbc_lblCategorias.anchor = GridBagConstraints.EAST;
    	gbc_lblCategorias.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCategorias.gridx = 1;
    	gbc_lblCategorias.gridy = 2;
    	getContentPane().add(lblCategorias, gbc_lblCategorias);

    	scrollCategorias = new JScrollPane();
    	GridBagConstraints gbc_scrollCategorias = new GridBagConstraints();
    	gbc_scrollCategorias.gridwidth = 10;
    	gbc_scrollCategorias.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollCategorias.fill = GridBagConstraints.BOTH;
    	gbc_scrollCategorias.gridx = 2;
    	gbc_scrollCategorias.gridy = 2;
    	getContentPane().add(scrollCategorias, gbc_scrollCategorias);

		listCategorias = new JList<String>();
		scrollCategorias.setViewportView(listCategorias);

    	// Ingresar nombre

    	JLabel lblNombre = new JLabel("Nombre:");
    	GridBagConstraints gbc_lblNombre = new GridBagConstraints();
    	gbc_lblNombre.anchor = GridBagConstraints.EAST;
    	gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombre.gridx = 1;
    	gbc_lblNombre.gridy = 3;
    	getContentPane().add(lblNombre, gbc_lblNombre);

    	fieldNombre = new JTextField();
    	GridBagConstraints gbc_fieldNombre = new GridBagConstraints();
    	gbc_fieldNombre.gridwidth = 10;
    	gbc_fieldNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldNombre.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldNombre.gridx = 2;
    	gbc_fieldNombre.gridy = 3;
    	getContentPane().add(fieldNombre, gbc_fieldNombre);
    	fieldNombre.setColumns(10);

    	// Ingresar descripción

    	JLabel lblDescripcion = new JLabel("Descripción:");
    	GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
    	gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
    	gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDescripcion.gridx = 1;
    	gbc_lblDescripcion.gridy = 4;
    	getContentPane().add(lblDescripcion, gbc_lblDescripcion);

    	scrollDescripcion = new JScrollPane();
    	GridBagConstraints gbc_scrollDescripcion = new GridBagConstraints();
    	gbc_scrollDescripcion.gridwidth = 10;
    	gbc_scrollDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollDescripcion.fill = GridBagConstraints.BOTH;
    	gbc_scrollDescripcion.gridx = 2;
    	gbc_scrollDescripcion.gridy = 4;
    	getContentPane().add(scrollDescripcion, gbc_scrollDescripcion);

    	areaDescripcion = new JTextArea();
    	scrollDescripcion.setViewportView(areaDescripcion);

		// Costos

		JLabel lblCostoTurista = new JLabel("Costo tipo turista:");
		GridBagConstraints gbc_lblCostoTurista = new GridBagConstraints();
		gbc_lblCostoTurista.anchor = GridBagConstraints.EAST;
		gbc_lblCostoTurista.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoTurista.gridx = 1;
		gbc_lblCostoTurista.gridy = 5;
		getContentPane().add(lblCostoTurista, gbc_lblCostoTurista);

		fieldCostoTurista = new JTextField();
		GridBagConstraints gbc_fieldCostoTurista = new GridBagConstraints();
		gbc_fieldCostoTurista.gridwidth = 10;
		gbc_fieldCostoTurista.insets = new Insets(0, 0, 5, 5);
		gbc_fieldCostoTurista.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldCostoTurista.gridx = 2;
		gbc_fieldCostoTurista.gridy = 5;
		getContentPane().add(fieldCostoTurista, gbc_fieldCostoTurista);
		fieldCostoTurista.setColumns(10);

		JLabel lblCostoEjecutivo = new JLabel("Costo tipo ejecutivo:");
		GridBagConstraints gbc_lblCostoEjecutivo = new GridBagConstraints();
		gbc_lblCostoEjecutivo.anchor = GridBagConstraints.EAST;
		gbc_lblCostoEjecutivo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoEjecutivo.gridx = 1;
		gbc_lblCostoEjecutivo.gridy = 6;
		getContentPane().add(lblCostoEjecutivo, gbc_lblCostoEjecutivo);

		fieldCostoEjecutivo = new JTextField();
		GridBagConstraints gbc_fieldCostoEjecutivo = new GridBagConstraints();
		gbc_fieldCostoEjecutivo.gridwidth = 10;
		gbc_fieldCostoEjecutivo.insets = new Insets(0, 0, 5, 5);
		gbc_fieldCostoEjecutivo.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldCostoEjecutivo.gridx = 2;
		gbc_fieldCostoEjecutivo.gridy = 6;
		getContentPane().add(fieldCostoEjecutivo, gbc_fieldCostoEjecutivo);
		fieldCostoEjecutivo.setColumns(10);

    	lblEquipajeExtra = new JLabel("Costo equipaje extra:");
    	GridBagConstraints gbc_lblEquipajeExtra = new GridBagConstraints();
    	gbc_lblEquipajeExtra.anchor = GridBagConstraints.EAST;
    	gbc_lblEquipajeExtra.insets = new Insets(0, 0, 5, 5);
    	gbc_lblEquipajeExtra.gridx = 1;
    	gbc_lblEquipajeExtra.gridy = 7;
    	getContentPane().add(lblEquipajeExtra, gbc_lblEquipajeExtra);

    	fieldEquipajeExtra = new JTextField();
    	GridBagConstraints gbc_fieldEquipajeExtra = new GridBagConstraints();
    	gbc_fieldEquipajeExtra.gridwidth = 10;
    	gbc_fieldEquipajeExtra.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldEquipajeExtra.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldEquipajeExtra.gridx = 2;
    	gbc_fieldEquipajeExtra.gridy = 7;
    	getContentPane().add(fieldEquipajeExtra, gbc_fieldEquipajeExtra);
    	fieldEquipajeExtra.setColumns(10);

    	// Hora

    	JLabel lblHora = new JLabel("Hora:");
    	GridBagConstraints gbc_lblHora = new GridBagConstraints();
    	gbc_lblHora.anchor = GridBagConstraints.EAST;
    	gbc_lblHora.insets = new Insets(0, 0, 5, 5);
    	gbc_lblHora.gridx = 1;
    	gbc_lblHora.gridy = 8;
    	getContentPane().add(lblHora, gbc_lblHora);

    	fieldHora = new JTextField();
    	fieldHora.setEditable(true);
    	GridBagConstraints gbc_fieldHora = new GridBagConstraints();
    	gbc_fieldHora.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldHora.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldHora.gridx = 2;
    	gbc_fieldHora.gridy = 8;
    	getContentPane().add(fieldHora, gbc_fieldHora);
    	fieldHora.setColumns(10);

    	lblHoraSeparador = new JLabel(":");
    	GridBagConstraints gbc_lblHoraSeparador = new GridBagConstraints();
    	gbc_lblHoraSeparador.insets = new Insets(0, 0, 5, 5);
    	gbc_lblHoraSeparador.gridx = 3;
    	gbc_lblHoraSeparador.gridy = 8;
    	getContentPane().add(lblHoraSeparador, gbc_lblHoraSeparador);

    	fieldMinuto = new JTextField();
    	fieldMinuto.setEditable(true);
    	GridBagConstraints gbc_fieldMinuto = new GridBagConstraints();
    	gbc_fieldMinuto.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldMinuto.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldMinuto.gridx = 4;
    	gbc_fieldMinuto.gridy = 8;
    	getContentPane().add(fieldMinuto, gbc_fieldMinuto);
    	fieldMinuto.setColumns(10);

    	// Fecha

    	JLabel lblFecha = new JLabel("Fecha:");
    	GridBagConstraints gbc_lblFecha = new GridBagConstraints();
    	gbc_lblFecha.anchor = GridBagConstraints.EAST;
    	gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFecha.gridx = 1;
    	gbc_lblFecha.gridy = 9;
    	getContentPane().add(lblFecha, gbc_lblFecha);

    	fieldDia = new JTextField();
    	fieldDia.setEditable(true);
    	GridBagConstraints gbc_fieldDia = new GridBagConstraints();
    	gbc_fieldDia.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldDia.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldDia.gridx = 2;
    	gbc_fieldDia.gridy = 9;
    	getContentPane().add(fieldDia, gbc_fieldDia);
    	fieldDia.setColumns(10);

    	lblFechaSeparador_1 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparador_1 = new GridBagConstraints();
    	gbc_lblFechaSeparador_1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparador_1.gridx = 3;
    	gbc_lblFechaSeparador_1.gridy = 9;
    	getContentPane().add(lblFechaSeparador_1, gbc_lblFechaSeparador_1);

    	fieldMes = new JTextField();
       	fieldMes.setEditable(true);
      	GridBagConstraints gbc_fieldMes = new GridBagConstraints();
    	gbc_fieldMes.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldMes.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldMes.gridx = 4;
    	gbc_fieldMes.gridy = 9;
    	getContentPane().add(fieldMes, gbc_fieldMes);
    	fieldMes.setColumns(10);

    	lblFechaSeparador_2 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparador_2 = new GridBagConstraints();
    	gbc_lblFechaSeparador_2.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparador_2.gridx = 5;
    	gbc_lblFechaSeparador_2.gridy = 9;
    	getContentPane().add(lblFechaSeparador_2, gbc_lblFechaSeparador_2);

    	fieldAnio = new JTextField();
    	fieldAnio.setEditable(true);
    	GridBagConstraints gbc_fieldAnio = new GridBagConstraints();
    	gbc_fieldAnio.insets = new Insets(0, 0, 5, 5);
    	gbc_fieldAnio.fill = GridBagConstraints.HORIZONTAL;
    	gbc_fieldAnio.gridx = 6;
    	gbc_fieldAnio.gridy = 9;
    	getContentPane().add(fieldAnio, gbc_fieldAnio);
    	fieldAnio.setColumns(10);

    	// Ciudad origen

    	JLabel lblCiudadOrigen = new JLabel("Ciudad de origen:");
    	GridBagConstraints gbc_lblCiudadOrigen = new GridBagConstraints();
    	gbc_lblCiudadOrigen.anchor = GridBagConstraints.EAST;
    	gbc_lblCiudadOrigen.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCiudadOrigen.gridx = 1;
    	gbc_lblCiudadOrigen.gridy = 10;
    	getContentPane().add(lblCiudadOrigen, gbc_lblCiudadOrigen);

    	comboPaisOrigen = new JComboBox<String>();
    	comboPaisOrigen.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			actualizarCiudadOrigen((String) comboPaisOrigen.getSelectedItem());
    		}
    	});
    	GridBagConstraints gbc_comboPaisOrigen = new GridBagConstraints();
    	gbc_comboPaisOrigen.gridwidth = 6;
    	gbc_comboPaisOrigen.insets = new Insets(0, 0, 5, 5);
    	gbc_comboPaisOrigen.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboPaisOrigen.gridx = 2;
    	gbc_comboPaisOrigen.gridy = 10;
    	getContentPane().add(comboPaisOrigen, gbc_comboPaisOrigen);

		comboCiudadOrigen = new JComboBox<String>();
		GridBagConstraints gbc_comboCiudadOrigen = new GridBagConstraints();
		gbc_comboCiudadOrigen.insets = new Insets(0, 0, 5, 5);
		gbc_comboCiudadOrigen.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboCiudadOrigen.gridx = 8;
		gbc_comboCiudadOrigen.gridy = 10;
		getContentPane().add(comboCiudadOrigen, gbc_comboCiudadOrigen);

    	// Ciudad destino

    	JLabel lblCiudadDestino = new JLabel("Ciudad de destino:");
    	GridBagConstraints gbc_lblCiudadDestino = new GridBagConstraints();
    	gbc_lblCiudadDestino.anchor = GridBagConstraints.EAST;
    	gbc_lblCiudadDestino.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCiudadDestino.gridx = 1;
    	gbc_lblCiudadDestino.gridy = 11;
    	getContentPane().add(lblCiudadDestino, gbc_lblCiudadDestino);

    	comboPaisDestino = new JComboBox<String>();
    	comboPaisDestino.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			actualizarCiudadDestino((String) comboPaisDestino.getSelectedItem());
    		}
    	});
    	GridBagConstraints gbc_comboPaisDestino = new GridBagConstraints();
    	gbc_comboPaisDestino.gridwidth = 6;
    	gbc_comboPaisDestino.insets = new Insets(0, 0, 5, 5);
    	gbc_comboPaisDestino.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboPaisDestino.gridx = 2;
    	gbc_comboPaisDestino.gridy = 11;
    	getContentPane().add(comboPaisDestino, gbc_comboPaisDestino);

		comboCiudadDestino = new JComboBox<String>();
		GridBagConstraints gbc_comboCiudadDestino = new GridBagConstraints();
		gbc_comboCiudadDestino.insets = new Insets(0, 0, 5, 5);
		gbc_comboCiudadDestino.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboCiudadDestino.gridx = 8;
		gbc_comboCiudadDestino.gridy = 11;
		getContentPane().add(comboCiudadDestino, gbc_comboCiudadDestino);

    	// Botones

    	btnAceptar = new JButton("Aceptar");
    	btnAceptar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			altaRutaVuelo();
    		}
    	});

    	lblDescripcionCorta = new JLabel("Descripcion Corta:");
    	GridBagConstraints gbc_lblDescripcionCorta = new GridBagConstraints();
    	gbc_lblDescripcionCorta.anchor = GridBagConstraints.EAST;
    	gbc_lblDescripcionCorta.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDescripcionCorta.gridx = 1;
    	gbc_lblDescripcionCorta.gridy = 12;
    	getContentPane().add(lblDescripcionCorta, gbc_lblDescripcionCorta);

    	txtDescCorta = new JTextField();
    	txtDescCorta.setColumns(10);
    	GridBagConstraints gbc_txtDescCorta = new GridBagConstraints();
    	gbc_txtDescCorta.gridwidth = 7;
    	gbc_txtDescCorta.insets = new Insets(0, 0, 5, 5);
    	gbc_txtDescCorta.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtDescCorta.gridx = 2;
    	gbc_txtDescCorta.gridy = 12;
    	getContentPane().add(txtDescCorta, gbc_txtDescCorta);
    	GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
    	gbc_btnAceptar.anchor = GridBagConstraints.EAST;
    	gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnAceptar.gridx = 10;
    	gbc_btnAceptar.gridy = 13;
    	getContentPane().add(btnAceptar, gbc_btnAceptar);

    	btnCancelar = new JButton("Cancelar");
    	btnCancelar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			limpiarEntrada();
    			setVisible(false);
    		}
    	});
    	GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
    	gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnCancelar.anchor = GridBagConstraints.EAST;
    	gbc_btnCancelar.gridx = 11;
    	gbc_btnCancelar.gridy = 13;
    	getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

	private void actualizarCiudadDestino(String Pais) {
		Set<String> ciudades = iRutaVuelo.obtenerNombresCiudades(Pais);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(ciudades.toArray(new String[0]));
		comboCiudadDestino.setModel(model);
		comboCiudadDestino.repaint();
	}

	private void actualizarCiudadOrigen(String Pais) {
		Set<String> ciudades = iRutaVuelo.obtenerNombresCiudades(Pais);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(ciudades.toArray(new String[0]));
		comboCiudadOrigen.setModel(model);
		comboCiudadOrigen.repaint();
	}

	private void altaRutaVuelo() {
		String aerolinea = (String) comboAerolineas.getSelectedItem();
		List<String> categorias = listCategorias.getSelectedValuesList();
		String nombre = fieldNombre.getText();
		String descripcion = areaDescripcion.getText();
		String costoTurista = fieldCostoTurista.getText();
		String costoEjecutivo = fieldCostoEjecutivo.getText();
		String costoEquipaje = fieldEquipajeExtra.getText();
		String hora = fieldHora.getText();
		String minuto = fieldMinuto.getText();
		String dia = fieldDia.getText();
		String mes = fieldMes.getText();
		String anio = fieldAnio.getText();
		String paisOrigen = (String) comboPaisOrigen.getSelectedItem();
		String ciudadOrigen = (String) comboCiudadOrigen.getSelectedItem();
		String paisDestino = (String) comboPaisDestino.getSelectedItem();
		String ciudadDestino = (String) comboCiudadDestino.getSelectedItem();

		LocalTime tiempo = null;
		LocalDate fecha = null;

		if (!validarCampos(nombre, descripcion, costoEjecutivo, costoTurista, costoEquipaje, hora, minuto, dia, mes, anio, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, aerolinea)) {
        	JOptionPane.showMessageDialog(this, "Debe rellenar todos los campos", "Alta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
        	return;
		}

		try {
			Float.parseFloat(costoEjecutivo);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El costo del asiento ejecutivo debe ser un número", "Alta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
			this.fieldCostoEjecutivo.setText("");
			return;
		}

		try {
			Float.parseFloat(costoTurista);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El costo del asiento turista debe ser un número", "Alta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
			this.fieldCostoTurista.setText("");
			return;
		}

		try {
			Float.parseFloat(costoEquipaje);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El costo del equipaje extra debe ser un número", "Alta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
			this.fieldEquipajeExtra.setText("");
			return;
		}

		try {
			fecha = LocalDate.of(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
		} catch (DateTimeException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ingrese una fecha válida", "Alta de Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
			this.fieldMes.setText("");
	    	this.fieldAnio.setText("");
	    	this.fieldDia.setText("");
			return;
		}

		try {
			tiempo = LocalTime.of(Integer.valueOf(hora), Integer.valueOf(minuto));
		} catch (DateTimeException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ingrese una hora válida", "Alta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
			this.fieldHora.setText("");
	    	this.fieldMinuto.setText("");
			return;
		}

		if (paisOrigen == paisDestino && ciudadOrigen == ciudadDestino) {
			JOptionPane.showMessageDialog(this, "El lugar de origen debe ser distinto al lugar de destino", "Alta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
        	return;
		}

		DTCiudad cOrigen = iRutaVuelo.obtenerDTCiudadPais(ciudadOrigen, paisOrigen);
		DTCiudad cDestino = iRutaVuelo.obtenerDTCiudadPais(ciudadDestino, paisDestino);
		DTCostos costos = new DTCostos(Float.parseFloat(costoEjecutivo), Float.parseFloat(costoTurista), Float.parseFloat(costoEquipaje));
		Set<Categoria> listaCategorias = iRutaVuelo.obtenerCategorias(categorias);

		try {
			this.iRutaVuelo.ingresarDatosRuta(nombre, descripcion, tiempo, costos, cOrigen, cDestino, fecha, listaCategorias, aerolinea, null, txtDescCorta.getText());

			JOptionPane.showMessageDialog(this, "La ruta de vuelo se ha creado exitosamente", "Alta de ruta de vuelo", JOptionPane.INFORMATION_MESSAGE);
			this.limpiarEntrada();
		} catch (RutaVueloExistenteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Alta Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
			this.fieldNombre.setText("");
			return;
		} catch (NumberFormatException | UsuarioNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Alta Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void inicializarDatos() {
		Set<String> nickAerolineas = iUsuario.obtenerAerolineas();
		DefaultComboBoxModel<String> modelNick = new DefaultComboBoxModel<String>(nickAerolineas.toArray(new String[0]));
		comboAerolineas.setModel(modelNick);
		comboAerolineas.repaint();

		Set<String> categorias = iRutaVuelo.obtenerCategorias();
		listCategorias.setListData(categorias.toArray(new String[0]));
		listCategorias.repaint();

		Set<String> paisesOrigen = iRutaVuelo.obtenerPaises();
		DefaultComboBoxModel<String> modelPaisesOrigen = new DefaultComboBoxModel<String>(paisesOrigen.toArray(new String[0]));
		comboPaisOrigen.setModel(modelPaisesOrigen);
		comboPaisOrigen.repaint();

		Set<String> paisesDestino = iRutaVuelo.obtenerPaises();
		DefaultComboBoxModel<String> modelPaisesDestino = new DefaultComboBoxModel<String>(paisesDestino.toArray(new String[0]));
		comboPaisDestino.setModel(modelPaisesDestino);
		comboPaisDestino.repaint();
	}

    public void limpiarEntrada() {
    	this.fieldNombre.setText("");
    	this.areaDescripcion.setText("");
    	this.fieldCostoEjecutivo.setText("");
    	this.fieldCostoTurista.setText("");
    	this.fieldEquipajeExtra.setText("");
    	this.fieldHora.setText("");
    	this.fieldMinuto.setText("");
    	this.fieldMes.setText("");
    	this.fieldAnio.setText("");
        this.fieldDia.setText("");
    }

    private boolean validarCampos(String nombre, String descripcion, String costoEjecutivo, String costoTurista, String costoExtra, String hora, String minuto, String dia, String mes, String anio, String ciudadOrigen, String paisOrigen, String ciudadDestino, String paisDestino, String aerolinea) {
    	if (nombre.isBlank() || descripcion.isBlank()|| costoEjecutivo.isBlank() || costoTurista.isBlank() || costoExtra.isBlank() || hora.isBlank() || minuto.isBlank() || dia.isBlank() || anio.isBlank()
    		|| paisOrigen == null || paisOrigen.isBlank() || ciudadOrigen == null || ciudadOrigen.isBlank() || paisDestino == null || paisDestino.isBlank() || ciudadDestino == null || ciudadDestino.isBlank() || aerolinea.isBlank()) {
   			return false;
   		}
        return true;
    }

}
