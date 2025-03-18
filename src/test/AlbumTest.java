package test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import main.model.Album;

@Testable
class AlbumTest {
	private Album foundationAlbum = new Album("Dark Side of the Moon", "Pink Floyd", "Psychedelic Rock", 1973);

	@Test
	void printTest() {
		assertEquals(foundationAlbum.toString(), """
				├───────────────────────────────┬─────────────────────┬──────────────────────────┬──────┤
				│ Dark Side of the Moon         │ Pink Floyd          │ Psychedelic Rock         │ 1973 │
				├───────────────────────────────┴─────────────────────┴──────────────────────────┴──────┤""");
	}
	
	@Test
	void songTest() {
		foundationAlbum.addSong("Money");
		assertEquals(foundationAlbum.toString(), """
				├───────────────────────────────┬─────────────────────┬──────────────────────────┬──────┤
				│ Dark Side of the Moon         │ Pink Floyd          │ Psychedelic Rock         │ 1973 │
				├───────────────────────────────┴─────────────────────┴──────────────────────────┴──────┤
				│     1.  Money                                                                         │""");
	}
	

}
