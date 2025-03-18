

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import model.Album;
import model.Playlist;
import model.Song;

@Testable
class PlaylistTest {
	private Album foundationAlbum = new Album("Dark Side of the Moon", "Pink Floyd", "Psychedelic Rock", 1973);
	private Song foundationSong = new Song("Money", foundationAlbum);
	private Playlist foundationPlaylist = new Playlist("test");

	@Test
	void addSongTest() {
		foundationPlaylist.addSong(foundationSong);
		assertEquals(foundationPlaylist.getSongs().get(0).toString(), new Song("Money", foundationAlbum).toString());
	}
	
	@Test
	void removeSongTest() {
		foundationPlaylist.addSong(foundationSong);
		foundationPlaylist.removeSong(foundationSong);
		assertTrue(foundationPlaylist.getSongs().isEmpty());
	}

}
