package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanologistSeedDTO;
import softuni.exam.models.dto.VolcanologistsSeedDTO;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {
    public static final String VOLCANOLOGISTS_FILE_PATH = "src/main/resources/files/xml/volcanologists.xml";

    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoRepository volcanoRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, VolcanoRepository volcanoRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(VOLCANOLOGISTS_FILE_PATH));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        VolcanologistsSeedDTO volcanologistsSeedDTO = this.xmlParser.fromFile(VOLCANOLOGISTS_FILE_PATH, VolcanologistsSeedDTO.class);

        StringBuilder sb = new StringBuilder();
        for(VolcanologistSeedDTO volcanologistSeedDTO : volcanologistsSeedDTO.getVolcanologistSeedDTOSet()){
            if(!this.validationUtil.isValid(volcanologistSeedDTO) ||
                    !this.volcanologistRepository.findByFirstNameAndLastName(volcanologistSeedDTO.getFirstName(), volcanologistSeedDTO.getLastName()).isEmpty() ||
                this.volcanoRepository.findById(volcanologistSeedDTO.getExploringVolcano()).isEmpty()){
                sb.append("Invalid volcanologist").append(System.lineSeparator());
                continue;
            }

            Volcanologist volcanologist = this.modelMapper.map(volcanologistSeedDTO, Volcanologist.class);
            volcanologist.setVolcano(this.volcanoRepository.findById(volcanologistSeedDTO.getExploringVolcano()).get());
            this.volcanologistRepository.saveAndFlush(volcanologist);
            sb.append(String.format("Successfully imported volcanologist %s %s%n",
                    volcanologist.getFirstName(), volcanologist.getLastName()));
        }

        return sb.toString();
    }
}
/*
volcanologistsSeedDTO.getVolcanologistSeedDTOSet().forEach(
                v -> {
                    if (!this.validationUtil.isValid(v) ||
                            this.volcanologistRepository.findByFirstName(
                                    v.getFirstName()).isPresent() ||
                            this.volcanologistRepository.findByLastName(
                                    v.getLastName()).isPresent()) {
                        sb.append("Invalid volcanologist").append(System.lineSeparator());
                    } else {
                        Volcanologist mapped = this.modelMapper.map(v, Volcanologist.class);

                        Volcano volcano = this.volcanoRepository.findById((long) v.getExploringVolcano()).get();
                        mapped.setVolcano(volcano);

                        this.volcanologistRepository.saveAndFlush(mapped);
                        sb.append(String.format("Successfully imported volcanologist %s %s%n",
                                mapped.getFirstName(), mapped.getLastName()));
                    }

                }
        );
 */