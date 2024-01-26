package info.patriceallary.chatop.repository;

import info.patriceallary.chatop.domain.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    //public Rental findRentalById(Integer id);

}
