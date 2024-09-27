package com.jony.rabbitmq.abandon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 消息队列提供者
 *
 * @Author jony
 * @Date 2024/9/25
 */
// @RestController
public class RabbitMqController {

    private final RabbitMQSend send;

    // @Autowired
    public RabbitMqController(RabbitMQSend send) {
        this.send = send;
    }

    @Operation(summary = "简单模式发送json消息", description = "简单模式发送json消息")
    @Parameter(name = "json", description = "消息json", required = true, schema = @Schema(defaultValue = "application/json"))
    @GetMapping("/simpleSend")
    public String simpleSend(@RequestParam(value = "json", required = false) String json) {
        try {
            this.send.simpleSend(json);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Operation(summary = "订阅模式发送json消息", description = "订阅模式发送json消息")
    @Parameter(name = "json", description = "消息json", required = true, schema = @Schema(defaultValue = "application/json"))
    @GetMapping("/routeSend")
    public String routeSend(@RequestParam(value = "json", required = false) String json) {
        try {
            this.send.routeSend(json);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Operation(summary = "路由模式发送json消息", description = "路由模式发送json消息")
    @Parameter(name = "json", description = "消息json", required = true, schema = @Schema(defaultValue = "application/json"))
    @Parameter(name = "routingKey", description = "key", required = true, schema = @Schema(defaultValue = "application/json"))
    @GetMapping("/routingSend")
    public String routingSend(@RequestParam(value = "json", required = false) String json, @RequestParam(value = "routingKey", required = false) String routingKey) {
        try {
            this.send.routingSend(routingKey, json);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Operation(summary = "主题模式发送json消息", description = "主题模式发送json消息")
    @Parameter(name = "json", description = "消息json", required = true, schema = @Schema(defaultValue = "application/json"))
    @Parameter(name = "routingKey", description = "key", required = true, schema = @Schema(defaultValue = "application/json"))
    @GetMapping("/topicSend")
    public String topicSend(@RequestParam(value = "json", required = false) String json, @RequestParam(value = "routingKey", required = false) String routingKey) {
        try {
            this.send.topicSend(routingKey, json);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Operation(summary = "死信模式发送json消息", description = "用于处理定时任务,如订单超时未支付自动取消")
    @Parameter(name = "json", description = "消息json", required = true, schema = @Schema(defaultValue = "application/json"))
    @Parameter(name = "routingKey", description = "key", required = true, schema = @Schema(defaultValue = "application/json"))
    @GetMapping("/beadSend")
    public String beadSend(@RequestParam(value = "json", required = false) String json, @RequestParam(value = "routingKey", required = false) String routingKey) {
        try {
            MessageProperties messageProperties = new MessageProperties();
            // 设置消息过期时间,这里设置的时间是10分钟
            messageProperties.setExpiration(600 + "000");
            Message message = new Message(json.getBytes(), messageProperties);
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            // 这里的key应该传死信队列绑定死信交换机的路由key,这里我们传key1
            this.send.beadSend("routing_key1", message);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
