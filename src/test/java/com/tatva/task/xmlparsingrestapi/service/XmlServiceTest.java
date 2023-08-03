package com.tatva.task.xmlparsingrestapi.service;
import com.tatva.task.xmlparsingrestapi.dto.XmlContentDTO;
import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import com.tatva.task.xmlparsingrestapi.repository.XmlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class XmlServiceTest {

    @Mock
    private XmlRepository xmlRepository;

    @InjectMocks
    private XmlServiceImpl xmlService;

    @Mock
    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetXmlContentWithFilteringAndPagination() {
        // Mocking input parameters
        XmlContentDTO filterDTO = new XmlContentDTO();
        filterDTO.setFilterBy("Newspaper");
        Pageable pageable = Pageable.unpaged();

        // Mocking repository response
        List<XmlContent> xmlContents = new ArrayList<>();
        xmlContents.add(new XmlContent("Newspaper 1", 800, 600, 300, LocalDateTime.now(), "file1.xml"));
        xmlContents.add(new XmlContent("Newspaper 2", 1024, 768, 200, LocalDateTime.now(), "file2.xml"));
        Page<XmlContent> expectedPage = mock(Page.class);
        when(expectedPage.getContent()).thenReturn(xmlContents);
        when(xmlRepository.findByNewspaperNameContainingIgnoreCase("Newspaper", pageable)).thenReturn(expectedPage);

        // Perform the method to be tested
        Page<XmlContent> resultPage = xmlService.getXmlContentWithFilteringAndPagination(filterDTO, pageable);

        // Verify the repository method was called with the correct parameters
        verify(xmlRepository, times(1)).findByNewspaperNameContainingIgnoreCase("Newspaper", pageable);

        // Assert the result
        assertEquals(xmlContents, resultPage.getContent());
    }

    @Test
    void testGetXmlContentWithFilteringAndPagination_WithNullFilterDTO() {
        // Mocking input parameters
        Pageable pageable = Pageable.unpaged();

        // Mocking repository response
        List<XmlContent> xmlContents = new ArrayList<>();
        xmlContents.add(new XmlContent("Newspaper 1", 800, 600, 300, LocalDateTime.now(), "file1.xml"));
        xmlContents.add(new XmlContent("Newspaper 2", 1024, 768, 200, LocalDateTime.now(), "file2.xml"));
        Page<XmlContent> expectedPage = mock(Page.class);
        when(expectedPage.getContent()).thenReturn(xmlContents);
        when(xmlRepository.findAll(pageable)).thenReturn(expectedPage);

        // Perform the method to be tested
        Page<XmlContent> resultPage = xmlService.getXmlContentWithFilteringAndPagination(null, pageable);

        // Verify the repository method was called with the correct parameters
        verify(xmlRepository, times(1)).findAll(pageable);

        // Assert the result
        assertEquals(xmlContents, resultPage.getContent());
    }


    @Test
    void testGetElementText() {
        // Mocking input parameters
        Element element = mock(Element.class);
        NodeList nodeList = mock(NodeList.class);
        when(element.getElementsByTagName("tagName")).thenReturn(nodeList);
        when(nodeList.getLength()).thenReturn(1);
        when(nodeList.item(0)).thenReturn(mock(Node.class));
        when(nodeList.item(0).getTextContent()).thenReturn("TagContent");

        // Perform the method to be tested
        String result = xmlService.getElementText(element, "tagName");

        // Assert the result
        assertEquals("TagContent", result);
    }


}
