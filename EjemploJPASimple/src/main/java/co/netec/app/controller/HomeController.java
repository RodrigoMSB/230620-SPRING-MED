package co.netec.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.netec.app.entities.Articulo;
import co.netec.app.service.IArticuloService;

@Controller
public class HomeController {
	
	@Autowired
	private IArticuloService servicio;
	
	@GetMapping("/")
	public String inicio(Model modelo) {
		
		List<Articulo> listarArticulos = servicio.findAll();
		modelo.addAttribute("articulos", listarArticulos);
		
		return "listArticulos";
	}

}
