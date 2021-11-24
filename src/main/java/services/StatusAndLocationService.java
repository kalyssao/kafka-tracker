package services;

import models.StatusAndLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.StatusAndLocationRepository;

import java.util.List;
import java.util.Optional;

// Returns the data we need visualized in the front end - history vs current?
@Service
public class StatusAndLocationService {

    @Autowired
    private StatusAndLocationRepository statusAndLocationRepository;

    // TO-DO - Get a map of user to location/latitude pairs?
    public List<StatusAndLocation> getAllLocations() {
        return statusAndLocationRepository.findAll();
    }

    // TO-DO - Return a longitude, latitude pair (do I need this really?)
    public void getUserLocation(Long id){
        Optional<StatusAndLocation> userStatusAndLocation = statusAndLocationRepository.findById(id);

        if (userStatusAndLocation.isPresent()) {
            // deconstruct to get location
        }

    }

    // TO-DO - Get a map of userID to status
    public void getUserStatus(){

    }
}
