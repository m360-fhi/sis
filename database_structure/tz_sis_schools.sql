CREATE DATABASE  IF NOT EXISTS `tz_sis_schools` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tz_sis_schools`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: tz_sis_schools
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `catalog_school_grade`
--

DROP TABLE IF EXISTS `catalog_school_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog_school_grade` (
  `level` int(10) unsigned NOT NULL,
  `grade` int(10) unsigned NOT NULL,
  `grade_label` varchar(45) NOT NULL,
  PRIMARY KEY (`level`,`grade`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `catalog_school_level`
--

DROP TABLE IF EXISTS `catalog_school_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog_school_level` (
  `level` int(10) unsigned NOT NULL,
  `level_label` varchar(45) NOT NULL,
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `catalog_school_shift`
--

DROP TABLE IF EXISTS `catalog_school_shift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog_school_shift` (
  `shift` int(10) unsigned NOT NULL,
  `shift_label` varchar(45) NOT NULL,
  PRIMARY KEY (`shift`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `catalog_school_subject`
--

DROP TABLE IF EXISTS `catalog_school_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog_school_subject` (
  `level` int(10) unsigned NOT NULL,
  `subject` int(10) unsigned NOT NULL,
  `subject_label` varchar(100) NOT NULL,
  PRIMARY KEY (`level`,`subject`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `catalog_school_type`
--

DROP TABLE IF EXISTS `catalog_school_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog_school_type` (
  `school_type` int(10) unsigned NOT NULL,
  `school_type_label` varchar(45) NOT NULL,
  PRIMARY KEY (`school_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `catalog_school_zone`
--

