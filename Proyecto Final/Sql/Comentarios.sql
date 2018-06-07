COMMENT ON TABLE CLIENTE_INFORMACION_CONTACTO IS "La información de la direccion, telefono y Correo Electronico del Cliente";
COMMENT ON TABLE CLIENTE_INFORMACION_PERSONAL IS "La informacion personal del cliente que son nombre(s), Apellido Materno, Apellido Paterno e ID";
COMMENT ON TABLE EMPLEADO_INFORMACION_CONTACTO IS "La informacion de la direccion, telefono y Correo Electronico de un Empleado";
COMMENT ON TABLE EMPLEADO_INFORMACION_PERSONAL IS "La informacion personal del empleado que son nombre(s), Apellido Materno, Apellido Paterno, su Numero de Seguro Social y RFC";
COMMENT ON TABLE EMPLEADO_INFORMACION_SUCURSAL IS "La informacion de la sucursal donde trabaja el empleado;"
COMMENT ON TABLE MEDICO_INFORMACION_CONTACTO IS "La informacion de la direccion, telefono y Correo Electronico de un Medico";
COMMENT ON TABLE MEDICO_INFORMACION_PERSONAL IS "La informacion personal del medico que son nombre(s), Apellido Materno, Apellido Paterno, Numero de Seguro Social, RFC y Cedula Profesional";
COMMENT ON TABLE MEDICO_INFORMACION_SUCURSAL IS "Nos da la informacion de la sucursal donde trabaja el Medico";
COMMENT ON TABLE COMPRA_INFORMACION_FECHA IS "La tabla que nos da la informacion de la fecha de la compra que se requiere";
COMMENT ON TABLE COMPRA_INFORMACION_GENERAL IS "Nos da la informacion general como id, el que vendio, numero receta, y la sucursal de venta";
COMMENT ON TABLE COMPRA_INFORMACION_PRODUCTO IS "La informacion de los productos vendidos";
COMMENT ON TABLE GENERAL_INFO_CONTACTO IS "La informacion de contacto de un empleado general";
COMMENT ON TABLE GENERAL_INFO_PERSONAL IS "La informacion personal de un empleado general";
COMMENT ON TABLE GENERAL_INFO_SUCURSAL IS "La informacion de donde trabaja el empleado general";
COMMENT ON TABLE INTENDENCIA_INFO_CONTACTO IS "La informacion del contacto de un empleado que es intendente";
COMMENT ON TABLE INTENDENCIA_INFO_PERSONAL IS "La informacion personal del empleado intendente";
COMMENT ON TABLE INTENDENCIA_INFO_SUCURSAL IS "La informacion de en que sucursal trabaja el intendente";
COMMENT ON TABLE MEDICAMENTO IS "La informacion del medicamento que se rquiera buscar";
COMMENT ON TABLE MOSTRADOR_INFO_CONTACTO IS "La informacion de contacto de un empleado que trabaja como mostrador";
COMMENT ON TABLE MOSTRADOR_INFO_PERSONAL IS "La informacion personal de un empleado que trabaja en mostrador";
COMMENT ON TABLE MOSTRADOR_INFO_SUCURSAL IS "La informacion de donde trabaja un empleado que trabaja en mostrador";
COMMENT ON TABLE RECETA_INFORMACION_CONSULTA IS "La informacion general de la consulta dada";
COMMENT ON TABLE RECETA_INFORMACION_MEDICO IS "La informacion del medico que dio la receta";
COMMENT ON TABLE RESPONSABLE_INFO_CONTACTO IS "La informacion de contacto del responsable de una sucursal";
COMMENT ON TABLE RESPONSABLE_INFO_PERSONAL IS "La informacion personal de una persona responsable de una sucursal";
COMMENT ON TABLE RESPONSABLE_INFO_SUCURSAL IS "La informacion de en que sucursal trabaja el responsable";
COMMENT ON TABLE SUCURSAL IS "La informacion de las Sucursales del Doctor Ahorro";
COMMENT ON TABLE INGREDIENTE IS "La tabla que nos dice que ingredientes tiene un medicamento";
COMMENT ON TABLE MEDICAMENTO_INGREDIENTE IS "Nos da acceso entre medicamento e ingrediente para que sea un catalogo";
COMMENT ON TABLE PRESENTACION IS "La tabla que nos dice que presentacion existe para un medicamento";
COMMENT ON TABLE MEDICAMENTO_PRESENTACION IS "La tabla de acceso entre medicamento y presentacion que nos da un catalogo entre los dos";
COMMENT ON TABLE ESPECIALIDAD IS "La tabla que nos da la informacion de que especialidades tiene un doctor";
COMMENT ON TABLE MEDICO_ESPECIALIDAD IS "La tabla que nos un catalogo de informacion entre el medico y sus especialidades";
COMMENT ON TABLE TELEFONOS_SUCURSALES IS "La lista de telefonos que puede tener una sucursal";
COMMENT ON TABLE TELEFONO_SUCURSAL IS "La tabla que nos un catalogo entre telefono y Sucursales";
COMMENT ON TABLE CLIENTE_RECETA IS "La tabla de la relacion entre Cliente y Receta";
COMMENT ON TABLE EMPLEADO_SUCURSAL IS "La tabla de le relacion entre Empleado y Sucursal";
COMMENT ON TABLE MEDICO_RECETA IS "La tabla de la relacion entre Medico y Receta";
COMMENT ON TABLE SUCURSAL_MEDICAMENTO IS "La tabla de la relacion entre Sucursal y Medicamento";
COMMENT ON TABLE MEDICAMENTO_RECETA IS "La tabla de la relacion entre Medicamento y Receta";
COMMENT ON TABLE MEDICAMENTO_COMPRA IS "La tabla de la relacion entre Medicamento y Compra";
COMMENT ON TABLE MOSTRADOR_COMPRA IS "La tabla de la relacion que existe entre Mostrador y Compra";

