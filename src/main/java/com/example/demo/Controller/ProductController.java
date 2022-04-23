package com.example.demo.Controller;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/")
	public String getpage() {
		return "index";
	}

	@RequestMapping(value = "/product/index", method = RequestMethod.GET)
	public String chercher(Model model, @RequestParam(name = "mc", defaultValue = "") String motCle,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		Page<Product> pageProduits = productRepository.SearchProduct("%" + motCle + "%", PageRequest.of(page, size));
		model.addAttribute("pageProduit", pageProduits);
		model.addAttribute("pageCourante", page);
		model.addAttribute("mc", motCle);
		int[] pages = new int[pageProduits.getTotalPages()];
		for (int i = 0; i < pages.length; i++)
			pages[i] = i;
		model.addAttribute("pages", pages);
		return "products";
	}

	@RequestMapping(value = "/formProduct")
	public String formProduit(Model model) {
		Product product = new Product();
		model.addAttribute(product);
		return "formProduct";
	}
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult errors) {

		if (errors.hasErrors()) {

			return "formProduct";
		} else {
			productRepository.save(product);
			return "configuration";
		}

	}

	@GetMapping("/admin/delete")
	public String delete(Long id, Model model, @RequestParam(name = "mc", defaultValue = "") String motCle,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		productRepository.deleteById(id);
//         return "redirect:/product/index?mc="+mc+"&page="+page+"&size="+size;
		return chercher(model, motCle, page, size);
	}

	@GetMapping("/admin/edit")
	public String formEdit(Long id, Model model) {
		Product product = productRepository.findById(id).get();
		model.addAttribute(product);
		return "formEdit";
	}

	
}
