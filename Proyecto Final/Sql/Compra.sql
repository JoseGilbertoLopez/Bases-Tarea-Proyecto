CREATE TABLE COMPRA_INFORMACION_GENERAL(
ID_Compra Number GENERATED ALWAYS AS IDENTITY (START with 1 INCREMENT by 1),
No_Receta Number ,
Sucursal Number NOT NULL,
Vendedor Number NOT NULL,
Fecha Date NOT NULL,
Total NUMBER(9,0) NOT NULL,
Tipo_Pago VARCHAR(255) NOT NULL
);

ALTER TABLE COMPRA_INFORMACION_GENERAL
ADD constraint PK_COMPRA_GENERAL
Primary Key(ID_Compra);

CREATE TABLE COMPRA_INFORMACION_SUCVEN(
Sucursal NUMBER NOT NULL,
Vendedor NUMBER NOT NULL
);

ALTER TABLE COMPRA_INFORMACION_SUCVEN
ADD constraint PK_COMPRA_PRODUCTO
Primary Key(Vendedor);

ALTER TABLE COMPRA_INFORMACION_GENERAL
ADD constraint FK1_INFORMACION_GENERAL
FOREIGN KEY(Vendedor)
REFERENCES COMPRA_INFORMACION_SUCVEN(Vendedor)
ON DELETE CASCADE;

CREATE TABLE PRODUCTO(
ID_PRODUCTO Number GENERATED ALWAYS AS IDENTITY (START with 1 INCREMENT by 1),
Producto VARCHAR(255)
);

ALTER TABLE PRODUCTO
ADD constraint PK_PRODUCTO
Primary Key(ID_PRODUCTO);

CREATE TABLE COMPRA_PRODUCTO(
ID_Producto NUMBER,
ID_Compra NUMBER
);

ALTER TABLE COMPRA_PRODUCTO
ADD constraint PK1_COMPRA_PRODUCTO
Primary Key(ID_Producto,ID_Compra);

ALTER TABLE COMPRA_PRODUCTO
ADD constraint FK1_COMPRA_PRODUCTO
FOREIGN KEY(ID_PRODUCTO)
REFERENCES PRODUCTO(ID_PRODUCTO)
ON DELETE CASCADE;

ALTER TABLE COMPRA_PRODUCTO
ADD constraint FK2_COMPRA_PRODUCTO
FOREIGN KEY(ID_Compra)
REFERENCES COMPRA_INFORMACION_GENERAL(ID_Compra)
ON DELETE CASCADE;