COMMENT ON COLUMN CLIENTE_INFORMACION_CONTACTO.NOMBRE IS "El nombre del cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_CONTACTO.APELLIDO_MATERNO IS "El apellido Materno del Cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_CONTACTO.Apellido_PATERNO IS "El apellido Paterno del Cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_CONTACTO.DIRECCION IS "La direccion de la vivienda del Cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_CONTACTO.TELEFONO IS "El numero telefonico del cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_CONTACTO.CORREO_ELECTRONICO IS "El Correo Electronico del Cliente";

COMMENT ON COLUMN CLIENTE_INFORMACION_PERSONAL.ID_CLIENTE IS "El id del cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_PERSONAL.NOMBRE IS "El nombre del Cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_PERSONAL.APELLIDO_MATERNO IS "El Apellido Materno del Cliente";
COMMENT ON COLUMN CLIENTE_INFORMACION_PERSONAL.APELLIDO_PATERNO IS "El Apellido Paterno del Cliente";

COMMENT ON COLUMN EMPLEADO_INFORMACION_CONTACTO.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_CONTACTO.NOMBRE IS "El nombre del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_CONTACTO.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_CONTACTO.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_CONTACTO.DIRECCION IS "La direccion del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_CONTACTO.CORREO_ELECTRONICO IS "El Correo Electronico del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_CONTACTO.TELEFONO IS "El telefono del Empleado";

COMMENT ON COLUMN EMPLEADO_INFORMACION_PERSONAL.ID_CLIENTE IS "El id que tiene asociado un empleado si alguna vez fue cliente";
COMMENT ON COLUMN EMPLEADO_INFORMACION_PERSONAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_PERSONAL.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_PERSONAL.NOMBRE IS "El Nombre del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_PERSONAL.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_PERSONAL.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";

COMMENT ON COLUMN EMPLEADO_INFORMACION_SUCURSAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN EMPLEADO_INFORMACION_SUCURSAL.SUCURSAL IS "Nos dice la Sucursal donde trabaja el Empleado";

COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.CEDULA_PROFESIONAL IS "La Cedula Profesional del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.NOMBRE IS "El nombre del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.APELLIDO_PATERNO IS "El Apellido Paterno del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.APELLIDO_MATERNO IS "El Apellido Materno del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.DIRECCION IS "La direccion de la vivienda del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.TELEFONO IS "El telefono del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_CONTACTO.CORREO_ELECTRONICO IS "El Correo Electronico del Medico";

