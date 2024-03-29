package app.openschool.course.module.item;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleService;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.api.dto.ModuleItemDto;
import app.openschool.course.module.item.api.dto.UpdateModuleItemRequest;
import app.openschool.course.module.item.api.mapper.ModuleItemMapper;
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
@RequestMapping("/api/v1/module-items")
public class ModuleItemController {

  private final ModuleItemService moduleItemService;
  private final ModuleItemMapper moduleItemMapper;
  private final ModuleService moduleService;
  private final MessageSource messageSource;

  public ModuleItemController(
      ModuleItemService moduleItemService,
      ModuleItemMapper moduleItemMapper,
      ModuleService moduleService,
      MessageSource messageSource) {
    this.moduleItemService = moduleItemService;
    this.moduleItemMapper = moduleItemMapper;
    this.moduleService = moduleService;
    this.messageSource = messageSource;
  }

  @Operation(summary = "add moduleItem", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Creates new module item and returns that"),
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
  public ResponseEntity<ModuleItemDto> add(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new moduleItem")
          @Valid
          @RequestBody
          CreateModuleItemRequest request,
      Principal principal) {
    Module moduleById = moduleService.findModuleById(request.getModuleId());
    if (!moduleById.getCourse().getMentor().getEmail().equals(principal.getName())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(moduleItemMapper.toModuleItemDto(moduleItemService.add(request)));
  }

  @Operation(summary = "modify moduleItem", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Modifies the module item and returns that"),
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
  @PutMapping("/{moduleItemId}")
  public ResponseEntity<ModuleItemDto> update(
      @Parameter(description = "Id of the moduleItem which will be modified") @PathVariable
          Long moduleItemId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for modifying the moduleItem")
          @Valid
          @RequestBody
          UpdateModuleItemRequest request,
      Principal principal) {
    moduleItemAffiliationVerification(principal, moduleItemId);

    return ResponseEntity.ok()
        .body(moduleItemMapper.toModuleItemDto(moduleItemService.update(moduleItemId, request)));
  }

  @Operation(summary = "delete moduleItem", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The module item was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid module item id supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @DeleteMapping("/{moduleItemId}")
  public ResponseEntity<ModuleItemDto> delete(
      @Parameter(description = "Id of the module which will be deleted") @PathVariable
          Long moduleItemId,
      Principal principal) {
    moduleItemAffiliationVerification(principal, moduleItemId);
    moduleItemService.delete(moduleItemId);
    return ResponseEntity.noContent().build();
  }

  private void moduleItemAffiliationVerification(Principal principal, Long moduleItemId) {
    ModuleItem byModuleItemId = moduleItemService.findByModuleItemId(moduleItemId);
    if (!principal
        .getName()
        .equals(byModuleItemId.getModule().getCourse().getMentor().getEmail())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
  }
}
