package sofuni.exam.service.Impl;

import jakarta.validation.Validator;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.xml.MoonImportDTO;
import sofuni.exam.models.dto.xml.MoonsImportDTO;
import sofuni.exam.models.entity.Discoverer;
import sofuni.exam.models.entity.Moon;
import sofuni.exam.models.entity.Planet;
import sofuni.exam.models.enums.Type;
import sofuni.exam.repository.DiscovererRepository;
import sofuni.exam.repository.MoonRepository;
import sofuni.exam.repository.PlanetRepository;
import sofuni.exam.service.MoonService;
import sofuni.exam.util.ValidationUtil;
import sofuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MoonServiceImpl implements MoonService {
    private static final String PATH = "src/main/resources/files/xml/moons.xml";

    private final MoonRepository moonRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final DiscovererRepository discovererRepository;
    private final PlanetRepository planetRepository;

    public MoonServiceImpl(MoonRepository moonRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil, DiscovererRepository discovererRepository, PlanetRepository planetRepository) {
        this.moonRepository = moonRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.discovererRepository = discovererRepository;
        this.planetRepository = planetRepository;
    }

    @Override
    public boolean areImported() {
        return this.moonRepository.count() > 0;
    }

    @Override
    public String readMoonsFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importMoons() throws IOException, JAXBException {
        MoonsImportDTO moonsImportDTO = this.xmlParser.fromFile(PATH, MoonsImportDTO.class);

        StringBuilder sb = new StringBuilder();
        for (MoonImportDTO moonImportDTO : moonsImportDTO.getMoons()) {
            if (!this.validationUtil.isValid(moonImportDTO) ||
                    this.moonRepository.findMoonByName(moonImportDTO.getName()).isPresent()) {
                sb.append("Invalid moon").append(System.lineSeparator());
                continue;
            }

            Moon moon = this.modelMapper.map(moonImportDTO, Moon.class);
            moon.setDiscovered(LocalDate.parse(moonImportDTO.getDiscovered(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            Discoverer discoverer = discovererRepository.findById(moonImportDTO.getDiscovererId()).get();
            moon.setDiscoverer(discoverer);

            Planet planet = planetRepository.findById(moonImportDTO.getPlanetId()).get();
            moon.setPlanet(planet);

            this.moonRepository.save(moon);

            sb.append(String.format("Successfully imported moon %s%n",
                    moon.getName()));
        }

        return sb.toString();
    }

    @Override
    public String exportMoons() {
        List<Moon> allByRadiusBetween = moonRepository.findAllByRadiusBetweenAndPlanet_TypeOrderByName(700, 2000, Type.GAS_GIANT);
        StringBuilder sb = new StringBuilder();
        for (Moon moon : allByRadiusBetween) {
            String format = String.format("***Moon %s is a natural satellite of %s and has a radius of %.2f km.",
                    moon.getName(), moon.getPlanet().getName(), moon.getRadius());
            String s = format.replaceAll(",", ".");
            sb.append(s).append(System.lineSeparator());
                sb.append(String.format("****Discovered by %s %s%n", moon.getDiscoverer().getFirstName(), moon.getDiscoverer().getLastName()))
                        .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
