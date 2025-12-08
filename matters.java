import java.util.*;

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
                ", name='" + name + '\'' +
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
        // Matemáticas Básicas y fundamentales
        matters.put(1000001, new Matter(1000001, "Matemáticas Básicas"));
        matters.put(1000002, new Matter(1000002, "Lecto-Escritura"));
        
        // Cálculo diferencial
        matters.put(1000004, new Matter(1000004, "Cálculo diferencial", 
            Arrays.asList(Arrays.asList(1000001))));
        matters.put(2016377, new Matter(2016377, "Cálculo diferencial en una variable", 
            Arrays.asList(Arrays.asList(1000001))));
        
        // Cálculo integral (requiere UNO de los cálculos diferenciales)
        matters.put(1000005, new Matter(1000005, "Cálculo integral", 
            Arrays.asList(Arrays.asList(1000004, 2016377))));
        matters.put(2015556, new Matter(2015556, "Cálculo integral en una variable", 
            Arrays.asList(Arrays.asList(1000004, 2016377))));
        
        // Cálculo en varias variables
        matters.put(1000006, new Matter(1000006, "Cálculo en Varias Variables", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        matters.put(2015162, new Matter(2015162, "Cálculo Vectorial", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        
        // Álgebra Lineal
        matters.put(1000003, new Matter(1000003, "Álgebra Lineal", 
            Arrays.asList(Arrays.asList(1000004, 2016377))));
        matters.put(2015555, new Matter(2015555, "Álgebra Lineal Básica", 
            Arrays.asList(Arrays.asList(1000004, 2016377))));
        
        // Probabilidad y Estadística
        matters.put(1000013, new Matter(1000013, "Probabilidad y Estadística Fundamental", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        matters.put(2027877, new Matter(2027877, "Probabilidad Fundamental", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        matters.put(2015178, new Matter(2015178, "Probabilidad", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        
        // Física
        matters.put(1000019, new Matter(1000019, "Fundamentos de Mecánica", 
            Arrays.asList(Arrays.asList(1000004, 2016377))));
        matters.put(1000017, new Matter(1000017, "Fundamentos de Electricidad y Magnetismo", 
            Arrays.asList(Arrays.asList(1000005, 2015556), Arrays.asList(1000019))));
        
        // Teoría de la Computación
        matters.put(2015174, new Matter(2015174, "Introducción a la Teoría de la Computación", 
            Arrays.asList(Arrays.asList(2015184, 2025963))));
        
        // Algoritmos
        matters.put(2016696, new Matter(2016696, "Algoritmos", 
            Arrays.asList(Arrays.asList(2016699), Arrays.asList(2015184, 2025963), Arrays.asList(2025964, 2026519))));
        
        // Matemáticas Discretas
        matters.put(2025963, new Matter(2025963, "Matemáticas Discretas I", 
            Arrays.asList(Arrays.asList(1000003, 2015555))));
        matters.put(2025819, new Matter(2025819, "Sistemas numéricos", 
            Arrays.asList(Arrays.asList(1000019))));
        matters.put(2025964, new Matter(2025964, "Matemáticas Discretas II", 
            Arrays.asList(Arrays.asList(2025963), Arrays.asList(2015184))));
        matters.put(2015181, new Matter(2015181, "Introducción a la teoría de conjuntos", 
            Arrays.asList(Arrays.asList(2025819))));
        
        // Métodos Numéricos
        matters.put(2015970, new Matter(2015970, "Métodos Numéricos", 
            Arrays.asList(Arrays.asList(1000006, 2015162))));
        matters.put(2019072, new Matter(2019072, "Análisis Numérico I", 
            Arrays.asList(Arrays.asList(2015155))));
        
        // Ingeniería Económica
        matters.put(2015703, new Matter(2015703, "Ingeniería Económica", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        matters.put(2025986, new Matter(2025986, "Ingeniería Económica y Análisis de Riesgo", 
            Arrays.asList(Arrays.asList(1000005, 2015556), Arrays.asList(1000013), 
                         Arrays.asList(2027877, 2015178), Arrays.asList(2016610))));
        matters.put(2016047, new Matter(2016047, "Modelos Económicos Computacionales", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        
        // Gestión de Proyectos
        matters.put(2015702, new Matter(2015702, "Gerencia y Gestión de Proyectos", 
            Arrays.asList(Arrays.asList(2015703, 2025986, 2016047))));
        matters.put(2016028, new Matter(2016028, "Diseño, Gestión y Evaluación de Proyectos", 
            Arrays.asList(Arrays.asList(2015703, 2025986, 2016047))));
        
        // Electrónica y Hardware
        matters.put(2016499, new Matter(2016499, "Electrónica Digital II", 
            Arrays.asList(Arrays.asList(2016498))));
        matters.put(2016508, new Matter(2016508, "Sistemas Embebidos", 
            Arrays.asList(Arrays.asList(2016499))));
        matters.put(2016512, new Matter(2016512, "Verificación de Sistemas Digitales", 
            Arrays.asList(Arrays.asList(2016499))));
        
        // Dinámica y Control
        matters.put(2017271, new Matter(2017271, "Principios de Dinámica", 
            Arrays.asList(Arrays.asList(1000003, 2015555))));
        matters.put(2017287, new Matter(2017287, "Sensores y Actuadores", 
            Arrays.asList(Arrays.asList(2016507))));
        matters.put(2016493, new Matter(2016493, "Control", 
            Arrays.asList(Arrays.asList(2016507))));
        matters.put(2016770, new Matter(2016770, "Robótica", 
            Arrays.asList(Arrays.asList(2017271), Arrays.asList(2016493))));
        
        // Economía
        matters.put(2016592, new Matter(2016592, "Economía general"));
        matters.put(2016610, new Matter(2016610, "Sistemas de costos", 
            Arrays.asList(Arrays.asList(2016592))));
        
        // Biología
        matters.put(1000009, new Matter(1000009, "Biología General"));
        matters.put(1000010, new Matter(1000010, "Biología Molecular y Celular"));
        
        // Diseño y Arte
        matters.put(2016099, new Matter(2016099, "Taller Forma y Estructura"));
        matters.put(2016069, new Matter(2016069, "Fundamentos Tecnológicos: Color y Producción", 
            Arrays.asList(Arrays.asList(2016099))));
        matters.put(2016061, new Matter(2016061, "Bocetación e Ilustración", 
            Arrays.asList(Arrays.asList(2016069))));
        matters.put(2016083, new Matter(2016083, "Producción en Medios Digitales", 
            Arrays.asList(Arrays.asList(2016061))));
        matters.put(2016091, new Matter(2016091, "Taller de énfasis en Multimedia e Imagen Digital 1", 
            Arrays.asList(Arrays.asList(2016083))));
        matters.put(2016093, new Matter(2016093, "Taller de énfasis en animación y narrativas audiovisuales 1", 
            Arrays.asList(Arrays.asList(2016083))));
        
        // Modelos Estocásticos
        matters.put(2025987, new Matter(2025987, "Modelos estocásticos para procesos de manufactura y sistemas de servicios", 
            Arrays.asList(Arrays.asList(2025971, 2015173), Arrays.asList(2025970, 2019082))));
        matters.put(2025988, new Matter(2025988, "Taller de simulación procesos de manufactura y sistemas de servicios", 
            Arrays.asList(Arrays.asList(2025987))));
        
        // Programación
        matters.put(2015734, new Matter(2015734, "Programación de Computadores"));
        matters.put(2026573, new Matter(2026573, "Introducción a las ciencias de la computación y a la programación"));
        matters.put(2016375, new Matter(2016375, "Programación Orientada a Objetos", 
            Arrays.asList(Arrays.asList(2015734, 2026573))));
        matters.put(2016699, new Matter(2016699, "Estructuras de Datos", 
            Arrays.asList(Arrays.asList(2016375))));
        
        // Ingeniería de Software
        matters.put(2016701, new Matter(2016701, "Ingeniería de Software I", 
            Arrays.asList(Arrays.asList(2016353, 2027641), Arrays.asList(2016703), Arrays.asList(2016699))));
        matters.put(2016702, new Matter(2016702, "Ingeniería de Software II", 
            Arrays.asList(Arrays.asList(2016701), Arrays.asList(2025967))));
        matters.put(2016716, new Matter(2016716, "Arquitectura de Software", 
            Arrays.asList(Arrays.asList(2016702))));
        
        // Lenguajes de Programación
        matters.put(2025966, new Matter(2025966, "Lenguajes de Programación", 
            Arrays.asList(Arrays.asList(2016699), Arrays.asList(2015174))));
        matters.put(2027642, new Matter(2027642, "Compiladores", 
            Arrays.asList(Arrays.asList(2016699), Arrays.asList(2015174))));
        matters.put(2027628, new Matter(2027628, "Teoría de Lenguajes Formales", 
            Arrays.asList(Arrays.asList(2016699), Arrays.asList(2015174))));
        
        // Arquitectura y Sistemas
        matters.put(2016698, new Matter(2016698, "Elementos de Computadores", 
            Arrays.asList(Arrays.asList(2025975))));
        matters.put(2016498, new Matter(2016498, "Electrónica Digital I", 
            Arrays.asList(Arrays.asList(2016495))));
        matters.put(2016697, new Matter(2016697, "Arquitectura de Computadores", 
            Arrays.asList(Arrays.asList(2016698, 2016498))));
        matters.put(2016707, new Matter(2016707, "Sistemas Operativos", 
            Arrays.asList(Arrays.asList(2016697))));
        
        // Redes
        matters.put(2025967, new Matter(2025967, "Redes de Computadores", 
            Arrays.asList(Arrays.asList(1000017), Arrays.asList(2016699), Arrays.asList(2016697))));
        matters.put(2016722, new Matter(2016722, "Computación Paralela y Distribuida", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2025983, new Matter(2025983, "Arquitectura de Infraestructura y gobierno de TICs", 
            Arrays.asList(Arrays.asList(2016702), Arrays.asList(2025982))));
        
        // Bases de Datos
        matters.put(2016353, new Matter(2016353, "Bases de Datos", 
            Arrays.asList(Arrays.asList(2016375))));
        matters.put(2027641, new Matter(2027641, "Análisis de bases de datos", 
            Arrays.asList(Arrays.asList(2016375))));
        
        // Comunicaciones
        matters.put(2025994, new Matter(2025994, "Teoría de la Información y Sistemas de Comunicaciones", 
            Arrays.asList(Arrays.asList(1000013, 2015178), Arrays.asList(2025967))));
        matters.put(2016492, new Matter(2016492, "Comunicaciones", 
            Arrays.asList(Arrays.asList(2016503))));
        
        // Sistemas de Información
        matters.put(2025982, new Matter(2025982, "Sistemas de Información", 
            Arrays.asList(Arrays.asList(2016353, 2027641), Arrays.asList(2015702, 2016028), Arrays.asList(2016703))));
        matters.put(2016053, new Matter(2016053, "Sistemas de Información Gerencial", 
            Arrays.asList(Arrays.asList(2016353, 2027641), Arrays.asList(2015702, 2016028), Arrays.asList(2016703))));
        
        // Criptografía y Seguridad
        matters.put(2025972, new Matter(2025972, "Introducción a la Criptografía y a la Seguridad de la Información", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2027311, new Matter(2027311, "Introducción a la criptografía y a la teoría de información", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2027313, new Matter(2027313, "Teoría de la codificación", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2027310, new Matter(2027310, "Criptografía", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2027309, new Matter(2027309, "Análisis forense digital", 
            Arrays.asList(Arrays.asList(2016696))));
        
        // Computación Visual y Lógica
        matters.put(2025960, new Matter(2025960, "Computación Visual", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2027629, new Matter(2027629, "Lógica computacional", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2019267, new Matter(2019267, "Teoría de la recursión", 
            Arrays.asList(Arrays.asList(2016696))));
        
        // Inteligencia Artificial
        matters.put(2025995, new Matter(2025995, "Introducción a los Sistemas Inteligentes", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2023251, new Matter(2023251, "Inteligencia Artificial y Minirobots", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2027631, new Matter(2027631, "Introducción a la Inteligencia Artificial", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2028837, new Matter(2028837, "Matemáticas del aprendizaje de máquinas", 
            Arrays.asList(Arrays.asList(2016696))));
        matters.put(2017290, new Matter(2017290, "Técnicas de Inteligencia Artificial", 
            Arrays.asList(Arrays.asList(2016696))));
        
        // Pensamiento Sistémico
        matters.put(2016703, new Matter(2016703, "Pensamiento Sistémico"));
        
        // Modelos y Simulación
        matters.put(2025969, new Matter(2025969, "Modelos Estocásticos y Simulación en Computación y Comunicaciones", 
            Arrays.asList(Arrays.asList(2025971, 2015173))));
        matters.put(2025970, new Matter(2025970, "Modelos y Simulación", 
            Arrays.asList(Arrays.asList(1000006, 2015162), Arrays.asList(2025964, 2026519), 
                         Arrays.asList(1000013, 2015178), Arrays.asList(2016375))));
        matters.put(2019082, new Matter(2019082, "Modelos matemáticos I", 
            Arrays.asList(Arrays.asList(1000006, 2015162), Arrays.asList(2025964, 2026519), 
                         Arrays.asList(1000013, 2015178), Arrays.asList(2016375))));
        
        // Optimización
        matters.put(2025971, new Matter(2025971, "Optimización", 
            Arrays.asList(Arrays.asList(2025970, 2019082))));
        matters.put(2015173, new Matter(2015173, "Introducción a la Optimización", 
            Arrays.asList(Arrays.asList(2025970, 2019082))));
        
        // Introducción
        matters.put(2025975, new Matter(2025975, "Introducción a la Ingeniería de Sistemas y Computación"));
        
        // Talleres y Proyectos
        matters.put(2024045, new Matter(2024045, "Taller de Proyectos Interdisciplinarios"));
        matters.put(2016615, new Matter(2016615, "Taller de Invención y Creatividad"));
        matters.put(2026551, new Matter(2026551, "Creación y Gestión de Empresas"));
        matters.put(2016007, new Matter(2016007, "Fundamentos de Administración"));
        matters.put(2016600, new Matter(2016600, "Gestión Tecnológica"));
        matters.put(2016599, new Matter(2016599, "Gestión de la Ciencia, la Tecnología y la Innovación"));
        matters.put(2016741, new Matter(2016741, "Finanzas"));
        matters.put(2016037, new Matter(2016037, "Finanzas Avanzadas", 
            Arrays.asList(Arrays.asList(1000005, 2015556))));
        
        // Trabajo de Grado
        matters.put(2025974, new Matter(2025974, "Trabajo de Grado - Trabajo Investigativo"));
        matters.put(2025973, new Matter(2025973, "Trabajo de Grado - Práctica de Extensión"));
        matters.put(2016843, new Matter(2016843, "Trabajo de Grado - Asignaturas de Posgrado"));
        
        // Prácticas
        matters.put(2016762, new Matter(2016762, "Práctica estudiantil I"));
        matters.put(2016763, new Matter(2016763, "Práctica estudiantil II"));
        matters.put(2016764, new Matter(2016764, "Práctica estudiantil III"));
        matters.put(1000070, new Matter(1000070, "Práctica Colombia I"));
        matters.put(1000071, new Matter(1000071, "Práctica Colombia II"));
        matters.put(1000072, new Matter(1000072, "Práctica Colombia III"));
        
        // Fundamentos matemáticos adicionales
        matters.put(2015168, new Matter(2015168, "Fundamentos de matemáticas"));
        matters.put(2026548, new Matter(2026548, "Introducción al Análisis Combinatorio", 
            Arrays.asList(Arrays.asList(2015168))));
        matters.put(2026519, new Matter(2026519, "Ecuaciones en Diferencias Finitas y Sistemas Dinámicos", 
            Arrays.asList(Arrays.asList(1000003, 2015555, 2015181))));
        
        // Estadística
        matters.put(2016366, new Matter(2016366, "Estadística Descriptiva y Exploratoria"));
        matters.put(2016379, new Matter(2016379, "Inferencia Estadística", 
            Arrays.asList(Arrays.asList(1000013, 2015178))));
        
        // Ecuaciones Diferenciales
        matters.put(2016342, new Matter(2016342, "Cálculo de ecuaciones diferenciales ordinarias", 
            Arrays.asList(Arrays.asList(1000005, 2015556, 1000003, 2015555))));
        
        // Electrónica
        matters.put(2016509, new Matter(2016509, "Taller de Ingeniería Electrónica", 
            Arrays.asList(Arrays.asList(2025975))));
        matters.put(2016489, new Matter(2016489, "Circuitos Eléctricos I", 
            Arrays.asList(Arrays.asList(2016509, 1000004, 2016377))));
        matters.put(2016495, new Matter(2016495, "Electrónica Análoga I", 
            Arrays.asList(Arrays.asList(2016489))));
        
        // Campos y Señales
        matters.put(2016506, new Matter(2016506, "Señales y Sistemas I", 
            Arrays.asList(Arrays.asList(2016489, 2016342))));
        matters.put(2015159, new Matter(2015159, "Variable Compleja", 
            Arrays.asList(Arrays.asList(2016342))));
        matters.put(2016507, new Matter(2016507, "Señales y Sistemas II", 
            Arrays.asList(Arrays.asList(2015159), Arrays.asList(2016506))));
        matters.put(2016487, new Matter(2016487, "Campos Electromagnéticos", 
            Arrays.asList(Arrays.asList(1000006, 2015162), Arrays.asList(1000017), Arrays.asList(2016506))));
        matters.put(2016503, new Matter(2016503, "Líneas y Antenas", 
            Arrays.asList(Arrays.asList(2016487))));
    }
    
    
    
    
    public void printGraph() {
        System.out.println("=== GRAFO DE MATERIAS ===\n");
        for (Matter matter : matters.values()) {
            System.out.println(matter);
        }
    }
}

// Clase principal para pruebas
public class Main {
    return
}
