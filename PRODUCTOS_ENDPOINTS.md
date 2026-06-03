# Endpoint Búsqueda y Filtrado de Productos

## Descripción General

El sistema ahora incluye un endpoint de búsqueda avanzada que permite filtrar productos por nombre, categoría y rango de precios. Este endpoint está diseñado para soportar catálogos dinámicos donde los productos se crean desde el panel de administrador.

## Endpoints Disponibles

### 1. Listar todos los Productos

**URL:** `GET http://localhost:8080/api/productos`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Monstera Deliciosa",
    "descripcion": "Planta tropical perfecta para interiores",
    "sku": "MONS-001",
    "precio": 890,
    "stock": 15,
    "imagenPrincipal": "https://example.com/image.jpg",
    "categoria": {
      "id": 1,
      "nombre": "Plantas de Interior",
      "descripcion": "Plantas ideales para espacios internos",
      "imagen": "https://example.com/category.jpg"
    },
    "tipoProducto": "PLANTA",
    "requiereCuidados": true,
    "estado": true,
    "slug": "monstera-deliciosa",
    "fechaCreacion": "2026-06-03T10:30:00"
  }
]
```

---

### 2. Búsqueda Avanzada de Productos

**URL:** `GET http://localhost:8080/api/productos/buscar`

**Parámetros Query (todos opcionales):**
- `nombre` (string): Busca en nombre y descripción
- `categoriaId` (long): Filtra por ID de categoría
- `precioMin` (decimal): Precio mínimo (inclusive)
- `precioMax` (decimal): Precio máximo (inclusive)

**Ejemplos:**

#### Buscar por nombre
```
GET http://localhost:8080/api/productos/buscar?nombre=monstera
```

#### Filtrar por categoría
```
GET http://localhost:8080/api/productos/buscar?categoriaId=1
```

#### Rango de precio
```
GET http://localhost:8080/api/productos/buscar?precioMin=100&precioMax=1000
```

#### Combinado
```
GET http://localhost:8080/api/productos/buscar?nombre=planta&categoriaId=1&precioMin=500&precioMax=1500
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Monstera Deliciosa",
    "precio": 890,
    "categoria": { "id": 1, "nombre": "Plantas de Interior" },
    ...
  }
]
```

---

### 3. Obtener Producto por ID

**URL:** `GET http://localhost:8080/api/productos/{id}`

**Path Parameters:**
- `id` (long): ID del producto

**Example:**
```
GET http://localhost:8080/api/productos/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "nombre": "Monstera Deliciosa",
  "descripcion": "Planta tropical perfecta para interiores",
  "precio": 890,
  "stock": 15,
  "categoria": {
    "id": 1,
    "nombre": "Plantas de Interior"
  },
  ...
}
```

---

### 4. Crear Producto

**URL:** `POST http://localhost:8080/api/productos`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {token}  # Requiere autenticación
```

**Body (JSON):**
```json
{
  "nombre": "Ficus Lyrata",
  "descripcion": "Árbol de violín, planta de interior elegante",
  "sku": "FICU-001",
  "precio": 1290,
  "stock": 8,
  "imagenPrincipal": "https://example.com/ficus.jpg",
  "categoriaId": 1,
  "tipoProducto": "PLANTA",
  "requiereCuidados": true,
  "estado": true,
  "slug": "ficus-lyrata"
}
```

**Response (201 Created):**
```json
{
  "id": 2,
  "nombre": "Ficus Lyrata",
  "precio": 1290,
  "categoria": { "id": 1, "nombre": "Plantas de Interior" },
  ...
}
```

---

### 5. Actualizar Producto

**URL:** `PUT http://localhost:8080/api/productos/{id}`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {token}  # Requiere autenticación
```

**Path Parameters:**
- `id` (long): ID del producto a actualizar

**Body (JSON):** (campos opcionales)
```json
{
  "precio": 1100,
  "stock": 20,
  "estado": true
}
```

**Response (200 OK):**
```json
{
  "id": 2,
  "nombre": "Ficus Lyrata",
  "precio": 1100,
  "stock": 20,
  ...
}
```

---

### 6. Eliminar Producto

**URL:** `DELETE http://localhost:8080/api/productos/{id}`

