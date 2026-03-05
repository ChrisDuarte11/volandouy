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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import datatypes.DTAerolinea;
import datatypes.DTCliente;
import datatypes.DTPaquete;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import excepciones.UsuarioNoExisteException;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

@SuppressWarnings("serial")
public class ConsultaUsuario extends JInternalFrame {
    private IRutaVuelo iRutaVuelo;
    private IUsuario iUsuario;

    private GridBagLayout gbl_panelUsuario;
    private GridBagLayout gridBagLayout;
    private JLabel lblApellido;
    private JLabel lblDescripcion;
    private JLabel lblFechaDeNacimiento;
    private JLabel lblFechaSeparador;
    private JLabel lblFechaSeparador2;
    private JLabel lblNacionalidad;
    private JLabel lblNumDoc;
    private JLabel lblTipoDeDocumento;
    private JLabel lblWeb;
    private JList<DTPaquete> listaPaquetes;
    private JList<DTReserva> listaReservas;
    private JList<DTRutaVuelo> listaRutas;
    private JList<String> listaUsuarios;
    private JPanel panelListaRutasVuelo;
    private JPanel panelPaquetes;
    private JPanel panelReservas;
    private JScrollPane scrollDescripcion;
    private JTextArea areaDescripcion;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtFechaAnio;
    private JTextField txtFechaDia;
    private JTextField txtFechaMes;
    private JTextField txtNacionalidad;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtNumDoc;
    private JTextField txtTipoDoc;
    private JTextField txtWeb;

    private boolean limpiando = false;
    private JLabel lblContrasea;
    private JTextField txtContrasena;

    public ConsultaUsuario(IUsuario interfazUsuario, IRutaVuelo iRutaVuelo, Principal principal) {
    	this.iUsuario = interfazUsuario;
    	this.iRutaVuelo = iRutaVuelo;
    	setTitle("Consulta de Usuario");
    	setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setClosable(true);
    	setBounds(0, 0, 710, 630);

    	Set<String> usuarios = iUsuario.listarUsuarios();
    	gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{495, 200};
    	gridBagLayout.rowHeights = new int[]{395, 200, 100, 100};
    	gridBagLayout.columnWeights = new double[]{1.0, 0.0};
    	gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0};
    	getContentPane().setLayout(gridBagLayout);

