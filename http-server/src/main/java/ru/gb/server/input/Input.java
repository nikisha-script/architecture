package ru.gb.server.input;

import java.io.IOException;

public interface Input {

    String askStr() throws IOException;

    Boolean getReady() throws IOException;

}
