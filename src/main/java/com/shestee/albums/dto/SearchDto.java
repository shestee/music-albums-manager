package com.shestee.albums.dto;

import com.shestee.albums.dto.enums.SearchGeneralOptions;

public class SearchDto {
    private String query;
    private SearchGeneralOptions searchOption;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SearchGeneralOptions getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(SearchGeneralOptions searchOption) {
        this.searchOption = searchOption;
    }

    public SearchDto() {
    }

    public SearchDto(String query, SearchGeneralOptions searchOption) {
        this.query = query;
        this.searchOption = searchOption;
    }
}
