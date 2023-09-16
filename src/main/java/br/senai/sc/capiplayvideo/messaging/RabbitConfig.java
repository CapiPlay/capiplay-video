package br.senai.sc.capiplayvideo.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

//    private final ConnectionFactory connectionFactory;
//
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange("video-service");
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(jsonMessageConverter());
//        return template;
//    }
//
//    @Bean
//    Queue queueVideoSalvo() {
//        return new Queue("videos.v1.video-salvo.engajamento");
//    }
//
//    @Bean
//    Queue queueVideoAtualizado() {
//        return new Queue("videos.v1.video-atualizado.video");
//    }
//
//    @Bean
//    Queue queueUsuarioSalvo() {
//        return new Queue("usuarios.v1.usuario-salvo.video");
//    }
//
//    @Bean
//    Queue queueAnonimoSalvo() {
//        return new Queue("usuarios.v1.anonimo-salvo.video");
//    }
//
//    @Bean
//    Binding bindingVideoSalvo(Queue queueVideoSalvo, TopicExchange exchange) {
//        return BindingBuilder.bind(queueVideoSalvo).to(exchange).with("VideoSalvoEvent");
//    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}
