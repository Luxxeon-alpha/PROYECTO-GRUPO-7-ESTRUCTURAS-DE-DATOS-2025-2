import java.util.*;
class PlanificadorSemestres {
    private GrafoMaterias grafoMaterias;
    
    public PlanificadorSemestres(GrafoMaterias grafo) {
        this.grafoMaterias = grafo;
    }
    
    /**
     * Verifica si una materia está disponible para cursar.
     * Una materia está disponible cuando:
     * - Para CADA grupo de prerequisitos (AND entre grupos)
     * - Se ha cursado AL MENOS UNA materia del grupo (OR dentro del grupo)
     */
    private boolean materiaDisponible(int materiaId, 
                                      Map<Integer, List<List<Integer>>> prerequisitos,
                                      Set<Integer> materiasCursadas) {
        List<List<Integer>> gruposPrereq = prerequisitos.get(materiaId);
        
        // Si no tiene prerequisitos, está disponible
        if (gruposPrereq == null || gruposPrereq.isEmpty()) {
            return true;
        }
        
        // Verificar cada grupo (AND)
        for (List<Integer> grupo : gruposPrereq) {
            // Dentro del grupo, necesita AL MENOS UNA (OR)
            boolean grupoSatisfecho = false;
            
            for (int prereq : grupo) {
                if (materiasCursadas.contains(prereq)) {
                    grupoSatisfecho = true;
                    break;
                }
            }
            
            // Si algún grupo no está satisfecho, la materia NO está disponible
            if (!grupoSatisfecho) {
                return false;
            }
        }
        
        // Todos los grupos están satisfechos
        return true;
    }
    
    public List<List<Integer>> planificarSemestres(int maxPorSemestre) {
        Map<Integer, List<List<Integer>>> prerequisitos = grafoMaterias.getEstructuraPrerequisitos();
        Set<Integer> todasLasMaterias = prerequisitos.keySet();
        Set<Integer> materiasCursadas = new HashSet<>();
        
        List<List<Integer>> plan = new ArrayList<>();
        
        // Mientras haya materias por cursar
        while (materiasCursadas.size() < todasLasMaterias.size()) {
            List<Integer> semestre = new ArrayList<>();
            
            // Encontrar todas las materias disponibles este semestre
            ArrayQueue<Integer> disponibles = new ArrayQueue<>(todasLasMaterias.size());
            
            for (int materiaId : todasLasMaterias) {
                // Si no está cursada Y está disponible
                if (!materiasCursadas.contains(materiaId) && 
                    materiaDisponible(materiaId, prerequisitos, materiasCursadas)) {
                    disponibles.enqueue(materiaId);
                }
            }
            
            // Si no hay materias disponibles, hay un ciclo o error
            if (disponibles.isEmpty()) {
                System.out.println("\n⚠️ ERROR: No hay más materias disponibles.");
                System.out.println("   Posible ciclo en prerequisitos o configuración incorrecta.");
                System.out.println("   Materias cursadas: " + materiasCursadas.size() + "/" + todasLasMaterias.size());
                break;
            }
            
            // Tomar hasta maxPorSemestre materias
            int materiasATomar = Math.min(maxPorSemestre, disponibles.size());
            
            for (int i = 0; i < materiasATomar; i++) {
                Integer materia = disponibles.dequeue();
                if (materia != null) {
                    semestre.add(materia);
                    materiasCursadas.add(materia);
                }
            }
            
            plan.add(semestre);
        }
        
        return plan;
    }
    
    public void imprimirPlan(List<List<Integer>> plan) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║     PLAN DE ESTUDIOS - INGENIERÍA DE SISTEMAS         ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        
        int totalCreditos = 0;
        
        for (int i = 0; i < plan.size(); i++) {
            System.out.println("┌─ SEMESTRE " + (i + 1) + " ───────────────────────────────────┐");
            
            List<Integer> semestre = plan.get(i);
            for (Integer idMateria : semestre) {
                Materia mat = grafoMaterias.getMateria(idMateria);
                if (mat != null) {
                    System.out.println("│  • " + mat.getNombre());
                    totalCreditos += 3;
                }
            }
            
            System.out.println("│  Materias: " + semestre.size());
            System.out.println("└────────────────────────────────────────────────────────┘\n");
        }
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  RESUMEN:");
        System.out.println("  • Total de semestres: " + plan.size());
        System.out.println("  • Total de materias: " + plan.stream().mapToInt(List::size).sum());
        System.out.println("  • Créditos estimados: " + totalCreditos);
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
}
// ===== CLASE PRINCIPAL =====
public class Main {
    public static void main(String[] args) {
        System.out.println("\n🎓 SISTEMA DE PLANIFICACIÓN ACADÉMICA - UNAL\n");
        
        // 1. Inicializar grafo de materias
        GrafoMaterias grafo = new GrafoMaterias();
        System.out.println("✓ Materias cargadas: " + grafo.getMaterias().size());
        
        // 2. Crear planificador
        PlanificadorSemestres planificador = new PlanificadorSemestres(grafo);
        
        // 3. Calcular plan óptimo (máximo 5 materias por semestre)
        int maxMateriasPorSemestre = 5;
        System.out.println("✓ Calculando plan con máximo " + maxMateriasPorSemestre + " materias/semestre...\n");
        
        List<List<Integer>> plan = planificador.planificarSemestres(maxMateriasPorSemestre);
        
        // 4. Imprimir resultado
        planificador.imprimirPlan(plan);
        
        // 5. Prueba con diferentes límites
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  COMPARACIÓN DE ESCENARIOS:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        for (int max : new int[]{3, 4, 5, 6}) {
            List<List<Integer>> planTemp = planificador.planificarSemestres(max);
            System.out.println("  Con " + max + " materias/semestre → " + planTemp.size() + " semestres");
        }
        
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }
}
