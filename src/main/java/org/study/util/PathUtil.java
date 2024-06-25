package org.study.util;

public class PathUtil {
    public String fixPath(String path) {
        if(path == null)
            return "/";
        if(!path.startsWith("/"))
            path = "/" + path;
        if(path.length() > 1 && path.endsWith("/"))
            path = path.substring(0, path.length() - 1);
        return path;
    }

    public String cleanPath(String path) {
        if(path == null)
            return null;
        return path.replaceAll("[/]+", "/");
    }
}
