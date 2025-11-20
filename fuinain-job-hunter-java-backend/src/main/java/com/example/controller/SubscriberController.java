package com.example.controller;

import com.example.domain.Subscriber;
import com.example.service.SubscriberService;
import com.example.util.annotation.ApiMessage;
import com.example.util.error.IdInvalidException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    @ApiMessage("Create a new subscriber")
    public ResponseEntity<Subscriber> create(@Valid @RequestBody Subscriber subscriber) throws IdInvalidException {
        boolean isExist = subscriberService.isExistsByEmail(subscriber.getEmail());
        if (isExist == true)
            throw new IdInvalidException("Email " + subscriber.getEmail() + " đã được đăng ký trước đó.");

        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.create(subscriber));
    }

    @PutMapping("/subscribers")
    @ApiMessage("Update a subscriber")
    public ResponseEntity<Subscriber> update(@RequestBody Subscriber subscriber) throws IdInvalidException {
        //check id
        Subscriber subsDB = this.subscriberService.findById(subscriber.getId());
        if (subsDB == null)
            throw new IdInvalidException("Subscriber với id " + subscriber.getId() + " không tồn tại.");

        return ResponseEntity.ok().body(subscriberService.update(subsDB, subscriber));
    }
}
