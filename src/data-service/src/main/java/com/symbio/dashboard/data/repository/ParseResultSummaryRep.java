package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.ParseResultSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParseResultSummaryRep extends JpaRepository<ParseResultSummary, Integer> {

    @Query(value = "SELECT * FROM parse_result_summary WHERE parseStatus = 0 AND parseCount < 99 ORDER BY id", nativeQuery = true)
    List<ParseResultSummary> getPendingList();

    @Query(value = "SELECT * FROM parse_result_summary WHERE parseStatus = 10 AND parseCount < 99 ORDER BY id", nativeQuery = true)
    List<ParseResultSummary> getPendingParseZipList();

    @Override
    ParseResultSummary getOne(Integer id);

    ParseResultSummary getByFileName(String fileName);

    List<ParseResultSummary> findByFileName(String fileName);
}