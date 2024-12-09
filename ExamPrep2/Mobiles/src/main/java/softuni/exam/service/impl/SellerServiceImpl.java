package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellersImportDTO;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class SellerServiceImpl implements SellerService {
    private static final String FILE_PATH = "src/main/resources/files/json/sellers.json";
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException {
        SellersImportDTO[] sellersImportDTOS = gson.fromJson(readSellersFromFile(), SellersImportDTO[].class);

        StringBuilder sb = new StringBuilder();

        Arrays.stream(sellersImportDTOS).forEach(c -> {
            if (!this.validationUtil.isValid(c) ||
                sellerRepository.findByLastName(c.getLastName()).isPresent()) {
                sb.append("Invalid seller").append(System.lineSeparator());
            } else {
                Seller mapped = modelMapper.map(c, Seller.class);
                sellerRepository.saveAndFlush(mapped);
                sb.append(String.format("Successfully imported seller %s %s%n", mapped.getFirstName(), mapped.getLastName()));
            }
        });

        return sb.toString();
    }
}
