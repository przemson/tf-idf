import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchEngineTest {

    // SUT
    private SearchEngine searchEngine;

    @BeforeEach
    public void setUp() {
        searchEngine = new SearchEngineImpl();
    }

    @Test
    public void shouldFindTerm() {
        // given
        searchEngine.indexDocument("1", "ala ma kotA[");
        searchEngine.indexDocument("2", "ala ma psa");
        searchEngine.indexDocument("3", "mala nie ma kota.");
        searchEngine.indexDocument("4", "bala nie miala kota");

        // when
        List<IndexEntry> entries = searchEngine.search("kota");

        // then
        assertEquals(2, entries.size());
    }

    @Test
    public void shouldNotFindTerm() {
        // given
        searchEngine.indexDocument("1", "ala ma kotki");

        // when
        List<IndexEntry> entries = searchEngine.search("kot");

        // then
        assertEquals(0, entries.size());
    }

    @Test
    public void shouldReturnZeroScoreWhenTermInAllDocuments() {
        // given
        searchEngine.indexDocument("1", "ala ma kotki");
        searchEngine.indexDocument("2", "ala ma psy]");
        searchEngine.indexDocument("3", "ala ma kanarki");
        searchEngine.indexDocument("4", "ala ma szczury");

        // when
        List<IndexEntry> entries = searchEngine.search("ala");

        // then
        assertEquals(0.0, entries.get(0).getScore());
    }

    @Test
    public void shouldReturnNonZeroScoreWhenTermInSomeDocuments() {
        // given
        searchEngine.indexDocument("1", "ala ma kotki");
        searchEngine.indexDocument("2", "ala ma psy]");
        searchEngine.indexDocument("3", "ala ma kanarki");
        searchEngine.indexDocument("4", "ala ma szczury");

        // when
        List<IndexEntry> entries = searchEngine.search("kanarki");

        // then
        assertTrue(entries.get(0).getScore() > 0.0);
    }

    @Test
    public void shouldReturnEmptyListWhenNoDocuments() {
        // when
        List<IndexEntry> entries = searchEngine.search("test");

        // then
        assertEquals(0, entries.size());
    }

    @Test
    public void shouldNotReplaceDocumentWhenAddedWithSameIndex() {
        // given
        searchEngine.indexDocument("1", "ala ma kotki");
        searchEngine.indexDocument("1", "ala ma psy]");

        // when
        List<IndexEntry> entries = searchEngine.search("ala");

        // then
        assertEquals(1, entries.size());

        // and when
        entries = searchEngine.search("psy");

        // then
        assertEquals(0, entries.size());
    }
}