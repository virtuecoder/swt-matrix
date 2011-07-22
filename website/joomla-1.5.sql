-- MySQL dump 10.11
--
-- Host: localhost    Database: netanel15
-- ------------------------------------------------------
-- Server version	5.0.51a-community-nt

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
-- Table structure for table `jos_advancedmodules`
--

DROP TABLE IF EXISTS `jos_advancedmodules`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_advancedmodules` (
  `moduleid` int(11) NOT NULL default '0',
  `params` text NOT NULL,
  PRIMARY KEY  (`moduleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_advancedmodules`
--

LOCK TABLES `jos_advancedmodules` WRITE;
/*!40000 ALTER TABLE `jos_advancedmodules` DISABLE KEYS */;
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (16,'hideempty=1\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_selection=1|3|4|5|6|2\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=2\nassignto_articles_selection=1\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=2\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (17,'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=1\nassignto_menuitems_inc_children=0\nassignto_menuitems_selection=4|7|10\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=1\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_secscats_selection=1:1\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (1,'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (18,'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (19,'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (20,'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (21,'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES (22,'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
/*!40000 ALTER TABLE `jos_advancedmodules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_banner`
--

DROP TABLE IF EXISTS `jos_banner`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_banner` (
  `bid` int(11) NOT NULL auto_increment,
  `cid` int(11) NOT NULL default '0',
  `type` varchar(30) NOT NULL default 'banner',
  `name` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `imptotal` int(11) NOT NULL default '0',
  `impmade` int(11) NOT NULL default '0',
  `clicks` int(11) NOT NULL default '0',
  `imageurl` varchar(100) NOT NULL default '',
  `clickurl` varchar(200) NOT NULL default '',
  `date` datetime default NULL,
  `showBanner` tinyint(1) NOT NULL default '0',
  `checked_out` tinyint(1) NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `editor` varchar(50) default NULL,
  `custombannercode` text,
  `catid` int(10) unsigned NOT NULL default '0',
  `description` text NOT NULL,
  `sticky` tinyint(1) unsigned NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  `tags` text NOT NULL,
  `params` text NOT NULL,
  PRIMARY KEY  (`bid`),
  KEY `viewbanner` (`showBanner`),
  KEY `idx_banner_catid` (`catid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_banner`
--

LOCK TABLES `jos_banner` WRITE;
/*!40000 ALTER TABLE `jos_banner` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_bannerclient`
--

DROP TABLE IF EXISTS `jos_bannerclient`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_bannerclient` (
  `cid` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL default '',
  `contact` varchar(255) NOT NULL default '',
  `email` varchar(255) NOT NULL default '',
  `extrainfo` text NOT NULL,
  `checked_out` tinyint(1) NOT NULL default '0',
  `checked_out_time` time default NULL,
  `editor` varchar(50) default NULL,
  PRIMARY KEY  (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_bannerclient`
--

LOCK TABLES `jos_bannerclient` WRITE;
/*!40000 ALTER TABLE `jos_bannerclient` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_bannerclient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_bannertrack`
--

DROP TABLE IF EXISTS `jos_bannertrack`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_bannertrack` (
  `track_date` date NOT NULL,
  `track_type` int(10) unsigned NOT NULL,
  `banner_id` int(10) unsigned NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_bannertrack`
--

LOCK TABLES `jos_bannertrack` WRITE;
/*!40000 ALTER TABLE `jos_bannertrack` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_bannertrack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_categories`
--

DROP TABLE IF EXISTS `jos_categories`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_categories` (
  `id` int(11) NOT NULL auto_increment,
  `parent_id` int(11) NOT NULL default '0',
  `title` varchar(255) NOT NULL default '',
  `name` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `image` varchar(255) NOT NULL default '',
  `section` varchar(50) NOT NULL default '',
  `image_position` varchar(30) NOT NULL default '',
  `description` text NOT NULL,
  `published` tinyint(1) NOT NULL default '0',
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `editor` varchar(50) default NULL,
  `ordering` int(11) NOT NULL default '0',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `count` int(11) NOT NULL default '0',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `cat_idx` (`section`,`published`,`access`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_categories`
--

LOCK TABLES `jos_categories` WRITE;
/*!40000 ALTER TABLE `jos_categories` DISABLE KEYS */;
INSERT INTO `jos_categories` (`id`, `parent_id`, `title`, `name`, `alias`, `image`, `section`, `image_position`, `description`, `published`, `checked_out`, `checked_out_time`, `editor`, `ordering`, `access`, `count`, `params`) VALUES (1,0,'SWT Matrix','','swt-matrix','','1','left','<p>SWT Matrix i a tabular SWT widget with unlimited capacity and instant rendering.</p>',1,0,'0000-00-00 00:00:00',NULL,1,0,0,'');
/*!40000 ALTER TABLE `jos_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_components`
--

DROP TABLE IF EXISTS `jos_components`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_components` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL default '',
  `link` varchar(255) NOT NULL default '',
  `menuid` int(11) unsigned NOT NULL default '0',
  `parent` int(11) unsigned NOT NULL default '0',
  `admin_menu_link` varchar(255) NOT NULL default '',
  `admin_menu_alt` varchar(255) NOT NULL default '',
  `option` varchar(50) NOT NULL default '',
  `ordering` int(11) NOT NULL default '0',
  `admin_menu_img` varchar(255) NOT NULL default '',
  `iscore` tinyint(4) NOT NULL default '0',
  `params` text NOT NULL,
  `enabled` tinyint(4) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `parent_option` (`parent`,`option`(32))
) ENGINE=MyISAM AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_components`
--

LOCK TABLES `jos_components` WRITE;
/*!40000 ALTER TABLE `jos_components` DISABLE KEYS */;
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (1,'Banners','',0,0,'','Banner Management','com_banners',0,'js/ThemeOffice/component.png',0,'track_impressions=0\ntrack_clicks=0\ntag_prefix=\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (2,'Banners','',0,1,'option=com_banners','Active Banners','com_banners',1,'js/ThemeOffice/edit.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (3,'Clients','',0,1,'option=com_banners&c=client','Manage Clients','com_banners',2,'js/ThemeOffice/categories.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (4,'Web Links','option=com_weblinks',0,0,'','Manage Weblinks','com_weblinks',0,'js/ThemeOffice/component.png',0,'show_comp_description=1\ncomp_description=\nshow_link_hits=1\nshow_link_description=1\nshow_other_cats=1\nshow_headings=1\nshow_page_title=1\nlink_target=0\nlink_icons=\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (5,'Links','',0,4,'option=com_weblinks','View existing weblinks','com_weblinks',1,'js/ThemeOffice/edit.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (6,'Categories','',0,4,'option=com_categories&section=com_weblinks','Manage weblink categories','',2,'js/ThemeOffice/categories.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (7,'Contacts','option=com_contact',0,0,'','Edit contact details','com_contact',0,'js/ThemeOffice/component.png',1,'contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (8,'Contacts','',0,7,'option=com_contact','Edit contact details','com_contact',0,'js/ThemeOffice/edit.png',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (9,'Categories','',0,7,'option=com_categories&section=com_contact_details','Manage contact categories','',2,'js/ThemeOffice/categories.png',1,'contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (10,'Polls','option=com_poll',0,0,'option=com_poll','Manage Polls','com_poll',0,'js/ThemeOffice/component.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (11,'News Feeds','option=com_newsfeeds',0,0,'','News Feeds Management','com_newsfeeds',0,'js/ThemeOffice/component.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (12,'Feeds','',0,11,'option=com_newsfeeds','Manage News Feeds','com_newsfeeds',1,'js/ThemeOffice/edit.png',0,'show_headings=1\nshow_name=1\nshow_articles=1\nshow_link=1\nshow_cat_description=1\nshow_cat_items=1\nshow_feed_image=1\nshow_feed_description=1\nshow_item_description=1\nfeed_word_count=0\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (13,'Categories','',0,11,'option=com_categories&section=com_newsfeeds','Manage Categories','',2,'js/ThemeOffice/categories.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (14,'User','option=com_user',0,0,'','','com_user',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (15,'Search','option=com_search',0,0,'option=com_search','Search Statistics','com_search',0,'js/ThemeOffice/component.png',1,'enabled=0\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (16,'Categories','',0,1,'option=com_categories&section=com_banner','Categories','',3,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (17,'Wrapper','option=com_wrapper',0,0,'','Wrapper','com_wrapper',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (18,'Mail To','',0,0,'','','com_mailto',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (19,'Media Manager','',0,0,'option=com_media','Media Manager','com_media',0,'',1,'upload_extensions=bmp,csv,doc,epg,gif,ico,jpg,odg,odp,ods,odt,pdf,png,ppt,swf,txt,xcf,xls,BMP,CSV,DOC,EPG,GIF,ICO,JPG,ODG,ODP,ODS,ODT,PDF,PNG,PPT,SWF,TXT,XCF,XLS\nupload_maxsize=10000000\nfile_path=images\nimage_path=images/stories\nrestrict_uploads=1\nallowed_media_usergroup=3\ncheck_mime=1\nimage_extensions=bmp,gif,jpg,png\nignore_extensions=\nupload_mime=image/jpeg,image/gif,image/png,image/bmp,application/x-shockwave-flash,application/msword,application/excel,application/pdf,application/powerpoint,text/plain,application/x-zip\nupload_mime_illegal=text/html\nenable_flash=0\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (20,'Articles','option=com_content',0,0,'','','com_content',0,'',1,'show_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=0\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=0\nshow_create_date=0\nshow_modify_date=0\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=0\nshow_print_icon=1\nshow_email_icon=0\nshow_hits=1\nfeed_summary=0\nfilter_tags=\nfilter_attritbutes=\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (21,'Configuration Manager','',0,0,'','Configuration','com_config',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (22,'Installation Manager','',0,0,'','Installer','com_installer',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (23,'Language Manager','',0,0,'','Languages','com_languages',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (24,'Mass mail','',0,0,'','Mass Mail','com_massmail',0,'',1,'mailSubjectPrefix=\nmailBodySuffix=\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (25,'Menu Editor','',0,0,'','Menu Editor','com_menus',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (27,'Messaging','',0,0,'','Messages','com_messages',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (28,'Modules Manager','',0,0,'','Modules','com_modules',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (29,'Plugin Manager','',0,0,'','Plugins','com_plugins',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (30,'Template Manager','',0,0,'','Templates','com_templates',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (31,'User Manager','',0,0,'','Users','com_users',0,'',1,'allowUserRegistration=1\nnew_usertype=Registered\nuseractivation=1\nfrontend_userparams=1\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (32,'Cache Manager','',0,0,'','Cache','com_cache',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (33,'Control Panel','',0,0,'','Control Panel','com_cpanel',0,'',1,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (34,'Huru Helpdesk','option=com_huruhelpdesk',0,0,'option=com_huruhelpdesk','Huru Helpdesk','com_huruhelpdesk',0,'js/ThemeOffice/component.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (35,'ARTIO JoomSEF','option=com_sef',0,0,'option=com_sef','ARTIO JoomSEF','com_sef',0,'components/com_sef/assets/images/icon.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (36,'Control Panel','',0,35,'option=com_sef','Control Panel','com_sef',0,'components/com_sef/assets/images/icon-16-sefcpanel.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (37,'Configuration','',0,35,'option=com_sef&controller=config&task=edit','Configuration','com_sef',1,'components/com_sef/assets/images/icon-16-sefconfig.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (38,'Manage Extensions','',0,35,'option=com_sef&controller=extension','Manage Extensions','com_sef',2,'components/com_sef/assets/images/icon-16-sefplugin.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (39,'Edit .htaccess','',0,35,'option=com_sef&controller=htaccess','Edit .htaccess','com_sef',3,'components/com_sef/assets/images/icon-16-edit.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (40,'','',0,35,'option=com_sef','','com_sef',4,'components/com_sef/assets/images/icon-10-blank.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (41,'Manage SEF URLs','',0,35,'option=com_sef&controller=sefurls&viewmode=3','Manage SEF URLs','com_sef',5,'components/com_sef/assets/images/icon-16-url-edit.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (42,'Manage Meta Tags','',0,35,'option=com_sef&controller=metatags','Manage Meta Tags','com_sef',6,'components/com_sef/assets/images/icon-16-manage-tags.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (43,'SiteMap','',0,35,'option=com_sef&controller=sitemap','SiteMap','com_sef',7,'components/com_sef/assets/images/icon-16-manage-sitemap.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (44,'Manage 301 Redirects','',0,35,'option=com_sef&controller=movedurls','Manage 301 Redirects','com_sef',8,'components/com_sef/assets/images/icon-16-301-redirects.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (45,'','',0,35,'option=com_sef','','com_sef',9,'components/com_sef/assets/images/icon-10-blank.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (46,'Upgrade','',0,35,'option=com_sef&task=showUpgrade','Upgrade','com_sef',10,'components/com_sef/assets/images/icon-16-update.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (47,'Support','',0,35,'option=com_sef&controller=info&task=help','Support','com_sef',11,'components/com_sef/assets/images/icon-16-help.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (49,'Advanced Module Manager','',0,0,'','Advanced Module Manager','com_advancedmodules',0,'',0,'\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (54,'Kunena Forum','option=com_kunena',0,0,'option=com_kunena','Kunena Forum','com_kunena',0,'components/com_kunena/images/kunenafavicon.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (61,'Info','',0,55,'option=com_phocadocumentation&view=phocadocumentationin','Info','com_phocadocumentation',2,'components/com_phocadocumentation/assets/images/icon-16-pdoc-info.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (60,'Documentation','',0,55,'option=com_phocadocumentation&view=phocadocumentations','Documentation','com_phocadocumentation',1,'components/com_phocadocumentation/assets/images/icon-16-pdoc-doc.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (59,'Control Panel','',0,55,'option=com_phocadocumentation','Control Panel','com_phocadocumentation',0,'components/com_phocadocumentation/assets/images/icon-16-pdoc-control-panel.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (55,'Phoca Documentation','option=com_phocadocumentation',0,0,'option=com_phocadocumentation','Phoca Documentation','com_phocadocumentation',0,'components/com_phocadocumentation/assets/images/icon-16-pdoc-menu.png',0,'most_viewed_docs_num=5\ndisplay_sections=1\ndisplay_up_icon=1\ndisplay_num_doc_secs=1\ndisplay_num_doc_secs_header=1\narticle_itemid=\n\n',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (62,'JCE','option=com_jce',0,0,'option=com_jce','JCE','com_jce',0,'components/com_jce/media/img/menu/logo.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (63,'WF_MENU_CPANEL','',0,62,'option=com_jce','WF_MENU_CPANEL','com_jce',0,'components/com_jce/media/img/menu/jce-cpanel.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (64,'WF_MENU_CONFIG','',0,62,'option=com_jce&view=config','WF_MENU_CONFIG','com_jce',1,'components/com_jce/media/img/menu/jce-config.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (65,'WF_MENU_PROFILES','',0,62,'option=com_jce&view=profiles','WF_MENU_PROFILES','com_jce',2,'components/com_jce/media/img/menu/jce-profiles.png',0,'',1);
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (66,'WF_MENU_INSTALL','',0,62,'option=com_jce&view=installer','WF_MENU_INSTALL','com_jce',3,'components/com_jce/media/img/menu/jce-install.png',0,'',1);
/*!40000 ALTER TABLE `jos_components` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_contact_details`
--

DROP TABLE IF EXISTS `jos_contact_details`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_contact_details` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `con_position` varchar(255) default NULL,
  `address` text,
  `suburb` varchar(100) default NULL,
  `state` varchar(100) default NULL,
  `country` varchar(100) default NULL,
  `postcode` varchar(100) default NULL,
  `telephone` varchar(255) default NULL,
  `fax` varchar(255) default NULL,
  `misc` mediumtext,
  `image` varchar(255) default NULL,
  `imagepos` varchar(20) default NULL,
  `email_to` varchar(255) default NULL,
  `default_con` tinyint(1) unsigned NOT NULL default '0',
  `published` tinyint(1) unsigned NOT NULL default '0',
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL default '0',
  `params` text NOT NULL,
  `user_id` int(11) NOT NULL default '0',
  `catid` int(11) NOT NULL default '0',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `mobile` varchar(255) NOT NULL default '',
  `webpage` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`),
  KEY `catid` (`catid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_contact_details`
--

LOCK TABLES `jos_contact_details` WRITE;
/*!40000 ALTER TABLE `jos_contact_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_contact_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_content`
--

DROP TABLE IF EXISTS `jos_content`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_content` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `title` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `title_alias` varchar(255) NOT NULL default '',
  `introtext` mediumtext NOT NULL,
  `fulltext` mediumtext NOT NULL,
  `state` tinyint(3) NOT NULL default '0',
  `sectionid` int(11) unsigned NOT NULL default '0',
  `mask` int(11) unsigned NOT NULL default '0',
  `catid` int(11) unsigned NOT NULL default '0',
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `created_by` int(11) unsigned NOT NULL default '0',
  `created_by_alias` varchar(255) NOT NULL default '',
  `modified` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_by` int(11) unsigned NOT NULL default '0',
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  `images` text NOT NULL,
  `urls` text NOT NULL,
  `attribs` text NOT NULL,
  `version` int(11) unsigned NOT NULL default '1',
  `parentid` int(11) unsigned NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `access` int(11) unsigned NOT NULL default '0',
  `hits` int(11) unsigned NOT NULL default '0',
  `metadata` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_section` (`sectionid`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`),
  KEY `idx_state` (`state`),
  KEY `idx_catid` (`catid`),
  KEY `idx_createdby` (`created_by`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_content`
--

LOCK TABLES `jos_content` WRITE;
/*!40000 ALTER TABLE `jos_content` DISABLE KEYS */;
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (1,'Welcome','welcome','','<p>Welcome to SWT Matrix! You must have taken the red pill :-)</p>','',1,0,0,0,'2011-03-13 22:10:04',62,'','2011-04-28 19:31:59',62,0,'0000-00-00 00:00:00','2011-03-13 22:10:04','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=0\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',9,0,5,'','',0,3,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (2,'Products','products','','<p>Products</p>','',1,0,0,0,'2011-03-14 01:26:16',62,'','0000-00-00 00:00:00',0,0,'0000-00-00 00:00:00','2011-03-14 01:26:16','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',1,0,4,'','',0,62,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (3,'SWT Matrix','swt-matrix','','<p>SWT Matrix is a tabular widget for the SWT Java GUI toolkit. Is is characterized by an unlimited capacity and instant rendering.</p>','',1,1,0,1,'2011-03-14 01:58:18',62,'','2011-04-29 13:14:35',62,0,'0000-00-00 00:00:00','2011-03-14 01:58:18','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',3,0,10,'','',0,180,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (4,'Download','download','','<p>Dependencies:</p>\r\n<ul>\r\n<li>Java 1.5 or higher. </li>\r\n<li>SWT 3.4 or higher (the lower versions may work, but have not been tested).</li>\r\n</ul>\r\n<p>By downloading the software below I agree with the&nbsp;<a href=\"swt-matrix/EULA.txt\" mce_href=\"swt-matrix/EULA.txt\" target=\"_blank\">End User License Agreement</a>.</p>\r\n<h3>Version 0.3 (alpha stage)</h3>\r\n<p><a href=\"swt-matrix/swt-matrix-0.3.jar\" mce_href=\"swt-matrix/swt-matrix-0.3.jar\">SWT Matrix</a></p>\r\n<p><a href=\"swt-matrix/swt-matrix-snippets-0.3.zip\" mce_href=\"swt-matrix/swt-matrix-snippets-0.3.zip\">Snippets</a></p>\r\n<p><a href=\"swt-matrix/change-log.txt\" mce_href=\"swt-matrix/change-log.txt\">Change Log</a></p>\r\n\r\n\r\n<mce:script type=\"text/javascript\"><!--\r\neula();\r\n<script>\r\n// --></mce:script>','',1,0,0,0,'2011-03-14 03:24:20',62,'','2011-07-09 19:15:37',62,0,'0000-00-00 00:00:00','2011-03-14 03:24:20','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',31,0,3,'','',0,90,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (5,'Features','features','','<p class=\"filters\">The list includes both done and planned features. Its also possible to filter features coming only in the next release. Outstanding features are unique or rare compared to similar components. The list is also filterable by somewhat subjective importance of the feature.</p>\r\n<p>Please let us know in the <a href=\"#comments\">comments </a>which features do you miss the most.</p>\r\n<form id=\"let-us-know\" style=\"display: none\"><textarea style=\"width: 80%; height: 75px;\"></textarea><input type=\"submit\" /></form>\r\n<p class=\"filters\">Filters: <a id=\"filter-all\" href=\"#\">all</a> <a id=\"filter-current\" href=\"#\">done</a> <a id=\"filter-next\" href=\"#\">next</a> <a id=\"filter-future\" href=\"#\">planned</a> <input id=\"filter-outstanding\" type=\"checkbox\" /> outstanding <input id=\"filter-high\" type=\"checkbox\" /> high <input id=\"filter-medium\" type=\"checkbox\" /> medium <input id=\"filter-low\" type=\"checkbox\" /> low</p>\r\n<!-- generated start --><table class=\'data\' cellspacing=\'1\'><tr><th style=\'white-space: nowrap\'>Feature</th><th>Description</th><th>Version</th><th>Importance</th><th>Outstanding</th><th>References</th></tr><tr><td colspan=\'7\' class=\'header\'><h3>Layout</h3></td></tr><tr><td style=\'white-space: nowrap\'>Standard sections</td><td>Header and body</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom sections</td><td>Additional sections like footer or filters can be defined</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0003.java\'>Snippet_0003</a></td></tr><tr><td style=\'white-space: nowrap\'>Unlimited number of items</td><td>Each section can have an unlimited number of items</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0002.java\'>Snippet_0002</a></td></tr><tr><td style=\'white-space: nowrap\'>Virtualization</td><td>Results in performance not dependant on the number of items in sections</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Show / hide section</td><td>For example it\'s common to show / hide the header section</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default cell width</td><td>Any cell that does not have a custom width will have the default width</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Individual cell width</td><td>Each cell can have a different width</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default line width</td><td>Any line that does not have a custom width will have the default width</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Individual line width</td><td>Each line can have a different width</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Spaces between cells</td><td>Similar to HTML table cellspacing attribute. It does not effect the cell size and cell painting algorithm.</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Zones</h3></td></tr><tr><td style=\'white-space: nowrap\'>Zone as crossing of sections</td><td>Body zone = row axis body section and column axis body section, Column header zone = row axis header section and column axis body section</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Separate painters</td><td>Each zone can have separate painters</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Separate event handlers</td><td>Each zone can have separate event handlers</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Selection</h3></td></tr><tr><td style=\'white-space: nowrap\'>Select axis items</td><td>Both full rows and full columns can be selected</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Select cells</td><td>Standard cell selection by mouse and keyboard.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Modify selection</td><td>Works for both full axis items and cells. It is done by standard CTRL selection gestures.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Header cells highlight</td><td>Header cells can be automatically highlighted for the selected cells.</td><td>0.1</td><td>low</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Enable selection</td><td>Cell selection can be enabled/disabled</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Resize</h3></td></tr><tr><td style=\'white-space: nowrap\'>Resize rows and columns</td><td>Not only columns can be resized but rows as well.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Multiple resize</td><td>Resizes all selected items to the same width as the one being resized</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Instant resize</td><td>See the item repainted with the new width while dragging, as opposed to repaint after drag finished.</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Resize in all sections</td><td>Not only body items can be resized, but the headers ones as well. For example the row header width can be changed by the user dragging it\'s right edge.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default resize ability</td><td>All items in a section can have the resize ability enabled or disabled by default.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item resize ability</td><td>Individual items can have the resize ability enabled or disabled</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom resize offset</td><td>Define how far from the line is the resize area</td><td>0.1</td><td>low</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Auto-resize</td><td>Column width and row height can be automatically calculated by double clicking in the resize area</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Move</h3></td></tr><tr><td style=\'white-space: nowrap\'>Move rows and columns</td><td>Not only columns can be moved but rows as well.</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Multiple move</td><td>Moves all selected items to the same width as the one being moved</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Instant move</td><td>See the items reordered while dragging, as opposed to repaint only after drag finished.</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Move in selection order</td><td>If multiple items are moved then they become ordered according to the sequence they were selected.</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Move in all sections</td><td>Not only body items can be moved, but in other sections as well. </td><td>0.1</td><td>low</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default move ability</td><td>All items in a section can have the move ability enabled or disabled by default.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item move ability</td><td>Individual items can have the move ability enabled or disabled</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Hide</h3></td></tr><tr><td style=\'white-space: nowrap\'>Hide rows and columns</td><td>Not only columns can be hidden but rows as well.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Multiple hide</td><td>Hides all selected items to the same width as the one being hidden</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Hide in all sections</td><td>Not only body items can be hidden, but in other sections as well. </td><td>0.1</td><td>low</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default hide ability</td><td>All items in a section can have the hide ability enabled or disabled by default.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item move ability</td><td>Individual items can have the hide ability enabled or disabled</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Group</h3></td></tr><tr><td style=\'white-space: nowrap\'>TreeTable</td><td>A column may display a tree like structure of the row axis items</td><td>0.3</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Cell merging</td><td>Individual cell merging</td><td>0.7</td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item hierarchy as groups</td><td>The hierarchy is illustrated by the item in higher level being merged to the extent of its children</td><td>0.7</td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Collapse hierarchy items</td><td>Node collapse/expand typical for tree widgets</td><td>0.7</td><td>high</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Scroll</h3></td></tr><tr><td style=\'white-space: nowrap\'>Item scrolling</td><td>Scroll position is always snapped to the beginning of a first visible item</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Pixel scrolling</td><td>Smooth scrolling by pixels</td><td>0.5</td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Scroll to given item</td><td></td><td>0.5</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Auto-scroll with acceleration</td><td>The content will scroll automatically while a dragging operation reaches the edge of the scrollable area. It is during select, resize and move operations.</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom auto-scroll offset</td><td>Define how far from the edge of scrollable area is the auto-scrolling will start</td><td>0.1</td><td>low</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom auto-scroll acceleration</td><td>Define how fast the the auto-scroll will accelerate</td><td>1.+</td><td>low</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Freeze head</td><td>Prevent the first items from scrolling, making them always visible</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0201.java\'>Snippet_0201</a></td></tr><tr><td style=\'white-space: nowrap\'>Freeze tail</td><td>Prevent the last items from scrolling, making them always visible</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0201.java\'>Snippet_0201</a></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Paint</h3></td></tr><tr><td style=\'white-space: nowrap\'>Background, foreground colors</td><td>Individual background and foreground color for cells.</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0017.java\'>Snippet_0017</a></td></tr><tr><td style=\'white-space: nowrap\'>Line color, width, style</td><td>Drawing lines with individual color, width and style.</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0012.java\'>Snippet_0012</a></td></tr><tr><td style=\'white-space: nowrap\'>Text font, align, padding</td><td>Drawing text with padding and aligning horizontally and vertically with custom colors and fonts for each cell.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Automatic numbering in headers</td><td>The default header painter draws text as sequential numbers starting from 0.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Image align, padding</td><td>Drawing image with padding and aligning horizontally and vertically</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0018.java\'>Snippet_0018</a></td></tr><tr><td style=\'white-space: nowrap\'>Multiline text</td><td>Text wrapping inside of a cell</td><td>0.3</td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Tilted text</td><td>Vertical or rotated </td><td>0.9</td><td>low</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom painter</td><td>All that is draw on the canvas can be replaced by a custom painter.</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0014.java\'>Snippet_0014</a>, <a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0015.java\'>Snippet_0015</a></td></tr><tr><td style=\'white-space: nowrap\'>Painters composition</td><td>List of matrix and zone painters is fully editable (add, replace, remove)</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Background painter</td><td>Custom background painter for the whole matrix, not only the cell. Can be used for current row highlighting</td><td>0.1</td><td>low</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0010.java\'>Snippet_0010</a>, <a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0015.java\'>Snippet_0015</a></td></tr><tr><td style=\'white-space: nowrap\'>Focus cell painter</td><td>Enables to customize the focus cell painting.</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0011.java\'>Snippet_0011</a></td></tr><tr><td style=\'white-space: nowrap\'>Cell style</td><td>Set of attributes that can be named and applied to a number of cells.</td><td></td><td>medium</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Zoom</td><td>Zoom in  and zoom out the view port area.</td><td>>1</td><td>low</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Shorten text at the end</td><td>Three dots at the end of text instead in the middle if the text is to long to fit in a cell.</td><td>0.9</td><td>medium</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Data</h3></td></tr><tr><td style=\'white-space: nowrap\'>Filtering</td><td>Filtering can be implemented by simply taking data to paint from a reduced collection of items.</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_003.java\'>Snippet_003</a></td></tr><tr><td style=\'white-space: nowrap\'>Sorting</td><td>Sorting marker can be displayed using painter method and sorting cen be triggered by the header zone event listener</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_005.java\'>Snippet_005</a></td></tr><tr><td style=\'white-space: nowrap\'>Add/remove items</td><td>Adding or removing items in the model is reflected in the matrix</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_004.java\'>Snippet_004</a></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Gestures</h3></td></tr><tr><td style=\'white-space: nowrap\'>Command binding</td><td>Custom binding many of the user activated commands to key/mouse gestures</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0901.java\'>Snippet_0901</a></td></tr><tr><td style=\'white-space: nowrap\'>SelectionEvent</td><td>Handling of SelectionEvent</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0902.java\'>Snippet_0902</a></td></tr><tr><td style=\'white-space: nowrap\'>ControlEvent</td><td>Handling of ControlEvent</td><td>0.1</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0902.java\'>Snippet_0902</a></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Edit</h3></td></tr><tr><td style=\'white-space: nowrap\'>Text, combo, date/time</td><td>Allow changing the value of a cell using standard SWT controls as pop-ups</td><td>0.2</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0404.java\'>Snippet_0404</a></td></tr><tr><td style=\'white-space: nowrap\'>Embedded native controls</td><td>Check buttons or other controls constantly embedded in the cell</td><td>0.2</td><td>middle</td><td>outstanding</td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0402.java\'>Snippet_0402</a></td></tr><tr><td style=\'white-space: nowrap\'>Checkbox emulation</td><td>For bettern performance then lots of native controls. Support for custom OS themes.</td><td>0.2</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0403.java\'>Snippet_0403</a></td></tr><tr><td style=\'white-space: nowrap\'>Cut/copy/paste</td><td>Using clipboard tab separated format compatible with Excel</td><td>0.2</td><td>high</td><td></td><td><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0410.java\'>Snippet_0410</a></td></tr><tr><td style=\'white-space: nowrap\'>Delete</td><td>Delete a cell value</td><td>0.2</td><td>high</td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Other</h3></td></tr><tr><td style=\'white-space: nowrap\'>Tooltips</td><td>Tooltips for individual cells</td><td>0.8</td><td>medium</td><td></td><td></td></tr></table><!-- generated end -->\r\n<script type=\"text/javascript\">// <![CDATA[\r\nfilterFeatures();\r\n// ]]></script>\r\n<p><a name=\"comments\"></a></p>','',1,1,0,1,'2011-03-14 09:04:13',62,'','2011-06-17 20:40:41',62,62,'2011-07-20 13:29:52','2011-03-14 09:04:13','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',89,0,11,'','',0,535,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (18,'Issue Tracker','issuet-racker','','<mce:script type=\"text/javascript\"><!--\r\nissue_tracker();\r\n// --></mce:script>\r\n\r\n<!--iframe src=\"http://www.codaset.com/netanel/swt-matrix/tickets\" mce_src=\"http://www.codaset.com/netanel/swt-matrix/tickets\" width=\"100%\" height=\"100%\">\r\n  Your browser does not support iframes.\r\n</iframe-->','',1,1,0,1,'2011-07-20 13:07:12',62,'','2011-07-20 13:29:22',62,0,'0000-00-00 00:00:00','2011-07-20 13:07:12','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',8,0,1,'','',0,16,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (6,'404','404','','<h1>404: Not Found</h1>\r\n<h2>Sorry, but the content you requested could not be found</h2>','',1,0,0,0,'2004-11-11 12:44:38',62,'','2011-03-14 12:01:41',0,62,'2011-05-06 16:43:28','2004-10-17 00:00:00','0000-00-00 00:00:00','','','menu_image=-1\nitem_title=0\npageclass_sfx=\nback_button=\nrating=0\nauthor=0\ncreatedate=0\nmodifydate=0\npdf=0\nprint=0\nemail=0',1,0,2,'','',0,750,'');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (7,'API','api','','<p>api</p>','',-2,1,0,1,'2011-03-14 12:43:56',62,'','2011-03-14 14:40:19',62,0,'0000-00-00 00:00:00','2011-03-14 12:43:56','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',2,0,0,'','',0,5,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (8,'Forum','forum','','<p><a id=\"nabblelink\" href=\"http://swt-matrix.1049369.n5.nabble.com/\">swt-matrix</a></p>\r\n<script src=\"http://swt-matrix.1049369.n5.nabble.com/embed/f4367519\"></script>','',1,0,0,0,'2011-03-14 18:03:36',62,'','2011-05-03 15:08:05',62,0,'0000-00-00 00:00:00','2011-03-14 18:03:36','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',2,0,1,'','',0,21,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (9,'SWT Matrix - Overview','swt-matrix-overview','','<p>{module SWT Matrix Menu}</p>\r\n<p>SWT Matrix is a tabular widget for <a href=\"http://www.eclipse.org/swt\">SWT</a> Java GUI toolkit.</p>\r\n<p>What makes it different from other such components is <strong>unlimited capacity</strong> and <strong>instant rendering</strong>. <br />It also focuses on developer productivity by <strong>clean design</strong> with minimal learning curve.</p>\r\n<p><img src=\"images/infinite.png\" border=\"0\" width=\"64\" height=\"64\" style=\"float:left; padding-right:10px\" /></p>\r\n<h3><a name=\"capacity\"></a>Unlimited Capacity</h3>\r\n<p style=\"padding-left: 85px\">SWT Matrix can handle an <strong>unlimited</strong> number of rows and columns. Integer.MAX_VALUE (2 147 483 647) is not a limit, neither is Long.MAX_VALUE (9 223 372 036 854 775 807). And still it performs block selection, moving, resizing scrolling and setting of various properties of cells, rows and columns. More then 2147483647 items is a rare case, but it <span style=\"text-decoration: underline;\">can</span> happen. It\'s better to be <span style=\"text-decoration: underline;\">safe</span> then sorry.</p>\r\n<p><img src=\"images/clock.png\" border=\"0\" width=\"64\" height=\"64\" style=\"float:left; padding-right:10px; \" /></p>\r\n<h3><a name=\"instant\"></a>Instant Rendering</h3>\r\n<p style=\"padding-left: 85px\">It is assumed by convention that<strong> instant</strong> for GUI applications means responding in less then 0.1 sec. Now consider scrolling a content of a table with 1 million rows and columns in a full screen mode with 1680 x 1050 screen resolution. It\'s around 2000 visible cells of size 50x16. Components that don\'t support virtual display choke completely. BTW don\'t be misled by SWT.VIRTUAL constructor flag of those widgets. Scroll to the end and you will see that it is lazy initialization rather then virtualization. None of the popular tabular components tested on Windows XP 2GHz are consistent to provide instant response for basic operations like navigation. SWT Matrix paints itself in about <strong>50 ms</strong>, which is at least <strong>8x better</strong> then the other grid/table widgets. And not all identified optimizations have been implemented yet.</p>\r\n<p><img src=\"images/design.png\" border=\"0\" width=\"64\" height=\"64\" style=\"float:left; padding-right:10px; \" /></p>\r\n<h3><a name=\"design\"></a>Clean design</h3>\r\n<p style=\"padding-left: 85px\">Total of <strong>7 classes</strong> in the public <a href=\"swt-matrix/javadoc/index.html\">API</a> does not sound like an over-complicated design for the amount of supported features and compared to hundreds of classes in other solutions. <strong>Symmetry</strong> is also a measure of a clean design. Thus there is no such a thing that works for columns, but does not work for rows and vice versa, or works for one section and does not for another. For example since columns can be resized why not resize the row header? Even freezing is symetric. If the head of an axis can be frozen why not the tail? A frozen footer may come handy.</p>\r\n<p>The current major limitations are for instance lack of cell merging, column/row grouping and hierarchy. But they are coming in the next releases. {article features}{link}Here{/link}{/article} is the detail feature list.</p>\r\n<p>The component is released under<strong> commercial licence</strong> but is free of charge for non commercial use.</p>\r\n<p>The current stage is <strong>alpha release</strong> to allow evaluation and early user feedback. <br />Hope to hear from you. Your opinion is invaluable to us.</p>\r\n<p> </p>\r\n<!-- AddThis Button BEGIN -->\r\n<div class=\"addthis_toolbox addthis_default_style \">\r\n<p><a class=\"addthis_button_facebook_like\"></a> <a class=\"addthis_button_tweet\"></a> <a class=\"addthis_counter addthis_pill_style\"></a></p>\r\n</div>\r\n<script src=\"http://s7.addthis.com/js/250/addthis_widget.js#pubid=xa-4dd798d712e3ff54\" type=\"text/javascript\"></script>\r\n<script type=\"text/javascript\">// <![CDATA[\r\n/*var cssLink = document.createElement(\"link\") \r\ncssLink.href = \"media/css/addvalthis.css\"; \r\ncssLink.rel = \"stylesheet\"; \r\ncssLink.type = \"text/css\"; \r\nframes[0].document.head.appendChild(cssLink);*/\r\n// ]]></script>\r\n<!-- AddThis Button END -->','',1,1,0,1,'2011-03-15 07:00:25',62,'','2011-06-17 21:16:29',62,0,'0000-00-00 00:00:00','2011-03-15 07:00:25','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',107,0,9,'','',0,285,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (10,'Design','swt-matrix-design','','<p>The over arching principle for the matrix widget design is simplicity, that materializes in:</p>\r\n<p>\r\n<ul>\r\n<li>Separation of concerns. The state maintenance has been </li>\r\n</ul>\r\n</p>\r\n<h3>Main concepts</h3>\r\n<p>- Matrix, MatrixAxis</p>\r\n<p>- Cell and line</p>\r\n<p>- AxisLayout</p>\r\n<p>- Index</p>\r\n<p>- Section</p>\r\n<p>- Zone</p>\r\n<p>- Dock</p>\r\n<p>- Painters</p>\r\n<p> </p>\r\n<p> </p>\r\n<p> </p>\r\n<p>Performance</p>\r\n<p>- Loop inversion: loop painters -&gt; loop cells -&gt; paint</p>\r\n<p>- Caching the layout computation results, paint does not recompute widths, distances, visible indexes</p>\r\n<p>- Caching results of GC.stringExtent for single characters of each font used in TextPainter.</p>\r\n<p> </p>\r\n<p>Indexes</p>\r\n<p>model index - original index that numbers the items in the model</p>\r\n<p>order index - position of the model index in the order sequence</p>\r\n<p>visible index - position of the model index in the order seqence after hiding some items</p>\r\n<p>The letter one matches with what the user sees on the screen.</p>\r\n<p> </p>\r\n<p>Selection and reordering is limited to the scope a single section only.</p>\r\n<p> </p>\r\n<p>Freezing</p>\r\n<p> </p>\r\n<p>Command</p>\r\n<p> </p>\r\n<p>Item reordering</p>\r\n<p> </p>\r\n<p>Indexes to selection are interim (position in order sequence ignoring hiding).</p>\r\n<p>This is to naturaly support a scenario of for example selecting extent</p>\r\n<p>from column 4 to column 6 including a hidden column 5 in between.</p>\r\n<p> </p>','',1,1,0,1,'2011-03-15 10:14:57',62,'','2011-04-13 12:27:57',62,62,'2011-04-13 12:27:57','2011-03-15 10:14:57','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',4,0,8,'','',0,5,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (11,'Snippets','snippets','','<p>Snippets are minimal stand-alone programs that demonstrate specific techniques or functionality. Often a small example is the easiest way to understand how to use a particular feature.</p>\r\n<!-- generated start --><p>Layout</p><ul><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0001.java\'>Simplest matrix.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0002.java\'>Unlimited number of items.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0003.java\'>Filter section between header and body.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0004.java\'>Add / remove model items.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0005.java\'>Sorting by columns.</a></li></ul><p>Painters</p><ul><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0010.java\'>Draw custom background for the whole matrix.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0011.java\'>Draw custom focus cell marker.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0012.java\'>Change the line style.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0013.java\'>Gap between cells like HTML table cell spacing attribute. Hide lines.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0014.java\'>Altering row background.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0015.java\'>Current row gradient highlighter.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0016.java\'>Mark selection with SWT.COLOR_LIST... colors.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0017.java\'>Cell background calculated</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0018.java\'>Image painting</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0019.java\'>Align column to the right.</a></li></ul><p>Navigation</p><ul><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0201.java\'>Freeze head and tail with different color for the dividing line</a></li></ul><p>Edit</p><ul><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0401.java\'>Simplest zone editor.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0402.java\'>Embedded check buttons.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0403.java\'>Check buttons emulated by images.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0404.java\'>Edit controls Text, DateTime, Boolean, Combo depending on the cell value.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0405.java\'>List as a custom editor control.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0410.java\'>Separate zone to insert new items.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0450.java\'>Activate editor with single click instead of double click.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0456.java\'>Traverse cells (Shift+) Tab and Enter. Traverse Matrix with (Shift+) Ctrl+Tab.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0490.java\'>Custom copy / paste.</a></li></ul><p>Other</p><ul><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0901.java\'>Change gesture binding.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0902.java\'>Selection and control event handling.</a></li><li><a href=\'http://netanel.pl/swt-matrix/snippets/Snippet_0903.java\'>Identifying items by distance.</a></li></ul><!-- genera