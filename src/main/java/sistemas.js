//Matters data
const MATTERS = {
  1000001: {
    id: 1000001,
    name: "Matemáticas Básicas",
  },
  1000002: {
    id: 1000002,
    name: "Lecto-Escritura",
  },
  1000004: {
    id: 1000004,
    name: "Cálculo diferencial",
    prerequisites: [1000001],
  },
  1000005: {
    id: 1000005,
    name: "Cálculo integral",
    prerequisites: [1000004],
  },
  1000006: {
    id: 1000006,
    name: "Cálculo en Varias Variables",
    prerequisites: [1000005],
  },
  1000003: {
    id: 1000003,
    name: "Álgebra Lineal",
    prerequisites: [1000004],
  },
  1000013: {
    id: 1000013,
    name: "Probabilidad y Estadística Fundamental",
    prerequisites: [1000005],
  },
  1000019: {
    id: 1000019,
    name: "Fundamentos de Mecánica",
    prerequisites: [1000004],
  }, 
  1000017: {
    id: 1000017,
    name: "Fundamentos de Electricidad y Magnetismo",
    prerequisites: [1000005,1000019],
  },
  2015174: {
    id: 2015174,
    name: "Introducción a la Teoría de la Computación",
    prerequisites: [2015184,2025963],
  },
  2016696: {
    id: 2016696,
    name: "Algoritmos",
    prerequisites: [2016699,2015184,2025963],
  },
  2025963: {
    id: 2025963,
    name: "Matemáticas Discretas I",
    prerequisites: [1000003],
  }, 
  2025964: {
    id: 2025964,
    name: "Matemáticas Discretas II",
    prerequisites: [2025963,2015184],
  }, 
  2015970: {
    id: 2015970,
    name: "Métodos Numéricos",
    prerequisites: [1000006],
  }, 
  2016375: {
    id: 2016375,
    name: "Programación Orientada a Objetos",
    prerequisites: [2015734], 
  },
  2016699: {
    id: 2016699,
    name: "Estructuras de Datos",
    prerequisites: [2016375], 
  },
  2016701: {
    id: 2016701,
    name: "Ingeniería de Software I",
    prerequisites: [2016353, 2016703, 2016699],
  },
  2016702: {
    id: 2016702,
    name: "Ingeniería de Software II",
    prerequisites: [2016701, 2025967], 
  },
  2016716: {
    id: 2016716,
    name: "Arquitectura de Software",
    prerequisites: [2016702], 
  },
  2015734: {
    id: 2015734,
    name: "Programación de Computadores",
  },
  2025966: {
    id: 2025966,
    name: "Lenguajes de Programación",
    prerequisites: [2016699, 2015174], 
  },
  2016697: {
    id: 2016697,
    name: "Arquitectura de Computadores",
    prerequisites: [2016698], 
  },
  2016707: {
    id: 2016707,
    name: "Sistemas Operativos",
    prerequisites: [2016697], 
  },
  2025967: {
    id: 2025967,
    name: "Redes de Computadores",
    prerequisites: [1000017, 2016699, 2016697], 
  },
  2016722: {
    id: 2016722,
    name: "Computación Paralela y Distribuida",
    prerequisites: [2016696], 
  },
  2025983: {
    id: 2025983,
    name: "Arquitectura de Infraestructura y gobierno de TICs",
    prerequisites: [2016702, 2025982], 
  },
  2016698: {
    id: 2016698,
    name: "Elementos de Computadores",
    prerequisites: [2025975], 
  },
  2016353: {
    id: 2016353,
    name: "Bases de Datos",
    prerequisites: [2016375], 
  },
  2025982: {
    id: 2025982,
    name: "Sistemas de Información",
    prerequisites: [2016353, 2016703], 
  },
  2025972: {
    id: 2025972,
    name: "Introducción a la Criptografía y a la Seguridad de la Información",
    prerequisites: [2016696],
  },
  2025960: {
    id: 2025960,
    name: "Computación Visual",
    prerequisites: [2016696], 
  },
  2025995: {
    id: 2025995,
    name: "Introducción a los Sistemas Inteligentes",
    prerequisites: [2016696], 
  },
  2016703: {
    id: 2016703,
    name: "Pensamiento Sistémico",
  },
  2025970: {
    id: 2025970,
    name: "Modelos y Simulación",
    prerequisites: [1000006, 2025964, 1000013, 2016375], 
  },
  2019082: {
    id: 2019082,
    name: "Modelos matemáticos I",
    prerequisites: [1000006, 2025964, 1000013, 2016375], 
  },
  2025971: {
    id: 2025971,
    name: "Optimización",
    prerequisites: [2025970], 
  },
  2025975: {
    id: 2025975,
    name: "Introducción a la Ingeniería de Sistemas y Computación",
  },
}
export default MATTERS;
