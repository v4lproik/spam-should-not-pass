#!/bin/sh

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
bin=${DIR}/../bin
lib=${DIR}/../lib

echo '
{
    "type" : "jdbc",
    "jdbc" : {
        "url" : "jdbc:mysql://192.168.59.103:3306/googlanime",
        "user" : "root",
        "password" : "s3cretP4ssword123456789OverrideInPipeline",
        "sql" : "select *, id as _id from Anime where type=\"manga\"",
        "index" : "mangas",
        "type" : "manga",
        "elasticsearch.host" : "192.168.59.103"
    }
}
' | java \
    -cp "${lib}/*" \
    -Dlog4j.configurationFile=${bin}/log4j2.xml \
    org.xbib.tools.Runner \
    org.xbib.tools.JDBCImporter
