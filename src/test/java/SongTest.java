

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import model.Album;
import model.Song;


@Testable
class SongTest {
	private Album foundationAlbum = new Album("Dark Side of the Moon", "Pink Floyd", "Psychedelic Rock", 1973);
	private Song foundationSong = new Song("Money", foundationAlbum);

	@Test
	void copyTest() {
		Song copy = new Song(foundationSong);
		assertEquals(copy.getAlbumTitle(),"Dark Side of the Moon");
		assertEquals(copy.getArtist(),"Pink Floyd");
		assertEquals(copy.getGenre(),"Psychedelic Rock");
		assertEquals(copy.getYear(), 1973);
		assertEquals(copy.getTitle(),"Money");
	}
	
	@Test
	void rateTest() {
		foundationSong.setRating(3);
		assertEquals(foundationSong.getRating(), 3);
	}
	
	@Test
	void rateFavoriteTest() {
		foundationSong.setRating(5);
		assertTrue(foundationSong.getFavorite());
	}
	
	@Test
	void removeRating() {
		foundationSong.setRating(3);
		foundationSong.setRating(-1);
		assertEquals(foundationSong.getRating(), -1);
	}
	
	@Test
	void favoriteTest() {
		foundationSong.setFavorite(true);
		assertTrue(foundationSong.getFavorite());
	}
	
	@Test
	void printTest() {
		assertEquals(foundationSong.toString(), "│ Money                                    │ Pink Floyd               │ Dark Side of the Moon         │ Psych │ 1973 │        │ ");
	}
}
