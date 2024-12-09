package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.ImportBooksDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class BookServiceImpl implements BookService {
    static final String PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importBooks() throws IOException {
        ImportBooksDTO[] importBooksDTOS = gson.fromJson(readBooksFromFile(), ImportBooksDTO[].class);

        StringBuilder sb = new StringBuilder();


        Arrays.stream(importBooksDTOS).forEach(c -> {
            if (!this.validationUtil.isValid(c) ||
                this.bookRepository.getBookByTitle(c.getTitle()).isPresent()){
                sb.append("Invalid book").append(System.lineSeparator());
            } else {
                Book mapped = modelMapper.map(c, Book.class);
                bookRepository.saveAndFlush(mapped);
                sb.append(String.format("Successfully imported book %s - %s", mapped.getAuthor(), mapped.getTitle())).append(System.lineSeparator());
            }
        });

        return sb.toString().trim();
    }
}
