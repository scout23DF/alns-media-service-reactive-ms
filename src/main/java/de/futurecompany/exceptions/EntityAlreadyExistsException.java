package de.futurecompany.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
