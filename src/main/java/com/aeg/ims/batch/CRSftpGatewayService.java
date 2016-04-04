package com.aeg.ims.batch;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CRSftpGatewayService {

    public void transfer() throws Exception{

        SftpTransferService crTransfer = new SftpTransferService(new ClassPathXmlApplicationContext("classpath:/partners/cr-hvac-context.xml"));
        crTransfer.execute();
        SftpTransferService crTransfer2 = new SftpTransferService(new ClassPathXmlApplicationContext("classpath:/partners/cr-hpwes-context.xml"));
        crTransfer2.execute();
        //crTransfer.execute("RNC - Files For Testing");
        //SftpTransferService crTransfer2 = new SftpTransferService(new ClassPathXmlApplicationContext("classpath:/partners/cr-hvac-context.xml"));
        //crTransfer.execute("HVAC - Files For Testing");
    }

    public static void main(String[] args) {


        try {
            CRSftpGatewayService service = new CRSftpGatewayService();
            service.transfer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
