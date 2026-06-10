package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entidad.Curso;
import model.CursoModel;

public class FrmConsultaCurso extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtFiltroNombre;
    private JTextField txtFiltroDocente;
    private JButton btnConsultar;
    private JTable table;
    private DefaultTableModel modelo;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                    FrmConsultaCurso frame = new FrmConsultaCurso();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmConsultaCurso() {
        setTitle("Rodrigo Padilla - Consulta de Cursos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Consultar Cursos");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setBounds(20, 20, 300, 30);
        contentPane.add(lblTitulo);
        
        // Campo 1 de búsqueda: Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 70, 70, 20);
        contentPane.add(lblNombre);
        
        txtFiltroNombre = new JTextField();
        txtFiltroNombre.setBounds(80, 70, 150, 25);
        contentPane.add(txtFiltroNombre);
        
        // Campo 2 de búsqueda: Docente
        JLabel lblDocente = new JLabel("Docente:");
        lblDocente.setBounds(250, 70, 70, 20);
        contentPane.add(lblDocente);
        
        txtFiltroDocente = new JTextField();
        txtFiltroDocente.setBounds(310, 70, 150, 25);
        contentPane.add(txtFiltroDocente);
        
        btnConsultar = new JButton("Buscar");
        btnConsultar.addActionListener(this);
        btnConsultar.setBounds(490, 70, 100, 25);
        contentPane.add(btnConsultar);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 120, 690, 260);
        contentPane.add(scrollPane);
        
        table = new JTable();
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Horario");
        modelo.addColumn("Docente");
        modelo.addColumn("Inicio");
        modelo.addColumn("Fin");
        modelo.addColumn("Precio");
        
        table.setModel(modelo);
        scrollPane.setViewportView(table);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConsultar) {
            String nom = txtFiltroNombre.getText().trim();
            String doc = txtFiltroDocente.getText().trim();
            
            CursoModel model = new CursoModel();
            List<Curso> lista = model.consultaPorNombreYDocente(nom, doc);
            
            modelo.setRowCount(0); 
            
            for (Curso c : lista) {
                Object[] fila = {
                    c.getIdCurso(),
                    c.getNombre(),
                    c.getHorario(),
                    c.getDocente(),
                    c.getFechaInicio(),
                    c.getFechaFin(),
                    c.getPrecio()
                };
                modelo.addRow(fila);
            }
        }
    }
}