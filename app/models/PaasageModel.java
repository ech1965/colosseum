package models;

import models.generic.NamedModel;

import javax.persistence.Column;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Class representing a PaaSage Model and tracking its state through the PaaSage platform
 */
@Entity
public class PaasageModel extends NamedModel{

    public enum State  { NEW, CREATED, READY_TO_REASON, REASONING, NO_SOLUTION, READY_TO_CHOOSE, READY_TO_DEPLOY, DEPLOYING, DEPLOYED, RUNNING}

    public enum Trigger {
        CREATE,                          // Resource being created by user
        UPLOAD_XMI,                      // XMI being uploaded by user
        START_REASONNING,                // Reasoning started by user
        REASONNED_NO_PLAN,               // Reason outcome: NO DEPLOYEMENT PLAN (by PaaSage)
        REASONNED_ONE_PLAN,              // Reason outcome:  One Deployement plan (by PaaSage)
        REASONNED_MULTI_PLANS,           // Reason outcome: Multiple deployement plans (by PaaSage)
        CHOOSE_PLAN,                     // Plan being chosen by user
        DEPLOY,                          // Deployment started by user
        FINISH_DEPLOYMENT,               // Deployment finished ( by PaaSage)
        RUN                              // Application start requested by PaaSage
    }


    public PaasageModel()  {}

    public PaasageModel(String name){
        super(name);
    }


    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * State of the PaaSage Model.
     */
    @Column(nullable = false)
    private String state;

    /**
     * Sub State of the PaaSage Model.
     */
    @Column(nullable = false)
    private String subState;

    /**
     * Current Executing Action of the PaaSage Model.
     */
    @Column(nullable = false)
    private String action;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        checkNotNull(state);
        this.state = state;
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        checkNotNull(subState);
        this.subState = subState;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        checkNotNull(action);
        this.action = action;
    }

}
