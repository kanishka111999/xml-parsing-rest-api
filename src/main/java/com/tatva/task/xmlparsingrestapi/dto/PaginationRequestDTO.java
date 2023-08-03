package com.tatva.task.xmlparsingrestapi.dto;

/* Pagination DTO for specific default paging values.*/
public class PaginationRequestDTO
{
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";

    public PaginationRequestDTO(int page, int size, String sortBy) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
