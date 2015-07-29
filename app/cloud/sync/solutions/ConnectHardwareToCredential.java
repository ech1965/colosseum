package cloud.sync.solutions;

import cloud.resources.HardwareInLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.CloudCredential;
import models.Hardware;
import models.service.HardwareModelService;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectHardwareToCredential implements Solution {

    private final HardwareModelService hardwareModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject public ConnectHardwareToCredential(HardwareModelService hardwareModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        this.hardwareModelService = hardwareModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof HardwareProblems.HardwareMissesCredential;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        HardwareInLocation hardwareInLocation =
            ((HardwareProblems.HardwareMissesCredential) problem).getHardwareInLocation();

        Hardware modelHardware = hardwareModelService.getByRemoteId(hardwareInLocation.id());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(hardwareInLocation.credential());

        if (modelHardware == null || cloudCredential == null) {
            throw new SolutionException();
        }
        modelHardware.getCloudCredentials().add(cloudCredential);
        hardwareModelService.save(modelHardware);
    }



}
