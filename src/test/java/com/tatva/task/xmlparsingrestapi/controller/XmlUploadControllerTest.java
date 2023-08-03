package com.tatva.task.xmlparsingrestapi.controller;


import com.tatva.task.xmlparsingrestapi.dto.PaginationRequestDTO;
import com.tatva.task.xmlparsingrestapi.dto.XmlContentDTO;
import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import com.tatva.task.xmlparsingrestapi.service.XmlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class XmlUploadControllerTest {

    @Mock
    private XmlServiceImpl xmlService;

    @InjectMocks
    private XmlUploadController xmlUploadController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadXmlFile() throws IOException, SAXException, ParserConfigurationException {
        // Mock the service method behavior for void method
        Mockito.doNothing().when(xmlService).processXmlFile(any());

        // Create a sample XML file to upload
        MockMultipartFile xmlFile = new MockMultipartFile("file", "test.xml", "application/xml", "<xml>Test content</xml>".getBytes());

        // Call the controller method
        ResponseEntity<String> response = xmlUploadController.uploadXmlFile(xmlFile);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("XML file uploaded and processed successfully.", response.getBody());
    }

    @Test
    public void testGetXmlContent() {
        // Create a list of mock XmlContent
        List<XmlContent> mockXmlContentList = new ArrayList<>();
        XmlContent xmlContent1 = new XmlContent("Times Of India", 160, 120, 400, LocalDateTime.parse("2023-07-29T11:34:34.057"), "newspaper.xml");
        XmlContent xmlContent2 = new XmlContent("The Hindu", 200, 150, 300, LocalDateTime.parse("2023-07-30T08:20:15.120"), "the_hindu.xml");
        // Add more XmlContent objects as needed
        mockXmlContentList.add(xmlContent1);
        mockXmlContentList.add(xmlContent2);

        // Create a mock Page with the list of XmlContent
        Page<XmlContent> mockPage = new PageImpl<>(mockXmlContentList);

        // Mock the service method behavior for Page<XmlContent>
        when(xmlService.getXmlContentWithFilteringAndPagination(any(), any())).thenReturn(mockPage);

        // Create dummy filter and pagination objects
        XmlContentDTO filterDTO = new XmlContentDTO();
        PaginationRequestDTO paginationRequest = new PaginationRequestDTO(0,10,"id");

        // Call the controller method
        ResponseEntity<Page<XmlContent>> response = xmlUploadController.getXmlContent(filterDTO, paginationRequest);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
    }
}
