package com.tatva.task.xmlparsingrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.xml.sax.SAXException;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(XmlProcessingException.class)
        public ResponseEntity<String> handleXmlProcessingException(XmlProcessingException ex) {
            // Handle the exception and return an appropriate response or error message.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Processing of XML has failed Please Try Again.");
        }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        // Handle the IOException and return an appropriate response or error message.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parsing and Validation Of XML file has failed , Please Upload the Valid Xml File." );
    }

    @ExceptionHandler(SAXException.class)
    public ResponseEntity<String> handleSAXException(SAXException ex) {
        // Handle the SAXException and return an appropriate response or error message.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parsing and Validation Of XML file has failed , Please Upload the Valid Xml File. ");
    }


}
