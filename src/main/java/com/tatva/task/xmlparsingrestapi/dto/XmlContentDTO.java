package com.tatva.task.xmlparsingrestapi.dto;

import java.time.LocalDateTime;

/* XML ContentDTO for fetching data as per particular columns */
public class XmlContentDTO {

        private int page = 0;
        private int size = 10;
        private String sortBy;
        private String filterBy;
        private Integer width;
        private Integer height;
        private Integer dpi;
        private String filename;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public XmlContentDTO(int page, int size, String sortBy, String filterBy, Integer width, Integer height, Integer dpi, String filename, LocalDateTime startTime, LocalDateTime endTime) {
            this.page = page;
            this.size = size;
            this.sortBy = sortBy;
            this.filterBy = filterBy;
            this.width = width;
            this.height = height;
            this.dpi = dpi;
            this.filename = filename;
            this.startTime = startTime;
            this.endTime = endTime;
        }

    public XmlContentDTO() {

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

        public String getFilterBy() {
            return filterBy;
        }

        public void setFilterBy(String filterBy) {
            this.filterBy = filterBy;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getDpi() {
            return dpi;
        }

        public void setDpi(Integer dpi) {
            this.dpi = dpi;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }
    }



