package com.example.twilio.email.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@RestController
public class EmailController {

	@PostMapping(value = "/mail/send")
	@Scheduled(cron = "* */2 * * * ?")
	public ResponseEntity<String> sendEmail() throws IOException {

		Email from = new Email();
		from.setEmail("<SEND_FROM_ADDRESS>");
		from.setName("<SEND_FROM_NAME>");

		Email to = new Email();
		to.setEmail("<SEND_TO_ADDRESS>");
		to.setName("<SEND_TO_NAME>");

		String subject = "Schedule Email From Twilio Using SendGrid, Java, and Spring Boot";
		Content content = new Content("text/html",
				"<em>Scheduled Email</em> sent using <strong>Java, Spring Boot, and Twilio</strong>");

		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		Request request = new Request();

		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());

		Response response = sg.api(request);

		System.out.println(response.getStatusCode());
		System.out.println(response.getHeaders());
		System.out.println(response.getBody());

		return new ResponseEntity<String>("Email sent successfully.", HttpStatus.OK);

	}
}
