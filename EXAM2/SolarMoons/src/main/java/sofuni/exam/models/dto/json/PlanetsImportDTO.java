package sofuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import sofuni.exam.models.enums.Type;

public class PlanetsImportDTO {
    @Expose
    @Length(min = 3, max = 20)
    @NotNull
    private String name;
    @Expose
    @NotNull
    @Positive
    private Integer diameter;
    @Expose
    @NotNull
    private Long distanceFromSun;
    @Expose
    @NotNull
    private double orbitalPeriod;
    @Expose
    @NotNull
    private Type type;

    public PlanetsImportDTO() {
    }

    public @Length(min = 3, max = 20) @NotNull String getName() {
        return name;
    }

    public void setName(@Length(min = 3, max = 20) @NotNull String name) {
        this.name = name;
    }

    public @NotNull Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(@NotNull Integer diameter) {
        this.diameter = diameter;
    }

    public @NotNull Long getDistanceFromSun() {
        return distanceFromSun;
    }

    public void setDistanceFromSun(@NotNull Long distanceFromSun) {
        this.distanceFromSun = distanceFromSun;
    }

    @NotNull
    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(@NotNull double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public @NotNull Type getType() {
        return type;
    }

    public void setType(@NotNull Type type) {
        this.type = type;
    }
}
