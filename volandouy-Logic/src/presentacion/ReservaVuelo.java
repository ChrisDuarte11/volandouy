package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import datatypes.DTPasaje;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTVuelo;
import excepciones.ClienteTieneReservaEnVueloException;
import excepciones.ReservaCantidadAsientosInvalidos;
import excepciones.RutaNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.enums.TipoAsiento;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

@SuppressWarnings("serial")
public class ReservaVuelo extends JInternalFrame {

	private IRutaVuelo iRutaVuelo;
	private IUsuario iUsuario;
	private Boolean ignorarSeleccion = false;
	private Boolean pasajeApellidoOK = false;
	private Boolean pasajeNombreOK = false;
	private DTPasaje pasajeSeleccionado = null;

	private JButton btnAgregar;
	private JButton btnEliminar;
	private JComboBox<String> comboBoxClientes;
	private JComboBox<String> comboBoxTipoAsiento;
	private JList<String> listAerolineas;
	private JList<DTPasaje> listPasajes;
	private JList<DTRutaVuelo> listRutas;
	private JList<DTVuelo> listVuelos;
	private JSpinner spinnerCantidadPasajes;
	private JTextField textAnioReserva;
	private JTextField textAnioVuelo;
	private JTextField textAsientosEjecutivo;
	private JTextField textAsientosTurista;
	private JTextField textDiaReserva;
	private JTextField textDiaVuelo;
	private JTextField textEquipajeExtra;
	private JTextField textHoraVuelo;
	private JTextField textMesReserva;
	private JTextField textMesVuelo;
	private JTextField textMinutoVuelo;
	private JTextField textNombreVuelo;
	private JTextField textPasajeApellido;
	private JTextField textPasajeNombre;

