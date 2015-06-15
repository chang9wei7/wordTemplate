package com.note4j.model;

/**
 * Created by changwei on 2015/6/13.
 * V1.0.1 @note4j.com
 * Copyright (c) 2014-2015 All rights reserved.
 */
public class MajorInfo {
    private String majorName;
    private String majorType;
    private String majorSpell;

    public MajorInfo(String majorName, String majorType, String majorSpell) {
        this.majorName = majorName;
        this.majorType = majorType;
        this.majorSpell = majorSpell;
    }

    public MajorInfo() {
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getMajorSpell() {
        return majorSpell;
    }

    public void setMajorSpell(String majorSpell) {
        this.majorSpell = majorSpell;
    }
}
