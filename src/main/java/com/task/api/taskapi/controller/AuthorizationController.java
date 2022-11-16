package com.task.api.taskapi.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.task.api.taskapi.entity.AccountEntity;
import com.task.api.taskapi.repository.IAccountRepository;
import com.task.api.taskapi.service.IAccountsManagerService;
import com.task.api.taskapi.service.ITeamTaskManagerService;
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
import java.security.GeneralSecurityException;

import static org.springframework.http.HttpStatus.*;


@Controller
@RequestMapping("api/authorization")
public class AuthorizationController {
    @Autowired
    private IAccountsManagerService accountsManagerService;
    @Autowired
    private ITeamTaskManagerService teamTaskManagerService;
    @Autowired
    private IAccountRepository accountRepository;


    @PostMapping()
    public ResponseEntity<String> addAccount(@RequestBody @NotNull AccountEntity account) throws IOException {
        if (accountRepository.findByName(account.getName()).size() != 0)
            return ResponseEntity.status(BAD_REQUEST).body("Username already using");

        String link = accountsManagerService.getAuthLink(account.getName());

        if (link != null) {
            accountRepository.save(account);
            return ResponseEntity.ok(link);

        } else {
            return ResponseEntity.status(BAD_GATEWAY).body("error by get auth link");

        }
    }

    @ApiOperation(value = "Callback for auth")
    @GetMapping(path = "/callback/{userIdURL}/", params = {"code", "scope"})
    ResponseEntity callbackAuth(@RequestParam String code, @PathVariable String userIdURL) throws IOException, GeneralSecurityException {
        String userId = URLDecoder.decode(userIdURL, String.valueOf(StandardCharsets.UTF_8));
        GoogleTokenResponse token = accountsManagerService.getTokenByResponse(code, userId);

        accountsManagerService.addNewCredentials(token, userId);
        var accountEntityOptional = accountRepository.findByName(userId).stream().findFirst();

        if (accountEntityOptional.isPresent()) {
            var accountEntity = accountEntityOptional.get();
            accountEntity.setTaskListId(teamTaskManagerService.initTeamTasksListOfAccount(userId).getId());

            accountRepository.save(accountEntity);
        } else
            return ResponseEntity.ok("Inside error!");

        return ResponseEntity.ok("Auth is ok!");
    }
}
