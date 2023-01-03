


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
 * ------------------------------- 发送消息 -------------------------------
 public class Test{
		@Autowired
		private  MessageServiceApi messageServiceApi;

		@GetMapping(value="/send")
		public R send() {
		MessageDTO msg=new MessageDTO("测试数据111");
		msg.setTopic("order-publish-test");
		messageServiceApi.asyncSend(msg);

		MessageDTO msg1=new MessageDTO("测试数据222");
		msg1.setTopic("order-publish-test");
		messageServiceApi.send(msg1);

		return R.data("ok");
		}
		}
**/

/**
 * ------------------------------- yaml配置 -------------------------------
 ysc:
	 mq:
		 type: rocketmq
		 address: localhost:9876
		 enable: true
		 group.id: ysc
 **/


