package app.openschool.course.module;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.Course;
import app.openschool.course.CourseService;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.ModuleDto;
import app.openschool.course.module.api.dto.UpdateModuleRequest;
import app.openschool.course.module.api.mapper.ModuleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/modules")
public class ModuleController {

  private final ModuleService moduleService;
  private final CourseService courseService;
  private final MessageSource messageSource;

  public ModuleController(
      ModuleService moduleService, CourseService courseService, MessageSource messageSource) {
    this.moduleService = moduleService;
    this.courseService = courseService;

    this.messageSource = messageSource;
  }

  @Operation(summary = "add module", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new module and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @PostMapping
  public ResponseEntity<ModuleDto> add(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new module")
          @Valid
          @RequestBody
          CreateModuleRequest request,
      Principal principal) {
    Course courseById =
        courseService
            .findCourseById(request.getCourseId())
            .orElseThrow(IllegalArgumentException::new);
    if (!principal.getName().equals(courseById.getMentor().getEmail())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ModuleMapper.toModuleDto(moduleService.add(request)));
  }

  @Operation(summary = "modify module", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Modifies the module and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @PutMapping("/{moduleId}")
  public ResponseEntity<ModuleDto> update(
      @Parameter(description = "Id of the module which will be modified") @PathVariable
          Long moduleId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for modifying module")
          @Valid
          @RequestBody
          UpdateModuleRequest request,
      Principal principal) {
    moduleAffiliationVerification(principal, moduleId);
    return ResponseEntity.ok()
        .body(ModuleMapper.toModuleDto(moduleService.update(moduleId, request)));
  }

  @Operation(summary = "delete module", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The module was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid module id supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('MENTOR')")
  @DeleteMapping("/{moduleId}")
  public ResponseEntity<HttpStatus> delete(
      @Parameter(description = "Id of the module which will be deleted") @PathVariable
          Long moduleId,
      Principal principal) {
    moduleAffiliationVerification(principal, moduleId);
    moduleService.delete(moduleId);
    return ResponseEntity.noContent().build();
  }

  private void moduleAffiliationVerification(Principal principal, Long moduleId) {
    Module moduleById = moduleService.findModuleById(moduleId);
    Course courseById =
        courseService
            .findCourseById(moduleById.getCourse().getId())
            .orElseThrow(IllegalArgumentException::new);
    if (!principal.getName().equals(courseById.getMentor().getEmail())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
  }
}
