<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" 
            indent="yes" 
            omit-xml-declaration="yes"
            encoding="UTF-8"
             />

<!-- 
    Generate the page frame 
-->
<xsl:include href="../common/util/convertIllegalCharactersInFileName.xsl" />
<xsl:include href="../common/util/string-replace-all.xsl" />
<xsl:include href="../common/util/percentage.xsl" />
<xsl:include href="../common/util/format-bytes.xsl" />
<xsl:include href="../common/util/format-msec-to-h.xsl" />
<xsl:include href="../common/util/create-totals-td.xsl" />
<xsl:include href="../common/util/filtered-footer-row.xsl" />
<xsl:include href="../common/util/load-profile-table.xsl" />

<xsl:include href="text/descriptions.xsl" />

<xsl:include href="sections/load-profile.xsl" />
<xsl:include href="sections/comment.xsl" />
<xsl:include href="sections/general.xsl" />
<xsl:include href="sections/summary.xsl" />
<xsl:include href="sections/network-summary.xsl" />

<xsl:include href="util/network-table.xsl" />
<xsl:include href="util/summary-timer-row.xsl" />

<xsl:include href="../common/sections/head.xsl" />
<xsl:include href="../common/sections/header.xsl" />
<xsl:include href="sections/navigation.xsl" />
<xsl:include href="../common/sections/footer.xsl" />

<xsl:include href="../common/sections/javascript.xsl" />

<xsl:template match="/testreport">

<xsl:text disable-output-escaping="yes">&lt;!</xsl:text><xsl:text>DOCTYPE html</xsl:text><xsl:text disable-output-escaping="yes">&gt;&#13;</xsl:text>
<html lang="en">    
<head>
    <title>Xceptance LoadTest Report - Overview</title>

	<xsl:call-template name="head" />
</head>
<body id="loadtestreport">
<div id="container">
    <div id="content">
        <xsl:call-template name="header" />

        <div id="data-content">
        	<!--
        		************************************
        		* Load Profile
        		************************************
        	-->
			<xsl:call-template name="load-profile">
				<xsl:with-param name="rootNode" select="configuration" />
			</xsl:call-template>

            <!--
                ************************************
                * Test Comment
                ************************************
            -->
            <xsl:call-template name="testcomment">
                <xsl:with-param name="rootNode" select="configuration/comments" />
            </xsl:call-template>

        	<!--
        		************************************
        		* General
        		************************************
        	-->
			<xsl:call-template name="general">
				<xsl:with-param name="rootNode" select="general" />
			</xsl:call-template>

			<!--
        		************************************
        		* Timer Summary
        		************************************
        	-->
        	<xsl:call-template name="summary"/>

        	<!--
        		************************************
        		* Network Summary
        		************************************
        	-->
			<xsl:call-template name="network-summary">
				<xsl:with-param name="rootNode" select="general" />
			</xsl:call-template>

        </div> <!-- data-content -->

        <xsl:call-template name="footer" />
    </div> <!-- content -->
</div> <!-- container -->    

<xsl:call-template name="javascript" />

</body>
</html>

</xsl:template>

</xsl:stylesheet>
