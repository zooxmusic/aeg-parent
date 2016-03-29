package com.aeg.ims.config;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import java.io.File;

@Configuration
public class SftpConfig {

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
        //Resource resource = new FileSystemResource(sftpProperties.privateKeyLocation);
        DefaultSftpSessionFactory sftpSessionFactory = new DefaultSftpSessionFactory();
        sftpSessionFactory.setHost("sftp.ameresco.com");
        sftpSessionFactory.setPort(22);
        sftpSessionFactory.setUser("sftp_a037_g30");
        //sftpSessionFactory.setPrivateKey(resource);
        return sftpSessionFactory;
    }

    @Bean
    @InboundChannelAdapter(value = "sftpChannel", poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
    public MessageSource<File> sftp() {
        SftpInboundFileSynchronizingMessageSource source = null;
        SftpInboundFileSynchronizer sftpInboundFileSynchronizer = null;
        File localDirectory = new File("C:/newddownloads");

        try {
            sftpInboundFileSynchronizer = new SftpInboundFileSynchronizer(sftpSessionFactory());
            sftpInboundFileSynchronizer.setFilter(new SftpSimplePatternFileListFilter("*.zip"));
            sftpInboundFileSynchronizer.setRemoteDirectory("Test");
            sftpInboundFileSynchronizer.setPreserveTimestamp(Boolean.TRUE);
            sftpInboundFileSynchronizer.synchronizeToLocalDirectory(localDirectory);
            sftpInboundFileSynchronizer.setDeleteRemoteFiles(Boolean.FALSE);
            sftpInboundFileSynchronizer.setTemporaryFileSuffix(".copy");
            source = new SftpInboundFileSynchronizingMessageSource(sftpInboundFileSynchronizer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }
}