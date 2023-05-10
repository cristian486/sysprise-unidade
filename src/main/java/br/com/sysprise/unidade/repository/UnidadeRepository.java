package br.com.sysprise.unidade.repository;

import br.com.sysprise.unidade.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

//    @Query("select case when count(p.id) > 0 then true else false end " +
//            "from Produto p join p.unidade where p.unidade.id = :id")
//    Boolean haProdutosVinculadosUnidade(Long id);
}
