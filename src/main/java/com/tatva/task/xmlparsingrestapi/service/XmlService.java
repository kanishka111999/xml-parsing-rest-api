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

/**
 * @Service layer which contains functionality of uploading and sorting,
 * filtering and paging of Xml Content.
 */
@Service
public class XmlService {

    @Autowired
    private XmlRepository xmlRepository;

    /**
     * Gets xml content with filtering and pagination.
     *
     * @param filterBy the filter by Newspapername
     * @param pageable the pageable 10
     * @return the xml content with filtering and pagination
     */
    public Page<XmlContent> getXmlContentWithFilteringAndPagination(String filterBy, Pageable pageable) {
        if (filterBy != null && !filterBy.isEmpty()) {
            // Perform filtering based on the "Newspaper Name" field
            return xmlRepository.findByNewspaperNameContainingIgnoreCase(filterBy, pageable);
        } else {
            // Fetch all content without filtering
            return xmlRepository.findAll(pageable);
        }
    }

    /**
     * Gets xml content stored in database.
     * @return the xml content in json.
     */
    public List<XmlContent> getXmlContent()
    {
        return xmlRepository.findAll();
    }

    /**
     * Process xml file uploaded and parse and validate it and
     * save the xml element in the database.
     * @param file the newspaper.xml
     * @throws IOException the io exception if any process terminates.
     */
    public void processXmlFile(MultipartFile file) throws IOException {
        validateXmlAgainstXsd(file);
        XmlContent xmlContent = parseXml(file);
        xmlRepository.save(xmlContent);
    }

    /**
     * Validate xml against xsd.
     *
     * @param file the newspaper.xml
     * @throws IOException the io exception
     */
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

    /**
     * Parse the xml content which is uploaded.
     * @param file newspaper.xml
     * @return the xml content
     * @throws IOException the io exception
     */
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

    /**
     * Gets element text.
     *
     * @param element Device
     * @param tagName DeviceInfo
     * @return height width dpi of deviceInfo
     */
    public String getElementText(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : null;
    }
}

