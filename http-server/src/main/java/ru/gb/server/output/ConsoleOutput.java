package ru.gb.server.output;

public class ConsoleOutput implements Output {
    @Override
    public void output(Object o) {
        System.out.println(o);
    }

}
