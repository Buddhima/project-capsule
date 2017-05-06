package com.project.capsule.web;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.capsule.web.bean.PatientReport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ReportController {

    int reportId = 0;

    @Inject
    private RestTemplate restTemplate;

    @RequestMapping(value = "/report/add",method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public String addReport(@RequestParam Map<String, Object> map) {

        PatientReport patientReport = new PatientReport();
        patientReport.reportData = new HashMap<String, Object>();

        patientReport.id = String.valueOf(reportId ++);
        patientReport.time = System.currentTimeMillis();

        for (Map.Entry<String, Object> entry: map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("name")) {
                patientReport.name = String.valueOf(entry.getValue());
            }
            else if (entry.getKey().equalsIgnoreCase("age")) {
                patientReport.age = Integer.parseInt(entry.getValue().toString());
            }
            else if (entry.getKey().equalsIgnoreCase("sex")) {
                patientReport.sex = String.valueOf(entry.getValue());
            }
            else if (entry.getKey().equalsIgnoreCase("doctorName")) {
                patientReport.doctorName = String.valueOf(entry.getValue());
            }
//            else if (entry.getKey().equalsIgnoreCase("time")) {
//                patientReport.time = (Long)entry.getValue();
//            }
            else if (entry.getKey().equalsIgnoreCase("reportType")) {
                patientReport.reportType = String.valueOf(entry.getValue());
            }
            else {
                patientReport.reportData.put(entry.getKey(), entry.getValue());
            }

        }

        Response response = restTemplate.postForObject("http://localhost:8081/report", patientReport, Response.class);

        return "addReportComplete";
    }

    @RequestMapping("/report/view")
    public String viewReport(@RequestParam(value="reportType", required=false, defaultValue="World") String reportType,
                             @RequestParam(value="id", required=false, defaultValue="4") int id,
                             Model model) {

        PatientReport patientReport = restTemplate.getForObject("http://localhost:8081/report/{id}", PatientReport.class, id);

        model.addAttribute("name", patientReport.name);
        model.addAttribute("age", patientReport.age);
        model.addAttribute("sex", patientReport.sex);
        model.addAttribute("doctorName", patientReport.doctorName);
        model.addAttribute("time", patientReport.time);
        model.addAttribute("reportType", reportType);

        model.addAllAttributes(patientReport.reportData);

        model.addAttribute("hideSubmit", true);

        return reportType;
    }

    @RequestMapping("/report/new")
    public String newReport(@RequestParam(value="name", required=false, defaultValue="World") String name) {

        return name;
    }


    @RequestMapping("/report/list")
    public String listReports() {

        return "listReports";
    }

    @RequestMapping("/report/find")
    public String findReports() {

        return "findReports";
    }

    @RequestMapping(value = "/report/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public String findReport(@RequestParam Map<String, Object> map, Model model) {

        List<PatientReport> patientReportList = restTemplate.postForObject("http://localhost:8081/report/find", map, List.class);

        model.addAttribute("reports", patientReportList);

        return "findReportResults";
    }

}
