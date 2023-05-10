package br.com.sysprise.unidade.service;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import pb.CriarUnidadeRequest;
import pb.Unidade;
import pb.UnidadeId;
import pb.UnidadeServiceGrpc;

import java.util.List;

@GrpcService
@AllArgsConstructor
public class UnidadeRpcService extends UnidadeServiceGrpc.UnidadeServiceImplBase {

    private final UnidadeService unidadeService;

    @Override
    public void createUnidade(CriarUnidadeRequest request, StreamObserver<UnidadeId> responseObserver) {
        Long unidadeId = unidadeService.cadastrar(request);
        responseObserver.onNext(UnidadeId.newBuilder().setId(unidadeId).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUnidade(UnidadeId request, StreamObserver<Unidade> responseObserver) {
        br.com.sysprise.unidade.model.Unidade unidadeBanco = unidadeService.findUnidadeById(request.getId());
        Unidade unidade = Unidade.newBuilder().setId(unidadeBanco.getId())
                                .setNome(unidadeBanco.getNome())
                                .setAbreviacao(unidadeBanco.getAbreviacao())
                                .build();
        responseObserver.onNext(unidade);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUnidade(UnidadeId request, StreamObserver<pb.blank> responseObserver) {
        unidadeService.deletar(request.getId());
        responseObserver.onNext(pb.blank.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void listUnidade(pb.blank request, StreamObserver<pb.ListaUnidades> responseObserver) {
        List<Unidade> unidades = unidadeService.listar().stream().map(unidadeBanco -> {
            return Unidade.newBuilder().setId(unidadeBanco.getId())
                    .setNome(unidadeBanco.getNome())
                    .setAbreviacao(unidadeBanco.getAbreviacao())
                    .build();
        }).toList();

        responseObserver.onNext(pb.ListaUnidades.newBuilder().addAllUnidade(unidades).build());
        responseObserver.onCompleted();
    }
}