COMMENT ON COLUMN MEDICO_INFORMACION_PERSONAL.ID_CLIENTE IS "El id que tiene asociado un medico si alguna vez fue cliente";
COMMENT ON COLUMN MEDICO_INFORMACION_PERSONAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_PERSONAL.RFC IS "El RFC del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_PERSONAL.CEDULA_PROFESIONAL IS "La Cedula Profesional del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_PERSONAL.NOMBRE IS "El Nombre del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_PERSONAL.APELLIDO_PATERNO IS "El Apellido Paterno del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_PERSONAL.APELLIDO_MATERNO IS "El Apellido Materno del Medico";

COMMENT ON COLUMN MEDICO_INFORMACION_SUCURSAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_SUCURSAL.CEDULA_PROFESIONAL IS "La Cedula Profesional del Medico";
COMMENT ON COLUMN MEDICO_INFORMACION_SUCURSAL.SUCURSAL IS "Nos da el nombre de la sucursal donde trabaja el Medico";

COMMENT ON COLUMN INTENDENCIA_INFO_CONTACTO.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_CONTACTO.NOMBRE IS "El nombre del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_CONTACTO.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_CONTACTO.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_CONTACTO.DIRECCION IS "La direccion del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_CONTACTO.CORREO_ELECTRONICO IS "El Correo Electronico del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_CONTACTO.TELEFONO IS "El telefono del Empleado";

COMMENT ON COLUMN INTENDENCIA_INFO_PERSONAL.ID_CLIENTE IS "El id que tiene asociado un intendente si fue alguna vez cliente";
COMMENT ON COLUMN INTENDENCIA_INFO_PERSONAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_PERSONAL.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_PERSONAL.NOMBRE IS "El Nombre del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_PERSONAL.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFO_PERSONAL.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";

COMMENT ON COLUMN INTENDENCIA_INFORMACION_SUCURSAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN INTENDENCIA_INFORMACION_SUCURSAL.SUCURSAL IS "Nos dice la Sucursal donde trabaja el Empleado";

COMMENT ON COLUMN GENERAL_INFO_CONTACTO.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN GENERAL_INFO_CONTACTO.NOMBRE IS "El nombre del Empleado";
COMMENT ON COLUMN GENERAL_INFO_CONTACTO.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";
COMMENT ON COLUMN GENERAL_INFO_CONTACTO.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN GENERAL_INFO_CONTACTO.DIRECCION IS "La direccion del Empleado";
COMMENT ON COLUMN GENERAL_INFO_CONTACTO.CORREO_ELECTRONICO IS "El Correo Electronico del Empleado";
COMMENT ON COLUMN GENERAL_INFO_CONTACTO.TELEFONO IS "El telefono del Empleado";

COMMENT ON COLUMN GENERAL_INFO_PERSONAL.ID_CLIENTE IS "El id que tiene asociado un empleado general si alguna vez fue un cliente";
COMMENT ON COLUMN GENERAL_INFO_PERSONAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN GENERAL_INFO_PERSONAL.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN GENERAL_INFO_PERSONAL.NOMBRE IS "El Nombre del Empleado";
COMMENT ON COLUMN GENERAL_INFO_PERSONAL.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN GENERAL_INFO_PERSONAL.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";

COMMENT ON COLUMN GENERAL_INFO_SUCURSAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN GENERAL_INFO_SUCURSAL.SUCURSAL IS "Nos dice la Sucursal donde trabaja el Empleado";

COMMENT ON COLUMN MOSTRADOR_INFO_CONTACTO.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_CONTACTO.NOMBRE IS "El nombre del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_CONTACTO.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";
COMMENT ON COLUMN MOSTRQDOR_INFO_CONTACTO.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_CONTACTO.DIRECCION IS "La direccion del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_CONTACTO.CORREO_ELECTRONICO IS "El Correo Electronico del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_CONTACTO.TELEFONO IS "El telefono del Empleado";

COMMENT ON COLUMN MOSTRADOR_INFO_PERSONAL.ID_CLIENTE IS "El id que tiene un empleado de mostrador si fue alguna vez cliente";
COMMENT ON COLUMN MOSTRADOR_INFO_PERSONAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_PERSONAL.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_PERSONAL.NOMBRE IS "El Nombre del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_PERSONAL.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_PERSONAL.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";

