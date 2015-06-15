package com.note4j.main;

/**
 * Created by changwei on 2015/6/12.
 * V1.0.1 @note4j.com
 * Copyright (c) 2014-2015 All rights reserved.
 */

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import com.note4j.model.MajorInfo;
import com.note4j.model.Student;
import com.note4j.util.FileUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ReplaceWord {

    private static Logger logger = Logger.getLogger(ReplaceWord.class.getName());

    private String majorInfoFile = "/Users/changwei/Downloads/wordFile/doctor_major.dat";
    private String sourceWordFile = "/Users/changwei/Downloads/wordFile/testFormat.docx";
    private String destWordFile = "/Users/changwei/Downloads/wordFile/dest.docx";
    private String excelFile = "/Users/changwei/Downloads/wordFile/master.xls";
//    private String sheetName = "学历注册（源文件）";

    public ReplaceWord(String majorInfoFile, String sourceWordFile, String destWordFile, String excelFile) {
        if (majorInfoFile != null) {
            this.majorInfoFile = majorInfoFile;
        }
        this.sourceWordFile = sourceWordFile;
        this.destWordFile = destWordFile;
        this.excelFile = excelFile;
    }

    public ReplaceWord() {
    }

    /**
     * 替换文件中的姓名
     *
     * @throws Exception
     */
    public void replace() throws Exception {
        List<String> lineList = FileUtil.readFile(majorInfoFile);
        List<MajorInfo> majorInfoList = SourceData.getMajorInfo(lineList);
        List<Student> studentList = SourceData.getStudentInfo(excelFile);
        Map<String, Integer> countMap = SourceData.getStudentCountByMajor(studentList, majorInfoList);
        OPCPackage pack = POIXMLDocument.openPackage(sourceWordFile);
        XWPFDocument docx = new XWPFDocument(pack);
        List<XWPFParagraph> paragraphs = docx.getParagraphs();
        System.out.println(paragraphs.size());
        for (XWPFParagraph tmp : paragraphs) {
            System.out.println(tmp.getParagraphText());
            List<XWPFRun> runs = tmp.getRuns();
            for (XWPFRun word : runs) {
                System.out.println("XWPFRun-Text:" + word.getText(0));
                for (MajorInfo majorInfo : majorInfoList) {
                    if (majorInfo.getMajorSpell().equals(word.getText(0)) && majorInfo.getMajorName().length() != 0) {
                        String majorName = majorInfo.getMajorName();
//                            List<Student> studentList = SourceData.getStudentInfo(excelFile, sheetName);
                        String studentStr = this.getStudentByMajor(studentList, majorName);
                        word.setText(studentStr, 0);
                        break;
                    }
                }
                for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                    if (entry.getKey().contains(word.getText(0))) {
                        word.setText(entry.getValue().toString(), 0);
                    }
                }
            }
        }

        FileOutputStream fos = new FileOutputStream(destWordFile);
        docx.write(fos);
        fos.flush();
        fos.close();
    }


    /**
     * 根据专业选择学生
     *
     * @param studentList
     * @param major
     * @return
     */
    public String getStudentByMajor(List<Student> studentList, String major) {
        String studentStr = "";
        for (Student student : studentList) {
            if (student.getMajor().contains(major)) {
                studentStr += student.getName();
                studentStr += "\t\t";
            }
        }
        System.out.println(studentStr);
        return studentStr;
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/log4j.properties");
//        ReplaceWord tools = new ReplaceWord();
//        try {
//            tools.replace();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//        try {
//            List<String> lineList = FileUtil.readFile("/Users/changwei/Downloads/wordFile/doctor_major.dat");
//            new ReplaceWord().getMajorInfo(lineList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            new ReplaceWord().getStudentByMajor(SourceData.getStudentInfo("/Users/changwei/Downloads/wordFile/doctor.xls", "学历注册（源文件）"), "采矿工程");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }

        try {
            new ReplaceWord().replace();
        } catch (Exception e) {
            logger.debug(null,e);
//            e.printStackTrace();
        }
    }
}

