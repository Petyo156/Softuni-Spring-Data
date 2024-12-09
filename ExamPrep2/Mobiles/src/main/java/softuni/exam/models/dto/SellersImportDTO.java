package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

public class SellersImportDTO {
    @Expose
    @Length(min = 3, max = 30)
    private String firstName;

    @Expose
    @Length(min = 3, max = 30)
    private String lastName;

    @Expose
    @Length(min = 3, max = 6)
    private String personalNumber;

    public SellersImportDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public @Length(min = 3, max = 6) String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(@Length(min = 3, max = 6) String personalNumber) {
        this.personalNumber = personalNumber;
    }
}
