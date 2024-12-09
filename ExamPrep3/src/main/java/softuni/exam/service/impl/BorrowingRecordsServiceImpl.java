package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ImportBorrowingRecordDTO;
import softuni.exam.models.dto.xml.ImportBorrowingRecordsDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    static final String PATH = "src/main/resources/files/xml/borrowing-records.xml";

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        ImportBorrowingRecordsDTO importBorrowingRecordsDTO = this.xmlParser.fromFile(PATH, ImportBorrowingRecordsDTO.class);

        StringBuilder sb = new StringBuilder();
        for (ImportBorrowingRecordDTO borrowingRecordDTO : importBorrowingRecordsDTO.getBorrowingRecords()) {
            if (!this.validationUtil.isValid(borrowingRecordDTO) ||
                    this.bookRepository.findBookByTitle(borrowingRecordDTO.getBookDTO().getTitle()).isEmpty() ||
                    this.libraryMemberRepository.findById(borrowingRecordDTO.getMemberDTO().getId()).isEmpty()) {
                sb.append("Invalid borrowing record").append(System.lineSeparator());
                continue;
            }

            Book book = (Book) this.bookRepository.findBookByTitle(borrowingRecordDTO.getBookDTO().getTitle()).get();
            LibraryMember libraryMember = this.libraryMemberRepository.findById(borrowingRecordDTO.getMemberDTO().getId()).get();

            BorrowingRecord borrowingRecord = this.modelMapper.map(borrowingRecordDTO, BorrowingRecord.class);
            borrowingRecord.setBook(book);
            borrowingRecord.setLibraryMember(libraryMember);
            borrowingRecord.setBorrowDate(LocalDate.parse(borrowingRecordDTO.getBorrowDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            this.borrowingRecordRepository.save(borrowingRecord);

            sb.append(String.format("Successfully imported borrowing record %s - %s%n",
                    borrowingRecord.getBook().getTitle(), borrowingRecord.getBorrowDate().toString()));
        }

        return sb.toString();
    }


    @Override
    public String exportBorrowingRecords() {
        List<BorrowingRecord> borrowingRecords = borrowingRecordRepository
                .findAllByBorrowDateBeforeOrderByBorrowDateDesc(LocalDate.parse("2021-09-10", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        StringBuilder sb = new StringBuilder();
        for (BorrowingRecord borrowingRecord : borrowingRecords) {
            if (borrowingRecord.getBook().getGenre() == Genre.SCIENCE_FICTION) {
                Book book = borrowingRecord.getBook();
                sb.append(String.format("Book title: %s", book.getTitle())).append(System.lineSeparator());
                sb.append(String.format("*Book author: %s", book.getAuthor())).append(System.lineSeparator());
                sb.append(String.format("**Date borrowed: %s", borrowingRecord.getBorrowDate().toString())).append(System.lineSeparator());
                sb.append(String.format("***Borrowed by: %s %s", borrowingRecord.getLibraryMember().getFirstName(),
                        borrowingRecord.getLibraryMember().getLastName())).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
