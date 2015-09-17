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
        "sql" : "select Anime.id as _id, Anime.id, Author.id as authors, Genre.name as genres, Producer.name as producers, title, englishTitle, japaneseTitle, episodeCount, episodeLength, finishedAiringDate, startedAiringDate, synopsis, showType, popularity, rank FROM Anime, Genre, Producer, Author, Anime_has_Genre, Anime_has_Producer, Anime_has_Author WHERE Anime_has_Genre.idGenre = Genre.id AND Anime_has_Genre.idAnime = Anime.id AND Anime_has_Producer.idAnime = Anime.id AND Anime_has_Producer.idProducer = Producer.id AND Anime_has_Author.idAnime = Anime.id AND Anime_has_Author.idAuthor = Author.id AND type=\"anime\"",
        "index" : "animes",
        "type" : "anime",
        "elasticsearch.host" : "192.168.59.103"
    }
}
' | java \
    -cp "${lib}/*" \
    -Dlog4j.configurationFile=${bin}/log4j2.xml \
    org.xbib.tools.Runner \
    org.xbib.tools.JDBCImporter