COMMENT ON COLUMN MOSTRADOR_INFO_SUCURSAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN MOSTRADOR_INFO_SUCURSAL.SUCURSAL IS "Nos dice la Sucursal donde trabaja el Empleado";

COMMENT ON COLUMN RESPONSABLE_INFO_CONTACTO.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_CONTACTO.NOMBRE IS "El nombre del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_CONTACTO.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_CONTACTO.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_CONTACTO.DIRECCION IS "La direccion del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_CONTACTO.CORREO_ELECTRONICO IS "El Correo Electronico del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_CONTACTO.TELEFONO IS "El telefono del Empleado";

COMMENT ON COLUMN RESPONSABLE_INFORMACION_PERSONAL.ID_CLIENTE IS "El id que tiene un responsable si alguna vez fue cliente";
COMMENT ON COLUMN RESPONSABLE_INFO_PERSONAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_PERSONAL.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_PERSONAL.NOMBRE IS "El Nombre del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_PERSONAL.APELLIDO_PATERNO IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_PERSONAL.APELLIDO_MATERNO IS "El Apellido Materno del Empleado";

COMMENT ON COLUMN RESPONSABLE_INFO_SUCURSAL.NO_SEGURO_SOCIAL IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN RESPONSABLE_INFO_SUCURSAL.SUCURSAL IS "Nos dice la Sucursal donde trabaja el Empleado";

COMMENT ON COLUMN ESPECIALIDAD.ID_Especialidad IS "El id de la especialidad que existen";
COMMENT ON COLUMN ESPCIALIDAD.ESPECIALIDAD IS "El nombre de la especialidad que existe";

COMMENT ON COLUMN INGREDIENTE.ID_INGREDIENTE IS "El id de los ingredientes que existen";
COMMENT ON COLUMN INGREDIENTE.INGREDIENTE IS "El nombre del ingrediente en concreto";

COMMENT ON COLUMN PRESENTACION.ID_PRESENTACION IS "EL ID de la  presentacion del medicamento";
COMMENT ON COLUMN PRESENTACION.PRESENTACION IS "El nombre de la presentacion";

COMMENT ON COLUMN MEDICAMENTO.MARCA IS "EL nombre de la marca del que proviene el medicamento";
COMMENT ON COLUMN MEDICAMENTO.NOMBRE IS "El nombre del medicamento";
COMMENT ON COLUMN MEDICAMENTO.PRECIO IS "El precio del medicamento";
COMMENT ON COLUMN MEDICAMENTO.ESPECIALIDAD IS "La especialidad del medicamento";

COMMENT ON COLUMN MEDICAMENTO_INGREDIENTE.ID_INGREDIENTE IS "EL id del ingrediente de la tabla Ingrediente";
COMMENT ON COLUMN MEDICAMENTO_INGREDIENTE.MARCA IS "La marca del medicamento de la tabla Medicamento";
COMMENT ON COLUMN MEDICAMENTO_INGREDIENTE.NOMBRE IS "El nombre del medicamento que proviene de la tabla Medicamento";

COMMENT ON COLUMN MEDICAMENTO_PRESENTACION.ID_PRESENTACION IS "El id de la presentacion de la tabla Presentacion";
COMMENT ON COLUMN MEDICAMENTO_PRESENTACION.MARCA IS "La marca del medicamento de la tabla medicamento";
COMMENT ON COLUMN MEDICAMENTO_PRESENTACION.NOMBRE IS "El nombre del medicamento que proviene de la tabla medicamento";

COMMENT ON COLUMN MEDICO_ESPECIALIDAD.ID_CLIENTE IS "El id que tiene el medico si alguna vez fue un cliente de la sucursal";
COMMENT ON COLUMN MEDICO_ESPECIALIDAD.ID_ESPECIALIDAD IS "El id de la especialidad que tiene el medico";
COMMENT ON COLUMN MEDICO_ESPECIALIDAD.NO_SEGURO_SOCIAL IS "El numero de Seguro Social asociado al medico";
COMMENT ON COLUMN MEDICO_ESPECIALIDAD.RFC IS "El RFC del medico";
COMMENT ON COLUMN MEDICO_ESPECIALIDAD.CEDULA_PROFESIONAL IS "La cedula preofesional del medico";

