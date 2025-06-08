# 📚 Literalura

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Una aplicación de consola inteligente para gestionar tu biblioteca digital**

[Características](#-características) • [Instalación](#-instalación) • [Uso](#-uso) • [API](#-api-reference) • [Contribuir](#-contribuir)

</div>

---

## 🌟 Descripción

**Literalura** es una aplicación de consola desarrollada en Java con Spring Boot que transforma la gestión de catálogos literarios. Integra dinámicamente datos desde la API pública de **Gutendx** (libros de dominio público) con una base de datos PostgreSQL local, ofreciendo una experiencia fluida para explorar y organizar literatura clásica.

### ✨ ¿Por qué Literalura?

- 🔍 **Búsqueda inteligente**: Encuentra libros al instante desde miles de títulos disponibles
- 💾 **Persistencia local**: Tus búsquedas se guardan automáticamente para acceso offline
- 🚫 **Sin duplicados**: Sistema inteligente que evita redundancia de datos
- 🌍 **Multiidioma**: Soporte completo para literatura en diferentes idiomas
- ⚡ **Rápido y eficiente**: Arquitectura optimizada con Spring Boot

---

## 🚀 Características

### 🔧 Funcionalidades principales

| Función | Descripción | Estado |
|---------|-------------|---------|
| **Búsqueda de libros** | Busca por título en API Gutendx y almacena localmente | ✅ |
| **Gestión de autores** | Relación autor-libro con control de duplicados | ✅ |
| **Filtros avanzados** | Por año, idioma y criterios específicos | ✅ |
| **Interfaz CLI** | Menú interactivo intuitivo y amigable | ✅ |
| **Persistencia** | Base de datos PostgreSQL con JPA | ✅ |

### 🎯 Filtros especializados

- **📅 Autores por año**: Lista autores vivos en un período específico
- **🌐 Libros por idioma**: Filtra literatura por código de idioma (es, en, fr, etc.)
- **📖 Catálogo completo**: Visualiza toda tu biblioteca personal

---

## 🛠️ Stack tecnológico

<table>
<tr>
<td align="center"><strong>Backend</strong></td>
<td align="center"><strong>Base de datos</strong></td>
<td align="center"><strong>Herramientas</strong></td>
</tr>
<tr>
<td>

- Java 17+
- Spring Boot 3.5.0
- Spring Data JPA
- HttpClient
- Jackson
- Lombok

</td>
<td>

- PostgreSQL
- Hibernate ORM
- JPA Repositories
- Query Methods

</td>
<td>

- Maven
- Git
- IntelliJ IDEA
- Gutendx API

</td>
</tr>
</table>

---


### 📁 Estructura del proyecto

```
literalura/
├── src/main/java/com/literalura/
│   ├── controller/
│   │   └── LiteraluraController.java    # CLI Interface
│   ├── service/
│   │   └── GutendxService.java          # API Consumer
│   ├── repository/
│   │   ├── AuthorRepository.java        # Author Data Access
│   │   └── BookRepository.java          # Book Data Access
│   ├── model/
│   │   ├── Author.java                  # Author Entity
│   │   ├── Book.java                    # Book Entity
│   │   └── dto/                         # Data Transfer Objects
│   └── LiteraluraApplication.java       # Main Application
├── src/main/resources/
│   └── application.properties           # Configuration
└── README.md
```

---

---
### 🎮 Menú principal

Al iniciar la aplicación, verás este menú interactivo:

```
╔══════════════════════════════════════════════════════════════════╗
║                    📚 CATÁLOGO LITERALURA 📚                    ║
╠══════════════════════════════════════════════════════════════════╣
║  1️⃣  Buscar Libro por Título                                    ║
║  2️⃣  Listar Todos los Libros                                    ║
║  3️⃣  Listar Todos los Autores                                   ║
║  4️⃣  Buscar Autores Vivos por Año                               ║
║  5️⃣  Filtrar Libros por Idioma                                  ║
║  0️⃣  Salir                                                      ║
╚══════════════════════════════════════════════════════════════════╝
```

### 🔍 Ejemplos de uso

#### Buscar un libro
```
Opción: 1
Ingrese el título del libro: don quijote
🔍 Buscando "don quijote"...
✅ Libro encontrado y guardado:
   📖 "Don Quijote" por Miguel de Cervantes Saavedra
```

#### Filtrar por idioma
```
Opción: 5
Códigos disponibles: es (Español), en (Inglés), fr (Francés)...
Ingrese código de idioma: es
📚 Libros en español:
   📖 "Don Quijote" - Miguel de Cervantes
   📖 "La Celestina" - Fernando de Rojas
```

#### Buscar autores por año
```
Opción: 4
Ingrese el año: 1850
👥 Autores vivos en 1850:
   ✍️  Charles Dickens (1812-1870)
   ✍️  Gustave Flaubert (1821-1880)
```

---

## 🔌 API Reference

### Gutendx API Integration

La aplicación consume la API pública de [Gutendx](https://gutendx.com/):

```http
GET https://gutendx.com/books/?search={titulo}
```

**Respuesta ejemplo:**
```json
{
  "results": [
    {
      "id": 996,
      "title": "Don Quijote",
      "authors": [
        {
          "name": "Cervantes Saavedra, Miguel de",
          "birth_year": 1547,
          "death_year": 1616
        }
      ],
      "languages": ["es"],
      "download_count": 4821
    }
  ]
}
```
---

## 🚀 Roadmap

### ✅ Versión actual (v1.0)
- [x] Búsqueda básica de libros
- [x] Gestión de autores y libros
- [x] Filtros por año e idioma
- [x] Interface CLI

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 🙏 Agradecimientos

- **Gutendx API** por proporcionar acceso gratuito a literatura clásica
- **Spring Boot Team** por el excelente framework
- **PostgreSQL Community** por la robusta base de datos
- **Alura & Oracle** por la inspiración del challenge


<div align="center">

---

*Literalura © 2025 - Transformando la manera de explorar literatura clásica*

</div>