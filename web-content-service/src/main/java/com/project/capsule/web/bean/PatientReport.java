package com.project.capsule.web.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by buddhima on 4/24/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientReport {


    public String id;

    public String name;
    public int age;
    public String sex;
    public String doctorName;

    public long time;

    public String reportType;

    public Map<String, Object> reportData;

}
