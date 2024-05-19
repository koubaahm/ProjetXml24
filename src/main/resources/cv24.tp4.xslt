<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns2="http://univ.fr/cv24" exclude-result-prefixes="ns2"
    expand-text="yes">

    <xsl:output method="html"
        doctype-public="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
        doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
        encoding="UTF-8" indent="yes" html-version="5" />

    <!-- Template pour formater les dates -->
    <xsl:template name="formatDate">
        <xsl:param name="date" />
        <xsl:variable name="year" select="substring($date, 1, 4)" />
        <xsl:variable name="month" select="substring($date, 6, 2)" />
        <xsl:variable name="day" select="substring($date, 9, 2)" />
        <xsl:variable name="monthName">
            <xsl:choose>
                <xsl:when test="$month = '01'">Janv</xsl:when>
                <xsl:when test="$month = '02'">Fevr</xsl:when>
                <xsl:when test="$month = '03'">Mar</xsl:when>
                <xsl:when test="$month = '04'">Avr</xsl:when>
                <xsl:when test="$month = '05'">Mai</xsl:when>
                <xsl:when test="$month = '06'">Juin</xsl:when>
                <xsl:when test="$month = '07'">Jui</xsl:when>
                <xsl:when test="$month = '08'">Aout</xsl:when>
                <xsl:when test="$month = '09'">Sept</xsl:when>
                <xsl:when test="$month = '10'">Oct</xsl:when>
                <xsl:when test="$month = '11'">Nov</xsl:when>
                <xsl:when test="$month = '12'">Dec</xsl:when>
            </xsl:choose>
        </xsl:variable>
        <xsl:value-of select="concat($day, ' ', $monthName, ' ', $year)" />
    </xsl:template>

    <!-- Template pour formater les numéros de téléphone -->
    <xsl:template name="formatPhoneNumber">
        <xsl:param name="phone" />
        <xsl:variable name="trimmedPhone" select="translate($phone, ' ', '')" />
        <xsl:choose>
            <xsl:when test="starts-with($trimmedPhone, '+33')">
                <xsl:value-of select="concat('+33 (0)', substring($trimmedPhone, 4, 1), ' ', substring($trimmedPhone, 5, 2), ' ', substring($trimmedPhone, 7, 2), ' ', substring($trimmedPhone, 9, 2), ' ', substring($trimmedPhone, 11))" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$phone" />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:variable name="monthNames" select="'Janvier Février Mars Avril Mai Juin Juillet Août Septembre Octobre Novembre Décembre'" />

    <xsl:template match="ns2:cv24">
        <html>
            <head>
                <title>CV24 - XSLT V1.0</title>
                <link rel="stylesheet" type="text/css" href="cv24.css" />
            </head>
            <body>
                <h1><strong>CV24 - XSLT V1.0</strong></h1>
                <h2>
                    <p>Le <xsl:value-of select="day-from-date(current-date())" /> <xsl:value-of select="tokenize($monthNames, ' ')[month-from-date(current-date())]" /> <xsl:value-of select="year-from-date(current-date())" /></p>
                    <p><strong><xsl:value-of select="ns2:identite/genre" /> <xsl:value-of select="ns2:identite/nom" /> <xsl:value-of select="ns2:identite/prenom" /></strong></p>
                    <p>Tel : <xsl:call-template name="formatPhoneNumber">
                            <xsl:with-param name="phone" select="ns2:identite/tel" />
                        </xsl:call-template>
                    </p>
                    <p>Mel : <xsl:value-of select="ns2:identite/mel" /></p>
                </h2>
                <h2>
                    <strong><xsl:value-of select="ns2:objectif" /></strong>
                    <p>Demande de <xsl:value-of select="ns2:objectif/@statut" /></p>
                </h2>
		<h2>
    <strong>Experience professionnelle</strong>
    <br />
    <ol>
        <xsl:for-each select="ns2:prof/ns2:detail">
            <li>
                <xsl:choose>
                    <xsl:when test="ns2:datefin">
                        <xsl:value-of select="ns2:titre" />
                        <xsl:text> (du </xsl:text>
                        <xsl:call-template name="formatDate">
                            <xsl:with-param name="date" select="ns2:datedeb" />
                        </xsl:call-template>
                        <xsl:text> au </xsl:text>
                        <xsl:call-template name="formatDate">
                            <xsl:with-param name="date" select="ns2:datefin" />
                        </xsl:call-template>
                        <xsl:text>)</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="ns2:titre" />
                        <xsl:text> (depuis le </xsl:text>
                        <xsl:call-template name="formatDate">
                            <xsl:with-param name="date" select="ns2:datedeb" />
                        </xsl:call-template>
                        <xsl:text>)</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
                <br />
            </li>
        </xsl:for-each>
    </ol>
