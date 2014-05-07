package helper;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail
{
   final static String from = "archy@0x271828.com";
   final static String host = "mail.0x271828.com";
   final static String username = "archy@0x271828.com";
   final static String password = "314159zlc";
   // final static String from = "zhulinchao7@gmail.com";
   // final static String host = "mail.gmail.com";
   // final static String username = "zhulinchao7@gmail.com";
   // final static String password = "vn@w8[v&a;A$Q~s6z0wb2=k)J";
   final static int port = 465;

   public static void send(String dst, String title, String content)
   {
      String to = dst; // "archy@zju.edu.cn";
      Properties props = System.getProperties();
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.ssl.enable", true);
      props.put("mail.smtp.port", port);
      props.put("mail.smtp.auth", true);

      // 获取默认的 Session 对象。
      Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
      });
      try {
         // 创建默认的 MimeMessage 对象。
         MimeMessage message = new MimeMessage(session);
         // Set From: 头部头字段
         message.setFrom(new InternetAddress(from));
         // Set To: 头部头字段
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         // Set Subject: 头字段
         message.setSubject("Esper-SRTP-EVENT-" + title);
         // 发送 HTML 消息, 可以插入html标签
         message.setContent("<h1>WaTch OUt</h1>"+content,
                            "text/html");
         // 发送消息
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}