package helper;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import helper.*;
public class SendSMS {
   private final static String prefix = "http://www.smsbao.com/sms?";
   private final static String usrename = "archychu";
   private final static String password = "314159zlc";
   static Log log = LogFactory.getLog(SendSMS.class);

   public static int send(String to, String content) {
      String url = null;
      try {
         url = prefix+"u="+usrename+"&p="+Md5Util.getMD5Str(password)+"&m="+to+"&c="+java.net.URLEncoder.encode(content, "UTF-8");
      } catch (Exception e) {
         log.error("encode Error"+url);
         return -1;
      }
      log.info(url);
      int res = -1;
      try {
         final URL smsUrl = new URL(url);
         BufferedReader smsURLReader = new BufferedReader(new InputStreamReader(smsUrl.openStream()));
         String stockInputData = null;
         while ((stockInputData = smsURLReader.readLine()) != null) {
            if (stockInputData.equals("0")) {
               res = 1;
               break;
            }
         }
         smsURLReader.close();
      } catch (MalformedURLException me){
         me.printStackTrace();
      } catch(IOException ie){
         ie.printStackTrace();
      }
      return res;
   }
}