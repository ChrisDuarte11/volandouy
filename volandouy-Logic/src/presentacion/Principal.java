package presentacion;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import datatypes.DTPaquete;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import datatypes.DTVuelo;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

public class Principal {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.ventanaPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame ventanaPrincipal;
	private AltaUsuario altaUsuarioInternalFrame;
	private AltaRutaVuelo altaRutaVueloInternalFrame;
	private AltaVuelo altaVueloInternalFrame;
	private ModificarUsuario modificarUsuarioInternalFrame;
	private ConsultaUsuario consultaUsuarioInternalFrame;
	private ReservaVuelo reservaVueloInternalFrame;
	private ConsultaRutaVuelo consultaRutaVueloInternalFrame;
	private AltaCiudad altaciudadInternalFrame;
	private ConsultaDeVuelo consultaVueloInternalFrame;

	private AceptarRutaVuelo aceptarRutaVueloInternalFrame;

	private CargarDatos cargarDatos;

	public Principal() {
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		IRutaVuelo iRutaVuelo = fabrica.getIRutaVuelo();
		inicializar(iUsuario, iRutaVuelo);

		ventanaPrincipal.getContentPane().setLayout(null);

		/**
		 * Alta de usuario
		 */
		altaUsuarioInternalFrame = new AltaUsuario(iUsuario);
		altaUsuarioInternalFrame.setLocation(12, 12);
		altaUsuarioInternalFrame.setVisible(false);
		altaUsuarioInternalFrame.actualizarVentana();

		ventanaPrincipal.getContentPane().add(altaUsuarioInternalFrame);

		/**
		 * Consutar usuario
		 */
		consultaUsuarioInternalFrame = new ConsultaUsuario(iUsuario, iRutaVuelo, this);
		consultaUsuarioInternalFrame.setLocation(12, 12);
		consultaUsuarioInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(consultaUsuarioInternalFrame);

		/**
		 * Modificar usuario
		 */
		modificarUsuarioInternalFrame = new ModificarUsuario(iUsuario);
		modificarUsuarioInternalFrame.setLocation(12, 12);
		modificarUsuarioInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(modificarUsuarioInternalFrame);

		/**
		 * Alta de ruta de vuelo
		 */
		altaRutaVueloInternalFrame = new AltaRutaVuelo(iUsuario, iRutaVuelo);
		altaRutaVueloInternalFrame.setLocation(12, 12);
		altaRutaVueloInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(altaRutaVueloInternalFrame);

		/**
		 * Consulta de ruta de vuelo
		 */
		consultaRutaVueloInternalFrame = new ConsultaRutaVuelo(iUsuario, iRutaVuelo, this);
		consultaRutaVueloInternalFrame.setLocation(12, 12);
		consultaRutaVueloInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(consultaRutaVueloInternalFrame);

		/**
		 * Alta de vuelo
		 */
		altaVueloInternalFrame = new AltaVuelo(iUsuario, iRutaVuelo);
		altaVueloInternalFrame.setLocation(12, 12);
		altaVueloInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(altaVueloInternalFrame);

		/**
		 * Consulta de vuelo
		 */
		consultaVueloInternalFrame = new ConsultaDeVuelo(iUsuario, iRutaVuelo, this);
		consultaVueloInternalFrame.setLocation(12, 12);
		consultaVueloInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(consultaVueloInternalFrame);

		/**
		 * Reserva de vuelo
		 */
		reservaVueloInternalFrame = new ReservaVuelo(iUsuario, iRutaVuelo);
		reservaVueloInternalFrame.setLocation(12, 12);
		reservaVueloInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(reservaVueloInternalFrame);

		/**
		 * Alta de ciudad
		 */
		altaciudadInternalFrame = new AltaCiudad(iRutaVuelo);
		altaciudadInternalFrame.setLocation(12, 12);
		altaciudadInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(altaciudadInternalFrame);

		/**
		 * Aceptar Ruta de Vuelo
		 */
		aceptarRutaVueloInternalFrame = new AceptarRutaVuelo(iUsuario, iRutaVuelo);
		aceptarRutaVueloInternalFrame.setLocation(12, 12);
		aceptarRutaVueloInternalFrame.setVisible(false);

		ventanaPrincipal.getContentPane().add(aceptarRutaVueloInternalFrame);

		/**
		 * Cargar datos
		 */
		this.cargarDatos = new CargarDatos(iUsuario, iRutaVuelo, this.ventanaPrincipal);
	}

	public void consultaPaquete(DTPaquete dtPaquete) {
		// TODO Auto-generated method stub
	}

	public void consultaReserva(DTReserva dtReserva) {
		this.consultaVuelo(new DTVuelo(dtReserva.getVuelo()));
	}

	public void consultaRutaVuelo(DTRutaVuelo dtRuta) {
		consultaRutaVueloInternalFrame.clear();
		consultaRutaVueloInternalFrame.inicializar(dtRuta);
		consultaRutaVueloInternalFrame.setVisible(true);
	}

	public void consultaUsuario(DTUsuario dtUsuario) {
		consultaUsuarioInternalFrame.clear();
		consultaUsuarioInternalFrame.inicializar(dtUsuario);
		consultaUsuarioInternalFrame.setVisible(true);
	}

