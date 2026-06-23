package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entidad.Curso;
import model.CursoModel;
import util.ValidateUtil;

public class FrmMantenimientoCurso extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modelo;
    private JTextField txtId, txtNombre, txtDescripcion, txtHorario, txtDocente, txtFechaInicio, txtFechaFin, txtPrecio, txtDuracion;
    private JButton btnRegistrar, btnModificar, btnEliminar, btnLimpiar;
    private CursoModel cursoModel = new CursoModel();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                    FrmMantenimientoCurso frame = new FrmMantenimientoCurso();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmMantenimientoCurso() {
        setTitle("Rodrigo Padilla - Mantenimiento CRUD (Evaluación T3)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitulo = new JLabel("MANTENIMIENTO DE CURSOS");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setBounds(10, 11, 814, 30);
        contentPane.add(lblTitulo);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 55, 794, 200);
        contentPane.add(scrollPane);
        
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarCamposDesdeTabla();
            }
        });
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        modelo.addColumn("Horario");
        modelo.addColumn("Docente");
        modelo.addColumn("Inicio");
        modelo.addColumn("Fin");
        modelo.addColumn("Precio");
        modelo.addColumn("Meses");
        table.setModel(modelo);
        scrollPane.setViewportView(table);
        
        int x1 = 40, x2 = 150, y = 280, w1 = 100, w2 = 230, h = 25;
        
        crearLabel("ID Curso:", x1, y, w1, h);
        txtId = new JTextField(); txtId.setEditable(false); txtId.setBounds(x2, y, 80, h); contentPane.add(txtId);
        
        y += 35;
        crearLabel("Nombre:", x1, y, w1, h);
        txtNombre = new JTextField(); txtNombre.setBounds(x2, y, w2, h); contentPane.add(txtNombre);
        crearLabel("Fecha Inicio:", 420, y, 120, h);
        txtFechaInicio = new JTextField(); txtFechaInicio.setBounds(550, y, w2, h); contentPane.add(txtFechaInicio);
        
        y += 35;
        crearLabel("Descripción:", x1, y, w1, h);
        txtDescripcion = new JTextField(); txtDescripcion.setBounds(x2, y, w2, h); contentPane.add(txtDescripcion);
        crearLabel("Fecha Fin:", 420, y, 120, h);
        txtFechaFin = new JTextField(); txtFechaFin.setBounds(550, y, w2, h); contentPane.add(txtFechaFin);
        
        y += 35;
        crearLabel("Horario:", x1, y, w1, h);
        txtHorario = new JTextField(); txtHorario.setBounds(x2, y, w2, h); contentPane.add(txtHorario);
        crearLabel("Precio S/:", 420, y, 120, h);
        txtPrecio = new JTextField(); txtPrecio.setBounds(550, y, w2, h); contentPane.add(txtPrecio);
        
        y += 35;
        crearLabel("Docente:", x1, y, w1, h);
        txtDocente = new JTextField(); txtDocente.setBounds(x2, y, w2, h); contentPane.add(txtDocente);
        crearLabel("Duración (mes):", 420, y, 120, h);
        txtDuracion = new JTextField(); txtDuracion.setBounds(550, y, w2, h); contentPane.add(txtDuracion);
        
        btnRegistrar = new JButton("Registrar"); btnRegistrar.setBounds(120, 480, 120, 35); btnRegistrar.addActionListener(this); contentPane.add(btnRegistrar);
        btnModificar = new JButton("Modificar"); btnModificar.setBounds(270, 480, 120, 35); btnModificar.addActionListener(this); contentPane.add(btnModificar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(420, 480, 120, 35); btnEliminar.addActionListener(this); contentPane.add(btnEliminar);
        btnLimpiar = new JButton("Limpiar"); btnLimpiar.setBounds(570, 480, 120, 35); btnLimpiar.addActionListener(this); contentPane.add(btnLimpiar);
        
        listarCursosEnTabla();
    }
    
    private void crearLabel(String texto, int x, int y, int w, int h) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label.setBounds(x, y, w, h);
        contentPane.add(label);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) registrar();
        if (e.getSource() == btnModificar) modificar();
        if (e.getSource() == btnEliminar) eliminar();
        if (e.getSource() == btnLimpiar) limpiarFormulario();
    }
    
    private void listarCursosEnTabla() {
        modelo.setRowCount(0);
        List<Curso> lista = cursoModel.listarTodos();
        for (Curso c : lista) {
            Object[] fila = { c.getIdCurso(), c.getNombre(), c.getDescripcion(), c.getHorario(), c.getDocente(), c.getFechaInicio(), c.getFechaFin(), c.getPrecio(), c.getDuracion() };
            modelo.addRow(fila);
        }
    }
    
    private void cargarCamposDesdeTabla() {
        int filaSel = table.getSelectedRow();
        if (filaSel != -1) {
            txtId.setText(modelo.getValueAt(filaSel, 0).toString());
            txtNombre.setText(modelo.getValueAt(filaSel, 1).toString());
            txtDescripcion.setText(modelo.getValueAt(filaSel, 2).toString());
            txtHorario.setText(modelo.getValueAt(filaSel, 3).toString());
            txtDocente.setText(modelo.getValueAt(filaSel, 4).toString());
            txtFechaInicio.setText(modelo.getValueAt(filaSel, 5).toString());
            txtFechaFin.setText(modelo.getValueAt(filaSel, 6).toString());
            txtPrecio.setText(modelo.getValueAt(filaSel, 7).toString());
            txtDuracion.setText(modelo.getValueAt(filaSel, 8).toString());
        }
    }

    private void registrar() {
        Curso obj = validarCampos();
        if (obj == null) return;
        int res = cursoModel.insertaCurso(obj);
        if (res > 0) {
            JOptionPane.showMessageDialog(this, "¡Curso registrado correctamente!");
            listarCursosEnTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar.");
        }
    }

    private void modificar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso de la tabla para modificar.");
            return;
        }
        Curso obj = validarCampos();
        if (obj == null) return;
        obj.setIdCurso(Integer.parseInt(txtId.getText()));
        
        int res = cursoModel.actualizaCurso(obj);
        if (res > 0) {
            JOptionPane.showMessageDialog(this, "¡Curso actualizado correctamente!");
            listarCursosEnTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar.");
        }
    }

    private void eliminar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso de la tabla para eliminar.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este curso?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            int res = cursoModel.eliminaCurso(id);
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "¡Curso eliminado correctamente!");
                listarCursosEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    }

    private void limpiarFormulario() {
        txtId.setText(""); txtNombre.setText(""); txtDescripcion.setText("");
        txtHorario.setText(""); txtDocente.setText(""); txtFechaInicio.setText("");
        txtFechaFin.setText(""); txtPrecio.setText(""); txtDuracion.setText("");
        table.clearSelection();
    }

    private Curso validarCampos() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String horario = txtHorario.getText().trim();
        String docente = txtDocente.getText().trim();
        String fInicio = txtFechaInicio.getText().trim();
        String fFin = txtFechaFin.getText().trim();
        String precio = txtPrecio.getText().trim();
        String duracion = txtDuracion.getText().trim();

        if (!nombre.matches(ValidateUtil.TEXTO_40)) {
            JOptionPane.showMessageDialog(this, "Nombre inválido (Máx. 40 letras)."); return null;
        }
        if (!descripcion.matches(ValidateUtil.TEXTO_NUM_100)) {
            JOptionPane.showMessageDialog(this, "Descripción inválida."); return null;
        }
        if (!horario.matches(ValidateUtil.TEXTO_NUM_100)) {
            JOptionPane.showMessageDialog(this, "Horario inválido."); return null;
        }
        if (!docente.matches(ValidateUtil.TEXTO_40)) {
            JOptionPane.showMessageDialog(this, "Docente inválido (Máx. 40 letras)."); return null;
        }
        if (!fInicio.matches(ValidateUtil.DATE_YYYY_MM_DD) || !fFin.matches(ValidateUtil.DATE_YYYY_MM_DD)) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido (YYYY-MM-DD)."); return null;
        }
        if (!precio.matches(ValidateUtil.NUM_DECIMAL)) {
            JOptionPane.showMessageDialog(this, "Precio inválido (Ej: 150.00)."); return null;
        }
        if (!duracion.matches(ValidateUtil.NUM_ENTERO)) {
            JOptionPane.showMessageDialog(this, "Duración inválida."); return null;
        }

        Curso obj = new Curso();
        obj.setNombre(nombre);
        obj.setDescripcion(descripcion);
        obj.setHorario(horario);
        obj.setDocente(docente);
        obj.setFechaInicio(LocalDate.parse(fInicio));
        obj.setFechaFin(LocalDate.parse(fFin));
        obj.setPrecio(Double.parseDouble(precio));
        obj.setDuracion(Integer.parseInt(duracion));
        return obj;
    }
}