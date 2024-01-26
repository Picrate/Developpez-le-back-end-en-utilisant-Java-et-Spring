package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.MessageDto;
import info.patriceallary.chatop.domain.dto.RentalDto;
import info.patriceallary.chatop.domain.dto.ResponseDto;
import info.patriceallary.chatop.services.DtoService;
import info.patriceallary.chatop.services.RentalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Iterable<RentalDto>> getAllRentals() {
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

    @PostMapping()
    public ResponseEntity<ResponseDto> createNewRental(@ModelAttribute RentalDto rentalDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        this.rentalService.save(this.dtoService.convertToRental(rentalDto), currentPrincipalName);
        return ResponseEntity.ok(new ResponseDto("Rental Created !"));
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<ResponseDto> updateAnExistingRental(@PathVariable Integer requestedId, @ModelAttribute RentalDto rentalDto) {
        rentalDto.setId(requestedId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        this.rentalService.save(this.dtoService.convertToRental(rentalDto), currentPrincipalName);
        return ResponseEntity.ok(new ResponseDto("Rental Updated !"));
    }


}
