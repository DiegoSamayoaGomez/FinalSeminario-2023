-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: finalseminario
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `ID_CATEGORIA` int(11) NOT NULL,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `DESCRIPCION` varchar(50) DEFAULT NULL,
  `FECHA_INGRESA` date DEFAULT NULL,
  `FECHA_MODIFICA` date DEFAULT NULL,
  `USUARIO_INGRESA` int(11) DEFAULT NULL,
  `USUARIO_MODIFICA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_CATEGORIA`),
  KEY `fk_CATEGORIA_USUARIO1_idx` (`USUARIO_INGRESA`),
  KEY `fk_CATEGORIA_USUARIO2_idx` (`USUARIO_MODIFICA`),
  CONSTRAINT `fk_CATEGORIA_USUARIO1` FOREIGN KEY (`USUARIO_INGRESA`) REFERENCES `usuario` (`ID_USUARIO`),
  CONSTRAINT `fk_CATEGORIA_USUARIO2` FOREIGN KEY (`USUARIO_MODIFICA`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `ID_CLIENTE` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `APELLIDO` varchar(50) DEFAULT NULL,
  `NIT` varchar(50) DEFAULT NULL,
  `TELEFONO` varchar(50) DEFAULT NULL,
  `DIRECCION` varchar(50) DEFAULT NULL,
  `FECHA_INGRESA` date DEFAULT NULL,
  `FECHA_MODIFICA` date DEFAULT NULL,
  `USUARIO_INGRESA` int(11) DEFAULT NULL,
  `USUARIO_MODIFICA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_CLIENTE`),
  KEY `CLIENTE` (`ID_CLIENTE`),
  KEY `fk_CLIENTE_USUARIO1_idx` (`USUARIO_INGRESA`),
  KEY `fk_CLIENTE_USUARIO2_idx` (`USUARIO_MODIFICA`),
  CONSTRAINT `fk_CLIENTE_USUARIO1` FOREIGN KEY (`USUARIO_INGRESA`) REFERENCES `usuario` (`ID_USUARIO`),
  CONSTRAINT `fk_CLIENTE_USUARIO2` FOREIGN KEY (`USUARIO_MODIFICA`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compra` (
  `ID_COMPRA` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `ESTADO_FINALIZADO` bit(1) DEFAULT NULL,
  `FECHA_COMPRA` date DEFAULT NULL,
  `FECHA_INGRESA` date DEFAULT NULL,
  `FECHA_MODIFICA` date DEFAULT NULL,
  `USUARIO_INGRESA` int(11) DEFAULT NULL,
  `USUARIO_MODIFICA` int(11) DEFAULT NULL,
  `ID_PROVEEDOR` int(11) DEFAULT NULL,
  `ID_TIPO_PAGO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_COMPRA`),
  KEY `fk_COMPRA_TIPO_PAGO1_idx` (`ID_TIPO_PAGO`),
  KEY `fk_COMPRA_PROVEEDOR1_idx` (`ID_PROVEEDOR`),
  KEY `fk_COMPRA_USUARIO1_idx` (`USUARIO_INGRESA`),
  KEY `fk_COMPRA_USUARIO2_idx` (`USUARIO_MODIFICA`),
  CONSTRAINT `fk_COMPRA_PROVEEDOR1` FOREIGN KEY (`ID_PROVEEDOR`) REFERENCES `proveedor` (`ID_PROVEEDOR`),
  CONSTRAINT `fk_COMPRA_TIPO_PAGO1` FOREIGN KEY (`ID_TIPO_PAGO`) REFERENCES `tipo_pago` (`ID_TIPO_PAGO`),
  CONSTRAINT `fk_COMPRA_USUARIO1` FOREIGN KEY (`USUARIO_INGRESA`) REFERENCES `usuario` (`ID_USUARIO`),
  CONSTRAINT `fk_COMPRA_USUARIO2` FOREIGN KEY (`USUARIO_MODIFICA`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_compra`
--

DROP TABLE IF EXISTS `detalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_compra` (
  `ID_DETALLE_COMPRA` int(11) NOT NULL AUTO_INCREMENT,
  `CANTIDAD` int(11) DEFAULT NULL,
  `MONTO` decimal(10,2) DEFAULT NULL,
  `ID_COMPRA` int(11) DEFAULT NULL,
  `ID_PRODUCTO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_DETALLE_COMPRA`),
  KEY `fk_DETALLE_COMPRA_COMPRA1_idx` (`ID_COMPRA`),
  KEY `fk_DETALLE_COMPRA_PRODUCTO1_idx` (`ID_PRODUCTO`),
  CONSTRAINT `fk_DETALLE_COMPRA_COMPRA1` FOREIGN KEY (`ID_COMPRA`) REFERENCES `compra` (`ID_COMPRA`),
  CONSTRAINT `fk_DETALLE_COMPRA_PRODUCTO1` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `producto` (`ID_PRODUCTO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_compra`
--

LOCK TABLES `detalle_compra` WRITE;
/*!40000 ALTER TABLE `detalle_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_venta`
--

DROP TABLE IF EXISTS `detalle_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_venta` (
  `ID_DETALLE_VENTA` int(11) NOT NULL AUTO_INCREMENT,
  `CANTIDAD` int(11) DEFAULT NULL,
  `MONTO` decimal(10,2) DEFAULT NULL,
  `ID_VENTA` int(11) DEFAULT NULL,
  `ID_PRODUCTO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_DETALLE_VENTA`),
  KEY `fk_DETALLE_VENTA_VENTA1_idx` (`ID_VENTA`),
  KEY `fk_DETALLE_VENTA_PRODUCTO1_idx` (`ID_PRODUCTO`),
  CONSTRAINT `fk_DETALLE_VENTA_PRODUCTO1` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `producto` (`ID_PRODUCTO`),
  CONSTRAINT `fk_DETALLE_VENTA_VENTA1` FOREIGN KEY (`ID_VENTA`) REFERENCES `venta` (`ID_VENTA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `ID_PRODUCTO` int(11) NOT NULL,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `DESCRIPCION` varchar(60) DEFAULT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  `PRECIO` decimal(10,2) DEFAULT NULL,
  `FECHA_INGRESA` date DEFAULT NULL,
  `FECHA_MODIFICA` date DEFAULT NULL,
  `USUARIO_INGRESA` int(11) DEFAULT NULL,
  `USUARIO_MODIFICA` int(11) DEFAULT NULL,
  `ID_CATEGORIA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_PRODUCTO`),
  KEY `fk_PRODUCTO_CATEGORIA1_idx` (`ID_CATEGORIA`),
  KEY `fk_PRODUCTO_USUARIO1_idx` (`USUARIO_INGRESA`),
  KEY `fk_PRODUCTO_USUARIO2_idx` (`USUARIO_MODIFICA`),
  CONSTRAINT `fk_PRODUCTO_CATEGORIA1` FOREIGN KEY (`ID_CATEGORIA`) REFERENCES `categoria` (`ID_CATEGORIA`),
  CONSTRAINT `fk_PRODUCTO_USUARIO1` FOREIGN KEY (`USUARIO_INGRESA`) REFERENCES `usuario` (`ID_USUARIO`),
  CONSTRAINT `fk_PRODUCTO_USUARIO2` FOREIGN KEY (`USUARIO_MODIFICA`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `ID_PROVEEDOR` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `DIRECCION` varchar(50) DEFAULT NULL,
  `TELEFONO` varchar(50) DEFAULT NULL,
  `FECHA_INGRESA` date DEFAULT NULL,
  `FECHA_MODIFICA` date DEFAULT NULL,
  `USUARIO_INGRESA` int(11) DEFAULT NULL,
  `USUARIO_MODIFICA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_PROVEEDOR`),
  KEY `fk_PROVEEDOR_USUARIO1_idx` (`USUARIO_INGRESA`),
  KEY `fk_PROVEEDOR_USUARIO2_idx` (`USUARIO_MODIFICA`),
  CONSTRAINT `fk_PROVEEDOR_USUARIO1` FOREIGN KEY (`USUARIO_INGRESA`) REFERENCES `usuario` (`ID_USUARIO`),
  CONSTRAINT `fk_PROVEEDOR_USUARIO2` FOREIGN KEY (`USUARIO_MODIFICA`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `ID_ROL` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPCION` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_ROL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_pago`
--

DROP TABLE IF EXISTS `tipo_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_pago` (
  `ID_TIPO_PAGO` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_TIPO_PAGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_pago`
--

LOCK TABLES `tipo_pago` WRITE;
/*!40000 ALTER TABLE `tipo_pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `ID_USUARIO` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `CONTRASENIA` varchar(50) DEFAULT NULL,
  `ID_ROL` int(11) NOT NULL,
  PRIMARY KEY (`ID_USUARIO`),
  KEY `fk_USUARIO_ROL1_idx` (`ID_ROL`),
  CONSTRAINT `fk_USUARIO_ROL1` FOREIGN KEY (`ID_ROL`) REFERENCES `rol` (`ID_ROL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `ID_VENTA` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` bit(1) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `ESTADO_FINALIZADO` bit(1) DEFAULT NULL,
  `FECHA_VENTA` date DEFAULT NULL,
  `FECHA_INGRESA` date DEFAULT NULL,
  `FECHA_MODIFICA` date DEFAULT NULL,
  `USUARIO_INGRESA` int(11) DEFAULT NULL,
  `USUARIO_MODIFICA` int(11) DEFAULT NULL,
  `ID_CLIENTE` int(11) DEFAULT NULL,
  `ID_TIPO_PAGO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_VENTA`),
  KEY `fk_VENTA_TIPO_PAGO1_idx` (`ID_TIPO_PAGO`),
  KEY `fk_VENTA_CLIENTE1_idx` (`ID_CLIENTE`),
  KEY `fk_VENTA_USUARIO1_idx` (`USUARIO_INGRESA`),
  KEY `fk_VENTA_USUARIO2_idx` (`USUARIO_MODIFICA`),
  CONSTRAINT `fk_VENTA_CLIENTE1` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `cliente` (`ID_CLIENTE`),
  CONSTRAINT `fk_VENTA_TIPO_PAGO1` FOREIGN KEY (`ID_TIPO_PAGO`) REFERENCES `tipo_pago` (`ID_TIPO_PAGO`),
  CONSTRAINT `fk_VENTA_USUARIO1` FOREIGN KEY (`USUARIO_INGRESA`) REFERENCES `usuario` (`ID_USUARIO`),
  CONSTRAINT `fk_VENTA_USUARIO2` FOREIGN KEY (`USUARIO_MODIFICA`) REFERENCES `usuario` (`ID_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-09 13:12:14
