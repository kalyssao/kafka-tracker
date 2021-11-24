package repository;

import models.StatusAndLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusAndLocationRepository extends JpaRepository {
    List<StatusAndLocation> findAll();

    Optional<StatusAndLocation> findById(Long id);

}
