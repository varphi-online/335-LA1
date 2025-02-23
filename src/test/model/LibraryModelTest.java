package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import main.model.Album;
import main.model.LibraryModel;
import main.model.MusicStore;
import main.model.Playlist;
import main.model.Song;

class LibraryModelTest {
	private LibraryModel foundationLibraryModel = new LibraryModel();
	private Album foundationAlbum = new Album("21", "Adele", "Pop", 2011);
	private Song foundationSong = new Song("Take It All",foundationAlbum);

	

	@Test
	void getFavoritesTest() {
		foundationLibraryModel.addSong(foundationSong);
		foundationSong.setFavorite(true);
		// Test against non-favorited song to assure object uniqueness
		assertNotEquals(foundationLibraryModel.getFavorites().get(0).toString(), new Song("Take It All",foundationAlbum).toString());
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

}
