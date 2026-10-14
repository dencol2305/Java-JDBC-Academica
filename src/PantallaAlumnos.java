import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/*
 -- Clase que gestiona la interfaz gráfica de usuario (GUI) para el módulo de alumnos.
 -- Implementa el  CRUD completo con cuatro (4)botones y consulta estructurada mediante una grilla.
 */
public class PantallaAlumnos extends JFrame {

    // Componentes visuales para la captura de datos del formulario (camelCase)
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTextField txtFecha;
    private JTextField txtCiudad;
    private JTextField txtGenero;
    private JTextField txtPrograma;

    // Los 4 botones requeridos para el ciclo operativo del CRUD
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnConsultar;

    // Instancia del controlador de operaciones a la base de datos MySQL usando el nombre exacto de tu archivo
    private AlumnoCrud gestorCrud;

    /**
     * Constructor encargado de inicializar y estructurar la interfaz principal.
     */
    public PantallaAlumnos() {
        // Inicializamos el objeto de acceso a datos (DAO) coincidiendo con tu archivo AlumnoCrud.java
        gestorCrud = new AlumnoCrud();

        // Configuración básica de la ventana principal
        setTitle("Módulo de Gestión de Alumnos - Sistema Académico");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Contenedor principal con distribución vertical (BorderLayout)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. PANEL DE FORMULARIO (Cuadrícula para etiquetas y cajas de texto)
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 8, 8));
        
        panelFormulario.add(new JLabel("Código del Alumno:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Edad (Años):"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Fecha Nacimiento (AAAA-MM-DD):"));
        txtFecha = new JTextField();
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("Ciudad de Origen:"));
        txtCiudad = new JTextField();
        panelFormulario.add(txtCiudad);

        panelFormulario.add(new JLabel("Género (M/F):"));
        txtGenero = new JTextField();
        panelFormulario.add(txtGenero);

        panelFormulario.add(new JLabel("Código del Programa:"));
        txtPrograma = new JTextField();
        panelFormulario.add(txtPrograma);

        // 2. PANEL DE BOTONES (Fila horizontal inferior con los 4 botones obligatorios)
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 5, 5));
        
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnConsultar = new JButton("Consultar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultar);

        // Organizamos las regiones dentro de la ventana principal
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

        // --- ASIGNACIÓN DE EVENTOS CONTROLADORES A LOS BOTONES ---
        btnGuardar.addActionListener(e -> ejecutarGuardar());
        btnActualizar.addActionListener(e -> ejecutarActualizar());
        btnEliminar.addActionListener(e -> ejecutarEliminar());
        btnConsultar.addActionListener(e -> abrirVentanaConsulta());
    }

    // ==========================================================
    // MÉTODOS DE ACCIÓN OPERATIVA (LÓGICA DEL CRUD)
    // ==========================================================

    private void ejecutarGuardar() {
        Alumno objetoAlumno = construirObjetoDesdeCampos();
        if (objetoAlumno != null) {
            boolean exito = gestorCrud.insertarAlumno(objetoAlumno);
            notificarResultado(exito, "¡Alumno guardado con éxito en la base de datos!");
        }
    }

    private void ejecutarActualizar() {
        Alumno objetoAlumno = construirObjetoDesdeCampos();
        if (objetoAlumno != null) {
            if (txtCodigo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe digitar el código del alumno a actualizar.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean exito = gestorCrud.actualizarAlumno(objetoAlumno);
            notificarResultado(exito, "¡Registro de alumno actualizado correctamente!");
        }
    }

    private void ejecutarEliminar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite el Código del alumno que desea eliminar.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al alumno con código " + codigo + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = gestorCrud.eliminarAlumno(codigo);
            notificarResultado(exito, "¡Alumno removido físicamente del sistema!");
        }
    }

    /**
     * Crea y despliega la ventana emergente (JDialog) para realizar la búsqueda por código
     * renderizando el resultado dentro de una grilla de datos (JTable).
     */
    private void abrirVentanaConsulta() {
        JDialog ventanaEmergente = new JDialog(this, "Consultar Alumno por Código", true);
        ventanaEmergente.setSize(600, 250); // Dimensiones adecuadas para la visualización de la grilla
        ventanaEmergente.setLocationRelativeTo(this);
        ventanaEmergente.setLayout(new BorderLayout(10, 10));

        // Panel de búsqueda superior (Etiqueta + Caja + Botón)
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel lblBuscar = new JLabel("Código a buscar:");
        JTextField txtBuscarCodigo = new JTextField(10);
        JButton btnBuscarPorCodigo = new JButton("Buscar");
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarCodigo);
        panelBusqueda.add(btnBuscarPorCodigo);

        // Configuración de las columnas de la Grilla (JTable)
        String[] columnasGrilla = {"Código", "Nombre", "Edad", "Fecha Nac.", "Ciudad", "Género", "Programa"};
        
        // Inicializamos el modelo deshabilitando la edición directa sobre las celdas
        javax.swing.table.DefaultTableModel modeloGrilla = new javax.swing.table.DefaultTableModel(columnasGrilla, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        JTable tablaResultados = new JTable(modeloGrilla);
        tablaResultados.getTableHeader().setReorderingAllowed(false); // Bloquea el arrastre de columnas

        // Lógica de búsqueda interna al presionar el botón de la ventana emergente
        btnBuscarPorCodigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoFiltro = txtBuscarCodigo.getText().trim();
                if (codigoFiltro.isEmpty()) {
                    JOptionPane.showMessageDialog(ventanaEmergente, "Por favor, digite un código para buscar.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Limpiamos registros anteriores de la rejilla
                modeloGrilla.setRowCount(0);

                // Solicitamos la lista completa al controlador JDBC
                List<Alumno> lista = gestorCrud.consultarAlumnos();
                Alumno alumnoEncontrado = null;

                // Buscamos coincidencia exacta de la llave primaria
                for (Alumno al : lista) {
                    if (al.getCodigoAlumno().equals(codigoFiltro)) {
                        alumnoEncontrado = al;
                        break;
                    }
                }

                // Si se localiza el alumno, se inyectan sus datos como fila en la grilla
                if (alumnoEncontrado != null) {
                    Object[] filaDatos = {
                        alumnoEncontrado.getCodigoAlumno(),
                        alumnoEncontrado.getNombreAlumno(),
                        alumnoEncontrado.getEdadAlumno(),
                        alumnoEncontrado.getFechaNacimiento(),
                        alumnoEncontrado.getCiudadAlumno(),
                        alumnoEncontrado.getGeneroAlumno(),
                        alumnoEncontrado.getCodigoPrograma()
                    };
                    
                    modeloGrilla.addRow(filaDatos);
                    
                    // Cargamos los datos de forma automática en el formulario base trasero para agilizar cambios
                    txtCodigo.setText(alumnoEncontrado.getCodigoAlumno());
                    txtNombre.setText(alumnoEncontrado.getNombreAlumno());
                    txtEdad.setText(String.valueOf(alumnoEncontrado.getEdadAlumno()));
                    txtFecha.setText(String.valueOf(alumnoEncontrado.getFechaNacimiento()));
                    txtCiudad.setText(alumnoEncontrado.getCiudadAlumno());
                    txtGenero.setText(alumnoEncontrado.getGeneroAlumno());
                    txtPrograma.setText(alumnoEncontrado.getCodigoPrograma());
                } else {
                    JOptionPane.showMessageDialog(ventanaEmergente, "No se encontró ningún alumno con el código: " + codigoFiltro, "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                    }
        }
    });
// Ensamblamos los componentes en la ventana emergente

    ventanaEmergente.add(panelBusqueda, BorderLayout.NORTH);
    ventanaEmergente.add(new JScrollPane(tablaResultados), BorderLayout.CENTER); // JScrollPane activa los encabezados
    ventanaEmergente.setVisible(true);
}
// ==========================================================
// MÉTODOS AUXILIARES DE CONVERSIÓN Y CONTROL
// ==========================================================
private Alumno construirObjetoDesdeCampos() {
try {
Alumno al = new Alumno();
al.setCodigoAlumno(txtCodigo.getText().trim());
al.setNombreAlumno(txtNombre.getText().trim());
al.setEdadAlumno(Integer.parseInt(txtEdad.getText().trim()));
al.setFechaNacimiento(java.sql.Date.valueOf(txtFecha.getText().trim()));
al.setCiudadAlumno(txtCiudad.getText().trim());
al.setGeneroAlumno(txtGenero.getText().trim());
al.setCodigoPrograma(txtPrograma.getText().trim());
return al;
 } catch (NumberFormatException ex) {
JOptionPane.showMessageDialog(this, "La edad debe ser exclusivamente un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
return null;
    } catch (IllegalArgumentException ex) {
    JOptionPane.showMessageDialog(this, "El formato de fecha de nacimiento requerido es AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
    return null;
    }
}
    private void notificarResultado(boolean exito, String mensajeExito) {
    if (exito) {
    JOptionPane.showMessageDialog(this, mensajeExito, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    limpiarCampos();
    } 
    else {
    JOptionPane.showMessageDialog(this, "Fallo en la operación. Verifique restricciones de integridad o llaves foráneas.", "Error de Servidor MySQL", JOptionPane.ERROR_MESSAGE);
    }
}
private void limpiarCampos() {
txtCodigo.setText("");
txtNombre.setText("");
txtEdad.setText("");
txtFecha.setText("");
txtCiudad.setText("");
txtGenero.setText("");
txtPrograma.setText("");
}
/*
* Hilo de ejecución principal de la interfaz visual.
*/
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaAlumnos().setVisible(true));
    }
}
