package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import entidad.Curso;
import util.MySqlDBConexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Curso> consultaPorNombreYDocente(String filtroNombre, String filtroDocente) {
        List<Curso> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null; 
        
        try {
            conn = MySqlDBConexion.getConexion();
            String sql = "SELECT * FROM curso WHERE nombre LIKE ? AND docente LIKE ?";
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, "%" + filtroNombre + "%");
            pstm.setString(2, "%" + filtroDocente + "%");
            
            rs = pstm.executeQuery();
            
            while (rs.next()) {
                Curso obj = new Curso();
                obj.setIdCurso(rs.getInt("idCurso"));
                obj.setNombre(rs.getString("nombre"));
                obj.setDescripcion(rs.getString("descripcion"));
                obj.setHorario(rs.getString("horario"));
                obj.setDocente(rs.getString("docente"));
                
                if(rs.getDate("fechaInicio") != null) {
                    obj.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                }
                if(rs.getDate("fechaFin") != null) {
                    obj.setFechaFin(rs.getDate("fechaFin").toLocalDate());
                }
                
                obj.setPrecio(rs.getDouble("precio"));
                obj.setDuracion(rs.getInt("duracion"));
                
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return lista;
    }
}