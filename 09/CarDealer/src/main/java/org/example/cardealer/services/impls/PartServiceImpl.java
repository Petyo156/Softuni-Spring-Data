package org.example.cardealer.services.impls;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.cardealer.persistence.entities.Part;
import org.example.cardealer.persistence.entities.Supplier;
import org.example.cardealer.persistence.repositories.PartRepository;
import org.example.cardealer.persistence.repositories.SupplierRepository;
import org.example.cardealer.services.PartService;
import org.example.cardealer.services.dtos.imports.SeedPartsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {

    private static final String path = "src/main/resources/xmls/parts.xml";

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, SupplierRepository supplierRepository) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedParts() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SeedPartsDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SeedPartsDTO seedPartsDTO = (SeedPartsDTO) unmarshaller.unmarshal(new File(path));
        seedPartsDTO.getParts().forEach(p -> {
            Part part = this.modelMapper.map(p, Part.class);
            part.setSupplier(getRandomSupplier());
            this.partRepository.saveAndFlush(part);
        });
    }

    private Supplier getRandomSupplier() {
        int random = ThreadLocalRandom.current().nextInt(1, (int) this.supplierRepository.count() + 1);
        return this.supplierRepository.findById(random).orElse(null);
    }

    @Override
    public boolean isImported() {
        return this.partRepository.count() > 0;
    }
}
