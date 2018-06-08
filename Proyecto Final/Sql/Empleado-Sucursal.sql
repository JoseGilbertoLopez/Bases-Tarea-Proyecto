CREATE TABLE EMPLEADO_SUCURSAL(
ID_CLIENTE Number ,
No_Seguro_Social VARCHAR(255) NOT NULL,
RFC VARCHAR(255) NOT NULL,
Nombre VARCHAR(255) NOT NULL,
Apellido_Paterno VARCHAR(255) NOT NULL,
Apellido_Materno VARCHAR(255) NOT NULL,
ID_SUCURSAL NUMBER
);

ALTER TABLE EMPLEADO_SUCURSAL
ADD constraint PK_EMPLEADO_SUCURSAL
Primary Key(ID_CLIENTE,No_Seguro_Social,RFC,ID_SUCURSAL);

ALTER TABLE EMPLEADO_SUCURSAL
ADD constraint FK1_EMPLEADO_SUCURSAL
FOREIGN KEY(ID_CLIENTE,No_Seguro_Social,RFC)
REFERENCES EMPLEADO_INFORMACION_PERSONAL(ID_CLIENTE,No_Seguro_Social,RFC)
ON DELETE CASCADE;


ALTER TABLE EMPLEADO_SUCURSAL
ADD constraint FK3_EMPLEADO_SUCURSAL
FOREIGN KEY(ID_SUCURSAL)
REFERENCES SUCURSAL(ID_SUCURSAL)
ON DELETE CASCADE;