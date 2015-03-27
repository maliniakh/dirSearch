package com.wh.dir;

import com.wh.dir.exception.InvalidArgumentsException;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * End to end tests, with no mocking, testing full functionality.
 */
public class SearchTestEnd2End {

    private final String TEST_DIR = "src/test/resources/rootDir";

    @Test
    public void testSimpleCase() throws Exception {
        Search search = new Search(new String[] {"-f", "file.test", TEST_DIR});
        List<Path> results = search.getResults();
        assertEquals(3, results.size());
        assertTrue(results.contains(Paths.get(TEST_DIR + "/file.test")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir1/file.test")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir2/dir3/file.test")));
    }

    @Test
    public void testPattern() throws Exception {
        Search search = new Search(new String[] {"-f", "file.test", "-p", "occurrence", TEST_DIR});
        List<Path> results = search.getResults();
        assertEquals(2, results.size());
        assertTrue(results.contains(Paths.get(TEST_DIR + "/file.test")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir2/dir3/file.test")));
    }

    @Test
    public void testContainStringNoFileFound() throws Exception {
        Search search = new Search(new String[] {"-f", "file.test", "-p", "non-existent-string", TEST_DIR});
        List<Path> results = search.getResults();
        assertEquals(0, results.size());
    }

    @SuppressWarnings("UnusedDeclaration")
    @Test(expected = InvalidArgumentsException.class)
    public void testContainStringNoDirProvided() throws Exception {
        Search search = new Search(new String[] {"-f", "file.test", "-p", "non-existent-string"});
    }

    @SuppressWarnings("UnusedDeclaration")
    @Test(expected = InvalidArgumentsException.class)
    public void testContainStringNoSuchDir() throws Exception {
        Search search = new Search(new String[] {"-f", "file.test", "-p", "non-existent-string", "NON_EXISTENT_DIR"});
    }

    @Test
    public void testRegularExpression() throws Exception {
        Search search = new Search(new String[] {"-f", "f.*.t.*", TEST_DIR});
        List<Path> results = search.getResults();
        assertEquals(5, results.size());
        assertTrue(results.contains(Paths.get(TEST_DIR + "/file.test")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir1/file.test")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir2/dir3/file.t")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir2/dir3/file.test")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir2/dir3/file.tests")));
    }

    @Test
    public void testRegularExpressionWithPattern() throws Exception {
        Search search = new Search(new String[] {"-f", "f.*.t.*", "-p", "occ..r.*ce", TEST_DIR});
        List<Path> results = search.getResults();
        assertEquals(2, results.size());
        assertTrue(results.contains(Paths.get(TEST_DIR + "/file.test")));
        assertTrue(results.contains(Paths.get(TEST_DIR + "/dir2/dir3/file.test")));
    }
}