		JPanel panelUsuario = new JPanel();
		panelUsuario.setBorder(new TitledBorder(null, "Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(5, 0, 5, 5);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = 0;
		getContentPane().add(panelUsuario, gbc_panelUsuario);
		gbl_panelUsuario = new GridBagLayout();
		gbl_panelUsuario.columnWidths = new int[]{15, 190, 30, 0, 30, 0, 50, 100, 15, 0};
		gbl_panelUsuario.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 80, 30, 0};
		gbl_panelUsuario.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelUsuario.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelUsuario.setLayout(gbl_panelUsuario);

		JLabel lblNewLabel = new JLabel("Nickname:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panelUsuario.add(lblNewLabel, gbc_lblNewLabel);

		txtNickname = new JTextField();
		txtNickname.setEditable(false);
		GridBagConstraints gbc_txtNickname = new GridBagConstraints();
		gbc_txtNickname.gridwidth = 6;
		gbc_txtNickname.insets = new Insets(0, 0, 5, 5);
		gbc_txtNickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNickname.gridx = 2;
		gbc_txtNickname.gridy = 1;
		panelUsuario.add(txtNickname, gbc_txtNickname);
		txtNickname.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		panelUsuario.add(lblNombre, gbc_lblNombre);

		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.gridwidth = 6;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 2;
		gbc_txtNombre.gridy = 2;
		panelUsuario.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);

		JLabel lblCorreo = new JLabel("Correo:");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 1;
		gbc_lblCorreo.gridy = 3;
		panelUsuario.add(lblCorreo, gbc_lblCorreo);

		txtCorreo = new JTextField();
		txtCorreo.setEditable(false);
		GridBagConstraints gbc_txtCorreo = new GridBagConstraints();
		gbc_txtCorreo.gridwidth = 6;
		gbc_txtCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_txtCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCorreo.gridx = 2;
		gbc_txtCorreo.gridy = 3;
		panelUsuario.add(txtCorreo, gbc_txtCorreo);
		txtCorreo.setColumns(10);

		lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 1;
		gbc_lblApellido.gridy = 4;
		panelUsuario.add(lblApellido, gbc_lblApellido);

		txtApellido = new JTextField();
		txtApellido.setEditable(false);
		GridBagConstraints gbc_txtApellido = new GridBagConstraints();
		gbc_txtApellido.gridwidth = 6;
		gbc_txtApellido.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellido.gridx = 2;
		gbc_txtApellido.gridy = 4;
		panelUsuario.add(txtApellido, gbc_txtApellido);
		txtApellido.setColumns(10);

		lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblFechaDeNacimiento = new GridBagConstraints();
		gbc_lblFechaDeNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaDeNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeNacimiento.gridx = 1;
		gbc_lblFechaDeNacimiento.gridy = 5;
		panelUsuario.add(lblFechaDeNacimiento, gbc_lblFechaDeNacimiento);

		txtFechaDia = new JTextField();
		txtFechaDia.setEditable(false);
		GridBagConstraints gbc_txtFechaDia = new GridBagConstraints();
		gbc_txtFechaDia.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaDia.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaDia.gridx = 2;
		gbc_txtFechaDia.gridy = 5;
		panelUsuario.add(txtFechaDia, gbc_txtFechaDia);
		txtFechaDia.setColumns(10);

		lblFechaSeparador = new JLabel("/");
		GridBagConstraints gbc_lblFechaSeparador = new GridBagConstraints();
		gbc_lblFechaSeparador.anchor = GridBagConstraints.EAST;
		gbc_lblFechaSeparador.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaSeparador.gridx = 3;
		gbc_lblFechaSeparador.gridy = 5;
		panelUsuario.add(lblFechaSeparador, gbc_lblFechaSeparador);

		txtFechaMes = new JTextField();
		txtFechaMes.setEditable(false);
		GridBagConstraints gbc_txtFechaMes = new GridBagConstraints();
		gbc_txtFechaMes.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaMes.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaMes.gridx = 4;
		gbc_txtFechaMes.gridy = 5;
		panelUsuario.add(txtFechaMes, gbc_txtFechaMes);
		txtFechaMes.setColumns(10);

		lblFechaSeparador2 = new JLabel("/");
		GridBagConstraints gbc_lblFechaSeparador2 = new GridBagConstraints();
		gbc_lblFechaSeparador2.anchor = GridBagConstraints.EAST;
		gbc_lblFechaSeparador2.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaSeparador2.gridx = 5;
		gbc_lblFechaSeparador2.gridy = 5;
		panelUsuario.add(lblFechaSeparador2, gbc_lblFechaSeparador2);

		txtFechaAnio = new JTextField();
		txtFechaAnio.setEditable(false);
		GridBagConstraints gbc_txtFechaAnio = new GridBagConstraints();
		gbc_txtFechaAnio.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaAnio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaAnio.gridx = 6;
		gbc_txtFechaAnio.gridy = 5;
		panelUsuario.add(txtFechaAnio, gbc_txtFechaAnio);
		txtFechaAnio.setColumns(10);

		lblTipoDeDocumento = new JLabel("Tipo de documento:");
		GridBagConstraints gbc_lblTipoDeDocumento = new GridBagConstraints();
		gbc_lblTipoDeDocumento.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDeDocumento.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDeDocumento.gridx = 1;
		gbc_lblTipoDeDocumento.gridy = 6;
		panelUsuario.add(lblTipoDeDocumento, gbc_lblTipoDeDocumento);

		txtTipoDoc = new JTextField();
		txtTipoDoc.setEditable(false);
		GridBagConstraints gbc_txtTipoDoc = new GridBagConstraints();
		gbc_txtTipoDoc.gridwidth = 6;
		gbc_txtTipoDoc.insets = new Insets(0, 0, 5, 5);
		gbc_txtTipoDoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTipoDoc.gridx = 2;
		gbc_txtTipoDoc.gridy = 6;
		panelUsuario.add(txtTipoDoc, gbc_txtTipoDoc);
		txtTipoDoc.setColumns(10);

		lblNumDoc = new JLabel("Número de documento:");
		GridBagConstraints gbc_lblNumDoc = new GridBagConstraints();
		gbc_lblNumDoc.anchor = GridBagConstraints.EAST;
		gbc_lblNumDoc.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumDoc.gridx = 1;
		gbc_lblNumDoc.gridy = 7;
		panelUsuario.add(lblNumDoc, gbc_lblNumDoc);

		txtNumDoc = new JTextField();
		txtNumDoc.setEditable(false);
		GridBagConstraints gbc_txtNumDoc = new GridBagConstraints();
		gbc_txtNumDoc.gridwidth = 6;
		gbc_txtNumDoc.insets = new Insets(0, 0, 5, 5);
		gbc_txtNumDoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumDoc.gridx = 2;
		gbc_txtNumDoc.gridy = 7;
		panelUsuario.add(txtNumDoc, gbc_txtNumDoc);
		txtNumDoc.setColumns(10);

		lblNacionalidad = new JLabel("Nacionalidad:");
		GridBagConstraints gbc_lblNacionalidad = new GridBagConstraints();
		gbc_lblNacionalidad.anchor = GridBagConstraints.EAST;
		gbc_lblNacionalidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblNacionalidad.gridx = 1;
		gbc_lblNacionalidad.gridy = 8;
		panelUsuario.add(lblNacionalidad, gbc_lblNacionalidad);
		txtNacionalidad = new JTextField();
		txtNacionalidad.setEditable(false);
		GridBagConstraints gbc_txtNacionalidad = new GridBagConstraints();
		gbc_txtNacionalidad.gridwidth = 6;
		gbc_txtNacionalidad.insets = new Insets(0, 0, 5, 5);
		gbc_txtNacionalidad.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNacionalidad.gridx = 2;
		gbc_txtNacionalidad.gridy = 8;
		panelUsuario.add(txtNacionalidad, gbc_txtNacionalidad);
		txtNacionalidad.setColumns(10);

		lblWeb = new JLabel("Web:");
		GridBagConstraints gbc_lblWeb = new GridBagConstraints();
		gbc_lblWeb.anchor = GridBagConstraints.EAST;
		gbc_lblWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblWeb.gridx = 1;
		gbc_lblWeb.gridy = 9;
		panelUsuario.add(lblWeb, gbc_lblWeb);

		txtWeb = new JTextField();
		txtWeb.setText("https://www.google.com.uy/");
		txtWeb.setEditable(false);
		GridBagConstraints gbc_txtWeb = new GridBagConstraints();
		gbc_txtWeb.gridwidth = 6;
		gbc_txtWeb.insets = new Insets(0, 0, 5, 5);
		gbc_txtWeb.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWeb.gridx = 2;
		gbc_txtWeb.gridy = 9;
		panelUsuario.add(txtWeb, gbc_txtWeb);
		txtWeb.setColumns(10);

		lblDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 10;
		panelUsuario.add(lblDescripcion, gbc_lblDescripcion);

		scrollDescripcion = new JScrollPane();
		GridBagConstraints gbc_scrollDescripcion = new GridBagConstraints();
		gbc_scrollDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_scrollDescripcion.gridwidth = 6;
		gbc_scrollDescripcion.fill = GridBagConstraints.BOTH;
		gbc_scrollDescripcion.gridx = 2;
		gbc_scrollDescripcion.gridy = 10;
		panelUsuario.add(scrollDescripcion, gbc_scrollDescripcion);
		areaDescripcion = new JTextArea();
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setEditable(false);
		scrollDescripcion.setViewportView(areaDescripcion);

		lblContrasea = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.insets = new Insets(0, 0, 0, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 11;
		panelUsuario.add(lblContrasea, gbc_lblContrasea);

		txtContrasena = new JTextField();
		txtContrasena.setEditable(false);
		txtContrasena.setColumns(10);
		GridBagConstraints gbc_txtContrasena = new GridBagConstraints();
		gbc_txtContrasena.gridwidth = 6;
		gbc_txtContrasena.insets = new Insets(0, 0, 0, 5);
		gbc_txtContrasena.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContrasena.gridx = 2;
		gbc_txtContrasena.gridy = 11;
		panelUsuario.add(txtContrasena, gbc_txtContrasena);

		JPanel panelListaUsuarios = new JPanel();
		panelListaUsuarios.setBorder(new TitledBorder(null, "Usuarios registrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelListaUsuarios = new GridBagConstraints();
		gbc_panelListaUsuarios.fill = GridBagConstraints.BOTH;
		gbc_panelListaUsuarios.insets = new Insets(0, 0, 5, 0);
		gbc_panelListaUsuarios.gridx = 1;
		gbc_panelListaUsuarios.gridy = 0;
		getContentPane().add(panelListaUsuarios, gbc_panelListaUsuarios);
		GridBagLayout gbl_panelListaUsuarios = new GridBagLayout();
		gbl_panelListaUsuarios.columnWidths = new int[]{0, 0};
		gbl_panelListaUsuarios.rowHeights = new int[]{0, 0};
		gbl_panelListaUsuarios.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelListaUsuarios.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelListaUsuarios.setLayout(gbl_panelListaUsuarios);

		JScrollPane scrollUsuarios = new JScrollPane();
		GridBagConstraints gbc_scrollUsuarios = new GridBagConstraints();
		gbc_scrollUsuarios.insets = new Insets(5, 0, 0, 0);
		gbc_scrollUsuarios.fill = GridBagConstraints.BOTH;
		gbc_scrollUsuarios.gridx = 0;
		gbc_scrollUsuarios.gridy = 0;
		panelListaUsuarios.add(scrollUsuarios, gbc_scrollUsuarios);
		listaUsuarios = new JList<String>(usuarios.stream().toArray(String[]::new));
		scrollUsuarios.setViewportView(listaUsuarios);
		listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listaUsuarios.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && !limpiando) {
					JList<String> source = (JList<String>) e.getSource();
					String nick = source.getSelectedValue().toString();
					actualizarDatosUsuario(nick);
				}
			}
		});

