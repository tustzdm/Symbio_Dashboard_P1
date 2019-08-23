package com.symbio.dashboard.service;

import com.symbio.dashboard.data.repository.ParseResultSummaryRep;
import com.symbio.dashboard.model.ParseResultSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class ParseResultSummaryServiceImpl implements ParseResultSummaryService {

    @Autowired
    private ParseResultSummaryRep parseResultSummaryRep;

    @Override
    public ParseResultSummary updateParseResultSummary(ParseResultSummary data) {
        return parseResultSummaryRep.saveAndFlush(data);
    }

    @Override
    public List<ParseResultSummary> getPendingParseZipList() {
        return parseResultSummaryRep.getPendingParseZipList();
    }
}
