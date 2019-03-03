package aspire.demo.amqpsqltransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class IndexController {

    private final MessageChannel output;
    private final AmqpSqlTxRepository amqpSqlTxRepository;

    public IndexController(MessageChannel output
                           , AmqpSqlTxRepository amqpSqlTxRepository
    ) {
        this.output = output;
        this.amqpSqlTxRepository = amqpSqlTxRepository;
    }

    @GetMapping("/send")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void send() {
        String message = UUID.randomUUID().toString();
        log.info("sending message:{}" + message);
        this.output.send(MessageBuilder.withPayload(message).build());
        AmqpSqlTx amqpSqlTx = new AmqpSqlTx();
        amqpSqlTx.setId(message);
        amqpSqlTxRepository.save(amqpSqlTx);
//        throw new RuntimeException("Not Sending Test");
    }
}
