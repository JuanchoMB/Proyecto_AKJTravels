package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.dto.ChatMessageDTO;
import co.edu.uniquindio.application.dto.ResponseDTO;
import co.edu.uniquindio.application.model.Booking;
import co.edu.uniquindio.application.model.ChatMessage;
import co.edu.uniquindio.application.model.enums.BookingState;
import co.edu.uniquindio.application.model.enums.MessageStatus;
import co.edu.uniquindio.application.repositories.BookingRepository;
import co.edu.uniquindio.application.repositories.ChatMessageRepository;
import co.edu.uniquindio.application.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    // Aquí no hay petición HTTP, sino un frame STOMP enviado por el cliente por eso no se usa RequestBody
    @MessageMapping("/chat.send/{bookingId}")
    public ResponseEntity<ResponseDTO<String>> sendMessage(@DestinationVariable String bookingId, ChatMessageDTO message) throws Exception {
        chatMessageService.sendMessage(bookingId, message);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "Mensaje enviado exitosamente"));
    }
}