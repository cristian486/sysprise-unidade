package br.com.sysprise.unidade.service;


import br.com.sysprise.unidade.model.*;
import br.com.sysprise.unidade.repository.UnidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pb.ProdutoServiceGrpc;
import pb.UnidadeId;

import java.util.List;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @GrpcClient("produto")
    private ProdutoServiceGrpc.ProdutoServiceBlockingStub produtoStub;

    public Unidade findUnidadeById(Long id) {
        return unidadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A unidade requisitada não foi encontrada!"));
    }

    public Page<DadosListagemUnidade> listar(Pageable pageable) {
        return unidadeRepository.findAll(pageable).map(DadosListagemUnidade::new);
    }

    public List<Unidade> listar() {
        return unidadeRepository.findAll();
    }

    public DadosDetalhamentoUnidade detalhar(Long id) {
        Unidade unidade = this.findUnidadeById(id);
        return new DadosDetalhamentoUnidade(unidade);
    }

    public DadosDetalhamentoUnidade cadastrar(DadosCadastroUnidade dadosCadastro) {
        Unidade unidade = new Unidade(dadosCadastro);
        unidadeRepository.save(unidade);
        return new DadosDetalhamentoUnidade(unidade);
    }

    public Long cadastrar(pb.CriarUnidadeRequest request) {
        Unidade unidade = new Unidade(request);
        unidadeRepository.save(unidade);
        return unidade.getId();
    }

    public DadosDetalhamentoUnidade atualizar(DadosAtualizarUnidade dadosAtualizar) {
        Unidade unidade = this.findUnidadeById(dadosAtualizar.id());
        unidade.atualizarCadastro(dadosAtualizar);
        return new DadosDetalhamentoUnidade(unidade);
    }

    public void deletar(Long id) {
        Unidade unidade = this.findUnidadeById(id);
        boolean haProdutosVinculados = produtoStub.verifyIfExistsProductsAssociatedWithUnit(UnidadeId.newBuilder().setId(id).build()).getExiste();
        if (haProdutosVinculados)
            throw new RuntimeException("Unidade não pode ser deletada pois há produtos vinculados a ela");
        unidadeRepository.delete(unidade);
    }
}
