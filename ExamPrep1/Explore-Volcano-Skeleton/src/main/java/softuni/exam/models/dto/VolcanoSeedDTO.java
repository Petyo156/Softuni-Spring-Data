package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.enums.VolcanoType;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class VolcanoSeedDTO {
    @Expose
    @Length(min = 2, max = 30)
    private String name;

    @Expose
    @Positive
    private Integer elevation;

    @Expose
    private String volcanoType;

    @Expose
    private boolean isActive;

    @Expose
    private String lastEruption;

    @Expose
    private Long country;

    public VolcanoSeedDTO() {}

    public @Length(min = 2, max = 30) String getName() {
        return name;
    }

    public void setName(@Length(min = 2, max = 30) String name) {
        this.name = name;
    }

    public @Positive Integer getElevation() {
        return elevation;
    }

    public void setElevation(@Positive Integer elevation) {
        this.elevation = elevation;
    }

    public String getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(String volcanoType) {
        this.volcanoType = volcanoType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(String lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
