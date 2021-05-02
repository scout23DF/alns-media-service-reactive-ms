package de.futurecompany.exceptions;

public class EntityNotFoundExistsException extends RuntimeException {

    public EntityNotFoundExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
