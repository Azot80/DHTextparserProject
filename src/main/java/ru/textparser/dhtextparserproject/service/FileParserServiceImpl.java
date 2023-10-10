package ru.textparser.dhtextparserproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.textparser.dhtextparserproject.entity.enums.CommonUtils;
import ru.textparser.dhtextparserproject.exception.FileParserException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

/**
 * Сервис для работы с файлами
 */
@Slf4j
@Service
public class FileParserServiceImpl implements FileParserService {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final static String FILE_CORE_NAME = "sortedInputData_";

    private final static String FILE_TYPE_END = ".txt";
    @Value("${upload.path}")
    private static String uploadPath;

    @Override
    public boolean sortAndSaveFileData ( String fileContent ) {
        Map<String, String> dataMap = new TreeMap<>();
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] parts = line.split(CommonUtils.SEPARATOR.getVal());
            if (parts.length == 2) {
                dataMap.put(parts[0], parts[1]);
            }
        }

        return saveDataToFile(dataMap);
    }

    private boolean saveDataToFile ( Map<String, String> dataMap ) {
        var fileName = getFileName();
        var directoryPath = fileName.substring(0, fileName.lastIndexOf(File.separator));

        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            log.error("Ошибка при создании директории для записи файла: " + directoryPath, e);
            return false;
        }

        try (var writer = new BufferedWriter(new FileWriter(fileName))) {
            dataMap.forEach(( key, value ) -> {
                try {
                    writer.write(key + CommonUtils.SEPARATOR.getVal() + value);
                    writer.newLine();
                } catch (FileParserException | IOException e) {
                    log.error("Ошибка записи файла: " + fileName);
                }
            });

            return true;

        } catch (IOException e) {
            log.error("Ошибка открытия для записи файла " + fileName, e);
        }

        return false;
    }

    private String getFileName () {
        var res = new StringBuilder();
        var now = LocalDateTime.now();
        var formattedDateTime = now.format(formatter);
        return res.append(FileParserServiceImpl.uploadPath).append(FILE_CORE_NAME).append(formattedDateTime).append(FILE_TYPE_END).toString();
    }
}
