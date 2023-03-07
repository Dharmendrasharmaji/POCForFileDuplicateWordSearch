package com.one.filesearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MyService {

    List<Map.Entry<String, Integer>> getDuplicateWords(String fileName) throws IOException;
}