	public void consultaVuelo(DTVuelo dtVuelo) {
		consultaVueloInternalFrame.clear();
		consultaVueloInternalFrame.inicializar(dtVuelo);
		consultaVueloInternalFrame.setVisible(true);
	}

	private void inicializar(IUsuario iUsuario, IRutaVuelo iRutaVuelo) {
		ventanaPrincipal = new JFrame();
		ventanaPrincipal.setTitle("Tarea 1");
		ventanaPrincipal.setBounds(100, 100, 1200, 800);
		ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		ventanaPrincipal.setJMenuBar(menuBar);

		/**
		 * Sección de menú: Usuario
		 */
		JMenu menuUsuario = new JMenu("Usuario");
		menuBar.add(menuUsuario);

		/**
		 * Alta de usuario
		 */
		JMenuItem menuItemAltaDeUsuario = new JMenuItem("Alta de usuario");
		menuItemAltaDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				altaUsuarioInternalFrame.limpiarEntrada();
				altaUsuarioInternalFrame.setVisible(true);
			}
		});
		menuUsuario.add(menuItemAltaDeUsuario);

		/**
		 * Consultar usuario
		 */
		JMenuItem menuItemConsultaUsuario = new JMenuItem("Consulta de usuario");
		menuItemConsultaUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultaUsuarioInternalFrame.clear();
				consultaUsuarioInternalFrame.inicializar();
				consultaUsuarioInternalFrame.setVisible(true);
			}
		});
		menuUsuario.add(menuItemConsultaUsuario);

		/**
		 * Modificar usuario
		 */
		JMenuItem menuItemModificarUsuario = new JMenuItem("Modificar usuario");
		menuItemModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificarUsuarioInternalFrame.limpiarEntrada();
				modificarUsuarioInternalFrame.inicializarDatos();
				modificarUsuarioInternalFrame.setVisible(true);
			}
		});
		menuUsuario.add(menuItemModificarUsuario);


		/**
		 * Sección de menú: Ruta vuelo
		 */
		JMenu menuRutaVuelo = new JMenu("Ruta vuelo");
		menuBar.add(menuRutaVuelo);

		/**
		 * Alta de ruta de vuelo
		 */
		JMenuItem menuItemAltaRutaVuelo = new JMenuItem("Alta de ruta de vuelo");
		menuItemAltaRutaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				altaRutaVueloInternalFrame.limpiarEntrada();
				altaRutaVueloInternalFrame.inicializarDatos();
				altaRutaVueloInternalFrame.setVisible(true);
			}
		});
		menuRutaVuelo.add(menuItemAltaRutaVuelo);

		/**
		 * Consulta de ruta de vuelo
		 */
		JMenuItem menuItemConsultaRutaVuelo = new JMenuItem("Consulta de ruta de vuelo");
		menuItemConsultaRutaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultaRutaVueloInternalFrame.clear();
				consultaRutaVueloInternalFrame.inicializar();
				consultaRutaVueloInternalFrame.setVisible(true);
			}
		});
		menuRutaVuelo.add(menuItemConsultaRutaVuelo);

		/**
		 * Alta de vuelo
		 */
		JMenuItem altaVueloMenuItem = new JMenuItem("Alta de vuelo");
		altaVueloMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				altaVueloInternalFrame.limpiarEntrada();
				altaVueloInternalFrame.inicializarAerolineas();
				altaVueloInternalFrame.setVisible(true);
			}
		});
		menuRutaVuelo.add(altaVueloMenuItem);

		/**
		 * Consulta de vuelo
		 */
		JMenuItem menuItemConsultaVuelo = new JMenuItem("Consulta de vuelo");
		menuItemConsultaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultaVueloInternalFrame.clear();
				consultaVueloInternalFrame.inicializar();
				consultaVueloInternalFrame.setVisible(true);
			}
		});
		menuRutaVuelo.add(menuItemConsultaVuelo);

		/**
		 * Reserva de vuelo
		 */
		JMenuItem menuItemReservadeVuelo = new JMenuItem("Reserva de vuelo");
		menuItemReservadeVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reservaVueloInternalFrame.inicializar();
				reservaVueloInternalFrame.setVisible(true);
			}
		});
		menuRutaVuelo.add(menuItemReservadeVuelo);

		/**
		 * Alta de ciudad
		 */
		JMenuItem menuItemAltaCiudad = new JMenuItem("Alta de ciudad");
		menuItemAltaCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				altaciudadInternalFrame.limpiarCampos();
				altaciudadInternalFrame.setVisible(true);
			}
		});
		menuRutaVuelo.add(menuItemAltaCiudad);

		/**
		 * Aceptar Ruta de Vuelo
		 */
		JMenuItem menuItemAceptarRutaVuelo = new JMenuItem("Aceptar o Rechazar Ruta de Vuelo");
		menuItemAceptarRutaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aceptarRutaVueloInternalFrame.inicializarAerolineas();
				aceptarRutaVueloInternalFrame.setVisible(true);
			}
		});
		menuRutaVuelo.add(menuItemAceptarRutaVuelo);


		/**
		 * Sección de menú: Otros
		 */
		JMenu menuOtros = new JMenu("Otros");
		menuBar.add(menuOtros);

		/**
		 * Cargar datos
		 */
		JMenuItem menuItemCargarDatos = new JMenuItem("Cargar datos");
		menuItemCargarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarDatos.cargarCSV();
			}
		});
		menuOtros.add(menuItemCargarDatos);
	}

}
