package presentacion;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import datatypes.DTRutaVuelo;
import datatypes.DTVuelo;
import excepciones.RutaNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.enums.EstadoRuta;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

public class ConsultaRutaVuelo extends JInternalFrame {

	private static final long serialVersionUID = 2944228762810592019L;

	private IRutaVuelo iRutaVuelo;
	private IUsuario iUsuario;
	private boolean limpiando = false;

	private JList<String> listaAerolineas;
	private JList<String> listaCategorias;
	private JList<DTRutaVuelo> listaRutasVuelo;
	private JList<DTVuelo> listaVuelos;
	private JTextArea txtAreaDescripcion;
	private JTextArea txtDescripcionCorta;
	private JTextField txtCiudadDestino;
	private JTextField txtCiudadOrigen;
	private JTextField txtCostoEjecutivo;
	private JTextField txtCostoEquipajeExtra;
	private JTextField txtCostoTurista;
	private JTextField txtFecha1;
	private JTextField txtFecha2;
	private JTextField txtFecha3;
	private JTextField txtHora1;
	private JTextField txtHora2;
	private JTextField txtNombre;
	private JTextField txtPaisDestino;
	private JTextField txtPaisOrigen;
	private JTextField txtEstadoRuta;

	public ConsultaRutaVuelo(IUsuario interfazUsuario, IRutaVuelo iRutaVuelo, Principal principal) {
		this.iUsuario = interfazUsuario;
    	this.iRutaVuelo = iRutaVuelo;
    	setTitle("Consulta de ruta de vuelo");
    	setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setClosable(true);
    	setBounds(0, 0, 1055, 800);

    	Set<String> aerolineas = iUsuario.obtenerAerolineas();
    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{200, 525};
    	gridBagLayout.rowHeights = new int[]{300, 0};
    	gridBagLayout.columnWeights = new double[]{1.0, 1.0};
    	gridBagLayout.rowWeights = new double[]{1.0, 1.0};
    	getContentPane().setLayout(gridBagLayout);


    	JPanel panelListaUsuarios = new JPanel();
    	panelListaUsuarios.setBorder(new TitledBorder(null, "Aerol\u00EDneas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
    	GridBagConstraints gbc_panelListaUsuarios = new GridBagConstraints();
    	gbc_panelListaUsuarios.fill = GridBagConstraints.BOTH;
    	gbc_panelListaUsuarios.insets = new Insets(5, 0, 5, 5);
    	gbc_panelListaUsuarios.gridx = 0;
    	gbc_panelListaUsuarios.gridy = 0;
    	getContentPane().add(panelListaUsuarios, gbc_panelListaUsuarios);
    	GridBagLayout gbl_panelListaUsuarios = new GridBagLayout();
    	gbl_panelListaUsuarios.columnWidths = new int[]{0, 0};
    	gbl_panelListaUsuarios.rowHeights = new int[]{0, 0};
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
    					limpiando = true;
						listaRutasVuelo.setListData(iRutaVuelo.obtenerRutasVuelo(nick).toArray(DTRutaVuelo[]::new));
						limpiando = false;
					}
    				catch (UsuarioNoExisteException e1) {
    					JOptionPane.showMessageDialog(getFocusOwner(), e1.getMessage(), "Consulta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
					}
    			}
    		}
    	});

    	JPanel panelRutas = new JPanel();
    	panelRutas.setBorder(new TitledBorder(null, "Rutas de vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    	GridBagConstraints gbc_panelRutas = new GridBagConstraints();
    	gbc_panelRutas.insets = new Insets(5, 0, 5, 0);
    	gbc_panelRutas.fill = GridBagConstraints.BOTH;
    	gbc_panelRutas.gridx = 1;
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
    				actualizarDatosRuta(source.getSelectedValue());
    			}
    		}
    	});

    	JPanel panelRutaVuelo = new JPanel();
    	panelRutaVuelo.setBorder(new TitledBorder(null, "Ruta de vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
    	GridBagConstraints gbc_panelRutaVuelo = new GridBagConstraints();
    	gbc_panelRutaVuelo.insets = new Insets(0, 0, 0, 5);
    	gbc_panelRutaVuelo.fill = GridBagConstraints.BOTH;
    	gbc_panelRutaVuelo.gridx = 0;
    	gbc_panelRutaVuelo.gridy = 1;
    	getContentPane().add(panelRutaVuelo, gbc_panelRutaVuelo);
    	GridBagLayout gbl_panelRutaVuelo = new GridBagLayout();
    	gbl_panelRutaVuelo.columnWidths = new int[]{25, 160, 85, 0, 40, 0, 40, 0, 85, 25, 0};
    	gbl_panelRutaVuelo.rowHeights = new int[]{30, 26, 26, 0, 0, 0, 0, 0, 0, 0, 80, 50, 80, 30, 0};
    	gbl_panelRutaVuelo.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
    	gbl_panelRutaVuelo.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
    	panelRutaVuelo.setLayout(gbl_panelRutaVuelo);

    	JLabel lblNombre = new JLabel("Nombre:");
    	GridBagConstraints gbc_lblNombre = new GridBagConstraints();
    	gbc_lblNombre.anchor = GridBagConstraints.WEST;
    	gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombre.gridx = 1;
    	gbc_lblNombre.gridy = 1;
    	panelRutaVuelo.add(lblNombre, gbc_lblNombre);

    	txtNombre = new JTextField();
    	txtNombre.setEditable(false);
    	txtNombre.setColumns(10);
    	GridBagConstraints gbc_txtNombre = new GridBagConstraints();
    	gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtNombre.gridwidth = 7;
    	gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_txtNombre.gridx = 2;
    	gbc_txtNombre.gridy = 1;
    	panelRutaVuelo.add(txtNombre, gbc_txtNombre);

    	JLabel lblCiudadOrigen = new JLabel("Ciudad de origen:");
    	GridBagConstraints gbc_lblCiudadOrigen = new GridBagConstraints();
    	gbc_lblCiudadOrigen.anchor = GridBagConstraints.WEST;
    	gbc_lblCiudadOrigen.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCiudadOrigen.gridx = 1;
    	gbc_lblCiudadOrigen.gridy = 2;
    	panelRutaVuelo.add(lblCiudadOrigen, gbc_lblCiudadOrigen);

    	txtCiudadOrigen = new JTextField();
    	txtCiudadOrigen.setEditable(false);
    	txtCiudadOrigen.setColumns(10);
    	GridBagConstraints gbc_txtCiudadOrigen = new GridBagConstraints();
    	gbc_txtCiudadOrigen.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtCiudadOrigen.gridwidth = 3;
    	gbc_txtCiudadOrigen.insets = new Insets(0, 0, 5, 5);
    	gbc_txtCiudadOrigen.gridx = 2;
    	gbc_txtCiudadOrigen.gridy = 2;
    	panelRutaVuelo.add(txtCiudadOrigen, gbc_txtCiudadOrigen);

    	txtPaisOrigen = new JTextField();
    	txtPaisOrigen.setEditable(false);
    	txtPaisOrigen.setColumns(10);
    	GridBagConstraints gbc_txtPaisOrigen = new GridBagConstraints();
    	gbc_txtPaisOrigen.gridwidth = 3;
    	gbc_txtPaisOrigen.insets = new Insets(0, 0, 5, 5);
    	gbc_txtPaisOrigen.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtPaisOrigen.gridx = 6;
    	gbc_txtPaisOrigen.gridy = 2;
    	panelRutaVuelo.add(txtPaisOrigen, gbc_txtPaisOrigen);

    	JLabel lblCiudadDestino = new JLabel("Ciudad de destino:");
    	GridBagConstraints gbc_lblCiudadDestino = new GridBagConstraints();
    	gbc_lblCiudadDestino.anchor = GridBagConstraints.WEST;
    	gbc_lblCiudadDestino.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCiudadDestino.gridx = 1;
    	gbc_lblCiudadDestino.gridy = 3;
    	panelRutaVuelo.add(lblCiudadDestino, gbc_lblCiudadDestino);

    	txtCiudadDestino = new JTextField();
    	txtCiudadDestino.setEditable(false);
    	txtCiudadDestino.setColumns(10);
    	GridBagConstraints gbc_txtCiudadDestino = new GridBagConstraints();
    	gbc_txtCiudadDestino.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtCiudadDestino.gridwidth = 3;
    	gbc_txtCiudadDestino.insets = new Insets(0, 0, 5, 5);
    	gbc_txtCiudadDestino.gridx = 2;
    	gbc_txtCiudadDestino.gridy = 3;
    	panelRutaVuelo.add(txtCiudadDestino, gbc_txtCiudadDestino);

    	txtPaisDestino = new JTextField();
    	txtPaisDestino.setEditable(false);
    	GridBagConstraints gbc_txtPaisDestino = new GridBagConstraints();
    	gbc_txtPaisDestino.gridwidth = 3;
    	gbc_txtPaisDestino.insets = new Insets(0, 0, 5, 5);
    	gbc_txtPaisDestino.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtPaisDestino.gridx = 6;
    	gbc_txtPaisDestino.gridy = 3;
    	panelRutaVuelo.add(txtPaisDestino, gbc_txtPaisDestino);
    	txtPaisDestino.setColumns(10);

    	JLabel lblHora = new JLabel("Hora:");
    	GridBagConstraints gbc_lblHora = new GridBagConstraints();
    	gbc_lblHora.anchor = GridBagConstraints.WEST;
    	gbc_lblHora.insets = new Insets(0, 0, 5, 5);
    	gbc_lblHora.gridx = 1;
    	gbc_lblHora.gridy = 4;
    	panelRutaVuelo.add(lblHora, gbc_lblHora);

    	txtHora1 = new JTextField();
    	txtHora1.setEditable(false);
    	GridBagConstraints gbc_txtHora1 = new GridBagConstraints();
    	gbc_txtHora1.gridwidth = 3;
    	gbc_txtHora1.insets = new Insets(0, 0, 5, 5);
    	gbc_txtHora1.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtHora1.gridx = 2;
    	gbc_txtHora1.gridy = 4;
    	panelRutaVuelo.add(txtHora1, gbc_txtHora1);
    	txtHora1.setColumns(10);

    	JLabel lblHoraSeparador = new JLabel(":");
    	GridBagConstraints gbc_lblHoraSeparador = new GridBagConstraints();
    	gbc_lblHoraSeparador.insets = new Insets(0, 0, 5, 5);
    	gbc_lblHoraSeparador.anchor = GridBagConstraints.EAST;
    	gbc_lblHoraSeparador.gridx = 5;
    	gbc_lblHoraSeparador.gridy = 4;
    	panelRutaVuelo.add(lblHoraSeparador, gbc_lblHoraSeparador);

    	txtHora2 = new JTextField();
    	txtHora2.setEditable(false);
    	GridBagConstraints gbc_txtHora2 = new GridBagConstraints();
    	gbc_txtHora2.gridwidth = 3;
    	gbc_txtHora2.insets = new Insets(0, 0, 5, 5);
    	gbc_txtHora2.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtHora2.gridx = 6;
    	gbc_txtHora2.gridy = 4;
    	panelRutaVuelo.add(txtHora2, gbc_txtHora2);
    	txtHora2.setColumns(10);

    	JLabel lblCostoBaseTurista = new JLabel("Costo base turista:");
    	GridBagConstraints gbc_lblCostoBaseTurista = new GridBagConstraints();
    	gbc_lblCostoBaseTurista.anchor = GridBagConstraints.WEST;
    	gbc_lblCostoBaseTurista.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCostoBaseTurista.gridx = 1;
    	gbc_lblCostoBaseTurista.gridy = 5;
    	panelRutaVuelo.add(lblCostoBaseTurista, gbc_lblCostoBaseTurista);

    	txtCostoTurista = new JTextField();
    	txtCostoTurista.setEditable(false);
    	txtCostoTurista.setColumns(10);
    	GridBagConstraints gbc_txtCostoTurista = new GridBagConstraints();
    	gbc_txtCostoTurista.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtCostoTurista.gridwidth = 7;
    	gbc_txtCostoTurista.insets = new Insets(0, 0, 5, 5);
    	gbc_txtCostoTurista.gridx = 2;
    	gbc_txtCostoTurista.gridy = 5;
    	panelRutaVuelo.add(txtCostoTurista, gbc_txtCostoTurista);

    	JLabel lblCostoBaseEjecutivo = new JLabel("Costo base ejecutivo:");
    	GridBagConstraints gbc_lblCostoBaseEjecutivo = new GridBagConstraints();
    	gbc_lblCostoBaseEjecutivo.anchor = GridBagConstraints.WEST;
    	gbc_lblCostoBaseEjecutivo.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCostoBaseEjecutivo.gridx = 1;
    	gbc_lblCostoBaseEjecutivo.gridy = 6;
    	panelRutaVuelo.add(lblCostoBaseEjecutivo, gbc_lblCostoBaseEjecutivo);

    	txtCostoEjecutivo = new JTextField();
    	txtCostoEjecutivo.setEditable(false);
    	txtCostoEjecutivo.setColumns(10);
    	GridBagConstraints gbc_txtCostoEjecutivo = new GridBagConstraints();
    	gbc_txtCostoEjecutivo.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtCostoEjecutivo.gridwidth = 7;
    	gbc_txtCostoEjecutivo.insets = new Insets(0, 0, 5, 5);
    	gbc_txtCostoEjecutivo.gridx = 2;
    	gbc_txtCostoEjecutivo.gridy = 6;
    	panelRutaVuelo.add(txtCostoEjecutivo, gbc_txtCostoEjecutivo);

    	JLabel lblCostoEquipajeExtra = new JLabel("Costo equipaje extra:");
    	GridBagConstraints gbc_lblCostoEquipajeExtra = new GridBagConstraints();
    	gbc_lblCostoEquipajeExtra.anchor = GridBagConstraints.WEST;
    	gbc_lblCostoEquipajeExtra.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCostoEquipajeExtra.gridx = 1;
    	gbc_lblCostoEquipajeExtra.gridy = 7;
    	panelRutaVuelo.add(lblCostoEquipajeExtra, gbc_lblCostoEquipajeExtra);

    	txtCostoEquipajeExtra = new JTextField();
    	txtCostoEquipajeExtra.setEditable(false);
    	txtCostoEquipajeExtra.setColumns(10);
    	GridBagConstraints gbc_txtCostoEquipajeExtra = new GridBagConstraints();
    	gbc_txtCostoEquipajeExtra.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtCostoEquipajeExtra.gridwidth = 7;
    	gbc_txtCostoEquipajeExtra.insets = new Insets(0, 0, 5, 5);
    	gbc_txtCostoEquipajeExtra.gridx = 2;
    	gbc_txtCostoEquipajeExtra.gridy = 7;
    	panelRutaVuelo.add(txtCostoEquipajeExtra, gbc_txtCostoEquipajeExtra);

    	JLabel lblFechaAlta = new JLabel("Fecha de alta:");
    	GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
    	gbc_lblFechaAlta.anchor = GridBagConstraints.WEST;
    	gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaAlta.gridx = 1;
    	gbc_lblFechaAlta.gridy = 8;
    	panelRutaVuelo.add(lblFechaAlta, gbc_lblFechaAlta);

    	txtFecha1 = new JTextField();
    	txtFecha1.setEditable(false);
    	txtFecha1.setColumns(10);
    	GridBagConstraints gbc_txtFecha1 = new GridBagConstraints();
    	gbc_txtFecha1.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtFecha1.insets = new Insets(0, 0, 5, 5);
    	gbc_txtFecha1.gridx = 2;
    	gbc_txtFecha1.gridy = 8;
    	panelRutaVuelo.add(txtFecha1, gbc_txtFecha1);

    	JLabel lblFechaSeparador1 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparador1 = new GridBagConstraints();
    	gbc_lblFechaSeparador1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparador1.gridx = 3;
    	gbc_lblFechaSeparador1.gridy = 8;
    	panelRutaVuelo.add(lblFechaSeparador1, gbc_lblFechaSeparador1);

    	txtFecha2 = new JTextField();
    	txtFecha2.setEditable(false);
    	txtFecha2.setColumns(10);
    	GridBagConstraints gbc_txtFecha2 = new GridBagConstraints();
    	gbc_txtFecha2.gridwidth = 3;
    	gbc_txtFecha2.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtFecha2.insets = new Insets(0, 0, 5, 5);
    	gbc_txtFecha2.gridx = 4;
    	gbc_txtFecha2.gridy = 8;
    	panelRutaVuelo.add(txtFecha2, gbc_txtFecha2);

    	JLabel lblFechaSeparador2 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaSeparador2 = new GridBagConstraints();
    	gbc_lblFechaSeparador2.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaSeparador2.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaSeparador2.gridx = 7;
    	gbc_lblFechaSeparador2.gridy = 8;
    	panelRutaVuelo.add(lblFechaSeparador2, gbc_lblFechaSeparador2);

    	txtFecha3 = new JTextField();
    	txtFecha3.setEditable(false);
    	txtFecha3.setColumns(10);
    	GridBagConstraints gbc_txtFecha3 = new GridBagConstraints();
    	gbc_txtFecha3.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtFecha3.insets = new Insets(0, 0, 5, 5);
    	gbc_txtFecha3.gridx = 8;
    	gbc_txtFecha3.gridy = 8;
    	panelRutaVuelo.add(txtFecha3, gbc_txtFecha3);

    	JLabel lblEstadoRuta = new JLabel("Estado de ruta:");
    	GridBagConstraints gbc_lblEstadoRuta = new GridBagConstraints();
    	gbc_lblEstadoRuta.anchor = GridBagConstraints.WEST;
    	gbc_lblEstadoRuta.insets = new Insets(0, 0, 5, 5);
    	gbc_lblEstadoRuta.gridx = 1;
    	gbc_lblEstadoRuta.gridy = 9;
    	panelRutaVuelo.add(lblEstadoRuta, gbc_lblEstadoRuta);

    	txtEstadoRuta = new JTextField();
    	txtEstadoRuta.setEditable(false);
    	GridBagConstraints gbc_txtEstadoRuta = new GridBagConstraints();
    	gbc_txtEstadoRuta.gridwidth = 7;
    	gbc_txtEstadoRuta.insets = new Insets(0, 0, 5, 5);
    	gbc_txtEstadoRuta.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtEstadoRuta.gridx = 2;
    	gbc_txtEstadoRuta.gridy = 9;
    	panelRutaVuelo.add(txtEstadoRuta, gbc_txtEstadoRuta);
    	txtEstadoRuta.setColumns(10);

    	JLabel lblDescripcion = new JLabel("Descripción:");
    	GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
    	gbc_lblDescripcion.anchor = GridBagConstraints.NORTHWEST;
    	gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDescripcion.gridx = 1;
    	gbc_lblDescripcion.gridy = 10;
    	panelRutaVuelo.add(lblDescripcion, gbc_lblDescripcion);

    	JScrollPane scrollDescripcion = new JScrollPane();
    	GridBagConstraints gbc_scrollDescripcion = new GridBagConstraints();
    	gbc_scrollDescripcion.fill = GridBagConstraints.BOTH;
    	gbc_scrollDescripcion.gridwidth = 7;
    	gbc_scrollDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollDescripcion.gridx = 2;
    	gbc_scrollDescripcion.gridy = 10;
    	panelRutaVuelo.add(scrollDescripcion, gbc_scrollDescripcion);

    	txtAreaDescripcion = new JTextArea();
    	txtAreaDescripcion.setEditable(false);
    	txtAreaDescripcion.setLineWrap(true);
    	scrollDescripcion.setViewportView(txtAreaDescripcion);

    	JLabel lblDescripcionCorta = new JLabel("Descripción corta:");
    	GridBagConstraints gbc_lblDescripcionCorta = new GridBagConstraints();
    	gbc_lblDescripcionCorta.anchor = GridBagConstraints.NORTHWEST;
    	gbc_lblDescripcionCorta.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDescripcionCorta.gridx = 1;
    	gbc_lblDescripcionCorta.gridy = 11;
    	panelRutaVuelo.add(lblDescripcionCorta, gbc_lblDescripcionCorta);

    	JScrollPane scrollPaneDescripcionCorta = new JScrollPane();
    	GridBagConstraints gbc_scrollPaneDescripcionCorta = new GridBagConstraints();
    	gbc_scrollPaneDescripcionCorta.gridwidth = 7;
    	gbc_scrollPaneDescripcionCorta.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollPaneDescripcionCorta.fill = GridBagConstraints.BOTH;
    	gbc_scrollPaneDescripcionCorta.gridx = 2;
    	gbc_scrollPaneDescripcionCorta.gridy = 11;
    	panelRutaVuelo.add(scrollPaneDescripcionCorta, gbc_scrollPaneDescripcionCorta);

    	txtDescripcionCorta = new JTextArea();
    	txtDescripcionCorta.setEditable(false);
    	txtDescripcionCorta.setLineWrap(true);
    	scrollPaneDescripcionCorta.setViewportView(txtDescripcionCorta);

    	JLabel lblCategorias = new JLabel("Categorías:");
    	GridBagConstraints gbc_lblCategorias = new GridBagConstraints();
    	gbc_lblCategorias.anchor = GridBagConstraints.NORTHWEST;
    	gbc_lblCategorias.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCategorias.gridx = 1;
    	gbc_lblCategorias.gridy = 12;
    	panelRutaVuelo.add(lblCategorias, gbc_lblCategorias);

    	JScrollPane scrollCategorias = new JScrollPane();
    	GridBagConstraints gbc_scrollCategorias = new GridBagConstraints();
    	gbc_scrollCategorias.gridwidth = 7;
    	gbc_scrollCategorias.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollCategorias.fill = GridBagConstraints.BOTH;
    	gbc_scrollCategorias.gridx = 2;
    	gbc_scrollCategorias.gridy = 12;
    	panelRutaVuelo.add(scrollCategorias, gbc_scrollCategorias);

    	listaCategorias = new JList<String>();
    	scrollCategorias.setViewportView(listaCategorias);

    	JPanel panelVuelos = new JPanel();
    	panelVuelos.setBorder(new TitledBorder(null, "Vuelos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    	GridBagConstraints gbc_panelVuelos = new GridBagConstraints();
    	gbc_panelVuelos.fill = GridBagConstraints.BOTH;
    	gbc_panelVuelos.gridx = 1;
    	gbc_panelVuelos.gridy = 1;
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
    	listaVuelos.addMouseListener(new MouseAdapter() {
    	    @SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
    	        JList<DTVuelo> list = (JList<DTVuelo>) evt.getSource();
    	        Rectangle rectangle = listaVuelos.getCellBounds(0, list.getLastVisibleIndex());
    	        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && rectangle != null && rectangle.contains(evt.getPoint())) {
    	            DTVuelo dtVuelo = listaVuelos.getSelectedValue();
    	            principal.consultaVuelo(dtVuelo);
    	        }
    	    }
    	});
	}

	private void actualizarDatosRuta(DTRutaVuelo dtRuta) {
		this.txtNombre.setText(dtRuta.getNombre());
		this.txtCiudadOrigen.setText(dtRuta.getCiudadOrigen().getNombre());
		this.txtPaisOrigen.setText(dtRuta.getCiudadOrigen().getPais());
		this.txtCiudadDestino.setText(dtRuta.getCiudadDestino().getNombre());
		this.txtPaisDestino.setText(dtRuta.getCiudadDestino().getPais());
		this.txtFecha1.setText(Integer.toString(dtRuta.getFechaAlta().getDayOfMonth()));
		this.txtFecha2.setText(Integer.toString(dtRuta.getFechaAlta().getMonthValue()));
		this.txtFecha3.setText(Integer.toString(dtRuta.getFechaAlta().getYear()));
		this.txtHora1.setText(Integer.toString(dtRuta.getHora().getHour()));
		this.txtHora2.setText(Integer.toString(dtRuta.getHora().getMinute()));
		this.txtCostoTurista.setText(Float.toString(dtRuta.getCostos().getCostoBaseTurista()));
		this.txtCostoEjecutivo.setText(Float.toString(dtRuta.getCostos().getCostoBaseEjecutivo()));
		this.txtCostoEquipajeExtra.setText(Float.toString(dtRuta.getCostos().getCostoEquipajeExtra()));
		this.txtAreaDescripcion.setText(dtRuta.getDescripcion());
		this.txtDescripcionCorta.setText(dtRuta.getDescripcionCorta());

		switch (dtRuta.getEstado()) {
			case INGRESADA:
				this.txtEstadoRuta.setText("Ingresada");
				break;
			case RECHAZADA:
				this.txtEstadoRuta.setText("Rechazada");
				break;
			case CONFIRMADA:
				this.txtEstadoRuta.setText("Confirmada");
				break;
			default:
				this.txtEstadoRuta.setText("Ké");
				break;
		}

		if (dtRuta.getEstado() == EstadoRuta.INGRESADA)

		this.listaCategorias.setListData(iRutaVuelo.obtenerRuta(dtRuta.getNombre()).getCategorias().stream().map(cat -> cat.getNombre()).toArray(String[]::new));
		try {
			this.listaVuelos.setListData(iRutaVuelo.obtenerVuelos(dtRuta).toArray(DTVuelo[]::new));
		} catch (RutaNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Consulta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void clear() {
		limpiando = true;
		this.listaAerolineas.clearSelection();
		this.listaCategorias.clearSelection();
		this.listaRutasVuelo.clearSelection();
		this.listaVuelos.clearSelection();
		this.listaCategorias.setListData(new String[0]);
		this.listaRutasVuelo.setListData(new DTRutaVuelo[0]);
		this.listaVuelos.setListData(new DTVuelo[0]);
		limpiando = false;

		this.txtNombre.setText("");
		this.txtCiudadOrigen.setText("");
		this.txtPaisOrigen.setText("");
		this.txtCiudadDestino.setText("");
		this.txtPaisDestino.setText("");
		this.txtFecha1.setText("");
		this.txtFecha2.setText("");
		this.txtFecha3.setText("");
		this.txtHora1.setText("");
		this.txtHora2.setText("");
		this.txtCostoTurista.setText("");
		this.txtCostoEjecutivo.setText("");
		this.txtCostoEquipajeExtra.setText("");
		this.txtAreaDescripcion.setText("");
		this.txtDescripcionCorta.setText("");
		this.txtEstadoRuta.setText("");
	}

	public void inicializar() {
		limpiando = true;
		listaAerolineas.clearSelection();
		limpiando = false;
		listaAerolineas.setListData(iUsuario.obtenerAerolineas().toArray(String[]::new));
	}

	public void inicializar(DTRutaVuelo dtRuta) {
		limpiando = true;
		listaAerolineas.clearSelection();
		listaAerolineas.setListData(iUsuario.obtenerAerolineas().toArray(String[]::new));
		try {
			String nickAerolinea = iUsuario.obtenerAerolinea(dtRuta.getNombre()).getNickname();
			listaAerolineas.setSelectedValue(nickAerolinea, true);
			Set<DTRutaVuelo> rutaVuelos = iRutaVuelo.obtenerRutasVuelo(nickAerolinea);
			listaRutasVuelo.setListData(rutaVuelos.toArray(DTRutaVuelo[]::new));
			limpiando = false;
			listaRutasVuelo.setSelectedValue(rutaVuelos.stream().filter(rv -> rv.getNombre().equals(dtRuta.getNombre())).toList().get(0), true);
		} catch (UsuarioNoExisteException | RutaNoExisteException e) {
			limpiando = false;
			JOptionPane.showMessageDialog(this, e.getMessage(), "Consulta de ruta de vuelo", JOptionPane.ERROR_MESSAGE);
		}
	}

}
