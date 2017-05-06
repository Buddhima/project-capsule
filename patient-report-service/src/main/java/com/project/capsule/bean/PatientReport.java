package com.project.capsule.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import java.util.Map;

/**
 * Created by buddhima on 4/24/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientReport {

    @Id
    public String id;

    public String name;
    public int age;
    public String sex;
    public String doctorName;

    public long time;

    public String reportType;

    public Map<String, Object> reportData;

}