COMMENT ON COLUMN RECETA_INFORMACION_CONSULTA.MEDICO IS "La informacion del medico que escribio la receta";
COMMENT ON COLUMN RECETA_INFORMACION_CONSULTA.DATOS_PACIENTE IS "La informacion del paciente";
COMMENT ON COLUMN RECETA_INFORMACION_CONSULTA.NUMERO_PACIENTES_TURNO IS "Nos da la informacion de cuentas personas fueron atendidas en una cita";
COMMENT ON COLUMN RECETA_INFORMACION_CONSULTA.TURNO_CONSULTA IS "El numero del turno de la consulta que recibio el paciento";

COMMENT ON COLUMN RECETA_INFORMACION_MEDICO.ID_RECETA IS "El id de la receta";
COMMENT ON COLUMN RECETA_INFORMACION_MEDICO.MEDICO IS "La informacion del medico que escribio la receta";
COMMENT ON COLUMN RECETA_INFORMACION_MEDICO.FIRMA IS "La firma del medico";

COMMENT ON COLUMN SUCURSAL.ID_SUCURSAL IS "El id de la sucursal";
COMMENT ON COLUMN SUCURSAL.RESPONSABLE IS "El dato del responsable de la sucursal";
COMMENT ON COLUMN SUCURSAL.DIRECCION IS "La direccion de la sucursal";
COMMENT ON COLUMN SUCURSAL.CIUDAD IS "La ciudad donde esta la sede de la Sucursal";
COMMENT ON COLUMN SUCURSAL.ESTADO IS "El estado donde esta la sede de la Sucursal";

COMMENT ON COLUMN TELEFONOS_SUCURSALES.ID_TELEFONO IS "El id del telefono de la sucursal";
COMMENT ON COLUMN TELEFONOS_SUCURSALES.TELEFONO IS "El telefono de la sucursal";

COMMENT ON COLUMN TELEFONO_SUCURSAL.ID_TELEFONO IS "El id del telefono asociado a la Sucursal";
COMMENT ON COLUMN TELEFONO_SUCURSAL.ID_SUCURSAL IS "El id de la sucursal";
COMMENT ON COLUMN TELEFONO_SUCURSAL.DIRECCION IS "La direccion de la sucursal";

COMMENT ON COLUMN CLIENTE_RECETA.ID_CLIENTE IS "El id del cliente";
COMMENT ON COLUMN CLIENTE_RECETA.Nombre IS "El nombre del cliente";
COMMENT ON COLUMN CLIENTE_RECETA.Apellido_Materno IS "El Apellido Materno del Cliente";
COMMENT ON COLUMN CLIENTE_RECETA.Apellido_Paterno IS "El Apellido Paterno del Cliente";
COMMENT ON COLUMN CLIENTE_RECETA.ID_RECETA IS "El id de la receta asociada al cliente";

COMMENT ON COLUMN EMPLEADO_SUCURSAL.ID_CLIENTE IS "El id asociado al cliente";
COMMENT ON COLUMN EMPLEADO_SUCURSAL.No_Seguro_Social IS "El numero de Seguro Social del Empleado";
COMMENT ON COLUMN EMPLEADO_SUCURSAL.RFC IS "El RFC del Empleado";
COMMENT ON COLUMN EMPLEADO_SUCURSAL.Nombre IS "El nombre del Empleado de la sucursal";
COMMENT ON COLUMN EMPLEADO_SUCURSAL.Apellido_Materno IS "El Apellido Materno del Empleado";
COMMENT ON COLUMN EMPLEADO_SUCURSAL.Apellido_Paterno IS "El Apellido Paterno del Empleado";
COMMENT ON COLUMN EMPLEADO_SUCURSAL.ID_SUCURSAL IS "El id de la Sucursal donde trabaja el Empleado";

