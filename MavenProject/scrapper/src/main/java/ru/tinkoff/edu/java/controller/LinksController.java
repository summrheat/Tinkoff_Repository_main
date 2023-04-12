package ru.tinkoff.edu.java.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.tinkoff.edu.java.dto.AddLinkRequest;
import ru.tinkoff.edu.java.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.dto.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@RequestMapping(value = "/links", consumes = "application/json", produces = "application/json")
@RestController
public class LinksController {

    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
    @ApiResponse(responseCode = "404", description = "Ресурс не найден")

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @GetMapping
    ListLinksResponse getAllLinks(@RequestHeader int tg_chat_id){
        return  new ListLinksResponse(6, null);
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @PostMapping
    AddLinkRequest addLink(@RequestHeader int tg_chat_id) throws URISyntaxException {
        return  new AddLinkRequest(new URI("https://springdoc.org/"));
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @DeleteMapping
    String deleteLink(@RequestHeader int tg_chat_id){
        return  "delete" + tg_chat_id;
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleException400(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(new ApiErrorResponse("Некорректные параметры запроса",
                e.getStatusCode().toString(),
                e.getObjectName(),
                e.getLocalizedMessage(),
                new String[]{Arrays.toString(e.getStackTrace())}));
    }



    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ApiErrorResponse> handleException404(HttpClientErrorException.NotFound e){
        return ResponseEntity.badRequest().body(new ApiErrorResponse("Ресурс не найден",
                e.getStatusCode().toString(),
                e.getStatusText(),
                e.getLocalizedMessage(),
                new String[]{String.valueOf(e.getStackTrace())}));
    }
}

