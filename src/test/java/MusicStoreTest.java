

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import model.Album;
import model.Song;
import model.MusicStore;

@Testable
class MusicStoreTest {
	private MusicStore foundationMusicStore;
	private Album foundationAlbum = new Album("21", "Adele", "Pop", 2011);
	private Song foundationSong = new Song("Take It All",foundationAlbum);
	
	public MusicStoreTest() throws IOException {
		foundationMusicStore = new MusicStore();
		foundationMusicStore.addAlbum(foundationAlbum);
		foundationMusicStore.addSong(new Song("Take It All",new Album("21", "Adele", "Pop", 2011)));
	}
	
	@Test
	void albumByTitleTest() {
		assertEquals(foundationMusicStore.findAlbumTitle("21").get(0).toString(), foundationAlbum.toString());
	}

	@Test 
	void albumByTitleEscapeTest() {
		assertEquals(foundationMusicStore.findAlbumTitle("21", true).get(0).hashCode(), foundationAlbum.hashCode());
	}
	
	@Test
	void albumByArtistTest() {
		assertEquals(foundationMusicStore.findAlbumArtist("Adele").get(0).toString(), foundationAlbum.toString());
	}
	
	@Test
	void songByAlbumTest() {
		assertEquals(foundationMusicStore.findSongAlbum("21").get(0).toString(), foundationSong.toString());
	}
	
	@Test
	void songByArtistTest() {
		assertEquals(foundationMusicStore.findSongArtist("Adele").get(0).toString(), foundationSong.toString());
	}
	
	@Test
	void songByTitleTest() {
		assertEquals(foundationMusicStore.findSongTitle("Take It All").get(0).toString(), foundationSong.toString());
	}

	@Test
	void songByRatingAllTest() {
		Song song1 = new Song("Rolling in the Deep", foundationAlbum);
		Song song2 = new Song("Someone Like You", foundationAlbum);
		Song song3 = new Song("Set Fire to the Rain", foundationAlbum);
		song1.setRating(5);
		song2.setRating(3);
		song3.setRating(4);
		foundationMusicStore.addSong(song1);
		foundationMusicStore.addSong(song2);
		foundationMusicStore.addSong(song3);
		ArrayList<Song> foundSongs = foundationMusicStore.findSongRating(null);
		assertEquals(foundSongs.get(1).toString(), song1.toString());
		assertEquals(foundSongs.get(2).toString(), song2.toString());
		assertEquals(foundSongs.get(3).toString(), song3.toString());
	}

	@Test
	void songByRatingTest() {
		Song song1 = new Song("Rolling in the Deep", foundationAlbum);
		Song song2 = new Song("Someone Like You", foundationAlbum);
		Song song3 = new Song("Set Fire to the Rain", foundationAlbum);
		song1.setRating(5);
		song2.setRating(4);
		song3.setRating(4);
		foundationMusicStore.addSong(song1);
		foundationMusicStore.addSong(song2);
		foundationMusicStore.addSong(song3);
		// expect song order 2,3
		ArrayList<Song> foundSongs = foundationMusicStore.findSongRating(4);
		assertEquals(foundSongs.get(0).toString(), song2.toString());
		assertEquals(foundSongs.get(1).toString(), song3.toString());
		assertEquals(foundSongs.size(), 2);
	}

	@Test
	void shuffleSongsTest() {
        for (int i=0; i<10; i++) {
            foundationMusicStore.addSong(new Song("Song " + i, foundationAlbum));
        }
        ArrayList<Song> before = foundationMusicStore.getSongs();
        foundationMusicStore.shuffleSongs();
        ArrayList<Song> after = foundationMusicStore.getSongs();
        assertNotEquals(before.toString(), after.toString());
	}

	@Test
	void findSongGenreTest() {
		Song song1 = new Song("s1", new Album("t1", "a1", "Alternative", 1));
		Song song2 = new Song("s2", new Album("t2", "a2", "Rock", 2));
		foundationMusicStore.addSong(song1);
		foundationMusicStore.addSong(song2);
		ArrayList<Song> foundAlt = foundationMusicStore.findSongGenre("Alternative");
		ArrayList<Song> foundRock = foundationMusicStore.findSongGenre("Rock");
		assertEquals(foundAlt.size(), 1);
		assertEquals(foundRock.size(), 1);
		assertEquals(foundAlt.get(0).toString(), song1.toString());
		assertEquals(foundRock.get(0).toString(), song2.toString());
	}

	@Test
	void findAlbumSongTest() {
		Album album1 = new Album("t1", "a1", "g1", 1);
		Album album2 = new Album("t2", "a2", "g2", 2);
		Song song1 = new Song("s1", album1);
		Song song2 = new Song("s2", album2);
		foundationMusicStore.addSong(song1);
		foundationMusicStore.addSong(song2);
		foundationMusicStore.addAlbum(album1);
		foundationMusicStore.addAlbum(album2);
		ArrayList<Album> found = foundationMusicStore.findAlbumSong("s1");
		assertEquals(found.size(), 1);
		assertEquals(found.get(0).toString(), album1.toString());
	}

	@Test
	void getSongsTest() {
		assertEquals(foundationMusicStore.getSongs().get(0).toString(), foundationSong.toString());
	}

}
