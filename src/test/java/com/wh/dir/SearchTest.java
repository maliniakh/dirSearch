package com.wh.dir;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SearchTest {

    @Test
    public void testContainStringSimpleCase() throws Exception {
        // mock object and call actual implementation
        Search search = mock(Search.class);
        Mockito.when(search.containsString((Pattern) any(), (Path) any())).thenCallRealMethod();
        Mockito.when(search.containsString(anyString(), (Path) any())).thenCallRealMethod();

        Path path = Paths.get(getClass().getResource("/containsString.test").toURI());
        assertTrue(search.containsString("occurrence", path));
        assertFalse(search.containsString("non-existent-string", path));
    }

    @Test(expected = IOException.class)
    public void testContainStringNoFileFound() throws Exception {
        // mock object and call actual implementation
        Search search = mock(Search.class);
        Mockito.when(search.containsString(anyString(), (Path) any())).thenCallRealMethod();
        Mockito.when(search.containsString((Pattern) any(), (Path) any())).thenCallRealMethod();

        Path path = Paths.get("/non-existing-file");
        assertTrue(search.containsString("occurrence", path));
    }
}
