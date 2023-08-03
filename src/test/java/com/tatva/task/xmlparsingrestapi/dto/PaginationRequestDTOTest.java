package com.tatva.task.xmlparsingrestapi.dto;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaginationRequestDTOTest {

    @Test
    public void testDefaultConstructor() {
        PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(0, 10, "id");
        Assertions.assertEquals(0, paginationRequestDTO.getPage());
        Assertions.assertEquals(10, paginationRequestDTO.getSize());
        Assertions.assertEquals("id", paginationRequestDTO.getSortBy());
    }

    @Test
    public void testGettersAndSetters() {
        PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(0, 10, "id");

        paginationRequestDTO.setPage(2);
        Assertions.assertEquals(2, paginationRequestDTO.getPage());

        paginationRequestDTO.setSize(20);
        Assertions.assertEquals(20, paginationRequestDTO.getSize());

        paginationRequestDTO.setSortBy("name");
        Assertions.assertEquals("name", paginationRequestDTO.getSortBy());
    }



    @Test
    public void testConstructorWithNullSortBy() {
        PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(0, 10, null);
        Assertions.assertEquals(0, paginationRequestDTO.getPage());
        Assertions.assertEquals(10, paginationRequestDTO.getSize());
        Assertions.assertNull(paginationRequestDTO.getSortBy());
    }
}
