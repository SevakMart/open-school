package app.openschool.course.module;

import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.ModuleDto;
import app.openschool.course.module.api.dto.UpdateModuleRequest;
import app.openschool.course.module.api.mapper.ModuleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
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
@RequestMapping("/api/v1/courses/{courseId}/modules")
public class ModuleController {

  private final ModuleService moduleService;

  public ModuleController(ModuleService moduleService) {
    this.moduleService = moduleService;
  }

  @Operation(summary = "add module", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<ModuleDto> add(
      @Parameter(description = "Id of the course which the new module will belong") @PathVariable
          Long courseId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new module")
          @Valid
          @RequestBody
          CreateModuleRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ModuleMapper.toModuleDto(moduleService.add(courseId, request)));
  }

  @Operation(summary = "modify module", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/{moduleId}")
  public ResponseEntity<ModuleDto> update(
      @Parameter(description = "Id of the module which will be modified") @PathVariable
          Long moduleId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for modifying module")
          @Valid
          @RequestBody
          UpdateModuleRequest request) {
    return ResponseEntity.ok()
        .body(ModuleMapper.toModuleDto(moduleService.update(moduleId, request)));
  }

  @Operation(summary = "delete module", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{moduleId}")
  public ResponseEntity<HttpStatus> delete(
      @Parameter(description = "Id of the module which will be deleted") @PathVariable
          Long moduleId) {
    moduleService.delete(moduleId);
    return ResponseEntity.noContent().build();
  }
}
