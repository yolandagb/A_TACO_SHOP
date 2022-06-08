package com.lm2a.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lm2a.data.IngredientRepository;
import com.lm2a.data.TacoRepository;
import com.lm2a.model.Ingredient;
import com.lm2a.model.Ingredient.Type;
import com.lm2a.model.Order;
import com.lm2a.model.Taco;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignController {

	@Autowired
	private IngredientRepository repo;
	
	@Autowired
	private TacoRepository tacoRepo;
	
	@GetMapping
	public String showDesignForm(Model model) {
		
		log.info("Acaba de empezar un diseño...");
		fillIngredientsForm(model);

		model.addAttribute("tktn", new Taco());
		return "design";
	}
	
	@ModelAttribute(name="order")
	public Order order() {
		return new Order();
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	public String processDesign(@Valid @ModelAttribute(name="tktn")Taco design, Errors errors, Model model, @ModelAttribute Order order) {
		if(errors.hasErrors()) {
			fillIngredientsForm(model);
			return "design";
		}
		Taco savedTaco = tacoRepo.save(design);
		order.addDesign(design);
		
		log.info("Procesando el taco: "+savedTaco);
		return "redirect:/orders/current";
	}
	
	public void fillIngredientsForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
				repo.findAll().forEach(i -> ingredients.add(i));

//				Arrays.asList(
//			      new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//			      new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//			      new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//			      new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//			      new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//			      new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//			      new Ingredient("CHED", "Cheddar", Type.CHEESE),
//			      new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//			      new Ingredient("SLSA", "Salsa", Type.SAUCE),
//			      new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//			    );

		Type[] types = Ingredient.Type.values();
		
		for(Type type: types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}
}
