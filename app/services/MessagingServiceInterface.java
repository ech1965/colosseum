package services;

import com.google.inject.ImplementedBy;
import services.messaging.MessageBase;
import services.messaging.impl.MessagingService;

/**
 * Created by ec on 7/03/15.
 */
@ImplementedBy(MessagingService.class)
public interface MessagingServiceInterface {
    public void publishMessage(String queue, MessageBase message);
}
