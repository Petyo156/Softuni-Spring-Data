package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class ImportLibraryMembersDTO {
    @Expose
    @Length(min = 2, max = 40)
    private String address;
    @Expose
    @Length(min = 2, max = 30)
    @NotNull
    private String firstName;
    @Expose
    @Length(min = 2, max = 30)
    private String lastName;
    @Expose
    @Length(min = 2, max = 20)
    private String phoneNumber;

    public ImportLibraryMembersDTO() {
    }

    public @Length(min = 2, max = 40) String getAddress() {
        return address;
    }

    public void setAddress(@Length(min = 2, max = 40) String address) {
        this.address = address;
    }

    public @Length(min = 2, max = 30) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Length(min = 2, max = 30) String firstName) {
        this.firstName = firstName;
    }

    public @Length(min = 2, max = 30) String getLastName() {
        return lastName;
    }

    public void setLastName(@Length(min = 2, max = 30) String lastName) {
        this.lastName = lastName;
    }

    public @Length(min = 2, max = 20) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Length(min = 2, max = 20) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
