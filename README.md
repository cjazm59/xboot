# xboot

#简介
xboot,基于spring框架体系,实现多种技术中间件适配
#

#### 核心组件库
 * x-message
 * x-lock
 * x-cache

### x-message
消息队列适配 <br>
可通过yaml参数配置指定消息组件 <br>
目的是为了屏蔽底层消息组件实现，做到应用层无感知切换消息组件
```



/**
 * ------------------------------- 发送消息 -------------------------------
 public class Test{
		@Autowired
		private  MessageServiceApi messageServiceApi;

		@GetMapping(value="/send")
		public R send() {
		MessageDTO msg=new MessageDTO();
		msg.setData("测试数据111")
		msg.setTopic("order-publish-test");
		messageServiceApi.asyncSend(msg);

		MessageDTO msg1=new MessageDTO();
		msg.setData("测试数据222")
		msg1.setTopic("order-publish-test");
		messageServiceApi.send(msg1);

		return R.data("ok");
		}
 }
**/

/**
 * ------------------------------- 消费端 -------------------------------
@MqConsumer(topic = "order-publish-test")
public class OrderProcess implements IMessageHandle {
	@Override
	public MqResult process(MessageDTO message) {
		System.out.println("OrderProcess 开始消费 ==>" + JsonUtil.toJson(message));
		return MqResult.succeed();
	}
}
**/


/**
 * ------------------------------- yaml配置 -------------------------------
 xboot:
     mq:
         type: rocketmq
         address: localhost:9876
         enable: true
         group.id: xboot
 **/
```
### x-lock
分布式锁适配
### x-cahche
缓存适配