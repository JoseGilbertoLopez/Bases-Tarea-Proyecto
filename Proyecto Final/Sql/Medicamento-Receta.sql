CREATE TABLE MEDICAMENTO_RECETA(
Marca VARCHAR(255),
Nombre VARCHAR(255),
Precio NUMBER(9,0),
Especialidad VARCHAR(255),
ID_Receta Number
);

ALTER TABLE MEDICAMENTO_RECETA
ADD constraint PK_MEDICAMENTO_RECETA
Primary Key(Marca,Nombre,ID_RECETA);

ALTER TABLE MEDICAMENTO_RECETA
ADD constraint FK1_MEDICAMENTO_RECETA
FOREIGN KEY(Marca,Nombre)
REFERENCES MEDICAMENTO(Marca,Nombre)
ON DELETE CASCADE;

ALTER TABLE MEDICAMENTO_RECETA
ADD constraint FK2_MEDICAMENTO_RECETA
FOREIGN KEY (ID_RECETA)
REFERENCES RECETA_INFORMACION_CONSULTA(ID_RECETA)
ON DELETE CASCADE;
