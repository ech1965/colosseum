package services.messaging.impl;

import play.Logger;

import services.MessagingServiceInterface;
import services.messaging.MessageBase;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by ec on 7/03/15.
 */
public class MessagingService implements MessagingServiceInterface {
    @Override
    public void publishMessage(String queue, MessageBase message) {
        checkNotNull(queue);
        checkNotNull(message);
        Logger.debug("About to publish this message" + message.toString() + " to queue " + queue.toString());
    }
}
