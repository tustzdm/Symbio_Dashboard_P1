package com.symbio.dashboard.service;

import com.symbio.dashboard.model.ParseResultSummary;

import java.util.List;

public interface ParseResultSummaryService {

    ParseResultSummary updateParseResultSummary(ParseResultSummary data);

    List<ParseResultSummary> getPendingParseZipList();
}
