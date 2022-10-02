package ru.gb.server.output;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.PrintWriter;

@RequiredArgsConstructor
@Getter
public class ServerOutput implements Output, AutoCloseable {

    private final PrintWriter printWriter;

    @Override
    public void output(Object o) {
        printWriter.println(o);
    }

    @Override
    public void close() {
        printWriter.close();
    }

}
