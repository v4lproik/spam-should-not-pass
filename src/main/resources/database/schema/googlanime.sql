# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 192.168.59.103 (MySQL 5.7.7-rc)
# Database: googlanime
# Generation Time: 2015-07-26 15:37:11 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# Dump of table Anime
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Anime`;

CREATE TABLE `Anime` (
  `id` int(11) NOT NULL,
  `type` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `title` varchar(200) DEFAULT '',
  `englishTitle` varchar(200) DEFAULT '',
  `japaneseTitle` varchar(200) DEFAULT '',
  `synopsis` text,
  `startedAiringDate` date DEFAULT NULL,
  `finishedAiringDate` date DEFAULT NULL,
  `rank` varchar(50) CHARACTER SET utf8 DEFAULT '',
  `popularity` varchar(50) CHARACTER SET utf8 DEFAULT '',
  `score` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `ageRating` varchar(50) CHARACTER SET utf8 DEFAULT '',
  `episodeCount` int(11) DEFAULT NULL,
  `episodeLength` varchar(50) CHARACTER SET utf8 DEFAULT '',
  `showType` varchar(50) CHARACTER SET utf8 DEFAULT '',
  `posterImage` varchar(300) CHARACTER SET utf8 DEFAULT '',
  `parent` int(11) DEFAULT NULL,
  `nbVolumes` int(11) DEFAULT NULL,
  `nbChapters` int(11) DEFAULT NULL,
  `serialization` varchar(200) DEFAULT NULL,
  `type_jpa` varchar(5) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# Dump of table Adaptations
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Adaptations`;

CREATE TABLE `Adaptations` (
  `idAnime` int(11) NOT NULL,
  `idAdaptation` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idAdaptation`),
  KEY `idAdaptation` (`idAdaptation`),
  CONSTRAINT `Adaptations_ibfk_2` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Adaptations_ibfk_4` FOREIGN KEY (`idAdaptation`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table Alternatives
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Alternatives`;

CREATE TABLE `Alternatives` (
  `idAnime` int(11) NOT NULL,
  `idAlternative` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idAlternative`),
  KEY `idAlternative` (`idAlternative`),
  CONSTRAINT `Alternatives_ibfk_3` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Alternatives_ibfk_4` FOREIGN KEY (`idAlternative`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




# Dump of table Author
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Author`;

CREATE TABLE `Author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `lastName` varchar(50) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `biography` varchar(500) CHARACTER SET latin1 DEFAULT '',
  `bloodType` varchar(3) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table CharacterT
# ------------------------------------------------------------

DROP TABLE IF EXISTS `CharacterT`;

CREATE TABLE `CharacterT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `japaneseName` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Episode
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Episode`;

CREATE TABLE `Episode` (
  `id` int(11) NOT NULL,
  `title` varchar(200) CHARACTER SET latin1 NOT NULL,
  `date` date NOT NULL,
  `idAnime` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAnime` (`idAnime`),
  CONSTRAINT `Episode_ibfk_1` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table CharacterNicknames
# ------------------------------------------------------------

DROP TABLE IF EXISTS `CharacterNicknames`;

CREATE TABLE `CharacterNicknames` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(200) NOT NULL DEFAULT '',
  `idCharacter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idCharacter` (`idCharacter`),
  CONSTRAINT `CharacterNicknames_ibfk_1` FOREIGN KEY (`idCharacter`) REFERENCES `CharacterT` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table Genre
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Genre`;

CREATE TABLE `Genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Others
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Others`;

CREATE TABLE `Others` (
  `idAnime` int(11) NOT NULL,
  `idOther` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idOther`),
  KEY `idOther` (`idOther`),
  CONSTRAINT `Others_ibfk_3` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Others_ibfk_4` FOREIGN KEY (`idOther`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Prequels
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Prequels`;

