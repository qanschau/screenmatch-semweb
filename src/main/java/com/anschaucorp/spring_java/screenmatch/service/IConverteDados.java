package com.anschaucorp.spring_java.screenmatch.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> tClass);

}
