package ru.tinkoff.edu.java.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.dao.JdbcChatService;
import ru.tinkoff.edu.dto.ApiErrorResponse;

import java.sql.SQLException;

@RequestMapping(value = "/tg-chat/{id}", consumes = "application/json", produces = "application/json")
@RestController
public class TgChatController {



    @Operation(summary = "Зарегестрировать чат")
    @PostMapping
    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
    String addChat(@PathVariable("id") int id){
        try {
            new JdbcChatService().addChat(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  "add" + id;
    }

    @Operation(summary = "Удалить чат")
    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
    @DeleteMapping
    String deleteChat(@PathVariable("id") int id){
        try {
            new JdbcChatService().deleteChat(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  "delete" + id;
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

