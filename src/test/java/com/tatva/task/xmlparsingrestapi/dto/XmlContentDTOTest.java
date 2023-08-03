package com.tatva.task.xmlparsingrestapi.dto;

import com.tatva.task.xmlparsingrestapi.dto.XmlContentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class XmlContentDTOTest {

    @Test
    public void testDefaultConstructor() {
        XmlContentDTO xmlContentDTO = new XmlContentDTO();
        Assertions.assertEquals(0, xmlContentDTO.getPage());
        Assertions.assertEquals(10, xmlContentDTO.getSize());
        Assertions.assertNull(xmlContentDTO.getSortBy());
        Assertions.assertNull(xmlContentDTO.getFilterBy());
        Assertions.assertNull(xmlContentDTO.getWidth());
        Assertions.assertNull(xmlContentDTO.getHeight());
        Assertions.assertNull(xmlContentDTO.getDpi());
        Assertions.assertNull(xmlContentDTO.getFilename());
        Assertions.assertNull(xmlContentDTO.getStartTime());
        Assertions.assertNull(xmlContentDTO.getEndTime());
    }

    @Test
    public void testParameterizedConstructor() {
        int page = 1;
        int size = 20;
        String sortBy = "name";
        String filterBy = "category";
        Integer width = 800;
        Integer height = 600;
        Integer dpi = 300;
        String filename = "example.xml";
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 1, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 2, 12, 0);

        XmlContentDTO xmlContentDTO = new XmlContentDTO(page, size, sortBy, filterBy, width, height, dpi, filename, startTime, endTime);

        Assertions.assertEquals(page, xmlContentDTO.getPage());
        Assertions.assertEquals(size, xmlContentDTO.getSize());
        Assertions.assertEquals(sortBy, xmlContentDTO.getSortBy());
        Assertions.assertEquals(filterBy, xmlContentDTO.getFilterBy());
        Assertions.assertEquals(width, xmlContentDTO.getWidth());
        Assertions.assertEquals(height, xmlContentDTO.getHeight());
        Assertions.assertEquals(dpi, xmlContentDTO.getDpi());
        Assertions.assertEquals(filename, xmlContentDTO.getFilename());
        Assertions.assertEquals(startTime, xmlContentDTO.getStartTime());
        Assertions.assertEquals(endTime, xmlContentDTO.getEndTime());
    }

    @Test
    public void testSettersAndGetters() {
        XmlContentDTO xmlContentDTO = new XmlContentDTO();

        xmlContentDTO.setPage(2);
        Assertions.assertEquals(2, xmlContentDTO.getPage());

        xmlContentDTO.setSize(15);
        Assertions.assertEquals(15, xmlContentDTO.getSize());

        xmlContentDTO.setSortBy("date");
        Assertions.assertEquals("date", xmlContentDTO.getSortBy());

        xmlContentDTO.setFilterBy("author");
        Assertions.assertEquals("author", xmlContentDTO.getFilterBy());

        xmlContentDTO.setWidth(1024);
        Assertions.assertEquals(1024, xmlContentDTO.getWidth());

        xmlContentDTO.setHeight(768);
        Assertions.assertEquals(768, xmlContentDTO.getHeight());

        xmlContentDTO.setDpi(150);
        Assertions.assertEquals(150, xmlContentDTO.getDpi());

        xmlContentDTO.setFilename("sample.xml");
        Assertions.assertEquals("sample.xml", xmlContentDTO.getFilename());

        LocalDateTime newStartTime = LocalDateTime.of(2023, 9, 1, 12, 0);
        xmlContentDTO.setStartTime(newStartTime);
        Assertions.assertEquals(newStartTime, xmlContentDTO.getStartTime());

        LocalDateTime newEndTime = LocalDateTime.of(2023, 9, 2, 12, 0);
        xmlContentDTO.setEndTime(newEndTime);
        Assertions.assertEquals(newEndTime, xmlContentDTO.getEndTime());
    }
}

