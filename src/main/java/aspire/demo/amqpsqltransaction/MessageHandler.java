package aspire.demo.amqpsqltransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Created by andy.lv
 * on: 2019/3/13 15:08
 */
@Service
@Slf4j
public class MessageHandler {

    public void handleMessage(Message<String> message) {
        log.info("handled message: {}" + message.getPayload());
    }
}