</h2>
<h2>
    <strong>Diplome</strong>
    <br />
    <xsl:for-each select="ns2:competences/diplome">
        <xsl:sort select="@niveau" data-type="number" order="descending"/>
        <li>
            <xsl:value-of select="titre"/>
            <xsl:text> (niveau </xsl:text>
            <xsl:value-of select="@niveau"/>
            <xsl:text>) - </xsl:text>
            <xsl:call-template name="formatDate">
                <xsl:with-param name="date" select="date"/>
            </xsl:call-template>
            <xsl:text>, </xsl:text>
            <xsl:value-of select="institut"/>
            <br/>
        </li>
    </xsl:for-each>
</h2>


<h2>
    <strong>Certifications</strong>
    <br />
    <xsl:for-each select="ns2:competences/certif">
        <xsl:call-template name="formatDate">
            <xsl:with-param name="date" select="datedeb" />
        </xsl:call-template>
        <xsl:text>-</xsl:text>
        <xsl:if test="ns2:datefin">
            <xsl:call-template name="formatDate">
                <xsl:with-param name="date" select="datefin" />
            </xsl:call-template>
        </xsl:if>
        <xsl:text> </xsl:text>
        <xsl:value-of select="titre" />
        <br />
    </xsl:for-each>
</h2>

				<h2>
			
					<xsl:for-each select="ns2:competences/certif">

						<xsl:call-template name="formatDate">
							<xsl:with-param name="date" select="datedeb" />
						</xsl:call-template>
						<xsl:text>-</xsl:text>
						<xsl:if test="datefin">
							<xsl:call-template name="formatDate">
								<xsl:with-param name="date" select="datefin" />
							</xsl:call-template>
						</xsl:if>
						<xsl:text> </xsl:text>
						<xsl:value-of select="titre" />
						<br />
					</xsl:for-each>

				</h2>
				<h2>
					<strong>Langues</strong>
					<br />
					<ul>
						<xsl:for-each select="ns2:divers/lv">

							<li>
								<xsl:value-of select="@lang" />
								<xsl:text>: </xsl:text>
								<xsl:value-of select="@cert" />
								<xsl:if test="@nivi != ''">
									<xsl:text> (</xsl:text>
									<xsl:value-of select="@nivi" />
									<xsl:text>)</xsl:text>
								</xsl:if>
								<xsl:text> - Niveau </xsl:text>
								<xsl:value-of select="@nivs" />
							</li>
						</xsl:for-each>
					</ul>
				</h2>

				<h2>
				<strong>Divers</strong>
<br />
<ul>
    <xsl:for-each select="ns2:divers/autre">
        <!-- Test pour voir si au moins un des attributs contient une valeur -->
        <xsl:if test="string-length(@titre) > 0 or string-length(@comment) > 0">
            <li>
                <!-- Affiche le commentaire s'il existe -->
                <xsl:if test="string-length(@comment) > 0">
                    <xsl:value-of select="@comment" />
                </xsl:if>

                <!-- Affiche un deux-points seulement si les deux champs sont présents -->
                <xsl:if test="string-length(@comment) > 0 and string-length(@titre) > 0">
                    <xsl:text>: </xsl:text>
                </xsl:if>

                <!-- Affiche le titre s'il existe -->
                <xsl:if test="string-length(@titre) > 0">
                    <xsl:value-of select="@titre" />
                </xsl:if>
            </li>
        </xsl:if>
    </xsl:for-each>
</ul>

				</h2>

			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
