package presentacion;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import datatypes.DTRutaVuelo;
import datatypes.DTVuelo;
import excepciones.RutaNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

public class ConsultaDeVuelo extends JInternalFrame{

	private static final long serialVersionUID = 6375287996767616555L;
	private JTextField txtNombre;
	private JTextField txtAsientosEjecutivos;
	private JTextField txtAsientosTurista;
	private JTextField txtFecha1;
	private JTextField txtFecha2;
	private JTextField txtFecha3;
	private JTextField txtHora1;
	private JTextField txtHora2;

	private IUsuario iUsuario;
	private IRutaVuelo iRutaVuelo;

	private JList<String> listaAerolineas;
	private JList<DTRutaVuelo> listaRutasVuelo;
	private JList<String> listaReservas;
	private JList<DTVuelo> listaVuelos;

	private boolean limpiando = false;

	public ConsultaDeVuelo(IUsuario interfazUsuario, IRutaVuelo iRutaVuelo, Principal principal) {
		this.iUsuario = interfazUsuario;
    	this.iRutaVuelo = iRutaVuelo;
    	setTitle("Consultas de Vuelo");
    	setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setClosable(true);
    	setBounds(0, 0, 1093, 850);

    	Set<String> aerolineas = iUsuario.obtenerAerolineas();
    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{10, 430, 10, 430, 10};
    	gridBagLayout.rowHeights = new int[]{303, 19, 429, 15};
    	gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0};
    	gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
    	getContentPane().setLayout(gridBagLayout);


