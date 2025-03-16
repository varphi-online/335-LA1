package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import main.model.Album;
import main.model.Song;
import main.model.MusicStore;

@Testable
class MusicStoreTest {
	private MusicStore foundationMusicStore;
	private Album foundationAlbum = new Album("21", "Adele", "Pop", 2011);
	private Song foundationSong = new Song("Take It All",foundationAlbum);
	
	public MusicStoreTest() throws IOException {
		foundationMusicStore = new MusicStore();
		foundationMusicStore.addAlbum(new Album("21", "Adele", "Pop", 2011));
		foundationMusicStore.addSong(new Song("Take It All",new Album("21", "Adele", "Pop", 2011)));
	}
	
	@Test
	void albumByTitleTest() {
		assertEquals(foundationMusicStore.findAlbumTitle("21").get(0).toString(), foundationAlbum.toString());
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
		// expect song order 1,3,2
		ArrayList<Song> foundSongs = foundationMusicStore.findSongRating(null);
		assertEquals(foundSongs.get(0).toString(), song1.toString());
		assertEquals(foundSongs.get(1).toString(), song3.toString());
		assertEquals(foundSongs.get(2).toString(), song2.toString());
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

}
