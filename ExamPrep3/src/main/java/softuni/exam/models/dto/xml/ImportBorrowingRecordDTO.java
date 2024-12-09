package softuni.exam.models.dto.xml;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "borrowing_record")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportBorrowingRecordDTO {
    @XmlElement(name = "borrow_date")
    @NotNull
    private String borrowDate;

    @XmlElement(name = "return_date")
    @NotNull
    private String returnDate;

    @XmlElement(name = "book")
    private BorrowingRecordBookDTO bookDTO;

    @XmlElement(name = "member")
    private BorrowingRecordMemberDTO memberDTO;

    @XmlElement
    @Length(min = 3, max = 100)
    private String remarks;

    public ImportBorrowingRecordDTO() {
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowingRecordBookDTO getBookDTO() {
        return bookDTO;
    }

    public void setBookDTO(BorrowingRecordBookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }

    public BorrowingRecordMemberDTO getMemberDTO() {
        return memberDTO;
    }

    public void setMemberDTO(BorrowingRecordMemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
