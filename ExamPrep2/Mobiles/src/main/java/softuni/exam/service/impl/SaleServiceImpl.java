package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SalesImportDTO;
import softuni.exam.models.dto.SellersImportDTO;
import softuni.exam.models.entity.Sale;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SaleRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SaleService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class SaleServiceImpl implements SaleService {
    private static final String FILE_PATH = "src/main/resources/files/json/sales.json";
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final SellerRepository sellerRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, SellerRepository sellerRepository) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public boolean areImported() {
        return this.saleRepository.count() > 0;
    }

    @Override
    public String readSalesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importSales() throws IOException {
        SalesImportDTO[] salesImportDTOS = gson.fromJson(readSalesFileContent(), SalesImportDTO[].class);

        StringBuilder sb = new StringBuilder();


        Arrays.stream(salesImportDTOS).forEach(c -> {
            if (!this.validationUtil.isValid(c) ||
                   this.saleRepository.findByNumber(c.getNumber()).isPresent()) {
                sb.append("Invalid sale").append(System.lineSeparator());
            } else {
                Sale mapped = modelMapper.map(c, Sale.class);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                mapped.setSaleDate(LocalDateTime.parse(c.getSaleDate(), formatter).toInstant(ZoneOffset.UTC));

                mapped.setSeller(sellerRepository.findById(c.getSeller()).get());
                saleRepository.saveAndFlush(mapped);
                sb.append(String.format("Successfully imported sale with number %s%n", mapped.getNumber()));
            }
        });

        return sb.toString();
    }
}
