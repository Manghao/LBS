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

Date: 2017-12-05 15:56:48
*/


-- ----------------------------
-- Table structure for sandwich
-- ----------------------------
DROP TABLE IF EXISTS "public"."sandwich";
CREATE TABLE "public"."sandwich" (
"id" int8 NOT NULL,
"description" varchar(255) COLLATE "default" NOT NULL,
"img" varchar(255) COLLATE "default",
"nom" varchar(255) COLLATE "default" NOT NULL,
"type_pain" varchar(255) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of sandwich
-- ----------------------------
INSERT INTO "public"."sandwich" VALUES ('10', 'Et tenetur praesentium nulla facere officia et. Consequatur quasi aperiam qui enim vel. Maiores nemo dolor aliquid et architecto ea voluptas at.', null, 'ab-fuga', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('11', 'Sed consequatur nobis est. Et et cupiditate ipsam sequi earum sint non. Qui incidunt magnam tempora quas eum qui.', null, 'vitae-in', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('12', 'Aut aut rem exercitationem voluptates qui. Qui soluta aut minus dolor est. Sequi cum quos vel placeat.', null, 'impedit-qui', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('13', 'Rerum ipsa nihil et aut possimus. Autem et aut eos odit. Maxime consequuntur nemo facilis atque culpa et.', null, 'similique-sed', 'mie');
INSERT INTO "public"."sandwich" VALUES ('14', 'Asperiores velit ut voluptatem magni et voluptatem. Illum aut possimus iste. Beatae ut ipsa aut dolores mollitia explicabo molestias.', null, 'qui-corporis', 'mie');
INSERT INTO "public"."sandwich" VALUES ('15', 'Veritatis voluptatem culpa et et autem itaque. Quos neque in aut esse facere consequuntur. Ab in repellat ut voluptatem ullam fugiat odit.', null, 'nihil-qui', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('16', 'Vel tempore est quae velit. Adipisci delectus itaque molestias voluptatem numquam libero libero accusamus. Aut delectus blanditiis non ab consequatur.', 'img/c83871837e68cc38e6fb7fd0b904bb59.png', 'sapiente-soluta', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('17', 'Ut qui perferendis est expedita aut iste. Illo id libero impedit sunt. Ut aliquid et qui unde at est.', null, 'tempore-omnis', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('18', 'Est reiciendis a illo voluptate. Expedita soluta autem iure. Voluptatum id nemo quos optio recusandae quia iure.', 'img/7589799de870ef7f71a6173879c53130.png', 'mollitia-omnis', 'mie');
INSERT INTO "public"."sandwich" VALUES ('19', 'Voluptatibus accusamus dolorem velit assumenda. Quo dolor ab autem deserunt necessitatibus et. Quisquam quas voluptas et quas perspiciatis aut rerum.', null, 'quo-est', 'mie');
INSERT INTO "public"."sandwich" VALUES ('20', 'Quae neque iusto fugiat omnis non alias dolore voluptatem. Beatae praesentium adipisci et corrupti accusamus nesciunt ullam vel. Et repudiandae asperiores quo impedit perspiciatis possimus accusantium.', null, 'aperiam-voluptas', 'mie');
INSERT INTO "public"."sandwich" VALUES ('21', 'Qui numquam possimus fugiat et quaerat. Nobis sapiente porro aliquam et. Quam voluptas qui iste et reprehenderit.', null, 'quis-dolorem', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('22', 'Vero blanditiis quis non iusto dolor incidunt. Sint temporibus vitae dolore veniam. Necessitatibus molestias vel aut eius non.', null, 'adipisci-sed', 'mie');
INSERT INTO "public"."sandwich" VALUES ('23', 'Ullam quia sequi consequatur commodi. Quam rem fugit tenetur quo incidunt et. Illo consequatur est cupiditate consectetur fugit quo.', 'img/7fd2633c08937135a935d2bdd1348949.png', 'minima-et', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('24', 'Incidunt laudantium illo esse molestiae magnam. Eum quia facere hic dolorem doloribus assumenda. Recusandae quae nesciunt provident voluptatum molestiae voluptatem vitae.', null, 'dolor-perspiciatis', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('25', 'Consequatur maiores reiciendis unde. Consequuntur nihil aliquam explicabo aut magnam et. Natus unde quia quia molestias.', null, 'ducimus-sunt', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('26', 'Beatae non reiciendis expedita itaque cupiditate odit rerum consequuntur. Exercitationem earum voluptatem accusantium ipsam dolore mollitia non inventore. Iure dolor velit asperiores optio optio non iure.', null, 'laborum-ad', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('27', 'Est ut et id perferendis placeat cumque. Provident qui perspiciatis in in distinctio. Ea sit nihil nihil.', 'img/5842d14c195888425c82fd1d7f135516.png', 'accusantium-omnis', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('28', 'Vero vero tempore veniam aut. Repellat itaque velit illum consequatur sint eos. Delectus eos sunt dolorum autem blanditiis eius quis dolores.', null, 'beatae-quaerat', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('29', 'Dolore quod dicta ut quisquam placeat. Deleniti velit eius quis sit sunt neque architecto. Perspiciatis quae voluptas tenetur qui at sunt vel.', null, 'ea-qui', 'mie');
INSERT INTO "public"."sandwich" VALUES ('30', 'Nostrum quia qui voluptatum ad. Delectus omnis eveniet modi. Qui dolorem soluta consequuntur iusto.', null, 'expedita-quo', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('31', 'Neque sit molestiae necessitatibus voluptas ad voluptatem voluptatem. Corporis inventore nam et dolorem odit ea quisquam enim. Pariatur ad dolorem laborum voluptatem.', null, 'laboriosam-eos', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('32', 'Eligendi aut atque id quia. Unde sed doloremque suscipit ut quia ipsam. Ipsa aliquid quidem et in enim exercitationem voluptatum sequi.', 'img/114d1c2d4c030dc1118bc16cbe2b05cc.png', 'quod-deleniti', 'mie');
INSERT INTO "public"."sandwich" VALUES ('33', 'Ea veritatis facere debitis aperiam adipisci corporis et. Eum libero asperiores atque voluptatem. Distinctio assumenda illo est soluta voluptatem ea expedita.', null, 'sit-enim', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('34', 'Molestiae voluptatem in impedit praesentium autem ex. Doloribus explicabo magni dolor consequuntur cupiditate aliquid voluptate. Corporis nulla voluptatem dolor ut aut facilis minima.', null, 'mollitia-ipsum', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('35', 'Et nihil qui vero non facilis quasi. Libero ut est qui non quos. Voluptates voluptatem quia quasi sit id dolor quaerat.', null, 'modi-cum', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('36', 'Sequi dolorem quas sed nostrum. Non quas molestias et sunt aliquam tenetur mollitia corrupti. Ut sit libero rem magni.', 'img/1dc4d4bcfccd4735194236f04d4d1f66.png', 'dolorum-aut', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('37', 'Dolorem architecto dolorem et. Et voluptatum fugiat voluptas quia saepe sed dolore. Non esse quia ea.', 'img/742673b0d81bc75f520f472c8566d3b7.png', 'itaque-natus', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('38', 'Voluptatem autem expedita non molestias. Magni accusantium harum adipisci iste eum. Reprehenderit suscipit est nihil et esse id perferendis quo.', null, 'unde-qui', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('39', 'Non ut aperiam dolore excepturi velit voluptatum. Tenetur nihil reprehenderit voluptatem voluptates non. Debitis magni explicabo sit.', null, 'architecto-minus', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('40', 'Consequatur reprehenderit ut ipsa in et reprehenderit eum. Incidunt dolores sed odit et corporis dignissimos omnis. Autem nihil vero odio repellendus voluptas ipsam.', 'img/c2d740de94f7d483bdde260494ab67fc.png', 'laudantium-voluptates', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('41', 'Saepe omnis harum temporibus. Enim quia qui at et aut reiciendis saepe non. Ex assumenda totam expedita qui voluptatem eveniet totam.', null, 'corrupti-sapiente', 'mie');
INSERT INTO "public"."sandwich" VALUES ('42', 'Dolor culpa distinctio harum ut. Consequatur quis soluta id nemo. Ea alias eum est earum.', null, 'et-nesciunt', 'mie');
INSERT INTO "public"."sandwich" VALUES ('43', 'Aliquam autem et non. Error quia et sed at dolores repellendus. Libero quia repellat debitis minus est.', 'img/cdc30e3970df46144adfa63e7e6b1595.png', 'sit-dolor', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('44', 'Ipsum perspiciatis nihil eos sint. Voluptas voluptate non ut laborum molestiae. Veritatis beatae cupiditate non optio.', 'img/a317714831e45c52deee048a48b1c892.png', 'voluptas-rerum', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('45', 'Vero ut consequatur voluptas in et maxime. Distinctio vitae totam voluptatum debitis in consectetur recusandae. Facilis nihil sed voluptatem delectus quod minus facere provident.', null, 'consectetur-commodi', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('46', 'Eos autem deserunt assumenda magni suscipit. Ut ut qui porro et. Quos consequatur mollitia veniam vitae.', 'img/991594dbfc3736a9c2622cf00112399f.png', 'minima-omnis', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('47', 'Ex veritatis fugit temporibus ipsa alias velit a et. Qui voluptas aut ipsam quidem nostrum odit cumque praesentium. Velit expedita reiciendis commodi ea cupiditate aut.', null, 'est-modi', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('48', 'Dolorem cumque molestiae voluptate error ut vero. Aut exercitationem dicta dicta nihil sapiente porro. Consequatur iure enim velit impedit nisi sunt.', null, 'inventore-in', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('49', 'Consequatur maiores id tenetur. Rerum quos mollitia similique sint omnis autem enim. Exercitationem necessitatibus aspernatur sequi aperiam minus.', null, 'temporibus-ipsam', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('50', 'Quas numquam minus cumque perferendis non quisquam. A magnam aut aut sint. Alias rem aut a nam.', null, 'perspiciatis-dolorum', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('51', 'Ut velit similique dolorum blanditiis. Doloribus sint ipsa esse in eligendi possimus. Omnis laboriosam ipsum praesentium voluptatem.', 'img/07fb4b5dc6a0425929a4b877205cebb3.png', 'molestiae-ratione', 'mie');
INSERT INTO "public"."sandwich" VALUES ('52', 'Quisquam dolores minus tempora dolores rem officia dolor ratione. Tenetur nemo quis veniam consequuntur rerum incidunt voluptas velit. Sed eius est id inventore odio.', null, 'veniam-voluptas', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('53', 'Aut aut aliquam odit minus corrupti omnis eos. Omnis et repellendus quis dolorum. Modi non ratione accusantium.', null, 'est-vero', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('54', 'Sint est incidunt esse officia consequuntur dolorum. Vitae ut non ab voluptas officia expedita adipisci. Autem qui harum sint recusandae.', null, 'repellendus-vitae', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('55', 'Ea consequatur quis dicta sit sed. Cumque illo dolores ab molestiae non blanditiis. Sit eum unde ratione explicabo repudiandae.', null, 'eligendi-eius', 'mie');
INSERT INTO "public"."sandwich" VALUES ('56', 'Id nesciunt aut dolorem ea repudiandae. Voluptatem quia et officiis et. Maxime sit enim ex ad aut velit.', null, 'nihil-maiores', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('57', 'Enim fuga sequi quis aspernatur. Rerum beatae vel aliquam qui nihil id. Illum doloribus culpa ad praesentium ex.', null, 'eaque-nostrum', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('58', 'Omnis consequatur quod possimus. Autem harum possimus harum laborum eligendi qui. Eligendi et dolorem id dolores.', 'img/83916726e811c8de722d057d02812fdd.png', 'nulla-consequuntur', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('59', 'Vero libero rem voluptas qui. Inventore similique itaque neque explicabo autem. Voluptatibus aperiam qui qui hic sunt et.', 'img/b047421fe8adcb89b8a0d938f29eb6da.png', 'ipsum-impedit', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('60', 'Est consequatur quasi inventore rerum. Aliquam fuga dolor id adipisci delectus. Vero quo ut enim dignissimos et doloribus quis dolores.', null, 'odio-quis', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('61', 'Aut adipisci maxime ut sed temporibus. Delectus repellat sint expedita et maiores. Voluptatem dolores rerum vel molestias velit sed modi deleniti.', 'img/ed392cf0faa69cc967d6ac8d9b9cbfb4.png', 'laboriosam-ut', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('62', 'Rerum explicabo ducimus culpa dolores. Asperiores error illo maxime qui libero modi est. Sit eum recusandae aut et quos.', null, 'officiis-ducimus', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('63', 'Tempore sequi est eos facere soluta voluptatem recusandae. Necessitatibus aliquid quae necessitatibus rerum maiores enim in. Vel voluptatem ut delectus.', null, 'aperiam-et', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('64', 'Voluptatem et quae molestiae aut et voluptas. Ducimus non culpa quia voluptas non debitis. Eos velit laborum dolor.', 'img/16d085eb2d4d8730f8c07be498ceee64.png', 'ut-laboriosam', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('65', 'Quos officia ea provident. Reprehenderit sunt et aspernatur minima facere quod nulla. Cupiditate aliquid numquam architecto laborum tempora in est.', 'img/d8df6f4d770664f76a847ebcf395f1fa.png', 'sunt-minus', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('66', 'Nemo commodi voluptas rerum molestiae ut assumenda dolor. Aut qui aut nemo aliquam facilis consectetur. Quidem possimus aut est corporis qui voluptas suscipit.', null, 'laboriosam-qui', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('67', 'Nobis voluptates commodi autem et beatae tempore. Ut sit natus recusandae eum recusandae. Qui temporibus ipsam dolores dolor esse quod.', null, 'rem-doloribus', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('68', 'Accusamus aspernatur harum enim deleniti est. Officiis consequuntur sapiente expedita commodi laboriosam recusandae. Debitis repellat et voluptas eos ipsam.', null, 'eius-vel', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('69', 'Assumenda doloremque ut explicabo aut esse et vero. Et accusamus explicabo debitis est consectetur. Quisquam voluptas dicta aperiam laboriosam sit.', 'img/52ab422d125a222a57986dfbec5c64f8.png', 'quia-quas', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('70', 'Quo perspiciatis tempora et quis reprehenderit esse sapiente. Mollitia illo repellendus culpa et. Deserunt pariatur voluptatem pariatur autem quidem aut rem.', 'img/e112e7284b1258058776999219be05c0.png', 'sit-expedita', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('71', 'Dolorem cupiditate maxime quia aut autem et libero. Ducimus qui qui fugit sed voluptatem odit atque iusto. Error iste at eum consequuntur qui et.', null, 'molestiae-minima', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('72', 'Asperiores voluptatem excepturi velit et. Rerum molestiae cumque quas tenetur et eveniet. Quaerat optio autem earum omnis sequi voluptas.', 'img/12b3f9d041d62611d539fbebf2eda05a.png', 'beatae-suscipit', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('73', 'Ea maxime id temporibus rem enim. Aut dolor sed asperiores illum nesciunt quisquam ut. Animi sed sed autem quia beatae non.', null, 'dicta-sint', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('74', 'Et magnam omnis amet nesciunt consectetur ipsa. Ratione magni sed possimus ut sed. At sit perferendis eos commodi voluptas omnis minus fugiat.', null, 'ut-praesentium', 'mie');
INSERT INTO "public"."sandwich" VALUES ('75', 'Est assumenda voluptas hic possimus recusandae repellat quis. Eum dignissimos libero illo illum id illum earum. Molestiae quibusdam hic iusto non.', 'img/c8ff58e7a299d8d01b1a69ac2cb7f5a5.png', 'doloribus-non', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('76', 'Explicabo quia quia officiis. Pariatur quam qui nostrum ut inventore. Rerum corporis voluptatum illum expedita quam neque fugit.', null, 'aut-iusto', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('77', 'Veniam sed ullam quasi quibusdam. Et illo sit dicta possimus. Eum praesentium esse a ipsa dolor neque deleniti quia.', null, 'nihil-debitis', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('78', 'Exercitationem fuga eaque ex autem libero. Necessitatibus totam aut est molestiae. Voluptatem suscipit laboriosam est qui natus qui nemo.', 'img/548dbc719a32fb75e627bb5ec88c1efe.png', 'tenetur-debitis', 'mie');
INSERT INTO "public"."sandwich" VALUES ('79', 'Quos qui officia voluptatem alias. Aut optio corrupti corrupti velit fugit. Voluptatem et neque omnis doloribus accusamus facilis.', null, 'error-aut', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('80', 'Soluta et labore delectus quisquam et ullam et. Vel unde delectus qui modi. Distinctio et ex sint qui dolor eos blanditiis libero.', 'img/38853c71a4167ee6e3f6bf0433cca2b5.png', 'dolorem-dolorem', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('81', 'Sunt et laudantium ducimus qui et excepturi ratione sit. Ab maxime labore sed ipsam officiis. Sit saepe autem incidunt et omnis nulla odio enim.', 'img/960684bbdef89b240b5fa221493f60ba.png', 'et-odit', 'mie');
INSERT INTO "public"."sandwich" VALUES ('82', 'Nostrum rerum voluptas quia repellendus et odio libero. Ipsa aliquid ipsum eligendi porro consequuntur nisi assumenda. Quam ratione reiciendis non vitae deserunt distinctio.', 'img/916e5a135ebc72c38de6f2fe35bc211a.png', 'nostrum-eligendi', 'mie');
INSERT INTO "public"."sandwich" VALUES ('83', 'Ipsam ratione at consequuntur modi ut. Ex odio sint molestiae id at quam cumque. Dolor laborum alias a repellat dolor qui et.', null, 'nesciunt-distinctio', 'mie');
INSERT INTO "public"."sandwich" VALUES ('84', 'In beatae eos dolorem aliquam est vero iste. Harum adipisci velit quod impedit fugiat. Exercitationem repellat asperiores dolor officiis laboriosam facilis id.', null, 'magni-autem', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('85', 'Deserunt cumque animi dolores iure. Et delectus non assumenda officia id dignissimos. Est velit quasi eum facilis accusamus.', 'img/151d76856fb9fa716fdede9b57ea00ec.png', 'sit-impedit', 'mie');
INSERT INTO "public"."sandwich" VALUES ('86', 'Ducimus aut sed facere ratione. Quia voluptatibus iusto voluptatem quod. Atque vel iure repellendus ipsum sed exercitationem recusandae.', 'img/f12957e5266616b1d7532323cc2de738.png', 'maxime-ut', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('87', 'Inventore accusantium qui est totam quis. Voluptates sit reprehenderit debitis officiis et et aut. Saepe atque qui quae quod velit.', 'img/886718da90e0bb0167bd3cae8e8fbebb.png', 'odit-illum', 'mie');
INSERT INTO "public"."sandwich" VALUES ('88', 'Et omnis molestiae vitae hic qui corrupti. Sit suscipit quo non architecto dolores deserunt. Eum minus sed quis.', null, 'in-est', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('89', 'Sit quam culpa sit sapiente magni voluptas nihil. Eaque architecto provident aperiam aliquid voluptatem. Quod minus maxime et ut aliquid quis consectetur.', 'img/4fe256dfeadfaa91951e6a81fd40921b.png', 'ipsum-hic', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('90', 'Qui ut est distinctio aliquid. Velit voluptatem aliquam odit. Numquam magni consequuntur fugiat amet dolor asperiores.', null, 'rerum-magnam', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('91', 'Dolorum eos consequatur blanditiis expedita dolores. Modi ut deserunt libero in veritatis repellat et. Nostrum nesciunt aut voluptatum minus qui exercitationem tempora aut.', null, 'aspernatur-hic', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('92', 'Reprehenderit nihil eum quidem nostrum maiores. Beatae placeat et voluptas ducimus voluptatum adipisci blanditiis aliquid. Sit fuga adipisci sequi autem sit voluptates.', 'img/e8a000fbe0bb1a78b9d2119fea1047a6.png', 'placeat-consequuntur', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('93', 'Atque praesentium rerum numquam. Id ut et consequatur dolorem. Rerum eum necessitatibus officia rerum sed minus quia.', null, 'reiciendis-sint', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('94', 'Nam dicta in repudiandae et recusandae distinctio. Sunt deleniti nesciunt eveniet impedit beatae blanditiis nisi. Molestias deserunt iusto reiciendis ut dolorem.', null, 'sint-aut', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('95', 'Sunt in aut at corrupti. Quaerat nihil explicabo sapiente aut adipisci. Cupiditate dicta voluptas quibusdam nihil accusamus et.', null, 'distinctio-ad', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('96', 'Voluptatum omnis optio ad odio ratione quos magnam. Illum et qui non iure. Dicta itaque quos harum quo quo omnis.', 'img/5fcb5797776b4acddcc3114599050d06.png', 'voluptas-molestiae', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('97', 'Inventore necessitatibus et autem voluptatibus quia eos. Molestias eaque voluptatum velit cum nulla repellat ut sit. Animi ut doloribus laudantium modi.', null, 'et-at', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('98', 'Et quae minus ipsa illum. Voluptas eligendi sit et praesentium assumenda beatae dolorum. Culpa exercitationem ducimus deserunt incidunt.', null, 'rerum-pariatur', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('99', 'Repellat exercitationem aspernatur expedita delectus autem quibusdam iure. Assumenda ut natus pariatur dolorem eos architecto qui reprehenderit. Quam sit natus magnam similique maxime.', 'img/1e794d57abb56ea407a77c56bcc37197.png', 'voluptatibus-tempora', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('100', 'Est necessitatibus sit inventore. Nihil culpa eos quaerat ipsa rem modi facere. At aut sed nesciunt inventore est ut.', 'img/4f96a002444be86d244e4f7159a29dc6.png', 'explicabo-quia', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('101', 'Dolorem dolorum adipisci ut in. Esse et consequatur tenetur dolor. Asperiores dolorem facilis voluptatum recusandae.', 'img/44fbc36c5ec08cc6c3c0e49272c5c684.png', 'ducimus-voluptates', 'mie');
INSERT INTO "public"."sandwich" VALUES ('102', 'Qui aliquam voluptas et et sit quo. Mollitia iusto dolorum natus ut sed vitae nihil ut. Nostrum aperiam dolore corporis placeat.', null, 'consequatur-aspernatur', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('103', 'In ut et voluptatem ad non necessitatibus omnis. Id aliquam praesentium nam. Laborum animi illum delectus est explicabo id quis.', 'img/f33bdb70d55794baf46aa398386853b2.png', 'aspernatur-commodi', 'mie');
INSERT INTO "public"."sandwich" VALUES ('104', 'Distinctio et et repellat dolores qui nihil. Quia nobis et eius vel. Autem assumenda magnam qui qui placeat.', 'img/10062116ad39a09919a97f41a1a13f2b.png', 'eaque-similique', 'mie');
INSERT INTO "public"."sandwich" VALUES ('105', 'Sit et modi eligendi eum modi quia provident. Qui dolor rerum repellat voluptates. Voluptas eligendi sint nihil iusto eum.', null, 'dolores-facilis', 'mie');
INSERT INTO "public"."sandwich" VALUES ('106', 'Sunt culpa sapiente temporibus dolorum aspernatur omnis. Et unde sed aspernatur est. Quisquam dolorum velit natus perspiciatis ut.', 'img/8e4971b29216225b039f07ffcf8c964d.png', 'eos-omnis', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('107', 'Atque ad eos a beatae. Doloribus nemo ipsam dolore vero. Maiores nam porro sit.', null, 'asperiores-inventore', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('108', 'Quia sit qui eaque doloremque mollitia eveniet aut excepturi. Ut sed velit doloribus. Quia qui dolor velit quo nostrum debitis perferendis.', null, 'velit-est', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('109', 'Ipsa occaecati nam dolores autem assumenda. Et nihil odit ut nihil et aut ut. Rem et ut adipisci fugit voluptatem.', null, 'ipsum-in', 'baguette campagne');
INSERT INTO "public"."sandwich" VALUES ('110', 'Delectus et deleniti libero blanditiis iste beatae. Consequatur ut ut magni iure qui accusantium id. Corporis illum illum aut atque tenetur aut.', null, 'dolores-voluptatem', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('111', 'Sunt non porro facere maiores. Enim porro voluptate nostrum aliquam facilis. Sed aut blanditiis qui.', 'img/f660903077e0dbd42af9ebad86b31373.png', 'non-dolor', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('112', 'Quidem exercitationem est suscipit omnis. Numquam dignissimos cumque porro voluptatem. Placeat cum sequi dolorum voluptates consectetur repellat quidem.', null, 'et-rerum', 'baguette');
INSERT INTO "public"."sandwich" VALUES ('113', 'Consequuntur temporibus dolor quibusdam iure fuga est. Ut unde suscipit corrupti odio dolores velit. Aut aut quis qui non dolorem voluptatem qui.', null, 'aut-dolorem', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('114', 'Rerum et in quis voluptas. Hic fugiat nobis maxime suscipit eligendi rerum. Dolorum officia nihil asperiores ipsa.', null, 'accusamus-voluptas', 'pain complet');
INSERT INTO "public"."sandwich" VALUES ('115', 'Sunt voluptatem est autem dolorem qui expedita. Dolores qui quis nam et officiis dolor. Sunt ratione quia in at est omnis temporibus.', null, 'est-excepturi', 'tortillas');
INSERT INTO "public"."sandwich" VALUES ('116', 'le bon sandwich au saumon', null, 'délice de la mer', 'mie');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sandwich
-- ----------------------------
ALTER TABLE "public"."sandwich" ADD PRIMARY KEY ("id");
