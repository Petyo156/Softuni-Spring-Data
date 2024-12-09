package org.example.cardealer.services.impls;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.cardealer.persistence.entities.Supplier;
import org.example.cardealer.persistence.repositories.SupplierRepository;
import org.example.cardealer.services.SupplierService;
import org.example.cardealer.services.dtos.imports.SeedSuppliersDTO;
import org.example.cardealer.services.dtos.imports.SeedSuppliersInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String XML_IMPORT_SUPPLIERS = "src/main/resources/xmls/suppliers.xml";

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSuppliers() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SeedSuppliersDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SeedSuppliersDTO seedSuppliersDTO = (SeedSuppliersDTO) unmarshaller.unmarshal(new File(XML_IMPORT_SUPPLIERS));
        seedSuppliersDTO.getSuppliers().forEach(supplier -> {
            this.supplierRepository.saveAndFlush(this.modelMapper.map(supplier, Supplier.class));
        });
    }

    @Override
    public boolean isImported() {
        return this.supplierRepository.count() > 0;
    }
}
