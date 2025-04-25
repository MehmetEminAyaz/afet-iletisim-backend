package com.example.afetsunucu.controller;

import com.example.afetsunucu.dto.HelpMessageFulfillRequest;
import com.example.afetsunucu.dto.HelpMessageRequest;
import com.example.afetsunucu.dto.HelpMessageResponse;
import com.example.afetsunucu.dto.HelpMessageUpdateRequest;
import com.example.afetsunucu.service.HelpMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class HelpMessageController {

    private final HelpMessageService helpMessageService;

    @PostMapping
    public ResponseEntity<HelpMessageResponse> createMessage(
            @Valid @RequestBody HelpMessageRequest request) {
        return ResponseEntity.ok(helpMessageService.createHelpMessage(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<HelpMessageResponse>> getAllMessages(
            @RequestParam(required = false) Boolean fulfilled) {
        return ResponseEntity.ok(helpMessageService.getAllMessages(fulfilled));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<HelpMessageResponse>> getMyMessages() {
        return ResponseEntity.ok(helpMessageService.getMyMessages());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        helpMessageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HelpMessageResponse> updateMessage(
            @PathVariable Long id,
            @RequestBody HelpMessageUpdateRequest request) {
        return ResponseEntity.ok(helpMessageService.updateMessage(id, request));
    }


    @PatchMapping("/{id}/fulfill")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HelpMessageResponse> updateFulfilled(
            @PathVariable Long id,
            @RequestBody HelpMessageFulfillRequest request) {
        return ResponseEntity.ok(helpMessageService.updateFulfilled(id, request));
    }

}
