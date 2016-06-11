package se.nimlab.hl.controllers;

import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import se.nimlab.hl.contract.CreateReadingDTO;
import se.nimlab.hl.contract.ReadingDTO;
import se.nimlab.hl.model.Reading;
import se.nimlab.hl.repository.ReadingRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Api(basePath = "*",
        value = "Readings",
        description = "Handles temperature and humidity readings")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
@RestController
public class ReadingController {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MapperFacade mapperFacade;

    @RequestMapping(value = "/readings", method = RequestMethod.POST, consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE}, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Registers a new sensor reading",
            notes = "Registers a new sensor reading")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The readings was registered successfully")
    })
    public ReadingDTO addReading(
            @ApiParam(value = "The readings to be registered", required = true)
            @RequestBody
            CreateReadingDTO readingDTO) {
        Reading reading = mapperFacade.map(readingDTO, Reading.class);
        return mapperFacade.map(readingRepository.save(reading), ReadingDTO.class);
    }

    @RequestMapping(value = "/readings", method = RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Registers a new sensor reading",
            notes = "Registers a new sensor reading")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The readings was registered successfully")
    })
    public Collection<ReadingDTO> getReadings() {
        List<ReadingDTO> results = new ArrayList<>();
        mapperFacade.mapAsCollection(readingRepository.findAll(), results, ReadingDTO.class);
        return results;
    }

}
