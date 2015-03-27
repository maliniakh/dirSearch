package com.wh.dir;

import org.junit.Test;
import static org.junit.Assert.*;

public class CmdLineOptionsTest {

    @Test
    public void testGetValueSimple() throws Exception {
        CmdLineOptions opts = new CmdLineOptions("search.sh -f file dir");
        assertEquals("file", opts.getValue("f"));
        assertFalse(opts.isArg("dir"));
        assertNull(opts.getValue("nonExistentArg"));
        assertEquals("dir", opts.getNoParameterValue());
    }

    @Test
    public void testGetValueMultipleParams() throws Exception {
        CmdLineOptions opts = new CmdLineOptions("search.sh -g -f file -d dir target");
        assertNull(opts.getValue("g"));
        assertEquals("file", opts.getValue("f"));
        assertEquals("dir", opts.getValue("d"));
        assertEquals("target", opts.getNoParameterValue());
        assertNull(opts.getValue("nonExistentArg"));
    }

    @Test
    public void testGetValueQuotes() throws Exception {
        CmdLineOptions opts = new CmdLineOptions("search.sh -f \"file\"");
        assertEquals("file", opts.getValue("f"));
        assertNull(opts.getValue("nonExistentArg"));
    }

    @Test
    public void testIsArgSimple() throws Exception {
        CmdLineOptions opts = new CmdLineOptions("search.sh -f file");
        assertTrue(opts.isArg("f"));
        assertFalse(opts.isArg("nonExistentArg"));
    }

    @Test
    public void testIsArgMisc() throws Exception {
        CmdLineOptions opts = new CmdLineOptions("search.sh -g -f \"file\" -d dir target");
        assertTrue(opts.isArg("f"));
        assertTrue(opts.isArg("g"));
        assertTrue(opts.isArg("d"));
        assertFalse(opts.isArg("nonExistentArg"));
        assertEquals("target", opts.getNoParameterValue());
    }
}