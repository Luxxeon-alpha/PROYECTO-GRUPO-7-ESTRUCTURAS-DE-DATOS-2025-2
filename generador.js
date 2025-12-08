const fs = require('fs');
const path = require('path');

if (process.argv.length < 3) {
    console.error("Uso: node generador.js archivo.js");
    process.exit(1);
}

const inputFile = process.argv[2];

if (!fs.existsSync(inputFile)) {
    console.error(`ERROR: El archivo ${inputFile} no existe.`);
    process.exit(1);
}

// nombre base sin extensión
const baseName = path.basename(inputFile, '.js');
const outputFileName = `${baseName}.java`;

const MATTERS = require(path.resolve(inputFile)).default || require(path.resolve(inputFile));

/**
 * Convierte el array de prerequisites de JS a formato Java
 * Reglas:
 * - Array plano: cada elemento es obligatorio (AND)
 * - Array anidado: al menos uno del array anidado es obligatorio (OR)
 * 
 * Ejemplo:
 * [1000001] -> Arrays.asList(Arrays.asList(1000001))
 * [[1000004, 2016377]] -> Arrays.asList(Arrays.asList(1000004, 2016377))
 * [[1000005,2015556], 1000019] -> Arrays.asList(Arrays.asList(1000005, 2015556), Arrays.asList(1000019))
 * [2016699, [2015184, 2025963], [2025964, 2026519]] -> 
 *   Arrays.asList(Arrays.asList(2016699), Arrays.asList(2015184, 2025963), Arrays.asList(2025964, 2026519))
 */
function convertPrerequisites(prerequisites) {
    if (!prerequisites || prerequisites.length === 0) {
        return '';
    }

    const groups = [];

    for (const item of prerequisites) {
        if (Array.isArray(item)) {
            // Es un array anidado (OR dentro del grupo)
            groups.push(`Arrays.asList(${item.join(', ')})`);
        } else {
            // Es un elemento simple, lo envolvemos en su propio grupo
            groups.push(`Arrays.asList(${item})`);
        }
    }

    return `, \n            Arrays.asList(${groups.join(', ')})`;
}

function generateJavaCode(matters) {
    let javaCode = `import java.util.*;

class Matter {
    private int id;
    private String name;
    private List<List<Integer>> prerequisites; // Lista de listas para manejar opciones (OR)

    public Matter(int id, String name) {
        this.id = id;
        this.name = name;
        this.prerequisites = new ArrayList<>();
    }

    public Matter(int id, String name, List<List<Integer>> prerequisites) {
        this.id = id;
        this.name = name;
        this.prerequisites = prerequisites;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<List<Integer>> getPrerequisites() {
        return prerequisites;
    }

    // Setters
    public void setPrerequisites(List<List<Integer>> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void addPrerequisite(List<Integer> prerequisiteGroup) {
        this.prerequisites.add(prerequisiteGroup);
    }

    @Override
    public String toString() {
        return "Matter{" +
                "id=" + id +
                ", name='" + name + '\\'' +
                ", prerequisites=" + prerequisites +
                '}';
    }
}

class MatterGraph {
    private Map<Integer, Matter> matters;

    public MatterGraph() {
        this.matters = new HashMap<>();
        initializeMatters();
    }

    private void initializeMatters() {
`;

    const sortedIds = Object.keys(matters).sort((a, b) => parseInt(a) - parseInt(b));

    for (const id of sortedIds) {
        const matter = matters[id];
        const prereqString = convertPrerequisites(matter.prerequisites);

        if (prereqString) {
            javaCode += `        matters.put(${id}, new Matter(${id}, "${matter.name}"${prereqString}));\n`;
        } else {
            javaCode += `        matters.put(${id}, new Matter(${id}, "${matter.name}"));\n`;
        }
    }

    javaCode += `    }

    public Matter getMatter(int id) {
        return matters.get(id);
    }

    public Map<Integer, Matter> getAllMatters() {
        return matters;
    }
    

    // Método para imprimir el grafo (solo algunas materias como ejemplo)
    public void printGraph() {
        System.out.println("=== GRAFO DE MATERIAS ===\\n");
        for (Matter matter : matters.values()) {
            System.out.println(matter);
        }
    }
}

// Clase principal para pruebas
public class ${baseName} {
    public static void main(String[] args) {
        MatterGraph graph = new MatterGraph();
        graph.printGraph();
    }
}
`;

    return javaCode;
}

const javaCode = generateJavaCode(MATTERS);
fs.writeFileSync(outputFileName, javaCode);

console.log(`${outputFileName} generado exitosamente`);
console.log(`Total de materias procesadas: ${Object.keys(MATTERS).length}`);