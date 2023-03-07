package com.one.filesearch.service;

import com.one.filesearch.exception.FileDoesNotExistException;
import com.one.filesearch.exception.FileNameTooLargeException;
import com.one.filesearch.exception.InvalidFileFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyServiceImpl implements MyService {

    @Override
    public List<Map.Entry<String, Integer>> getDuplicateWords(String fileName) throws IOException {

//        logging info data to the file logging.log
        log.info("getDuplicateWords() method is called in Service.class.");

//        trim input file name and lower case
        String name = fileName.trim().toLowerCase();

//        check if input file name size is >15
        if (fileName.length() > 15) {
            log.error("input file name size is exceeded.");
            throw new FileNameTooLargeException("File name length should be < 15 .");
        }

//        check if file extension is .txt
        if (!fileName.endsWith(".txt")) {
            log.error("File extension is inappropiate. ");
            throw new InvalidFileFormatException("file name should be in the format of .txt text file. ");
        }

//        creation of File object with input file name
        File file = new File("Dir/" + name);

//        check if file exists in Dir directory
        if (!file.exists()) {
            log.error("file " + name + " doesn't exist.");
            throw new FileDoesNotExistException("file " + name + " doesn't exist.");
        }

//        reading words from a file in a List
        List<String> readList = Arrays.stream(Files.readString(Paths.get("Dir/" + name)).split("\\s"))
                .map(i -> i.replace(".", "").toLowerCase())
                .map(i -> i.replace(",", ""))
                .filter(i -> i.length() > 0).collect(Collectors.toList());

//        empty HashMap object to store duplicate words and count
        HashMap<String, Integer> map = new HashMap<>();

//        putting duplicate words in map
        readList.stream().forEach(s -> {
            if (map.containsKey(s))
                map.put(s, map.get(s) + 1);
            else
                map.put(s, 1);
        });

//        creating list of entry where count of duplicate words > 1
        List<Map.Entry<String, Integer>> listData = map.entrySet().stream().filter(e -> e.getValue() > 1).collect(Collectors.toList());

//        sorting duplicate words best on repitation count in descending order
        Collections.sort(listData, (o1, o2) -> o2.getValue() - o1.getValue());

//        logging for successfull returning data
        log.info("Duplicate data list is returned successfully.");

//        returning descending ordered List of entries data of duplication word and count
        return listData;
    }
}