CREATE TABLE `Prequels` (
  `idAnime` int(11) NOT NULL,
  `idPrequel` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idPrequel`),
  KEY `idPrequel` (`idPrequel`),
  CONSTRAINT `Prequels_ibfk_3` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Prequels_ibfk_4` FOREIGN KEY (`idPrequel`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Producer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Producer`;

CREATE TABLE `Producer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Sequels
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Sequels`;

CREATE TABLE `Sequels` (
  `idAnime` int(11) NOT NULL,
  `idSequel` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idSequel`),
  KEY `idSequel` (`idSequel`),
  CONSTRAINT `Sequels_ibfk_3` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Sequels_ibfk_4` FOREIGN KEY (`idSequel`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table SideStories
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SideStories`;

CREATE TABLE `SideStories` (
  `idAnime` int(11) NOT NULL,
  `idSideStory` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idSideStory`),
  KEY `idSideStory` (`idSideStory`),
  CONSTRAINT `SideStories_ibfk_3` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SideStories_ibfk_4` FOREIGN KEY (`idSideStory`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table SpinOffs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SpinOffs`;

CREATE TABLE `SpinOffs` (
  `idAnime` int(11) NOT NULL,
  `idSpinOff` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idSpinOff`),
  KEY `idSpinOff` (`idSpinOff`),
  CONSTRAINT `SpinOffs_ibfk_3` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SpinOffs_ibfk_4` FOREIGN KEY (`idSpinOff`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Summaries
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Summaries`;

CREATE TABLE `Summaries` (
  `idAnime` int(11) NOT NULL,
  `idSummary` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idSummary`),
  KEY `idSummary` (`idSummary`),
  CONSTRAINT `Summaries_ibfk_3` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Summaries_ibfk_4` FOREIGN KEY (`idSummary`) REFERENCES `Anime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Synonym
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Synonym`;

CREATE TABLE `Synonym` (
  `title` varchar(200) CHARACTER SET latin1 NOT NULL,
  `idAnime` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `idAnime` (`idAnime`),
  CONSTRAINT `Synonym_ibfk_1` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Tag
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Tag`;

CREATE TABLE `Tag` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET latin1 NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Anime_has_Author
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Anime_has_Author`;

CREATE TABLE `Anime_has_Author` (
  `idAnime` int(11) NOT NULL,
  `idAuthor` int(11) NOT NULL,
  `job` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idAnime`,`idAuthor`),
  KEY `idAuthor` (`idAuthor`),
  CONSTRAINT `Anime_has_Author_ibfk_2` FOREIGN KEY (`idAuthor`) REFERENCES `Author` (`id`),
  CONSTRAINT `Anime_has_Author_ibfk_4` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table Anime_has_Character
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Anime_has_Character`;

CREATE TABLE `Anime_has_Character` (
  `idAnime` int(11) NOT NULL,
  `idCharacter` int(11) NOT NULL,
  `role` varchar(50) CHARACTER SET latin1 NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`idAnime`,`idCharacter`),
  KEY `idCharacter` (`idCharacter`),
  CONSTRAINT `Anime_has_Character_ibfk_2` FOREIGN KEY (`idCharacter`) REFERENCES `CharacterT` (`id`),
  CONSTRAINT `Anime_has_Character_ibfk_4` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Anime_has_Genre
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Anime_has_Genre`;

CREATE TABLE `Anime_has_Genre` (
  `idAnime` int(11) NOT NULL,
  `idGenre` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idGenre`),
  KEY `idGenre` (`idGenre`),
  CONSTRAINT `Anime_has_Genre_ibfk_3` FOREIGN KEY (`idGenre`) REFERENCES `Genre` (`id`),
  CONSTRAINT `Anime_has_Genre_ibfk_5` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Anime_has_Producer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Anime_has_Producer`;

CREATE TABLE `Anime_has_Producer` (
  `idAnime` int(11) NOT NULL,
  `idProducer` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idProducer`),
  KEY `idProducer` (`idProducer`),
  CONSTRAINT `Anime_has_Producer_ibfk_2` FOREIGN KEY (`idProducer`) REFERENCES `Producer` (`id`),
  CONSTRAINT `Anime_has_Producer_ibfk_4` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Anime_has_Tag
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Anime_has_Tag`;

CREATE TABLE `Anime_has_Tag` (
  `idAnime` int(11) NOT NULL,
  `idTag` int(11) NOT NULL,
  PRIMARY KEY (`idAnime`,`idTag`),
  KEY `idTag` (`idTag`),
  CONSTRAINT `Anime_has_Tag_ibfk_2` FOREIGN KEY (`idTag`) REFERENCES `Tag` (`id`),
  CONSTRAINT `Anime_has_Tag_ibfk_4` FOREIGN KEY (`idAnime`) REFERENCES `Anime` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
