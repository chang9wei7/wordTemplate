package com.note4j.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changwei on 2015/6/13.
 * V1.0.1 @note4j.com
 * Copyright (c) 2014-2015 All rights reserved.
 */
public class FileUtil {
    public static List<String> readFile(String fileDir) throws Exception {
        File file = new File(fileDir);
        List<String> fileList = new ArrayList<String>();
        if (!file.exists()) {
            System.err.println("File Not exist!");
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String strLine = "";
        while ((strLine = br.readLine()) != null) {
            fileList.add(strLine);
        }
        return fileList;
    }

}
