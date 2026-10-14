
import java.sql.Date;

public class Alumno {
    // Atributos privados mapeados directamente de las columnas de la tabla tabalum
    private String codigoAlumno;      // Corresponde a 'codal' (CHAR)
    private String nombreAlumno;      // Corresponde a 'nomal' (VARCHAR)
    private int edadAlumno;           // Corresponde a 'edadal' (SMALLINT)
    private Date fechaNacimiento;     // Corresponde a 'fechal' (DATE)
    private String ciudadAlumno;      // Corresponde a 'ciudadal' (VARCHAR)
    private String generoAlumno;      // Corresponde a 'genal' (CHAR)
    private String codigoPrograma;    // Corresponde a 'codprog' (CHAR)

    // Constructor vacío requerido por los estándares de persistencia
    public Alumno() {}

    // Constructor completo para instanciar el objeto con todos sus datos
    public Alumno(String codigoAlumno, String nombreAlumno, int edadAlumno, Date fechaNacimiento, 
                  String ciudadAlumno, String generoAlumno, String codigoPrograma) {
        this.codigoAlumno = codigoAlumno;
        this.nombreAlumno = nombreAlumno;
        this.edadAlumno = edadAlumno;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudadAlumno = ciudadAlumno;
        this.generoAlumno = generoAlumno;
        this.codigoPrograma = codigoPrograma;
    }

    // --- Métodos de Acceso (Getters y Setters) en formato camelCase ---

    public String getCodigoAlumno() { return codigoAlumno; }
    public void setCodigoAlumno(String codigoAlumno) { this.codigoAlumno = codigoAlumno; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public int getEdadAlumno() { return edadAlumno; }
    public void setEdadAlumno(int edadAlumno) { this.edadAlumno = edadAlumno; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCiudadAlumno() { return ciudadAlumno; }
    public void setCiudadAlumno(String ciudadAlumno) { this.ciudadAlumno = ciudadAlumno; }

    public String getGeneroAlumno() { return generoAlumno; }
    public void setGeneroAlumno(String generoAlumno) { this.generoAlumno = generoAlumno; }

    public String getCodigoPrograma() { return codigoPrograma; }
    public void setCodigoPrograma(String codigoPrograma) { this.codigoPrograma = codigoPrograma; }

    
}
