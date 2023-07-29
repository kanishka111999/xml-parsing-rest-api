package com.tatva.task.xmlparsingrestapi.service;

import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import com.tatva.task.xmlparsingrestapi.repository.XmlRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class XmlServiceTest {

    @Mock
    private XmlRepository xmlRepository;

    @InjectMocks
    private XmlService xmlService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetXmlContentWithFilteringAndPagination_WithFilter() {
        // Test data
        String filterBy = "Newspaper";
        List<XmlContent> xmlContents = new ArrayList<>();
        xmlContents.add(new XmlContent("Newspaper A", 800, 600, 300, LocalDateTime.now(), "file1.xml"));
        xmlContents.add(new XmlContent("Newspaper B", 1024, 768, 200, LocalDateTime.now(), "file2.xml"));
        Page<XmlContent> page = new PageImpl<>(xmlContents);

        // Mocking the repository
        Pageable pageable = PageRequest.of(0, 10);
        when(xmlRepository.findByNewspaperNameContainingIgnoreCase(filterBy, pageable)).thenReturn(page);

        // Test the method
        Page<XmlContent> result = xmlService.getXmlContentWithFilteringAndPagination(filterBy, pageable);

        // Verify the result
        assertEquals(xmlContents.size(), result.getContent().size());
        assertEquals(xmlContents.get(0).getNewspaperName(), result.getContent().get(0).getNewspaperName());
        assertEquals(xmlContents.get(1).getNewspaperName(), result.getContent().get(1).getNewspaperName());

        verify(xmlRepository, times(1)).findByNewspaperNameContainingIgnoreCase(filterBy, pageable);
        verifyNoMoreInteractions(xmlRepository);
    }

    @Test
    public void testGetXmlContentWithFilteringAndPagination_WithoutFilter() {
        // Test data
        List<XmlContent> xmlContents = new ArrayList<>();
        xmlContents.add(new XmlContent("Newspaper A", 800, 600, 300, LocalDateTime.now(), "file1.xml"));
        xmlContents.add(new XmlContent("Newspaper B", 1024, 768, 200, LocalDateTime.now(), "file2.xml"));
        Page<XmlContent> page = new PageImpl<>(xmlContents);

        // Mocking the repository
        Pageable pageable = PageRequest.of(0, 10);
        when(xmlRepository.findAll(pageable)).thenReturn(page);

        // Test the method without filtering
        Page<XmlContent> result = xmlService.getXmlContentWithFilteringAndPagination(null, pageable);

        // Verify the result
        assertEquals(xmlContents.size(), result.getContent().size());
        assertEquals(xmlContents.get(0).getNewspaperName(), result.getContent().get(0).getNewspaperName());
        assertEquals(xmlContents.get(1).getNewspaperName(), result.getContent().get(1).getNewspaperName());

        verify(xmlRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(xmlRepository);
    }

    @Test
    public void testProcessXmlFile_ValidXmlFile() throws IOException {
        // Test data
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<epaperRequest>\n" +
                "    <deviceInfo name=\"someName\" id=\"someId\">\n" +
                "        <screenInfo width=\"800\" height=\"600\" dpi=\"300\" />\n" +
                "        <osInfo name=\"OS_Name\" version=\"1.0\" />\n" +
                "        <appInfo>\n" +
                "            <newspaperName>Newspaper X</newspaperName>\n" +
                "            <version>1.0</version>\n" +
                "        </appInfo>\n" +
                "    </deviceInfo>\n" +
                "    <getPages editionDefId=\"123\" publicationDate=\"2023-07-28\" />\n" +
                "</epaperRequest>";

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", xmlData.getBytes());

        // Mocking the repository save method to return the saved XmlContent object
        XmlContent savedXmlContent = new XmlContent("Newspaper X", 800, 600, 300, LocalDateTime.now(), "test.xml");
        when(xmlRepository.save(any(XmlContent.class))).thenReturn(savedXmlContent);

        // Test the method
        xmlService.processXmlFile(file);

        // Verify the save method was called with the correct XmlContent object
        ArgumentCaptor<XmlContent> xmlContentCaptor = ArgumentCaptor.forClass(XmlContent.class);
        verify(xmlRepository, times(1)).save(xmlContentCaptor.capture());
        verifyNoMoreInteractions(xmlRepository);

        // Verify the captured XmlContent object
        XmlContent capturedXmlContent = xmlContentCaptor.getValue();
        assertEquals("Newspaper X", capturedXmlContent.getNewspaperName());
        assertEquals(800, capturedXmlContent.getWidth());
        assertEquals(600, capturedXmlContent.getHeight());
        assertEquals(300, capturedXmlContent.getDpi());
        assertNotNull(capturedXmlContent.getUploadTime());
        assertEquals("test.xml", capturedXmlContent.getFilename());
    }


    @Test(expected = IOException.class)
    public void testProcessXmlFile_InvalidXmlFile() throws IOException {
        // Test data - Invalid XML data
        String invalidXmlData = "<invalid>";

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", invalidXmlData.getBytes());

        // Test the method with an invalid XML file
        xmlService.processXmlFile(file);

        // The method should throw an IOException for invalid XML
    }

    @Test
    public void testValidateXmlAgainstXsd_ValidXmlFile() throws IOException {
        // Test data
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<epaperRequest>\n" +
                "    <deviceInfo name=\"someName\" id=\"someId\">\n" +
                "        <screenInfo width=\"800\" height=\"600\" dpi=\"300\" />\n" +
                "        <osInfo name=\"OS_Name\" version=\"1.0\" />\n" +
                "        <appInfo>\n" +
                "            <newspaperName>Newspaper X</newspaperName>\n" +
                "            <version>1.0</version>\n" +
                "        </appInfo>\n" +
                "    </deviceInfo>\n" +
                "    <getPages editionDefId=\"123\" publicationDate=\"2023-07-28\" />\n" +
                "</epaperRequest>";

        // Test the method with a valid XML file
        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", xmlData.getBytes());
        xmlService.validateXmlAgainstXsd(file);
    }


    @Test(expected = IOException.class)
    public void testValidateXmlAgainstXsd_InvalidXmlFile() throws IOException {
        // Test data - Invalid XML data
        String invalidXmlData = "<invalid>";

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", invalidXmlData.getBytes());

        // Test the method with an invalid XML file
        xmlService.validateXmlAgainstXsd(file);

        // The method should throw an IOException for invalid XML
    }

    @Test
    public void testParseXml_ValidXmlFile() throws IOException {
        // Test data
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<deviceInfo>\n" +
                "    <newspaperName>Newspaper X</newspaperName>\n" +
                "    <screenInfo width=\"800\" height=\"600\" dpi=\"300\"/>\n" +
                "</deviceInfo>";

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", xmlData.getBytes());

        // Test the method
        XmlContent xmlContent = xmlService.parseXml(file);

        // Verify the result
        assertEquals("Newspaper X", xmlContent.getNewspaperName());
        assertEquals(800, xmlContent.getWidth());
        assertEquals(600, xmlContent.getHeight());
        assertEquals(300, xmlContent.getDpi());
        assertNotNull(xmlContent.getUploadTime());
        assertEquals("test.xml", xmlContent.getFilename());
    }

    @Test(expected = IOException.class)
    public void testParseXml_InvalidXmlFile() throws IOException {
        // Test data - Invalid XML data
        String invalidXmlData = "<invalid></invalid>";

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", invalidXmlData.getBytes());

        // Test the method with an invalid XML file
        xmlService.parseXml(file);

        // The method should throw an IOException for invalid XML
    }

    @Test
    public void testGetXmlContent() {
        List<XmlContent> expectedXmlContentList = new ArrayList<>();
        expectedXmlContentList.add(new XmlContent("abc", 100, 200, 320, LocalDateTime.now(), "abc.xml"));
        expectedXmlContentList.add(new XmlContent("def", 300, 400, 420, LocalDateTime.now(), "def.xml"));

        // Mock the behavior of xmlRepository.findAll() to return the sample list
        when(xmlRepository.findAll()).thenReturn(expectedXmlContentList);

        // Act
        List<XmlContent> actualXmlContentList = xmlService.getXmlContent();

        // Assert
        // Verify that the repository method was called
        verify(xmlRepository, times(1)).findAll();

        // Verify the returned list is not null and has the expected size
        assert actualXmlContentList != null;
        assert actualXmlContentList.size() == expectedXmlContentList.size();

        assert actualXmlContentList.containsAll(expectedXmlContentList);
    }
}


