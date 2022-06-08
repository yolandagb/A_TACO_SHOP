package com.lm2a.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lm2a.data.OrderRepository;
import com.lm2a.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepo;
	
	@GetMapping("/current")
	public String orderForm(Model model) {
			model.addAttribute("order", new Order());
			return "orderForm";
	}
	
	@PostMapping
	public String processOrder(Model model, @Valid Order order, Errors errors, SessionStatus sessionStatus) {
		if(errors.hasErrors()) {
			model.addAttribute("order", order);
			return "orderForm";
		}
		orderRepo.save(order);
		sessionStatus.setComplete();
		
		log.info("Acaba de entrar una orden!!!"+order);
		return "redirect:/";
	}
}
