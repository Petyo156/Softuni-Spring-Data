package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "volcanologist")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistSeedDTO {

    @XmlElement(name = "first_name")
    @Length(min = 2, max = 30)
    private String firstName;

    @XmlElement(name = "last_name")
    @Length(min = 2, max = 30)
    private String lastName;

    @XmlElement
    private Double salary;

    @XmlElement
    @Min(18)
    @Max(80)
    private Integer age;

    @XmlElement(name = "exploring_from")
    private String exploringFrom;

    @XmlElement(name = "exploring_volcano_id")
    private Long exploringVolcano;

    public VolcanologistSeedDTO() {
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(String exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public Long getExploringVolcano() {
        return exploringVolcano;
    }

    public void setExploringVolcano(Long exploringVolcano) {
        this.exploringVolcano = exploringVolcano;
    }
}
/*
    <volcanologist>
        <first_name>John</first_name>
        <last_name>Doe</last_name>
        <salary>5000.00</salary>
        <age>55</age>
        <exploring_from>1987-01-15</exploring_from>
        <exploring_volcano_id>11</exploring_volcano_id>
    </volcanologist>
 */