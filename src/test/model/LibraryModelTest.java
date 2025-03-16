package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import main.model.Album;
import main.model.LibraryModel;
import main.model.MusicStore;
import main.model.Playlist;
import main.model.Song;

@Testable
class LibraryModelTest {
	private LibraryModel foundationLibraryModel = new LibraryModel();
	private Album foundationAlbum = new Album("21", "Adele", "Pop", 2011);
	private Song foundationSong = new Song("Take It All",foundationAlbum);

	

	@Test
	void getFavoritesTest() {
		foundationLibraryModel.addSong(foundationSong);
		foundationSong.setFavorite(true);
		// Test against non-favorited song to assure object uniqueness
		assertNotEquals(foundationLibraryModel.getFavorites(), new Song("Take It All",foundationAlbum).getFavorite());
	}
	
	@Test
	void addPlaylistNameTest() {
		foundationLibraryModel.addPlaylist("Bangers");
		assertEquals(foundationLibraryModel.findPlaylist("Bangers").get(0).toString(), new Playlist("Bangers").toString());
	}
	
	@Test
	void addPlaylistTest() {
		foundationLibraryModel.addPlaylist(new Playlist("Bangers"));
		assertEquals(foundationLibraryModel.findPlaylist("Bangers").get(0).toString(), new Playlist("Bangers").toString());
	}

	@Test
	void getTopPlayedSongsTest() {
		Song song1 = new Song("s1",foundationAlbum);
		Song song2 = new Song("s2",foundationAlbum);
		Song song3 = new Song("s3",foundationAlbum);
		song1.play();
		song1.play(); // song1: 2 plays
		song2.play();
		song2.play();
		song2.play(); // song2: 3 plays
		song3.play(); // song3: 1 plays
		foundationLibraryModel.addSong(song1);
		foundationLibraryModel.addSong(song2);
		foundationLibraryModel.addSong(song3);
		assertEquals(foundationLibraryModel.getTopPlayedSongs(), Arrays.asList(song2, song1, song3));
	}

	@Test
	void getMostRecentSongsTest() {
		Song song1 = new Song("s1", foundationAlbum);
        Song song2 = new Song("s2", foundationAlbum);
        Song song3 = new Song("s3", foundationAlbum);
        sleep(10);
        song1.play();
        sleep(10);
        song3.play();
        foundationLibraryModel.addSong(song1);
        foundationLibraryModel.addSong(song2);
        foundationLibraryModel.addSong(song3);
        // order expected 3, 1, 2
        ArrayList<Song> recentSongs = foundationLibraryModel.getMostRecentSongs();
        assertEquals(song3, recentSongs.get(0));
        assertEquals(song1, recentSongs.get(1));
        assertEquals(song2, recentSongs.get(2));
	}

	@Test
	void playSongTest() {
		foundationLibraryModel.addSong(foundationSong);
		assertTrue(foundationLibraryModel.playSong("Take It All"));
		assertFalse(foundationLibraryModel.playSong("Not a Song"));
		assertEquals(foundationLibraryModel.getNowPlaying(), foundationSong);
	}

	@Test void getNowPlayingTest() {
		assertNull(foundationLibraryModel.getNowPlaying());
		foundationLibraryModel.addSong(foundationSong);
		foundationLibraryModel.playSong("Take It All");
		assertEquals(foundationLibraryModel.getNowPlaying(), foundationSong);
	}

	@Test 
	void removeSongTest() {
		foundationLibraryModel.addSong(foundationSong);
		assertTrue(foundationLibraryModel.removeSong("Take It All"));
		assertFalse(foundationLibraryModel.removeSong("Not a Song"));
		assertEquals(foundationLibraryModel.getNowPlaying(), null);
	}

	@Test 
	void removeAlbumTest() {
		foundationLibraryModel.addAlbum(foundationAlbum);
		assertTrue(foundationLibraryModel.removeAlbum("21"));
		assertFalse(foundationLibraryModel.removeAlbum("Not an Album"));
		assertEquals(foundationLibraryModel.getNowPlaying(), null);
	}

	/**
     * Helper func to cause intentional delay in ms
     */
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
