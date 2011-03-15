-- phpMyAdmin SQL Dump
-- version 2.11.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 14, 2011 at 07:15 PM
-- Server version: 5.0.51
-- PHP Version: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `netanel15`
--

-- --------------------------------------------------------

--
-- Table structure for table `jos_advancedmodules`
--

DROP TABLE IF EXISTS `jos_advancedmodules`;
CREATE TABLE `jos_advancedmodules` (
  `moduleid` int(11) NOT NULL default '0',
  `params` text NOT NULL,
  PRIMARY KEY  (`moduleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_advancedmodules`
--

INSERT INTO `jos_advancedmodules` (`moduleid`, `params`) VALUES
(16, 'hideempty=1\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_selection=1|3|4|5|6|2\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=2\nassignto_articles_selection=1\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=2\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n'),
(17, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=1\nassignto_menuitems_inc_children=0\nassignto_menuitems_selection=4|7|10\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=1\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_secscats_selection=1:1\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n'),
(1, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n'),
(18, 'hideempty=0\ntooltip=\ncolor=FFFFFF\nmirror_module=0\nmatch_method=and\nassignto_menuitems=0\nassignto_menuitems_inc_children=0\nassignto_menuitems_inc_noitemid=0\nassignto_secscats=0\nassignto_secscats_inc=inc_secs|inc_cats|inc_arts|x\nassignto_articles=0\nassignto_articles_selection=\nassignto_articles_keywords=\nassignto_components=0\nassignto_components_selection=x\nassignto_urls=0\nassignto_urls_selection=\nassignto_urls_selection_sef=\nassignto_browsers=0\nassignto_date=0\nassignto_date_publish_up=\nassignto_date_publish_down=\nassignto_seasons=0\nassignto_seasons_selection=x\nassignto_seasons_hemisphere=northern\nassignto_months=0\nassignto_months_selection=x\nassignto_days=0\nassignto_days_selection=x\nassignto_time=0\nassignto_time_publish_up=0:00\nassignto_time_publish_down=0:00\nassignto_usergrouplevels=0\nassignto_usergrouplevels_selection=0\nassignto_users=0\nassignto_users_selection=\nassignto_languages=0\nassignto_templates=0\nassignto_php=0\nassignto_php_selection=\n');

-- --------------------------------------------------------

--
-- Table structure for table `jos_banner`
--

DROP TABLE IF EXISTS `jos_banner`;
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
-- Table structure for table `jos_bannerclient`
--

DROP TABLE IF EXISTS `jos_bannerclient`;
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
-- Table structure for table `jos_bannertrack`
--

DROP TABLE IF EXISTS `jos_bannertrack`;
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

DROP TABLE IF EXISTS `jos_categories`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `jos_categories`
--

INSERT INTO `jos_categories` (`id`, `parent_id`, `title`, `name`, `alias`, `image`, `section`, `image_position`, `description`, `published`, `checked_out`, `checked_out_time`, `editor`, `ordering`, `access`, `count`, `params`) VALUES
(1, 0, 'SWT Matrix', '', 'swt-matrix', '', '1', 'left', '<p>SWT Matrix i a tabular SWT widget with unlimited capacity and instant rendering.</p>', 1, 0, '0000-00-00 00:00:00', NULL, 1, 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_components`
--

DROP TABLE IF EXISTS `jos_components`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=50 ;

--
-- Dumping data for table `jos_components`
--

INSERT INTO `jos_components` (`id`, `name`, `link`, `menuid`, `parent`, `admin_menu_link`, `admin_menu_alt`, `option`, `ordering`, `admin_menu_img`, `iscore`, `params`, `enabled`) VALUES
(1, 'Banners', '', 0, 0, '', 'Banner Management', 'com_banners', 0, 'js/ThemeOffice/component.png', 0, 'track_impressions=0\ntrack_clicks=0\ntag_prefix=\n\n', 1),
(2, 'Banners', '', 0, 1, 'option=com_banners', 'Active Banners', 'com_banners', 1, 'js/ThemeOffice/edit.png', 0, '', 1),
(3, 'Clients', '', 0, 1, 'option=com_banners&c=client', 'Manage Clients', 'com_banners', 2, 'js/ThemeOffice/categories.png', 0, '', 1),
(4, 'Web Links', 'option=com_weblinks', 0, 0, '', 'Manage Weblinks', 'com_weblinks', 0, 'js/ThemeOffice/component.png', 0, 'show_comp_description=1\ncomp_description=\nshow_link_hits=1\nshow_link_description=1\nshow_other_cats=1\nshow_headings=1\nshow_page_title=1\nlink_target=0\nlink_icons=\n\n', 1),
(5, 'Links', '', 0, 4, 'option=com_weblinks', 'View existing weblinks', 'com_weblinks', 1, 'js/ThemeOffice/edit.png', 0, '', 1),
(6, 'Categories', '', 0, 4, 'option=com_categories&section=com_weblinks', 'Manage weblink categories', '', 2, 'js/ThemeOffice/categories.png', 0, '', 1),
(7, 'Contacts', 'option=com_contact', 0, 0, '', 'Edit contact details', 'com_contact', 0, 'js/ThemeOffice/component.png', 1, 'contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n', 1),
(8, 'Contacts', '', 0, 7, 'option=com_contact', 'Edit contact details', 'com_contact', 0, 'js/ThemeOffice/edit.png', 1, '', 1),
(9, 'Categories', '', 0, 7, 'option=com_categories&section=com_contact_details', 'Manage contact categories', '', 2, 'js/ThemeOffice/categories.png', 1, 'contact_icons=0\nicon_address=\nicon_email=\nicon_telephone=\nicon_fax=\nicon_misc=\nshow_headings=1\nshow_position=1\nshow_email=0\nshow_telephone=1\nshow_mobile=1\nshow_fax=1\nbannedEmail=\nbannedSubject=\nbannedText=\nsession=1\ncustomReply=0\n\n', 1),
(10, 'Polls', 'option=com_poll', 0, 0, 'option=com_poll', 'Manage Polls', 'com_poll', 0, 'js/ThemeOffice/component.png', 0, '', 1),
(11, 'News Feeds', 'option=com_newsfeeds', 0, 0, '', 'News Feeds Management', 'com_newsfeeds', 0, 'js/ThemeOffice/component.png', 0, '', 1),
(12, 'Feeds', '', 0, 11, 'option=com_newsfeeds', 'Manage News Feeds', 'com_newsfeeds', 1, 'js/ThemeOffice/edit.png', 0, 'show_headings=1\nshow_name=1\nshow_articles=1\nshow_link=1\nshow_cat_description=1\nshow_cat_items=1\nshow_feed_image=1\nshow_feed_description=1\nshow_item_description=1\nfeed_word_count=0\n\n', 1),
(13, 'Categories', '', 0, 11, 'option=com_categories&section=com_newsfeeds', 'Manage Categories', '', 2, 'js/ThemeOffice/categories.png', 0, '', 1),
(14, 'User', 'option=com_user', 0, 0, '', '', 'com_user', 0, '', 1, '', 1),
(15, 'Search', 'option=com_search', 0, 0, 'option=com_search', 'Search Statistics', 'com_search', 0, 'js/ThemeOffice/component.png', 1, 'enabled=0\n\n', 1),
(16, 'Categories', '', 0, 1, 'option=com_categories&section=com_banner', 'Categories', '', 3, '', 1, '', 1),
(17, 'Wrapper', 'option=com_wrapper', 0, 0, '', 'Wrapper', 'com_wrapper', 0, '', 1, '', 1),
(18, 'Mail To', '', 0, 0, '', '', 'com_mailto', 0, '', 1, '', 1),
(19, 'Media Manager', '', 0, 0, 'option=com_media', 'Media Manager', 'com_media', 0, '', 1, 'upload_extensions=bmp,csv,doc,epg,gif,ico,jpg,odg,odp,ods,odt,pdf,png,ppt,swf,txt,xcf,xls,BMP,CSV,DOC,EPG,GIF,ICO,JPG,ODG,ODP,ODS,ODT,PDF,PNG,PPT,SWF,TXT,XCF,XLS\nupload_maxsize=10000000\nfile_path=images\nimage_path=images/stories\nrestrict_uploads=1\nallowed_media_usergroup=3\ncheck_mime=1\nimage_extensions=bmp,gif,jpg,png\nignore_extensions=\nupload_mime=image/jpeg,image/gif,image/png,image/bmp,application/x-shockwave-flash,application/msword,application/excel,application/pdf,application/powerpoint,text/plain,application/x-zip\nupload_mime_illegal=text/html\nenable_flash=0\n\n', 1),
(20, 'Articles', 'option=com_content', 0, 0, '', '', 'com_content', 0, '', 1, 'show_noauth=0\nshow_title=1\nlink_titles=0\nshow_intro=0\nshow_section=0\nlink_section=0\nshow_category=0\nlink_category=0\nshow_author=0\nshow_create_date=0\nshow_modify_date=0\nshow_item_navigation=0\nshow_readmore=1\nshow_vote=0\nshow_icons=1\nshow_pdf_icon=1\nshow_print_icon=1\nshow_email_icon=1\nshow_hits=1\nfeed_summary=0\nfilter_tags=\nfilter_attritbutes=\n\n', 1),
(21, 'Configuration Manager', '', 0, 0, '', 'Configuration', 'com_config', 0, '', 1, '', 1),
(22, 'Installation Manager', '', 0, 0, '', 'Installer', 'com_installer', 0, '', 1, '', 1),
(23, 'Language Manager', '', 0, 0, '', 'Languages', 'com_languages', 0, '', 1, '', 1),
(24, 'Mass mail', '', 0, 0, '', 'Mass Mail', 'com_massmail', 0, '', 1, 'mailSubjectPrefix=\nmailBodySuffix=\n\n', 1),
(25, 'Menu Editor', '', 0, 0, '', 'Menu Editor', 'com_menus', 0, '', 1, '', 1),
(27, 'Messaging', '', 0, 0, '', 'Messages', 'com_messages', 0, '', 1, '', 1),
(28, 'Modules Manager', '', 0, 0, '', 'Modules', 'com_modules', 0, '', 1, '', 1),
(29, 'Plugin Manager', '', 0, 0, '', 'Plugins', 'com_plugins', 0, '', 1, '', 1),
(30, 'Template Manager', '', 0, 0, '', 'Templates', 'com_templates', 0, '', 1, '', 1),
(31, 'User Manager', '', 0, 0, '', 'Users', 'com_users', 0, '', 1, 'allowUserRegistration=1\nnew_usertype=Registered\nuseractivation=1\nfrontend_userparams=1\n\n', 1),
(32, 'Cache Manager', '', 0, 0, '', 'Cache', 'com_cache', 0, '', 1, '', 1),
(33, 'Control Panel', '', 0, 0, '', 'Control Panel', 'com_cpanel', 0, '', 1, '', 1),
(34, 'Huru Helpdesk', 'option=com_huruhelpdesk', 0, 0, 'option=com_huruhelpdesk', 'Huru Helpdesk', 'com_huruhelpdesk', 0, 'js/ThemeOffice/component.png', 0, '', 1),
(35, 'ARTIO JoomSEF', 'option=com_sef', 0, 0, 'option=com_sef', 'ARTIO JoomSEF', 'com_sef', 0, 'components/com_sef/assets/images/icon.png', 0, '', 1),
(36, 'Control Panel', '', 0, 35, 'option=com_sef', 'Control Panel', 'com_sef', 0, 'components/com_sef/assets/images/icon-16-sefcpanel.png', 0, '', 1),
(37, 'Configuration', '', 0, 35, 'option=com_sef&controller=config&task=edit', 'Configuration', 'com_sef', 1, 'components/com_sef/assets/images/icon-16-sefconfig.png', 0, '', 1),
(38, 'Manage Extensions', '', 0, 35, 'option=com_sef&controller=extension', 'Manage Extensions', 'com_sef', 2, 'components/com_sef/assets/images/icon-16-sefplugin.png', 0, '', 1),
(39, 'Edit .htaccess', '', 0, 35, 'option=com_sef&controller=htaccess', 'Edit .htaccess', 'com_sef', 3, 'components/com_sef/assets/images/icon-16-edit.png', 0, '', 1),
(40, '', '', 0, 35, 'option=com_sef', '', 'com_sef', 4, 'components/com_sef/assets/images/icon-10-blank.png', 0, '', 1),
(41, 'Manage SEF URLs', '', 0, 35, 'option=com_sef&controller=sefurls&viewmode=3', 'Manage SEF URLs', 'com_sef', 5, 'components/com_sef/assets/images/icon-16-url-edit.png', 0, '', 1),
(42, 'Manage Meta Tags', '', 0, 35, 'option=com_sef&controller=metatags', 'Manage Meta Tags', 'com_sef', 6, 'components/com_sef/assets/images/icon-16-manage-tags.png', 0, '', 1),
(43, 'SiteMap', '', 0, 35, 'option=com_sef&controller=sitemap', 'SiteMap', 'com_sef', 7, 'components/com_sef/assets/images/icon-16-manage-sitemap.png', 0, '', 1),
(44, 'Manage 301 Redirects', '', 0, 35, 'option=com_sef&controller=movedurls', 'Manage 301 Redirects', 'com_sef', 8, 'components/com_sef/assets/images/icon-16-301-redirects.png', 0, '', 1),
(45, '', '', 0, 35, 'option=com_sef', '', 'com_sef', 9, 'components/com_sef/assets/images/icon-10-blank.png', 0, '', 1),
(46, 'Upgrade', '', 0, 35, 'option=com_sef&task=showUpgrade', 'Upgrade', 'com_sef', 10, 'components/com_sef/assets/images/icon-16-update.png', 0, '', 1),
(47, 'Support', '', 0, 35, 'option=com_sef&controller=info&task=help', 'Support', 'com_sef', 11, 'components/com_sef/assets/images/icon-16-help.png', 0, '', 1),
(49, 'Advanced Module Manager', '', 0, 0, '', 'Advanced Module Manager', 'com_advancedmodules', 0, '', 0, '\n', 1);

-- --------------------------------------------------------

--
-- Table structure for table `jos_contact_details`
--

DROP TABLE IF EXISTS `jos_contact_details`;
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

DROP TABLE IF EXISTS `jos_content`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `jos_content`
--

INSERT INTO `jos_content` (`id`, `title`, `alias`, `title_alias`, `introtext`, `fulltext`, `state`, `sectionid`, `mask`, `catid`, `created`, `created_by`, `created_by_alias`, `modified`, `modified_by`, `checked_out`, `checked_out_time`, `publish_up`, `publish_down`, `images`, `urls`, `attribs`, `version`, `parentid`, `ordering`, `metakey`, `metadesc`, `access`, `hits`, `metadata`) VALUES
(1, 'Welcome', 'welcome', '', '<p>This site is under construction. Please come later.</p>\r\n<p><img src="images/under_construction.png" border="0" alt="under_construction" width="256" height="256" style="border: 0;" /></p>', '', 1, 0, 0, 0, '2011-03-13 22:10:04', 62, '', '2011-03-14 17:46:14', 62, 0, '0000-00-00 00:00:00', '2011-03-13 22:10:04', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 5, 0, 5, '', '', 0, 3, 'robots=\nauthor='),
(2, 'Products', 'products', '', '<p>Products</p>', '', 1, 0, 0, 0, '2011-03-14 01:26:16', 62, '', '0000-00-00 00:00:00', 0, 0, '0000-00-00 00:00:00', '2011-03-14 01:26:16', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 1, 0, 4, '', '', 0, 38, 'robots=\nauthor='),
(3, 'SWT Matrix', 'swtmatrix', '', '<p>SWT Matrix is a tabular widget for the SWT Java GUI toolkit. Is is characterized by an unlimited capacity and instant rendering.</p>', '', 1, 1, 0, 1, '2011-03-14 01:58:18', 62, '', '2011-03-14 14:40:28', 62, 0, '0000-00-00 00:00:00', '2011-03-14 01:58:18', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 2, 0, 2, '', '', 0, 161, 'robots=\nauthor='),
(4, 'Download', 'download', '', '<p>Download</p>', '', 1, 0, 0, 0, '2011-03-14 03:24:20', 62, '', '0000-00-00 00:00:00', 0, 0, '0000-00-00 00:00:00', '2011-03-14 03:24:20', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 1, 0, 3, '', '', 0, 2, 'robots=\nauthor='),
(5, 'Features', 'features', '', '<h3><span style="color: #333333; font-family: Tahoma, Helvetica, Arial, sans-serif; font-size: 12px; font-weight: normal;">Axis item is a row or a column.</span></h3>\r\n<h3>Axis</h3>\r\n<p>\r\n<ul>\r\n<li>Separate axis sections: header, body, footer, etc.</li>\r\n<li><strong>Unlimited </strong>number of items in a section</li>\r\n<li>Auto numbering of item header text</li>\r\n<li>Block selection of axis items with full key/mouse bindings and accelerating auto scroll</li>\r\n</ul>\r\n</p>\r\n<h3>Matrix</h3>\r\n<p>\r\n<ul>\r\n<li>Cell navigation (current cell marker) with full key/mouse bindings</li>\r\n<li>Block cell selection with full key/mouse bindings and accelerating auto scroll</li>\r\n<li>Configurable key/mouse bindings.</li>\r\n</ul>\r\n</p>\r\n<p> </p>', '', 1, 1, 0, 1, '2011-03-14 09:04:13', 62, '', '2011-03-14 17:25:16', 62, 0, '0000-00-00 00:00:00', '2011-03-14 09:04:13', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 4, 0, 3, '', '', 0, 28, 'robots=\nauthor='),
(6, '404', '404', '', '<h1>404: Not Found</h1>\r\n<h2>Sorry, but the content you requested could not be found</h2>', '', 1, 0, 0, 0, '2004-11-11 12:44:38', 62, '', '2011-03-14 12:01:41', 0, 62, '2004-11-11 12:45:09', '2004-10-17 00:00:00', '0000-00-00 00:00:00', '', '', 'menu_image=-1\nitem_title=0\npageclass_sfx=\nback_button=\nrating=0\nauthor=0\ncreatedate=0\nmodifydate=0\npdf=0\nprint=0\nemail=0', 1, 0, 2, '', '', 0, 750, ''),
(7, 'API', 'api', '', '<p>api</p>', '', 1, 1, 0, 1, '2011-03-14 12:43:56', 62, '', '2011-03-14 14:40:19', 62, 0, '0000-00-00 00:00:00', '2011-03-14 12:43:56', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 2, 0, 1, '', '', 0, 1, 'robots=\nauthor='),
(8, 'Contact', 'contact', '', '<p>email: <a href="mailto: info@netanel.pl" title="email">info@netanel.pl</a></p>', '', 1, 0, 0, 0, '2011-03-14 18:03:36', 62, '', '0000-00-00 00:00:00', 0, 0, '0000-00-00 00:00:00', '2011-03-14 18:03:36', '0000-00-00 00:00:00', '', '', 'show_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_vote=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nlanguage=\nkeyref=\nreadmore=', 1, 0, 1, '', '', 0, 1, 'robots=\nauthor=');

-- --------------------------------------------------------

--
-- Table structure for table `jos_content_frontpage`
--

DROP TABLE IF EXISTS `jos_content_frontpage`;
CREATE TABLE `jos_content_frontpage` (
  `content_id` int(11) NOT NULL default '0',
  `ordering` int(11) NOT NULL default '0',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_content_frontpage`
--

INSERT INTO `jos_content_frontpage` (`content_id`, `ordering`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `jos_content_rating`
--

DROP TABLE IF EXISTS `jos_content_rating`;
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

DROP TABLE IF EXISTS `jos_core_acl_aro`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `jos_core_acl_aro`
--

INSERT INTO `jos_core_acl_aro` (`id`, `section_value`, `value`, `order_value`, `name`, `hidden`) VALUES
(10, 'users', '62', 0, 'Administrator', 0);

-- --------------------------------------------------------

--
-- Table structure for table `jos_core_acl_aro_groups`
--

DROP TABLE IF EXISTS `jos_core_acl_aro_groups`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=31 ;

--
-- Dumping data for table `jos_core_acl_aro_groups`
--

INSERT INTO `jos_core_acl_aro_groups` (`id`, `parent_id`, `name`, `lft`, `rgt`, `value`) VALUES
(17, 0, 'ROOT', 1, 22, 'ROOT'),
(28, 17, 'USERS', 2, 21, 'USERS'),
(29, 28, 'Public Frontend', 3, 12, 'Public Frontend'),
(18, 29, 'Registered', 4, 11, 'Registered'),
(19, 18, 'Author', 5, 10, 'Author'),
(20, 19, 'Editor', 6, 9, 'Editor'),
(21, 20, 'Publisher', 7, 8, 'Publisher'),
(30, 28, 'Public Backend', 13, 20, 'Public Backend'),
(23, 30, 'Manager', 14, 19, 'Manager'),
(24, 23, 'Administrator', 15, 18, 'Administrator'),
(25, 24, 'Super Administrator', 16, 17, 'Super Administrator');

-- --------------------------------------------------------

--
-- Table structure for table `jos_core_acl_aro_map`
--

DROP TABLE IF EXISTS `jos_core_acl_aro_map`;
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

DROP TABLE IF EXISTS `jos_core_acl_aro_sections`;
CREATE TABLE `jos_core_acl_aro_sections` (
  `id` int(11) NOT NULL auto_increment,
  `value` varchar(230) NOT NULL default '',
  `order_value` int(11) NOT NULL default '0',
  `name` varchar(230) NOT NULL default '',
  `hidden` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `jos_gacl_value_aro_sections` (`value`),
  KEY `jos_gacl_hidden_aro_sections` (`hidden`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `jos_core_acl_aro_sections`
--

INSERT INTO `jos_core_acl_aro_sections` (`id`, `value`, `order_value`, `name`, `hidden`) VALUES
(10, 'users', 1, 'Users', 0);

-- --------------------------------------------------------

--
-- Table structure for table `jos_core_acl_groups_aro_map`
--

DROP TABLE IF EXISTS `jos_core_acl_groups_aro_map`;
CREATE TABLE `jos_core_acl_groups_aro_map` (
  `group_id` int(11) NOT NULL default '0',
  `section_value` varchar(240) NOT NULL default '',
  `aro_id` int(11) NOT NULL default '0',
  UNIQUE KEY `group_id_aro_id_groups_aro_map` (`group_id`,`section_value`,`aro_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_core_acl_groups_aro_map`
--

INSERT INTO `jos_core_acl_groups_aro_map` (`group_id`, `section_value`, `aro_id`) VALUES
(25, '', 10);

-- --------------------------------------------------------

--
-- Table structure for table `jos_core_log_items`
--

DROP TABLE IF EXISTS `jos_core_log_items`;
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

DROP TABLE IF EXISTS `jos_core_log_searches`;
CREATE TABLE `jos_core_log_searches` (
  `search_term` varchar(128) NOT NULL default '',
  `hits` int(11) unsigned NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_core_log_searches`
--


-- --------------------------------------------------------

--
-- Table structure for table `jos_groups`
--

DROP TABLE IF EXISTS `jos_groups`;
CREATE TABLE `jos_groups` (
  `id` tinyint(3) unsigned NOT NULL default '0',
  `name` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_groups`
--

INSERT INTO `jos_groups` (`id`, `name`) VALUES
(0, 'Public'),
(1, 'Registered'),
(2, 'Special');

-- --------------------------------------------------------

--
-- Table structure for table `jos_huruhelpdesk_attachments`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_attachments`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_categories`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_config`;
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
(1, 'helpdesk@domain.com', 'http://www.netanel.pl/', 1, 1, 3, 15, 24, 1, 1, 10, 0, '0.88 beta', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 50, 50, 50, 'Huru Helpdesk', 0, 0, -1, 10000, '.jpg,.png', 'image/jpeg,image/png', 1048576, 1, '', 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_huruhelpdesk_departments`
--

DROP TABLE IF EXISTS `jos_huruhelpdesk_departments`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_emailmsg`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_langstrings`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_language`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_notes`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_priority`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_problems`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_status`;
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

DROP TABLE IF EXISTS `jos_huruhelpdesk_users`;
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
-- Table structure for table `jos_menu`
--

DROP TABLE IF EXISTS `jos_menu`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `jos_menu`
--

INSERT INTO `jos_menu` (`id`, `menutype`, `name`, `alias`, `link`, `type`, `published`, `parent`, `componentid`, `sublevel`, `ordering`, `checked_out`, `checked_out_time`, `pollid`, `browserNav`, `access`, `utaccess`, `params`, `lft`, `rgt`, `home`) VALUES
(1, 'mainmenu', 'Home', 'home', 'index.php?option=com_content&view=frontpage', 'component', 1, 0, 20, 0, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 3, 'num_leading_articles=1\nnum_intro_articles=4\nnum_columns=2\nnum_links=4\norderby_pri=\norderby_sec=front\nmulti_column_order=1\nshow_pagination=2\nshow_pagination_results=1\nshow_feed_link=1\nshow_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=0\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 1),
(2, 'mainmenu', 'Help desk', 'helpdesk', 'index.php?option=com_huruhelpdesk&view=cpanel', 'component', 1, 6, 34, 1, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'page_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0),
(3, 'mainmenu', 'Products', 'products', 'index.php?option=com_content&view=article&id=2', 'component', 1, 0, 20, 0, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0),
(4, 'mainmenu', 'SWT Matrix', 'swtmatrix', 'index.php?option=com_content&view=article&id=3', 'component', 1, 3, 20, 1, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0),
(5, 'mainmenu', 'Download', 'download', 'index.php?option=com_content&view=article&id=4', 'component', 1, 0, 20, 0, 3, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0),
(6, 'mainmenu', 'Support', 'support', '', 'separator', 1, 0, 0, 0, 5, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'menu_image=-1\n\n', 0, 0, 0),
(7, 'mainmenu', 'Features', 'swtmatrixfeatures', 'index.php?option=com_content&view=article&id=5', 'component', 1, 4, 20, 2, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0),
(8, 'SWT-Matrix', 'Features', 'features', 'index.php?Itemid=7', 'menulink', 1, 0, 0, 0, 1, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'menu_item=7\n\n', 0, 0, 0),
(11, 'mainmenu', 'Purchase', 'purchase', '', 'separator', 1, 0, 0, 0, 4, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'menu_image=-1\n\n', 0, 0, 0),
(12, 'mainmenu', 'Contact', 'contact', 'index.php?option=com_content&view=article&id=8', 'component', 1, 0, 20, 0, 6, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0),
(9, 'SWT-Matrix', 'API', 'api', 'index.php?Itemid=10', 'menulink', 1, 0, 0, 0, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'menu_item=10\n\n', 0, 0, 0),
(10, 'mainmenu', 'API', 'api', 'index.php?option=com_content&view=article&id=7', 'component', 1, 4, 20, 2, 2, 0, '0000-00-00 00:00:00', 0, 0, 0, 0, 'show_noauth=\nshow_title=\nlink_titles=\nshow_intro=\nshow_section=\nlink_section=\nshow_category=\nlink_category=\nshow_author=\nshow_create_date=\nshow_modify_date=\nshow_item_navigation=\nshow_readmore=\nshow_vote=\nshow_icons=\nshow_pdf_icon=\nshow_print_icon=\nshow_email_icon=\nshow_hits=\nfeed_summary=\npage_title=\nshow_page_title=1\npageclass_sfx=\nmenu_image=-1\nsecure=0\n\n', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `jos_menu_types`
--

DROP TABLE IF EXISTS `jos_menu_types`;
CREATE TABLE `jos_menu_types` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `menutype` varchar(75) NOT NULL default '',
  `title` varchar(255) NOT NULL default '',
  `description` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `menutype` (`menutype`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `jos_menu_types`
--

INSERT INTO `jos_menu_types` (`id`, `menutype`, `title`, `description`) VALUES
(1, 'mainmenu', 'Main Menu', 'The main menu for the site'),
(2, 'SWT-Matrix', 'SWT Matrix', '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_messages`
--

DROP TABLE IF EXISTS `jos_messages`;
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

DROP TABLE IF EXISTS `jos_messages_cfg`;
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

DROP TABLE IF EXISTS `jos_migration_backlinks`;
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

DROP TABLE IF EXISTS `jos_modules`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `jos_modules`
--

INSERT INTO `jos_modules` (`id`, `title`, `content`, `ordering`, `position`, `checked_out`, `checked_out_time`, `published`, `module`, `numnews`, `access`, `showtitle`, `params`, `iscore`, `client_id`, `control`) VALUES
(1, 'Main Menu', '', 2, 'left', 0, '0000-00-00 00:00:00', 0, 'mod_mainmenu', 0, 0, 1, 'menutype=mainmenu\nmenu_style=list\nstartLevel=0\nendLevel=0\nshowAllChildren=0\nwindow_open=\nshow_whitespace=0\ncache=1\ntag_id=\nclass_sfx=\nmoduleclass_sfx=_menu\nmaxdepth=10\nmenu_images=0\nmenu_images_align=0\nmenu_images_link=0\nexpand_menu=0\nactivate_parent=0\nfull_active_id=0\nindent_image=0\nindent_image1=\nindent_image2=\nindent_image3=\nindent_image4=\nindent_image5=\nindent_image6=\nspacer=\nend_spacer=\n\n', 1, 0, ''),
(2, 'Login', '', 1, 'login', 0, '0000-00-00 00:00:00', 1, 'mod_login', 0, 0, 1, '', 1, 1, ''),
(3, 'Popular', '', 3, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_popular', 0, 2, 1, '', 0, 1, ''),
(4, 'Recent added Articles', '', 4, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_latest', 0, 2, 1, 'ordering=c_dsc\nuser_id=0\ncache=0\n\n', 0, 1, ''),
(5, 'Menu Stats', '', 5, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_stats', 0, 2, 1, '', 0, 1, ''),
(6, 'Unread Messages', '', 1, 'header', 0, '0000-00-00 00:00:00', 1, 'mod_unread', 0, 2, 1, '', 1, 1, ''),
(7, 'Online Users', '', 2, 'header', 0, '0000-00-00 00:00:00', 1, 'mod_online', 0, 2, 1, '', 1, 1, ''),
(8, 'Toolbar', '', 1, 'toolbar', 0, '0000-00-00 00:00:00', 1, 'mod_toolbar', 0, 2, 1, '', 1, 1, ''),
(9, 'Quick Icons', '', 1, 'icon', 0, '0000-00-00 00:00:00', 1, 'mod_quickicon', 0, 2, 1, '', 1, 1, ''),
(10, 'Logged in Users', '', 2, 'cpanel', 0, '0000-00-00 00:00:00', 1, 'mod_logged', 0, 2, 1, '', 0, 1, ''),
(11, 'Footer', '', 0, 'footer', 0, '0000-00-00 00:00:00', 1, 'mod_footer', 0, 0, 1, '', 1, 1, ''),
(12, 'Admin Menu', '', 1, 'menu', 0, '0000-00-00 00:00:00', 1, 'mod_menu', 0, 2, 1, '', 0, 1, ''),
(13, 'Admin SubMenu', '', 1, 'submenu', 0, '0000-00-00 00:00:00', 1, 'mod_submenu', 0, 2, 1, '', 0, 1, ''),
(14, 'User Status', '', 1, 'status', 0, '0000-00-00 00:00:00', 1, 'mod_status', 0, 2, 1, '', 0, 1, ''),
(15, 'Title', '', 1, 'title', 0, '0000-00-00 00:00:00', 1, 'mod_title', 0, 2, 1, '', 0, 1, ''),
(16, 'breadcrumbs', '', 0, 'breadcrumb', 0, '0000-00-00 00:00:00', 1, 'mod_breadcrumbs', 0, 0, 1, 'showHome=0\nhomeText=\nshowLast=1\nseparator=\nmoduleclass_sfx=\ncache=0\n\n', 0, 0, ''),
(17, 'SWT Matrix Menu', '', 0, 'right', 0, '0000-00-00 00:00:00', 1, 'mod_mainmenu', 0, 0, 1, 'menutype=SWT-Matrix\nmenu_style=list\nstartLevel=0\nendLevel=0\nshowAllChildren=0\nwindow_open=\nshow_whitespace=0\ncache=1\ntag_id=\nclass_sfx=\nmoduleclass_sfx=\nmaxdepth=10\nmenu_images=0\nmenu_images_align=0\nmenu_images_link=0\nexpand_menu=0\nactivate_parent=0\nfull_active_id=0\nindent_image=0\nindent_image1=\nindent_image2=\nindent_image3=\nindent_image4=\nindent_image5=\nindent_image6=\nspacer=\nend_spacer=\n\n', 0, 0, ''),
(18, 'Search', '', 0, 'search', 0, '0000-00-00 00:00:00', 1, 'mod_search', 0, 0, 0, 'moduleclass_sfx=\nwidth=20\ntext=search\nbutton=\nbutton_pos=right\nimagebutton=\nbutton_text=\nset_itemid=\ncache=1\ncache_time=900\n\n', 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_modules_menu`
--

DROP TABLE IF EXISTS `jos_modules_menu`;
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
(16, 0),
(17, 4),
(17, 7),
(17, 10),
(18, 0);

-- --------------------------------------------------------

--
-- Table structure for table `jos_newsfeeds`
--

DROP TABLE IF EXISTS `jos_newsfeeds`;
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

DROP TABLE IF EXISTS `jos_plugins`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=38 ;

--
-- Dumping data for table `jos_plugins`
--

INSERT INTO `jos_plugins` (`id`, `name`, `element`, `folder`, `access`, `ordering`, `published`, `iscore`, `client_id`, `checked_out`, `checked_out_time`, `params`) VALUES
(1, 'Authentication - Joomla', 'joomla', 'authentication', 0, 1, 1, 1, 0, 0, '0000-00-00 00:00:00', ''),
(2, 'Authentication - LDAP', 'ldap', 'authentication', 0, 2, 0, 1, 0, 0, '0000-00-00 00:00:00', 'host=\nport=389\nuse_ldapV3=0\nnegotiate_tls=0\nno_referrals=0\nauth_method=bind\nbase_dn=\nsearch_string=\nusers_dn=\nusername=\npassword=\nldap_fullname=fullName\nldap_email=mail\nldap_uid=uid\n\n'),
(3, 'Authentication - GMail', 'gmail', 'authentication', 0, 4, 0, 0, 0, 0, '0000-00-00 00:00:00', ''),
(4, 'Authentication - OpenID', 'openid', 'authentication', 0, 3, 0, 0, 0, 0, '0000-00-00 00:00:00', ''),
(5, 'User - Joomla!', 'joomla', 'user', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', 'autoregister=1\n\n'),
(6, 'Search - Content', 'content', 'search', 0, 1, 1, 1, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\nsearch_content=1\nsearch_uncategorised=1\nsearch_archived=1\n\n'),
(7, 'Search - Contacts', 'contacts', 'search', 0, 3, 1, 1, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n'),
(8, 'Search - Categories', 'categories', 'search', 0, 4, 1, 0, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n'),
(9, 'Search - Sections', 'sections', 'search', 0, 5, 1, 0, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n'),
(10, 'Search - Newsfeeds', 'newsfeeds', 'search', 0, 6, 1, 0, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n'),
(11, 'Search - Weblinks', 'weblinks', 'search', 0, 2, 1, 1, 0, 0, '0000-00-00 00:00:00', 'search_limit=50\n\n'),
(12, 'Content - Pagebreak', 'pagebreak', 'content', 0, 10000, 1, 1, 0, 0, '0000-00-00 00:00:00', 'enabled=1\ntitle=1\nmultipage_toc=1\nshowall=1\n\n'),
(13, 'Content - Rating', 'vote', 'content', 0, 4, 1, 1, 0, 0, '0000-00-00 00:00:00', ''),
(14, 'Content - Email Cloaking', 'emailcloak', 'content', 0, 5, 1, 0, 0, 0, '0000-00-00 00:00:00', 'mode=1\n\n'),
(15, 'Content - Code Hightlighter (GeSHi)', 'geshi', 'content', 0, 5, 0, 0, 0, 0, '0000-00-00 00:00:00', ''),
(16, 'Content - Load Module', 'loadmodule', 'content', 0, 6, 1, 0, 0, 0, '0000-00-00 00:00:00', 'enabled=1\nstyle=0\n\n'),
(17, 'Content - Page Navigation', 'pagenavigation', 'content', 0, 2, 1, 1, 0, 0, '0000-00-00 00:00:00', 'position=1\n\n'),
(18, 'Editor - No Editor', 'none', 'editors', 0, 0, 1, 1, 0, 0, '0000-00-00 00:00:00', ''),
(19, 'Editor - TinyMCE', 'tinymce', 'editors', 0, 0, 1, 1, 0, 0, '0000-00-00 00:00:00', 'mode=advanced\nskin=0\ncompressed=0\ncleanup_startup=0\ncleanup_save=2\nentity_encoding=raw\nlang_mode=0\nlang_code=en\ntext_direction=ltr\ncontent_css=1\ncontent_css_custom=\nrelative_urls=1\nnewlines=0\ninvalid_elements=applet\nextended_elements=\ntoolbar=top\ntoolbar_align=left\nhtml_height=550\nhtml_width=750\nelement_path=1\nfonts=1\npaste=1\nsearchreplace=1\ninsertdate=1\nformat_date=%Y-%m-%d\ninserttime=1\nformat_time=%H:%M:%S\ncolors=1\ntable=1\nsmilies=1\nmedia=1\nhr=1\ndirectionality=1\nfullscreen=1\nstyle=1\nlayer=1\nxhtmlxtras=1\nvisualchars=1\nnonbreaking=1\ntemplate=0\nadvimage=1\nadvlink=1\nautosave=1\ncontextmenu=1\ninlinepopups=1\nsafari=1\ncustom_plugin=\ncustom_button=\n\n'),
(20, 'Editor - XStandard Lite 2.0', 'xstandard', 'editors', 0, 0, 0, 1, 0, 0, '0000-00-00 00:00:00', ''),
(21, 'Editor Button - Image', 'image', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', ''),
(22, 'Editor Button - Pagebreak', 'pagebreak', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', ''),
(23, 'Editor Button - Readmore', 'readmore', 'editors-xtd', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', ''),
(24, 'XML-RPC - Joomla', 'joomla', 'xmlrpc', 0, 7, 0, 1, 0, 0, '0000-00-00 00:00:00', ''),
(25, 'XML-RPC - Blogger API', 'blogger', 'xmlrpc', 0, 7, 0, 1, 0, 0, '0000-00-00 00:00:00', 'catid=1\nsectionid=0\n\n'),
(27, 'System - SEF', 'sef', 'system', 0, 1, 1, 0, 0, 0, '0000-00-00 00:00:00', ''),
(28, 'System - Debug', 'debug', 'system', 0, 2, 1, 0, 0, 0, '0000-00-00 00:00:00', 'queries=1\nmemory=1\nlangauge=1\n\n'),
(29, 'System - Legacy', 'legacy', 'system', 0, 3, 0, 1, 0, 0, '0000-00-00 00:00:00', 'route=0\n\n'),
(30, 'System - Cache', 'cache', 'system', 0, 4, 0, 1, 0, 0, '0000-00-00 00:00:00', 'browsercache=0\ncachetime=15\n\n'),
(31, 'System - Log', 'log', 'system', 0, 5, 0, 1, 0, 0, '0000-00-00 00:00:00', ''),
(32, 'System - Remember Me', 'remember', 'system', 0, 6, 1, 1, 0, 0, '0000-00-00 00:00:00', ''),
(33, 'System - Backlink', 'backlink', 'system', 0, 7, 0, 1, 0, 0, '0000-00-00 00:00:00', ''),
(34, 'System - Mootools Upgrade', 'mtupgrade', 'system', 0, 8, 0, 1, 0, 0, '0000-00-00 00:00:00', ''),
(35, 'System - ARTIO JoomSEF', 'joomsef', 'system', 0, -9, 1, 0, 0, 0, '0000-00-00 00:00:00', ''),
(36, 'System - Advanced Module Manager', 'advancedmodules', 'system', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', ''),
(37, 'System - NoNumber! Elements', 'nonumberelements', 'system', 0, 0, 1, 0, 0, 0, '0000-00-00 00:00:00', '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_polls`
--

DROP TABLE IF EXISTS `jos_polls`;
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
-- Table structure for table `jos_poll_data`
--

DROP TABLE IF EXISTS `jos_poll_data`;
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

DROP TABLE IF EXISTS `jos_poll_date`;
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

DROP TABLE IF EXISTS `jos_poll_menu`;
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
-- Table structure for table `jos_sections`
--

DROP TABLE IF EXISTS `jos_sections`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `jos_sections`
--

INSERT INTO `jos_sections` (`id`, `title`, `name`, `alias`, `image`, `scope`, `image_position`, `description`, `published`, `checked_out`, `checked_out_time`, `ordering`, `access`, `count`, `params`) VALUES
(1, 'Products', '', 'products', '', 'content', 'left', '<p>Products</p>', 1, 0, '0000-00-00 00:00:00', 1, 0, 3, '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_sefaliases`
--

DROP TABLE IF EXISTS `jos_sefaliases`;
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

DROP TABLE IF EXISTS `jos_sefexts`;
CREATE TABLE `jos_sefexts` (
  `id` int(11) NOT NULL auto_increment,
  `file` varchar(100) NOT NULL,
  `filters` text,
  `params` text,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `jos_sefexts`
--

INSERT INTO `jos_sefexts` (`id`, `file`, `filters`, `params`, `title`) VALUES
(1, 'com_wrapper.xml', NULL, 'ignoreSource=0\nitemid=1\noverrideId=', NULL),
(2, 'com_content.xml', '+^[0-9]*$=limit,limitstart,month,showall,year\n+^[a-zA-Z]+$=task,type,view\n+^[0-9]+(:[a-zA-Z0-9._-]+)?$=catid,id,sectionid', 'acceptVars=view; id; catid; sectionid; type; year; month; filter; limit; limitstart; task; showall', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `jos_sefexttexts`
--

DROP TABLE IF EXISTS `jos_sefexttexts`;
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

DROP TABLE IF EXISTS `jos_sefmoved`;
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

DROP TABLE IF EXISTS `jos_sefurls`;
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

DROP TABLE IF EXISTS `jos_sefurlword_xref`;
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

DROP TABLE IF EXISTS `jos_sefwords`;
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

DROP TABLE IF EXISTS `jos_session`;
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

INSERT INTO `jos_session` (`username`, `time`, `session_id`, `guest`, `userid`, `usertype`, `gid`, `client_id`, `data`) VALUES
('', '1300092450', 'bl90mttkfb37ptnnk54jeqhvm2', 1, 0, '', 0, 1, '__default|a:8:{s:15:"session.counter";i:4;s:19:"session.timer.start";i:1300092436;s:18:"session.timer.last";i:1300092447;s:17:"session.timer.now";i:1300092449;s:22:"session.client.browser";s:120:"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.127 Safari/534.16";s:8:"registry";O:9:"JRegistry":3:{s:17:"_defaultNameSpace";s:7:"session";s:9:"_registry";a:1:{s:7:"session";a:1:{s:4:"data";O:8:"stdClass":0:{}}}s:7:"_errors";a:0:{}}s:4:"user";O:5:"JUser":19:{s:2:"id";i:0;s:4:"name";N;s:8:"username";N;s:5:"email";N;s:8:"password";N;s:14:"password_clear";s:0:"";s:8:"usertype";N;s:5:"block";N;s:9:"sendEmail";i:0;s:3:"gid";i:0;s:12:"registerDate";N;s:13:"lastvisitDate";N;s:10:"activation";N;s:6:"params";N;s:3:"aid";i:0;s:5:"guest";i:1;s:7:"_params";O:10:"JParameter":7:{s:4:"_raw";s:0:"";s:4:"_xml";N;s:9:"_elements";a:0:{}s:12:"_elementPath";a:1:{i:0;s:65:"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element";}s:17:"_defaultNameSpace";s:8:"_default";s:9:"_registry";a:1:{s:8:"_default";a:1:{s:4:"data";O:8:"stdClass":0:{}}}s:7:"_errors";a:0:{}}s:9:"_errorMsg";N;s:7:"_errors";a:0:{}}s:13:"session.token";s:32:"a9031bcb250e744c7883a59eb33a61bc";}'),
('admin', '1300125993', '5hp899rg04382g6m8nt94g8m57', 0, 62, 'Super Administrator', 25, 1, '__default|a:8:{s:15:"session.counter";i:334;s:19:"session.timer.start";i:1300092436;s:18:"session.timer.last";i:1300125993;s:17:"session.timer.now";i:1300125993;s:22:"session.client.browser";s:120:"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.127 Safari/534.16";s:8:"registry";O:9:"JRegistry":3:{s:17:"_defaultNameSpace";s:7:"session";s:9:"_registry";a:5:{s:7:"session";a:1:{s:4:"data";O:8:"stdClass":0:{}}s:11:"application";a:1:{s:4:"data";O:8:"stdClass":1:{s:4:"lang";s:0:"";}}s:10:"com_cpanel";a:1:{s:4:"data";O:8:"stdClass":1:{s:9:"mtupgrade";O:8:"stdClass":1:{s:7:"checked";b:1;}}}s:9:"com_menus";a:1:{s:4:"data";O:8:"stdClass":1:{s:8:"menutype";s:8:"mainmenu";}}s:3:"sef";a:1:{s:4:"data";O:8:"stdClass":1:{s:7:"sefurls";O:8:"stdClass":1:{s:8:"viewmode";s:1:"0";}}}}s:7:"_errors";a:0:{}}s:4:"user";O:5:"JUser":19:{s:2:"id";s:2:"62";s:4:"name";s:13:"Administrator";s:8:"username";s:5:"admin";s:5:"email";s:31:"jacek.p.kolodziejczyk@gmail.com";s:8:"password";s:65:"22e72019da69849ad2c8be6adaddf246:qy4KjJ3Zg8OP6AJjhYrdeppD33YQnERh";s:14:"password_clear";s:0:"";s:8:"usertype";s:19:"Super Administrator";s:5:"block";s:1:"0";s:9:"sendEmail";s:1:"1";s:3:"gid";s:2:"25";s:12:"registerDate";s:19:"2011-03-13 21:18:07";s:13:"lastvisitDate";s:19:"2011-03-14 08:17:34";s:10:"activation";s:0:"";s:6:"params";s:0:"";s:3:"aid";i:2;s:5:"guest";i:0;s:7:"_params";O:10:"JParameter":7:{s:4:"_raw";s:0:"";s:4:"_xml";N;s:9:"_elements";a:0:{}s:12:"_elementPath";a:1:{i:0;s:65:"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element";}s:17:"_defaultNameSpace";s:8:"_default";s:9:"_registry";a:1:{s:8:"_default";a:1:{s:4:"data";O:8:"stdClass":0:{}}}s:7:"_errors";a:0:{}}s:9:"_errorMsg";N;s:7:"_errors";a:0:{}}s:13:"session.token";s:32:"a9031bcb250e744c7883a59eb33a61bc";}'),
('', '1300126224', '430d978e6de3bb55f95b8239693a7336', 1, 0, '', 0, 0, '__default|a:7:{s:22:"session.client.browser";s:120:"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.127 Safari/534.16";s:15:"session.counter";i:221;s:8:"registry";O:9:"JRegistry":3:{s:17:"_defaultNameSpace";s:7:"session";s:9:"_registry";a:2:{s:7:"session";a:1:{s:4:"data";O:8:"stdClass":0:{}}s:7:"hh_list";a:1:{s:4:"data";O:8:"stdClass":24:{s:4:"type";s:0:"";s:4:"user";s:0:"";s:4:"days";s:0:"";s:5:"stype";s:0:"";s:4:"task";s:0:"";s:5:"order";s:0:"";s:4:"sort";s:0:"";s:10:"searchuser";s:0:"";s:10:"searchdays";s:0:"";s:10:"searchtask";s:0:"";s:14:"searchusername";s:0:"";s:15:"searchproblemid";s:0:"";s:9:"searchrep";s:0:"";s:14:"searchcategory";s:0:"";s:16:"searchdepartment";s:0:"";s:12:"searchstatus";s:0:"";s:14:"searchpriority";s:0:"";s:13:"searchkeyword";s:0:"";s:13:"searchsubject";s:0:"";s:17:"searchdescription";s:0:"";s:14:"searchsolution";s:0:"";s:19:"searchstartdatefrom";s:0:"";s:17:"searchstartdateto";s:0:"";s:5:"count";s:0:"";}}}s:7:"_errors";a:0:{}}s:4:"user";O:5:"JUser":19:{s:2:"id";i:0;s:4:"name";N;s:8:"username";N;s:5:"email";N;s:8:"password";N;s:14:"password_clear";s:0:"";s:8:"usertype";N;s:5:"block";N;s:9:"sendEmail";i:0;s:3:"gid";i:0;s:12:"registerDate";N;s:13:"lastvisitDate";N;s:10:"activation";N;s:6:"params";N;s:3:"aid";i:0;s:5:"guest";i:1;s:7:"_params";O:10:"JParameter":7:{s:4:"_raw";s:0:"";s:4:"_xml";N;s:9:"_elements";a:0:{}s:12:"_elementPath";a:1:{i:0;s:65:"C:\\Dev\\wamp\\www\\netanel15\\libraries\\joomla\\html\\parameter\\element";}s:17:"_defaultNameSpace";s:8:"_default";s:9:"_registry";a:1:{s:8:"_default";a:1:{s:4:"data";O:8:"stdClass":0:{}}}s:7:"_errors";a:0:{}}s:9:"_errorMsg";N;s:7:"_errors";a:0:{}}s:19:"session.timer.start";i:1300090597;s:18:"session.timer.last";i:1300126221;s:17:"session.timer.now";i:1300126224;}');

-- --------------------------------------------------------

--
-- Table structure for table `jos_stats_agents`
--

DROP TABLE IF EXISTS `jos_stats_agents`;
CREATE TABLE `jos_stats_agents` (
  `agent` varchar(255) NOT NULL default '',
  `type` tinyint(1) unsigned NOT NULL default '0',
  `hits` int(11) unsigned NOT NULL default '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_stats_agents`
--


-- --------------------------------------------------------

--
-- Table structure for table `jos_templates_menu`
--

DROP TABLE IF EXISTS `jos_templates_menu`;
CREATE TABLE `jos_templates_menu` (
  `template` varchar(255) NOT NULL default '',
  `menuid` int(11) NOT NULL default '0',
  `client_id` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`menuid`,`client_id`,`template`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jos_templates_menu`
--

INSERT INTO `jos_templates_menu` (`template`, `menuid`, `client_id`) VALUES
('wm_jooroos', 0, 0),
('khepri', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `jos_users`
--

DROP TABLE IF EXISTS `jos_users`;
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=63 ;

--
-- Dumping data for table `jos_users`
--

INSERT INTO `jos_users` (`id`, `name`, `username`, `email`, `password`, `usertype`, `block`, `sendEmail`, `gid`, `registerDate`, `lastvisitDate`, `activation`, `params`) VALUES
(62, 'Administrator', 'admin', 'jacek.p.kolodziejczyk@gmail.com', '22e72019da69849ad2c8be6adaddf246:qy4KjJ3Zg8OP6AJjhYrdeppD33YQnERh', 'Super Administrator', 0, 1, 25, '2011-03-13 21:18:07', '2011-03-14 08:47:31', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `jos_weblinks`
--

DROP TABLE IF EXISTS `jos_weblinks`;
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

