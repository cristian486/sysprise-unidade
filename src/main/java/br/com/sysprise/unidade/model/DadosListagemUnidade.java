package br.com.sysprise.unidade.model;

public record DadosListagemUnidade(Long id, String nome) {

    public DadosListagemUnidade(Unidade unidade) {
        this(unidade.getId(), unidade.getNome());
    }
}
