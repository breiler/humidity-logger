package se.nimlab.hl.controllers;

import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import se.nimlab.hl.contract.CreateReadingDTO;
import se.nimlab.hl.contract.ReadingDTO;
import se.nimlab.hl.model.Device;
import se.nimlab.hl.model.Reading;
import se.nimlab.hl.repository.DeviceRepository;
import se.nimlab.hl.repository.ReadingRepository;

import java.util.ArrayList;
import java.util.Date;
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
    private DeviceRepository deviceRepository;

    @Autowired
    private MapperFacade mapperFacade;

    @RequestMapping(value = "/devices/{deviceId}/readings", method = RequestMethod.POST, consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE}, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Registers a new sensor reading",
            notes = "Registers a new sensor reading")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The readings was registered successfully")
    })
    public ResponseEntity addReading(
            @ApiParam(value = "The device to register the readings to", required = true)
            @PathVariable(value = "deviceId")
            Long deviceId,
            @ApiParam(value = "The readings to be registered", required = true)
            @RequestBody
            CreateReadingDTO readingDTO) {

        Device device = deviceRepository.findOne(deviceId);
        if (device == null || !StringUtils.equals(device.getSecretKey(), readingDTO.getSecretKey())) {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        Reading reading = mapperFacade.map(readingDTO, Reading.class);
        reading.setDeviceId(deviceId);
        reading.setCreated(new Date());
        return ResponseEntity.ok(mapperFacade.map(readingRepository.save(reading), ReadingDTO.class));
    }

    @RequestMapping(value = "/devices/{deviceId}/readings", method = RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Registers a new sensor reading",
            notes = "Registers a new sensor reading",
            responseContainer = "List",
            response = ReadingDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The registered readings")
    })
    public List<ReadingDTO> getReadings(
            @ApiParam(value = "The device to register the readings to", required = true)
            @PathVariable(value = "deviceId")
            Long deviceId) {
        List<ReadingDTO> results = new ArrayList<>();
        mapperFacade.mapAsCollection(readingRepository.findByDeviceId(deviceId), results, ReadingDTO.class);
        return results;
    }

}
