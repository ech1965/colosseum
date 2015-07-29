package cloud.sync.solutions;

import cloud.resources.ImageInLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import models.CloudCredential;
import models.Image;
import models.service.ImageModelService;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectImageToCredential implements Solution {

    private final ImageModelService imageModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject public ConnectImageToCredential(ImageModelService imageModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {

        this.imageModelService = imageModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof ImageProblems.ImageMissesCredential;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        ImageInLocation imageInLocation =
            ((ImageProblems.ImageMissesCredential) problem).getImageInLocation();

        Image modelImage = imageModelService.getByRemoteId(imageInLocation.id());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(imageInLocation.credential());

        if (modelImage == null || cloudCredential == null) {
            throw new SolutionException();
        }

        modelImage.getCloudCredentials().add(cloudCredential);
        imageModelService.save(modelImage);
    }



}
