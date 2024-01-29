package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;

    public RentalService(RentalRepository rentalRepository, UserService userService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
    }

    public Optional<Rental> getRentalById(Integer id) {
        return this.rentalRepository.findById(id);
    }

    public List<Rental> getAllReantals() {
        return this.rentalRepository.findAll();
    }

    public void save(Rental rental, String principalName) {
        // Rental Already exists -> update
        if (rental.getId() != null) {
            rental.setUpdated_at(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        }
        // Get Owner
        if (this.userService.getUserByEmail(principalName).isPresent()) {
            User owner = this.userService.getUserByEmail(principalName).get();
            rental.setOwner(owner);
        }
        this.rentalRepository.saveAndFlush(rental);
    }


}
