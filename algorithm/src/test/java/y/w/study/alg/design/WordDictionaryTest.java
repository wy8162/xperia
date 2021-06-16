package y.w.study.alg.design;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WordDictionaryTest {
    @Test
    public void test() {
        WordDictionary dictionary = new WordDictionary();

        dictionary.addWord("bad");
        dictionary.addWord("dad");
        dictionary.addWord("mad");

        boolean result;

        assertEquals(false, dictionary.search("pad"));
        assertEquals(true, dictionary.search("bad"));
        assertEquals(true, dictionary.search(".ad"));
        assertEquals(true, dictionary.search("b.."));

    }

    @Test
    public void test2() {
        WordDictionary dictionary = new WordDictionary();

        dictionary.addWord("a");
        dictionary.addWord("a");

        boolean result;

        assertEquals(false, dictionary.search(".a"));
        assertEquals(false, dictionary.search("a."));
        assertEquals(true, dictionary.search("."));
        assertEquals(true, dictionary.search("a"));
        assertEquals(false, dictionary.search("aa"));

    }
}
