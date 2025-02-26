**_IMPORTANT!! THE UML DIAGRAM IS PROGRAMATCIALLY RENDERED WITH MERMAIDJS. GO TO THE [GITHUB](https://github.com/varphi-online/335-LA1/tree/main) TO VIEW_**

# 335-LA1

Long Assignment 1 for CSC335 @ UofA

## Group Members

- Gabe Venegas
- Aidan Fuhrmann

## Video Demo

https://youtu.be/o09k_JYGhto

## Compile to Jar

```bash
javac -d out -sourcepath src src/main/controller/Controller.java &&
jar cfm app.jar MANIFEST.MF -C out . -C src . README.md && java -jar app.jar
```

Then run: `java -jar app.jar`

## UML Diagram
```mermaid
---
title: Model UML Diagram
---
classDiagram
    class Album {
        #String albumTitle
        #String artist
        #String Genre
        #int Year
        -Arraylist~String~ songs
        +getAlbumTitle() String
        +getArtist() String
        +getGenre() String
        +getYear() int
        +addSong(String song)
    }

    class Song {
        -String title
        -Optional~int~ rating
        -Boolean favorite
        +getRating() Optional~int~
        +setRating(int rating)
        +getTitle() String
        +getFavorite() Boolean
        +setFavorite()
    }

    Album <|-- Song : extends

    class Playlist {
        -ArrayList~Song~ songs
        -String name
        +getName() String
        +getSongs() ArrayList~Song~
        +addSong(Song song)
        +removeSong(Song song)
    }

    class MusicStore {
        #ArrayList~Album~ albums
        #ArrayList~Song~ songs
        +addAlbum()
        +addSong()
        +findAlbumTitle(String title) ArrayList~Album~
        +findAlbumArtist(String name) ArrayList~Album~
        +findSongTitle(String title) ArrayList~Song~
        +findSongArtist(String name) ArrayList~Song~
    }

    class LibraryModel {
        -ArrayList~Playlist~ playlists
        +addPlaylist(String name)
        +findPlaylist(String name) Playlist
        +getFavorites() Arraylist~Playlist~
    }

    LibraryModel <|-- MusicStore : extends
```

https://mermaid.js.org/syntax/classDiagram.html
