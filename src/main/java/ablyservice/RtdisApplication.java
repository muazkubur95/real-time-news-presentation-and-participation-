package ablyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RtdisApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RtdisApplication.class, args);
		RtdisService rtdisService = context.getBean(RtdisService.class);

		String ablyApiKey = "Hbqg4w.lNISjQ:o4-bjmkR5Rt9dnPrx7JI3AAtXopTh4lxkrPUDnrNOxY";
		String channelName = "[product:ably-tfl/tube]tube:940GZZLUWFN:arrivals";
		rtdisService.startAblySubscription(ablyApiKey, channelName);
	}
}
