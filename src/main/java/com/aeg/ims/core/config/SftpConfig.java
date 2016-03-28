package com.aeg.ims.core.config;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.file.remote.session.SessionFactory;

@Configuration
public class SftpConfin {

    @Autowired
    SftpProperties sftpProperties;

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> getSftpSessionFactory() {
        Resource resource = new FileSystemResource(
                sftpProperties.privateKeyLocation);
        DefaultSftpSessionFactory sftpSessionFactory = new DefaultSftpSessionFactory();
        sftpSessionFactory.setHost(sftpProperties.hostName);
        sftpSessionFactory.setPort(sftpProperties.port);
        sftpSessionFactory.setUser(sftpProperties.username);
        sftpSessionFactory.setPrivateKey(resource);
        return sftpSessionFactory;
    }

    @Bean
    @InboundChannelAdapter(value = "sftpChannel", poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
    public MessageSource<File> sftp() {
        SftpInboundFileSynchronizingMessageSource source = null;
        SftpInboundFileSynchronizer sftpInboundFileSynchronizer = null;
        File localDirectory = new File(sftpProperties.localDirectory);

        try {
            sftpInboundFileSynchronizer = new SftpInboundFileSynchronizer(getSftpSessionFactory());
            sftpInboundFileSynchronizer.setFilter(new SftpSimplePatternFileListFilter(sftpProperties.fileNamePattern));
            sftpInboundFileSynchronizer.setRemoteDirectory(sftpProperties.remoteDirectory);
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