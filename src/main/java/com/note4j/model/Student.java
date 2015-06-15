package com.note4j.model;

/**
 * Created by changwei on 2015/6/13.
 * V1.0.1 @note4j.com
 * Copyright (c) 2014-2015 All rights reserved.
 */
public class Student {
    private String sid;
    private String name;
    private String major;

    private String college;

    public Student(String sid, String name, String major, String college) {
        this.sid = sid;
        this.name = name;
        this.major = major;
        this.college = college;
    }

    public Student() {

    }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", major='" + major + '\'' +
                ", college='" + college + '\'' +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
