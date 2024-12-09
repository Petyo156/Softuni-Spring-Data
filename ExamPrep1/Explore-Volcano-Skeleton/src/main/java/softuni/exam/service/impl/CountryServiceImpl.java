package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String COUNTRIES_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        CountrySeedDTO[] countrySeedDTOS = gson.fromJson(readCountriesFromFile(), CountrySeedDTO[].class);

        StringBuilder sb = new StringBuilder();

        Arrays.stream(countrySeedDTOS).forEach(c -> {
            if (!this.validationUtil.isValid(c) ||
                    this.countryRepository.findByName(c.getName()).isPresent()) {
                sb.append("Invalid country").append(System.lineSeparator());
            } else {
                Country mapped = modelMapper.map(c, Country.class);
                countryRepository.saveAndFlush(mapped);
                sb.append(String.format("Successfully imported country %s - %s%n", mapped.getName(), mapped.getCapital()));
            }
        });

        return sb.toString();
    }
}
