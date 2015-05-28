
/**
 * Created by ec on 9/02/15.
 */

/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import controllers.security.SecuredToken;
import dtos.PaasageModelDto;
import dtos.conversion.api.ModelDtoConversionService;
import models.PaasageModel;
import models.service.api.PaasageModelServiceInterface;
import models.service.api.generic.ModelService;
import play.mvc.Security;
import services.MessagingServiceInterface;
import services.messaging.PaasageMessage;


/**
 * Implementation of the GenericApiController for the PaaSageModel model class.
 *
 * @author Etienne Charlier
 */
@Security.Authenticated(SecuredToken.class)
public class PaasageModelController extends GenericApiController<PaasageModel, PaasageModelDto, PaasageModelDto, PaasageModelDto> {

    private static final StateMachineConfig<PaasageModel.State, PaasageModel.Action> passageModelStateConfigForUsers;

    private static final StateMachineConfig<PaasageModel.State, PaasageModel.Action> passageModelStateConfigForSystem;

    static {
        passageModelStateConfigForUsers = new StateMachineConfig<>();

        passageModelStateConfigForUsers.configure(PaasageModel.State.CREATED)
                .permit(PaasageModel.Action.UPLOAD_XMI, PaasageModel.State.READY_TO_REASON);

        passageModelStateConfigForUsers.configure(PaasageModel.State.READY_TO_REASON)
                .permit(PaasageModel.Action.START_REASONNING,PaasageModel.State.REASONING);

        passageModelStateConfigForUsers.configure(PaasageModel.State.REASONING)
                .permit(PaasageModel.Action.REASONNED_NO_PLAN, PaasageModel.State.NO_SOLUTION)
                .permit(PaasageModel.Action.REASONNED_ONE_PLAN, PaasageModel.State.READY_TO_DEPLOY)
                .permit(PaasageModel.Action.REASONNED_MULTI_PLANS,PaasageModel.State.READY_TO_CHOOSE);

        passageModelStateConfigForUsers.configure(PaasageModel.State.NO_SOLUTION)
                .permit(PaasageModel.Action.UPLOAD_XMI, PaasageModel.State.READY_TO_REASON);

        passageModelStateConfigForUsers.configure(PaasageModel.State.READY_TO_CHOOSE)
                .permit(PaasageModel.Action.UPLOAD_XMI, PaasageModel.State.READY_TO_REASON)
                .permit(PaasageModel.Action.CHOOSE_PLAN, PaasageModel.State.READY_TO_DEPLOY);

        passageModelStateConfigForUsers.configure(PaasageModel.State.READY_TO_DEPLOY)
                .permit(PaasageModel.Action.DEPLOY, PaasageModel.State.DEPLOYING);

        passageModelStateConfigForUsers.configure(PaasageModel.State.DEPLOYING)
                .permit(PaasageModel.Action.FINISH_DEPLOYMENT, PaasageModel.State.DEPLOYED);

        passageModelStateConfigForUsers.configure(PaasageModel.State.DEPLOYED)
                .permit(PaasageModel.Action.RUN, PaasageModel.State.RUNNING);



        passageModelStateConfigForSystem = new StateMachineConfig<>();

        passageModelStateConfigForSystem.configure(PaasageModel.State.CREATED)
                .permit(PaasageModel.Action.UPLOAD_XMI, PaasageModel.State.UPLOADING_XMI);

        passageModelStateConfigForSystem.configure(PaasageModel.State.UPLOADING_XMI)
                .permit(PaasageModel.Action.XMI_UPLOADED, PaasageModel.State.READY_TO_REASON);

        passageModelStateConfigForSystem.configure(PaasageModel.State.READY_TO_REASON)
                .permit(PaasageModel.Action.START_REASONNING,PaasageModel.State.REASONING);

        passageModelStateConfigForSystem.configure(PaasageModel.State.REASONING)
                .permit(PaasageModel.Action.REASONNED_NO_PLAN, PaasageModel.State.NO_SOLUTION)
                .permit(PaasageModel.Action.REASONNED_ONE_PLAN, PaasageModel.State.READY_TO_DEPLOY)
                .permit(PaasageModel.Action.REASONNED_MULTI_PLANS,PaasageModel.State.READY_TO_CHOOSE);

        passageModelStateConfigForSystem.configure(PaasageModel.State.NO_SOLUTION)
                .permit(PaasageModel.Action.UPLOAD_XMI, PaasageModel.State.READY_TO_REASON);

        passageModelStateConfigForSystem.configure(PaasageModel.State.READY_TO_CHOOSE)
                .permit(PaasageModel.Action.UPLOAD_XMI, PaasageModel.State.READY_TO_REASON)
                .permit(PaasageModel.Action.CHOOSE_PLAN, PaasageModel.State.READY_TO_DEPLOY);

        passageModelStateConfigForSystem.configure(PaasageModel.State.READY_TO_DEPLOY)
                .permit(PaasageModel.Action.DEPLOY, PaasageModel.State.DEPLOYING);

        passageModelStateConfigForSystem.configure(PaasageModel.State.DEPLOYING)
                .permit(PaasageModel.Action.FINISH_DEPLOYMENT, PaasageModel.State.DEPLOYED);

        passageModelStateConfigForSystem.configure(PaasageModel.State.DEPLOYED)
                .permit(PaasageModel.Action.RUN, PaasageModel.State.RUNNING);

    }


    @Inject public PaasageModelController(ModelService<PaasageModel> modelService,
                                          TypeLiteral<PaasageModel> typeLiteral,
                                          ModelDtoConversionService conversionService,
                                          MessagingServiceInterface messagingServiceInterface) {
        super(modelService, typeLiteral, conversionService);
        this.messagingService = messagingServiceInterface;

    }

    protected MessagingServiceInterface messagingService;

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.PaasageModelController.get(id).absoluteURL(request());
    }

    @Override
    protected BeforeAfterResult beforeUpdate(PaasageModel current, PaasageModelDto wanted)
    {
        StateMachine<PaasageModel.State, PaasageModel.Action> modelState;
        //TODO: use user specific or system specific FSM
        if ("backedn@paasage.net".equalsIgnoreCase(request().username())) {
           modelState = new StateMachine<>(current.getState(), passageModelStateConfigForSystem);
        }
        else {
            modelState = new StateMachine<>(current.getState(), passageModelStateConfigForUsers);
        }
        // FIXME: quick hack to accept state IN_ERROR from any action
        if (wanted.getState() == PaasageModel.State.IN_ERROR)
            return BeforeAfterResult.CONTINUE;
        try {
            modelState.fire(wanted.getAction());
            wanted.state = modelState.getState();
        }
        catch (IllegalStateException e){
            return BeforeAfterResult.ABORT;
        }
        return BeforeAfterResult.CONTINUE;
    }

    @Override
    protected void postPut(PaasageModel updated) {

        PaasageMessage message = new PaasageMessage(updated.getId(), updated.getAction().toString());
        messagingService.publishMessage("PAASAGE", message);
        return ;
    }
}
