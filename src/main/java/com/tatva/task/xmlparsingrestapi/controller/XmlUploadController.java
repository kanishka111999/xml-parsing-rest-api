package com.tatva.task.xmlparsingrestapi.controller;

import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import com.tatva.task.xmlparsingrestapi.repository.XmlRepository;
import com.tatva.task.xmlparsingrestapi.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


/**
 * @Controller Class to for Request Mapping which is containing get and post mapping.
 */
@RestController
@RequestMapping("/xml")
public class XmlUploadController {
    @Autowired
    private XmlService xmlService;

    @Autowired
    private XmlRepository xmlRepository;

    /**
     * Post mapping for uploading the xml file
     *
     * @param file newspaper.xml
     * @return the response entity
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file) {
        try {
            xmlService.processXmlFile(file);
            return ResponseEntity.ok("XML file uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading the XML file.");
        }
    }

    /**
     * Gets xml content for fetching and filtering and sorting.
     *
     * @param page     0
     * @param size     10
     * @param sortBy   attributes
     * @param filterBy the filter by newspaername
     * @return the xml content
     */
    @GetMapping("/xml-content")
    public ResponseEntity<Page<XmlContent>> getXmlContent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String filterBy
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<XmlContent> xmlContentPage = xmlService.getXmlContentWithFilteringAndPagination(filterBy, pageable);
            System.out.println(xmlContentPage);
            return ResponseEntity.ok(xmlContentPage);
        } catch (Exception e) {
            // Handle the exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * To Get xml content stored on database
     *
     * @return the all xml content
     */
    @GetMapping("/get-xml-content")
    public ResponseEntity<List<XmlContent>> getAllXmlContent()
    {
       List<XmlContent> xmlContent= xmlService.getXmlContent();
       return new ResponseEntity<List<XmlContent>>(xmlContent,HttpStatus.OK);
    }

}
