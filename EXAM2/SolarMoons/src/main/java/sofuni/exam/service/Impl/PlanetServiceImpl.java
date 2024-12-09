package sofuni.exam.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.json.PlanetsImportDTO;
import sofuni.exam.models.entity.Planet;
import sofuni.exam.repository.PlanetRepository;
import sofuni.exam.service.PlanetService;
import sofuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PlanetServiceImpl implements PlanetService {
    private static final String PATH = "src/main/resources/files/json/planets.json";

    private final PlanetRepository planetRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PlanetServiceImpl(PlanetRepository planetRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.planetRepository = planetRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.planetRepository.count() > 0;
    }

    @Override
    public String readPlanetsFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importPlanets() throws IOException {
        PlanetsImportDTO[] planetsImportDTOS = gson.fromJson(readPlanetsFileContent(), PlanetsImportDTO[].class);

        StringBuilder sb = new StringBuilder();


        Arrays.stream(planetsImportDTOS).forEach(c -> {
            if (!this.validationUtil.isValid(c) ||
                    this.planetRepository.getPlanetByName(c.getName()).isPresent()){
                sb.append("Invalid planet").append(System.lineSeparator());
            } else {
                Planet mapped = modelMapper.map(c, Planet.class);
                planetRepository.saveAndFlush(mapped);
                sb.append(String.format("Successfully imported planet %s", mapped.getName())).append(System.lineSeparator());
            }
        });

        return sb.toString().trim();
    }
}
