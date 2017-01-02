package br.com.casadocodigo.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.data.ProdutoDAO;
import br.com.casadocodigo.loja.infra.CarrinhoCompras;
import br.com.casadocodigo.loja.model.CarrinhoItem;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private CarrinhoCompras carrinho;

	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipoPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");

		CarrinhoItem carrinhoItem = criaItem(produtoId, tipoPreco);
		carrinho.add(carrinhoItem);

		return modelAndView;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView visualizarCarrinho() {
		return new ModelAndView("carrinho/itens");
	}

	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoProduto) {
		Produto produto = produtoDAO.findById(produtoId);
		return new CarrinhoItem(produto, tipoProduto);
	}

	@RequestMapping("/remover")
	public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco, RedirectAttributes redirectAttributes) {
		carrinho.remover(produtoId, tipoPreco);
		redirectAttributes.addFlashAttribute("message", "Item removido com sucesso!");
		return new ModelAndView("redirect:/carrinho");
	}
}
