package com.obo.autorouterbuildercore.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    public static String upperCaseFirst(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
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

    public static String getFirstParamsStrByAnnotation(String fileString, String annotationName){

        int index = fileString.indexOf(annotationName);
        StringBuffer sb = new StringBuffer();
        // int indexLeftBracket = 0;
        boolean start = false;
        for (int i = index + annotationName.length(); i < fileString.length(); i++) {
            if (start) {
                sb.append(fileString.charAt(i));
            }
            if (fileString.charAt(i) == '(') {
                start = true;
            }
            if (fileString.charAt(i) == ')') {
                sb.deleteCharAt(sb.length() - 1);
                break;
            }

        }
        return sb.toString();

    }


    public static List<Map<String, String>> getParamMapListByMethodParamsStr(String fileStr, String annotationStr) {
        int startPosition = 0;
        int currentIndex = -1;
        List<Map<String, String>> list = new ArrayList<>();

        while ((currentIndex = fileStr.indexOf(annotationStr, startPosition))!= -1) {
            String paramsStr = StringUtil.getFirstParamsStrByAnnotation(fileStr,annotationStr);
            Map<String, String> paramMap = new HashMap<>();
            String[] paramArray = paramsStr.toString().split(",");
            for (int i = 0; i < paramArray.length; i++) {
                String paramStr = paramArray[i];
                String[] param = paramStr.toString().split(" ");
                for (int k = 0; k < 2; k++) {
                    paramMap.put(param[0].trim(), param[1].trim());
                }
            }
            list.add(paramMap);
            System.out.println("paramsStr = " + paramsStr);
            int startPositionNew =currentIndex+ paramsStr.length()+2;
            fileStr = fileStr.substring(startPositionNew,fileStr.length());

        }
        return list;
    }

    public static Map<String, String> getParamMapByAnnotationParamsStr(String paramsStr) {
        Map<String, String> paramMap = new HashMap<>();
        String[] paramArray = paramsStr.toString().split(",");
        for (int i = 0; i < paramArray.length; i++) {
            String paramStr = paramArray[i];
            String[] param = paramStr.toString().split("=");
            for (int k = 0; k < 2; k++) {
                param[1] =  param[1].replace("\"","");
                paramMap.put(param[0].trim(), param[1].trim());
            }
        }
        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " value= " + entry.getValue());
        }
        return paramMap;
    }

    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
