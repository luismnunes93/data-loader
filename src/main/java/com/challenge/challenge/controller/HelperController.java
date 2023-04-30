package com.challenge.challenge.controller;

import com.challenge.challenge.service.ImportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/helper")
@RequiredArgsConstructor
public class HelperController {

    private final ImportDataService importDataService;

    @PostMapping("/import-all-data")
    public ResponseEntity<Void> importAllData(){
        try{
            importDataService.importAllData();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem loading initial data");
        }
    }

    @PostMapping("/import-zones")
    public ResponseEntity<Void> importZones(){
        try{
            importDataService.importZones();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem loading initial data");
        }
    }

    @PostMapping("/import-green-trip")
    public ResponseEntity<Void> importGreenTrip(){
        try{
            importDataService.importGreenTrip();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem loading initial data");
        }
    }

    @PostMapping("/import-yellow-trip")
    public ResponseEntity<Void> importYellowTrip(){
        try{
            importDataService.importYellowTrip();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem loading initial data");
        }
    }
}
