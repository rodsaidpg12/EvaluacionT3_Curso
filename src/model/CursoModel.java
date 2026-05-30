package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import entidad.Curso;
import util.MySqlDBConexion;

public class CursoModel {

    public int insertaCurso(Curso obj) {
        int salida = -1;
        Connection conn = null;
        PreparedStatement pstm = null;
        
        try {
            conn = MySqlDBConexion.getConexion();
            String sql = "INSERT INTO curso (nombre, descripcion, horario, docente, fechaInicio, fechaFin, precio, duracion) VALUES (?,?,?,?,?,?,?,?)";
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, obj.getNombre());
            pstm.setString(2, obj.getDescripcion());
            pstm.setString(3, obj.getHorario());
            pstm.setString(4, obj.getDocente());
            pstm.setDate(5, java.sql.Date.valueOf(obj.getFechaInicio()));
            pstm.setDate(6, java.sql.Date.valueOf(obj.getFechaFin()));
            pstm.setDouble(7, obj.getPrecio());
            pstm.setInt(8, obj.getDuracion());

            salida = pstm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }
}