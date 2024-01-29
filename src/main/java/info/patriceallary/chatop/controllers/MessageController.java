package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.MessageDto;
import info.patriceallary.chatop.domain.dto.ResponseDto;
import info.patriceallary.chatop.services.domain.MessageService;
import info.patriceallary.chatop.services.dto.DtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final DtoService dtoService;
    private final MessageService messageService;

    public MessageController(DtoService dtoService, MessageService messageService) {
        this.dtoService = dtoService;
        this.messageService = messageService;
    }

    // Post new Message for a rental
    @PostMapping("/")
    public ResponseEntity<ResponseDto> postNewMessage(@RequestBody MessageDto messageDto) {
        this.messageService.save(dtoService.convertToMessage(messageDto));
        return ResponseEntity.ok(new ResponseDto("Message sent !"));
    }

}
