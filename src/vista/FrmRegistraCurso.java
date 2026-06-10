package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import entidad.Curso;
import model.CursoModel;
import util.ValidateUtil;

public class FrmRegistraCurso extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtHorario;
    private JTextField txtDocente;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JTextField txtPrecio;
    private JTextField txtDuracion;
    private JButton btnRegistrar;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                    FrmRegistraCurso frame = new FrmRegistraCurso();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmRegistraCurso() {
    	setTitle("Rodrigo Padilla - Evaluación T2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 520);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Registrar Curso");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(10, 20, 440, 27);
        contentPane.add(lblTitulo);
        
        crearCampo("Nombre:", 70, txtNombre = new JTextField());
        crearCampo("Descripción:", 110, txtDescripcion = new JTextField());
        crearCampo("Horario:", 150, txtHorario = new JTextField());
        crearCampo("Docente:", 190, txtDocente = new JTextField());
        crearCampo("Fecha Inicio (YYYY-MM-DD):", 230, txtFechaInicio = new JTextField());
        crearCampo("Fecha Fin (YYYY-MM-DD):", 270, txtFechaFin = new JTextField());
        crearCampo("Precio:", 310, txtPrecio = new JTextField());
        crearCampo("Duración (meses):", 350, txtDuracion = new JTextField());
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(this);
        btnRegistrar.setBounds(180, 410, 120, 30);
        contentPane.add(btnRegistrar);
    }
    
    private void crearCampo(String textoLabel, int y, JTextField txtField) {
        JLabel label = new JLabel(textoLabel);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label.setBounds(20, y, 190, 20);
        contentPane.add(label);
        
        txtField.setBounds(220, y, 180, 25);
        contentPane.add(txtField);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) {
            do_btnRegistrar_actionPerformed(e);
        }
    }

    protected void do_btnRegistrar_actionPerformed(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String horario = txtHorario.getText().trim();
        String docente = txtDocente.getText().trim();
        String fechaInicio = txtFechaInicio.getText().trim();
        String fechaFin = txtFechaFin.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String duracionStr = txtDuracion.getText().trim();

        // --- SISTEMA DE VALIDACIONES CON REGEX ---
        if (!nombre.matches(ValidateUtil.TEXTO_40)) {
            JOptionPane.showMessageDialog(this, "El nombre es inválido. Use solo letras (máx. 40 caracteres).");
            return;
        }
        if (!descripcion.matches(ValidateUtil.TEXTO_NUM_100)) {
            JOptionPane.showMessageDialog(this, "La descripción contiene caracteres no permitidos o es muy larga.");
            return;
        }
        if (!horario.matches(ValidateUtil.TEXTO_NUM_100)) {
            JOptionPane.showMessageDialog(this, "El horario contiene caracteres no permitidos.");
            return;
        }
        if (!docente.matches(ValidateUtil.TEXTO_40)) {
            JOptionPane.showMessageDialog(this, "El docente es inválido. Use solo letras (máx. 40 caracteres).");
            return;
        }
        if (!fechaInicio.matches(ValidateUtil.DATE_YYYY_MM_DD) || !fechaFin.matches(ValidateUtil.DATE_YYYY_MM_DD)) {
            JOptionPane.showMessageDialog(this, "Error en fecha. Revise que exista y use el formato YYYY-MM-DD.");
            return;
        }
        if (!precioStr.matches(ValidateUtil.NUM_DECIMAL)) {
            JOptionPane.showMessageDialog(this, "Precio inválido. Ingrese un valor numérico (ej: 150 o 150.50).");
            return;
        }
        if (!duracionStr.matches(ValidateUtil.NUM_ENTERO)) {
            JOptionPane.showMessageDialog(this, "Duración inválida. Ingrese la cantidad de meses en números enteros.");
            return;
        }

        // --- REGISTRO EN LA BASE DE DATOS ---
        try {
            Curso obj = new Curso();
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            obj.setHorario(horario);
            obj.setDocente(docente);
            obj.setFechaInicio(LocalDate.parse(fechaInicio));
            obj.setFechaFin(LocalDate.parse(fechaFin));
            obj.setPrecio(Double.parseDouble(precioStr));
            obj.setDuracion(Integer.parseInt(duracionStr));
            
            CursoModel model = new CursoModel();
            int salida = model.insertaCurso(obj);
            
            if (salida > 0) {
                JOptionPane.showMessageDialog(this, "¡Curso registrado correctamente en BD_Evaluacion!");
                txtNombre.setText(""); txtDescripcion.setText(""); txtHorario.setText(""); 
                txtDocente.setText(""); txtFechaInicio.setText(""); txtFechaFin.setText(""); 
                txtPrecio.setText(""); txtDuracion.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el curso en la base de datos.");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado en el sistema.");
            ex.printStackTrace();
        }
    }
}