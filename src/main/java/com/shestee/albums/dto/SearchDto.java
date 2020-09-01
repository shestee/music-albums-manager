package com.shestee.albums.dto;

import com.shestee.albums.entity.enums.Medium;
import com.shestee.albums.dto.enums.SearchGeneralOptions;

public class SearchDto {
    private String query;
    private SearchGeneralOptions searchGeneralOption;
    private Medium searchFormatOption;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SearchGeneralOptions getSearchGeneralOption() {
        return searchGeneralOption;
    }

    public void setSearchGeneralOption(SearchGeneralOptions searchGeneralOption) {
        this.searchGeneralOption = searchGeneralOption;
    }

    public Medium getSearchFormatOption() {
        return searchFormatOption;
    }

    public void setSearchFormatOption(Medium searchFormatOption) {
        this.searchFormatOption = searchFormatOption;
    }

    public SearchDto() {
    }

    public SearchDto(String query, SearchGeneralOptions searchGeneralOption, Medium searchFormatOption) {
        this.query = query;
        this.searchGeneralOption = searchGeneralOption;
        this.searchFormatOption = searchFormatOption;
    }
}
