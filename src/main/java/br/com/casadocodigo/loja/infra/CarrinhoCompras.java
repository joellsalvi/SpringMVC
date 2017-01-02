package br.com.casadocodigo.loja.infra;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.model.CarrinhoItem;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<CarrinhoItem, Integer> carrinho = new LinkedHashMap<CarrinhoItem, Integer>();

	public void add(CarrinhoItem carrinhoItem) {
		this.carrinho.put(carrinhoItem, getQuantidade(carrinhoItem) + 1);
	}

	public int getQuantidade(CarrinhoItem carrinhoItem) {
		if (!this.carrinho.containsKey(carrinhoItem)) {
			this.carrinho.put(carrinhoItem, 0);
		}
		return this.carrinho.get(carrinhoItem);
	}

	public Integer getQuantidade() {
		return this.carrinho.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
	}

	public Collection<CarrinhoItem> getItensCarrinho() {
		return this.carrinho.keySet();
	}

	public BigDecimal getTotal(CarrinhoItem item) {
		return item.getTotal(getQuantidade(item));
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;

		for (CarrinhoItem item : carrinho.keySet()) {
			total = total.add(getTotal(item));
		}

		return total;
	}

	public ModelAndView excluirItemDoCarrinho() {
		return new ModelAndView("redirect:/produtos");
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		this.carrinho.remove(new CarrinhoItem(new Produto(produtoId), tipoPreco));
	}
}
