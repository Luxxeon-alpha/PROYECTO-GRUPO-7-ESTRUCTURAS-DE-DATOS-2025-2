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

    return `Arrays.asList(${groups.join(', ')})`;
}

function generateMatterGraphClass(matters) {
    let javaCode = `import java.util.*;

public class MatterGraph {
    private Map<Integer, Matter> matters = new HashMap<>();
    
    public MatterGraph() {
        cargarMaterias();
    }
    
    private void cargarMaterias() {
`;

    const sortedIds = Object.keys(matters).sort((a, b) => parseInt(a) - parseInt(b));

    for (const id of sortedIds) {
        const matter = matters[id];
        const prereqString = convertPrerequisites(matter.prerequisites);

        if (prereqString) {
            javaCode += `        matters.put(${id}, new Matter(${id}, "${matter.name}",\n                ${prereqString}));\n`;
        } else {
            javaCode += `        matters.put(${id}, new Matter(${id}, "${matter.name}"));\n`;
        }
    }

    javaCode += `    }
    
    public Map<Integer, Matter> getMatters() {
        return matters;
    }
    
    public Matter getMatter(int id) {
        return matters.get(id);
    }
}
`;

    return javaCode;
}

// Generar solo MatterGraph.java
const matterGraphClass = generateMatterGraphClass(MATTERS);

fs.writeFileSync('MatterGraph.java', matterGraphClass);
console.log('MatterGraph.java generado exitosamente');
console.log(`Total de materias procesadas: ${Object.keys(MATTERS).length}`);
