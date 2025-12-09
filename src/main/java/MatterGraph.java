import java.util.*;

public class MatterGraph {
    private Map<Integer, Matter> matters = new HashMap<>();
    
    public MatterGraph() {
        cargarMaterias();
    }
    
    private void cargarMaterias() {
        matters.put(1000001, new Matter(1000001, "Matemáticas Básicas"));
        matters.put(1000002, new Matter(1000002, "Lecto-Escritura"));
        matters.put(1000003, new Matter(1000003, "Álgebra Lineal",
                Arrays.asList(Arrays.asList(1000004))));
        matters.put(1000004, new Matter(1000004, "Cálculo diferencial",
                Arrays.asList(Arrays.asList(1000001))));
        matters.put(1000005, new Matter(1000005, "Cálculo integral",
                Arrays.asList(Arrays.asList(1000004))));
        matters.put(1000006, new Matter(1000006, "Cálculo en Varias Variables",
                Arrays.asList(Arrays.asList(1000005))));
        matters.put(1000013, new Matter(1000013, "Probabilidad y Estadística Fundamental",
                Arrays.asList(Arrays.asList(1000005))));
        matters.put(1000017, new Matter(1000017, "Fundamentos de Electricidad y Magnetismo",
                Arrays.asList(Arrays.asList(1000005), Arrays.asList(1000019))));
        matters.put(1000019, new Matter(1000019, "Fundamentos de Mecánica",
                Arrays.asList(Arrays.asList(1000004))));
        matters.put(2015174, new Matter(2015174, "Introducción a la Teoría de la Computación",
                Arrays.asList(Arrays.asList(2015184), Arrays.asList(2025963))));
        matters.put(2015734, new Matter(2015734, "Programación de Computadores"));
        matters.put(2015970, new Matter(2015970, "Métodos Numéricos",
                Arrays.asList(Arrays.asList(1000006))));
        matters.put(2016353, new Matter(2016353, "Bases de Datos",
                Arrays.asList(Arrays.asList(2016375))));
        matters.put(2016375, new Matter(2016375, "Programación Orientada a Objetos",
                Arrays.asList(Arrays.asList(2015734))));
        matters.put(2016696, new Matter(2016696, "Algoritmos",
                Arrays.asList(Arrays.asList(2016699), Arrays.asList(2015184), Arrays.asList(2025963))));
        matters.put(2016697, new Matter(2016697, "Arquitectura de Computadores",
                Arrays.asList(Arrays.asList(2016698))));
        matters.put(2016698, new Matter(2016698, "Elementos de Computadores",
                Arrays.asList(Arrays.asList(2025975))));
        matters.put(2016699, new Matter(2016699, "Estructuras de Datos",
                Arrays.asList(Arrays.asList(2016375))));
        matters.put(2016701, new Matter(2016701, "Ingeniería de Software I",
                Arrays.asList(Arrays.asList(2016353), Arrays.asList(2016703), Arrays.asList(2016699))));
        matters.put(2016702, new Matter(2016702, "Ingeniería de Software II",
                Arrays.asList(Arrays.asList(2016701), Arrays.asList(2025967))));
        matters.put(2016703, new Matter(2016703, "Pensamiento Sistémico"));
        matters.put(2016707, new Matter(2016707, "Sistemas Operativos",
                Arrays.asList(Arrays.asList(2016697))));
        matters.put(2016716, new Matter(2016716, "Arquitectura de Software",
                Arrays.asList(Arrays.asList(2016702))));
        matters.put(2016722, new Matter(2016722, "Computación Paralela y Distribuida",
                Arrays.asList(Arrays.asList(2016696))));
        matters.put(2019082, new Matter(2019082, "Modelos matemáticos I",
                Arrays.asList(Arrays.asList(1000006), Arrays.asList(2025964), Arrays.asList(1000013), Arrays.asList(2016375))));
        matters.put(2025960, new Matter(2025960, "Computación Visual",
                Arrays.asList(Arrays.asList(2016696))));
        matters.put(2025963, new Matter(2025963, "Matemáticas Discretas I",
                Arrays.asList(Arrays.asList(1000003))));
        matters.put(2025964, new Matter(2025964, "Matemáticas Discretas II",
                Arrays.asList(Arrays.asList(2025963), Arrays.asList(2015184))));
        matters.put(2025966, new Matter(2025966, "Lenguajes de Programación",
                Arrays.asList(Arrays.asList(2016699), Arrays.asList(2015174))));
        matters.put(2025967, new Matter(2025967, "Redes de Computadores",
                Arrays.asList(Arrays.asList(1000017), Arrays.asList(2016699), Arrays.asList(2016697))));
        matters.put(2025970, new Matter(2025970, "Modelos y Simulación",
                Arrays.asList(Arrays.asList(1000006), Arrays.asList(2025964), Arrays.asList(1000013), Arrays.asList(2016375))));
        matters.put(2025971, new Matter(2025971, "Optimización",
                Arrays.asList(Arrays.asList(2025970))));
        matters.put(2025972, new Matter(2025972, "Introducción a la Criptografía y a la Seguridad de la Información",
                Arrays.asList(Arrays.asList(2016696))));
        matters.put(2025975, new Matter(2025975, "Introducción a la Ingeniería de Sistemas y Computación"));
        matters.put(2025982, new Matter(2025982, "Sistemas de Información",
                Arrays.asList(Arrays.asList(2016353), Arrays.asList(2016703))));
        matters.put(2025983, new Matter(2025983, "Arquitectura de Infraestructura y gobierno de TICs",
                Arrays.asList(Arrays.asList(2016702), Arrays.asList(2025982))));
        matters.put(2025995, new Matter(2025995, "Introducción a los Sistemas Inteligentes",
                Arrays.asList(Arrays.asList(2016696))));
    }
    
    public Map<Integer, Matter> getMatters() {
        return matters;
    }
    
    public Matter getMatter(int id) {
        return matters.get(id);
    }
}


