package models;

import models.generic.Model;

import javax.persistence.*;

import java.util.Base64;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;


/**
 * Class representing a PaaSage Model and tracking its state through the PaaSage platform
 */
@Entity
public class PaasageModel extends Model{

    /**
     * A common method for all enums since they can't have another base class
     * @param <T> Enum type
     * @param c enum type. All enums must be all caps.
     * @param string case insensitive
     * @return corresponding enum, or null
     */
    private static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
            return Enum.valueOf(c, string.trim().toUpperCase());
        }
        return null;
    }

    public enum State  {
        NEW, UNCHANGED, CREATED, UPLOADING_XMI, READY_TO_REASON, REASONING, NO_SOLUTION, READY_TO_CHOOSE, READY_TO_DEPLOY, DEPLOYING, DEPLOYED, RUNNING, IN_ERROR;
        public static State  fromString(String name) {
            return getEnumFromString(State.class, name);
        }
    }

    public enum Action {
        UNCHANGED,                       // If no arrow in state diagram, don't do anything
        CREATE,                          // Resource being created by user
        UPLOAD_XMI,                      // XMI being uploaded by user
        XMI_UPLOADED,                    // XMI file uploaded into CDO
        START_REASONNING,                // Reasoning started by user
        REASONNED_NO_PLAN,               // Reason outcome: NO Deployment PLAN (by PaaSage)
        REASONNED_ONE_PLAN,              // Reason outcome:  One Deployment plan (by PaaSage)
        REASONNED_MULTI_PLANS,           // Reason outcome: Multiple Deployment plans (by PaaSage)
        CHOOSE_PLAN,                     // Plan being chosen by user
        DEPLOY,                          // Deployment started by user
        FINISH_DEPLOYMENT,               // Deployment finished ( by PaaSage)
        RUN;                              // Application start requested by PaaSage

        public static Action  fromString(String name) {
            return getEnumFromString(Action.class, name);
        }
    }


    public PaasageModel()  {}

    public PaasageModel(String name)
    {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
    }


    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * State of the PaaSage Model.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    /**
     * Sub State of the PaaSage Model.
     */
    @Column(nullable = false)
    private String subState;

    /**
     * Current Executing Action of the PaaSage Model.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    /**
     * XMI file as uploaded by the api Caller (must be Base64 encoded)
     */
    @Lob
    private String xmiModelEncoded;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        checkNotNull(state);
        this.state = state;
    }

    public void setState(String state) {
        checkNotNull(state);
        this.state = State.fromString(state);
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        checkNotNull(subState);
        this.subState = subState;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        checkNotNull(action);
        this.action = action;
    }

    public void setAction(String action) {
        checkNotNull(action);
        this.action = Action.fromString(action);
    }

    public String getXmiModelEncoded() {
        return xmiModelEncoded;
    }

    public void setXmiModelEncoded(String xmiModelEncoded) {
        this.xmiModelEncoded = xmiModelEncoded;
    }

    // Kind of getter but for computed fields
    public String decodeXmiModel() {
        // Do not decode then SENTINEL
        if ("UNCHANGED".equalsIgnoreCase(xmiModelEncoded))
            return xmiModelEncoded;
        byte[] decoded = Base64.getDecoder().decode(xmiModelEncoded);
        String decodedString = new String(decoded);
        return decodedString;
    }

    // Kind of setter for computed field
    public void encodeXmiModel(String xmiModelDecoded) {
        byte[] encoded = Base64.getEncoder().encode(xmiModelDecoded.getBytes());
        this.xmiModelEncoded =new String(encoded);
    }
}
