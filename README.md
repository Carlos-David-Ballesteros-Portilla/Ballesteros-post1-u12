# ballesteros-post1-u12

Aplicación Spring Boot contenedorizada con Docker y desplegada en Railway.  
Laboratorio Post-Contenido 1 — Unidad 12: Despliegue y CI/CD.

## Aplicación en producción

**URL pública:** https://ballesteros-post1-u12-production.up.railway.app

Verificar estado:
```
GET https://ballesteros-post1-u12-production.up.railway.app/actuator/health
```
Respuesta esperada: `{"status":"UP"}`

---

## Requisitos para ejecutar localmente

- Docker Desktop instalado y en ejecución
- Maven 3.8+ o el wrapper `mvnw` del proyecto

---

## Construcción de la imagen Docker

Desde la raíz del proyecto:

```bash
docker build -t ballesteros-post1-u12:local .
```

Verificar que la imagen fue creada:

```bash
docker images | grep ballesteros-post1-u12
```

La imagen final usa `eclipse-temurin:21-jre-alpine` y debe pesar menos de 300 MB.

---

## Ejecución local con Docker Compose

El archivo `docker-compose.yml` orquesta dos servicios: la aplicación Spring Boot (`app`) y una base de datos PostgreSQL (`db`).

### Variables de entorno usadas por Docker Compose

| Variable | Valor en Compose | Descripción |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | `prod` | Activa el perfil de producción |
| `DATABASE_URL` | `jdbc:postgresql://db:5432/appdb` | URL de conexión a PostgreSQL |
| `DB_USER` | `appuser` | Usuario de la base de datos |
| `DB_PASS` | `apppass` | Contraseña de la base de datos |

### Levantar el stack completo

```bash
docker compose up -d --build
```

### Verificar que ambos servicios están corriendo

```bash
docker compose ps
```

Ambos servicios deben aparecer en estado `Up` / `healthy`.

### Probar la aplicación localmente

```bash
# Health check
curl http://localhost:8080/actuator/health

# Endpoints REST de la aplicación
curl http://localhost:8080/api/productos
```

### Detener los servicios

```bash
docker compose down
```

Para eliminar también el volumen de datos de PostgreSQL:

```bash
docker compose down -v
```

---

## Despliegue en Railway

### Variables de entorno configuradas en Railway

Las siguientes variables se configuraron en el panel "Variables" del servicio de la aplicación en Railway. Los valores de `DATABASE_URL`, `DB_USER` y `DB_PASS` se referencian directamente desde el servicio PostgreSQL aprovisionado por Railway.

| Variable | Valor referenciado en Railway |
|---|---|
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `DATABASE_URL` | `${{Postgres.DATABASE_URL}}` |
| `DB_USER` | `${{Postgres.PGUSER}}` |
| `DB_PASS` | `${{Postgres.PGPASSWORD}}` |

### Proceso de despliegue

1. Repositorio conectado a Railway desde GitHub ("Deploy from GitHub repo").
2. Railway detecta el `Dockerfile` automáticamente y construye la imagen.
3. Se aprovisionó un servicio PostgreSQL desde el panel del proyecto.
4. Se configuraron las variables de entorno referenciando el servicio de base de datos.
5. Se generó el dominio público desde "Settings" → "Networking" → "Generate Domain".

---

## Estructura del repositorio

```
ballesteros-post1-u12/
├── src/
│   └── main/
│       └── resources/
│           ├── application.properties
│           └── application-prod.properties
├── Dockerfile
├── .dockerignore
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Perfil de producción (`application-prod.properties`)

El perfil `prod` externaliza toda la configuración sensible a variables de entorno, desactiva la consola SQL y restringe los endpoints de Actuator a `health` e `info`.

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.root=WARN
management.endpoints.web.exposure.include=health,info
```
