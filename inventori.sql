/*
 Navicat Premium Dump SQL

 Source Server         : LocalHostMysql
 Source Server Type    : MySQL
 Source Server Version : 100432 (10.4.32-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : inventori

 Target Server Type    : MySQL
 Target Server Version : 100432 (10.4.32-MariaDB)
 File Encoding         : 65001

 Date: 20/07/2026 23:28:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tmbarang
-- ----------------------------
DROP TABLE IF EXISTS `tmbarang`;
CREATE TABLE `tmbarang`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `kode` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_kategori` int NOT NULL,
  `satuan` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `stok` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `myKey`(`id_kategori` ASC) USING BTREE,
  CONSTRAINT `myKey` FOREIGN KEY (`id_kategori`) REFERENCES `tmkategori` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tmbarang
-- ----------------------------
INSERT INTO `tmbarang` VALUES (5, 'A-005', 'Xiaomi TV', 1, 'Buah', 65);
INSERT INTO `tmbarang` VALUES (7, 'S-022', 'LG', 1, 'Buah', 10);
INSERT INTO `tmbarang` VALUES (9, 'S-021', 'Samsung 21 Inch', 1, 'Buah', 80);
INSERT INTO `tmbarang` VALUES (10, 'A-011', 'Toshiba 20 Inch', 1, 'Buah', 10);
INSERT INTO `tmbarang` VALUES (11, '90AB', 'LCD TV Samsung', 1, 'Buah', 90);
INSERT INTO `tmbarang` VALUES (12, 'F-010', 'Samsung 24 Inch', 1, 'Buah', 80);
INSERT INTO `tmbarang` VALUES (13, 'A-001', 'Simbada', 3, 'Buah', 70);
INSERT INTO `tmbarang` VALUES (14, 'M-001', 'MacBook Pro 15 Inch', 6, 'Buah', 14);
INSERT INTO `tmbarang` VALUES (15, 'M-002', 'MacBook Pro 17 Inch', 6, 'Buah', 19);
INSERT INTO `tmbarang` VALUES (16, 'C-001', 'Acer Aspire One 15 Inch', 8, 'Buah', 15);
INSERT INTO `tmbarang` VALUES (17, 'C-002', 'Acer Aspire One 18 Inch', 8, 'Buah', 6);
INSERT INTO `tmbarang` VALUES (18, 'A-002', 'Simbada Type C3', 3, 'Buah', 68);
INSERT INTO `tmbarang` VALUES (19, 'S-001', 'Sharp TV', 1, 'Buah', 3);

-- ----------------------------
-- Table structure for tmkategori
-- ----------------------------
DROP TABLE IF EXISTS `tmkategori`;
CREATE TABLE `tmkategori`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `no_rak` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tmkategori
-- ----------------------------
INSERT INTO `tmkategori` VALUES (1, 'TV', 1);
INSERT INTO `tmkategori` VALUES (2, 'DVD', 2);
INSERT INTO `tmkategori` VALUES (3, 'Sound System', 10);
INSERT INTO `tmkategori` VALUES (4, 'Joy Stick', 5);
INSERT INTO `tmkategori` VALUES (5, 'MacBook', 3);
INSERT INTO `tmkategori` VALUES (6, 'MacBook Pro', 3);
INSERT INTO `tmkategori` VALUES (7, 'MacBook Air', 3);
INSERT INTO `tmkategori` VALUES (8, 'Laptop', 4);

-- ----------------------------
-- Table structure for tmpelanggan
-- ----------------------------
DROP TABLE IF EXISTS `tmpelanggan`;
CREATE TABLE `tmpelanggan`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `kode` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `alamat` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `no_telp` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tmpelanggan
-- ----------------------------
INSERT INTO `tmpelanggan` VALUES (1, 'P001', 'ITC BSD', 'Jl. Pahlawan seribu', '0222');
INSERT INTO `tmpelanggan` VALUES (2, 'P002', 'WTC Matahari', 'Jl. Serpong', '0872727727');
INSERT INTO `tmpelanggan` VALUES (3, 'P003', 'BSD Plaza', 'Jl. Serpong', '08929292');

-- ----------------------------
-- Table structure for tmpetugas
-- ----------------------------
DROP TABLE IF EXISTS `tmpetugas`;
CREATE TABLE `tmpetugas`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `username` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `status` enum('aktif','nonaktif') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tmpetugas
-- ----------------------------
INSERT INTO `tmpetugas` VALUES (2, 'Administrator', 'admin', 'admin', 'aktif');

-- ----------------------------
-- Table structure for tmsupplier
-- ----------------------------
DROP TABLE IF EXISTS `tmsupplier`;
CREATE TABLE `tmsupplier`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `kode` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `alamat` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `no_telp` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tmsupplier
-- ----------------------------
INSERT INTO `tmsupplier` VALUES (1, 'S008', 'Apple Inc', 'Jl. Banjar, Kota banjarmasin', '0228192832');
INSERT INTO `tmsupplier` VALUES (3, 'S005', 'Microsoft Inc', 'Jl. Pasir Kaliki', '08282828');
INSERT INTO `tmsupplier` VALUES (4, 'S002', 'Lenovo Inc', 'Jakarta', '08282828');
INSERT INTO `tmsupplier` VALUES (5, 'S004', 'Acer Inc', 'Bandung', '0222');

-- ----------------------------
-- Table structure for trbarang_keluar
-- ----------------------------
DROP TABLE IF EXISTS `trbarang_keluar`;
CREATE TABLE `trbarang_keluar`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `tgl` date NOT NULL,
  `id_petugas` int NOT NULL,
  `id_pelanggan` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trbarang_keluar
-- ----------------------------
INSERT INTO `trbarang_keluar` VALUES (1, '2016-12-19', 2, 1);
INSERT INTO `trbarang_keluar` VALUES (2, '2016-12-19', 2, 1);
INSERT INTO `trbarang_keluar` VALUES (3, '2016-12-19', 2, 1);
INSERT INTO `trbarang_keluar` VALUES (4, '2016-12-19', 2, 1);
INSERT INTO `trbarang_keluar` VALUES (5, '2016-12-19', 2, 2);
INSERT INTO `trbarang_keluar` VALUES (6, '2016-12-22', 2, 1);
INSERT INTO `trbarang_keluar` VALUES (7, '2016-12-22', 2, 2);

-- ----------------------------
-- Table structure for trbarang_keluar_detail
-- ----------------------------
DROP TABLE IF EXISTS `trbarang_keluar_detail`;
CREATE TABLE `trbarang_keluar_detail`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_barang_keluar` int NOT NULL,
  `id_barang` int NOT NULL,
  `jumlah` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trbarang_keluar_detail
-- ----------------------------
INSERT INTO `trbarang_keluar_detail` VALUES (1, 1, 14, 10);
INSERT INTO `trbarang_keluar_detail` VALUES (2, 1, 15, 11);
INSERT INTO `trbarang_keluar_detail` VALUES (3, 2, 14, 5);
INSERT INTO `trbarang_keluar_detail` VALUES (4, 2, 15, 6);
INSERT INTO `trbarang_keluar_detail` VALUES (5, 3, 14, 5);
INSERT INTO `trbarang_keluar_detail` VALUES (6, 3, 15, 6);
INSERT INTO `trbarang_keluar_detail` VALUES (7, 4, 14, 10);
INSERT INTO `trbarang_keluar_detail` VALUES (8, 4, 15, 11);
INSERT INTO `trbarang_keluar_detail` VALUES (9, 5, 16, 10);
INSERT INTO `trbarang_keluar_detail` VALUES (10, 5, 17, 11);
INSERT INTO `trbarang_keluar_detail` VALUES (11, 6, 13, 10);
INSERT INTO `trbarang_keluar_detail` VALUES (12, 6, 18, 12);
INSERT INTO `trbarang_keluar_detail` VALUES (13, 7, 5, 15);

-- ----------------------------
-- Table structure for trbarang_masuk
-- ----------------------------
DROP TABLE IF EXISTS `trbarang_masuk`;
CREATE TABLE `trbarang_masuk`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `tgl` date NOT NULL,
  `id_petugas` int NOT NULL,
  `id_supplier` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trbarang_masuk
-- ----------------------------
INSERT INTO `trbarang_masuk` VALUES (3, '2016-12-17', 2, 1);
INSERT INTO `trbarang_masuk` VALUES (4, '2016-12-19', 2, 1);
INSERT INTO `trbarang_masuk` VALUES (5, '2016-12-19', 2, 1);
INSERT INTO `trbarang_masuk` VALUES (6, '2016-12-19', 2, 5);
INSERT INTO `trbarang_masuk` VALUES (7, '2016-12-22', 2, 1);

-- ----------------------------
-- Table structure for trbarang_masuk_detail
-- ----------------------------
DROP TABLE IF EXISTS `trbarang_masuk_detail`;
CREATE TABLE `trbarang_masuk_detail`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_barang_masuk` int NOT NULL,
  `id_barang` int NOT NULL,
  `jumlah` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trbarang_masuk_detail
-- ----------------------------
INSERT INTO `trbarang_masuk_detail` VALUES (5, 3, 14, 5);
INSERT INTO `trbarang_masuk_detail` VALUES (6, 3, 15, 6);
INSERT INTO `trbarang_masuk_detail` VALUES (7, 4, 14, 10);
INSERT INTO `trbarang_masuk_detail` VALUES (8, 4, 15, 11);
INSERT INTO `trbarang_masuk_detail` VALUES (9, 5, 14, 20);
INSERT INTO `trbarang_masuk_detail` VALUES (10, 5, 15, 25);
INSERT INTO `trbarang_masuk_detail` VALUES (11, 6, 16, 5);
INSERT INTO `trbarang_masuk_detail` VALUES (12, 6, 17, 6);
INSERT INTO `trbarang_masuk_detail` VALUES (13, 7, 14, 4);
INSERT INTO `trbarang_masuk_detail` VALUES (14, 7, 15, 5);

SET FOREIGN_KEY_CHECKS = 1;
