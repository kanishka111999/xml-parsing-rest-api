package com.tatva.task.xmlparsingrestapi.controller;


import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import com.tatva.task.xmlparsingrestapi.service.XmlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class XmlUploadControllerTest {

    @Mock
    private XmlService xmlService;

    @InjectMocks
    private XmlUploadController xmlUploadController;

    @Before
    public void setUp() {
    }

    @Test
    public void testUploadXmlFile_Success() throws IOException {
        MultipartFile file = new MockMultipartFile("test.xml", "<xml>Test content</xml>".getBytes());

        ResponseEntity<String> responseEntity = xmlUploadController.uploadXmlFile(file);

        verify(xmlService, times(1)).processXmlFile(any(MultipartFile.class));
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().equals("XML file uploaded and processed successfully.");
    }

    @Test
    public void testUploadXmlFile_Failure() throws IOException {
        MultipartFile file = new MockMultipartFile("test.xml", "<xml>Test content</xml>".getBytes());
        doThrow(new IOException()).when(xmlService).processXmlFile(any(MultipartFile.class));

        ResponseEntity<String> responseEntity = xmlUploadController.uploadXmlFile(file);

        verify(xmlService, times(1)).processXmlFile(any(MultipartFile.class));
        assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
        assert responseEntity.getBody().equals("Error uploading the XML file.");
    }

    @Test
    public void testGetXmlContent_Success() {
        int page = 0;
        int size = 10;
        String sortBy = "someField";
        String filterBy = "someFilter";
        Page<XmlContent> xmlContentPage = mock(Page.class);
        when(xmlService.getXmlContentWithFilteringAndPagination(eq(filterBy), any())).thenReturn(xmlContentPage);

        ResponseEntity<Page<XmlContent>> responseEntity = xmlUploadController.getXmlContent(page, size, sortBy, filterBy);

        verify(xmlService, times(1)).getXmlContentWithFilteringAndPagination(eq(filterBy), any());
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() == xmlContentPage;
    }

    @Test
    public void testGetXmlContent_Exception() {
        int page = 0;
        int size = 10;
        String sortBy = "someField";
        String filterBy = "someFilter";
        when(xmlService.getXmlContentWithFilteringAndPagination(eq(filterBy), any())).thenThrow(new RuntimeException());

        ResponseEntity<Page<XmlContent>> responseEntity = xmlUploadController.getXmlContent(page, size, sortBy, filterBy);

        verify(xmlService, times(1)).getXmlContentWithFilteringAndPagination(eq(filterBy), any());
        assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
        assert responseEntity.getBody() == null;
    }
    @Test
    public void testGetAllXmlContent() {
        // Arrange
        List<XmlContent> xmlContentList = new ArrayList<>();
        xmlContentList.add(new XmlContent("abc", 100, 200, 320, LocalDateTime.now(), "abc.xml"));
        xmlContentList.add(new XmlContent("def", 300, 400, 420, LocalDateTime.now(), "def.xml"));

        // Mock the behavior of xmlService.getXmlContent() to return the sample list
        when(xmlService.getXmlContent()).thenReturn(xmlContentList);

        // Act
        ResponseEntity<List<XmlContent>> responseEntity = xmlUploadController.getAllXmlContent();

        // Assert
        // Verify that the service method was called
        verify(xmlService, times(1)).getXmlContent();

        // Verify the response status code is HttpStatus.OK
        assert responseEntity.getStatusCode() == HttpStatus.OK;

        // Verify the response body contains the correct XML content list
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().size() == 2;
        assert responseEntity.getBody().get(0).getNewspaperName().equals("abc");
        assert responseEntity.getBody().get(1).getNewspaperName().equals("def");
    }
}
