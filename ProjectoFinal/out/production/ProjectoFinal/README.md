# Proyecto Final – Sistema de Gestión para Restaurante

## Integrantes
- Emanuel Israel Finozzi
- Guillermo José Hansen

---

## Descripción general
Aplicación de escritorio desarrollada en **Java (Swing)** que permite gestionar las operaciones esenciales de un restaurante desde una interfaz moderna, modular y fácil de mantener.  

El sistema implementa el **patrón MVC (Modelo–Vista–Controlador)** junto con una **arquitectura por paquetes**, separando claramente la interfaz de usuario, la lógica de negocio y la persistencia de datos.  

La navegación entre pantallas se realiza mediante la clase `Navegador`, que utiliza un **CardLayout** para mantener una única ventana principal y cambiar entre vistas de forma fluida.

---

## Funcionalidades principales
- Gestión de Mesas: alta, baja, cambio de estado (Libre, Ocupada, Reservada) y asignación de mozo.  
- Carta / Menú: administración de productos, categorías, precios y stock.  
- Pedidos y Pagos: registro de pedidos, cálculo de subtotales y confirmación de pago.  
- Reportes: resumen de ventas y total del día.

---

## Estructura del proyecto

```
src/
├── ui/           → Interfaz gráfica (formularios .form y .java)
│
├── model/        → Clases de datos (Mesa, Producto, Pedido, etc.)
│
├── service/      → Lógica de negocio y conexión entre vista y datos
│
├── repository/   → Persistencia binaria de objetos (.dat)
│
├── util/         → Clases auxiliares y de navegación
│
└── exception/    → Excepciones personalizadas
```

---

## 1. Paquete `ui/` – Interfaz Gráfica

### Menu_Principal.java
Pantalla inicial de la aplicación. Contiene los botones para navegar hacia las demás secciones: Gestión de Mesas, Carta, Pedidos y Reporte del Día.  
Se integra con `Navegador` para cambiar de vista mediante `CardLayout`.

### GestionDeMesa.java
Interfaz para crear, eliminar o modificar mesas.  
Utiliza `MesaService` para aplicar los cambios en el modelo y persistirlos mediante `MesaRepository`.

### Carta.java
Pantalla para administrar los productos del menú.  
Permite dar de alta comidas, bebidas o postres.  
Interactúa con `ProductoService` para actualizar la persistencia binaria.

### Pedidos_Pagos.java
Formulario que permite registrar los pedidos de una mesa, calcular subtotales y confirmar pagos.  
Utiliza las clases `MesaService`, `ProductoService` y `PedidoRepository`.  
Incluye control de stock mediante la excepción `StockInsuficienteException`.

### ReporteBasico.java
Muestra un resumen de todas las ventas registradas durante el día.  
Obtiene los datos desde `ReporteService` y los formatea para ser visualizados en una tabla.

---

## 2. Paquete `model/` – Clases de Datos

### mesa.java
Modelo de datos que representa una mesa.  
Contiene los atributos `id`, `mozo`, `nombre` y `estado`.

### Producto.java
Clase abstracta que define la estructura básica de un producto de la carta:
- `id`, `nombre`, `categoria`, `precio`, `stock`, `activo`.
- Método abstracto `obtenerTipo()` que obliga a las subclases a definir su tipo.

### Comida.java, Bebida.java, Postre.java
Subclases concretas que extienden `Producto` e implementan `obtenerTipo()` con su respectiva categoría.  

### Pedido.java
Representa un pedido realizado en una mesa, con su `fechaHora`, `total`, `método de pago` y lista de ítems.  
Se serializa para ser guardado por `PedidoRepository`.

---

## 3. Paquete `repository/` – Persistencia de Datos

### ArchivoRepository.java
Clase genérica para persistencia binaria de listas de objetos serializables (`Serializable`).  
Provee los métodos:
```java
guardar(List<T> datos, String nombreArchivo);
cargar(String nombreArchivo);
```
Usa `ObjectOutputStream` y `ObjectInputStream` para almacenar y recuperar datos desde archivos `.dat`.

### ProductoRepository.java
Gestiona los productos de la carta en `data/carta/productos.dat`.  

### MesaRepository.java
Maneja las mesas en `data/mesas/mesas.dat`.  

### PedidoRepository.java
Gestiona la lista de pedidos del día (`data/pedidos/pedidos_diarios.dat`).  

---

## 4. Paquete `service/` – Lógica de Negocio

### IProductoService.java
Define el contrato de operaciones sobre los productos.

### ProductoService.java
Implementación concreta de `IProductoService`.  
Controla la lógica de:
- alta y modificación de productos según categoría,  
- persistencia automática tras cada cambio,  
- generación de tablas para la UI.

### MesaService.java
Gestiona la lista de mesas: agregar, eliminar, cambiar estado, asignar mozo.  
Usa `MesaRepository` para guardar los cambios.

### ReporteService.java
Obtiene los pedidos desde `PedidoRepository` y calcula el total diario y los datos para las tablas del reporte.

---

## 5. Paquete `util/` – Utilidades

### Navegador.java
Controla la navegación de pantallas con `CardLayout`.  
Mantiene una sola ventana principal y permite cambiar entre vistas de manera fluida y modular.

---

## 6. Paquete `exception/` – Excepciones Personalizadas

### StockInsuficienteException.java
Se lanza cuando se intenta registrar un pedido con más cantidad de la disponible en stock.

### MesaNoDisponibleException.java
Se utiliza al intentar reservar o asignar una mesa que ya está ocupada.

---

## 7. Validadores

### ProductoValidator.java
Valida los datos de entrada antes de crear o modificar un producto (nombre, precio, stock y categoría).

### MesaValidator.java
Verifica los campos requeridos para una mesa (nombre, mozo, estado).

---

## 8. Main – Punto de Entrada

### Main.java
Inicia la aplicación, instancia el `Navegador` y carga la vista `Menu_Principal`.
```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Navegador().mostrarMenuPrincipal());
}
```

---

## Persistencia Binaria
Los datos se almacenan en la carpeta `data/`, dentro de subcarpetas específicas:
```
data/
├── carta/     → productos.dat
├── mesas/     → mesas.dat
└── pedidos/   → pedidos_diarios.dat
```
Se usa `ObjectOutputStream` y `ObjectInputStream` para guardar y recuperar las listas completas de objetos.

---

## Patrón de Diseño MVC
- Modelo: clases del paquete `model/`.  
- Vista: clases de `ui/`.  
- Controlador / Lógica: clases de `service/`.  
- Persistencia: `repository/`.  

El `Navegador` actúa como controlador de flujo entre vistas, manteniendo la independencia entre la UI y la lógica.

---

## Estado actual del desarrollo
- Menú principal: completo  
- Estructura de paquetes: completa  
- Navegador: funcional  
- Persistencia binaria: implementada  
- Gestión de mesas: completa  
- Carta de productos: completa  
- Pedidos y pagos: funcional  
- Reporte de ventas: funcional  
- Validaciones y excepciones: integradas  

---

## Créditos
Proyecto educativo sin fines comerciales, desarrollado como trabajo práctico final de **Programación II**.  
Permite ejercitar conceptos de programación orientada a objetos, modularización, persistencia binaria y patrones de diseño en aplicaciones de escritorio.
