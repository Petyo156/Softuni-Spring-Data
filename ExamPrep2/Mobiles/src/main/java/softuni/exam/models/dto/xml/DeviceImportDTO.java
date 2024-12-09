package softuni.exam.models.dto.xml;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entity.DeviceType;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "device")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceImportDTO {
    @XmlElement
    @Length(min = 2, max = 20)
    private String brand;
    @XmlElement(name = "device_type")
    private DeviceType deviceType;
    @XmlElement
    @Length(min = 1, max = 20)
    private String model;
    @XmlElement
    @Min(0)
    private Double price;
    @XmlElement
    private Integer storage;
    @XmlElement(name = "sale_id")
    private Long sale;

    public DeviceImportDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public @Min(0) Double getPrice() {
        return price;
    }

    public void setPrice(@Min(0) Double price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Long getSale() {
        return sale;
    }

    public void setSale(Long sale) {
        this.sale = sale;
    }
}
