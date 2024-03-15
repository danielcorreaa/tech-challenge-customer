package com.techchallenge.infrastructure.api;

import com.techchallenge.application.usecase.SolicitationExcludeUseCase;
import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.SolicitationExclude;
import com.techchallenge.infrastructure.api.dtos.SolicitationExcludeRequest;
import com.techchallenge.infrastructure.api.dtos.SolicitationExcludeResponse;
import com.techchallenge.infrastructure.api.mapper.SolicitationExcludeMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/solicitation/exclude")
@Tag(name = "Solicitation Exclude API")
public class SolicitationExcludeApi {

    private SolicitationExcludeUseCase solicitationExcludeUseCase;

    private SolicitationExcludeMapper mapper;

    public SolicitationExcludeApi(SolicitationExcludeUseCase solicitationExcludeUseCase, SolicitationExcludeMapper mapper) {
        this.solicitationExcludeUseCase = solicitationExcludeUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Result<SolicitationExcludeResponse>> insert(@RequestBody @Valid SolicitationExcludeRequest request,
                                                           UriComponentsBuilder uri) {
        SolicitationExclude solicitationExclude = solicitationExcludeUseCase
                .insert(mapper.toSolicitateExclude(request));
        UriComponents uriComponents = uri.path("/api/v1/solicitation/exclude/find/{cpf}")
                .buildAndExpand(solicitationExclude.getCpfValue());
        return ResponseEntity.created(uriComponents.toUri()).body(Result.create(
                mapper.toSolicitateExcludeResponse(solicitationExclude)));
    }

    @GetMapping("/find/{cpf}")
    public ResponseEntity<Result<SolicitationExcludeResponse>> find(@PathVariable String cpf) {
        SolicitationExclude solicitationExclude = solicitationExcludeUseCase.findByCpf(cpf);
        return ResponseEntity.ok(Result.ok(mapper.toSolicitateExcludeResponse(solicitationExclude)));
    }

    @GetMapping("/find")
    public ResponseEntity<Result<List<SolicitationExcludeResponse>>> find(
            @RequestParam(defaultValue = "false") boolean exclude,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        Result<List<SolicitationExclude>> allSolicitationExcluded;
        if(exclude){
            allSolicitationExcluded = solicitationExcludeUseCase.findAllSolicitationExcluded(page, size);
        }else {
            allSolicitationExcluded = solicitationExcludeUseCase.findAllSolicitationNotExcluded(page, size);
        }
        var result =  Result.ok(mapper.toSolicitateExcludeResponseList(allSolicitationExcluded),
                allSolicitationExcluded.getHasNext(), allSolicitationExcluded.getTotal());
        return ResponseEntity.ok(result);
    }
}
