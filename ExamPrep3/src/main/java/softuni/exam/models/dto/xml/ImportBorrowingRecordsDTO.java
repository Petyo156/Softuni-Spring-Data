package softuni.exam.models.dto.xml;

import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportBorrowingRecordsDTO {
    @XmlElement(name = "borrowing_record")
    private List<ImportBorrowingRecordDTO> borrowingRecords;

    public ImportBorrowingRecordsDTO() {}

    public List<ImportBorrowingRecordDTO> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(List<ImportBorrowingRecordDTO> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}
