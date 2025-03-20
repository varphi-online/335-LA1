

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import model.Album;
import model.LibraryModel;
import model.Playlist;
import model.Song;

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
		assertEquals(foundationLibraryModel.retrieveTopPlayedSongs(), Arrays.asList(song2, song1, song3));
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
        ArrayList<Song> recentSongs = foundationLibraryModel.retrieveMostRecentSongs();
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
		foundationLibraryModel.removeSong("Take It All");
		foundationLibraryModel.removeSong("Not a Song");
		assertEquals(foundationLibraryModel.findSongArtist(""), new ArrayList<Song>());
	}

	@Test 
	void removeAlbumTest() {
		foundationLibraryModel.addAlbum(foundationAlbum);
		foundationLibraryModel.removeAlbum("21");
		foundationLibraryModel.removeAlbum("Not an Album");
		assertEquals(foundationLibraryModel.findAlbumArtist(""), new ArrayList<Album>());
	}

	@Test 
	void getAutoPlaylistsTest() {
		Album album1 = new Album("t1", "a1", "g1", 2000);
		Album album2 = new Album("t2", "a2", "g2", 2001);
		Album album3 = new Album("t3", "a3", "g3", 2002);
		int size1 = 5;
		int size2 = 10;
		int size3 = 12;
		for (int i = 0; i < 15; i++) {
			if (i < size1) foundationLibraryModel.addSong(new Song("s" + i, album1));
			if (i < size2) foundationLibraryModel.addSong(new Song("s" + i, album2));
			if (i < size3) foundationLibraryModel.addSong(new Song("s" + i, album3));
		}
		ArrayList<Playlist> autoPlaylists = foundationLibraryModel.retrieveAutoPlaylists();
		assertEquals(autoPlaylists.size(), 2);
		assertEquals(autoPlaylists.get(0).getSongs().size(), size2);
		assertEquals(autoPlaylists.get(1).getSongs().size(), size3);
		assertEquals(autoPlaylists.get(0).getName(), "g2 Songs");
		assertEquals(autoPlaylists.get(1).getName(), "g3 Songs");
	}

	@Test
	void getTopRatedSongsTest() {
		Song song1 = new Song("s1", foundationAlbum);
		Song song2 = new Song("s2", foundationAlbum);
		Song song3 = new Song("s3", foundationAlbum);
		song1.setRating(5);
		song2.setRating(4);
		song3.setRating(3);
		foundationLibraryModel.addSong(song1);
		foundationLibraryModel.addSong(song2);
		foundationLibraryModel.addSong(song3);
		Playlist expectedPlaylist = new Playlist("Top Rated Songs");
		expectedPlaylist.addSong(song1);
		expectedPlaylist.addSong(song2);
		assertEquals(foundationLibraryModel.retrieveTopRatedSongs().toString(), expectedPlaylist.toString());
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
