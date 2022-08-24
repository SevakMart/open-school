package app.openschool.feature;

import app.openschool.feature.api.CourseSearchingFeaturesDto;
import app.openschool.feature.api.CreateAwsTemplateRequest;

public interface FeatureService {

  CourseSearchingFeaturesDto getCourseSearchingFeatures();

  void createTemplate(CreateAwsTemplateRequest request);

  void deleteTemplate(String templateName);
}
