package com.tatva.task.xmlparsingrestapi.service;

import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import com.tatva.task.xmlparsingrestapi.repository.XmlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class XmlService {

    @Autowired
    private XmlRepository xmlRepository;

    // Method to get XML content with filtering and pagination
    public Page<XmlContent> getXmlContentWithFilteringAndPagination(String filterBy, Pageable pageable) {
        if (filterBy != null && !filterBy.isEmpty()) {
            // Perform filtering based on the "Newspaper Name" field
            return xmlRepository.findByNewspaperNameContainingIgnoreCase(filterBy, pageable);
        } else {
            // Fetch all content without filtering
            return xmlRepository.findAll(pageable);
        }
    }

    //return the all xmlContent records
    public List<XmlContent> getXmlContent()
    {
        return xmlRepository.findAll();
    }

    //method to process xml file
    public void processXmlFile(MultipartFile file) throws IOException {
        // Validate XML against XSD schema
        validateXmlAgainstXsd(file);

        // Parse and extract data from XML
        XmlContent xmlContent = parseXml(file);

        // Save data to the database
        xmlRepository.save(xmlContent);
    }

    public void validateXmlAgainstXsd(MultipartFile file) throws IOException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            InputStream xsdStream = getClass().getClassLoader().getResourceAsStream("epaperRequest.xsd");
            Schema schema = schemaFactory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file.getInputStream()));
        } catch (SAXException e) {
            throw new IOException("XML validation failed.", e);
        }
    }

    public XmlContent parseXml(MultipartFile file) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file.getInputStream());

            Element deviceInfoElement = (Element) doc.getElementsByTagName("deviceInfo").item(0);
            String newspaperName = getElementText(deviceInfoElement, "newspaperName");

            Element screenInfoElement = (Element) deviceInfoElement.getElementsByTagName("screenInfo").item(0);
            int width = Integer.parseInt(screenInfoElement.getAttribute("width"));
            int height = Integer.parseInt(screenInfoElement.getAttribute("height"));
            int dpi = Integer.parseInt(screenInfoElement.getAttribute("dpi"));

            LocalDateTime uploadTime = LocalDateTime.now();
            String filename = file.getOriginalFilename();

            return new XmlContent(newspaperName, width, height, dpi, uploadTime, filename);
        } catch (Exception e) {
            throw new IOException("XML parsing failed.", e);
        }
    }

    public String getElementText(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : null;
    }
}

