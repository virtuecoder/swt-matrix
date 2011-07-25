-- phpMyAdmin SQL Dump
-- version 2.11.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 04, 2011 at 11:59 AM
-- Server version: 5.0.51
-- PHP Version: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `netanel`
--

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=34 ;

--
-- Dumping data for table `jos_assets`
--

INSERT INTO `jos_assets` (`id`, `parent_id`, `lft`, `rgt`, `level`, `name`, `title`, `rules`) VALUES
(1, 0, 1, 418, 0, 'root.1', 'Root Asset', '{"core.login.site":{"6":1,"2":1},"core.login.admin":{"6":1},"core.admin":{"8":1},"core.manage":{"7":1},"core.create":{"6":1,"3":1},"core.delete":{"6":1},"core.edit":{"6":1,"4":1},"core.edit.state":{"6":1,"5":1},"core.edit.own":{"6":1,"3":1}}'),
(2, 1, 1, 2, 1, 'com_admin', 'com_admin', '{}'),
(3, 1, 3, 6, 1, 'com_banners', 'com_banners', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}'),
(4, 1, 7, 8, 1, 'com_cache', 'com_cache', '{"core.admin":{"7":1},"core.manage":{"7":1}}'),
(5, 1, 9, 10, 1, 'com_checkin', 'com_checkin', '{"core.admin":{"7":1},"core.manage":{"7":1}}'),
(6, 1, 11, 12, 1, 'com_config', 'com_config', '{}'),
(7, 1, 13, 16, 1, 'com_contact', 'com_contact', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}'),
(8, 1, 17, 20, 1, 'com_content', 'com_content', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":[],"core.edit":{"4":1},"core.edit.state":{"5":1},"core.edit.own":[]}'),
(9, 1, 21, 22, 1, 'com_cpanel', 'com_cpanel', '{}'),
(10, 1, 23, 24, 1, 'com_installer', 'com_installer', '{"core.admin":{"7":1},"core.manage":{"7":1},"core.delete":[],"core.edit.state":[]}'),
(11, 1, 25, 26, 1, 'com_languages', 'com_languages', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}'),
(12, 1, 27, 28, 1, 'com_login', 'com_login', '{}'),
(13, 1, 29, 30, 1, 'com_mailto', 'com_mailto', '{}'),
(14, 1, 31, 32, 1, 'com_massmail', 'com_massmail', '{}'),
(15, 1, 33, 34, 1, 'com_media', 'com_media', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":{"5":1}}'),
(16, 1, 35, 36, 1, 'com_menus', 'com_menus', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}'),
(17, 1, 37, 38, 1, 'com_messages', 'com_messages', '{"core.admin":{"7":1},"core.manage":{"7":1}}'),
(18, 1, 39, 40, 1, 'com_modules', 'com_modules', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}'),
(19, 1, 41, 44, 1, 'com_newsfeeds', 'com_newsfeeds', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}'),
(20, 1, 45, 46, 1, 'com_plugins', 'com_plugins', '{"core.admin":{"7":1},"core.manage":[],"core.edit":[],"core.edit.state":[]}'),
(21, 1, 47, 48, 1, 'com_redirect', 'com_redirect', '{"core.admin":{"7":1},"core.manage":[]}'),
(22, 1, 49, 50, 1, 'com_search', 'com_search', '{"core.admin":{"7":1},"core.manage":{"6":1}}'),
(23, 1, 51, 52, 1, 'com_templates', 'com_templates', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}'),
(24, 1, 53, 54, 1, 'com_users', 'com_users', '{"core.admin":{"7":1},"core.manage":[],"core.create":[],"core.delete":[],"core.edit":[],"core.edit.own":{"6":1},"core.edit.state":[]}'),
(25, 1, 55, 58, 1, 'com_weblinks', 'com_weblinks', '{"core.admin":{"7":1},"core.manage":{"6":1},"core.create":{"3":1},"core.delete":[],"core.edit":{"4":1},"core.edit.state":{"5":1},"core.edit.own":[]}'),
(26, 1, 59, 60, 1, 'com_wrapper', 'com_wrapper', '{}'),
(27, 8, 18, 19, 2, 'com_content.category.2', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}'),
(28, 3, 4, 5, 2, 'com_banners.category.3', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[]}'),
(29, 7, 14, 15, 2, 'com_contact.category.4', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}'),
(30, 19, 42, 43, 2, 'com_newsfeeds.category.5', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}'),
(31, 25, 56, 57, 2, 'com_weblinks.category.6', 'Uncategorised', '{"core.create":[],"core.delete":[],"core.edit":[],"core.edit.state":[],"core.edit.own":[]}'),
(32, 1, 414, 415, 1, 'com_jtaghelpdesk', 'jtaghelpdesk', '{}'),
(33, 1, 416, 417, 1, 'com_huruhelpdesk', 'huruhelpdesk', '{}');

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
-- Table structure for table `jos_categories`
--

CREATE TABLE `jos_categories` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `jos_categories`
--

INSERT INTO `jos_categories` (`id`, `asset_id`, `parent_id`, `lft`, `rgt`, `level`, `path`, `extension`, `title`, `alias`, `note`, `description`, `published`, `checked_out`, `checked_out_time`, `access`, `params`, `metadesc`, `metakey`, `metadata`, `created_user_id`, `created_time`, `modified_user_id`, `modified_time`, `hits`, `language`) VALUES
(1, 0, 0, 0, 11, 0, '', 'system', 'ROOT', 'root', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{}', '', '', '', 0, '2009-10-18 16:07:09', 0, '0000-00-00 00:00:00', 0, '*'),
(2, 27, 1, 1, 2, 1, 'uncategorised', 'com_content', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:26:37', 0, '0000-00-00 00:00:00', 0, '*'),
(3, 28, 1, 3, 4, 1, 'uncategorised', 'com_banners', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":"","foobar":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:27:35', 0, '0000-00-00 00:00:00', 0, '*'),
(4, 29, 1, 5, 6, 1, 'uncategorised', 'com_contact', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:27:57', 0, '0000-00-00 00:00:00', 0, '*'),
(5, 30, 1, 7, 8, 1, 'uncategorised', 'com_newsfeeds', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:28:15', 0, '0000-00-00 00:00:00', 0, '*'),
(6, 31, 1, 9, 10, 1, 'uncategorised', 'com_weblinks', 'Uncategorised', 'uncategorised', '', '', 1, 0, '0000-00-00 00:00:00', 1, '{"target":"","image":""}', '', '', '{"page_title":"","author":"","robots":""}', 42, '2010-06-28 13:28:33', 0, '0000-00-00 00:00:00', 0, '*');

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
-- Dumping data for table `jos_contact_details`
--


-- --------------------------------------------------------

--
-- Table structure for table `jos_content`
--

CREATE TABLE `jos_content` (
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
-- Dumping data for table `jos_content`
--


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


-- --------------------------------------------------------

--
-- Table structure for table `jos_content_rating`
--

CREATE TABLE `jos_content_rating` (
  `content_id` int(11) NOT NULL default '0',
  `rating_sum` int(10) unsigned NOT NULL default '0',
  `rating_count` int(10) unsigned NOT NULL default '0',
  `lastip` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_content_rating`
--


-- --------------------------------------------------------

--
-- Table structure for table `jos_core_log_searches`
--

CREATE TABLE `jos_core_log_searches` (
  `search_term` varchar(128) NOT NULL default '',
  `hits` int(10) unsigned NOT NULL default '0'
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10004 ;

--
-- Dumping data for table `jos_extensions`
--

INSERT INTO `jos_extensions` (`extension_id`, `name`, `type`, `element`, `folder`, `client_id`, `enabled`, `access`, `protected`, `manifest_cache`, `params`, `custom_data`, `system_data`, `checked_out`, `checked_out_time`, `ordering`, `state`) VALUES
(1, 'com_mailto', 'component', 'com_mailto', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(2, 'com_wrapper', 'component', 'com_wrapper', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(3, 'com_admin', 'component', 'com_admin', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(4, 'com_banners', 'component', 'com_banners', '', 1, 1, 1, 0, '', '{"purchase_type":"3","track_impressions":"0","track_clicks":"0","metakey_prefix":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(5, 'com_cache', 'component', 'com_cache', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(6, 'com_categories', 'component', 'com_categories', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(7, 'com_checkin', 'component', 'com_checkin', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(8, 'com_contact', 'component', 'com_contact', '', 1, 1, 1, 0, '', '{"show_contact_category":"hide","show_contact_list":"0","presentation_style":"sliders","show_name":"1","show_position":"1","show_email":"0","show_street_address":"1","show_suburb":"1","show_state":"1","show_postcode":"1","show_country":"1","show_telephone":"1","show_mobile":"1","show_fax":"1","show_webpage":"1","show_misc":"1","show_image":"1","image":"","allow_vcard":"0","show_articles":"0","show_profile":"0","show_links":"0","linka_name":"","linkb_name":"","linkc_name":"","linkd_name":"","linke_name":"","contact_icons":"0","icon_address":"","icon_email":"","icon_telephone":"","icon_mobile":"","icon_fax":"","icon_misc":"","show_headings":"1","show_position_headings":"1","show_email_headings":"0","show_telephone_headings":"1","show_mobile_headings":"0","show_fax_headings":"0","allow_vcard_headings":"0","show_suburb_headings":"1","show_state_headings":"1","show_country_headings":"1","show_email_form":"1","show_email_copy":"1","banned_email":"","banned_subject":"","banned_text":"","validate_session":"1","custom_reply":"0","redirect":"","show_category_crumb":"0","metakey":"","metadesc":"","robots":"","author":"","rights":"","xreference":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(9, 'com_cpanel', 'component', 'com_cpanel', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(10, 'com_installer', 'component', 'com_installer', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(11, 'com_languages', 'component', 'com_languages', '', 1, 1, 1, 1, '', '{"administrator":"en-GB","site":"en-GB"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(12, 'com_login', 'component', 'com_login', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(13, 'com_media', 'component', 'com_media', '', 1, 1, 0, 1, '', '{"upload_extensions":"bmp,csv,doc,gif,ico,jpg,jpeg,odg,odp,ods,odt,pdf,png,ppt,swf,txt,xcf,xls,BMP,CSV,DOC,GIF,ICO,JPG,JPEG,ODG,ODP,ODS,ODT,PDF,PNG,PPT,SWF,TXT,XCF,XLS","upload_maxsize":"10","file_path":"images","image_path":"images","restrict_uploads":"1","allowed_media_usergroup":"3","check_mime":"1","image_extensions":"bmp,gif,jpg,png","ignore_extensions":"","upload_mime":"image\\/jpeg,image\\/gif,image\\/png,image\\/bmp,application\\/x-shockwave-flash,application\\/msword,application\\/excel,application\\/pdf,application\\/powerpoint,text\\/plain,application\\/x-zip","upload_mime_illegal":"text\\/html","enable_flash":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(14, 'com_menus', 'component', 'com_menus', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(15, 'com_messages', 'component', 'com_messages', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(16, 'com_modules', 'component', 'com_modules', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(17, 'com_newsfeeds', 'component', 'com_newsfeeds', '', 1, 1, 1, 0, '', '{"show_feed_image":"1","show_feed_description":"1","show_item_description":"1","feed_word_count":"0","show_headings":"1","show_name":"1","show_articles":"0","show_link":"1","show_description":"1","show_description_image":"1","display_num":"","show_pagination_limit":"1","show_pagination":"1","show_pagination_results":"1","show_cat_items":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(18, 'com_plugins', 'component', 'com_plugins', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(19, 'com_search', 'component', 'com_search', '', 1, 1, 1, 1, '', '{"enabled":"0","show_date":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(20, 'com_templates', 'component', 'com_templates', '', 1, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(21, 'com_weblinks', 'component', 'com_weblinks', '', 1, 1, 1, 0, '', '{"show_comp_description":"1","comp_description":"","show_link_hits":"1","show_link_description":"1","show_other_cats":"0","show_headings":"0","show_numbers":"0","show_report":"1","count_clicks":"1","target":"0","link_icons":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(22, 'com_content', 'component', 'com_content', '', 1, 1, 0, 1, '', '{"article_layout":"_:default","show_title":"1","link_titles":"1","show_intro":"1","show_category":"1","link_category":"1","show_parent_category":"0","link_parent_category":"0","show_author":"1","link_author":"0","show_create_date":"0","show_modify_date":"0","show_publish_date":"1","show_item_navigation":"1","show_vote":"0","show_readmore":"1","show_readmore_title":"1","readmore_limit":"100","show_icons":"1","show_print_icon":"1","show_email_icon":"1","show_hits":"1","show_noauth":"0","category_layout":"_:blog","show_category_title":"0","show_description":"0","show_description_image":"0","maxLevel":"1","show_empty_categories":"0","show_no_articles":"1","show_subcat_desc":"1","show_cat_num_articles":"0","show_base_description":"1","maxLevelcat":"-1","show_empty_categories_cat":"0","show_subcat_desc_cat":"1","show_cat_num_articles_cat":"1","num_leading_articles":"1","num_intro_articles":"4","num_columns":"2","num_links":"4","multi_column_order":"0","orderby_pri":"order","orderby_sec":"rdate","order_date":"published","show_pagination_limit":"1","filter_field":"hide","show_headings":"1","list_show_date":"0","date_format":"","list_show_hits":"1","list_show_author":"1","show_pagination":"2","show_pagination_results":"1","show_feed_link":"1","feed_summary":"0","filters":{"1":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"6":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"7":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"2":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"3":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"4":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"5":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"10":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"12":{"filter_type":"BL","filter_tags":"","filter_attributes":""},"8":{"filter_type":"BL","filter_tags":"","filter_attributes":""}}}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(23, 'com_config', 'component', 'com_config', '', 1, 1, 0, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(24, 'com_redirect', 'component', 'com_redirect', '', 1, 1, 0, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(25, 'com_users', 'component', 'com_users', '', 1, 1, 0, 1, '', '{"allowUserRegistration":"1","new_usertype":"2","useractivation":"1","frontend_userparams":"1","mailSubjectPrefix":"","mailBodySuffix":""}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(100, 'PHPMailer', 'library', 'phpmailer', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(101, 'SimplePie', 'library', 'simplepie', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(102, 'phputf8', 'library', 'phputf8', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(103, 'Joomla! Web Application Framework', 'library', 'joomla', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:33:"Joomla! Web Application Framework";s:4:"type";s:7:"library";s:12:"creationDate";s:4:"2008";s:6:"author";s:6:"Joomla";s:9:"copyright";s:67:"Copyright (C) 2005 - 2011 Open Source Matters. All rights reserved.";s:11:"authorEmail";s:16:"admin@joomla.org";s:9:"authorUrl";s:21:"http://www.joomla.org";s:7:"version";s:5:"1.6.0";s:11:"description";s:90:"The Joomla! Web Application Framework is the Core of the Joomla! Content Management System";s:5:"group";s:0:"";}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(200, 'mod_articles_archive', 'module', 'mod_articles_archive', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(201, 'mod_articles_latest', 'module', 'mod_articles_latest', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(202, 'mod_articles_popular', 'module', 'mod_articles_popular', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(203, 'mod_banners', 'module', 'mod_banners', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(204, 'mod_breadcrumbs', 'module', 'mod_breadcrumbs', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(205, 'mod_custom', 'module', 'mod_custom', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(206, 'mod_feed', 'module', 'mod_feed', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(207, 'mod_footer', 'module', 'mod_footer', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(208, 'mod_login', 'module', 'mod_login', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(209, 'mod_menu', 'module', 'mod_menu', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(210, 'mod_articles_news', 'module', 'mod_articles_news', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(211, 'mod_random_image', 'module', 'mod_random_image', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(212, 'mod_related_items', 'module', 'mod_related_items', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(213, 'mod_search', 'module', 'mod_search', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(214, 'mod_stats', 'module', 'mod_stats', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(215, 'mod_syndicate', 'module', 'mod_syndicate', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(216, 'mod_users_latest', 'module', 'mod_users_latest', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(217, 'mod_weblinks', 'module', 'mod_weblinks', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(218, 'mod_whosonline', 'module', 'mod_whosonline', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(219, 'mod_wrapper', 'module', 'mod_wrapper', '', 0, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(220, 'mod_articles_category', 'module', 'mod_articles_category', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(221, 'mod_articles_categories', 'module', 'mod_articles_categories', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(222, 'mod_languages', 'module', 'mod_languages', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(300, 'mod_custom', 'module', 'mod_custom', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(301, 'mod_feed', 'module', 'mod_feed', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(302, 'mod_latest', 'module', 'mod_latest', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(303, 'mod_logged', 'module', 'mod_logged', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(304, 'mod_login', 'module', 'mod_login', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(305, 'mod_menu', 'module', 'mod_menu', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(306, 'mod_online', 'module', 'mod_online', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(307, 'mod_popular', 'module', 'mod_popular', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(308, 'mod_quickicon', 'module', 'mod_quickicon', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(309, 'mod_status', 'module', 'mod_status', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(310, 'mod_submenu', 'module', 'mod_submenu', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(311, 'mod_title', 'module', 'mod_title', '', 1, 1, 1, 0, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(312, 'mod_toolbar', 'module', 'mod_toolbar', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(313, 'mod_unread', 'module', 'mod_unread', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(400, 'plg_authentication_gmail', 'plugin', 'gmail', 'authentication', 0, 0, 1, 0, '', '{"applysuffix":"0","suffix":"","verifypeer":"1","user_blacklist":""}', '', '', 0, '0000-00-00 00:00:00', 1, 0),
(401, 'plg_authentication_joomla', 'plugin', 'joomla', 'authentication', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(402, 'plg_authentication_ldap', 'plugin', 'ldap', 'authentication', 0, 0, 1, 0, '', '{"host":"","port":"389","use_ldapV3":"0","negotiate_tls":"0","no_referrals":"0","auth_method":"bind","base_dn":"","search_string":"","users_dn":"","username":"admin","password":"bobby7","ldap_fullname":"fullName","ldap_email":"mail","ldap_uid":"uid"}', '', '', 0, '0000-00-00 00:00:00', 3, 0),
(404, 'plg_content_emailcloak', 'plugin', 'emailcloak', 'content', 0, 1, 1, 0, '', '{"mode":"1"}', '', '', 0, '0000-00-00 00:00:00', 1, 0),
(405, 'plg_content_geshi', 'plugin', 'geshi', 'content', 0, 0, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0),
(406, 'plg_content_loadmodule', 'plugin', 'loadmodule', 'content', 0, 1, 1, 0, '', '{"style":"none"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(407, 'plg_content_pagebreak', 'plugin', 'pagebreak', 'content', 0, 1, 1, 1, '', '{"title":"1","multipage_toc":"1","showall":"1"}', '', '', 0, '0000-00-00 00:00:00', 4, 0),
(408, 'plg_content_pagenavigation', 'plugin', 'pagenavigation', 'content', 0, 1, 1, 1, '', '{"position":"1"}', '', '', 0, '0000-00-00 00:00:00', 5, 0),
(409, 'plg_content_vote', 'plugin', 'vote', 'content', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 6, 0),
(410, 'plg_editors_codemirror', 'plugin', 'codemirror', 'editors', 0, 1, 1, 1, '', '{"linenumbers":"0","tabmode":"indent"}', '', '', 0, '0000-00-00 00:00:00', 1, 0),
(411, 'plg_editors_none', 'plugin', 'none', 'editors', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0),
(412, 'plg_editors_tinymce', 'plugin', 'tinymce', 'editors', 0, 1, 1, 1, '', '{"mode":"1","skin":"0","compressed":"0","cleanup_startup":"0","cleanup_save":"2","entity_encoding":"raw","lang_mode":"0","lang_code":"en","text_direction":"ltr","content_css":"1","content_css_custom":"","relative_urls":"1","newlines":"0","invalid_elements":"script,applet,iframe","extended_elements":"","toolbar":"top","toolbar_align":"left","html_height":"550","html_width":"750","element_path":"1","fonts":"1","paste":"1","searchreplace":"1","insertdate":"1","format_date":"%Y-%m-%d","inserttime":"1","format_time":"%H:%M:%S","colors":"1","table":"1","smilies":"1","media":"1","hr":"1","directionality":"1","fullscreen":"1","style":"1","layer":"1","xhtmlxtras":"1","visualchars":"1","nonbreaking":"1","template":"1","blockquote":"1","wordcount":"1","advimage":"1","advlink":"1","autosave":"1","contextmenu":"1","inlinepopups":"1","safari":"0","custom_plugin":"","custom_button":""}', '', '', 0, '0000-00-00 00:00:00', 3, 0),
(413, 'plg_editors-xtd_article', 'plugin', 'article', 'editors-xtd', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0),
(414, 'plg_editors-xtd_image', 'plugin', 'image', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 2, 0),
(415, 'plg_editors-xtd_pagebreak', 'plugin', 'pagebreak', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 3, 0),
(416, 'plg_editors-xtd_readmore', 'plugin', 'readmore', 'editors-xtd', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 4, 0),
(417, 'plg_search_categories', 'plugin', 'categories', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(418, 'plg_search_contacts', 'plugin', 'contacts', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(419, 'plg_search_content', 'plugin', 'content', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(420, 'plg_search_newsfeeds', 'plugin', 'newsfeeds', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(421, 'plg_search_weblinks', 'plugin', 'weblinks', 'search', 0, 1, 1, 0, '', '{"search_limit":"50","search_content":"1","search_archived":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(422, 'plg_system_languagefilter', 'plugin', 'languagefilter', 'system', 0, 0, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0),
(423, 'plg_system_p3p', 'plugin', 'p3p', 'system', 0, 1, 1, 1, '', '{"headers":"NOI ADM DEV PSAi COM NAV OUR OTRo STP IND DEM"}', '', '', 0, '0000-00-00 00:00:00', 2, 0),
(424, 'plg_system_cache', 'plugin', 'cache', 'system', 0, 0, 1, 1, '', '{"browsercache":"0","cachetime":"15"}', '', '', 0, '0000-00-00 00:00:00', 3, 0),
(425, 'plg_system_debug', 'plugin', 'debug', 'system', 0, 1, 1, 0, '', '{"profile":"1","queries":"1","memory":"1","language_files":"1","language_strings":"1","strip-first":"1","strip-prefix":"","strip-suffix":""}', '', '', 0, '0000-00-00 00:00:00', 4, 0),
(426, 'plg_system_log', 'plugin', 'log', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 5, 0),
(427, 'plg_system_redirect', 'plugin', 'redirect', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 6, 0),
(428, 'plg_system_remember', 'plugin', 'remember', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 7, 0),
(429, 'plg_system_sef', 'plugin', 'sef', 'system', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 8, 0),
(430, 'plg_system_logout', 'plugin', 'logout', 'system', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 9, 0),
(431, 'plg_user_contactcreator', 'plugin', 'contactcreator', 'user', 0, 0, 1, 1, '', '{"autowebpage":"","category":"34","autopublish":"0"}', '', '', 0, '0000-00-00 00:00:00', 1, 0),
(432, 'plg_user_joomla', 'plugin', 'joomla', 'user', 0, 1, 1, 0, '', '{"autoregister":"1"}', '', '', 0, '0000-00-00 00:00:00', 2, 0),
(433, 'plg_user_profile', 'plugin', 'profile', 'user', 0, 0, 1, 1, '', '{"register-require_address1":"1","register-require_address2":"1","register-require_city":"1","register-require_region":"1","register-require_country":"1","register-require_postal_code":"1","register-require_phone":"1","register-require_website":"1","register-require_favoritebook":"1","register-require_aboutme":"1","register-require_tos":"1","register-require_dob":"1","profile-require_address1":"1","profile-require_address2":"1","profile-require_city":"1","profile-require_region":"1","profile-require_country":"1","profile-require_postal_code":"1","profile-require_phone":"1","profile-require_website":"1","profile-require_favoritebook":"1","profile-require_aboutme":"1","profile-require_tos":"1","profile-require_dob":"1"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(434, 'plg_extension_joomla', 'plugin', 'joomla', 'extension', 0, 1, 1, 1, '', '{}', '', '', 0, '0000-00-00 00:00:00', 1, 0),
(435, 'plg_content_joomla', 'plugin', 'joomla', 'content', 0, 1, 1, 0, '', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(500, 'atomic', 'template', 'atomic', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:6:"atomic";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"10/10/09";s:6:"author";s:12:"Ron Severdia";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"contact@kontentdesign.com";s:9:"authorUrl";s:28:"http://www.kontentdesign.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:26:"TPL_ATOMIC_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(502, 'bluestork', 'template', 'bluestork', '', 1, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:9:"bluestork";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"07/02/09";s:6:"author";s:12:"Ron Severdia";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"contact@kontentdesign.com";s:9:"authorUrl";s:28:"http://www.kontentdesign.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:29:"TPL_BLUESTORK_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"useRoundedCorners":"1","showSiteName":"0","textBig":"0","highContrast":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(503, 'beez_20', 'template', 'beez_20', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:7:"beez_20";s:4:"type";s:8:"template";s:12:"creationDate";s:16:"25 November 2009";s:6:"author";s:12:"Angie Radtke";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:23:"a.radtke@derauftritt.de";s:9:"authorUrl";s:26:"http://www.der-auftritt.de";s:7:"version";s:5:"1.6.0";s:11:"description";s:25:"TPL_BEEZ2_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"wrapperSmall":"53","wrapperLarge":"72","sitetitle":"","sitedescription":"","navposition":"center","templatecolor":"nature"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(504, 'hathor', 'template', 'hathor', '', 1, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:6:"hathor";s:4:"type";s:8:"template";s:12:"creationDate";s:8:"May 2010";s:6:"author";s:11:"Andrea Tarr";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:25:"hathor@tarrconsulting.com";s:9:"authorUrl";s:29:"http://www.tarrconsulting.com";s:7:"version";s:5:"1.6.0";s:11:"description";s:26:"TPL_HATHOR_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"showSiteName":"0","colourChoice":"0","boldText":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(505, 'beez5', 'template', 'beez5', '', 0, 1, 1, 0, 'a:11:{s:6:"legacy";b:0;s:4:"name";s:5:"beez5";s:4:"type";s:8:"template";s:12:"creationDate";s:11:"21 May 2010";s:6:"author";s:12:"Angie Radtke";s:9:"copyright";s:72:"Copyright (C) 2005 - 2010 Open Source Matters, Inc. All rights reserved.";s:11:"authorEmail";s:23:"a.radtke@derauftritt.de";s:9:"authorUrl";s:26:"http://www.der-auftritt.de";s:7:"version";s:5:"1.6.0";s:11:"description";s:25:"TPL_BEEZ5_XML_DESCRIPTION";s:5:"group";s:0:"";}', '{"wrapperSmall":"53","wrapperLarge":"72","sitetitle":"","sitedescription":"","navposition":"center","html5":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(600, 'English (United Kingdom)', 'language', 'en-GB', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(601, 'English (United Kingdom)', 'language', 'en-GB', '', 1, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(700, 'Joomla! CMS', 'file', 'joomla', '', 0, 1, 1, 1, '', '', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(10000, 'Yougrids', 'template', 'yougrids', '', 0, 1, 1, 0, '{"legacy":true,"name":"Yougrids","type":"template","creationDate":"10-01-2010","author":"Youjoomla LLC","copyright":"Youjoomla LLC","authorEmail":"youjoomlallc@gmail.com","authorUrl":"www.youjoomla.com","version":"1.0","description":"\\n\\t\\t<div id=\\"temp_desc\\"><h1 class=\\"admin_t_name\\">YouGrids Joomla! Template<\\/h1>\\n\\t\\t\\t<img src=\\"..\\/templates\\/yougrids\\/template_thumbnail.png\\" \\/><br \\/><br \\/>\\n\\t\\t\\t<h1 class=\\"admin_t_name\\">is proudly powered by<\\/h1>\\n\\t\\t\\t<a href=\\"http:\\/\\/www.yjsimplegrid.com\\" class=\\"modal\\" rel=\\"{handler: ''iframe'', size: {x: 800, y: 700}}\\">\\n\\t\\t\\t<span title=\\"YJSimpleGrid Joomla! Template Framework by Youjoomla.com\\">\\n\\t\\t\\t<img src=\\"..\\/templates\\/yougrids\\/images\\/admin\\/yjsgadmin_logo.png\\" border=\\"0\\" title=\\"\\" alt=\\"\\"\\/>\\n\\t\\t\\t<\\/span>\\n\\t\\t\\t<\\/a>\\n\\t\\t\\t\\n\\t\\t\\t<div id=\\"temp_det\\">      \\n\\t\\t\\t<h3>Version 1.0.10 Initial Release<\\/h3>\\n\\t\\t\\t\\n\\t\\t\\t<p>YouGrids Joomla  Template by Youjoomla.com. This is Free Template by Youjoomla.com. Visit <a href=\\"http:\\/\\/www.youjoomla.com\\">Youjoomla \\t\\t\\tTemlplates Club<\\/a> home page.\\n\\t\\t\\t<\\/p>\\n\\t\\t\\t<h3>Some of the YJSimpleGrid Template Framework Features are:<\\/h3>\\n\\t\\t\\t<ul>\\n\\t\\t\\t<li>Advanced Joomla Template Manager Admin Panel<\\/li>\\n\\t\\t\\t<li>Flexible Layouts<\\/li>\\n\\t\\t\\t<li>Over 52 collapsible module positions easily add more<\\/li>\\n\\t\\t\\t<li>Automatic module width adjustment in template manager<\\/li>\\n\\t\\t\\t<li>Native RTL<\\/li>\\n\\t\\t\\t<li>Native Mobile devices support<\\/li>\\n\\t\\t\\t<li>Custom iPhone\\/iPad\\/iPod and handheld devices template<\\/li>\\n\\t\\t\\t<li>Cross browser support<\\/li>\\n\\t\\t\\t<li>Native Cufon and Google Font plus 15 CSS font styles<\\/li>\\n\\t\\t\\t<li>Native Jquery Support<\\/li>\\n\\t\\t\\t<li>Google Analytics website tracking built in<\\/li>\\n\\t\\t\\t<li>Native rounded and square module styles 1 click switch<\\/li>\\n\\t\\t\\t<li>1 image PNG transparent rounded corners approach for easy editing<\\/li>\\n\\t\\t\\t<li>Component off switch or menu item assignment<\\/li>\\n\\t\\t\\t<li>Topmenu off switch or menu item assignment<\\/li>\\n\\t\\t\\t<li>7 default module grids with 5 modules each. Easily add more<\\/li>\\n\\t\\t\\t<li>Flexible logo and header size changer<\\/li>\\n\\t\\t\\t<li>Upgraded server side compression<\\/li>\\n\\t\\t\\t<li>Frontpage news items number of  characters control<\\/li>\\n\\t\\t\\t<li>Visible RTL switch and Font resizer<\\/li>\\n\\t\\t\\t<li>5 menu styles<\\/li>\\n\\t\\t\\t<li>Show page title under menu link<\\/li>\\n\\t\\t\\t<li>Custom error and offline pages<\\/li>\\n\\t\\t\\t<li>Custom user pages ( com_user )<\\/li>\\n\\t\\t\\t<li>Slide to top smothscroll<\\/li>\\n\\t\\t\\t<li>XHTML , CSS and JS valid<\\/li>\\n\\t\\t\\t<\\/ul>\\n\\t\\t\\t<h3>For additional documentation please visit <a href=\\"http:\\/\\/www.yjsimplegrid.com\\" target=\\"_blank\\">YJSimpleGrid<\\/a><\\/h3>\\n\\t\\t\\t\\n\\t\\t\\t<\\/div>\\n\\t\\t\\t<\\/div>\\n\\t","group":""}', '{"":"TOP_MENU_OFF_YJ_LABEL","STYLE_SETTINGS_TAB":"STYLE_SETTINGS_TAB","custom_css":"2","STTEXT_LABEL":"STTEXT_LABEL","STYLE_SUB":"STYLE_SUB","default_color":"metal","default_font":"3","default_font_family":"6","selectors_override":"2","selectors_override_type":"1","css_font_family":"12","google_font_family":"2","cufon_font_family":"1","affected_selectors":"div.title h1,div.title h2,div.componentheading, h1,h2,h3,h4,h5,h6,.yjround h4,.yjsquare h4","LOGO_SUB":"LOGO_SUB","LGTEXT_LABEL":"LGTEXT_LABEL","LOGO_YJ_TITLE":"LOGO_YJ_TITLE","logo_height":"125px","logo_width":"300px","turn_logo_off":"2","turn_header_block_off":"2","TOP_MENU_SUB":"TOP_MENU_SUB","TMTEXT_LABEL":"TMTEXT_LABEL","TOP_MENU_YJ_LABEL":"TOP_MENU_YJ_LABEL","menuName":"mainmenu","default_menu_style":"2","sub_width":"280px","yjsg_menu_offset":"95","turn_topmenu_off":"","DEF_GRID_SUB":"DEF_GRID_SUB","DGTEXT_LABEL":"DGTEXT_LABEL","MAINB_YJ_LABEL":"MAINB_YJ_LABEL","css_widthdefined":"px","css_width":"1000","site_layout":"2","MBC_W_LABEL":"MBC_W_LABEL","widthdefined":"%","maincolumn":"55","insetcolumn":"0","leftcolumn":"22.5","rightcolumn":"22.5","SPII_LABEL":"SPII_LABEL","widthdefined_itmid":"%","maincolumn_itmid":"55","insetcolumn_itmid":"0","leftcolumn_itmid":"22.5","rightcolumn_itmid":"22.5","define_itemid":"","MG_SUB":"MG_SUB","MGTEXT_LABEL":"MGTEXT_LABEL","yjsg_1_width":"20|20|20|20|20","yjsg_header_width":"33|33|33","yjsg_2_width":"20|20|20|20|20","yjsg_3_width":"20|20|20|20|20","yjsg_4_width":"20|20|20|20|20","yjsg_bodytop_width":"33|33|33","yjsg_yjsgbodytbottom_width":"33|33|33","yjsg_5_width":"20|20|20|20|20","yjsg_6_width":"20|20|20|20|20","yjsg_7_width":"20|20|20|20|20","MOBILE_SUB":"MOBILE_SUB","MOBILE_TXT_LABEL":"MOBILE_TXT_LABEL","iphone_default":"1","android_default":"1","handheld_default":"2","mobile_reg":"1","ADD_F_SUB":"ADD_F_SUB","ADTEXT_LABEL":"ADTEXT_LABEL","GA_YJ_LABEL":"GA_YJ_LABEL","GATEXT_LABEL":"GATEXT_LABEL","ga_switch":"0","GAcode":"UA-xxxxxx-x","NOT_YJ_LABEL":"NOT_YJ_LABEL","compress":"0","ie6notice":"0","nonscript":"0","ST_YJ_LABEL":"ST_YJ_LABEL","show_tools":"1","show_fres":"1","show_rtlc":"1","TDIR_YJ_LABEL":"TDIR_YJ_LABEL","text_direction":"2","SEO_YJ_LABEL":"SEO_YJ_LABEL","turn_seo_off":"2","seo":"Your description goes here","tags":"Your keywords go here","COPY_YJ_LABEL":"COPY_YJ_LABEL","branding_off":"2","joomlacredit_off":"2","ADV_SUB":"ADV_SUB","ADVTEXT_LABEL":"ADVTEXT_LABEL","FPC_YJ_LABEL":"FPC_YJ_LABEL","fp_controll_switch":"2","fp_chars_limit":"3000","fp_after_text":"","SCRIPT_YJ_LABEL":"SCRIPT_YJ_LABEL","JQ_LABEL":"JQ_LABEL","jq_switch":"2","SMS_YJ_LABEL":"SMS_YJ_LABEL","MSTEXT_LABEL":"MSTEXT_LABEL","YJsg1_module_style":"YJsgxhtml","YJsgh_module_style":"YJsgxhtml","YJsg2_module_style":"YJsgxhtml","YJsg3_module_style":"YJsgxhtml","YJsg4_module_style":"YJsgxhtml","YJsgmt_module_style":"YJsgxhtml","YJsgl_module_style":"YJsgxhtml","YJsgr_module_style":"YJsgxhtml","YJsgi_module_style":"YJsgxhtml","YJsgit_module_style":"YJsgxhtml","YJsgib_module_style":"YJsgxhtml","YJsgmb_module_style":"YJsgxhtml","YJsg5_module_style":"YJsgxhtml","YJsg6_module_style":"YJsgxhtml","YJsg7_module_style":"YJsgxhtml","CP_LABEL":"CP_LABEL","component_switch":"","admin_css_time":"0"}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(10001, 'System - YJMegaMenu', 'plugin', 'YJMegaMenu', 'system', 0, 0, 1, 0, '{"legacy":false,"name":"System - YJMegaMenu","type":"plugin","creationDate":"January 2011","author":"YouJoomla LLC","copyright":"Copyright (C) 2007 - 2011 Youjoomla LLC. All rights reserved.","authorEmail":"youjoomlallc@gmail.com","authorUrl":"www.www.youjoomla.com","version":"1.6","description":"Provides YJMegaMenu Parameters Tab in MainMenu Administration!","group":""}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(10002, 'jtaghelpdesk', 'component', 'com_jtaghelpdesk', '', 0, 1, 0, 0, '{"legacy":true,"name":"JTag Helpdesk","type":"component","creationDate":"06\\/08\\/2009","author":"Andrey Kvasnevskiy, Aleksey Pakholkov, M.Salman","copyright":"This component is released under the GNU\\/GPL License","authorEmail":"helpdesk@joomlatag.com","authorUrl":"www.joomlatag.com","version":"1.0.11 ","description":"Joomlatag Helpdesk Ticketing System Free","group":""}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0),
(10003, 'huruhelpdesk', 'component', 'com_huruhelpdesk', '', 0, 1, 0, 0, '{"legacy":true,"name":"Huru Helpdesk","type":"component","creationDate":"October 2010","author":"James R. Erickson","copyright":"James R. Erickson, 2010","authorEmail":"james.r.erickson@gmail.com","authorUrl":"","version":"0.88 (d) beta","description":"\\n\\t\\t\\n\\t\\t\\t<p><b>Huru Helpdesk<\\/b><\\/p>\\n\\t\\t\\t\\n\\t\\t\\t<p>Version 0.88 (d) beta<\\/p>\\n\\n\\t\\t\\t<p>Huru Helpdesk is based on the ASP application Liberum Helpdesk (<a href=\\"http:\\/\\/www.liberum.org\\">www.liberum.org<\\/a>). \\n\\t\\t\\tMany thanks to the developers of Liberum<\\/p>\\n\\t\\t\\n\\t","group":""}', '{}', '', '', 0, '0000-00-00 00:00:00', 0, 0);

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `jos_hd_category`
--

INSERT INTO `jos_hd_category` (`catid`, `name`, `description`, `image`) VALUES
(1, 'Default Category', 'If there are no other suitable categories submit your tickets here ;)', NULL);

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `jos_hd_groups`
--

INSERT INTO `jos_hd_groups` (`grpid`, `name`, `image`, `userpermissions`) VALUES
(1, 'user', 'components/com_jtaghelpdesk/images/mdn_userSmall.jpg', '----'),
(2, 'advisor', 'components/com_jtaghelpdesk/images/mdn_userSmallGreen.jpg', 'V---'),
(3, 'administrator', 'components/com_jtaghelpdesk/images/mdn_userSmallRed.jpg', 'VMED');

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

INSERT INTO `jos_hd_permissions` (`grpid`, `catid`, `type`) VALUES
(1, 1, 'vmrcd---'),
(2, 1, 'VmRCDPAO'),
(3, 1, 'VmRCDPAO');

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

INSERT INTO `jos_hd_settings` (`name`, `value`) VALUES
('iconset', 'mdn_'),
('notifyemail', ''),
('highlight', '!'),
('notifyusers', '0'),
('enhighlight', '1'),
('ticketsfront', '5'),
('ticketssub', '15'),
('sourceemail', ''),
('msgbox', 'none'),
('lang', 'english.php'),
('name', 'Joomlatag Helpdesk Ticketing System'),
('newpostmsg', 'a new message has arrived:'),
('newpostmsg1', 'a new message has arrived:'),
('newpostmsg2', ''),
('newpostmsg3', ''),
('users', '0'),
('agree', '0'),
('agreei', ''),
('agreelw', 'You must agree to the following terms to use this system'),
('agreen', 'agreement'),
('agreela', 'If you have read the terms please continue'),
('agreeb', 'continue'),
('view', 'a'),
('msgboxh', '10'),
('msgboxw', '58'),
('msgboxt', '1'),
('dorganisation', 'individual'),
('copyright', 'Joomlatag Helpdesk Ticketing System'),
('date', 'j-M-Y (h:i)'),
('defaultmsg', 'type here...'),
('dateshort', 'j-M-Y'),
('assignname', 'Assigned Tickets'),
('assigndescription', 'Tickets assigned to you to answer'),
('assignimage', ''),
('versionmajor', '1'),
('timezone', '+0000'),
('notifyadmin', '0'),
('versionminor', '0'),
('versionpatch', '11'),
('css', 'disable'),
('versionname', 'stable'),
('upgrade', '0'),
('userdefault', '1'),
('usersimport', '0'),
('debug', '0'),
('debugmessage', 'Continue >>');

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `jos_huruhelpdesk_categories`
--


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

INSERT INTO `jos_huruhelpdesk_config` (`id`, `hdreply`, `hdurl`, `notifyuser`, `enablekb`, `defaultpriority`, `defaultstatus`, `closestatus`, `allowanonymous`, `defaultlang`, `pagerpriority`, `userselect`, `version`, `show_username`, `show_email`, `show_department`, `show_location`, `show_phone`, `show_category`, `show_status`, `show_priority`, `show_rep`, `show_timespent`, `set_username`, `set_email`, `set_department`, `set_location`, `set_phone`, `set_category`, `set_status`, `set_priority`, `set_rep`, `set_timespent`, `hdnotifyname`, `defaultdepartment`, `defaultcategory`, `defaultrep`, `fileattach_allow`, `fileattach_allowedextensions`, `fileattach_allowedmimetypes`, `fileattach_maxsize`, `fileattach_type`, `fileattach_path`, `fileattach_download`, `fileattach_maxage`, `notifyadminonnewcases`) VALUES
(1, 'helpdesk@domain.com', 'http://server.domain.com/', 1, 1, 3, 15, 24, 1, 1, 10, 0, '0.88 beta', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 50, 50, 50, 'Huru Helpdesk', -1, -1, -1, 10000, '.jpg,.png', 'image/jpeg,image/png', 1048576, 1, '', 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_huruhelpdesk_departments`
--

CREATE TABLE `jos_huruhelpdesk_departments` (
  `department_id` bigint(20) NOT NULL auto_increment,
  `dname` text NOT NULL,
  UNIQUE KEY `department_id` (`department_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `jos_huruhelpdesk_departments`
--


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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `jos_huruhelpdesk_emailmsg`
--

INSERT INTO `jos_huruhelpdesk_emailmsg` (`id`, `type`, `subject`, `body`) VALUES
(1, 'repclose', 'HELPDESK: Problem [problemid] Closed', 'The following problem has been closed.  You can view the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nSOLUTION\r\n--------\r\n[solution]'),
(2, 'repnew', 'HELPDESK: Problem [problemid] Assigned', 'The following problem has been assigned to you.  You can update the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nUSER INFORMATION\r\n----------------\r\nUsername: [uid]\r\nEmail: [uemail]\r\nPhone: [phone]\r\nLocation: [location]\r\nDepartment: [department]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]'),
(3, 'reppager', 'HELPDESK: Problem [problemid] Assigned/Updated', 'Title:[title]\r\nUser:[uid]\r\nPriority:[priority]'),
(4, 'repupdate', 'HELPDESK: Problem [problemid] Updated', 'The following problem has been updated.  You can view the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]\r\n\r\nNOTES\r\n-----------\r\n[notes]'),
(5, 'userclose', 'HELPDESK: Problem [problemid] Closed', 'Your help desk problem has been closed.  You can view the solution below or at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nSOLUTION\r\n--------\r\n[solution]'),
(6, 'usernew', 'HELPDESK: Problem [problemid] Created', 'Thank you for submitting your problem to the help desk.  You can view or update the problem at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]'),
(7, 'userupdate', 'HELPDESK: Problem [problemid] Updated', 'Your help desk problem has been updated.  You can view the problem at: [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nUser: [uid]\r\nDate: [startdate]\r\nTitle: [title]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]\r\n\r\nNOTES\r\n-----------\r\n[notes]'),
(8, 'adminnew', 'HELPDESK: Problem [problemid] Created', 'The following problem has been created.  You can update the problem at [url]\r\n\r\nPROBLEM DETAILS\r\n---------------\r\nID: [problemid]\r\nDate: [startdate]\r\nTitle: [title]\r\nPriority: [priority]\r\nCategory: [category]\r\n\r\nUSER INFORMATION\r\n----------------\r\nFullname: [fullname]\r\nUsername: [uid]\r\nEmail: [uemail]\r\nPhone: [phone]\r\nLocation: [location]\r\nDepartment: [department]\r\n\r\nDESCRIPTION\r\n-----------\r\n[description]');

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=581 ;

--
-- Dumping data for table `jos_huruhelpdesk_langstrings`
--

INSERT INTO `jos_huruhelpdesk_langstrings` (`id`, `lang_id`, `variable`, `langtext`) VALUES
(54, 1, 'Classification', 'Classification'),
(59, 1, 'Close', 'Close'),
(60, 1, 'CloseDate', 'Close Date'),
(72, 1, 'ContactInformation', 'Contact Information'),
(79, 1, 'DateSubmitted', 'Entered'),
(91, 1, 'Department', 'Department'),
(95, 1, 'Description', 'Description'),
(104, 1, 'EditInformation', 'Edit your contact information'),
(108, 1, 'EMail', 'E-Mail'),
(109, 1, 'EmailAddress', 'E-Mail Address'),
(120, 1, 'EndDate', 'End Date'),
(121, 1, 'EnterAdditionalNotes', 'Enter Additional Notes'),
(125, 1, 'EnteredBy', 'Entered By'),
(126, 1, 'EnterinKnowledgeBase', 'Enter in Knowledge Base'),
(135, 1, 'From', 'From'),
(142, 1, 'HideFromEndUser', 'Hide new note from end user'),
(144, 1, 'ID', 'ID'),
(146, 1, 'In', 'In'),
(148, 1, 'InOutBoard', 'View In/Out Board'),
(174, 1, 'Location', 'Location'),
(191, 1, 'minutes', 'minutes'),
(199, 1, 'NewProblem', 'New Problem'),
(207, 1, 'Noresultsfound', 'No matching problems found'),
(210, 1, 'Notes', 'Notes'),
(218, 1, 'OpenProblems', 'Open problems'),
(219, 1, 'OpenProblemsfor', 'Open problems for'),
(222, 1, 'Or', 'Or'),
(225, 1, 'Out', 'Out'),
(237, 1, 'Phone', 'Phone'),
(248, 1, 'Priority', 'Priority'),
(257, 1, 'ProblemID', 'View Problem #'),
(259, 1, 'ProblemInformation', 'Problem Information'),
(263, 1, 'Problems', 'Problems'),
(277, 1, 'ReopenProblem', 'Reopen'),
(278, 1, 'Rep', 'Rep'),
(282, 1, 'Reports', 'Reports'),
(286, 1, 'Required', 'Required'),
(292, 1, 'Save', 'Save'),
(294, 1, 'Search', 'Search'),
(296, 1, 'SearchFields', 'Search Fields'),
(297, 1, 'SearchProblems', 'Search problems'),
(298, 1, 'SearchResults', 'Search Results'),
(299, 1, 'SearchtheKnowledgeBase', 'Search the knowledgebase'),
(302, 1, 'SelectCategory', 'Select Category'),
(303, 1, 'SelectDepartment', 'Select Department'),
(304, 1, 'SelectUser', 'Select User'),
(31, 1, 'AssignedTo', 'Assigned To'),
(313, 1, 'Solution', 'Solution'),
(317, 1, 'StartDate', 'Start Date'),
(319, 1, 'Status', 'Status'),
(329, 1, 'Subject', 'Subject'),
(330, 1, 'Submit', 'Submit'),
(332, 1, 'SubmitNewProblem', 'Submit new problem'),
(335, 1, 'SupportRep', 'Support Rep'),
(352, 1, 'Time', 'Time'),
(353, 1, 'TimeSpent', 'Time Spent'),
(354, 1, 'Title', 'Title'),
(356, 1, 'Total', 'Total'),
(373, 1, 'User', 'User'),
(376, 1, 'UserName', 'User Name'),
(385, 1, 'View', 'View'),
(386, 1, 'ViewProblemList', 'View all open problems'),
(387, 1, 'Viewproblemsfor', 'View open problems assigned to'),
(394, 1, 'ViewAssignedProblems', 'View assigned problems'),
(395, 1, 'ViewSubmittedProblems', 'View submitted problems'),
(397, 1, 'ViewProblemsFromLast', 'View all problems from last'),
(398, 1, 'days', 'days'),
(399, 1, 'Activity', 'Activity'),
(400, 1, 'Home', 'Home'),
(401, 1, 'Refresh', 'Refresh'),
(402, 1, 'NoLimit', '(No limit)'),
(403, 1, 'Back', 'Back'),
(404, 1, 'ProblemNumber', 'Problem #'),
(405, 1, 'ProblemSaved', 'Problem saved'),
(406, 1, 'ErrorSavingProblem', 'Error saving record: Invalid or missing required fields.'),
(409, 1, 'DefaultRep', 'Default Rep'),
(410, 1, 'NotFound', 'No matching problem found'),
(411, 1, 'EnterVerification', 'Verification'),
(412, 1, 'Name', 'Full Name'),
(413, 1, 'Admin', 'Admin'),
(414, 1, 'ShowReps', 'Show Reps'),
(415, 1, 'ShowAll', 'Show All'),
(416, 1, 'RepsAdmins', 'Reps & Admins Only'),
(417, 1, 'AllUsers', 'All Huru Users'),
(418, 1, 'SearchCriteria', 'Search Criteria'),
(419, 1, 'Reset', 'Reset'),
(421, 1, 'To', 'To'),
(422, 1, 'SearchText', 'Search Text'),
(423, 1, 'Browse', 'Browse'),
(424, 1, 'Cancel', 'Cancel'),
(425, 1, 'NewSearch', 'New Search'),
(426, 1, 'Results', 'Results'),
(427, 1, 'ProblemsFound', 'problem(s) found'),
(428, 1, 'EnterSearch', 'Enter your search criteria & click the Search button'),
(429, 1, 'EnterReport', 'Enter your report criteria & click the View button'),
(430, 1, 'AvailableReports', 'Available Reports'),
(431, 1, 'DateRange', 'Date Range'),
(432, 1, 'AverageTime', 'Average Time'),
(433, 1, 'PercentProblemTotal', '% of Problems'),
(434, 1, 'PercentTimeTotal', '% of Time'),
(435, 1, 'min', 'min'),
(436, 1, 'Unknown', 'Unknown'),
(437, 1, 'ActivitySummary', 'MIS Activity Summary'),
(438, 1, 'Modified', 'Modified'),
(439, 1, 'through', 'through'),
(440, 1, 'MailProblemID', 'Problem Id Number'),
(441, 1, 'MailTitle', 'Problem Title/Subject'),
(442, 1, 'MailDescription', 'Problem Description'),
(443, 1, 'MailUID', 'Username of person who reported problem'),
(444, 1, 'MailUEmail', 'Email address of person who reported problem'),
(445, 1, 'MailPhone', 'Phone number of person who reported problem'),
(446, 1, 'MailLocation', 'Location of person who reported problem'),
(447, 1, 'MailDepartment', 'Department of person who reported problem'),
(448, 1, 'MailPriority', 'Priority of problem'),
(449, 1, 'MailCategory', 'Category of problem'),
(45, 1, 'Category', 'Category'),
(450, 1, 'MailStartDate', 'Date problem was reported/opened'),
(451, 1, 'MailURL', 'URL to problem'),
(452, 1, 'MailSolution', 'Problem solution'),
(453, 1, 'MailNotes', 'Notes about problem'),
(454, 1, 'ProblemsSubmittedBy', 'Problems submitted by'),
(456, 1, 'for', 'for'),
(457, 1, 'ForPrevious', 'for previous'),
(458, 1, 'All', 'All'),
(459, 1, 'OpenProblemsLC', 'open problems'),
(460, 1, 'Print', 'Print'),
(461, 1, 'UserProfile', 'User Profile'),
(462, 1, 'JoomlaUserInfo', 'Joomla! user information'),
(463, 1, 'HuruUserInfo', 'Helpdesk User Profile'),
(464, 1, 'HomePhone', 'Home Phone'),
(465, 1, 'MobilePhone', 'Mobile Phone'),
(466, 1, 'PagerAddress', 'Pager Address'),
(467, 1, 'Location1', 'Location 1'),
(468, 1, 'Location2', 'Location 2'),
(469, 1, 'Language', 'Language'),
(470, 1, 'ManageCategories', 'Manage Categories'),
(471, 1, 'EditCategory', 'Edit Category'),
(472, 1, 'CategoryName', 'Category Name'),
(473, 1, 'Default', 'Default'),
(474, 1, 'GeneralConfiguration', 'General Configuration'),
(475, 1, 'ReplyAddress', 'Reply Address'),
(476, 1, 'BaseURL', 'Helpdesk Base URL'),
(477, 1, 'NotifyUserOnCaseUpdate', 'Notify User on Case Update'),
(478, 1, 'AllowAnonymousCases', 'Allow Anonymous Cases'),
(479, 1, 'AllowUserSelectOnNewCases', 'Allow User Select on New Cases'),
(480, 1, 'KnowledgeBaseViewAuthority', 'Knowledgebase View Authority'),
(481, 1, 'Disable', 'Disable'),
(482, 1, 'RepsOnly', 'Reps Only'),
(483, 1, 'UsersAndReps', 'Users & Reps'),
(484, 1, 'Anyone', 'Anyone'),
(485, 1, 'DefaultPriority', 'Default Priority'),
(486, 1, 'PagerPriority', 'Pager Priority'),
(487, 1, 'DefaultStatus', 'Default Status'),
(488, 1, 'ClosedStatus', 'Closed Status'),
(489, 1, 'DefaultLanguage', 'Default Language'),
(490, 1, 'EmailMessages', 'Email Messages'),
(491, 1, 'Users', 'Users'),
(492, 1, 'Departments', 'Departments'),
(493, 1, 'Categories', 'Categories'),
(494, 1, 'Priorities', 'Priorities'),
(495, 1, 'Statuses', 'Statuses'),
(496, 1, 'Languages', 'Languages'),
(497, 1, 'About', 'About'),
(498, 1, 'Administration', 'Administration'),
(499, 1, 'ManageDepartments', 'Manage Departments'),
(500, 1, 'DepartmentName', 'Department Name'),
(501, 1, 'EditDepartment', 'Edit Department'),
(502, 1, 'ManageEmailMessages', 'Manage Email Messages'),
(503, 1, 'Type', 'Type'),
(504, 1, 'Body', 'Body'),
(505, 1, 'Edit...', 'Edit...'),
(506, 1, 'EditEmailMessage', 'Edit Email Message'),
(507, 1, 'AvailableSubstitutions', 'Available Substitutions'),
(508, 1, 'ManageLanguages', 'Manage Languages'),
(509, 1, 'LanguageName', 'Language Name'),
(510, 1, 'Localized', 'Localized'),
(511, 1, 'EditLanguage', 'Edit Language'),
(512, 1, 'LanguageStrings', 'Language Strings'),
(513, 1, 'ManagePriorities', 'Manage Priorities'),
(514, 1, 'PriorityName', 'Priority Name'),
(515, 1, 'EditPriority', 'Edit Priority'),
(516, 1, 'ManageStatuses', 'Manage Statuses'),
(517, 1, 'Rank', 'Rank'),
(518, 1, 'StatusName', 'Status Name'),
(519, 1, 'EditStatus', 'Edit Status'),
(520, 1, 'ManageLanguageStrings', 'Manage Language Strings'),
(521, 1, 'LanguageID', 'Language ID'),
(522, 1, 'Variable', 'Variable'),
(523, 1, 'Text', 'Text'),
(524, 1, 'EditString', 'Edit String'),
(525, 1, 'ManageUsers', 'Manage Users'),
(526, 1, 'HuruID', 'Huru ID'),
(527, 1, 'JoomlaID', 'Joomla! ID'),
(528, 1, 'SyncJoomlaUsers', 'Sync Joomla! Users'),
(529, 1, 'SyncJoomlaUsersConfirm', 'This will synchronize the Huru users table with the Joomla! users table - importing accounts into Huru as necessary.  No Joomla! user accounts will be altered.'),
(530, 1, 'EditUser', 'Edit User'),
(531, 1, 'IsUser', 'User'),
(532, 1, 'IsRep', 'Rep'),
(533, 1, 'IsAdmin', 'Administrator'),
(534, 1, 'ViewReports', 'View Reports'),
(535, 1, 'UserSuperAdminNote', '(Note: This setting is ignored for Joomla! Super Administrators - who are always Huru Helpdesk Admins)'),
(536, 1, 'DefaultAssignment', 'Category Default'),
(537, 1, 'PageTitle', 'Huru Helpdesk'),
(538, 1, 'SelectOverride', 'Overrides contact info above'),
(539, 1, 'CannotDeleteClosedStatus', 'Cannot delete status set as Closed Status in General Confguration'),
(540, 1, 'Go', 'Go'),
(541, 1, 'ProblemDeleted', 'Problem deleted'),
(542, 1, 'ProblemNotDeleted', 'Error deleting problem'),
(543, 1, 'DeleteProblem', 'Delete Problem #'),
(544, 1, 'Delete', 'Delete'),
(545, 1, 'ProblemCreated', 'Problem created'),
(546, 1, 'AttachFileToNote', 'Attach file to note'),
(547, 1, 'Attachment', 'Attachment'),
(548, 1, 'AttachmentFileNoteFound', 'Attachment file not found'),
(549, 1, 'DefaultFileAttachmentNote', 'File attached'),
(550, 1, 'ErrorSavingAttachment', 'Error saving attachment'),
(551, 1, 'NotImplemented', 'Not implemented'),
(552, 1, 'FileTypeNotAllowed', 'File type not allowed'),
(553, 1, 'FileTooLarge', 'File too large'),
(554, 1, 'UnknownError', 'Unknown error'),
(555, 1, 'NotificationSenderName', 'Notification Sender Name'),
(556, 1, 'AllowFileAttachments', 'Allow File Attachments to Cases'),
(557, 1, 'AllowedAttachmentExtensions', 'Allowed Attachment Extensions'),
(558, 1, 'ExtensionExample', 'Comma separated list of allowed file extensions (with leading periods) [e.g: .jpg,.png,.txt]'),
(559, 1, 'MaximumAttachmentSize', 'Maximum Attachment Size'),
(560, 1, 'Bytes', 'bytes'),
(561, 1, 'AttachmentSizeWarning', 'Huru maximum is 16MB.  PHP may be configured for less.  Check your php.ini file'),
(562, 1, 'AttachmentDownloadPermissions', 'Allow attachment download'),
(563, 1, 'AttachmentDeleted', 'Attachment deleted'),
(564, 1, 'AttachmentNotDeleted', 'Error deleting attachment'),
(565, 1, 'MaximumAttachmentAge', 'Auto-purge old attachments after'),
(566, 1, 'SetToZero', 'Set to 0 to disable auto-purge'),
(567, 1, 'MailFullname', 'Full name of user who entered case (from Joomla account)'),
(568, 1, 'NotifyAdminOnNewCase', 'Email address to notify for all new cases'),
(569, 1, 'LeaveBlank', 'Leave blank to disable'),
(570, 1, 'Notifications', 'Notifications'),
(571, 1, 'Permissions', 'Permissions'),
(572, 1, 'Defaults', 'Defaults'),
(573, 1, 'FileAttachments', 'File Attachments'),
(574, 1, 'DisplayedFields', 'Displayed Fields'),
(575, 1, 'DefaultDepartment', 'Default Department'),
(576, 1, 'DefaultCategory', 'Default Category'),
(577, 1, 'Show', 'Show'),
(578, 1, 'Set', 'Set'),
(579, 1, 'IfNotSetable', 'If not visible/setable by everyone, a default for this field must be set above'),
(580, 1, 'Updated', 'Updated');

-- --------------------------------------------------------

--
-- Table structure for table `jos_huruhelpdesk_language`
--

CREATE TABLE `jos_huruhelpdesk_language` (
  `id` bigint(20) NOT NULL auto_increment,
  `langname` text NOT NULL,
  `localized` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `jos_huruhelpdesk_language`
--

INSERT INTO `jos_huruhelpdesk_language` (`id`, `langname`, `localized`) VALUES
(1, 'English', 'English');

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `jos_huruhelpdesk_notes`
--


-- --------------------------------------------------------

--
-- Table structure for table `jos_huruhelpdesk_priority`
--

CREATE TABLE `jos_huruhelpdesk_priority` (
  `priority_id` bigint(20) NOT NULL auto_increment,
  `pname` text NOT NULL,
  UNIQUE KEY `priority_id` (`priority_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `jos_huruhelpdesk_priority`
--

INSERT INTO `jos_huruhelpdesk_priority` (`priority_id`, `pname`) VALUES
(6, ' 6 - VERY HIGH '),
(5, ' 5 - HIGH '),
(4, ' 4 - ELEVATED '),
(3, ' 3 - NORMAL '),
(2, ' 2 - LOW '),
(1, '1 - VERY LOW'),
(10, ' 10 - EMERGENCY - PAGE '),
(9, ' 9 - EMERGENCY - NO PAGE ');

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `jos_huruhelpdesk_problems`
--


-- --------------------------------------------------------

--
-- Table structure for table `jos_huruhelpdesk_status`
--

CREATE TABLE `jos_huruhelpdesk_status` (
  `id` int(11) NOT NULL auto_increment,
  `status_id` bigint(20) NOT NULL,
  `sname` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

--
-- Dumping data for table `jos_huruhelpdesk_status`
--

INSERT INTO `jos_huruhelpdesk_status` (`id`, `status_id`, `sname`) VALUES
(22, 65, 'TESTING'),
(21, 63, 'WAITING'),
(20, 60, 'HOLD'),
(19, 55, 'ESCALATED'),
(18, 50, 'IN PROGRESS'),
(17, 20, 'OPEN'),
(16, 10, 'RECEIVED'),
(15, 1, 'NEW'),
(24, 100, 'CLOSED');

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `jos_huruhelpdesk_users`
--


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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `jos_languages`
--

INSERT INTO `jos_languages` (`lang_id`, `lang_code`, `title`, `title_native`, `sef`, `image`, `description`, `metakey`, `metadesc`, `published`) VALUES
(1, 'en-GB', 'English (UK)', 'English (UK)', 'en', 'en', '', '', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `jos_menu`
--

CREATE TABLE `jos_menu` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=104 ;

--
-- Dumping data for table `jos_menu`
--

INSERT INTO `jos_menu` (`id`, `menutype`, `title`, `alias`, `note`, `path`, `link`, `type`, `published`, `parent_id`, `level`, `component_id`, `ordering`, `checked_out`, `checked_out_time`, `browserNav`, `access`, `img`, `template_style_id`, `params`, `lft`, `rgt`, `home`, `language`, `client_id`) VALUES
(1, '', 'Menu_Item_Root', 'root', '', '', '', '', 1, 0, 0, 0, 0, 0, '0000-00-00 00:00:00', 0, 0, '', 0, '', 0, 45, 0, '*', 0),
(2, 'menu', 'com_banners', 'Banners', '', 'Banners', 'index.php?option=com_banners', 'component', 0, 1, 1, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners', 0, '', 1, 10, 0, '*', 1),
(3, 'menu', 'com_banners', 'Banners', '', 'Banners/Banners', 'index.php?option=com_banners', 'component', 0, 2, 2, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners', 0, '', 2, 3, 0, '*', 1),
(4, 'menu', 'com_banners_categories', 'Categories', '', 'Banners/Categories', 'index.php?option=com_categories&extension=com_banners', 'component', 0, 2, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners-cat', 0, '', 4, 5, 0, '*', 1),
(5, 'menu', 'com_banners_clients', 'Clients', '', 'Banners/Clients', 'index.php?option=com_banners&view=clients', 'component', 0, 2, 2, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners-clients', 0, '', 6, 7, 0, '*', 1),
(6, 'menu', 'com_banners_tracks', 'Tracks', '', 'Banners/Tracks', 'index.php?option=com_banners&view=tracks', 'component', 0, 2, 2, 4, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:banners-tracks', 0, '', 8, 9, 0, '*', 1),
(7, 'menu', 'com_contact', 'Contacts', '', 'Contacts', 'index.php?option=com_contact', 'component', 0, 1, 1, 8, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:contact', 0, '', 11, 16, 0, '*', 1),
(8, 'menu', 'com_contact', 'Contacts', '', 'Contacts/Contacts', 'index.php?option=com_contact', 'component', 0, 7, 2, 8, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:contact', 0, '', 12, 13, 0, '*', 1),
(9, 'menu', 'com_contact_categories', 'Categories', '', 'Contacts/Categories', 'index.php?option=com_categories&extension=com_contact', 'component', 0, 7, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:contact-cat', 0, '', 14, 15, 0, '*', 1),
(10, 'menu', 'com_messages', 'Messaging', '', 'Messaging', 'index.php?option=com_messages', 'component', 0, 1, 1, 15, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:messages', 0, '', 17, 22, 0, '*', 1),
(11, 'menu', 'com_messages_add', 'New Private Message', '', 'Messaging/New Private Message', 'index.php?option=com_messages&task=message.add', 'component', 0, 10, 2, 15, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:messages-add', 0, '', 18, 19, 0, '*', 1),
(12, 'menu', 'com_messages_read', 'Read Private Message', '', 'Messaging/Read Private Message', 'index.php?option=com_messages', 'component', 0, 10, 2, 15, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:messages-read', 0, '', 20, 21, 0, '*', 1),
(13, 'menu', 'com_newsfeeds', 'News Feeds', '', 'News Feeds', 'index.php?option=com_newsfeeds', 'component', 0, 1, 1, 17, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:newsfeeds', 0, '', 23, 28, 0, '*', 1),
(14, 'menu', 'com_newsfeeds_feeds', 'Feeds', '', 'News Feeds/Feeds', 'index.php?option=com_newsfeeds', 'component', 0, 13, 2, 17, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:newsfeeds', 0, '', 24, 25, 0, '*', 1),
(15, 'menu', 'com_newsfeeds_categories', 'Categories', '', 'News Feeds/Categories', 'index.php?option=com_categories&extension=com_newsfeeds', 'component', 0, 13, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:newsfeeds-cat', 0, '', 26, 27, 0, '*', 1),
(16, 'menu', 'com_redirect', 'Redirect', '', 'Redirect', 'index.php?option=com_redirect', 'component', 0, 1, 1, 24, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:redirect', 0, '', 37, 38, 0, '*', 1),
(17, 'menu', 'com_search', 'Search', '', 'Search', 'index.php?option=com_search', 'component', 0, 1, 1, 19, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:search', 0, '', 29, 30, 0, '*', 1),
(18, 'menu', 'com_weblinks', 'Weblinks', '', 'Weblinks', 'index.php?option=com_weblinks', 'component', 0, 1, 1, 21, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:weblinks', 0, '', 31, 36, 0, '*', 1),
(19, 'menu', 'com_weblinks_links', 'Links', '', 'Weblinks/Links', 'index.php?option=com_weblinks', 'component', 0, 18, 2, 21, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:weblinks', 0, '', 32, 33, 0, '*', 1),
(20, 'menu', 'com_weblinks_categories', 'Categories', '', 'Weblinks/Categories', 'index.php?option=com_categories&extension=com_weblinks', 'component', 0, 18, 2, 6, 0, 0, '0000-00-00 00:00:00', 0, 0, 'class:weblinks-cat', 0, '', 34, 35, 0, '*', 1),
(101, 'mainmenu', 'Home', 'home', '', 'home', 'index.php?option=com_content&view=featured', 'component', 1, 1, 1, 22, 0, 0, '0000-00-00 00:00:00', 0, 1, '', 0, '{"num_leading_articles":"1","num_intro_articles":"3","num_columns":"3","num_links":"0","orderby_pri":"","orderby_sec":"front","order_date":"","multi_column_order":"1","show_pagination":"2","show_pagination_results":"1","show_noauth":"","article-allow_ratings":"","article-allow_comments":"","show_feed_link":"1","feed_summary":"","show_title":"","link_titles":"","show_intro":"","show_category":"","link_category":"","show_parent_category":"","link_parent_category":"","show_author":"","show_create_date":"","show_modify_date":"","show_publish_date":"","show_item_navigation":"","show_readmore":"","show_icons":"","show_print_icon":"","show_email_icon":"","show_hits":"","menu-anchor_title":"","menu-anchor_css":"","menu_image":"","show_page_heading":1,"page_title":"","page_heading":"","pageclass_sfx":"","menu-meta_description":"","menu-meta_keywords":"","robots":"","secure":0}', 39, 40, 1, '*', 0),
(102, 'main', 'JTag Helpdesk', 'jtag-helpdesk', '', 'jtag-helpdesk', 'index.php?option=com_jtaghelpdesk', 'component', 0, 1, 1, 10002, 0, 0, '0000-00-00 00:00:00', 0, 1, 'class:component', 0, '', 41, 42, 0, '', 1),
(103, 'main', 'Huru Helpdesk', 'huru-helpdesk', '', 'huru-helpdesk', 'index.php?option=com_huruhelpdesk', 'component', 0, 1, 1, 10003, 0, 0, '0000-00-00 00:00:00', 0, 1, 'class:component', 0, '', 43, 44, 0, '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `jos_menu_types`
--

CREATE TABLE `jos_menu_types` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `menutype` varchar(24) NOT NULL,
  `title` varchar(48) NOT NULL,
  `description` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_menutype` (`menutype`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `jos_menu_types`
--

INSERT INTO `jos_menu_types` (`id`, `menutype`, `title`, `description`) VALUES
(1, 'mainmenu', 'Main Menu', 'The main menu for the site');

-- --------------------------------------------------------

--
-- Table structure for table `jos_messages`
--

CREATE TABLE `jos_messages` (
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
-- Table structure for table `jos_modules`
--

CREATE TABLE `jos_modules` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `jos_modules`
--

INSERT INTO `jos_modules` (`id`, `title`, `note`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `published`, `module`, `access`, `showtitle`, `params`, `client_id`, `language`) VALUES
(1, 'Main Menu', '', '', 1, 'position-7', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_menu', 1, 1, '{"menutype":"mainmenu","startLevel":"0","endLevel":"0","showAllChildren":"0","tag_id":"","class_sfx":"","window_open":"","layout":"","moduleclass_sfx":"_menu","cache":"1","cache_time":"900","cachemode":"itemid"}', 0, '*'),
(2, 'Login', '', '', 1, 'login', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_login', 1, 1, '', 1, '*'),
(3, 'Popular Articles', '', '', 3, 'cpanel', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_popular', 3, 1, '{"count":"5","catid":"","user_id":"0","layout":"_:default","moduleclass_sfx":"","cache":"0","automatic_title":"1"}', 1, '*'),
(4, 'Recently Added Articles', '', '', 4, 'cpanel', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_latest', 3, 1, '{"count":"5","ordering":"c_dsc","catid":"","user_id":"0","layout":"_:default","moduleclass_sfx":"","cache":"0","automatic_title":"1"}', 1, '*'),
(6, 'Unread Messages', '', '', 1, 'header', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_unread', 3, 1, '', 1, '*'),
(7, 'Online Users', '', '', 2, 'header', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_online', 3, 1, '', 1, '*'),
(8, 'Toolbar', '', '', 1, 'toolbar', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_toolbar', 3, 1, '', 1, '*'),
(9, 'Quick Icons', '', '', 1, 'icon', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_quickicon', 3, 1, '', 1, '*'),
(10, 'Logged-in Users', '', '', 2, 'cpanel', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_logged', 3, 1, '{"count":"5","name":"1","layout":"_:default","moduleclass_sfx":"","cache":"0","automatic_title":"1"}', 1, '*'),
(12, 'Admin Menu', '', '', 1, 'menu', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_menu', 3, 1, '{"layout":"","moduleclass_sfx":"","shownew":"1","showhelp":"1","cache":"0"}', 1, '*'),
(13, 'Admin Submenu', '', '', 1, 'submenu', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_submenu', 3, 1, '', 1, '*'),
(14, 'User Status', '', '', 1, 'status', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_status', 3, 1, '', 1, '*'),
(15, 'Title', '', '', 1, 'title', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_title', 3, 1, '', 1, '*'),
(16, 'Login Form', '', '', 7, 'position-7', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_login', 1, 1, '{"greeting":"1","name":"0"}', 0, '*'),
(17, 'Breadcrumbs', '', '', 1, 'position-2', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 1, 'mod_breadcrumbs', 1, 1, '{"moduleclass_sfx":"","showHome":"1","homeText":"Home","showComponent":"1","separator":"","cache":"1","cache_time":"900","cachemode":"itemid"}', 0, '*'),
(18, 'Banners', '', '', 1, 'position-5', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 0, 'mod_banners', 1, 1, '{"target":"1","count":"1","cid":"1","catid":["27"],"tag_search":"0","ordering":"0","header_text":"","footer_text":"","layout":"","moduleclass_sfx":"","cache":"1","cache_time":"900"}', 0, '*');

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

INSERT INTO `jos_modules_menu` (`moduleid`, `menuid`) VALUES
(1, 0),
(2, 0),
(3, 0),
(4, 0),
(6, 0),
(7, 0),
(8, 0),
(9, 0),
(10, 0),
(12, 0),
(13, 0),
(14, 0),
(15, 0),
(16, 0),
(17, 0),
(18, 0);

-- --------------------------------------------------------

--
-- Table structure for table `jos_newsfeeds`
--

CREATE TABLE `jos_newsfeeds` (
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
-- Dumping data for table `jos_newsfeeds`
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
-- Table structure for table `jos_session`
--

CREATE TABLE `jos_session` (
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
-- Dumping data for table `jos_session`
--

INSERT INTO `jos_session` (`session_id`, `client_id`, `guest`, `time`, `data`, `userid`, `username`, `usertype`) VALUES
('dkmvj73i4e04698ap8mbagoe25', 1, 0, '1300047134', '__default|a:8:{s:15:"session.counter";i:40;s:19:"session.timer.start";i:1300045342;s:18:"session.timer.last";i:1300047127;s:17:"session.timer.now";i:1300047134;s:22:"session.client.browser";s:120:"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.127 Safari/534.16";s:8:"registry";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":3:{s:11:"application";O:8:"stdClass":1:{s:4:"lang";s:0:"";}s:13:"com_templates";O:8:"stdClass":2:{s:6:"styles";O:8:"stdClass":1:{s:10:"limitstart";i:0;}s:4:"edit";O:8:"stdClass":1:{s:5:"style";O:8:"stdClass":2:{s:2:"id";a:0:{}s:4:"data";N;}}}s:13:"com_installer";O:8:"stdClass":3:{s:7:"message";s:0:"";s:17:"extension_message";s:0:"";s:12:"redirect_url";N;}}}s:4:"user";O:5:"JUser":23:{s:9:"\0*\0isRoot";b:1;s:2:"id";s:2:"42";s:4:"name";s:10:"Super User";s:8:"username";s:5:"admin";s:5:"email";s:31:"jacek.p.kolodziejczyk@gmail.com";s:8:"password";s:65:"8fa709fc544db409f0bb251606028dfb:vLDfbuM4mUuIgF3b2oX1uCZFGxvkts2u";s:14:"password_clear";s:0:"";s:8:"usertype";s:10:"deprecated";s:5:"block";s:1:"0";s:9:"sendEmail";s:1:"1";s:12:"registerDate";s:19:"2011-03-13 19:41:57";s:13:"lastvisitDate";s:19:"0000-00-00 00:00:00";s:10:"activation";s:0:"";s:6:"params";s:0:"";s:6:"groups";a:1:{s:11:"Super Users";s:1:"8";}s:5:"guest";i:0;s:10:"\0*\0_params";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":0:{}}s:14:"\0*\0_authGroups";a:2:{i:0;i:1;i:1;i:8;}s:14:"\0*\0_authLevels";a:4:{i:0;i:1;i:1;i:1;i:2;i:2;i:3;i:3;}s:15:"\0*\0_authActions";N;s:12:"\0*\0_errorMsg";N;s:10:"\0*\0_errors";a:0:{}s:3:"aid";i:0;}s:13:"session.token";s:32:"884b89e31ced0dd9af2e9a25c012673d";}', 42, 'admin', ''),
('q5nvv1njisc02981v50l7rkum5', 0, 1, '1300047123', '__default|a:8:{s:15:"session.counter";i:9;s:19:"session.timer.start";i:1300045379;s:18:"session.timer.last";i:1300046961;s:17:"session.timer.now";i:1300047122;s:22:"session.client.browser";s:120:"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.127 Safari/534.16";s:8:"registry";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":0:{}}s:4:"user";O:5:"JUser":23:{s:9:"\0*\0isRoot";b:0;s:2:"id";i:0;s:4:"name";N;s:8:"username";N;s:5:"email";N;s:8:"password";N;s:14:"password_clear";s:0:"";s:8:"usertype";N;s:5:"block";N;s:9:"sendEmail";i:0;s:12:"registerDate";N;s:13:"lastvisitDate";N;s:10:"activation";N;s:6:"params";N;s:6:"groups";a:0:{}s:5:"guest";i:1;s:10:"\0*\0_params";O:9:"JRegistry":1:{s:7:"\0*\0data";O:8:"stdClass":0:{}}s:14:"\0*\0_authGroups";a:1:{i:0;i:1;}s:14:"\0*\0_authLevels";a:2:{i:0;i:1;i:1;i:1;}s:15:"\0*\0_authActions";N;s:12:"\0*\0_errorMsg";N;s:10:"\0*\0_errors";a:0:{}s:3:"aid";i:0;}s:13:"session.token";s:32:"a828830eefc9e95219c0f28542d583c1";}', 0, '', '');

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `jos_template_styles`
--

INSERT INTO `jos_template_styles` (`id`, `template`, `client_id`, `home`, `title`, `params`) VALUES
(2, 'bluestork', 1, '1', 'Bluestork - Default', '{"useRoundedCorners":"1","showSiteName":"0"}'),
(3, 'atomic', 0, '1', 'Atomic - Default', '{}'),
(4, 'beez_20', 0, '0', 'Beez2 - Default', '{"wrapperSmall":"53","wrapperLarge":"72","logo":"images\\/joomla_black.gif","sitetitle":"Joomla!","sitedescription":"Open Source Content Management","navposition":"left","templatecolor":"personal","html5":"0"}'),
(5, 'hathor', 1, '0', 'Hathor - Default', '{"showSiteName":"0","colourChoice":"","boldText":"0"}'),
(6, 'beez5', 0, '0', 'Beez5 - Default-Fruit Shop', '{"wrapperSmall":"53","wrapperLarge":"72","logo":"images\\/sampledata\\/fruitshop\\/fruits.gif","sitetitle":"Matuna Market ","sitedescription":"Fruit Shop Sample Site","navposition":"left","html5":"0"}'),
(7, 'yougrids', 0, '0', 'Yougrids - Default', '{"":"TOP_MENU_OFF_YJ_LABEL","STYLE_SETTINGS_TAB":"STYLE_SETTINGS_TAB","custom_css":"2","STTEXT_LABEL":"STTEXT_LABEL","STYLE_SUB":"STYLE_SUB","default_color":"metal","default_font":"3","default_font_family":"6","selectors_override":"2","selectors_override_type":"1","css_font_family":"12","google_font_family":"2","cufon_font_family":"1","affected_selectors":"div.title h1,div.title h2,div.componentheading, h1,h2,h3,h4,h5,h6,.yjround h4,.yjsquare h4","LOGO_SUB":"LOGO_SUB","LGTEXT_LABEL":"LGTEXT_LABEL","LOGO_YJ_TITLE":"LOGO_YJ_TITLE","logo_height":"125px","logo_width":"300px","turn_logo_off":"2","turn_header_block_off":"2","TOP_MENU_SUB":"TOP_MENU_SUB","TMTEXT_LABEL":"TMTEXT_LABEL","TOP_MENU_YJ_LABEL":"TOP_MENU_YJ_LABEL","menuName":"mainmenu","default_menu_style":"2","sub_width":"280px","yjsg_menu_offset":"95","turn_topmenu_off":"","DEF_GRID_SUB":"DEF_GRID_SUB","DGTEXT_LABEL":"DGTEXT_LABEL","MAINB_YJ_LABEL":"MAINB_YJ_LABEL","css_widthdefined":"px","css_width":"1000","site_layout":"2","MBC_W_LABEL":"MBC_W_LABEL","widthdefined":"%","maincolumn":"55","insetcolumn":"0","leftcolumn":"22.5","rightcolumn":"22.5","SPII_LABEL":"SPII_LABEL","widthdefined_itmid":"%","maincolumn_itmid":"55","insetcolumn_itmid":"0","leftcolumn_itmid":"22.5","rightcolumn_itmid":"22.5","define_itemid":"","MG_SUB":"MG_SUB","MGTEXT_LABEL":"MGTEXT_LABEL","yjsg_1_width":"20|20|20|20|20","yjsg_header_width":"33|33|33","yjsg_2_width":"20|20|20|20|20","yjsg_3_width":"20|20|20|20|20","yjsg_4_width":"20|20|20|20|20","yjsg_bodytop_width":"33|33|33","yjsg_yjsgbodytbottom_width":"33|33|33","yjsg_5_width":"20|20|20|20|20","yjsg_6_width":"20|20|20|20|20","yjsg_7_width":"20|20|20|20|20","MOBILE_SUB":"MOBILE_SUB","MOBILE_TXT_LABEL":"MOBILE_TXT_LABEL","iphone_default":"1","android_default":"1","handheld_default":"2","mobile_reg":"1","ADD_F_SUB":"ADD_F_SUB","ADTEXT_LABEL":"ADTEXT_LABEL","GA_YJ_LABEL":"GA_YJ_LABEL","GATEXT_LABEL":"GATEXT_LABEL","ga_switch":"0","GAcode":"UA-xxxxxx-x","NOT_YJ_LABEL":"NOT_YJ_LABEL","compress":"0","ie6notice":"0","nonscript":"0","ST_YJ_LABEL":"ST_YJ_LABEL","show_tools":"1","show_fres":"1","show_rtlc":"1","TDIR_YJ_LABEL":"TDIR_YJ_LABEL","text_direction":"2","SEO_YJ_LABEL":"SEO_YJ_LABEL","turn_seo_off":"2","seo":"Your description goes here","tags":"Your keywords go here","COPY_YJ_LABEL":"COPY_YJ_LABEL","branding_off":"2","joomlacredit_off":"2","ADV_SUB":"ADV_SUB","ADVTEXT_LABEL":"ADVTEXT_LABEL","FPC_YJ_LABEL":"FPC_YJ_LABEL","fp_controll_switch":"2","fp_chars_limit":"3000","fp_after_text":"","SCRIPT_YJ_LABEL":"SCRIPT_YJ_LABEL","JQ_LABEL":"JQ_LABEL","jq_switch":"2","SMS_YJ_LABEL":"SMS_YJ_LABEL","MSTEXT_LABEL":"MSTEXT_LABEL","YJsg1_module_style":"YJsgxhtml","YJsgh_module_style":"YJsgxhtml","YJsg2_module_style":"YJsgxhtml","YJsg3_module_style":"YJsgxhtml","YJsg4_module_style":"YJsgxhtml","YJsgmt_module_style":"YJsgxhtml","YJsgl_module_style":"YJsgxhtml","YJsgr_module_style":"YJsgxhtml","YJsgi_module_style":"YJsgxhtml","YJsgit_module_style":"YJsgxhtml","YJsgib_module_style":"YJsgxhtml","YJsgmb_module_style":"YJsgxhtml","YJsg5_module_style":"YJsgxhtml","YJsg6_module_style":"YJsgxhtml","YJsg7_module_style":"YJsgxhtml","CP_LABEL":"CP_LABEL","component_switch":"","admin_css_time":"0"}');

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='Update Sites' AUTO_INCREMENT=3 ;

--
-- Dumping data for table `jos_update_sites`
--

INSERT INTO `jos_update_sites` (`update_site_id`, `name`, `type`, `location`, `enabled`) VALUES
(1, 'Joomla Core', 'collection', 'http://update.joomla.org/core/list.xml', 1),
(2, 'Joomla Extension Directory', 'collection', 'http://update.joomla.org/jed/list.xml', 1);

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

INSERT INTO `jos_update_sites_extensions` (`update_site_id`, `extension_id`) VALUES
(1, 700),
(2, 700);

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `jos_usergroups`
--

INSERT INTO `jos_usergroups` (`id`, `parent_id`, `lft`, `rgt`, `title`) VALUES
(1, 0, 1, 20, 'Public'),
(2, 1, 6, 17, 'Registered'),
(3, 2, 7, 14, 'Author'),
(4, 3, 8, 11, 'Editor'),
(5, 4, 9, 10, 'Publisher'),
(6, 1, 2, 5, 'Manager'),
(7, 6, 3, 4, 'Administrator'),
(8, 1, 18, 19, 'Super Users');

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=67 ;

--
-- Dumping data for table `jos_users`
--

INSERT INTO `jos_users` (`id`, `name`, `username`, `email`, `password`, `usertype`, `block`, `sendEmail`, `gid`, `registerDate`, `lastvisitDate`, `activation`, `params`) VALUES
(62, 'Administrator', 'admin', 'jacek.p.kolodziejczyk@gmail.com', '8e813771a3224ccb8b817a6da05fbed6:ve7DkyNhOrQNqG1B5HhxsjOzcGOfmVOw', 'Super Administrator', 0, 1, 25, '2011-03-13 21:18:07', '2011-05-04 09:29:50', '', 'admin_language=\nlanguage=\neditor=\nhelpsite=\ntimezone=0\n\n'),
(63, 'Jacek', 'jacek', 'jackolo@poczta.fm', '8f39acd9fba3134e96c5d4f6b025ab16:iNoq0ku6Jp8BW9swWGvNhHOprddJSpBQ', 'Registered', 0, 0, 18, '2011-04-14 13:52:15', '2011-05-03 12:51:18', 'f9b1ef19e09e5e4d7ad9faf64839c132', ''),
(64, 'bbb', 'bbb', 'bbb@b.pl', 'a6544a4c61523f4af13493d38994e040:2wAKjgcH2LeVMJU0cZWDVbLE9cSQBW5m', 'Registered', 0, 0, 18, '2011-04-14 15:22:19', '2011-04-14 18:39:48', 'f3c72fb900fbf7edf6463172b21f5b7c', ''),
(65, 'cccc', 'cccc', 'c@c.pl', '9da19f8c7b4ae5e12d90a27fbe5b1e51:qWhgjF3a6tMLvDmkBPUXhVZuTqdAER7y', 'Registered', 0, 0, 18, '2011-04-14 18:40:07', '2011-04-14 18:43:00', 'b4df79c17fb4d8a48411228b5102f376', '\n'),
(66, 'dddd', 'dddd', 'd@d.pl', 'd87e05f38b5389a8e92e59ae941c116a:XvovyXSLl53VZkNLaGKgSAzh1JKc5of2', 'Registered', 0, 0, 18, '2011-04-14 18:43:22', '2011-04-15 09:24:33', '4cc3c5a62a7d802ced24c9decbe484c9', '\n');

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

INSERT INTO `jos_user_usergroup_map` (`user_id`, `group_id`) VALUES
(40, 8),
(42, 8);

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `jos_viewlevels`
--

INSERT INTO `jos_viewlevels` (`id`, `title`, `ordering`, `rules`) VALUES
(1, 'Public', 0, '[1]'),
(2, 'Registered', 1, '[6,2,8]'),
(3, 'Special', 2, '[6,3,8]');

-- --------------------------------------------------------

--
-- Table structure for table `jos_weblinks`
--

CREATE TABLE `jos_weblinks` (
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
-- Dumping data for table `jos_weblinks`
--

