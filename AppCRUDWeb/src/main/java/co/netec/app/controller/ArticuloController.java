package co.netec.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.netec.app.entities.Articulo;
import co.netec.app.service.IArticuloService;

@Controller
public class ArticuloController {
	
	@Autowired
	private IArticuloService servicio;
	
	@GetMapping("/listar")
	public String inicio(Model modelo) {
		
		List<Articulo> listaDeArticulos = servicio.findAll();
		modelo.addAttribute("articulos", listaDeArticulos);
		
		return "listaArticulo";
	}
	
	@GetMapping("/crear")
	public String irAPaginaGrabar() {
		
		return "formArticulo";
	}
	
	@PostMapping("/save")
	public String salvar(Articulo articulo, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			
			for (ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrió una incidencia: " + error.getDefaultMessage());
			}
			
			return "formArticulo";
		}
		
		servicio.save(articulo);
		attributes.addFlashAttribute("msg", "Registro Completado");
		
		return "redirect:/listar";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attributes) {
		
		try {
			
			servicio.delete(id);
			attributes.addFlashAttribute("result", "Se ha borrado con éxito el artículo");
			
		} catch(ResponseStatusException exception) {
			
			attributes.addFlashAttribute("result", exception.getMessage());
			
		} catch(Exception ex) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
			
		}
		
		return "redirect:/listar";
	}
	
	@GetMapping("/update/{id}")
	public String autocompletarActualizar(@PathVariable("id") int id, Model modelo) {
		
		Articulo articulo = servicio.findById(id);
		modelo.addAttribute("objeto", articulo);
		
		return "formArticulo";
	}
	


}
