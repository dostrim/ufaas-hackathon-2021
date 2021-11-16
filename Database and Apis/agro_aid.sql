-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 16, 2021 at 12:07 AM
-- Server version: 5.7.23-23
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lykrklmy_agro_aid`
--

-- --------------------------------------------------------

--
-- Table structure for table `farmers`
--

CREATE TABLE `farmers` (
  `id` int(11) NOT NULL,
  `first_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone_number` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `farmers`
--

INSERT INTO `farmers` (`id`, `first_name`, `last_name`, `phone_number`, `password`, `gender`) VALUES
(8, 'Matovu', 'Joseph', '+256775759547', '123456', 'Female'),
(9, 'Tendo', 'John', '+256750673606', '123456', 'Male'),
(13, 'Isaac', 'Balintuma', '+256704873305', '123456', 'Male'),
(14, 'Isaac', 'Balintuma', '+256784234512', '123456', 'Male'),
(17, 'Matovu', 'Joseph', '+256707072434', '123456', 'Male'),
(18, 'Matovu', 'Joseph', '+256707002435', '123456', 'Male'),
(19, 'Justine', 'Balintuma', '+256750313260', '1234567890', 'Male'),
(20, 'Lilian', 'Balintuma', '+256701200400', '123456', 'Male'),
(21, 'Julie ', 'matovu', '+256708200600', '123456', 'Male'),
(22, 'Pregnant', 'Farm', '+256701800800', '1234567890', 'Female'),
(23, 'Christine ', 'Hello ', '+256707070523', '123456', 'Male'),
(24, 'Christine ', 'Kihunde Kiiza ', '+256702318190', 'Love@2021', 'Female');

-- --------------------------------------------------------

--
-- Table structure for table `farmers_plants`
--

CREATE TABLE `farmers_plants` (
  `id` int(11) NOT NULL,
  `plant_id` int(11) NOT NULL,
  `farmer_id` int(11) NOT NULL,
  `planting_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `farmers_plants`
--

INSERT INTO `farmers_plants` (`id`, `plant_id`, `farmer_id`, `planting_date`, `created_at`) VALUES
(2, 1, 1, '2021-01-01', '2021-10-07 02:02:15'),
(3, 6, 1, '2021-10-05', '2021-10-07 04:02:36'),
(4, 1, 4, NULL, '2021-10-07 04:42:18'),
(5, 5, 4, NULL, '2021-10-07 04:42:22'),
(6, 6, 4, NULL, '2021-10-07 04:42:25'),
(7, 6, 5, NULL, '2021-10-07 05:17:57'),
(8, 5, 5, NULL, '2021-10-07 05:17:58'),
(9, 6, 6, '2021-10-04', '2021-10-07 05:31:56'),
(11, 4, 6, NULL, '2021-10-07 05:31:59'),
(12, 6, 7, '2021-10-03', '2021-10-07 05:50:07'),
(13, 6, 9, '0000-00-00', '2021-10-07 07:23:24'),
(14, 6, 11, '2021-09-20', '2021-10-07 07:35:28'),
(15, 8, 11, '0000-00-00', '2021-10-07 07:35:29'),
(16, 6, 12, '2021-09-08', '2021-10-07 07:51:55'),
(17, 4, 12, NULL, '2021-10-07 07:51:57'),
(18, 6, 13, '2021-09-20', '2021-10-07 08:39:50'),
(19, 4, 13, NULL, '2021-10-07 08:39:52'),
(20, 6, 14, '2021-09-20', '2021-10-07 08:47:36'),
(21, 4, 14, NULL, '2021-10-07 08:47:37'),
(22, 5, 9, NULL, '2021-10-13 03:23:41'),
(23, 6, 17, '2021-09-26', '2021-10-26 07:23:14'),
(24, 5, 17, NULL, '2021-10-26 13:57:36'),
(25, 3, 17, NULL, '2021-10-26 13:57:40'),
(26, 8, 17, NULL, '2021-10-26 13:57:42'),
(27, 11, 17, NULL, '2021-10-26 13:57:46'),
(28, 10, 17, NULL, '2021-10-26 13:57:48'),
(29, 2, 17, NULL, '2021-10-26 14:10:24'),
(30, 6, 17, NULL, '2021-10-27 07:40:00'),
(31, 5, 17, NULL, '2021-10-27 07:40:01'),
(32, 8, 17, NULL, '2021-10-27 07:40:01'),
(33, 9, 17, NULL, '2021-10-27 07:40:03'),
(34, 3, 17, NULL, '2021-10-27 07:40:06'),
(35, 2, 17, NULL, '2021-10-27 07:40:07'),
(36, 1, 19, NULL, '2021-10-27 08:43:40'),
(37, 6, 19, NULL, '2021-10-27 08:43:42'),
(38, 6, 20, '2021-10-01', '2021-10-27 08:45:04'),
(39, 8, 20, NULL, '2021-10-27 08:45:09'),
(40, 6, 22, '2021-10-20', '2021-10-28 09:53:34'),
(41, 5, 22, NULL, '2021-10-28 09:53:36'),
(42, 6, 23, '2021-11-01', '2021-11-01 12:03:49'),
(43, 11, 24, '2021-10-01', '2021-11-01 12:39:25'),
(44, 8, 24, NULL, '2021-11-01 12:39:34'),
(45, 5, 24, NULL, '2021-11-01 12:39:35'),
(46, 6, 13, NULL, '2021-11-03 13:01:32'),
(47, 4, 13, NULL, '2021-11-03 13:01:34'),
(48, 6, 17, NULL, '2021-11-13 19:57:06');

-- --------------------------------------------------------

--
-- Table structure for table `farms`
--

