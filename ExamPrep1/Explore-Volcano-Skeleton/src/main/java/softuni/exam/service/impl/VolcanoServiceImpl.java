package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoSeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.enums.VolcanoType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VolcanoServiceImpl implements VolcanoService {
    private static final String VOLCANOES_FILE_PATH = "src/main/resources/files/json/volcanoes.json";

    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANOES_FILE_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        VolcanoSeedDTO[] volcanoSeedDTOS = gson.fromJson(readVolcanoesFileContent(), VolcanoSeedDTO[].class);

        StringBuilder sb = new StringBuilder();

        for (VolcanoSeedDTO volcanoSeedDTO : volcanoSeedDTOS) {
            if(this.volcanoRepository.findVolcanoByName(volcanoSeedDTO.getName()).isPresent() ||
            !this.validationUtil.isValid(volcanoSeedDTO)) {
                sb.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }

            Volcano volcano = modelMapper.map(volcanoSeedDTO, Volcano.class);
            volcano.setVolcanoType(VolcanoType.valueOf(volcanoSeedDTO.getVolcanoType()));
            volcano.setCountry(this.countryRepository.findById( (long) volcanoSeedDTO.getCountry()).get());
            this.volcanoRepository.save(volcano);

            sb.append(String.format("Successfully imported volcano %s of type %s", volcano.getName(), volcano.getVolcanoType().toString()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }


    @Override
    public String exportVolcanoes() {
        StringBuilder sb = new StringBuilder();
        List<Volcano> volcanoes = volcanoRepository.findAllByElevationGreaterThanAndIsActiveAndLastEruptionIsNotNullOrderByElevationDesc(3000, true);

        for (Volcano volcano : volcanoes) {
            sb.append(String.format("Volcano: %s", volcano.getName())).append(System.lineSeparator());
            sb.append(String.format("   *Located in: %s", volcano.getCountry().getName())).append(System.lineSeparator());
            sb.append(String.format("   **Elevation: %d", volcano.getElevation())).append(System.lineSeparator());
            sb.append(String.format("   ***Last eruption on: %s", volcano.getLastEruption())).append(System.lineSeparator());
        }
        return sb.toString();
    }
}