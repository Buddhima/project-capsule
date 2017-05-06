package com.project.capsule;

import com.project.capsule.bean.PatientReport;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by buddhima on 4/24/17.
 */
public interface PatientReportRepository extends MongoRepository<PatientReport, String> {

    public List<PatientReport> findByName(String name);

    public List<PatientReport> findByNameLike(String name);

    public List<PatientReport> findByTimeBetween(long from, long to);

}
