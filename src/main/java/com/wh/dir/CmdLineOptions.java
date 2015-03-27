package com.wh.dir;


import com.wh.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple command line options / parameters parser
 */
public class CmdLineOptions {

    /**
     * maps attributes to values, e.g. -n 10 (n - key, 10 - value)
     */
    private Map<String, String> arg2ValueMap = new HashMap<>();

    /**
     * Values from command line that are not associated with none of the parameters
     * Like / in:
     * rm / -rf
     * Please note this implementation wouldn't be able to handle rm -rf / case, since it would need to know whether
     * -f comes with its value or not.
     */
    private String noParameterValue;

    public CmdLineOptions(String cmd) {
        parse(cmd);
    }

    /**
     * assumes arguments are passed like in public static void main(String[] args), elements are separated with spaces
     * @param args
     */
    public CmdLineOptions(String[] args) {
        String cmd = Utils.array2String(args, " ");
        parse(cmd);
    }

    private void parse(String cmd) {
        //(--?[a-zA-Z]+)[:\s=]?([A-Z]:(?:\\[\w\s-]+)+\\?(?=\s-)|\"[^\"]*\"|[^-][^\s]*)?
        // following regex seems to work in most cases, although it fails at some,
        // for example on odd number of quotation marks
        Pattern pattern = Pattern.compile("--?([a-zA-Z]+)[\\s]?([A-Z]:(?:\\\\[\\w\\s-]+)+\\\\?(?=\\s-)|\\\"[^\\\"]*\\\"|[^-][^\\s]*)?");
        Matcher matcher = pattern.matcher(cmd);

        // index of the righ-most matched strings
        int maxEndIdx = 0;

        while (matcher.find()) {

            String g1 = matcher.group(1);
            String g2 = matcher.group(2);

            if(matcher.end(2) > maxEndIdx) {
                maxEndIdx = matcher.end(2);
            }

            if(g1 == null) {
                throw new IllegalStateException("groups must be non-null?");
            }
            // remove quotation marks from matched group
            g2 = g2 != null ? g2.replace("\"", "") : null;
            // add values to map
            arg2ValueMap.put(g1, g2);

        }

        // get no-parameter value
        noParameterValue = cmd.substring(maxEndIdx).trim();
    }

    /**
     * @param arg
     * @return true if there is arg in parsed arguments
     */
    public boolean isArg(String arg) {
        return arg2ValueMap.containsKey(arg);
    }

    /**
     * @param arg
     * @return arg's value
     */
    public String getValue(String arg) {
        return arg2ValueMap.get(arg);
    }

    public String getNoParameterValue() {
        return noParameterValue;
    }
}