package se.nimlab.hl.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class BeanMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        /*factory.classMap(Reading.class, ReadingDTO.class)
                .byDefault()
                .register();

        factory.classMap(Device.class, DeviceDTO.class)
                .byDefault()
                .register();

        factory.classMap(Device.class, CreateDeviceDTO.class)
                .byDefault()
                .register();*/
    }
} 