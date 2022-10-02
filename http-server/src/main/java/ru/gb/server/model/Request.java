package ru.gb.server.model;

import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@ToString
public class Request {

    private Path path;

    public Path of(String content, String[] parts) {
        path = Paths.get(content, parts[1]);
        return path;
    }

}
