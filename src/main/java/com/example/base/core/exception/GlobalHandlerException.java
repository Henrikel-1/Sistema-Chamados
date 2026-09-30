package com.example.base.core.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @Override
    protected @org.jspecify.annotations.Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation Error");
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage() == null ? "valor inválido" : error.getDefaultMessage()))
                .toList();

        problem.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Usuário não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ProblemDetail> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("E-mail já cadastrado");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    // Segurança extra: dois cadastros iguais ao mesmo tempo (constraint unique do banco)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleIntegridade(DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Já existe um registro com esses dados");
        problem.setTitle("Conflito de dados");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    // Login com e-mail ou senha errados
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleCredenciaisInvalidas(BadCredentialsException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
                "E-mail ou senha inválidos");
        problem.setTitle("Credenciais inválidas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem);
    }
}