package com.xwx.xwx.service;

import com.xwx.xwx.pojo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xwx.xwx.mapper.SearchMapper;
import java.util.List;
@Service
public class SearchService {

    @Autowired
    private SearchMapper searchMapper;

    public List<Search> searchCourses(String query) {
        return searchMapper.searchCourses(query);
    }
}