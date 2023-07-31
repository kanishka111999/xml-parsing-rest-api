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

    /**
     * Instantiates a new Xml content.
     */
    public XmlContent()
    {

    }

    /**
     * Instantiates a new Xml content.
     *
     * @param newspaperName Times Of India
     * @param width         160
     * @param height        120
     * @param dpi           400
     * @param uploadTime    2023-07-29T11:34:34.057
     * @param filename      newspaper.xml
     */
//Argument Constructor to initialize the value of all attributes.
    public XmlContent(String newspaperName, int width, int height, int dpi, LocalDateTime uploadTime, String filename) {
        this.newspaperName = newspaperName;
        this.width = width;
        this.height = height;
        this.dpi = dpi;
        this.uploadTime = uploadTime;
        this.filename = filename;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets newspaper name.
     *
     * @return the newspaper name
     */
    public String getNewspaperName() {
        return newspaperName;
    }

    /**
     * Sets newspaper name.
     *
     * @param newspaperName the newspaper name
     */
    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets dpi.
     *
     * @return the dpi
     */
    public int getDpi() {
        return dpi;
    }

    /**
     * Sets dpi.
     *
     * @param dpi the dpi
     */
    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    /**
     * Gets upload time.
     *
     * @return the upload time
     */
    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    /**
     * Sets upload time.
     *
     * @param uploadTime the upload time
     */
    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * Gets filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets filename.
     *
     * @param filename the filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
