package ru.textparser.dhtextparserproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.textparser.dhtextparserproject.service.FileParserService;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Контроллер для работы с файлами
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class TextParserController {

    private final FileParserService fileParserService;

    @RequestMapping(method = RequestMethod.POST, value = "/uploadFile")
    public ResponseEntity<String> uploadFile ( @RequestParam("file") MultipartFile file ) {
        try {
            if (file.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            var fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            if (!fileParserService.sortAndSaveFileData(fileContent)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
