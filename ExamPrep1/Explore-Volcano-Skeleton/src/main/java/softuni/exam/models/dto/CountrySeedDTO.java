package softuni.exam.models.dto;


import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;


public class CountrySeedDTO {
    @Expose
    @Length(min = 3, max = 30)
    private String name;
    @Expose
    @Length(min = 3, max = 30)
    private String capital;

    public CountrySeedDTO() {
    }

    public @Length(min = 3, max = 30) String getName() {
        return name;
    }

    public void setName(@Length(min = 3, max = 30) String name) {
        this.name = name;
    }

    public @Length(min = 3, max = 30) String getCapital() {
        return capital;
    }

    public void setCapital(@Length(min = 3, max = 30) String capital) {
        this.capital = capital;
    }
}
