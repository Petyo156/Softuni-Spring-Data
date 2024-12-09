package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord extends BaseEntity {
    @Column(nullable = false, name = "borrow_date")
    private LocalDate borrowDate;
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;
    @Column
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "member_id",
            referencedColumnName = "id")
    private LibraryMember libraryMember;

    @ManyToOne
    @JoinColumn(name = "book_id",
            referencedColumnName = "id")
    private Book book;

    public BorrowingRecord() {
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