DROP TABLE IF EXISTS `catalog_school_zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog_school_zone` (
  `zone` int(10) unsigned NOT NULL,
  `zone_label` varchar(45) NOT NULL,
  PRIMARY KEY (`zone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `definition_council`
--

DROP TABLE IF EXISTS `definition_council`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `definition_council` (
  `region_code` int(11) unsigned NOT NULL,
  `council_code` int(11) unsigned NOT NULL,
  `council_name` varchar(45) NOT NULL,
  PRIMARY KEY (`region_code`,`council_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `definition_region`
--

DROP TABLE IF EXISTS `definition_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `definition_region` (
  `region_code` int(11) unsigned NOT NULL,
  `region_name` varchar(45) NOT NULL,
  PRIMARY KEY (`region_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `definition_school_calendar`
--

DROP TABLE IF EXISTS `definition_school_calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `definition_school_calendar` (
  `DateTZ` date NOT NULL,
  `DayOfWeekTZ` int(11) DEFAULT NULL,
  `DayNumberTZ` int(11) DEFAULT NULL,
  `WeekNumberTZ` int(11) DEFAULT NULL,
  `MonthNumberTZ` int(11) DEFAULT NULL,
  `IsSchoolDayTZ` int(11) DEFAULT NULL,
  `TermTZ` int(11) DEFAULT NULL,
  PRIMARY KEY (`DateTZ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `definition_ward`
--

DROP TABLE IF EXISTS `definition_ward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `definition_ward` (
  `id_correlative` int(11) NOT NULL AUTO_INCREMENT,
  `region_code` int(11) NOT NULL,
  `council_code` int(11) NOT NULL,
  `ward_code` int(11) NOT NULL,
  `ward_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id_correlative`,`region_code`,`council_code`,`ward_code`),
  KEY `council_idx` (`region_code`,`council_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7163 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `enrollment_view`
--

DROP TABLE IF EXISTS `enrollment_view`;
/*!50001 DROP VIEW IF EXISTS `enrollment_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `enrollment_view` AS SELECT 
 1 AS `id`,
 1 AS `year`,
 1 AS `emis_code`,
 1 AS `school_shift`,
 1 AS `school_level`,
 1 AS `school_grade`,
 1 AS `student_age`,
 1 AS `sex`,
 1 AS `total`,
 1 AS `date_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `module_a_school_profile`
--

DROP TABLE IF EXISTS `module_a_school_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_a_school_profile` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `school_name` varchar(100) DEFAULT NULL,
  `school_region_code` int(11) DEFAULT NULL,
  `school_council_code` int(11) DEFAULT NULL,
  `school_ward_code` int(11) DEFAULT NULL,
  `school_zone` int(11) DEFAULT NULL,
  `school_mobile_number` varchar(45) DEFAULT NULL,
  `school_email_address` varchar(45) DEFAULT NULL,
  `school_form4` int(11) DEFAULT NULL,
  `school_form6` int(11) DEFAULT NULL,
  `school_type` int(11) DEFAULT NULL,
  `school_level` varchar(100) DEFAULT NULL,
  `school_registration_number` varchar(45) DEFAULT NULL,
  `school_year_established` int(11) DEFAULT NULL,
  `gps_longitude` decimal(38,10) DEFAULT NULL,
  `gps_latitude` decimal(38,10) DEFAULT NULL,
  `school_student_count` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `idx_module_a_school_profile_emis_code` (`emis_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8531 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_d_preprimary_education_operations`
--

DROP TABLE IF EXISTS `module_d_preprimary_education_operations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_d_preprimary_education_operations` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `classroom_solid` int(11) DEFAULT NULL,
  `classroom_semi_solid` int(11) DEFAULT NULL,
  `classroom_makeshift` int(11) DEFAULT NULL,
  `classroom_partitioned` int(11) DEFAULT NULL,
  `classroom_open_air` int(11) DEFAULT NULL,
  `classroom_other` int(11) DEFAULT NULL,
  `chairs` int(11) DEFAULT NULL,
  `desk_1_seater` int(11) DEFAULT NULL,
  `desk_2_seater` int(11) DEFAULT NULL,
  `desk_3_seater` int(11) DEFAULT NULL,
  `bench_1_seater` int(11) DEFAULT NULL,
  `bench_2_seater` int(11) DEFAULT NULL,
  `bench_3_seater` int(11) DEFAULT NULL,
  `chalkboard` int(11) DEFAULT NULL,
  `tables_count` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `emis` (`emis_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8715 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_f_primary_curriculum_instruction`
--

DROP TABLE IF EXISTS `module_f_primary_curriculum_instruction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_f_primary_curriculum_instruction` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `have_national_curriculum` int(11) DEFAULT NULL,
  `number_national_curriculum` int(11) DEFAULT NULL,
  `math_standard_1` int(11) DEFAULT NULL,
  `math_standard_2` int(11) DEFAULT NULL,
  `math_standard_3` int(11) DEFAULT NULL,
  `math_standard_4` int(11) DEFAULT NULL,
  `math_standard_5` int(11) DEFAULT NULL,
  `math_standard_6` int(11) DEFAULT NULL,
  `math_standard_7` int(11) DEFAULT NULL,
  `english_standard_1` int(11) DEFAULT NULL,
  `english_standard_2` int(11) DEFAULT NULL,
  `english_standard_3` int(11) DEFAULT NULL,
  `english_standard_4` int(11) DEFAULT NULL,
  `english_standard_5` int(11) DEFAULT NULL,
  `english_standard_6` int(11) DEFAULT NULL,
  `english_standard_7` int(11) DEFAULT NULL,
  `kiswahili_standard_1` int(11) DEFAULT NULL,
  `kiswahili_standard_2` int(11) DEFAULT NULL,
  `kiswahili_standard_3` int(11) DEFAULT NULL,
  `kiswahili_standard_4` int(11) DEFAULT NULL,
  `kiswahili_standard_5` int(11) DEFAULT NULL,
  `kiswahili_standard_6` int(11) DEFAULT NULL,
  `kiswahili_standard_7` int(11) DEFAULT NULL,
  `french_standard_1` int(11) DEFAULT NULL,
  `french_standard_2` int(11) DEFAULT NULL,
  `french_standard_3` int(11) DEFAULT NULL,
  `french_standard_4` int(11) DEFAULT NULL,
  `french_standard_5` int(11) DEFAULT NULL,
  `french_standard_6` int(11) DEFAULT NULL,
  `french_standard_7` int(11) DEFAULT NULL,
  `science_standard_1` int(11) DEFAULT NULL,
  `science_standard_2` int(11) DEFAULT NULL,
  `science_standard_3` int(11) DEFAULT NULL,
  `science_standard_4` int(11) DEFAULT NULL,
  `science_standard_5` int(11) DEFAULT NULL,
  `science_standard_6` int(11) DEFAULT NULL,
  `science_standard_7` int(11) DEFAULT NULL,
  `geography_standard_1` int(11) DEFAULT NULL,
  `geography_standard_2` int(11) DEFAULT NULL,
  `geography_standard_3` int(11) DEFAULT NULL,
  `geography_standard_4` int(11) DEFAULT NULL,
  `geography_standard_5` int(11) DEFAULT NULL,
  `geography_standard_6` int(11) DEFAULT NULL,
  `geography_standard_7` int(11) DEFAULT NULL,
  `civics_standard_1` int(11) DEFAULT NULL,
  `civics_standard_2` int(11) DEFAULT NULL,
  `civics_standard_3` int(11) DEFAULT NULL,
  `civics_standard_4` int(11) DEFAULT NULL,
  `civics_standard_5` int(11) DEFAULT NULL,
  `civics_standard_6` int(11) DEFAULT NULL,
  `civics_standard_7` int(11) DEFAULT NULL,
  `history_standard_1` int(11) DEFAULT NULL,
  `history_standard_2` int(11) DEFAULT NULL,
  `history_standard_3` int(11) DEFAULT NULL,
  `history_standard_4` int(11) DEFAULT NULL,
  `history_standard_5` int(11) DEFAULT NULL,
  `history_standard_6` int(11) DEFAULT NULL,
  `history_standard_7` int(11) DEFAULT NULL,
  `vskills_standard_1` int(11) DEFAULT NULL,
  `vskills_standard_2` int(11) DEFAULT NULL,
  `vskills_standard_3` int(11) DEFAULT NULL,
  `vskills_standard_4` int(11) DEFAULT NULL,
  `vskills_standard_5` int(11) DEFAULT NULL,
  `vskills_standard_6` int(11) DEFAULT NULL,
  `vskills_standard_7` int(11) DEFAULT NULL,
  `per_sports_standard_1` int(11) DEFAULT NULL,
  `per_sports_standard_2` int(11) DEFAULT NULL,
  `per_sports_standard_3` int(11) DEFAULT NULL,
  `per_sports_standard_4` int(11) DEFAULT NULL,
  `per_sports_standard_5` int(11) DEFAULT NULL,
  `per_sports_standard_6` int(11) DEFAULT NULL,
  `per_sports_standard_7` int(11) DEFAULT NULL,
  `ict_standard_1` int(11) DEFAULT NULL,
  `ict_standard_2` int(11) DEFAULT NULL,
  `ict_standard_3` int(11) DEFAULT NULL,
  `ict_standard_4` int(11) DEFAULT NULL,
  `ict_standard_5` int(11) DEFAULT NULL,
  `ict_standard_6` int(11) DEFAULT NULL,
  `ict_standard_7` int(11) DEFAULT NULL,
  `religion_standard_1` int(11) DEFAULT NULL,
  `religion_standard_2` int(11) DEFAULT NULL,
  `religion_standard_3` int(11) DEFAULT NULL,
  `religion_standard_4` int(11) DEFAULT NULL,
  `religion_standard_5` int(11) DEFAULT NULL,
  `religion_standard_6` int(11) DEFAULT NULL,
  `religion_standard_7` int(11) DEFAULT NULL,
  `reading_standard_1` int(11) DEFAULT NULL,
  `reading_standard_2` int(11) DEFAULT NULL,
  `reading_standard_3` int(11) DEFAULT NULL,
  `reading_standard_4` int(11) DEFAULT NULL,
  `reading_standard_5` int(11) DEFAULT NULL,
  `reading_standard_6` int(11) DEFAULT NULL,
  `reading_standard_7` int(11) DEFAULT NULL,
  `writing_standard_1` int(11) DEFAULT NULL,
  `writing_standard_2` int(11) DEFAULT NULL,
  `writing_standard_3` int(11) DEFAULT NULL,
  `writing_standard_4` int(11) DEFAULT NULL,
  `writing_standard_5` int(11) DEFAULT NULL,
  `writing_standard_6` int(11) DEFAULT NULL,
  `writing_standard_7` int(11) DEFAULT NULL,
  `arithmetic_standard_1` int(11) DEFAULT NULL,
  `arithmetic_standard_2` int(11) DEFAULT NULL,
  `arithmetic_standard_3` int(11) DEFAULT NULL,
  `arithmetic_standard_4` int(11) DEFAULT NULL,
  `arithmetic_standard_5` int(11) DEFAULT NULL,
  `arithmetic_standard_6` int(11) DEFAULT NULL,
  `arithmetic_standard_7` int(11) DEFAULT NULL,
  `health_standard_1` int(11) DEFAULT NULL,
  `health_standard_2` int(11) DEFAULT NULL,
  `health_standard_3` int(11) DEFAULT NULL,
  `health_standard_4` int(11) DEFAULT NULL,
  `health_standard_5` int(11) DEFAULT NULL,
  `health_standard_6` int(11) DEFAULT NULL,
  `health_standard_7` int(11) DEFAULT NULL,
  `games_standard_1` int(11) DEFAULT NULL,
  `games_standard_2` int(11) DEFAULT NULL,
  `games_standard_3` int(11) DEFAULT NULL,
  `games_standard_4` int(11) DEFAULT NULL,
  `games_standard_5` int(11) DEFAULT NULL,
  `games_standard_6` int(11) DEFAULT NULL,
  `games_standard_7` int(11) DEFAULT NULL,
  `other_standard_1` int(11) DEFAULT NULL,
  `other_standard_2` int(11) DEFAULT NULL,
  `other_standard_3` int(11) DEFAULT NULL,
  `other_standard_4` int(11) DEFAULT NULL,
  `other_standard_5` int(11) DEFAULT NULL,
  `other_standard_6` int(11) DEFAULT NULL,
  `other_standard_7` int(11) DEFAULT NULL,
  `total_textbooks_nat_curr_la` int(11) DEFAULT NULL,
  `total_textbooks_nat_curr_m` int(11) DEFAULT NULL,
  `total_textbooks_nat_curr_s` int(11) DEFAULT NULL,
  `total_textbooks_nat_curr_ss` int(11) DEFAULT NULL,
  `total_textbooks_nat_curr_o` int(11) DEFAULT NULL,
  `often_use_textbooks_la` int(11) DEFAULT NULL,
  `often_use_textbooks_m` int(11) DEFAULT NULL,
  `often_use_textbooks_s` int(11) DEFAULT NULL,
  `often_use_textbooks_ss` int(11) DEFAULT NULL,
  `often_use_textbooks_o` int(11) DEFAULT NULL,
  `safe_location` int(11) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `social_standard_3` int(11) DEFAULT NULL,
  `social_standard_4` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `emis` (`emis_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8028 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_g_primary_infrastructure_furniture`
--

DROP TABLE IF EXISTS `module_g_primary_infrastructure_furniture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_g_primary_infrastructure_furniture` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `classrooms_solid` int(11) DEFAULT NULL,
  `classrooms_semi_solid` int(11) DEFAULT NULL,
  `classrooms_makeshift` int(11) DEFAULT NULL,
  `classrooms_partitioned` int(11) DEFAULT NULL,
  `classrooms_open_air` int(11) DEFAULT NULL,
  `classrooms_other` int(11) DEFAULT NULL,
  `chairs` int(11) DEFAULT NULL,
  `desk_1_seater` int(11) DEFAULT NULL,
  `desk_2_seater` int(11) DEFAULT NULL,
  `desk_3_seater` int(11) DEFAULT NULL,
  `bench_1_seater` int(11) DEFAULT NULL,
  `bench_2_seater` int(11) DEFAULT NULL,
  `bench_3_seater` int(11) DEFAULT NULL,
  `chalkboard` int(11) DEFAULT NULL,
  `tables_count` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `emis` (`emis_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8616 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_q_school_infrastructure`
--

DROP TABLE IF EXISTS `module_q_school_infrastructure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_q_school_infrastructure` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `school_solid` int(11) DEFAULT NULL,
  `school_makeshift` int(11) DEFAULT NULL,
  `school_semi_solid` int(11) DEFAULT NULL,
  `school_open_air` int(11) DEFAULT NULL,
  `school_other` int(11) DEFAULT NULL,
  `school_library` int(11) DEFAULT NULL,
  `school_librarian` int(11) DEFAULT NULL,
  `school_reading_space` int(11) DEFAULT NULL,
  `school_number_books` int(11) DEFAULT NULL,
  `school_number_shelves` int(11) DEFAULT NULL,
  `school_cellphone` int(11) DEFAULT NULL,
  `school_fence_surrounding` int(11) DEFAULT NULL,
  `school_drinking_water` int(11) DEFAULT NULL,
  `school_latrine` int(11) DEFAULT NULL,
  `school_boys_only` int(11) DEFAULT NULL,
  `school_girls_only` int(11) DEFAULT NULL,
  `school_boys_girls` int(11) DEFAULT NULL,
  `school_male_staff` int(11) DEFAULT NULL,
  `school_female_staff` int(11) DEFAULT NULL,
  `school_male_female_staff` int(11) DEFAULT NULL,
  `school_auditorium` int(11) DEFAULT NULL,
  `school_health_clinic` int(11) DEFAULT NULL,
  `school_head_office` int(11) DEFAULT NULL,
  `school_farm` int(11) DEFAULT NULL,
  `school_staff_room` int(11) DEFAULT NULL,
  `school_hand_washing_facility` int(11) DEFAULT NULL,
  `school_garden` int(11) DEFAULT NULL,
  `school_admin_block` int(11) DEFAULT NULL,
  `school_source_power` int(11) DEFAULT NULL,
  `school_computer_lab` int(11) DEFAULT NULL,
  `school_func_generator` int(11) DEFAULT NULL,
  `school_regular_fuel` int(11) DEFAULT NULL,
  `school_sports_playground` int(11) DEFAULT NULL,
  `school_staff_housing` int(11) DEFAULT NULL,
  `school_science_lab` int(11) DEFAULT NULL,
  `school_transport` int(11) DEFAULT NULL,
  `school_cafeteria` int(11) DEFAULT NULL,
  `school_storage` int(11) DEFAULT NULL,
  `school_wood_work` int(11) DEFAULT NULL,
  `school_home_economic` int(11) DEFAULT NULL,
  `school_metal_work` int(11) DEFAULT NULL,
  `school_auto_mechanics` int(11) DEFAULT NULL,
  `school_teacher_living_solid` int(11) DEFAULT NULL,
  `school_teacher_living_semi_solid` int(11) DEFAULT NULL,
  `school_teacher_living_makeshift` int(11) DEFAULT NULL,
  `school_teacher_living_other` int(11) DEFAULT NULL,
  `sdw` int(11) DEFAULT NULL,
  `ssdw1` int(11) DEFAULT NULL,
  `ssdw2` int(11) DEFAULT NULL,
  `ssdw3` int(11) DEFAULT NULL,
  `ssdw4` int(11) DEFAULT NULL,
  `ssdw5` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `emis` (`emis_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8679 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_r_source_funds`
--

DROP TABLE IF EXISTS `module_r_source_funds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_r_source_funds` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `r1a1` int(11) DEFAULT NULL,
  `r1a2` int(11) DEFAULT NULL,
  `r1b1` int(11) DEFAULT NULL,
  `r1b2` int(11) DEFAULT NULL,
  `r1c1` int(11) DEFAULT NULL,
  `r1c2` int(11) DEFAULT NULL,
  `r1d1` int(11) DEFAULT NULL,
  `r1d2` int(11) DEFAULT NULL,
  `r1e1` int(11) DEFAULT NULL,
  `r1e2` int(11) DEFAULT NULL,
  `r1f1` int(11) DEFAULT NULL,
  `r1f2` int(11) DEFAULT NULL,
  `r1g1` int(11) DEFAULT NULL,
  `r1g2` int(11) DEFAULT NULL,
  `r2a1` int(11) DEFAULT NULL,
  `r2a2` int(11) DEFAULT NULL,
  `r2a3` int(11) DEFAULT NULL,
  `r2b1` int(11) DEFAULT NULL,
  `r2b2` int(11) DEFAULT NULL,
  `r2b3` int(11) DEFAULT NULL,
  `r2c1` int(11) DEFAULT NULL,
  `r2c2` int(11) DEFAULT NULL,
  `r2c3` int(11) DEFAULT NULL,
  `r2d1` int(11) DEFAULT NULL,
  `r2d2` int(11) DEFAULT NULL,
  `r2d3` int(11) DEFAULT NULL,
  `r2e1` int(11) DEFAULT NULL,
  `r2e2` int(11) DEFAULT NULL,
  `r2e3` int(11) DEFAULT NULL,
  `r2e4` int(11) DEFAULT NULL,
  `r2f1` int(11) DEFAULT NULL,
  `r2f2` int(11) DEFAULT NULL,
  `r2f3` int(11) DEFAULT NULL,
  `r2f4` int(11) DEFAULT NULL,
  `r3a1` int(11) DEFAULT NULL,
  `r3a2` int(11) DEFAULT NULL,
  `r3a3` varchar(100) DEFAULT NULL,
  `r3b1` int(11) DEFAULT NULL,
  `r3b2` int(11) DEFAULT NULL,
  `r3b3` varchar(100) DEFAULT NULL,
  `r3c1` int(11) DEFAULT NULL,
  `r3c2` int(11) DEFAULT NULL,
  `r3c3` varchar(100) DEFAULT NULL,
  `r3d1` int(11) DEFAULT NULL,
  `r3d2` int(11) DEFAULT NULL,
  `r3d3` varchar(100) DEFAULT NULL,
  `r3e1` int(11) DEFAULT NULL,
  `r3e2` int(11) DEFAULT NULL,
  `r3e3` varchar(100) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `emis` (`emis_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8564 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_s_management_teaching_staff`
--

DROP TABLE IF EXISTS `module_s_management_teaching_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_s_management_teaching_staff` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `ptm_functioning` int(11) DEFAULT NULL,
  `ptm_times_meet_year` int(11) DEFAULT NULL,
  `ptm_chairperson` varchar(100) DEFAULT NULL,
  `ptm_chairperson_telephone` int(11) DEFAULT NULL,
  `board_committee_functioning` int(11) DEFAULT NULL,
  `board_committee_meet_year` int(11) DEFAULT NULL,
  `board_committee_chairperson` varchar(100) DEFAULT NULL,
  `board_committee_chairperson_telephone` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `emis` (`emis_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8789 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `population`
--

DROP TABLE IF EXISTS `population`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `population` (
  `Region` int(11) DEFAULT NULL,
  `Council` int(11) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `Sex` int(11) DEFAULT NULL,
  `Population` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_finance`
--

DROP TABLE IF EXISTS `school_finance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_finance` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_date` date DEFAULT NULL,
  `emis` int(11) DEFAULT NULL,
  `f1` int(11) DEFAULT NULL,
  `f2` int(11) DEFAULT NULL,
  `f3` int(11) DEFAULT NULL,
  `f4` int(11) DEFAULT NULL,
  `f5` int(11) DEFAULT NULL,
  `f6` int(11) DEFAULT NULL,
  `f7` int(11) DEFAULT NULL,
  `f8` int(11) DEFAULT NULL,
  `f9` int(11) DEFAULT NULL,
  `f10` int(11) DEFAULT NULL,
  `f11` int(11) DEFAULT NULL,
  `f12` int(11) DEFAULT NULL,
  `f13` int(11) DEFAULT NULL,
  `f14` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3832 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_finance_deposits`
--

DROP TABLE IF EXISTS `school_finance_deposits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_finance_deposits` (
  `idfinance_deposits` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idfinance_deposits`),
  UNIQUE KEY `unique` (`emis_code`,`date`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=13796 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_finance_expenditures`
--

DROP TABLE IF EXISTS `school_finance_expenditures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_finance_expenditures` (
  `idfinance_expenditures` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idfinance_expenditures`),
  UNIQUE KEY `unique` (`emis_code`,`date`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4314 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_poralg_catalog`
--

DROP TABLE IF EXISTS `school_poralg_catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_poralg_catalog` (
  `emis_code` int(10) unsigned NOT NULL COMMENT 'EMIS Code',
  `region_code` int(10) unsigned NOT NULL COMMENT 'Region number',
  `council_code` int(10) unsigned NOT NULL COMMENT 'Council number',
  `ward_code` int(10) unsigned NOT NULL COMMENT 'Ward number',
  `region_name` varchar(60) NOT NULL COMMENT 'Region name',
  `council_name` varchar(60) NOT NULL COMMENT 'Council name',
  `ward_name` varchar(60) NOT NULL COMMENT 'Ward name',
  `school_name` varchar(150) NOT NULL COMMENT 'School name',
  PRIMARY KEY (`emis_code`,`region_code`,`council_code`,`ward_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Geografy of Schools';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_poralg_catalog_attendance`
--

DROP TABLE IF EXISTS `school_poralg_catalog_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_poralg_catalog_attendance` (
  `emis_code` int(10) unsigned NOT NULL COMMENT 'EMIS Code',
  `region_code` int(10) unsigned NOT NULL COMMENT 'Region number',
  `council_code` int(10) unsigned NOT NULL COMMENT 'Council number',
  `ward_code` int(10) unsigned NOT NULL COMMENT 'Ward number',
  `region_name` varchar(60) NOT NULL COMMENT 'Region name',
  `council_name` varchar(60) NOT NULL COMMENT 'Council name',
  `ward_name` varchar(60) NOT NULL COMMENT 'Ward name',
  `school_name` varchar(150) NOT NULL COMMENT 'School name',
  `2019` int(1) NOT NULL COMMENT 'Attendance 2019',
  `2020` int(1) NOT NULL COMMENT 'Attendance 2020',
  `2021` int(1) NOT NULL COMMENT 'Attendance 2021',
  `2022` int(1) NOT NULL COMMENT 'Attendance 2022',
  `2023` int(1) NOT NULL COMMENT 'Attendance 2023',
  `2024` int(1) NOT NULL COMMENT 'Attendance 2024',
  PRIMARY KEY (`emis_code`,`region_code`,`council_code`,`ward_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Geografy of Schools';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_teachers_staff`
--

DROP TABLE IF EXISTS `school_teachers_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_teachers_staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emis_code` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `teacher_staff` varchar(100) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `givenname` varchar(100) DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `year_birthday` varchar(100) DEFAULT NULL,
  `check_number` varchar(100) DEFAULT NULL,
  `position_head_school` varchar(100) DEFAULT NULL,
  `position_deputy_head` varchar(100) DEFAULT NULL,
  `position_registar` varchar(100) DEFAULT NULL,
  `position_custodian` varchar(100) DEFAULT NULL,
  `position_teacher_trainer` varchar(100) DEFAULT NULL,
  `position_other` varchar(100) DEFAULT NULL,
  `teaching_preprimary` varchar(100) DEFAULT NULL,
  `teaching_primary` varchar(100) DEFAULT NULL,
  `teaching_secondary_o` varchar(100) DEFAULT NULL,
  `teaching_secondary_a` varchar(100) DEFAULT NULL,
  `teaching_no_teaching` varchar(100) DEFAULT NULL,
  `professional_qualifications` varchar(100) DEFAULT NULL,
  `academic_qualifications` varchar(100) DEFAULT NULL,
  `subject_teaching_language_arts` varchar(100) DEFAULT NULL,
  `subject_teaching_mathematics` varchar(100) DEFAULT NULL,
  `subjust_teaching_science` varchar(100) DEFAULT NULL,
  `subject_teaching_social_stidies` varchar(100) DEFAULT NULL,
  `subject_teaching_other` varchar(100) DEFAULT NULL,
  `year_position` varchar(100) DEFAULT NULL,
  `salary` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `exit_t` int(11) DEFAULT NULL,
  `yearexit` varchar(45) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emis` (`emis_code`,`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=121928 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `school_textbooks_temp`
--

DROP TABLE IF EXISTS `school_textbooks_temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_textbooks_temp` (
  `emis` varchar(45) NOT NULL,
  `subject` varchar(45) NOT NULL,
  `grade` varchar(45) NOT NULL,
  `textbooks` int(11) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`emis`,`subject`,`grade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_attendance`
--

DROP TABLE IF EXISTS `student_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_submited` datetime DEFAULT NULL,
  `emis_code` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `teacher_present` int(11) DEFAULT NULL,
  `person_charge` int(11) DEFAULT NULL,
  `school_shift` int(11) DEFAULT NULL,
  `school_level` int(11) DEFAULT NULL,
  `school_grade` int(11) DEFAULT NULL,
  `school_section` int(11) DEFAULT NULL,
  `school_subject` int(11) DEFAULT NULL,
  `student_present` int(11) DEFAULT NULL,
  `student_absent` int(11) DEFAULT NULL,
  `date_submitted2` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emis` (`date_submited`,`emis_code`,`teacher_id`,`teacher_present`,`person_charge`,`school_shift`,`school_level`,`school_grade`,`school_section`,`school_subject`),
  KEY `index` (`date_submited`,`emis_code`,`teacher_id`,`teacher_present`,`person_charge`,`school_shift`,`school_level`,`school_grade`,`school_section`,`school_subject`)
) ENGINE=InnoDB AUTO_INCREMENT=3743023 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `student_attendance_view`
--

DROP TABLE IF EXISTS `student_attendance_view`;
/*!50001 DROP VIEW IF EXISTS `student_attendance_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `student_attendance_view` AS SELECT 
 1 AS `id`,
 1 AS `date_submited`,
 1 AS `emis_code`,
 1 AS `teacher_id`,
 1 AS `teacher_present`,
 1 AS `person_charge`,
 1 AS `school_shift`,
 1 AS `school_level`,
 1 AS `school_grade`,
 1 AS `school_section`,
 1 AS `type`,
 1 AS `total`,
 1 AS `date_submitted2`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `student_behaviour`
--

DROP TABLE IF EXISTS `student_behaviour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_behaviour` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `emis_code` int(11) DEFAULT NULL,
  `teacher_code` int(11) DEFAULT NULL,
  `school_shift` int(11) DEFAULT NULL,
  `school_level` int(11) DEFAULT NULL,
  `school_grade` int(11) DEFAULT NULL,
  `school_section` int(11) DEFAULT NULL,
  `school_subject` int(11) DEFAULT NULL,
  `student_reason1` int(11) DEFAULT NULL,
  `student_reason2` int(11) DEFAULT NULL,
  `student_reason3` int(11) DEFAULT NULL,
  `student_reason4` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emis` (`date`,`emis_code`,`teacher_code`,`school_shift`,`school_level`,`school_grade`,`school_section`,`school_subject`)
) ENGINE=InnoDB AUTO_INCREMENT=32996 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_disability`
--

DROP TABLE IF EXISTS `student_disability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_disability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `emis_code` int(11) DEFAULT NULL,
  `school_shift` int(11) DEFAULT NULL,
  `school_level` int(11) DEFAULT NULL,
  `school_grade` int(11) DEFAULT NULL,
  `student_sex` int(11) DEFAULT NULL,
  `student_total` int(11) DEFAULT NULL,
  `student_vision` int(11) DEFAULT NULL,
  `student_hearing` int(11) DEFAULT NULL,
  `student_phisical` int(11) DEFAULT NULL,
  `student_handicap` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`year`,`emis_code`,`school_shift`,`school_level`,`school_grade`,`student_sex`)
) ENGINE=InnoDB AUTO_INCREMENT=81290 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_enrollment`
--

DROP TABLE IF EXISTS `student_enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_enrollment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `emis_code` int(11) DEFAULT NULL,
  `school_shift` int(11) DEFAULT NULL,
  `school_level` int(11) DEFAULT NULL,
  `school_grade` int(11) DEFAULT NULL,
  `student_age` int(11) DEFAULT NULL,
  `student_total` int(11) DEFAULT NULL,
  `student_male` int(11) DEFAULT NULL,
  `student_female` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`year`,`emis_code`,`school_shift`,`school_level`,`school_grade`,`student_age`)
) ENGINE=InnoDB AUTO_INCREMENT=11594441 DEFAULT CHARSET=latin1 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `student_enrollment_view`
--

DROP TABLE IF EXISTS `student_enrollment_view`;
/*!50001 DROP VIEW IF EXISTS `student_enrollment_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `student_enrollment_view` AS SELECT 
 1 AS `id`,
 1 AS `year`,
 1 AS `emis_code`,
 1 AS `school_shift`,
 1 AS `school_level`,
 1 AS `school_grade`,
 1 AS `student_age`,
 1 AS `sex`,
 1 AS `total`,
 1 AS `date_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `student_evaluation`
--

DROP TABLE IF EXISTS `student_evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_evaluation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `emis_code` int(11) DEFAULT NULL,
  `teacher_code` int(11) DEFAULT NULL,
  `school_shift` int(11) DEFAULT NULL,
  `school_level` int(11) DEFAULT NULL,
  `school_grade` int(11) DEFAULT NULL,
  `school_section` int(11) DEFAULT NULL,
  `school_subject` int(11) DEFAULT NULL,
  `test_number` int(11) DEFAULT NULL,
  `student_number` int(11) DEFAULT NULL,
  `student_average_grade` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`date`,`emis_code`,`teacher_code`,`school_shift`,`school_level`,`school_grade`,`school_section`,`school_subject`,`test_number`,`student_number`)
) ENGINE=InnoDB AUTO_INCREMENT=215979 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_newentrant`
--

DROP TABLE IF EXISTS `student_newentrant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_newentrant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `emis_code` int(11) DEFAULT NULL,
  `school_shift` int(11) DEFAULT NULL,
  `school_level` int(11) DEFAULT NULL,
  `school_grade` int(11) DEFAULT NULL,
  `student_age` int(11) DEFAULT NULL,
  `student_total` int(11) DEFAULT NULL,
  `student_male` int(11) DEFAULT NULL,
  `student_female` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`year`,`emis_code`,`school_shift`,`school_level`,`school_grade`,`student_age`)
) ENGINE=InnoDB AUTO_INCREMENT=759626 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_repeater`
--

DROP TABLE IF EXISTS `student_repeater`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_repeater` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `emis_code` int(11) DEFAULT NULL,
  `school_shift` int(11) DEFAULT NULL,
  `school_level` int(11) DEFAULT NULL,
  `school_grade` int(11) DEFAULT NULL,
  `student_age` int(11) DEFAULT NULL,
  `student_total` int(11) DEFAULT NULL,
  `student_male` int(11) DEFAULT NULL,
  `student_female` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`year`,`emis_code`,`school_shift`,`school_level`,`school_grade`,`student_age`)
) ENGINE=InnoDB AUTO_INCREMENT=581555 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `view_enrollment_1`
--

DROP TABLE IF EXISTS `view_enrollment_1`;
/*!50001 DROP VIEW IF EXISTS `view_enrollment_1`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_enrollment_1` AS SELECT 
 1 AS `id`,
 1 AS `year`,
 1 AS `emis`,
 1 AS `school_shift`,
 1 AS `school_level`,
 1 AS `school_grade`,
 1 AS `student_age`,
 1 AS `sex`,
 1 AS `enrolled`,
 1 AS `date_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_textbooks_1`
--

DROP TABLE IF EXISTS `view_textbooks_1`;
/*!50001 DROP VIEW IF EXISTS `view_textbooks_1`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_textbooks_1` AS SELECT 
 1 AS `emis`,
 1 AS `subject`,
 1 AS `grade`,
 1 AS `textbooks`,
 1 AS `date_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'tz_sis_schools'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `enrollment1` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `enrollment1` ON SCHEDULE EVERY 10 MINUTE STARTS '2012-10-04 23:59:00' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'enrollment' DO select tz_sis_schools.enrollment_view */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `enrollment2` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `enrollment2` ON SCHEDULE EVERY 10 MINUTE STARTS '2012-10-04 23:59:00' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'enrollment2' DO select tz_sis_schools.view_enrollment_1 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `enrollment3` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `enrollment3` ON SCHEDULE EVERY 10 MINUTE STARTS '2012-10-04 23:59:00' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'enrollment3' DO select tz_sis_schools.student_enrollment_view */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'tz_sis_schools'
--

--
-- Final view structure for view `enrollment_view`
--

/*!50001 DROP VIEW IF EXISTS `enrollment_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `enrollment_view` AS select `student_enrollment`.`id` AS `id`,`student_enrollment`.`year` AS `year`,`student_enrollment`.`emis_code` AS `emis_code`,`student_enrollment`.`school_shift` AS `school_shift`,`student_enrollment`.`school_level` AS `school_level`,`student_enrollment`.`school_grade` AS `school_grade`,`student_enrollment`.`student_age` AS `student_age`,1 AS `sex`,sum(`student_enrollment`.`student_male`) AS `total`,`student_enrollment`.`date_time` AS `date_time` from `student_enrollment` group by `student_enrollment`.`id`,`student_enrollment`.`year`,`student_enrollment`.`emis_code`,`student_enrollment`.`school_shift`,`student_enrollment`.`school_level`,`student_enrollment`.`school_grade`,`student_enrollment`.`student_age` union select `student_enrollment`.`id` AS `id`,`student_enrollment`.`year` AS `year`,`student_enrollment`.`emis_code` AS `emis_code`,`student_enrollment`.`school_shift` AS `school_shift`,`student_enrollment`.`school_level` AS `school_level`,`student_enrollment`.`school_grade` AS `school_grade`,`student_enrollment`.`student_age` AS `student_age`,1 AS `sex`,sum(`student_enrollment`.`student_female`) AS `total`,`student_enrollment`.`date_time` AS `date_time` from `student_enrollment` group by `student_enrollment`.`id`,`student_enrollment`.`year`,`student_enrollment`.`emis_code`,`student_enrollment`.`school_shift`,`student_enrollment`.`school_level`,`student_enrollment`.`school_grade`,`student_enrollment`.`student_age` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `student_attendance_view`
--

/*!50001 DROP VIEW IF EXISTS `student_attendance_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `student_attendance_view` AS select `student_attendance`.`id` AS `id`,`student_attendance`.`date_submited` AS `date_submited`,`student_attendance`.`emis_code` AS `emis_code`,`student_attendance`.`teacher_id` AS `teacher_id`,`student_attendance`.`teacher_present` AS `teacher_present`,`student_attendance`.`person_charge` AS `person_charge`,`student_attendance`.`school_shift` AS `school_shift`,`student_attendance`.`school_level` AS `school_level`,`student_attendance`.`school_grade` AS `school_grade`,`student_attendance`.`school_section` AS `school_section`,0 AS `type`,sum(`student_attendance`.`student_absent`) AS `total`,`student_attendance`.`date_submitted2` AS `date_submitted2` from `student_attendance` group by `student_attendance`.`date_submited`,`student_attendance`.`emis_code`,`student_attendance`.`teacher_id`,`student_attendance`.`teacher_present`,`student_attendance`.`person_charge`,`student_attendance`.`school_shift`,`student_attendance`.`school_level`,`student_attendance`.`school_grade`,`student_attendance`.`school_section` union select `student_attendance`.`id` AS `id`,`student_attendance`.`date_submited` AS `date_submited`,`student_attendance`.`emis_code` AS `emis_code`,`student_attendance`.`teacher_id` AS `teacher_id`,`student_attendance`.`teacher_present` AS `teacher_present`,`student_attendance`.`person_charge` AS `person_charge`,`student_attendance`.`school_shift` AS `school_shift`,`student_attendance`.`school_level` AS `school_level`,`student_attendance`.`school_grade` AS `school_grade`,`student_attendance`.`school_section` AS `school_section`,1 AS `type`,sum(`student_attendance`.`student_present`) AS `total`,`student_attendance`.`date_submitted2` AS `date_submitted2` from `student_attendance` group by `student_attendance`.`date_submited`,`student_attendance`.`emis_code`,`student_attendance`.`teacher_id`,`student_attendance`.`teacher_present`,`student_attendance`.`person_charge`,`student_attendance`.`school_shift`,`student_attendance`.`school_level`,`student_attendance`.`school_grade`,`student_attendance`.`school_section` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `student_enrollment_view`
--

/*!50001 DROP VIEW IF EXISTS `student_enrollment_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `student_enrollment_view` AS select `student_enrollment`.`id` AS `id`,`student_enrollment`.`year` AS `year`,`student_enrollment`.`emis_code` AS `emis_code`,`student_enrollment`.`school_shift` AS `school_shift`,`student_enrollment`.`school_level` AS `school_level`,`student_enrollment`.`school_grade` AS `school_grade`,`student_enrollment`.`student_age` AS `student_age`,1 AS `sex`,sum(`student_enrollment`.`student_male`) AS `total`,`student_enrollment`.`date_time` AS `date_time` from `student_enrollment` group by `student_enrollment`.`id`,`student_enrollment`.`year`,`student_enrollment`.`emis_code`,`student_enrollment`.`school_shift`,`student_enrollment`.`school_level`,`student_enrollment`.`school_grade`,`student_enrollment`.`student_age` union select `student_enrollment`.`id` AS `id`,`student_enrollment`.`year` AS `year`,`student_enrollment`.`emis_code` AS `emis_code`,`student_enrollment`.`school_shift` AS `school_shift`,`student_enrollment`.`school_level` AS `school_level`,`student_enrollment`.`school_grade` AS `school_grade`,`student_enrollment`.`student_age` AS `student_age`,1 AS `sex`,sum(`student_enrollment`.`student_female`) AS `total`,`student_enrollment`.`date_time` AS `date_time` from `student_enrollment` group by `student_enrollment`.`id`,`student_enrollment`.`year`,`student_enrollment`.`emis_code`,`student_enrollment`.`school_shift`,`student_enrollment`.`school_level`,`student_enrollment`.`school_grade`,`student_enrollment`.`student_age` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_enrollment_1`
--

/*!50001 DROP VIEW IF EXISTS `view_enrollment_1`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_enrollment_1` AS select `student_enrollment`.`id` AS `id`,`student_enrollment`.`year` AS `year`,`student_enrollment`.`emis_code` AS `emis`,`student_enrollment`.`school_shift` AS `school_shift`,`student_enrollment`.`school_level` AS `school_level`,`student_enrollment`.`school_grade` AS `school_grade`,`student_enrollment`.`student_age` AS `student_age`,'M' AS `sex`,`student_enrollment`.`student_male` AS `enrolled`,`student_enrollment`.`date_time` AS `date_time` from `student_enrollment` union select `student_enrollment`.`id` AS `id`,`student_enrollment`.`year` AS `year`,`student_enrollment`.`emis_code` AS `emis`,`student_enrollment`.`school_shift` AS `school_shift`,`student_enrollment`.`school_level` AS `school_level`,`student_enrollment`.`school_grade` AS `school_grade`,`student_enrollment`.`student_age` AS `student_age`,'F' AS `sex`,`student_enrollment`.`student_female` AS `enrolled`,`student_enrollment`.`date_time` AS `date_time` from `student_enrollment` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_textbooks_1`
--

/*!50001 DROP VIEW IF EXISTS `view_textbooks_1`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `view_textbooks_1` AS select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Mathematics' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`math_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Mathematics' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`math_standard_2` AS `f2a2`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Mathematics' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`math_standard_3` AS `f2a3`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Mathematics' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`math_standard_4` AS `f2a4`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Mathematics' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`math_standard_5` AS `f2a5`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Mathematics' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`math_standard_6` AS `f2a6`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Mathematics' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`math_standard_7` AS `f2a7`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'English' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`english_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'English' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`english_standard_2` AS `f2a9`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'English' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`english_standard_3` AS `f2a10`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'English' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`english_standard_4` AS `f2a11`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'English' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`english_standard_5` AS `f2a12`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'English' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`english_standard_6` AS `f2a13`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'English' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`english_standard_7` AS `f2a14`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Kiswahili' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`kiswahili_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Kiswahili' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`kiswahili_standard_2` AS `f2a16`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Kiswahili' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`kiswahili_standard_3` AS `f2a17`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Kiswahili' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`kiswahili_standard_4` AS `f2a18`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Kiswahili' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`kiswahili_standard_5` AS `f2a19`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Kiswahili' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`kiswahili_standard_6` AS `f2a20`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Kiswahili' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`kiswahili_standard_7` AS `f2a21`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'French' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`french_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'French' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`french_standard_2` AS `f2a23`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'French' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`french_standard_3` AS `f2a24`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'French' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`french_standard_4` AS `f2a25`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'French' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`french_standard_5` AS `f2a26`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'French' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`french_standard_6` AS `f2a27`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'French' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`french_standard_7` AS `f2a28`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Science' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`science_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Science' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`science_standard_2` AS `f2a30`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Science' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`science_standard_3` AS `f2a31`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Science' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`science_standard_4` AS `f2a32`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Science' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`science_standard_5` AS `f2a33`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Science' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`science_standard_6` AS `f2a34`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Science' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`science_standard_7` AS `f2a35`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Geography' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`geography_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Geography' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`geography_standard_2` AS `f2a37`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Geography' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`geography_standard_3` AS `f2a38`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Geography' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`geography_standard_4` AS `f2a39`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Geography' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`geography_standard_5` AS `f2a40`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Geography' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`geography_standard_6` AS `f2a41`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Geography' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`geography_standard_7` AS `f2a42`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Civics' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`civics_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Civics' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`civics_standard_2` AS `f2a44`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Civics' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`civics_standard_3` AS `f2a45`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Civics' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`civics_standard_4` AS `f2a46`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Civics' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`civics_standard_5` AS `f2a47`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Civics' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`civics_standard_6` AS `f2a48`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Civics' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`civics_standard_7` AS `f2a49`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'History' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`history_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'History' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`history_standard_2` AS `f2a51`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'History' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`history_standard_3` AS `f2a52`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'History' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`history_standard_4` AS `f2a53`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'History' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`history_standard_5` AS `f2a54`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'History' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`history_standard_6` AS `f2a55`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'History' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`history_standard_7` AS `f2a56`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Vocational skills' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`vskills_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Vocational skills' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`vskills_standard_2` AS `f2a58`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Vocational skills' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`vskills_standard_3` AS `f2a59`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Vocational skills' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`vskills_standard_4` AS `f2a60`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Vocational skills' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`vskills_standard_5` AS `f2a61`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Vocational skills' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`vskills_standard_6` AS `f2a62`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Vocational skills' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`vskills_standard_7` AS `f2a63`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Personality and Sports' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`per_sports_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Personality and Sports' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`per_sports_standard_2` AS `f2a65`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Personality and Sports' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`per_sports_standard_3` AS `f2a66`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Personality and Sports' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`per_sports_standard_4` AS `f2a67`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Personality and Sports' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`per_sports_standard_5` AS `f2a68`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Personality and Sports' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`per_sports_standard_6` AS `f2a69`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Personality and Sports' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`per_sports_standard_7` AS `f2a70`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'ICT' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`ict_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'ICT' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`ict_standard_2` AS `f2a72`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'ICT' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`ict_standard_3` AS `f2a73`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'ICT' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`ict_standard_4` AS `f2a74`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'ICT' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`ict_standard_5` AS `f2a75`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'ICT' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`ict_standard_6` AS `f2a76`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'ICT' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`ict_standard_7` AS `f2a77`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Religion Studies' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`religion_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Religion Studies' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`religion_standard_2` AS `f2a79`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Religion Studies' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`religion_standard_3` AS `f2a80`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Religion Studies' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`religion_standard_4` AS `f2a81`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Religion Studies' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`religion_standard_5` AS `f2a82`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Religion Studies' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`religion_standard_6` AS `f2a83`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Religion Studies' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`religion_standard_7` AS `f2a84`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Reading' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`reading_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Reading' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`reading_standard_2` AS `f2a86`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Reading' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`reading_standard_3` AS `f2a87`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Reading' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`reading_standard_4` AS `f2a88`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Reading' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`reading_standard_5` AS `f2a89`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Reading' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`reading_standard_6` AS `f2a90`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Reading' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`reading_standard_7` AS `f2a91`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Writing' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`writing_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Writing' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`writing_standard_2` AS `f2a93`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Writing' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`writing_standard_3` AS `f2a94`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Writing' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`writing_standard_4` AS `f2a95`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Writing' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`writing_standard_5` AS `f2a96`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Writing' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`writing_standard_6` AS `f2a97`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Writing' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`writing_standard_7` AS `f2a98`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Arithmetic' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`arithmetic_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Arithmetic' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`arithmetic_standard_2` AS `f2a100`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Arithmetic' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`arithmetic_standard_3` AS `f2a101`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Arithmetic' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`arithmetic_standard_4` AS `f2a102`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Arithmetic' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`arithmetic_standard_5` AS `f2a103`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Arithmetic' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`arithmetic_standard_6` AS `f2a104`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Arithmetic' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`arithmetic_standard_7` AS `f2a105`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Health and Environment Education' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`health_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Health and Environment Education' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`health_standard_2` AS `f2a107`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Health and Environment Education' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`health_standard_3` AS `f2a108`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Health and Environment Education' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`health_standard_4` AS `f2a109`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Health and Environment Education' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`health_standard_5` AS `f2a110`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Health and Environment Education' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`health_standard_6` AS `f2a111`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Health and Environment Education' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`health_standard_7` AS `f2a112`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Games, Sport and Fine and Performing Art' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`games_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Games, Sport and Fine and Performing Art' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`games_standard_2` AS `f2a114`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Games, Sport and Fine and Performing Art' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`games_standard_3` AS `f2a115`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Games, Sport and Fine and Performing Art' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`games_standard_4` AS `f2a116`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Games, Sport and Fine and Performing Art' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`games_standard_5` AS `f2a117`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Games, Sport and Fine and Performing Art' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`games_standard_6` AS `f2a118`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Games, Sport and Fine and Performing Art' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`games_standard_7` AS `f2a119`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Other' AS `subject`,'STD I' AS `grade`,`module_f_primary_curriculum_instruction`.`other_standard_1` AS `textbooks`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Other' AS `subject`,'STD II' AS `grade`,`module_f_primary_curriculum_instruction`.`other_standard_2` AS `f2a121`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Other' AS `subject`,'STD III' AS `grade`,`module_f_primary_curriculum_instruction`.`other_standard_3` AS `f2a122`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Other' AS `subject`,'STD IV' AS `grade`,`module_f_primary_curriculum_instruction`.`other_standard_4` AS `f2a123`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Other' AS `subject`,'STD V' AS `grade`,`module_f_primary_curriculum_instruction`.`other_standard_5` AS `f2a124`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Other' AS `subject`,'STD VI' AS `grade`,`module_f_primary_curriculum_instruction`.`other_standard_6` AS `f2a125`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` union select `module_f_primary_curriculum_instruction`.`emis_code` AS `emis`,'Other' AS `subject`,'STD VII' AS `grade`,`module_f_primary_curriculum_instruction`.`other_standard_7` AS `f2a126`,`module_f_primary_curriculum_instruction`.`date_time` AS `date_time` from `module_f_primary_curriculum_instruction` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-31 21:38:14
