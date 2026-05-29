# Guía de Prueba - Trebol Backend API

## Estado de la Aplicación

✅ **Backend Corriendo:** http://localhost:8080  
✅ **Base de Datos:** MySQL 8.0.44 en localhost:3306  
✅ **Endpoints Probados:** Creación de usuarios funcional

---

## Endpoints Disponibles

### 1. Crear Usuario (POST)

**URL:** `POST http://localhost:8080/api/usuarios`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@test.com",
  "password": "secure123",
  "telefono": "3001234567",
  "direccion": "Calle Principal 123"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@test.com"
}
```

---

## Ejemplos de Prueba

### Usando PowerShell

```powershell
# Crear usuario
$body = @{
  nombre="Juan"
  apellido="Pérez"
  correo="juan@test.com"
  password="secure123"
  telefono="3001234567"
  direccion="Calle Principal 123"
} | ConvertTo-Json

Invoke-WebRequest -Uri http://localhost:8080/api/usuarios `
  -Method POST `
  -ContentType "application/json" `
  -Body $body | Select-Object -ExpandProperty Content
```

### Usando cURL (Git Bash o similar)

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre":"Juan",
    "apellido":"Pérez",
    "correo":"juan@test.com",
    "password":"secure123",
    "telefono":"3001234567",
    "direccion":"Calle Principal 123"
  }'
```

### Usando Postman o Insomnia

1. **Crear nueva request**
   - Método: POST
   - URL: `http://localhost:8080/api/usuarios`
   
2. **Headers:**
   - `Content-Type: application/json`

3. **Body (raw JSON):**
   ```json
   {
     "nombre": "Juan",
     "apellido": "Pérez",
     "correo": "juan@test.com",
     "password": "secure123",
     "telefono": "3001234567",
     "direccion": "Calle Principal 123"
   }
   ```

4. **Click Send**

---

## Configuración de Seguridad (Desarrollo)

**Estado Actual:**
- ✅ CORS habilitado para todas las rutas (`*`)
- ✅ CSRF deshabilitado (desarrollo)
- ✅ `/api/usuarios` permitido sin autenticación
- ✅ Todas las rutas públicas en desarrollo

**Para Producción:**
- [ ] Cambiar CORS a orígenes específicos
- [ ] Habilitar CSRF
- [ ] Implementar autenticación JWT
- [ ] Cambiar contraseña de BD (actualmente: root/sofia)
- [ ] Usar variable de entorno para secreto JWT

---

## Base de Datos

**Configuración:**
```properties
URL: jdbc:mysql://localhost:3306/trebol_db
Usuario: root
Contraseña: sofia
Driver: MySQL 8.0
```

**Tablas Creadas Automáticamente:**
- `usuarios` - Almacena datos de usuarios
- `roles` - Estructura para roles (vacía por ahora)

**Validaciones en BD:**
- Email único (`correo` UNIQUE)
- Auto-incremento de IDs

---

## Próximos Pasos / Endpoints Pendientes

- [ ] GET `/api/usuarios` - Listar todos los usuarios
- [ ] GET `/api/usuarios/{id}` - Obtener usuario por ID
- [ ] PUT `/api/usuarios/{id}` - Actualizar usuario
- [ ] DELETE `/api/usuarios/{id}` - Eliminar usuario
- [ ] POST `/api/auth/login` - Autenticación con JWT
- [ ] POST `/api/auth/register` - Registro de nuevos usuarios

---

## Logs Disponibles

La aplicación registra todas las consultas SQL. En `application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Visualizar en:** Terminal donde corre `mvn spring-boot:run`

---

## Troubleshooting

**Error: Puerto 8080 en uso**
```powershell
Stop-Process -Name java -Force
```

**Error: Conexión a BD rechazada**
- Verificar que MySQL está corriendo
- Revisar credenciales en `application.properties`

**Error: CORS bloqueado (en frontend)**
- Ya está configurado para desarrollo
- Para producción, modificar `SecurityConfig.java`

---

## Arquitectura

```
trebol-backend/
├── src/main/java/com/trebol/
│   ├── entity/          # Modelos JPA (@Entity)
│   ├── dto/             # DTOs para request/response
│   ├── controller/      # Endpoints REST
│   ├── service/         # Lógica de negocio
│   ├── repository/      # Acceso a BD (JPA)
│   ├── security/        # JWT + Spring Security
│   ├── exception/       # Manejo de excepciones
│   └── response/        # Wrapper genérico de respuestas
└── src/main/resources/
    └── application.properties  # Configuración
```

**Stack Tecnológico:**
- **Framework:** Spring Boot 3.5.0
- **Base de Datos:** MySQL 8.0 + JPA/Hibernate
- **Seguridad:** Spring Security + JWT (JJWT 0.12.3)
- **Build:** Maven
- **Java:** JDK 21
- **Lombok:** 1.18.32 (generación automática de getters/setters)

---

Última actualización: 2026-05-28
