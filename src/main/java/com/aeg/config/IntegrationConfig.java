package com.aeg.config;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.sftp.Sftp;
import org.springframework.integration.file.remote.gateway.AbstractRemoteFileOutboundGateway;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import java.io.File;


@EnableIntegration
//@IntegrationComponentScan
@Configuration
public class IntegrationConfig {


    /*public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(IntegrationConfig.class, args);
        MessageChannel ftpChannel = (MessageChannel)ctx.getBean("ftpChannel");
        File file = new File("C:/szucs.txt");
        Message<File> message = MessageBuilder.withPayload(file).build();
        ftpChannel.send(message);

    }*/
    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
        //Resource resource = new FileSystemResource(sftpProperties.privateKeyLocation);
        return new CachingSessionFactory(crSftpServer());
    }

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> ftpSessionFactory() {
        //Resource resource = new FileSystemResource(sftpProperties.privateKeyLocation);
        return new CachingSessionFactory(crSftpServer());
    }

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> crSftpServer() {
        //Resource resource = new FileSystemResource(sftpProperties.privateKeyLocation);
        DefaultSftpSessionFactory sftpSessionFactory = new DefaultSftpSessionFactory();
        sftpSessionFactory.setHost("vault.clearesult.com");
        sftpSessionFactory.setPort(22);
        sftpSessionFactory.setUser("bszucs");
        sftpSessionFactory.setPassword("Z00xMu$1c");
        //sftpSessionFactory.setPrivateKey(resource);
        return sftpSessionFactory;
    }
    @Bean
    public MessageChannel ftpChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outboundFlow() {
        return IntegrationFlows
                .from(ftpChannel())
                .handle(Sftp.outboundAdapter(this.sftpSessionFactory())
                        .remoteFileSeparator("\\")
                        .useTemporaryFileName(false)
                        .remoteDirectory("ToICF")).get();
    }


    @Bean
    public IntegrationFlow controlBus() {
        return IntegrationFlowDefinition::<Void>controlBus;
    }

    @Bean
    public PollableChannel remoteFileOutputChannel() {
        return new QueueChannel();
    }
    @Bean
    public IntegrationFlow sftpInboundFlow() {
        return IntegrationFlows
                .from(s -> s.sftp(this.crSftpServer())
                                .preserveTimestamp(true)
                                .remoteDirectory("HVAC - Files For Testing")
                                .regexFilter(".*\\.*$")
                                .localDirectory(new File("classpath:local-dir")),
                        e -> e.id("sftpInboundAdapter").autoStartup(false))
                .channel(MessageChannels.queue("sftpInboundResultChannel"))
                .get();
    }


    @Bean
    public IntegrationFlow sftpMGetFlow() {
        return IntegrationFlows.from("sftpMgetInputChannel")
                .handleWithAdapter(h ->
                        h.sftpGateway(this.crSftpServer(),
                                AbstractRemoteFileOutboundGateway.Command.MGET,
                                "payload")
                                    .options(AbstractRemoteFileOutboundGateway.Option.RECURSIVE)
                                    .regexFileNameFilter(".*")
                                    .localDirectoryExpression("@sftpServer.targetLocalDirectoryName + #remoteDirectory"))
                .channel(remoteFileOutputChannel())
                .get();
    }

    /*@Bean
    public IntegrationFlow outboundFlow() {
        return IntegrationFlows.from(uploadChannel())
                .handle(Sftp.outboundAdapter(ftpSessionFactory(), FileExistsMode.REPLACE))
                        .charset(Charset.forName("UTF-8"))
                        .remoteFileSeparator("\\")
                        .remoteDirectory("ToICF")
                        .fileNameExpression("payload.getName()")
                        .autoCreateDirectory(true)
                        .useTemporaryFileName(true)
                        .temporaryFileSuffix(".tranferring")
                        .fileNameExpression("fileNameGenerator")
                )
                .channel(uploadChannel())
                .get();
    }


    @Bean
    public MessageChannel ftpChannel() {
        ChannelSftp handler = new ChannelSftp();
        FileTransferringMessageHandler handler = new FileTransferringMessageHandler(this.sftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression("/ToICF"));
        return handler;
    }*/

    /*@Bean
    @InboundChannelAdapter(value = "ftpChannel", poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
    public MessageChannel sftpInbound() {
        SftpInboundFileSynchronizingMessageSource source = null;
        SftpInboundFileSynchronizer sftpInboundFileSynchronizer = null;
        File localDirectory = new File("file:output");


        try {
            sftpInboundFileSynchronizer = new SftpInboundFileSynchronizer(sftpSessionFactory());
            sftpInboundFileSynchronizer.setFilter(new SftpSimplePatternFileListFilter(""));
            sftpInboundFileSynchronizer.setRemoteDirectory("ToICF");
            sftpInboundFileSynchronizer.setPreserveTimestamp(Boolean.TRUE);
            sftpInboundFileSynchronizer.synchronizeToLocalDirectory(localDirectory);
            sftpInboundFileSynchronizer.setDeleteRemoteFiles(Boolean.FALSE);
            sftpInboundFileSynchronizer.setTemporaryFileSuffix(".copy");
            source = new SftpInboundFileSynchronizingMessageSource(sftpInboundFileSynchronizer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }*/
    /*@Bean
    @ServiceActivator(inputChannel = "sftpChannel")
    public MessageHandler sftpOutboundGateway(SessionFactory<ChannelSftp.LsEntry> sessionFactory) {
        return new SftpOutboundGateway(sessionFactory,
                (session, requestMessage) -> session.list(requestMessage.getPayload()));
    }
    @MessagingGateway(name = "gatewayLS", defaultRequestChannel = "inbound",
            defaultHeaders = @GatewayHeader(name = "calledMethod",
                    expression="#gatewayMethod.name"))
    public interface ToSftpFlowGateway {

        public List<Boolean> lsGetAndRmFiles(String dir);
    }*/


}
