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

Date: 2018-01-09 17:07:03
*/


-- ----------------------------
-- Table structure for sandwich_taille
-- ----------------------------
DROP TABLE IF EXISTS "public"."sandwich_taille";
CREATE TABLE "public"."sandwich_taille" (
"sandwich_id" varchar(255) COLLATE "default" NOT NULL,
"taille_id" varchar(255) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of sandwich_taille
-- ----------------------------
INSERT INTO "public"."sandwich_taille" VALUES ('10', '1');
INSERT INTO "public"."sandwich_taille" VALUES ('10', '2');
INSERT INTO "public"."sandwich_taille" VALUES ('10', '3');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sandwich_taille
-- ----------------------------
ALTER TABLE "public"."sandwich_taille" ADD PRIMARY KEY ("sandwich_id", "taille_id");

-- ----------------------------
-- Foreign Key structure for table "public"."sandwich_taille"
-- ----------------------------
ALTER TABLE "public"."sandwich_taille" ADD FOREIGN KEY ("sandwich_id") REFERENCES "public"."sandwich" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sandwich_taille" ADD FOREIGN KEY ("taille_id") REFERENCES "public"."taille" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sandwich_taille" ADD FOREIGN KEY ("taille_id") REFERENCES "public"."taille" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sandwich_taille" ADD FOREIGN KEY ("sandwich_id") REFERENCES "public"."sandwich" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
