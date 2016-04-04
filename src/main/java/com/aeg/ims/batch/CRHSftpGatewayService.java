package com.aeg.ims.batch;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CRHSftpGatewayService {

    public static void main(String[] args) {


        try {
            SftpTransferService crTransfer = new SftpTransferService(new ClassPathXmlApplicationContext("classpath:/partners/crh-hvac-context.xml"));
            crTransfer.execute();
            //crTransfer.execute("RNC - Files For Testing");
            //SftpTransferService crTransfer2 = new SftpTransferService(new ClassPathXmlApplicationContext("classpath:/partners/cr-hvac-context.xml"));
            //crTransfer.execute("HVAC - Files For Testing");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
