/*
Navicat PGSQL Data Transfer

Source Server         : Wildfly
Source Server Version : 100100
Source Host           : 192.168.99.100:5432
Source Database       : td1
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 100100
File Encoding         : 65001

Date: 2017-12-05 15:56:59
*/


-- ----------------------------
-- Table structure for categorie
-- ----------------------------
DROP TABLE IF EXISTS "public"."categorie";
CREATE TABLE "public"."categorie" (
"id" int8 NOT NULL,
"description" varchar(255) COLLATE "default",
"nom" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of categorie
-- ----------------------------
INSERT INTO "public"."categorie" VALUES ('1', 'sandwichs ingrédients bio et locaux', 'bio');
INSERT INTO "public"."categorie" VALUES ('2', 'sandwichs végétariens - peuvent contenir des produits laitiers', 'végétarien');
INSERT INTO "public"."categorie" VALUES ('3', 'sandwichs traditionnels : jambon, pâté, poulet etc ..', 'traditionnel');
INSERT INTO "public"."categorie" VALUES ('4', 'sandwichs chauds : américain, burger, ', 'chaud');
INSERT INTO "public"."categorie" VALUES ('5', '100% Veggie', 'veggie');
INSERT INTO "public"."categorie" VALUES ('16', 'Tacos, nems, burritos, nos sandwichs du monde entier', 'world');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table categorie
-- ----------------------------
ALTER TABLE "public"."categorie" ADD PRIMARY KEY ("id");