**Headers:**
```
Authorization: Bearer {token}  # Requiere autenticación
```

**Path Parameters:**
- `id` (long): ID del producto a eliminar

**Response (204 No Content)**
```
(Sin cuerpo)
```

---

## Categorías Disponibles

Para obtener las categorías que existen en el sistema:

**URL:** `GET http://localhost:8080/api/categorias`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Plantas de Interior",
    "descripcion": "Plantas ideales para espacios internos",
    "imagen": "https://example.com/interior.jpg"
  },
  {
    "id": 2,
    "nombre": "Plantas de Exterior",
    "descripcion": "Plantas resistentes para jardín",
    "imagen": "https://example.com/exterior.jpg"
  }
]
```

---

## Ejemplos de Prueba

### PowerShell

```powershell
# Listar todos los productos
Invoke-RestMethod -Uri "http://localhost:8080/api/productos" `
  -Method GET | ConvertTo-Json

# Buscar productos por nombre
Invoke-RestMethod -Uri "http://localhost:8080/api/productos/buscar?nombre=monstera" `
  -Method GET | ConvertTo-Json

# Filtrar por rango de precio
Invoke-RestMethod -Uri "http://localhost:8080/api/productos/buscar?precioMin=500&precioMax=1500" `
  -Method GET | ConvertTo-Json

# Crear producto (requiere token)
$body = @{
  nombre = "Sansevieria"
  descripcion = "Planta de bajo mantenimiento"
  sku = "SANS-001"
  precio = 490
  stock = 25
  imagenPrincipal = "https://example.com/sansevieria.jpg"
  categoriaId = 1
  tipoProducto = "PLANTA"
  requiereCuidados = $false
  estado = $true
  slug = "sansevieria"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/productos" `
  -Method POST `
  -Headers @{
    "Authorization" = "Bearer YOUR_TOKEN_HERE"
    "Content-Type" = "application/json"
  } `
  -Body $body | ConvertTo-Json
```

### cURL

```bash
# Listar todos los productos
curl http://localhost:8080/api/productos

# Buscar por nombre
curl "http://localhost:8080/api/productos/buscar?nombre=monstera"

# Filtrar por rango de precio
curl "http://localhost:8080/api/productos/buscar?precioMin=500&precioMax=1500"

# Crear producto
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "nombre": "Sansevieria",
    "descripcion": "Planta de bajo mantenimiento",
    "precio": 490,
    "stock": 25,
    "imagenPrincipal": "https://example.com/sansevieria.jpg",
    "categoriaId": 1,
    "tipoProducto": "PLANTA",
    "requiereCuidados": false,
    "estado": true,
    "slug": "sansevieria"
  }'
```

---

## Validaciones

### Crear/Actualizar Producto

| Campo | Tipo | Validación |
|-------|------|-----------|
| nombre | String | Requerido, max 255 caracteres |
| descripcion | String | Opcional, max 1000 caracteres |
| sku | String | Requerido, único |
| precio | BigDecimal | Requerido, mayor a 0 |
| stock | Integer | Requerido, mayor o igual a 0 |
| imagenPrincipal | String | Opcional, URL válida |
| categoriaId | Long | Opcional, debe existir en BD |
| tipoProducto | Enum | Requerido (PLANTA, ACCESORIO, SERVICIO) |
| requiereCuidados | Boolean | Requerido |
| estado | Boolean | Requerido (true=activo, false=inactivo) |
| slug | String | Requerido, único, lowercase con guiones |

---

## Notas Importantes

1. **Filtrado Dinámico**: El frontend extrae automáticamente las categorías disponibles de los productos en el catálogo
2. **Productos en Tiempo Real**: Cambios creados desde el panel administrador se reflejan inmediatamente en el catálogo
3. **Sin Datos Mock**: Todos los productos y categorías provienen de la base de datos
4. **Búsqueda Flexible**: El parámetro `nombre` busca en nombre y descripción del producto
5. **Combinación de Filtros**: Todos los parámetros de búsqueda pueden combinarse para refinar resultados

---

## HTTP Status Codes

| Código | Significado |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado correctamente |
| 204 | No Content - Operación exitosa sin cuerpo |
| 400 | Bad Request - Datos inválidos |
| 401 | Unauthorized - Requiere autenticación |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error en el servidor |
