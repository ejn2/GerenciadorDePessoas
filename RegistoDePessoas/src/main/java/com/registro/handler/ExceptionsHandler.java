package com.registro.handler;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.registro.errors.ApiNotFoundException;
import com.registro.errors.PadraoDeErros;


@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ApiNotFoundException.class})
	public ResponseEntity<?> handleSourceNotFoundException(ApiNotFoundException ex) {

		PadraoDeErros padrao = new PadraoDeErros();
		padrao.setTimestamp(LocalDateTime.now().toString());
		padrao.setStatus(HttpStatus.NOT_FOUND.value());
		padrao.setMessage(ex.getMessage());

		return new ResponseEntity<>(padrao, HttpStatus.NOT_FOUND);
		
	}


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		
		PadraoDeErros padrao = new PadraoDeErros();
		padrao.setTimestamp(LocalDateTime.now().toString());
		padrao.setStatus(HttpStatus.BAD_REQUEST.value());
		padrao.setMessage("Você deve informar apenas numeros inteiros");
		
	    return new ResponseEntity<>(padrao, HttpStatus.BAD_REQUEST);
	}



	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		pageNotFoundLogger.warn(ex.getMessage());

		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		
		if (!CollectionUtils.isEmpty(supportedMethods)) {
			headers.setAllow(supportedMethods);
		}
		
		PadraoDeErros padrao = new PadraoDeErros();
		
		padrao.setTimestamp(LocalDateTime.now().toString());
		padrao.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		padrao.setMessage("Metodo não suportado");
		
		return new ResponseEntity<>(padrao, HttpStatus.METHOD_NOT_ALLOWED);

	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		PadraoDeErros padrao = new PadraoDeErros();
		padrao.setTimestamp(LocalDateTime.now().toString());
		padrao.setStatus(HttpStatus.BAD_REQUEST.value());
		padrao.setMessage("Json mal formatado, identifique o erro e corrija-o");

		return new ResponseEntity<>(padrao, HttpStatus.BAD_REQUEST);
	}
}