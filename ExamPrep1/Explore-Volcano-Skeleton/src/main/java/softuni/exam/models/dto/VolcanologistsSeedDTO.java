package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistsSeedDTO {
    @XmlElement(name = "volcanologist")
    private List<VolcanologistSeedDTO> volcanologistSeedDTOSet;

    public VolcanologistsSeedDTO() {
        this.volcanologistSeedDTOSet = new ArrayList<>();
    }

    public List<VolcanologistSeedDTO> getVolcanologistSeedDTOSet() {
        return volcanologistSeedDTOSet;
    }

    public void setVolcanologistSeedDTOSet(List<VolcanologistSeedDTO> volcanologistSeedDTOSet) {
        this.volcanologistSeedDTOSet = volcanologistSeedDTOSet;
    }
}
