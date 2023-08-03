package com.tatva.task.xmlparsingrestapi.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
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

    //Argument Constructor to set the all values.
    public XmlContent(final String newspaperName, final int width, final int height, final int dpi, final LocalDateTime uploadTime, final String filename) {
        this.newspaperName = newspaperName;
        this.width = width;
        this.height = height;
        this.dpi = dpi;
        this.uploadTime = uploadTime;
        this.filename = filename;
    }

    //All Attributes Getters and Setters.
    public Long getId() {
        return id;
    }


    public void setId(final Long id) {
        this.id = id;
    }


    public String getNewspaperName() {
        return newspaperName;
    }


    public void setNewspaperName(final String newspaperName) {
        this.newspaperName = newspaperName;
    }


    public int getWidth() {
        return width;
    }


    public void setWidth(final int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(final int height) {
        this.height = height;
    }


    public int getDpi() {
        return dpi;
    }


    public void setDpi(final int dpi) {
        this.dpi = dpi;
    }


    public LocalDateTime getUploadTime() {
        return uploadTime;
    }


    public void setUploadTime(final LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }


    public String getFilename() {
        return filename;
    }


    public void setFilename(final String filename) {
        this.filename = filename;
    }
}
