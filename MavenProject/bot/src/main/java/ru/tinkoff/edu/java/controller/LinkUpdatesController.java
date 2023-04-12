package ru.tinkoff.edu.java.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.dto.LinkUpdaterRequest;

@RequestMapping("/updates")
@RestController
public class LinkUpdatesController {

    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")


    @Operation(summary = "Отправить обновление")
    @PostMapping(consumes = "application/json", produces = "application/json")
    String updateLink(@RequestBody @Valid LinkUpdaterRequest request){
        return  "some body ones told me...";
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(new ApiErrorResponse("Некорректные параметры запроса",
                e.getStatusCode().toString(),
                e.getObjectName(),
                e.getLocalizedMessage(),
                new String[]{String.valueOf(e.getStackTrace())}));
    }



}

