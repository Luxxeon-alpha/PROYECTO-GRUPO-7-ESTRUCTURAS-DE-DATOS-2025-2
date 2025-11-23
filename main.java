import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/*
 * Graduacion UNAL - Planificador de semestres con estructura de arbol.

 */
public class GraduacionUNALGUI extends JFrame {

    // Nodo del "arbol de prerrequisitos"
    static class CourseNode {
        int id;
        java.util.List<Integer> prereqIds = new ArrayList<>();  // IDs de padres
        java.util.List<CourseNode> children = new ArrayList<>();

        CourseNode(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Curso " + id + " (pre: " + prereqIds + ")";
        }
    }

    private JTextField idField;
    private JTextField prereqField;
    private JTextField maxPerSemesterField;
    private JTextArea outputArea;

    private Map<Integer, CourseNode> courses = new HashMap<>();

    public GraduacionUNALGUI() {
        super("Graduación UNAL - Planificador con Árboles");
        buildGUI();
    }

    private void buildGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID asignatura:");
        idField = new JTextField(10);

        JLabel prereqLabel = new JLabel("Prerrequisitos (IDs separados por coma, vacío si no tiene):");
        prereqField = new JTextField(20);

        JLabel maxLabel = new JLabel("Máximo de asignaturas por semestre:");
        maxPerSemesterField = new JTextField(5);

        JButton addButton = new JButton("Agregar / Actualizar asignatura");
        JButton calculateButton = new JButton("Calcular plan de semestres");
        JButton clearButton = new JButton("Limpiar todo");

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(prereqLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(prereqField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(maxLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(maxPerSemesterField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonsPanel.add(addButton);
        buttonsPanel.add(calculateButton);
        buttonsPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        inputPanel.add(buttonsPanel, gbc);

        add(inputPanel, BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddCourse();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCalculatePlan();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courses.clear();
                outputArea.setText("");
                idField.setText("");
                prereqField.setText("");
                maxPerSemesterField.setText("");
            }
        });
    }
    private void handleAddCourse() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID numérico.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }






        
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String prereqText = prereqField.getText().trim();
        java.util.List<Integer> prereqIds = new ArrayList<>();

        if (!prereqText.isEmpty()) {
            String[] parts = prereqText.split(",");
            for (String p : parts) {
                String s = p.trim();
                if (!s.isEmpty()) {
                    try {
                        int preId = Integer.parseInt(s);
                        prereqIds.add(preId);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Todos los prerrequisitos deben ser enteros.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }

        CourseNode course = courses.get(id);
        if (course == null) {
            course = new CourseNode(id);
            courses.put(id, course);
        }
        course.prereqIds.clear();
        course.prereqIds.addAll(prereqIds);

        for (int preId : prereqIds) {
            if (!courses.containsKey(preId)) {
                courses.put(preId, new CourseNode(preId));
            }
        }

        outputArea.append("Registrada/actualizada asignatura: " + course + "\n");
    }




    
  
    private void handleCalculatePlan() {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Registre al menos una asignatura.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maxText = maxPerSemesterField.getText().trim();
        if (maxText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique el máximo de asignaturas por semestre.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int maxPerSemester;
        try {
            maxPerSemester = Integer.parseInt(maxText);
            if (maxPerSemester <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El máximo por semestre debe ser un entero positivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String result = computePlanWithComplexity(courses, maxPerSemester);
        outputArea.append("\n========================\n");
        outputArea.append(result);
        outputArea.append("\n");
    }

  
    private static String computePlanWithComplexity(Map<Integer, CourseNode> courses,
                                                    int maxPerSemester) {
        StringBuilder sb = new StringBuilder();

        
        for (CourseNode c : courses.values()) {
            c.children.clear();
        }



        
        Map<Integer, Integer> indegree = new HashMap<>();
        for (CourseNode c : courses.values()) {
            indegree.put(c.id, 0);
        }

        int edges = 0; 

        for (CourseNode c : courses.values()) {
            for (int preId : c.prereqIds) {
                CourseNode pre = courses.get(preId);
                if (pre == null) {
                    
                    return "ERROR: El prerrequisito con ID " + preId +
                           " no ha sido registrado como asignatura. " +
                           "Regístrelo antes de calcular el plan.\n";
                }
                pre.children.add(c);          
                indegree.put(c.id, indegree.get(c.id) + 1);
                edges++;
            }
        }

        int n = courses.size(); 
        Queue<CourseNode> queue = new ArrayDeque<>();
        for (CourseNode c : courses.values()) {
            if (indegree.get(c.id) == 0) {
                queue.offer(c);
            }
        }

        java.util.List<java.util.List<Integer>> semesters = new ArrayList<>();
        int scheduled = 0;

        while (scheduled < n) {
            if (queue.isEmpty()) {
                sb.append("ERROR: Existe un ciclo de prerrequisitos. " +
                          "No se puede generar un plan válido.\n");
                return sb.toString();
            }

            java.util.List<Integer> thisSemester = new ArrayList<>();
            int capacity = maxPerSemester;

            
            while (capacity > 0 && !queue.isEmpty()) {
                CourseNode c = queue.poll();
                if (indegree.get(c.id) < 0) { 
                    continue;
                }
                thisSemester.add(c.id);
                indegree.put(c.id, -1); 
                scheduled++;
                capacity--;
            }

            for (int courseId : thisSemester) {
                CourseNode courseNode = courses.get(courseId);
                for (CourseNode child : courseNode.children) {
                    int deg = indegree.get(child.id);
                    if (deg > 0) {
                        deg--;
                        indegree.put(child.id, deg);
                        if (deg == 0) {
                            queue.offer(child);
                        }
                    }
                }
            }

            semesters.add(thisSemester);
        }
        sb.append("Máximo de asignaturas por semestre: ").append(maxPerSemester).append("\n");
        sb.append("Número total de asignaturas (N): ").append(n).append("\n");
        sb.append("Número total de relaciones de prerrequisito (E): ").append(edges).append("\n\n");

        sb.append("Número mínimo de semestres necesarios: ").append(semesters.size()).append("\n");
        for (int i = 0; i < semesters.size(); i++) {
            java.util.List<Integer> sem = semesters.get(i);
            Collections.sort(sem);
            sb.append("Semestre ").append(i + 1).append(": ");
            for (int j = 0; j < sem.size(); j++) {
                sb.append(sem.get(j));
                if (j < sem.size() - 1) sb.append(", ");
            }
            sb.append("\n");
        }

        sb.append("\n--- Complejidad del algoritmo ---\n");
        sb.append("Construcción del bosque (hijos + indegree): O(N + E) tiempo y O(N + E) espacio.\n");
        sb.append("Recorrido por niveles (BFS en el bosque): O(N + E) tiempo y O(N) espacio adicional.\n");
        sb.append("Complejidad total aproximada: O(N + E) en tiempo, O(N + E) en memoria.\n");
        
        sb.append("Donde:\n");
        sb.append("  N = número de asignaturas (nodos del árbol/grafo).\n");
        sb.append("  E = número de relaciones de prerrequisito (aristas entre nodos).\n");

        return sb.toString();
    }

    public static void main(String[] args) {
    
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GraduacionUNALGUI gui = new GraduacionUNALGUI();
                gui.setVisible(true);
            }
        });
    }
}
