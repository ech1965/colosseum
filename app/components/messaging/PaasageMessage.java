package components.messaging;

import java.io.Serializable;

/**
 * Created by ec on 7/03/15.
 */
public class PaasageMessage extends MessageBase implements Serializable{
    public PaasageMessage(Long passageModelId, String action){
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
        return "PaasageMessage{" +
                "paasageModelId=" + paasageModelId +
                ", action='" + action + '\'' +
                '}';
    }
}
