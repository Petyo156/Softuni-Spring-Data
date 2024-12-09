package org.example.cardealer.services.impls;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.cardealer.persistence.entities.Customer;
import org.example.cardealer.persistence.repositories.CustomerRepository;
import org.example.cardealer.services.CustomerService;
import org.example.cardealer.services.dtos.exports.ExportCustromerDTO;
import org.example.cardealer.services.dtos.exports.ExportCustomersDTO;
import org.example.cardealer.services.dtos.imports.SeedCustomersDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String SEED_PATH = "src/main/resources/xmls/customers.xml";
    private static final String EXPORT_ORDERED_CUSTOMERS_PATH = "src/main/resources/xmls/exports/ordered-customers.xml";

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCustomers() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SeedCustomersDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SeedCustomersDTO seedCustomersDTO = (SeedCustomersDTO) unmarshaller.unmarshal(new File(SEED_PATH));
        seedCustomersDTO.getCustomers().forEach(s -> {
            try {
                Customer customer = this.modelMapper.map(s, Customer.class);
                this.customerRepository.saveAndFlush(customer);
            } catch (Exception e) {
                System.err.println("Error saving customer: " + s + ". Cause: " + e.getMessage());
            }});
    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        Set<ExportCustromerDTO> collected = customerRepository.findAllByOrderByBirthDateAscIsYoungDriverDesc()
                .stream()
                .map(s -> this.modelMapper.map(s, ExportCustromerDTO.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        ExportCustomersDTO exportCustomersDTO = new ExportCustomersDTO(collected);

        JAXBContext jaxbContext = JAXBContext.newInstance(ExportCustomersDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(exportCustomersDTO, new File(EXPORT_ORDERED_CUSTOMERS_PATH));
    }

    @Override
    public boolean isImported() {
        return this.customerRepository.count() > 0;
    }
}
