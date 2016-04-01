package com.aeg.ims.ftp;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.UnknownHostException;

import org.junit.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

/**
 * @author Gary Russell
 * @author Amol Nayak
 *
 */
public class FtpOutboundChannelAdapterTest {

    @Test
    public void runDemo() throws Exception{
        ConfigurableApplicationContext ctx =
                new ClassPathXmlApplicationContext("META-INF/spring/integration/DynamicFtpOutboundChannelAdapterSample-context.xml");
        MessageChannel channel = ctx.getBean("toDynRouter", MessageChannel.class);
        File file = File.createTempFile("temp", "txt");
        Message<File> message = MessageBuilder.withPayload(file)
                .setHeader("customer", "cust1")
                .build();
        try {
            channel.send(message);
        }
        catch (MessagingException e) {
            assertThat(e.getCause().getCause().getCause(), instanceOf(UnknownHostException.class));
            assertTrue(e.getCause().getCause().getCause().getMessage().startsWith("host.for.cust1"));
        }
        // send another so we can see in the log we don't create the ac again.
        try {
            channel.send(message);
        }
        catch (MessagingException e) {
            assertThat(e.getCause().getCause().getCause(), instanceOf(UnknownHostException.class));
            assertTrue(e.getCause().getCause().getCause().getMessage().startsWith("host.for.cust1"));
        }
        // send to a different customer; again, check the log to see a new ac is built
        message = MessageBuilder.withPayload(file)
                .setHeader("customer", "cust2").build();
        try {
            channel.send(message);
        }
        catch (MessagingException e) {
            assertThat(e.getCause().getCause().getCause(), instanceOf(UnknownHostException.class));
            assertTrue(e.getCause().getCause().getCause().getMessage().startsWith("host.for.cust2"));
        }

        // send to a different customer; again, check the log to see a new ac is built
        //and the first one created (cust1) should be closed and removed as per the max cache size restriction
        message = MessageBuilder.withPayload(file)
                .setHeader("customer", "cust3").build();
        try {
            channel.send(message);
        }
        catch (MessagingException e) {
            assertThat(e.getCause().getCause().getCause(), instanceOf(UnknownHostException.class));
            assertTrue(e.getCause().getCause().getCause().getMessage().startsWith("host.for.cust3"));
        }

        //send to cust1 again, since this one has been invalidated before, we should
        //see a new ac created (with ac of cust2 destroyed and removed)
        message = MessageBuilder.withPayload(file)
                .setHeader("customer", "cust1").build();
        try {
            channel.send(message);
        }
        catch (MessagingException e) {
            assertThat(e.getCause().getCause().getCause(), instanceOf(UnknownHostException.class));
            assertEquals("host.for.cust1", e.getCause().getCause().getCause().getMessage());
        }

        ctx.close();
    }
}