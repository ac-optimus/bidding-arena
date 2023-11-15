package biddingservice.factories;

import biddingservice.Constants;
import biddingservice.gateway.EmailSender;
import biddingservice.gateway.Sender;
import biddingservice.gateway.SmsSender;
import jakarta.inject.Inject;


public class SenderFactory {
    private EmailSender emailSender;
    private SmsSender smsSender;

    @Inject
    public SenderFactory(EmailSender emailSender, SmsSender smsSender) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
    }

    public Sender getSender(String senderConfig) {
        return switch (senderConfig) {
            case Constants.Gateway.EMAIL -> emailSender;
            case Constants.Gateway.SMS -> smsSender;
            default -> null;
        };
    }

}
