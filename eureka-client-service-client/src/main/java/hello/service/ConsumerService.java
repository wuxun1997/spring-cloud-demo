package hello.service;

public interface ConsumerService {
    String call(String name);

    String backupCall(String name);
}
