package com.note4j.main;

import com.note4j.model.GlobalType;
import com.note4j.model.MajorInfo;
import com.note4j.model.Student;
import com.note4j.util.FileUtil;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by changwei on 2015/6/13.
 * V1.0.1 @note4j.com
 * Copyright (c) 2014-2015 All rights reserved.
 */
public class SourceData {

    private static Logger logger = Logger.getLogger(SourceData.class.getName());

    /**
     * 从excel表格中读取学生信息
     *
     * @param excelFileStr
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<Student> getStudentInfo(String excelFileStr) throws IOException, InvalidFormatException {
        File excelFile = new File(excelFileStr);
        Workbook workbook = null;
        Student student = null;
        List<Student> studentList = new ArrayList<Student>();
        Map indexMap = new HashMap<String, Integer>();
        workbook = WorkbookFactory.create(excelFile);
//        Sheet sheet = workbook.getSheet(sheetName);
        Sheet sheet = workbook.getSheetAt(0);
        //由于下标从0开始，所以需要加1.
        int sheetSize = sheet.getLastRowNum() + 1;
        for (int i = 0; i < sheetSize; i++) {
            Row row = sheet.getRow(i);
            if (i != 0) {
                // 获取下标为i的行
                String sid = row.getCell((Integer) indexMap.get(GlobalType.SID)).toString();
                String name = row.getCell((Integer) indexMap.get(GlobalType.NAME)).toString();
                String major = row.getCell((Integer) indexMap.get(GlobalType.MAJOR)).toString();
                String college = row.getCell((Integer) indexMap.get(GlobalType.COLLEGE)).toString();
                student = new Student(sid, name, major, college);
                studentList.add(student);
            } else {
                int cellSize = row.getLastCellNum();
                //将键存入map中
                for (int j = 0; j != cellSize; j++) {
                    if (row.getCell(j) != null && !("".equals(row.getCell(j)))) {
                        indexMap.put(row.getCell(j).toString(), j);
                    }
                }
            }
        }
        return studentList;
    }

    /**
     * 根据专业统计学生的数量
     *
     * @param studentList
     * @return
     */
    public static Map<String, Integer> getStudentCountByMajor(List<Student> studentList, List<MajorInfo> majorInfoList) {
        Map<String, Integer> majorCountMap = new HashMap<String, Integer>();
//        Map<String, Integer> typeCountMap = new HashMap<String, Integer>();
        for (Student student : studentList) {
            String major = student.getMajor();
            String majorSpell = null;
            String majorType = null;
            for (MajorInfo majorInfo : majorInfoList) {
                if (major.equals(majorInfo.getMajorName())) {
                    majorSpell = majorInfo.getMajorSpell() + "_num";
                    majorType = majorInfo.getMajorType();
                    break;
                }
            }
            int majorCount = 1;
            int typeCount = 1;
            if (majorCountMap.containsKey(majorSpell)) {
                majorCount = majorCountMap.get(majorSpell) + 1;
            }
            if (majorCountMap.containsKey(majorType)) {
                typeCount = majorCountMap.get(majorType) + 1;
            }
            majorCountMap.put(majorSpell, majorCount);
            majorCountMap.put(majorType, typeCount);
//            typeCountMap.put(majorType, typeCount);
        }
        int countDoctor = 0;
        int countMaster = 0;
        for (Map.Entry<String, Integer> entry : majorCountMap.entrySet()) {
            String keyStr = entry.getKey();
            if (keyStr.contains("doctor")) {
                countDoctor += entry.getValue();
            } else if (keyStr.contains("master")) {
                countMaster += entry.getValue();
            }
        }
        majorCountMap.put("doctor_num", countDoctor);
        majorCountMap.put("master_num", countMaster);
        return majorCountMap;
    }

    /**
     * 根据配置文件读取专业信息
     *
     * @param fileLine
     * @return
     */
    public static List<MajorInfo> getMajorInfo(List<String> fileLine) {
        List<MajorInfo> majorList = new ArrayList<MajorInfo>();
        MajorInfo majorInfo = null;
        String majorType = "";
        String majorName = "";
        String majorReplace = "";
        String[] strArr;
        for (String lineStr : fileLine) {
            if (" ".equals(lineStr)) {
                continue;
            } else if (lineStr.contains("***")) {
                majorType = lineStr;
                strArr = lineStr.split(":");
                majorName = "";
                majorReplace = strArr[1];
            } else {
                strArr = lineStr.split(":");
                majorName = strArr[0];
                majorReplace = strArr[1];
            }
            majorInfo = new MajorInfo(majorName, majorType, majorReplace);
            majorList.add(majorInfo);

        }
        return majorList;
    }

    /**
     * 测试用，读取excel表格中的专业列表
     *
     * @param fileDir
     * @param sheetName
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void getMajorSet(String fileDir, String sheetName) throws IOException, InvalidFormatException {
        File excelFile = new File(fileDir);
        Workbook workbook = null;
        Student student = null;
        Set<String> majorSet = new LinkedHashSet<String>();
        Map indexMap = new HashMap<String, Integer>();
        workbook = WorkbookFactory.create(excelFile);
        Sheet sheet = workbook.getSheet(sheetName);
        //由于下标从0开始，所以需要加1.
        int sheetSize = sheet.getLastRowNum() + 1;
        for (int i = 0; i < sheetSize; i++) {
            Row row = sheet.getRow(i);
            if (i != 0) {
                // 获取下标为i的行
                String major = row.getCell((Integer) indexMap.get(GlobalType.MAJOR)).toString();
                majorSet.add(major);
            } else {
                int cellSize = row.getLastCellNum();
                //将键存入map中
                for (int j = 0; j != cellSize; j++) {
                    if (row.getCell(j) != null && !("".equals(row.getCell(j)))) {
                        indexMap.put(row.getCell(j).toString(), j);
                    }
                }
            }
        }
        for (String str : majorSet) {
            System.out.println(str);
        }
    }


    public static void main(String[] agrs) {
        Workbook workbook = null;
        String fileStr = "/Users/changwei/Downloads/wordFile/doctor.xls";
        String fileStr2 = "/Users/changwei/Downloads/wordFile/doctor_major.dat";
        List<Student> studentList = null;
        try {
            studentList = new SourceData().getStudentInfo(fileStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        System.out.println(studentList.size());
        // 除了上面testReadExcel的方式读取以外,还支持foreach的方式读取
        for (Student student : studentList) {
            System.out.println(student.toString());
            System.out.println("\n");
        }
        try {
            System.out.println(getStudentCountByMajor(studentList, getMajorInfo(FileUtil.readFile(fileStr2))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
