package sofuni.exam.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.json.DiscoverersImportDTO;
import sofuni.exam.models.entity.Discoverer;
import sofuni.exam.repository.DiscovererRepository;
import sofuni.exam.service.DiscovererService;
import sofuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class DiscovererServiceImpl implements DiscovererService {
    private static final String PATH = "src/main/resources/files/json/discoverers.json";

    private final DiscovererRepository discovererRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public DiscovererServiceImpl(DiscovererRepository discovererRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.discovererRepository = discovererRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.discovererRepository.count() > 0;
    }

    @Override
    public String readDiscovererFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importDiscoverers() throws IOException {
        DiscoverersImportDTO[] discoverersImportDTOS = gson.fromJson(readDiscovererFileContent(), DiscoverersImportDTO[].class);

        StringBuilder sb = new StringBuilder();

        Arrays.stream(discoverersImportDTOS).forEach(c -> {
            if (!this.validationUtil.isValid(c) ||
                    this.discovererRepository.getDiscovererByFirstNameAndLastName(c.getFirstName(), c.getLastName()).isPresent()){
                sb.append("Invalid discoverer").append(System.lineSeparator());
            } else {
                Discoverer mapped = modelMapper.map(c, Discoverer.class);
                discovererRepository.saveAndFlush(mapped);
                sb.append(String.format("Successfully imported discoverer %s %s",
                        mapped.getFirstName(), mapped.getLastName())).append(System.lineSeparator());
            }
        });

        return sb.toString().trim();
    }
}
