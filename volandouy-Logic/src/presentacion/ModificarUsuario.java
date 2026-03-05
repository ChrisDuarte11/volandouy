package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import datatypes.DTAerolinea;
import datatypes.DTCliente;
import datatypes.DTUsuario;
import excepciones.UsuarioNoExisteException;
import logica.enums.Pais;
import logica.enums.TipoDoc;
import logica.fabrica.IUsuario;

@SuppressWarnings("serial")
public class ModificarUsuario extends JInternalFrame {
	private IUsuario iUsuario;
	private Boolean ignorarEventoSeleccion = false;

	private JTextField nickField;
	private JTextField nombreField;
	private JTextField correoField;
	private JTextField apellidoField;
	private JTextField nroDocField;
	private JTextField diaField;
	private JTextField mesField;
	private JTextField anioField;
	private JTextField webField;
	private JList<String> listUsuarios;
	private JComboBox<TipoDoc> comboTipoDoc;
	private JComboBox<Pais> comboPais;
	private JTextArea descripcionArea;
	private JLabel lblApellido;
	private JLabel lblTipoDoc;
	private JLabel lblNroDoc;
	private JLabel lblFechaNac;
	private JLabel lblDateSeparator1;
	private JLabel lblDateSeparator2;
	private JLabel lblNacionalidad;
	private JLabel lblDescripcion;
	private JLabel lblLinkWeb;
	private JScrollPane scrollDescripcion;
	private GridBagLayout gblpanelEditarUsuario;

	public ModificarUsuario(IUsuario interfazUsuario) {
		this.iUsuario = interfazUsuario;

		setTitle("Modificar datos de usuario");
		setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    	setClosable(true);
    	setBounds(0, 0, 800, 460);

    	// WindowBuilder
    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{200, 585, 0};
    	gridBagLayout.rowHeights = new int[]{370, 0};
    	gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
    	gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    	getContentPane().setLayout(gridBagLayout);

    	JPanel panelListaUsuarios = new JPanel();
    	GridBagConstraints gbc_panelListaUsuarios = new GridBagConstraints();
    	gbc_panelListaUsuarios.insets = new Insets(0, 0, 0, 5);
    	gbc_panelListaUsuarios.fill = GridBagConstraints.BOTH;
    	gbc_panelListaUsuarios.gridx = 0;
    	gbc_panelListaUsuarios.gridy = 0;
    	getContentPane().add(panelListaUsuarios, gbc_panelListaUsuarios);
    	panelListaUsuarios.setBorder(BorderFactory.createTitledBorder("Usuarios registrados"));
    	GridBagLayout gbl_panelListaUsuarios = new GridBagLayout();
    	gbl_panelListaUsuarios.columnWidths = new int[]{110, 0};
    	gbl_panelListaUsuarios.rowHeights = new int[]{1, 0};
    	gbl_panelListaUsuarios.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    	gbl_panelListaUsuarios.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    	panelListaUsuarios.setLayout(gbl_panelListaUsuarios);

    	JScrollPane scrollListUsuarios = new JScrollPane();
    	GridBagConstraints gbc_scrollListUsuarios = new GridBagConstraints();
    	gbc_scrollListUsuarios.fill = GridBagConstraints.BOTH;
    	gbc_scrollListUsuarios.gridx = 0;
    	gbc_scrollListUsuarios.gridy = 0;
    	panelListaUsuarios.add(scrollListUsuarios, gbc_scrollListUsuarios);

    	listUsuarios = new JList<String>();
    	listUsuarios.addListSelectionListener(new ListSelectionListener() {
    		@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
    			if (!e.getValueIsAdjusting() && !ignorarEventoSeleccion) {
    				JList<String> source = (JList<String>) e.getSource();
    				String nick = source.getSelectedValue().toString();
    				actualizarDatosUsuario(nick);
    			}
    		}
    	});
    	listUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	scrollListUsuarios.setViewportView(listUsuarios);

