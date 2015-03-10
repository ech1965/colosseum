package services.messaging;

import java.io.Serializable;

/**
 * Created by ec on 7/03/15.
 */
public class MessagePassage extends MessageBase implements Serializable{
    public MessagePassage(Long passageModelId, String action){
        super("PaaSage");
        this.paasageModelId = passageModelId;
        this.action = action;
    }
    private Long paasageModelId;
    private String action;

    public Long getPaasageModelId() {
        return paasageModelId;
    }

    public void setPaasageModelId(Long paasageModelId) {
        this.paasageModelId = paasageModelId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "MessagePassage{" +
                "paasageModelId=" + paasageModelId +
                ", action='" + action + '\'' +
                '}';
    }
}
