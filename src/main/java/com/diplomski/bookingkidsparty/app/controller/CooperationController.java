package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.CooperationResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.service.CooperationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("cooperations")
@Tag(name = "Cooperation Controller", description = "Manage cooperation between playroom, animators and kettering")
public class CooperationController {

    @Autowired
    CooperationService cooperationService;

    @PostMapping()
    @Operation(summary = "Added new cooperation",
            description = "Create new connection between playroom and kettering or animator",
            responses = {
                    @ApiResponse(responseCode = "204", description = "CooperationRequest added"),
                    @ApiResponse(responseCode = "400", description = "Illegal argument exception")
            })
    public ResponseEntity<?> add(@RequestBody CooperationRequestDTO cooperationDTOreq) {

        cooperationService.add(cooperationDTOreq);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @Operation(summary = "Get all cooperations by service provider id",
//	parameters = @Parameter(in = ParameterIn.PATH, name = "serviceProvicerId", required=true, description = "Service Provider identifier",
//	allowEmptyValue = false, schema = @Schema(type = "uuid", format = "uuid", description = "service Provider Id", accessMode = AccessMode.AUTO)),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return all cooperations by service provider id",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ServiceProviderOnePhotoResponseDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Illegal argument exception")
            })
    public ResponseEntity<List<ServiceProviderOnePhotoResponseDTO>> getAllByServiceProvider(
            @RequestParam(value = "serviceProviderId", required = true) UUID serviceProviderId) {
        List<ServiceProviderOnePhotoResponseDTO> services = cooperationService.findAllByServiceProvider(serviceProviderId);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{serviceProviderId}")
    public ResponseEntity<List<CooperationResponseDTO>> getAllByServiceProviderId(@PathVariable("serviceProviderId") UUID serviceProviderId) {
        List<CooperationResponseDTO> services = cooperationService.findAllByServiceProviderId(serviceProviderId);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(
            @RequestParam(value = "playroomId", required = true) UUID playroomId,
            @RequestParam(value = "cooperationServiceId", required = true) UUID cooperationServiceId) {
        return new ResponseEntity<>(cooperationService
                .delete(new CooperationRequestDTO(playroomId, cooperationServiceId)) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }
}
