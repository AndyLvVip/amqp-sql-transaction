package aspire.demo.amqpsqltransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ReceiverService {

    private final AmqpSqlTxRepository amqpSqlTxRepository;

    public ReceiverService(AmqpSqlTxRepository amqpSqlTxRepository) {
        this.amqpSqlTxRepository = amqpSqlTxRepository;
    }

    @StreamListener(Processor.INPUT)
    @Transactional
    public void receive(String message) {
        log.info("receive message:{}" + message);
        AmqpSqlTx amqpSqlTx = amqpSqlTxRepository.findById(message).get();
        amqpSqlTxRepository.delete(amqpSqlTx);
        throw new RuntimeException("Not receive test");
    }
}