	public ReservaVuelo(IUsuario interfazUsuario, IRutaVuelo interfazRutaVuelo) {
		this.iUsuario = interfazUsuario;
		this.iRutaVuelo = interfazRutaVuelo;

		setTitle("Reserva de vuelo");
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setBounds(0, 0, 1350, 650);

    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{250, 425, 300, 0};
    	gridBagLayout.rowHeights = new int[]{300, 300, 0};
    	gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
    	gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
    	getContentPane().setLayout(gridBagLayout);

    	JPanel panelAerolineas = new JPanel();
    	panelAerolineas.setBorder(new TitledBorder(null, "Aerol\u00EDneas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelAerolineas = new GridBagConstraints();
    	gbc_panelAerolineas.insets = new Insets(0, 0, 5, 5);
    	gbc_panelAerolineas.fill = GridBagConstraints.BOTH;
    	gbc_panelAerolineas.gridx = 0;
    	gbc_panelAerolineas.gridy = 0;
    	getContentPane().add(panelAerolineas, gbc_panelAerolineas);
    	panelAerolineas.setLayout(new GridLayout(1, 0, 0, 0));

    	JScrollPane scrollAerolineas = new JScrollPane();
    	panelAerolineas.add(scrollAerolineas);

    	listAerolineas = new JList<String>();
    	listAerolineas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	listAerolineas.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting() && !ignorarSeleccion) {
    				JList<String> listaAerolineas = (JList<String>) e.getSource();
    				String aerolinea = listaAerolineas.getSelectedValue();
    				cargarRutas(aerolinea);
    			}
    		}
    	});
    	scrollAerolineas.setViewportView(listAerolineas);

    	JPanel panelRutas = new JPanel();
    	panelRutas.setBorder(new TitledBorder(null, "Rutas de vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelRutas = new GridBagConstraints();
    	gbc_panelRutas.insets = new Insets(0, 0, 5, 5);
    	gbc_panelRutas.fill = GridBagConstraints.BOTH;
    	gbc_panelRutas.gridx = 1;
    	gbc_panelRutas.gridy = 0;
    	getContentPane().add(panelRutas, gbc_panelRutas);
    	panelRutas.setLayout(new GridLayout(1, 0, 0, 0));

    	JScrollPane scrollRutas = new JScrollPane();
    	panelRutas.add(scrollRutas);

    	listRutas = new JList<DTRutaVuelo>();
    	listRutas.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting() && !ignorarSeleccion) {
    				JList<DTRutaVuelo> listaRutas = (JList<DTRutaVuelo>) e.getSource();
    				DTRutaVuelo ruta = listaRutas.getSelectedValue();
    				cargarVuelos(ruta);
    			}
    		}
    	});
    	listRutas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	scrollRutas.setViewportView(listRutas);

    	JPanel panelReserva = new JPanel();
    	panelReserva.setBorder(new TitledBorder(null, "Reserva", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelReserva = new GridBagConstraints();
    	gbc_panelReserva.insets = new Insets(0, 0, 5, 0);
    	gbc_panelReserva.fill = GridBagConstraints.BOTH;
    	gbc_panelReserva.gridx = 2;
    	gbc_panelReserva.gridy = 0;
    	getContentPane().add(panelReserva, gbc_panelReserva);
    	GridBagLayout gbl_panelReserva = new GridBagLayout();
    	gbl_panelReserva.columnWidths = new int[]{25, 210, 30, 0, 30, 0, 50, 0, 0, 120, 25, 0};
    	gbl_panelReserva.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 30, 0, 30, 0};
    	gbl_panelReserva.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    	gbl_panelReserva.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
    	panelReserva.setLayout(gbl_panelReserva);

    	JLabel lblCliente = new JLabel("Cliente:");
    	GridBagConstraints gbc_lblCliente = new GridBagConstraints();
    	gbc_lblCliente.anchor = GridBagConstraints.EAST;
    	gbc_lblCliente.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCliente.gridx = 1;
    	gbc_lblCliente.gridy = 1;
    	panelReserva.add(lblCliente, gbc_lblCliente);

    	comboBoxClientes = new JComboBox<String>();
    	GridBagConstraints gbc_comboBoxClientes = new GridBagConstraints();
    	gbc_comboBoxClientes.gridwidth = 8;
    	gbc_comboBoxClientes.insets = new Insets(0, 0, 5, 5);
    	gbc_comboBoxClientes.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboBoxClientes.gridx = 2;
    	gbc_comboBoxClientes.gridy = 1;
    	panelReserva.add(comboBoxClientes, gbc_comboBoxClientes);

    	JLabel lblTipoAsiento = new JLabel("Tipo de asiento:");
    	GridBagConstraints gbc_lblTipoAsiento = new GridBagConstraints();
    	gbc_lblTipoAsiento.anchor = GridBagConstraints.EAST;
    	gbc_lblTipoAsiento.insets = new Insets(0, 0, 5, 5);
    	gbc_lblTipoAsiento.gridx = 1;
    	gbc_lblTipoAsiento.gridy = 2;
    	panelReserva.add(lblTipoAsiento, gbc_lblTipoAsiento);

    	comboBoxTipoAsiento = new JComboBox<String>();
    	comboBoxTipoAsiento.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			actualizarCantidadPasajes();
    		}
    	});
    	for (TipoAsiento tipo : TipoAsiento.values()) {
    		comboBoxTipoAsiento.addItem(tipo.getTipo());
    	}
    	GridBagConstraints gbc_comboBoxTipoAsiento = new GridBagConstraints();
    	gbc_comboBoxTipoAsiento.gridwidth = 8;
    	gbc_comboBoxTipoAsiento.insets = new Insets(0, 0, 5, 5);
    	gbc_comboBoxTipoAsiento.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboBoxTipoAsiento.gridx = 2;
    	gbc_comboBoxTipoAsiento.gridy = 2;
    	panelReserva.add(comboBoxTipoAsiento, gbc_comboBoxTipoAsiento);

    	JLabel lblCantidadPasajes = new JLabel("Cantidad de pasajes:");
    	GridBagConstraints gbc_lblCantidadPasajes = new GridBagConstraints();
    	gbc_lblCantidadPasajes.anchor = GridBagConstraints.EAST;
    	gbc_lblCantidadPasajes.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCantidadPasajes.gridx = 1;
    	gbc_lblCantidadPasajes.gridy = 3;
    	panelReserva.add(lblCantidadPasajes, gbc_lblCantidadPasajes);

    	spinnerCantidadPasajes = new JSpinner();
    	GridBagConstraints gbc_spinnerCantidadPasajes = new GridBagConstraints();
    	gbc_spinnerCantidadPasajes.gridwidth = 3;
    	gbc_spinnerCantidadPasajes.fill = GridBagConstraints.HORIZONTAL;
    	gbc_spinnerCantidadPasajes.insets = new Insets(0, 0, 5, 5);
    	gbc_spinnerCantidadPasajes.gridx = 2;
    	gbc_spinnerCantidadPasajes.gridy = 3;
    	panelReserva.add(spinnerCantidadPasajes, gbc_spinnerCantidadPasajes);


    	ImageIcon icon = (ImageIcon) UIManager.getIcon("OptionPane.informationIcon");
    	Image imagen = icon.getImage();
    	ImageIcon iconResized = new ImageIcon(imagen.getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH));

    	JLabel label = new JLabel(iconResized);
    	label.setToolTipText("En caso de que la cantidad de pasajes sea mayor que 1, \ningrese el nombre y apellido de aquellos pasajeros \nexcluyendo al cliente que realiza la reserva.");
    	GridBagConstraints gbc_label = new GridBagConstraints();
    	gbc_label.fill = GridBagConstraints.VERTICAL;
    	gbc_label.anchor = GridBagConstraints.WEST;
    	gbc_label.insets = new Insets(0, 0, 5, 5);
    	gbc_label.gridx = 6;
    	gbc_label.gridy = 3;
    	panelReserva.add(label, gbc_label);

    	JLabel lblEquipajeExtra = new JLabel("Cantidad de equipaje extra:");
    	GridBagConstraints gbc_lblEquipajeExtra = new GridBagConstraints();
    	gbc_lblEquipajeExtra.anchor = GridBagConstraints.EAST;
    	gbc_lblEquipajeExtra.insets = new Insets(0, 0, 5, 5);
    	gbc_lblEquipajeExtra.gridx = 1;
    	gbc_lblEquipajeExtra.gridy = 4;
    	panelReserva.add(lblEquipajeExtra, gbc_lblEquipajeExtra);

    	textEquipajeExtra = new JTextField();
    	GridBagConstraints gbc_textEquipajeExtra = new GridBagConstraints();
    	gbc_textEquipajeExtra.gridwidth = 3;
    	gbc_textEquipajeExtra.insets = new Insets(0, 0, 5, 5);
    	gbc_textEquipajeExtra.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textEquipajeExtra.gridx = 2;
    	gbc_textEquipajeExtra.gridy = 4;
    	panelReserva.add(textEquipajeExtra, gbc_textEquipajeExtra);
    	textEquipajeExtra.setColumns(10);

    	JLabel lblFechaDeReserva = new JLabel("Fecha de reserva:");
    	GridBagConstraints gbc_lblFechaDeReserva = new GridBagConstraints();
    	gbc_lblFechaDeReserva.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaDeReserva.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaDeReserva.gridx = 1;
    	gbc_lblFechaDeReserva.gridy = 5;
    	panelReserva.add(lblFechaDeReserva, gbc_lblFechaDeReserva);

    	textDiaReserva = new JTextField();
    	GridBagConstraints gbc_textDiaReserva = new GridBagConstraints();
    	gbc_textDiaReserva.insets = new Insets(0, 0, 5, 5);
    	gbc_textDiaReserva.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textDiaReserva.gridx = 2;
    	gbc_textDiaReserva.gridy = 5;
    	panelReserva.add(textDiaReserva, gbc_textDiaReserva);
    	textDiaReserva.setColumns(10);

    	JLabel lblFechaSeparadorReserva_1 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparadorReserva_1 = new GridBagConstraints();
    	gbc_lblFechaSeparadorReserva_1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparadorReserva_1.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaSeparadorReserva_1.gridx = 3;
    	gbc_lblFechaSeparadorReserva_1.gridy = 5;
    	panelReserva.add(lblFechaSeparadorReserva_1, gbc_lblFechaSeparadorReserva_1);

    	textMesReserva = new JTextField();
    	GridBagConstraints gbc_textMesReserva = new GridBagConstraints();
    	gbc_textMesReserva.insets = new Insets(0, 0, 5, 5);
    	gbc_textMesReserva.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textMesReserva.gridx = 4;
    	gbc_textMesReserva.gridy = 5;
    	panelReserva.add(textMesReserva, gbc_textMesReserva);
    	textMesReserva.setColumns(10);

    	JLabel lblFechaSeparadorReserva_2 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparadorReserva_2 = new GridBagConstraints();
    	gbc_lblFechaSeparadorReserva_2.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparadorReserva_2.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaSeparadorReserva_2.gridx = 5;
    	gbc_lblFechaSeparadorReserva_2.gridy = 5;
    	panelReserva.add(lblFechaSeparadorReserva_2, gbc_lblFechaSeparadorReserva_2);

    	textAnioReserva = new JTextField();
    	GridBagConstraints gbc_textAnioReserva = new GridBagConstraints();
    	gbc_textAnioReserva.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textAnioReserva.insets = new Insets(0, 0, 5, 5);
    	gbc_textAnioReserva.gridx = 6;
    	gbc_textAnioReserva.gridy = 5;
    	panelReserva.add(textAnioReserva, gbc_textAnioReserva);
    	textAnioReserva.setColumns(10);

    	JButton btnSiguiente = new JButton("Siguiente");
    	btnSiguiente.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			reservarVuelo();
    		}
    	});
    	GridBagConstraints gbc_btnSiguiente = new GridBagConstraints();
    	gbc_btnSiguiente.anchor = GridBagConstraints.EAST;
    	gbc_btnSiguiente.insets = new Insets(0, 0, 5, 5);
    	gbc_btnSiguiente.gridx = 8;
    	gbc_btnSiguiente.gridy = 7;
    	panelReserva.add(btnSiguiente, gbc_btnSiguiente);

    	JButton btnCancelar = new JButton("Cancelar");
    	GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
    	gbc_btnCancelar.anchor = GridBagConstraints.EAST;
    	gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnCancelar.gridx = 9;
    	gbc_btnCancelar.gridy = 7;
    	panelReserva.add(btnCancelar, gbc_btnCancelar);

    	JPanel panelVuelos = new JPanel();
    	panelVuelos.setBorder(new TitledBorder(null, "Vuelos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelVuelos = new GridBagConstraints();
    	gbc_panelVuelos.insets = new Insets(0, 0, 0, 5);
    	gbc_panelVuelos.fill = GridBagConstraints.BOTH;
    	gbc_panelVuelos.gridx = 0;
    	gbc_panelVuelos.gridy = 1;
    	getContentPane().add(panelVuelos, gbc_panelVuelos);
    	panelVuelos.setLayout(new GridLayout(1, 0, 0, 0));

    	JScrollPane scrollVuelos = new JScrollPane();
    	panelVuelos.add(scrollVuelos);

    	listVuelos = new JList<DTVuelo>();
    	listVuelos.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting() && !ignorarSeleccion) {
    				JList<DTVuelo> listaVuelos = (JList<DTVuelo>) e.getSource();
    				DTVuelo vuelo = listaVuelos.getSelectedValue();
    				cargarInfoVuelo(vuelo);
    				actualizarCantidadPasajes();
    			}
    		}
    	});
    	listVuelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	scrollVuelos.setViewportView(listVuelos);

    	JPanel panelInfoVuelo = new JPanel();
    	panelInfoVuelo.setBorder(new TitledBorder(null, "Informaci\u00F3n del vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelInfoVuelo = new GridBagConstraints();
    	gbc_panelInfoVuelo.insets = new Insets(0, 0, 0, 5);
    	gbc_panelInfoVuelo.fill = GridBagConstraints.BOTH;
    	gbc_panelInfoVuelo.gridx = 1;
    	gbc_panelInfoVuelo.gridy = 1;
    	getContentPane().add(panelInfoVuelo, gbc_panelInfoVuelo);
    	GridBagLayout gbl_panelInfoVuelo = new GridBagLayout();
    	gbl_panelInfoVuelo.columnWidths = new int[]{25, 230, 30, 0, 30, 0, 50, 25, 0};
    	gbl_panelInfoVuelo.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 30, 0};
    	gbl_panelInfoVuelo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    	gbl_panelInfoVuelo.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
    	panelInfoVuelo.setLayout(gbl_panelInfoVuelo);

    	JLabel lblNombreVuelo = new JLabel("Nombre:");
    	GridBagConstraints gbc_lblNombreVuelo = new GridBagConstraints();
    	gbc_lblNombreVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombreVuelo.anchor = GridBagConstraints.EAST;
    	gbc_lblNombreVuelo.gridx = 1;
    	gbc_lblNombreVuelo.gridy = 1;
    	panelInfoVuelo.add(lblNombreVuelo, gbc_lblNombreVuelo);

    	textNombreVuelo = new JTextField();
    	textNombreVuelo.setEditable(false);
    	GridBagConstraints gbc_textNombreVuelo = new GridBagConstraints();
    	gbc_textNombreVuelo.gridwidth = 5;
    	gbc_textNombreVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_textNombreVuelo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textNombreVuelo.gridx = 2;
    	gbc_textNombreVuelo.gridy = 1;
    	panelInfoVuelo.add(textNombreVuelo, gbc_textNombreVuelo);
    	textNombreVuelo.setColumns(10);

    	JLabel lblAsientosTurista = new JLabel("Asientos turista disponibles:");
    	GridBagConstraints gbc_lblAsientosTurista = new GridBagConstraints();
    	gbc_lblAsientosTurista.anchor = GridBagConstraints.EAST;
    	gbc_lblAsientosTurista.insets = new Insets(0, 0, 5, 5);
    	gbc_lblAsientosTurista.gridx = 1;
    	gbc_lblAsientosTurista.gridy = 2;
    	panelInfoVuelo.add(lblAsientosTurista, gbc_lblAsientosTurista);

    	textAsientosTurista = new JTextField();
    	textAsientosTurista.setEditable(false);
    	GridBagConstraints gbc_textAsientosTurista = new GridBagConstraints();
    	gbc_textAsientosTurista.gridwidth = 3;
    	gbc_textAsientosTurista.insets = new Insets(0, 0, 5, 5);
    	gbc_textAsientosTurista.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textAsientosTurista.gridx = 2;
    	gbc_textAsientosTurista.gridy = 2;
    	panelInfoVuelo.add(textAsientosTurista, gbc_textAsientosTurista);
    	textAsientosTurista.setColumns(10);

    	JLabel lblAsientosEjecutivo = new JLabel("Asientos ejecutivo disponibles:");
    	GridBagConstraints gbc_lblAsientosEjecutivo = new GridBagConstraints();
    	gbc_lblAsientosEjecutivo.anchor = GridBagConstraints.EAST;
    	gbc_lblAsientosEjecutivo.insets = new Insets(0, 0, 5, 5);
    	gbc_lblAsientosEjecutivo.gridx = 1;
    	gbc_lblAsientosEjecutivo.gridy = 3;
    	panelInfoVuelo.add(lblAsientosEjecutivo, gbc_lblAsientosEjecutivo);

    	textAsientosEjecutivo = new JTextField();
    	textAsientosEjecutivo.setEditable(false);
    	GridBagConstraints gbc_textAsientosEjecutivo = new GridBagConstraints();
    	gbc_textAsientosEjecutivo.gridwidth = 3;
    	gbc_textAsientosEjecutivo.insets = new Insets(0, 0, 5, 5);
    	gbc_textAsientosEjecutivo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textAsientosEjecutivo.gridx = 2;
    	gbc_textAsientosEjecutivo.gridy = 3;
    	panelInfoVuelo.add(textAsientosEjecutivo, gbc_textAsientosEjecutivo);
    	textAsientosEjecutivo.setColumns(10);

    	JLabel lblDuracion = new JLabel("Duración:");
    	GridBagConstraints gbc_lblDuracion = new GridBagConstraints();
    	gbc_lblDuracion.anchor = GridBagConstraints.EAST;
    	gbc_lblDuracion.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDuracion.gridx = 1;
    	gbc_lblDuracion.gridy = 4;
    	panelInfoVuelo.add(lblDuracion, gbc_lblDuracion);

    	textHoraVuelo = new JTextField();
    	textHoraVuelo.setEditable(false);
    	GridBagConstraints gbc_textHoraVuelo = new GridBagConstraints();
    	gbc_textHoraVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_textHoraVuelo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textHoraVuelo.gridx = 2;
    	gbc_textHoraVuelo.gridy = 4;
    	panelInfoVuelo.add(textHoraVuelo, gbc_textHoraVuelo);
    	textHoraVuelo.setColumns(10);

    	JLabel lblDuracionSeparador = new JLabel(":");
    	GridBagConstraints gbc_lblDuracionSeparador = new GridBagConstraints();
    	gbc_lblDuracionSeparador.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDuracionSeparador.gridx = 3;
    	gbc_lblDuracionSeparador.gridy = 4;
    	panelInfoVuelo.add(lblDuracionSeparador, gbc_lblDuracionSeparador);

    	textMinutoVuelo = new JTextField();
    	textMinutoVuelo.setEditable(false);
    	GridBagConstraints gbc_textMinutoVuelo = new GridBagConstraints();
    	gbc_textMinutoVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_textMinutoVuelo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textMinutoVuelo.gridx = 4;
    	gbc_textMinutoVuelo.gridy = 4;
    	panelInfoVuelo.add(textMinutoVuelo, gbc_textMinutoVuelo);
    	textMinutoVuelo.setColumns(10);

    	JLabel lblFecha = new JLabel("Fecha:");
    	GridBagConstraints gbc_lblFecha = new GridBagConstraints();
    	gbc_lblFecha.anchor = GridBagConstraints.EAST;
    	gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFecha.gridx = 1;
    	gbc_lblFecha.gridy = 5;
    	panelInfoVuelo.add(lblFecha, gbc_lblFecha);

    	textDiaVuelo = new JTextField();
    	textDiaVuelo.setEditable(false);
    	GridBagConstraints gbc_textDiaVuelo = new GridBagConstraints();
    	gbc_textDiaVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_textDiaVuelo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textDiaVuelo.gridx = 2;
    	gbc_textDiaVuelo.gridy = 5;
    	panelInfoVuelo.add(textDiaVuelo, gbc_textDiaVuelo);
    	textDiaVuelo.setColumns(10);

    	JLabel lblFechaSeparadorVuelo_1 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparadorVuelo_1 = new GridBagConstraints();
    	gbc_lblFechaSeparadorVuelo_1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparadorVuelo_1.gridx = 3;
    	gbc_lblFechaSeparadorVuelo_1.gridy = 5;
    	panelInfoVuelo.add(lblFechaSeparadorVuelo_1, gbc_lblFechaSeparadorVuelo_1);

    	textMesVuelo = new JTextField();
    	textMesVuelo.setEditable(false);
    	GridBagConstraints gbc_textMesVuelo = new GridBagConstraints();
    	gbc_textMesVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_textMesVuelo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textMesVuelo.gridx = 4;
    	gbc_textMesVuelo.gridy = 5;
    	panelInfoVuelo.add(textMesVuelo, gbc_textMesVuelo);
    	textMesVuelo.setColumns(10);

    	JLabel labelFechaSeparadorVuelo_2 = new JLabel("/");
    	GridBagConstraints gbc_labelFechaSeparadorVuelo_2 = new GridBagConstraints();
    	gbc_labelFechaSeparadorVuelo_2.insets = new Insets(0, 0, 5, 5);
    	gbc_labelFechaSeparadorVuelo_2.gridx = 5;
    	gbc_labelFechaSeparadorVuelo_2.gridy = 5;
    	panelInfoVuelo.add(labelFechaSeparadorVuelo_2, gbc_labelFechaSeparadorVuelo_2);

    	textAnioVuelo = new JTextField();
    	textAnioVuelo.setEditable(false);
    	GridBagConstraints gbc_textAnioVuelo = new GridBagConstraints();
    	gbc_textAnioVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_textAnioVuelo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textAnioVuelo.gridx = 6;
    	gbc_textAnioVuelo.gridy = 5;
    	panelInfoVuelo.add(textAnioVuelo, gbc_textAnioVuelo);
    	textAnioVuelo.setColumns(10);

    	JPanel panelPasajeros = new JPanel();
    	panelPasajeros.setBorder(new TitledBorder(null, "Pasajeros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelPasajeros = new GridBagConstraints();
    	gbc_panelPasajeros.fill = GridBagConstraints.BOTH;
    	gbc_panelPasajeros.gridx = 2;
    	gbc_panelPasajeros.gridy = 1;
    	getContentPane().add(panelPasajeros, gbc_panelPasajeros);
    	GridBagLayout gbl_panelPasajeros = new GridBagLayout();
    	gbl_panelPasajeros.columnWidths = new int[]{25, 65, 110, 85, 110, 25, 25, 49, 25, 0};
    	gbl_panelPasajeros.rowHeights = new int[]{30, 0, 15, 150, 30, 0};
    	gbl_panelPasajeros.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    	gbl_panelPasajeros.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
    	panelPasajeros.setLayout(gbl_panelPasajeros);

    	JLabel lblPasajeNombre = new JLabel("Nombres:");
    	GridBagConstraints gbc_lblPasajeNombre = new GridBagConstraints();
    	gbc_lblPasajeNombre.anchor = GridBagConstraints.EAST;
    	gbc_lblPasajeNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_lblPasajeNombre.gridx = 1;
    	gbc_lblPasajeNombre.gridy = 1;
    	panelPasajeros.add(lblPasajeNombre, gbc_lblPasajeNombre);

    	textPasajeNombre = new JTextField();
    	textPasajeNombre.getDocument().addDocumentListener(new DocumentListener() {
    		public void changedUpdate(DocumentEvent e) { notificar(); }
    		public void removeUpdate(DocumentEvent e) { notificar(); }
    		public void insertUpdate(DocumentEvent e) { notificar(); }

    		public void notificar() {
    			if (!textPasajeNombre.getText().isBlank()) {
    				pasajeNombreOK = true;
    			} else {
    				pasajeNombreOK = false;
    			}
    			actualizarBotonAgregar();
    		}
    	});
    	GridBagConstraints gbc_textPasajeNombre = new GridBagConstraints();
    	gbc_textPasajeNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_textPasajeNombre.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textPasajeNombre.gridx = 2;
    	gbc_textPasajeNombre.gridy = 1;
    	panelPasajeros.add(textPasajeNombre, gbc_textPasajeNombre);
    	textPasajeNombre.setColumns(10);

    	JLabel lblPasajeApellido = new JLabel("Apellidos:");
    	GridBagConstraints gbc_lblPasajeApellido = new GridBagConstraints();
    	gbc_lblPasajeApellido.anchor = GridBagConstraints.EAST;
    	gbc_lblPasajeApellido.insets = new Insets(0, 0, 5, 5);
    	gbc_lblPasajeApellido.gridx = 3;
    	gbc_lblPasajeApellido.gridy = 1;
    	panelPasajeros.add(lblPasajeApellido, gbc_lblPasajeApellido);

    	textPasajeApellido = new JTextField();
    	textPasajeApellido.getDocument().addDocumentListener(new DocumentListener() {
    		public void changedUpdate(DocumentEvent e) { notificar(); }
    		public void removeUpdate(DocumentEvent e) { notificar(); }
    		public void insertUpdate(DocumentEvent e) { notificar(); }

    		public void notificar() {
    			if (!textPasajeApellido.getText().isBlank()) {
    				pasajeApellidoOK = true;
    			} else {
    				pasajeApellidoOK = false;
    			}
    			actualizarBotonAgregar();
    		}
    	});
    	GridBagConstraints gbc_textPasajeApellido = new GridBagConstraints();
    	gbc_textPasajeApellido.insets = new Insets(0, 0, 5, 5);
    	gbc_textPasajeApellido.fill = GridBagConstraints.HORIZONTAL;
    	gbc_textPasajeApellido.gridx = 4;
    	gbc_textPasajeApellido.gridy = 1;
    	panelPasajeros.add(textPasajeApellido, gbc_textPasajeApellido);
    	textPasajeApellido.setColumns(10);

    	btnEliminar = new JButton("-");
    	btnEliminar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			eliminarPasaje();
    		}
    	});

    	btnAgregar = new JButton("+");
    	btnAgregar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			agregarPasaje();
    			textPasajeNombre.setText("");
    			textPasajeApellido.setText("");
    		}
    	});
    	GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
    	gbc_btnAgregar.fill = GridBagConstraints.HORIZONTAL;
    	gbc_btnAgregar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnAgregar.gridx = 6;
    	gbc_btnAgregar.gridy = 1;
    	panelPasajeros.add(btnAgregar, gbc_btnAgregar);
    	GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
    	gbc_btnEliminar.fill = GridBagConstraints.HORIZONTAL;
    	gbc_btnEliminar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnEliminar.gridx = 7;
    	gbc_btnEliminar.gridy = 1;
    	panelPasajeros.add(btnEliminar, gbc_btnEliminar);

    	JScrollPane scrollPanePasajes = new JScrollPane();
    	GridBagConstraints gbc_scrollPanePasajes = new GridBagConstraints();
    	gbc_scrollPanePasajes.gridwidth = 7;
    	gbc_scrollPanePasajes.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollPanePasajes.fill = GridBagConstraints.BOTH;
    	gbc_scrollPanePasajes.gridx = 1;
    	gbc_scrollPanePasajes.gridy = 3;
    	panelPasajeros.add(scrollPanePasajes, gbc_scrollPanePasajes);

    	DefaultListModel<DTPasaje> modelPasajes = new DefaultListModel<>();
    	listPasajes = new JList<>(modelPasajes);
    	listPasajes.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting()) {
    				JList<DTPasaje> source = (JList<DTPasaje>) e.getSource();
    				pasajeSeleccionado = source.getSelectedValue();
    				actualizarBotonEliminar();
    			}
    		}
    	});
    	listPasajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	scrollPanePasajes.setViewportView(listPasajes);
	}

	private void reservarVuelo() {
		String aerolinea = this.listAerolineas.getSelectedValue();
		DTRutaVuelo ruta = this.listRutas.getSelectedValue();
		DTVuelo vuelo = this.listVuelos.getSelectedValue();
		String cliente = (String) this.comboBoxClientes.getSelectedItem();
		String tipoAsiento = (String) this.comboBoxTipoAsiento.getSelectedItem();
		int cantPasajes = (int) this.spinnerCantidadPasajes.getValue();
		String cantEquipajeExtra = this.textEquipajeExtra.getText();
		String dia = this.textDiaReserva.getText();
		String mes = this.textMesReserva.getText();
		String anio = this.textAnioReserva.getText();
		ListModel<DTPasaje> modelPasajes = this.listPasajes.getModel();

		if (!validarCampos(aerolinea, ruta, vuelo, cantPasajes, cantEquipajeExtra, dia, mes, anio, modelPasajes)) {
			return;
		}

		float costoReserva;
		if (tipoAsiento.equals("Turista")) {
			costoReserva = ruta.getCostos().getCostoBaseTurista() * cantPasajes + ruta.getCostos().getCostoEquipajeExtra() * Integer.parseInt(cantEquipajeExtra);
		} else {
			costoReserva = ruta.getCostos().getCostoBaseEjecutivo() * cantPasajes + ruta.getCostos().getCostoEquipajeExtra() * Integer.parseInt(cantEquipajeExtra);
		}

		int opcion = JOptionPane.showConfirmDialog(this, "El costo de la reserva es $" + costoReserva + ".\n\n¿Desea confirmar la reserva?", "Reserva de vuelo", JOptionPane.OK_CANCEL_OPTION);

		if (opcion == JOptionPane.CANCEL_OPTION) {
			return;
		}

		LocalDate fecha = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));

		Set<DTPasaje> pasajes = new HashSet<>();
		for (int i = 0; i < modelPasajes.getSize(); i++) {
			pasajes.add(modelPasajes.getElementAt(i));
		}

		DTReserva reserva = new DTReserva(fecha, TipoAsiento.desdeString(tipoAsiento), cantPasajes, Integer.parseInt(cantEquipajeExtra), vuelo.getNombre(), pasajes);

		try {
			this.iUsuario.ingresarDatosReserva(reserva, cliente, ruta.getNombre());
		} catch (ClienteTieneReservaEnVueloException e) {
			JOptionPane.showMessageDialog(this, "El cliente " + cliente + " ya tiene una reserva en este vuelo", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
		} catch (UsuarioNoExisteException e) {
			e.printStackTrace();
		} catch (ReservaCantidadAsientosInvalidos e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(this, "La reserva se ha efectuado exitosamente", "Reserva de vuelo", JOptionPane.INFORMATION_MESSAGE);
		this.limpiarEntrada();
	}

	private boolean validarCampos(String aerolinea, DTRutaVuelo ruta, DTVuelo vuelo, int cantPasajes, String cantEquipajeExtra, String dia, String mes, String anio, ListModel<DTPasaje> pasajes) {
		if (aerolinea == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una aerolínea", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (ruta == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una ruta de vuelo", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (vuelo == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un vuelo", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (cantPasajes == 0) {
			JOptionPane.showMessageDialog(this, "Debe comprar al menos un pasaje", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (cantEquipajeExtra.isBlank()) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar la cantidad de equipaje extra", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			if (Integer.parseInt(cantEquipajeExtra) < 0) {
				JOptionPane.showMessageDialog(this, "La cantidad de equipaje extra debe ser no negativa", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "La cantidad de equipaje extra debe ser un número entero", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
		} catch (NumberFormatException | DateTimeException e) {
			JOptionPane.showMessageDialog(this, "Ingrese una fecha válida", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (cantPasajes - 1 != pasajes.getSize()) {
			JOptionPane.showMessageDialog(this, "La cantidad de pasajes y la cantidad de pasajeros no coincide", "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void cargarAerolineas() {
		this.ignorarSeleccion = true;

		Set<String> aerolineas = iUsuario.obtenerAerolineas();
		this.listAerolineas.setListData(aerolineas.toArray(String[]::new));
		this.listAerolineas.repaint();

		this.ignorarSeleccion = false;
	}

	private void cargarRutas(String aerolinea) {
		this.ignorarSeleccion = true;

		// cargarRutas() se ejecuta cuando se cambia la selección de aerolínea
		// Se limpia la lista y la información de vuelos que hayan podido quedar
    	this.listVuelos.setListData(new DTVuelo[0]);
    	this.limpiarInfoVuelo();

		try {
			Set<DTRutaVuelo> rutasVuelo = this.iRutaVuelo.obtenerRutasVuelo(aerolinea);
			this.listRutas.setListData(rutasVuelo.toArray(DTRutaVuelo[]::new));
		} catch (UsuarioNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		this.ignorarSeleccion = false;
	}

	private void cargarVuelos(DTRutaVuelo rutaVuelo) {
		this.ignorarSeleccion = true;
		try {
			Set<DTVuelo> vuelos = this.iRutaVuelo.obtenerVuelos(rutaVuelo);
			this.listVuelos.setListData(vuelos.toArray(DTVuelo[]::new));
		} catch (RutaNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Reserva de vuelo", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		this.ignorarSeleccion = false;
	}

	private void cargarInfoVuelo(DTVuelo vuelo) {
		this.textNombreVuelo.setText(vuelo.getNombre());
		this.textAsientosTurista.setText(String.valueOf(vuelo.getAsientosMaxTurista()));
		this.textAsientosEjecutivo.setText(String.valueOf(vuelo.getAsientosMaxEjecutivo()));

		LocalTime duracion = vuelo.getDuracion();
		this.textHoraVuelo.setText(String.format("%02d", duracion.getHour()));
		this.textMinutoVuelo.setText(String.format("%02d", duracion.getMinute()));

		LocalDate fecha = vuelo.getFecha();
		this.textDiaVuelo.setText(String.format("%02d", fecha.getDayOfMonth()));
		this.textMesVuelo.setText(String.format("%02d", fecha.getMonthValue()));
		this.textAnioVuelo.setText(String.format("%02d", fecha.getYear()));
	}

	private void limpiarInfoVuelo() {
		this.textNombreVuelo.setText("");
		this.textAsientosTurista.setText("");
		this.textAsientosEjecutivo.setText("");
		this.textHoraVuelo.setText("");
		this.textMinutoVuelo.setText("");
		this.textDiaVuelo.setText("");
		this.textMesVuelo.setText("");
		this.textAnioVuelo.setText("");
	}

	private void limpiarEntrada() {
		this.ignorarSeleccion = true;

		// Panel rutas de vuelo
		this.listRutas.setListData(new DTRutaVuelo[0]);

		// Panel vuelos
		this.listVuelos.setListData(new DTVuelo[0]);

		// Panel info de vuelo
		this.limpiarInfoVuelo();

		// Panel reservas
		this.spinnerCantidadPasajes.setValue(0);
		this.textEquipajeExtra.setText("");
		this.textDiaReserva.setText("");
		this.textMesReserva.setText("");
		this.textAnioReserva.setText("");

		// Panel pasajes
		this.textPasajeNombre.setText("");
		this.textPasajeApellido.setText("");
		DefaultListModel<DTPasaje> listModel = (DefaultListModel<DTPasaje>) this.listPasajes.getModel();
		listModel.removeAllElements();

		this.ignorarSeleccion = false;
	}

	private void cargarClientes() {
		Set<String> clientes = this.iUsuario.listarClientes();
		DefaultComboBoxModel<String> modelClientes = new DefaultComboBoxModel<String>(clientes.toArray(String[]::new));
		this.comboBoxClientes.setModel(modelClientes);
	}

	public void inicializar() {
		this.limpiarEntrada();
		this.cargarAerolineas();
		this.cargarClientes();

		this.btnAgregar.setEnabled(false);
		this.btnEliminar.setEnabled(false);
	}

	private void actualizarBotonAgregar() {
		this.btnAgregar.setEnabled(this.pasajeNombreOK && this.pasajeApellidoOK);
	}

	private void actualizarBotonEliminar() {
		this.btnEliminar.setEnabled(this.pasajeSeleccionado != null);
	}

	private void agregarPasaje() {
		DefaultListModel<DTPasaje> modelPasajes = (DefaultListModel<DTPasaje>) this.listPasajes.getModel();
		modelPasajes.add(0, new DTPasaje(this.textPasajeNombre.getText().replaceAll("\\s+", " ").trim(), this.textPasajeApellido.getText().replaceAll("\\s+", " ").trim()));
	}

	private void eliminarPasaje() {
		DefaultListModel<DTPasaje> modelPasajes = (DefaultListModel<DTPasaje>) this.listPasajes.getModel();
		modelPasajes.removeElement(this.pasajeSeleccionado);
		this.pasajeSeleccionado = null;
	}

	private void actualizarCantidadPasajes() {
		if (this.textAsientosTurista != null && !this.textAsientosTurista.getText().isEmpty()) {
			String tipoCliente = (String) this.comboBoxTipoAsiento.getSelectedItem();
			int max = tipoCliente == "Turista" ? Integer.valueOf(this.textAsientosTurista.getText()) : Integer.valueOf(this.textAsientosEjecutivo.getText());

			this.spinnerCantidadPasajes.setModel(new SpinnerNumberModel(0, 0, max, 1));
		}
	}

}
