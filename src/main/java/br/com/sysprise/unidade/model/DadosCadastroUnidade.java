package br.com.sysprise.unidade.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUnidade(@NotBlank(message = "Obrigatório o preenchimento do nome")
                                   String nome,
                                   String abreviacao) {
}
