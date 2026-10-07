package com.xwx.xwx.controller;
import com.xwx.xwx.service.SearchService;
import com.xwx.xwx.pojo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/courses")
    public List<Search> searchCourses(@RequestParam String query) {
        return searchService.searchCourses(query);
    }
}