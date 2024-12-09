package sofuni.exam.models.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "moons")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoonsImportDTO {
    @XmlElement(name = "moon")
    private List<MoonImportDTO> moons;

    public MoonsImportDTO() {
    }

    public List<MoonImportDTO> getMoons() {
        return moons;
    }

    public void setMoons(List<MoonImportDTO> moons) {
        this.moons = moons;
    }
}