		panelListaRutasVuelo = new JPanel();
		panelListaRutasVuelo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Rutas de vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_panelListaRutasVuelo = new GridBagConstraints();
		gbc_panelListaRutasVuelo.gridwidth = 2;
		gbc_panelListaRutasVuelo.insets = new Insets(0, 0, 5, 0);
		gbc_panelListaRutasVuelo.fill = GridBagConstraints.BOTH;
		gbc_panelListaRutasVuelo.gridx = 0;
		gbc_panelListaRutasVuelo.gridy = 1;
		getContentPane().add(panelListaRutasVuelo, gbc_panelListaRutasVuelo);
		GridBagLayout gbl_panelListaRutasVuelo = new GridBagLayout();
		gbl_panelListaRutasVuelo.columnWidths = new int[]{0, 0};
		gbl_panelListaRutasVuelo.rowHeights = new int[]{0, 0};
		gbl_panelListaRutasVuelo.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelListaRutasVuelo.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelListaRutasVuelo.setLayout(gbl_panelListaRutasVuelo);

		JScrollPane scrollRutas = new JScrollPane();
		GridBagConstraints gbc_scrollRutas = new GridBagConstraints();
		gbc_scrollRutas.fill = GridBagConstraints.BOTH;
		gbc_scrollRutas.gridx = 0;
		gbc_scrollRutas.gridy = 0;
		panelListaRutasVuelo.add(scrollRutas, gbc_scrollRutas);

