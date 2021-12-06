package com.fortunebill.springboot.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot04TaskApplicationTests {

    @Autowired
    JavaMailSenderImpl javaMailSender;


    // 测试简单的邮件发送
    @Test
    public void testMail1() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("测试邮件");
        mailMessage.setText("这是一封通过代码发送的测试邮件");
        mailMessage.setTo("as283798272@163.com");
        mailMessage.setFrom("283798272@qq.com");
        javaMailSender.send(mailMessage);
    }

    // 测试复杂消息邮件发送
    @Test
    public void testMail2() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // multipart 是否上传附件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // 邮件设置
        helper.setSubject("通知开会");
        helper.setText("<b style='color:red'>今晚7：30 准时开会</b>", true);
        helper.setTo("as283798272@163.com");
        helper.setFrom("283798272@qq.com");
        // 附件
        helper.addAttachment("Docker 常用命令.md", new File("C:\\Users\\Kavin\\Desktop\\Docker 常用命令.md"));
        helper.addAttachment("Docker镜像.md", new File("C:\\Users\\Kavin\\Desktop\\Docker镜像.md"));
        javaMailSender.send(mimeMessage);
    }


}
