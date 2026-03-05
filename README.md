# Sistema de Reservas (Java – Consola)

Aplicación de consola desarrollada en **Java** para gestionar reservas mediante un menú interactivo.  
El sistema permite crear, consultar, actualizar y eliminar reservas, aplicando reglas de negocio para validar horarios, fechas y conflictos.

Este proyecto fue desarrollado con fines de **aprendizaje y práctica de programación orientada a objetos**.


## Cómo ejecutar el proyecto

1. Clonar el repositorio
2. Abrir el proyecto en tu IDE (IntelliJ, Eclipse o VSCode)
3. Ejecutar la clase `Main`

El sistema se ejecutará en consola mostrando el menú interactivo.


## Funcionalidades

El sistema permite realizar las siguientes operaciones:

1. **Crear reserva**
2. **Ver todas las reservas**
3. **Buscar reserva por ID**
4. **Modificar reserva**
5. **Eliminar reserva**
6. **Consultar horarios disponibles**

Cada reserva contiene:

- ID único
- Nombre del cliente
- Fecha
- Hora


## Reglas del sistema

El sistema aplica varias validaciones para asegurar la consistencia de las reservas:

- No se permiten **fechas pasadas**
- Si la reserva es para **hoy**, la hora debe ser **posterior a la actual**
- Las reservas solo se permiten entre **08:00 y 18:00**
- Los horarios solo pueden crearse en **intervalos de 30 minutos**
    - Ejemplo: `08:00`, `08:30`, `09:00`, `09:30`
- No se permiten **dos reservas en la misma fecha y hora**


## Estructura del proyecto

El proyecto está organizado en tres clases principales:

### Reserva
Representa una reserva individual.

Contiene:

- ID
- nombre
- fecha
- hora

Incluye validaciones básicas en los setters.


### ReservaService
Contiene la **lógica de negocio del sistema**.

Responsabilidades:

- Crear reservas
- Buscar reservas
- Actualizar reservas
- Eliminar reservas
- Generar horarios disponibles
- Validar reglas del sistema
- Detectar conflictos de horarios

También mantiene un campo `ultimoError` para informar al usuario cuando una operación falla.


### Main
Actúa como **interfaz de usuario en consola**.

Responsabilidades:

- Mostrar el menú
- Leer datos del usuario
- Validar entradas
- Llamar a `ReservaService`
- Mostrar resultados y mensajes de error


# Tecnologías utilizadas

- **Java**
- API `java.time` (LocalDate, LocalTime)
- Programación Orientada a Objetos
- Estructuras de datos (`ArrayList`)


# Posibles mejoras futuras

- Persistencia con **base de datos**
- Interfaz gráfica (JavaFX / Swing)
- API REST (Spring Boot)
- Validaciones más avanzadas de disponibilidad
- Manejo de múltiples salas o recursos


# Autor

Israel Liranzo

Proyecto desarrollado como ejercicio de práctica en Java.