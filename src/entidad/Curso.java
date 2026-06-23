package entidad;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Curso {
    private int idCurso;
    private String nombre;
    private String descripcion;
    private String horario;
    private String docente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precio;
    private int duracion;
    private int estado; 
}