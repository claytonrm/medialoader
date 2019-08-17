package com.roihunter.medialoader.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    private Util() {
        throw new AssertionError("Util class should not be instantiated.");
    }

    public static String readJsonFile(final String filePath) {
        try {
            final File input = new ClassPathResource(filePath).getFile();
            return new String(Files.readAllBytes(Paths.get(input.getPath())));
        } catch (IOException e) {
            log.error("Could not read json file.", e);
        }
        return null;
    }

}
