package com.justbuyit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utilities for testing.
 */
public class TestUtils {

    /**
     * Reads the given file from the resources directory as a stream.
     * 
     * @param fileName
     *            the file name
     * @return the file stream
     * @throws FileNotFoundException
     *             if the file was not found
     */
    public static InputStream getSampleFileStream(String fileName) throws FileNotFoundException {
        File resourcesDirectory = new File("src/test/resources");
        File eventXML = new File(resourcesDirectory, fileName);
        return new FileInputStream(eventXML);
    }

}
