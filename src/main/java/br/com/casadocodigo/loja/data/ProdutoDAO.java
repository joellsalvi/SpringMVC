package br.com.casadocodigo.loja.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.model.Produto;

@Repository
@Transactional
public class ProdutoDAO {

    @PersistenceContext
    private EntityManager manager;

    public void gravar(Produto produto){
    	
//    	System.out.println("Salvando novo produto");
//    	try {
//	        Thread.sleep(20000);    // 20 segundos
//	    } catch (InterruptedException e) {
//	        e.printStackTrace();
//	    }
    	
        manager.persist(produto);
//        System.out.println("Salvou o produto");
    }

	public List<Produto> listar() {
		return manager.createQuery("select p from Produto p", Produto.class).getResultList();
	}

	public Produto findById(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos precos where p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}

}
