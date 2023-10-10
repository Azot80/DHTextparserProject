package ru.textparser.dhtextparserproject.service;

import java.io.IOException;

public interface FileParserService {
    boolean sortAndSaveFileData( String fileContent) throws IOException;
}
