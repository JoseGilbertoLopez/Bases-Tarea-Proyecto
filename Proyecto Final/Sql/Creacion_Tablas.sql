CREATE TABLE CLIENTE_INFROMACION_PERSONAL(
ID_CLIENTE Number GENERATED ALWAYS AS IDENTITY (START with 1 INCREMENT by 1),
Nombre VARCHAR(255) NOT NULL,
Apellido_Materno VARCHAR(255) NOT NULL,
Apellido_Paterno VARCHAR(255) NOT NULL
);

ALTER TABLE CLIENTE_INFROMACION_PERSONAL
ADD constraint PK_CLIENTE_PERSONAL
Primary Key(ID_CLIENTE);

ALTER TABLE  CLIENTE_INFROMACION_PERSONAL
ADD constraint FK1_CLIENTE_PERSONAL
FOREIGN KEY(Nombre,Apellido_Materno,Apellido_Paterno)
REFERENCES CLIENTE_INFORMACION_CONTACTO(Nombre,Apellido_Materno,Apellido_Paterno)
ON DELETE CASCADE;


CREATE TABLE CLIENTE_INFORMACION_CONTACTO(
Nombre VARCHAR(255) NOT NULL,
Apellido_Materno VARCHAR(255) NOT NULL,
Apellido_Paterno VARCHAR(255) NOT NULL,
Direccion VARCHAR(255) NOT NULL,
Telefono VARCHAR(255) NOT NULL,
Correo_Electronico VARCHAR(255) NOT NULL
);


ALTER TABLE CLIENTE_INFORMACION_CONTACTO
ADD constraint PK1_CLIENTE_CONTACTO
PRIMARY KEY(Nombre, Apellido_Materno, Apellido_Paterno);

CREATE TABLE EMPLEADO_INFORMACION_PERSONAL(
No_Seguro_Social VARCHAR(255) NOT NULL,
RFC VARCHAR(255) NOT NULL,
Nombre VARCHAR(255) NOT NULL,
Apellido_Paterno VARCHAR(255) NOT NULL,
Apellido_Materno VARCHAR(255) NOT NULL
);

ALTER TABLE EMPLEADO_INFORMACION_PERSONAL
ADD constraint PK_EMPLEADO_PERSONAL
Primary Key(No_Seguro_Social,RFC);

ALTER TABLE EMPLEADO_INFORMACION_PERSONAL
ADD constraint FK1_EMPLEADO_PERSONAL
FOREIGN KEY (RFC,Nombre)
REFERENCES EMPLEADO_INFORMACION_CONTACTO(RFC,Nombre)
ON DELETE CASCADE;




CREATE TABLE EMPLEADO_INFORMACION_CONTACTO(
RFC VARCHAR(255) NOT NULL,
Nombre VARCHAR(255) NOT NULL,
Apellido_Materno VARCHAR(255) NOT NULL,
Apellido_Paterno VARCHAR(255) NOT NULL,
Direccion VARCHAR(255) NOT NULL,
Correo_Electronico VARCHAR(255) NOT NULL,
Telefono VARCHAR(255) NOT NULL
);

ALTER TABLE EMPLEADO_INFORMACION_CONTACTO
ADD constraint PK_Empleado_Contacto
Primary Key(RFC,Nombre);


CREATE TABLE EMPLEADO_INFORMACION_SUCURSAL(
No_Seguro_Social VARCHAR(255) NOT NULL,
Sucursal VARCHAR(255) NOT NULL
);

ALTER TABLE EMPLEADO_INFORMACION_SUCURSAL
ADD constraint PK1_EMPLEADO_SUCURSAL
Primary key(No_Seguro_Social);

ALTER TABLE EMPLEADO_INFORMACION_PERSONAL
ADD constraint FK2_EMPLEADO_SUCURSAL
FOREIGN KEY(No_Seguro_Social)
REFERENCES EMPLEADO_INFORMACION_SUCURSAL(No_Seguro_Social)
ON DELETE CASCADE;

CREATE TABLE MEDICO_INFORMACION_PERSONAL(
No_Seguro_Social VARCHAR(255) NOT NULL,
RFC VARCHAR(255) NOT NULL,
Cedula_Profesional VARCHAR(255) NOT NULL,
Nombre VARCHAR(255) NOT NULL,
Apellido_Paterno VARCHAR(255) NOT NULL,
Apellido_Materno VARCHAR(255) NOT NULL
);

ALTER TABLE MEDICO_INFORMACION_PERSONAL
ADD constraint PK_MEDICO_PERSONAL
Primary key(No_Seguro_Social,RFC,Cedula_Profesional);

