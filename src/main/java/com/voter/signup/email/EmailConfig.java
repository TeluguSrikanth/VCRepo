package com.voter.signup.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	
	@Value("${spring.mail.host}")
	  private String smtpHost;

	  @Value("${spring.mail.port}")
	  private String smtpPort;

	  @Value("${spring.mail.username}")
	  private String emailUsername;

	  @Value("${spring.mail.password}")
	  private String emailPassword;
	  

	  @Bean
	  public JavaMailSender getJavaMailSender() {
		  JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
		  javaMailSender.setHost(smtpHost);
		  javaMailSender.setPort(Integer.parseInt(smtpPort));
		  javaMailSender.setUsername(emailUsername); 
		  javaMailSender.setPassword(emailPassword);
		  
		  Properties props = javaMailSender.getJavaMailProperties();
		  //props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "true"); // Enable debug mode for troubleshooting

		  
		  
		  return javaMailSender;
		  
	  }
	  

	
}
