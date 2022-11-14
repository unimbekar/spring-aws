package dev.stratospheric;

import java.util.Collections;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

// import org.springframework.context.annotation.Primary;
// import com.amazonaws.services.s3.AmazonS3Client;
// import com.amazonaws.services.s3.AmazonS3ClientBuilder;
// import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

@Configuration
public class MessagingConfig {
  
  // @Bean
  // @Primary
  // public AmazonS3 s3Client() {
  //   return AmazonS3ClientBuilder.standard()                  
  //           .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
  //           .build();
  // }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
    return new QueueMessagingTemplate(amazonSQSAsync);
  }

  @Bean
  public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
    return new NotificationMessagingTemplate(amazonSNS);
  }

  @Bean
  public QueueMessageHandlerFactory queueMessageHandlerFactory() {
    QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
    MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setStrictContentTypeMatch(false);
    factory.setArgumentResolvers(Collections.singletonList(new PayloadMethodArgumentResolver(messageConverter)));
    return factory;
  }
}
