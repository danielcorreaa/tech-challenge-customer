package com.techchallenge.infrastructure.gateway;

import com.techchallenge.application.gateway.SolicitationExcludeGateway;
import com.techchallenge.core.exceptions.NotFoundException;
import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.SolicitationExclude;
import com.techchallenge.infrastructure.persistence.entity.SolicitationExcludeEntity;
import com.techchallenge.infrastructure.persistence.mapper.SolicitationEntityMapper;
import com.techchallenge.infrastructure.persistence.repository.SolicitationExcludeResository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SolicitationExcludeGatewayRepository implements SolicitationExcludeGateway {

    private final SolicitationExcludeResository solicitationExcludeResository;

    private final SolicitationEntityMapper solicitationEntityMapper;

    public SolicitationExcludeGatewayRepository(SolicitationExcludeResository solicitationExcludeResository, SolicitationEntityMapper solicitationEntityMapper) {
        this.solicitationExcludeResository = solicitationExcludeResository;
        this.solicitationEntityMapper = solicitationEntityMapper;
    }

    @Override
    public SolicitationExclude insert(SolicitationExclude solicitationExclude) {
        SolicitationExcludeEntity entity = solicitationEntityMapper.toSolicitationExcludeEntity(solicitationExclude);
        return solicitationEntityMapper.toSolicitationExclude(solicitationExcludeResository.save(entity));
    }

    @Override
    public Optional<SolicitationExclude> findByCpf(String cpf) {
        Optional<SolicitationExcludeEntity> entity = solicitationExcludeResository.findById(cpf);
        return entity.map(solicitationEntityMapper::toSolicitationExclude);
    }

    @Override
    public Boolean exists(String cpf) {
        return solicitationExcludeResository.existsById(cpf);
    }

    @Override
    public Result<List<SolicitationExclude>> findAllSolicitationExcluded(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SolicitationExcludeEntity> pageResponse = solicitationExcludeResository.findAllSolicitationExcluded(pageable);
        List<SolicitationExclude> list = solicitationEntityMapper
                .toSolicitationExcludeList(pageResponse.stream().toList());
        return Result.ok(list,
                pageResponse.hasNext(),
                pageResponse.getTotalElements());
    }

    @Override
    public Result<List<SolicitationExclude>> findAllSolicitationNotExcluded(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SolicitationExcludeEntity> pageResponse = solicitationExcludeResository.findAllSolicitationNotExcluded(pageable);
        List<SolicitationExclude> list = solicitationEntityMapper
                .toSolicitationExcludeList(pageResponse.stream().toList());
        return Result.ok(list,
                pageResponse.hasNext(),
                pageResponse.getTotalElements());
    }
}
