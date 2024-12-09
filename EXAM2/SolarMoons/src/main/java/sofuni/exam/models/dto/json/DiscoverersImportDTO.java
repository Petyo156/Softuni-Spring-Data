package sofuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class DiscoverersImportDTO {
    @Expose
    @Length(min = 2, max = 20)
    @NotNull
    private String firstName;
    @Expose
    @Length(min = 2, max = 20)
    @NotNull
    private String lastName;
    @Expose
    @Length(min = 5, max = 20)
    @NotNull
    private String nationality;
    @Expose
    @Length(min = 5, max = 20)
    private String occupation;

    public DiscoverersImportDTO() {
    }

    public @Length(min = 2, max = 20) @NotNull String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Length(min = 2, max = 20) @NotNull String firstName) {
        this.firstName = firstName;
    }

    public @Length(min = 2, max = 20) @NotNull String getLastName() {
        return lastName;
    }

    public void setLastName(@Length(min = 2, max = 20) @NotNull String lastName) {
        this.lastName = lastName;
    }

    public @Length(min = 5, max = 20) @NotNull String getNationality() {
        return nationality;
    }

    public void setNationality(@Length(min = 5, max = 20) @NotNull String nationality) {
        this.nationality = nationality;
    }

    public @Length(min = 5, max = 20) String getOccupation() {
        return occupation;
    }

    public void setOccupation(@Length(min = 5, max = 20) String occupation) {
        this.occupation = occupation;
    }
}
