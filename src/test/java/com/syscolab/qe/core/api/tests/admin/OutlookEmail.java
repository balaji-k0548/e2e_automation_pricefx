package com.syscolab.qe.core.api.tests.admin;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.ContentTypes;
import org.apache.http.HttpHeaders;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.HashMap;
import java.util.Properties;

public class OutlookEmail {

    public OutlookEmail() throws Exception {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imaps.partialfetch", "false");
        props.put("mail.mime.base64.ignoreerrors", "true");

        Session mailSession = Session.getInstance(props);
        mailSession.setDebug(true);
        Store store = mailSession.getStore("imap");
//        store.connect("outlook.office365.com", "tqeautomation@outlook.com", "9ijnBGT5");
        store.connect("outlook.office365.com", "sper1321@corp.sysco.com", "");


        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        System.out.println("Total Message:" + folder.getMessageCount());
        System.out.println("Unread Message:" + folder.getUnreadMessageCount());

        Message[] messages = folder.getMessages();

        for (Message mail : messages) {



            System.out.println("*********************************");
            System.out.println("MESSAGE : \n");

            System.out.println("Subject: " + mail.getSubject());
            System.out.println("From: " + mail.getFrom()[0]);
            System.out.println("To: " + mail.getAllRecipients()[0]);
            System.out.println("Date: " + mail.getReceivedDate());
//            System.out.println("Size: " + mail.getSize());
//            System.out.println("Flags: " + mail.getFlags());
//            System.out.println("ContentType: " + mail.getContentType());
            System.out.println("Body: \n" + mail.getContent().toString());
            System.out.println("*******************************");

        }
    }

    public static void api(){
        RestUtil.API_HOST = "https://graph.microsoft.com/v1.0/me/mailfolders/inbox/messages?$select=subject,from,receivedDateTime&$top=25&$orderby=receivedDateTime%20DESC";
        RestUtil.BASE_PATH = "";
        RestUtil.send(getBearerHeaders(""),null,"","GET");
//
//        RestUtil.API_HOST = "https://graph.microsoft.com/";
//        RestUtil.BASE_PATH = "v1.0/me/messages";
//        RestUtil.send(getBearerHeaders(""),null,"","GET");


//        GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();
//
//        MessageCollectionPage messages = graphClient.me().messages()
//                .buildRequest()
//                .get();
    }

    public static void main(String args[]) throws Exception {
//        api();
        new OutlookEmail();
    }

    public static HashMap<String, String> getBearerHeaders(String accessToken){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
        headers.put(HttpHeaders.AUTHORIZATION, ContentTypes.BEARER+accessToken);
        return headers;
    }
}
