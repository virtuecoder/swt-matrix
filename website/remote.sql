-- phpMyAdmin SQL Dump
-- version 2.6.4-pl3
-- http://www.phpmyadmin.net
-- 
-- Host: db16.1and1.pl
-- Generation Time: Oct 09, 2012 at 08:21 AM
-- Server version: 5.0.95
-- PHP Version: 5.3.3-7+squeeze14
-- 
-- Database: `db361399687`
-- 
CREATE DATABASE `db361399687` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE db361399687;

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_assets`
-- 

CREATE TABLE `bak_assets` (
  `id` int(10) unsigned NOT NULL auto_increment COMMENT 'Primary Key',
  `parent_id` int(11) NOT NULL default '0' COMMENT 'Nested set parent.',
  `lft` int(11) NOT NULL default '0' COMMENT 'Nested set lft.',
  `rgt` int(11) NOT NULL default '0' COMMENT 'Nested set rgt.',
  `level` int(10) unsigned NOT NULL COMMENT 'The cached level in the nested tree.',
  `name` varchar(50) NOT NULL COMMENT 'The unique name for the asset.\n',
  `title` varchar(100) NOT NULL COMMENT 'The descriptive title for the asset.',
  `rules` varchar(5120) NOT NULL COMMENT 'JSON encoded access control.',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_asset_name` (`name`),
  KEY `idx_lft_rgt` (`lft`,`rgt`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 AUTO_INCREMENT=32 ;

-- 
-- Dumping data for table `bak_assets`
-- 

INSERT INTO `bak_assets` VALUES (1, 0, 1, 414, 0, 'root.1', 'Root Asset', '{"core.login.site":{"6":1,"2":1},"core.login.admin":{"6":1},"core.admin":{"8":1},"core.manage":{"7":1},"core.create":{"6":1,"3":1},"core.delete":{"6":1},"core.edit":{"6":1,"4":1},"core.edit.state":{"6":1,"5":1},"core.edit.own":{"6":1,"3":1}}');
INSERT INTO `bak_assets` VALUES (2, 1, 1, 2, 1, 'com_admin', 'com_admin', '{}');
INSERT INTO `bak_assets` VALUES (3, 1, 3, 6, 1, 'com_banners', 'com_banners', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (4, 1, 7, 8, 1, 'com_cache', 'com_cache', '{"core.admin":{"7":1},"core.manage":{"7":1}}');
INSERT INTO `bak_assets` VALUES (5, 1, 9, 10, 1, 'com_checkin', 'com_checkin', '{"core.admin":{"7":1},"core.manage":{"7":1}}');
INSERT INTO `bak_assets` VALUES (6, 1, 11, 12, 1, 'com_config', 'com_config', '{}');
INSERT INTO `bak_assets` VALUES (7, 1, 13, 16, 1, 'com_contact', 'com_contact', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `bak_assets` VALUES (8, 1, 17, 20, 1, 'com_content', 'com_content', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":[],"core.edit":{"4":1},"core.edit.state":{"5":1},"core.edit.own":[]}');
INSERT INTO `bak_assets` VALUES (9, 1, 21, 22, 1, 'com_cpanel', 'com_cpanel', '{}');
INSERT INTO `bak_assets` VALUES (10, 1, 23, 24, 1, 'com_installer', 'com_installer', '{"core.admin":{"7":1},"core.manage":{"7":1},"core.delete":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (11, 1, 25, 26, 1, 'com_languages', 'com_languages', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (12, 1, 27, 28, 1, 'com_login', 'com_login', '{}');
INSERT INTO `bak_assets` VALUES (13, 1, 29, 30, 1, 'com_mailto', 'com_mailto', '{}');
INSERT INTO `bak_assets` VALUES (14, 1, 31, 32, 1, 'com_massmail', 'com_massmail', '{}');
INSERT INTO `bak_assets` VALUES (15, 1, 33, 34, 1, 'com_media', 'com_media', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":{"5":1}}');
INSERT INTO `bak_assets` VALUES (16, 1, 35, 36, 1, 'com_menus', 'com_menus', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (17, 1, 37, 38, 1, 'com_messages', 'com_messages', '{"core.admin":{"7":1},"core.manage":{"7":1}}');
INSERT INTO `bak_assets` VALUES (18, 1, 39, 40, 1, 'com_modules', 'com_modules', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (19, 1, 41, 44, 1, 'com_newsfeeds', 'com_newsfeeds', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `bak_assets` VALUES (20, 1, 45, 46, 1, 'com_plugins', 'com_plugins', '{"core.admin":{"7":1},"core.manage":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (21, 1, 47, 48, 1, 'com_redirect', 'com_redirect', '{"core.admin":{"7":1},"core.manage":[]}');
INSERT INTO `bak_assets` VALUES (22, 1, 49, 50, 1, 'com_search', 'com_search', '{"core.admin":{"7":1},"core.manage":{"6":1}}');
INSERT INTO `bak_assets` VALUES (23, 1, 51, 52, 1, 'com_templates', 'com_templates', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (24, 1, 53, 54, 1, 'com_users', 'com_users', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.own":{"6":1},"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (25, 1, 55, 58, 1, 'com_weblinks', 'com_weblinks', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":[],"core.edit":{"4":1},"core.edit.state":{"5":1},"core.edit.own":[]}');
INSERT INTO `bak_assets` VALUES (26, 1, 59, 60, 1, 'com_wrapper', 'com_wrapper', '{}');
INSERT INTO `bak_assets` VALUES (27, 8, 18, 19, 2, 'com_content.category.2', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `bak_assets` VALUES (28, 3, 4, 5, 2, 'com_banners.category.3', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `bak_assets` VALUES (29, 7, 14, 15, 2, 'com_contact.category.4', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `bak_assets` VALUES (30, 19, 42, 43, 2, 'com_newsfeeds.category.5', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `bak_assets` VALUES (31, 25, 56, 57, 2, 'com_weblinks.category.6', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_banner_clients`
-- 

CREATE TABLE `bak_banner_clients` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL default '',
  `contact` varchar(255) NOT NULL default '',
  `email` varchar(255) NOT NULL default '',
  `extrainfo` text NOT NULL,
  `state` tinyint(3) NOT NULL default '0',
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `metakey` text NOT NULL,
  `own_prefix` tinyint(4) NOT NULL default '0',
  `metakey_prefix` varchar(255) NOT NULL default '',
  `purchase_type` tinyint(4) NOT NULL default '-1',
  `track_clicks` tinyint(4) NOT NULL default '-1',
  `track_impressions` tinyint(4) NOT NULL default '-1',
  PRIMARY KEY  (`id`),
  KEY `idx_own_prefix` (`own_prefix`),
  KEY `idx_metakey_prefix` (`metakey_prefix`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_banner_clients`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_banner_tracks`
-- 

CREATE TABLE `bak_banner_tracks` (
  `track_date` datetime NOT NULL,
  `track_type` int(10) unsigned NOT NULL,
  `banner_id` int(10) unsigned NOT NULL,
  `count` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`track_date`,`track_type`,`banner_id`),
  KEY `idx_track_date` (`track_date`),
  KEY `idx_track_type` (`track_type`),
  KEY `idx_banner_id` (`banner_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_banner_tracks`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_banners`
-- 

CREATE TABLE `bak_banners` (
  `id` int(11) NOT NULL auto_increment,
  `cid` int(11) NOT NULL default '0',
  `type` int(11) NOT NULL default '0',
  `name` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `imptotal` int(11) NOT NULL default '0',
  `impmade` int(11) NOT NULL default '0',
  `clicks` int(11) NOT NULL default '0',
  `clickurl` varchar(200) NOT NULL default '',
  `state` tinyint(3) NOT NULL default '0',
  `catid` int(10) unsigned NOT NULL default '0',
  `description` text NOT NULL,
  `custombannercode` varchar(2048) NOT NULL,
  `sticky` tinyint(1) unsigned NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  `metakey` text NOT NULL,
  `params` text NOT NULL,
  `own_prefix` tinyint(1) NOT NULL default '0',
  `metakey_prefix` varchar(255) NOT NULL default '',
  `purchase_type` tinyint(4) NOT NULL default '-1',
  `track_clicks` tinyint(4) NOT NULL default '-1',
  `track_impressions` tinyint(4) NOT NULL default '-1',
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  `reset` datetime NOT NULL default '0000-00-00 00:00:00',
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `language` char(7) NOT NULL default '',
  PRIMARY KEY  (`id`),
  KEY `idx_state` (`state`),
  KEY `idx_own_prefix` (`own_prefix`),
  KEY `idx_metakey_prefix` (`metakey_prefix`),
  KEY `idx_banner_catid` (`catid`),
  KEY `idx_language` (`language`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_banners`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_categories`
-- 

CREATE TABLE `bak_categories` (
  `id` int(11) NOT NULL auto_increment,
  `asset_id` int(10) unsigned NOT NULL default '0' COMMENT 'FK to the #__assets table.',
  `parent_id` int(10) unsigned NOT NULL default '0',
  `lft` int(11) NOT NULL default '0',
  `rgt` int(11) NOT NULL default '0',
  `level` int(10) unsigned NOT NULL default '0',
  `path` varchar(255) NOT NULL default '',
  `extension` varchar(50) NOT NULL default '',
  `title` varchar(255) NOT NULL,
  `alias` varchar(255) NOT NULL default '',
  `note` varchar(255) NOT NULL default '',
  `description` varchar(5120) NOT NULL default '',
  `published` tinyint(1) NOT NULL default '0',
  `checked_out` int(11) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `params` text NOT NULL,
  `metadesc` varchar(1024) NOT NULL COMMENT 'The meta description for the page.',
  `metakey` varchar(1024) NOT NULL COMMENT 'The meta keywords for the page.',
  `metadata` varchar(2048) NOT NULL COMMENT 'JSON encoded metadata properties.',
  `created_user_id` int(10) unsigned NOT NULL default '0',
  `created_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_user_id` int(10) unsigned NOT NULL default '0',
  `modified_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `hits` int(10) unsigned NOT NULL default '0',
  `language` char(7) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `cat_idx` (`extension`,`published`,`access`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`),
  KEY `idx_path` (`path`),
  KEY `idx_left_right` (`lft`,`rgt`),
  KEY `idx_alias` (`alias`),
  KEY `idx_language` (`language`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- Dumping data for table `bak_categories`
-- 

INSERT INTO `bak_categories` VALUES (1, 0, 0, 0, 11, 0, '', 'system', 'ROOT', 'root', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{}', '', '', '', 0, '2009-10-18 16:07:09', 0, '0000-00-00 00:00:00', 0, '*');
INSERT INTO `bak_categories` VALUES (2, 27, 1, 1, 2, 1, 'uncategorised', 'com_content', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:26:37', 0, '0000-00-00 00:00:00', 0, '*');
INSERT INTO `bak_categories` VALUES (3, 28, 1, 3, 4, 1, 'uncategorised', 'com_banners', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":"","foobar":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:27:35', 0, '0000-00-00 00:00:00', 0, '*');
INSERT INTO `bak_categories` VALUES (4, 29, 1, 5, 6, 1, 'uncategorised', 'com_contact', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:27:57', 0, '0000-00-00 00:00:00', 0, '*');
INSERT INTO `bak_categories` VALUES (5, 30, 1, 7, 8, 1, 'uncategorised', 'com_newsfeeds', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:28:15', 0, '0000-00-00 00:00:00', 0, '*');
INSERT INTO `bak_categories` VALUES (6, 31, 1, 9, 10, 1, 'uncategorised', 'com_weblinks', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:28:33', 0, '0000-00-00 00:00:00', 0, '*');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_contact_details`
-- 

CREATE TABLE `bak_contact_details` (
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
  `published` tinyint(1) NOT NULL default '0',
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL default '0',
  `params` text NOT NULL,
  `user_id` int(11) NOT NULL default '0',
  `catid` int(11) NOT NULL default '0',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `mobile` varchar(255) NOT NULL default '',
  `webpage` varchar(255) NOT NULL default '',
  `sortname1` varchar(255) NOT NULL,
  `sortname2` varchar(255) NOT NULL,
  `sortname3` varchar(255) NOT NULL,
  `language` char(7) NOT NULL,
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `created_by` int(10) unsigned NOT NULL default '0',
  `created_by_alias` varchar(255) NOT NULL default '',
  `modified` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_by` int(10) unsigned NOT NULL default '0',
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `metadata` text NOT NULL,
  `featured` tinyint(3) unsigned NOT NULL default '0' COMMENT 'Set if article is featured.',
  `xreference` varchar(50) NOT NULL COMMENT 'A reference to enable linkages to external data sets.',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`),
  KEY `idx_state` (`published`),
  KEY `idx_catid` (`catid`),
  KEY `idx_createdby` (`created_by`),
  KEY `idx_featured_catid` (`featured`,`catid`),
  KEY `idx_language` (`language`),
  KEY `idx_xreference` (`xreference`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_contact_details`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_content`
-- 

CREATE TABLE `bak_content` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `asset_id` int(10) unsigned NOT NULL default '0' COMMENT 'FK to the #__assets table.',
  `title` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `title_alias` varchar(255) NOT NULL default '',
  `introtext` mediumtext NOT NULL,
  `fulltext` mediumtext NOT NULL,
  `state` tinyint(3) NOT NULL default '0',
  `sectionid` int(10) unsigned NOT NULL default '0',
  `mask` int(10) unsigned NOT NULL default '0',
  `catid` int(10) unsigned NOT NULL default '0',
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `created_by` int(10) unsigned NOT NULL default '0',
  `created_by_alias` varchar(255) NOT NULL default '',
  `modified` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_by` int(10) unsigned NOT NULL default '0',
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  `images` text NOT NULL,
  `urls` text NOT NULL,
  `attribs` varchar(5120) NOT NULL,
  `version` int(10) unsigned NOT NULL default '1',
  `parentid` int(10) unsigned NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `access` int(10) unsigned NOT NULL default '0',
  `hits` int(10) unsigned NOT NULL default '0',
  `metadata` text NOT NULL,
  `featured` tinyint(3) unsigned NOT NULL default '0' COMMENT 'Set if article is featured.',
  `language` char(7) NOT NULL COMMENT 'The language code for the article.',
  `xreference` varchar(50) NOT NULL COMMENT 'A reference to enable linkages to external data sets.',
  PRIMARY KEY  (`id`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`),
  KEY `idx_state` (`state`),
  KEY `idx_catid` (`catid`),
  KEY `idx_createdby` (`created_by`),
  KEY `idx_featured_catid` (`featured`,`catid`),
  KEY `idx_language` (`language`),
  KEY `idx_xreference` (`xreference`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_content`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_content_frontpage`
-- 

CREATE TABLE `bak_content_frontpage` (
  `content_id` int(11) NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_content_frontpage`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_content_rating`
-- 

CREATE TABLE `bak_content_rating` (
  `content_id` int(11) NOT NULL default '0',
  `rating_sum` int(10) unsigned NOT NULL default '0',
  `rating_count` int(10) unsigned NOT NULL default '0',
  `lastip` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_content_rating`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_core_log_searches`
-- 

CREATE TABLE `bak_core_log_searches` (
  `search_term` varchar(128) NOT NULL default '',
  `hits` int(10) unsigned NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_core_log_searches`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_extensions`
-- 

CREATE TABLE `bak_extensions` (
  `extension_id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `type` varchar(20) NOT NULL,
  `element` varchar(100) NOT NULL,
  `folder` varchar(100) NOT NULL,
  `client_id` tinyint(3) NOT NULL,
  `enabled` tinyint(3) NOT NULL default '1',
  `access` tinyint(3) unsigned NOT NULL default '1',
  `protected` tinyint(3) NOT NULL default '0',
  `manifest_cache` text NOT NULL,
  `params` text NOT NULL,
  `custom_data` text NOT NULL,
  `system_data` text NOT NULL,
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) default '0',
  `state` int(11) default '0',
  PRIMARY KEY  (`extension_id`),
  KEY `element_clientid` (`element`,`client_id`),
  KEY `element_folder_clientid` (`element`,`folder`,`client_id`),
  KEY `extension` (`type`,`element`,`folder`,`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 AUTO_INCREMENT=10000 ;

-- 
-- Dumping data for table `bak_extensions`
-- 

INSERT INTO `bak_extensions` VALUES (1, 'com_mailto', 'component', 'com_mailto', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (2, 'com_wrapper', 'component', 'com_wrapper', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (3, 'com_admin', 'component', 'com_admin', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (4, 'com_banners', 'component', 'com_banners', '', 1, 1, 1, 0, '', '{"purchase_type":"3","track_impressions":"0","track_clicks":"0","metakey_prefix":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (5, 'com_cache', 'component', 'com_cache', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (6, 'com_categories', 'component', 'com_categories', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (7, 'com_checkin', 'component', 'com_checkin', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (8, 'com_contact', 'component', 'com_contact', '', 1, 1, 1, 0, '', '{"show_contact_category":"hide","show_contact_list":"0","presentation_style":"sliders","show_name":"1","show_position":"1","show_email":"0","show_street_address":"1","show_suburb":"1","show_state":"1","show_postcode":"1","show_country":"1","show_telephone":"1","show_mobile":"1","show_fax":"1","show_webpage":"1","show_misc":"1","show_image":"1","image":"","allow_vcard":"0","show_articles":"0","show_profile":"0","show_links":"0","linka_name":"","linkb_name":"","linkc_name":"","linkd_name":"","linke_name":"","contact_icons":"0","icon_address":"","icon_email":"","icon_telephone":"","icon_mobile":"","icon_fax":"","icon_misc":"","show_headings":"1","show_position_headings":"1","show_email_headings":"0","show_telephone_headings":"1","show_mobile_headings":"0","show_fax_headings":"0","allow_vcard_headings":"0","show_suburb_headings":"1","show_state_headings":"1","show_country_headings":"1","show_email_form":"1","show_email_copy":"1","banned_email":"","banned_subject":"","banned_text":"","validate_session":"1","custom_reply":"0","redirect":"","show_category_crumb":"0","metakey":"","metadesc":"","robots":"","author":"","rights":"","xreference":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (9, 'com_cpanel', 'component', 'com_cpanel', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (10, 'com_installer', 'component', 'com_installer', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (11, 'com_languages', 'component', 'com_languages', '', 1, 1, 1, 1, '', '{"administrator":"en-GB","site":"en-GB"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (12, 'com_login', 'component', 'com_login', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (13, 'com_media', 'component', 'com_media', '', 1, 1, 0, 1, '', '{"upload_extensions":"bmp,csv,doc,gif,ico,jpg,jpeg,odg,odp,ods,odt,pdf,png,ppt,swf,txt,xcf,xls,BMP,CSV,DOC,GIF,ICO,JPG,JPEG,ODG,ODP,ODS,ODT,PDF,PNG,PPT,SWF,TXT,XCF,XLS","upload_maxsize":"10","file_path":"images","image_path":"images","restrict_uploads":"1","allowed_media_usergroup":"3","check_mime":"1","image_extensions":"bmp,gif,jpg,png","ignore_extensions":"","upload_mime":"image\\/jpeg,image\\/gif,image\\/png,image\\/bmp,application\\/x-shockwave-flash,application\\/msword,application\\/excel,application\\/pdf,application\\/powerpoint,text\\/plain,application\\/x-zip","upload_mime_illegal":"text\\/html","enable_flash":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (14, 'com_menus', 'component', 'com_menus', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (15, 'com_messages', 'component', 'com_messages', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (16, 'com_modules', 'component', 'com_modules', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (17, 'com_newsfeeds', 'component', 'com_newsfeeds', '', 1, 1, 1, 0, '', '{"show_feed_image":"1","show_feed_description":"1","show_item_description":"1","feed_word_count":"0","show_headings":"1","show_name":"1","show_articles":"0","show_link":"1","show_description":"1","show_description_image":"1","display_num":"","show_pagination_limit":"1","show_pagination":"1","show_pagination_results":"1","show_cat_items":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (18, 'com_plugins', 'component', 'com_plugins', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (19, 'com_search', 'component', 'com_search', '', 1, 1, 1, 1, '', '{"enabled":"0","show_date":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (20, 'com_templates', 'component', 'com_templates', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (21, 'com_weblinks', 'component', 'com_weblinks', '', 1, 1, 1, 0, '', '{"show_comp_description":"1","comp_description":"","show_link_hits":"1","show_link_description":"1","show_other_cats":"0","show_headings":"0","show_numbers":"0","show_report":"1","count_clicks":"1","target":"0","link_icons":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (22, 'com_content', 'component', 'com_content', '', 1, 1, 0, 1, '', '{"article_layout":"_:default","show_title":"1","link_titles":"1","show_intro":"1","show_category":"1","link_category":"1","show_parent_category":"0","link_parent_category":"0","show_author":"1","link_author":"0","show_create_date":"0","show_modify_date":"0","show_publish_date":"1","show_item_navigation":"1","show_vote":"0","show_readmore":"1","show_readmore_title":"1","readmore_limit":"100","show_icons":"1","show_print_icon":"1","show_email_icon":"1","show_hits":"1","show_noauth":"0","category_layout":"_:blog","show_category_title":"0","show_description":"0","show_description_image":"0","maxLevel":"1","show_empty_categories":"0","show_no_articles":"1","show_subcat_desc":"1","show_cat_num_articles":"0","show_base_description":"1","maxLevelcat":"-1","show_empty_categories_cat":"0","show_subcat_desc_cat":"1","show_cat_num_articles_cat":"1","num_leading_articles":"1","num_intro_articles":"4","num_columns":"2","num_links":"4","multi_column_order":"0","orderby_pri":"order","orderby_sec":"rdate","order_date":"published","show_pagination_limit":"1","filter_field":"hide","show_headings":"1","list_show_date":"0","date_format":"","list_show_hits":"1","list_show_author":"1","show_pagination":"2","show_pagination_results":"1","show_feed_link":"1","feed_summary":"0","filters":{"1":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"6":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"7":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"2":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"3":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"4":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"5":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"10":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"12":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"8":{"filter_type":"BL","filter_tags":"","filter_attributes":""}}}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (23, 'com_config', 'component', 'com_config', '', 1, 1, 0, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (24, 'com_redirect', 'component', 'com_redirect', '', 1, 1, 0, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (25, 'com_users', 'component', 'com_users', '', 1, 1, 0, 1, '', '{"allowUserRegistration":"1","new_usertype":"2","useractivation":"1","frontend_userparams":"1","mailSubjectPrefix":"","mailBodySuffix":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (100, 'PHPMailer', 'library', 'phpmailer', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (101, 'SimplePie', 'library', 'simplepie', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (102, 'phputf8', 'library', 'phputf8', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (103, 'Joomla! Web Application Framework', 'library', 'joomla', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:33:"Joomla! Web Application Framework";s:4:"type";s:7:"library";s:12:"creationDate";s:4:"2008";s:6:"author";s:6:"Joomla";s:9:"copyright";s:67:"Copyright (C) 2005 - 2011 Open Source Matters. All rights reserved.";s:11:"authorEmail";s:16:"admin@joomla.org";s:9:"authorUrl";s:21:"http://www.joomla.org";s:7:"version";s:5:"1.6.0";s:11:"description";s:90:"The Joomla! Web Application Framework is the Core of the Joomla! Content Management System";s:5:"group";s:0:"";}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (200, 'mod_articles_archive', 'module', 'mod_articles_archive', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (201, 'mod_articles_latest', 'module', 'mod_articles_latest', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (202, 'mod_articles_popular', 'module', 'mod_articles_popular', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (203, 'mod_banners', 'module', 'mod_banners', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (204, 'mod_breadcrumbs', 'module', 'mod_breadcrumbs', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (205, 'mod_custom', 'module', 'mod_custom', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (206, 'mod_feed', 'module', 'mod_feed', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (207, 'mod_footer', 'module', 'mod_footer', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (208, 'mod_login', 'module', 'mod_login', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (209, 'mod_menu', 'module', 'mod_menu', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (210, 'mod_articles_news', 'module', 'mod_articles_news', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (211, 'mod_random_image', 'module', 'mod_random_image', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (212, 'mod_related_items', 'module', 'mod_related_items', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (213, 'mod_search', 'module', 'mod_search', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (214, 'mod_stats', 'module', 'mod_stats', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (215, 'mod_syndicate', 'module', 'mod_syndicate', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (216, 'mod_users_latest', 'module', 'mod_users_latest', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (217, 'mod_weblinks', 'module', 'mod_weblinks', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (218, 'mod_whosonline', 'module', 'mod_whosonline', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (219, 'mod_wrapper', 'module', 'mod_wrapper', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (220, 'mod_articles_category', 'module', 'mod_articles_category', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (221, 'mod_articles_categories', 'module', 'mod_articles_categories', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (222, 'mod_languages', 'module', 'mod_languages', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (300, 'mod_custom', 'module', 'mod_custom', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (301, 'mod_feed', 'module', 'mod_feed', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (302, 'mod_latest', 'module', 'mod_latest', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (303, 'mod_logged', 'module', 'mod_logged', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (304, 'mod_login', 'module', 'mod_login', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (305, 'mod_menu', 'module', 'mod_menu', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (306, 'mod_online', 'module', 'mod_online', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (307, 'mod_popular', 'module', 'mod_popular', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (308, 'mod_quickicon', 'module', 'mod_quickicon', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (309, 'mod_status', 'module', 'mod_status', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (310, 'mod_submenu', 'module', 'mod_submenu', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (311, 'mod_title', 'module', 'mod_title', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (312, 'mod_toolbar', 'module', 'mod_toolbar', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (313, 'mod_unread', 'module', 'mod_unread', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (400, 'plg_authentication_gmail', 'plugin', 'gmail', 'authentication', 0, 0, 1, 0, '', '{"applysuffix":"0","suffix":"","verifypeer":"1","user_blacklist":""}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `bak_extensions` VALUES (401, 'plg_authentication_joomla', 'plugin', 'joomla', 'authentication', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (402, 'plg_authentication_ldap', 'plugin', 'ldap', 'authentication', 0, 0, 1, 0, '', '{"host":"","port":"389","use_ldapV3":"0","negotiate_tls":"0","no_referrals":"0","auth_method":"bind","base_dn":"","search_string":"","users_dn":"","username":"admin","password":"bobby7","ldap_fullname":"fullName","ldap_email":"mail","ldap_uid":"uid"}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `bak_extensions` VALUES (404, 'plg_content_emailcloak', 'plugin', 'emailcloak', 'content', 0, 1, 1, 0, '', '{"mode":"1"}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `bak_extensions` VALUES (405, 'plg_content_geshi', 'plugin', 'geshi', 'content', 0, 0, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `bak_extensions` VALUES (406, 'plg_content_loadmodule', 'plugin', 'loadmodule', 'content', 0, 1, 1, 0, '', '{"style":"none"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (407, 'plg_content_pagebreak', 'plugin', 'pagebreak', 'content', 0, 1, 1, 1, '', '{"title":"1","multipage_toc":"1","showall":"1"}', '', '', 0, '0000-00-00 00:00:00', 4, 0);
INSERT INTO `bak_extensions` VALUES (408, 'plg_content_pagenavigation', 'plugin', 'pagenavigation', 'content', 0, 1, 1, 1, '', '{"position":"1"}', '', '', 0, '0000-00-00 00:00:00', 5, 0);
INSERT INTO `bak_extensions` VALUES (409, 'plg_content_vote', 'plugin', 'vote', 'content', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 6, 0);
INSERT INTO `bak_extensions` VALUES (410, 'plg_editors_codemirror', 'plugin', 'codemirror', 'editors', 0, 1, 1, 1, '', '{"linenumbers":"0","tabmode":"indent"}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `bak_extensions` VALUES (411, 'plg_editors_none', 'plugin', 'none', 'editors', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `bak_extensions` VALUES (412, 'plg_editors_tinymce', 'plugin', 'tinymce', 'editors', 0, 1, 1, 1, '', '{"mode":"1","skin":"0","compressed":"0","cleanup_startup":"0","cleanup_save":"2","entity_encoding":"raw","lang_mode":"0","lang_code":"en","text_direction":"ltr","content_css":"1","content_css_custom":"","relative_urls":"1","newlines":"0","invalid_elements":"script,applet,iframe","extended_elements":"","toolbar":"top","toolbar_align":"left","html_height":"550","html_width":"750","element_path":"1","fonts":"1","paste":"1","searchreplace":"1","insertdate":"1","format_date":"%Y-%m-%d","inserttime":"1","format_time":"%H:%M:%S","colors":"1","table":"1","smilies":"1","media":"1","hr":"1","directionality":"1","fullscreen":"1","style":"1","layer":"1","xhtmlxtras":"1","visualchars":"1","nonbreaking":"1","template":"1","blockquote":"1","wordcount":"1","advimage":"1","advlink":"1","autosave":"1","contextmenu":"1","inlinepopups":"1","safari":"0","custom_plugin":"","custom_button":""}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `bak_extensions` VALUES (413, 'plg_editors-xtd_article', 'plugin', 'article', 'editors-xtd', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `bak_extensions` VALUES (414, 'plg_editors-xtd_image', 'plugin', 'image', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `bak_extensions` VALUES (415, 'plg_editors-xtd_pagebreak', 'plugin', 'pagebreak', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `bak_extensions` VALUES (416, 'plg_editors-xtd_readmore', 'plugin', 'readmore', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 4, 0);
INSERT INTO `bak_extensions` VALUES (417, 'plg_search_categories', 'plugin', 'categories', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (418, 'plg_search_contacts', 'plugin', 'contacts', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (419, 'plg_search_content', 'plugin', 'content', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (420, 'plg_search_newsfeeds', 'plugin', 'newsfeeds', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (421, 'plg_search_weblinks', 'plugin', 'weblinks', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (422, 'plg_system_languagefilter', 'plugin', 'languagefilter', 'system', 0, 0, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `bak_extensions` VALUES (423, 'plg_system_p3p', 'plugin', 'p3p', 'system', 0, 1, 1, 1, '', '{"headers":"NOI ADM DEV PSAi COM NAV OUR OTRo STP IND DEM"}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `bak_extensions` VALUES (424, 'plg_system_cache', 'plugin', 'cache', 'system', 0, 0, 1, 1, '', '{"browsercache":"0","cachetime":"15"}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `bak_extensions` VALUES (425, 'plg_system_debug', 'plugin', 'debug', 'system', 0, 1, 1, 0, '', '{"profile":"1","queries":"1","memory":"1","language_files":"1","language_strings":"1","strip-first":"1","strip-prefix":"","strip-suffix":""}', '', '', 0, '0000-00-00 00:00:00', 4, 0);
INSERT INTO `bak_extensions` VALUES (426, 'plg_system_log', 'plugin', 'log', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 5, 0);
INSERT INTO `bak_extensions` VALUES (427, 'plg_system_redirect', 'plugin', 'redirect', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 6, 0);
INSERT INTO `bak_extensions` VALUES (428, 'plg_system_remember', 'plugin', 'remember', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 7, 0);
INSERT INTO `bak_extensions` VALUES (429, 'plg_system_sef', 'plugin', 'sef', 'system', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 8, 0);
INSERT INTO `bak_extensions` VALUES (430, 'plg_system_logout', 'plugin', 'logout', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 9, 0);
INSERT INTO `bak_extensions` VALUES (431, 'plg_user_contactcreator', 'plugin', 'contactcreator', 'user', 0, 0, 1, 1, '', '{"autowebpage":"","category":"34","autopublish":"0"}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `bak_extensions` VALUES (432, 'plg_user_joomla', 'plugin', 'joomla', 'user', 0, 1, 1, 0, '', '{"autoregister":"1"}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `bak_extensions` VALUES (433, 'plg_user_profile', 'plugin', 'profile', 'user', 0, 0, 1, 1, '', '{"register-require_address1":"1","register-require_address2":"1","register-require_city":"1","register-require_region":"1","register-require_country":"1","register-require_postal_code":"1","register-require_phone":"1","register-require_website":"1","register-require_favoritebook":"1","register-require_aboutme":"1","register-require_tos":"1","register-require_dob":"1","profile-require_address1":"1","profile-require_address2":"1","profile-require_city":"1","profile-require_region":"1","profile-require_country":"1","profile-require_postal_code":"1","profile-require_phone":"1","profile-require_website":"1","profile-require_favoritebook":"1","profile-require_aboutme":"1","profile-require_tos":"1","profile-require_dob":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (434, 'plg_extension_joomla', 'plugin', 'joomla', 'extension', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `bak_extensions` VALUES (435, 'plg_content_joomla', 'plugin', 'joomla', 'content', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (500, 'atomic', 'template', 'atomic', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:6:"atomic";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"10/10/09";s:6:"author";s:12:"Ron Severdia";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"contact@kontentdesign.com";s:9:"authorUrl";s:28:"http://www.kontentdesign.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:26:"TPL_ATOMIC_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (502, 'bluestork', 'template', 'bluestork', '', 1, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:9:"bluestork";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"07/02/09";s:6:"author";s:12:"Ron Severdia";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"contact@kontentdesign.com";s:9:"authorUrl";s:28:"http://www.kontentdesign.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:29:"TPL_BLUESTORK_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"useRoundedCorners":"1","showSiteName":"0","textBig":"0","highContrast":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (503, 'beez_20', 'template', 'beez_20', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:7:"beez_20";s:4:"type";s:8:"template";s:12:"creationDate";s:16:"25 November 2009";s:6:"author";s:12:"Angie Radtke";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:23:"a.radtke@derauftritt.de";s:9:"authorUrl";s:26:"http://www.der-auftritt.de";s:7:"version";s:5:"1.6.0";s:11:"description";s:25:"TPL_BEEZ2_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"wrapperSmall":"53","wrapperLarge":"72","sitetitle":"","sitedescription":"","navposition":"center","templatecolor":"nature"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (504, 'hathor', 'template', 'hathor', '', 1, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:6:"hathor";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"May 2010";s:6:"author";s:11:"Andrea Tarr";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"hathor@tarrconsulting.com";s:9:"authorUrl";s:29:"http://www.tarrconsulting.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:26:"TPL_HATHOR_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"showSiteName":"0","colourChoice":"0","boldText":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (505, 'beez5', 'template', 'beez5', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:5:"beez5";s:4:"type";s:8:"template";s:12:"creationDate";s:11:"21 May 2010";s:6:"author";s:12:"Angie Radtke";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:23:"a.radtke@derauftritt.de";s:9:"authorUrl";s:26:"http://www.der-auftritt.de";s:7:"version";s:5:"1.6.0";s:11:"description";s:25:"TPL_BEEZ5_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"wrapperSmall":"53","wrapperLarge":"72","sitetitle":"","sitedescription":"","navposition":"center","html5":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (600, 'English (United Kingdom)', 'language', 'en-GB', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (601, 'English (United Kingdom)', 'language', 'en-GB', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `bak_extensions` VALUES (700, 'Joomla! CMS', 'file', 'joomla', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_languages`
-- 

CREATE TABLE `bak_languages` (
  `lang_id` int(11) unsigned NOT NULL auto_increment,
  `lang_code` char(7) NOT NULL,
  `title` varchar(50) NOT NULL,
  `title_native` varchar(50) NOT NULL,
  `sef` varchar(50) NOT NULL,
  `image` varchar(50) NOT NULL,
  `description` varchar(512) NOT NULL,
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `published` int(11) NOT NULL default '0',
  PRIMARY KEY  (`lang_id`),
  UNIQUE KEY `idx_sef` (`sef`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `bak_languages`
-- 

INSERT INTO `bak_languages` VALUES (1, 'en-GB', 'English (UK)', 'English (UK)', 'en', 'en', '', '', '', 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_menu`
-- 

CREATE TABLE `bak_menu` (
  `id` int(11) NOT NULL auto_increment,
  `menutype` varchar(24) NOT NULL COMMENT 'The type of menu this item belongs to. FK to #__menu_types.menutype',
  `title` varchar(255) NOT NULL COMMENT 'The display title of the menu item.',
  `alias` varchar(255) NOT NULL COMMENT 'The SEF alias of the menu item.',
  `note` varchar(255) NOT NULL default '',
  `path` varchar(1024) NOT NULL COMMENT 'The computed path of the menu item based on the alias field.',
  `link` varchar(1024) NOT NULL COMMENT 'The actually link the menu item refers to.',
  `type` varchar(16) NOT NULL COMMENT 'The type of link: Component, URL, Alias, Separator',
  `published` tinyint(4) NOT NULL default '0' COMMENT 'The published state of the menu link.',
  `parent_id` int(10) unsigned NOT NULL default '1' COMMENT 'The parent menu item in the menu tree.',
  `level` int(10) unsigned NOT NULL default '0' COMMENT 'The relative level in the tree.',
  `component_id` int(10) unsigned NOT NULL default '0' COMMENT 'FK to #__extensions.id',
  `ordering` int(11) NOT NULL default '0' COMMENT 'The relative ordering of the menu item in the tree.',
  `checked_out` int(10) unsigned NOT NULL default '0' COMMENT 'FK to #__users.id',
  `checked_out_time` timestamp NOT NULL default '0000-00-00 00:00:00' COMMENT 'The time the menu item was checked out.',
  `browserNav` tinyint(4) NOT NULL default '0' COMMENT 'The click behaviour of the link.',
  `access` tinyint(3) unsigned NOT NULL default '0' COMMENT 'The access level required to view the menu item.',
  `img` varchar(255) NOT NULL COMMENT 'The image of the menu item.',
  `template_style_id` int(10) unsigned NOT NULL default '0',
  `params` text NOT NULL COMMENT 'JSON encoded data for the menu item.',
  `lft` int(11) NOT NULL default '0' COMMENT 'Nested set lft.',
  `rgt` int(11) NOT NULL default '0' COMMENT 'Nested set rgt.',
  `home` tinyint(3) unsigned NOT NULL default '0' COMMENT 'Indicates if this menu item is the home or default page.',
  `language` char(7) NOT NULL default '',
  `client_id` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_client_id_parent_id_alias` (`client_id`,`parent_id`,`alias`),
  KEY `idx_componentid` (`component_id`,`menutype`,`published`,`access`),
  KEY `idx_menutype` (`menutype`),
  KEY `idx_left_right` (`lft`,`rgt`),
  KEY `idx_alias` (`alias`),
  KEY `idx_path` (`path`(333)),
  KEY `idx_language` (`language`)
) ENGINE=MyISAM AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 AUTO_INCREMENT=102 ;

-- 
-- Dumping data for table `bak_menu`
-- 

INSERT INTO `bak_menu` VALUES (1, '', 'Menu_Item_Root', 'root', '', '', '', '', 1, 0, 0, 0, 0, 0, '0000-00-00 00:00:00', 0, 0, '', 0, '', 0, 41, 0, '*', 0);
INSERT INTO `bak_menu` VALUES (2, 'menu', 'com_banners', 'Banners', '', 'Banners', 'index.php?option=com_banners', 'component', 0, 1, 1, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners', 0, '', 1, 10, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (3, 'menu', 'com_banners', 'Banners', '', 'Banners/Banners', 'index.php?option=com_banners', 'component', 0, 2, 2, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners', 0, '', 2, 3, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (4, 'menu', 'com_banners_categories', 'Categories', '', 'Banners/Categories', 'index.php?option=com_categories&extension=com_banners', 'component', 0, 2, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners-cat', 0, '', 4, 5, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (5, 'menu', 'com_banners_clients', 'Clients', '', 'Banners/Clients', 'index.php?option=com_banners&view=clients', 'component', 0, 2, 2, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners-clients', 0, '', 6, 7, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (6, 'menu', 'com_banners_tracks', 'Tracks', '', 'Banners/Tracks', 'index.php?option=com_banners&view=tracks', 'component', 0, 2, 2, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners-tracks', 0, '', 8, 9, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (7, 'menu', 'com_contact', 'Contacts', '', 'Contacts', 'index.php?option=com_contact', 'component', 0, 1, 1, 8, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:contact', 0, '', 11, 16, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (8, 'menu', 'com_contact', 'Contacts', '', 'Contacts/Contacts', 'index.php?option=com_contact', 'component', 0, 7, 2, 8, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:contact', 0, '', 12, 13, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (9, 'menu', 'com_contact_categories', 'Categories', '', 'Contacts/Categories', 'index.php?option=com_categories&extension=com_contact', 'component', 0, 7, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:contact-cat', 0, '', 14, 15, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (10, 'menu', 'com_messages', 'Messaging', '', 'Messaging', 'index.php?option=com_messages', 'component', 0, 1, 1, 15, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:messages', 0, '', 17, 22, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (11, 'menu', 'com_messages_add', 'New Private Message', '', 'Messaging/New Private Message', 'index.php?option=com_messages&task=message.add', 'component', 0, 10, 2, 15, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:messages-add', 0, '', 18, 19, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (12, 'menu', 'com_messages_read', 'Read Private Message', '', 'Messaging/Read Private Message', 'index.php?option=com_messages', 'component', 0, 10, 2, 15, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:messages-read', 0, '', 20, 21, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (13, 'menu', 'com_newsfeeds', 'News Feeds', '', 'News Feeds', 'index.php?option=com_newsfeeds', 'component', 0, 1, 1, 17, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:newsfeeds', 0, '', 23, 28, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (14, 'menu', 'com_newsfeeds_feeds', 'Feeds', '', 'News Feeds/Feeds', 'index.php?option=com_newsfeeds', 'component', 0, 13, 2, 17, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:newsfeeds', 0, '', 24, 25, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (15, 'menu', 'com_newsfeeds_categories', 'Categories', '', 'News Feeds/Categories', 'index.php?option=com_categories&extension=com_newsfeeds', 'component', 0, 13, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:newsfeeds-cat', 0, '', 26, 27, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (16, 'menu', 'com_redirect', 'Redirect', '', 'Redirect', 'index.php?option=com_redirect', 'component', 0, 1, 1, 24, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:redirect', 0, '', 37, 38, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (17, 'menu', 'com_search', 'Search', '', 'Search', 'index.php?option=com_search', 'component', 0, 1, 1, 19, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:search', 0, '', 29, 30, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (18, 'menu', 'com_weblinks', 'Weblinks', '', 'Weblinks', 'index.php?option=com_weblinks', 'component', 0, 1, 1, 21, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:weblinks', 0, '', 31, 36, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (19, 'menu', 'com_weblinks_links', 'Links', '', 'Weblinks/Links', 'index.php?option=com_weblinks', 'component', 0, 18, 2, 21, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:weblinks', 0, '', 32, 33, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (20, 'menu', 'com_weblinks_categories', 'Categories', '', 'Weblinks/Categories', 'index.php?option=com_categories&extension=com_weblinks', 'component', 0, 18, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:weblinks-cat', 0, '', 34, 35, 0, '*', 1);
INSERT INTO `bak_menu` VALUES (101, 'mainmenu', 'Home', 'home', '', 'home', 'index.php?option=com_content&view=featured', 'component', 1, 1, 1, 22, 0, 0, '0000-00-00 00:00:00', 0, 1, '', 0, '{"num_leading_articles":"1","num_intro_articles":"3","num_columns":"3","num_links":"0","orderby_pri":"","orderby_sec":"front","order_date":"","multi_column_order":"1","show_pagination":"2","show_pagination_results":"1","show_noauth":"","article-allow_ratings":"","article-allow_comments":"","show_feed_link":"1","feed_summary":"","show_title":"","link_titles":"","show_intro":"","show_category":"","link_category":"","show_parent_category":"","link_parent_category":"","show_author":"","show_create_date":"","show_modify_date":"","show_publish_date":"","show_item_navigation":"","show_readmore":"","show_icons":"","show_print_icon":"","show_email_icon":"","show_hits":"","menu-anchor_title":"","menu-anchor_css":"","menu_image":"","show_page_heading":1,"page_title":"","page_heading":"","pageclass_sfx":"","menu-meta_description":"","menu-meta_keywords":"","robots":"","secure":0}', 39, 40, 1, '*', 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_menu_types`
-- 

CREATE TABLE `bak_menu_types` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `menutype` varchar(24) NOT NULL,
  `title` varchar(48) NOT NULL,
  `description` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_menutype` (`menutype`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `bak_menu_types`
-- 

INSERT INTO `bak_menu_types` VALUES (1, 'mainmenu', 'Main Menu', 'The main menu for the site');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_messages`
-- 

CREATE TABLE `bak_messages` (
  `message_id` int(10) unsigned NOT NULL auto_increment,
  `user_id_from` int(10) unsigned NOT NULL default '0',
  `user_id_to` int(10) unsigned NOT NULL default '0',
  `folder_id` tinyint(3) unsigned NOT NULL default '0',
  `date_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `state` tinyint(1) NOT NULL default '0',
  `priority` tinyint(1) unsigned NOT NULL default '0',
  `subject` varchar(255) NOT NULL default '',
  `message` text NOT NULL,
  PRIMARY KEY  (`message_id`),
  KEY `useridto_state` (`user_id_to`,`state`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_messages`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_messages_cfg`
-- 

CREATE TABLE `bak_messages_cfg` (
  `user_id` int(10) unsigned NOT NULL default '0',
  `cfg_name` varchar(100) NOT NULL default '',
  `cfg_value` varchar(255) NOT NULL default '',
  UNIQUE KEY `idx_user_var_name` (`user_id`,`cfg_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_messages_cfg`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_modules`
-- 

CREATE TABLE `bak_modules` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(100) NOT NULL default '',
  `note` varchar(255) NOT NULL default '',
  `content` text NOT NULL,
  `ordering` int(11) NOT NULL default '0',
  `position` varchar(50) default NULL,
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  `published` tinyint(1) NOT NULL default '0',
  `module` varchar(50) default NULL,
  `access` tinyint(3) unsigned NOT NULL default '0',
  `showtitle` tinyint(3) unsigned NOT NULL default '1',
  `params` text NOT NULL,
  `client_id` tinyint(4) NOT NULL default '0',
  `language` char(7) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `published` (`published`,`access`),
  KEY `newsfeeds` (`module`,`published`),
  KEY `idx_language` (`language`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

-- 
-- Dumping data for table `bak_modules`
-- 

INSERT INTO `bak_modules` VALUES (1, 'Main Menu', '', '', 1, 'position-7', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_menu', 1, 1, '{"menutype":"mainmenu","startLevel":"0","endLevel":"0","showAllChildren":"0","tag_id":"","class_sfx":"","window_open":"","layout":"","moduleclass_sfx":"_menu","cache":"1","cache_time":"900","cachemode":"itemid"}', 0, '*');
INSERT INTO `bak_modules` VALUES (2, 'Login', '', '', 1, 'login', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_login', 1, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (3, 'Popular Articles', '', '', 3, 'cpanel', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_popular', 3, 1, '{"count":"5","catid":"","user_id":"0","layout":"_:default","moduleclass_sfx":"","cache":"0","automatic_title":"1"}', 1, '*');
INSERT INTO `bak_modules` VALUES (4, 'Recently Added Articles', '', '', 4, 'cpanel', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_latest', 3, 1, '{"count":"5","ordering":"c_dsc","catid":"","user_id":"0","layout":"_:default","moduleclass_sfx":"","cache":"0","automatic_title":"1"}', 1, '*');
INSERT INTO `bak_modules` VALUES (6, 'Unread Messages', '', '', 1, 'header', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_unread', 3, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (7, 'Online Users', '', '', 2, 'header', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_online', 3, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (8, 'Toolbar', '', '', 1, 'toolbar', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_toolbar', 3, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (9, 'Quick Icons', '', '', 1, 'icon', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_quickicon', 3, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (10, 'Logged-in Users', '', '', 2, 'cpanel', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_logged', 3, 1, '{"count":"5","name":"1","layout":"_:default","moduleclass_sfx":"","cache":"0","automatic_title":"1"}', 1, '*');
INSERT INTO `bak_modules` VALUES (12, 'Admin Menu', '', '', 1, 'menu', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_menu', 3, 1, '{"layout":"","moduleclass_sfx":"","shownew":"1","showhelp":"1","cache":"0"}', 1, '*');
INSERT INTO `bak_modules` VALUES (13, 'Admin Submenu', '', '', 1, 'submenu', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_submenu', 3, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (14, 'User Status', '', '', 1, 'status', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_status', 3, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (15, 'Title', '', '', 1, 'title', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_title', 3, 1, '', 1, '*');
INSERT INTO `bak_modules` VALUES (16, 'Login Form', '', '', 7, 'position-7', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_login', 1, 1, '{"greeting":"1","name":"0"}', 0, '*');
INSERT INTO `bak_modules` VALUES (17, 'Breadcrumbs', '', '', 1, 'position-2', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_breadcrumbs', 1, 1, '{"moduleclass_sfx":"","showHome":"1","homeText":"Home","showComponent":"1","separator":"","cache":"1","cache_time":"900","cachemode":"itemid"}', 0, '*');
INSERT INTO `bak_modules` VALUES (18, 'Banners', '', '', 1, 'position-5', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 0, 'mod_banners', 1, 1, '{"target":"1","count":"1","cid":"1","catid":["27"],"tag_search":"0","ordering":"0","header_text":"","footer_text":"","layout":"","moduleclass_sfx":"","cache":"1","cache_time":"900"}', 0, '*');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_modules_menu`
-- 

CREATE TABLE `bak_modules_menu` (
  `moduleid` int(11) NOT NULL default '0',
  `menuid` int(11) NOT NULL default '0',
  PRIMARY KEY  (`moduleid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_modules_menu`
-- 

INSERT INTO `bak_modules_menu` VALUES (1, 0);
INSERT INTO `bak_modules_menu` VALUES (2, 0);
INSERT INTO `bak_modules_menu` VALUES (3, 0);
INSERT INTO `bak_modules_menu` VALUES (4, 0);
INSERT INTO `bak_modules_menu` VALUES (6, 0);
INSERT INTO `bak_modules_menu` VALUES (7, 0);
INSERT INTO `bak_modules_menu` VALUES (8, 0);
INSERT INTO `bak_modules_menu` VALUES (9, 0);
INSERT INTO `bak_modules_menu` VALUES (10, 0);
INSERT INTO `bak_modules_menu` VALUES (12, 0);
INSERT INTO `bak_modules_menu` VALUES (13, 0);
INSERT INTO `bak_modules_menu` VALUES (14, 0);
INSERT INTO `bak_modules_menu` VALUES (15, 0);
INSERT INTO `bak_modules_menu` VALUES (16, 0);
INSERT INTO `bak_modules_menu` VALUES (17, 0);
INSERT INTO `bak_modules_menu` VALUES (18, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_newsfeeds`
-- 

CREATE TABLE `bak_newsfeeds` (
  `catid` int(11) NOT NULL default '0',
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(100) NOT NULL default '',
  `alias` varchar(100) NOT NULL default '',
  `link` varchar(200) NOT NULL default '',
  `filename` varchar(200) default NULL,
  `published` tinyint(1) NOT NULL default '0',
  `numarticles` int(10) unsigned NOT NULL default '1',
  `cache_time` int(10) unsigned NOT NULL default '3600',
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL default '0',
  `rtl` tinyint(4) NOT NULL default '0',
  `access` tinyint(3) unsigned NOT NULL default '0',
  `language` char(7) NOT NULL default '',
  `params` text NOT NULL,
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `created_by` int(10) unsigned NOT NULL default '0',
  `created_by_alias` varchar(255) NOT NULL default '',
  `modified` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_by` int(10) unsigned NOT NULL default '0',
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `metadata` text NOT NULL,
  `xreference` varchar(50) NOT NULL COMMENT 'A reference to enable linkages to external data sets.',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`),
  KEY `idx_state` (`published`),
  KEY `idx_catid` (`catid`),
  KEY `idx_createdby` (`created_by`),
  KEY `idx_language` (`language`),
  KEY `idx_xreference` (`xreference`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_newsfeeds`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_redirect_links`
-- 

CREATE TABLE `bak_redirect_links` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `old_url` varchar(150) NOT NULL,
  `new_url` varchar(150) NOT NULL,
  `referer` varchar(150) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `published` tinyint(4) NOT NULL,
  `created_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_link_old` (`old_url`),
  KEY `idx_link_modifed` (`modified_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_redirect_links`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_schemas`
-- 

CREATE TABLE `bak_schemas` (
  `extension_id` int(11) NOT NULL,
  `version_id` varchar(20) NOT NULL,
  PRIMARY KEY  (`extension_id`,`version_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_schemas`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_session`
-- 

CREATE TABLE `bak_session` (
  `session_id` varchar(32) NOT NULL default '',
  `client_id` tinyint(3) unsigned NOT NULL default '0',
  `guest` tinyint(4) unsigned default '1',
  `time` varchar(14) default '',
  `data` varchar(20480) default NULL,
  `userid` int(11) default '0',
  `username` varchar(150) default '',
  `usertype` varchar(50) default '',
  PRIMARY KEY  (`session_id`),
  KEY `whosonline` (`guest`,`usertype`),
  KEY `userid` (`userid`),
  KEY `time` (`time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_session`
-- 

INSERT INTO `bak_session` VALUES ('9a409054dde89198421994c45e63fc11', 1, 0, '1300044567', '__default|a:8:{s:15:"session.counter";i:3;s:19:"session.timer.start";i:1300044556;s:18:"session.timer.last";i:1300044566;s:17:"session.timer.now";i:1300044566;s:22:"session.client.browser";s:120:"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.127 Safari/534.16";s:8:"registry";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":1:{s:11:"application";O:8:"stdClass":1:{s:4:"lang";s:0:"";}}}s:4:"user";O:5:"JUser":23:{s:9:"\0*\0isRoot";b:1;s:2:"id";s:2:"42";s:4:"name";s:10:"Super User";s:8:"username";s:5:"admin";s:5:"email";s:31:"jacek.p.kolodziejczyk@gmail.com";s:8:"password";s:65:"f3ec4891168e0ffee89db7bd9503e302:pjrsJhscB0lVpPCKrHObRMQNLM8Bwh2t";s:14:"password_clear";s:0:"";s:8:"usertype";s:10:"deprecated";s:5:"block";s:1:"0";s:9:"sendEmail";s:1:"1";s:12:"registerDate";s:19:"2011-03-13 19:22:32";s:13:"lastvisitDate";s:19:"0000-00-00 00:00:00";s:10:"activation";s:0:"";s:6:"params";s:0:"";s:6:"groups";a:1:{s:11:"Super Users";s:1:"8";}s:5:"guest";i:0;s:10:"\0*\0_params";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":0:{}}s:14:"\0*\0_authGroups";a:2:{i:0;i:1;i:1;i:8;}s:14:"\0*\0_authLevels";a:4:{i:0;i:1;i:1;i:1;i:2;i:2;i:3;i:3;}s:15:"\0*\0_authActions";N;s:12:"\0*\0_errorMsg";N;s:10:"\0*\0_errors";a:0:{}s:3:"aid";i:0;}s:13:"session.token";s:32:"6ce1cc48ea72d1a1babaf07b42246694";}', 42, 'admin', '');
INSERT INTO `bak_session` VALUES ('d2ce8ce940dc22ec66b4df1f43da659f', 0, 1, '1300044578', '__default|a:8:{s:15:"session.counter";i:1;s:19:"session.timer.start";i:1300044578;s:18:"session.timer.last";i:1300044578;s:17:"session.timer.now";i:1300044578;s:22:"session.client.browser";s:120:"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.127 Safari/534.16";s:8:"registry";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":0:{}}s:4:"user";O:5:"JUser":23:{s:9:"\0*\0isRoot";b:0;s:2:"id";i:0;s:4:"name";N;s:8:"username";N;s:5:"email";N;s:8:"password";N;s:14:"password_clear";s:0:"";s:8:"usertype";N;s:5:"block";N;s:9:"sendEmail";i:0;s:12:"registerDate";N;s:13:"lastvisitDate";N;s:10:"activation";N;s:6:"params";N;s:6:"groups";a:0:{}s:5:"guest";i:1;s:10:"\0*\0_params";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":0:{}}s:14:"\0*\0_authGroups";a:1:{i:0;i:1;}s:14:"\0*\0_authLevels";a:2:{i:0;i:1;i:1;i:1;}s:15:"\0*\0_authActions";N;s:12:"\0*\0_errorMsg";N;s:10:"\0*\0_errors";a:0:{}s:3:"aid";i:0;}s:13:"session.token";s:32:"7990a6d31902f83be85fc5626f898485";}', 0, '', '');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_template_styles`
-- 

CREATE TABLE `bak_template_styles` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `template` varchar(50) NOT NULL default '',
  `client_id` tinyint(1) unsigned NOT NULL default '0',
  `home` char(7) NOT NULL default '0',
  `title` varchar(255) NOT NULL default '',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_template` (`template`),
  KEY `idx_home` (`home`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- Dumping data for table `bak_template_styles`
-- 

INSERT INTO `bak_template_styles` VALUES (2, 'bluestork', 1, '1', 'Bluestork - Default', '{"useRoundedCorners":"1","showSiteName":"0"}');
INSERT INTO `bak_template_styles` VALUES (3, 'atomic', 0, '0', 'Atomic - Default', '{}');
INSERT INTO `bak_template_styles` VALUES (4, 'beez_20', 0, '1', 'Beez2 - Default', '{"wrapperSmall":"53","wrapperLarge":"72","logo":"images\\/joomla_black.gif","sitetitle":"Joomla!","sitedescription":"Open Source Content Management","navposition":"left","templatecolor":"personal","html5":"0"}');
INSERT INTO `bak_template_styles` VALUES (5, 'hathor', 1, '0', 'Hathor - Default', '{"showSiteName":"0","colourChoice":"","boldText":"0"}');
INSERT INTO `bak_template_styles` VALUES (6, 'beez5', 0, '0', 'Beez5 - Default-Fruit Shop', '{"wrapperSmall":"53","wrapperLarge":"72","logo":"images\\/sampledata\\/fruitshop\\/fruits.gif","sitetitle":"Matuna Market ","sitedescription":"Fruit Shop Sample Site","navposition":"left","html5":"0"}');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_update_categories`
-- 

CREATE TABLE `bak_update_categories` (
  `categoryid` int(11) NOT NULL auto_increment,
  `name` varchar(20) default '',
  `description` text NOT NULL,
  `parent` int(11) default '0',
  `updatesite` int(11) default '0',
  PRIMARY KEY  (`categoryid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Update Categories' AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_update_categories`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_update_sites`
-- 

CREATE TABLE `bak_update_sites` (
  `update_site_id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `type` varchar(20) default '',
  `location` text NOT NULL,
  `enabled` int(11) default '0',
  PRIMARY KEY  (`update_site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Update Sites' AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `bak_update_sites`
-- 

INSERT INTO `bak_update_sites` VALUES (1, 'Joomla Core', 'collection', 'http://update.joomla.org/core/list.xml', 1);
INSERT INTO `bak_update_sites` VALUES (2, 'Joomla Extension Directory', 'collection', 'http://update.joomla.org/jed/list.xml', 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_update_sites_extensions`
-- 

CREATE TABLE `bak_update_sites_extensions` (
  `update_site_id` int(11) NOT NULL default '0',
  `extension_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`update_site_id`,`extension_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Links extensions to update sites';

-- 
-- Dumping data for table `bak_update_sites_extensions`
-- 

INSERT INTO `bak_update_sites_extensions` VALUES (1, 700);
INSERT INTO `bak_update_sites_extensions` VALUES (2, 700);

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_updates`
-- 

CREATE TABLE `bak_updates` (
  `update_id` int(11) NOT NULL auto_increment,
  `update_site_id` int(11) default '0',
  `extension_id` int(11) default '0',
  `categoryid` int(11) default '0',
  `name` varchar(100) default '',
  `description` text NOT NULL,
  `element` varchar(100) default '',
  `type` varchar(20) default '',
  `folder` varchar(20) default '',
  `client_id` tinyint(3) default '0',
  `version` varchar(10) default '',
  `data` text NOT NULL,
  `detailsurl` text NOT NULL,
  PRIMARY KEY  (`update_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Available Updates' AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_updates`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_user_profiles`
-- 

CREATE TABLE `bak_user_profiles` (
  `user_id` int(11) NOT NULL,
  `profile_key` varchar(100) NOT NULL,
  `profile_value` varchar(255) NOT NULL,
  `ordering` int(11) NOT NULL default '0',
  UNIQUE KEY `idx_user_id_profile_key` (`user_id`,`profile_key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Simple user profile storage table';

-- 
-- Dumping data for table `bak_user_profiles`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `bak_user_usergroup_map`
-- 

CREATE TABLE `bak_user_usergroup_map` (
  `user_id` int(10) unsigned NOT NULL default '0' COMMENT 'Foreign Key to #__users.id',
  `group_id` int(10) unsigned NOT NULL default '0' COMMENT 'Foreign Key to #__usergroups.id',
  PRIMARY KEY  (`user_id`,`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `bak_user_usergroup_map`
-- 

INSERT INTO `bak_user_usergroup_map` VALUES (42, 8);

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_usergroups`
-- 

CREATE TABLE `bak_usergroups` (
  `id` int(10) unsigned NOT NULL auto_increment COMMENT 'Primary Key',
  `parent_id` int(10) unsigned NOT NULL default '0' COMMENT 'Adjacency List Reference Id',
  `lft` int(11) NOT NULL default '0' COMMENT 'Nested set lft.',
  `rgt` int(11) NOT NULL default '0' COMMENT 'Nested set rgt.',
  `title` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_usergroup_parent_title_lookup` (`parent_id`,`title`),
  KEY `idx_usergroup_title_lookup` (`title`),
  KEY `idx_usergroup_adjacency_lookup` (`parent_id`),
  KEY `idx_usergroup_nested_set_lookup` USING BTREE (`lft`,`rgt`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

-- 
-- Dumping data for table `bak_usergroups`
-- 

INSERT INTO `bak_usergroups` VALUES (1, 0, 1, 20, 'Public');
INSERT INTO `bak_usergroups` VALUES (2, 1, 6, 17, 'Registered');
INSERT INTO `bak_usergroups` VALUES (3, 2, 7, 14, 'Author');
INSERT INTO `bak_usergroups` VALUES (4, 3, 8, 11, 'Editor');
INSERT INTO `bak_usergroups` VALUES (5, 4, 9, 10, 'Publisher');
INSERT INTO `bak_usergroups` VALUES (6, 1, 2, 5, 'Manager');
INSERT INTO `bak_usergroups` VALUES (7, 6, 3, 4, 'Administrator');
INSERT INTO `bak_usergroups` VALUES (8, 1, 18, 19, 'Super Users');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_users`
-- 

CREATE TABLE `bak_users` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL default '',
  `username` varchar(150) NOT NULL default '',
  `email` varchar(100) NOT NULL default '',
  `password` varchar(100) NOT NULL default '',
  `usertype` varchar(25) NOT NULL default '',
  `block` tinyint(4) NOT NULL default '0',
  `sendEmail` tinyint(4) default '0',
  `registerDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `lastvisitDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `activation` varchar(100) NOT NULL default '',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `usertype` (`usertype`),
  KEY `idx_name` (`name`),
  KEY `idx_block` (`block`),
  KEY `username` (`username`),
  KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 AUTO_INCREMENT=43 ;

-- 
-- Dumping data for table `bak_users`
-- 

INSERT INTO `bak_users` VALUES (42, 'Super User', 'admin', 'jacek.p.kolodziejczyk@gmail.com', 'f3ec4891168e0ffee89db7bd9503e302:pjrsJhscB0lVpPCKrHObRMQNLM8Bwh2t', 'deprecated', 0, 1, '2011-03-13 19:22:32', '2011-03-13 19:29:26', '', '');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_viewlevels`
-- 

CREATE TABLE `bak_viewlevels` (
  `id` int(10) unsigned NOT NULL auto_increment COMMENT 'Primary Key',
  `title` varchar(100) NOT NULL default '',
  `ordering` int(11) NOT NULL default '0',
  `rules` varchar(5120) NOT NULL COMMENT 'JSON encoded access control.',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_assetgroup_title_lookup` (`title`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `bak_viewlevels`
-- 

INSERT INTO `bak_viewlevels` VALUES (1, 'Public', 0, '[1]');
INSERT INTO `bak_viewlevels` VALUES (2, 'Registered', 1, '[6,2,8]');
INSERT INTO `bak_viewlevels` VALUES (3, 'Special', 2, '[6,3,8]');

-- --------------------------------------------------------

-- 
-- Table structure for table `bak_weblinks`
-- 

CREATE TABLE `bak_weblinks` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `catid` int(11) NOT NULL default '0',
  `sid` int(11) NOT NULL default '0',
  `title` varchar(250) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `url` varchar(250) NOT NULL default '',
  `description` text NOT NULL,
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  `hits` int(11) NOT NULL default '0',
  `state` tinyint(1) NOT NULL default '0',
  `checked_out` int(11) NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) NOT NULL default '0',
  `archived` tinyint(1) NOT NULL default '0',
  `approved` tinyint(1) NOT NULL default '1',
  `access` int(11) NOT NULL default '1',
  `params` text NOT NULL,
  `language` char(7) NOT NULL default '',
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `created_by` int(10) unsigned NOT NULL default '0',
  `created_by_alias` varchar(255) NOT NULL default '',
  `modified` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_by` int(10) unsigned NOT NULL default '0',
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `metadata` text NOT NULL,
  `featured` tinyint(3) unsigned NOT NULL default '0' COMMENT 'Set if link is featured.',
  `xreference` varchar(50) NOT NULL COMMENT 'A reference to enable linkages to external data sets.',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  KEY `idx_access` (`access`),
  KEY `idx_checkout` (`checked_out`),
  KEY `idx_state` (`state`),
  KEY `idx_catid` (`catid`),
  KEY `idx_createdby` (`created_by`),
  KEY `idx_featured_catid` (`featured`,`catid`),
  KEY `idx_language` (`language`),
  KEY `idx_xreference` (`xreference`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `bak_weblinks`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_advancedmodules`
-- 

CREATE TABLE `jos_advancedmodules` (
  `moduleid` int(11) NOT NULL default '0',
  `params` text NOT NULL,
  PRIMARY KEY  (`moduleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_advancedmodules`
-- 

INSERT INTO `jos_advancedmodules` VALUES (16, 'hideempty=1\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_selection=1|3|4|5|6|2\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=2\nassignto_articles_selection=1\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=2\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` VALUES (17, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=1\nassignto_menuitems_inc_children=0\nassignto_menuitems_selection=4|7|10\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=1\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_secscats_selection=1:1\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` VALUES (1, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` VALUES (18, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` VALUES (19, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` VALUES (20, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` VALUES (21, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');
INSERT INTO `jos_advancedmodules` VALUES (22, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_assets`
-- 

CREATE TABLE `jos_assets` (
  `id` int(10) unsigned NOT NULL auto_increment COMMENT 'Primary Key',
  `parent_id` int(11) NOT NULL default '0' COMMENT 'Nested set parent.',
  `lft` int(11) NOT NULL default '0' COMMENT 'Nested set lft.',
  `rgt` int(11) NOT NULL default '0' COMMENT 'Nested set rgt.',
  `level` int(10) unsigned NOT NULL COMMENT 'The cached level in the nested tree.',
  `name` varchar(50) NOT NULL COMMENT 'The unique name for the asset.\n',
  `title` varchar(100) NOT NULL COMMENT 'The descriptive title for the asset.',
  `rules` varchar(5120) NOT NULL COMMENT 'JSON encoded access control.',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_asset_name` (`name`),
  KEY `idx_lft_rgt` (`lft`,`rgt`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 AUTO_INCREMENT=34 ;

-- 
-- Dumping data for table `jos_assets`
-- 

INSERT INTO `jos_assets` VALUES (1, 0, 1, 418, 0, 'root.1', 'Root Asset', '{"core.login.site":{"6":1,"2":1},"core.login.admin":{"6":1},"core.admin":{"8":1},"core.manage":{"7":1},"core.create":{"6":1,"3":1},"core.delete":{"6":1},"core.edit":{"6":1,"4":1},"core.edit.state":{"6":1,"5":1},"core.edit.own":{"6":1,"3":1}}');
INSERT INTO `jos_assets` VALUES (2, 1, 1, 2, 1, 'com_admin', 'com_admin', '{}');
INSERT INTO `jos_assets` VALUES (3, 1, 3, 6, 1, 'com_banners', 'com_banners', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (4, 1, 7, 8, 1, 'com_cache', 'com_cache', '{"core.admin":{"7":1},"core.manage":{"7":1}}');
INSERT INTO `jos_assets` VALUES (5, 1, 9, 10, 1, 'com_checkin', 'com_checkin', '{"core.admin":{"7":1},"core.manage":{"7":1}}');
INSERT INTO `jos_assets` VALUES (6, 1, 11, 12, 1, 'com_config', 'com_config', '{}');
INSERT INTO `jos_assets` VALUES (7, 1, 13, 16, 1, 'com_contact', 'com_contact', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (8, 1, 17, 20, 1, 'com_content', 'com_content', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":[],"core.edit":{"4":1},"core.edit.state":{"5":1},"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (9, 1, 21, 22, 1, 'com_cpanel', 'com_cpanel', '{}');
INSERT INTO `jos_assets` VALUES (10, 1, 23, 24, 1, 'com_installer', 'com_installer', '{"core.admin":{"7":1},"core.manage":{"7":1},"core.delete":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (11, 1, 25, 26, 1, 'com_languages', 'com_languages', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (12, 1, 27, 28, 1, 'com_login', 'com_login', '{}');
INSERT INTO `jos_assets` VALUES (13, 1, 29, 30, 1, 'com_mailto', 'com_mailto', '{}');
INSERT INTO `jos_assets` VALUES (14, 1, 31, 32, 1, 'com_massmail', 'com_massmail', '{}');
INSERT INTO `jos_assets` VALUES (15, 1, 33, 34, 1, 'com_media', 'com_media', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":{"5":1}}');
INSERT INTO `jos_assets` VALUES (16, 1, 35, 36, 1, 'com_menus', 'com_menus', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (17, 1, 37, 38, 1, 'com_messages', 'com_messages', '{"core.admin":{"7":1},"core.manage":{"7":1}}');
INSERT INTO `jos_assets` VALUES (18, 1, 39, 40, 1, 'com_modules', 'com_modules', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (19, 1, 41, 44, 1, 'com_newsfeeds', 'com_newsfeeds', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (20, 1, 45, 46, 1, 'com_plugins', 'com_plugins', '{"core.admin":{"7":1},"core.manage":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (21, 1, 47, 48, 1, 'com_redirect', 'com_redirect', '{"core.admin":{"7":1},"core.manage":[]}');
INSERT INTO `jos_assets` VALUES (22, 1, 49, 50, 1, 'com_search', 'com_search', '{"core.admin":{"7":1},"core.manage":{"6":1}}');
INSERT INTO `jos_assets` VALUES (23, 1, 51, 52, 1, 'com_templates', 'com_templates', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (24, 1, 53, 54, 1, 'com_users', 'com_users', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.own":{"6":1},"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (25, 1, 55, 58, 1, 'com_weblinks', 'com_weblinks', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":[],"core.edit":{"4":1},"core.edit.state":{"5":1},"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (26, 1, 59, 60, 1, 'com_wrapper', 'com_wrapper', '{}');
INSERT INTO `jos_assets` VALUES (27, 8, 18, 19, 2, 'com_content.category.2', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (28, 3, 4, 5, 2, 'com_banners.category.3', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}');
INSERT INTO `jos_assets` VALUES (29, 7, 14, 15, 2, 'com_contact.category.4', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (30, 19, 42, 43, 2, 'com_newsfeeds.category.5', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (31, 25, 56, 57, 2, 'com_weblinks.category.6', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}');
INSERT INTO `jos_assets` VALUES (32, 1, 414, 415, 1, 'com_jtaghelpdesk', 'jtaghelpdesk', '{}');
INSERT INTO `jos_assets` VALUES (33, 1, 416, 417, 1, 'com_huruhelpdesk', 'huruhelpdesk', '{}');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_banner`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_banner`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_banner_clients`
-- 

CREATE TABLE `jos_banner_clients` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL default '',
  `contact` varchar(255) NOT NULL default '',
  `email` varchar(255) NOT NULL default '',
  `extrainfo` text NOT NULL,
  `state` tinyint(3) NOT NULL default '0',
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `metakey` text NOT NULL,
  `own_prefix` tinyint(4) NOT NULL default '0',
  `metakey_prefix` varchar(255) NOT NULL default '',
  `purchase_type` tinyint(4) NOT NULL default '-1',
  `track_clicks` tinyint(4) NOT NULL default '-1',
  `track_impressions` tinyint(4) NOT NULL default '-1',
  PRIMARY KEY  (`id`),
  KEY `idx_own_prefix` (`own_prefix`),
  KEY `idx_metakey_prefix` (`metakey_prefix`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_banner_clients`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_banner_tracks`
-- 

CREATE TABLE `jos_banner_tracks` (
  `track_date` datetime NOT NULL,
  `track_type` int(10) unsigned NOT NULL,
  `banner_id` int(10) unsigned NOT NULL,
  `count` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`track_date`,`track_type`,`banner_id`),
  KEY `idx_track_date` (`track_date`),
  KEY `idx_track_type` (`track_type`),
  KEY `idx_banner_id` (`banner_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_banner_tracks`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_bannerclient`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_bannerclient`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_banners`
-- 

CREATE TABLE `jos_banners` (
  `id` int(11) NOT NULL auto_increment,
  `cid` int(11) NOT NULL default '0',
  `type` int(11) NOT NULL default '0',
  `name` varchar(255) NOT NULL default '',
  `alias` varchar(255) NOT NULL default '',
  `imptotal` int(11) NOT NULL default '0',
  `impmade` int(11) NOT NULL default '0',
  `clicks` int(11) NOT NULL default '0',
  `clickurl` varchar(200) NOT NULL default '',
  `state` tinyint(3) NOT NULL default '0',
  `catid` int(10) unsigned NOT NULL default '0',
  `description` text NOT NULL,
  `custombannercode` varchar(2048) NOT NULL,
  `sticky` tinyint(1) unsigned NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  `metakey` text NOT NULL,
  `params` text NOT NULL,
  `own_prefix` tinyint(1) NOT NULL default '0',
  `metakey_prefix` varchar(255) NOT NULL default '',
  `purchase_type` tinyint(4) NOT NULL default '-1',
  `track_clicks` tinyint(4) NOT NULL default '-1',
  `track_impressions` tinyint(4) NOT NULL default '-1',
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_up` datetime NOT NULL default '0000-00-00 00:00:00',
  `publish_down` datetime NOT NULL default '0000-00-00 00:00:00',
  `reset` datetime NOT NULL default '0000-00-00 00:00:00',
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `language` char(7) NOT NULL default '',
  PRIMARY KEY  (`id`),
  KEY `idx_state` (`state`),
  KEY `idx_own_prefix` (`own_prefix`),
  KEY `idx_metakey_prefix` (`metakey_prefix`),
  KEY `idx_banner_catid` (`catid`),
  KEY `idx_language` (`language`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_banners`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_bannertrack`
-- 

CREATE TABLE `jos_bannertrack` (
  `track_date` date NOT NULL,
  `track_type` int(10) unsigned NOT NULL,
  `banner_id` int(10) unsigned NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_bannertrack`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_categories`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_categories`
-- 

INSERT INTO `jos_categories` VALUES (1, 0, 'SWT Matrix', '', 'swt-matrix', '', '1', 'left', '<p>SWT Matrix i a tabular SWT widget with unlimited capacity and instant rendering.</p>', 1, 0, '0000-00-00 00:00:00', NULL, 1, 0, 0, '');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_components`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 AUTO_INCREMENT=67 ;

-- 
-- Dumping data for table `jos_components`
-- 

INSERT INTO `jos_components` VALUES (1, 'Banners', '', 0, 0, '', 'Banner Management', 'com_banners', 0, 'js/ThemeOffice/component.png', 0, 'track_impressions=0\ntrack_clicks=0\ntag_prefix=\n\n', 1);
INSERT INTO `jos_components` VALUES (2, 'Banners', '', 0, 1, 'option=com_banners', 'Active Banners', 'com_banners', 1, 'js/ThemeOffice/edit.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (3, 'Clients', '', 0, 1, 'option=com_banners&c=client', 'Manage Clients', 'com_banners', 2, 'js/ThemeOffice/categories.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (4, 'Web Links', 'option=com_weblinks', 0, 0, '', 'Manage Weblinks', 'com_weblinks', 0, 'js/ThemeOffice/component.png', 0, 'show_comp_description=1\ncomp_description=\nshow_link_hits=1\nshow_link_description=1\nshow_other_cats=1\nshow_headings=1\nshow_page_title=1\nlink_target=0\nlink_icons=\n\n', 1);
INSERT INTO `jos_components` VALUES (5, 'Links', '', 0, 4, 'option=com_weblinks', 'View existing weblinks', 'com_weblinks', 1, 'js/ThemeOffice/edit.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (6, 'Categories', '', 0, 4, 'option=com_categories&section=com_weblinks', 'Manage weblink categories', '', 2, 'js/ThemeOffice/categories.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (7, 'Contacts', 'option=com_contact', 0, 0, '', 'Edit contact details', 'com_contact', 0, 'js/ThemeOffice/component.png', 1, 'contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n', 1);
INSERT INTO `jos_components` VALUES (8, 'Contacts', '', 0, 7, 'option=com_contact', 'Edit contact details', 'com_contact', 0, 'js/ThemeOffice/edit.png', 1, '', 1);
INSERT INTO `jos_components` VALUES (9, 'Categories', '', 0, 7, 'option=com_categories&section=com_contact_details', 'Manage contact categories', '', 2, 'js/ThemeOffice/categories.png', 1, 'contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n', 1);
INSERT INTO `jos_components` VALUES (10, 'Polls', 'option=com_poll', 0, 0, 'option=com_poll', 'Manage Polls', 'com_poll', 0, 'js/ThemeOffice/component.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (11, 'News Feeds', 'option=com_newsfeeds', 0, 0, '', 'News Feeds Management', 'com_newsfeeds', 0, 'js/ThemeOffice/component.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (12, 'Feeds', '', 0, 11, 'option=com_newsfeeds', 'Manage News Feeds', 'com_newsfeeds', 1, 'js/ThemeOffice/edit.png', 0, 'show_headings=1\nshow_name=1\nshow_articles=1\nshow_link=1\nshow_cat_description=1\nshow_cat_items=1\nshow_feed_image=1\nshow_feed_description=1\nshow_item_description=1\nfeed_word_count=0\n\n', 1);
INSERT INTO `jos_components` VALUES (13, 'Categories', '', 0, 11, 'option=com_categories&section=com_newsfeeds', 'Manage Categories', '', 2, 'js/ThemeOffice/categories.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (14, 'User', 'option=com_user', 0, 0, '', '', 'com_user', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (15, 'Search', 'option=com_search', 0, 0, 'option=com_search', 'Search Statistics', 'com_search', 0, 'js/ThemeOffice/component.png', 1, 'enabled=0\n\n', 1);
INSERT INTO `jos_components` VALUES (16, 'Categories', '', 0, 1, 'option=com_categories&section=com_banner', 'Categories', '', 3, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (17, 'Wrapper', 'option=com_wrapper', 0, 0, '', 'Wrapper', 'com_wrapper', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (18, 'Mail To', '', 0, 0, '', '', 'com_mailto', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (19, 'Media Manager', '', 0, 0, 'option=com_media', 'Media Manager', 'com_media', 0, '', 1, 'upload_extensions=bmp,csv,doc,epg,gif,ico,jpg,odg,odp,ods,odt,pdf,png,ppt,swf,txt,xcf,xls,BMP,CSV,DOC,EPG,GIF,ICO,JPG,ODG,ODP,ODS,ODT,PDF,PNG,PPT,SWF,TXT,XCF,XLS\nupload_maxsize=10000000\nfile_path=images\nimage_path=images/stories\nrestrict_uploads=1\nallowed_media_usergroup=3\ncheck_mime=1\nimage_extensions=bmp,gif,jpg,png\nignore_extensions=\nupload_mime=image/jpeg,image/gif,image/png,image/bmp,application/x-shockwave-flash,application/msword,application/excel,application/pdf,application/powerpoint,text/plain,application/x-zip\nupload_mime_illegal=text/html\nenable_flash=0\n\n', 1);
INSERT INTO `jos_components` VALUES (20, 'Articles', 'option=com_content', 0, 0, '', '', 'com_content', 0, '', 1, 'show_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=0\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=0\nshow_create_date=0\nshow_modify_date=0\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=0\nshow_print_icon=1\nshow_email_icon=0\nshow_hits=1\nfeed_summary=0\nfilter_tags=\nfilter_attritbutes=\n\n', 1);
INSERT INTO `jos_components` VALUES (21, 'Configuration Manager', '', 0, 0, '', 'Configuration', 'com_config', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (22, 'Installation Manager', '', 0, 0, '', 'Installer', 'com_installer', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (23, 'Language Manager', '', 0, 0, '', 'Languages', 'com_languages', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (24, 'Mass mail', '', 0, 0, '', 'Mass Mail', 'com_massmail', 0, '', 1, 'mailSubjectPrefix=\nmailBodySuffix=\n\n', 1);
INSERT INTO `jos_components` VALUES (25, 'Menu Editor', '', 0, 0, '', 'Menu Editor', 'com_menus', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (27, 'Messaging', '', 0, 0, '', 'Messages', 'com_messages', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (28, 'Modules Manager', '', 0, 0, '', 'Modules', 'com_modules', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (29, 'Plugin Manager', '', 0, 0, '', 'Plugins', 'com_plugins', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (30, 'Template Manager', '', 0, 0, '', 'Templates', 'com_templates', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (31, 'User Manager', '', 0, 0, '', 'Users', 'com_users', 0, '', 1, 'allowUserRegistration=1\nnew_usertype=Registered\nuseractivation=1\nfrontend_userparams=1\n\n', 1);
INSERT INTO `jos_components` VALUES (32, 'Cache Manager', '', 0, 0, '', 'Cache', 'com_cache', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (33, 'Control Panel', '', 0, 0, '', 'Control Panel', 'com_cpanel', 0, '', 1, '', 1);
INSERT INTO `jos_components` VALUES (34, 'Huru Helpdesk', 'option=com_huruhelpdesk', 0, 0, 'option=com_huruhelpdesk', 'Huru Helpdesk', 'com_huruhelpdesk', 0, 'js/ThemeOffice/component.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (35, 'ARTIO JoomSEF', 'option=com_sef', 0, 0, 'option=com_sef', 'ARTIO JoomSEF', 'com_sef', 0, 'components/com_sef/assets/images/icon.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (36, 'Control Panel', '', 0, 35, 'option=com_sef', 'Control Panel', 'com_sef', 0, 'components/com_sef/assets/images/icon-16-sefcpanel.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (37, 'Configuration', '', 0, 35, 'option=com_sef&controller=config&task=edit', 'Configuration', 'com_sef', 1, 'components/com_sef/assets/images/icon-16-sefconfig.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (38, 'Manage Extensions', '', 0, 35, 'option=com_sef&controller=extension', 'Manage Extensions', 'com_sef', 2, 'components/com_sef/assets/images/icon-16-sefplugin.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (39, 'Edit .htaccess', '', 0, 35, 'option=com_sef&controller=htaccess', 'Edit .htaccess', 'com_sef', 3, 'components/com_sef/assets/images/icon-16-edit.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (40, '', '', 0, 35, 'option=com_sef', '', 'com_sef', 4, 'components/com_sef/assets/images/icon-10-blank.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (41, 'Manage SEF URLs', '', 0, 35, 'option=com_sef&controller=sefurls&viewmode=3', 'Manage SEF URLs', 'com_sef', 5, 'components/com_sef/assets/images/icon-16-url-edit.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (42, 'Manage Meta Tags', '', 0, 35, 'option=com_sef&controller=metatags', 'Manage Meta Tags', 'com_sef', 6, 'components/com_sef/assets/images/icon-16-manage-tags.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (43, 'SiteMap', '', 0, 35, 'option=com_sef&controller=sitemap', 'SiteMap', 'com_sef', 7, 'components/com_sef/assets/images/icon-16-manage-sitemap.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (44, 'Manage 301 Redirects', '', 0, 35, 'option=com_sef&controller=movedurls', 'Manage 301 Redirects', 'com_sef', 8, 'components/com_sef/assets/images/icon-16-301-redirects.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (45, '', '', 0, 35, 'option=com_sef', '', 'com_sef', 9, 'components/com_sef/assets/images/icon-10-blank.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (46, 'Upgrade', '', 0, 35, 'option=com_sef&task=showUpgrade', 'Upgrade', 'com_sef', 10, 'components/com_sef/assets/images/icon-16-update.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (47, 'Support', '', 0, 35, 'option=com_sef&controller=info&task=help', 'Support', 'com_sef', 11, 'components/com_sef/assets/images/icon-16-help.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (49, 'Advanced Module Manager', '', 0, 0, '', 'Advanced Module Manager', 'com_advancedmodules', 0, '', 0, '\n', 1);
INSERT INTO `jos_components` VALUES (54, 'Kunena Forum', 'option=com_kunena', 0, 0, 'option=com_kunena', 'Kunena Forum', 'com_kunena', 0, 'components/com_kunena/images/kunenafavicon.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (61, 'Info', '', 0, 55, 'option=com_phocadocumentation&view=phocadocumentationin', 'Info', 'com_phocadocumentation', 2, 'components/com_phocadocumentation/assets/images/icon-16-pdoc-info.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (60, 'Documentation', '', 0, 55, 'option=com_phocadocumentation&view=phocadocumentations', 'Documentation', 'com_phocadocumentation', 1, 'components/com_phocadocumentation/assets/images/icon-16-pdoc-doc.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (59, 'Control Panel', '', 0, 55, 'option=com_phocadocumentation', 'Control Panel', 'com_phocadocumentation', 0, 'components/com_phocadocumentation/assets/images/icon-16-pdoc-control-panel.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (55, 'Phoca Documentation', 'option=com_phocadocumentation', 0, 0, 'option=com_phocadocumentation', 'Phoca Documentation', 'com_phocadocumentation', 0, 'components/com_phocadocumentation/assets/images/icon-16-pdoc-menu.png', 0, 'most_viewed_docs_num=5\ndisplay_sections=1\ndisplay_up_icon=1\ndisplay_num_doc_secs=1\ndisplay_num_doc_secs_header=1\narticle_itemid=\n\n', 1);
INSERT INTO `jos_components` VALUES (62, 'JCE', 'option=com_jce', 0, 0, 'option=com_jce', 'JCE', 'com_jce', 0, 'components/com_jce/media/img/menu/logo.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (63, 'WF_MENU_CPANEL', '', 0, 62, 'option=com_jce', 'WF_MENU_CPANEL', 'com_jce', 0, 'components/com_jce/media/img/menu/jce-cpanel.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (64, 'WF_MENU_CONFIG', '', 0, 62, 'option=com_jce&view=config', 'WF_MENU_CONFIG', 'com_jce', 1, 'components/com_jce/media/img/menu/jce-config.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (65, 'WF_MENU_PROFILES', '', 0, 62, 'option=com_jce&view=profiles', 'WF_MENU_PROFILES', 'com_jce', 2, 'components/com_jce/media/img/menu/jce-profiles.png', 0, '', 1);
INSERT INTO `jos_components` VALUES (66, 'WF_MENU_INSTALL', '', 0, 62, 'option=com_jce&view=installer', 'WF_MENU_INSTALL', 'com_jce', 3, 'components/com_jce/media/img/menu/jce-install.png', 0, '', 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_contact_details`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_contact_details`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_content`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

-- 
-- Dumping data for table `jos_content`
-- 

INSERT INTO `jos_content` VALUES (1, 'Welcome', 'welcome', '', '<p>Welcome to SWT Matrix! You must have taken the red pill :-)</p>', '', 1, 0, 0, 0, '2011-03-13 22:10:04', 62, '', '2011-04-28 19:31:59', 62, 62, '2011-09-19 20:09:58', '2011-03-13 22:10:04', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=0\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 9, 0, 4, '', '', 0, 3, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (2, 'Products', 'products', '', '<p>Products</p>', '', 1, 0, 0, 0, '2011-03-14 01:26:16', 62, '', '0000-00-00 00:00:00', 0, 0, '0000-00-00 00:00:00', '2011-03-14 01:26:16', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 1, 0, 3, '', '', 0, 62, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (3, 'SWT Matrix', 'swt-matrix', '', '<p>SWT Matrix is a tabular widget for the SWT Java GUI toolkit. Is is characterized by an unlimited capacity and instant rendering.</p>', '', 1, 1, 0, 1, '2011-03-14 01:58:18', 62, '', '2011-04-29 13:14:35', 62, 0, '0000-00-00 00:00:00', '2011-03-14 01:58:18', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 3, 0, 12, '', '', 0, 180, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (4, 'Download', 'download', '', '<p>Dependencies:</p>\r\n<ul>\r\n<li>Java 1.5 or higher. </li>\r\n<li>SWT 3.4 or higher (the lower versions may work, but have not been tested).</li>\r\n</ul>\r\n<p>By downloading the software below I agree with the&nbsp;<a href="swt-matrix/EULA_v1.0.html" mce_href="swt-matrix/EULA_v1.0.html" target="_blank">End User License Agreement</a>.</p>\r\n<h3>Version 0.5.10 (alpha stage)</h3>\r\n<p><a href="swt-matrix/swt-matrix-0.5.10.zip" mce_href="swt-matrix/swt-matrix-0.5.10.zip" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">swt-matrix-0.5.10.zip</a> - includes swt-matrix-0.5.10.jar, javadoc and snippets.</p>\r\n<p><a href="swt-matrix/Change-log.txt" mce_href="swt-matrix/Change-log.txt">change log</a><br mce_bogus="1"></p>\r\n<mce:script type="text/javascript"><!--\r\neula();\r\n<script>\r\n// --></mce:script>', '', 1, 0, 0, 0, '2011-03-14 03:24:20', 62, '', '2012-08-03 06:47:15', 62, 0, '0000-00-00 00:00:00', '2011-03-14 03:24:20', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 46, 0, 2, '', '', 0, 1329, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (5, 'Features', 'features', '', '<p class="filters">The list includes both done and planned features. Its also possible to filter features coming only in the next release. Outstanding features are unique or rare compared to similar components. The list is also filterable by somewhat subjective importance of the feature.</p>\r\n<p>Please let us know in the <a href="#comments">comments </a>which features do you miss the most.</p>\r\n<form id="let-us-know" style="display: none"><textarea style="width: 80%; height: 75px;"></textarea><input type="submit" /></form>\r\n<p class="filters">Filters: <a id="filter-all" href="#">all</a> <a id="filter-current" href="#">done</a> <a id="filter-next" href="#">next</a> <a id="filter-future" href="#">planned</a> <input id="filter-outstanding" type="checkbox" /> outstanding <input id="filter-high" type="checkbox" /> high <input id="filter-medium" type="checkbox" /> medium <input id="filter-low" type="checkbox" /> low</p>\r\n<!-- generated start --><table class=''data'' cellspacing=''1''><tr><th style=''white-space: nowrap''>Feature</th><th>Description</th><th>Version</th><th>Importance</th><th>Outstanding</th><th>References</th></tr><tr><td colspan=''7'' class=''header''><h3>Layout</h3></td></tr><tr><td style=''white-space: nowrap''>Standard sections</td><td>Header and body</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Custom sections</td><td>Additional sections like footer or filters can be defined</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0003.java''>Snippet_0003</a></td></tr><tr><td style=''white-space: nowrap''>Unlimited number of items</td><td>Each section can have an unlimited number of items</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0002.java''>Snippet_0002</a></td></tr><tr><td style=''white-space: nowrap''>Virtualization</td><td>Results in performance not dependant on the number of items in sections</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Show / hide section</td><td>For example it''s common to show / hide the header section</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Default cell width</td><td>Any cell that does not have a custom width will have the default width</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Individual cell width</td><td>Each cell can have a different width</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Default line width</td><td>Any line that does not have a custom width will have the default width</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Individual line width</td><td>Each line can have a different width</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Spaces between cells</td><td>Similar to HTML table cellspacing attribute. It does not effect the cell size and cell painting algorithm.</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Zones</h3></td></tr><tr><td style=''white-space: nowrap''>Zone as crossing of sections</td><td>Body zone = row axis body section and column axis body section, Column header zone = row axis header section and column axis body section</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Separate painters</td><td>Each zone can have separate painters</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Separate event handlers</td><td>Each zone can have separate event handlers</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Selection</h3></td></tr><tr><td style=''white-space: nowrap''>Select axis items</td><td>Both full rows and full columns can be selected</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Select cells</td><td>Standard cell selection by mouse and keyboard.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Modify selection</td><td>Works for both full axis items and cells. It is done by standard CTRL selection gestures.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Header cells highlight</td><td>Header cells can be automatically highlighted for the selected cells.</td><td>0.1</td><td>low</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Enable selection</td><td>Cell selection can be enabled/disabled</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Resize</h3></td></tr><tr><td style=''white-space: nowrap''>Resize rows and columns</td><td>Not only columns can be resized but rows as well.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Multiple resize</td><td>Resizes all selected items to the same width as the one being resized</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Instant resize</td><td>See the item repainted with the new width while dragging, as opposed to repaint after drag finished.</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Resize in all sections</td><td>Not only body items can be resized, but the headers ones as well. For example the row header width can be changed by the user dragging it''s right edge.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Default resize ability</td><td>All items in a section can have the resize ability enabled or disabled by default.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Item resize ability</td><td>Individual items can have the resize ability enabled or disabled</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Custom resize offset</td><td>Define how far from the line is the resize area</td><td>0.1</td><td>low</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Auto-resize</td><td>Column width and row height can be automatically calculated by double clicking in the resize area</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Move</h3></td></tr><tr><td style=''white-space: nowrap''>Move rows and columns</td><td>Not only columns can be moved but rows as well.</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Multiple move</td><td>Moves all selected items to the same width as the one being moved</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Instant move</td><td>See the items reordered while dragging, as opposed to repaint only after drag finished.</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Move in selection order</td><td>If multiple items are moved then they become ordered according to the sequence they were selected.</td><td>0.1</td><td>medium</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Move in all sections</td><td>Not only body items can be moved, but in other sections as well. </td><td>0.1</td><td>low</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Default move ability</td><td>All items in a section can have the move ability enabled or disabled by default.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Item move ability</td><td>Individual items can have the move ability enabled or disabled</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Hide</h3></td></tr><tr><td style=''white-space: nowrap''>Hide rows and columns</td><td>Not only columns can be hidden but rows as well.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Multiple hide</td><td>Hides all selected items to the same width as the one being hidden</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Hide in all sections</td><td>Not only body items can be hidden, but in other sections as well. </td><td>0.1</td><td>low</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Default hide ability</td><td>All items in a section can have the hide ability enabled or disabled by default.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Item move ability</td><td>Individual items can have the hide ability enabled or disabled</td><td>0.1</td><td>medium</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Group</h3></td></tr><tr><td style=''white-space: nowrap''>TreeTable</td><td>A column may display a tree like structure of the row axis items</td><td>0.3</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Cell merging</td><td>Individual cell merging</td><td>0.7</td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Item hierarchy as groups</td><td>The hierarchy is illustrated by the item in higher level being merged to the extent of its children</td><td>0.7</td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Collapse hierarchy items</td><td>Node collapse/expand typical for tree widgets</td><td>0.7</td><td>high</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Scroll</h3></td></tr><tr><td style=''white-space: nowrap''>Item scrolling</td><td>Scroll position is always snapped to the beginning of a first visible item</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Pixel scrolling</td><td>Smooth scrolling by pixels</td><td>0.5</td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Scroll to given item</td><td></td><td>0.5</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Auto-scroll with acceleration</td><td>The content will scroll automatically while a dragging operation reaches the edge of the scrollable area. It is during select, resize and move operations.</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Custom auto-scroll offset</td><td>Define how far from the edge of scrollable area is the auto-scrolling will start</td><td>0.1</td><td>low</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Custom auto-scroll acceleration</td><td>Define how fast the the auto-scroll will accelerate</td><td>1.+</td><td>low</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Freeze head</td><td>Prevent the first items from scrolling, making them always visible</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0201.java''>Snippet_0201</a></td></tr><tr><td style=''white-space: nowrap''>Freeze tail</td><td>Prevent the last items from scrolling, making them always visible</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0201.java''>Snippet_0201</a></td></tr><tr><td colspan=''7'' class=''header''><h3>Paint</h3></td></tr><tr><td style=''white-space: nowrap''>Background, foreground colors</td><td>Individual background and foreground color for cells.</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0017.java''>Snippet_0017</a></td></tr><tr><td style=''white-space: nowrap''>Line color, width, style</td><td>Drawing lines with individual color, width and style.</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0012.java''>Snippet_0012</a></td></tr><tr><td style=''white-space: nowrap''>Text font, align, padding</td><td>Drawing text with padding and aligning horizontally and vertically with custom colors and fonts for each cell.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Automatic numbering in headers</td><td>The default header painter draws text as sequential numbers starting from 0.</td><td>0.1</td><td>high</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Image align, padding</td><td>Drawing image with padding and aligning horizontally and vertically</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0018.java''>Snippet_0018</a></td></tr><tr><td style=''white-space: nowrap''>Multiline text</td><td>Text wrapping inside of a cell</td><td>0.3</td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Tilted text</td><td>Vertical or rotated </td><td>0.9</td><td>low</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Custom painter</td><td>All that is draw on the canvas can be replaced by a custom painter.</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0014.java''>Snippet_0014</a>, <a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0015.java''>Snippet_0015</a></td></tr><tr><td style=''white-space: nowrap''>Painters composition</td><td>List of matrix and zone painters is fully editable (add, replace, remove)</td><td>0.1</td><td>high</td><td>outstanding</td><td></td></tr><tr><td style=''white-space: nowrap''>Background painter</td><td>Custom background painter for the whole matrix, not only the cell. Can be used for current row highlighting</td><td>0.1</td><td>low</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0010.java''>Snippet_0010</a>, <a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0015.java''>Snippet_0015</a></td></tr><tr><td style=''white-space: nowrap''>Focus cell painter</td><td>Enables to customize the focus cell painting.</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0011.java''>Snippet_0011</a></td></tr><tr><td style=''white-space: nowrap''>Cell style</td><td>Set of attributes that can be named and applied to a number of cells.</td><td></td><td>medium</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Zoom</td><td>Zoom in  and zoom out the view port area.</td><td>>1</td><td>low</td><td></td><td></td></tr><tr><td style=''white-space: nowrap''>Shorten text at the end</td><td>Three dots at the end of text instead in the middle if the text is to long to fit in a cell.</td><td>0.9</td><td>medium</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Data</h3></td></tr><tr><td style=''white-space: nowrap''>Filtering</td><td>Filtering can be implemented by simply taking data to paint from a reduced collection of items.</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_003.java''>Snippet_003</a></td></tr><tr><td style=''white-space: nowrap''>Sorting</td><td>Sorting marker can be displayed using painter method and sorting cen be triggered by the header zone event listener</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_005.java''>Snippet_005</a></td></tr><tr><td style=''white-space: nowrap''>Add/remove items</td><td>Adding or removing items in the model is reflected in the matrix</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_004.java''>Snippet_004</a></td></tr><tr><td colspan=''7'' class=''header''><h3>Gestures</h3></td></tr><tr><td style=''white-space: nowrap''>Command binding</td><td>Custom binding many of the user activated commands to key/mouse gestures</td><td>0.1</td><td>high</td><td>outstanding</td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0901.java''>Snippet_0901</a></td></tr><tr><td style=''white-space: nowrap''>SelectionEvent</td><td>Handling of SelectionEvent</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0902.java''>Snippet_0902</a></td></tr><tr><td style=''white-space: nowrap''>ControlEvent</td><td>Handling of ControlEvent</td><td>0.1</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0902.java''>Snippet_0902</a></td></tr><tr><td colspan=''7'' class=''header''><h3>Edit</h3></td></tr><tr><td style=''white-space: nowrap''>Text, combo, date/time</td><td>Allow changing the value of a cell using standard SWT controls as pop-ups</td><td>0.2</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0404.java''>Snippet_0404</a></td></tr><tr><td style=''white-space: nowrap''>Embedded native controls</td><td>Check buttons or other controls constantly embedded in the cell</td><td>0.2</td><td>middle</td><td>outstanding</td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0402.java''>Snippet_0402</a></td></tr><tr><td style=''white-space: nowrap''>Checkbox emulation</td><td>For bettern performance then lots of native controls. Support for custom OS themes.</td><td>0.2</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0403.java''>Snippet_0403</a></td></tr><tr><td style=''white-space: nowrap''>Cut/copy/paste</td><td>Using clipboard tab separated format compatible with Excel</td><td>0.2</td><td>high</td><td></td><td><a href=''http://netanel.pl/swt-matrix/snippets/Snippet_0410.java''>Snippet_0410</a></td></tr><tr><td style=''white-space: nowrap''>Delete</td><td>Delete a cell value</td><td>0.2</td><td>high</td><td></td><td></td></tr><tr><td colspan=''7'' class=''header''><h3>Other</h3></td></tr><tr><td style=''white-space: nowrap''>Tooltips</td><td>Tooltips for individual cells</td><td>0.8</td><td>medium</td><td></td><td></td></tr></table><!-- generated end -->\r\n<script type="text/javascript">// <![CDATA[\r\nfilterFeatures();\r\n// ]]></script>\r\n<p><a name="comments"></a></p>', '', 1, 1, 0, 1, '2011-03-14 09:04:13', 62, '', '2011-06-17 20:40:41', 62, 0, '0000-00-00 00:00:00', '2011-03-14 09:04:13', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 89, 0, 13, '', '', 0, 1959, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (18, 'Issue Tracker', 'issuet-racker', '', '<!--iframe src="http://www.codaset.com/netanel/swt-matrix/tickets" mce_src="http://www.codaset.com/netanel/swt-matrix/tickets" width="100%" height="100%">\r\n  Your browser does not support iframes.\r\n</iframe--><p>\r\n\r\n<iframe src="http://netanel.myjetbrains.com/youtrack/issues" mce_src="http://netanel.myjetbrains.com/youtrack/issues" width="100%" height="800">\r\n  Your browser does not support iframes.\r\n</iframe></p>\r\n\r\n<script type="text/javascript">\r\njQuery(".main").css("width", "auto");\r\n</script>', '', 1, 1, 0, 1, '2011-07-20 13:07:12', 62, '', '2011-10-13 15:23:22', 62, 0, '0000-00-00 00:00:00', '2011-07-20 13:07:12', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 22, 0, 3, '', '', 0, 393, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (6, '404', '404', '', '<h1>404: Not Found</h1>\r\n<h2>Sorry, but the content you requested could not be found</h2>', '', 1, 0, 0, 0, '2004-11-11 12:44:38', 62, '', '2011-03-14 12:01:41', 0, 62, '2011-05-06 16:43:28', '2004-10-17 00:00:00', '0000-00-00 00:00:00', '', '', 'menu_image=-1\nitem_title=0\npageclass_sfx=\nback_button=\nrating=0\nauthor=0\ncreatedate=0\nmodifydate=0\npdf=0\nprint=0\nemail=0', 1, 0, 1, '', '', 0, 750, '');
INSERT INTO `jos_content` VALUES (7, 'API', 'api', '', '<p>api</p>', '', -2, 1, 0, 1, '2011-03-14 12:43:56', 62, '', '2011-03-14 14:40:19', 62, 0, '0000-00-00 00:00:00', '2011-03-14 12:43:56', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 2, 0, 0, '', '', 0, 5, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (8, 'Forum', 'forum', '', '<p><a id="nabblelink" href="http://swt-matrix.1049369.n5.nabble.com/">swt-matrix</a></p>\r\n<script src="http://swt-matrix.1049369.n5.nabble.com/embed/f4367519"></script>', '', -2, 0, 0, 0, '2011-03-14 18:03:36', 62, '', '2011-05-03 15:08:05', 62, 0, '0000-00-00 00:00:00', '2011-03-14 18:03:36', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 2, 0, 0, '', '', 0, 22, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (9, 'SWT Matrix - Overview', 'swt-matrix-overview', '', '<p>{module SWT Matrix Menu}</p>\r\n<p>SWT Matrix is a tabular widget for&nbsp;<a href="http://www.eclipse.org/swt" mce_href="http://www.eclipse.org/swt">SWT</a> Java GUI toolkit.</p>\r\n<p>What makes it different from other such components is <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">unlimited capacity</span> and <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">instant rendering</span>. <br />It also focuses on developer productivity by <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">clean design</span> with minimal learning curve.</p>\r\n<p><img src="images/infinite.png" mce_src="images/infinite.png" style="float:left; padding-right:10px" mce_style="float:left; padding-right:10px" border="0" height="64" width="64"></p>\r\n<h3>Unlimited Capacity</h3>\r\n<p>SWT Matrix can handle an <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">unlimited</span> number of rows and columns. Integer.MAX_VALUE (2 147 483 647)&nbsp;is not a limit, neither is Long.MAX_VALUE (9 223 372 036 854 775 807). And still it performs block selection, moving, resizing scrolling and setting of various properties of cells, rows and columns. More then 2147483647&nbsp;items is a rare case, but it <u style="">can</u> happen. It''s better to be <u style="">safe</u> then sorry.</p>\r\n<p><img src="images/clock.png" mce_src="images/clock.png" style="float:left; padding-right:10px; " mce_style="float:left; padding-right:10px; " border="0" height="64" width="64"></p>\r\n<h3>Instant Rendering</h3>\r\n<p>By convention <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">instant</span> for GUI applications means responding in less then 0.1 sec. Now consider scrolling a content of a&nbsp;table with 1 million rows and columns in a full screen mode with 1680 x 1050 screen resolution. It''s around 2000 visible cells of size 50x16. Components that don''t support virtual display choke completely. BTW don''t be misled by SWT.VIRTUAL constructor flag of those widgets. Scroll to the end and you will see that it is lazy initialization rather then virtualization. None of the popular tabular components tested on Windows XP 2GHz are consistent to provide instant response for basic operations like scrolling. SWT Matrix paints itself in about <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">50 ms</span>, which is at least&nbsp;<span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">8x better</span> then any other grid/table widget. And not all identified optimizations have been implemented yet.</p>\r\n<p><img src="images/design.png" mce_src="images/design.png" style="float:left; padding-right:10px; " mce_style="float:left; padding-right:10px; " border="0" height="64" width="64"></p>\r\n<h3>Clean design</h3>\r\n<p>Total of <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">12 classes/interfaces</span> in the public <a href="swt-matrix/javadoc/index.html" mce_href="swt-matrix/javadoc/index.html">API</a> does not sound like an over-complicated design for the amount of supported features and compared to hundreds of classes in other solutions. <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Symmetry</span> is also a measure of a clean design. Thus there is no such a thing that works for columns, but does not work for rows and vice versa, or works for one section and does not for another. For example since columns can be resized why not resize the row header? Even freezing is symetric. If the head of an axis can be frozen why not the tail? A frozen footer may come handy.</p>\r\n<p>The current major limitations are for instance lack of cell merging, column/row grouping and hierarchy. But they are coming in the next releases. {article features}{link}Here{/link}{/article} is the detail feature list.</p>\r\n\r\n<p>The component is released under<span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1"> commercial licence</span> but is free of charge for non commercial use.</p><p>Fell free to express you feed back. Your opinion is invaluable to us. Hope to {article forum}{link}hear{/link}{/article}&nbsp;from you.</p>\r\n<p><br /></p>\r\n<!-- AddThis Button BEGIN -->\r\n<div class="addthis_toolbox addthis_default_style ">\r\n<p><a class="addthis_button_facebook_like"></a> <a class="addthis_button_tweet"></a> <a class="addthis_counter addthis_pill_style"></a><br mce_bogus="1"></p>\r\n</div>\r\n<mce:script mce_src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=xa-4dd798d712e3ff54" type="text/javascript"></mce:script>\r\n<mce:script type="text/javascript"><!--\r\n/*var cssLink = document.createElement("link") \r\ncssLink.href = "media/css/addvalthis.css"; \r\ncssLink.rel = "stylesheet"; \r\ncssLink.type = "text/css"; \r\nframes[0].document.head.appendChild(cssLink);*/\r\n// --></mce:script>\r\n<!-- AddThis Button END -->', '', 1, 1, 0, 1, '2011-03-15 07:00:25', 62, '', '2012-10-08 23:33:07', 62, 0, '0000-00-00 00:00:00', '2011-03-15 07:00:25', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 121, 0, 11, '', '', 0, 2918, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (10, 'Design', 'swt-matrix-design', '', '<p>The over arching principle for the matrix widget design is simplicity, that materializes in:</p>\r\n<p>\r\n<ul>\r\n<li>Separation of concerns. The state maintenance has been </li>\r\n</ul>\r\n</p>\r\n<h3>Main concepts</h3>\r\n<p>- Matrix, MatrixAxis</p>\r\n<p>- Cell and line</p>\r\n<p>- AxisLayout</p>\r\n<p>- Index</p>\r\n<p>- Section</p>\r\n<p>- Zone</p>\r\n<p>- Dock</p>\r\n<p>- Painters</p>\r\n<p> </p>\r\n<p> </p>\r\n<p> </p>\r\n<p>Performance</p>\r\n<p>- Loop inversion: loop painters -&gt; loop cells -&gt; paint</p>\r\n<p>- Caching the layout computation results, paint does not recompute widths, distances, visible indexes</p>\r\n<p>- Caching results of GC.stringExtent for single characters of each font used in TextPainter.</p>\r\n<p> </p>\r\n<p>Indexes</p>\r\n<p>model index - original index that numbers the items in the model</p>\r\n<p>order index - position of the model index in the order sequence</p>\r\n<p>visible index - position of the model index in the order seqence after hiding some items</p>\r\n<p>The letter one matches with what the user sees on the screen.</p>\r\n<p> </p>\r\n<p>Selection and reordering is limited to the scope a single section only.</p>\r\n<p> </p>\r\n<p>Freezing</p>\r\n<p> </p>\r\n<p>Command</p>\r\n<p> </p>\r\n<p>Item reordering</p>\r\n<p> </p>\r\n<p>Indexes to selection are interim (position in order sequence ignoring hiding).</p>\r\n<p>This is to naturaly support a scenario of for example selecting extent</p>\r\n<p>from column 4 to column 6 including a hidden column 5 in between.</p>\r\n<p> </p>', '', 1, 1, 0, 1, '2011-03-15 10:14:57', 62, '', '2011-04-13 12:27:57', 62, 62, '2011-04-13 12:27:57', '2011-03-15 10:14:57', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 4, 0, 10, '', '', 0, 5, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (11, 'Snippets', 'snippets', '', '<p>Snippets are minimal stand-alone programs that demonstrate specific techniques or functionality. Often a small example is the easiest way to understand how to use a particular feature.</p><p>All the snippets are available for {article download}{link}download{/link}{/article}.</p><p>Layout</p>\r\n<!-- generated start --><li><a href="http://netanel.pl/swt-matrix/snippets/S0001_SimplestMatrix.java" mce_href="swt-matrix/snippets/S0001_SimplestMatrix.java">Simplest matrix.</a></li><li><a href="http://netanel.pl/swt-matrix/snippets/S0002_UnlimitedNumberOfItems.java" mce_href="swt-matrix/snippets/S0002_UnlimitedNumberOfItems.java">Unlimited number of items.</a></li><li><a href="http://netanel.pl/swt-matrix/snippets/S0003_FilterSectionBetweenHeaderAndBody.java" mce_href="swt-matrix/snippets/S0003_FilterSectionBetweenHeaderAndBody.java">Filter section between header and body.</a></li><li><a href="http://netanel.pl/swt-matrix/snippets/S0004_Add_RemoveModelItems.java" mce_href="swt-matrix/snippets/S0004_Add_RemoveModelItems.java">Add / remove model items.</a></li><li><a href="http://netanel.pl/swt-matrix/snippets/S0005_SortingByColumns.java" mce_href="swt-matrix/snippets/S0005_SortingByColumns.java">Sorting by columns.</a></li><ul></ul><p>Painters</p><li><a href="swt-matrix/snippets/S0010_DrawCustomBackgroundForTheWholeMatrix_ResizeTheWindow_.java" mce_href="swt-matrix/snippets/S0010_DrawCustomBackgroundForTheWholeMatrix_ResizeTheWindow_.java">Draw custom background for the whole matrix.</a></li>\r\n<li><a href="swt-matrix/snippets/S0011_DrawCustomFocusCellMarker.java" mce_href="swt-matrix/snippets/S0011_DrawCustomFocusCellMarker.java">Draw custom focus cell marker.</a></li>\r\n<li><a href="swt-matrix/snippets/S0012_ChangeTheLineStyle.java" mce_href="swt-matrix/snippets/S0012_ChangeTheLineStyle.java">Change the line style.</a></li>\r\n<li><a href="swt-matrix/snippets/S0013_GapBetweenCellsLikeHTMLTableCellspacingAttribute_HideLines.java" mce_href="swt-matrix/snippets/S0013_GapBetweenCellsLikeHTMLTableCellspacingAttribute_HideLines.java">Gap between cells like HTML table cell spacing attribute. Hide lines.</a></li>\r\n<li><a href="swt-matrix/snippets/S0014_AlteringRowBackground.java" mce_href="swt-matrix/snippets/S0014_AlteringRowBackground.java">Altering row background.</a></li>\r\n<li><a href="swt-matrix/snippets/S0015_CurrentRowGradientHighlighter.java" mce_href="swt-matrix/snippets/S0015_CurrentRowGradientHighlighter.java">Current row gradient highlighter.</a></li>\r\n<li><a href="swt-matrix/snippets/S0016_MarkSelectionWithSWT_COLOR_LIST___Colors.java" mce_href="swt-matrix/snippets/S0016_MarkSelectionWithSWT_COLOR_LIST___Colors.java">Mark selection with SWT.COLOR_LIST... colors.</a></li>\r\n<li><a href="swt-matrix/snippets/S0017_CellBackgroundCalculated.java" mce_href="swt-matrix/snippets/S0017_CellBackgroundCalculated.java">Cell background calculated</a></li>\r\n<li><a href="swt-matrix/snippets/S0018_ImagePainting.java" mce_href="swt-matrix/snippets/S0018_ImagePainting.java">Image painting</a></li>\r\n<li><a href="swt-matrix/snippets/S0019_AlignColumnToTheRight.java" mce_href="swt-matrix/snippets/S0019_AlignColumnToTheRight.java">Align column to the right.</a></li>\r\n<li><a href="swt-matrix/snippets/S0201_FreezeHeadAndTailWithDifferentColorForTheDividingLine.java" mce_href="swt-matrix/snippets/S0201_FreezeHeadAndTailWithDifferentColorForTheDividingLine.java">Freeze head and tail with different color for the dividing line</a></li><p>Edit</p><li><a href="swt-matrix/snippets/S0401_SimplestZoneEditor.java" mce_href="swt-matrix/snippets/S0401_SimplestZoneEditor.java">Simplest zone editor.</a></li>\r\n<li><a href="swt-matrix/snippets/S0402_EmbeddedCheckButtons.java" mce_href="swt-matrix/snippets/S0402_EmbeddedCheckButtons.java">Embedded check buttons.</a></li>\r\n<li><a href="swt-matrix/snippets/S0403_CheckButtonsEmulatedByImages.java" mce_href="swt-matrix/snippets/S0403_CheckButtonsEmulatedByImages.java">Check buttons emulated by images.</a></li>\r\n<li><a href="swt-matrix/snippets/S0404_EditControlsText_DateTime_Boolean_ComboDependingOnTheCellValue.java" mce_href="swt-matrix/snippets/S0404_EditControlsText_DateTime_Boolean_ComboDependingOnTheCellValue.java">Edit controls Text, DateTime, Boolean, Combo depending on the cell value.</a></li>\r\n<li><a href="swt-matrix/snippets/S0405_ListAsACustomEditorControl.java" mce_href="swt-matrix/snippets/S0405_ListAsACustomEditorControl.java">List as a custom editor control.</a></li>\r\n<li><a href="swt-matrix/snippets/S0410_SeparateZoneToInsertNewItems.java" mce_href="swt-matrix/snippets/S0410_SeparateZoneToInsertNewItems.java">Separate zone to insert new items.</a></li>\r\n<li><a href="swt-matrix/snippets/S0450_ActivateEditorWithSingleClickInsteadOfDoubleClick.java" mce_href="swt-matrix/snippets/S0450_ActivateEditorWithSingleClickInsteadOfDoubleClick.java">Activate editor with single click instead of double click.</a></li><ul></ul><p>Other</p><li><a href="swt-matrix/snippets/S0456_TraverseCells_Shift__TabAndEnter_TraverseMatrixWith_Shift__Ctrl_Tab.java" mce_href="swt-matrix/snippets/S0456_TraverseCells_Shift__TabAndEnter_TraverseMatrixWith_Shift__Ctrl_Tab.java">Traverse cells (Shift+) Tab and Enter. Traverse Matrix with (Shift+) Ctrl+Tab.</a></li>\r\n<li><a href="swt-matrix/snippets/S0490_CustomCopy_Paste.java" mce_href="swt-matrix/snippets/S0490_CustomCopy_Paste.java">Custom copy / paste.</a></li>\r\n<li><a href="swt-matrix/snippets/S0901_ChangeGestureBinding.java" mce_href="swt-matrix/snippets/S0901_ChangeGestureBinding.java">Change gesture binding.</a></li>\r\n<li><a href="swt-matrix/snippets/S0902_SelectionAndControlEventHandling.java" mce_href="swt-matrix/snippets/S0902_SelectionAndControlEventHandling.java">Selection and control event handling.</a></li>\r\n<li><a href="swt-matrix/snippets/S0903_IdentifyingItemsByDistance.java" mce_href="swt-matrix/snippets/S0903_IdentifyingItemsByDistance.java">Identifying items by distance.</a></li>\r\n<li><a href="swt-matrix/snippets/S0910_DragAndDropFilesFromExternalWindow.java" mce_href="swt-matrix/snippets/S0910_DragAndDropFilesFromExternalWindow.java" style="">Drag and drop files from external window.</a></li>\r\n<li><a href="swt-matrix/snippets/S1001_CompoundExample.java" mce_href="swt-matrix/snippets/S1001_CompoundExample.java">Compound example.</a></li><p><br /></p><p><br /></p><ul></ul><!-- generated end -->', '', 1, 1, 0, 1, '2011-03-15 10:17:07', 62, '', '2011-09-28 07:18:15', 62, 0, '0000-00-00 00:00:00', '2011-03-15 10:17:07', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 12, 0, 9, '', '', 0, 1067, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (12, 'Tutorial', 'tutorial', '', '<p>{article swt-matrix-design}{url}design{/article}</p>', '', -2, 1, 0, 1, '2011-03-15 10:17:35', 62, '', '2011-03-15 10:54:36', 62, 0, '0000-00-00 00:00:00', '2011-03-15 10:17:35', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 2, 0, 0, '', '', 0, 0, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (13, 'Tutorial', 'swt-matrix-tutorial', '', '<!-- ArticleToC: enabled=yes -->\r\n<p>The <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html">Matrix</a> widget is designed with a <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">separation of concerns</span> in mind. It does not imply any data storage model for the visual attributes of the matrix elements. Instead it concentrates on efficient matrix <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">layout</span> calculation, <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">painting</span>, and <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">event handling</span>.</p>\r\n<h3>Layout</h3>\r\n<p>The diagram below presents the basic concepts of the Matrix layout.</p>\r\n<p><img src="swt-matrix/images/Section.png" mce_src="swt-matrix/images/Section.png" border="0" height="267" width="473"></p>\r\n<p style="text-align: left; " mce_style="text-align: left; "><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Diagram 1</span></p>\r\n<table class="dictionary mceItemTable" border="0">\r\n<tbody>\r\n<tr>\r\n<td>\r\n<p><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Axis</span></p>\r\n</td>\r\n<td>\r\n<p><a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Axis.html" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Axis.html">Axis</a> is one of the two <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">dimension</span>s of the two-dimensional matrix. Axis names are appended with 0 and 1 in order to make them as short as possible, even in length and staying next to each other when sorted.</p>\r\n<pre xml:lang="java">Axis axisX = matrix.getAxisX();\r\nAxis axisY = matrix.getAxisY();</pre>\r\nis faster to type and more readable then\r\n<pre xml:lang="java">Axis columnAxis = matrix.getColumnAxis();\r\nAxis rowAxis = matrix.getRowAxis();</pre>\r\n<p>Appending axis name with X and Y would invert the alphabetical sorting disturbing the coding style, because there is a convention to pass row index as a first argument, leading to for example</p>\r\n<pre>zone.isSelected(indexX, indexY);</pre>\r\n<p>Axis is composed of sections (at least one) and is indexed by a specific sub-type of the java.lang.Number class. By default it is Integer class. It can be switched to BigInteger for example by creating the Axis manually:</p>\r\n<pre>Axis axisX = new Axis(Integer.class, 2);    // Two sections indexed by Integer class<br />Axis axisY = new Axis(BigInteger.class, 3);    // Three sections indexed by BigInteger class<br />final Matrix matrix = new Matrix(shell, SWT.V_SCROLL, axisX, axisY);</pre>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<p><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Section</span></p>\r\n</td>\r\n<td>\r\n<p><a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Section.html" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Section.html">Section</a> is a continuous <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">segment</span> of a matrix axis containing a number of items. Section can serve as a header, body, footer, filter area, etc. By default each axis has two sections: <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">header</span> with one item and <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">body</span> with zero items. The number of sections can be specified by creating the axis manually:</p>\r\n<pre>Axis axisX = new Axis(Integer.class, 2);    // Two sections indexed by Integer class<br />Axis axisY = new Axis(BigInteger.class, 3);    // Three sections indexed by BigInteger class<br />final Matrix matrix = new Matrix(shell, SWT.V_SCROLL, axisX, axisY);</pre>\r\n<p>On Diagram 1 row axis has a header section with 1 item and the body section with 10 items, while the column axis has header section with 1 item and body section with 5 items.</p>\r\n<p>This approach may cause some confusion, because in order to show the labels for the columns one must set the header section on the row axis visible, for example:</p>\r\n<pre>matrix.getAxisY().getHeader().setVisible(true);</pre>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<p><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Zone</span></p>\r\n</td>\r\n<td>\r\n<p><a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Zone.html" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Zone.html">Zone</a> constitutes a <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">region</span> of the matrix where a section from <code>axis0</code> and a section from the <code>axis1</code> <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">intersect</span> with each other. There are four zones on the diagram below:</p>\r\n<table class="mceItemTable" border="0">\r\n<tbody>\r\n<tr>\r\n<td>\r\n<ul>\r\n<li>body</li>\r\n</ul>\r\n</td>\r\n<td>at intersection of axisX body section and axisY body section</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<ul>\r\n<li>column header</li>\r\n</ul>\r\n</td>\r\n<td>at intersection of axisX body section and axisY header section</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<ul>\r\n<li>row header</li>\r\n</ul>\r\n</td>\r\n<td>at intersection of axisX header section and axisY body section</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<ul>\r\n<li>top left</li>\r\n</ul>\r\n</td>\r\n<td>at intersection of axisX header section and axisY header section</td>\r\n</tr>\r\n</tbody>\r\n</table>\r\n<p><img src="swt-matrix/images/Zone.png" mce_src="swt-matrix/images/Zone.png" alt="zones" border="0"></p><p>Zones are created automatically by the matrix to cover all intersections of axis sections.</p>\r\n<p>Each zone has its own collection of painters and key/mouse bindings.</p>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n<p><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Cell</span></p>\r\n</td>\r\n<td>\r\n<p>Cell is identified by indexes of two section items from perpendicular axises. Cell area does not include lines, so whenever the line width changes the cell content painting algorithm can stay untouched. Painting lines separately provides also speed optimization advantages.</p>\r\n<p>The line at index n belongs logically to section n-th item as well as cell at index n, so whenever the n-th item is hidden or moved so is the n-th line. The exception is line with index equal to <code>section.getCount()</code>, which is not bound to any cell.</p>\r\n</td>\r\n</tr>\r\n</tbody>\r\n</table>\r\n<h3>Painting</h3>\r\n<p><a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html">Painter</a> is responsible to <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">draw</span> everything that appears on the matrix canvas: background, images, text, lines. The design of painting mechanism allows 100% customization of the matrix appearance.</p>\r\n<h4>Painting order</h4>\r\n<p>Both matrix and each zone has an individual list of painters that defines the order in which the painters <code>paint</code> method is executed. The painter list can be manipulated by the <code><a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#addPainter%28int,%20pl.netanel.swt.matrix.Painter%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#addPainter(int, pl.netanel.swt.matrix.Painter)">addPainter</a></code>, <code><a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#removePainter%28int%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#removePainter(int)">removePainter</a></code>, <code><a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#setPainter%28int,%20pl.netanel.swt.matrix.Painter%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#setPainter(int, pl.netanel.swt.matrix.Painter)">setPainter</a></code>, <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#replacePainter%28pl.netanel.swt.matrix.Painter%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html#replacePainter(pl.netanel.swt.matrix.Painter)"><code>replacePainter</code> </a>methods.</p>\r\n<p>The default structure of painters is following:</p>\r\n<ul>\r\n<li>matrix painters - collection of painters  \r\n<ul>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen</span> <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">area </span>painter - named by one of the Painter.NAME_FROZEN_... names, <span mce_name="em" mce_style="font-style: italic;" style="font-style: italic;" class="Apple-style-span" mce_fixed="1">(description in the next paragraph)</span> \r\n<ul>\r\n<li>zones - that are visible in the given frozen area  \r\n<ul>\r\n<li>zone painters - collection of painters  \r\n<ul>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">cells</span> painter - named Painter.NAME_CELLS</li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">horizontal lines</span> painter - named Painter.NAME_LINES_X</li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">vertical lines</span> painter - named Painter.NAME_LINES_Y</li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">emulated controls</span> painter - named Painter.NAME_EMULATED_CONTROLS</li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">embedded controls</span> painter - named Painter.NAME_EMBEDDED_CONTROLS</li>\r\n</ul>\r\n</li>\r\n</ul>\r\n</li>\r\n</ul>\r\n</li>\r\n</ul>\r\n<ul>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">focus cell</span> painter - a painter named Painter.NAME_FOCUS_CELL</li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">drag horizontal item</span> painter - a painter named Painter.NAME_DRAG_ITEM_X</li><li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">drag vertical item</span> painter - a painter named Painter.NAME_DRAG_ITEM_Y</li>\r\n</ul>\r\n</li>\r\n</ul>\r\n<p>Frozen area painter is responsible for painting separately one of 9 matrix areas formed by the head and tail freeze dividers on both axises:</p>\r\n<ul>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen none none</span> -&nbsp;<span class="column2">area not frozen neither on X axis not on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen none tail</span> -&nbsp;<span class="column2">area not frozen on X axis and tail frozen on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen tail none</span> -&nbsp;<span class="column2">area tail frozen on X axis and not frozen on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen none head</span> -&nbsp;<span class="column2">area not frozen on X axis and head frozen on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen head none</span> -&nbsp;<span class="column2">area head frozen on X axis and not frozen on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen head tail</span> -&nbsp;<span class="column2">area head frozen on X axis and tail frozen on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen tail head</span> -&nbsp;<span class="column2">area tail frozen on X axis and head frozen on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen tail tail</span> -&nbsp;<span class="column2">area tail frozen on X axis and tail frozen on Y axis</span></li>\r\n<li><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">frozen head head</span> -&nbsp;<span class="column2">area head frozen on X axis and head frozen on Y axis</span></li>\r\n</ul>\r\n<h4>Painter Scope</h4>\r\n<p>The painter type specifies the scope in which it paints. The zone painting mechanism feeds the paint method with with values appropriate for the given scope.</p>\r\n<p>Only zone painters can handle various painter scopes. Matrix works with only one type <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html#SCOPE_SINGLE" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html#SCOPE_SINGLE">SCOPE_SINGLE</a>, so the actual painter type is ignored.</p>\r\n<h4>Painter Properties</h4>\r\n<p>Painter has a set of public properties that are investigated by the paint method to determine what should be drawn on the canvas. Those properties include:</p>\r\n<ul>\r\n<li><code>text</code>, <code>textAlignX</code>, <code>textAlignY</code>, <code>textMarginX</code>, <code>textMarginY</code>,</li>\r\n<li><code>image</code>, <code>imageAlignX</code>, <code>imageAlignX</code>, <code>imageMarginX</code>, <code>imageMarginY</code></li>\r\n<li><code>foreground</code> and <code>background</code> colors</li>\r\n<li><code>selectionHighlight</code></li>\r\n</ul>\r\n<p>In order to alter properties depending on the cell indexes one should replace the default painter, for instance (<a href="swt-matrix/snippets/Snippet_0017.java" mce_href="swt-matrix/snippets/Snippet_0017.java">Snippet_0017</a>):</p>\r\n<pre>final Color color = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);\r\nmatrix.getBody().replacePainter(new Painter<integer, integer="">(Painter.NAME_CELLS, Painter.SCOPE_CELLS) {\r\n    @Override\r\n    public void setup(Integer indexX, Integer indexY) {\r\n        background = indexY % 2 + indexX % 2 == 1 ? color : null;\r\n    }\r\n});\r\n</integer,></pre>\r\n<h3>Zone</h3>\r\n<h4>Cell Merging<br /></h4><p>Cell merging can be done by (<a mce_href="swt-matrix/snippets/S0210_CellMerging.java" href="swt-matrix/snippets/S0210_CellMerging.java">Snippet S0210</a>):</p><pre>zone.setMerged(startX, countX, startY, countY);</pre><p>If the range defined by setMerging contains a cell that is merged, then all cells merged with that cell will be unmerged. This behavior is the same as in spreadsheets.</p><p>The merged area is attached to the cell with (startX, startY)  coordinates, and is not attached the cell at the end of the merged area,  so the cells can be moved in and out the merged region - this opertion  does not change the merged area size. The area size changes when the  cells inside of it get hidden or shown.</p><p>The maximum size of the merged area can be set by:</p><pre>zone.setMergeLimit(X limitX, Y limitY);</pre><p>It is introduced to prevent performance problems with layout calculation which in case of merged cells must go beyond viewport area. The default value of the merge limit is 1000.<br /></p><p>Cell can be checked for being merged by:</p><pre>zone.isMerged(indexX, indexY);</pre><h3>Event handling</h3>\r\n<h4>Command Binding</h4>\r\n<p>Typical commands are bound to keyboard and mouse gestures. The default bindings are enlisted {article swt-matrix-bindings}{link}here{/link}{/article}. The more complex action triggering conditions includes moving by select axis items and start dragging from one of them.</p>\r\n<p>The bindings can be added or removed by the <code>bind</code> and <code>unbind</code> methods. For example the default matrix binding to move the focus cell to the beginning of line can be altered by (<a href="swt-matrix/snippets/Snippet_0901.java" mce_href="swt-matrix/snippets/Snippet_0901.java" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">Snippet_0901</a>):</p>\r\n<pre>matrix.unbind(Matrix.CMD_FOCUS_MOST_LEFT, SWT.KeyDown, SWT.HOME);    // Windows standard<br />matrix.bind(Matrix.CMD_FOCUS_MOST_LEFT, SWT.KeyDown, SWT.MOD1 | SWT.ARROW_LEFT);    // Mac OS standard</pre>\r\n<p>Zones also have the <code>bind</code> and <code>unbind</code> methods.</p>\r\n<h4>Secondary Events</h4>\r\n<p>A SelectionEvent event of selecting cells by user can be handled with the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Zone.html#addSelectionListener%28org.eclipse.swt.events.SelectionListener%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Zone.html#addSelectionListener(org.eclipse.swt.events.SelectionListener)">Zone.addSelectionListener</a> method and selecting section items by the user can be handled with the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Section.html#addSelectionListener%28org.eclipse.swt.events.SelectionListener%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Section.html#addSelectionListener(org.eclipse.swt.events.SelectionListener)">Section.addSelectionListener</a> method. Also a ControlEvent can be handled by <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Section.html#addControlListener%28org.eclipse.swt.events.ControlListener%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Section.html#addControlListener(org.eclipse.swt.events.ControlListener)">Section.addControlListener</a> method. See <a href="swt-matrix/snippets/Snippet_0902.java" mce_href="swt-matrix/snippets/Snippet_0902.java" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">Snippet_0902</a>.</p>\r\n<h3><img mce_name="a" name="Editing" class="mceItemAnchor">Editing</h3>\r\n<p>Editing of a matrix cells is facilitated by the <a href="swt-matrix/javadoc/index.html" mce_href="swt-matrix/javadoc/index.html">ZoneEditor</a> class, for each &lt;code&gt;Zone&lt;/code&gt; separately. The simplest way to make the matrix editable is to instiate the <code>ZoneEditor</code> class with a concrete implementation of the <code>setModelValue</code> method to apply the modified value to the model (see <a href="swt-matrix/snippets/Snippet_0401.java" mce_href="swt-matrix/snippets/Snippet_0401.java">Snippet_0401</a>):</p>\r\n<pre>data = new ArrayList&lt;Object[]&gt;\r\nnew ZoneEditor(matrix.getBody()) {\r\n    @Override\r\n    public void setModelValue(Number indexX, Number indexY, Object value) {\r\n        data.get(indexY.intValue())[indexX.intValue()] = value;\r\n    }\r\n};\r\n</pre>\r\n<p>The default cell editor control for the above is <code>Text</code> and the value to edit is taken from the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html#getText%28N0,%20N1%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html#getText(N0, N1)">Painter.getText(Number, Number)</a> method of the "cells" painter of the zone being edited.</p>\r\n<p>Besides <code>Text</code> the <code>ZoneEditor</code> also supports other <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">cell editor controls</span>: native <code>Combo</code>, <code>DateTime</code> controls and emulated, native looking check boxes.</p>\r\n<p>In order to use other controls then <code>Text</code> the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#createControl%28N0,%20N1%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#createControl(N0, N1)" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">createControl(Number, Number)</a> method of <code>ZoneEditor</code> must be overriden to return the desired control for the specified cells. For example:</p>\r\n<pre>    @Override\r\n    public Control createControl(Number indexX, Number indexY, Composite parent) {\r\n        if (indexX.intValue() == 2) {\r\n            Combo combo = new Combo(parent, SWT.BORDER);\r\n            combo.setItems(comboItems);\r\n            return combo;\r\n        } \r\n        else if (indexX.intValue() == 3) {\r\n            return new DateTime(matrix, SWT.BORDER);\r\n        }\r\n        else {\r\n             return super.createControl(index0, index1);\r\n         }\r\n     }</pre>\r\n<p>Since <code>DateTime</code> control is suited to <code>Date </code>values and check boxes to <code>Boolean</code> values thus the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#getModelValue%28N0,%20N1%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#getModelValue(N0, N1)">getModelValue(Number, Number)</a> method provides a mechanism to get a proper <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">type of value</span> suited to the cell editor control. For example:</p>\r\n<pre>    @Override\r\n    public Object getModelValue(Number indexX, Number indexY) {\r\n        return data.get(indexY.intValue())[indexX.intValue()];\r\n    }</pre>\r\n<p>There is also method to set value in the cell editor control after it is activated <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#setEditorValue%28org.eclipse.swt.widgets.Control,%20java.lang.Object%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#setEditorValue(org.eclipse.swt.widgets.Control, java.lang.Object)">setEditorValue(Control, Object)</a> and <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#getEditorValue%28org.eclipse.swt.widgets.Control%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#getEditorValue(org.eclipse.swt.widgets.Control)">getEditorValue(Control)</a> method to get the value from the cell editor control after the apply action has been triggered. They need to be overridden in order to handle <span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">custom control types</span> other then the built-in ones. For example:</p>\r\n<pre>    @Override\r\n    public void setEditorValue(Control control, Object value) {\r\n        if (control instanceof List) {\r\n            List list = ((List) control);\r\n            list.deselectAll();\r\n            int position = list.indexOf((String) value);\r\n            if (position != -1) {\r\n                list.select(position);\r\n            }\r\n        } else {\r\n            super.setEditorValue(control, value);\r\n        }\r\n    }\r\n    \r\n    @Override\r\n    public Object getEditorValue(Control control) {\r\n        if (control instanceof List) {\r\n            List list = (List) control;\r\n            int position = list.getSelectionIndex();\r\n            return position == -1 ? null : list.getItem(position);\r\n        } else {\r\n            return super.getEditorValue(control);\r\n        }\r\n    }</pre>\r\n<h4>Embedded Controls</h4>\r\n<p>Edit controls can be be embedded in the cells by returning true from the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#hasEmbeddedControl%28N0,%20N1%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#hasEmbeddedControl(N0, N1)" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">hasEmbeddedControl(Number, Number)</a> method, for example (<a href="swt-matrix/snippets/Snippet_0402.java" mce_href="swt-matrix/snippets/Snippet_0402.java" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">Snippet_0402</a>):</p>\r\n<pre>    @Override\r\n    public boolean hasEmbeddedControl(Number indexX, Number indexY) {\r\n        Object value = data.get(indexY.intValue())[indexX.intValue()];\r\n        return value instanceof Boolean;\r\n    }\r\n    \r\n    @Override\r\n    protected Control createControl(Number indexX, Number indexY, Composite parent) {\r\n        Object value = data.get(indexY.intValue())[indexX.intValue()];\r\n        if (value instanceof Boolean) {\r\n            return new Button(parent, SWT.CHECK);\r\n        }\r\n        return super.createControl(indexX, indexY);\r\n    }</pre>\r\n<h4>Checkbox Emulation</h4>\r\n<p>Embedding controls in cells hurts performance. Check boxes can be emulated by returning a value from the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#getCheckboxEmulation%28N0,%20N1%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#getCheckboxEmulation(N0, N1)" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">getCheckboxEmulation(Number, Number)</a> method, for example:</p>\r\n<pre>    @Override\r\n    public Object[] getCheckboxEmulation(Number indexX, Number indexY) {\r\n        Object value = data.get(indexX.intValue())[indexY.intValue()];\r\n        return value instanceof Boolean ? getDefaultCheckBoxImages() : null;\r\n    } </pre>\r\n<p>The images are taken from the path specified by the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#setEmulationPath%28java.lang.String%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#setEmulationPath(java.lang.String)">setEmulationPath(String)</a> method.</p>\r\n<p>The images of the check boxes in the current system theme can be created by then <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#snapControlImages%28java.lang.String%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#snapControlImages(java.lang.String)">snapControlImages(String) </a>the method.</p>\r\n<h4>Clipboard Operations</h4>\r\n<p><code>MatrixEditor</code> is also responcible for clipboard operations.</p>\r\n<p>The <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#copy%28%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#copy()">copy()</a> method uses the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#format%28N0,%20N1%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#format(N0, N1)">format(Number, Number)</a> function to get the <code>String</code> values for the individual cell. By default it is the value returned from the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html#getText%28N0,%20N1%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Painter.html#getText(N0, N1)" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">Painter.getText(Number, Number)</a> method of the "cells" painter of the zone being edited.</p>\r\n<p>The <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#cut%28%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#cut()" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">cut()</a> method sets <code>null </code>value to the selected cells after invoking <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#copy%28%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#copy()" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">copy()</a> method.</p>\r\n<p>The <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#paste%28%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#paste()" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">paste()</a> method uses the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#parse%28N0,%20N1,%20java.lang.String%29" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/ZoneEditor.html#parse(N0, N1, java.lang.String)" style="color: #1b57b1; text-decoration: none; font-weight: normal;" mce_style="color: #1b57b1; text-decoration: none; font-weight: normal;">parse(Number, Number, String)</a> function to parse the values for the individual cells.</p>\r\n<p>The clipboard operations can be customized by overriding any of the above mentioned methods.</p>\r\n<h4>Commands</h4>\r\n<p>The default command bindings are listed below:</p>\r\n<table class="data mceItemTable" border="0">\r\n<tbody>\r\n<tr>\r\n<td class="caption"><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Command</span></td>\r\n<td class="caption"><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1">Matrix</span></td>\r\n<td><span mce_name="strong" mce_style="font-weight: bold;" style="font-weight: bold;" class="Apple-style-span" mce_fixed="1"><span class="caption">Edit C</span>ontrol</span></td>\r\n</tr>\r\n<tr>\r\n<td>CMD_EDIT</td>\r\n<td>SWT.KeyDown SWT.F2 or SWT.MouseDoubleClick or<br />(for check buttons only) SWT.KeyDown '' ''</td>\r\n<td><br mce_bogus="1"></td>\r\n</tr>\r\n<tr>\r\n<td>CMD_APPLY_EDIT</td>\r\n<td><br mce_bogus="1"></td>\r\n<td>SWT.CR or SWT.FocusOut</td>\r\n</tr>\r\n<tr>\r\n<td>CMD_CANCEL_EDIT</td>\r\n<td><br mce_bogus="1"></td>\r\n<td>SWT.ESC</td>\r\n</tr>\r\n<tr>\r\n<td>CMD_COPY</td>\r\n<td>SWT.KeyDown SWT.MOD1 | ''c''</td>\r\n<td><br mce_bogus="1"></td>\r\n</tr>\r\n<tr>\r\n<td>CMD_PASTE</td>\r\n<td>SWT.KeyDown SWT.MOD1 | ''p''</td>\r\n<td><br mce_bogus="1"></td>\r\n</tr>\r\n<tr>\r\n<td>CMD_CUT</td>\r\n<td>SWT.KeyDown SWT.MOD1 | ''x''</td>\r\n<td><br mce_bogus="1"></td>\r\n</tr>\r\n<tr>\r\n<td>CMD_DEL</td>\r\n<td>SWT.KeyDown SWT.DEL</td>\r\n<td><br mce_bogus="1"></td>\r\n</tr>\r\n</tbody>\r\n</table>\r\n<p><span style="font-family: monospace; font-size: xx-small;" mce_style="font-family: monospace; font-size: xx-small;"><br /></span></p>', '', 1, 1, 0, 1, '2011-03-15 10:17:35', 62, '', '2012-07-29 05:47:34', 62, 0, '0000-00-00 00:00:00', '2011-03-15 10:17:35', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 211, 0, 8, '', '', 0, 2392, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (14, 'Buy', 'buy', '', '<p>The use of SWT Matrix is guarded by this End User License Agreement <a mce_href="swt-matrix/EULA_v1.0.html" href="swt-matrix/EULA_v1.0.html">EULA</a>.<br /></p><p>The purchase plans are following:<br /></p><ol><li>Developer license: $129, there are volume discounts.</li><li>Site license: $2000 - unlimited number of developers in one organization.</li><li>Non commercial license: $0 - for private use and non-commercial products, although donations are welcome.</li><li>Startup license: no payments are required until the product built with it earns a revenue of 3 times the price of SWT Matrix, organization is considered startup if has less then 3 developers.</li></ol><p> Please note that Developer is a named user and not a concurrent user.<br /> <br />The purchase includes one year of free upgrades. After that period  the license can be renewed for 25% of the full license price at that  time in order to get upgrades for another year. Skipping the license  renewal does not prevent you from using older versions of the product  within range of one year from your last license purchase.<br /> Redistribution of swt-matrix-*.jar to the end users of your product is royalty free.<br /> Bugs are fixed obviously free of charge - in most cases within 48 hours.</p><p>Please contact us via <a href="mailto:%20office@netanel.pl" mce_href="mailto: office@netanel.pl" style="">office@netanel.pl</a> to discuss licencing and procurement.</p><p>\r\n<a href="https://sites.fastspring.com/netanel/instant/swt-matrix" mce_href="https://sites.fastspring.com/netanel/instant/swt-matrix" target="_top"><img mce_src="images/fs_buynow.png" src="images/fs_buynow.png" alt="buy now" height="67" width="280"></a><br mce_bogus="1"></p>', '', 1, 1, 0, 1, '2011-03-17 14:17:45', 62, '', '2012-08-03 06:48:46', 62, 62, '2012-10-08 23:31:01', '2011-03-17 14:17:45', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 91, 0, 7, '', '', 0, 1260, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (19, 'Forum', 'forum', '', '<p><a id="nabblelink" href="http://swt-matrix.1049369.n5.nabble.com/">swt-matrix</a></p>\r\n<script src="http://swt-matrix.1049369.n5.nabble.com/embed/f4367519"></script>', '', 1, 1, 0, 1, '2011-07-30 21:00:59', 62, '', '2011-08-18 20:55:19', 62, 0, '0000-00-00 00:00:00', '2011-07-30 21:00:59', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 2, 0, 2, '', '', 0, 642, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (16, 'Key & Mouse Bindings', 'swt-matrix-bindings', '', '<p>All the command constants (CMD_...) are defined in the <a href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html" mce_href="swt-matrix/javadoc/pl/netanel/swt/matrix/Matrix.html">Matrix</a> class.</p>\r\n<p>The SWT constants are from the standard SWT library class.</p>\r\n<p>The character codes denote keys and numeric codes denote mouse buttons.</p>\r\n<h3>Matrix Bindings</h3>\r\n<p>Matrix bindings apply to all zones.</p>\r\n<p>\r\n<table class="dictionary mceItemTable" border="0">\r\n<tbody>\r\n<tr>\r\n<th>Event Type</th><th>Key / Mouse Combination</th> <th>Command</th>\r\n</tr>\r\n<tr>\r\n<td colspan="3">\r\n<h4>Focus Cell Navigation - keys</h4>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.ARROW_UP</td>\r\n<td>CMD_FOCUS_UP</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.ARROW_DOWN</td>\r\n<td>CMD_FOCUS_DOWN</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.ARROW_LEFT</td>\r\n<td>CMD_FOCUS_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.ARROW_RIGHT</td>\r\n<td>CMD_FOCUS_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.PAGE_DOWN</td>\r\n<td>CMD_FOCUS_PAGE_DOWN</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.PAGE_UP</td>\r\n<td>CMD_FOCUS_PAGE_UP</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD3 | SWT.PAGE_DOWN</td>\r\n<td>CMD_FOCUS_PAGE_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD3 | SWT.PAGE_UP</td>\r\n<td>CMD_FOCUS_PAGE_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.HOME</td>\r\n<td>CMD_FOCUS_MOST_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.END</td>\r\n<td>CMD_FOCUS_MOST_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.PAGE_UP</td>\r\n<td>CMD_FOCUS_MOST_UP</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.PAGE_DOWN</td>\r\n<td>CMD_FOCUS_MOST_DOWN</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.HOME</td>\r\n<td>CMD_FOCUS_MOST_UP_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.END</td>\r\n<td>CMD_FOCUS_MOST_DOWN_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td colspan="3">\r\n<h4>Focus Cell Navigation - mouse</h4>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.MouseDown</td>\r\n<td>button 1</td>\r\n<td>CMD_FOCUS_LOCATION</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.MouseDown</td>\r\n<td>SWT.MOD1 | button 1</td>\r\n<td>CMD_FOCUS_LOCATION_ALTER</td>\r\n</tr>\r\n<tr>\r\n<td colspan="3">\r\n<h4>Cell Selection - keys</h4>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | ''a''</td>\r\n<td>CMD_SELECT_ALL</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.ARROW_UP</td>\r\n<td>CMD_SELECT_UP</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.ARROW_DOWN</td>\r\n<td>CMD_SELECT_DOWN</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.ARROW_LEFT</td>\r\n<td>CMD_SELECT_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.ARROW_RIGHT</td>\r\n<td>CMD_SELECT_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.PAGE_UP</td>\r\n<td>CMD_SELECT_PAGE_UP</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.PAGE_DOWN</td>\r\n<td>CMD_SELECT_PAGE_DOWN</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.MOD3 | SWT.ARROW_LEFT</td>\r\n<td>CMD_SELECT_PAGE_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.MOD3 | SWT.ARROW_RIGHT</td>\r\n<td>CMD_SELECT_PAGE_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.MOD2 | SWT.PAGE_UP</td>\r\n<td>CMD_SELECT_FULL_UP</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.MOD2 | SWT.PAGE_DOWN</td>\r\n<td>CMD_SELECT_FULL_DOWN</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.HOME</td>\r\n<td>CMD_SELECT_FULL_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD2 | SWT.END</td>\r\n<td>CMD_SELECT_FULL_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.MOD2 | SWT.HOME</td>\r\n<td>CMD_SELECT_FULL_UP_LEFT</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.KeyDown</td>\r\n<td>SWT.MOD1 | SWT.MOD2 | SWT.END</td>\r\n<td>CMD_SELECT_FULL_DOWN_RIGHT</td>\r\n</tr>\r\n<tr>\r\n<td colspan="3">\r\n<h4>Cell Selection - mouse</h4>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.MouseDown</td>\r\n<td>SWT.MOD2 | button 1</td>\r\n<td>CMD_SELECT_TO_LOCATION</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.MouseDown</td>\r\n<td>SWT.MOD1 | SWT.MOD2 | button 1</td>\r\n<td>CMD_SELECT_TO_LOCATION_ALTER</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.MouseMove</td>\r\n<td>SWT.BUTTON1</td>\r\n<td>CMD_SELECT_TO_LOCATION</td>\r\n</tr>\r\n<tr>\r\n<td>SWT.MouseMove</td>\r\n<td>SWT.MOD1 | SWT.BUTTON1</td>\r\n<td>CMD_SELECT_TO_LOCATION_ALTER</td>\r\n</tr>\r\n\r\n<tr>\r\n<td colspan="3">\r\n<h4>Other</h4>\r\n</td>\r\n</tr>\r\n<tr><td>SWT.KeyDown</td><td>''c''</td><td>Matrix.CMD_COPY</td></tr>\r\n<tr><td>SWT.KeyDown</td><td>SWT.MOD3 | SWT.DEL</td><td>Matrix.CMD_ITEM_HIDE</td></tr>\r\n<tr><td>SWT.KeyDown</td><td>SWT.MOD3 | SWT.INSERT</td><td>Matrix.CMD_ITEM_SHOW</td></tr>\r\n\r\n</tbody>\r\n</table>\r\n</p>\r\n\r\n\r\n\r\n<h3>Row Header Bindings</h3>\r\n<p>These bindings apply only to row header zone.</p>\r\n<p>\r\n<table class="dictionary mceItemTable" border="0">\r\n<tbody>\r\n<tr>\r\n<th>Event Type</th><th>Key / Mouse Combination</th> <th>Command</th>\r\n</tr>\r\n<tr><td>SWT.MouseDown</td><td>button 1</td><td>Matrix.CMD_SELECT_ROW</td></tr>\r\n<tr><td>SWT.MouseDown</td><td>SWT.MOD1 | button 1</td><td>Matrix.CMD_SELECT_ROW_ALTER</td></tr>\r\n<tr><td>SWT.MouseDown</td><td>SWT.MOD2 | button 1</td><td>Matrix.CMD_SELECT_TO_ROW</td></tr>\r\n<tr><td>SWT.MouseDown</td><td>SWT.MOD1 | SWT.MOD2 | button 1</td><td>Matrix.CMD_SELECT_TO_ROW_ALTER</td></tr>\r\n<tr><td>SWT.MouseMove</td><td>SWT.BUTTON1</td><td>Matrix.CMD_SELECT_TO_ROW</td></tr>\r\n<tr><td>SWT.MouseMove</td><td>SWT.MOD1 | SWT.BUTTON1</td><td>Matrix.CMD_SELECT_TO_ROW_ALTER</td></tr>\r\n<tr><td>SWT.MouseDoubleClick</td><td>button 1</td><td>Matrix.CMD_RESIZE_PACK</td></tr>\r\n</tbody>\r\n</table>\r\n</p>\r\n\r\n\r\n\r\n<h3>Column Header Bindings</h3>\r\n<p>These bindings apply only to column header zone.</p>\r\n<p>\r\n<table class="dictionary mceItemTable" border="0">\r\n<tbody>\r\n<tr>\r\n<th>Event Type</th><th>Key / Mouse Combination</th> <th>Command</th>\r\n</tr>\r\n<tr><td>SWT.MouseDown</td><td>button 1</td><td>Matrix.CMD_SELECT_COLUMN</td></tr>\r\n<tr><td>SWT.MouseDown</td><td>SWT.MOD1 | button 1</td><td>Matrix.CMD_SELECT_COLUMN_ALTER</td></tr>\r\n<tr><td>SWT.MouseDown</td><td>SWT.MOD2 | button 1</td><td>Matrix.CMD_SELECT_TO_COLUMN</td></tr>\r\n<tr><td>SWT.MouseDown</td><td>SWT.MOD1 | SWT.MOD2 | button 1</td><td>Matrix.CMD_SELECT_TO_COLUMN_ALTER</td></tr>\r\n<tr><td>SWT.MouseMove</td><td>SWT.BUTTON1</td><td>Matrix.CMD_SELECT_TO_COLUMN</td></tr>\r\n<tr><td>SWT.MouseMove</td><td>SWT.MOD1 | SWT.BUTTON1</td><td>Matrix.CMD_SELECT_TO_COLUMN_ALTER</td></tr>\r\n<tr><td>SWT.MouseDoubleClick</td><td>button 1</td><td>Matrix.CMD_RESIZE_PACK</td></tr>\r\n</tbody>\r\n</table>\r\n</p>\r\n\r\n\r\n<h3>Top Left Bindings</h3>\r\n<p>These bindings apply only to top left zone.</p>\r\n<p>\r\n<table class="dictionary mceItemTable" border="0">\r\n<tbody>\r\n<tr>\r\n<th>Event Type</th><th>Key / Mouse Combination</th> <th>Command</th>\r\n</tr>\r\n<tr><td>SWT.MouseDown</td><td>button 1</td><td>Matrix.CMD_SELECT_ALL</td></tr>\r\n\r\n</tbody>\r\n</table>\r\n</p>', '', 1, 1, 0, 1, '2011-04-26 12:12:06', 62, '', '2012-04-21 22:47:41', 62, 0, '0000-00-00 00:00:00', '2011-04-26 12:12:06', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 40, 0, 5, '', '', 0, 489, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (15, 'FAQ', 'swt-matrix-faq', '', '<p>Q. I can''t find the required method in Matrix class, where is it?</p>\r\n<p>A. Most of the cell related methods are in Zone class. The axis items, which are rows and columns, on the other hand are handled by Section and Axis classes.</p>', '', 1, 1, 0, 1, '2011-03-27 16:35:28', 62, '', '0000-00-00 00:00:00', 0, 0, '0000-00-00 00:00:00', '2011-03-27 16:35:28', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 1, 0, 6, '', '', 0, 0, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (17, 'Purchase Completion', 'purchase-completion', '', '<p>Thank you for your purchase.</p><p>Please contact us <a href="mailto: support@netanel.pl" mce_href="mailto: support@netanel.pl" style="">support@netanel.pl</a> in case of any questions regarding the product.</p>', '', 1, 1, 0, 1, '2011-07-08 15:41:44', 62, '', '2011-07-08 16:29:48', 62, 0, '0000-00-00 00:00:00', '2011-07-08 15:41:44', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 2, 0, 4, '', '', 0, 0, 'robots=\nauthor=');
INSERT INTO `jos_content` VALUES (20, 'Screenshots', 'screenshots', '', '<p><img mce_src="images/swt-matrix-screenshot-1.jpg" src="images/swt-matrix-screenshot-1.jpg" width="976" height="456" alt="screenshot 1" style=""></p>\r\n<script type="text/javascript">// <![CDATA[\r\nfilterFeatures();\r\n// ]]></script>', '', 1, 1, 0, 1, '2011-08-18 20:09:57', 62, '', '2011-08-18 20:51:16', 62, 0, '0000-00-00 00:00:00', '2011-08-18 20:09:57', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 9, 0, 1, '', '', 0, 2022, 'robots=\nauthor=');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_content_frontpage`
-- 

CREATE TABLE `jos_content_frontpage` (
  `content_id` int(11) NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_content_frontpage`
-- 

INSERT INTO `jos_content_frontpage` VALUES (1, 1);
INSERT INTO `jos_content_frontpage` VALUES (9, 2);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_content_rating`
-- 

CREATE TABLE `jos_content_rating` (
  `content_id` int(11) NOT NULL default '0',
  `rating_sum` int(11) unsigned NOT NULL default '0',
  `rating_count` int(11) unsigned NOT NULL default '0',
  `lastip` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_content_rating`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_core_acl_aro`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

-- 
-- Dumping data for table `jos_core_acl_aro`
-- 

INSERT INTO `jos_core_acl_aro` VALUES (10, 'users', '62', 0, 'Administrator', 0);
INSERT INTO `jos_core_acl_aro` VALUES (11, 'users', '63', 0, 'Jacek', 0);
INSERT INTO `jos_core_acl_aro` VALUES (12, 'users', '64', 0, 'bbb', 0);
INSERT INTO `jos_core_acl_aro` VALUES (13, 'users', '65', 0, 'cccc', 0);
INSERT INTO `jos_core_acl_aro` VALUES (14, 'users', '66', 0, 'dddd', 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_core_acl_aro_groups`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 AUTO_INCREMENT=31 ;

-- 
-- Dumping data for table `jos_core_acl_aro_groups`
-- 

INSERT INTO `jos_core_acl_aro_groups` VALUES (17, 0, 'ROOT', 1, 22, 'ROOT');
INSERT INTO `jos_core_acl_aro_groups` VALUES (28, 17, 'USERS', 2, 21, 'USERS');
INSERT INTO `jos_core_acl_aro_groups` VALUES (29, 28, 'Public Frontend', 3, 12, 'Public Frontend');
INSERT INTO `jos_core_acl_aro_groups` VALUES (18, 29, 'Registered', 4, 11, 'Registered');
INSERT INTO `jos_core_acl_aro_groups` VALUES (19, 18, 'Author', 5, 10, 'Author');
INSERT INTO `jos_core_acl_aro_groups` VALUES (20, 19, 'Editor', 6, 9, 'Editor');
INSERT INTO `jos_core_acl_aro_groups` VALUES (21, 20, 'Publisher', 7, 8, 'Publisher');
INSERT INTO `jos_core_acl_aro_groups` VALUES (30, 28, 'Public Backend', 13, 20, 'Public Backend');
INSERT INTO `jos_core_acl_aro_groups` VALUES (23, 30, 'Manager', 14, 19, 'Manager');
INSERT INTO `jos_core_acl_aro_groups` VALUES (24, 23, 'Administrator', 15, 18, 'Administrator');
INSERT INTO `jos_core_acl_aro_groups` VALUES (25, 24, 'Super Administrator', 16, 17, 'Super Administrator');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_core_acl_aro_map`
-- 

CREATE TABLE `jos_core_acl_aro_map` (
  `acl_id` int(11) NOT NULL default '0',
  `section_value` varchar(230) NOT NULL default '0',
  `value` varchar(100) NOT NULL,
  PRIMARY KEY  (`acl_id`,`section_value`,`value`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_core_acl_aro_map`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_core_acl_aro_sections`
-- 

CREATE TABLE `jos_core_acl_aro_sections` (
  `id` int(11) NOT NULL auto_increment,
  `value` varchar(230) NOT NULL default '',
  `order_value` int(11) NOT NULL default '0',
  `name` varchar(230) NOT NULL default '',
  `hidden` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `jos_gacl_value_aro_sections` (`value`),
  KEY `jos_gacl_hidden_aro_sections` (`hidden`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

-- 
-- Dumping data for table `jos_core_acl_aro_sections`
-- 

INSERT INTO `jos_core_acl_aro_sections` VALUES (10, 'users', 1, 'Users', 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_core_acl_groups_aro_map`
-- 

CREATE TABLE `jos_core_acl_groups_aro_map` (
  `group_id` int(11) NOT NULL default '0',
  `section_value` varchar(240) NOT NULL default '',
  `aro_id` int(11) NOT NULL default '0',
  UNIQUE KEY `group_id_aro_id_groups_aro_map` (`group_id`,`section_value`,`aro_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_core_acl_groups_aro_map`
-- 

INSERT INTO `jos_core_acl_groups_aro_map` VALUES (18, '', 11);
INSERT INTO `jos_core_acl_groups_aro_map` VALUES (18, '', 12);
INSERT INTO `jos_core_acl_groups_aro_map` VALUES (18, '', 13);
INSERT INTO `jos_core_acl_groups_aro_map` VALUES (18, '', 14);
INSERT INTO `jos_core_acl_groups_aro_map` VALUES (25, '', 10);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_core_log_items`
-- 

CREATE TABLE `jos_core_log_items` (
  `time_stamp` date NOT NULL default '0000-00-00',
  `item_table` varchar(50) NOT NULL default '',
  `item_id` int(11) unsigned NOT NULL default '0',
  `hits` int(11) unsigned NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_core_log_items`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_core_log_searches`
-- 

CREATE TABLE `jos_core_log_searches` (
  `search_term` varchar(128) NOT NULL default '',
  `hits` int(11) unsigned NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_core_log_searches`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_extensions`
-- 

CREATE TABLE `jos_extensions` (
  `extension_id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `type` varchar(20) NOT NULL,
  `element` varchar(100) NOT NULL,
  `folder` varchar(100) NOT NULL,
  `client_id` tinyint(3) NOT NULL,
  `enabled` tinyint(3) NOT NULL default '1',
  `access` tinyint(3) unsigned NOT NULL default '1',
  `protected` tinyint(3) NOT NULL default '0',
  `manifest_cache` text NOT NULL,
  `params` text NOT NULL,
  `custom_data` text NOT NULL,
  `system_data` text NOT NULL,
  `checked_out` int(10) unsigned NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ordering` int(11) default '0',
  `state` int(11) default '0',
  PRIMARY KEY  (`extension_id`),
  KEY `element_clientid` (`element`,`client_id`),
  KEY `element_folder_clientid` (`element`,`folder`,`client_id`),
  KEY `extension` (`type`,`element`,`folder`,`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10004 DEFAULT CHARSET=utf8 AUTO_INCREMENT=10004 ;

-- 
-- Dumping data for table `jos_extensions`
-- 

INSERT INTO `jos_extensions` VALUES (1, 'com_mailto', 'component', 'com_mailto', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (2, 'com_wrapper', 'component', 'com_wrapper', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (3, 'com_admin', 'component', 'com_admin', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (4, 'com_banners', 'component', 'com_banners', '', 1, 1, 1, 0, '', '{"purchase_type":"3","track_impressions":"0","track_clicks":"0","metakey_prefix":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (5, 'com_cache', 'component', 'com_cache', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (6, 'com_categories', 'component', 'com_categories', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (7, 'com_checkin', 'component', 'com_checkin', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (8, 'com_contact', 'component', 'com_contact', '', 1, 1, 1, 0, '', '{"show_contact_category":"hide","show_contact_list":"0","presentation_style":"sliders","show_name":"1","show_position":"1","show_email":"0","show_street_address":"1","show_suburb":"1","show_state":"1","show_postcode":"1","show_country":"1","show_telephone":"1","show_mobile":"1","show_fax":"1","show_webpage":"1","show_misc":"1","show_image":"1","image":"","allow_vcard":"0","show_articles":"0","show_profile":"0","show_links":"0","linka_name":"","linkb_name":"","linkc_name":"","linkd_name":"","linke_name":"","contact_icons":"0","icon_address":"","icon_email":"","icon_telephone":"","icon_mobile":"","icon_fax":"","icon_misc":"","show_headings":"1","show_position_headings":"1","show_email_headings":"0","show_telephone_headings":"1","show_mobile_headings":"0","show_fax_headings":"0","allow_vcard_headings":"0","show_suburb_headings":"1","show_state_headings":"1","show_country_headings":"1","show_email_form":"1","show_email_copy":"1","banned_email":"","banned_subject":"","banned_text":"","validate_session":"1","custom_reply":"0","redirect":"","show_category_crumb":"0","metakey":"","metadesc":"","robots":"","author":"","rights":"","xreference":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (9, 'com_cpanel', 'component', 'com_cpanel', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (10, 'com_installer', 'component', 'com_installer', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (11, 'com_languages', 'component', 'com_languages', '', 1, 1, 1, 1, '', '{"administrator":"en-GB","site":"en-GB"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (12, 'com_login', 'component', 'com_login', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (13, 'com_media', 'component', 'com_media', '', 1, 1, 0, 1, '', '{"upload_extensions":"bmp,csv,doc,gif,ico,jpg,jpeg,odg,odp,ods,odt,pdf,png,ppt,swf,txt,xcf,xls,BMP,CSV,DOC,GIF,ICO,JPG,JPEG,ODG,ODP,ODS,ODT,PDF,PNG,PPT,SWF,TXT,XCF,XLS","upload_maxsize":"10","file_path":"images","image_path":"images","restrict_uploads":"1","allowed_media_usergroup":"3","check_mime":"1","image_extensions":"bmp,gif,jpg,png","ignore_extensions":"","upload_mime":"image\\/jpeg,image\\/gif,image\\/png,image\\/bmp,application\\/x-shockwave-flash,application\\/msword,application\\/excel,application\\/pdf,application\\/powerpoint,text\\/plain,application\\/x-zip","upload_mime_illegal":"text\\/html","enable_flash":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (14, 'com_menus', 'component', 'com_menus', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (15, 'com_messages', 'component', 'com_messages', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (16, 'com_modules', 'component', 'com_modules', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (17, 'com_newsfeeds', 'component', 'com_newsfeeds', '', 1, 1, 1, 0, '', '{"show_feed_image":"1","show_feed_description":"1","show_item_description":"1","feed_word_count":"0","show_headings":"1","show_name":"1","show_articles":"0","show_link":"1","show_description":"1","show_description_image":"1","display_num":"","show_pagination_limit":"1","show_pagination":"1","show_pagination_results":"1","show_cat_items":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (18, 'com_plugins', 'component', 'com_plugins', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (19, 'com_search', 'component', 'com_search', '', 1, 1, 1, 1, '', '{"enabled":"0","show_date":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (20, 'com_templates', 'component', 'com_templates', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (21, 'com_weblinks', 'component', 'com_weblinks', '', 1, 1, 1, 0, '', '{"show_comp_description":"1","comp_description":"","show_link_hits":"1","show_link_description":"1","show_other_cats":"0","show_headings":"0","show_numbers":"0","show_report":"1","count_clicks":"1","target":"0","link_icons":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (22, 'com_content', 'component', 'com_content', '', 1, 1, 0, 1, '', '{"article_layout":"_:default","show_title":"1","link_titles":"1","show_intro":"1","show_category":"1","link_category":"1","show_parent_category":"0","link_parent_category":"0","show_author":"1","link_author":"0","show_create_date":"0","show_modify_date":"0","show_publish_date":"1","show_item_navigation":"1","show_vote":"0","show_readmore":"1","show_readmore_title":"1","readmore_limit":"100","show_icons":"1","show_print_icon":"1","show_email_icon":"1","show_hits":"1","show_noauth":"0","category_layout":"_:blog","show_category_title":"0","show_description":"0","show_description_image":"0","maxLevel":"1","show_empty_categories":"0","show_no_articles":"1","show_subcat_desc":"1","show_cat_num_articles":"0","show_base_description":"1","maxLevelcat":"-1","show_empty_categories_cat":"0","show_subcat_desc_cat":"1","show_cat_num_articles_cat":"1","num_leading_articles":"1","num_intro_articles":"4","num_columns":"2","num_links":"4","multi_column_order":"0","orderby_pri":"order","orderby_sec":"rdate","order_date":"published","show_pagination_limit":"1","filter_field":"hide","show_headings":"1","list_show_date":"0","date_format":"","list_show_hits":"1","list_show_author":"1","show_pagination":"2","show_pagination_results":"1","show_feed_link":"1","feed_summary":"0","filters":{"1":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"6":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"7":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"2":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"3":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"4":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"5":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"10":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"12":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"8":{"filter_type":"BL","filter_tags":"","filter_attributes":""}}}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (23, 'com_config', 'component', 'com_config', '', 1, 1, 0, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (24, 'com_redirect', 'component', 'com_redirect', '', 1, 1, 0, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (25, 'com_users', 'component', 'com_users', '', 1, 1, 0, 1, '', '{"allowUserRegistration":"1","new_usertype":"2","useractivation":"1","frontend_userparams":"1","mailSubjectPrefix":"","mailBodySuffix":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (100, 'PHPMailer', 'library', 'phpmailer', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (101, 'SimplePie', 'library', 'simplepie', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (102, 'phputf8', 'library', 'phputf8', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (103, 'Joomla! Web Application Framework', 'library', 'joomla', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:33:"Joomla! Web Application Framework";s:4:"type";s:7:"library";s:12:"creationDate";s:4:"2008";s:6:"author";s:6:"Joomla";s:9:"copyright";s:67:"Copyright (C) 2005 - 2011 Open Source Matters. All rights reserved.";s:11:"authorEmail";s:16:"admin@joomla.org";s:9:"authorUrl";s:21:"http://www.joomla.org";s:7:"version";s:5:"1.6.0";s:11:"description";s:90:"The Joomla! Web Application Framework is the Core of the Joomla! Content Management System";s:5:"group";s:0:"";}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (200, 'mod_articles_archive', 'module', 'mod_articles_archive', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (201, 'mod_articles_latest', 'module', 'mod_articles_latest', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (202, 'mod_articles_popular', 'module', 'mod_articles_popular', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (203, 'mod_banners', 'module', 'mod_banners', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (204, 'mod_breadcrumbs', 'module', 'mod_breadcrumbs', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (205, 'mod_custom', 'module', 'mod_custom', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (206, 'mod_feed', 'module', 'mod_feed', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (207, 'mod_footer', 'module', 'mod_footer', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (208, 'mod_login', 'module', 'mod_login', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (209, 'mod_menu', 'module', 'mod_menu', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (210, 'mod_articles_news', 'module', 'mod_articles_news', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (211, 'mod_random_image', 'module', 'mod_random_image', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (212, 'mod_related_items', 'module', 'mod_related_items', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (213, 'mod_search', 'module', 'mod_search', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (214, 'mod_stats', 'module', 'mod_stats', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (215, 'mod_syndicate', 'module', 'mod_syndicate', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (216, 'mod_users_latest', 'module', 'mod_users_latest', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (217, 'mod_weblinks', 'module', 'mod_weblinks', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (218, 'mod_whosonline', 'module', 'mod_whosonline', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (219, 'mod_wrapper', 'module', 'mod_wrapper', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (220, 'mod_articles_category', 'module', 'mod_articles_category', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (221, 'mod_articles_categories', 'module', 'mod_articles_categories', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (222, 'mod_languages', 'module', 'mod_languages', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (300, 'mod_custom', 'module', 'mod_custom', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (301, 'mod_feed', 'module', 'mod_feed', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (302, 'mod_latest', 'module', 'mod_latest', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (303, 'mod_logged', 'module', 'mod_logged', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (304, 'mod_login', 'module', 'mod_login', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (305, 'mod_menu', 'module', 'mod_menu', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (306, 'mod_online', 'module', 'mod_online', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (307, 'mod_popular', 'module', 'mod_popular', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (308, 'mod_quickicon', 'module', 'mod_quickicon', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (309, 'mod_status', 'module', 'mod_status', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (310, 'mod_submenu', 'module', 'mod_submenu', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (311, 'mod_title', 'module', 'mod_title', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (312, 'mod_toolbar', 'module', 'mod_toolbar', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (313, 'mod_unread', 'module', 'mod_unread', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (400, 'plg_authentication_gmail', 'plugin', 'gmail', 'authentication', 0, 0, 1, 0, '', '{"applysuffix":"0","suffix":"","verifypeer":"1","user_blacklist":""}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `jos_extensions` VALUES (401, 'plg_authentication_joomla', 'plugin', 'joomla', 'authentication', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (402, 'plg_authentication_ldap', 'plugin', 'ldap', 'authentication', 0, 0, 1, 0, '', '{"host":"","port":"389","use_ldapV3":"0","negotiate_tls":"0","no_referrals":"0","auth_method":"bind","base_dn":"","search_string":"","users_dn":"","username":"admin","password":"bobby7","ldap_fullname":"fullName","ldap_email":"mail","ldap_uid":"uid"}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `jos_extensions` VALUES (404, 'plg_content_emailcloak', 'plugin', 'emailcloak', 'content', 0, 1, 1, 0, '', '{"mode":"1"}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `jos_extensions` VALUES (405, 'plg_content_geshi', 'plugin', 'geshi', 'content', 0, 0, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `jos_extensions` VALUES (406, 'plg_content_loadmodule', 'plugin', 'loadmodule', 'content', 0, 1, 1, 0, '', '{"style":"none"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (407, 'plg_content_pagebreak', 'plugin', 'pagebreak', 'content', 0, 1, 1, 1, '', '{"title":"1","multipage_toc":"1","showall":"1"}', '', '', 0, '0000-00-00 00:00:00', 4, 0);
INSERT INTO `jos_extensions` VALUES (408, 'plg_content_pagenavigation', 'plugin', 'pagenavigation', 'content', 0, 1, 1, 1, '', '{"position":"1"}', '', '', 0, '0000-00-00 00:00:00', 5, 0);
INSERT INTO `jos_extensions` VALUES (409, 'plg_content_vote', 'plugin', 'vote', 'content', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 6, 0);
INSERT INTO `jos_extensions` VALUES (410, 'plg_editors_codemirror', 'plugin', 'codemirror', 'editors', 0, 1, 1, 1, '', '{"linenumbers":"0","tabmode":"indent"}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `jos_extensions` VALUES (411, 'plg_editors_none', 'plugin', 'none', 'editors', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `jos_extensions` VALUES (412, 'plg_editors_tinymce', 'plugin', 'tinymce', 'editors', 0, 1, 1, 1, '', '{"mode":"1","skin":"0","compressed":"0","cleanup_startup":"0","cleanup_save":"2","entity_encoding":"raw","lang_mode":"0","lang_code":"en","text_direction":"ltr","content_css":"1","content_css_custom":"","relative_urls":"1","newlines":"0","invalid_elements":"script,applet,iframe","extended_elements":"","toolbar":"top","toolbar_align":"left","html_height":"550","html_width":"750","element_path":"1","fonts":"1","paste":"1","searchreplace":"1","insertdate":"1","format_date":"%Y-%m-%d","inserttime":"1","format_time":"%H:%M:%S","colors":"1","table":"1","smilies":"1","media":"1","hr":"1","directionality":"1","fullscreen":"1","style":"1","layer":"1","xhtmlxtras":"1","visualchars":"1","nonbreaking":"1","template":"1","blockquote":"1","wordcount":"1","advimage":"1","advlink":"1","autosave":"1","contextmenu":"1","inlinepopups":"1","safari":"0","custom_plugin":"","custom_button":""}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `jos_extensions` VALUES (413, 'plg_editors-xtd_article', 'plugin', 'article', 'editors-xtd', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `jos_extensions` VALUES (414, 'plg_editors-xtd_image', 'plugin', 'image', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `jos_extensions` VALUES (415, 'plg_editors-xtd_pagebreak', 'plugin', 'pagebreak', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `jos_extensions` VALUES (416, 'plg_editors-xtd_readmore', 'plugin', 'readmore', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 4, 0);
INSERT INTO `jos_extensions` VALUES (417, 'plg_search_categories', 'plugin', 'categories', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (418, 'plg_search_contacts', 'plugin', 'contacts', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (419, 'plg_search_content', 'plugin', 'content', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (420, 'plg_search_newsfeeds', 'plugin', 'newsfeeds', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (421, 'plg_search_weblinks', 'plugin', 'weblinks', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (422, 'plg_system_languagefilter', 'plugin', 'languagefilter', 'system', 0, 0, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `jos_extensions` VALUES (423, 'plg_system_p3p', 'plugin', 'p3p', 'system', 0, 1, 1, 1, '', '{"headers":"NOI ADM DEV PSAi COM NAV OUR OTRo STP IND DEM"}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `jos_extensions` VALUES (424, 'plg_system_cache', 'plugin', 'cache', 'system', 0, 0, 1, 1, '', '{"browsercache":"0","cachetime":"15"}', '', '', 0, '0000-00-00 00:00:00', 3, 0);
INSERT INTO `jos_extensions` VALUES (425, 'plg_system_debug', 'plugin', 'debug', 'system', 0, 1, 1, 0, '', '{"profile":"1","queries":"1","memory":"1","language_files":"1","language_strings":"1","strip-first":"1","strip-prefix":"","strip-suffix":""}', '', '', 0, '0000-00-00 00:00:00', 4, 0);
INSERT INTO `jos_extensions` VALUES (426, 'plg_system_log', 'plugin', 'log', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 5, 0);
INSERT INTO `jos_extensions` VALUES (427, 'plg_system_redirect', 'plugin', 'redirect', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 6, 0);
INSERT INTO `jos_extensions` VALUES (428, 'plg_system_remember', 'plugin', 'remember', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 7, 0);
INSERT INTO `jos_extensions` VALUES (429, 'plg_system_sef', 'plugin', 'sef', 'system', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 8, 0);
INSERT INTO `jos_extensions` VALUES (430, 'plg_system_logout', 'plugin', 'logout', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 9, 0);
INSERT INTO `jos_extensions` VALUES (431, 'plg_user_contactcreator', 'plugin', 'contactcreator', 'user', 0, 0, 1, 1, '', '{"autowebpage":"","category":"34","autopublish":"0"}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `jos_extensions` VALUES (432, 'plg_user_joomla', 'plugin', 'joomla', 'user', 0, 1, 1, 0, '', '{"autoregister":"1"}', '', '', 0, '0000-00-00 00:00:00', 2, 0);
INSERT INTO `jos_extensions` VALUES (433, 'plg_user_profile', 'plugin', 'profile', 'user', 0, 0, 1, 1, '', '{"register-require_address1":"1","register-require_address2":"1","register-require_city":"1","register-require_region":"1","register-require_country":"1","register-require_postal_code":"1","register-require_phone":"1","register-require_website":"1","register-require_favoritebook":"1","register-require_aboutme":"1","register-require_tos":"1","register-require_dob":"1","profile-require_address1":"1","profile-require_address2":"1","profile-require_city":"1","profile-require_region":"1","profile-require_country":"1","profile-require_postal_code":"1","profile-require_phone":"1","profile-require_website":"1","profile-require_favoritebook":"1","profile-require_aboutme":"1","profile-require_tos":"1","profile-require_dob":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (434, 'plg_extension_joomla', 'plugin', 'joomla', 'extension', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0);
INSERT INTO `jos_extensions` VALUES (435, 'plg_content_joomla', 'plugin', 'joomla', 'content', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (500, 'atomic', 'template', 'atomic', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:6:"atomic";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"10/10/09";s:6:"author";s:12:"Ron Severdia";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"contact@kontentdesign.com";s:9:"authorUrl";s:28:"http://www.kontentdesign.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:26:"TPL_ATOMIC_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (502, 'bluestork', 'template', 'bluestork', '', 1, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:9:"bluestork";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"07/02/09";s:6:"author";s:12:"Ron Severdia";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"contact@kontentdesign.com";s:9:"authorUrl";s:28:"http://www.kontentdesign.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:29:"TPL_BLUESTORK_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"useRoundedCorners":"1","showSiteName":"0","textBig":"0","highContrast":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (503, 'beez_20', 'template', 'beez_20', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:7:"beez_20";s:4:"type";s:8:"template";s:12:"creationDate";s:16:"25 November 2009";s:6:"author";s:12:"Angie Radtke";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:23:"a.radtke@derauftritt.de";s:9:"authorUrl";s:26:"http://www.der-auftritt.de";s:7:"version";s:5:"1.6.0";s:11:"description";s:25:"TPL_BEEZ2_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"wrapperSmall":"53","wrapperLarge":"72","sitetitle":"","sitedescription":"","navposition":"center","templatecolor":"nature"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (504, 'hathor', 'template', 'hathor', '', 1, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:6:"hathor";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"May 2010";s:6:"author";s:11:"Andrea Tarr";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"hathor@tarrconsulting.com";s:9:"authorUrl";s:29:"http://www.tarrconsulting.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:26:"TPL_HATHOR_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"showSiteName":"0","colourChoice":"0","boldText":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (505, 'beez5', 'template', 'beez5', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:5:"beez5";s:4:"type";s:8:"template";s:12:"creationDate";s:11:"21 May 2010";s:6:"author";s:12:"Angie Radtke";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:23:"a.radtke@derauftritt.de";s:9:"authorUrl";s:26:"http://www.der-auftritt.de";s:7:"version";s:5:"1.6.0";s:11:"description";s:25:"TPL_BEEZ5_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"wrapperSmall":"53","wrapperLarge":"72","sitetitle":"","sitedescription":"","navposition":"center","html5":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (600, 'English (United Kingdom)', 'language', 'en-GB', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (601, 'English (United Kingdom)', 'language', 'en-GB', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (700, 'Joomla! CMS', 'file', 'joomla', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (10000, 'Yougrids', 'template', 'yougrids', '', 0, 1, 1, 0, '{"legacy":true,"name":"Yougrids","type":"template","creationDate":"10-01-2010","author":"Youjoomla LLC","copyright":"Youjoomla LLC","authorEmail":"youjoomlallc@gmail.com","authorUrl":"www.youjoomla.com","version":"1.0","description":"\\n\\t\\t<div id=\\"temp_desc\\"><h1 class=\\"admin_t_name\\">YouGrids Joomla! Template<\\/h1>\\n\\t\\t\\t<img src=\\"..\\/templates\\/yougrids\\/template_thumbnail.png\\" \\/><br \\/><br \\/>\\n\\t\\t\\t<h1 class=\\"admin_t_name\\">is proudly powered by<\\/h1>\\n\\t\\t\\t<a href=\\"http:\\/\\/www.yjsimplegrid.com\\" class=\\"modal\\" rel=\\"{handler: ''iframe'', size: {x: 800, y: 700}}\\">\\n\\t\\t\\t<span title=\\"YJSimpleGrid Joomla! Template Framework by Youjoomla.com\\">\\n\\t\\t\\t<img src=\\"..\\/templates\\/yougrids\\/images\\/admin\\/yjsgadmin_logo.png\\" border=\\"0\\" title=\\"\\" alt=\\"\\"\\/>\\n\\t\\t\\t<\\/span>\\n\\t\\t\\t<\\/a>\\n\\t\\t\\t\\n\\t\\t\\t<div id=\\"temp_det\\">      \\n\\t\\t\\t<h3>Version 1.0.10 Initial Release<\\/h3>\\n\\t\\t\\t\\n\\t\\t\\t<p>YouGrids Joomla  Template by Youjoomla.com. This is Free Template by Youjoomla.com. Visit <a href=\\"http:\\/\\/www.youjoomla.com\\">Youjoomla \\t\\t\\tTemlplates Club<\\/a> home page.\\n\\t\\t\\t<\\/p>\\n\\t\\t\\t<h3>Some of the YJSimpleGrid Template Framework Features are:<\\/h3>\\n\\t\\t\\t<ul>\\n\\t\\t\\t<li>Advanced Joomla Template Manager Admin Panel<\\/li>\\n\\t\\t\\t<li>Flexible Layouts<\\/li>\\n\\t\\t\\t<li>Over 52 collapsible module positions easily add more<\\/li>\\n\\t\\t\\t<li>Automatic module width adjustment in template manager<\\/li>\\n\\t\\t\\t<li>Native RTL<\\/li>\\n\\t\\t\\t<li>Native Mobile devices support<\\/li>\\n\\t\\t\\t<li>Custom iPhone\\/iPad\\/iPod and handheld devices template<\\/li>\\n\\t\\t\\t<li>Cross browser support<\\/li>\\n\\t\\t\\t<li>Native Cufon and Google Font plus 15 CSS font styles<\\/li>\\n\\t\\t\\t<li>Native Jquery Support<\\/li>\\n\\t\\t\\t<li>Google Analytics website tracking built in<\\/li>\\n\\t\\t\\t<li>Native rounded and square module styles 1 click switch<\\/li>\\n\\t\\t\\t<li>1 image PNG transparent rounded corners approach for easy editing<\\/li>\\n\\t\\t\\t<li>Component off switch or menu item assignment<\\/li>\\n\\t\\t\\t<li>Topmenu off switch or menu item assignment<\\/li>\\n\\t\\t\\t<li>7 default module grids with 5 modules each. Easily add more<\\/li>\\n\\t\\t\\t<li>Flexible logo and header size changer<\\/li>\\n\\t\\t\\t<li>Upgraded server side compression<\\/li>\\n\\t\\t\\t<li>Frontpage news items number of  characters control<\\/li>\\n\\t\\t\\t<li>Visible RTL switch and Font resizer<\\/li>\\n\\t\\t\\t<li>5 menu styles<\\/li>\\n\\t\\t\\t<li>Show page title under menu link<\\/li>\\n\\t\\t\\t<li>Custom error and offline pages<\\/li>\\n\\t\\t\\t<li>Custom user pages ( com_user )<\\/li>\\n\\t\\t\\t<li>Slide to top smothscroll<\\/li>\\n\\t\\t\\t<li>XHTML , CSS and JS valid<\\/li>\\n\\t\\t\\t<\\/ul>\\n\\t\\t\\t<h3>For additional documentation please visit <a href=\\"http:\\/\\/www.yjsimplegrid.com\\" target=\\"_blank\\">YJSimpleGrid<\\/a><\\/h3>\\n\\t\\t\\t\\n\\t\\t\\t<\\/div>\\n\\t\\t\\t<\\/div>\\n\\t","group":""}', '{"":"TOP_MENU_OFF_YJ_LABEL","STYLE_SETTINGS_TAB":"STYLE_SETTINGS_TAB","custom_css":"2","STTEXT_LABEL":"STTEXT_LABEL","STYLE_SUB":"STYLE_SUB","default_color":"metal","default_font":"3","default_font_family":"6","selectors_override":"2","selectors_override_type":"1","css_font_family":"12","google_font_family":"2","cufon_font_family":"1","affected_selectors":"div.title h1,div.title h2,div.componentheading, h1,h2,h3,h4,h5,h6,.yjround h4,.yjsquare h4","LOGO_SUB":"LOGO_SUB","LGTEXT_LABEL":"LGTEXT_LABEL","LOGO_YJ_TITLE":"LOGO_YJ_TITLE","logo_height":"125px","logo_width":"300px","turn_logo_off":"2","turn_header_block_off":"2","TOP_MENU_SUB":"TOP_MENU_SUB","TMTEXT_LABEL":"TMTEXT_LABEL","TOP_MENU_YJ_LABEL":"TOP_MENU_YJ_LABEL","menuName":"mainmenu","default_menu_style":"2","sub_width":"280px","yjsg_menu_offset":"95","turn_topmenu_off":"","DEF_GRID_SUB":"DEF_GRID_SUB","DGTEXT_LABEL":"DGTEXT_LABEL","MAINB_YJ_LABEL":"MAINB_YJ_LABEL","css_widthdefined":"px","css_width":"1000","site_layout":"2","MBC_W_LABEL":"MBC_W_LABEL","widthdefined":"%","maincolumn":"55","insetcolumn":"0","leftcolumn":"22.5","rightcolumn":"22.5","SPII_LABEL":"SPII_LABEL","widthdefined_itmid":"%","maincolumn_itmid":"55","insetcolumn_itmid":"0","leftcolumn_itmid":"22.5","rightcolumn_itmid":"22.5","define_itemid":"","MG_SUB":"MG_SUB","MGTEXT_LABEL":"MGTEXT_LABEL","yjsg_1_width":"20|20|20|20|20","yjsg_header_width":"33|33|33","yjsg_2_width":"20|20|20|20|20","yjsg_3_width":"20|20|20|20|20","yjsg_4_width":"20|20|20|20|20","yjsg_bodytop_width":"33|33|33","yjsg_yjsgbodytbottom_width":"33|33|33","yjsg_5_width":"20|20|20|20|20","yjsg_6_width":"20|20|20|20|20","yjsg_7_width":"20|20|20|20|20","MOBILE_SUB":"MOBILE_SUB","MOBILE_TXT_LABEL":"MOBILE_TXT_LABEL","iphone_default":"1","android_default":"1","handheld_default":"2","mobile_reg":"1","ADD_F_SUB":"ADD_F_SUB","ADTEXT_LABEL":"ADTEXT_LABEL","GA_YJ_LABEL":"GA_YJ_LABEL","GATEXT_LABEL":"GATEXT_LABEL","ga_switch":"0","GAcode":"UA-xxxxxx-x","NOT_YJ_LABEL":"NOT_YJ_LABEL","compress":"0","ie6notice":"0","nonscript":"0","ST_YJ_LABEL":"ST_YJ_LABEL","show_tools":"1","show_fres":"1","show_rtlc":"1","TDIR_YJ_LABEL":"TDIR_YJ_LABEL","text_direction":"2","SEO_YJ_LABEL":"SEO_YJ_LABEL","turn_seo_off":"2","seo":"Your description goes here","tags":"Your keywords go here","COPY_YJ_LABEL":"COPY_YJ_LABEL","branding_off":"2","joomlacredit_off":"2","ADV_SUB":"ADV_SUB","ADVTEXT_LABEL":"ADVTEXT_LABEL","FPC_YJ_LABEL":"FPC_YJ_LABEL","fp_controll_switch":"2","fp_chars_limit":"3000","fp_after_text":"","SCRIPT_YJ_LABEL":"SCRIPT_YJ_LABEL","JQ_LABEL":"JQ_LABEL","jq_switch":"2","SMS_YJ_LABEL":"SMS_YJ_LABEL","MSTEXT_LABEL":"MSTEXT_LABEL","YJsg1_module_style":"YJsgxhtml","YJsgh_module_style":"YJsgxhtml","YJsg2_module_style":"YJsgxhtml","YJsg3_module_style":"YJsgxhtml","YJsg4_module_style":"YJsgxhtml","YJsgmt_module_style":"YJsgxhtml","YJsgl_module_style":"YJsgxhtml","YJsgr_module_style":"YJsgxhtml","YJsgi_module_style":"YJsgxhtml","YJsgit_module_style":"YJsgxhtml","YJsgib_module_style":"YJsgxhtml","YJsgmb_module_style":"YJsgxhtml","YJsg5_module_style":"YJsgxhtml","YJsg6_module_style":"YJsgxhtml","YJsg7_module_style":"YJsgxhtml","CP_LABEL":"CP_LABEL","component_switch":"","admin_css_time":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (10001, 'System - YJMegaMenu', 'plugin', 'YJMegaMenu', 'system', 0, 0, 1, 0, '{"legacy":false,"name":"System - YJMegaMenu","type":"plugin","creationDate":"January 2011","author":"YouJoomla LLC","copyright":"Copyright (C) 2007 - 2011 Youjoomla LLC. All rights reserved.","authorEmail":"youjoomlallc@gmail.com","authorUrl":"www.www.youjoomla.com","version":"1.6","description":"Provides YJMegaMenu Parameters Tab in MainMenu Administration!","group":""}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (10002, 'jtaghelpdesk', 'component', 'com_jtaghelpdesk', '', 0, 1, 0, 0, '{"legacy":true,"name":"JTag Helpdesk","type":"component","creationDate":"06\\/08\\/2009","author":"Andrey Kvasnevskiy, Aleksey Pakholkov, M.Salman","copyright":"This component is released under the GNU\\/GPL License","authorEmail":"helpdesk@joomlatag.com","authorUrl":"www.joomlatag.com","version":"1.0.11 ","description":"Joomlatag Helpdesk Ticketing System Free","group":""}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);
INSERT INTO `jos_extensions` VALUES (10003, 'huruhelpdesk', 'component', 'com_huruhelpdesk', '', 0, 1, 0, 0, '{"legacy":true,"name":"Huru Helpdesk","type":"component","creationDate":"October 2010","author":"James R. Erickson","copyright":"James R. Erickson, 2010","authorEmail":"james.r.erickson@gmail.com","authorUrl":"","version":"0.88 (d) beta","description":"\\n\\t\\t\\n\\t\\t\\t<p><b>Huru Helpdesk<\\/b><\\/p>\\n\\t\\t\\t\\n\\t\\t\\t<p>Version 0.88 (d) beta<\\/p>\\n\\n\\t\\t\\t<p>Huru Helpdesk is based on the ASP application Liberum Helpdesk (<a href=\\"http:\\/\\/www.liberum.org\\">www.liberum.org<\\/a>). \\n\\t\\t\\tMany thanks to the developers of Liberum<\\/p>\\n\\t\\t\\n\\t","group":""}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_announcement`
-- 

CREATE TABLE `jos_fb_announcement` (
  `id` int(3) NOT NULL auto_increment,
  `title` tinytext NOT NULL,
  `sdescription` text NOT NULL,
  `description` text NOT NULL,
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `published` tinyint(1) NOT NULL default '0',
  `ordering` tinyint(4) NOT NULL default '0',
  `showdate` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_fb_announcement`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_attachments`
-- 

CREATE TABLE `jos_fb_attachments` (
  `mesid` int(11) NOT NULL default '0',
  `filelocation` text NOT NULL,
  KEY `mesid` (`mesid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_attachments`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_categories`
-- 

CREATE TABLE `jos_fb_categories` (
  `id` int(11) NOT NULL auto_increment,
  `parent` int(11) default '0',
  `name` tinytext,
  `cat_emoticon` tinyint(4) NOT NULL default '0',
  `locked` tinyint(4) NOT NULL default '0',
  `alert_admin` tinyint(4) NOT NULL default '0',
  `moderated` tinyint(4) NOT NULL default '1',
  `moderators` varchar(15) default NULL,
  `pub_access` tinyint(4) default '1',
  `pub_recurse` tinyint(4) default '1',
  `admin_access` tinyint(4) default '0',
  `admin_recurse` tinyint(4) default '1',
  `ordering` tinyint(4) NOT NULL default '0',
  `future2` int(11) default '0',
  `published` tinyint(4) NOT NULL default '0',
  `checked_out` tinyint(4) NOT NULL default '0',
  `checked_out_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `review` tinyint(4) NOT NULL default '0',
  `allow_anonymous` tinyint(4) NOT NULL default '0',
  `post_anonymous` tinyint(4) NOT NULL default '0',
  `hits` int(11) NOT NULL default '0',
  `description` text NOT NULL,
  `headerdesc` text NOT NULL,
  `class_sfx` varchar(20) NOT NULL,
  `id_last_msg` int(10) NOT NULL default '0',
  `numTopics` mediumint(8) NOT NULL default '0',
  `numPosts` mediumint(8) NOT NULL default '0',
  `time_last_msg` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `parent` (`parent`),
  KEY `published_pubaccess_id` (`published`,`pub_access`,`id`),
  KEY `msg_id` (`id_last_msg`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

-- 
-- Dumping data for table `jos_fb_categories`
-- 

INSERT INTO `jos_fb_categories` VALUES (1, 0, 'Main Forum', 0, 0, 0, 1, NULL, 0, 0, 0, 0, 1, 0, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'This is the main forum category. As a level one category it serves as a container for individual boards or forums. It is also referred to as a level 1 category and is a must have for any Kunena Forum setup.', 'In order to provide additional information for you guests and members, the forum header can be leveraged to display text at the very top of a particular category.', '', 3, 2, 1, 1304198069);
INSERT INTO `jos_fb_categories` VALUES (4, 1, 'General', 0, 0, 0, 0, NULL, 0, 0, 0, 0, 2, 0, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'Forum to discuss any topic', '', '', 3, 1, 1, 1304198069);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_config`
-- 

CREATE TABLE `jos_fb_config` (
  `id` int(11) NOT NULL default '0',
  `board_title` text,
  `email` text,
  `board_offline` int(11) default NULL,
  `board_ofset` text,
  `offline_message` text,
  `default_view` text,
  `enablerss` int(11) default NULL,
  `enablepdf` int(11) default NULL,
  `threads_per_page` int(11) default NULL,
  `messages_per_page` int(11) default NULL,
  `messages_per_page_search` int(11) default NULL,
  `showhistory` int(11) default NULL,
  `historylimit` int(11) default NULL,
  `shownew` int(11) default NULL,
  `newchar` text,
  `jmambot` int(11) default NULL,
  `disemoticons` int(11) default NULL,
  `template` text,
  `templateimagepath` text,
  `joomlastyle` int(11) default NULL,
  `showannouncement` int(11) default NULL,
  `avataroncat` int(11) default NULL,
  `catimagepath` text,
  `numchildcolumn` int(11) default NULL,
  `showchildcaticon` int(11) default NULL,
  `annmodid` text,
  `rtewidth` int(11) default NULL,
  `rteheight` int(11) default NULL,
  `enablerulespage` int(11) default NULL,
  `enableforumjump` int(11) default NULL,
  `reportmsg` int(11) default NULL,
  `username` int(11) default NULL,
  `askemail` int(11) default NULL,
  `showemail` int(11) default NULL,
  `showuserstats` int(11) default NULL,
  `poststats` int(11) default NULL,
  `statscolor` int(11) default NULL,
  `showkarma` int(11) default NULL,
  `useredit` int(11) default NULL,
  `useredittime` int(11) default NULL,
  `useredittimegrace` int(11) default NULL,
  `editmarkup` int(11) default NULL,
  `allowsubscriptions` int(11) default NULL,
  `subscriptionschecked` int(11) default NULL,
  `allowfavorites` int(11) default NULL,
  `wrap` int(11) default NULL,
  `maxsubject` int(11) default NULL,
  `maxsig` int(11) default NULL,
  `regonly` int(11) default NULL,
  `changename` int(11) default NULL,
  `pubwrite` int(11) default NULL,
  `floodprotection` int(11) default NULL,
  `mailmod` int(11) default NULL,
  `mailadmin` int(11) default NULL,
  `captcha` int(11) default NULL,
  `mailfull` int(11) default NULL,
  `allowavatar` int(11) default NULL,
  `allowavatarupload` int(11) default NULL,
  `allowavatargallery` int(11) default NULL,
  `imageprocessor` text,
  `avatarsmallheight` int(11) default NULL,
  `avatarsmallwidth` int(11) default NULL,
  `avatarheight` int(11) default NULL,
  `avatarwidth` int(11) default NULL,
  `avatarlargeheight` int(11) default NULL,
  `avatarlargewidth` int(11) default NULL,
  `avatarquality` int(11) default NULL,
  `avatarsize` int(11) default NULL,
  `allowimageupload` int(11) default NULL,
  `allowimageregupload` int(11) default NULL,
  `imageheight` int(11) default NULL,
  `imagewidth` int(11) default NULL,
  `imagesize` int(11) default NULL,
  `allowfileupload` int(11) default NULL,
  `allowfileregupload` int(11) default NULL,
  `filetypes` text,
  `filesize` int(11) default NULL,
  `showranking` int(11) default NULL,
  `rankimages` int(11) default NULL,
  `avatar_src` text,
  `fb_profile` text,
  `pm_component` text,
  `cb_profile` int(11) default NULL,
  `discussbot` int(11) default NULL,
  `userlist_rows` int(11) default NULL,
  `userlist_online` int(11) default NULL,
  `userlist_avatar` int(11) default NULL,
  `userlist_name` int(11) default NULL,
  `userlist_username` int(11) default NULL,
  `userlist_posts` int(11) default NULL,
  `userlist_karma` int(11) default NULL,
  `userlist_email` int(11) default NULL,
  `userlist_usertype` int(11) default NULL,
  `userlist_joindate` int(11) default NULL,
  `userlist_lastvisitdate` int(11) default NULL,
  `userlist_userhits` int(11) default NULL,
  `showlatest` int(11) default NULL,
  `latestcount` int(11) default NULL,
  `latestcountperpage` int(11) default NULL,
  `latestcategory` text,
  `latestsinglesubject` int(11) default NULL,
  `latestreplysubject` int(11) default NULL,
  `latestsubjectlength` int(11) default NULL,
  `latestshowdate` int(11) default NULL,
  `latestshowhits` int(11) default NULL,
  `latestshowauthor` int(11) default NULL,
  `showstats` int(11) default NULL,
  `showwhoisonline` int(11) default NULL,
  `showgenstats` int(11) default NULL,
  `showpopuserstats` int(11) default NULL,
  `popusercount` int(11) default NULL,
  `showpopsubjectstats` int(11) default NULL,
  `popsubjectcount` int(11) default NULL,
  `usernamechange` int(11) default NULL,
  `rules_infb` int(11) default NULL,
  `rules_cid` int(11) default NULL,
  `rules_link` text,
  `enablehelppage` int(11) default NULL,
  `help_infb` int(11) default NULL,
  `help_cid` int(11) default NULL,
  `help_link` text,
  `showspoilertag` int(11) default NULL,
  `showvideotag` int(11) default NULL,
  `showebaytag` int(11) default NULL,
  `trimlongurls` int(11) default NULL,
  `trimlongurlsfront` int(11) default NULL,
  `trimlongurlsback` int(11) default NULL,
  `autoembedyoutube` int(11) default NULL,
  `autoembedebay` int(11) default NULL,
  `ebaylanguagecode` text,
  `fbsessiontimeout` int(11) default NULL,
  `highlightcode` int(11) default NULL,
  `rsstype` text,
  `rsshistory` text,
  `fbdefaultpage` text,
  `default_sort` text,
  `alphauserpoints` int(11) default NULL,
  `alphauserpointsrules` int(11) default NULL,
  `alphauserpointsnumchars` int(11) default NULL,
  `sef` int(11) default NULL,
  `sefcats` int(11) default NULL,
  `sefutf8` int(11) default NULL,
  `hide_ip` int(11) default NULL,
  `js_actstr_integration` int(11) default NULL,
  `userlist_enable` int(11) default NULL,
  `showimgforguest` int(11) default NULL,
  `showfileforguest` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_config`
-- 

INSERT INTO `jos_fb_config` VALUES (1, 'Forum', 'info@netanel.pl', 0, '0', '<h2>The Forum is currently offline for maintenance.</h2>\r\n<div>Check back soon!</div>', 'flat', 1, 1, 20, 10, 15, 1, 10, 1, 'NEW!', 0, 1, 'default_ex', 'default_ex', 1, 0, 0, '', 0, 0, '62', 450, 300, 0, 1, 1, 1, 0, 0, 0, 0, 9, 0, 1, 0, 600, 1, 1, 1, 1, 250, 50, 300, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 'gd2', 50, 50, 100, 100, 250, 250, 65, 2048, 0, 1, 800, 800, 150, 0, 1, 'zip,txt,doc,gz,tgz', 120, 0, 0, 'fb', 'fb', 'no', 0, 0, 30, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 10, 5, '', 1, 1, 400, 1, 1, 1, 0, 0, 0, 0, 5, 0, 5, 0, 0, 1, 'http://www.kunena.com/', 0, 0, 1, 'http://www.kunena.com/', 1, 0, 0, 1, 40, 20, 0, 0, 'en-us', 1800, 1, 'thread', 'month', 'recent', 'asc', 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_config_backup`
-- 

CREATE TABLE `jos_fb_config_backup` (
  `id` int(11) NOT NULL default '0',
  `board_title` text,
  `email` text,
  `board_offline` int(11) default NULL,
  `board_ofset` text,
  `offline_message` text,
  `default_view` text,
  `enablerss` int(11) default NULL,
  `enablepdf` int(11) default NULL,
  `threads_per_page` int(11) default NULL,
  `messages_per_page` int(11) default NULL,
  `messages_per_page_search` int(11) default NULL,
  `showhistory` int(11) default NULL,
  `historylimit` int(11) default NULL,
  `shownew` int(11) default NULL,
  `newchar` text,
  `jmambot` int(11) default NULL,
  `disemoticons` int(11) default NULL,
  `template` text,
  `templateimagepath` text,
  `joomlastyle` int(11) default NULL,
  `showannouncement` int(11) default NULL,
  `avataroncat` int(11) default NULL,
  `catimagepath` text,
  `numchildcolumn` int(11) default NULL,
  `showchildcaticon` int(11) default NULL,
  `annmodid` text,
  `rtewidth` int(11) default NULL,
  `rteheight` int(11) default NULL,
  `enablerulespage` int(11) default NULL,
  `enableforumjump` int(11) default NULL,
  `reportmsg` int(11) default NULL,
  `username` int(11) default NULL,
  `askemail` int(11) default NULL,
  `showemail` int(11) default NULL,
  `showuserstats` int(11) default NULL,
  `poststats` int(11) default NULL,
  `statscolor` int(11) default NULL,
  `showkarma` int(11) default NULL,
  `useredit` int(11) default NULL,
  `useredittime` int(11) default NULL,
  `useredittimegrace` int(11) default NULL,
  `editmarkup` int(11) default NULL,
  `allowsubscriptions` int(11) default NULL,
  `subscriptionschecked` int(11) default NULL,
  `allowfavorites` int(11) default NULL,
  `wrap` int(11) default NULL,
  `maxsubject` int(11) default NULL,
  `maxsig` int(11) default NULL,
  `regonly` int(11) default NULL,
  `changename` int(11) default NULL,
  `pubwrite` int(11) default NULL,
  `floodprotection` int(11) default NULL,
  `mailmod` int(11) default NULL,
  `mailadmin` int(11) default NULL,
  `captcha` int(11) default NULL,
  `mailfull` int(11) default NULL,
  `allowavatar` int(11) default NULL,
  `allowavatarupload` int(11) default NULL,
  `allowavatargallery` int(11) default NULL,
  `imageprocessor` text,
  `avatarsmallheight` int(11) default NULL,
  `avatarsmallwidth` int(11) default NULL,
  `avatarheight` int(11) default NULL,
  `avatarwidth` int(11) default NULL,
  `avatarlargeheight` int(11) default NULL,
  `avatarlargewidth` int(11) default NULL,
  `avatarquality` int(11) default NULL,
  `avatarsize` int(11) default NULL,
  `allowimageupload` int(11) default NULL,
  `allowimageregupload` int(11) default NULL,
  `imageheight` int(11) default NULL,
  `imagewidth` int(11) default NULL,
  `imagesize` int(11) default NULL,
  `allowfileupload` int(11) default NULL,
  `allowfileregupload` int(11) default NULL,
  `filetypes` text,
  `filesize` int(11) default NULL,
  `showranking` int(11) default NULL,
  `rankimages` int(11) default NULL,
  `avatar_src` text,
  `fb_profile` text,
  `pm_component` text,
  `cb_profile` int(11) default NULL,
  `discussbot` int(11) default NULL,
  `userlist_rows` int(11) default NULL,
  `userlist_online` int(11) default NULL,
  `userlist_avatar` int(11) default NULL,
  `userlist_name` int(11) default NULL,
  `userlist_username` int(11) default NULL,
  `userlist_posts` int(11) default NULL,
  `userlist_karma` int(11) default NULL,
  `userlist_email` int(11) default NULL,
  `userlist_usertype` int(11) default NULL,
  `userlist_joindate` int(11) default NULL,
  `userlist_lastvisitdate` int(11) default NULL,
  `userlist_userhits` int(11) default NULL,
  `showlatest` int(11) default NULL,
  `latestcount` int(11) default NULL,
  `latestcountperpage` int(11) default NULL,
  `latestcategory` text,
  `latestsinglesubject` int(11) default NULL,
  `latestreplysubject` int(11) default NULL,
  `latestsubjectlength` int(11) default NULL,
  `latestshowdate` int(11) default NULL,
  `latestshowhits` int(11) default NULL,
  `latestshowauthor` int(11) default NULL,
  `showstats` int(11) default NULL,
  `showwhoisonline` int(11) default NULL,
  `showgenstats` int(11) default NULL,
  `showpopuserstats` int(11) default NULL,
  `popusercount` int(11) default NULL,
  `showpopsubjectstats` int(11) default NULL,
  `popsubjectcount` int(11) default NULL,
  `usernamechange` int(11) default NULL,
  `rules_infb` int(11) default NULL,
  `rules_cid` int(11) default NULL,
  `rules_link` text,
  `enablehelppage` int(11) default NULL,
  `help_infb` int(11) default NULL,
  `help_cid` int(11) default NULL,
  `help_link` text,
  `showspoilertag` int(11) default NULL,
  `showvideotag` int(11) default NULL,
  `showebaytag` int(11) default NULL,
  `trimlongurls` int(11) default NULL,
  `trimlongurlsfront` int(11) default NULL,
  `trimlongurlsback` int(11) default NULL,
  `autoembedyoutube` int(11) default NULL,
  `autoembedebay` int(11) default NULL,
  `ebaylanguagecode` text,
  `fbsessiontimeout` int(11) default NULL,
  `highlightcode` int(11) default NULL,
  `rsstype` text,
  `rsshistory` text,
  `fbdefaultpage` text,
  `default_sort` text,
  `alphauserpoints` int(11) default NULL,
  `alphauserpointsrules` int(11) default NULL,
  `alphauserpointsnumchars` int(11) default NULL,
  `sef` int(11) default NULL,
  `sefcats` int(11) default NULL,
  `sefutf8` int(11) default NULL,
  `hide_ip` int(11) default NULL,
  `js_actstr_integration` int(11) default NULL,
  `userlist_enable` int(11) default NULL,
  `showimgforguest` int(11) default NULL,
  `showfileforguest` int(11) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_config_backup`
-- 

INSERT INTO `jos_fb_config_backup` VALUES (1, 'Forum', 'info@netanel.pl', 0, '0', '<h2>The Forum is currently offline for maintenance.</h2>\r\n<div>Check back soon!</div>', 'flat', 1, 1, 20, 10, 15, 1, 10, 1, 'NEW!', 0, 1, 'default_ex', 'default_ex', 1, 0, 0, '', 0, 0, '62', 450, 300, 0, 1, 1, 1, 0, 0, 0, 0, 9, 0, 1, 0, 600, 1, 1, 1, 1, 250, 50, 300, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 'gd2', 50, 50, 100, 100, 250, 250, 65, 2048, 0, 1, 800, 800, 150, 0, 1, 'zip,txt,doc,gz,tgz', 120, 0, 0, 'fb', 'fb', 'no', 0, 0, 30, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 10, 5, '', 1, 1, 400, 1, 1, 1, 0, 0, 0, 0, 5, 0, 5, 0, 0, 1, 'http://www.kunena.com/', 0, 0, 1, 'http://www.kunena.com/', 1, 0, 0, 1, 40, 20, 0, 0, 'en-us', 1800, 1, 'thread', 'month', 'recent', 'asc', 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_favorites`
-- 

CREATE TABLE `jos_fb_favorites` (
  `thread` int(11) NOT NULL default '0',
  `userid` int(11) NOT NULL default '0',
  UNIQUE KEY `thread` (`thread`,`userid`),
  KEY `userid` (`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_favorites`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_groups`
-- 

CREATE TABLE `jos_fb_groups` (
  `id` int(4) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_fb_groups`
-- 

INSERT INTO `jos_fb_groups` VALUES (1, '"._KUNENA_REGISTERED."');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_messages`
-- 

CREATE TABLE `jos_fb_messages` (
  `id` int(11) NOT NULL auto_increment,
  `parent` int(11) default '0',
  `thread` int(11) default '0',
  `catid` int(11) NOT NULL default '0',
  `name` tinytext,
  `userid` int(11) NOT NULL default '0',
  `email` tinytext,
  `subject` tinytext,
  `time` int(11) NOT NULL default '0',
  `ip` varchar(15) default NULL,
  `topic_emoticon` int(11) NOT NULL default '0',
  `locked` tinyint(4) NOT NULL default '0',
  `hold` tinyint(4) NOT NULL default '0',
  `ordering` int(11) default '0',
  `hits` int(11) default '0',
  `moved` tinyint(4) default '0',
  `modified_by` int(7) default NULL,
  `modified_time` int(11) default NULL,
  `modified_reason` tinytext,
  PRIMARY KEY  (`id`),
  KEY `thread` (`thread`),
  KEY `parent` (`parent`),
  KEY `catid` (`catid`),
  KEY `ip` (`ip`),
  KEY `userid` (`userid`),
  KEY `time` (`time`),
  KEY `locked` (`locked`),
  KEY `hold_time` (`hold`,`time`),
  KEY `parent_hits` (`parent`,`hits`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `jos_fb_messages`
-- 

INSERT INTO `jos_fb_messages` VALUES (2, 0, 2, 4, 'admin', 62, 'jacek.p.kolodziejczyk@gmail.com', 'Welcome', 1302859368, '127.0.0.1', 0, 0, 0, 0, 19, 0, NULL, NULL, NULL);
INSERT INTO `jos_fb_messages` VALUES (3, 2, 2, 4, 'jacek', 63, 'jackolo@poczta.fm', 'Re: Welcome', 1304198069, '127.0.0.1', 0, 0, 0, 0, 0, 0, NULL, NULL, NULL);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_messages_text`
-- 

CREATE TABLE `jos_fb_messages_text` (
  `mesid` int(11) NOT NULL default '0',
  `message` text NOT NULL,
  PRIMARY KEY  (`mesid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_messages_text`
-- 

INSERT INTO `jos_fb_messages_text` VALUES (2, 'Hello, welcome to the forum.');
INSERT INTO `jos_fb_messages_text` VALUES (3, 'This post by my name');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_moderation`
-- 

CREATE TABLE `jos_fb_moderation` (
  `catid` int(11) NOT NULL default '0',
  `userid` int(11) NOT NULL default '0',
  `future1` tinyint(4) default '0',
  `future2` int(11) default '0',
  PRIMARY KEY  (`catid`,`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_moderation`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_ranks`
-- 

CREATE TABLE `jos_fb_ranks` (
  `rank_id` mediumint(8) unsigned NOT NULL auto_increment,
  `rank_title` varchar(255) NOT NULL default '',
  `rank_min` mediumint(8) unsigned NOT NULL default '0',
  `rank_special` tinyint(1) unsigned NOT NULL default '0',
  `rank_image` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`rank_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- 
-- Dumping data for table `jos_fb_ranks`
-- 

INSERT INTO `jos_fb_ranks` VALUES (1, 'Fresh Boarder', 0, 0, 'rank1.gif');
INSERT INTO `jos_fb_ranks` VALUES (2, 'Junior Boarder', 20, 0, 'rank2.gif');
INSERT INTO `jos_fb_ranks` VALUES (3, 'Senior Boarder', 40, 0, 'rank3.gif');
INSERT INTO `jos_fb_ranks` VALUES (4, 'Expert Boarder', 80, 0, 'rank4.gif');
INSERT INTO `jos_fb_ranks` VALUES (5, 'Gold Boarder', 160, 0, 'rank5.gif');
INSERT INTO `jos_fb_ranks` VALUES (6, 'Platinum Boarder', 320, 0, 'rank6.gif');
INSERT INTO `jos_fb_ranks` VALUES (7, 'Administrator', 0, 1, 'rankadmin.gif');
INSERT INTO `jos_fb_ranks` VALUES (8, 'Moderator', 0, 1, 'rankmod.gif');
INSERT INTO `jos_fb_ranks` VALUES (9, 'Spammer', 0, 1, 'rankspammer.gif');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_sessions`
-- 

CREATE TABLE `jos_fb_sessions` (
  `userid` int(11) NOT NULL default '0',
  `allowed` text,
  `lasttime` int(11) NOT NULL default '0',
  `readtopics` text,
  `currvisit` int(11) NOT NULL default '0',
  PRIMARY KEY  (`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_sessions`
-- 

INSERT INTO `jos_fb_sessions` VALUES (62, 'na', 1271322239, '', 1302859387);
INSERT INTO `jos_fb_sessions` VALUES (63, '1,4', 1304202511, '2', 1304438956);
INSERT INTO `jos_fb_sessions` VALUES (66, 'na', 1271323473, '', 1302860600);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_smileys`
-- 

CREATE TABLE `jos_fb_smileys` (
  `id` int(4) NOT NULL auto_increment,
  `code` varchar(12) NOT NULL default '',
  `location` varchar(50) NOT NULL default '',
  `greylocation` varchar(60) NOT NULL default '',
  `emoticonbar` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 AUTO_INCREMENT=38 ;

-- 
-- Dumping data for table `jos_fb_smileys`
-- 

INSERT INTO `jos_fb_smileys` VALUES (1, 'B)', 'cool.png', 'cool-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (2, ':(', 'sad.png', 'sad-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (3, ':)', 'smile.png', 'smile-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (4, ':-)', 'smile.png', 'smile-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (5, ':-(', 'sad.png', 'sad-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (6, ':laugh:', 'laughing.png', 'laughing-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (7, ':cheer:', 'cheerful.png', 'cheerful-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (8, ';)', 'wink.png', 'wink-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (9, ';-)', 'wink.png', 'wink-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (10, ':P', 'tongue.png', 'tongue-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (12, ':X', 'sick.png', 'sick-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (13, ':x', 'sick.png', 'sick-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (14, ':angry:', 'angry.png', 'angry-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (15, ':mad:', 'angry.png', 'angry-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (16, ':unsure:', 'unsure.png', 'unsure-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (17, ':ohmy:', 'shocked.png', 'shocked-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (18, ':huh:', 'wassat.png', 'wassat-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (19, ':dry:', 'ermm.png', 'ermm-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (20, ':ermm:', 'ermm.png', 'ermm-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (21, ':lol:', 'grin.png', 'grin-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (22, ':sick:', 'sick.png', 'sick-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (23, ':silly:', 'silly.png', 'silly-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (24, ':y32b4:', 'silly.png', 'silly-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (25, ':blink:', 'blink.png', 'blink-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (26, ':blush:', 'blush.png', 'blush-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (27, ':kiss:', 'kissing.png', 'kissing-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (28, ':rolleyes:', 'blink.png', 'blink-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (29, ':woohoo:', 'w00t.png', 'w00t-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (30, ':side:', 'sideways.png', 'sideways-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (31, ':S', 'dizzy.png', 'dizzy-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (32, ':s', 'dizzy.png', 'dizzy-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (33, ':evil:', 'devil.png', 'devil-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (34, ':whistle:', 'whistling.png', 'whistling-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (35, ':pinch:', 'pinch.png', 'pinch-grey.png', 1);
INSERT INTO `jos_fb_smileys` VALUES (36, ':p', 'tongue.png', 'tongue-grey.png', 0);
INSERT INTO `jos_fb_smileys` VALUES (37, ':D', 'laughing.png', 'laughing-grey.png', 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_subscriptions`
-- 

CREATE TABLE `jos_fb_subscriptions` (
  `thread` int(11) NOT NULL default '0',
  `userid` int(11) NOT NULL default '0',
  `future1` int(11) default '0',
  UNIQUE KEY `thread` (`thread`,`userid`),
  KEY `userid` (`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_subscriptions`
-- 

INSERT INTO `jos_fb_subscriptions` VALUES (2, 62, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_users`
-- 

CREATE TABLE `jos_fb_users` (
  `userid` int(11) NOT NULL default '0',
  `view` varchar(8) NOT NULL default 'flat',
  `signature` text,
  `moderator` int(11) default '0',
  `ordering` int(11) default '0',
  `posts` int(11) default '0',
  `avatar` varchar(50) default NULL,
  `karma` int(11) default '0',
  `karma_time` int(11) default '0',
  `group_id` int(4) default '1',
  `uhits` int(11) default '0',
  `personalText` tinytext,
  `gender` tinyint(4) NOT NULL default '0',
  `birthdate` date NOT NULL default '0001-01-01',
  `location` varchar(50) default NULL,
  `ICQ` varchar(50) default NULL,
  `AIM` varchar(50) default NULL,
  `YIM` varchar(50) default NULL,
  `MSN` varchar(50) default NULL,
  `SKYPE` varchar(50) default NULL,
  `GTALK` varchar(50) default NULL,
  `websitename` varchar(50) default NULL,
  `websiteurl` varchar(50) default NULL,
  `rank` tinyint(4) NOT NULL default '0',
  `hideEmail` tinyint(1) NOT NULL default '1',
  `showOnline` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`userid`),
  KEY `group_id` (`group_id`),
  KEY `posts` (`posts`),
  KEY `uhits` (`uhits`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_fb_users`
-- 

INSERT INTO `jos_fb_users` VALUES (62, 'flat', NULL, 1, 0, 1, NULL, 0, 0, 1, 2, NULL, 0, '0001-01-01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 1);
INSERT INTO `jos_fb_users` VALUES (66, 'flat', NULL, 0, 0, 0, NULL, 0, 0, 1, 0, NULL, 0, '0001-01-01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 1);
INSERT INTO `jos_fb_users` VALUES (63, 'flat', NULL, 0, 0, 1, NULL, 0, 0, 1, 0, NULL, 0, '0001-01-01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_version`
-- 

CREATE TABLE `jos_fb_version` (
  `id` int(11) NOT NULL auto_increment,
  `version` varchar(20) NOT NULL,
  `versiondate` date NOT NULL,
  `installdate` date NOT NULL,
  `build` varchar(20) NOT NULL,
  `versionname` varchar(40) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_fb_version`
-- 

INSERT INTO `jos_fb_version` VALUES (1, '1.5.13', '2010-11-04', '2011-04-15', '1902', 'Unasema');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_fb_whoisonline`
-- 

CREATE TABLE `jos_fb_whoisonline` (
  `id` int(6) NOT NULL auto_increment,
  `userid` int(7) NOT NULL default '0',
  `time` varchar(14) NOT NULL default '0',
  `item` int(6) default '0',
  `what` varchar(255) default '0',
  `func` varchar(50) default NULL,
  `do` varchar(50) default NULL,
  `task` varchar(50) default NULL,
  `link` text,
  `userip` varchar(20) NOT NULL default '',
  `user` tinyint(2) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `userid` (`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

-- 
-- Dumping data for table `jos_fb_whoisonline`
-- 

INSERT INTO `jos_fb_whoisonline` VALUES (13, 0, '1330251491', 0, 'Forum Main', '', '', '', 'http://netanel.pl/index2.php', '66.103.62.11', 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_groups`
-- 

CREATE TABLE `jos_groups` (
  `id` tinyint(3) unsigned NOT NULL default '0',
  `name` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_groups`
-- 

INSERT INTO `jos_groups` VALUES (0, 'Public');
INSERT INTO `jos_groups` VALUES (1, 'Registered');
INSERT INTO `jos_groups` VALUES (2, 'Special');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_category`
-- 

CREATE TABLE `jos_hd_category` (
  `catid` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL default '',
  `description` varchar(255) default NULL,
  `image` varchar(255) default NULL,
  PRIMARY KEY  (`catid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_hd_category`
-- 

INSERT INTO `jos_hd_category` VALUES (1, 'Default Category', 'If there are no other suitable categories submit your tickets here ;)', NULL);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_groups`
-- 

CREATE TABLE `jos_hd_groups` (
  `grpid` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL default '',
  `image` varchar(255) default NULL,
  `userpermissions` varchar(4) NOT NULL default '----',
  PRIMARY KEY  (`grpid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `jos_hd_groups`
-- 

INSERT INTO `jos_hd_groups` VALUES (1, 'user', 'components/com_jtaghelpdesk/images/mdn_userSmall.jpg', '----');
INSERT INTO `jos_hd_groups` VALUES (2, 'advisor', 'components/com_jtaghelpdesk/images/mdn_userSmallGreen.jpg', 'V---');
INSERT INTO `jos_hd_groups` VALUES (3, 'administrator', 'components/com_jtaghelpdesk/images/mdn_userSmallRed.jpg', 'VMED');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_highlight`
-- 

CREATE TABLE `jos_hd_highlight` (
  `hdid` int(11) NOT NULL default '0',
  `ticketid` int(11) NOT NULL default '0',
  `datetime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`hdid`,`ticketid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_hd_highlight`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_msg`
-- 

CREATE TABLE `jos_hd_msg` (
  `msgid` int(11) NOT NULL auto_increment,
  `ticketid` int(11) NOT NULL default '0',
  `hdid` int(11) NOT NULL default '0',
  `msg` text NOT NULL,
  `datetime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`msgid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_hd_msg`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_permissions`
-- 

CREATE TABLE `jos_hd_permissions` (
  `grpid` int(11) NOT NULL default '0',
  `catid` int(11) default '0',
  `type` varchar(8) NOT NULL default '',
  KEY `grpid` (`grpid`,`catid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_hd_permissions`
-- 

INSERT INTO `jos_hd_permissions` VALUES (1, 1, 'vmrcd---');
INSERT INTO `jos_hd_permissions` VALUES (2, 1, 'VmRCDPAO');
INSERT INTO `jos_hd_permissions` VALUES (3, 1, 'VmRCDPAO');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_settings`
-- 

CREATE TABLE `jos_hd_settings` (
  `name` varchar(255) NOT NULL default '',
  `value` varchar(255) default NULL,
  PRIMARY KEY  (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_hd_settings`
-- 

INSERT INTO `jos_hd_settings` VALUES ('iconset', 'mdn_');
INSERT INTO `jos_hd_settings` VALUES ('notifyemail', '');
INSERT INTO `jos_hd_settings` VALUES ('highlight', '!');
INSERT INTO `jos_hd_settings` VALUES ('notifyusers', '0');
INSERT INTO `jos_hd_settings` VALUES ('enhighlight', '1');
INSERT INTO `jos_hd_settings` VALUES ('ticketsfront', '5');
INSERT INTO `jos_hd_settings` VALUES ('ticketssub', '15');
INSERT INTO `jos_hd_settings` VALUES ('sourceemail', '');
INSERT INTO `jos_hd_settings` VALUES ('msgbox', 'none');
INSERT INTO `jos_hd_settings` VALUES ('lang', 'english.php');
INSERT INTO `jos_hd_settings` VALUES ('name', 'Joomlatag Helpdesk Ticketing System');
INSERT INTO `jos_hd_settings` VALUES ('newpostmsg', 'a new message has arrived:');
INSERT INTO `jos_hd_settings` VALUES ('newpostmsg1', 'a new message has arrived:');
INSERT INTO `jos_hd_settings` VALUES ('newpostmsg2', '');
INSERT INTO `jos_hd_settings` VALUES ('newpostmsg3', '');
INSERT INTO `jos_hd_settings` VALUES ('users', '0');
INSERT INTO `jos_hd_settings` VALUES ('agree', '0');
INSERT INTO `jos_hd_settings` VALUES ('agreei', '');
INSERT INTO `jos_hd_settings` VALUES ('agreelw', 'You must agree to the following terms to use this system');
INSERT INTO `jos_hd_settings` VALUES ('agreen', 'agreement');
INSERT INTO `jos_hd_settings` VALUES ('agreela', 'If you have read the terms please continue');
INSERT INTO `jos_hd_settings` VALUES ('agreeb', 'continue');
INSERT INTO `jos_hd_settings` VALUES ('view', 'a');
INSERT INTO `jos_hd_settings` VALUES ('msgboxh', '10');
INSERT INTO `jos_hd_settings` VALUES ('msgboxw', '58');
INSERT INTO `jos_hd_settings` VALUES ('msgboxt', '1');
INSERT INTO `jos_hd_settings` VALUES ('dorganisation', 'individual');
INSERT INTO `jos_hd_settings` VALUES ('copyright', 'Joomlatag Helpdesk Ticketing System');
INSERT INTO `jos_hd_settings` VALUES ('date', 'j-M-Y (h:i)');
INSERT INTO `jos_hd_settings` VALUES ('defaultmsg', 'type here...');
INSERT INTO `jos_hd_settings` VALUES ('dateshort', 'j-M-Y');
INSERT INTO `jos_hd_settings` VALUES ('assignname', 'Assigned Tickets');
INSERT INTO `jos_hd_settings` VALUES ('assigndescription', 'Tickets assigned to you to answer');
INSERT INTO `jos_hd_settings` VALUES ('assignimage', '');
INSERT INTO `jos_hd_settings` VALUES ('versionmajor', '1');
INSERT INTO `jos_hd_settings` VALUES ('timezone', '+0000');
INSERT INTO `jos_hd_settings` VALUES ('notifyadmin', '0');
INSERT INTO `jos_hd_settings` VALUES ('versionminor', '0');
INSERT INTO `jos_hd_settings` VALUES ('versionpatch', '11');
INSERT INTO `jos_hd_settings` VALUES ('css', 'disable');
INSERT INTO `jos_hd_settings` VALUES ('versionname', 'stable');
INSERT INTO `jos_hd_settings` VALUES ('upgrade', '0');
INSERT INTO `jos_hd_settings` VALUES ('userdefault', '1');
INSERT INTO `jos_hd_settings` VALUES ('usersimport', '0');
INSERT INTO `jos_hd_settings` VALUES ('debug', '0');
INSERT INTO `jos_hd_settings` VALUES ('debugmessage', 'Continue >>');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_ticket`
-- 

CREATE TABLE `jos_hd_ticket` (
  `hdid` int(11) NOT NULL default '0',
  `ticketid` int(11) NOT NULL auto_increment,
  `ticketname` varchar(25) NOT NULL default '',
  `lifecycle` tinyint(1) NOT NULL default '0',
  `datetime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `category` int(11) NOT NULL default '0',
  `assign` int(11) default NULL,
  PRIMARY KEY  (`ticketid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_hd_ticket`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_hd_users`
-- 

CREATE TABLE `jos_hd_users` (
  `hdid` int(11) NOT NULL default '0',
  `organisation` varchar(25) NOT NULL default '',
  `agree` tinyint(1) NOT NULL default '0',
  `grpid` int(11) NOT NULL default '0',
  PRIMARY KEY  (`hdid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_hd_users`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_attachments`
-- 

CREATE TABLE `jos_huruhelpdesk_attachments` (
  `id` int(11) NOT NULL auto_increment,
  `note_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `type` text NOT NULL,
  `size` int(11) NOT NULL,
  `content` mediumblob NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_attachments`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_categories`
-- 

CREATE TABLE `jos_huruhelpdesk_categories` (
  `category_id` bigint(20) NOT NULL auto_increment,
  `cname` text NOT NULL,
  `rep_id` bigint(20) NOT NULL,
  UNIQUE KEY `category_id` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_categories`
-- 

INSERT INTO `jos_huruhelpdesk_categories` VALUES (1, 'Feature', 0);
INSERT INTO `jos_huruhelpdesk_categories` VALUES (2, 'Bug', 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_config`
-- 

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

-- 
-- Dumping data for table `jos_huruhelpdesk_config`
-- 

INSERT INTO `jos_huruhelpdesk_config` VALUES (1, 'info@netanel.pl', 'http://www.netanel.pl/index.php?option=com_huruhelpdesk&view=cpanel&Itemid=2', 1, 1, 3, 15, 24, 1, 1, 10, 0, '0.88 beta', 0, 0, 10000, 0, 0, 0, 0, 0, 50, 50, 0, 0, 100, 0, 0, 0, 50, 50, 50, 50, 'Netanel Helpdesk', 1, 1, -1, 10000, '.jpg,.png', 'image/jpeg,image/png', 1048576, 1, '', 0, 0, 'info@netanel.pl');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_departments`
-- 

CREATE TABLE `jos_huruhelpdesk_departments` (
  `department_id` bigint(20) NOT NULL auto_increment,
  `dname` text NOT NULL,
  UNIQUE KEY `department_id` (`department_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_departments`
-- 

INSERT INTO `jos_huruhelpdesk_departments` VALUES (1, 'Testing');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_emailmsg`
-- 

CREATE TABLE `jos_huruhelpdesk_emailmsg` (
  `id` int(11) NOT NULL auto_increment,
  `type` text NOT NULL,
  `subject` text NOT NULL,
  `body` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_emailmsg`
-- 

INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (1, 'repclose', 'HELPDESK: Problem [problemid] Closed', 'The following problem has been closed.  You can view the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nSOLUTION\r\n--------\r\n[solution]');
INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (2, 'repnew', 'HELPDESK: Problem [problemid] Assigned', 'The following problem has been assigned to you.  You can update the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nUSER INFORMATION\r\n----------------\r\nUsername: [uid]\r\nEmail: [uemail]\r\nPhone: [phone]\r\nLocation: [location]\r\nDepartment: [department]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]');
INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (3, 'reppager', 'HELPDESK: Problem [problemid] Assigned/Updated', 'Title:[title]\r\nUser:[uid]\r\nPriority:[priority]');
INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (4, 'repupdate', 'HELPDESK: Problem [problemid] Updated', 'The following problem has been updated.  You can view the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]\r\n\r\nNOTES\r\n-----------\r\n[notes]');
INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (5, 'userclose', 'HELPDESK: Problem [problemid] Closed', 'Your help desk problem has been closed.  You can view the solution below or at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nSOLUTION\r\n--------\r\n[solution]');
INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (6, 'usernew', 'HELPDESK: Problem [problemid] Created', 'Thank you for submitting your problem to the help desk.  You can view or update the problem at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]');
INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (7, 'userupdate', 'HELPDESK: Problem [problemid] Updated', 'Your help desk problem has been updated.  You can view the problem at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]\r\n\r\nNOTES\r\n-----------\r\n[notes]');
INSERT INTO `jos_huruhelpdesk_emailmsg` VALUES (8, 'adminnew', 'HELPDESK: Problem [problemid] Created', 'The following problem has been created.  You can update the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nUSER INFORMATION\r\n----------------\r\nFullname: [fullname]\r\nUsername: [uid]\r\nEmail: [uemail]\r\nPhone: [phone]\r\nLocation: [location]\r\nDepartment: [department]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_langstrings`
-- 

CREATE TABLE `jos_huruhelpdesk_langstrings` (
  `id` int(11) NOT NULL auto_increment,
  `lang_id` bigint(20) NOT NULL,
  `variable` text NOT NULL,
  `langtext` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=581 DEFAULT CHARSET=utf8 AUTO_INCREMENT=581 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_langstrings`
-- 

INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (54, 1, 'Classification', 'Classification');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (59, 1, 'Close', 'Close');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (60, 1, 'CloseDate', 'Close Date');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (72, 1, 'ContactInformation', 'Contact Information');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (79, 1, 'DateSubmitted', 'Entered');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (91, 1, 'Department', 'Department');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (95, 1, 'Description', 'Description');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (104, 1, 'EditInformation', 'Edit your contact information');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (108, 1, 'EMail', 'E-Mail');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (109, 1, 'EmailAddress', 'E-Mail Address');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (120, 1, 'EndDate', 'End Date');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (121, 1, 'EnterAdditionalNotes', 'Enter Additional Notes');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (125, 1, 'EnteredBy', 'Entered By');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (126, 1, 'EnterinKnowledgeBase', 'Enter in Knowledge Base');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (135, 1, 'From', 'From');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (142, 1, 'HideFromEndUser', 'Hide new note from end user');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (144, 1, 'ID', 'ID');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (146, 1, 'In', 'In');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (148, 1, 'InOutBoard', 'View In/Out Board');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (174, 1, 'Location', 'Location');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (191, 1, 'minutes', 'minutes');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (199, 1, 'NewProblem', 'New Problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (207, 1, 'Noresultsfound', 'No matching problems found');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (210, 1, 'Notes', 'Notes');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (218, 1, 'OpenProblems', 'Open problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (219, 1, 'OpenProblemsfor', 'Open problems for');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (222, 1, 'Or', 'Or');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (225, 1, 'Out', 'Out');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (237, 1, 'Phone', 'Phone');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (248, 1, 'Priority', 'Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (257, 1, 'ProblemID', 'View Problem #');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (259, 1, 'ProblemInformation', 'Problem Information');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (263, 1, 'Problems', 'Problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (277, 1, 'ReopenProblem', 'Reopen');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (278, 1, 'Rep', 'Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (282, 1, 'Reports', 'Reports');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (286, 1, 'Required', 'Required');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (292, 1, 'Save', 'Save');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (294, 1, 'Search', 'Search');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (296, 1, 'SearchFields', 'Search Fields');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (297, 1, 'SearchProblems', 'Search problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (298, 1, 'SearchResults', 'Search Results');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (299, 1, 'SearchtheKnowledgeBase', 'Search the knowledgebase');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (302, 1, 'SelectCategory', 'Select Category');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (303, 1, 'SelectDepartment', 'Select Department');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (304, 1, 'SelectUser', 'Select User');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (31, 1, 'AssignedTo', 'Assigned To');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (313, 1, 'Solution', 'Solution');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (317, 1, 'StartDate', 'Start Date');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (319, 1, 'Status', 'Status');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (329, 1, 'Subject', 'Subject');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (330, 1, 'Submit', 'Submit');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (332, 1, 'SubmitNewProblem', 'Submit new problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (335, 1, 'SupportRep', 'Support Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (352, 1, 'Time', 'Time');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (353, 1, 'TimeSpent', 'Time Spent');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (354, 1, 'Title', 'Title');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (356, 1, 'Total', 'Total');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (373, 1, 'User', 'User');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (376, 1, 'UserName', 'User Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (385, 1, 'View', 'View');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (386, 1, 'ViewProblemList', 'View all open problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (387, 1, 'Viewproblemsfor', 'View open problems assigned to');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (394, 1, 'ViewAssignedProblems', 'View assigned problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (395, 1, 'ViewSubmittedProblems', 'View submitted problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (397, 1, 'ViewProblemsFromLast', 'View all problems from last');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (398, 1, 'days', 'days');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (399, 1, 'Activity', 'Activity');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (400, 1, 'Home', 'Home');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (401, 1, 'Refresh', 'Refresh');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (402, 1, 'NoLimit', '(No limit)');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (403, 1, 'Back', 'Back');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (404, 1, 'ProblemNumber', 'Problem #');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (405, 1, 'ProblemSaved', 'Problem saved');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (406, 1, 'ErrorSavingProblem', 'Error saving record: Invalid or missing required fields.');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (409, 1, 'DefaultRep', 'Default Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (410, 1, 'NotFound', 'No matching problem found');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (411, 1, 'EnterVerification', 'Verification');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (412, 1, 'Name', 'Full Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (413, 1, 'Admin', 'Admin');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (414, 1, 'ShowReps', 'Show Reps');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (415, 1, 'ShowAll', 'Show All');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (416, 1, 'RepsAdmins', 'Reps & Admins Only');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (417, 1, 'AllUsers', 'All Huru Users');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (418, 1, 'SearchCriteria', 'Search Criteria');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (419, 1, 'Reset', 'Reset');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (421, 1, 'To', 'To');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (422, 1, 'SearchText', 'Search Text');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (423, 1, 'Browse', 'Browse');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (424, 1, 'Cancel', 'Cancel');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (425, 1, 'NewSearch', 'New Search');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (426, 1, 'Results', 'Results');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (427, 1, 'ProblemsFound', 'problem(s) found');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (428, 1, 'EnterSearch', 'Enter your search criteria & click the Search button');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (429, 1, 'EnterReport', 'Enter your report criteria & click the View button');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (430, 1, 'AvailableReports', 'Available Reports');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (431, 1, 'DateRange', 'Date Range');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (432, 1, 'AverageTime', 'Average Time');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (433, 1, 'PercentProblemTotal', '% of Problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (434, 1, 'PercentTimeTotal', '% of Time');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (435, 1, 'min', 'min');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (436, 1, 'Unknown', 'Unknown');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (437, 1, 'ActivitySummary', 'MIS Activity Summary');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (438, 1, 'Modified', 'Modified');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (439, 1, 'through', 'through');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (440, 1, 'MailProblemID', 'Problem Id Number');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (441, 1, 'MailTitle', 'Problem Title/Subject');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (442, 1, 'MailDescription', 'Problem Description');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (443, 1, 'MailUID', 'Username of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (444, 1, 'MailUEmail', 'Email address of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (445, 1, 'MailPhone', 'Phone number of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (446, 1, 'MailLocation', 'Location of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (447, 1, 'MailDepartment', 'Department of person who reported problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (448, 1, 'MailPriority', 'Priority of problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (449, 1, 'MailCategory', 'Category of problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (45, 1, 'Category', 'Category');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (450, 1, 'MailStartDate', 'Date problem was reported/opened');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (451, 1, 'MailURL', 'URL to problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (452, 1, 'MailSolution', 'Problem solution');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (453, 1, 'MailNotes', 'Notes about problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (454, 1, 'ProblemsSubmittedBy', 'Problems submitted by');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (456, 1, 'for', 'for');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (457, 1, 'ForPrevious', 'for previous');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (458, 1, 'All', 'All');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (459, 1, 'OpenProblemsLC', 'open problems');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (460, 1, 'Print', 'Print');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (461, 1, 'UserProfile', 'User Profile');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (462, 1, 'JoomlaUserInfo', 'Joomla! user information');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (463, 1, 'HuruUserInfo', 'Helpdesk User Profile');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (464, 1, 'HomePhone', 'Home Phone');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (465, 1, 'MobilePhone', 'Mobile Phone');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (466, 1, 'PagerAddress', 'Pager Address');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (467, 1, 'Location1', 'Location 1');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (468, 1, 'Location2', 'Location 2');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (469, 1, 'Language', 'Language');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (470, 1, 'ManageCategories', 'Manage Categories');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (471, 1, 'EditCategory', 'Edit Category');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (472, 1, 'CategoryName', 'Category Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (473, 1, 'Default', 'Default');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (474, 1, 'GeneralConfiguration', 'General Configuration');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (475, 1, 'ReplyAddress', 'Reply Address');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (476, 1, 'BaseURL', 'Helpdesk Base URL');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (477, 1, 'NotifyUserOnCaseUpdate', 'Notify User on Case Update');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (478, 1, 'AllowAnonymousCases', 'Allow Anonymous Cases');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (479, 1, 'AllowUserSelectOnNewCases', 'Allow User Select on New Cases');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (480, 1, 'KnowledgeBaseViewAuthority', 'Knowledgebase View Authority');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (481, 1, 'Disable', 'Disable');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (482, 1, 'RepsOnly', 'Reps Only');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (483, 1, 'UsersAndReps', 'Users & Reps');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (484, 1, 'Anyone', 'Anyone');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (485, 1, 'DefaultPriority', 'Default Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (486, 1, 'PagerPriority', 'Pager Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (487, 1, 'DefaultStatus', 'Default Status');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (488, 1, 'ClosedStatus', 'Closed Status');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (489, 1, 'DefaultLanguage', 'Default Language');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (490, 1, 'EmailMessages', 'Email Messages');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (491, 1, 'Users', 'Users');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (492, 1, 'Departments', 'Departments');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (493, 1, 'Categories', 'Categories');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (494, 1, 'Priorities', 'Priorities');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (495, 1, 'Statuses', 'Statuses');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (496, 1, 'Languages', 'Languages');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (497, 1, 'About', 'About');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (498, 1, 'Administration', 'Administration');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (499, 1, 'ManageDepartments', 'Manage Departments');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (500, 1, 'DepartmentName', 'Department Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (501, 1, 'EditDepartment', 'Edit Department');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (502, 1, 'ManageEmailMessages', 'Manage Email Messages');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (503, 1, 'Type', 'Type');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (504, 1, 'Body', 'Body');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (505, 1, 'Edit...', 'Edit...');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (506, 1, 'EditEmailMessage', 'Edit Email Message');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (507, 1, 'AvailableSubstitutions', 'Available Substitutions');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (508, 1, 'ManageLanguages', 'Manage Languages');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (509, 1, 'LanguageName', 'Language Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (510, 1, 'Localized', 'Localized');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (511, 1, 'EditLanguage', 'Edit Language');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (512, 1, 'LanguageStrings', 'Language Strings');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (513, 1, 'ManagePriorities', 'Manage Priorities');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (514, 1, 'PriorityName', 'Priority Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (515, 1, 'EditPriority', 'Edit Priority');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (516, 1, 'ManageStatuses', 'Manage Statuses');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (517, 1, 'Rank', 'Rank');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (518, 1, 'StatusName', 'Status Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (519, 1, 'EditStatus', 'Edit Status');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (520, 1, 'ManageLanguageStrings', 'Manage Language Strings');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (521, 1, 'LanguageID', 'Language ID');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (522, 1, 'Variable', 'Variable');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (523, 1, 'Text', 'Text');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (524, 1, 'EditString', 'Edit String');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (525, 1, 'ManageUsers', 'Manage Users');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (526, 1, 'HuruID', 'Huru ID');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (527, 1, 'JoomlaID', 'Joomla! ID');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (528, 1, 'SyncJoomlaUsers', 'Sync Joomla! Users');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (529, 1, 'SyncJoomlaUsersConfirm', 'This will synchronize the Huru users table with the Joomla! users table - importing accounts into Huru as necessary.  No Joomla! user accounts will be altered.');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (530, 1, 'EditUser', 'Edit User');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (531, 1, 'IsUser', 'User');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (532, 1, 'IsRep', 'Rep');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (533, 1, 'IsAdmin', 'Administrator');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (534, 1, 'ViewReports', 'View Reports');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (535, 1, 'UserSuperAdminNote', '(Note: This setting is ignored for Joomla! Super Administrators - who are always Huru Helpdesk Admins)');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (536, 1, 'DefaultAssignment', 'Category Default');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (537, 1, 'PageTitle', 'Huru Helpdesk');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (538, 1, 'SelectOverride', 'Overrides contact info above');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (539, 1, 'CannotDeleteClosedStatus', 'Cannot delete status set as Closed Status in General Confguration');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (540, 1, 'Go', 'Go');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (541, 1, 'ProblemDeleted', 'Problem deleted');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (542, 1, 'ProblemNotDeleted', 'Error deleting problem');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (543, 1, 'DeleteProblem', 'Delete Problem #');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (544, 1, 'Delete', 'Delete');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (545, 1, 'ProblemCreated', 'Problem created');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (546, 1, 'AttachFileToNote', 'Attach file to note');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (547, 1, 'Attachment', 'Attachment');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (548, 1, 'AttachmentFileNoteFound', 'Attachment file not found');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (549, 1, 'DefaultFileAttachmentNote', 'File attached');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (550, 1, 'ErrorSavingAttachment', 'Error saving attachment');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (551, 1, 'NotImplemented', 'Not implemented');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (552, 1, 'FileTypeNotAllowed', 'File type not allowed');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (553, 1, 'FileTooLarge', 'File too large');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (554, 1, 'UnknownError', 'Unknown error');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (555, 1, 'NotificationSenderName', 'Notification Sender Name');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (556, 1, 'AllowFileAttachments', 'Allow File Attachments to Cases');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (557, 1, 'AllowedAttachmentExtensions', 'Allowed Attachment Extensions');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (558, 1, 'ExtensionExample', 'Comma separated list of allowed file extensions (with leading periods) [e.g: .jpg,.png,.txt]');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (559, 1, 'MaximumAttachmentSize', 'Maximum Attachment Size');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (560, 1, 'Bytes', 'bytes');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (561, 1, 'AttachmentSizeWarning', 'Huru maximum is 16MB.  PHP may be configured for less.  Check your php.ini file');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (562, 1, 'AttachmentDownloadPermissions', 'Allow attachment download');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (563, 1, 'AttachmentDeleted', 'Attachment deleted');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (564, 1, 'AttachmentNotDeleted', 'Error deleting attachment');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (565, 1, 'MaximumAttachmentAge', 'Auto-purge old attachments after');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (566, 1, 'SetToZero', 'Set to 0 to disable auto-purge');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (567, 1, 'MailFullname', 'Full name of user who entered case (from Joomla account)');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (568, 1, 'NotifyAdminOnNewCase', 'Email address to notify for all new cases');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (569, 1, 'LeaveBlank', 'Leave blank to disable');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (570, 1, 'Notifications', 'Notifications');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (571, 1, 'Permissions', 'Permissions');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (572, 1, 'Defaults', 'Defaults');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (573, 1, 'FileAttachments', 'File Attachments');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (574, 1, 'DisplayedFields', 'Displayed Fields');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (575, 1, 'DefaultDepartment', 'Default Department');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (576, 1, 'DefaultCategory', 'Default Category');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (577, 1, 'Show', 'Show');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (578, 1, 'Set', 'Set');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (579, 1, 'IfNotSetable', 'If not visible/setable by everyone, a default for this field must be set above');
INSERT INTO `jos_huruhelpdesk_langstrings` VALUES (580, 1, 'Updated', 'Updated');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_language`
-- 

CREATE TABLE `jos_huruhelpdesk_language` (
  `id` bigint(20) NOT NULL auto_increment,
  `langname` text NOT NULL,
  `localized` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_language`
-- 

INSERT INTO `jos_huruhelpdesk_language` VALUES (1, 'English', 'English');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_notes`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_notes`
-- 

INSERT INTO `jos_huruhelpdesk_notes` VALUES (1, 1, 'Problem created', '2011-04-12 10:25:40', '', '127.0.0.1', 1);
INSERT INTO `jos_huruhelpdesk_notes` VALUES (2, 2, 'Problem created', '2011-04-12 10:26:42', '', '127.0.0.1', 1);
INSERT INTO `jos_huruhelpdesk_notes` VALUES (3, 3, 'Problem created', '2011-04-12 10:28:52', '', '127.0.0.1', 1);
INSERT INTO `jos_huruhelpdesk_notes` VALUES (4, 4, 'Problem created', '2011-04-14 20:46:24', 'dddd', '127.0.0.1', 1);
INSERT INTO `jos_huruhelpdesk_notes` VALUES (5, 5, 'Problem created', '2011-07-19 18:12:05', '', '127.0.0.1', 1);
INSERT INTO `jos_huruhelpdesk_notes` VALUES (6, 5, 'fix it please', '2011-07-19 18:12:05', '', '127.0.0.1', 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_priority`
-- 

CREATE TABLE `jos_huruhelpdesk_priority` (
  `priority_id` bigint(20) NOT NULL auto_increment,
  `pname` text NOT NULL,
  UNIQUE KEY `priority_id` (`priority_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_priority`
-- 

INSERT INTO `jos_huruhelpdesk_priority` VALUES (6, ' 6 - VERY HIGH ');
INSERT INTO `jos_huruhelpdesk_priority` VALUES (5, ' 5 - HIGH ');
INSERT INTO `jos_huruhelpdesk_priority` VALUES (4, ' 4 - ELEVATED ');
INSERT INTO `jos_huruhelpdesk_priority` VALUES (3, ' 3 - NORMAL ');
INSERT INTO `jos_huruhelpdesk_priority` VALUES (2, ' 2 - LOW ');
INSERT INTO `jos_huruhelpdesk_priority` VALUES (1, '1 - VERY LOW');
INSERT INTO `jos_huruhelpdesk_priority` VALUES (10, ' 10 - EMERGENCY - PAGE ');
INSERT INTO `jos_huruhelpdesk_priority` VALUES (9, ' 9 - EMERGENCY - NO PAGE ');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_problems`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_problems`
-- 

INSERT INTO `jos_huruhelpdesk_problems` VALUES (1, 'Jacek', 'jacek.p.kolodziejczyk@gmail.com', '', '', 0, 15, 0, 1, '0000-00-00 00:00:00', 1, 'test', 'asdfasdf', '', '2011-04-12 10:25:40', 3, 0, 0);
INSERT INTO `jos_huruhelpdesk_problems` VALUES (2, 'a', 'jacek.p.kolodziejczyk@gmail.com', 'Kraków', '6132662', 0, 15, 0, 1, '0000-00-00 00:00:00', 1, 'test2', 'a', '', '2011-04-12 10:26:42', 3, 0, 0);
INSERT INTO `jos_huruhelpdesk_problems` VALUES (3, 'a', 'jacek.p.kolodziejczyk@gmail.com', 'Kraków', '6132662', 0, 15, 0, 1, '0000-00-00 00:00:00', 1, 'test3', 'asdfasdf', '', '2011-04-12 10:28:52', 3, 0, 0);
INSERT INTO `jos_huruhelpdesk_problems` VALUES (4, 'dddd', 'd@d.pl', '', '', 0, 15, 0, 1, '0000-00-00 00:00:00', 1, 'd1', 'ddddd', '', '2011-04-14 20:46:23', 3, 3, 0);
INSERT INTO `jos_huruhelpdesk_problems` VALUES (5, 'Juzek', 'juzek@wp.pl', '', '', 0, 15, 0, 2, '0000-00-00 00:00:00', 1, 'asfasdfasdfa', 'sdfasfasdfasdfa', '', '2011-07-19 18:12:05', 3, 0, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_status`
-- 

CREATE TABLE `jos_huruhelpdesk_status` (
  `id` int(11) NOT NULL auto_increment,
  `status_id` bigint(20) NOT NULL,
  `sname` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_status`
-- 

INSERT INTO `jos_huruhelpdesk_status` VALUES (22, 65, 'TESTING');
INSERT INTO `jos_huruhelpdesk_status` VALUES (21, 63, 'WAITING');
INSERT INTO `jos_huruhelpdesk_status` VALUES (20, 60, 'HOLD');
INSERT INTO `jos_huruhelpdesk_status` VALUES (19, 55, 'ESCALATED');
INSERT INTO `jos_huruhelpdesk_status` VALUES (18, 50, 'IN PROGRESS');
INSERT INTO `jos_huruhelpdesk_status` VALUES (17, 20, 'OPEN');
INSERT INTO `jos_huruhelpdesk_status` VALUES (16, 10, 'RECEIVED');
INSERT INTO `jos_huruhelpdesk_status` VALUES (15, 1, 'NEW');
INSERT INTO `jos_huruhelpdesk_status` VALUES (24, 100, 'CLOSED');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_huruhelpdesk_users`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `jos_huruhelpdesk_users`
-- 

INSERT INTO `jos_huruhelpdesk_users` VALUES (1, 62, 0, 0, 0, '', '', '', '', '', '', 0, 1, 0);
INSERT INTO `jos_huruhelpdesk_users` VALUES (2, 63, 0, 0, 0, '', '', '', '', '', '', 0, 1, 0);
INSERT INTO `jos_huruhelpdesk_users` VALUES (3, 66, 1, 0, 0, '', '', '', '', '', '', 0, 1, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_languages`
-- 

CREATE TABLE `jos_languages` (
  `lang_id` int(11) unsigned NOT NULL auto_increment,
  `lang_code` char(7) NOT NULL,
  `title` varchar(50) NOT NULL,
  `title_native` varchar(50) NOT NULL,
  `sef` varchar(50) NOT NULL,
  `image` varchar(50) NOT NULL,
  `description` varchar(512) NOT NULL,
  `metakey` text NOT NULL,
  `metadesc` text NOT NULL,
  `published` int(11) NOT NULL default '0',
  PRIMARY KEY  (`lang_id`),
  UNIQUE KEY `idx_sef` (`sef`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_languages`
-- 

INSERT INTO `jos_languages` VALUES (1, 'en-GB', 'English (UK)', 'English (UK)', 'en', 'en', '', '', '', 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_menu`
-- 

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
  KEY `menutype` (`menutype`),
  KEY `link` (`link`(27))
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

-- 
-- Dumping data for table `jos_menu`
-- 

INSERT INTO `jos_menu` VALUES (1, 'mainmenu', 'Home', 'home', 'index.php?option=com_content&view=frontpage', 'component', 1, 0, 20, 0, 1, 62, '2011-05-31 17:08:28', 0, 0, 0, 3, 'num_leading_articles=1\nnum_intro_articles=4\nnum_columns=2\nnum_links=4\norderby_pri=\norderby_sec=front\nmulti_column_order=1\nshow_pagination=2\nshow_pagination_results=1\nshow_feed_link=1\nshow_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=0\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 1);
INSERT INTO `jos_menu` VALUES (2, 'mainmenu', 'Issue Tracker', 'issuetracker', 'index.php?option=com_content&view=article&id=18', 'component', 1, 23, 20, 1, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (3, 'mainmenu', 'Products', 'products', 'index.php?option=com_content&view=article&id=9', 'component', 1, 0, 20, 0, 3, 62, '2011-07-19 16:06:21', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (4, 'mainmenu', 'SWT Matrix', 'swtmatrix', 'index.php?option=com_content&view=article&id=9', 'component', 1, 3, 20, 1, 1, 62, '2011-07-19 16:06:09', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=table.png\nsecure=0\nmega_showtitle=1\nmega_desc=Limits breaking tabular widget\nmega_cols=1\nmega_group=1\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (5, 'mainmenu', 'Download', 'download', 'index.php?option=com_content&view=article&id=4', 'component', 1, 0, 20, 0, 4, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (6, 'mainmenu', 'Forum', 'forum', 'index.php?option=com_content&view=article&id=8', 'component', 0, 0, 20, 0, 6, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (23, 'mainmenu', 'Community', 'community', 'https://swtmatrix.fogbugz.com/', 'url', 1, 0, 0, 0, 9, 62, '2011-08-18 20:53:09', 0, 0, 0, 0, 'menu_image=-1\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (24, 'mainmenu', 'Issue Tracker', 'issuetracker', 'index.php?option=com_huruhelpdesk&view=cpanel', 'component', -2, 0, 34, 0, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'page_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (7, 'mainmenu', 'Features', 'features', 'index.php?option=com_content&view=article&id=5', 'component', 1, 4, 20, 2, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (8, 'SWT-Matrix-Menu', 'Features', 'features', 'index.php?Itemid=7', 'menulink', 1, 0, 0, 0, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'menu_item=7\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (11, 'mainmenu', 'Buy', 'buy', 'index.php?option=com_content&view=article&id=14', 'component', 1, 0, 20, 0, 5, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (12, 'mainmenu', 'Contact', 'contact', 'index.php?option=com_content&view=article&id=8', 'component', 0, 0, 20, 0, 7, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (9, 'SWT-Matrix-Menu', 'API', 'api', 'index.php?Itemid=10', 'menulink', 1, 0, 0, 0, 3, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'menu_item=10\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (14, 'mainmenu', 'Design', 'design', 'index.php?option=com_content&view=article&id=10', 'component', 0, 4, 20, 2, 4, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (10, 'mainmenu', 'API', 'api', 'swt-matrix/javadoc/index.html', 'url', 1, 4, 0, 2, 5, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'menu_image=-1\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (13, 'mainmenu', 'Overview', 'overview', 'index.php?option=com_content&view=article&id=9', 'component', 1, 4, 20, 2, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (15, 'mainmenu', 'Tutorial', 'tutorial', 'index.php?option=com_content&view=article&id=13', 'component', 1, 4, 20, 2, 6, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (16, 'mainmenu', 'Snippets', 'snippets', 'index.php?option=com_content&view=article&id=11', 'component', 1, 4, 20, 2, 7, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (17, 'Hidden', 'Design', 'design', 'index.php?option=com_content&view=article&id=10', 'component', 1, 0, 20, 0, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (18, 'mainmenu', 'Forum Zoho', 'forum', 'http://discussions.zoho.com/swtmatrix/', 'url', 0, 6, 0, 1, 1, 62, '2011-07-19 16:02:52', 0, 1, 0, 0, 'menu_image=-1\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (19, 'mainmenu', 'Login', 'login', 'index.php?option=com_user&view=login', 'component', 0, 0, 14, 0, 8, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_login_title=1\nheader_login=\nlogin=\nlogin_message=1\ndescription_login=1\ndescription_login_text=\nimage_login=\nimage_login_align=right\nshow_logout_title=1\nheader_logout=\nlogout=\nlogout_message=1\ndescription_logout=1\ndescription_logout_text=\nimage_logout=\nimage_logout_align=left\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=200\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=mod\nmega_subcontent-mod-modules=19\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (20, 'mainmenu', 'Forum Kunena', 'forumkunena', 'index.php?option=com_kunena', 'component', 0, 6, 54, 1, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'page_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (21, 'SWT-Matrix-Menu', 'Tutorial', 'tutorial', 'index.php?option=com_content&view=article&id=13', 'component', 1, 0, 20, 0, 4, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (22, 'SWT-Matrix-Menu', 'Snippets', 'snippets', 'index.php?option=com_content&view=article&id=11', 'component', 1, 0, 20, 0, 5, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (25, 'mainmenu', 'Forum', 'forum', 'index.php?option=com_content&view=article&id=19', 'component', 1, 23, 20, 1, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (26, 'SWT-Matrix-Menu', 'Screenshots', 'screenshosts', 'index.php?option=com_content&view=article&id=20', 'component', 1, 0, 20, 0, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (27, 'mainmenu', 'Screentshots', 'screenshots', 'index.php?option=com_content&view=article&id=20', 'component', 1, 4, 20, 2, 3, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);
INSERT INTO `jos_menu` VALUES (28, 'mainmenu', 'Forum', 'forum', 'index.php?option=com_content&view=article&id=19', 'component', 0, 0, 20, 0, 10, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\nmega_showtitle=1\nmega_desc=\nmega_cols=1\nmega_group=0\nmega_width=\nmega_colw=\nmega_colxw=\nmega_class=\nmega_subcontent=0\n\n', 0, 0, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_menu_types`
-- 

CREATE TABLE `jos_menu_types` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `menutype` varchar(75) NOT NULL default '',
  `title` varchar(255) NOT NULL default '',
  `description` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `menutype` (`menutype`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `jos_menu_types`
-- 

INSERT INTO `jos_menu_types` VALUES (1, 'mainmenu', 'Main Menu', 'The main menu for the site');
INSERT INTO `jos_menu_types` VALUES (2, 'SWT-Matrix-Menu', 'SWT Matrix Menu', '');
INSERT INTO `jos_menu_types` VALUES (3, 'Hidden', 'HIdden', 'for article links');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_messages`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_messages`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_messages_cfg`
-- 

CREATE TABLE `jos_messages_cfg` (
  `user_id` int(10) unsigned NOT NULL default '0',
  `cfg_name` varchar(100) NOT NULL default '',
  `cfg_value` varchar(255) NOT NULL default '',
  UNIQUE KEY `idx_user_var_name` (`user_id`,`cfg_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_messages_cfg`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_migration_backlinks`
-- 

CREATE TABLE `jos_migration_backlinks` (
  `itemid` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` text NOT NULL,
  `sefurl` text NOT NULL,
  `newurl` text NOT NULL,
  PRIMARY KEY  (`itemid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_migration_backlinks`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_modules`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 AUTO_INCREMENT=23 ;

-- 
-- Dumping data for table `jos_modules`
-- 

INSERT INTO `jos_modules` VALUES (1, 'Main Menu', '', 0, 'left', 0, '0000-00-00 00:00:00', 0, 'mod_mainmenu', 0, 0, 1, 'menutype=mainmenu\nmenu_style=list_flat\nstartLevel=0\nendLevel=0\nshowAllChildren=0\nwindow_open=\nshow_whitespace=0\ncache=1\ntag_id=\nclass_sfx=\nmoduleclass_sfx=_menu\nmaxdepth=10\nmenu_images=0\nmenu_images_align=0\nmenu_images_link=0\nexpand_menu=0\nactivate_parent=0\nfull_active_id=0\nindent_image=0\nindent_image1=\nindent_image2=\nindent_image3=\nindent_image4=\nindent_image5=\nindent_image6=\nspacer=\nend_spacer=\n\n', 1, 0, '');
INSERT INTO `jos_modules` VALUES (2, 'Login', '', 1, 'login', 0, '0000-00-00 00:00:00', 1, 'mod_login', 0, 0, 1, '', 1, 1, '');
INSERT INTO `jos_modules` VALUES (3, 'Popular', '', 3, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_popular', 0, 2, 1, '', 0, 1, '');
INSERT INTO `jos_modules` VALUES (4, 'Recent added Articles', '', 4, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_latest', 0, 2, 1, 'ordering=c_dsc\nuser_id=0\ncache=0\n\n', 0, 1, '');
INSERT INTO `jos_modules` VALUES (5, 'Menu Stats', '', 5, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_stats', 0, 2, 1, '', 0, 1, '');
INSERT INTO `jos_modules` VALUES (6, 'Unread Messages', '', 1, 'header', 0, '0000-00-00 00:00:00', 1, 'mod_unread', 0, 2, 1, '', 1, 1, '');
INSERT INTO `jos_modules` VALUES (7, 'Online Users', '', 2, 'header', 0, '0000-00-00 00:00:00', 1, 'mod_online', 0, 2, 1, '', 1, 1, '');
INSERT INTO `jos_modules` VALUES (8, 'Toolbar', '', 1, 'toolbar', 0, '0000-00-00 00:00:00', 1, 'mod_toolbar', 0, 2, 1, '', 1, 1, '');
INSERT INTO `jos_modules` VALUES (9, 'Quick Icons', '', 1, 'icon', 0, '0000-00-00 00:00:00', 1, 'mod_quickicon', 0, 2, 1, '', 1, 1, '');
INSERT INTO `jos_modules` VALUES (10, 'Logged in Users', '', 2, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_logged', 0, 2, 1, '', 0, 1, '');
INSERT INTO `jos_modules` VALUES (11, 'Footer', '', 0, 'footer', 0, '0000-00-00 00:00:00', 1, 'mod_footer', 0, 0, 1, '', 1, 1, '');
INSERT INTO `jos_modules` VALUES (12, 'Admin Menu', '', 1, 'menu', 0, '0000-00-00 00:00:00', 1, 'mod_menu', 0, 2, 1, '', 0, 1, '');
INSERT INTO `jos_modules` VALUES (13, 'Admin SubMenu', '', 1, 'submenu', 0, '0000-00-00 00:00:00', 1, 'mod_submenu', 0, 2, 1, '', 0, 1, '');
INSERT INTO `jos_modules` VALUES (14, 'User Status', '', 1, 'status', 0, '0000-00-00 00:00:00', 1, 'mod_status', 0, 2, 1, '', 0, 1, '');
INSERT INTO `jos_modules` VALUES (15, 'Title', '', 1, 'title', 0, '0000-00-00 00:00:00', 1, 'mod_title', 0, 2, 1, '', 0, 1, '');
INSERT INTO `jos_modules` VALUES (16, 'breadcrumbs', '', 0, 'breadcrumb', 0, '0000-00-00 00:00:00', 1, 'mod_breadcrumbs', 0, 0, 1, 'showHome=0\nhomeText=\nshowLast=1\nseparator=\nmoduleclass_sfx=\ncache=0\n\n', 0, 0, '');
INSERT INTO `jos_modules` VALUES (17, 'SWT Matrix Menu', '', 0, 'menu', 0, '0000-00-00 00:00:00', 1, 'mod_mainmenu', 0, 0, 1, 'menutype=SWT-Matrix-Menu\nmenu_style=list\nstartLevel=0\nendLevel=0\nshowAllChildren=0\nwindow_open=\nshow_whitespace=0\ncache=1\ntag_id=\nclass_sfx=\nmoduleclass_sfx=\nmaxdepth=10\nmenu_images=0\nmenu_images_align=0\nmenu_images_link=0\nexpand_menu=0\nactivate_parent=0\nfull_active_id=0\nindent_image=0\nindent_image1=\nindent_image2=\nindent_image3=\nindent_image4=\nindent_image5=\nindent_image6=\nspacer=\nend_spacer=\n\n', 0, 0, '');
INSERT INTO `jos_modules` VALUES (18, 'Search', '', 0, 'search', 62, '2011-04-20 09:33:42', 1, 'mod_search', 0, 0, 0, 'moduleclass_sfx=\nwidth=20\ntext=search\nbutton=\nbutton_pos=right\nimagebutton=1\nbutton_text=\nset_itemid=\ncache=1\ncache_time=900\n\n', 0, 0, '');
INSERT INTO `jos_modules` VALUES (19, 'Login', '', 0, 'menu', 0, '0000-00-00 00:00:00', 1, 'mod_login', 0, 0, 1, 'cache=0\nmoduleclass_sfx=\npretext=Login is needed to post in the issue tracker and the forum.\nposttext=\nlogin=\nlogout=\ngreeting=1\nname=0\nusesecure=0\n\n', 0, 0, '');
INSERT INTO `jos_modules` VALUES (21, 'Footer', '', 1, 'footer', 0, '0000-00-00 00:00:00', 1, 'mod_footer', 0, 0, 1, 'cache=1\n\n', 0, 0, '');
INSERT INTO `jos_modules` VALUES (22, 'Easy Joomla PayPal Payment / Donations Module', '', 0, 'left', 0, '0000-00-00 00:00:00', 0, 'mod_j15paypal', 0, 0, 0, 'payment_type=2\nlogo_on=0\nlogo=http://www.paypal.com/en_US/i/btn/x-click-but04.gif\nmoduleclass_sfx=\npaypal_email=dattard@gmail.com\npaypal_org=Donation\npaypalcur_on=1\npaypalval_on=1\npaypalcur_val=USD\npaypalval_button=Donate\npaypalval_val=5\npaypalvalleast_val=5\npaypalreturn=http://www.yoursite.com\npaypalcancel=http://www.yoursite.com\npaymentlocation=\n\n', 0, 0, '');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_modules_menu`
-- 

CREATE TABLE `jos_modules_menu` (
  `moduleid` int(11) NOT NULL default '0',
  `menuid` int(11) NOT NULL default '0',
  PRIMARY KEY  (`moduleid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_modules_menu`
-- 

INSERT INTO `jos_modules_menu` VALUES (1, 0);
INSERT INTO `jos_modules_menu` VALUES (16, 0);
INSERT INTO `jos_modules_menu` VALUES (17, 4);
INSERT INTO `jos_modules_menu` VALUES (17, 7);
INSERT INTO `jos_modules_menu` VALUES (17, 10);
INSERT INTO `jos_modules_menu` VALUES (18, 0);
INSERT INTO `jos_modules_menu` VALUES (19, 0);
INSERT INTO `jos_modules_menu` VALUES (21, 0);
INSERT INTO `jos_modules_menu` VALUES (22, 0);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_newsfeeds`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_newsfeeds`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_plugins`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 AUTO_INCREMENT=51 ;

-- 
-- Dumping data for table `jos_plugins`
-- 

INSERT INTO `jos_plugins` VALUES (1, 'Authentication - Joomla', 'joomla', 'authentication', 0, 1, 1, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (2, 'Authentication - LDAP', 'ldap', 'authentication', 0, 2, 0, 1, 0, 0, '0000-00-00 00:00:00', 'host=\nport=389\nuse_ldapV3=0\nnegotiate_tls=0\nno_referrals=0\nauth_method=bind\nbase_dn=\nsearch_string=\nusers_dn=\nusername=\npassword=\nldap_fullname=fullName\nldap_email=mail\nldap_uid=uid\n\n');
INSERT INTO `jos_plugins` VALUES (3, 'Authentication - GMail', 'gmail', 'authentication', 0, 4, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (4, 'Authentication - OpenID', 'openid', 'authentication', 0, 3, 0, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (5, 'User - Joomla!', 'joomla', 'user', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', 'autoregister=1\n\n');
INSERT INTO `jos_plugins` VALUES (6, 'Search - Content', 'content', 'search', 0, 1, 1, 1, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\nsearch_content=1\nsearch_uncategorised=1\nsearch_archived=1\n\n');
INSERT INTO `jos_plugins` VALUES (7, 'Search - Contacts', 'contacts', 'search', 0, 3, 1, 1, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n');
INSERT INTO `jos_plugins` VALUES (8, 'Search - Categories', 'categories', 'search', 0, 4, 1, 0, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n');
INSERT INTO `jos_plugins` VALUES (9, 'Search - Sections', 'sections', 'search', 0, 5, 1, 0, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n');
INSERT INTO `jos_plugins` VALUES (10, 'Search - Newsfeeds', 'newsfeeds', 'search', 0, 6, 1, 0, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n');
INSERT INTO `jos_plugins` VALUES (11, 'Search - Weblinks', 'weblinks', 'search', 0, 2, 1, 1, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n');
INSERT INTO `jos_plugins` VALUES (12, 'Content - Pagebreak', 'pagebreak', 'content', 0, 10000, 1, 1, 0, 0, '0000-00-00 00:00:00', 'enabled=1\ntitle=1\nmultipage_toc=1\nshowall=1\n\n');
INSERT INTO `jos_plugins` VALUES (13, 'Content - Rating', 'vote', 'content', 0, 4, 1, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (14, 'Content - Email Cloaking', 'emailcloak', 'content', 0, 5, 1, 0, 0, 0, '0000-00-00 00:00:00', 'mode=1\n\n');
INSERT INTO `jos_plugins` VALUES (15, 'Content - Code Hightlighter (GeSHi)', 'geshi', 'content', 0, 5, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (16, 'Content - Load Module', 'loadmodule', 'content', 0, 6, 1, 0, 0, 0, '0000-00-00 00:00:00', 'enabled=1\nstyle=0\n\n');
INSERT INTO `jos_plugins` VALUES (17, 'Content - Page Navigation', 'pagenavigation', 'content', 0, 2, 1, 1, 0, 0, '0000-00-00 00:00:00', 'position=1\n\n');
INSERT INTO `jos_plugins` VALUES (18, 'Editor - No Editor', 'none', 'editors', 0, 0, 1, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (19, 'Editor - TinyMCE', 'tinymce', 'editors', 0, 0, 1, 1, 0, 0, '0000-00-00 00:00:00', 'mode=extended\nskin=0\ncompressed=0\ncleanup_startup=0\ncleanup_save=0\nentity_encoding=raw\nlang_mode=0\nlang_code=en\ntext_direction=ltr\ncontent_css=1\ncontent_css_custom=\nrelative_urls=1\nnewlines=0\ninvalid_elements=applet\nextended_elements=input[name\\|size\\|type\\|value\\|onclick]\ntoolbar=top\ntoolbar_align=left\nhtml_height=550\nhtml_width=750\nelement_path=1\nfonts=1\npaste=1\nsearchreplace=1\ninsertdate=1\nformat_date=%Y-%m-%d\ninserttime=1\nformat_time=%H:%M:%S\ncolors=1\ntable=1\nsmilies=1\nmedia=1\nhr=1\ndirectionality=1\nfullscreen=1\nstyle=1\nlayer=1\nxhtmlxtras=1\nvisualchars=1\nnonbreaking=1\nblockquote=1\ntemplate=0\nadvimage=1\nadvlink=1\nautosave=1\ncontextmenu=1\ninlinepopups=1\nsafari=1\ncustom_plugin=\ncustom_button=\n\n');
INSERT INTO `jos_plugins` VALUES (20, 'Editor - XStandard Lite 2.0', 'xstandard', 'editors', 0, 0, 0, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (21, 'Editor Button - Image', 'image', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (22, 'Editor Button - Pagebreak', 'pagebreak', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (23, 'Editor Button - Readmore', 'readmore', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (24, 'XML-RPC - Joomla', 'joomla', 'xmlrpc', 0, 7, 0, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (25, 'XML-RPC - Blogger API', 'blogger', 'xmlrpc', 0, 7, 0, 1, 0, 0, '0000-00-00 00:00:00', 'catid=1\nsectionid=0\n\n');
INSERT INTO `jos_plugins` VALUES (27, 'System - SEF', 'sef', 'system', 0, 1, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (28, 'System - Debug', 'debug', 'system', 0, 2, 1, 0, 0, 0, '0000-00-00 00:00:00', 'queries=1\nmemory=1\nlangauge=1\n\n');
INSERT INTO `jos_plugins` VALUES (29, 'System - Legacy', 'legacy', 'system', 0, 3, 0, 1, 0, 0, '0000-00-00 00:00:00', 'route=0\n\n');
INSERT INTO `jos_plugins` VALUES (30, 'System - Cache', 'cache', 'system', 0, 4, 0, 1, 0, 0, '0000-00-00 00:00:00', 'browsercache=0\ncachetime=15\n\n');
INSERT INTO `jos_plugins` VALUES (31, 'System - Log', 'log', 'system', 0, 5, 0, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (32, 'System - Remember Me', 'remember', 'system', 0, 6, 1, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (33, 'System - Backlink', 'backlink', 'system', 0, 7, 0, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (34, 'System - Mootools Upgrade', 'mtupgrade', 'system', 0, 8, 1, 1, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (35, 'System - ARTIO JoomSEF', 'joomsef', 'system', 0, -9, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (36, 'System - Advanced Module Manager', 'advancedmodules', 'system', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (37, 'System - NoNumber! Elements', 'nonumberelements', 'system', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (38, 'System - Articles Anywhere', 'articlesanywhere', 'system', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (39, 'Editor Button - Articles Anywhere', 'articlesanywhere', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (40, 'JA Menu Parameters', 'plg_jamenuparams', 'system', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (42, 'Huru Helpdesk - User Sync', 'huruhelpdesk_user_sync', 'user', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', 'isUser=1\n\n');
INSERT INTO `jos_plugins` VALUES (44, 'Content AutoToC', 'autotoc', 'content', 0, 0, 0, 0, 0, 0, '0000-00-00 00:00:00', 'enabledDefault=0\nshowOnFrontPage=0\naddNumbering=0\nnumSlice=2\naddToc=1\nuseDefaultCSS=1\nfloatDir=right\nappendClear=1\naddNumberingToc=0\ndisplayToc=table\n\n');
INSERT INTO `jos_plugins` VALUES (45, 'Content - Article Table of Contents', 'toc', 'content', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', 'enabled=1\naddNumbering=0\naddToc=0\nindentToc=1\ndisplayToc=table\n\n');
INSERT INTO `jos_plugins` VALUES (46, 'System - Modules Anywhere', 'modulesanywhere', 'system', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', 'style=none\noverride_style=1\noverride_settings=1\nmodule_tag=module\nignore_state=0\nmodulepos_tag=modulepos\nhandle_loadposition=0\n@activate_jumper=0\narticles_enable=1\narticles_security_level=23\ncomponents_enable=1\ncomponents=x\nother_enable=1\nplace_comments=1\n\n');
INSERT INTO `jos_plugins` VALUES (47, 'Editor Button - Modules Anywhere', 'modulesanywhere', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_plugins` VALUES (48, 'Content - ValAddThis', 'valaddthis', 'content', 0, 0, 0, 0, 0, 62, '2011-05-21 12:07:14', 'plugin_class=\naddthis_pub=\nsecure_server=1\naddthis_type=0\naddthis_type_cat=0\naddthis_type_sec=0\naddthis_type_front=0\naddthis_position=0\nshow_cat=0\nshow_sec=0\nshow_front=0\nfilter_art=\nfilter_cat=\nfilter_sec=\nhtml_before=\nhtml_after=\nservices_compact=facebook, twitter, buzz, delicious\nservices_expanded=\nservices_exclude=\nui_use_css=1\nui_use_addressbook=0\ndata_track_linkback=0\ndata_use_flash=1\ndata_use_cookies=1\nui_use_embeddable_services_beta=0\ndata_ga_tracker=0\nga_tracker_object=\nui_header_color=\nui_header_background=\nui_cobrand=\nui_offset_top=\nui_offset_left=\nui_hover_direction=0\nui_delay=\nui_language=\ntext_share_caption=\ntext_email_caption=\ntext_email=\ntext_favorites=\ntext_more=\nbutton_type=0\nui_click=0\ncustom_choice=0\naddthis_button=0\ncustom_button=\ncustom_text=\ntext_style=\nalt_text=\nrssfeed_url=\ntoolbox_services=\nuse_text_flag=0\nuse_nofollow=0\ntoolbox_style=default\ntoolbox_width=\ntoolbox_sharetext=\ntooltip_text=\nuse_more_flag=1\ntoolbox_more=\ntoolbox_separator=\n\n');
INSERT INTO `jos_plugins` VALUES (49, 'Disqus Comment System for Joomla! (by JoomlaWorks)', 'jw_disqus', 'content', 0, 0, 0, 0, 0, 0, '0000-00-00 00:00:00', 'disqusSubDomain=swt-matrix\nselectedCategories=\nselectedMenus=1|3|4|13|7|10|15|16|5|11|6\ndisqusListingCounter=0\ndisqusArticleCounter=0\ndisqusDevMode=1\ndebugMode=1\n\n');
INSERT INTO `jos_plugins` VALUES (50, 'Editor - JCE', 'jce', 'editors', 0, 0, 0, 0, 0, 0, '0000-00-00 00:00:00', '');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_poll_data`
-- 

CREATE TABLE `jos_poll_data` (
  `id` int(11) NOT NULL auto_increment,
  `pollid` int(11) NOT NULL default '0',
  `text` text NOT NULL,
  `hits` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `pollid` (`pollid`,`text`(1))
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_poll_data`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_poll_date`
-- 

CREATE TABLE `jos_poll_date` (
  `id` bigint(20) NOT NULL auto_increment,
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  `vote_id` int(11) NOT NULL default '0',
  `poll_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `poll_id` (`poll_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_poll_date`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_poll_menu`
-- 

CREATE TABLE `jos_poll_menu` (
  `pollid` int(11) NOT NULL default '0',
  `menuid` int(11) NOT NULL default '0',
  PRIMARY KEY  (`pollid`,`menuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_poll_menu`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_polls`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_polls`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_redirect_links`
-- 

CREATE TABLE `jos_redirect_links` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `old_url` varchar(150) NOT NULL,
  `new_url` varchar(150) NOT NULL,
  `referer` varchar(150) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `published` tinyint(4) NOT NULL,
  `created_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `modified_date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_link_old` (`old_url`),
  KEY `idx_link_modifed` (`modified_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_redirect_links`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_schemas`
-- 

CREATE TABLE `jos_schemas` (
  `extension_id` int(11) NOT NULL,
  `version_id` varchar(20) NOT NULL,
  PRIMARY KEY  (`extension_id`,`version_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_schemas`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sections`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `jos_sections`
-- 

INSERT INTO `jos_sections` VALUES (1, 'Products', '', 'products', '', 'content', 'left', '<p>Products</p>', 1, 0, '0000-00-00 00:00:00', 1, 0, 3, '');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sefaliases`
-- 

CREATE TABLE `jos_sefaliases` (
  `id` int(11) NOT NULL auto_increment,
  `alias` varchar(255) NOT NULL default '',
  `vars` text NOT NULL,
  `url` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `alias` (`alias`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_sefaliases`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sefexts`
-- 

CREATE TABLE `jos_sefexts` (
  `id` int(11) NOT NULL auto_increment,
  `file` varchar(100) NOT NULL,
  `filters` text,
  `params` text,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `jos_sefexts`
-- 

INSERT INTO `jos_sefexts` VALUES (1, 'com_wrapper.xml', NULL, 'ignoreSource=0\nitemid=1\noverrideId=', NULL);
INSERT INTO `jos_sefexts` VALUES (2, 'com_content.xml', '+^[0-9]*$=limit,limitstart,month,showall,year\n+^[a-zA-Z]+$=task,type,view\n+^[0-9]+(:[a-zA-Z0-9._-]+)?$=catid,id,sectionid', 'acceptVars=view; id; catid; sectionid; type; year; month; filter; limit; limitstart; task; showall', NULL);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sefexttexts`
-- 

CREATE TABLE `jos_sefexttexts` (
  `id` int(11) NOT NULL auto_increment,
  `extension` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_sefexttexts`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sefmoved`
-- 

CREATE TABLE `jos_sefmoved` (
  `id` int(11) NOT NULL auto_increment,
  `old` varchar(255) NOT NULL,
  `new` varchar(255) NOT NULL,
  `lastHit` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  KEY `old` (`old`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_sefmoved`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sefurls`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_sefurls`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sefurlword_xref`
-- 

CREATE TABLE `jos_sefurlword_xref` (
  `word` int(11) NOT NULL,
  `url` int(11) NOT NULL,
  PRIMARY KEY  (`word`,`url`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_sefurlword_xref`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_sefwords`
-- 

CREATE TABLE `jos_sefwords` (
  `id` int(11) NOT NULL auto_increment,
  `word` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_sefwords`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_session`
-- 

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

-- 
-- Dumping data for table `jos_session`
-- 

INSERT INTO `jos_session` VALUES ('', '1349761916', '8659ed4e719493dfc64d23114cff6fe7', 1, 0, '', 0, 0, '__default|a:7:{s:15:"session.counter";i:1;s:19:"session.timer.start";i:1349761916;s:18:"session.timer.last";i:1349761916;s:17:"session.timer.now";i:1349761916;s:22:"session.client.browser";s:67:"Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1";s:8:"registry";O:9:"JRegistry":3:{s:17:"_defaultNameSpace";s:7:"session";s:9:"_registry";a:1:{s:7:"session";a:1:{s:4:"data";O:8:"stdClass":0:{}}}s:7:"_errors";a:0:{}}s:4:"user";O:5:"JUser":19:{s:2:"id";i:0;s:4:"name";N;s:8:"username";N;s:5:"email";N;s:8:"password";N;s:14:"password_clear";s:0:"";s:8:"usertype";N;s:5:"block";N;s:9:"sendEmail";i:0;s:3:"gid";i:0;s:12:"registerDate";N;s:13:"lastvisitDate";N;s:10:"activation";N;s:6:"params";N;s:3:"aid";i:0;s:5:"guest";i:1;s:7:"_params";O:10:"JParameter":7:{s:4:"_raw";s:0:"";s:4:"_xml";N;s:9:"_elements";a:0:{}s:12:"_elementPath";a:1:{i:0;s:85:"/homepages/45/d346168534/htdocs/wsb6077281001/libraries/joomla/html/parameter/element";}s:17:"_defaultNameSpace";s:8:"_default";s:9:"_registry";a:1:{s:8:"_default";a:1:{s:4:"data";O:8:"stdClass":0:{}}}s:7:"_errors";a:0:{}}s:9:"_errorMsg";N;s:7:"_errors";a:0:{}}}');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_stats_agents`
-- 

CREATE TABLE `jos_stats_agents` (
  `agent` varchar(255) NOT NULL default '',
  `type` tinyint(1) unsigned NOT NULL default '0',
  `hits` int(11) unsigned NOT NULL default '1',
  KEY `agent` (`agent`(10))
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_stats_agents`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_template_styles`
-- 

CREATE TABLE `jos_template_styles` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `template` varchar(50) NOT NULL default '',
  `client_id` tinyint(1) unsigned NOT NULL default '0',
  `home` char(7) NOT NULL default '0',
  `title` varchar(255) NOT NULL default '',
  `params` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_template` (`template`),
  KEY `idx_home` (`home`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

-- 
-- Dumping data for table `jos_template_styles`
-- 

INSERT INTO `jos_template_styles` VALUES (2, 'bluestork', 1, '1', 'Bluestork - Default', '{"useRoundedCorners":"1","showSiteName":"0"}');
INSERT INTO `jos_template_styles` VALUES (3, 'atomic', 0, '1', 'Atomic - Default', '{}');
INSERT INTO `jos_template_styles` VALUES (4, 'beez_20', 0, '0', 'Beez2 - Default', '{"wrapperSmall":"53","wrapperLarge":"72","logo":"images\\/joomla_black.gif","sitetitle":"Joomla!","sitedescription":"Open Source Content Management","navposition":"left","templatecolor":"personal","html5":"0"}');
INSERT INTO `jos_template_styles` VALUES (5, 'hathor', 1, '0', 'Hathor - Default', '{"showSiteName":"0","colourChoice":"","boldText":"0"}');
INSERT INTO `jos_template_styles` VALUES (6, 'beez5', 0, '0', 'Beez5 - Default-Fruit Shop', '{"wrapperSmall":"53","wrapperLarge":"72","logo":"images\\/sampledata\\/fruitshop\\/fruits.gif","sitetitle":"Matuna Market ","sitedescription":"Fruit Shop Sample Site","navposition":"left","html5":"0"}');
INSERT INTO `jos_template_styles` VALUES (7, 'yougrids', 0, '0', 'Yougrids - Default', '{"":"TOP_MENU_OFF_YJ_LABEL","STYLE_SETTINGS_TAB":"STYLE_SETTINGS_TAB","custom_css":"2","STTEXT_LABEL":"STTEXT_LABEL","STYLE_SUB":"STYLE_SUB","default_color":"metal","default_font":"3","default_font_family":"6","selectors_override":"2","selectors_override_type":"1","css_font_family":"12","google_font_family":"2","cufon_font_family":"1","affected_selectors":"div.title h1,div.title h2,div.componentheading, h1,h2,h3,h4,h5,h6,.yjround h4,.yjsquare h4","LOGO_SUB":"LOGO_SUB","LGTEXT_LABEL":"LGTEXT_LABEL","LOGO_YJ_TITLE":"LOGO_YJ_TITLE","logo_height":"125px","logo_width":"300px","turn_logo_off":"2","turn_header_block_off":"2","TOP_MENU_SUB":"TOP_MENU_SUB","TMTEXT_LABEL":"TMTEXT_LABEL","TOP_MENU_YJ_LABEL":"TOP_MENU_YJ_LABEL","menuName":"mainmenu","default_menu_style":"2","sub_width":"280px","yjsg_menu_offset":"95","turn_topmenu_off":"","DEF_GRID_SUB":"DEF_GRID_SUB","DGTEXT_LABEL":"DGTEXT_LABEL","MAINB_YJ_LABEL":"MAINB_YJ_LABEL","css_widthdefined":"px","css_width":"1000","site_layout":"2","MBC_W_LABEL":"MBC_W_LABEL","widthdefined":"%","maincolumn":"55","insetcolumn":"0","leftcolumn":"22.5","rightcolumn":"22.5","SPII_LABEL":"SPII_LABEL","widthdefined_itmid":"%","maincolumn_itmid":"55","insetcolumn_itmid":"0","leftcolumn_itmid":"22.5","rightcolumn_itmid":"22.5","define_itemid":"","MG_SUB":"MG_SUB","MGTEXT_LABEL":"MGTEXT_LABEL","yjsg_1_width":"20|20|20|20|20","yjsg_header_width":"33|33|33","yjsg_2_width":"20|20|20|20|20","yjsg_3_width":"20|20|20|20|20","yjsg_4_width":"20|20|20|20|20","yjsg_bodytop_width":"33|33|33","yjsg_yjsgbodytbottom_width":"33|33|33","yjsg_5_width":"20|20|20|20|20","yjsg_6_width":"20|20|20|20|20","yjsg_7_width":"20|20|20|20|20","MOBILE_SUB":"MOBILE_SUB","MOBILE_TXT_LABEL":"MOBILE_TXT_LABEL","iphone_default":"1","android_default":"1","handheld_default":"2","mobile_reg":"1","ADD_F_SUB":"ADD_F_SUB","ADTEXT_LABEL":"ADTEXT_LABEL","GA_YJ_LABEL":"GA_YJ_LABEL","GATEXT_LABEL":"GATEXT_LABEL","ga_switch":"0","GAcode":"UA-xxxxxx-x","NOT_YJ_LABEL":"NOT_YJ_LABEL","compress":"0","ie6notice":"0","nonscript":"0","ST_YJ_LABEL":"ST_YJ_LABEL","show_tools":"1","show_fres":"1","show_rtlc":"1","TDIR_YJ_LABEL":"TDIR_YJ_LABEL","text_direction":"2","SEO_YJ_LABEL":"SEO_YJ_LABEL","turn_seo_off":"2","seo":"Your description goes here","tags":"Your keywords go here","COPY_YJ_LABEL":"COPY_YJ_LABEL","branding_off":"2","joomlacredit_off":"2","ADV_SUB":"ADV_SUB","ADVTEXT_LABEL":"ADVTEXT_LABEL","FPC_YJ_LABEL":"FPC_YJ_LABEL","fp_controll_switch":"2","fp_chars_limit":"3000","fp_after_text":"","SCRIPT_YJ_LABEL":"SCRIPT_YJ_LABEL","JQ_LABEL":"JQ_LABEL","jq_switch":"2","SMS_YJ_LABEL":"SMS_YJ_LABEL","MSTEXT_LABEL":"MSTEXT_LABEL","YJsg1_module_style":"YJsgxhtml","YJsgh_module_style":"YJsgxhtml","YJsg2_module_style":"YJsgxhtml","YJsg3_module_style":"YJsgxhtml","YJsg4_module_style":"YJsgxhtml","YJsgmt_module_style":"YJsgxhtml","YJsgl_module_style":"YJsgxhtml","YJsgr_module_style":"YJsgxhtml","YJsgi_module_style":"YJsgxhtml","YJsgit_module_style":"YJsgxhtml","YJsgib_module_style":"YJsgxhtml","YJsgmb_module_style":"YJsgxhtml","YJsg5_module_style":"YJsgxhtml","YJsg6_module_style":"YJsgxhtml","YJsg7_module_style":"YJsgxhtml","CP_LABEL":"CP_LABEL","component_switch":"","admin_css_time":"0"}');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_templates_menu`
-- 

CREATE TABLE `jos_templates_menu` (
  `template` varchar(255) NOT NULL default '',
  `menuid` int(11) NOT NULL default '0',
  `client_id` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`menuid`,`client_id`,`template`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_templates_menu`
-- 

INSERT INTO `jos_templates_menu` VALUES ('ja_purity_ii', 0, 0);
INSERT INTO `jos_templates_menu` VALUES ('khepri', 0, 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_update_categories`
-- 

CREATE TABLE `jos_update_categories` (
  `categoryid` int(11) NOT NULL auto_increment,
  `name` varchar(20) default '',
  `description` text NOT NULL,
  `parent` int(11) default '0',
  `updatesite` int(11) default '0',
  PRIMARY KEY  (`categoryid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Update Categories' AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_update_categories`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_update_sites`
-- 

CREATE TABLE `jos_update_sites` (
  `update_site_id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `type` varchar(20) default '',
  `location` text NOT NULL,
  `enabled` int(11) default '0',
  PRIMARY KEY  (`update_site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Update Sites' AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `jos_update_sites`
-- 

INSERT INTO `jos_update_sites` VALUES (1, 'Joomla Core', 'collection', 'http://update.joomla.org/core/list.xml', 1);
INSERT INTO `jos_update_sites` VALUES (2, 'Joomla Extension Directory', 'collection', 'http://update.joomla.org/jed/list.xml', 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_update_sites_extensions`
-- 

CREATE TABLE `jos_update_sites_extensions` (
  `update_site_id` int(11) NOT NULL default '0',
  `extension_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`update_site_id`,`extension_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Links extensions to update sites';

-- 
-- Dumping data for table `jos_update_sites_extensions`
-- 

INSERT INTO `jos_update_sites_extensions` VALUES (1, 700);
INSERT INTO `jos_update_sites_extensions` VALUES (2, 700);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_updates`
-- 

CREATE TABLE `jos_updates` (
  `update_id` int(11) NOT NULL auto_increment,
  `update_site_id` int(11) default '0',
  `extension_id` int(11) default '0',
  `categoryid` int(11) default '0',
  `name` varchar(100) default '',
  `description` text NOT NULL,
  `element` varchar(100) default '',
  `type` varchar(20) default '',
  `folder` varchar(20) default '',
  `client_id` tinyint(3) default '0',
  `version` varchar(10) default '',
  `data` text NOT NULL,
  `detailsurl` text NOT NULL,
  PRIMARY KEY  (`update_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Available Updates' AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_updates`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_user_profiles`
-- 

CREATE TABLE `jos_user_profiles` (
  `user_id` int(11) NOT NULL,
  `profile_key` varchar(100) NOT NULL,
  `profile_value` varchar(255) NOT NULL,
  `ordering` int(11) NOT NULL default '0',
  UNIQUE KEY `idx_user_id_profile_key` (`user_id`,`profile_key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Simple user profile storage table';

-- 
-- Dumping data for table `jos_user_profiles`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_user_usergroup_map`
-- 

CREATE TABLE `jos_user_usergroup_map` (
  `user_id` int(10) unsigned NOT NULL default '0' COMMENT 'Foreign Key to #__users.id',
  `group_id` int(10) unsigned NOT NULL default '0' COMMENT 'Foreign Key to #__usergroups.id',
  PRIMARY KEY  (`user_id`,`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `jos_user_usergroup_map`
-- 

INSERT INTO `jos_user_usergroup_map` VALUES (40, 8);
INSERT INTO `jos_user_usergroup_map` VALUES (42, 8);

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_usergroups`
-- 

CREATE TABLE `jos_usergroups` (
  `id` int(10) unsigned NOT NULL auto_increment COMMENT 'Primary Key',
  `parent_id` int(10) unsigned NOT NULL default '0' COMMENT 'Adjacency List Reference Id',
  `lft` int(11) NOT NULL default '0' COMMENT 'Nested set lft.',
  `rgt` int(11) NOT NULL default '0' COMMENT 'Nested set rgt.',
  `title` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_usergroup_parent_title_lookup` (`parent_id`,`title`),
  KEY `idx_usergroup_title_lookup` (`title`),
  KEY `idx_usergroup_adjacency_lookup` (`parent_id`),
  KEY `idx_usergroup_nested_set_lookup` USING BTREE (`lft`,`rgt`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

-- 
-- Dumping data for table `jos_usergroups`
-- 

INSERT INTO `jos_usergroups` VALUES (1, 0, 1, 20, 'Public');
INSERT INTO `jos_usergroups` VALUES (2, 1, 6, 17, 'Registered');
INSERT INTO `jos_usergroups` VALUES (3, 2, 7, 14, 'Author');
INSERT INTO `jos_usergroups` VALUES (4, 3, 8, 11, 'Editor');
INSERT INTO `jos_usergroups` VALUES (5, 4, 9, 10, 'Publisher');
INSERT INTO `jos_usergroups` VALUES (6, 1, 2, 5, 'Manager');
INSERT INTO `jos_usergroups` VALUES (7, 6, 3, 4, 'Administrator');
INSERT INTO `jos_usergroups` VALUES (8, 1, 18, 19, 'Super Users');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_users`
-- 

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
) ENGINE=MyISAM AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 AUTO_INCREMENT=67 ;

-- 
-- Dumping data for table `jos_users`
-- 

INSERT INTO `jos_users` VALUES (62, 'Administrator', 'admin', 'jacek.p.kolodziejczyk@gmail.com', '206fb885934fc3f7d312b204ebca8913:G8wcM1KOP0GdeAb7HF63BeojFVjuumtf', 'Super Administrator', 0, 1, 25, '2011-03-13 21:18:07', '2012-10-08 23:29:28', '', 'admin_language=\nlanguage=\neditor=\nhelpsite=\ntimezone=0\n\n');
INSERT INTO `jos_users` VALUES (63, 'Jacek', 'jacek', 'jackolo@poczta.fm', '8f39acd9fba3134e96c5d4f6b025ab16:iNoq0ku6Jp8BW9swWGvNhHOprddJSpBQ', 'Registered', 0, 0, 18, '2011-04-14 13:52:15', '2011-05-03 12:50:34', 'f9b1ef19e09e5e4d7ad9faf64839c132', '');
INSERT INTO `jos_users` VALUES (64, 'bbb', 'bbb', 'bbb@b.pl', 'a6544a4c61523f4af13493d38994e040:2wAKjgcH2LeVMJU0cZWDVbLE9cSQBW5m', 'Registered', 0, 0, 18, '2011-04-14 15:22:19', '2011-04-14 18:39:48', 'f3c72fb900fbf7edf6463172b21f5b7c', '');
INSERT INTO `jos_users` VALUES (65, 'cccc', 'cccc', 'c@c.pl', '9da19f8c7b4ae5e12d90a27fbe5b1e51:qWhgjF3a6tMLvDmkBPUXhVZuTqdAER7y', 'Registered', 0, 0, 18, '2011-04-14 18:40:07', '2011-04-14 18:43:00', 'b4df79c17fb4d8a48411228b5102f376', '\n');
INSERT INTO `jos_users` VALUES (66, 'dddd', 'dddd', 'd@d.pl', 'd87e05f38b5389a8e92e59ae941c116a:XvovyXSLl53VZkNLaGKgSAzh1JKc5of2', 'Registered', 0, 0, 18, '2011-04-14 18:43:22', '2011-04-15 09:24:33', '4cc3c5a62a7d802ced24c9decbe484c9', '\n');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_viewlevels`
-- 

CREATE TABLE `jos_viewlevels` (
  `id` int(10) unsigned NOT NULL auto_increment COMMENT 'Primary Key',
  `title` varchar(100) NOT NULL default '',
  `ordering` int(11) NOT NULL default '0',
  `rules` varchar(5120) NOT NULL COMMENT 'JSON encoded access control.',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_assetgroup_title_lookup` (`title`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `jos_viewlevels`
-- 

INSERT INTO `jos_viewlevels` VALUES (1, 'Public', 0, '[1]');
INSERT INTO `jos_viewlevels` VALUES (2, 'Registered', 1, '[6,2,8]');
INSERT INTO `jos_viewlevels` VALUES (3, 'Special', 2, '[6,3,8]');

-- --------------------------------------------------------

-- 
-- Table structure for table `jos_weblinks`
-- 

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `jos_weblinks`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `jos_wf_profiles`
-- 

CREATE TABLE `jos_wf_profiles` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `users` text NOT NULL,
  `types` varchar(255) NOT NULL,
  `components` text NOT NULL,
  `area` tinyint(3) NOT NULL,
  `rows` text NOT NULL,
  `plugins` text NOT NULL,
  `published` tinyint(3) NOT NULL,
  `ordering` int(11) NOT NULL,
  `checked_out` tinyint(3) NOT NULL,
  `checked_out_time` datetime NOT NULL,
  `params` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `jos_wf_profiles`
-- 

INSERT INTO `jos_wf_profiles` VALUES (1, 'Default', 'Default Profile for all users', '', '19,20,21,23,24,25', '', 0, 'help,newdocument,undo,redo,spacer,bold,italic,underline,strikethrough,justifyfull,justifycenter,justifyleft,justifyright,spacer,blockquote,formatselect,styleselect,removeformat,cleanup;fontselect,fontsizeselect,forecolor,backcolor,spacer,paste,indent,outdent,numlist,bullist,sub,sup,textcase,charmap,hr;directionality,fullscreen,preview,source,print,searchreplace,spacer,table;visualaid,visualchars,nonbreaking,style,xhtmlxtras,anchor,unlink,link,imgmanager,spellchecker,article', 'contextmenu,browser,inlinepopups,media,help,paste,searchreplace,directionality,fullscreen,preview,source,table,textcase,print,style,nonbreaking,visualchars,xhtmlxtras,imgmanager,link,spellchecker,article', 1, 1, 0, '0000-00-00 00:00:00', '');
INSERT INTO `jos_wf_profiles` VALUES (2, 'Front End', 'Sample Front-end Profile', '', '19,20,21', '', 1, 'help,newdocument,undo,redo,spacer,bold,italic,underline,strikethrough,justifyfull,justifycenter,justifyleft,justifyright,spacer,formatselect,styleselect;paste,searchreplace,indent,outdent,numlist,bullist,cleanup,charmap,removeformat,hr,sub,sup,textcase,nonbreaking,visualchars;fullscreen,preview,print,visualaid,style,xhtmlxtras,anchor,unlink,link,imgmanager,spellchecker,article', 'contextmenu,inlinepopups,help,paste,searchreplace,fullscreen,preview,print,style,textcase,nonbreaking,visualchars,xhtmlxtras,imgmanager,link,spellchecker,article', 0, 2, 0, '0000-00-00 00:00:00', '');
