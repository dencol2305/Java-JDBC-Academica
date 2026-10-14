import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que centraliza las consultas y manipulaciones de la tabla de alumnos.
 * Coincide exactamente con el archivo físico alumnoCRUD.java.
 */
public class AlumnoCrud {

    // ==========================================
    // MÉTODO: INSERCIÓN DE UN NUEVO ALUMNO
    // ==========================================
    public boolean insertarAlumno(Alumno objetoAlumno) {
        String sentenciaSql = "INSERT INTO tabalum (codal, nomal, edadal, fechal, ciudadal, genal, codprog) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection canalConexion = ConexionBD.conectar();
             PreparedStatement comandoPreparado = canalConexion.prepareStatement(sentenciaSql)) {
            
            comandoPreparado.setString(1, objetoAlumno.getCodigoAlumno());
            comandoPreparado.setString(2, objetoAlumno.getNombreAlumno());
            comandoPreparado.setInt(3, objetoAlumno.getEdadAlumno());
            comandoPreparado.setDate(4, objetoAlumno.getFechaNacimiento());
            comandoPreparado.setString(5, objetoAlumno.getCiudadAlumno());
            comandoPreparado.setString(6, objetoAlumno.getGeneroAlumno());
            comandoPreparado.setString(7, objetoAlumno.getCodigoPrograma());
            
            return comandoPreparado.executeUpdate() > 0;
            
        } catch (SQLException errorSql) {
            System.out.println("Error al ejecutar inserción: " + errorSql.getMessage());
            return false;
        }
    }

    // ==========================================
    // MÉTODO: CONSULTA GENERAL (RETORNA UNA LISTA)
    // ==========================================
    public List<Alumno> consultarAlumnos() {
        List<Alumno> listaAlumnos = new ArrayList<>();
        String sentenciaSql = "SELECT * FROM tabalum";

        try (Connection canalConexion = ConexionBD.conectar();
             PreparedStatement comandoPreparado = canalConexion.prepareStatement(sentenciaSql);
             ResultSet contenedorResultados = comandoPreparado.executeQuery()) {

            while (contenedorResultados.next()) {
                Alumno estudiante = new Alumno();
                estudiante.setCodigoAlumno(contenedorResultados.getString("codal"));
                estudiante.setNombreAlumno(contenedorResultados.getString("nomal"));
                estudiante.setEdadAlumno(contenedorResultados.getInt("edadal"));
                estudiante.setFechaNacimiento(contenedorResultados.getDate("fechal"));
                estudiante.setCiudadAlumno(contenedorResultados.getString("ciudadal"));
                estudiante.setGeneroAlumno(contenedorResultados.getString("genal"));
                estudiante.setCodigoPrograma(contenedorResultados.getString("codprog"));
                
                listaAlumnos.add(estudiante);
            }
        } catch (SQLException errorSql) {
            System.out.println("Error al ejecutar consulta: " + errorSql.getMessage());
        }
        return listaAlumnos;
    }

    // ==========================================
    // MÉTODO: ACTUALIZACIÓN DE DATOS EXISTENTES
    // ==========================================
    public boolean actualizarAlumno(Alumno objetoAlumno) {
        String sentenciaSql = "UPDATE tabalum SET nomal = ?, edadal = ?, fechal = ?, ciudadal = ?, genal = ?, codprog = ? WHERE codal = ?";
        
        try (Connection canalConexion = ConexionBD.conectar();
             PreparedStatement comandoPreparado = canalConexion.prepareStatement(sentenciaSql)) {
            
            comandoPreparado.setString(1, objetoAlumno.getNombreAlumno());
            comandoPreparado.setInt(2, objetoAlumno.getEdadAlumno());
            comandoPreparado.setDate(3, objetoAlumno.getFechaNacimiento());
            comandoPreparado.setString(4, objetoAlumno.getCiudadAlumno());
            comandoPreparado.setString(5, objetoAlumno.getGeneroAlumno());
            comandoPreparado.setString(6, objetoAlumno.getCodigoPrograma());
            comandoPreparado.setString(7, objetoAlumno.getCodigoAlumno()); // Condición WHERE
            
            return comandoPreparado.executeUpdate() > 0;
            
        } catch (SQLException errorSql) {
            System.out.println("Error al ejecutar actualización: " + errorSql.getMessage());
            return false;
        }
    }

    // ==========================================
    // MÉTODO: ELIMINACIÓN FÍSICA POR CÓDIGO
    // ==========================================
    public boolean eliminarAlumno(String codigoAlumno) {
        String sentenciaSql = "DELETE FROM tabalum WHERE codal = ?";
        
        try (Connection canalConexion = ConexionBD.conectar();
             PreparedStatement comandoPreparado = canalConexion.prepareStatement(sentenciaSql)) {
            
            comandoPreparado.setString(1, codigoAlumno);
            return comandoPreparado.executeUpdate() > 0;
            
        } catch (SQLException errorSql) {
            System.out.println("Error al ejecutar eliminación: " + errorSql.getMessage());
            return false;
        }
    }
}

