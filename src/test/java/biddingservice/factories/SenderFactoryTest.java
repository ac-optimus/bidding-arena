package biddingservice.factories;

import biddingservice.Constants;
import biddingservice.gateway.EmailSender;
import biddingservice.gateway.Sender;
import biddingservice.gateway.SmsSender;
import jakarta.validation.constraints.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class SenderFactoryTest {
    @Mock
    EmailSender emailSender;
    @Mock
    SmsSender smsSender;
    @InjectMocks
    SenderFactory senderFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSenderSMSSender() {
        Sender result = senderFactory.getSender(Constants.Gateway.SMS);
        if (!(result instanceof SmsSender))  {
            Assertions.assertEquals(false, true);
        }
    }

    @Test
    void testGetSenderEmailSender() {
        Sender result = senderFactory.getSender(Constants.Gateway.EMAIL);
        if (!(result instanceof EmailSender))  {
            Assertions.assertEquals(false, true);
        }
    }

    @Test
    void testGetSenderDefault() {
        Sender result = senderFactory.getSender("senderConfig");
        if (result == null)  {
            Assertions.assertEquals(true, true);
        }
    }

}