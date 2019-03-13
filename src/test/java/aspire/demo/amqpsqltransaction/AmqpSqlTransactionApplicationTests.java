package aspire.demo.amqpsqltransaction;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesMessageThat;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;
import static org.springframework.integration.test.matcher.PayloadAndHeaderMatcher.sameExceptIgnorableHeaders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqpSqlTransactionApplicationTests {

	@Autowired
	Processor processor;

	@Autowired
	Sink channels;

	@Autowired
	MessageCollector collector;

	@SpyBean
	MessageHandler messageHandler;

	@Test
	public void payload() {
		BlockingQueue<Message<?>> messages = collector.forChannel(processor.output());

		Message<String> msg1 = new GenericMessage<>(UUID.randomUUID().toString());
		Message<String> msg2 = new GenericMessage<>(UUID.randomUUID().toString());

		processor.output().send(msg1);
		processor.output().send(msg2);

		Assert.assertThat(messages, receivesPayloadThat(is(msg1.getPayload())));
		Assert.assertThat(messages, receivesMessageThat((Matcher<Message<Object>>) (Matcher<?>) sameExceptIgnorableHeaders(msg2)));
	}




	@Test
	public void receive() {
		Message<String> msg1 = new GenericMessage<>(UUID.randomUUID().toString());

		this.channels.input().send(msg1);
		ArgumentCaptor<Message<?>> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
		verify(messageHandler, times(1)).handleMessage((Message<String>) messageArgumentCaptor.capture());
		assertThat(messageArgumentCaptor.getValue()).hasFieldOrPropertyWithValue("payload", msg1.getPayload());

	}

}
