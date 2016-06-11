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
import se.nimlab.hl.contract.CreateDeviceDTO;
import se.nimlab.hl.contract.DeviceDTO;
import se.nimlab.hl.contract.UpdateDeviceDTO;
import se.nimlab.hl.model.Device;
import se.nimlab.hl.repository.DeviceRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Api(basePath = "*",
        value = "Devices",
        description = "Handles devices")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
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

    @RequestMapping(value = "/devices", method = RequestMethod.PUT, consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE}, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Updates a device",
            notes = "Updates a  device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The device was updated successfully"),
            @ApiResponse(code = 401, message = "If wrong secret key was given")
    })
    public ResponseEntity updateDevice(
            @ApiParam(value = "The device to be updated", required = true)
            @RequestBody
            UpdateDeviceDTO updateDeviceDTO) {
        Device device = deviceRepository.findOne(updateDeviceDTO.getId());
        if (device == null || !StringUtils.equals(device.getSecretKey(), updateDeviceDTO.getSecretKey())) {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        device.setLastSeen(new Date());
        device.setLastExternalIp(context.getRemoteAddr());
        device.setLastInternalIp(updateDeviceDTO.getLastInternalIp());
        return ResponseEntity.ok(mapperFacade.map(deviceRepository.save(device), DeviceDTO.class));
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
