package ru.gb.server;

import lombok.extern.slf4j.Slf4j;
import ru.gb.server.input.ClientInput;
import ru.gb.server.model.Request;
import ru.gb.server.model.Response;
import ru.gb.server.output.ConsoleOutput;
import ru.gb.server.output.Output;
import ru.gb.server.output.ServerOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class HttpServer {

    private final int poolThreads = Runtime.getRuntime().availableProcessors();
    private final String nextLine = System.lineSeparator();
    private final ExecutorService pool = Executors.newFixedThreadPool(poolThreads);
    private final String path = "D:\\java\\gb\\java\\architecture\\http-server\\src\\main\\resources\\";
    private final Output consoleOutput = new ConsoleOutput();
    private final Request request = new Request();
    private Response response;

    public void init() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            consoleOutput.output("Server started!");
            log.info("Server started!");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                consoleOutput.output("connected to the server");
                 pool.submit(() -> {
                     try (ClientInput input = new ClientInput(
                             new BufferedReader(
                                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)));
                     ServerOutput output = new ServerOutput(new PrintWriter(socket.getOutputStream()))) {

                         String firstLine = input.askStr();
                         String[] parts = firstLine.split(" ");
                         consoleOutput.output(firstLine);
                         while (input.getReady()) {
                             consoleOutput.output(input.askStr());
                         }

                         Path pathRsl = request.of(path, parts);
                         if (!Files.exists(request.getPath())) {
                             this.response = new Response("NOT_FOUND", "404");
                             output.output("HTTP/1.1 " + response.getStatus()  + response.getText());
                             output.output("Content-Type: text/html; charset=utf-8");
                             output.output(nextLine);
                             output.output("<h1>Файл не найден!</h1>");
                             output.getPrintWriter().flush();
                         } else {
                             this.response = new Response("OK", "200");
                             output.output("HTTP/1.1 " + response.getStatus()  + response.getText());
                             output.output("Content-Type: text/html; charset=utf-8");
                             output.output(nextLine);
                             Files.newBufferedReader(pathRsl).transferTo(output.getPrintWriter());
                             consoleOutput.output("Client disconnected!");
                             log.info("Client disconnected!");
                         }
                     } catch (IOException e) {
                         log.error("IOException", e);
                     }
                });
            }
        } catch (IOException e) {
            log.error("IOException", e);
        }

    }

}
