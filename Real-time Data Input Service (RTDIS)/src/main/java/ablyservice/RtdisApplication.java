package ablyservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RtdisApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RtdisApplication.class, args);
		RtdisService rtdisService = context.getBean(RtdisService.class);
		String channelName = "[product:ably-bbc/news]home";
		rtdisService.startAblySubscription(channelName);
	}
}