COMMENT ON COLUMN MEDICO_RECETA.ID_CLIENTE IS "El id del medico si fuee Cliente al menos una vez";
COMMENT ON COLUMN MEDICO_RECETA.No_Seguro_Social IS "El Numero de Seguro Social del Medico";
COMMENT ON COLUMN MEDICO_RECETA.RFC IS "El RFC del medico que escribe la receta";
COMMENT ON COLUMN MEDICO_RECETA.Cedula_Profesional IS "La Cedula Profesional del medico";
COMMENT ON COLUMN MEDICO_RECETA.Nombre IS "El nombre del Medico";
COMMENT ON COLUMN MEDICO_RECETA.Apellido_Materno IS "El Apellido Materno del Medico";
COMMENT ON COLUMN MEDICO_RECETA.Apellido_Paterno IS "El Apellido Paterno del Medico";
COMMENT ON COLUMN MEDICO_RECETA.ID_RECETA IS "El ide de la receta que escribio el Medico";

COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.ID_SUCURSAL IS "El id de la sucursal donde esta el medicamento";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.RESPONSABLE IS "El Responsable de la sucursal";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.DIRECCION IS "La direccion de la Sucursal";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.CIUDAD IS "La Ciudad donde esta la Sucursal";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.ESTADO IS "El estado donde esta la sucursal";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.NOMBRE IS "El nombre del Medicamento";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.Marca IS "El nombre de la Marca a la cua lpertenece el Medicamento";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.Precio IS "El precio del Medicamento";
COMMENT ON COLUMN SUCURSAL_MEDICAMENTO.Especialidad IS "Nos dice si el medicamento es de tipo venta general o tiene restricciones especiales";

COMMENT ON COLUMN MEDICAMENTO_RECETA.MARCA IS "Nos dice el nombre de la marca a la que pertenece el Medicamento";
COMMENT ON COLUMN MEDICAMENTO_RECETA.NOMBRE IS "El nombre del Medicamento";
COMMENT ON COLUMN MEDICAMENTO_RECETA.PRECIO IS "El precio que tiene el Medicamento";
COMMENT ON COLUMN MEDICAMENTO_RECETA.ESPECIALIDAD IS "Nos dice si el medicamento es de venta general o tiene restricciones especiales";
COMMENT ON COLUMN MEDICAMENTO_RECETA.ID_RECETA IS "El id de la receta donde se menciona el Medicamento";
COMMENT ON COLUMN MEDICAMENTO_RECETA.Medic IS "El medico que escribio la Receta";
COMMENT ON COLUMN MEDICAMENTO_RECETA.Fira IS "La firma del medico en la receta";



COMMENT ON COLUMN MEDICAMENTO_COMPRA.Marca IS "La marca a la que pertenece el Medicamento";
COMMENT ON COLUMN MEDICAMENTO_COMPRA.Nombre IS "El nombre del Medicamento";
COMMENT ON COLUMN MEDICAMENTO_COMPRA.Precio IS "El precio del medicamento";
COMMENT ON COLUMN MEDICAMENTO_COMPRA.Especialidad IS "Nos dice si el medicamento es de venta general o tiene una restriccion especial";
COMMENT ON COLUMN MEDICAMENTO_COMPRA.ID_COMPRA IS "El id de la compra donde esta el medicamento(s) mencionados";
COMMENT ON COLUMN MEDICAMENTO_COMPRA.No_Receta IS "EL numero de Receta donde se menciona el medicamento";
 
 COMMENT ON COLUMN MOSTRADOR_COMPRA.ID_CLIENTE IS "El id que tiene el empleado Mostrador si alguna vez ha sido Cliente";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.No_Seguro_Social is "EL numero de Seguro Social del Empleado Mostrador";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.RFC IS "El RFC del Empleado Mostrador";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.Nombre is "El nombre del Empleado Mostrador";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.Apellido_Materno IS "El Apellido Materno del Empleado Mostrador";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.Apellido_Paterno IS "El Apellido Paterno del Empleado Mostrador";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.ID_COMPRA IS "El id de Compra donde un Empleado MOstrador hizo una cierta venta";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.No_Receta IS "El numero de la Receta donde el Empleado MOstrador especificado participo en su venta";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.SUCURSAL IS "El nombre de la Sucursal donde se llevo la compra de medicamento(s)";
 COMMENT ON COLUMN MOSTRADOR_COMPRA.VENDEDOR IS "LA informacion del que hizo la Venta";