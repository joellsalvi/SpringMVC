package br.com.casadocodigo.loja.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.infra.CarrinhoCompras;
import br.com.casadocodigo.loja.model.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/finalizar")
	public Callable<ModelAndView> finalizar(RedirectAttributes redirectAttributes) {
		//Callable torna a execução do método assincrona, liberando o servidor para outras requisições, não o deixando travado até q esta termine
		//Sempre que houver chamada a serviçõs externos deve ser avaliado se precisa ser assincrono.
		return () -> {
			try {
				String uri = "http://book-payment.herokuapp.com/payment";
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				redirectAttributes.addFlashAttribute("msgSucesso", response);
				return new ModelAndView("redirect:/produtos");
			} catch (HttpClientErrorException e) {
				redirectAttributes.addFlashAttribute("msgError", "Valor maior que o permitido");
				return new ModelAndView("redirect:/carrinho");
			}
		};
	}
}
