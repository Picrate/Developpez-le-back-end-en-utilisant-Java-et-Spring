package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Optional<Rental> getRentalById(Integer id) {
        return this.rentalRepository.findById(id);
    }

    public List<Rental> getAllReantals() {
        return this.rentalRepository.findAll();
    }

    public void save(Rental rental) {
        if (this.getRentalById(rental.getId()).isPresent()) {
            rental.setUpdated_at(Timestamp.valueOf(LocalDate.now().atStartOfDay()));

        }
        this.rentalRepository.save(rental);
    }


}
