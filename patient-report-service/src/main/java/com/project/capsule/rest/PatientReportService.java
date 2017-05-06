package com.project.capsule.rest;

import com.project.capsule.PatientReportRepository;
import com.project.capsule.bean.PatientReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by buddhima on 4/24/17.
 */

@Named
@Path("/report")
public class PatientReportService {

    @Autowired
    private PatientReportRepository repository;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response storePatientReport(@RequestBody PatientReport patientReport) {

        repository.save(patientReport);

        return Response.status(201)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PatientReport retrievePatientReport(@PathParam("id") int id) {

        PatientReport patientReport = repository.findOne(String.valueOf(id));

        return patientReport;

        /*if (patientReport == null) {

            return Response.status(404)
                    .entity(patientReport)
                    .build();
        } else {

            return Response.status(200)
                    .entity(patientReport)
                    .build();
        }*/
    }

    @POST
    @Path("find")
    public List<PatientReport> findReports(@RequestParam Map<String, Object> map) {
        List<PatientReport> patientReports = new ArrayList<PatientReport>();

        Map<String, PatientReport> resultantMap = new HashMap<String, PatientReport>();

        List<PatientReport> resultantReports;

        if (map.containsKey("name") && map.get("name") != null) {

            String patientName = (String) map.get("name");

            if (!patientName.trim().equalsIgnoreCase("")) {

                resultantReports = repository.findByNameLike(patientName);

                for (PatientReport report : resultantReports)
                    resultantMap.put(report.id, report);
            }
        }

        long fromTime = 0L;

        if (map.containsKey("timeFrom")) {

            if ((map.get("timeFrom") instanceof String)
                    && !((String) map.get("timeFrom")).equalsIgnoreCase("")) {

                String fromVal = (String) map.get("timeFrom");

                fromTime = Long.parseLong(fromVal);
            }
        }

        long toTime = 0L;

        if (map.containsKey("timeTo")) {

            if ((map.get("timeTo") instanceof String)
                    && !((String) map.get("timeTo")).equalsIgnoreCase("")) {
                String toVal = (String) map.get("timeTo");

                toTime = Long.parseLong(toVal);
            }
        }

        if (fromTime > 0L && toTime == 0L) {
            toTime = fromTime + 432000000L;
        }

        if (fromTime > 0L) {

            resultantReports = repository.findByTimeBetween(fromTime, toTime);

            for (PatientReport report : resultantReports)
                resultantMap.put(report.id, report);

        }

        patientReports.addAll(resultantMap.values());

        return patientReports;
    }

}