ALTER TABLE MEDICO_INFORMACION_PERSONAL
ADD constraint FK1_MEDICO_PERSONAL
FOREIGN KEY(No_Seguro_Social,Cedula_Profesional,Nombre,Apellido_Materno,Apellido_Paterno)
REFERENCES MEDICO_INFORMACION_CONTACTO(No_Seguro_Social,Cedula_Profesional,Nombre,Apellido_Materno,Apellido_Paterno)
ON DELETE CASCADE;

ALTER TABLE MEDICO_INFORMACION_PERSONAL
ADD constraint FK2_MEDICO_PERSONAL
FOREIGN KEY(No_Seguro_Social,Cedula_Profesional)
REFERENCES MEDICO_INFORMACION_SUCURSAL(No_Seguro_Social,Cedula_Profesional)
ON DELETE CASCADE;

CREATE TABLE MEDICO_INFORMACION_CONTACTO(
No_Seguro_Social VARCHAR(255) NOT NULL,
Cedula_Profesional VARCHAR(255) NOT NULL,
Nombre VARCHAR(255) NOT NULL,
Apellido_Paterno VARCHAR(255) NOT NULL,
Apellido_Materno VARCHAR(255) NOT NULL,
Direccion VARCHAR(255) NOT NULL,
Telefono VARCHAR(255) NOT NULL,
Correo_Electronico VARCHAR(255) NOT NULL
);


ALTER TABLE MEDICO_INFORMACION_CONTACTO
ADD constraint PK_MEDICO_CONTACTO
Primary key(No_Seguro_Social,Cedula_Profesional,Nombre,Apellido_Materno,Apellido_Paterno);

CREATE TABLE MEDICO_INFORMACION_SUCURSAL(
No_Seguro_Social VARCHAR(255) NOT NULL,
Cedula_Profesional VARCHAR(255) NOT NULL,
SUCURSAL VARCHAR(255) NOT NULL
);

ALTER TABLE MEDICO_INFORMACION_SUCURSAL
ADD constraint PK_MEDICO_SUCURSAL
Primary key(No_Seguro_Social,Cedula_Profesional);

CREATE TABLE COMPRA_INFORMACION_GENERAL(
ID_Compra Number GENERATED ALWAYS AS IDENTITY (START with 1 INCREMENT by 1),
No_Receta Integer,
Sucursal VARCHAR(255),
Vendedor VARCHAR(255)
);

ALTER TABLE COMPRA_INFORMACION_GENERAL
ADD constraint PK_COMPRA_GENERAL
Primary Key(ID_Compra,No_Receta);

ALTER TABLE COMPRA_INFORMACION_GENERAL
ADD constraint FK1_COMPRA_GENERAL
FOREIGN KEY(Sucursal)
REFERENCES COMPRA_INFORMACION_PRODUCTO(Sucursal)
ON DELETE CASCADE;

ALTER TABLE COMPRA_INFORMACION_GENERAL
ADD constraint FK2_COMPRA_GENERAL
FOREIGN KEY(No_Receta)
REFERENCES COMPRA_INFORMACION_FECHA(No_Receta)
ON DELETE CASCADE;

CREATE TABLE COMPRA_INFORMACION_PRODUCTO(
Sucursal VARCHAR(255),
Productos VARCHAR(255),
Total INTEGER
);

ALTER TABLE COMPRA_INFORMACION_PRODUCTO
ADD constraint PK_COMPRA_PRODUCTO
Primary Key(Sucursal,Productos);

CREATE TABLE COMPRA_INFORMACION_FECHA(
No_Receta Integer,
Fecha TimeStamp
);

ALTER TABLE COMPRA_INFORMACION_FECHA
ADD constraint PK_COMPRA_FECHA
Primary Key(No_Receta, Fecha);

CREATE TABLE MEDICAMENTO(
Marca VARCHAR(255),
Nombre VARCHAR(255),
Precio NUMBER(9,0),
Especialidad VARCHAR(255)
);

ALTER TABLE MEDICAMENTO
ADD constraint PK_MEDICAMENTO
Primary Key(Marca,Nombre);

CREATE TABLE RECETA_INFORMACION_MEDICO(
ID_Receta Number GENERATED ALWAYS AS IDENTITY (START with 1 INCREMENT by 1),
Medico VARCHAR(255),
Firma VARCHAR(255)
);

ALTER TABLE RECETA_INFORMACION_MEDICO
ADD constraint PK_RECETA_MEDICO
Primary Key(ID_Receta);

CREATE TABLE RECETA_INFORMACION_CONSULTA(
Medico VARCHAR(255),
Datos_Paciente VARCHAR(255),
Numero_Pacientes_Turno INTEGER,
Turno_Consulta INTEGER
);

ALTER TABLE RECETA_INFORMACION_CONSULTA
ADD constraint PK_RECETA_CONSULTA
Primary Key(Medico,Datos_Paciente);