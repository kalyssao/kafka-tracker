package repository;

import models.StatusAndLocation;

import java.util.List;
import java.util.Optional;

public interface StatusAndLocationRepository {
    List<StatusAndLocation> findAll();

    Optional<StatusAndLocation> findById(Long id);

}
