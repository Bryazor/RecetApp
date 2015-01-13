-- MySQL dump 10.13  Distrib 5.5.9, for Win32 (x86)
--
-- Host: localhost    Database: recetapp
-- ------------------------------------------------------
-- Server version	5.5.15

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
-- Current Database: `recetapp`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `recetapp` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci */;

USE `recetapp`;

--
-- Table structure for table `anotaciones`
--

DROP TABLE IF EXISTS `anotaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anotaciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `anotaciones` text COLLATE latin1_spanish_ci NOT NULL,
  `id_tipo` bigint(20) NOT NULL,
  `id_receta` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `anotaciones_2_receta_fk` (`id_receta`),
  KEY `anotaciones_2_tiposanot_fk` (`id_tipo`),
  CONSTRAINT `anotaciones_2_receta_fk` FOREIGN KEY (`id_receta`) REFERENCES `recetas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `anotaciones_2_tiposanot_fk` FOREIGN KEY (`id_tipo`) REFERENCES `tipos_anotaciones` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anotaciones`
--

LOCK TABLES `anotaciones` WRITE;
/*!40000 ALTER TABLE `anotaciones` DISABLE KEYS */;
INSERT INTO `anotaciones` VALUES (1,'Conviene dejar reposar antes de servir.',1,1),(2,'Puede aprovechar los 35 minutos de tiempo en temperatura Varoma para hacer patatas al vapor y servirlas como guarnición.',3,1);
/*!40000 ALTER TABLE `anotaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorias` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Carnes'),(2,'Caldos'),(3,'Arroces'),(4,'Postres'),(5,'Entrantes');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredientes`
--

DROP TABLE IF EXISTS `ingredientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingredientes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` float NOT NULL,
  `id_medida` bigint(20) NOT NULL,
  `id_tipo` bigint(20) NOT NULL,
  `id_parte` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ingredientes_2_partes_fk` (`id_parte`),
  KEY `ingredientes_2_medidas_fk` (`id_medida`),
  KEY `ingredientes_2_tiposingred_fk` (`id_tipo`),
  CONSTRAINT `ingredientes_2_partes_fk` FOREIGN KEY (`id_parte`) REFERENCES `partes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ingredientes_2_medidas_fk` FOREIGN KEY (`id_medida`) REFERENCES `medidas` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `ingredientes_2_tiposingred_fk` FOREIGN KEY (`id_tipo`) REFERENCES `tipos_ingredientes` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredientes`
--

LOCK TABLES `ingredientes` WRITE;
/*!40000 ALTER TABLE `ingredientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingredientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instrucciones`
--

DROP TABLE IF EXISTS `instrucciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instrucciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` text COLLATE latin1_spanish_ci,
  `orden` int(11) NOT NULL,
  `id_parte` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `instrucciones_2_partes_fk` (`id_parte`),
  CONSTRAINT `instrucciones_2_partes_fk` FOREIGN KEY (`id_parte`) REFERENCES `partes` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instrucciones`
--

LOCK TABLES `instrucciones` WRITE;
/*!40000 ALTER TABLE `instrucciones` DISABLE KEYS */;
INSERT INTO `instrucciones` VALUES (1,'Ponga en el vaso la cebollay el ajo. Trocee programando 5 segundos, velocidad 5. Con la espátula, baje los restos de cebolla y ajo de la tapa y del interior del vaso hacia las cuchillas.',1,1),(2,'Añada el aceite y programe 10 minutos, temperatura Varoma, velocidad 1.',2,1),(3,'Incorpore la carne ligeramente enharinada, las zanahorias, la sal, la pimienta, el laurel y el clavo. Programe 5 minutos, 100º, velocidad cuchara. Cuendo falte 1 minuto, incorpore el vino a través del bocal de la tapa.',3,1),(4,'Añada el agua y programe 35 minutos, temperatura Varona, giro a la izquierda, velocidad cuchara.',4,1),(5,'Agregue las alcachofas y programe 10 minutos, 100º, giro a la izquierda, velocidad cuchara.',5,1),(6,'Compruebe el punto de cocción de la carne y las alcachofas y, si fuese necesario, programe 5 minutos más a la misma temperatura y velocidad.',6,1);
/*!40000 ALTER TABLE `instrucciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medidas`
--

DROP TABLE IF EXISTS `medidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medidas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  `abreviatura` varchar(50) COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medidas`
--

LOCK TABLES `medidas` WRITE;
/*!40000 ALTER TABLE `medidas` DISABLE KEYS */;
INSERT INTO `medidas` VALUES (1,'gramo(s)','g'),(2,'kilo(s)','kg'),(3,'cucharadita(s)',NULL),(4,'cucharadita(s) colmada(s)',NULL),(5,'cucharada(s)',NULL),(6,'diente(s)',NULL),(7,'pellizco(s)',NULL),(8,'',NULL),(9,'hoja(s)',NULL);
/*!40000 ALTER TABLE `medidas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partes`
--

DROP TABLE IF EXISTS `partes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `id_receta` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `partes_2_recetas_fk` (`id_receta`),
  CONSTRAINT `partes_2_recetas_fk` FOREIGN KEY (`id_receta`) REFERENCES `recetas` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partes`
--

LOCK TABLES `partes` WRITE;
/*!40000 ALTER TABLE `partes` DISABLE KEYS */;
INSERT INTO `partes` VALUES (1,'Estafado de cerdo',1);
/*!40000 ALTER TABLE `partes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recetas`
--

DROP TABLE IF EXISTS `recetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recetas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT '0',
  `para` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  `tiempo_total` int(11) NOT NULL DEFAULT '0',
  `tiempo_thermomix` int(11) NOT NULL DEFAULT '0',
  `id_categoria` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `recetas_2_categoria` (`id_categoria`),
  CONSTRAINT `recetas_2_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recetas`
--

LOCK TABLES `recetas` WRITE;
/*!40000 ALTER TABLE `recetas` DISABLE KEYS */;
INSERT INTO `recetas` VALUES (1,'Estofado de cerdo con alcachofas','2013-11-22 13:11:00',6,'personas',4080,3905,1);
/*!40000 ALTER TABLE `recetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_anotaciones`
--

DROP TABLE IF EXISTS `tipos_anotaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_anotaciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) COLLATE latin1_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_anotaciones`
--

LOCK TABLES `tipos_anotaciones` WRITE;
/*!40000 ALTER TABLE `tipos_anotaciones` DISABLE KEYS */;
INSERT INTO `tipos_anotaciones` VALUES (1,'Nota'),(2,'Comentario'),(3,'Sugerencia'),(4,'Observación');
/*!40000 ALTER TABLE `tipos_anotaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_ingredientes`
--

DROP TABLE IF EXISTS `tipos_ingredientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_ingredientes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_ingredientes`
--

LOCK TABLES `tipos_ingredientes` WRITE;
/*!40000 ALTER TABLE `tipos_ingredientes` DISABLE KEYS */;
INSERT INTO `tipos_ingredientes` VALUES (1,'cebolla en cuartos'),(2,'ajo'),(3,'aceite de oliva'),(4,'harina'),(5,'magro de cerdo en trozos pequeños'),(6,'zanahoria en rodajas gruesas'),(7,'sal'),(8,'pimienta negra molida'),(9,'laurel'),(10,'clavo de olor'),(11,'vino tinto'),(12,'agua'),(13,'alcachofa en cuartos (congelada)');
/*!40000 ALTER TABLE `tipos_ingredientes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-24  1:00:04
