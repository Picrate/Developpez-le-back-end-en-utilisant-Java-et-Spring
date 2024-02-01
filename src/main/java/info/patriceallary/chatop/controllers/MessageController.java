package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.MessageDto;
import info.patriceallary.chatop.domain.dto.ResponseDto;
import info.patriceallary.chatop.services.domain.MessageService;
import info.patriceallary.chatop.services.dto.DtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class MessageController {

    private final DtoService dtoService;
    private final MessageService messageService;

    public MessageController(DtoService dtoService, MessageService messageService) {
        this.dtoService = dtoService;
        this.messageService = messageService;
    }

    // Post new Message for a rental
    @PostMapping()
    @Operation(summary = "Send a message to a rental owner", description = "Post a message to a rental owner", security = {
            @SecurityRequirement(name = "bearerToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid datas submitted",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<ResponseDto> postNewMessage(@RequestBody MessageDto messageDto) {
        this.messageService.save(dtoService.convertToMessage(messageDto));
        return ResponseEntity.ok(new ResponseDto("Message sent !"));
    }

}
