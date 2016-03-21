-- MySQL dump 10.13  Distrib 5.5.11, for Win64 (x86)
--
-- Host: localhost    Database: contapoliza
-- ------------------------------------------------------
-- Server version	5.5.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cuentas`
--

DROP TABLE IF EXISTS `cuentas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuentas` (
  `IdCuenta` int(6) NOT NULL,
  `NomCuenta` varchar(80) NOT NULL,
  `descripcion` varchar(255) DEFAULT 'null',
  `saldo` double DEFAULT '0',
  PRIMARY KEY (`IdCuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentas`
--

LOCK TABLES `cuentas` WRITE;
/*!40000 ALTER TABLE `cuentas` DISABLE KEYS */;
INSERT INTO `cuentas` VALUES (1000,'Saldo','Saldo Inicial del Año',0),(1101,'Banco Banamex','Cuenta de banco banamex',1500000),(1105,'Caja','Caja Chica',499500),(1300,'Compensaciones por servicios eventuales',NULL,0),(2101,'Materiales de Oficina',NULL,500),(2102,'Materiales de Limpieza',NULL,0),(2103,'Materiales Didacticos',NULL,0),(2104,'Materiales Estadisticos y Geograficos',NULL,0),(2105,'Materiales y utiles de Impresion',NULL,0),(2106,'Materiales y utiles de Impresion para procesamiento de equipo de computo electro',NULL,0),(2201,'Alimentacion de personas',NULL,0),(2202,'Alimentacion de animales',NULL,0),(2203,'Utencilios para el servicio de Alimentacion',NULL,0),(2400,'Materiales y Articulos de Construccion','Agrupa las asignaciones destinadas a la adquisicion de materiales y articulos utilizados en la construccion, reconstruccion, ampliacion, adaptacion, mejora, conservacion y mantenimiento de bienes e inmuebles.',0),(2401,'Materiales de construccion','Asignaciones destinadas a la adquisicion de materiales utilizados en la construccion.',0),(2402,'Estructura y Manufactura','Asignaciones destinadas a la adquisicion de toda clase de estructuras y manufacturas que se utilizan en la construccion, reconstruccion, ampliacion, adaptacion, mejora, conservacion y mantenimiento.',0),(2403,'Materiales complementarios','Asignaciones destinadas a la adquisicion de materiales de cualquier naturaleza para el acondicionamiento de las obras publicas y bienes inmuebles',0),(2500,'Productos quimicos, farmaceuticos y de laoratorio','Agrupa las asignaciones destinadas a la adquisicion de productos quimicos farmaceuticos de aplicacion humana o animal.',0),(2501,'Sustancias quimicas','Asignaciones destinadas a la adquisicion de toda clase de sustancias quimicas',0),(2502,'Plaguicidas, Abonos y Ferteilizantes','Asignacion destinadas a la adquisicion de este tipo de productos y cuyo estado de fabricacion se encuentre terminado y que requieran los Institutos Tecnologicos para los usos propios, tales como: fertilizantes complejo e inorganico, fungicidas, etc.',0),(2503,'Medicinas y productos farmaceuticos','Asignaciones destinadas a la adquisicion de medicinas y productos farmaceuticos de aplicacion humana o animal.',0),(2504,'Materiales y suministros medicos','Asignaciones destinadas a la adquisicion de toda clase de materiales y suministros medicos que se requieran en los institutos tecnologicos.',0),(2505,'Materiales y suministros de laboratorio','Asignaciones destinadas a la adquisicion de toda clase de materiales, suministros y animales utilizados en los laboratorios medicos',0),(2600,'Combustibles, Lubricantes y aditivos','Agrupa las asignaciones destinadas a la adquisicion de combustibles, lubricantes y aditivos de todo tipo que se requieran para el funcionamiento y prestaciones de los servicios propios los institutos tecnologicos.',0),(2601,'Combustibles','Asignaciones destinadas a la adquisicion de toda clase de combustible en estado liquido o gaseoso, crudos o refinados.',0),(2602,'Lubricantes y aditivos','Asignaciones destinadas a la compra de toda clase de grasas y lubricantes.',0),(2700,'Vestuario, Blancos, Prendas de Proteccion y Articulos Deportivos','Agrupa las asignaciones destinadas a la adquisicion de vestuarios y sus accesorios, blancos, prendas de proteccion y articulos deportivos que requieran los Institutos Tecnologicos.',0),(2701,'Vestuario, uniformes y blancos','Asignacion destinadas a la adquisicion de toda clase de ropa elaborada y sus accesorios.',0),(2702,'Prendas de Proteccion','Asignacion destinadas a la adquisicion de prendas especiales de proteccion personal.',0),(2703,'Articulos deportivos','Asignaciones destinadas a la adquisicion de todo tipo de articulos deportivos.',0),(3101,'Servicios Postal',NULL,0),(3102,'Servicio Telegrafico',NULL,0),(3103,'Servicios Telefonicos',NULL,0),(3104,'Servicio de Energia Electrica',NULL,0),(3105,'Servicio de Agua Potable',NULL,0),(3200,'Servicio de Arrendamiento',NULL,0),(3203,'Arrendamiento de maquinaria y equipo',NULL,0),(3301,'Asesoria y capacitacion',NULL,0),(3400,'Servicios comerciales y bancario',NULL,0),(3401,'Almacenaje, embalaje y envase',NULL,0),(3402,'Fletes y maniobras',NULL,0),(3403,'Intereses, descuentos y otros servicios bancarios',NULL,0),(3405,'Impuestos de importacion',NULL,0),(3406,'Impuestos de exportacion',NULL,0),(3407,'Otros impuestos y derechos',NULL,0),(3410,'Diferencias en cambios',NULL,0),(3411,'Servicios de vigilancia',NULL,0),(3412,'Higiene y fumigacion',NULL,0),(3500,'Servicio de Mantenimientgo, Conservacion e Instalacion',NULL,0),(3501,'Mantenimiento y conservacion de mobilario y equipo',NULL,0),(3503,'Mantenimiento y Conservacion de Maquinaria y Equipo',NULL,0),(3505,'Instalaciones',NULL,0),(3600,'Servicios de Difucion e Informacion',NULL,0),(3601,'Gastos de Propaganda',NULL,0),(3602,'Impresiones y publicaciones Oficiales',NULL,0),(3603,'Espectaculos Culturales',NULL,0),(3700,'Servicios de traslado e instalacion',NULL,0),(3701,'Pasajes nacionales',NULL,0),(3702,'Viaticos nacionales',NULL,0),(3704,'Traslado de personal',NULL,0),(3705,'Pasajes internacionales',NULL,0),(3706,'Viaticos en el extranjero',NULL,0),(3800,'Servicios oficiales',NULL,0),(3801,'Gastos de ceremonial y orden social',NULL,0),(3803,'Congresos, convenciones y exposiciones',NULL,0),(4303,'Seguros',NULL,0),(5100,'Mobiliario y Equipo de Administracion',NULL,0),(5101,'Mobiliario',NULL,0),(5102,'Equipo de Administracion',NULL,0),(5103,'Equipo Educacional y Recreativo',NULL,0),(5104,'Bienes Artisticos Y Culturales',NULL,0),(5201,'Maquinaria y Equipo Agropecuario',NULL,0),(5204,'Equipos y Aparatos de Comunicaciones y Telecomunicaciones',NULL,0),(5205,'Maquinaria y Equipo Electrico.',NULL,0),(5300,'Vehiculos y equipo de transporte',NULL,0),(5301,'Vehiculos y equipo terrestre',NULL,0),(5303,'Vehiculos y equipo maritimo, lacustre y fluvial',NULL,0),(5401,'Equipo medico',NULL,0),(5402,'Instrumental medico',NULL,0),(5501,'Herramientas y Maquinas-Herramienta',NULL,0),(5502,'Refacciones  y Accesorios Mayores',NULL,0),(5601,'Animales de trabajo',NULL,0),(5602,'Animales de reproduccion',NULL,0);
/*!40000 ALTER TABLE `cuentas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transacciones`
--

DROP TABLE IF EXISTS `transacciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transacciones` (
  `IdTransaccion` int(9) NOT NULL AUTO_INCREMENT,
  `Concepto` varchar(255) NOT NULL,
  `dinero` double NOT NULL,
  `CuentaCargo` int(6) NOT NULL,
  `CuentaAbono` int(6) NOT NULL,
  `Fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizada` char(1) DEFAULT '0',
  PRIMARY KEY (`IdTransaccion`),
  KEY `FK_transacciones_1` (`CuentaCargo`),
  KEY `FK_transacciones_2` (`CuentaAbono`),
  CONSTRAINT `FK_transacciones_1` FOREIGN KEY (`CuentaCargo`) REFERENCES `cuentas` (`IdCuenta`),
  CONSTRAINT `FK_transacciones_2` FOREIGN KEY (`CuentaAbono`) REFERENCES `cuentas` (`IdCuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transacciones`
--

LOCK TABLES `transacciones` WRITE;
/*!40000 ALTER TABLE `transacciones` DISABLE KEYS */;
INSERT INTO `transacciones` VALUES (1,'Contamos con $50000 en caja para mov.chicos',500000,1105,1000,'2011-11-10 17:48:30','1'),(2,'Iniciamos el ano con $1500000',1500000,1101,1000,'2011-11-10 17:51:41','1'),(15,'Papel',500,2101,1105,'2011-11-10 18:12:39','1');
/*!40000 ALTER TABLE `transacciones` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-11-14  9:41:15
