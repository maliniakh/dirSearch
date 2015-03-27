package com.wh.dir;

import com.wh.Utils;
import com.wh.dir.exception.InvalidArgumentsException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 */
public class Search {

    CmdLineOptions opts;

    Pattern filePattern;

    Path directory;

    Pattern pattern;

    public Search(String[] args) throws InvalidArgumentsException {
        this.opts = new CmdLineOptions(args);

        // file argument
        String fValue = opts.getValue("f");
        if(fValue == null) {
            throw new InvalidArgumentsException();
        }
        this.filePattern = Pattern.compile(fValue);

        // string to match pattern
        if(opts.getValue("p") != null) {
            this.pattern = Pattern.compile(opts.getValue("p"));
        }

        // take care of target directory argument
        String noParameterValue = opts.getNoParameterValue();
        if(noParameterValue == null || noParameterValue.length() == 0) {
           throw new InvalidArgumentsException();
        }
        this.directory = Paths.get(noParameterValue);
        if(!directory.toFile().isDirectory()) {
            throw new InvalidArgumentsException();
        }
    }

    /**
     * @return String representation
     * @throws IOException
     */
    public String getResultsString() throws IOException {
        List<Path> matchedFiles = getResults();
        return Utils.list2String(matchedFiles, "\n");
    }

    public List<Path> getResults() throws IOException {
        final List<Path> matchedFiles = new ArrayList<>();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if(filePattern.matcher(fileName).matches()) {
                    // check content if -p option is provided
                    if(pattern != null) {
                        if(containsString(pattern, file)) {
                            matchedFiles.add(file);
                        }
                    } else {
                        matchedFiles.add(file);
                    }
                }

                return super.visitFile(file, attrs);
            }
        });

        return matchedFiles;
    }

    /**
     * @param file file to be read.
     * @param pattern pattern to search for, might be plain string or regex as well.
     * @return true if string occurrs in the file.
     * @throws IOException
     */
    public boolean containsString(Pattern pattern, Path file) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
            for(String line; (line = br.readLine()) != null; ) {
                if(pattern.matcher(line).find()) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * @param file file to be read.
     * @param pattern string to search for, might be plain string or regex as well.
     * @return true if string occurrs in the file.
     * @throws IOException
     */
    public boolean containsString(String pattern, Path file) throws IOException {
        return containsString(Pattern.compile(pattern), file);
    }

    /**
     * Prints app usage in Unix-like manner.
     */
    public static void printUsage() {
        StringBuffer sb = new StringBuffer();
        sb.append("Search usage:\n");
        sb.append("\tsearch.sh -f FILENAME [OPTIONS] DIRECTORY\n");
        sb.append("\t\tFILENAME\n\t\t\tFile name to search for. Regular expressions are supported.\n");
        sb.append("\n\t\tDIRECTORY\n\t\t\tRoot directory to start searching from. Regular expressions are supported.\n");
        sb.append("\n\tOPTIONS\n");
        sb.append("\t\t-p STRING\n\t\t\tReturns only these matched files, which contain STRING as well. STRING might be just a " +
                "plain string or a regular expression.");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) throws IOException {
        try {
            Search search = new Search(args);
            String results = search.getResultsString();
            System.out.println(results);
        } catch (InvalidArgumentsException e) {
            Search.printUsage();
        }
    }
}
