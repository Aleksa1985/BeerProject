package com.lexsoft.project.beer.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public String getFileAsString(String fileName) {
        try {
            InputStream input = this.getClass().getResourceAsStream(fileName);
            return IOUtils.toString(input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
