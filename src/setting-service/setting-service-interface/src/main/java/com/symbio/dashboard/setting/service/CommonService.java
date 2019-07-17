package com.symbio.dashboard.setting.service;

import com.symbio.dashboard.Result;
import com.symbio.dashboard.dictionary.dto.upload.DictionaryUpload;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    Result getDictionaryInfo(DictionaryUpload dictionaryUpload);
}
