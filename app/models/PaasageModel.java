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
