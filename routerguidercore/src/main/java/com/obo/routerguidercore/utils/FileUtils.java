package com.obo.routerguidercore.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by obo on 2017/11/28.
 */

public class FileUtils {
    public static List<File> getAllFiles(File workFile) {
        List<File> files = new ArrayList<>();
        File[]sonFiles = workFile.listFiles();

        for (File sonFile: sonFiles) {
            if (sonFile.isDirectory()) {
                files.addAll(getAllFiles(sonFile));
            } else {
                files.add(sonFile);
            }
        }
        return files;
    }

}
