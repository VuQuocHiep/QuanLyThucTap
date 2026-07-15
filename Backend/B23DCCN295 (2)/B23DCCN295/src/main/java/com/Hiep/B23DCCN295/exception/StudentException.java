package com.Hiep.B23DCCN295.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentException {

    // Lỗi validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlingValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getFieldError().getDefaultMessage());
    }

    // Không tìm thấy dữ liệu
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    // Trùng dữ liệu
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<String> handleDuplicate(DuplicateException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    // Sai kiểu dữ liệu JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJson(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
                .body("Dữ liệu gửi lên không hợp lệ.");
    }

    // Thiếu RequestParam
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParam(MissingServletRequestParameterException exception) {
        return ResponseEntity.badRequest()
                .body("Thiếu tham số: " + exception.getParameterName());
    }

    // Sai phương thức (GET gọi POST,...)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethod(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Phương thức HTTP không được hỗ trợ.");
    }

    // Lỗi ràng buộc Database (Unique, Foreign Key...)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDatabase(DataIntegrityViolationException exception) {
        return ResponseEntity.badRequest()
                .body("Lỗi dữ liệu trong cơ sở dữ liệu.");
    }

    // RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handler(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    // Bắt tất cả lỗi còn lại
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Đã xảy ra lỗi trong hệ thống.");
    }
}