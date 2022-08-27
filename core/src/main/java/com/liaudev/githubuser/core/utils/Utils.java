package com.liaudev.githubuser.core.utils;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class Utils {
    public static String getQueryGraph(String query, String lastCursor, int method) {
        String resultQueryGraph = "", querySearch = "", cursorAfter = "";
        switch (method) {
            case 0:
                querySearch = query.isEmpty() ? "language:java" : query;
                cursorAfter = lastCursor.isEmpty() ? "" : ",after:\"" + lastCursor + "\"";
                resultQueryGraph = "query { search( query: \"" + querySearch + "\", type: USER, first:10" + cursorAfter +
                        ") {userCount edges { node { ... on User { id login name location email company avatarUrl followers " +
                        "{ totalCount } following { totalCount } repositories { totalCount }}}cursor}}}";
                break;
            case 1:
                cursorAfter = lastCursor.isEmpty() ? "" : ",after:\"" + lastCursor + "\"";
                resultQueryGraph = "query { user( login: \"" + query + "\" ) { followers( first:10" + cursorAfter +
                        " ) { edges{ node{ ... on User { id login name location email company avatarUrl followers " +
                        "{ totalCount } following { totalCount } repositories{ totalCount }}} cursor}}}}";
                break;
            case 2:
                cursorAfter = lastCursor.isEmpty() ? "" : ",after:\"" + lastCursor + "\"";
                resultQueryGraph = "query { user( login: \"" + query + "\" ) { following( first:10" + cursorAfter +
                        " ) { edges{ node{ ... on User { id login name location email company avatarUrl followers " +
                        "{ totalCount } following { totalCount } repositories{ totalCount }}} cursor}}}}";
                break;
        }

        return resultQueryGraph;
    }
}