		listaRutas = new JList<DTRutaVuelo>();
		listaRutas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollRutas.setViewportView(listaRutas);

		listaRutas.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
				JList<DTRutaVuelo> list = (JList<DTRutaVuelo>) evt.getSource();
				Rectangle r = listaRutas.getCellBounds(0, list.getLastVisibleIndex());
				if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && r != null && r.contains(evt.getPoint())) {
					DTRutaVuelo dtRuta = listaRutas.getSelectedValue();
					principal.consultaRutaVuelo(dtRuta);
				}
			}
		});

		panelReservas = new JPanel();
		panelReservas.setBorder(new TitledBorder(null, "Reservas de vuelos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelReservas = new GridBagConstraints();
		gbc_panelReservas.gridwidth = 2;
		gbc_panelReservas.insets = new Insets(0, 0, 5, 0);
		gbc_panelReservas.fill = GridBagConstraints.BOTH;
		gbc_panelReservas.gridx = 0;
		gbc_panelReservas.gridy = 2;
		getContentPane().add(panelReservas, gbc_panelReservas);
		GridBagLayout gbl_panelReservas = new GridBagLayout();
		gbl_panelReservas.columnWidths = new int[]{0, 0};
		gbl_panelReservas.rowHeights = new int[]{0, 0};
		gbl_panelReservas.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelReservas.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelReservas.setLayout(gbl_panelReservas);

		JScrollPane scrollReservas = new JScrollPane();
		GridBagConstraints gbc_scrollReservas = new GridBagConstraints();
		gbc_scrollReservas.fill = GridBagConstraints.BOTH;
		gbc_scrollReservas.gridx = 0;
		gbc_scrollReservas.gridy = 0;
		panelReservas.add(scrollReservas, gbc_scrollReservas);

		listaReservas = new JList<DTReserva>();
		listaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollReservas.setViewportView(listaReservas);

		listaReservas.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
				JList<DTReserva> list = (JList<DTReserva>) evt.getSource();
				Rectangle r = listaReservas.getCellBounds(0, list.getLastVisibleIndex());
				if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && r != null && r.contains(evt.getPoint())) {
					DTReserva dtReserva = listaReservas.getSelectedValue();
					principal.consultaReserva(dtReserva);
				}
			}
		});

		panelPaquetes = new JPanel();
		panelPaquetes.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Paquetes comprados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_panelPaquetes = new GridBagConstraints();
		gbc_panelPaquetes.gridwidth = 2;
		gbc_panelPaquetes.fill = GridBagConstraints.BOTH;
		gbc_panelPaquetes.gridx = 0;
		gbc_panelPaquetes.gridy = 3;
		getContentPane().add(panelPaquetes, gbc_panelPaquetes);
		GridBagLayout gbl_panelPaquetes = new GridBagLayout();
		gbl_panelPaquetes.columnWidths = new int[]{0, 0};
		gbl_panelPaquetes.rowHeights = new int[]{0, 0};
		gbl_panelPaquetes.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelPaquetes.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelPaquetes.setLayout(gbl_panelPaquetes);

		JScrollPane scrollPaquetes = new JScrollPane();
		GridBagConstraints gbc_scrollPaquetes = new GridBagConstraints();
		gbc_scrollPaquetes.fill = GridBagConstraints.BOTH;
		gbc_scrollPaquetes.gridx = 0;
		gbc_scrollPaquetes.gridy = 0;
		panelPaquetes.add(scrollPaquetes, gbc_scrollPaquetes);

		listaPaquetes = new JList<DTPaquete>();
		listaPaquetes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaquetes.setViewportView(listaPaquetes);

    	listaPaquetes.addMouseListener(new MouseAdapter() {
    	    @SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
    	        JList<DTPaquete> list = (JList<DTPaquete>) evt.getSource();
    	        Rectangle r = listaPaquetes.getCellBounds(0, list.getLastVisibleIndex());
    	        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && r != null && r.contains(evt.getPoint())) {
    	            DTPaquete dtPaquete = listaPaquetes.getSelectedValue();
    	            principal.consultaPaquete(dtPaquete);
    	        }
    	    }
    	});

    }

    private void actualizarDatosUsuario(String nick){
		try{
			DTUsuario dtUser = iUsuario.obtenerUsuario(nick);
			this.txtNickname.setText(dtUser.getNickname());
			this.txtNombre.setText(dtUser.getNombre());
			this.txtCorreo.setText(dtUser.getCorreo());
			this.txtContrasena.setText(dtUser.getPassword());
			if(dtUser instanceof DTCliente dtCliente) {
				this.txtApellido.setText(dtCliente.getApellido());
				this.txtTipoDoc.setText(dtCliente.getTipoDoc().toString());
				this.txtNumDoc.setText(dtCliente.getNumDoc());
				this.txtNacionalidad.setText(dtCliente.getNacionalidad().getPais());
				this.txtFechaDia.setText(Integer.toString(dtCliente.getFechaNacimiento().getDayOfMonth()));
				this.txtFechaMes.setText(Integer.toString(dtCliente.getFechaNacimiento().getMonthValue()));
				this.txtFechaAnio.setText(Integer.toString(dtCliente.getFechaNacimiento().getYear()));
				this.listaReservas.setListData(iUsuario.obtenerReservas(nick).toArray(DTReserva[]::new));
				this.listaPaquetes.setListData(iUsuario.obtenerPaquetes(nick).toArray(DTPaquete[]::new));
				visibilidadAerolinea(false);
				visibilidadCliente(true);
			}
			else {
				DTAerolinea dtAero = (DTAerolinea)dtUser;
				this.txtWeb.setText(dtAero.getWeb());
				this.areaDescripcion.setText(dtAero.getDescripcion());
				this.listaRutas.setListData(iRutaVuelo.obtenerRutasVuelo(nick).toArray(DTRutaVuelo[]::new));
				visibilidadCliente(false);
				visibilidadAerolinea(true);
			}

		}
		catch(UsuarioNoExisteException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Consulta de Usuario", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void clear() {
    	limpiando = true;
    	listaUsuarios.clearSelection();
    	limpiando = false;
    	listaPaquetes.clearSelection();
    	listaReservas.clearSelection();
    	listaRutas.clearSelection();

    	listaRutas.setListData(new DTRutaVuelo[0]);
    	listaPaquetes.setListData(new DTPaquete[0]);
    	listaReservas.setListData(new DTReserva[0]);

    	this.txtNombre.setText("");
    	this.txtNickname.setText("");
    	this.txtCorreo.setText("");
    	this.txtApellido.setText("");
    	this.txtTipoDoc.setText("");
    	this.txtNumDoc.setText("");
    	this.txtNacionalidad.setText("");
    	this.txtFechaDia.setText("");
    	this.txtFechaMes.setText("");
    	this.txtFechaAnio.setText("");
    	this.txtWeb.setText("");
    	this.areaDescripcion.setText("");
    	this.txtContrasena.setText("");
    }

	public void inicializar() {
		limpiando = true;
		listaUsuarios.clearSelection();
		limpiando = false;
		listaUsuarios.setListData(iUsuario.listarUsuarios().toArray(String[]::new));

		gridBagLayout.rowHeights[2] = 0;
		gridBagLayout.rowHeights[3] = 0;
		this.panelPaquetes.setVisible(false);
		this.panelReservas.setVisible(false);
	}

	public void inicializar(DTUsuario dtUsuario) {
		inicializar();
		boolean cliente = dtUsuario instanceof DTCliente;
		visibilidadCliente(cliente);
		visibilidadAerolinea(!cliente);
		listaUsuarios.setSelectedValue(dtUsuario.getNickname(), true);

	}

	private void visibilidadAerolinea(boolean visible) {
		this.txtWeb.setVisible(visible);
		this.areaDescripcion.setVisible(visible);
		this.listaRutas.setVisible(visible);
		this.scrollDescripcion.setVisible(visible);

		this.lblWeb.setVisible(visible);
		this.lblDescripcion.setVisible(visible);

		this.panelListaRutasVuelo.setVisible(visible);
		if(!visible) {
			gridBagLayout.rowHeights[1] = 0;
			gridBagLayout.rowWeights[1] = 0;
			gbl_panelUsuario.rowHeights[10] = 0;
		}
		else {
			gridBagLayout.rowHeights[1] = 200;
			gridBagLayout.rowWeights[1] = 1;
			gbl_panelUsuario.rowHeights[10] = 80;
		}
	}

	private void visibilidadCliente(boolean visible) {
		this.txtApellido.setVisible(visible);
		this.txtTipoDoc.setVisible(visible);
		this.txtNumDoc.setVisible(visible);
		this.txtNacionalidad.setVisible(visible);
		this.txtFechaDia.setVisible(visible);
		this.txtFechaMes.setVisible(visible);
		this.txtFechaAnio.setVisible(visible);
		this.listaReservas.setVisible(visible);
		this.listaPaquetes.setVisible(visible);

		this.lblApellido.setVisible(visible);
		this.lblTipoDeDocumento.setVisible(visible);
		this.lblNumDoc.setVisible(visible);
		this.lblNacionalidad.setVisible(visible);
		this.lblFechaDeNacimiento.setVisible(visible);
		this.lblFechaSeparador.setVisible(visible);
		this.lblFechaSeparador2.setVisible(visible);

		this.panelPaquetes.setVisible(visible);
		this.panelReservas.setVisible(visible);

		if (visible) {
			gridBagLayout.rowHeights[2] = 100;
			gridBagLayout.rowHeights[3] = 100;
			gridBagLayout.rowWeights[2] = 1;
			gridBagLayout.rowWeights[3] = 1;
		} else {
			gridBagLayout.rowHeights[2] = 0;
			gridBagLayout.rowHeights[3] = 0;
			gridBagLayout.rowWeights[2] = 0;
			gridBagLayout.rowWeights[3] = 0;
		}
	}
}