    	JPanel panelListaUsuarios = new JPanel();
    	panelListaUsuarios.setBorder(new TitledBorder(null, "Aerolineas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelListaUsuarios = new GridBagConstraints();
    	gbc_panelListaUsuarios.fill = GridBagConstraints.BOTH;
    	gbc_panelListaUsuarios.insets = new Insets(5, 0, 5, 5);
    	gbc_panelListaUsuarios.gridx = 1;
    	gbc_panelListaUsuarios.gridy = 0;
    	getContentPane().add(panelListaUsuarios, gbc_panelListaUsuarios);
    	GridBagLayout gbl_panelListaUsuarios = new GridBagLayout();
    	gbl_panelListaUsuarios.columnWidths = new int[]{351, 0};
    	gbl_panelListaUsuarios.rowHeights = new int[]{85, 0};
    	gbl_panelListaUsuarios.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    	gbl_panelListaUsuarios.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    	panelListaUsuarios.setLayout(gbl_panelListaUsuarios);

    	JScrollPane scrollPane = new JScrollPane();
    	GridBagConstraints gbc_scrollPane = new GridBagConstraints();
    	gbc_scrollPane.insets = new Insets(5, 0, 0, 0);
    	gbc_scrollPane.fill = GridBagConstraints.BOTH;
    	gbc_scrollPane.gridx = 0;
    	gbc_scrollPane.gridy = 0;
    	panelListaUsuarios.add(scrollPane, gbc_scrollPane);
    	listaAerolineas = new JList<String>(aerolineas.toArray(String[]::new));
    	scrollPane.setViewportView(listaAerolineas);
    	listaAerolineas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    	listaAerolineas.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting() && !limpiando) {
    				JList<String> source = (JList<String>) e.getSource();
    				String nick = source.getSelectedValue().toString();
    				try {
						listaRutasVuelo.setListData(iRutaVuelo.obtenerRutasVuelo(nick).toArray(DTRutaVuelo[]::new));
					} catch (UsuarioNoExisteException e1) {
    					JOptionPane.showMessageDialog(getFocusOwner(), e1.getMessage(), "Consulta de Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
					}
    			}
    		}
    	});

    	JPanel panelRutas = new JPanel();
    	panelRutas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Rutas de Vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    	GridBagConstraints gbc_panelRutas = new GridBagConstraints();
    	gbc_panelRutas.insets = new Insets(5, 0, 5, 5);
    	gbc_panelRutas.fill = GridBagConstraints.BOTH;
    	gbc_panelRutas.gridx = 3;
    	gbc_panelRutas.gridy = 0;
    	getContentPane().add(panelRutas, gbc_panelRutas);
    	GridBagLayout gbl_panelRutas = new GridBagLayout();
    	gbl_panelRutas.columnWidths = new int[]{0, 0};
    	gbl_panelRutas.rowHeights = new int[]{26, 0};
    	gbl_panelRutas.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    	gbl_panelRutas.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    	panelRutas.setLayout(gbl_panelRutas);

    	JScrollPane scrollRutaVuelo = new JScrollPane();
    	GridBagConstraints gbc_scrollRutaVuelo = new GridBagConstraints();
    	gbc_scrollRutaVuelo.fill = GridBagConstraints.BOTH;
    	gbc_scrollRutaVuelo.gridx = 0;
    	gbc_scrollRutaVuelo.gridy = 0;
    	panelRutas.add(scrollRutaVuelo, gbc_scrollRutaVuelo);

    	listaRutasVuelo = new JList<DTRutaVuelo>();
    	scrollRutaVuelo.setViewportView(listaRutasVuelo);

    	listaRutasVuelo.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting() && !limpiando) {
    				JList<DTRutaVuelo> source = (JList<DTRutaVuelo>) e.getSource();
    				cargarVuelos(source.getSelectedValue());
    			}
    		}
    	});

    	JPanel panelVuelo = new JPanel();
    	panelVuelo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    	GridBagConstraints gbc_panelVuelo = new GridBagConstraints();
    	gbc_panelVuelo.insets = new Insets(0, 0, 5, 5);
    	gbc_panelVuelo.fill = GridBagConstraints.BOTH;
    	gbc_panelVuelo.gridx = 1;
    	gbc_panelVuelo.gridy = 2;
    	getContentPane().add(panelVuelo, gbc_panelVuelo);
    	GridBagLayout gbl_panelVuelo = new GridBagLayout();
    	gbl_panelVuelo.columnWidths = new int[]{0, 0, 30, 0, 30, 0, 30, 0, 30, 0, 11, 0};
    	gbl_panelVuelo.rowHeights = new int[]{26, 26, 0, 0, 0, 0, 0, 0, 28, 27, 123, 0};
    	gbl_panelVuelo.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
    	gbl_panelVuelo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
    	panelVuelo.setLayout(gbl_panelVuelo);

    	JLabel lblNombre = new JLabel("Nombre:");
    	GridBagConstraints gbc_lblNombre = new GridBagConstraints();
    	gbc_lblNombre.anchor = GridBagConstraints.WEST;
    	gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombre.gridx = 1;
    	gbc_lblNombre.gridy = 0;
    	panelVuelo.add(lblNombre, gbc_lblNombre);

    	txtNombre = new JTextField();
    	txtNombre.setEditable(false);
    	txtNombre.setColumns(10);
    	GridBagConstraints gbc_txtNombre = new GridBagConstraints();
    	gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtNombre.gridwidth = 8;
    	gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_txtNombre.gridx = 2;
    	gbc_txtNombre.gridy = 0;
    	panelVuelo.add(txtNombre, gbc_txtNombre);

    	JLabel lblAsientosTurista = new JLabel("Asientos maximos turista:");
    	GridBagConstraints gbc_lblAsientosTurista = new GridBagConstraints();
    	gbc_lblAsientosTurista.anchor = GridBagConstraints.WEST;
    	gbc_lblAsientosTurista.insets = new Insets(0, 0, 5, 5);
    	gbc_lblAsientosTurista.gridx = 1;
    	gbc_lblAsientosTurista.gridy = 1;
    	panelVuelo.add(lblAsientosTurista, gbc_lblAsientosTurista);

    	txtAsientosTurista = new JTextField();
    	txtAsientosTurista.setEditable(false);
    	txtAsientosTurista.setColumns(10);
    	GridBagConstraints gbc_txtAsientosTurista = new GridBagConstraints();
    	gbc_txtAsientosTurista.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtAsientosTurista.gridwidth = 3;
    	gbc_txtAsientosTurista.insets = new Insets(0, 0, 5, 5);
    	gbc_txtAsientosTurista.gridx = 2;
    	gbc_txtAsientosTurista.gridy = 1;
    	panelVuelo.add(txtAsientosTurista, gbc_txtAsientosTurista);

    	    	JLabel lblAsientosEjecutivos = new JLabel("Asientos maximos ejecutivo:");
    	    	GridBagConstraints gbc_lblAsientosEjecutivos = new GridBagConstraints();
    	    	gbc_lblAsientosEjecutivos.anchor = GridBagConstraints.WEST;
    	    	gbc_lblAsientosEjecutivos.insets = new Insets(0, 0, 5, 5);
    	    	gbc_lblAsientosEjecutivos.gridx = 1;
    	    	gbc_lblAsientosEjecutivos.gridy = 2;
    	    	panelVuelo.add(lblAsientosEjecutivos, gbc_lblAsientosEjecutivos);

    	    	txtAsientosEjecutivos = new JTextField();
    	    	txtAsientosEjecutivos.setEditable(false);
    	    	txtAsientosEjecutivos.setColumns(10);
    	    	GridBagConstraints gbc_txtAsientosEjecutivos = new GridBagConstraints();
    	    	gbc_txtAsientosEjecutivos.fill = GridBagConstraints.HORIZONTAL;
    	    	gbc_txtAsientosEjecutivos.gridwidth = 3;
    	    	gbc_txtAsientosEjecutivos.insets = new Insets(0, 0, 5, 5);
    	    	gbc_txtAsientosEjecutivos.gridx = 2;
    	    	gbc_txtAsientosEjecutivos.gridy = 2;
    	    	panelVuelo.add(txtAsientosEjecutivos, gbc_txtAsientosEjecutivos);

    	JLabel lblHora = new JLabel("Duracion:");
    	GridBagConstraints gbc_lblHora = new GridBagConstraints();
    	gbc_lblHora.anchor = GridBagConstraints.WEST;
    	gbc_lblHora.insets = new Insets(0, 0, 5, 5);
    	gbc_lblHora.gridx = 1;
    	gbc_lblHora.gridy = 3;
    	panelVuelo.add(lblHora, gbc_lblHora);

    	txtHora1 = new JTextField();
    	txtHora1.setEditable(false);
    	GridBagConstraints gbc_txtHora1 = new GridBagConstraints();
    	gbc_txtHora1.gridwidth = 3;
    	gbc_txtHora1.insets = new Insets(0, 0, 5, 5);
    	gbc_txtHora1.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtHora1.gridx = 2;
    	gbc_txtHora1.gridy = 3;
    	panelVuelo.add(txtHora1, gbc_txtHora1);
    	txtHora1.setColumns(10);

    	JLabel lblFechaSeparador_1 = new JLabel(":");
    	GridBagConstraints gbc_lblFechaSeparador_1 = new GridBagConstraints();
    	gbc_lblFechaSeparador_1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparador_1.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaSeparador_1.gridx = 5;
    	gbc_lblFechaSeparador_1.gridy = 3;
    	panelVuelo.add(lblFechaSeparador_1, gbc_lblFechaSeparador_1);

    	txtHora2 = new JTextField();
    	txtHora2.setEditable(false);
    	GridBagConstraints gbc_txtHora2 = new GridBagConstraints();
    	gbc_txtHora2.gridwidth = 4;
    	gbc_txtHora2.insets = new Insets(0, 0, 5, 5);
    	gbc_txtHora2.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtHora2.gridx = 6;
    	gbc_txtHora2.gridy = 3;
    	panelVuelo.add(txtHora2, gbc_txtHora2);
    	txtHora2.setColumns(10);

    	JLabel lblFechaAlta = new JLabel("Fecha:");
    	GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
    	gbc_lblFechaAlta.anchor = GridBagConstraints.WEST;
    	gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaAlta.gridx = 1;
    	gbc_lblFechaAlta.gridy = 7;
    	panelVuelo.add(lblFechaAlta, gbc_lblFechaAlta);

    	txtFecha1 = new JTextField();
    	txtFecha1.setEditable(false);
    	txtFecha1.setColumns(10);
    	GridBagConstraints gbc_txtFecha1 = new GridBagConstraints();
    	gbc_txtFecha1.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtFecha1.insets = new Insets(0, 0, 5, 5);
    	gbc_txtFecha1.gridx = 2;
    	gbc_txtFecha1.gridy = 7;
    	panelVuelo.add(txtFecha1, gbc_txtFecha1);

    	JLabel lblFechaSeparador = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparador = new GridBagConstraints();
    	gbc_lblFechaSeparador.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaSeparador.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparador.gridx = 3;
    	gbc_lblFechaSeparador.gridy = 7;
    	panelVuelo.add(lblFechaSeparador, gbc_lblFechaSeparador);

    	txtFecha2 = new JTextField();
    	txtFecha2.setEditable(false);
    	txtFecha2.setColumns(10);
    	GridBagConstraints gbc_txtFecha2 = new GridBagConstraints();
    	gbc_txtFecha2.gridwidth = 3;
    	gbc_txtFecha2.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtFecha2.insets = new Insets(0, 0, 5, 5);
    	gbc_txtFecha2.gridx = 4;
    	gbc_txtFecha2.gridy = 7;
    	panelVuelo.add(txtFecha2, gbc_txtFecha2);

    	JLabel lblFechaSeparador2 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparador2 = new GridBagConstraints();
    	gbc_lblFechaSeparador2.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaSeparador2.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparador2.gridx = 7;
    	gbc_lblFechaSeparador2.gridy = 7;
    	panelVuelo.add(lblFechaSeparador2, gbc_lblFechaSeparador2);

    	txtFecha3 = new JTextField();
    	txtFecha3.setEditable(false);
    	txtFecha3.setColumns(10);
    	GridBagConstraints gbc_txtFecha3 = new GridBagConstraints();
    	gbc_txtFecha3.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtFecha3.gridwidth = 2;
    	gbc_txtFecha3.insets = new Insets(0, 0, 5, 5);
    	gbc_txtFecha3.gridx = 8;
    	gbc_txtFecha3.gridy = 7;
    	panelVuelo.add(txtFecha3, gbc_txtFecha3);



    	    	JLabel lblReservas = new JLabel("Reservas:");
    	    	GridBagConstraints gbc_lblReservas = new GridBagConstraints();
    	    	gbc_lblReservas.anchor = GridBagConstraints.NORTHWEST;
    	    	gbc_lblReservas.insets = new Insets(0, 0, 5, 5);
    	    	gbc_lblReservas.gridx = 1;
    	    	gbc_lblReservas.gridy = 8;
    	    	panelVuelo.add(lblReservas, gbc_lblReservas);

    	    	JScrollPane scrollReservas = new JScrollPane();
    	    	GridBagConstraints gbc_scrollReservas = new GridBagConstraints();
    	    	gbc_scrollReservas.gridheight = 2;
    	    	gbc_scrollReservas.gridwidth = 8;
    	    	gbc_scrollReservas.insets = new Insets(0, 0, 5, 5);
    	    	gbc_scrollReservas.fill = GridBagConstraints.BOTH;
    	    	gbc_scrollReservas.gridx = 2;
    	    	gbc_scrollReservas.gridy = 8;
    	    	panelVuelo.add(scrollReservas, gbc_scrollReservas);

    	    	    	listaReservas = new JList<String>();
    	    	    	scrollReservas.setViewportView(listaReservas);

    	JPanel panelVuelos = new JPanel();
    	panelVuelos.setBorder(new TitledBorder(null, "Vuelos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelVuelos = new GridBagConstraints();
    	gbc_panelVuelos.insets = new Insets(0, 0, 5, 5);
    	gbc_panelVuelos.fill = GridBagConstraints.BOTH;
    	gbc_panelVuelos.gridx = 3;
    	gbc_panelVuelos.gridy = 2;
    	getContentPane().add(panelVuelos, gbc_panelVuelos);
    	GridBagLayout gbl_panelVuelos = new GridBagLayout();
    	gbl_panelVuelos.columnWidths = new int[]{0, 0};
    	gbl_panelVuelos.rowHeights = new int[]{0, 0};
    	gbl_panelVuelos.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    	gbl_panelVuelos.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    	panelVuelos.setLayout(gbl_panelVuelos);

    	JScrollPane scrollVuelos = new JScrollPane();
    	GridBagConstraints gbc_scrollVuelos = new GridBagConstraints();
    	gbc_scrollVuelos.fill = GridBagConstraints.BOTH;
    	gbc_scrollVuelos.gridx = 0;
    	gbc_scrollVuelos.gridy = 0;
    	panelVuelos.add(scrollVuelos, gbc_scrollVuelos);

    	listaVuelos = new JList<DTVuelo>();
    	scrollVuelos.setViewportView(listaVuelos);

    	listaVuelos.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting() && !limpiando) {
    				JList<DTVuelo> source = (JList<DTVuelo>) e.getSource();
    				actualizarDatosVuelo(source.getSelectedValue());
    			}
    		}
    	});

	}

	protected void actualizarDatosVuelo(DTVuelo dtVuelo) {
		this.txtNombre.setText(dtVuelo.getNombre());
		this.txtAsientosEjecutivos.setText(Integer.toString(dtVuelo.getAsientosMaxEjecutivo()));
		this.txtAsientosTurista.setText(Integer.toString(dtVuelo.getAsientosMaxTurista()));
		this.txtFecha1.setText(Integer.toString(dtVuelo.getFecha().getDayOfMonth()));
		this.txtFecha2.setText(Integer.toString(dtVuelo.getFecha().getMonthValue()));
		this.txtFecha3.setText(Integer.toString(dtVuelo.getFecha().getYear()));
		this.txtHora1.setText(Integer.toString(dtVuelo.getDuracion().getHour()));
		this.txtHora2.setText(Integer.toString(dtVuelo.getDuracion().getMinute()));
		this.listaReservas.setListData(this.iRutaVuelo.obtenerReservas(this.listaRutasVuelo.getSelectedValue(), dtVuelo).stream().map(res -> res.toString()).toArray(String[]::new));
	}

	public void cargarVuelos(DTRutaVuelo dtRuta) {
		try {
			this.listaVuelos.setListData(this.iRutaVuelo.obtenerVuelos(dtRuta).toArray(DTVuelo[]::new));
		} catch (RutaNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Consulta de Vuelo", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void clear() {
		limpiando = true;
		this.listaAerolineas.clearSelection();
		this.listaReservas.clearSelection();
		this.listaRutasVuelo.clearSelection();
		this.listaVuelos.clearSelection();
		this.listaReservas.setListData(new String[0]);
		this.listaRutasVuelo.setListData(new DTRutaVuelo[0]);
		this.listaVuelos.setListData(new DTVuelo[0]);
		limpiando = false;

		this.txtNombre.setText("");
		this.txtAsientosEjecutivos.setText("");
		this.txtAsientosTurista.setText("");
		this.txtFecha1.setText("");
		this.txtFecha2.setText("");
		this.txtFecha3.setText("");
		this.txtHora1.setText("");
		this.txtHora2.setText("");

	}
	public void inicializar() {
		limpiando = true;
		listaAerolineas.clearSelection();
		limpiando = false;
		listaAerolineas.setListData(iUsuario.obtenerAerolineas().toArray(String[]::new));
	}

	public void inicializar(DTVuelo dtVuelo) {
		limpiando = true;
		listaAerolineas.clearSelection();
		listaAerolineas.setListData(iUsuario.obtenerAerolineas().toArray(String[]::new));
		try {
			DTRutaVuelo dtRuta = iRutaVuelo.obtenerRuta(dtVuelo);
			listaAerolineas.setSelectedValue(iUsuario.obtenerAerolinea(dtRuta.getNombre()), true);
			Set<DTRutaVuelo> rutaVuelos = iRutaVuelo.obtenerRutasVuelo(iUsuario.obtenerAerolinea(dtRuta.getNombre()).getNickname());
			listaRutasVuelo.setListData(rutaVuelos.toArray(DTRutaVuelo[]::new));
			listaRutasVuelo.setSelectedValue(rutaVuelos.stream().filter(rv -> rv.getNombre().equals(dtRuta.getNombre())).toList().get(0), true);
			cargarVuelos(dtRuta);
			limpiando = false;
			for (int i=0; i< listaVuelos.getModel().getSize(); i++) {
				if (listaVuelos.getModel().getElementAt(i).getNombre().equals(dtVuelo.getNombre())) {
					dtVuelo = listaVuelos.getModel().getElementAt(i);
					break;
				}
			}
			listaVuelos.setSelectedValue(dtVuelo, true);
		} catch (UsuarioNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Consulta de Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
			limpiando = false;
		} catch (RutaNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Consulta de Ruta de Vuelo", JOptionPane.ERROR_MESSAGE);
			limpiando = false;
		}

	}

}
