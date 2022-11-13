package com.task.api.taskapi.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.task.api.taskapi.entity.AccountEntity;
import com.task.api.taskapi.repository.IAccountRepository;
import com.task.api.taskapi.service.IAccountsManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@Controller
@RequestMapping("api/authorization")
public class AuthorizationController {
    @Autowired
    private IAccountsManagerService accountsManagerService;

    @Autowired
    private IAccountRepository accountRepository;

    @PostMapping()
    public ResponseEntity<String> addAccount(@RequestBody @NotNull AccountEntity account) throws IOException {
        String link = accountsManagerService.getAuthLink(account.getName());
        /*
        Tasks service = new Tasks.Builder(HTTP_TRANSPORT, JSON_FACTORY, )
                .setApplicationName(TaskApiApplication.getApplicationName())
                .build();
        */

        if (link != null) {
            accountRepository.save(account);
            return ResponseEntity.ok(link);

        }
        else {
            return ResponseEntity.ok("error");

        }
    }

    @ApiOperation(value = "Callback for auth")
    @GetMapping(path = "/callback/{userIdURL}/", params = {"code", "scope"})
    ResponseEntity callbackAuth(@RequestParam String code, @PathVariable String userIdURL) throws IOException {
        String userId = URLDecoder.decode(userIdURL, String.valueOf(StandardCharsets.UTF_8));
        GoogleTokenResponse token = accountsManagerService.getTokenResponse(code, userId);

        accountsManagerService.addNewCredentials(token, userId);

        return ResponseEntity.ok("Auth is ok!");
    }
}
