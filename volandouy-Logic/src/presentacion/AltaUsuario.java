package presentacion;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.DateTimeException;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import excepciones.UsuarioRegistradoException;
import logica.fabrica.IUsuario;
import logica.enums.Pais;
import logica.enums.TipoDoc;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class AltaUsuario extends JInternalFrame {
    private IUsuario iUsuario;

    private GridBagLayout gridBagLayout;
    private JComboBox<String> comboTipoAlta;
    private JTextField nickField;
    private JTextField nombreField;
    private JTextField correoField;
    private JTextField apellidoField;
    private JComboBox<TipoDoc> comboTipoDoc;
    private JTextField nroDocField;
    private JTextField diaNacimientoField;
    private JTextField mesNacimientoField;
    private JTextField anioNacimientoField;
    private JComboBox<Pais> comboPais;
    private JTextArea descripcionArea;
    private JTextField webField;

    private JLabel lblApellido;
    private JLabel lblTipoDoc;
    private JLabel lblNroDoc;
    private JLabel lblFechaNac;
    private JLabel lblDateSeparator1;
    private JLabel lblDateSeparator2;
    private JLabel lblNacionalidad;
    private JScrollPane scrollPane;
    private JLabel lblDescripcion;
    private JLabel lblLinkDePag;

    private String tipoAltaActual;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    private JLabel lblImagenDePerfil;
    private JButton btnSeleccionarImagen;
    private JButton btnEliminarImagen;
    private JLabel lblImagenSeleccionada;
    private byte[] imagen = null;
    private JLabel lblFechaAlta;
    private JTextField txtDiaAlta;
    private JTextField txtMesAlta;
    private JTextField txtAnioAlta;
    private JLabel lblFechaAltaSeparador2;
    private JLabel lblFechaAltaSeparador1;
    private JLabel lblConfirmarPassword;
    private JPasswordField txtConfirmarPassword;

    public AltaUsuario(IUsuario interfazUsuario) {
    	this.iUsuario = interfazUsuario;
    	this.tipoAltaActual = "Cliente";

		// Propiedades del internalFrame
    	setTitle("Alta de Usuario");
    	setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setClosable(true);
    	setBounds(0, 0, 710, 485);

    	// WindowBuilder
    	gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{15, 190, 30, 0, 30, 0, 50, 0, 0, 120, 35, 0};
    	gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 80, 0, 30, 0, 30, 0};
    	gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    	gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    	getContentPane().setLayout(gridBagLayout);

    	JLabel lblTipoDeAlta = new JLabel("Tipo de alta:");
    	GridBagConstraints gbc_lblTipoDeAlta = new GridBagConstraints();
    	gbc_lblTipoDeAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_lblTipoDeAlta.anchor = GridBagConstraints.EAST;
    	gbc_lblTipoDeAlta.gridx = 1;
    	gbc_lblTipoDeAlta.gridy = 1;
    	getContentPane().add(lblTipoDeAlta, gbc_lblTipoDeAlta);

    	comboTipoAlta = new JComboBox<String>(new String[] {"Cliente", "Aerolínea"});
    	comboTipoAlta.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			tipoAltaActual((String) comboTipoAlta.getSelectedItem());
    			actualizarVentana();
    		}
    	});
    	GridBagConstraints gbc_comboTipoAlta = new GridBagConstraints();
    	gbc_comboTipoAlta.gridwidth = 8;
    	gbc_comboTipoAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_comboTipoAlta.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboTipoAlta.gridx = 2;
    	gbc_comboTipoAlta.gridy = 1;
    	getContentPane().add(comboTipoAlta, gbc_comboTipoAlta);

    	JLabel lblNickname = new JLabel("Nickname*:");
    	GridBagConstraints gbc_lblNickname = new GridBagConstraints();
    	gbc_lblNickname.anchor = GridBagConstraints.EAST;
    	gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNickname.gridx = 1;
    	gbc_lblNickname.gridy = 2;
    	getContentPane().add(lblNickname, gbc_lblNickname);

    	nickField = new JTextField();
    	GridBagConstraints gbc_nickField = new GridBagConstraints();
    	gbc_nickField.gridwidth = 8;
    	gbc_nickField.insets = new Insets(0, 0, 5, 5);
    	gbc_nickField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_nickField.gridx = 2;
    	gbc_nickField.gridy = 2;
    	getContentPane().add(nickField, gbc_nickField);
    	nickField.setColumns(10);

    	JLabel lblNombre = new JLabel("Nombre*:");
    	GridBagConstraints gbc_lblNombre = new GridBagConstraints();
    	gbc_lblNombre.anchor = GridBagConstraints.EAST;
    	gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombre.gridx = 1;
    	gbc_lblNombre.gridy = 3;
    	getContentPane().add(lblNombre, gbc_lblNombre);

    	nombreField = new JTextField();
    	GridBagConstraints gbc_nombreField = new GridBagConstraints();
    	gbc_nombreField.gridwidth = 8;
    	gbc_nombreField.insets = new Insets(0, 0, 5, 5);
    	gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_nombreField.gridx = 2;
    	gbc_nombreField.gridy = 3;
    	getContentPane().add(nombreField, gbc_nombreField);
    	nombreField.setColumns(10);

    	JLabel lblCorreo = new JLabel("Correo*:");
    	GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
    	gbc_lblCorreo.anchor = GridBagConstraints.EAST;
    	gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCorreo.gridx = 1;
    	gbc_lblCorreo.gridy = 4;
    	getContentPane().add(lblCorreo, gbc_lblCorreo);

    	correoField = new JTextField();
    	GridBagConstraints gbc_correoField = new GridBagConstraints();
    	gbc_correoField.gridwidth = 8;
    	gbc_correoField.insets = new Insets(0, 0, 5, 5);
    	gbc_correoField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_correoField.gridx = 2;
    	gbc_correoField.gridy = 4;
    	getContentPane().add(correoField, gbc_correoField);
    	correoField.setColumns(10);

    	lblPassword = new JLabel("Contraseña*:");
    	GridBagConstraints gbc_lblPassword = new GridBagConstraints();
    	gbc_lblPassword.anchor = GridBagConstraints.EAST;
    	gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
    	gbc_lblPassword.gridx = 1;
    	gbc_lblPassword.gridy = 5;
    	getContentPane().add(lblPassword, gbc_lblPassword);

    	txtPassword = new JPasswordField();
    	GridBagConstraints gbc_txtPassword = new GridBagConstraints();
    	gbc_txtPassword.gridwidth = 8;
    	gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
    	gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtPassword.gridx = 2;
    	gbc_txtPassword.gridy = 5;
    	getContentPane().add(txtPassword, gbc_txtPassword);

    	lblConfirmarPassword = new JLabel("Confirmar contraseña*:");
    	GridBagConstraints gbc_lblConfirmarPassword = new GridBagConstraints();
    	gbc_lblConfirmarPassword.anchor = GridBagConstraints.EAST;
    	gbc_lblConfirmarPassword.insets = new Insets(0, 0, 5, 5);
    	gbc_lblConfirmarPassword.gridx = 1;
    	gbc_lblConfirmarPassword.gridy = 6;
    	getContentPane().add(lblConfirmarPassword, gbc_lblConfirmarPassword);

    	txtConfirmarPassword = new JPasswordField();
    	GridBagConstraints gbc_txtConfirmarPassword = new GridBagConstraints();
    	gbc_txtConfirmarPassword.gridwidth = 8;
    	gbc_txtConfirmarPassword.insets = new Insets(0, 0, 5, 5);
    	gbc_txtConfirmarPassword.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtConfirmarPassword.gridx = 2;
    	gbc_txtConfirmarPassword.gridy = 6;
    	getContentPane().add(txtConfirmarPassword, gbc_txtConfirmarPassword);

    	lblImagenDePerfil = new JLabel("Imagen de perfil:");
    	GridBagConstraints gbc_lblImagenDePerfil = new GridBagConstraints();
    	gbc_lblImagenDePerfil.anchor = GridBagConstraints.EAST;
    	gbc_lblImagenDePerfil.insets = new Insets(0, 0, 5, 5);
    	gbc_lblImagenDePerfil.gridx = 1;
    	gbc_lblImagenDePerfil.gridy = 7;
    	getContentPane().add(lblImagenDePerfil, gbc_lblImagenDePerfil);

    	btnSeleccionarImagen = new JButton("Seleccionar imagen");
    	btnSeleccionarImagen.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			elegirImagen();
    		}
    	});

    	lblImagenSeleccionada = new JLabel("");
    	GridBagConstraints gbc_lblImagenSeleccionada = new GridBagConstraints();
    	gbc_lblImagenSeleccionada.gridwidth = 5;
    	gbc_lblImagenSeleccionada.insets = new Insets(0, 0, 5, 5);
    	gbc_lblImagenSeleccionada.gridx = 2;
    	gbc_lblImagenSeleccionada.gridy = 7;
    	getContentPane().add(lblImagenSeleccionada, gbc_lblImagenSeleccionada);
    	GridBagConstraints gbc_btnSeleccionarImagen = new GridBagConstraints();
    	gbc_btnSeleccionarImagen.insets = new Insets(0, 0, 5, 5);
    	gbc_btnSeleccionarImagen.gridx = 8;
    	gbc_btnSeleccionarImagen.gridy = 7;
    	getContentPane().add(btnSeleccionarImagen, gbc_btnSeleccionarImagen);

    	btnEliminarImagen = new JButton("Eliminar imagen");
    	btnEliminarImagen.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			imagen = null;
    			lblImagenSeleccionada.setText("");
    		}
    	});
    	GridBagConstraints gbc_btnEliminarImagen = new GridBagConstraints();
    	gbc_btnEliminarImagen.insets = new Insets(0, 0, 5, 5);
    	gbc_btnEliminarImagen.gridx = 9;
    	gbc_btnEliminarImagen.gridy = 7;
    	getContentPane().add(btnEliminarImagen, gbc_btnEliminarImagen);

    	lblFechaAlta = new JLabel("Fecha de alta*:");
    	GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
    	gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaAlta.gridx = 1;
    	gbc_lblFechaAlta.gridy = 8;
    	getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);

    	txtDiaAlta = new JTextField();
    	GridBagConstraints gbc_txtDiaAlta = new GridBagConstraints();
    	gbc_txtDiaAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_txtDiaAlta.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtDiaAlta.gridx = 2;
    	gbc_txtDiaAlta.gridy = 8;
    	getContentPane().add(txtDiaAlta, gbc_txtDiaAlta);
    	txtDiaAlta.setColumns(10);

    	lblFechaAltaSeparador1 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaAltaSeparador_1 = new GridBagConstraints();
    	gbc_lblFechaAltaSeparador_1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaAltaSeparador_1.gridx = 3;
    	gbc_lblFechaAltaSeparador_1.gridy = 8;
    	getContentPane().add(lblFechaAltaSeparador1, gbc_lblFechaAltaSeparador_1);

    	txtMesAlta = new JTextField();
    	GridBagConstraints gbc_txtMesAlta = new GridBagConstraints();
    	gbc_txtMesAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_txtMesAlta.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtMesAlta.gridx = 4;
    	gbc_txtMesAlta.gridy = 8;
    	getContentPane().add(txtMesAlta, gbc_txtMesAlta);
    	txtMesAlta.setColumns(10);

    	lblFechaAltaSeparador2 = new JLabel("/");
    	GridBagConstraints gbc_lblFechaAltaSeparador_2 = new GridBagConstraints();
    	gbc_lblFechaAltaSeparador_2.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaAltaSeparador_2.gridx = 5;
    	gbc_lblFechaAltaSeparador_2.gridy = 8;
    	getContentPane().add(lblFechaAltaSeparador2, gbc_lblFechaAltaSeparador_2);

    	txtAnioAlta = new JTextField();
    	GridBagConstraints gbc_txtAnioAlta = new GridBagConstraints();
    	gbc_txtAnioAlta.insets = new Insets(0, 0, 5, 5);
    	gbc_txtAnioAlta.fill = GridBagConstraints.HORIZONTAL;
    	gbc_txtAnioAlta.gridx = 6;
    	gbc_txtAnioAlta.gridy = 8;
    	getContentPane().add(txtAnioAlta, gbc_txtAnioAlta);
    	txtAnioAlta.setColumns(10);

    	lblApellido = new JLabel("Apellido*:");
    	GridBagConstraints gbc_lblApellido = new GridBagConstraints();
    	gbc_lblApellido.anchor = GridBagConstraints.EAST;
    	gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
    	gbc_lblApellido.gridx = 1;
    	gbc_lblApellido.gridy = 9;
    	getContentPane().add(lblApellido, gbc_lblApellido);

    	apellidoField = new JTextField();
    	GridBagConstraints gbc_apellidoField = new GridBagConstraints();
    	gbc_apellidoField.gridwidth = 8;
    	gbc_apellidoField.insets = new Insets(0, 0, 5, 5);
    	gbc_apellidoField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_apellidoField.gridx = 2;
    	gbc_apellidoField.gridy = 9;
    	getContentPane().add(apellidoField, gbc_apellidoField);
    	apellidoField.setColumns(10);

    	lblTipoDoc = new JLabel("Tipo de documento:");
    	GridBagConstraints gbc_lblTipoDoc = new GridBagConstraints();
    	gbc_lblTipoDoc.anchor = GridBagConstraints.EAST;
    	gbc_lblTipoDoc.insets = new Insets(0, 0, 5, 5);
    	gbc_lblTipoDoc.gridx = 1;
    	gbc_lblTipoDoc.gridy = 10;
    	getContentPane().add(lblTipoDoc, gbc_lblTipoDoc);

    	comboTipoDoc = new JComboBox<TipoDoc>(TipoDoc.values());
    	GridBagConstraints gbc_comboTipoDoc = new GridBagConstraints();
    	gbc_comboTipoDoc.gridwidth = 8;
    	gbc_comboTipoDoc.insets = new Insets(0, 0, 5, 5);
    	gbc_comboTipoDoc.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboTipoDoc.gridx = 2;
    	gbc_comboTipoDoc.gridy = 10;
    	getContentPane().add(comboTipoDoc, gbc_comboTipoDoc);

    	lblNroDoc = new JLabel("Número de documento*:");
    	GridBagConstraints gbc_lblNroDoc = new GridBagConstraints();
    	gbc_lblNroDoc.anchor = GridBagConstraints.EAST;
    	gbc_lblNroDoc.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNroDoc.gridx = 1;
    	gbc_lblNroDoc.gridy = 11;
    	getContentPane().add(lblNroDoc, gbc_lblNroDoc);

    	nroDocField = new JTextField();
    	GridBagConstraints gbc_nroDocField = new GridBagConstraints();
    	gbc_nroDocField.gridwidth = 8;
    	gbc_nroDocField.insets = new Insets(0, 0, 5, 5);
    	gbc_nroDocField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_nroDocField.gridx = 2;
    	gbc_nroDocField.gridy = 11;
    	getContentPane().add(nroDocField, gbc_nroDocField);
    	nroDocField.setColumns(10);

    	lblFechaNac = new JLabel("Fecha de nacimiento*:");
    	GridBagConstraints gbc_lblFechaNac = new GridBagConstraints();
    	gbc_lblFechaNac.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaNac.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaNac.gridx = 1;
    	gbc_lblFechaNac.gridy = 12;
    	getContentPane().add(lblFechaNac, gbc_lblFechaNac);

    	diaNacimientoField = new JTextField();
    	GridBagConstraints gbc_diaNacimientoField = new GridBagConstraints();
    	gbc_diaNacimientoField.insets = new Insets(0, 0, 5, 5);
    	gbc_diaNacimientoField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_diaNacimientoField.gridx = 2;
    	gbc_diaNacimientoField.gridy = 12;
    	getContentPane().add(diaNacimientoField, gbc_diaNacimientoField);
    	diaNacimientoField.setColumns(10);

    	lblDateSeparator1 = new JLabel("/");
    	GridBagConstraints gbc_lblDateSeparator_1 = new GridBagConstraints();
    	gbc_lblDateSeparator_1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDateSeparator_1.gridx = 3;
    	gbc_lblDateSeparator_1.gridy = 12;
    	getContentPane().add(lblDateSeparator1, gbc_lblDateSeparator_1);

    	mesNacimientoField = new JTextField();
    	GridBagConstraints gbc_mesNacimientoField = new GridBagConstraints();
    	gbc_mesNacimientoField.insets = new Insets(0, 0, 5, 5);
    	gbc_mesNacimientoField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_mesNacimientoField.gridx = 4;
    	gbc_mesNacimientoField.gridy = 12;
    	getContentPane().add(mesNacimientoField, gbc_mesNacimientoField);
    	mesNacimientoField.setColumns(10);

    	lblDateSeparator2 = new JLabel("/");
    	GridBagConstraints gbc_lblDateSeparator_2 = new GridBagConstraints();
    	gbc_lblDateSeparator_2.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDateSeparator_2.gridx = 5;
    	gbc_lblDateSeparator_2.gridy = 12;
    	getContentPane().add(lblDateSeparator2, gbc_lblDateSeparator_2);

    	anioNacimientoField = new JTextField();
    	GridBagConstraints gbc_anioNacimientoField = new GridBagConstraints();
    	gbc_anioNacimientoField.insets = new Insets(0, 0, 5, 5);
    	gbc_anioNacimientoField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_anioNacimientoField.gridx = 6;
    	gbc_anioNacimientoField.gridy = 12;
    	getContentPane().add(anioNacimientoField, gbc_anioNacimientoField);
    	anioNacimientoField.setColumns(10);

    	lblNacionalidad = new JLabel("Nacionalidad*:");
    	GridBagConstraints gbc_lblNacionalidad = new GridBagConstraints();
    	gbc_lblNacionalidad.anchor = GridBagConstraints.EAST;
    	gbc_lblNacionalidad.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNacionalidad.gridx = 1;
    	gbc_lblNacionalidad.gridy = 13;
    	getContentPane().add(lblNacionalidad, gbc_lblNacionalidad);

    	comboPais = new JComboBox<Pais>(Pais.values());
    	GridBagConstraints gbc_comboPais = new GridBagConstraints();
    	gbc_comboPais.gridwidth = 8;
    	gbc_comboPais.insets = new Insets(0, 0, 5, 5);
    	gbc_comboPais.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboPais.gridx = 2;
    	gbc_comboPais.gridy = 13;
    	getContentPane().add(comboPais, gbc_comboPais);

    	lblDescripcion = new JLabel("Descripción*:");
    	GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
    	gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
    	gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDescripcion.gridx = 1;
    	gbc_lblDescripcion.gridy = 14;
    	getContentPane().add(lblDescripcion, gbc_lblDescripcion);

    	scrollPane = new JScrollPane();
    	GridBagConstraints gbc_scrollPane = new GridBagConstraints();
    	gbc_scrollPane.gridwidth = 8;
    	gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollPane.fill = GridBagConstraints.BOTH;
    	gbc_scrollPane.gridx = 2;
    	gbc_scrollPane.gridy = 14;
    	getContentPane().add(scrollPane, gbc_scrollPane);

    	descripcionArea = new JTextArea();
    	scrollPane.setViewportView(descripcionArea);

    	lblLinkDePag = new JLabel("Link de pág. web:");
    	GridBagConstraints gbc_lblLinkDePag = new GridBagConstraints();
    	gbc_lblLinkDePag.anchor = GridBagConstraints.EAST;
    	gbc_lblLinkDePag.insets = new Insets(0, 0, 5, 5);
    	gbc_lblLinkDePag.gridx = 1;
    	gbc_lblLinkDePag.gridy = 15;
    	getContentPane().add(lblLinkDePag, gbc_lblLinkDePag);

    	webField = new JTextField();
    	GridBagConstraints gbc_webField = new GridBagConstraints();
    	gbc_webField.gridwidth = 8;
    	gbc_webField.insets = new Insets(0, 0, 5, 5);
    	gbc_webField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_webField.gridx = 2;
    	gbc_webField.gridy = 15;
    	getContentPane().add(webField, gbc_webField);
    	webField.setColumns(10);

    	JButton btnAceptar = new JButton("Aceptar");
    	btnAceptar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			altaUsuario();
    		}
    	});
    	GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
    	gbc_btnAceptar.anchor = GridBagConstraints.EAST;
    	gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnAceptar.gridx = 8;
    	gbc_btnAceptar.gridy = 17;
    	getContentPane().add(btnAceptar, gbc_btnAceptar);

    	JButton btnCancelar = new JButton("Cancelar");
    	btnCancelar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			limpiarEntrada();
    			setVisible(false);
    		}
    	});
    	GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
    	gbc_btnCancelar.anchor = GridBagConstraints.EAST;
    	gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnCancelar.gridx = 9;
    	gbc_btnCancelar.gridy = 17;
    	getContentPane().add(btnCancelar, gbc_btnCancelar);

    }

    private void altaUsuario() {
        String nickname = nickField.getText();
        String nombre = nombreField.getText();
        String correo = correoField.getText();
        String password = new String(this.txtPassword.getPassword());
        String confirmarPassword = new String(this.txtConfirmarPassword.getPassword());
        String diaAlta = this.txtDiaAlta.getText();
        String mesAlta = this.txtMesAlta.getText();
        String anioAlta = this.txtAnioAlta.getText();
        LocalDate fechaAlta = null;
        String apellido = apellidoField.getText();
        TipoDoc tipoDoc = (TipoDoc) comboTipoDoc.getSelectedItem();
        String nroDoc = nroDocField.getText();
        Pais nacionalidad = (Pais) comboPais.getSelectedItem();
        String diaNacimiento = diaNacimientoField.getText();
        String mesNacimiento = mesNacimientoField.getText();
        String anioNacimiento = anioNacimientoField.getText();
		LocalDate fechaNac = null;
        String descripcion = descripcionArea.getText();
        String web = webField.getText();

        if (!validarCampos(nickname, nombre, correo, password, confirmarPassword, diaAlta, mesAlta, anioAlta, apellido, diaNacimiento, mesNacimiento, anioNacimiento, nroDoc, descripcion)) {
        	JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        if (!password.equals(confirmarPassword)) {
			JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
			return;
        }

        try {
        	fechaAlta = LocalDate.of(Integer.valueOf(anioAlta), Integer.valueOf(mesAlta), Integer.valueOf(diaAlta));
        } catch (DateTimeException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ingrese una fecha de alta válida", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
			return;
        }

        if (this.tipoAltaActual == "Cliente") {
			try {
				fechaNac = LocalDate.of(Integer.valueOf(anioNacimiento), Integer.valueOf(mesNacimiento), Integer.valueOf(diaNacimiento));
			} catch (DateTimeException | NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Ingrese una fecha de nacimiento válida", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (fechaNac.isBefore(LocalDate.now().minusYears(120))) {
				JOptionPane.showMessageDialog(this, "Usted debe tener menos de 120 años", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (fechaNac.isAfter(LocalDate.now().minusYears(18))) {
				JOptionPane.showMessageDialog(this, "Usted debe ser mayor de edad", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
				return;
			}
        }

        if (!correo.contains("@")) {
        	JOptionPane.showMessageDialog(this, "Debe ingresar un correo válido", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
        	return;
        }

		try {
			switch (this.tipoAltaActual) {
				case "Cliente":
					this.iUsuario.ingresarDatosCliente(nickname, nombre, correo, password, this.imagen, fechaAlta, apellido, fechaNac, tipoDoc, nroDoc, nacionalidad);
					break;
				case "Aerolínea":
					this.iUsuario.ingresarDatosAerolinea(nickname, nombre, correo, password, this.imagen, fechaAlta, descripcion, web);
					break;
			}

			JOptionPane.showMessageDialog(this, "El usuario se ha creado exitosamente", "Alta de usuario", JOptionPane.INFORMATION_MESSAGE);
			this.limpiarEntrada();
		} catch (UsuarioRegistradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de usuario", JOptionPane.ERROR_MESSAGE);
		}
    }

    private boolean validarCampos(String nickname, String nombre, String correo, String password, String confirmarPassword, String diaAlta, String mesAlta, String anioAlta, String apellido, String dia, String mes, String anio, String nroDoc, String descripcion) {
        if (nickname.isEmpty() || nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty() || diaAlta.isEmpty() || mesAlta.isEmpty() || anioAlta.isEmpty()) {
        	return false;
        } else if (this.tipoAltaActual == "Cliente" && (apellido.isEmpty() || dia.isEmpty() || mes.isEmpty() || anio.isEmpty() || nroDoc.isEmpty())) {
            return false;
        } else if (this.tipoAltaActual == "Aerolínea" && descripcion.isEmpty()) {
        	return false;
        }
        return true;
    }

    public void tipoAltaActual(String tipoUsuario) {
    	this.tipoAltaActual = tipoUsuario;
    }

    public void actualizarVentana() {
    	switch (this.tipoAltaActual) {
			case "Cliente":
				this.lblApellido.setVisible(true);
				this.apellidoField.setVisible(true);
				this.lblTipoDoc.setVisible(true);
				this.comboTipoDoc.setVisible(true);
				this.lblNroDoc.setVisible(true);
				this.nroDocField.setVisible(true);
				this.lblFechaNac.setVisible(true);
				this.diaNacimientoField.setVisible(true);
				this.lblDateSeparator1.setVisible(true);
				this.mesNacimientoField.setVisible(true);
				this.lblDateSeparator2.setVisible(true);
				this.anioNacimientoField.setVisible(true);
				this.lblNacionalidad.setVisible(true);
				this.comboPais.setVisible(true);

				this.gridBagLayout.rowHeights[14] = 0;
				this.scrollPane.setVisible(false);
				this.lblDescripcion.setVisible(false);
				this.descripcionArea.setVisible(false);
				this.lblLinkDePag.setVisible(false);
				this.webField.setVisible(false);

				this.gridBagLayout.rowHeights[16] = 30; // Para estilizar la ventana. Botones quedan a la misma altura

				break;

			case "Aerolínea":
				this.lblApellido.setVisible(false);
				this.apellidoField.setVisible(false);
				this.lblTipoDoc.setVisible(false);
				this.comboTipoDoc.setVisible(false);
				this.lblNroDoc.setVisible(false);
				this.nroDocField.setVisible(false);
				this.lblFechaNac.setVisible(false);
				this.diaNacimientoField.setVisible(false);
				this.lblDateSeparator1.setVisible(false);
				this.mesNacimientoField.setVisible(false);
				this.lblDateSeparator2.setVisible(false);
				this.anioNacimientoField.setVisible(false);
				this.lblNacionalidad.setVisible(false);
				this.comboPais.setVisible(false);

				this.gridBagLayout.rowHeights[14] = 80;
				this.scrollPane.setVisible(true);
				this.lblDescripcion.setVisible(true);
				this.descripcionArea.setVisible(true);
				this.lblLinkDePag.setVisible(true);
				this.webField.setVisible(true);

				this.gridBagLayout.rowHeights[16] = 57; // Para estilizar la ventana. Botones quedan a la misma altura

				break;

			default:
				JOptionPane.showMessageDialog(this, "Algo anduvo mal...");
				break;
    	}
    }

    public void inicializarCombos() {
        this.comboTipoAlta.addItem("Cliente");
        this.comboTipoAlta.addItem("Aerolínea");
    }

    public void limpiarEntrada() {
    	this.nickField.setText("");
    	this.nombreField.setText("");
    	this.correoField.setText("");
    	this.txtPassword.setText("");
    	this.txtConfirmarPassword.setText("");
    	this.imagen = null;
    	this.txtDiaAlta.setText("");
    	this.txtMesAlta.setText("");
    	this.txtAnioAlta.setText("");
    	this.lblImagenSeleccionada.setText("");
    	this.apellidoField.setText("");
    	this.nroDocField.setText("");
    	this.diaNacimientoField.setText("");
    	this.mesNacimientoField.setText("");
    	this.anioNacimientoField.setText("");
    	this.descripcionArea.setText("");
    	this.webField.setText("");
    }

    private void elegirImagen() {
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Elige una imagen");
    	fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png"));

    	int returnValue = fileChooser.showOpenDialog(this);
    	if (returnValue == JFileChooser.APPROVE_OPTION) {
    		File selectedFile = fileChooser.getSelectedFile();
    		this.lblImagenSeleccionada.setText(selectedFile.getName());

    		try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
				this.imagen = new byte[(int) selectedFile.length()];
				inputStream.read(this.imagen);
    		} catch (IOException e) {
    			JOptionPane.showMessageDialog(this, "Error al leer la foto de perfil", "Alta de usuario", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }

}