    	JPanel panelEditarUsuario = new JPanel();
    	GridBagConstraints gbc_panelEditarUsuario = new GridBagConstraints();
    	gbc_panelEditarUsuario.fill = GridBagConstraints.BOTH;
    	gbc_panelEditarUsuario.gridx = 1;
    	gbc_panelEditarUsuario.gridy = 0;
    	getContentPane().add(panelEditarUsuario, gbc_panelEditarUsuario);
    	panelEditarUsuario.setBorder(BorderFactory.createTitledBorder("Editar usuario"));
    	gblpanelEditarUsuario = new GridBagLayout();
    	gblpanelEditarUsuario.columnWidths = new int[]{15, 190, 30, 0, 30, 0, 50, 0, 0, 120, 35};
    	gblpanelEditarUsuario.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 80, 0, 30, 0, 30};
    	gblpanelEditarUsuario.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
    	gblpanelEditarUsuario.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    	panelEditarUsuario.setLayout(gblpanelEditarUsuario);

    	JLabel lblNickname = new JLabel("Nickname:");
    	GridBagConstraints gbc_lblNickname = new GridBagConstraints();
    	gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNickname.anchor = GridBagConstraints.EAST;
    	gbc_lblNickname.gridx = 1;
    	gbc_lblNickname.gridy = 1;
    	panelEditarUsuario.add(lblNickname, gbc_lblNickname);

    	nickField = new JTextField();
    	nickField.setEditable(false);
    	GridBagConstraints gbc_nickField = new GridBagConstraints();
    	gbc_nickField.gridwidth = 8;
    	gbc_nickField.insets = new Insets(0, 0, 5, 5);
    	gbc_nickField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_nickField.gridx = 2;
    	gbc_nickField.gridy = 1;
    	panelEditarUsuario.add(nickField, gbc_nickField);
    	nickField.setColumns(10);

    	JLabel lblNombre = new JLabel("Nombre:");
    	GridBagConstraints gbc_lblNombre = new GridBagConstraints();
    	gbc_lblNombre.anchor = GridBagConstraints.EAST;
    	gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNombre.gridx = 1;
    	gbc_lblNombre.gridy = 2;
    	panelEditarUsuario.add(lblNombre, gbc_lblNombre);

    	nombreField = new JTextField();
    	GridBagConstraints gbc_nombreField = new GridBagConstraints();
    	gbc_nombreField.gridwidth = 8;
    	gbc_nombreField.insets = new Insets(0, 0, 5, 5);
    	gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_nombreField.gridx = 2;
    	gbc_nombreField.gridy = 2;
    	panelEditarUsuario.add(nombreField, gbc_nombreField);
    	nombreField.setColumns(10);

    	JLabel lblCorreo = new JLabel("Correo:");
    	GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
    	gbc_lblCorreo.anchor = GridBagConstraints.EAST;
    	gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
    	gbc_lblCorreo.gridx = 1;
    	gbc_lblCorreo.gridy = 3;
    	panelEditarUsuario.add(lblCorreo, gbc_lblCorreo);

    	correoField = new JTextField();
    	correoField.setEditable(false);
    	GridBagConstraints gbc_correoField = new GridBagConstraints();
    	gbc_correoField.gridwidth = 8;
    	gbc_correoField.insets = new Insets(0, 0, 5, 5);
    	gbc_correoField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_correoField.gridx = 2;
    	gbc_correoField.gridy = 3;
    	panelEditarUsuario.add(correoField, gbc_correoField);
    	correoField.setColumns(10);

    	lblApellido = new JLabel("Apellido:");
    	GridBagConstraints gbc_lblApellido = new GridBagConstraints();
    	gbc_lblApellido.anchor = GridBagConstraints.EAST;
    	gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
    	gbc_lblApellido.gridx = 1;
    	gbc_lblApellido.gridy = 4;
    	panelEditarUsuario.add(lblApellido, gbc_lblApellido);

    	apellidoField = new JTextField();
    	GridBagConstraints gbc_apellidoField = new GridBagConstraints();
    	gbc_apellidoField.gridwidth = 8;
    	gbc_apellidoField.insets = new Insets(0, 0, 5, 5);
    	gbc_apellidoField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_apellidoField.gridx = 2;
    	gbc_apellidoField.gridy = 4;
    	panelEditarUsuario.add(apellidoField, gbc_apellidoField);
    	apellidoField.setColumns(10);

    	lblTipoDoc = new JLabel("Tipo de documento:");
    	GridBagConstraints gbc_lblTipoDoc = new GridBagConstraints();
    	gbc_lblTipoDoc.anchor = GridBagConstraints.EAST;
    	gbc_lblTipoDoc.insets = new Insets(0, 0, 5, 5);
    	gbc_lblTipoDoc.gridx = 1;
    	gbc_lblTipoDoc.gridy = 5;
    	panelEditarUsuario.add(lblTipoDoc, gbc_lblTipoDoc);

    	comboTipoDoc = new JComboBox<TipoDoc>(TipoDoc.values());
    	GridBagConstraints gbc_comboTipoDoc = new GridBagConstraints();
    	gbc_comboTipoDoc.gridwidth = 8;
    	gbc_comboTipoDoc.insets = new Insets(0, 0, 5, 5);
    	gbc_comboTipoDoc.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboTipoDoc.gridx = 2;
    	gbc_comboTipoDoc.gridy = 5;
    	panelEditarUsuario.add(comboTipoDoc, gbc_comboTipoDoc);

    	lblNroDoc = new JLabel("Número de documento:");
    	GridBagConstraints gbc_lblNroDoc = new GridBagConstraints();
    	gbc_lblNroDoc.anchor = GridBagConstraints.EAST;
    	gbc_lblNroDoc.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNroDoc.gridx = 1;
    	gbc_lblNroDoc.gridy = 6;
    	panelEditarUsuario.add(lblNroDoc, gbc_lblNroDoc);

    	nroDocField = new JTextField();
    	GridBagConstraints gbc_nroDocField = new GridBagConstraints();
    	gbc_nroDocField.gridwidth = 8;
    	gbc_nroDocField.insets = new Insets(0, 0, 5, 5);
    	gbc_nroDocField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_nroDocField.gridx = 2;
    	gbc_nroDocField.gridy = 6;
    	panelEditarUsuario.add(nroDocField, gbc_nroDocField);
    	nroDocField.setColumns(10);

    	lblFechaNac = new JLabel("Fecha de nacimiento:");
    	GridBagConstraints gbc_lblFechaNac = new GridBagConstraints();
    	gbc_lblFechaNac.anchor = GridBagConstraints.EAST;
    	gbc_lblFechaNac.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFechaNac.gridx = 1;
    	gbc_lblFechaNac.gridy = 7;
    	panelEditarUsuario.add(lblFechaNac, gbc_lblFechaNac);

    	diaField = new JTextField();
    	GridBagConstraints gbc_diaField = new GridBagConstraints();
    	gbc_diaField.insets = new Insets(0, 0, 5, 5);
    	gbc_diaField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_diaField.gridx = 2;
    	gbc_diaField.gridy = 7;
    	panelEditarUsuario.add(diaField, gbc_diaField);
    	diaField.setColumns(10);

    	lblDateSeparator1 = new JLabel("/");
    	GridBagConstraints gbc_lblDateSeparator_1 = new GridBagConstraints();
    	gbc_lblDateSeparator_1.anchor = GridBagConstraints.EAST;
    	gbc_lblDateSeparator_1.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDateSeparator_1.gridx = 3;
    	gbc_lblDateSeparator_1.gridy = 7;
    	panelEditarUsuario.add(lblDateSeparator1, gbc_lblDateSeparator_1);

    	mesField = new JTextField();
    	GridBagConstraints gbc_mesField = new GridBagConstraints();
    	gbc_mesField.insets = new Insets(0, 0, 5, 5);
    	gbc_mesField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_mesField.gridx = 4;
    	gbc_mesField.gridy = 7;
    	panelEditarUsuario.add(mesField, gbc_mesField);
    	mesField.setColumns(10);

    	lblDateSeparator2 = new JLabel("/");
    	GridBagConstraints gbc_lblDateSeparator_2 = new GridBagConstraints();
    	gbc_lblDateSeparator_2.anchor = GridBagConstraints.EAST;
    	gbc_lblDateSeparator_2.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDateSeparator_2.gridx = 5;
    	gbc_lblDateSeparator_2.gridy = 7;
    	panelEditarUsuario.add(lblDateSeparator2, gbc_lblDateSeparator_2);

    	anioField = new JTextField();
    	GridBagConstraints gbc_anioField = new GridBagConstraints();
    	gbc_anioField.insets = new Insets(0, 0, 5, 5);
    	gbc_anioField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_anioField.gridx = 6;
    	gbc_anioField.gridy = 7;
    	panelEditarUsuario.add(anioField, gbc_anioField);
    	anioField.setColumns(10);

    	lblNacionalidad = new JLabel("Nacionalidad:");
    	GridBagConstraints gbc_lblNacionalidad = new GridBagConstraints();
    	gbc_lblNacionalidad.anchor = GridBagConstraints.EAST;
    	gbc_lblNacionalidad.insets = new Insets(0, 0, 5, 5);
    	gbc_lblNacionalidad.gridx = 1;
    	gbc_lblNacionalidad.gridy = 8;
    	panelEditarUsuario.add(lblNacionalidad, gbc_lblNacionalidad);

    	comboPais = new JComboBox<Pais>(Pais.values());
    	GridBagConstraints gbc_comboPais = new GridBagConstraints();
    	gbc_comboPais.gridwidth = 8;
    	gbc_comboPais.insets = new Insets(0, 0, 5, 5);
    	gbc_comboPais.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboPais.gridx = 2;
    	gbc_comboPais.gridy = 8;
    	panelEditarUsuario.add(comboPais, gbc_comboPais);

    	lblDescripcion = new JLabel("Descripción:");
    	GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
    	gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
    	gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_lblDescripcion.gridx = 1;
    	gbc_lblDescripcion.gridy = 9;
    	panelEditarUsuario.add(lblDescripcion, gbc_lblDescripcion);

    	scrollDescripcion = new JScrollPane();
    	GridBagConstraints gbc_scrollDescripcion = new GridBagConstraints();
    	gbc_scrollDescripcion.gridwidth = 8;
    	gbc_scrollDescripcion.insets = new Insets(0, 0, 5, 5);
    	gbc_scrollDescripcion.fill = GridBagConstraints.BOTH;
    	gbc_scrollDescripcion.gridx = 2;
    	gbc_scrollDescripcion.gridy = 9;
    	panelEditarUsuario.add(scrollDescripcion, gbc_scrollDescripcion);

    	descripcionArea = new JTextArea();
    	scrollDescripcion.setViewportView(descripcionArea);

    	lblLinkWeb = new JLabel("Link a pág. web:");
    	GridBagConstraints gbc_lblLinkWeb = new GridBagConstraints();
    	gbc_lblLinkWeb.anchor = GridBagConstraints.EAST;
    	gbc_lblLinkWeb.insets = new Insets(0, 0, 5, 5);
    	gbc_lblLinkWeb.gridx = 1;
    	gbc_lblLinkWeb.gridy = 10;
    	panelEditarUsuario.add(lblLinkWeb, gbc_lblLinkWeb);

    	webField = new JTextField();
    	GridBagConstraints gbc_webField = new GridBagConstraints();
    	gbc_webField.gridwidth = 8;
    	gbc_webField.insets = new Insets(0, 0, 5, 5);
    	gbc_webField.fill = GridBagConstraints.HORIZONTAL;
    	gbc_webField.gridx = 2;
    	gbc_webField.gridy = 10;
    	panelEditarUsuario.add(webField, gbc_webField);
    	webField.setColumns(10);

    	JButton btnAceptar = new JButton("Aceptar");
    	btnAceptar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			modificarUsuario();
    		}
    	});
    	GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
    	gbc_btnAceptar.anchor = GridBagConstraints.EAST;
    	gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnAceptar.gridx = 8;
    	gbc_btnAceptar.gridy = 12;
    	panelEditarUsuario.add(btnAceptar, gbc_btnAceptar);

    	JButton btnCancelar = new JButton("Cancelar");
    	btnCancelar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			limpiarEntrada();
    			setVisible(false);
    		}
    	});
    	GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
    	gbc_btnCancelar.anchor = GridBagConstraints.EAST;
    	gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
    	gbc_btnCancelar.gridx = 9;
    	gbc_btnCancelar.gridy = 12;
    	panelEditarUsuario.add(btnCancelar, gbc_btnCancelar);


	}

	private void modificarUsuario() {
		String nickname = nickField.getText();
        String nombre = nombreField.getText();
        String correo = correoField.getText();
        String apellido = apellidoField.getText();
        TipoDoc tipoDoc = (TipoDoc) comboTipoDoc.getSelectedItem();
        String nroDoc = nroDocField.getText();
        Pais nacionalidad = (Pais) comboPais.getSelectedItem();
        String dia = diaField.getText();
        String mes = mesField.getText();
        String anio = anioField.getText();
		LocalDate fechaNac = null;
        String descripcion = descripcionArea.getText();
        String web = webField.getText();

        if (this.listUsuarios.getModel().getSize() == 0) {
        	JOptionPane.showMessageDialog(this, "No existen usuario registrados", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        DTUsuario dtUsuario = null;
        try {
			dtUsuario = iUsuario.obtenerUsuario(nickname);
		} catch (UsuarioNoExisteException e) {
        	JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario a modificar", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
        	return;
		}

        if (!validarCampos(dtUsuario, nombre, apellido, dia, mes, anio, nroDoc, descripcion)) {
        	JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        if (!correo.contains("@")) {
        	JOptionPane.showMessageDialog(this, "Debe ingresar un correo válido", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        if (dtUsuario instanceof DTCliente) {
			try {
				fechaNac = LocalDate.of(Integer.valueOf(anio), Integer.valueOf(mes), Integer.valueOf(dia));
			} catch (DateTimeException | NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Ingrese una fecha válida", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (fechaNac.isBefore(LocalDate.now().minusYears(120))) {
				JOptionPane.showMessageDialog(this, "Usted debe tener menos de 120 años", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (fechaNac.isAfter(LocalDate.now().minusYears(18))) {
				JOptionPane.showMessageDialog(this, "Usted debe ser mayor de edad", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
				return;
			}

			DTCliente modificado = new DTCliente(nickname, nombre, correo, null, null, null, apellido, fechaNac, tipoDoc, nacionalidad, nroDoc);

			if (modificado.equals((DTCliente) dtUsuario)) {
				JOptionPane.showMessageDialog(this, "Debe modificar algún atributo", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				this.iUsuario.editarUsuario(new DTCliente(nickname, nombre, correo, null, null, null, apellido, fechaNac, tipoDoc, nacionalidad, nroDoc));
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Modificar Usuario", JOptionPane.ERROR_MESSAGE);
			} catch (UsuarioNoExisteException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Modificar Usuario", JOptionPane.ERROR_MESSAGE);
			}
        } else { // dtUsuario instanceof DTAerolinea
        	DTAerolinea modificado = new DTAerolinea(nickname, nombre, correo, null, null, null, descripcion, web);

        	if (modificado.equals((DTAerolinea) dtUsuario)) {
				JOptionPane.showMessageDialog(this, "Debe modificar algún atributo", "Modificar usuario", JOptionPane.ERROR_MESSAGE);
				return;
        	}

        	try {
				this.iUsuario.editarUsuario(new DTAerolinea(nickname, nombre, correo, null, null, null, descripcion, web));
			} catch (UsuarioNoExisteException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Modificar Usuario", JOptionPane.ERROR_MESSAGE);
			}
        }

		JOptionPane.showMessageDialog(this, "El usuario se ha modificado exitosamente", "Modificar usuario", JOptionPane.INFORMATION_MESSAGE);
		this.limpiarEntrada();

//		try {
//			switch (this.tipoAltaActual) {
//				case ("Cliente"):
//					this.iUsuario.ingresarDatosCliente(nickname, nombre, correo, apellido, fechaNac, tipoDoc, Integer.parseInt(nroDoc), nacionalidad);
//					break;
//				case ("Aerolínea"):
//					this.iUsuario.ingresarDatosAerolinea(nickname, nombre, correo, descripcion, web);
//					break;
//			}
//
//			JOptionPane.showMessageDialog(this, "El usuario se ha creado exitosamente", "Alta de usuario", JOptionPane.INFORMATION_MESSAGE);
//			this.limpiarEntrada();
//		} catch (UsuarioRegistradoException e) {
//			JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de usuario", JOptionPane.ERROR_MESSAGE);
//		}
	}

	private boolean validarCampos(DTUsuario dtUsuario, String nombre, String apellido, String dia, String mes, String anio, String nroDoc, String descripcion) {
        if (nombre.isEmpty()) {
        	return false;
        } else if (dtUsuario instanceof DTCliente && (apellido.isEmpty() || dia.isEmpty() || mes.isEmpty() || anio.isEmpty() || nroDoc.isEmpty())) {
            return false;
        } else if (dtUsuario instanceof DTAerolinea && descripcion.isEmpty()) {
        	return false;
        }
        return true;
	}

	public void inicializarDatos() {
		Set<String> nickUsuarios = this.iUsuario.listarUsuarios();
		listUsuarios.setListData(nickUsuarios.toArray(new String[0]));
		listUsuarios.repaint();
	}

	private void actualizarDatosUsuario(String nick) {
		DTUsuario dtUsuario = null;
		try {
			dtUsuario = iUsuario.obtenerUsuario(nick);
		} catch (UsuarioNoExisteException e) {
			e.printStackTrace();
		}

		this.nickField.setText(dtUsuario.getNickname());
		this.nombreField.setText(dtUsuario.getNombre());
    	this.correoField.setText(dtUsuario.getCorreo());

		if (dtUsuario instanceof DTCliente) {
			this.apellidoField.setText(((DTCliente) dtUsuario).getApellido());
			comboTipoDoc.setSelectedItem(((DTCliente) dtUsuario).getTipoDoc());
			this.nroDocField.setText(String.valueOf(((DTCliente) dtUsuario).getNumDoc()));

			LocalDate fechaNac = ((DTCliente) dtUsuario).getFechaNacimiento();

			this.diaField.setText(String.valueOf(fechaNac.getDayOfMonth()));
			this.mesField.setText(String.valueOf(fechaNac.getMonthValue()));
			this.anioField.setText(String.valueOf(fechaNac.getYear()));

			comboPais.setSelectedItem(((DTCliente) dtUsuario).getNacionalidad());

			this.lblApellido.setVisible(true);
			this.apellidoField.setVisible(true);
			this.lblTipoDoc.setVisible(true);
			this.comboTipoDoc.setVisible(true);
			this.lblNroDoc.setVisible(true);
			this.nroDocField.setVisible(true);
			this.lblFechaNac.setVisible(true);
			this.diaField.setVisible(true);
			this.lblDateSeparator1.setVisible(true);
			this.mesField.setVisible(true);
			this.lblDateSeparator2.setVisible(true);
			this.anioField.setVisible(true);
			this.lblNacionalidad.setVisible(true);
			this.comboPais.setVisible(true);

			this.gblpanelEditarUsuario.rowHeights[9] = 0;
			this.scrollDescripcion.setVisible(false);
			this.lblDescripcion.setVisible(false);
			this.descripcionArea.setVisible(false);
			this.lblLinkWeb.setVisible(false);
			this.webField.setVisible(false);

			this.gblpanelEditarUsuario.rowHeights[11] = 30; // Para estilizar la ventana. Botones quedan a la misma altura
		} else {
			this.descripcionArea.setText(((DTAerolinea) dtUsuario).getDescripcion());
			this.webField.setText(((DTAerolinea) dtUsuario).getWeb());

			this.lblApellido.setVisible(false);
			this.apellidoField.setVisible(false);
			this.lblTipoDoc.setVisible(false);
			this.comboTipoDoc.setVisible(false);
			this.lblNroDoc.setVisible(false);
			this.nroDocField.setVisible(false);
			this.lblFechaNac.setVisible(false);
			this.diaField.setVisible(false);
			this.lblDateSeparator1.setVisible(false);
			this.mesField.setVisible(false);
			this.lblDateSeparator2.setVisible(false);
			this.anioField.setVisible(false);
			this.lblNacionalidad.setVisible(false);
			this.comboPais.setVisible(false);

			this.gblpanelEditarUsuario.rowHeights[9] = 80;
			this.scrollDescripcion.setVisible(true);
			this.lblDescripcion.setVisible(true);
			this.descripcionArea.setVisible(true);
			this.lblLinkWeb.setVisible(true);
			this.webField.setVisible(true);

			this.gblpanelEditarUsuario.rowHeights[11] = 57; // Para estilizar la ventana. Botones quedan a la misma altura
		}
	}

	public void limpiarEntrada() {
		ignorarEventoSeleccion = true;
		this.listUsuarios.clearSelection();
		this.listUsuarios.removeAll();
		ignorarEventoSeleccion = false;
		this.nickField.setText("");
    	this.nombreField.setText("");
    	this.correoField.setText("");
    	this.apellidoField.setText("");
    	this.nroDocField.setText("");
    	this.diaField.setText("");
    	this.mesField.setText("");
    	this.anioField.setText("");
    	this.descripcionArea.setText("");
    	this.webField.setText("");
	}
}
