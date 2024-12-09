package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.ImportBooksDTO;
import softuni.exam.models.dto.json.ImportLibraryMembersDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    public static final String PATH = "src/main/resources/files/json/library-members.json";

    private final LibraryMemberRepository libraryMemberRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        ImportLibraryMembersDTO[] importLibraryMembersDTOS = gson.fromJson(readLibraryMembersFileContent(), ImportLibraryMembersDTO[].class);

        StringBuilder sb = new StringBuilder();


        Arrays.stream(importLibraryMembersDTOS).forEach(c -> {
            if (!this.validationUtil.isValid(c) ||
                    this.libraryMemberRepository.getLibraryMemberByPhoneNumber(c.getPhoneNumber()).isPresent()){
                sb.append("Invalid library member").append(System.lineSeparator());
            } else {
                LibraryMember mapped = modelMapper.map(c, LibraryMember.class);
                libraryMemberRepository.saveAndFlush(mapped);
                sb.append(String.format("Successfully imported library member %s - %s", mapped.getFirstName(), mapped.getLastName())).append(System.lineSeparator());
            }
        });

        return sb.toString().trim();
    }
}
