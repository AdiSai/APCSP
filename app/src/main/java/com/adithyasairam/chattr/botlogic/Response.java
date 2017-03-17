package com.adithyasairam.chattr.botlogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdiSai on 3/12/17.
 */

public class Response {
    private static final String[] greetings = {"Hey", "Hello", "Hi", "Welcome", "Sup"};
    private static String data;

    private Response(String d) { data = d; }

    public static Response getNextResponse(Response previous) {
        Response r = new Response(previous.getData());
        String data = getData();
        List<Integer> category = categorize(data);
        int struct = category.get(0); int type = category.get(1); int flags = category.get(2);
        if (flags != 0) {
            setData(greetings[randomIndex(greetings.length)]);
        } else {

        }
        save(data);
        return r;
    }

    public static void setData(String newData) { data = newData; }
    public static String getData() { return data; }

    private static List<Integer> categorize(String data) {
        List<Integer> categories = new ArrayList<>(); //[PREVIOUS STATEMENT STRUCT, PREVIOUS STATEMENT TYPE, FLAGS]
        //PREVIOUS STATEMENT STRUCT [0: Question, 1: Imperative, 2: Declarative, ]
        if (data.contains("?")) { categories.add(0, 0); }

        //PREVIOUS STATEMENT TYPE [0: Greeting, ]
        for (String s : greetings) { if (data.contains(s)) { categories.add(1, 0); } }

        //FLAGS [-1: Previous is Null/Empty, 0: Previous is not Null/Empty]
        if(data == null || data.isEmpty() || data.contains("")) { categories.add(2, -1); } else { categories.add(2, 0); }
        return categories;
    }

    private static void save(String data) {

    }

    private static int randomIndex(int maxLength) { return (int)(Math.random() * maxLength - 1); }
}
