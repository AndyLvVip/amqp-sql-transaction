package aspire.demo.amqpsqltransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ReceiverService {


    private final MessageHandler messageHandler;


    public ReceiverService(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @StreamListener(Processor.INPUT)
    @Transactional
    public void receive(Message<String> message) {
        messageHandler.handleMessage(message);
    }
}
