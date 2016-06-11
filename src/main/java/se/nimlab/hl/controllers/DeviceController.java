package se.nimlab.hl.controllers;

import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import se.nimlab.hl.contract.CreateDeviceDTO;
import se.nimlab.hl.contract.CreateReadingDTO;
import se.nimlab.hl.contract.DeviceDTO;
import se.nimlab.hl.contract.ReadingDTO;
import se.nimlab.hl.model.Device;
import se.nimlab.hl.model.Reading;
import se.nimlab.hl.repository.DeviceRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Api(basePath = "*",
        value = "Devices",
        description = "Handles devices")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
@RestController
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private HttpServletRequest context;

    @RequestMapping(value = "/devices", method = RequestMethod.POST, consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE}, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Registers a new device",
            notes = "Registers a new device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The device was registered successfully")
    })
    public DeviceDTO addDevice(
            @ApiParam(value = "The device to be registered", required = true)
            @RequestBody
            CreateDeviceDTO createDeviceDTO) {
        Device device = mapperFacade.map(createDeviceDTO, Device.class);
        device.setLastSeen(new Date());
        device.setCreated(device.getLastSeen());
        device.setLastExternalIp(context.getRemoteAddr());
        return mapperFacade.map(deviceRepository.save(device), DeviceDTO.class);
    }

    @RequestMapping(value = "/devices", method = RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetches all registered devices",
            notes = "Fetches all registered devices",
            responseContainer = "List",
            response = DeviceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If devices could be fetched successfully")
    })
    public Collection<DeviceDTO> getDevices() {
        List<DeviceDTO> results = new ArrayList<>();
        mapperFacade.mapAsCollection(deviceRepository.findAll(), results, DeviceDTO.class);
        return results;
    }
}
