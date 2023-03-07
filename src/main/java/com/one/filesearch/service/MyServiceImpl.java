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

        log.info("getDuplicateWords() method is called in Service.class .");

        String name = fileName.trim().toLowerCase();

        if (fileName.length()>15) {
            log.error("input file name size is exceeded.");
            throw new FileNameTooLargeException("File name length should be < 15 .");
        }

        if (!fileName.endsWith(".txt")) {
            log.error("File extension is inappropiate. ");
            throw new InvalidFileFormatException("file name should be in the format of .txt text file. ");
        }

        File file = new File("Dir/" + name);

        if (!file.exists()) {
            log.error("file "+name+" doesn't exists");
            throw new FileDoesNotExistException("file " + name + " doesn't exist.");
        }
        List<String> readList = Arrays.stream(Files.readString(Paths.get("Dir/" + name)).split("\\s"))
                .map(i -> i.replace(".", "").toLowerCase())
                .map(i -> i.replace(",", ""))
                .filter(i -> i.length() > 0).collect(Collectors.toList());

        HashMap<String, Integer> map = new HashMap<>();

        for (String s :
                readList) {
            if (map.containsKey(s))
                map.put(s, map.get(s) + 1);
            else
                map.put(s, 1);
        }

        List<Map.Entry<String, Integer>> listData = map.entrySet().stream().filter(e -> e.getValue() > 1).collect(Collectors.toList());
        Collections.sort(listData, (o1, o2) -> o2.getValue() - o1.getValue());

        log.info("Duplicate data list is returned successfully.");
        return listData;
    }
}
