Proyecto Final - Sistema de Gestión para Restaurante

Integrantes:

Emanuel Israel Finozzi

Guillermo José Hansen

Descripción general

Aplicación de escritorio desarrollada en Java (Swing) que permite gestionar las operaciones esenciales de un restaurante desde una interfaz moderna, modular y fácil de mantener.

El sistema implementa el patrón MVC (Model–Vista–Controlador) junto con una arquitectura por paquetes, separando claramente la interfaz, la lógica de negocio y la persistencia de datos.
La navegación entre pantallas se realiza mediante la clase Navegador, que utiliza un CardLayout para mantener una única ventana principal y cambiar entre vistas de forma fluida.

Funcionalidades principales

Gestión de Mesas: alta, baja, cambio de estado (Libre, Ocupada, Reservada) y asignación de mozo.

Carta / Menú: administración de productos, categorías, precios y stock.

Pedido y Pago: registro de pedidos, cálculo de subtotal y confirmación de pago.

Reporte Básico: resumen de ventas y total del día.

Estructura del proyecto

src/
├── ui/ → Interfaz gráfica (formularios .form y .java)
│ └── Menu_Principal.java
│ └── GestionDeMesa.java
│
├── model/ → Clases de datos (Mesa, Producto, Pedido, etc.)
│
├── service/ → Lógica de negocio y conexión entre vista y datos
│
├── repository/ → Persistencia binaria de objetos (.dat)
│ └── ArchivoRepository.java
│
├── util/ → Clases auxiliares y de navegación
│ └── Navegador.java
│
└── exception/ → Excepciones personalizadas

Ejecución del programa

Abrir el proyecto en IntelliJ IDEA.

Ejecutar la clase Main.java.

Se abrirá el Menú Principal, desde el cual se puede acceder a:

Gestión de Mesas

Carta / Menú

Pedido & Pago

Reporte del Día

La navegación entre vistas se realiza mediante CardLayout, controlado por la clase Navegador.

Tecnologías utilizadas

Java SE 17+

Swing (GUI Builder de IntelliJ IDEA)

Patrón MVC

Persistencia binaria (ObjectOutputStream / ObjectInputStream)

Arquitectura modular por paquetes

Estado actual del desarrollo

Menú Principal: completo
Estructura de paquetes: completa
Sistema de Navegación (Navegador): funcional
Persistencia binaria (ArchivoRepository): implementada
Gestión de Mesas: en desarrollo
Carta de Productos: pendiente
Pedido & Pago: pendiente
Reporte de Ventas: pendiente

Próximos pasos

Conectar la interfaz de Gestión de Mesas con su lógica mediante MesaService y MesaRepository.

Desarrollar las pantallas de Carta, Pedidos y Reportes.

Implementar validaciones, excepciones personalizadas y control de errores.

Realizar pruebas de integración y preparar la entrega final.

Créditos

Proyecto educativo sin fines comerciales, desarrollado como trabajo práctico de Programación II en Java Swing.
Facilita la comprensión de patrones de diseño, modularización y persistencia de datos en entornos de escritorio.