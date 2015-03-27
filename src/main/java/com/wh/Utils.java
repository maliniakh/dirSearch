package com.wh;


import java.util.List;

public class Utils {

    /**
     * @param arr
     * @param separator
     * @return array's elements as string, provided string separated.
     */
    public static String array2String(Object[] arr, CharSequence separator) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            // append separator for all but last element
            if(i != arr.length - 1) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    /**
     * @param list
     * @param separator
     * @return array's elements as string, provided string separated.
     */
    public static String list2String(List list, CharSequence separator) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            // append separator for all but last element
            if(i != list.size() - 1) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }
}