package com.neobis.g4g.girls_for_girls.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileEmptyException extends RuntimeException{
    private final String message;
}
