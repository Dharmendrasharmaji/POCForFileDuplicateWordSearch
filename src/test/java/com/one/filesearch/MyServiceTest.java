package com.one.filesearch;


import com.one.filesearch.exception.FileDoesNotExistException;
import com.one.filesearch.exception.FileNameTooLargeException;
import com.one.filesearch.exception.InvalidFileFormatException;
import com.one.filesearch.service.MyService;
import com.one.filesearch.service.MyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class MyServiceTest {


    private MyService myService;

    @BeforeEach
    void setUp() {
        myService = new MyServiceImpl();
    }

    @Test
    public void shouldThrowExceptionWhenFileNameIsGreaterThan15Character() {

        Assertions.assertThrows(FileNameTooLargeException.class, () -> myService.getDuplicateWords("diiiiii11111111112222.txt"));
    }

    @Test
    public void shouldThrowExceptionWhenFileExtensionIsNotTXT() {

        Assertions.assertThrows(InvalidFileFormatException.class, () -> myService.getDuplicateWords("myfile.pdf"));
    }

    @Test
    public void shouldThrowExceptionWhenFileDoesNotExist() {

        Assertions.assertThrows(FileDoesNotExistException.class, () -> myService.getDuplicateWords("myfile4.txt"));
    }

    @Test
    public void shouldReturnListOfEntriesOfDuplicateWordsAndCountInDescendingOrder() throws IOException {

        List<Map.Entry<String, Integer>> actual = myService.getDuplicateWords("myfile2.txt");

        Assertions.assertEquals(7, actual.size());

        Assertions.assertTrue(checkIsInDescendingOrder(actual));


    }

    private boolean checkIsInDescendingOrder(List<Map.Entry<String, Integer>> map) {
        for (int i = 1; i < map.size(); i++) {
            if (map.get(i).getValue() <= map.get(i - 1).getValue()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
