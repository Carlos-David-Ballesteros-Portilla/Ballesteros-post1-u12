# Ballesteros-post2-u11 — Logging con SLF4J/Logback y Documentación con Swagger/OpenAPI

Proyecto Spring Boot desarrollado para la Unidad 11 de Programación Web (UDES 2026).  
Extiende el laboratorio anterior agregando logging estructurado con SLF4J/Logback  
y documentación interactiva de la API con springdoc-openapi (Swagger UI).

---

## Arquitectura en capas

```
controller/        ← Recibe peticiones HTTP, anotado con @Tag, @Operation, @ApiResponse
service/           ← Lógica de negocio con logging SLF4J (INFO, WARN, DEBUG)
repository/        ← Acceso a datos, extiende JpaRepository (patrón DAO)
dto/               ← Objetos de transferencia anotados con @Schema
entity/            ← Entidad JPA mapeada a la base de datos
factory/           ← Conversión entre entidad y DTOs
exception/         ← Manejo centralizado de errores con @RestControllerAdvice
```

---

## Requisitos

- Java 17+
- Maven 3.9.x
- Spring Boot 3.2.x o superior

---

## Ejecución

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/Ballesteros-post2-u11.git
cd Ballesteros-post2-u11

# Iniciar la aplicación
mvn spring-boot:run
```

La app queda disponible en `http://localhost:8080`.

---

## Swagger UI

Una vez iniciada la aplicación, la documentación interactiva está disponible en:

```
http://localhost:8080/swagger-ui.html
```

El JSON de la especificación OpenAPI está en:

```
http://localhost:8080/api-docs
```

---

## Archivos de Log

Los logs se escriben simultáneamente en consola y en archivo.  
El archivo de log se encuentra en:

```
logs/catalogo.log
```

Con rotación diaria, los logs históricos se guardan como:

```
logs/catalogo.YYYY-MM-DD.log
```

Se conservan hasta 30 días de historial. La carpeta `logs/` está en `.gitignore`.

---

## Niveles de Log configurados

| Paquete | Nivel |
|---------|-------|
| `com.empresa.catalogo` | DEBUG |
| Global | INFO |

---

## Endpoints disponibles

| Método | URL | Descripción | Status |
|--------|-----|-------------|--------|
| GET | /api/productos | Lista todos los productos activos | 200 |
| GET | /api/productos/{id} | Busca un producto por id | 200 |
| POST | /api/productos | Crea un nuevo producto | 201 |
| DELETE | /api/productos/{id} | Elimina un producto por id | 204 |

---

## Checkpoints verificados

---

### Checkpoint 1 — SLF4J en el Servicio

Al ejecutar `mvn spring-boot:run` y realizar un POST y un GET a `/api/productos`, los mensajes de log aparecen en consola con el formato configurado.

Ejemplo de salida esperada:
```
INFO  ProductoServiceImpl - Creando producto: nombre=Laptop, categoria=ELECTRONICA
INFO  ProductoServiceImpl - Producto creado exitosamente con id=1
```

**Evidencia:**

![Checkpoint 1](docs/check%201.png)

---

### Checkpoint 2 — Logback con archivo de log

Tras reiniciar la aplicación y realizar operaciones, el archivo `logs/catalogo.log` existe y contiene los mensajes con formato de fecha completo.

**Evidencia:**

![Checkpoint 2](docs/check%202.png)

---

### Checkpoint 3 — Swagger UI

Swagger UI accesible en `http://localhost:8080/swagger-ui.html` muestra el grupo "Productos" con los endpoints POST, GET por id, GET todos y DELETE documentados. Cada endpoint muestra las respuestas 200/201, 400 y 404.

**Evidencia:**

![Checkpoint 3](docs/check%203.png)
