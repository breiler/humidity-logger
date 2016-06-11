package se.nimlab.hl.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import se.nimlab.hl.contract.ReadingDTO;
import se.nimlab.hl.model.Reading;

@Component
public class BeanMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.classMap(Reading.class, ReadingDTO.class)
                .byDefault()
                .register();
    }
} 