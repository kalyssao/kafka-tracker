package services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusAndLocationService {

    @GetMapping("/locations/{userID}")
    public void getUserLocation(){

    }

    @GetMapping("/status/{userID}")
    public void getUserStatus(){

    }
}
