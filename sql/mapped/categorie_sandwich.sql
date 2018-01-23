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

Date: 2018-01-09 17:06:58
*/


-- ----------------------------
-- Table structure for categorie_sandwich
-- ----------------------------
DROP TABLE IF EXISTS "public"."categorie_sandwich";
CREATE TABLE "public"."categorie_sandwich" (
"categorie_id" varchar(255) COLLATE "default" NOT NULL,
"sandwich_id" varchar(255) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of categorie_sandwich
-- ----------------------------
INSERT INTO "public"."categorie_sandwich" VALUES ('1', '10');
INSERT INTO "public"."categorie_sandwich" VALUES ('1', '11');
INSERT INTO "public"."categorie_sandwich" VALUES ('2', '10');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table categorie_sandwich
-- ----------------------------
ALTER TABLE "public"."categorie_sandwich" ADD PRIMARY KEY ("categorie_id", "sandwich_id");

-- ----------------------------
-- Foreign Key structure for table "public"."categorie_sandwich"
-- ----------------------------
ALTER TABLE "public"."categorie_sandwich" ADD FOREIGN KEY ("sandwich_id") REFERENCES "public"."sandwich" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."categorie_sandwich" ADD FOREIGN KEY ("categorie_id") REFERENCES "public"."categorie" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."categorie_sandwich" ADD FOREIGN KEY ("categorie_id") REFERENCES "public"."categorie" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."categorie_sandwich" ADD FOREIGN KEY ("sandwich_id") REFERENCES "public"."sandwich" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
