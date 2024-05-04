package com.slavomirlobotka.dailyroutineforkids.email;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final SpringTemplateEngine templateEngine;

  private final String emailFrom = System.getenv("DAILY_ROUTINE_EMAIL_FROM");
  private final String apiKey = System.getenv("DAILY_ROUTINE_SENDGRID_API_KEY");
  private final String emailVerificationLink =
      System.getenv("DAILY_ROUTINE_EMAIL_CONFIRMATION_LINK");

  @Override
  public String sendEmail(String emailTo, String firstName, String digitToken) throws IOException {
    Email from = new Email(emailFrom);
    String subject = "Complete your registration with Daily Routine";
    Email to = new Email(emailTo);
    String htmlContent = buildEmailContent(firstName, emailTo, digitToken);
    Content content = new Content("text/html", htmlContent);
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid(apiKey);
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      return response.getBody();
    } catch (IOException ex) {
      System.out.println("Failed to send email to: " + emailTo);
      System.out.println(ex);
      throw ex;
    }
  }

  @Override
  public String buildEmailContent(String username, String email, String digitToken) {
    Context context = new Context();
    context.setVariable("username", username);
    context.setVariable("activation_code", digitToken);
    context.setVariable("confirmationUrl", buildVerificationLink(email));
    return templateEngine.process(EmailTemplateName.ACTIVATE_ACCOUNT.getName(), context);
  }

  @Override
  public String buildVerificationLink(String email) {
    return emailVerificationLink + email;
  }
}
