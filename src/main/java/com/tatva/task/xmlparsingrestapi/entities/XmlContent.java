package com.tatva.task.xmlparsingrestapi.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * @Entity Class to Populate and Map the data to database layer.
 */

@Entity
@Table(name="xml_content")
public class XmlContent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String newspaperName;
    private int width;
    private int height;
    private int dpi;
    private LocalDateTime uploadTime;
    private String filename;

    public XmlContent()
    {

    }


    public XmlContent(String newspaperName, int width, int height, int dpi, LocalDateTime uploadTime, String filename) {
        this.newspaperName = newspaperName;
        this.width = width;
        this.height = height;
        this.dpi = dpi;
        this.uploadTime = uploadTime;
        this.filename = filename;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewspaperName() {
        return newspaperName;
    }

    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
