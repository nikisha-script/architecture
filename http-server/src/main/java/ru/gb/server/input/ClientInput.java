package ru.gb.server.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;

@Getter
@RequiredArgsConstructor
public class ClientInput implements Input, AutoCloseable {

    private final BufferedReader input;

    @Override
    public String askStr() throws IOException {
        return input.readLine();
    }

    @Override
    public Boolean getReady() throws IOException {
        return input.ready();
    }

    @Override
    public void close() throws IOException {
        input.close();
    }

}
