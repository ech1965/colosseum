package models.service;

import com.google.inject.AbstractModule;
import controllers.security.SecuredSessionOrToken;
import controllers.security.SecuredToken;
import dtos.*;

/**
 * Created by daniel on 28.04.15.
 */
public class StaticServiceModule extends AbstractModule {

    @Override protected void configure() {

        //static injection in dtos
        requestStaticInjection(ApplicationComponentDto.References.class);
        requestStaticInjection(ApplicationInstanceDto.References.class);
        requestStaticInjection(CloudDto.References.class);
        requestStaticInjection(CloudCredentialDto.References.class);
        requestStaticInjection(CommunicationDto.References.class);
        requestStaticInjection(CommunicationChannelDto.References.class);
        requestStaticInjection(LoginDto.References.class);
        requestStaticInjection(TenantDto.References.class);
        requestStaticInjection(HardwareDto.References.class);
        requestStaticInjection(ImageDto.References.class);
        requestStaticInjection(InstanceDto.References.class);
        requestStaticInjection(IpAddressDto.References.class);
        requestStaticInjection(KeyPairDto.References.class);
        requestStaticInjection(LocationDto.References.class);
        //requestStaticInjection(PaasageModelDto.References.class);
        requestStaticInjection(VirtualMachineDto.References.class);
        requestStaticInjection(VirtualMachineTemplateDto.References.class);

        //static injection in security tokens
        requestInjection(SecuredToken.References.class);
        requestStaticInjection(SecuredSessionOrToken.References.class);

    }
}