CREATE TABLE `farms` (
  `id` int(11) NOT NULL,
  `location` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `size` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `boundary` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `latitude` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `farmer_id` int(250) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `forums`
--

CREATE TABLE `forums` (
  `id` int(11) NOT NULL,
  `title` text COLLATE utf8_unicode_ci NOT NULL,
  `content` text COLLATE utf8_unicode_ci NOT NULL,
  `up_vote` int(11) NOT NULL DEFAULT '0',
  `down_vote` int(11) NOT NULL DEFAULT '0',
  `farmer_id` int(11) NOT NULL,
  `plant_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `picture` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `forums`
--

INSERT INTO `forums` (`id`, `title`, `content`, `up_vote`, `down_vote`, `farmer_id`, `plant_id`, `created_at`, `picture`) VALUES
(1, 'Lorem Ipsum is simply dummy text of the printing and typesetting', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem', 3, 2, 9, 1, '2021-10-26 12:18:36', 'plant_sample_image.jpg'),
(2, 'Banana Pests and Disease Problem', 'Ugandan farmers are faced with the pest and disease burden affecting most banana species grown in the country, ranging from the East African highland banana commonly known as matooke, sukari ndizi, bogoya and gonja. The pests considered \"most dangerous\" by crop scientists are weevils and nematodes.\r\n\r\nThe banana weevils, known as Cosmopolites sordidus, are found in all banana growing countries and have existed from the time farmers began growing bananas. They lay eggs at the stems and cause the trunks to rot leading to collapse of the plant.\r\n\r\nYield loss\r\n\r\nAccording to Dr David Talengera, a banana breeder at the National Agricultural Research Laboratories Institute (NARLI) in Kawanda, the weevils destroy the core of the banana stem from the neighbouring infested suckers.\r\n\r\nThis causes water deficiency in the plant causing stunted growth leading to a 30 to 50 per cent yield loss in most farmers\' fields.\r\n\r\nThe banana nematodes are a type of roundworm, which inhibit the root system of the crop causing inability to absorb water and reduce the leaves of the plant leading to 50 per cent yield loss.\r\n\r\nHowever the nematode species are very difficult to distinguish; some are parasitic while others are not.', 0, 0, 8, 6, '2021-10-27 06:33:20', 'feed_one.jpg'),
(3, 'How to manage your Banana plantation', 'For a well grown and high yielding banana plantation, management is a priorty. Below are some of the to-dos.\r\n\r\nDesuckering; this involves uprooting of excess suckers from a banana mat, you will this in order to suit the harvest frequency. Removing of the side shoot is done until the emergence of flowers 1-3 stems at most per mat (i.e. the bearing one, the follower and the sucker). Sucker management is important to avoid high mats and to maintain proper spacing. High or many suckers per mat could easily fall.\r\n\r\nMulching; this is used to conserve moisture in the soils, and to reduce rainfall runoff to avoid erosion. Mulch also improves the soil as the mulch material rots. However, mulch is known to serve as breeding place for banana weevils and other pests. Additionally, if you placed your mulch too close to the mother plant it will affect the growth of the young suckers. The means you need to work out a balanced approach to mulching your banana plantation in Uganda.\r\n\r\nStaking; Bananas are susceptible to winds and should be staked to provide extra support to the banana stems. Banana cultivars that bear very big bunches are most susceptible to heavy winds. You normally do your banana staking using a forked pole.\r\n\r\nBagging; this involves majorly covering the banana bunch with a treated polythene bag to minimize sooty mold (Furry growth of fungus), insect damage and abrasion injury to the fruits.\r\n\r\nDeflowering; once all the fingers have developed the rest of the inflorescence including the male flower bud) should be removed to reduce incidences of fungus and insect attack.\r\n\r\nFertilizer requirements: Bananas absorb a lot of nutrients from the soil. Therefore there is need to for you to replenish the soil using external sources like the farm yard manure, crop residues, homestead and kitchen refuse. You should however avoid applying metals or polythene on your banana plants. You should never apply manure too close to the banana mat as this would encourage banana weevils to breed and will also result in the high mat condition.\r\n', 0, 0, 18, 6, '2021-10-27 06:39:47', 'banana_plantations.jpg'),
(4, 'How bananas are grown', 'You can propagate/Plant your Bananas vegetatively or by breeding. The vegetative method however is the commonest among our farmers, and you will be able to use any of these planting materials below:\r\nPeepers; these are very young suckers appearing above the ground with scale leaves only.\r\nSword suckers; these are formed from buds or eyes low on corm and bear narrow elongated leaves, these are usually 30_60 cm tall with. These are the most preferred material as they are usually firm in the ground.\r\nMaiden sucker; these are relatively old with greater than 60cm and when are used for propagation it’s advised that the leaves should be cut off to try minimize water loss.\r\nBits of large corms; they are obtained from corms which have bared a bunch. They are dug up; the upper parts are removed and are cut in two or more pieces each containing one or more eyes.\r\nWater suckers; these are young with broad leaves and arise from the top parts of the corms. Always avoid planting these types of suckers as they are usually easy to dislodge from the mother plant and are usually weak.\r\nBefore planting, you should ensure that the suckers are clean, and free from pests and diseases. Your suckers should be cleaned by paring (cutting off all the roots and peeling off the outer layer of the corm). Paring should be done until all tunnels made by weevils have been removed. The pared suckers should be hot water treated to kill nematodes.\r\nThe best time for planting your bananas will depend on your local climatic conditions.\r\nIn areas with pronounced dry season and yet irrigation is not possible, you will typically plant at the beginning of the rains.\r\nYou will plant your bananas in holes dug by hand. Your banana holes should be roughly (45x45x45) cm, with a recommended spacing of (3x3) cm.\r\nMix well rotten manure or compost (1-2) tins with top soil and return it to the hole.\r\nPut the sucker in the middle of the hole and cover with the rest of the soil.\r\nIf you chose to use corm bits, be sure NOT to bury your corms deep; cover with just a 5cm layer of soil.\r\n', 0, 0, 14, 6, '2021-10-27 06:43:42', 'growing_bananas2.jpg'),
(5, 'Guideline on Tomato growing in Uganda', 'Tomato production is one of the major agricultural activities in Uganda. It is a very profitable venture but have its own challenges.\r\n\r\nBotanical name: Lycopersicon esculentum (L)\r\n\r\nSuitable varieties\r\n Roma VFN, Pectomech VF, Tropimech, Rio Grande, Jaguar, Lindo, Titao Derma\r\n\r\nTomato requires warm days, bright sunshine and cool nights for optimum yields. High temperatures and low humidity cause excessive flower drop and reduce yields drastically. Soils should be well drained and fertile.\r\n\r\nIn tomato production, a thorough land preparation is important in enhancing early crop establishment and adequate weed control. Incorporation of well-decomposed poultry manure at the rate of 25ton/ha at land preparation may be beneficial. Prepare ridges or bed across the contours on which seedlings may be transplanted. Construct a farm pond to collect excess water and reuse for irrigation.\r\n\r\n\r\nSeeds can be sown in seed beds in seed trays or seed boxes. Prepare seed beds at 1.2m wide and at any convenient length and then level beds. Water beds, cover with dry grass and burn or solarize soil with transparent plastic sheets for 5-8 weeks to sterilize the soil.\r\n\r\nSow seeds in drills 10cm apart. Cover beds with well dried non-seeded grass or palm fronds. After emergence, remove dry grass and provide shade over the bed. Thin out weak, mal-formed seedlings to avoid overcrowding. Prick out seedlings at first true leaf stage. Transplant seedlings 3-4 weeks after emergence on the field at 5-leaf stage. Two weeks before planting on the field, apply a liquid feed of 5g/L of NPK 15-15-15. harden seedlings 1 week before transplanting by decreasing shade until at least 1-day full exposure to sunlight and/or reducing irrigation.\r\n\r\nPlanting preferably late in the afternoon. Plant 60×30 cm in the dry season and 60×60 cm in the wet season.\r\n\r\n\r\nTimely weed control is necessary for healthy crop growth. This may be achieved by frequent shallow hoeing. Application of pre-emergence weedicides 3-4 days before transplanting will enhance weed control.\r\n\r\n\r\n Water supply is very important, especially in the dry season. The most critical time for ample soil moisture is during bloom and early fruiting stages.', 2, 0, 17, 5, '2021-10-27 06:46:17', 'tomatoes.jpg'),
(6, 'Steps taken in harvesting Rice', 'Harvesting rice depends on many things but most importantly the maturity of the crop. The different varieties mature at different rates but most of them achieve this between 105 to 150 days after establishing the crop. It can be done in two ways and that is mechanical and manual harvesting.\r\n\r\nMechanical harvesting\r\n\r\nThis is not commonly used because of the pricing of the machines but for those that use them, they can help from cutting the rice, separating the grain from the stem and cleaning the rice before packaging it for transportation.\r\n\r\nThe use of the machines reduces on the labor to be used during the rice growing process.\r\n\r\nManual harvesting\r\n\r\nThis is the most commonly practiced in Uganda and it roughly takes about 40 to 80 hours if you harvesting per hectare. It is very labor intensive and the farmers use sickles when harvesting. The only good thing about this is that a farmer gets to collect all the plants from the farm.\r\n\r\nThe steps involved when harvesting are listed below:\r\n\r\nThe reaping process is the first step and it involves cutting the mature straw just above the ground after it drying.\r\n\r\nThe threshing process, this involves separating the grain from the rest of the straw that is cut.\r\n\r\nCleaning or separation step is where the farmer separates the mature grain from the immature and non-grain materials.\r\n\r\nHauling and drying, this is where the cut grain is packed and taken to the threshing area and it is taken for drying in the sun.\r\n\r\nPiling and bagging is where the crop is taken for storage before being packed and transported for permanent storage before sale', 0, 0, 14, 2, '2021-10-27 06:49:30', 'rice_harvesting.jpg'),
(7, 'Tips on Sowing Cabbages', 'Cabbage seeds can be sowed in large numbers due to how hardy these plants are. Their ability to withstand rough handling ensures that cultivators can grow them extensively. The seeds usually take anywhere between 5 to 10 days, with an additional 6-8 weeks required for them to grow into plants that are ready for field setting.\r\n\r\nAn important sowing tip to keep in mind is with regards to choosing which type of cabbage seed to sow. While cabbages can be commercially farmed in large numbers in big farms, they need adequate space between each planted bed. If you are planning on cultivating cabbages in your garden and have limited space, then you should opt for seeds of the small cabbage varieties. Some important sowing tips to keep in mind in order to get the best yield are:\r\n\r\n\r\nSeeds need to be sowed anywhere between half to one-fourth of an inch into the soil\r\n\r\nThe starting soil mix for the seeds must be kept moist and the seeds should be left to germinate in an optimal temperature of 77F for 5-10 days\r\n\r\nThe seedlings must be transplanted into the prepared beds in the garden or farm after they have grown to a size of 4-6 inches with 2-4 leaves visible.\r\n\r\nThe optimal temperature outside for cabbage transplantation is around 50 F\r\n\r\nThe transplanted plants must have a space of 1-2 feet between them and the rows must have a space of 12-42 inches between them. This also helps in controlling pests that might harm the crops.\r\n\r\nInter plant cabbages with other crops such as beets, spinach, green onions, herbs, etc. To maintain soil quality and produce higher yield.\r\n\r\nIt is best to start sowing cabbage transplants early to better offset any risks of bad weather or cutworms. Young cabbage plants are very hardy and comparatively more capable of withstanding these adverse conditions.', 0, 0, 18, 8, '2021-10-27 07:45:27', 'tips_for_cabbage_growing.jpg'),
(8, 'Varieties of Maize mainly grown', 'Common varieties of Maize seed currently available in East Africa include; longe 1, longe 2H, longe 4, longe 5H, longe 2H.\r\n\r\nSome earlier varieties of Corn in Uganda included White Star and Western Queen which were released in 1960 for the northern and western areas of Uganda respectively. White Star was being recommended for its early maturity (115 days) specifically for short rain areas (e.g.Northern Uganda).\r\n\r\nKawanda Composite A was released in 1971. and dominated the improved seed multiplication programme for a while. KWCA was specifically recommended for commercial production during the long rains in the maize growing areas. It is rather late maturing (133 days) and requires early planting.\r\n\r\nA second Kawanda Composite (KWCB) was developed between 1972-1974, and was said to be ready for release to farmers by 1977. It was not, however, released due to lack of seed multiplication facilities.\r\n\r\n', 0, 0, 13, 3, '2021-10-27 07:48:22', 'Uganda_7.jpg'),
(9, 'How to manage mango disease problems', 'Just like any other plant, mangoes are affected by diseases and these include:\r\nAnthracnose (Colletotrichum gloeosporioides) \r\nAfflicts mangoes most severely. In the case of anthracnose, mango disease symptoms appear as black, sunken, irregularly shaped lesions that grow resulting in blossom blight, leaf spotting, fruit staining and eventual rot. The disease is fostered by rainy conditions and heavy dews.\r\n\r\nMango scab (Elsinoe mangiferae) is another fungal disease that attacks leaves, flowers, fruit and twigs. The first signs of infection mimic the symptoms of anthracnose. Fruit lesions will be covered with a corky, brown tissue and leaves become distorted. \r\n\r\nVerticillium wilt attacks the tree’s roots and vascular system, preventing the tree from up-taking water. Leaves begin to wilt, brown, and desiccate; stems and limbs die back; and the vascular tissues turn brown. The disease is most damaging to young trees and may even kill them. \r\n\r\nParasitic algal spot is another infection that more rarely afflicts mango trees. In this case, mango disease symptoms present as circular greenish/grey spots that turn rust red on the leaves. Infection of stems can lead to bark cankers and stem thickening and death.\r\n\r\n\r\nTreating a sick mangoes for fungal diseases involves using a fungicide. All susceptible parts of the tree should be thoroughly coated with the fungicide before infection occurs. If applied when the tree is already infected, the fungicide will have no effect. Fungicide sprays need to be reapplied on new growth. Apply fungicide in the early spring and again 10-21 days later to protect the panicles of blossoms during development and fruit set. If powdery mildew is in evidence, apply sulfur to prevent the spread of the infection to new growth.\r\n\r\nIf the tree becomes infected with verticillium wilt, prune out any infected limbs. \r\n\r\nMango scab generally doesn’t need to be treated since an anthracnose spray program also controls scab. \r\n\r\nAlgal spot will also usually not be an issue when copper fungicides are periodically applied during the summer. To reduce the risk of fungal infections, grow only anthracnose resistant cultivars of mango. Maintain a consistent and timely program for fungal application and thoroughly cover all susceptible parts of the tree. ', 0, 0, 9, 10, '2021-10-27 07:55:51', 'mango_tree.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `markets`
--

CREATE TABLE `markets` (
  `id` int(11) NOT NULL,
  `name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `latitude` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `markets`
--

INSERT INTO `markets` (`id`, `name`, `latitude`, `longitude`) VALUES
(1, 'Kalerwe Market', '0.3505132', '32.5695073'),
(2, 'Nakasero Market', '0.3118127', '32.5777162'),
(3, 'Owino Market', '0.3101571', '32.5705829'),
(4, 'Akawamu Market', '0.3612767', '32.5735364');

-- --------------------------------------------------------

--
-- Table structure for table `market_prices`
--

CREATE TABLE `market_prices` (
  `id` int(11) NOT NULL,
  `plant_id` int(11) NOT NULL,
  `market_id` int(11) NOT NULL,
  `price` int(15) NOT NULL,
  `units` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `status` enum('up','down') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'up',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `market_prices`
--

INSERT INTO `market_prices` (`id`, `plant_id`, `market_id`, `price`, `units`, `status`, `created_at`) VALUES
(1, 6, 1, 15000, 'Bunch', 'up', '2021-10-07 02:26:26'),
(2, 6, 2, 16000, 'Bunch', 'up', '2021-10-07 02:26:26'),
(3, 6, 3, 13000, 'Bunch', 'down', '2021-10-07 02:26:26');

-- --------------------------------------------------------

--
-- Table structure for table `plants`
--

CREATE TABLE `plants` (
  `id` int(10) NOT NULL,
  `plant_name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `plant_image` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `plant_icon` varchar(200) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `plants`
--

INSERT INTO `plants` (`id`, `plant_name`, `plant_image`, `plant_icon`) VALUES
(1, 'Cassava', 'cassava.jpg', 'cassava_icon.png'),
(2, 'Rice', 'rice.jpg', 'rice_icon.png'),
(3, 'Maize', 'maize.jpg', 'maize_icon.png'),
(4, 'Yams', '', 'yam_icon.png'),
(5, 'Tomatoes', 'tomato.jpg', 'tomato_icon.png'),
(6, 'Banana', 'banana.jpg', 'banana_icon.png'),
(7, 'Coffee', 'coffee.jpeg', 'coffee_icon.png'),
(8, 'Cabbage', 'cabbage.jpg', 'cabbage_icon.png'),
(9, 'Apples', 'apple.jpg', 'apples_icon.png'),
(10, 'Mangoes', 'mango.jpg', 'mangoes_icon.png'),
(11, 'Onions', 'onion.jpg', 'onions_icon.png'),
(12, 'Beans', 'beans.jpg', 'beans_icon.png');

-- --------------------------------------------------------

--
-- Table structure for table `plant_age`
--

CREATE TABLE `plant_age` (
  `id` int(11) NOT NULL,
  `minimum` int(11) NOT NULL DEFAULT '0',
  `maximum` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `plant_age`
--

INSERT INTO `plant_age` (`id`, `minimum`, `maximum`, `created_at`) VALUES
(1, 0, 120, '2021-10-06 20:48:18'),
(2, 0, 450, '2021-10-06 20:48:18'),
(3, 0, 21, '2021-10-06 20:48:54'),
(4, 0, 28, '2021-10-06 20:48:54');

-- --------------------------------------------------------

--
-- Table structure for table `plant_disease`
--

CREATE TABLE `plant_disease` (
  `id` int(10) NOT NULL,
  `plant_age` int(11) NOT NULL DEFAULT '1',
  `plant_id` int(10) NOT NULL,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(5000) COLLATE utf8_unicode_ci NOT NULL,
  `cause` varchar(5000) COLLATE utf8_unicode_ci NOT NULL,
  `symptom` varchar(5000) COLLATE utf8_unicode_ci NOT NULL,
  `treatment` varchar(5000) COLLATE utf8_unicode_ci NOT NULL,
  `attack` enum('pest','disease') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'disease'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `plant_disease`
--

INSERT INTO `plant_disease` (`id`, `plant_age`, `plant_id`, `name`, `image`, `description`, `cause`, `symptom`, `treatment`, `attack`) VALUES
(1, 1, 9, 'Apple Scab', 'Apple_Scab.jpg', 'Apple scab is a disease of Malus trees, such as apple trees, caused by the ascomycete fungus Venturia inaequalis. The disease manifests as dull black or grey-brown lesions on the surface of tree leaves, buds or fruits.', 'The fungal disease forms pale yellow or olive-green spots on the upper surface of leaves. Dark, velvety spots may appear on the lower surface', 'Scabby spots are sunken and tan and may have velvety spores in the center. As these spots mature, they become larger and turn brown and corky. Infected fruit becomes distorted and may crack allowing entry of secondary organisms. Severely affected fruit may drop, especially when young.', 'For best control, spray liquid copper soap early, two weeks before symptoms normally appear. Alternatively, begin applications when disease first appears, and repeat at 7 to 10 day intervals up to blossom drop.', 'disease'),
(2, 1, 5, 'septoria leaf spot', 'septoria_leaf_spot.jpg', 'Septoria leaf spot is caused by a fungus, Septoria lycopersici. It is one of the most destructive diseases of tomato foliage and is particularly severe in areas where wet, humid weather persists for extended periods. Septoria leaf spot usually appears on the lower leaves after the first fruit sets.', 'The fungus overwinters on infected tomato debris or on weeds in the nightshade family, the same family to which tomatoes belong. The fungus can also survive on equipment such as plant stakes and cages. Long periods of high relative humidity, temperatures of 60–80 degrees F, and leaf wetness are ideal conditions for development and spread of the pathogen.', 'Septoria leaf spot usually appears on the lower leaves after the first fruit sets. Spots are circular, about 1/16 to 1/4 inch in diameter with dark brown margins and tan to gray centers with small black fruiting structures. Characteristically, there are many spots per leaf. This disease spreads upwards from oldest to youngest growth. If leaf lesions are numerous, the leaves turn slightly yellow, then brown, and then wither. Fruit infection is rare.', '1. Remove diseased leaves. If caught early, the lower infected leaves can be removed and burned or destroyed. However, removing leaves above where fruit has formed will weaken the plant and expose fruit to sunscald. At the end of the season, collect all foliage from infected plants and dispose of or bury. Do not compost diseased plants.\r\n\r\n2. Improve air circulation around the plants. If the plants can still be handled without breaking them, stake or cage the plants to raise them off the ground and promote faster drying of the foliage.\r\n\r\n3. Mulch around the base of the plants. Mulching will reduce splashing soil, which may contain fungal spores associated with debris. Apply mulch after the soil has warmed.\r\n\r\n4. Do not use overhead watering. Overhead watering facilitates infection and spreads the disease. Use a soaker hose at the base of the plant to keep the foliage dry. Water early in the day.\r\n\r\n5. Control weeds. Nightshade and horsenettle are frequently hosts of Septoria leaf spot and should be eradicated around the garden site.\r\n\r\n6. Use crop rotation. Next year do not plant tomatoes back in the same location where diseased tomatoes grew. Wait 1–2 years before replanting tomatoes in these areas.\r\n\r\n7. Use fungicidal sprays. If the above measures do not control the disease, you may want to use fungicidal sprays. Fungicides will not cure infected leaves, but they will protect new leaves from becoming infected. Apply at 7 to 10 day intervals throughout the season. Apply chlorothalonil, maneb, macozeb, or a copper-based fungicide, such as Bordeaux mixture, copper hydroxide, copper sulfate, or copper oxychloride sulfate. Follow harvest restrictions listed on the pesticide label.', 'disease'),
(3, 1, 5, 'Bacterial Wilt', 'Bacteria_wilt.jpg', 'Bacterial wilt is caused by Ralstonia solanacearum (formerly Pseudomonas solanacearum). This bacterium survives in the soil for extended periods and enters the roots through wounds made by transplanting, cultivation or insects and through natural wounds where secondary roots emerge.', 'High temperatures and high moisture. The bacteria multiply rapidly inside the water-conducting tissue of the plant, filling it with slime. This results in a rapid wilt of the plant, while the leaves stay green. If an infected stem is cut crosswise, it will look brown and tiny drops of yellowish ooze may be visible.', 'Leaves first appear dull green, wilt during the day and recover at night.\r\n\r\nLeaves eventually yellow and brown at the margins, completely wither and die.\r\n\r\nWilt progression varies by crop.\r\n Cucumbers and melons wilt and die rapidly.\r\n Pumpkins take up to two weeks to wilt completely\r\n Summer squash may continue to produce for several weeks even when infected.\r\n\r\nWilt progresses down the vine until the entire vine wilts or dies.\r\n\r\nStriped or spotted cucumber beetles will be present in the garden.\r\n\r\nIf infected vines are cut close to the crown of the plant, and the cross-sections pressed together, thread-like strands of bacterial ooze are visible when the two halves are gently pulled apart again.', 'Control of bacterial wilt of plants grown in infested soil is difficult. Rotation with non-susceptible plants, such as corn, beans and cabbage, for at least three years provides some control. Do not use pepper, eggplant, potato, sunflower or cosmos in this rotation. Remove and destroy all infected plant material. Plant only certified disease-free plants. The cultivar Kewalo is partially resistant to bacterial wilt, but is an uncommon cultivar. Chemical control is not available for this disease.', 'disease'),
(4, 1, 5, 'Early Blight', 'early_blight.jpg', 'Early blight is caused by the fungus Alternaria solani. Common on tomato and potato plants. The fungus produces distinctive \"bullseye\" patterned leaf spots and can also cause stem lesions and fruit rot on tomato and tuber blight on potato', 'Early blight is caused by the fungus, Alternaria solani, which survives in infected leaf or stem tissues diseased potato tubers on or in the soil and in infected tomato fruits. This fungus is universally present in fields where susceptible crops have been grown', 'Symptoms first appear on the lower, older leaves as small brown spots with concentric rings that form a \"bull\'s eye\" pattern. As the disease matures, it spreads outward on the leaf surface causing it to turn yellow, wither and die. Eventually the stem, fruit and upper portion of the plant will become infected. Crops can be severely damaged.', 'Use resistant or tolerant tomato cultivars. Use pathogen-free seed and do not set diseased plants in the field. Use crop rotation, eradicate weeds and volunteer tomato plants, space plants to not touch, mulch plants, fertilize properly, don\'t wet tomato plants with irrigation water, and keep the plants growing vigorously. Trim off and dispose of infected lower branches and leaves.\r\n\r\nTo reduce disease severity, test the garden soil annually and maintain a sufficient level of potassium. Side dress tomato plants monthly with calcium nitrate for adequate growth.\r\n\r\nIf disease is severe enough to warrant chemical control, select one of the following fungicides: mancozeb (very good); chlorothalonil or copper fungicides (good). ', 'disease'),
(5, 1, 5, 'Late Blight', 'late_blight.jpg', 'Late blight is a potentially serious disease of potato and tomato, caused by the fungus Phytophthora infestans. Late blight is especially damaging during cool, wet weather. The fungus can affect all plant parts. Young leaf lesions are small and appear as dark, water-soaked spots. These leaf spots will quickly enlarge and a white mold will appear at the margins of the affected area on the lower surface of leaves.', 'Infected tomato fruits develop shiny, dark or olive-colored lesions, which may cover large areas. Fungal spores are spread between plants and gardens by rain and wind. A combination of daytime temperatures with high humidity is ideal for infection.', 'Late blight first appears on the lower, older leaves as water-soaked, gray-green spots. As the disease matures, these spots darken and a white fungal growth forms on the undersides. Eventually the entire plant will become infected.', 'Keep foliage dry. Locate your garden where it will receive morning sun.\r\n\r\nAllow extra room between the plants, and avoid overhead watering, especially late in the day.\r\n\r\nPurchase certified disease-free seeds and plants.\r\n\r\nDestroy volunteer tomato and potato plants and nightshade family weeds, which may harbor the fungus.\r\n\r\nDo not compost rotten, store-bought potatoes.\r\n\r\nPull out and destroy diseased plants.\r\n\r\nIf disease is severe enough to warrant chemical control, select one of the following fungicides: chlorothalonil (very good); copper fungicide, or mancozeb (good). See Table 1 for examples of fungicide products for home garden use. Follow the directions on the label.\r\n\r\nPlant resistant cultivars. See Table 3 for tomato cultivars with resistance to late blight.', 'disease'),
(6, 1, 3, 'Anthracnose', 'Anthracnose.jpg', 'Anthracnose, caused by the fungus Colletotrichum graminicola, is often the first disease that shows up on corn.', 'The fungal pathogen Colletotrichum graminicola is the causal agent of anthracnose in corn. Anthracnose is common early in the season in fields where debris from the previous year was left on the soil. This early-season disease phase is generally more severe in fields where continuous no-till corn has been grown. It causes a leaf spot disease when corn is in the seedling stage. The pathogen is disseminated by wind and rain splash.', 'Small, round to irregular, water-soaked spots first appear on lower leaves. Spots later turn yellow and then brown with reddish-brown borders. Yellow zones often develop around the leaf spots (see figure at right). Numerous spots can cause leaf tips or entire leaves to turn yellow. With the aid of a hand lens, black spines can be seen arising from the center of the spots. The leaf-spotting phase of the disease generally doesn\'t occur past the knee-high stage of corn growth.', 'Plant hybrids resistant to anthracnose; rotating crops and plowing crop debris into soil may help reduce incidence of early season infections.', 'disease'),
(7, 1, 3, 'Grey Leaf Spot', 'grey_leaf_spot.jpg', 'Gray leaf spot is a common fungal disease caused by the pathogen Cercospora zeae-maydis in corn.', 'Gray leaf spot caused by the pathogen Cercospora zeae-maydis in corn.', 'Small necrotic spots with chlorotic halos on leaves which expand to rectangular lesions 1-6 cm in length and 2-4 mm wide; as the lesions mature they turn tan in color and finally gray; lesions have sharp, parallel edges and are opaque; disease can develop quickly causing complete blighting of leaves and plant death.\r\n\r\n1. Brown Spots with yellow rings throughout the leaf during the growing period of the Cassava\r\n2. Lesions that are 0.15-0.2 cm in diameter\r\n3. Serious cases can lead to holes throughout the lesions on the leaf', 'Plant corn hybrids with resistance to the disease; crop rotation and plowing debris into soil may reduce levels of inoculum in the soil but may not provide control in areas where the disease is prevalent; foliar fungicides may be economically viable for some high yeilding susceptible hybrids.', 'disease'),
(8, 1, 3, 'Charcoal Rot', 'Charcoal_rot.jpg', 'Charcoal rot is caused by the fungus Macrophomina phaseolina. The pith and rind of affected plants appear gray because of the numerous tiny black microsclerotia that develop. The pith tissue is disintegrated, leaving the vascular tissue with a granular, gray appearance.', 'Charcoal rot is caused by the fungus Macrophomina phaseolina.\r\n\r\nThe fungus overwinters as sclerotia in crop residue and soil and infects plants through roots. It may occur when growing conditions are hot and dry.', 'Symptoms are usually first apparent at the tasseling stage; plant stalks become shredded and pith is completely rotted with stringy strands of vascular tissue left intact; small, black fungal fruiting bodies are visible in the vascular strands and give the tissue a gray coloration; fungus grows into internodes of the stalk causing the plant to ripen early and causing the stalk to weaken; plant may break.', 'There are currently no available fungicides to treat the disease; avoid stressing plants by practicing good water management; rotating crops with small grains may help reduce disease incidence.', 'disease'),
(9, 1, 1, 'Anthracnose', 'cassava_anth.jpg', 'The cassava anthracnose disease affects the stem of the cassava and can lead to loss or shortages in planting materials. ', 'Cassava anthracnose disease caused by the fungus Colletotrichum gloeosporioides', 'Cankers on stems and leaf petioles; leaves drooping downwards; wilting leaves which die and fall from plant leading to plant defoliation; death of shoots; soft parts of plant become twisted and distorted', 'Anthracnose usually does not cause large-scale economic damage to cassava and control is usually not necessary; avoid planting cuttings with cankers; if disease does occur crop debris should be removed and destroyed after harvest', 'disease'),
(10, 1, 1, 'Cassava Bacterial Blight', 'cbb.jpg', 'Xanthomonas axonopodis pv. manihotis is the pathogen that causes bacterial blight of cassava.', 'Bacterium', 'Small, angular, brown, water-soaked lesions between leaf veins on lower surfaces of leaves; leaf blades turning brown as lesion expands; lesions may have a yello halo; lesions coalesce to form large necrotic patches; defoliation occurs with leaf petioles remaining in horizontal position as leaves drop; dieback of shoots; brown gum may be present on stems, leaves and petioles', 'Rotate cassava crop with non-host; plow crop debris into soil after harvest or remove and burn it; prune infected parts from plant; propagate cuttings only from healthy plants; intercrop cassava with corn (maize) and melon', 'disease'),
(11, 1, 2, 'Bacterial leaf streak', 'bls.jpg', 'Bacterial leaf streak, also known as black chaff, is a common bacterial disease of wheat. The disease is caused by the bacterial species Xanthomonas translucens pv. undulosa.', 'Caused by the bacterial species Xanthomonas translucens pv. undulosa.', 'Small, water-soaked streaks between leaf veins which are initially dark green and then turn translucent; streaks grow larger, coalesce and turn light brown in color; tiny beads of yellow colored bacterial exudate are common on the surface of the streaks; leaves turn brown and then gray-white in color before they die', 'Control of bacterial leaf streak is dependent on the use of resistant rice varieties and on planting of treated seed', 'disease'),
(12, 1, 2, 'Rice Bacterial blight', 'bacterial_blight.jpg', 'Rice bacterial blight, also called bacterial blight of rice, deadly bacterial disease that is among the most destructive afflictions of cultivated rice (Oryza sativa and O. glaberrima).', 'Bacterium - Xanthomonas oryzae pv. oryzae', 'Water-soaked stripes on leaf blades; yellow or white stripes on leaf blades; leaves appear grayish in color; plants wilting and rolling up; leaves turning yellow; stunted plants; plant death; youngest leaf on plant turning yellow', 'Bacterial blight can be effectively controlled by planting resistant rice varieties; avoid excessive nitrogen fertilization; plow stubble and straw into soil after harvest', 'disease'),
(13, 1, 2, 'Bakanae', 'bakanae.jpg', 'Bakanae is a seedborne fungal disease. The fungus infects plants through the roots or crowns. It then grows systemically within the plant. Infected plants are abnormally tall with pale, thin leaves, produce fewer tillers, and produce only partially filled or empty grains.', 'Fungus', 'Seedlings are elongated, slender and pale; seedlings are stunted and chlorotic; death of seedlings; abnormal elongation of older plants which often makes them visible as they grow taller than uninfected plants in the field; sterile plants which do not produce panicles or produce empty panicles', 'Treating seeds with appropriate fungicides prior to planting can be very effective at controlling the disease; less susceptible rice varieties should be grown in areas where fungicide-treated seed is not available', 'disease'),
(14, 1, 6, 'Anthracnose', '', 'Anthracnose is caused by the fungus Colletotrichum musae, that survives in dead or decaying leaves and also on fruits. Its spores can be spread by wind, water and insects as well as by birds and rats feeding on bananas.', 'Fungus Colletotrichum musae', 'Brown spots on fruit peel; large brown to black areas; black lesions on green fruit', 'Commercially produced fruit should be washed and dipped in fungicide prior to shipping; protect fruit from injury; remove flower parts which can harbour fungus.', 'disease'),
(15, 2, 6, 'Black sigatoka', 'banana_sigatoka.jpg', 'Black Sigatoka is a leaf-spot disease of banana plants caused by the ascomycete fungus Mycosphaerella fijiensis', 'fungus Mycosphaerella fijiensis', 'Red/brown flecks or spots on underside or topside of leaves; spots with dark or yellow border and grey centre; death of leaf surface; bunch not developing', 'Export plantations may require regular fungicide applications; increase plant spacing to improve air circulation and reduce humidity; remove leaves with mature spots', 'disease'),
(16, 2, 6, 'Cigar end rot', 'cigar.jpg', 'Cigar end rot is an important disease of banana, which is caused by the fungus Verticillium theobromae. It causes a dry rot of the flower end that produces an ash grey wrinkled lesion on the banana fingers, similar to the burnt end of a cigar.', 'Fungus Verticillium fructigena', 'Tips of fingers initially begin to darken and wrinkle; tips of fingers develop a dark rot; if Verticillium fungi are present then the rot is typically dry and the tips become mummified, if Trachysphaera is present, the rotted are become covered with white spores which gives the fingers the ashen appearance characteristic of cigar end rot.', 'Infected flowers should be removed from the plant; bunches should be bagged using perforated polyethylene; chemical control may be necessary in the case of severe infestations.', 'disease'),
(17, 1, 6, 'Cordana leaf spot', 'Cordana_leaf_spot.jpg', 'Cordana leaf spot is a disease of banana that, even though it is common worlwide, has generally little impact on production. It is caused by two Neocordana fungi that are often found as secondary invaders of leaf lesions caused by other fungi.', 'Fungus Cordana musae', 'Initially the lower leaves shows oval shaped yellow or pale brown spots near the leaf margins. As the disease progress, the central dead brown area of spots is covered by concentric zonation which is surrounded by a yellow halo. The individual spots may join together to form large necrotic area.', 'Remove all the infected leaves and burn them. If the disease is severe spray copper based fungicides.', 'pest'),
(18, 1, 6, 'Banana Bract mosaic', 'banana_mosaic.jpg', 'Bract mosaic is a viral disease caused by the Banana bract mosaic virus (BBrMV) and transmitted by aphids. Its common name comes from the characteristic mosaic symptoms on the flower bracts. Infection can result in growth defects, reduced suckering and misshapen fruit. Severe incidences can lead to fruit rejection and consequently to economic losses.', 'Cucumber mosaic virus', 'Chlorotic mottling or stripes on foliage; distorted fruit which may have chlorotic streaks or mottling; distorted leaves; leaf necrosis', 'Remove susceptible host plants from around plantation; plant virus-free material', 'disease'),
(19, 1, 7, 'Cercospora leaf spot', 'leafspotcc.jpg', 'Cercospora leaf spot is a fungus that occurs on leaves when plants are under stress. The fungus can develop both in seedbeds and after plants have been transplanted into bags. It is the most common nursery disease and a sign of poor management.', 'Fungus Cercospora coffeicola', 'Brown spots on foliage which enlarge and develop gray-white center and a red-brown margin; lesions may also be surrounded by a yellow halo or may have a burned appearance if lesions are very numerous; infected leaves may drop from plant prematurely; lesions on green berries are brown and sunken and may have a purplish halo; infected red berries may have large black sunken areas', 'Ensure crop is adequately fertilized as nutrient deficient plants are more susceptible to the disease; remove all crop debris from filed after pruning to prevent build up of inoculum; good plant spacing and pruning to open up the canopy promotes good air circulation around foliage and protects against disease; if disease does occur then it can be controlled with the use of copper fungicides where available', 'disease'),
(20, 1, 7, 'Coffee berry disease', 'coffee_berry_diseasecc.jpeg', 'Colletotrichum kahawae is a fungal plant pathogen that causes coffee berry disease. The pathogen is an ascomycete that reproduces asexually. The asexual spores are stored within acervuli.', 'Colletotrichum kahawae is a fungal plant pathogen that causes coffee berry disease', 'Dark sunken lesions on green berries\r\n\r\nberries dropping from plant\r\n\r\nmummified berries', 'Protective sprays of copper containing fungicides can help to control the disease; any diseased berries should be removed from plants; resistant varieties are available and should be planted in areas where disease is present', 'disease'),
(21, 1, 7, 'Black twig borer', 'Black_twig_borercc.jpg', 'Black Twig Borer (Xylosandrus compactus) is a species of a scolytid beetle and is one of the few ambrosia beetles that will infest healthy or stressed plants.', 'Xylosandrus compactus insect', 'Wilting and yellowing of foliage, often at end of twigs and branches (termed \"flagging\")\r\n\r\no pin sized hole can often be found on the underside of the flagging stems or twigs where the insect has entered the plant\r\n\r\nTwigs and stems are hollowed out and can be seen by cutting open the affected tissue\r\n\r\nThe adult beetle is small and black, approx. 2 mm in length and is rarely seen; eggs and pupae are creamy white in color', 'Prune out infested twigs and stems and destroy; flagging branches should be pruned back a few inches from the beginning of symptomatic areas; adequate fertilizer and irrigation to ensure vigorous plants can speed recovery from pruning injury', 'disease'),
(22, 1, 12, 'Alternaria leaf spot', 'bean_leaf_spot.jpg', 'Alternaria leaf spot is caused by the fungal pathogen Alternaria alternata. This is a minor disease of faba beans occurring late in the season and is often confused with chocolate spot.', 'Fungus Alternaria alternata', 'Small irregular brown lesions on leaves which expand and turn gray-brown or dark brown with concentric zones\r\n\r\nolder areas of lesions may dry out and drop from leaves causing shot hole\r\n\r\nlesions coalesce to form large necrotic patches', 'Plant beans in fertile soil\r\n\r\nfoliar fungicide application may be required', 'disease'),
(23, 1, 8, 'Black Rot', 'black_rot.jpg', ' This affects the leaves. Humid rainy conditions are ideal for the development of black rot. Yellow to light brown patches appear at the margins of leaves and later black veins develop within the yellowed areas. Affected areas turn brown and dry out, often leaving a triangular-shaped lesion on the leaf margin with one point of the triangle directed toward the midrib.', 'It is caused by the bacterium Xanthomonas campestris pv. campestris, is the most common and destructive disease of the cabbage family worldwide.', 'Leaves come with a \'V\' shaped lesions\r\nSeeds get discolourations, lesions. The stems get internal discoloration (black in colour) and so are the vegetative organs. Eventually the whole plant dies.', 'To manage, remove and destroy infected crop residues, use clean and healthy seedlings and tolerant varieties. Spray SULCOP DF from Osho Chemicals, early then repeat after every 10 days.', 'disease'),
(24, 1, 8, 'Alternaria Leaf spot', 'leaf_spot.jpg', 'During wet seasons, a brown velvety spore-bearing lesion appears on the older leaves. Leaf spots begin as a small dark spot and enlarge to form a large circular lesion forming a bull’s-eye pattern.', 'The disease is caused by several Alternaria species including A. brassicicola, A. brassicae and A. raphanin. Alternaria brassicicola and A. brassicae infect cabbage, cauliflower, broccoli, Brussels sprouts, kohlrabi, kale and turnips.', 'Common symptoms of Alternaria diseases are dark brown to black circular leaf spots. As the lesions age, the center may fall out, giving them a shot hole appearance. Lesions can coalesce into larger necrotic areas followed by leaf drop and eventually death.', 'Apply fungicides', 'disease'),
(25, 1, 10, 'Anthracnose', 'Anthracnose_in_mangoes.jpg', 'It is the most common disease of mango, especially in regions that have high rainfall and heavy dews. It affects leaves, stems and floral panicle, but the fruit receive the most damage. ', 'It is caused by the fungus Collectrichum gloeosporioides', 'The fungus causes brown spots on leaves and black spots on fruit and flowers and makes the young branches brittle. ', ' The infestation can be reduced if dead material (branches, leaves and infested fruit) is\r\nremoved from the orchard. After harvest, anthracnose can be controlled if\r\nthe fruit is given a water bath for 3 to 5 minutes at 55° C', 'disease'),
(26, 1, 10, 'Powdery Mildew', 'Powdery_Mildew_in_mangoes.jpg', 'Powdery mildew is one of the most serious diseases of mango affecting almost all the varieties.It can damage young fruit and flowers. This fungus appears mostly in warm, humid weather (temperature of 22° C\r\nand relative humidity of 65 %)', 'It is caused by the fungus Oidium mangiferae and causes extremely high reductions in yield. The fungus attacks inflorescences, leaves, and young fruits.', 'The affected flowers and fruits drop pre-maturely reducing the crop load considerably or might even prevent the fruit set.', 'Alternate spraying of Wettable sulphur 0.2 per cent (2 g Sulfex/litre), Tridemorph O.1 per cent (1 ml Calixin/litre) and Bavistin @ 0.1 % at 15 days interval are recommended for effective control of the disease', 'disease'),
(27, 1, 10, 'Red rust', 'Red_rust.jpg', 'The disease can easily be recognized by the rusty red spots mainly on leaves and sometimes on petioles and bark of young twigs and is epiphytic in nature.', 'Red rust disease, caused by an alga, has been observed in mango growing areas', 'The disease is evident by the rusty red spots mainly on leaves and sometimes on petioles and bark of young twigs. . The spots are greenish grey in colour and velvety in texture. Later, they turn reddish brown. The circular and slightly elevated spots sometimes coalesce to form larger and irregular spots', 'Two to three sprays of Copper Oxychloride (0.3%) is effective in controlling the disease.', 'disease'),
(28, 1, 12, 'Bacterial Brown Spot', 'bacterial_brown_spot.jpg', 'It is a common disease that affects bean world wide', 'Caused by Pseudomonas syringae pv. syringae is the bacterium causing bacterial brown spot in legumes, most commonly seen in warm weather.', 'Brown circles outlined in yellow. These progress into streaks along leaf veins. The centers of lesions eventually fall out, causing the plant to look tattered. Pods show water-soaked circles that turn brown.\r\n\r\nThey might develop in a bent shape. Infected seeds may shrivel and discolor. In humid climates, cream or silver bacteria might ooze from infected plant parts.', ' Copper-based sprays can be effective against bacterial brown spot.\r\n\r\nFor Prevention, Plant disease-free seeds, rotate crop families, remove or incorporate plant stands after production, do not reuse irrigation water, avoid working in fields in wet conditions, and remove volunteer legume plants throughout the year.', 'disease'),
(29, 1, 11, 'Purple blotch', 'Purple_blotch.jpg', 'Purple blotch is a disease of onion and related crops caused by Alternaria porri, a fungus affecting tops and bulbs. The disease is prevalent throughout the onion growing regions. Yield losses attributed to Alernaria purple blotch are reported to approach 25%.', 'It is caused by Alternaria porri', 'Symptoms first appear as small tan spots on leaves. The lesions become sunken and rapidly expand rapidly up and down the leaf. Individual lesions 1/4\" - 3/4\" in diameter frequently are surrounded by a band of purple tissue. Numerous leaf lesions contribute to collapse of the entire top. Wounds occurring at or shortly before harvest provide sites for Alternaria infection of onion bulbs.', 'Crop rotation is important to prevent pathogen populations from building up to high levels. Rotations out of onions for 2-3 years are recommended. Most commercial onion crops must be protected from purple blotch by using repeated applications of protective fungicides.', 'disease'),
(30, 1, 9, 'Fire Blight', 'fire_blight.jpg', 'Named for the scorched appearance of infected leaves, fire blight is a destructive bacterial disease (Erwinia amylovora) found on apples, pears and other members of the rose family. The disease enters the tree at the tips of the branches and then travels down the stems causing dieback.', ' Bacterial disease (Erwinia amylovora)', 'Most infected leaves and branch tips wilt rapidly turn brown or black; the leaves die but do not drop off. Trees will also develop reddish water soaked lesions on the bark. On warm days, these lesions ooze an orange-brown liquid. Fire blight kills blossoms, shoots, limbs and sometimes, the entire tree.', 'Select resistant varieties whenever possible. Avoid heavy pruning or excess applications of nitrogen fertilizer, both of which encourage new growth. Avoid planting close to wild plants of hawthorn, apple or pear.\r\nAs soon as fire blight is discovered, prune off infected branches 1 foot below the diseased sections and burn them to prevent further infection. Dip pruning shears into a 10% alcohol or bleach solution between each cut to avoid transmitting the disease from one branch to another.\r\n\r\nEarly applications of liquid copper are effective against this plant problem. Mix 0.5 to 2.0 oz per gallon of water and apply at silver tip and bud break , repeat at 3 to 5 day intervals up to petal fall. Use the lower rate if disease pressure is light and the higher rate when conditions favor heavy disease pressure.', 'disease'),
(31, 1, 9, 'Cedar apple rust ', 'cedar_apple_rust.jpg', 'Cedar apple rust (Gymnosporangium juniperi-virginianae) is a fungal disease that requires juniper plants to complete its complicated two year life-cycle. Spores overwinter as a reddish-brown gall on young twigs of various juniper species.\r\nThe spores that develop on these trees will only infect junipers the following year. From year to year, the disease must pass from junipers to apples to junipers again; it cannot spread between apple trees.', 'Caused by a fungus called (Gymnosporangium juniperi-virginianae)', 'On apple and crab-apple trees, look for pale yellow pinhead sized spots on the upper surface of the leaves shortly after bloom.\r\nThese gradually enlarge to bright orange-yellow spots which make the disease easy to identify. \r\nOrange spots may develop on the fruit as well. \r\nHeavily infected leaves may drop prematurely', 'Choose resistant cultivars when available.\r\n\r\nRake up and dispose of fallen leaves and other debris from under trees.\r\n\r\nRemove galls from infected junipers. In some cases, juniper plants should be removed entirely.', 'disease'),
(32, 1, 11, 'Pink root', 'pink_root.jpg', 'The onions fail to grow as expected, and the roots show a pink colour in a plant removed from the soil.\r\n\r\nThe disease often occurs where onions are grown repeatedly in the same soil. Some soils are more prone to pink root than others. The best course of action is to plant resistant varieties.', 'Phoma terrestris - a fungus', 'Above ground, severely infected plants are small and stunted. Leaves start to die back. Symptoms above ground can simulate drought or nutrient deficiency symptoms. \r\n\r\nBelow ground, the characteristic symptom\r\nis pink colored roots that give the disease its name and make a diagnosis in the field easy. The roots later turn dark red or purple and start to dry up. Eventually the roots die. \r\n\r\nIt will spread to newly produced roots which leads to reduced bulb size because of the restricted root system. The continued infection of new roots prevents plants from reaching marketable size', 'The best management option is the use of resistant\r\nvarieties. There are resistant onion varieties available\r\nbut resistance levels can vary from field to field due to genetic variability of the pathogen. Talk to your local seed provider about varieties that may work in your area.\r\n\r\nMaintain healthy plants with optimum fertility, control of other diseases and insect pests, and avoid planting\r\nonion after onion. Even though crop rotation does not\r\nhave an effect on the disease, planting onion every\r\nfive years can keep disease incidence low.\r\n\r\nDisease incidence goes up with every onion crop and when\r\nonions are planted after cereal crops. ', 'disease'),
(33, 1, 11, 'Downy mildew', 'Onion_downy_mildew.jpg', 'Onion downy mildew is caused by the fungus-like (Oomycete) organism Peronospora destructor, which infects first the leaves and later bulbs of onions and shallots in mild, humid weather in late spring and summer. Some perennial onions such as chives may also be infected. It is worse in cool wet seasons and in wet areas.', 'Caused by the fungus-like (Oomycete) organism Peronospora destructor, ', 'The affected leaves turn yellow and die off from the tip downwards\r\n\r\nIn moist conditions a white, and later purplish mould develops on affected parts of the leaf\r\n\r\nThis, in turn, is commonly followed by darker mould growth of other leaf-infecting fungi\r\n\r\nBulbs can also be infected and often sprout prematurely or shrivel in store', 'Avoid overcrowded crops, damp conditions and sheltered sites and maintain good weed control to ensure airflow through the crop\r\n\r\nDo not compost infected material\r\n\r\nIt is recommended to remove and dispose of affected plants and avoid replanting with onion or shallots for five years\r\n\r\nIt is important not to allow any bulbs to remain in the soil from year to year. The fungus remains dormant in infected bulbs, producing spores in the spring which spread the disease to newly sown plants', 'disease'),
(34, 1, 4, 'Yam mosaic disease', 'Yam_mosaic.jpg', 'Yam mosaic virus is the most important virus of yams infecting all the edible species, including the Dioscorea cayenensis-rotundata complex, D. alata, D. esculenta and D. trifida. A common feature of the disease is the apparent recovery of some plants soon after infection when symptoms disappear.', 'Yam mosaic potyvirus', 'Light green streaks appear along the main leaf veins or between them. These streaks form different patterns: some are light green, feather-­like patches between the veins', 'When plants have leaves with these symptoms, it is important to remove the plants and destroy them. If aphids are present, put a rice bag over the plant before pulling it out, and then tip the plant into a fire to destroy it and the insects.', 'disease'),
(35, 1, 4, 'Yam Anthracnose disease ', 'yam_anthracnose.jpg', 'Anthracnose disease of yam has had a considerable\r\nimpact on yam production world-wide.\r\n\r\nThe disease causing agents not only reduce the\r\nquantity of yam produced, but also reduce the quality by\r\nmaking them unappealing to the consumers.\r\nYam is prone to infection right from the seedling stage\r\nthrough to harvesting and even after harvesting, in\r\nstorage. ', 'G. cingulata is the perfect state\r\nof C. gloeosporioides, the form that is usually found\r\ncausing field anthracnose disease. ', 'On susceptible yam cultivars, symptoms appeared atfirst as small dark brown or black lesion on the leaves,petioles and stems. The lesion is often surrounded by a chlorotic halo enlarged and coalesces, resulting in extensive necrosis of the leaves and die-back of the stem ', 'Use of crop rotation, fallowing and planting of\r\nhealthy materials and the destruction of infected crop\r\ncultivars', 'disease'),
(36, 1, 12, 'Bean rust', 'bean_rust.jpg', 'Bean rust is more important on dry beans and pole\r\nsnap beans, but it can also affect bush snap and\r\nlima beans.', 'Bean rust is caused by the fungus Uromyces\r\nappendiculatus.', 'Rust fungal structures or pustules\r\ntend to occur most numerously on leaf undersides,\r\nless abundantly on pods, and sparingly on stems.\r\nInfection is first evident as tiny, almost white,\r\nslightly raised pustules that later become the\r\ndistinct, reddish-brown, tiny circular “cushions”\r\ntypical of rust fungi', 'As a preventative measure against rust fungus, many bean growers will add lime sulphur to the soil around bean plants in early spring. \r\n\r\nSome other ways to prevent rust spots on bean plants are: \r\nProperly spacing plants to allow for air flow and prevent infected plant tissues from rubbing against other plants. \r\nWatering bean plants with a slow trickle directly at the root zone of the plant. \r\n\r\nSplashing water can spread fungal spores. \r\n\r\nKeeping garden clean of debris that can be a breeding ground for pests and disease.\r\n\r\n', 'disease'),
(37, 1, 8, 'Head rot', 'Headrot.jpg', 'A firm to slimy dark decay at the base of outer leaves and in cabbage heads develops during the period between head formation and maturity. The fungus grows up to main stem, passing between the leaf petioles. Foliage leaves die and drop off, thus exposing the stem beneath the head. Over the whole head surface, brown fungus mycelia and tiny brown resting fungal bodies (sclerotia) may develop and be visible over the head surface. Secondary rot bacteria usually invade the diseased tissue and turn the head into a slimy foul-smelling mass. ', 'Caused by a fungus called Sclerotia', ' Lesions, abnormal colours, abnormal forms, wilting and fungal growth. ', 'Good seedbed management, avoid fields with a history of the disease, practise crop rotation and deeply plough fields.', 'disease'),
(38, 1, 5, 'Root knot', 'root_knots.jpg', 'Root knots are caused by root-knot nematodes, which are microscopic worms that live in soil and feed on the roots of many common garden crops  The nematode gets its name because its feeding causes galls (swellings or “knots”) to form on the roots of infected plants', 'Nematodes', 'Affected plants become stunted and yellow and have a tendency to wilt in hot weather. The roots are severely distorted, swollen and bear knots or galls.', 'Remove plants and dig up the roots at the end of each growing season to remove the nematodes\' food source. Dispose of the plant matter. Till the soil after removing the plants to dry the soil and expose the nematodes to sunlight, which kills them.', 'disease'),
(39, 1, 1, 'Cassava Mosaic Disease', 'cassava_Mosaic.jpg', 'Cassava mosaic disease is the very severe and widespread in Africa. This leads to the production of no tubers or few depending on the severity of the disease and the age of plant at the time of infection', 'Mosaic virus', 'CMB infected crops produce several foliar symptoms including mottling, misshapen, mosaic and twisted leaflets and a reduction in the size of leaves and plants.', 'Remove disease plants showing symptoms of the disease and destroy them. This will help reduce spread of diseases on farms.\r\nUse resistant varieties and use healthy planting materials.', 'disease'),
(40, 1, 1, 'Cassava Brown steak Disease', 'cassava_brown_steak_disease.jpg', 'Cassava brown streak disease (CBSD) is\r\na devastating disease that causes loss of\r\ncassava root (tuber) production and quality.\r\nIt can render susceptible varieties unusable\r\nif cassava roots are left in the ground for\r\nover nine months. CBSD is now occurring in\r\nareas that were believed to be unsuitable\r\nfor the disease such as high altitude areas ', 'Virus', 'Symptoms of Cassava brown streak disease can be observed on leaves, stems and cassava roots \r\nSymptoms of cassava brown streak disease appear as patches of yellow areas mixed with normal green colour \r\n\r\nIt produces characteristic yellow or necrotic vein banding on leaves which may enlarge and join to form comparatively large yellow or necrotic patches.\r\n\r\nThe disease appears as dark brown “streaks” and “spots”  on stems, with dead spots on leaf scars.\r\nThese streaks are most prominent on upper, green portions of the stem.\r\n\r\n“Streaks” may appear as scratch-like wounds on stems.\r\nThe diseased plants may show shoot tip death, which may progress into cassava stem die-back.', 'Planting of clean (symptomless) cassava \r\nand ensuring Field hygiene.', 'disease'),
(41, 1, 12, 'Bean Anthracnose', 'bean_anthrancose.jpg', 'Anthracnose is one of the most invasive and destructive dry bean diseases. It can destroy up to 95% of your crop while also threatening the growth rate and seed and pod quality.', 'The disease is caused by the fungus Colletotrichum lindemuthianum, which is mainly seed-borne.', 'Initial symptoms are brick-red lesions that appear on the veins on the lower leaf surfaces. These then become darker and spread to the upper side of the leaves and stems.\r\n\r\nLesions also appear on the pods, starting as small reddish-brown to purple spots that grow to between 5mm and 8mm, becoming darker and sunken.\r\n\r\nIn severe infections, lesions occur on the seed itself. When the seed germinates, the hypocotyl (growth point) becomes infected and the seedling stem shows signs of infection.', 'Once anthracnose becomes a problem in a specific land, don’t plant beans on or near the land for two to three years.\r\nRestrict movement within an infected land and don’t cultivate or harvest a crop if it is wet\r\n\r\nRotate the bean crop with non-susceptible crops such as maize, sunflower and wheat;', 'disease'),
(43, 1, 12, 'Bacterial blight', 'Bacterial_blight.jpg', 'Bacterial blight is one of the severe diseases that attack beans. Depending on the cultivar and the plant growth stage during which infection occurs, bacterial blight can reduce yield by up to 92%, and emergence in infected seeds by 67%. Secondary infection can increase plant mortality to 81%.', 'Bacterial blight is caused by the bacterium Xanthomonas axonopodis pv. vignicola, which occurs in humid and moderate subtropical climates', 'Initial symptoms are tiny, water-soaked dots under the leaf. These vary from pinpoint size to more than 1,25cm in diameter, with a yellow halo. They often expand, join up and develop into large necrotic lesions.\r\n\r\nInfected seeds are discolored and shriveled. In a severe infestation, pod development is poor and most of the seeds are shriveled and unable to germinate', 'The disease can be managed in several ways: cultural practices, intercropping beans with maize or cassava, planting disease-free seed, and timely application of registered chemicals.', 'disease'),
(44, 1, 3, 'Fusarium ear rot', 'Fusarium_ear_rot.jpg', 'Fusarium ear rot is caused primarily by the Fusarium verticillioides fungus, which also causes stalk rot, root rot and seedling blight in maize.', 'caused by the Fusarium verticillioides fungus', 'Symptoms include scattered individual kernels or groups of kernels with whitish-pink fungal growth. These may also have a ‘starburst’ pattern of white streaks on the cap along the base of the kernel', 'To reduce the incidence of the disease, plant a tight-husked hybrid adapted to local conditions, control ear feeding insects, avoid high plant populations, and maintain adequate nitrogen and other essential growth nutrient levels. \r\n\r\nCarry out crop rotation and sub-soiling in compacted soils to minimise plant stress.', 'disease'),
(45, 1, 2, 'Rice blast', 'Rice_blast.jpg', 'Rice blast is generally considered the most important disease of rice worldwide because of its extensive distribution and destructiveness under favorable conditions.\r\n\r\nRice blast can affect most of the rice plant with the exception of the roots. The fungus can infect plants at any growth stage.', 'Rice blast caused by fungus Magnaporthe oryzae', 'The pathogen infects and produces lesions on the following parts of the rice plant: leaf (leaf blast), leaf collar (collar blast), culm, culm nodes, panicle neck node (neck rot) and panicle (panicle blast).', 'Use of registered fungicides, use of resistant varieties', 'disease'),
(46, 1, 10, 'Gummosis', 'gummosis.jpg', 'Gummosis is a nonspecific condition where sap leaks from a wound in the tree. It usually occurs when the tree has a perennial or bacterial canker, or is attacked by the peach tree borer.\r\n\r\n', 'Bacterial canker ', 'The disease is characterized by the presence of profuse oozing of gum on the surface of the affected wood, bark of the trunk and also on larger braches but more common on the cracked branches.\r\n\r\nIn severe cases, droplets of gum trickle down on stem, bark turn dark brown with longitudinal cracks, rots completely and the tree dries up because of cracking, rotting and girdling effects', 'Providing good drainage by amending the soil or transplanting is essential to its recovery. \r\nAnother step in gummosis treatment involves removing the diseased bark. \r\n\r\nIf you want to know how to treat gummosis, remove the darkened area of bark from the tree, plus a strip of the healthy bark until the wound is surrounded by a margin of healthy bark.\r\n\r\nSome fungicides can also be used.', 'disease'),
(47, 1, 6, 'Banana Freckle', 'banana_freckles.jpg', 'Banana freckle disease causes characteristic sandpapery feeling spots on leaves and fruit. Plant health, productivity, and fruit quality and appearance can be adversely affected.', 'Banana freckle is caused by the fungus Phyllosticta cavendishii. \r\n', 'Sandpapery feeling spots, predominantly on leaves and fruit.\r\n\r\nSpots can be very small to large (1–4 mm) and dark brown to black in colour.\r\n\r\nThe spots can run together to form streaks. Sometimes the centre of the larger spots are lighter in colour.\r\n\r\nSpots can also appear on the midrib of the leaf, bunch stalks and flower bracts.\r\n\r\nThe spots have a sandpaper feel when touched because the fungal structures protrude through the plant surface.', 'Repeated applications of fungicides such as Dithane M45, Captan, Ferbam, Mancozeb or thiophanate-methylbased treatments will help control infection levels and can prevent new infections in healthy plants, but it is difficult if not impossible to completely eradicate the fungus in an infected host plant.', 'disease'),
(48, 1, 6, 'Banana Bunchy top', 'bunchy_top.jpg', 'Banana bunchy top is a viral disease known for infecting banana plants and other crops. The disease, often called BBTD for banana bunchy top disease, gets its name from the bunchy appearance of infected plants. By that time, however, the  virus has most likely been spread to other plants by the banana aphid, Pentalonia nigronervosa.', 'Bunchy top is a viral disease caused by the Banana bunchy top virus (BBTV)', 'The first symptoms are dark green streaks on the lower portion of the leaf\'s midrib and later on the secondary veins. Removing the waxy white coating on the midrib makes it easier to see the streaking. \r\n\r\nSymptoms of an advanced infection include leaves becoming progressively dwarfed, upright and bunched at the top of the plant, with wavy and chlorotic margins that tend to turn necrotic. Cabbage top and curly top have also been used to term the disease', 'Control of banana bunchy top is achieved by killing the banana aphids then destroying all infected material. First, the aphids should be killed on the infected banana material, and then all the plant material should be destroyed to prevent the spread of the virus.', 'disease'),
(49, 1, 6, 'Banana bacterial Wilt', 'banana_wilt.jpg', 'The banana bacterial wilt (BBW) disease is spreading in some districts of Uganda. Farmers in the districts of Bushenyi, Mbarara, Isingiro, and Masaka which are leading producers of bananas in the country, are scared by the out-break. BBW disease had destroyed 90% of bananas in Uganda.\r\n\r\nThe transmission of the disease is through farm tools (pangas, hoes, knives, and de-leafers) used by farmers as well as traders, livestock, insects, birds and bats that feed on sap from injured banana plants.', 'Bacteria', 'Affected plants produce a variety of features, including leaves turning yellow and withering.\r\n\r\nIf the plant has bananas, it stops growing and ripens when still immature. Fruits develop brown stains when they are cut.\r\n\r\nYellow pus oozes from a cut stem and from the male bud after about 10 minutes.\r\n\r\nThe plants stop growing and finally fizzle away.', 'Destroy the sick plants by chopping them, then sun-dry them. Also, make sure you use clean suckers when planting. Disinfect your farm tools before using them.\r\n\r\nStop sharing farm tools. Hoes should not be used in the garden until the banana wilt disease is cleared.', 'disease');
INSERT INTO `plant_disease` (`id`, `plant_age`, `plant_id`, `name`, `image`, `description`, `cause`, `symptom`, `treatment`, `attack`) VALUES
(50, 1, 6, 'Fusarium wilt', 'panama_wilt.jpg', 'Fusarium wilt is a common vascular wilt fungal disease, exhibiting symptoms similar to Verticillium wilt. This disease has been investigated extensively since the early years of this century. ', 'The pathogen that causes Fusarium wilt is Fusarium oxysporum.', 'Yellow leaf syndrome, the yellowing of the border of the leaves which eventually leads to bending of the petiole. \r\n\r\nGreen leaf syndrome, which occurs in certain cultivars, marked by the persistence of the green color of the leaves followed by the bending of the petiole as in yellow leaf syndrome. \r\n\r\nInternally, the disease is characterized by a vascular discoloration. This begins in the roots and rhizomes with a yellowing that proceeds to a reddish-brown color in the pseudostem, as the pathogen blocks the plant\'s nutrient and water transport.', 'The most commonly used practices include mostly sanitation and quarantine practices to prevent the spread of Panama disease out of infected fields. However, the most effective tool against Panama disease is the development of banana plants resistant to Fusarium oxysporum', 'disease'),
(51, 1, 11, 'Onion Smut', 'onion_smut.jpg', 'Onion smut  affects the flag leaf (cotyledon) as it grows through the soil. Often the seedling survives this initial infection. A cool, wet spring increases the incidence of smut infection because the onion seedlings grow slowly and the flag leaf is in the soil for a longer period. Similarly, planting onion seeds too deep will also make them more likely to be infected.\r\nThe disease is spread when contaminated soil or set onions are transferred to smut-free areas.\r\n\r\n', 'Onion smut is caused by the soil-borne fungus Urocystis cepulae', 'Symptoms include black streaks and blisters appearing in the leaves and small bulbs later in the growing season as the fungus moves from the infected flag leaf to younger leaves. Some seedlings will be killed by the disease. ', 'Seed treatments can reduce losses to the disease and growing onions from transplants avoids the disease.', 'disease'),
(52, 1, 11, 'Botrytis Leaf Blight', 'Botrytis_Leaf_Blight.jpg', 'Botrytis leaf blight is a very common disease that is caused by the fungus Botrytis squamosa, which overwinters as sclerotia in soil, on onion debris and on bulbs in cull piles. This fungus disease usually develops after mid-June when temperatures and leaf wetness are favorable for infection', 'Caused by the fungus Botrytis squamosa', 'The first symptoms of leaf blight are greyish-white, oval-shaped spots, about 1 to 3 mm in length that appear on the leaves. These spots are often surrounded by a distinctive silvery-white \"halo\" with uneven margins. The centres of many spots become sunken and straw-colored and when numerous, the leaf tips begin to dieback, eventually affecting the entire leaf.', 'Plant spacings that permit better air movement and irrigation schedules that do not extend leaf wetness periods may be helpful.\r\nTo reduce the incidence and severity of Botrytis, remove cull piles and cull onions from field areas, rogue out volunteer onions and rotate crops.', 'disease'),
(53, 1, 11, 'White Rot', 'white_rot_in_onions.jpg', 'White rot, caused by the soil-borne fungus Sclerotium cepivorum, is a very destructive disease that begins in the field and can carry over into storage.', 'fungus - Sclerotium cepivorum', 'Yellowing and dieback of the leaf tips, followed by a collapse of the affected leaves.\r\n\r\nWhen the bulbs and roots are examined, a white, fluffy mold and soft rot will be observed. Masses of tiny black sclerotia can also be seen within this mold.\r\n\r\n', 'If your plants do contract white rot, dig up the crop as soon as you\'re aware of it, and dispose of every scrap of plant tissue by burning or in your household waste. Do not compost it. If the infection is not too severe, you may be able to use part of the infected crop.', 'disease'),
(54, 1, 8, 'Downy mildew', 'downymildew1.jpg', 'Downy mildew of brassicas is a disease of seedlings and also mature plants. The fungus penetrates the tissues under wet conditions and grows out to produce fuzzy whitish patches.', 'It is caused by a fungus-like (Oomycete) organism', 'Irregular yellow patches on leaves which turn light brown in color; fluffy gray growth on the undersides of the leaves', 'Remove all crop debris after harvest; rotate with non-brassicas; application of appropriate fungicides may be required if symptoms of disease are present.', 'disease'),
(55, 1, 8, 'Club rot ', 'clubrootdamage.jpg', 'Club rot can be difficult to distinguish from nematode damage; fungus can survive in soil for periods in excess of 10 years; can be spread by movement of contaminated soil and irrigation water to uninfected areas', 'Fungus - Plasmodiophora brassicae', 'Slow growing, stunted plants; yellowish leaves which wilt during day and rejuvenate in part at night; swollen, distorted roots; extensive gall formation', 'Once the pathogen is present in the soil it can survive for many years, elimination of the pathogen is economically unfeasible; rotating crops generally does not provide effective control.\r\nPlant only certified seed and avoid field grown transplants unless produced in a fumigated bed.\r\n\r\nApplying lime to the soil can reduce fungus sporulation', 'disease'),
(56, 1, 8, 'Bacterial soft rot', 'soft_rot.jpg', 'This is a common disease in Cabbage. Bacteria are easily spread on tools and by irrigation water; disease emergence favored by warm, moist conditions.', 'Bacterium', 'Water-soaked lesions on cabbage head which expand to form a large rotted mass of cream colored tissue which is liquid underneath; surface of lesions usually crack and exude slimy liquid which turns tan, dark brown or black on exposure to air.', 'Chemical treatments are not available for bacterial soft rot, control relies on cultural practices; rotate crops; plant cabbage in well-draining soils or raised beds; only harvest heads when they are dry; avoid damaging heads during harvest.', 'disease'),
(57, 1, 7, 'Coffee Wilt Disease', 'coffee_wilt.jpg', 'The fungus attacks the vascular system of the coffee plant, causing blockage of water and nutrient transportation from roots to other parts of the plant.\r\n\r\nIt causes wilting and eventual death of the affected plant.The disease was first reported in the Democratic Republic of Congo in the 1950s', 'CWD is caused by fusarium xylarioides fungus.', 'Small brown lesions appear on the young leaves still in the apical bud. \r\nThe lesions are usually marginal and the necrotic areas do not grow with the rest of the leaf, resulting in the uneven growth and leaf deformation. \r\nThe affected branches usually have shortened internodes.', 'The infected ones should be chopped, uprooted and the whole plant burnt.\r\n\r\nIntroduction of overhead shade and windbreakers reduces the incidence of crinkle leaf.\r\n', 'disease'),
(58, 1, 7, 'Coffee Leaf Rust', 'coffee_leaf_rust.jpg', 'This fungus is a worldwide problem for coffee producers have been battling it for generations. It has the power to cripple, or even wipe out, the country’s national cash crop. So the disease is most prevalent in Arabica grown in the warm, humid conditions of low altitudes. \r\n\r\nPlants affected by coffee leaf rust are unable to ripen fully and if they do fruit, will produce light beans that taste astringent. ', 'It is caused by Fungus', 'The disease presents as an orange rust-like dust on the underside of the coffee leaves. It is a cyclical condition that causes defoliation, just like coffee leaf miners. Wind and rain spread coffee leaf rust spores, which thrive at around 70°F/21°C', 'The combined or alternate use of copper‐based and systemic fungicides is advised for the most effective treatment. Copper-based fungicides are usually used when there is a small amount of infection and systemic fungicides are used for large outbreaks.', 'disease'),
(59, 1, 9, 'Black Rot', 'Apple_blackrot.jpg', 'Black rot is a disease of apples that infects fruit, leaves and bark. It can also jump to healthy tissue on pear or quince trees, but is typically a secondary fungus of weak or dead tissues in other plants.\r\n\r\nSpores can overwinter in twigs or fruit remaining on the tree and spread during rainfall.', 'Caused by the fungus Botryosphaeria obtusa.', 'Purple flecks or circular lesions which are brown in the centre and purple at margin; red flecks, purple lesions and/or brown black rings on fruit.', 'Remove dead wood, mummified fruit and cankers from trees to reduce spread of disease\r\n\r\nBurn any prunings that have been made from the tree\r\n\r\nDisease can be controlled by applying fungicides from silver tip to harvest.', 'disease'),
(60, 1, 9, 'Flyspeck', 'flyspeck.png', 'Flyspeck is a disease of maturing apples, caused by the fungus Zygophiala jamaicensis. Spores germinate when temperatures are between 60 and 83 degrees Fahrenheit (15-28 C.)\r\n', 'Caused by the fungus Zygophiala jamaicensis (also known as Schizothyrium pomi)', 'Shiny black fungal fruiting bodies appear as dots arranged in irregular to circular pattern on fruit surface.', 'Prune trees to open canopy and promote drying of fruit surface\r\n\r\nFungicides may be applied as a preventative measure', 'disease'),
(61, 1, 3, 'Fungal ', 'fungal_disease.jpg', 'Southern Corn Leaf Blight is a devastating fungal disease that attacks your corn crop and spreads quickly. You will first notice the blight on the leaves of your corn plant. The disease shows up as light brown elongated spots with a darker brown border. If left untreated, this leaf blight will spread through your entire corn crop in as little as ten days.', 'Fungus', 'The disease shows up as light brown elongated spots with a darker brown border. If left untreated, this leaf blight will spread through your entire corn crop in as little as ten days.', 'Unfortunately, it is difficult to treat a fungal corn disease organically. Most casual gardeners are unlikely to be able to identify most fungal diseases until the crop is already beyond help. Additionally, fungi spread very rapidly, so even if you recognize the problem, it may be too late to save the plants.\r\n\r\nPrevention is a far better way to manage a fungal disease in your corn. Since spores can overwinter in corn debris and live in the soil for years, it is crucial that you remove corn debris as soon as the harvest is over.\r\n\r\nYou should also practice rigorous crop rotation, planting your corn in a different area every season. You may also consider planting hybrid corn varieties, as they seem to have some resistance to leaf blight and other diseases.', 'disease'),
(62, 1, 1, 'Cassava Bud Necrosis', 'bud necrosis.jpg', 'Bud necrosis is a fungal disease usually found in cassava growing areas with humid environments. Incidence of the disease is therefore higher in the humid forest zones compared to the drier savanna areas. Observations indicate that poor farm sanitation (weedy fields) in high relative humidity zones promotes high incidence and severity of the disease.', 'Fungus', 'The main symptom of the disease is the appearance of dark or grey patches or necrotic lesions on stem surfaces of susceptible cultivars. The necrotic areas are made up of fungal tissue of the causal organism. Necrotic areas often cover buds on the stem giving the disease its name.', 'Farmers must use cuttings derived from healthy stems completely free from necrotic lesions.\r\n 	\r\nIncidence and severity of bud necrosis is significantly reduced on farms when good planting distances that allow free movement of air around plants are maintained. The disease is better controlled when weeds are also well managed.  These good practices contribute effectively to disease control when healthy planting materials are always used to start farms.\r\n \r\nPlant debris, especially stem pieces bearing necrotic lesions. must be destroyed by burning immediately after harvest to reduce sources of infective fungal spores.', 'disease'),
(63, 1, 1, 'Brown and white leaf spot', 'white & brown leafspot.jpg', 'Brown and white leaf spots are relatively minor diseases of cassava caused by fungi. White leaf spot is less frequently seen in most cassava growing areas.', 'Fungi', 'The key symptom of brown leaf spot disease is the appearance of few to several brown spots on the upper surface of leaves of susceptible varieties. Margins of brown spots are irregular. The middle of brown spots may break given rise to ‘shot holes’.\r\n \r\nWhite leaf spot disease is characterized by the presence of white spots on the upper surface of leaves of infected plants. Symptoms of both diseases may be found on the same leaf ', 'Several farmers regard symptoms of brown leaf spot disease as signs of crop maturity because the spots according to these farmers show up when plants are fully grown. Very little or nothing is therefore done to control the disease.\r\n\r\nGood weed control practices can, therefore, reduce the spread of leaf spot diseases.', 'disease');

-- --------------------------------------------------------

--
-- Table structure for table `workers`
--

CREATE TABLE `workers` (
  `id` int(11) NOT NULL,
  `name` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `location` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `speciality` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `latitude` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `type` enum('agronomists','extension_workers') COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `workers`
--

INSERT INTO `workers` (`id`, `name`, `location`, `speciality`, `description`, `latitude`, `longitude`, `type`, `created_at`) VALUES
(1, 'John Katamba', 'Kasangati', 'Beans Specialist', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s', '0.4391006', '32.6007646', 'agronomists', '2021-10-07 00:08:58'),
(2, 'Kihunde Kizza', 'Wandegeya', 'All Crops Specialist', 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum', '0.333782', '32.569809', 'agronomists', '2021-10-07 00:08:58');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `farmers`
--
ALTER TABLE `farmers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phone_number` (`phone_number`);

--
-- Indexes for table `farmers_plants`
--
ALTER TABLE `farmers_plants`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `farms`
--
ALTER TABLE `farms`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `forums`
--
ALTER TABLE `forums`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `markets`
--
ALTER TABLE `markets`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `market_prices`
--
ALTER TABLE `market_prices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `plants`
--
ALTER TABLE `plants`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `plant_age`
--
ALTER TABLE `plant_age`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `plant_disease`
--
ALTER TABLE `plant_disease`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_plant_disease` (`plant_id`);

--
-- Indexes for table `workers`
--
ALTER TABLE `workers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `farmers`
--
ALTER TABLE `farmers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `farmers_plants`
--
ALTER TABLE `farmers_plants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `farms`
--
ALTER TABLE `farms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `forums`
--
ALTER TABLE `forums`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `markets`
--
ALTER TABLE `markets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `market_prices`
--
ALTER TABLE `market_prices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `plants`
--
ALTER TABLE `plants`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `plant_age`
--
ALTER TABLE `plant_age`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `plant_disease`
--
ALTER TABLE `plant_disease`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `workers`
--
ALTER TABLE `workers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `plant_disease`
--
ALTER TABLE `plant_disease`
  ADD CONSTRAINT `plant_disease_ibfk_1` FOREIGN KEY (`plant_id`) REFERENCES `plants` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
