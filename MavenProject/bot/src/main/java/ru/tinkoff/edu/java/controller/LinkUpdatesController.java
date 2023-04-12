package ru.tinkoff.edu.java.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.dto.ErrorResponse;
import ru.tinkoff.edu.java.dto.UpdateLinkRequest;

@RequestMapping("/updates")
@RestController
public class LinkUpdatesController {

    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")


    @Operation(summary = "Отправить обновление")
    @PostMapping(consumes = "application/json", produces = "application/json")
    String updateLink(@RequestBody @Valid UpdateLinkRequest request){
        return  "send update";
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(new ErrorResponse("Некорректные параметры запроса",
                e.getStatusCode().toString(),
                e.getObjectName(),
                e.getLocalizedMessage(),
                new String[]{String.valueOf(e.getStackTrace())}));
    }



}

