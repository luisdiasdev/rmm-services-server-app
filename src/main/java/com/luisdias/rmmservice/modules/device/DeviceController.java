package com.luisdias.rmmservice.modules.device;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/devices")
@Tag(name = "devices", description = "Manage customer devices")
public class DeviceController {
}
