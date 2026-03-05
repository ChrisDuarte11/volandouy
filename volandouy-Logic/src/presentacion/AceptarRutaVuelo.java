package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import datatypes.DTRutaVuelo;
import excepciones.UsuarioNoExisteException;
import logica.enums.EstadoRuta;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

@SuppressWarnings("serial")
public class AceptarRutaVuelo extends JInternalFrame {

	private IUsuario iUsuario;

	private JComboBox<String> aerolineaComboBox;
	private JComboBox<String> rutaVueloComboBox;

	public AceptarRutaVuelo(IUsuario iUsuario, IRutaVuelo iRutaVuelo) {
		this.iUsuario = iUsuario;

		setTitle("Alta de vuelo");
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 710, 350);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{25, 197, 30, 0, 30, 0, 50, 0, 0, 62, 25, 0};
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

						JButton aceptarButton = new JButton("Aceptar");
						aceptarButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								aceptar(EstadoRuta.CONFIRMADA);
							}
						});
						GridBagConstraints gbc_aceptarButton = new GridBagConstraints();
						gbc_aceptarButton.anchor = GridBagConstraints.EAST;
						gbc_aceptarButton.insets = new Insets(0, 0, 5, 5);
						gbc_aceptarButton.gridx = 2;
						gbc_aceptarButton.gridy = 4;
						getContentPane().add(aceptarButton, gbc_aceptarButton);

								JButton rechazarButton = new JButton("Rechazar");
								rechazarButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										aceptar(EstadoRuta.RECHAZADA);
									}
								});
								GridBagConstraints gbc_rechazarButton = new GridBagConstraints();
								gbc_rechazarButton.anchor = GridBagConstraints.EAST;
								gbc_rechazarButton.insets = new Insets(0, 0, 5, 5);
								gbc_rechazarButton.gridx = 4;
								gbc_rechazarButton.gridy = 4;
								getContentPane().add(rechazarButton, gbc_rechazarButton);
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
			if (ruta.getEstado().equals(EstadoRuta.INGRESADA))
			nombresRutas.add(ruta.getNombre());
		}
		DefaultComboBoxModel<String> modelNick = new DefaultComboBoxModel<String>(nombresRutas.toArray(new String[0]));
		rutaVueloComboBox.setModel(modelNick);
		rutaVueloComboBox.repaint();
	}

	public void limpiarEntrada() {
		this.aerolineaComboBox.setSelectedItem("");
		this.rutaVueloComboBox.setSelectedItem("");
	}

	/*private boolean validarAerolinea(String aerolinea) {
		Set<String> aerolineasSet = iUsuario.obtenerAerolineas();
		return aerolineasSet.contains(aerolinea);
	}

	private boolean validarRutaVuelo(String rutaVuelo) {
		Set<String> rutaVueloSet = iRutaVuelo.obtenerRutasVuelo();
		return rutaVueloSet.contains(rutaVuelo);
	} */

	private void aceptar(EstadoRuta estado) {
		Fabrica.getInstance().getIRutaVuelo().aceptarORechazarRutaVuelo((String) rutaVueloComboBox.getSelectedItem(), estado);
		try {
			inicializarRutaVuelos((String) aerolineaComboBox.getSelectedItem());
		} catch (UsuarioNoExisteException e) {
			e.printStackTrace();
		}
	}

}
