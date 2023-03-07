package utils;

import com.hyperionoj.web.HyperionOJWebApplication;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;


@SpringBootTest(classes = HyperionOJWebApplication.class)
@RunWith(SpringRunner.class)
public class KafkaTest {

    private static final String KAFKA_TEST_TOPIC = "kafka_test_topic";

    private static final Logger log = LoggerFactory.getLogger(KafkaTest.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = {KAFKA_TEST_TOPIC})
    public void kafkaResult(ConsumerRecord<?, ?> record) {
        log.info("get a message topic: " + record.topic() + " and value: " + record.value());
    }

    public void sendKafka() {
        kafkaTemplate.send(KAFKA_TEST_TOPIC, "kafka_test_message");
        log.info("message send success !");
    }

}
