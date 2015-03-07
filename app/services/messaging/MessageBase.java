package services.messaging;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ec on 7/03/15.
 * Base class for all messages pushed on the messaging ( RabbitMQ)
 */
public abstract class MessageBase implements Serializable {

    protected MessageBase(String name){
        checkNotNull(name);
        this.name  = name;
    }
    private String name;

    @Override
    public String toString() {
        return "MessageBase{" +
                "name='" + name + '\'' +
                '}';
    }
}
