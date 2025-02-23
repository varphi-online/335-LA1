# 335-LA1
Long Assignment 1 for CSC335 (University of Arizona)

https://mermaid.js.org/syntax/classDiagram.html

## Todo:
- Albums should print all of the songs attached to them
- List of favorites

```mermaid
---
title: Model UML Diagram
---
classDiagram
    class Song {
        -String title
        -int Rating
        +getRating() int
        +setRating(int rating)
        +getTitle() String
    }

    class Album {
        -String albumTitle
        -String artist
        -String Genre
        -int Year
        +getAlbumTitle() String
        +getArtist() String
        +getGenre() String
        +getYear() int
    }

    class Playlist {
        -ArrayList~Song~ songs
        -String name
        +getName() String
        +getSongs() ArrayList~Song~
        +addSong(Song song)
        +removeSong(Song song)
    }

    Album <|-- Song : extends

    class MusicStore {
        #ArrayList~Album~ albums
        #ArrayList~Song~ songs
        +findAlbumTitle(String title) ArrayList~Album~
        +findAlbumArtist(String name) ArrayList~Album~
        +findSongTitle(String title) ArrayList~Song~
        +findSongArtist(String name) ArrayList~Song~
    }

    class LibraryModel {
        -ArrayList~Playlist~ playlists
        -ArrayList~Song~ favorites
        +addPlaylist(String name)
        +findPlaylist(String name) Playlist
    }

    LibraryModel <|-- MusicStore : extends
```