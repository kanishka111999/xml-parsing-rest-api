package com.tatva.task.xmlparsingrestapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * @Entity Class to Populate and Map the data to database layer.
 */

@Entity
@Table(name="xml_content")
@Getter
@Setter
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
}
