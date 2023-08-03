package com.tatva.task.xmlparsingrestapi.controller;

import com.tatva.task.xmlparsingrestapi.dto.PaginationRequestDTO;
import com.tatva.task.xmlparsingrestapi.dto.XmlContentDTO;
import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import com.tatva.task.xmlparsingrestapi.exception.XmlProcessingException;
import com.tatva.task.xmlparsingrestapi.service.XmlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;


/* @Controller Class to for Request Mapping
 * which is containing get and post mapping.
 */
@RestController
@RequestMapping("/xml")
public class XmlUploadController {
    @Autowired
    private XmlServiceImpl xmlService;


    /* This Method is For uploading the xml file through xml/upload endpoints */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadXmlFile(final @RequestParam("file") MultipartFile file) throws IOException,SAXException,ParserConfigurationException{
            xmlService.processXmlFile(file);
            return ResponseEntity.ok("XML file uploaded and processed successfully.");

    }


    /* This Method is For getting all xml-content with specific filtering, sorting and pagination methods */
    @GetMapping("/xml-content")
    public ResponseEntity<Page<XmlContent>> getXmlContent(
            @ModelAttribute XmlContentDTO filterDTO,
            @ModelAttribute PaginationRequestDTO paginationRequest
    ) {
        final Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), Sort.by(paginationRequest.getSortBy()));
        Page<XmlContent> xmlContentPage = xmlService.getXmlContentWithFilteringAndPagination(filterDTO, pageable);
        return ResponseEntity.ok(xmlContentPage);
    }


}
