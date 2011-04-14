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
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
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
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (20,'Articles','option=com_content',0,0,'','','com_content',0,'',1,'show_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=0\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=0\nshow_create_date=0\nshow_modify_date=0\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=0\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\nfeed_summary=0\nfilter_tags=\nfilter_attritbutes=\n\n',1);
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
INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES (50,'Kunena Forum','option=com_kunena',0,0,'option=com_kunena','Kunena Forum','com_kunena',0,'components/com_kunena/images/kunenafavicon.png',0,'',1);
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
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_content`
--

LOCK TABLES `jos_content` WRITE;
/*!40000 ALTER TABLE `jos_content` DISABLE KEYS */;
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (1,'Welcome','welcome','','<p>Welcome to SWT Matrix! You must have taken the red pill :-)</p>\r\n<p>This site is under construction. Please come later.</p>\r\n<p><img src=\"images/under_construction.png\" border=\"0\" alt=\"under_construction\" width=\"256\" height=\"256\" style=\"border: 0;\" /></p>','',1,0,0,0,'2011-03-13 22:10:04',62,'','2011-04-14 07:59:33',62,0,'0000-00-00 00:00:00','2011-03-13 22:10:04','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',7,0,5,'','',0,3,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (2,'Products','products','','<p>Products</p>','',1,0,0,0,'2011-03-14 01:26:16',62,'','0000-00-00 00:00:00',0,0,'0000-00-00 00:00:00','2011-03-14 01:26:16','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',1,0,4,'','',0,62,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (3,'SWT Matrix','swtmatrix','','<p>SWT Matrix is a tabular widget for the SWT Java GUI toolkit. Is is characterized by an unlimited capacity and instant rendering.</p>','',1,1,0,1,'2011-03-14 01:58:18',62,'','2011-03-14 14:40:28',62,0,'0000-00-00 00:00:00','2011-03-14 01:58:18','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',2,0,7,'','',0,180,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (4,'Download','download','','<h3>Version 0.1 (alpha stage)</h3>\r\n<p><a href=\"swt-matrix/swt-matrix-0.1.jar\">swt-matrix-0.1.jar</a></p>\r\n<p>Java 1.5 required.</p>\r\n<p>The only third party library dependence is the <a href=\"http://www.eclipse.org/swt/\">SWT</a> library &gt;= 3.4 (the lower versions compatibility has not been investigated).</p>','',1,0,0,0,'2011-03-14 03:24:20',62,'','2011-04-13 10:49:36',62,0,'0000-00-00 00:00:00','2011-03-14 03:24:20','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',4,0,3,'','',0,19,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (5,'Features','swt-matrix-features','','<p class=\"filters\">Filters: <a id=\"filter-all\" href=\"#\">all</a> <a id=\"filter-current\" href=\"#\">current</a> <a id=\"filter-next\" href=\"#\">next</a> <a id=\"filter-future\" href=\"#\">future</a> <input id=\"filter-outstanding\" type=\"checkbox\"/> outstanding <input id=\"filter-high\" type=\"checkbox\" checked/> high <input id=\"filter-medium\" type=\"checkbox\" checked/> medium <input id=\"filter-low\" type=\"checkbox\" checked/> low</p>\r\n<!-- generated start --><table class=\'data\' cellspacing=\'1\'><tr><th style=\'white-space: nowrap\'>Feature</th><th>Description</th><th>Version</th><th>References</th><th>Importance</th><th>Outstanding</th></tr><tr><td colspan=\'7\' class=\'header\'><h3>Layout</h3></td></tr><tr><td style=\'white-space: nowrap\'>Standard sections</td><td>Header and body</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom sections</td><td>For example footer, filters can be set</td><td>0.1</td><td></td><td>high</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Unlimited number of items</td><td>Each section can have an unlimited number of items</td><td>0.1</td><td></td><td>high</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Virtualization</td><td>Results in performance not dependant on the number of items in sections</td><td>0.1</td><td></td><td>high</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Show / hide section</td><td>For example it\'s common to show / hide the header section</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default cell width</td><td>Any cell that does not have a custom width will have the default width</td><td>0.1</td><td></td><td>medium</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Individual cell width</td><td></td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default line width</td><td>Any line that does not have a custom width will have the default width</td><td>0.1</td><td></td><td>medium</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Individual line width</td><td></td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Spaces between cells</td><td>Similar to HTML table cellspacing attribute. It does not effect the cell size and cell painting algorithm.</td><td>0.1</td><td></td><td>medium</td><td>outstanding</td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Selection</h3></td></tr><tr><td style=\'white-space: nowrap\'>Select axis items</td><td>Both full rows and full columns can be selected</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Select cells</td><td>Standard selection by mouse and keyboard.</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Modify selection</td><td>Works for both full axis items and cells. It is done by standard CTRL selection gestures.</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Header cells highlight</td><td>Header cells can be automatically highlighted for the selected cells.</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Enable selection</td><td></td><td>0.1</td><td></td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Resize</h3></td></tr><tr><td style=\'white-space: nowrap\'>Resize rows and columns</td><td>Not only columns can be resized but rows as well.</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Multiple resize</td><td>Resizes all selected items to the same width as the one being resized</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Instant resize</td><td>See the item repainted with the new width while dragging, as opposed to repaint after drag finished.</td><td>0.1</td><td></td><td>medium</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Resize in all sections</td><td>Not only body items can be resized, but the headers ones as well. For example the row header width can be changed by the user dragging it\'s right edge.</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default resize ability</td><td>All items in a section can have the resize ability enabled or disabled by default.</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item resize ability</td><td>Individual items can have the resize ability enabled or disabled</td><td>0.1</td><td></td><td>medium</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom resize offset</td><td>Define how far from the line is the resize area</td><td>0.1</td><td></td><td>low</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Auto-resize</td><td>Column width and row height can be automatically calculated by double clicking in the resize area</td><td>0.2</td><td></td><td>high</td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Move</h3></td></tr><tr><td style=\'white-space: nowrap\'>Move rows and columns</td><td>Not only columns can be moved but rows as well.</td><td>0.1</td><td></td><td>high</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Multiple move</td><td>Moves all selected items to the same width as the one being moved</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Instant move</td><td>See the items reordered while dragging, as opposed to repaint only after drag finished.</td><td>0.1</td><td></td><td>medium</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Move in selection order</td><td>If multiple items are moved then they become ordered according to the sequence they were selected.</td><td>0.1</td><td></td><td>medium</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Move in all sections</td><td>Not only body items can be moved, but in other sections as well. </td><td>0.1</td><td></td><td>low</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default move ability</td><td>All items in a section can have the move ability enabled or disabled by default.</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item move ability</td><td>Individual items can have the move ability enabled or disabled</td><td>0.1</td><td></td><td>medium</td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Hide</h3></td></tr><tr><td style=\'white-space: nowrap\'>Hide rows and columns</td><td>Not only columns can be hidden but rows as well.</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Multiple hide</td><td>Hides all selected items to the same width as the one being hidden</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Hide in all sections</td><td>Not only body items can be hidden, but in other sections as well. </td><td>0.1</td><td></td><td>low</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Default hide ability</td><td>All items in a section can have the hide ability enabled or disabled by default.</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item move ability</td><td>Individual items can have the hide ability enabled or disabled</td><td>0.1</td><td></td><td>medium</td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Group</h3></td></tr><tr><td style=\'white-space: nowrap\'>Cell merging</td><td>Individual cell merging</td><td>0.7</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item hierarchy as groups</td><td>The hierarchy is illustrated by the item in higher level being merged to the extent of its children</td><td>0.7</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Item hierarchy as tree</td><td>A given row or column may display a tree like connections describing the hierarchy </td><td>0.7</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Collapse hierarchy items</td><td></td><td>0.7</td><td></td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Scroll</h3></td></tr><tr><td style=\'white-space: nowrap\'>Item scrolling</td><td>Scroll position is always snapped to the beginning of a first visible item</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Pixel scrolling</td><td>Smooth scrolling by pixels</td><td>0.5</td><td></td><td>medium</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Scroll to given item</td><td></td><td>0.5</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Auto-scroll with acceleration</td><td>The content will scroll automatically while a dragging operation reaches the edge of the scrollable area. It is during select, resize and move operations.</td><td>0.1</td><td></td><td>high</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Custom auto-scroll offset</td><td>Define how far from the edge of scrollable area is the auto-scrolling will start</td><td>0.1</td><td></td><td>low</td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Custom auto-scroll acceleration</td><td>Define how fast the the auto-scroll will accelerate</td><td>1.+</td><td></td><td>low</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Freeze head</td><td>Prevent the first items from scrolling, making them always visible</td><td>0.1</td><td></td><td>high</td><td></td></tr><tr><td style=\'white-space: nowrap\'>Freeze tail</td><td>Prevent the last items from scrolling, making them always visible</td><td>0.1</td><td></td><td>high</td><td>outstanding</td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Paint</h3></td></tr><tr><td style=\'white-space: nowrap\'>Background, foreground colors</td><td></td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Line color, width, style</td><td></td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Text font, color, align, padding</td><td>Custom text aligning horizontally and vertically</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Automatic numbering in headers</td><td></td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Image size, align, padding</td><td></td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Multiline text</td><td></td><td>0.3</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Tilted text</td><td>Vertical or rotated </td><td>0.9</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom painter</td><td></td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Painters composition</td><td>List of matrix and zone painters is fully editable (add, replace, remove)</td><td>0.1</td><td></td><td></td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Background painter</td><td>Custom background painter for the whole matrix, not only the cell. Can be used for current row highlighting</td><td>0.1</td><td>Snippet_0010, Snippet_0015</td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Navigation cell painter</td><td></td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Cell style</td><td>Set of attributes that can be named and applied to a number of cells.</td><td></td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Zoom</td><td></td><td></td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Shorten text at the end</td><td>Three dots at the end of text instead in the middle if the text is to long to fit in a cell.</td><td></td><td></td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Zones</h3></td></tr><tr><td style=\'white-space: nowrap\'>Zone as crossing of sections</td><td>Body zone = row axis body section and column axis body section, Column header zone = row axis header section and column axis body section</td><td>0.1</td><td></td><td></td><td>outstanding</td></tr><tr><td style=\'white-space: nowrap\'>Separate painters</td><td>Each zone can have separate painters</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Separate event handlers</td><td>Each zone can have separate event handlers</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Gestures</h3></td></tr><tr><td style=\'white-space: nowrap\'>Custom gesture binding</td><td>Custom gesture binding for most of the user actions</td><td>0.1</td><td></td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Edit</h3></td></tr><tr><td style=\'white-space: nowrap\'>Text, combo</td><td>Allow changing the value of a cell.</td><td>0.2</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Native looking check box</td><td></td><td>0.2</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Custom editor</td><td></td><td>0.2</td><td></td><td></td><td></td></tr><tr><td style=\'white-space: nowrap\'>Cut/copy/paste</td><td></td><td>0.2</td><td></td><td></td><td></td></tr><tr><td colspan=\'7\' class=\'header\'><h3>Other</h3></td></tr><tr><td style=\'white-space: nowrap\'>Tooltips</td><td></td><td>0.8</td><td></td><td></td><td></td></tr></table><!-- generated end -->\r\n\r\n<script type=\"text/javascript\">\r\nfilterFeatures();\r\n</script>','',1,1,0,1,'2011-03-14 09:04:13',62,'','2011-04-14 12:18:33',62,62,'2011-04-14 12:18:33','2011-03-14 09:04:13','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',72,0,8,'','',0,349,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (6,'404','404','','<h1>404: Not Found</h1>\r\n<h2>Sorry, but the content you requested could not be found</h2>','',1,0,0,0,'2004-11-11 12:44:38',62,'','2011-03-14 12:01:41',0,62,'2004-11-11 12:45:09','2004-10-17 00:00:00','0000-00-00 00:00:00','','','menu_image=-1\nitem_title=0\npageclass_sfx=\nback_button=\nrating=0\nauthor=0\ncreatedate=0\nmodifydate=0\npdf=0\nprint=0\nemail=0',1,0,2,'','',0,750,'');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (7,'API','api','','<p>api</p>','',-2,1,0,1,'2011-03-14 12:43:56',62,'','2011-03-14 14:40:19',62,0,'0000-00-00 00:00:00','2011-03-14 12:43:56','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',2,0,0,'','',0,5,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (8,'Contact','contact','','<p>email: <a href=\"mailto: info@netanel.pl\" title=\"email\">info@netanel.pl</a></p>','',1,0,0,0,'2011-03-14 18:03:36',62,'','0000-00-00 00:00:00',0,0,'0000-00-00 00:00:00','2011-03-14 18:03:36','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',1,0,1,'','',0,5,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (9,'SWT Matrix - Overview','swt-matrix-overview','','<p>SWT Matrix is a tabular widget for the <a href=\"http://www.eclipse.org/swt\">SWT </a>Java GUI toolkit.</p>\r\n<p>What makes it different from other grid components is <strong>unlimited capacity</strong> and <strong>instant rendering</strong>. <br />It also focuses on developer productivity by <strong>clean design</strong> with minimal learning curve.</p>\r\n<p><img src=\"images/infinite.png\" border=\"0\" width=\"64\" height=\"64\" style=\"float:left; padding-right:10px\" /></p>\r\n<h3><a name=\"capacity\"></a>Unlimited Capacity</h3>\r\n<p style=\"padding-left: 85px\">SWT Matrix can handle an <strong>unlimited</strong> number of rows and columns. Integer.MAX_VALUE (2 147 483 647) is not a limit, neither is Long.MAX_VALUE (9 223 372 036 854 775 807). And still it performs block selection, moving, resizing scrolling and setting of various properties of cells, rows and columns. More then 2147483647 items is a rare case, but it <span style=\"text-decoration: underline;\">can</span> happen. It\'s better to be <span style=\"text-decoration: underline;\">safe</span> then sorry.</p>\r\n<p><img src=\"images/clock.png\" border=\"0\" width=\"64\" height=\"64\" style=\"float:left; padding-right:10px; \" /></p>\r\n<h3><a name=\"instant\"></a>Instant Rendering</h3>\r\n<p style=\"padding-left: 85px\"><strong>Instant</strong> for GUI applications means responding in less then 0.1 sec. Now consider scrolling a content of a table with 1 million rows and columns in a full screen mode with 1680 x 1050 screen resolution. It\'s around 2000 visible cells of size 50x16. Components that don\'t support virtual display choke completely. BTW don\'t be misled by SWT.VIRTUAL constructor flag of those widgets. Scroll to the end and you will see that it is lazy initialization rather then virtualization. None of the popular tabular components tested on Windows XP 2GHz are consistent to provide instant response for basic operations like navigation. SWT Matrix paints itself in about <strong>50 ms</strong>, which is at least <strong>8x better</strong> then the other grid/table widgets. And not all identified optimizations have been implemented yet.</p>\r\n<p><img src=\"images/design.png\" border=\"0\" width=\"64\" height=\"64\" style=\"float:left; padding-right:10px; \" /></p>\r\n<h3><a name=\"design\"></a>Clean design</h3>\r\n<p style=\"padding-left: 85px\">Total of <strong>7 classes</strong> does not sound like an over complicated design for the amount of supported features. <strong>Symmetry</strong> is also a measure of a clean design. Thus there is no such a thing that works for columns, but does not work for rows and vice versa, or works for one section and does not for another. For example since columns can be resized why not resize in the row header? Even freezing is symetric. If the head of an axis can be frozen why not the tail? A frozen footer may come handy.</p>\r\n<p>The current major limitations are lack of ready matrix editor, cell merging, column/row grouping and hierarchy. But they are coming in the next versions. Look {article swt-matrix-features}{link}here{/link}{/article} for the detail feature list.</p>\r\n<p>The component is released under<strong> dual licence</strong>: commercial one and LGPL for private / non commercial use.</p>\r\n<p>The current stage is <strong>alpha release</strong>.</p>','',1,1,0,1,'2011-03-15 07:00:25',62,'','2011-04-14 08:00:19',62,0,'0000-00-00 00:00:00','2011-03-15 07:00:25','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',71,0,6,'','',0,221,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (10,'Design','swt-matrix-design','','<p>The over arching principle for the matrix widget design is simplicity, that materializes in:</p>\r\n<p>\r\n<ul>\r\n<li>Separation of concerns. The state maintenance has been </li>\r\n</ul>\r\n</p>\r\n<h3>Main concepts</h3>\r\n<p>- Matrix, MatrixAxis</p>\r\n<p>- Cell and line</p>\r\n<p>- AxisLayout</p>\r\n<p>- Index</p>\r\n<p>- Section</p>\r\n<p>- Zone</p>\r\n<p>- Dock</p>\r\n<p>- Painters</p>\r\n<p> </p>\r\n<p> </p>\r\n<p> </p>\r\n<p>Performance</p>\r\n<p>- Loop inversion: loop painters -&gt; loop cells -&gt; paint</p>\r\n<p>- Caching the layout computation results, paint does not recompute widths, distances, visible indexes</p>\r\n<p>- Caching results of GC.stringExtent for single characters of each font used in TextPainter.</p>\r\n<p> </p>\r\n<p>Indexes</p>\r\n<p>model index - original index that numbers the items in the model</p>\r\n<p>order index - position of the model index in the order sequence</p>\r\n<p>visible index - position of the model index in the order seqence after hiding some items</p>\r\n<p>The letter one matches with what the user sees on the screen.</p>\r\n<p> </p>\r\n<p>Selection and reordering is limited to the scope a single section only.</p>\r\n<p> </p>\r\n<p>Freezing</p>\r\n<p> </p>\r\n<p>Command</p>\r\n<p> </p>\r\n<p>Item reordering</p>\r\n<p> </p>\r\n<p>Indexes to selection are interim (position in order sequence ignoring hiding).</p>\r\n<p>This is to naturaly support a scenario of for example selecting extent</p>\r\n<p>from column 4 to column 6 including a hidden column 5 in between.</p>\r\n<p> </p>','',1,1,0,1,'2011-03-15 10:14:57',62,'','2011-04-13 12:27:57',62,62,'2011-04-13 12:27:57','2011-03-15 10:14:57','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',4,0,5,'','',0,5,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (11,'Snippets','snippets','','<p>Snippets are minimal stand-alone programs that demonstrate specific techniques or functionality. Often a small example is the easiest way to understand how to use a particular feature.</p>\r\n<!-- generated start --><p>Layout</p><ul><li><a href=\'swt-matrix/snippets/Snippet_0001.java\'>Create matrix with default model.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0002.java\'>Add / remove model items.</a></li></ul><p>Painters</p><ul><li><a href=\'swt-matrix/snippets/Snippet_0010.java\'>Draw custom background for the whole matrix.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0011.java\'>Draw custom current cell marker.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0012.java\'>Change the line style.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0013.java\'>Gap between cells like HTML table cellspacing attribute. Hide lines.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0014.java\'>Altering row background.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0015.java\'>Current row gradient highlighter.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0016.java\'>Mark selection with SWT.COLOR_LIST... colors.</a></li><li><a href=\'swt-matrix/snippets/Snippet_0017.java\'>Cell background calculated</a></li><li><a href=\'swt-matrix/snippets/Snippet_0018.java\'>Image painting</a></li><li><a href=\'swt-matrix/snippets/Snippet_0019.java\'>Align column to the right.</a></li></ul><p>Scrolling</p><ul><li><a href=\'swt-matrix/snippets/Snippet_0200.java\'>Freeze head and tail</a></li><li><a href=\'swt-matrix/snippets/Snippet_0201.java\'>Freeze head and tail with different color for the dividing line</a></li></ul><p>Other</p><ul><li><a href=\'swt-matrix/snippets/Snippet_0901.java\'>Change gesture binding.</a></li></ul><!-- generated end -->','',1,1,0,1,'2011-03-15 10:17:07',62,'','2011-03-25 19:34:37',62,0,'0000-00-00 00:00:00','2011-03-15 10:17:07','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',6,0,4,'','',0,10,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (12,'Tutorial','tutorial','','<p>{article swt-matrix-design}{url}design{/article}</p>','',-2,1,0,1,'2011-03-15 10:17:35',62,'','2011-03-15 10:54:36',62,0,'0000-00-00 00:00:00','2011-03-15 10:17:35','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',2,0,0,'','',0,0,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (13,'Tutorial','tutorial','','<p>{article swt-matrix-design}<a href=\"{url}\">;design</a>{/article}</p>','',1,1,0,1,'2011-03-15 10:17:35',62,'','2011-03-15 10:56:39',62,0,'0000-00-00 00:00:00','2011-03-15 10:17:35','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',5,0,3,'','',0,6,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (14,'Buy','buy','','<p>The commercial licence is not available for purchase yet. The first release of the product is due on June 30th, 2011.</p>\r\n<p>Your donation now though would be deducted from any licence fees in the future and would speed up the product development.</p>\r\n<p><input alt=\"PayPal - The safer, easier way to pay online!\" name=\"submit\" src=\"https://www.paypalobjects.com/WEBSCR-640-20110401-1/en_US/i/btn/btn_donateCC_LG.gif\" type=\"image\" /> <img src=\"https://www.paypalobjects.com/WEBSCR-640-20110401-1/pl_PL/i/scr/pixel.gif\" border=\"0\" width=\"1\" height=\"1\" /></p>\r\n<p> </p>','',1,1,0,1,'2011-03-17 14:17:45',62,'','2011-04-12 07:55:56',62,0,'0000-00-00 00:00:00','2011-03-17 14:17:45','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',5,0,2,'','',0,23,'robots=\nauthor=');
INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES (15,'FAQ','swt-matrix-faq','','<p>Q. I can\'t find the required method in Matrix class, where is it?</p>\r\n<p>A. Most of the cell related methods are in Zone class. The axis items, which are rows and columns, on the other hand are handled by Section and Axis classes.</p>','',1,1,0,1,'2011-03-27 16:35:28',62,'','0000-00-00 00:00:00',0,0,'0000-00-00 00:00:00','2011-03-27 16:35:28','0000-00-00 00:00:00','','','show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=',1,0,1,'','',0,0,'robots=\nauthor=');
/*!40000 ALTER TABLE `jos_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_content_frontpage`
--

DROP TABLE IF EXISTS `jos_content_frontpage`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_content_frontpage` (
  `content_id` int(11) NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_content_frontpage`
--

LOCK TABLES `jos_content_frontpage` WRITE;
/*!40000 ALTER TABLE `jos_content_frontpage` DISABLE KEYS */;
INSERT INTO `jos_content_frontpage` (`content_id`, `ordering`) VALUES (1,1);
INSERT INTO `jos_content_frontpage` (`content_id`, `ordering`) VALUES (9,2);
/*!40000 ALTER TABLE `jos_content_frontpage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_content_rating`
--

DROP TABLE IF EXISTS `jos_content_rating`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_content_rating` (
  `content_id` int(11) NOT NULL default '0',
  `rating_sum` int(11) unsigned NOT NULL default '0',
  `rating_count` int(11) unsigned NOT NULL default '0',
  `lastip` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_content_rating`
--

LOCK TABLES `jos_content_rating` WRITE;
/*!40000 ALTER TABLE `jos_content_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_content_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_core_acl_aro`
--

DROP TABLE IF EXISTS `jos_core_acl_aro`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_core_acl_aro` (
  `id` int(11) NOT NULL auto_increment,
  `section_value` varchar(240) NOT NULL default '0',
  `value` varchar(240) NOT NULL default '',
  `order_value` int(11) NOT NULL default '0',
  `name` varchar(255) NOT NULL default '',
  `hidden` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `jos_section_value_value_aro` (`section_value`(100),`value`(100)),
  KEY `jos_gacl_hidden_aro` (`hidden`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_core_acl_aro`
--

LOCK TABLES `jos_core_acl_aro` WRITE;
/*!40000 ALTER TABLE `jos_core_acl_aro` DISABLE KEYS */;
INSERT INTO `jos_core_acl_aro` (`id`, `section_value`, `value`, `order_value`, `name`, `hidden`) VALUES (10,'users','62',0,'Administrator',0);
/*!40000 ALTER TABLE `jos_core_acl_aro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_core_acl_aro_groups`
--

DROP TABLE IF EXISTS `jos_core_acl_aro_groups`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_core_acl_aro_groups` (
  `id` int(11) NOT NULL auto_increment,
  `parent_id` int(11) NOT NULL default '0',
  `name` varchar(255) NOT NULL default '',
  `lft` int(11) NOT NULL default '0',
  `rgt` int(11) NOT NULL default '0',
  `value` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`),
  KEY `jos_gacl_parent_id_aro_groups` (`parent_id`),
  KEY `jos_gacl_lft_rgt_aro_groups` (`lft`,`rgt`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_core_acl_aro_groups`
--

LOCK TABLES `jos_core_acl_aro_groups` WRITE;
/*!40000 ALTER TABLE `jos_core_acl_aro_groups` DISABLE KEYS */;
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (17,0,'ROOT',1,22,'ROOT');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (28,17,'USERS',2,21,'USERS');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (29,28,'Public Frontend',3,12,'Public Frontend');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (18,29,'Registered',4,11,'Registered');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (19,18,'Author',5,10,'Author');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (20,19,'Editor',6,9,'Editor');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (21,20,'Publisher',7,8,'Publisher');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (30,28,'Public Backend',13,20,'Public Backend');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (23,30,'Manager',14,19,'Manager');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (24,23,'Administrator',15,18,'Administrator');
INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES (25,24,'Super Administrator',16,17,'Super Administrator');
/*!40000 ALTER TABLE `jos_core_acl_aro_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_core_acl_aro_map`
--

DROP TABLE IF EXISTS `jos_core_acl_aro_map`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_core_acl_aro_map` (
  `acl_id` int(11) NOT NULL default '0',
  `section_value` varchar(230) NOT NULL default '0',
  `value` varchar(100) NOT NULL,
  PRIMARY KEY  (`acl_id`,`section_value`,`value`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_core_acl_aro_map`
--

LOCK TABLES `jos_core_acl_aro_map` WRITE;
/*!40000 ALTER TABLE `jos_core_acl_aro_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_core_acl_aro_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_core_acl_aro_sections`
--

DROP TABLE IF EXISTS `jos_core_acl_aro_sections`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_core_acl_aro_sections` (
  `id` int(11) NOT NULL auto_increment,
  `value` varchar(230) NOT NULL default '',
  `order_value` int(11) NOT NULL default '0',
  `name` varchar(230) NOT NULL default '',
  `hidden` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `jos_gacl_value_aro_sections` (`value`),
  KEY `jos_gacl_hidden_aro_sections` (`hidden`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_core_acl_aro_sections`
--

LOCK TABLES `jos_core_acl_aro_sections` WRITE;
/*!40000 ALTER TABLE `jos_core_acl_aro_sections` DISABLE KEYS */;
INSERT INTO `jos_core_acl_aro_sections` (`id`, `value`, `order_value`, `name`, `hidden`) VALUES (10,'users',1,'Users',0);
/*!40000 ALTER TABLE `jos_core_acl_aro_sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_core_acl_groups_aro_map`
--

DROP TABLE IF EXISTS `jos_core_acl_groups_aro_map`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_core_acl_groups_aro_map` (
  `group_id` int(11) NOT NULL default '0',
  `section_value` varchar(240) NOT NULL default '',
  `aro_id` int(11) NOT NULL default '0',
  UNIQUE KEY `group_id_aro_id_groups_aro_map` (`group_id`,`section_value`,`aro_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_core_acl_groups_aro_map`
--

LOCK TABLES `jos_core_acl_groups_aro_map` WRITE;
/*!40000 ALTER TABLE `jos_core_acl_groups_aro_map` DISABLE KEYS */;
INSERT INTO `jos_core_acl_groups_aro_map` (`group_id`, `section_value`, `aro_id`) VALUES (25,'',10);
/*!40000 ALTER TABLE `jos_core_acl_groups_aro_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_core_log_items`
--

DROP TABLE IF EXISTS `jos_core_log_items`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_core_log_items` (
  `time_stamp` date NOT NULL default '0000-00-00',
  `item_table` varchar(50) NOT NULL default '',
  `item_id` int(11) unsigned NOT NULL default '0',
  `hits` int(11) unsigned NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_core_log_items`
--

LOCK TABLES `jos_core_log_items` WRITE;
/*!40000 ALTER TABLE `jos_core_log_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_core_log_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_core_log_searches`
--

DROP TABLE IF EXISTS `jos_core_log_searches`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_core_log_searches` (
  `search_term` varchar(128) NOT NULL default '',
  `hits` int(11) unsigned NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_core_log_searches`
--

LOCK TABLES `jos_core_log_searches` WRITE;
/*!40000 ALTER TABLE `jos_core_log_searches` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_core_log_searches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_fb_version`
--

DROP TABLE IF EXISTS `jos_fb_version`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_fb_version` (
  `id` int(11) NOT NULL auto_increment,
  `version` varchar(20) NOT NULL,
  `versiondate` date NOT NULL,
  `installdate` date NOT NULL,
  `build` varchar(20) NOT NULL,
  `versionname` varchar(40) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_fb_version`
--

LOCK TABLES `jos_fb_version` WRITE;
/*!40000 ALTER TABLE `jos_fb_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_fb_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_groups`
--

DROP TABLE IF EXISTS `jos_groups`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_groups` (
  `id` tinyint(3) unsigned NOT NULL default '0',
  `name` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_groups`
--

LOCK TABLES `jos_groups` WRITE;
/*!40000 ALTER TABLE `jos_groups` DISABLE KEYS */;
INSERT INTO `jos_groups` (`id`, `name`) VALUES (0,'Public');
INSERT INTO `jos_groups` (`id`, `name`) VALUES (1,'Registered');
INSERT INTO `jos_groups` (`id`, `name`) VALUES (2,'Special');
/*!40000 ALTER TABLE `jos_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_attachments`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_attachments`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_attachments` (
  `id` int(11) NOT NULL auto_increment,
  `note_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `type` text NOT NULL,
  `size` int(11) NOT NULL,
  `content` mediumblob NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_attachments`
--

LOCK TABLES `jos_huruhelpdesk_attachments` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_huruhelpdesk_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_categories`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_categories`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_categories` (
  `category_id` bigint(20) NOT NULL auto_increment,
  `cname` text NOT NULL,
  `rep_id` bigint(20) NOT NULL,
  UNIQUE KEY `category_id` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_categories`
--

LOCK TABLES `jos_huruhelpdesk_categories` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_categories` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_categories` (`category_id`, `cname`, `rep_id`) VALUES (1,'Feature',0);
INSERT INTO `jos_huruhelpdesk_categories` (`category_id`, `cname`, `rep_id`) VALUES (2,'Bug',0);
/*!40000 ALTER TABLE `jos_huruhelpdesk_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_config`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_config`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_config` (
  `id` int(11) NOT NULL,
  `hdreply` text NOT NULL,
  `hdurl` text NOT NULL,
  `notifyuser` int(11) NOT NULL,
  `enablekb` int(11) NOT NULL,
  `defaultpriority` bigint(20) NOT NULL,
  `defaultstatus` bigint(20) NOT NULL,
  `closestatus` bigint(20) NOT NULL,
  `allowanonymous` int(11) NOT NULL,
  `defaultlang` int(11) NOT NULL,
  `pagerpriority` int(11) NOT NULL,
  `userselect` int(11) NOT NULL,
  `version` text NOT NULL,
  `show_username` int(11) NOT NULL,
  `show_email` int(11) NOT NULL,
  `show_department` int(11) NOT NULL,
  `show_location` int(11) NOT NULL,
  `show_phone` int(11) NOT NULL,
  `show_category` int(11) NOT NULL,
  `show_status` int(11) NOT NULL,
  `show_priority` int(11) NOT NULL,
  `show_rep` int(11) NOT NULL,
  `show_timespent` int(11) NOT NULL,
  `set_username` int(11) NOT NULL,
  `set_email` int(11) NOT NULL,
  `set_department` int(11) NOT NULL,
  `set_location` int(11) NOT NULL,
  `set_phone` int(11) NOT NULL,
  `set_category` int(11) NOT NULL,
  `set_status` int(11) NOT NULL,
  `set_priority` int(11) NOT NULL,
  `set_rep` int(11) NOT NULL,
  `set_timespent` int(11) NOT NULL,
  `hdnotifyname` text NOT NULL,
  `defaultdepartment` bigint(20) NOT NULL,
  `defaultcategory` bigint(20) NOT NULL,
  `defaultrep` bigint(20) NOT NULL,
  `fileattach_allow` int(11) NOT NULL,
  `fileattach_allowedextensions` text NOT NULL,
  `fileattach_allowedmimetypes` text NOT NULL,
  `fileattach_maxsize` bigint(20) NOT NULL,
  `fileattach_type` int(11) NOT NULL,
  `fileattach_path` text NOT NULL,
  `fileattach_download` int(11) NOT NULL,
  `fileattach_maxage` int(11) NOT NULL,
  `notifyadminonnewcases` text NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_config`
--

LOCK TABLES `jos_huruhelpdesk_config` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_config` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_config` (`id`, `hdreply`, `hdurl`, `notifyuser`, `enablekb`, `defaultpriority`, `defaultstatus`, `closestatus`, `allowanonymous`, `defaultlang`, `pagerpriority`, `userselect`, `version`, `show_username`, `show_email`, `show_department`, `show_location`, `show_phone`, `show_category`, `show_status`, `show_priority`, `show_rep`, `show_timespent`, `set_username`, `set_email`, `set_department`, `set_location`, `set_phone`, `set_category`, `set_status`, `set_priority`, `set_rep`, `set_timespent`, `hdnotifyname`, `defaultdepartment`, `defaultcategory`, `defaultrep`, `fileattach_allow`, `fileattach_allowedextensions`, `fileattach_allowedmimetypes`, `fileattach_maxsize`, `fileattach_type`, `fileattach_path`, `fileattach_download`, `fileattach_maxage`, `notifyadminonnewcases`) VALUES (1,'info@netanel.pl','http://www.netanel.pl/index.php?option=com_huruhelpdesk&view=cpanel&Itemid=2',1,1,3,15,24,1,1,10,0,'0.88 beta',0,0,10000,0,0,0,0,0,0,0,0,0,0,0,0,0,50,50,50,50,'Netanel Helpdesk',1,1,-1,10000,'.jpg,.png','image/jpeg,image/png',1048576,1,'',0,0,'info@netanel.pl');
/*!40000 ALTER TABLE `jos_huruhelpdesk_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_departments`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_departments`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_departments` (
  `department_id` bigint(20) NOT NULL auto_increment,
  `dname` text NOT NULL,
  UNIQUE KEY `department_id` (`department_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_departments`
--

LOCK TABLES `jos_huruhelpdesk_departments` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_departments` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_departments` (`department_id`, `dname`) VALUES (1,'Testing');
/*!40000 ALTER TABLE `jos_huruhelpdesk_departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_emailmsg`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_emailmsg`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_emailmsg` (
  `id` int(11) NOT NULL auto_increment,
  `type` text NOT NULL,
  `subject` text NOT NULL,
  `body` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_emailmsg`
--

LOCK TABLES `jos_huruhelpdesk_emailmsg` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_emailmsg` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (1,'repclose','HELPDESK: Problem [problemid] Closed','The following problem has been closed.  You can view the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nSOLUTION\r\n--------\r\n[solution]');
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (2,'repnew','HELPDESK: Problem [problemid] Assigned','The following problem has been assigned to you.  You can update the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nUSER INFORMATION\r\n----------------\r\nUsername: [uid]\r\nEmail: [uemail]\r\nPhone: [phone]\r\nLocation: [location]\r\nDepartment: [department]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]');
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (3,'reppager','HELPDESK: Problem [problemid] Assigned/Updated','Title:[title]\r\nUser:[uid]\r\nPriority:[priority]');
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (4,'repupdate','HELPDESK: Problem [problemid] Updated','The following problem has been updated.  You can view the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]\r\n\r\nNOTES\r\n-----------\r\n[notes]');
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (5,'userclose','HELPDESK: Problem [problemid] Closed','Your help desk problem has been closed.  You can view the solution below or at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nSOLUTION\r\n--------\r\n[solution]');
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (6,'usernew','HELPDESK: Problem [problemid] Created','Thank you for submitting your problem to the help desk.  You can view or update the problem at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]');
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (7,'userupdate','HELPDESK: Problem [problemid] Updated','Your help desk problem has been updated.  You can view the problem at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]\r\n\r\nNOTES\r\n-----------\r\n[notes]');
INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES (8,'adminnew','HELPDESK: Problem [problemid] Created','The following problem has been created.  You can update the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nUSER INFORMATION\r\n----------------\r\nFullname: [fullname]\r\nUsername: [uid]\r\nEmail: [uemail]\r\nPhone: [phone]\r\nLocation: [location]\r\nDepartment: [department]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]');
/*!40000 ALTER TABLE `jos_huruhelpdesk_emailmsg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_langstrings`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_langstrings`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_langstrings` (
  `id` int(11) NOT NULL auto_increment,
  `lang_id` bigint(20) NOT NULL,
  `variable` text NOT NULL,
  `langtext` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=581 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_langstrings`
--

LOCK TABLES `jos_huruhelpdesk_langstrings` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_langstrings` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (54,1,'Classification','Classification');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (59,1,'Close','Close');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (60,1,'CloseDate','Close Date');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (72,1,'ContactInformation','Contact Information');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (79,1,'DateSubmitted','Entered');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (91,1,'Department','Department');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (95,1,'Description','Description');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (104,1,'EditInformation','Edit your contact information');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (108,1,'EMail','E-Mail');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (109,1,'EmailAddress','E-Mail Address');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (120,1,'EndDate','End Date');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (121,1,'EnterAdditionalNotes','Enter Additional Notes');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (125,1,'EnteredBy','Entered By');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (126,1,'EnterinKnowledgeBase','Enter in Knowledge Base');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (135,1,'From','From');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (142,1,'HideFromEndUser','Hide new note from end user');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (144,1,'ID','ID');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (146,1,'In','In');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (148,1,'InOutBoard','View In/Out Board');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (174,1,'Location','Location');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (191,1,'minutes','minutes');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (199,1,'NewProblem','New Problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (207,1,'Noresultsfound','No matching problems found');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (210,1,'Notes','Notes');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (218,1,'OpenProblems','Open problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (219,1,'OpenProblemsfor','Open problems for');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (222,1,'Or','Or');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (225,1,'Out','Out');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (237,1,'Phone','Phone');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (248,1,'Priority','Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (257,1,'ProblemID','View Problem #');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (259,1,'ProblemInformation','Problem Information');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (263,1,'Problems','Problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (277,1,'ReopenProblem','Reopen');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (278,1,'Rep','Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (282,1,'Reports','Reports');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (286,1,'Required','Required');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (292,1,'Save','Save');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (294,1,'Search','Search');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (296,1,'SearchFields','Search Fields');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (297,1,'SearchProblems','Search problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (298,1,'SearchResults','Search Results');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (299,1,'SearchtheKnowledgeBase','Search the knowledgebase');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (302,1,'SelectCategory','Select Category');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (303,1,'SelectDepartment','Select Department');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (304,1,'SelectUser','Select User');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (31,1,'AssignedTo','Assigned To');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (313,1,'Solution','Solution');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (317,1,'StartDate','Start Date');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (319,1,'Status','Status');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (329,1,'Subject','Subject');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (330,1,'Submit','Submit');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (332,1,'SubmitNewProblem','Submit new problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (335,1,'SupportRep','Support Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (352,1,'Time','Time');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (353,1,'TimeSpent','Time Spent');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (354,1,'Title','Title');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (356,1,'Total','Total');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (373,1,'User','User');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (376,1,'UserName','User Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (385,1,'View','View');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (386,1,'ViewProblemList','View all open problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (387,1,'Viewproblemsfor','View open problems assigned to');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (394,1,'ViewAssignedProblems','View assigned problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (395,1,'ViewSubmittedProblems','View submitted problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (397,1,'ViewProblemsFromLast','View all problems from last');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (398,1,'days','days');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (399,1,'Activity','Activity');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (400,1,'Home','Home');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (401,1,'Refresh','Refresh');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (402,1,'NoLimit','(No limit)');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (403,1,'Back','Back');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (404,1,'ProblemNumber','Problem #');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (405,1,'ProblemSaved','Problem saved');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (406,1,'ErrorSavingProblem','Error saving record: Invalid or missing required fields.');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (409,1,'DefaultRep','Default Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (410,1,'NotFound','No matching problem found');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (411,1,'EnterVerification','Verification');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (412,1,'Name','Full Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (413,1,'Admin','Admin');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (414,1,'ShowReps','Show Reps');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (415,1,'ShowAll','Show All');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (416,1,'RepsAdmins','Reps & Admins Only');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (417,1,'AllUsers','All Huru Users');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (418,1,'SearchCriteria','Search Criteria');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (419,1,'Reset','Reset');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (421,1,'To','To');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (422,1,'SearchText','Search Text');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (423,1,'Browse','Browse');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (424,1,'Cancel','Cancel');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (425,1,'NewSearch','New Search');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (426,1,'Results','Results');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (427,1,'ProblemsFound','problem(s) found');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (428,1,'EnterSearch','Enter your search criteria & click the Search button');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (429,1,'EnterReport','Enter your report criteria & click the View button');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (430,1,'AvailableReports','Available Reports');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (431,1,'DateRange','Date Range');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (432,1,'AverageTime','Average Time');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (433,1,'PercentProblemTotal','% of Problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (434,1,'PercentTimeTotal','% of Time');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (435,1,'min','min');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (436,1,'Unknown','Unknown');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (437,1,'ActivitySummary','MIS Activity Summary');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (438,1,'Modified','Modified');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (439,1,'through','through');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (440,1,'MailProblemID','Problem Id Number');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (441,1,'MailTitle','Problem Title/Subject');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (442,1,'MailDescription','Problem Description');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (443,1,'MailUID','Username of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (444,1,'MailUEmail','Email address of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (445,1,'MailPhone','Phone number of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (446,1,'MailLocation','Location of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (447,1,'MailDepartment','Department of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (448,1,'MailPriority','Priority of problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (449,1,'MailCategory','Category of problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (45,1,'Category','Category');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (450,1,'MailStartDate','Date problem was reported/opened');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (451,1,'MailURL','URL to problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (452,1,'MailSolution','Problem solution');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (453,1,'MailNotes','Notes about problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (454,1,'ProblemsSubmittedBy','Problems submitted by');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (456,1,'for','for');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (457,1,'ForPrevious','for previous');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (458,1,'All','All');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (459,1,'OpenProblemsLC','open problems');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (460,1,'Print','Print');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (461,1,'UserProfile','User Profile');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (462,1,'JoomlaUserInfo','Joomla! user information');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (463,1,'HuruUserInfo','Helpdesk User Profile');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (464,1,'HomePhone','Home Phone');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (465,1,'MobilePhone','Mobile Phone');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (466,1,'PagerAddress','Pager Address');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (467,1,'Location1','Location 1');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (468,1,'Location2','Location 2');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (469,1,'Language','Language');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (470,1,'ManageCategories','Manage Categories');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (471,1,'EditCategory','Edit Category');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (472,1,'CategoryName','Category Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (473,1,'Default','Default');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (474,1,'GeneralConfiguration','General Configuration');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (475,1,'ReplyAddress','Reply Address');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (476,1,'BaseURL','Helpdesk Base URL');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (477,1,'NotifyUserOnCaseUpdate','Notify User on Case Update');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (478,1,'AllowAnonymousCases','Allow Anonymous Cases');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (479,1,'AllowUserSelectOnNewCases','Allow User Select on New Cases');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (480,1,'KnowledgeBaseViewAuthority','Knowledgebase View Authority');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (481,1,'Disable','Disable');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (482,1,'RepsOnly','Reps Only');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (483,1,'UsersAndReps','Users & Reps');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (484,1,'Anyone','Anyone');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (485,1,'DefaultPriority','Default Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (486,1,'PagerPriority','Pager Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (487,1,'DefaultStatus','Default Status');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (488,1,'ClosedStatus','Closed Status');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (489,1,'DefaultLanguage','Default Language');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (490,1,'EmailMessages','Email Messages');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (491,1,'Users','Users');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (492,1,'Departments','Departments');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (493,1,'Categories','Categories');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (494,1,'Priorities','Priorities');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (495,1,'Statuses','Statuses');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (496,1,'Languages','Languages');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (497,1,'About','About');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (498,1,'Administration','Administration');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (499,1,'ManageDepartments','Manage Departments');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (500,1,'DepartmentName','Department Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (501,1,'EditDepartment','Edit Department');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (502,1,'ManageEmailMessages','Manage Email Messages');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (503,1,'Type','Type');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (504,1,'Body','Body');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (505,1,'Edit...','Edit...');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (506,1,'EditEmailMessage','Edit Email Message');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (507,1,'AvailableSubstitutions','Available Substitutions');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (508,1,'ManageLanguages','Manage Languages');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (509,1,'LanguageName','Language Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (510,1,'Localized','Localized');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (511,1,'EditLanguage','Edit Language');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (512,1,'LanguageStrings','Language Strings');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (513,1,'ManagePriorities','Manage Priorities');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (514,1,'PriorityName','Priority Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (515,1,'EditPriority','Edit Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (516,1,'ManageStatuses','Manage Statuses');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (517,1,'Rank','Rank');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (518,1,'StatusName','Status Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (519,1,'EditStatus','Edit Status');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (520,1,'ManageLanguageStrings','Manage Language Strings');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (521,1,'LanguageID','Language ID');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (522,1,'Variable','Variable');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (523,1,'Text','Text');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (524,1,'EditString','Edit String');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (525,1,'ManageUsers','Manage Users');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (526,1,'HuruID','Huru ID');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (527,1,'JoomlaID','Joomla! ID');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (528,1,'SyncJoomlaUsers','Sync Joomla! Users');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (529,1,'SyncJoomlaUsersConfirm','This will synchronize the Huru users table with the Joomla! users table - importing accounts into Huru as necessary.  No Joomla! user accounts will be altered.');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (530,1,'EditUser','Edit User');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (531,1,'IsUser','User');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (532,1,'IsRep','Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (533,1,'IsAdmin','Administrator');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (534,1,'ViewReports','View Reports');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (535,1,'UserSuperAdminNote','(Note: This setting is ignored for Joomla! Super Administrators - who are always Huru Helpdesk Admins)');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (536,1,'DefaultAssignment','Category Default');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (537,1,'PageTitle','Huru Helpdesk');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (538,1,'SelectOverride','Overrides contact info above');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (539,1,'CannotDeleteClosedStatus','Cannot delete status set as Closed Status in General Confguration');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (540,1,'Go','Go');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (541,1,'ProblemDeleted','Problem deleted');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (542,1,'ProblemNotDeleted','Error deleting problem');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (543,1,'DeleteProblem','Delete Problem #');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (544,1,'Delete','Delete');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (545,1,'ProblemCreated','Problem created');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (546,1,'AttachFileToNote','Attach file to note');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (547,1,'Attachment','Attachment');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (548,1,'AttachmentFileNoteFound','Attachment file not found');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (549,1,'DefaultFileAttachmentNote','File attached');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (550,1,'ErrorSavingAttachment','Error saving attachment');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (551,1,'NotImplemented','Not implemented');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (552,1,'FileTypeNotAllowed','File type not allowed');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (553,1,'FileTooLarge','File too large');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (554,1,'UnknownError','Unknown error');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (555,1,'NotificationSenderName','Notification Sender Name');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (556,1,'AllowFileAttachments','Allow File Attachments to Cases');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (557,1,'AllowedAttachmentExtensions','Allowed Attachment Extensions');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (558,1,'ExtensionExample','Comma separated list of allowed file extensions (with leading periods) [e.g: .jpg,.png,.txt]');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (559,1,'MaximumAttachmentSize','Maximum Attachment Size');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (560,1,'Bytes','bytes');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (561,1,'AttachmentSizeWarning','Huru maximum is 16MB.  PHP may be configured for less.  Check your php.ini file');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (562,1,'AttachmentDownloadPermissions','Allow attachment download');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (563,1,'AttachmentDeleted','Attachment deleted');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (564,1,'AttachmentNotDeleted','Error deleting attachment');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (565,1,'MaximumAttachmentAge','Auto-purge old attachments after');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (566,1,'SetToZero','Set to 0 to disable auto-purge');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (567,1,'MailFullname','Full name of user who entered case (from Joomla account)');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (568,1,'NotifyAdminOnNewCase','Email address to notify for all new cases');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (569,1,'LeaveBlank','Leave blank to disable');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (570,1,'Notifications','Notifications');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (571,1,'Permissions','Permissions');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (572,1,'Defaults','Defaults');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (573,1,'FileAttachments','File Attachments');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (574,1,'DisplayedFields','Displayed Fields');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (575,1,'DefaultDepartment','Default Department');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (576,1,'DefaultCategory','Default Category');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (577,1,'Show','Show');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (578,1,'Set','Set');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (579,1,'IfNotSetable','If not visible/setable by everyone, a default for this field must be set above');
INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES (580,1,'Updated','Updated');
/*!40000 ALTER TABLE `jos_huruhelpdesk_langstrings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_language`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_language`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_language` (
  `id` bigint(20) NOT NULL auto_increment,
  `langname` text NOT NULL,
  `localized` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_language`
--

LOCK TABLES `jos_huruhelpdesk_language` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_language` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_language` (`id`, `langname`, `localized`) VALUES (1,'English','English');
/*!40000 ALTER TABLE `jos_huruhelpdesk_language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_notes`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_notes`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_notes` (
  `note_id` int(11) NOT NULL auto_increment,
  `id` bigint(20) NOT NULL,
  `note` longtext NOT NULL,
  `adddate` datetime NOT NULL,
  `uid` text NOT NULL,
  `ip` text,
  `priv` int(11) NOT NULL,
  PRIMARY KEY  (`note_id`),
  FULLTEXT KEY `note` (`note`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_notes`
--

LOCK TABLES `jos_huruhelpdesk_notes` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_notes` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_notes` (`note_id`, `id`, `note`, `adddate`, `uid`, `ip`, `priv`) VALUES (1,1,'Problem created','2011-04-12 10:25:40','','127.0.0.1',1);
INSERT INTO `jos_huruhelpdesk_notes` (`note_id`, `id`, `note`, `adddate`, `uid`, `ip`, `priv`) VALUES (2,2,'Problem created','2011-04-12 10:26:42','','127.0.0.1',1);
INSERT INTO `jos_huruhelpdesk_notes` (`note_id`, `id`, `note`, `adddate`, `uid`, `ip`, `priv`) VALUES (3,3,'Problem created','2011-04-12 10:28:52','','127.0.0.1',1);
/*!40000 ALTER TABLE `jos_huruhelpdesk_notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_priority`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_priority`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_priority` (
  `priority_id` bigint(20) NOT NULL auto_increment,
  `pname` text NOT NULL,
  UNIQUE KEY `priority_id` (`priority_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_priority`
--

LOCK TABLES `jos_huruhelpdesk_priority` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_priority` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (6,' 6 - VERY HIGH ');
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (5,' 5 - HIGH ');
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (4,' 4 - ELEVATED ');
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (3,' 3 - NORMAL ');
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (2,' 2 - LOW ');
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (1,'1 - VERY LOW');
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (10,' 10 - EMERGENCY - PAGE ');
INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES (9,' 9 - EMERGENCY - NO PAGE ');
/*!40000 ALTER TABLE `jos_huruhelpdesk_priority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_problems`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_problems`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_problems` (
  `id` bigint(20) NOT NULL auto_increment,
  `uid` text NOT NULL,
  `uemail` text NOT NULL,
  `ulocation` text NOT NULL,
  `uphone` text NOT NULL,
  `rep` bigint(20) NOT NULL,
  `status` bigint(20) NOT NULL,
  `time_spent` bigint(20) NOT NULL,
  `category` bigint(20) NOT NULL,
  `close_date` datetime NOT NULL,
  `department` bigint(20) NOT NULL,
  `title` text NOT NULL,
  `description` text NOT NULL,
  `solution` text NOT NULL,
  `start_date` datetime NOT NULL,
  `priority` bigint(20) NOT NULL,
  `entered_by` bigint(20) NOT NULL,
  `kb` bigint(20) NOT NULL,
  UNIQUE KEY `id` (`id`),
  KEY `rep` (`rep`,`status`,`category`,`department`,`priority`),
  FULLTEXT KEY `solution` (`solution`),
  FULLTEXT KEY `description` (`description`),
  FULLTEXT KEY `title` (`title`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_problems`
--

LOCK TABLES `jos_huruhelpdesk_problems` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_problems` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_problems` (`id`, `uid`, `uemail`, `ulocation`, `uphone`, `rep`, `status`, `time_spent`, `category`, `close_date`, `department`, `title`, `description`, `solution`, `start_date`, `priority`, `entered_by`, `kb`) VALUES (1,'Jacek','jacek.p.kolodziejczyk@gmail.com','','',0,15,0,1,'0000-00-00 00:00:00',1,'test','asdfasdf','','2011-04-12 10:25:40',3,0,0);
INSERT INTO `jos_huruhelpdesk_problems` (`id`, `uid`, `uemail`, `ulocation`, `uphone`, `rep`, `status`, `time_spent`, `category`, `close_date`, `department`, `title`, `description`, `solution`, `start_date`, `priority`, `entered_by`, `kb`) VALUES (2,'a','jacek.p.kolodziejczyk@gmail.com','Kraków','6132662',0,15,0,1,'0000-00-00 00:00:00',1,'test2','a','','2011-04-12 10:26:42',3,0,0);
INSERT INTO `jos_huruhelpdesk_problems` (`id`, `uid`, `uemail`, `ulocation`, `uphone`, `rep`, `status`, `time_spent`, `category`, `close_date`, `department`, `title`, `description`, `solution`, `start_date`, `priority`, `entered_by`, `kb`) VALUES (3,'a','jacek.p.kolodziejczyk@gmail.com','Kraków','6132662',0,15,0,1,'0000-00-00 00:00:00',1,'test3','asdfasdf','','2011-04-12 10:28:52',3,0,0);
/*!40000 ALTER TABLE `jos_huruhelpdesk_problems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_status`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_status`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_status` (
  `id` int(11) NOT NULL auto_increment,
  `status_id` bigint(20) NOT NULL,
  `sname` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_status`
--

LOCK TABLES `jos_huruhelpdesk_status` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_status` DISABLE KEYS */;
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (22,65,'TESTING');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (21,63,'WAITING');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (20,60,'HOLD');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (19,55,'ESCALATED');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (18,50,'IN PROGRESS');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (17,20,'OPEN');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (16,10,'RECEIVED');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (15,1,'NEW');
INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES (24,100,'CLOSED');
/*!40000 ALTER TABLE `jos_huruhelpdesk_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_huruhelpdesk_users`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_users`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_huruhelpdesk_users` (
  `id` int(11) NOT NULL auto_increment,
  `joomla_id` int(11) NOT NULL,
  `isuser` int(11) NOT NULL,
  `isrep` int(11) NOT NULL,
  `isadmin` int(11) NOT NULL,
  `phone` text NOT NULL,
  `pageraddress` text NOT NULL,
  `phonemobile` text NOT NULL,
  `phonehome` text NOT NULL,
  `location1` text NOT NULL,
  `location2` text NOT NULL,
  `department` bigint(20) NOT NULL,
  `language` bigint(20) NOT NULL,
  `viewreports` int(11) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_huruhelpdesk_users`
--

LOCK TABLES `jos_huruhelpdesk_users` WRITE;
/*!40000 ALTER TABLE `jos_huruhelpdesk_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_huruhelpdesk_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_menu`
--

DROP TABLE IF EXISTS `jos_menu`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_menu` (
  `id` int(11) NOT NULL auto_increment,
  `menutype` varchar(75) default NULL,
  `name` varchar(255) default NULL,
  `alias` varchar(255) NOT NULL default '',
  `link` text,
  `type` varchar(50) NOT NULL default '',
  `published` tinyint(1) NOT NULL default '0',
  `parent` int(11) unsigned NOT NULL default '0',
  `componentid` int(11) unsigned NOT NULL default '0',
  `sublevel` int(11) default '0',
  `ordering` int(11) default '0',
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `pollid` int(11) NOT NULL default '0',
  `browserNav` tinyint(4) default '0',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `utaccess` tinyint(3) unsigned NOT NULL default '0',
  `params` text NOT NULL,
  `lft` int(11) unsigned NOT NULL default '0',
  `rgt` int(11) unsigned NOT NULL default '0',
  `home` int(1) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `componentid` (`componentid`,`menutype`,`published`,`access`),
  KEY `menutype` (`menutype`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_menu`
--

LOCK TABLES `jos_menu` WRITE;
/*!40000 ALTER TABLE `jos_menu` DISABLE KEYS */;
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (1,'mainmenu','Home','home','index.php?option=com_content&view=frontpage','component',1,0,20,0,1,0,'0000-00-00 00:00:00',0,0,0,3,'num_leading_articles=1\nnum_intro_articles=4\nnum_columns=2\nnum_links=4\norderby_pri=\norderby_sec=front\nmulti_column_order=1\nshow_pagination=2\nshow_pagination_results=1\nshow_feed_link=1\nshow_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=0\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n',0,0,1);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (2,'mainmenu','Issue Tracker','issuetracker','index.php?option=com_huruhelpdesk&view=cpanel','component',1,6,34,1,1,0,'0000-00-00 00:00:00',0,0,0,0,'page_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (3,'mainmenu','Products','products','index.php?option=com_content&view=article&id=9','component',1,0,20,0,2,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (4,'mainmenu','SWT Matrix','swtmatrix','index.php?option=com_content&view=article&id=9','component',1,3,20,1,1,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=table.png\nsecure=0\nmega_showtitle=1\nmega_desc=Limits breaking tabular widget\nmega_cols=1\nmega_group=1\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (5,'mainmenu','Download','download','index.php?option=com_content&view=article&id=4','component',1,0,20,0,3,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (6,'mainmenu','Support','support','','separator',1,0,0,0,5,0,'0000-00-00 00:00:00',0,0,0,0,'menu_image=-1\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (7,'mainmenu','Features','swtmatrixfeatures','index.php?option=com_content&view=article&id=5','component',1,4,20,2,2,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (8,'SWT-Matrix','Features','features','index.php?Itemid=7','menulink',1,0,0,0,1,0,'0000-00-00 00:00:00',0,0,0,0,'menu_item=7\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (11,'mainmenu','Buy / Donate','swt-matrix-buy','index.php?option=com_content&view=article&id=14','component',1,0,20,0,4,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (12,'mainmenu','Contact','contact','index.php?option=com_content&view=article&id=8','component',1,0,20,0,6,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (9,'SWT-Matrix','API','api','index.php?Itemid=10','menulink',1,0,0,0,2,0,'0000-00-00 00:00:00',0,0,0,0,'menu_item=10\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (14,'mainmenu','Design','design','index.php?option=com_content&view=article&id=10','component',0,4,20,2,3,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (10,'mainmenu','API','api','swt-matrix\\javadoc','url',1,4,0,2,4,0,'0000-00-00 00:00:00',0,1,0,0,'menu_image=-1\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (13,'mainmenu','Overview','overview','index.php?option=com_content&view=article&id=9','component',1,4,20,2,1,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (15,'mainmenu','Tutorial','tutorial','index.php?option=com_content&view=article&id=13','component',1,4,20,2,5,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (16,'mainmenu','Snippets','snippets','index.php?option=com_content&view=article&id=11','component',1,4,20,2,6,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (17,'Hidden','Design','design','index.php?option=com_content&view=article&id=10','component',1,0,20,0,1,0,'0000-00-00 00:00:00',0,0,0,0,'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n',0,0,0);
INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES (18,'mainmenu','Forum','forum','http://discussions.zoho.com/swtmatrix/','url',1,6,0,1,2,0,'0000-00-00 00:00:00',0,1,0,0,'menu_image=-1\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n',0,0,0);
/*!40000 ALTER TABLE `jos_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_menu_types`
--

DROP TABLE IF EXISTS `jos_menu_types`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_menu_types` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `menutype` varchar(75) NOT NULL default '',
  `title` varchar(255) NOT NULL default '',
  `description` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `menutype` (`menutype`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_menu_types`
--

LOCK TABLES `jos_menu_types` WRITE;
/*!40000 ALTER TABLE `jos_menu_types` DISABLE KEYS */;
INSERT INTO `jos_menu_types` (`id`, `menutype`, `title`, `description`) VALUES (1,'mainmenu','Main Menu','The main menu for the site');
INSERT INTO `jos_menu_types` (`id`, `menutype`, `title`, `description`) VALUES (2,'SWT-Matrix','SWT Matrix','');
INSERT INTO `jos_menu_types` (`id`, `menutype`, `title`, `description`) VALUES (3,'Hidden','HIdden','for article links');
/*!40000 ALTER TABLE `jos_menu_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_messages`
--

DROP TABLE IF EXISTS `jos_messages`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_messages` (
  `message_id` int(10) unsigned NOT NULL auto_increment,
  `user_id_from` int(10) unsigned NOT NULL default '0',
  `user_id_to` int(10) unsigned NOT NULL default '0',
  `folder_id` int(10) unsigned NOT NULL default '0',
  `date_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `state` int(11) NOT NULL default '0',
  `priority` int(1) unsigned NOT NULL default '0',
  `subject` text NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY  (`message_id`),
  KEY `useridto_state` (`user_id_to`,`state`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_messages`
--

LOCK TABLES `jos_messages` WRITE;
/*!40000 ALTER TABLE `jos_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_messages_cfg`
--

DROP TABLE IF EXISTS `jos_messages_cfg`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_messages_cfg` (
  `user_id` int(10) unsigned NOT NULL default '0',
  `cfg_name` varchar(100) NOT NULL default '',
  `cfg_value` varchar(255) NOT NULL default '',
  UNIQUE KEY `idx_user_var_name` (`user_id`,`cfg_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_messages_cfg`
--

LOCK TABLES `jos_messages_cfg` WRITE;
/*!40000 ALTER TABLE `jos_messages_cfg` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_messages_cfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_migration_backlinks`
--

DROP TABLE IF EXISTS `jos_migration_backlinks`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_migration_backlinks` (
  `itemid` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` text NOT NULL,
  `sefurl` text NOT NULL,
  `newurl` text NOT NULL,
  PRIMARY KEY  (`itemid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_migration_backlinks`
--

LOCK TABLES `jos_migration_backlinks` WRITE;
/*!40000 ALTER TABLE `jos_migration_backlinks` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_migration_backlinks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_modules`
--

DROP TABLE IF EXISTS `jos_modules`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_modules` (
  `id` int(11) NOT NULL auto_increment,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `ordering` int(11) NOT NULL default '0',
  `position` varchar(50) default NULL,
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `published` tinyint(1) NOT NULL default '0',
  `module` varchar(50) default NULL,
  `numnews` int(11) NOT NULL default '0',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `showtitle` tinyint(3) unsigned NOT NULL default '1',
  `params` text NOT NULL,
  `iscore` tinyint(4) NOT NULL default '0',
  `client_id` tinyint(4) NOT NULL default '0',
  `control` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `published` (`published`,`access`),
  KEY `newsfeeds` (`module`,`published`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_modules`
--

LOCK TABLES `jos_modules` WRITE;
/*!40000 ALTER TABLE `jos_modules` DISABLE KEYS */;
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (1,'Main Menu','',0,'left',0,'0000-00-00 00:00:00',0,'mod_mainmenu',0,0,1,'menutype=mainmenu\nmenu_style=list_flat\nstartLevel=0\nendLevel=0\nshowAllChildren=0\nwindow_open=\nshow_whitespace=0\ncache=1\ntag_id=\nclass_sfx=\nmoduleclass_sfx=_menu\nmaxdepth=10\nmenu_images=0\nmenu_images_align=0\nmenu_images_link=0\nexpand_menu=0\nactivate_parent=0\nfull_active_id=0\nindent_image=0\nindent_image1=\nindent_image2=\nindent_image3=\nindent_image4=\nindent_image5=\nindent_image6=\nspacer=\nend_spacer=\n\n',1,0,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (2,'Login','',1,'login',0,'0000-00-00 00:00:00',1,'mod_login',0,0,1,'',1,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (3,'Popular','',3,'cpanel',0,'0000-00-00 00:00:00',1,'mod_popular',0,2,1,'',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (4,'Recent added Articles','',4,'cpanel',0,'0000-00-00 00:00:00',1,'mod_latest',0,2,1,'ordering=c_dsc\nuser_id=0\ncache=0\n\n',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (5,'Menu Stats','',5,'cpanel',0,'0000-00-00 00:00:00',1,'mod_stats',0,2,1,'',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (6,'Unread Messages','',1,'header',0,'0000-00-00 00:00:00',1,'mod_unread',0,2,1,'',1,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (7,'Online Users','',2,'header',0,'0000-00-00 00:00:00',1,'mod_online',0,2,1,'',1,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (8,'Toolbar','',1,'toolbar',0,'0000-00-00 00:00:00',1,'mod_toolbar',0,2,1,'',1,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (9,'Quick Icons','',1,'icon',0,'0000-00-00 00:00:00',1,'mod_quickicon',0,2,1,'',1,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (10,'Logged in Users','',2,'cpanel',0,'0000-00-00 00:00:00',1,'mod_logged',0,2,1,'',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (11,'Footer','',0,'footer',0,'0000-00-00 00:00:00',1,'mod_footer',0,0,1,'',1,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (12,'Admin Menu','',1,'menu',0,'0000-00-00 00:00:00',1,'mod_menu',0,2,1,'',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (13,'Admin SubMenu','',1,'submenu',0,'0000-00-00 00:00:00',1,'mod_submenu',0,2,1,'',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (14,'User Status','',1,'status',0,'0000-00-00 00:00:00',1,'mod_status',0,2,1,'',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (15,'Title','',1,'title',0,'0000-00-00 00:00:00',1,'mod_title',0,2,1,'',0,1,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (16,'breadcrumbs','',0,'breadcrumb',0,'0000-00-00 00:00:00',1,'mod_breadcrumbs',0,0,1,'showHome=0\nhomeText=\nshowLast=1\nseparator=\nmoduleclass_sfx=\ncache=0\n\n',0,0,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (17,'SWT Matrix Menu','',0,'menu',0,'0000-00-00 00:00:00',0,'mod_mainmenu',0,0,1,'menutype=SWT-Matrix\nmenu_style=list\nstartLevel=0\nendLevel=0\nshowAllChildren=0\nwindow_open=\nshow_whitespace=0\ncache=1\ntag_id=\nclass_sfx=\nmoduleclass_sfx=\nmaxdepth=10\nmenu_images=0\nmenu_images_align=0\nmenu_images_link=0\nexpand_menu=0\nactivate_parent=0\nfull_active_id=0\nindent_image=0\nindent_image1=\nindent_image2=\nindent_image3=\nindent_image4=\nindent_image5=\nindent_image6=\nspacer=\nend_spacer=\n\n',0,0,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (18,'Search','',0,'search',0,'0000-00-00 00:00:00',1,'mod_search',0,0,0,'moduleclass_sfx=\nwidth=20\ntext=search\nbutton=\nbutton_pos=right\nimagebutton=\nbutton_text=\nset_itemid=\ncache=1\ncache_time=900\n\n',0,0,'');
INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES (19,'Login','',1,'menu',0,'0000-00-00 00:00:00',1,'mod_login',0,0,1,'cache=0\nmoduleclass_sfx=\npretext=\nposttext=\nlogin=\nlogout=\ngreeting=1\nname=0\nusesecure=0\n\n',0,0,'');
/*!40000 ALTER TABLE `jos_modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_modules_menu`
--

DROP TABLE IF EXISTS `jos_modules_menu`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_modules_menu` (
  `moduleid` int(11) NOT NULL default '0',
  `menuid` int(11) NOT NULL default '0',
  PRIMARY KEY  (`moduleid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_modules_menu`
--

LOCK TABLES `jos_modules_menu` WRITE;
/*!40000 ALTER TABLE `jos_modules_menu` DISABLE KEYS */;
INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES (1,0);
INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES (16,0);
INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES (17,4);
INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES (17,7);
INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES (17,10);
INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES (18,0);
INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES (19,0);
/*!40000 ALTER TABLE `jos_modules_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_newsfeeds`
--

DROP TABLE IF EXISTS `jos_newsfeeds`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_newsfeeds` (
  `catid` int(11) NOT NULL default '0',
  `id` int(11) NOT NULL auto_increment,
  `name` text NOT NULL,
  `alias` varchar(255) NOT NULL default '',
  `link` text NOT NULL,
  `filename` varchar(200) default NULL,
  `published` tinyint(1) NOT NULL default '0',
  `numarticles` int(11) unsigned NOT NULL default '1',
  `cache_time` int(11) unsigned NOT NULL default '3600',
  `checked_out` tinyint(3) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL default '0',
  `rtl` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `published` (`published`),
  KEY `catid` (`catid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_newsfeeds`
--

LOCK TABLES `jos_newsfeeds` WRITE;
/*!40000 ALTER TABLE `jos_newsfeeds` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_newsfeeds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_plugins`
--

DROP TABLE IF EXISTS `jos_plugins`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_plugins` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL default '',
  `element` varchar(100) NOT NULL default '',
  `folder` varchar(100) NOT NULL default '',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  `published` tinyint(3) NOT NULL default '0',
  `iscore` tinyint(3) NOT NULL default '0',
  `client_id` tinyint(3) NOT NULL default '0',
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_folder` (`published`,`client_id`,`access`,`folder`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_plugins`
--

LOCK TABLES `jos_plugins` WRITE;
/*!40000 ALTER TABLE `jos_plugins` DISABLE KEYS */;
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (1,'Authentication - Joomla','joomla','authentication',0,1,1,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (2,'Authentication - LDAP','ldap','authentication',0,2,0,1,0,0,'0000-00-00 00:00:00','host=\nport=389\nuse_ldapV3=0\nnegotiate_tls=0\nno_referrals=0\nauth_method=bind\nbase_dn=\nsearch_string=\nusers_dn=\nusername=\npassword=\nldap_fullname=fullName\nldap_email=mail\nldap_uid=uid\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (3,'Authentication - GMail','gmail','authentication',0,4,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (4,'Authentication - OpenID','openid','authentication',0,3,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (5,'User - Joomla!','joomla','user',0,0,1,0,0,0,'0000-00-00 00:00:00','autoregister=1\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (6,'Search - Content','content','search',0,1,1,1,0,0,'0000-00-00 00:00:00','search_limit=50\nsearch_content=1\nsearch_uncategorised=1\nsearch_archived=1\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (7,'Search - Contacts','contacts','search',0,3,1,1,0,0,'0000-00-00 00:00:00','search_limit=50\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (8,'Search - Categories','categories','search',0,4,1,0,0,0,'0000-00-00 00:00:00','search_limit=50\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (9,'Search - Sections','sections','search',0,5,1,0,0,0,'0000-00-00 00:00:00','search_limit=50\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (10,'Search - Newsfeeds','newsfeeds','search',0,6,1,0,0,0,'0000-00-00 00:00:00','search_limit=50\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (11,'Search - Weblinks','weblinks','search',0,2,1,1,0,0,'0000-00-00 00:00:00','search_limit=50\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (12,'Content - Pagebreak','pagebreak','content',0,10000,1,1,0,0,'0000-00-00 00:00:00','enabled=1\ntitle=1\nmultipage_toc=1\nshowall=1\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (13,'Content - Rating','vote','content',0,4,1,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (14,'Content - Email Cloaking','emailcloak','content',0,5,1,0,0,0,'0000-00-00 00:00:00','mode=1\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (15,'Content - Code Hightlighter (GeSHi)','geshi','content',0,5,0,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (16,'Content - Load Module','loadmodule','content',0,6,1,0,0,0,'0000-00-00 00:00:00','enabled=1\nstyle=0\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (17,'Content - Page Navigation','pagenavigation','content',0,2,1,1,0,0,'0000-00-00 00:00:00','position=1\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (18,'Editor - No Editor','none','editors',0,0,1,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (19,'Editor - TinyMCE','tinymce','editors',0,0,1,1,0,0,'0000-00-00 00:00:00','mode=advanced\nskin=0\ncompressed=0\ncleanup_startup=0\ncleanup_save=2\nentity_encoding=raw\nlang_mode=0\nlang_code=en\ntext_direction=ltr\ncontent_css=1\ncontent_css_custom=\nrelative_urls=1\nnewlines=0\ninvalid_elements=applet\nextended_elements=\ntoolbar=top\ntoolbar_align=left\nhtml_height=550\nhtml_width=750\nelement_path=1\nfonts=1\npaste=1\nsearchreplace=1\ninsertdate=1\nformat_date=%Y-%m-%d\ninserttime=1\nformat_time=%H:%M:%S\ncolors=1\ntable=1\nsmilies=1\nmedia=1\nhr=1\ndirectionality=1\nfullscreen=1\nstyle=1\nlayer=1\nxhtmlxtras=1\nvisualchars=1\nnonbreaking=1\ntemplate=0\nadvimage=1\nadvlink=1\nautosave=1\ncontextmenu=1\ninlinepopups=1\nsafari=1\ncustom_plugin=\ncustom_button=\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (20,'Editor - XStandard Lite 2.0','xstandard','editors',0,0,0,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (21,'Editor Button - Image','image','editors-xtd',0,0,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (22,'Editor Button - Pagebreak','pagebreak','editors-xtd',0,0,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (23,'Editor Button - Readmore','readmore','editors-xtd',0,0,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (24,'XML-RPC - Joomla','joomla','xmlrpc',0,7,0,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (25,'XML-RPC - Blogger API','blogger','xmlrpc',0,7,0,1,0,0,'0000-00-00 00:00:00','catid=1\nsectionid=0\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (27,'System - SEF','sef','system',0,1,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (28,'System - Debug','debug','system',0,2,1,0,0,0,'0000-00-00 00:00:00','queries=1\nmemory=1\nlangauge=1\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (29,'System - Legacy','legacy','system',0,3,0,1,0,0,'0000-00-00 00:00:00','route=0\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (30,'System - Cache','cache','system',0,4,0,1,0,0,'0000-00-00 00:00:00','browsercache=0\ncachetime=15\n\n');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (31,'System - Log','log','system',0,5,0,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (32,'System - Remember Me','remember','system',0,6,1,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (33,'System - Backlink','backlink','system',0,7,0,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (34,'System - Mootools Upgrade','mtupgrade','system',0,8,1,1,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (35,'System - ARTIO JoomSEF','joomsef','system',0,-9,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (36,'System - Advanced Module Manager','advancedmodules','system',0,0,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (37,'System - NoNumber! Elements','nonumberelements','system',0,0,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (38,'System - Articles Anywhere','articlesanywhere','system',0,0,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (39,'Editor Button - Articles Anywhere','articlesanywhere','editors-xtd',0,0,1,0,0,0,'0000-00-00 00:00:00','');
INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES (40,'JA Menu Parameters','plg_jamenuparams','system',0,0,1,0,0,0,'0000-00-00 00:00:00','');
/*!40000 ALTER TABLE `jos_plugins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_poll_data`
--

DROP TABLE IF EXISTS `jos_poll_data`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_poll_data` (
  `id` int(11) NOT NULL auto_increment,
  `pollid` int(11) NOT NULL default '0',
  `text` text NOT NULL,
  `hits` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `pollid` (`pollid`,`text`(1))
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_poll_data`
--

LOCK TABLES `jos_poll_data` WRITE;
/*!40000 ALTER TABLE `jos_poll_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_poll_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_poll_date`
--

DROP TABLE IF EXISTS `jos_poll_date`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_poll_date` (
  `id` bigint(20) NOT NULL auto_increment,
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  `vote_id` int(11) NOT NULL default '0',
  `poll_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `poll_id` (`poll_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_poll_date`
--

LOCK TABLES `jos_poll_date` WRITE;
/*!40000 ALTER TABLE `jos_poll_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_poll_date` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_poll_menu`
--

DROP TABLE IF EXISTS `jos_poll_menu`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_poll_menu` (
  `pollid` int(11) NOT NULL default '0',
  `menuid` int(11) NOT NULL default '0',
  PRIMARY KEY  (`pollid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_poll_menu`
--

LOCK TABLES `jos_poll_menu` WRITE;
/*!40000 ALTER TABLE `jos_poll_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_poll_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_polls`
--

DROP TABLE IF EXISTS `jos_polls`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_polls` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `title` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `voters` int(9) NOT NULL default '0',
  `checked_out` int(11) NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `published` tinyint(1) NOT NULL default '0',
  `access` int(11) NOT NULL default '0',
  `lag` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_polls`
--

LOCK TABLES `jos_polls` WRITE;
/*!40000 ALTER TABLE `jos_polls` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_polls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sections`
--

DROP TABLE IF EXISTS `jos_sections`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sections` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(255) NOT NULL default '',
  `name` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `image` text NOT NULL,
  `scope` varchar(50) NOT NULL default '',
  `image_position` varchar(30) NOT NULL default '',
  `description` text NOT NULL,
  `published` tinyint(1) NOT NULL default '0',
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL default '0',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `count` int(11) NOT NULL default '0',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_scope` (`scope`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sections`
--

LOCK TABLES `jos_sections` WRITE;
/*!40000 ALTER TABLE `jos_sections` DISABLE KEYS */;
INSERT INTO `jos_sections` (`id`, `title`, `name`, `alias`, `image`, `scope`, `image_position`, `description`, `published`, `checked_out`, `checked_out_time`, `ordering`, `access`, `count`, `params`) VALUES (1,'Products','','products','','content','left','<p>Products</p>',1,0,'0000-00-00 00:00:00',1,0,3,'');
/*!40000 ALTER TABLE `jos_sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sefaliases`
--

DROP TABLE IF EXISTS `jos_sefaliases`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sefaliases` (
  `id` int(11) NOT NULL auto_increment,
  `alias` varchar(255) NOT NULL default '',
  `vars` text NOT NULL,
  `url` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `alias` (`alias`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sefaliases`
--

LOCK TABLES `jos_sefaliases` WRITE;
/*!40000 ALTER TABLE `jos_sefaliases` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_sefaliases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sefexts`
--

DROP TABLE IF EXISTS `jos_sefexts`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sefexts` (
  `id` int(11) NOT NULL auto_increment,
  `file` varchar(100) NOT NULL,
  `filters` text,
  `params` text,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sefexts`
--

LOCK TABLES `jos_sefexts` WRITE;
/*!40000 ALTER TABLE `jos_sefexts` DISABLE KEYS */;
INSERT INTO `jos_sefexts` (`id`, `file`, `filters`, `params`, `title`) VALUES (1,'com_wrapper.xml',NULL,'ignoreSource=0\nitemid=1\noverrideId=',NULL);
INSERT INTO `jos_sefexts` (`id`, `file`, `filters`, `params`, `title`) VALUES (2,'com_content.xml','+^[0-9]*$=limit,limitstart,month,showall,year\n+^[a-zA-Z]+$=task,type,view\n+^[0-9]+(:[a-zA-Z0-9._-]+)?$=catid,id,sectionid','acceptVars=view; id; catid; sectionid; type; year; month; filter; limit; limitstart; task; showall',NULL);
/*!40000 ALTER TABLE `jos_sefexts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sefexttexts`
--

DROP TABLE IF EXISTS `jos_sefexttexts`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sefexttexts` (
  `id` int(11) NOT NULL auto_increment,
  `extension` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sefexttexts`
--

LOCK TABLES `jos_sefexttexts` WRITE;
/*!40000 ALTER TABLE `jos_sefexttexts` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_sefexttexts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sefmoved`
--

DROP TABLE IF EXISTS `jos_sefmoved`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sefmoved` (
  `id` int(11) NOT NULL auto_increment,
  `old` varchar(255) NOT NULL,
  `new` varchar(255) NOT NULL,
  `lastHit` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  KEY `old` (`old`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sefmoved`
--

LOCK TABLES `jos_sefmoved` WRITE;
/*!40000 ALTER TABLE `jos_sefmoved` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_sefmoved` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sefurls`
--

DROP TABLE IF EXISTS `jos_sefurls`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sefurls` (
  `id` int(11) NOT NULL auto_increment,
  `cpt` int(11) NOT NULL default '0',
  `sefurl` varchar(255) NOT NULL,
  `origurl` varchar(255) character set utf8 collate utf8_bin NOT NULL,
  `Itemid` varchar(20) default NULL,
  `metadesc` varchar(255) default '',
  `metakey` varchar(255) default '',
  `metatitle` varchar(255) default '',
  `metalang` varchar(30) default '',
  `metarobots` varchar(30) default '',
  `metagoogle` varchar(30) default '',
  `metacustom` text,
  `canonicallink` varchar(255) default '',
  `dateadd` date NOT NULL default '0000-00-00',
  `priority` int(11) NOT NULL default '0',
  `trace` text,
  `enabled` tinyint(1) NOT NULL default '1',
  `locked` tinyint(1) NOT NULL default '0',
  `sef` tinyint(1) NOT NULL default '1',
  `sm_indexed` tinyint(1) NOT NULL default '0',
  `sm_date` date NOT NULL default '0000-00-00',
  `sm_frequency` varchar(20) NOT NULL default 'weekly',
  `sm_priority` varchar(10) NOT NULL default '0.5',
  `trashed` tinyint(1) NOT NULL default '0',
  `flag` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `sefurl` (`sefurl`),
  KEY `origurl` (`origurl`,`Itemid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sefurls`
--

LOCK TABLES `jos_sefurls` WRITE;
/*!40000 ALTER TABLE `jos_sefurls` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_sefurls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sefurlword_xref`
--

DROP TABLE IF EXISTS `jos_sefurlword_xref`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sefurlword_xref` (
  `word` int(11) NOT NULL,
  `url` int(11) NOT NULL,
  PRIMARY KEY  (`word`,`url`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sefurlword_xref`
--

LOCK TABLES `jos_sefurlword_xref` WRITE;
/*!40000 ALTER TABLE `jos_sefurlword_xref` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_sefurlword_xref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_sefwords`
--

DROP TABLE IF EXISTS `jos_sefwords`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_sefwords` (
  `id` int(11) NOT NULL auto_increment,
  `word` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_sefwords`
--

LOCK TABLES `jos_sefwords` WRITE;
/*!40000 ALTER TABLE `jos_sefwords` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_sefwords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_session`
--

DROP TABLE IF EXISTS `jos_session`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_session` (
  `username` varchar(150) default '',
  `time` varchar(14) default '',
  `session_id` varchar(200) NOT NULL default '0',
  `guest` tinyint(4) default '1',
  `userid` int(11) default '0',
  `usertype` varchar(50) default '',
  `gid` tinyint(3) unsigned NOT NULL default '0',
  `client_id` tinyint(3) unsigned NOT NULL default '0',
  `data` longtext,
  PRIMARY KEY  (`session_id`(64)),
  KEY `whosonline` (`guest`,`usertype`),
  KEY `userid` (`userid`),
  KEY `time` (`time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_session`
--

LOCK TABLES `jos_session` WRITE;
/*!40000 ALTER TABLE `jos_session` DISABLE KEYS */;
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('','1302627307','b896a4i9bkiig5cntgd020k854',1,0,'',0,0,'__default|a:8:{s:15:\"session.counter\";i:109;s:19:\"session.timer.start\";i:1302590986;s:18:\"session.timer.last\";i:1302627302;s:17:\"session.timer.now\";i:1302627307;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:2:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}s:7:\"hh_list\";a:1:{s:4:\"data\";O:8:\"stdClass\":26:{s:4:\"type\";s:0:\"\";s:4:\"user\";s:0:\"\";s:4:\"days\";s:0:\"\";s:5:\"stype\";s:0:\"\";s:4:\"task\";s:0:\"\";s:5:\"order\";s:0:\"\";s:4:\"sort\";s:0:\"\";s:10:\"searchuser\";s:0:\"\";s:10:\"searchdays\";s:0:\"\";s:10:\"searchtask\";s:0:\"\";s:14:\"searchusername\";s:0:\"\";s:15:\"searchproblemid\";s:0:\"\";s:9:\"searchrep\";s:0:\"\";s:14:\"searchcategory\";s:0:\"\";s:16:\"searchdepartment\";s:0:\"\";s:12:\"searchstatus\";s:0:\"\";s:14:\"searchpriority\";s:0:\"\";s:13:\"searchkeyword\";s:0:\"\";s:13:\"searchsubject\";s:0:\"\";s:17:\"searchdescription\";s:0:\"\";s:14:\"searchsolution\";s:0:\"\";s:19:\"searchstartdatefrom\";s:0:\"\";s:17:\"searchstartdateto\";s:0:\"\";s:5:\"count\";s:0:\"\";s:4:\"view\";s:4:\"list\";s:6:\"Itemid\";s:1:\"2\";}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";i:0;s:4:\"name\";N;s:8:\"username\";N;s:5:\"email\";N;s:8:\"password\";N;s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";N;s:5:\"block\";N;s:9:\"sendEmail\";i:0;s:3:\"gid\";i:0;s:12:\"registerDate\";N;s:13:\"lastvisitDate\";N;s:10:\"activation\";N;s:6:\"params\";N;s:3:\"aid\";i:0;s:5:\"guest\";i:1;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}s:13:\"session.token\";s:32:\"b061900ac63a75cf14ca7d2075b4d49b\";}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('','1302591547','qpu1gbs7g5jnika77ac61egd22',1,0,'',0,1,'__default|a:8:{s:15:\"session.counter\";i:1;s:19:\"session.timer.start\";i:1302591533;s:18:\"session.timer.last\";i:1302591533;s:17:\"session.timer.now\";i:1302591533;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:1:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";i:0;s:4:\"name\";N;s:8:\"username\";N;s:5:\"email\";N;s:8:\"password\";N;s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";N;s:5:\"block\";N;s:9:\"sendEmail\";i:0;s:3:\"gid\";i:0;s:12:\"registerDate\";N;s:13:\"lastvisitDate\";N;s:10:\"activation\";N;s:6:\"params\";N;s:3:\"aid\";i:0;s:5:\"guest\";i:1;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}s:13:\"session.token\";s:32:\"5c53fdcfa7a5b3207810a71df943ae53\";}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('admin','1302624360','io0k580j30dp1dt2b8jmeof7p1',0,62,'Super Administrator',25,1,'__default|a:8:{s:15:\"session.counter\";i:176;s:19:\"session.timer.start\";i:1302591533;s:18:\"session.timer.last\";i:1302624360;s:17:\"session.timer.now\";i:1302624360;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:9:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}s:11:\"application\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:4:\"lang\";s:0:\"\";}}s:10:\"com_cpanel\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:9:\"mtupgrade\";O:8:\"stdClass\":1:{s:7:\"checked\";b:1;}}}s:11:\"com_content\";a:1:{s:4:\"data\";O:8:\"stdClass\":8:{s:23:\"viewcontentfilter_order\";s:7:\"c.title\";s:27:\"viewcontentfilter_order_Dir\";s:3:\"asc\";s:23:\"viewcontentfilter_state\";s:0:\"\";s:16:\"viewcontentcatid\";i:0;s:26:\"viewcontentfilter_authorid\";i:0;s:27:\"viewcontentfilter_sectionid\";i:-1;s:17:\"viewcontentsearch\";s:0:\"\";s:21:\"viewcontentlimitstart\";i:0;}}s:6:\"global\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:4:\"list\";O:8:\"stdClass\":1:{s:5:\"limit\";i:20;}}}s:9:\"com_menus\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:8:\"menutype\";s:8:\"mainmenu\";}}s:11:\"hh_userlist\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:5:\"count\";i:1;}}s:11:\"com_plugins\";a:1:{s:4:\"data\";O:8:\"stdClass\":2:{s:4:\"site\";O:8:\"stdClass\":5:{s:12:\"filter_order\";s:8:\"p.folder\";s:16:\"filter_order_Dir\";s:0:\"\";s:12:\"filter_state\";s:0:\"\";s:11:\"filter_type\";s:1:\"1\";s:6:\"search\";s:3:\"kun\";}s:10:\"limitstart\";i:0;}}s:13:\"com_installer\";a:1:{s:4:\"data\";O:8:\"stdClass\":2:{s:10:\"limitstart\";O:8:\"stdClass\":3:{s:9:\"component\";i:0;s:6:\"plugin\";i:0;s:6:\"module\";i:20;}s:7:\"plugins\";O:8:\"stdClass\":1:{s:5:\"group\";s:0:\"\";}}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";s:2:\"62\";s:4:\"name\";s:13:\"Administrator\";s:8:\"username\";s:5:\"admin\";s:5:\"email\";s:31:\"jacek.p.kolodziejczyk@gmail.com\";s:8:\"password\";s:65:\"22e72019da69849ad2c8be6adaddf246:qy4KjJ3Zg8OP6AJjhYrdeppD33YQnERh\";s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";s:19:\"Super Administrator\";s:5:\"block\";s:1:\"0\";s:9:\"sendEmail\";s:1:\"1\";s:3:\"gid\";s:2:\"25\";s:12:\"registerDate\";s:19:\"2011-03-13 21:18:07\";s:13:\"lastvisitDate\";s:19:\"2011-03-28 23:14:26\";s:10:\"activation\";s:0:\"\";s:6:\"params\";s:0:\"\";s:3:\"aid\";i:2;s:5:\"guest\";i:0;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}s:13:\"session.token\";s:32:\"5c53fdcfa7a5b3207810a71df943ae53\";}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('','1302677772','f4fado6tc8u9u58htque4sitp6',1,0,'',0,1,'__default|a:8:{s:15:\"session.counter\";i:1;s:19:\"session.timer.start\";i:1302677757;s:18:\"session.timer.last\";i:1302677757;s:17:\"session.timer.now\";i:1302677757;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:1:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";i:0;s:4:\"name\";N;s:8:\"username\";N;s:5:\"email\";N;s:8:\"password\";N;s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";N;s:5:\"block\";N;s:9:\"sendEmail\";i:0;s:3:\"gid\";i:0;s:12:\"registerDate\";N;s:13:\"lastvisitDate\";N;s:10:\"activation\";N;s:6:\"params\";N;s:3:\"aid\";i:0;s:5:\"guest\";i:1;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}s:13:\"session.token\";s:32:\"3cbc58a9d4b163d34cf66e36677059b9\";}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('admin','1302697677','g1fnj0vn201h0erjvt59difgt1',0,62,'Super Administrator',25,1,'__default|a:8:{s:15:\"session.counter\";i:85;s:19:\"session.timer.start\";i:1302677757;s:18:\"session.timer.last\";i:1302697677;s:17:\"session.timer.now\";i:1302697677;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:5:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}s:11:\"application\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:4:\"lang\";s:0:\"\";}}s:11:\"com_content\";a:1:{s:4:\"data\";O:8:\"stdClass\":8:{s:23:\"viewcontentfilter_order\";s:7:\"c.title\";s:27:\"viewcontentfilter_order_Dir\";s:3:\"asc\";s:23:\"viewcontentfilter_state\";s:0:\"\";s:16:\"viewcontentcatid\";i:0;s:26:\"viewcontentfilter_authorid\";i:0;s:27:\"viewcontentfilter_sectionid\";i:-1;s:17:\"viewcontentsearch\";s:0:\"\";s:21:\"viewcontentlimitstart\";i:0;}}s:6:\"global\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:4:\"list\";O:8:\"stdClass\":1:{s:5:\"limit\";i:20;}}}s:9:\"com_menus\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:8:\"menutype\";s:8:\"mainmenu\";}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";s:2:\"62\";s:4:\"name\";s:13:\"Administrator\";s:8:\"username\";s:5:\"admin\";s:5:\"email\";s:31:\"jacek.p.kolodziejczyk@gmail.com\";s:8:\"password\";s:65:\"22e72019da69849ad2c8be6adaddf246:qy4KjJ3Zg8OP6AJjhYrdeppD33YQnERh\";s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";s:19:\"Super Administrator\";s:5:\"block\";s:1:\"0\";s:9:\"sendEmail\";s:1:\"1\";s:3:\"gid\";s:2:\"25\";s:12:\"registerDate\";s:19:\"2011-03-13 21:18:07\";s:13:\"lastvisitDate\";s:19:\"2011-04-12 06:59:07\";s:10:\"activation\";s:0:\"\";s:6:\"params\";s:0:\"\";s:3:\"aid\";i:2;s:5:\"guest\";i:0;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}s:13:\"session.token\";s:32:\"3cbc58a9d4b163d34cf66e36677059b9\";}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('','1302697115','8cqskdr9560onaae41ct63aic3',1,0,'',0,0,'__default|a:7:{s:15:\"session.counter\";i:30;s:19:\"session.timer.start\";i:1302677784;s:18:\"session.timer.last\";i:1302696902;s:17:\"session.timer.now\";i:1302697115;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:1:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";i:0;s:4:\"name\";N;s:8:\"username\";N;s:5:\"email\";N;s:8:\"password\";N;s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";N;s:5:\"block\";N;s:9:\"sendEmail\";i:0;s:3:\"gid\";i:0;s:12:\"registerDate\";N;s:13:\"lastvisitDate\";N;s:10:\"activation\";N;s:6:\"params\";N;s:3:\"aid\";i:0;s:5:\"guest\";i:1;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('','1302766835','pr7k6fdn0cd057o0l00osn7rs4',1,0,'',0,1,'__default|a:8:{s:15:\"session.counter\";i:1;s:19:\"session.timer.start\";i:1302766708;s:18:\"session.timer.last\";i:1302766708;s:17:\"session.timer.now\";i:1302766708;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:1:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";i:0;s:4:\"name\";N;s:8:\"username\";N;s:5:\"email\";N;s:8:\"password\";N;s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";N;s:5:\"block\";N;s:9:\"sendEmail\";i:0;s:3:\"gid\";i:0;s:12:\"registerDate\";N;s:13:\"lastvisitDate\";N;s:10:\"activation\";N;s:6:\"params\";N;s:3:\"aid\";i:0;s:5:\"guest\";i:1;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}s:13:\"session.token\";s:32:\"8c0585b8c17f5dbf8b1583e353839d5f\";}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('admin','1302783513','ubjf1cql9oi3d919cpkpgk82m2',0,62,'Super Administrator',25,1,'__default|a:8:{s:15:\"session.counter\";i:141;s:19:\"session.timer.start\";i:1302766708;s:18:\"session.timer.last\";i:1302783513;s:17:\"session.timer.now\";i:1302783513;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:4:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}s:11:\"application\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:4:\"lang\";s:0:\"\";}}s:11:\"com_content\";a:1:{s:4:\"data\";O:8:\"stdClass\":8:{s:23:\"viewcontentfilter_order\";s:7:\"c.title\";s:27:\"viewcontentfilter_order_Dir\";s:3:\"asc\";s:23:\"viewcontentfilter_state\";s:0:\"\";s:16:\"viewcontentcatid\";i:0;s:26:\"viewcontentfilter_authorid\";i:0;s:27:\"viewcontentfilter_sectionid\";i:-1;s:17:\"viewcontentsearch\";s:0:\"\";s:21:\"viewcontentlimitstart\";i:0;}}s:6:\"global\";a:1:{s:4:\"data\";O:8:\"stdClass\":1:{s:4:\"list\";O:8:\"stdClass\":1:{s:5:\"limit\";i:20;}}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";s:2:\"62\";s:4:\"name\";s:13:\"Administrator\";s:8:\"username\";s:5:\"admin\";s:5:\"email\";s:31:\"jacek.p.kolodziejczyk@gmail.com\";s:8:\"password\";s:65:\"22e72019da69849ad2c8be6adaddf246:qy4KjJ3Zg8OP6AJjhYrdeppD33YQnERh\";s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";s:19:\"Super Administrator\";s:5:\"block\";s:1:\"0\";s:9:\"sendEmail\";s:1:\"1\";s:3:\"gid\";s:2:\"25\";s:12:\"registerDate\";s:19:\"2011-03-13 21:18:07\";s:13:\"lastvisitDate\";s:19:\"2011-04-13 06:56:14\";s:10:\"activation\";s:0:\"\";s:6:\"params\";s:0:\"\";s:3:\"aid\";i:2;s:5:\"guest\";i:0;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}s:13:\"session.token\";s:32:\"8c0585b8c17f5dbf8b1583e353839d5f\";}');
INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES ('','1302784622','jamn71j6i4j7fu49m3ck3jp190',1,0,'',0,0,'__default|a:7:{s:15:\"session.counter\";i:110;s:19:\"session.timer.start\";i:1302766849;s:18:\"session.timer.last\";i:1302784586;s:17:\"session.timer.now\";i:1302784622;s:22:\"session.client.browser\";s:120:\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16\";s:8:\"registry\";O:9:\"JRegistry\":3:{s:17:\"_defaultNameSpace\";s:7:\"session\";s:9:\"_registry\";a:1:{s:7:\"session\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:4:\"user\";O:5:\"JUser\":19:{s:2:\"id\";i:0;s:4:\"name\";N;s:8:\"username\";N;s:5:\"email\";N;s:8:\"password\";N;s:14:\"password_clear\";s:0:\"\";s:8:\"usertype\";N;s:5:\"block\";N;s:9:\"sendEmail\";i:0;s:3:\"gid\";i:0;s:12:\"registerDate\";N;s:13:\"lastvisitDate\";N;s:10:\"activation\";N;s:6:\"params\";N;s:3:\"aid\";i:0;s:5:\"guest\";i:1;s:7:\"_params\";O:10:\"JParameter\":7:{s:4:\"_raw\";s:0:\"\";s:4:\"_xml\";N;s:9:\"_elements\";a:0:{}s:12:\"_elementPath\";a:1:{i:0;s:65:\"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element\";}s:17:\"_defaultNameSpace\";s:8:\"_default\";s:9:\"_registry\";a:1:{s:8:\"_default\";a:1:{s:4:\"data\";O:8:\"stdClass\":0:{}}}s:7:\"_errors\";a:0:{}}s:9:\"_errorMsg\";N;s:7:\"_errors\";a:0:{}}}');
/*!40000 ALTER TABLE `jos_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_stats_agents`
--

DROP TABLE IF EXISTS `jos_stats_agents`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_stats_agents` (
  `agent` varchar(255) NOT NULL default '',
  `type` tinyint(1) unsigned NOT NULL default '0',
  `hits` int(11) unsigned NOT NULL default '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_stats_agents`
--

LOCK TABLES `jos_stats_agents` WRITE;
/*!40000 ALTER TABLE `jos_stats_agents` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_stats_agents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_templates_menu`
--

DROP TABLE IF EXISTS `jos_templates_menu`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_templates_menu` (
  `template` varchar(255) NOT NULL default '',
  `menuid` int(11) NOT NULL default '0',
  `client_id` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`menuid`,`client_id`,`template`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_templates_menu`
--

LOCK TABLES `jos_templates_menu` WRITE;
/*!40000 ALTER TABLE `jos_templates_menu` DISABLE KEYS */;
INSERT INTO `jos_templates_menu` (`template`, `menuid`, `client_id`) VALUES ('ja_purity_ii',0,0);
INSERT INTO `jos_templates_menu` (`template`, `menuid`, `client_id`) VALUES ('khepri',0,1);
/*!40000 ALTER TABLE `jos_templates_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_users`
--

DROP TABLE IF EXISTS `jos_users`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_users` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL default '',
  `username` varchar(150) NOT NULL default '',
  `email` varchar(100) NOT NULL default '',
  `password` varchar(100) NOT NULL default '',
  `usertype` varchar(25) NOT NULL default '',
  `block` tinyint(4) NOT NULL default '0',
  `sendEmail` tinyint(4) default '0',
  `gid` tinyint(3) unsigned NOT NULL default '1',
  `registerDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `lastvisitDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `activation` varchar(100) NOT NULL default '',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `usertype` (`usertype`),
  KEY `idx_name` (`name`),
  KEY `gid_block` (`gid`,`block`),
  KEY `username` (`username`),
  KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_users`
--

LOCK TABLES `jos_users` WRITE;
/*!40000 ALTER TABLE `jos_users` DISABLE KEYS */;
INSERT INTO `jos_users` (`id`, `name`, `username`, `email`, `password`, `usertype`, `block`, `sendEmail`, `gid`, `registerDate`, `lastvisitDate`, `activation`, `params`) VALUES (62,'Administrator','admin','jacek.p.kolodziejczyk@gmail.com','22e72019da69849ad2c8be6adaddf246:qy4KjJ3Zg8OP6AJjhYrdeppD33YQnERh','Super Administrator',0,1,25,'2011-03-13 21:18:07','2011-04-14 07:40:36','','');
/*!40000 ALTER TABLE `jos_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jos_weblinks`
--

DROP TABLE IF EXISTS `jos_weblinks`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jos_weblinks` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `catid` int(11) NOT NULL default '0',
  `sid` int(11) NOT NULL default '0',
  `title` varchar(250) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `url` varchar(250) NOT NULL default '',
  `description` text NOT NULL,
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  `hits` int(11) NOT NULL default '0',
  `published` tinyint(1) NOT NULL default '0',
  `checked_out` int(11) NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL default '0',
  `archived` tinyint(1) NOT NULL default '0',
  `approved` tinyint(1) NOT NULL default '1',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `catid` (`catid`,`published`,`archived`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jos_weblinks`
--

LOCK TABLES `jos_weblinks` WRITE;
/*!40000 ALTER TABLE `jos_weblinks` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_weblinks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-04-14 12:38:15
