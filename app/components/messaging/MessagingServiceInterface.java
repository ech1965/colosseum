package components.messaging;

import com.google.inject.ImplementedBy;
import components.messaging.impl.MessagingService;

/**
 * Created by ec on 7/03/15.
 */
@ImplementedBy(MessagingService.class)
public interface MessagingServiceInterface {
    void publishMessage(String queue, MessageBase message);
}
