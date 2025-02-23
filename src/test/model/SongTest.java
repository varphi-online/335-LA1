package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import main.model.Album;
import main.model.Song;

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
		assertEquals(foundationSong.getRating(), Optional.of(3));
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
		assertEquals(foundationSong.getRating(), Optional.empty());
	}
	
	@Test
	void favoriteTest() {
		foundationSong.setFavorite(true);
		assertTrue(foundationSong.getFavorite());
	}
	
	@Test
	void printTest() {
		assertEquals(foundationSong.toString(), "│ Money                                    │ Pink Floyd               │ Dark Side of the Moon         │ Psych │ 1973 │        │");
	}
}
