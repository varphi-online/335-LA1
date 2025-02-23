package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import main.model.Album;
import main.model.Song;
import main.model.MusicStore;

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

}
