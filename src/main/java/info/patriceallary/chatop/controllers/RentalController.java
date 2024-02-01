package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.*;
import info.patriceallary.chatop.services.domain.RentalService;
import info.patriceallary.chatop.services.dto.DtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/rentals")
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class RentalController {
    private final DtoService dtoService;
    private final RentalService rentalService;
    public RentalController(DtoService dtoService, RentalService rentalService)
    {
        this.dtoService = dtoService;
        this.rentalService = rentalService;
    }

    @GetMapping()
    @Operation(summary = "Show all rentals available", description = "Get all available rentals", security = {
            @SecurityRequirement(name = "bearerToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RentalListDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<RentalListDto> getAllRentals() {
        return ResponseEntity.ok(this.dtoService.getAllRentalDtos());
    }

    @GetMapping("/{requestedId}")
    @Operation(summary = "Get rental details", description = "Get rental details by submitting its identifier", security = {
            @SecurityRequirement(name = "bearerToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RentalDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Rental not found",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<RentalDto> getRentalById(@Valid @PathVariable Integer requestedId) {
        if(this.dtoService.getRentalDtoById(requestedId) == null)
        {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(this.dtoService.getRentalDtoById(requestedId));
        }
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Create a new Rental", description = "Post a new rental", security = {
            @SecurityRequirement(name = "bearerToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid datas submitted",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<ResponseDto> createNewRental(@ModelAttribute FormRentalDto formRentalDto, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        this.rentalService.save(this.dtoService.convertFormRentalToRental(formRentalDto, request.getRequestURL().toString()), currentPrincipalName);
        return ResponseEntity.ok(new ResponseDto("Rental Created !"));
    }

    @PutMapping(value = "/{requestedId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update an existing rental", description = "Update rental informations", security = {
            @SecurityRequirement(name = "bearerToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid datas submitted",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<ResponseDto> updateAnExistingRental(@PathVariable Integer requestedId, @ModelAttribute RentalDto rentalDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(this.rentalService.isOwner(requestedId, authentication.getName())) {
            this.rentalService.update(requestedId, this.dtoService.convertToRental(rentalDto));
            return ResponseEntity.ok(new ResponseDto("Rental Updated !"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto("you can't update a rent you don't own !"));
        }

    }


}
