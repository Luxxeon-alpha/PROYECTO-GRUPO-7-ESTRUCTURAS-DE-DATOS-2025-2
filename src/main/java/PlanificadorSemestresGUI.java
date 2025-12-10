import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

public class PlanificadorSemestresGUI extends JFrame {

    private MatterGraph graph;
    private PlanificadorSemestres planner;

    private JSpinner spinnerMaxPorSemestre;
    private JTextArea areaMaterias;
    private JTextArea areaPlan;
    private JLabel labelSemestresMinimos;
    private JTextField txtBuscar;  // campo para buscar por código

    public PlanificadorSemestresGUI() {
        graph = new MatterGraph();
        planner = new PlanificadorSemestres(graph);

        initUI();
        cargarMateriasEnTexto();
    }

    private void initUI() {
        setTitle("Planificador de Semestres - Grupo 7");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(new JLabel("Máximo de materias por semestre:"));

        spinnerMaxPorSemestre = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        panelTop.add(spinnerMaxPorSemestre);

        JButton btnCalcular = new JButton("Calcular plan");
        btnCalcular.addActionListener(this::onCalcularPlan);
        panelTop.add(btnCalcular);

        labelSemestresMinimos = new JLabel("Semestres mínimos: -");
        panelTop.add(labelSemestresMinimos);

        panelTop.add(Box.createHorizontalStrut(20));

        panelTop.add(new JLabel("Buscar código:"));
        txtBuscar = new JTextField(8);
        panelTop.add(txtBuscar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::onBuscarMateria);
        panelTop.add(btnBuscar);

        areaMaterias = new JTextArea();
        areaMaterias.setEditable(false);
        areaMaterias.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollMaterias = new JScrollPane(areaMaterias);
        scrollMaterias.setBorder(BorderFactory.createTitledBorder("Materias y prerrequisitos"));

        areaPlan = new JTextArea();
        areaPlan.setEditable(false);
        areaPlan.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPlan = new JScrollPane(areaPlan);
        scrollPlan.setBorder(BorderFactory.createTitledBorder("Plan sugerido por semestres"));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollMaterias,
                scrollPlan
        );
        splitPane.setResizeWeight(0.5);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelTop, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private void cargarMateriasEnTexto() {
        StringBuilder sb = new StringBuilder();
        Map<Integer, Matter> materias = graph.getMatters();
        int x = 0;

        for (Map.Entry<Integer, Matter> entry : materias.entrySet()) {
            int id = entry.getKey();
            Matter m = entry.getValue();
            x++;

            sb.append("ID: ").append(id)
                    .append(" | Nombre: ").append(m.getName())
                    .append("\n   Prerrequisitos: ");

            List<List<Integer>> prereqs = m.getPrerequisites();

            if (prereqs == null || prereqs.isEmpty()) {
                sb.append("Ninguno");
            } else {
                boolean first = true;
                for (List<Integer> grupo : prereqs) {
                    for (Integer preId : grupo) {
                        if (!first) sb.append(", ");
                        sb.append(preId);
                        first = false;
                    }
                }
            }
            sb.append("\n\n");
        }

        areaMaterias.setText(sb.toString());
        areaMaterias.setCaretPosition(0);
    }

    private void onCalcularPlan(ActionEvent e) {
        int maxPorSemestre = (Integer) spinnerMaxPorSemestre.getValue();
        String aux = "";

        try {
            List<List<Integer>> plan = planner.planificarSemestres(maxPorSemestre);
            mostrarPlan(plan);
            int semestresMinimos = planner.calcularSemestresMinimos(maxPorSemestre);
            aux = "Semestres mínimos: " + semestresMinimos;
            labelSemestresMinimos.setText(aux);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error en el modelo",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ocurrió un error al calcular el plan:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarPlan(List<List<Integer>> plan) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PLAN DE SEMESTRES ===\n\n");
        int i = 0;

        for (int j = 0; j < plan.size(); j++) {
            sb.append("Semestre ").append(j + 1).append(": ");

            List<Integer> materiasSemestre = plan.get(j);
            if (materiasSemestre.isEmpty()) {
                sb.append("(sin materias)");
            } else {
                boolean first = true;
                for (Integer idMateria : materiasSemestre) {
                    Matter m = graph.getMatter(idMateria);
                    String nombre = (m != null) ? m.getName() : "?";
                    if (!first) sb.append(" | ");
                    sb.append(idMateria).append(" - ").append(nombre);
                    first = false;
                    i++;
                }
            }
            sb.append("\n");
        }

        areaPlan.setText(sb.toString());
        areaPlan.setCaretPosition(0);
    }

    private void onBuscarMateria(ActionEvent e) {
        String texto = txtBuscar.getText();
        if (texto == null || texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un código de asignatura.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El código debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Matter m = graph.getMatter(codigo);
        if (m == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la asignatura con código " + codigo, "No encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("Código: ").append(codigo).append("\n");
        info.append("Nombre: ").append(m.getName()).append("\n");
        info.append("Prerrequisitos: ");

        List<List<Integer>> prereqs = m.getPrerequisites();
        if (prereqs == null || prereqs.isEmpty()) {
            info.append("Ninguno");
        } else {
            boolean first = true;
            for (List<Integer> grupo : prereqs) {
                for (Integer preId : grupo) {
                    if (!first) info.append(", ");
                    info.append(preId);
                    first = false;
                }
            }
        }

        JOptionPane.showMessageDialog(this, info.toString(), "Detalle de asignatura", JOptionPane.INFORMATION_MESSAGE);

        String todo = areaMaterias.getText();
        String patron = "ID: " + codigo;
        int idx = todo.indexOf(patron);
        if (idx >= 0) {
            areaMaterias.requestFocus();
            areaMaterias.setCaretPosition(idx);
            areaMaterias.select(idx, idx + patron.length());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlanificadorSemestresGUI gui = new PlanificadorSemestresGUI();
            gui.setVisible(true);
        });
    }
}
