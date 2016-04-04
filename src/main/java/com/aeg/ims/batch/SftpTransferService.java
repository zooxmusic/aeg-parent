package com.aeg.ims.batch;

import com.aeg.ims.ftp.SftpTestUtils;
import com.jcraft.jsch.ChannelSftp;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by bszucs on 4/4/2016.
 */
public class SftpTransferService {

    private ConfigurableApplicationContext context;
    public SftpTransferService(ConfigurableApplicationContext ctx) {
        this.context = ctx;
    }

    public void execute() throws Exception {

        SourcePollingChannelAdapter adapter = null;
        try {
            if(context.isActive()) {
                context.start();
            } else {
                context.refresh();
            }

            //AtomicReference<String> remoteDir = (AtomicReference<String>) context.getBean("remoteDir", AtomicReference.class);
            //remoteDir.set(dir);
            PollableChannel localFileChannel = context.getBean("receiveChannel", PollableChannel.class);
            //SessionFactory<ChannelSftp.LsEntry> sessionFactory = context.getBean(CachingSessionFactory.class);
            adapter = context.getBean(SourcePollingChannelAdapter.class);
            adapter.start();

            Message<?> received = localFileChannel.receive();

            System.out.println("Received first file message: " + received);


        } catch(Exception e) {
            throw e;
        } finally {
            if(null != adapter)
                adapter.stop();

            context.close();
        }
    }


    /*public void execute2(final String dir) throws Exception {

        SourcePollingChannelAdapter adapter = null;
        try {
            if(context.isActive()) {
                context.start();
            } else {
                context.refresh();
            }


            AtomicReference<String> remoteDir = (AtomicReference<String>) context.getBean("remoteDir", AtomicReference.class);
            remoteDir.set(dir);
            FileReadingMessageSource localFileChannel = context.getBean("sftpInboundAdapter.source", FileReadingMessageSource.class);
            adapter = context.getBean(SourcePollingChannelAdapter.class);
            adapter.start();
            localFileChannel.setDirectory(new File(dir));

            Message<?> received = localFileChannel.receive();

            System.out.println("Received first file message: " + received);


        } catch(Exception e) {
            throw e;
        } finally {
            if(null != adapter)
                adapter.stop();

            context.close();
        }
    }

    public void execute2() throws Exception {

        try {
            @SuppressWarnings("unchecked")
            SourcePollingChannelAdapter adapter = context.getBean("inputFileChannelAdapter", SourcePollingChannelAdapter.class);
            FileReadingMessageSource source = context.getBean("inputFileChannelAdapter.source", FileReadingMessageSource.class);
            File monitorFileDir = new File("H");
            //source.setDirectory( monitorFileDir );
            adapter.start();

            AtomicReference<String> targetDir = (AtomicReference<String>) context.getBean("remoteDir", AtomicReference.class);
            targetDir.set("HVAC - Files For Testing");

            Message<?> received = source.receive();

            targetDir.set("HPwES - Files For Testing");
            received = source.receive();
            System.out.println("Received first file message: " + received);

        } catch(Exception e) {
            throw e;
        } finally {
            context.close();
        }
    }
*/

}
