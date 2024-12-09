package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.DeviceImportDTO;
import softuni.exam.models.dto.xml.DevicesImportDTO;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.DeviceType;
import softuni.exam.repository.DeviceRepository;
import softuni.exam.repository.SaleRepository;
import softuni.exam.service.DeviceService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final String FILE_PATH = "src/main/resources/files/xml/devices.xml";
    private final DeviceRepository deviceRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final SaleRepository saleRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil, SaleRepository saleRepository) {
        this.deviceRepository = deviceRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.saleRepository = saleRepository;
    }

    @Override
    public boolean areImported() {
        return this.deviceRepository.count() > 0;
    }

    @Override
    public String readDevicesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importDevices() throws IOException, JAXBException {
        DevicesImportDTO devicesImportDTO = this.xmlParser.fromFile(FILE_PATH, DevicesImportDTO.class);

        StringBuilder sb = new StringBuilder();
        for (DeviceImportDTO deviceImportDTO : devicesImportDTO.getDeviceImportDTOList()) {
            if (!this.validationUtil.isValid(deviceImportDTO) ||
                    this.deviceRepository.findByModelAndBrand(
                            deviceImportDTO.getModel(), deviceImportDTO.getBrand()).isPresent() ||
                    this.saleRepository.findById(deviceImportDTO.getSale()).isEmpty()) {
                sb.append("Invalid device").append(System.lineSeparator());
                continue;
            }

            Device device = this.modelMapper.map(deviceImportDTO, Device.class);
            device.setSale(this.saleRepository.findById(deviceImportDTO.getSale()).get());

            this.deviceRepository.saveAndFlush(device);

            sb.append(String.format("Successfully imported device of type %s with brand %s%n",
                    device.getDeviceType().name(), device.getBrand()));
        }

        return sb.toString();
    }

    @Override
    public String exportDevices() {
        List<Device> devices = deviceRepository.findAllByDeviceTypeAndPriceLessThanAndStorageIsGreaterThanEqualOrderByBrandAsc(DeviceType.SMART_PHONE, 1000.00, 128);
        StringBuilder sb = new StringBuilder();
        for (Device device : devices) {
            sb.append(String.format("Device brand: %s%n", device.getBrand()))
                    .append(String.format("   *Model: %s%n", device.getModel()))
                    .append(String.format("   **Storage: %d%n", device.getStorage()));

            String end = String.format("%.2f", device.getPrice());
            end = end.replaceAll(",", ".");

            sb.append(String.format("   ***Price: %s%n", end));

        }
        return sb.toString();
    }
}