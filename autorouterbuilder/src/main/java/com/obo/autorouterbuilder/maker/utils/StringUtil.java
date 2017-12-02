package com.obo.autorouterbuilder.maker.utils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by obo on 2017/11/24.
 */

public class StringUtil {
    public static String getTypeWithFirstUpperCase(String typeString) {
        String[] splitString = typeString.split("\\.");
        String availableString = splitString[splitString.length - 1];
        return (availableString.charAt(0) + "").toUpperCase()
                + availableString.substring(1).toLowerCase();
    }


    public static String getGroupName(String path) {
        String[] pathSplit = path.split("/");
        System.out.println("pathSplit.length = " + pathSplit.length);
        return pathSplit[1];
    }

    public static Map<String, String> getParamMap(String fileString, String annotation) {
        Map<String, String> paramMap = new HashMap<>();
        int startPosition = 0;
        int currentIndex = -1;
        while ((currentIndex = fileString.indexOf(annotation, startPosition))!= -1) {
            for (int i = currentIndex + annotation.length(); i < fileString.length(); i ++) {
                if (fileString.charAt(i) == ';') {
                    String content = fileString.substring(currentIndex + annotation.length() + 1, i);
                    System.out.println("content = " + content);
                    String [] splite = content.trim().split("\\s+");

                    System.out.println("size = " + splite.length);
                    System.out.println("splite = " + splite[splite.length - 2] + "  " + splite[splite.length - 1]);
                    paramMap.put(splite[splite.length - 1], splite[splite.length - 2]);
                    startPosition = i;
                    break;
                }
            }
        }
        return paramMap;
    }


}
