package org.example.cardealer.services.impls;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.cardealer.persistence.entities.Car;
import org.example.cardealer.persistence.entities.Part;
import org.example.cardealer.persistence.repositories.CarRepository;
import org.example.cardealer.persistence.repositories.PartRepository;
import org.example.cardealer.services.CarService;
import org.example.cardealer.services.dtos.exports.ExportCarDTO;
import org.example.cardealer.services.dtos.exports.ExportCarsDTO;
import org.example.cardealer.services.dtos.exports.ExportCustomersDTO;
import org.example.cardealer.services.dtos.imports.SeedCarsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static String SEED_CARS = "src/main/resources/xmls/cars.xml";
    private static String EXPORT_ORDERED_CARS_PATH = "src/main/resources/xmls/exports/ordered-cars.xml";

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, PartRepository partRepository) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
    }

    @Override
    public void seedCars() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SeedCarsDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SeedCarsDTO seedCarsDTO = (SeedCarsDTO) unmarshaller.unmarshal(new File(SEED_CARS));
        seedCarsDTO.getCars().forEach(s -> {
            Car car = this.modelMapper.map(s, Car.class);
            car.setParts(getRandomParts());
            this.carRepository.saveAndFlush(car);
        });
    }

    @Override
    public void exportOrderedCars() throws JAXBException {
        LinkedHashSet<ExportCarDTO> collected = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(s -> this.modelMapper.map(s, ExportCarDTO.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        ExportCarsDTO exportCarsDTO = new ExportCarsDTO(collected);

        JAXBContext jaxbContext = JAXBContext.newInstance(ExportCarsDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(exportCarsDTO, new File(EXPORT_ORDERED_CARS_PATH));

    }

    private Set<Part> getRandomParts() {
        HashSet<Part> parts = new HashSet<>();
        int num = ThreadLocalRandom.current().nextInt(3, 6);
        for (int i = 0; i < num; i++) {
            parts.add(this.partRepository.
                    findById(ThreadLocalRandom.current()
                            .nextInt(1, (int) this.partRepository.count() + 1))
                    .orElse(null));
        }
        return parts;
    }

    @Override
    public boolean isImported() {
        return this.carRepository.count() > 0;
    }
}
