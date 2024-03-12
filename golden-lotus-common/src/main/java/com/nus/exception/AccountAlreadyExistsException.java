package com.nus.exception;

public class AccountAlreadyExistsException extends BaseException{
    public AccountAlreadyExistsException(){

    }

    public AccountAlreadyExistsException(String msg){
        super(msg);
    }
}
