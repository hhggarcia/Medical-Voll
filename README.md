
# Api VollMed

VollMed es una api de administración de citas médicas. El proyecto está implementado utilizando Spring Boot, Spring Security y una base de datos PostgreSQL. A continuación, se describen los principales componentes y funcionalidades del proyecto:

## Tech Stack

**Backend**: Spring Boot, Spring Security

**Base de Datos**: PostgreSQL.

**Autenticación**: JWT (JSON Web Tokens)

**ORM**: Hibernate (JPA)

**Controladores REST**: Implementados para gestionar medicos, pacientes y consultas.

## API Referencias

### Autenticación

```http
  POST /login
```
Cuerpo de la solicitud:
| Propiedad | Tipo     | Descripción
| :-------- | :------- | :-------------------------------- |
| `login`      | `String` | **Requerido**. Nombre del usuario |
| `clave`      | `String` | **Requerido**. Contraseña |

### Medicos
#### Obtener

```http
  GET /medicos
```
#### Detalle

```http
  GET /medicos/{id}
```

#### Registrar
```http
  POST /medicos
```
Cuerpo de la solicitud:
| Propiedad | Tipo     | Descripción
| :-------- | :------- | :-------------------------------- |
| `nombre`      | `String` | **Requerido**. Nombre del médico |
| `email`      | `String` | **Requerido**. Correo a utilizar |
| `documento`      | `String` | **Requerido**. documento de identificación |
| `telefono`      | `String` | **Requerido**. número celular del médico  |
| `especialidad`      | `Especialidad` | **Requerido**. valores aceptados: ORTOPEDIA, CARDIOLOGIA, GINECOLOGIA Y PEDIATRIA  |
| `datosDireccion`      | `DatosDireccion` | **Requerido**. Objeto con las siguientes propiedades: calle, distrito, ciudad, numero, complemento.  |

#### Actualizar
```http
  PUT /medicos/{id}
```
Cuerpo de la solicitud:
| Propiedad | Tipo     | Descripción
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Requerido**. Id del médico a actualizar |
| `nombre`      | `String` | **Requerido**. Nombre del médico |
| `documento`      | `String` | **Requerido**. documento de identificación |
| `datosDireccion`      | `DatosDireccion` | **Requerido**. Objeto con las siguientes propiedades: calle, distrito, ciudad, numero, complemento.  |

#### Eliminar

```http
  DELETE /medicos/{id}
```

### Pacientes
#### Obtener

```http
  GET /pacientes
```

#### Registrar
```http
  POST /pacientes
```
Cuerpo de la solicitud:
| Propiedad | Tipo     | Descripción
| :-------- | :------- | :-------------------------------- |
| `nombre`      | `String` | **Requerido**. Nombre del pacientes |
| `email`      | `String` | **Requerido**. Correo a utilizar |
| `documento`      | `String` | **Requerido**. documento de identificación |
| `telefono`      | `String` | **Requerido**. Número celular del paciente  |
| `datosDireccion`      | `DatosDireccion` | **Requerido**. Objeto con las siguientes propiedades: calle, distrito, ciudad, numero, complemento.  |

#### Actualizar
```http
  PUT /pacientes/{id}
```
Cuerpo de la solicitud:
| Propiedad | Tipo     | Descripción
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Requerido**. Id del paciente a actualizar |
| `nombre`      | `String` | **Requerido**. Nombre del paciente |
| `telefono`      | `String` | **Requerido**. Número celular del paciente  |
| `datosDireccion`      | `DatosDireccion` | **Requerido**. Objeto con las siguientes propiedades: calle, distrito, ciudad, numero, complemento.  |

#### Eliminar

```http
  DELETE /pacientes/{id}
```

### Consultas

#### Reservar
```http
  POST /consultas
```
Cuerpo de la solicitud:
| Propiedad | Tipo     | Descripción
| :-------- | :------- | :-------------------------------- |
| `idMedico`      | `String` | **Requerido**. Id del médico seleccionado |
| `idPaciente`      | `String` | **Requerido**. Id del paciente que realiza la consulta |
| `fecha`      | `String` | **Requerido**. fecha elegida de la consulta. |
| `especialidad`      | `Especialidad` | **Requerido**. valores aceptados: ORTOPEDIA, CARDIOLOGIA, GINECOLOGIA Y PEDIATRIA  |

#### Cancelar Consulta
```http
  DELETE /consultas
```
Cuerpo de la solicitud:
| Propiedad | Tipo     | Descripción
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Requerido**. Id de la consulta a cancelar |
| `datosDireccion`      | `MotivoCancelacion` | **Requerido**. Se aceptan los sigueintes valores: PACIENTE_DESINTIO, MEDICO_CANCELO, OTROS  |
