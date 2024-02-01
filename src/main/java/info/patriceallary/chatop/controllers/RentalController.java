package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.FormRentalDto;
import info.patriceallary.chatop.domain.dto.RentalDto;
import info.patriceallary.chatop.domain.dto.RentalListDto;
import info.patriceallary.chatop.domain.dto.ResponseDto;
import info.patriceallary.chatop.services.domain.RentalService;
import info.patriceallary.chatop.services.dto.DtoService;
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
public class RentalController {
    private final DtoService dtoService;
    private final RentalService rentalService;
    public RentalController(DtoService dtoService, RentalService rentalService)
    {
        this.dtoService = dtoService;
        this.rentalService = rentalService;
    }

    @GetMapping()
    public ResponseEntity<RentalListDto> getAllRentals() {
        return ResponseEntity.ok(this.dtoService.getAllRentalDtos());
    }

    @GetMapping("/{requestedId}")
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
    public ResponseEntity<ResponseDto> createNewRental(@ModelAttribute FormRentalDto formRentalDto, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        this.rentalService.save(this.dtoService.convertFormRentalToRental(formRentalDto, request.getRequestURL().toString()), currentPrincipalName);
        return ResponseEntity.ok(new ResponseDto("Rental Created !"));
    }

    @PutMapping(value = "/{requestedId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
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
