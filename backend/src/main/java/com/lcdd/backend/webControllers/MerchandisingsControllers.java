package com.lcdd.backend.webControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lcdd.backend.pojo.Merchandising;
import com.lcdd.backend.services.MerchandisingService;

@Controller
public class MerchandisingsControllers {
	
	@Autowired
	private MerchandisingService service;
	
	@RequestMapping(value= {"merchandising"})
	public String serveMerchandising(Model model) {
		return "merchandising";
	}
		
	@GetMapping("/merchList")
	public ResponseEntity<String> getMerchsList(@RequestParam() int pageId) {
		String result = "";
		
		Page<Merchandising> pageMerch = service.findAllPages(pageId,3);
		
		if(pageMerch.hasContent()) {
			for(Merchandising merch : pageMerch.getContent()) {
				if(merch.getDiscount() > 0) {
					if (merch.isHaveImage()) {
						result += "<div class='col-lg-4 col-sm-6'>" +
							"<div class='l_product_item'>" +
								"<h4>" + merch.getName() + "</h4>" +
								"<h5>" + merch.getPrice() + "€-" + merch.getDiscount() + "%</h5>" +
								"<div class='l_p_img'>" +
									"<img src='/images/merchImages/image-" + merch.getId() +".jpg' width='300' height='300'>" +
									"<h5 class='sale'>Opciones:</h5>" +
								"</div>" +
								"<div class='l_p_text'>" +
									"<ul>" +
										"<li class='p_icon'><a href='/merchandising/" + merch.getId() + "'>Ver Detalles</a></li>" +
									"</ul>" +
								"</div>" +
							"</div>" +
						"</div>";
					} else {
						result += "<div class='col-lg-4 col-sm-6'>" +
							"<div class='l_product_item'>" +
								"<h4>" + merch.getName() + "</h4>" +
								"<h5>" + merch.getPrice() + "€-" + merch.getDiscount() + "%</h5>" +
								"<div class='l_p_img'>" +
									"<img src='/assets/img/merch-image.jpg' width='300' height='300'>" +
									"<h5 class='sale'>Opciones:</h5>" +
								"</div>" +
								"<div class='l_p_text'>" +
									"<ul>" +
										"<li class='p_icon'><a href='/merchandising/" + merch.getId() + "'>Ver Detalles</a></li>" +
									"</ul>" +
								"</div>" +
							"</div>" +
						"</div>";
					}
				} else {
					if (merch.isHaveImage()) {
						result += "<div class='col-lg-4 col-sm-6'>" +
							"<div class='l_product_item'>" +
								"<h4>" + merch.getName() + "</h4>" +
								"<h5>" + merch.getPrice() + "€</h5>" +
								"<div class='l_p_img'>" +
								"<img src='/images/merchImages/image-" + merch.getId() +".jpg' width='300' height='300'>" +
									"<h5 class='sale'>Opciones:</h5>" +
								"</div>" +
								"<div class='l_p_text'>" +
									"<ul>" +
										"<li class='p_icon'><a href='/merchandising/" + merch.getId() + "'>Ver Detalles</a></li>" +
									"</ul>" +
								"</div>" +
							"</div>" +
						"</div>";
					} else {
						result += "<div class='col-lg-4 col-sm-6'>" +
							"<div class='l_product_item'>" +
								"<h4>" + merch.getName() + "</h4>" +
								"<h5>" + merch.getPrice() + "€" + "</h5>" +
								"<div class='l_p_img'>" +
									"<img src='/assets/img/merch-image.jpg' width='300' height='300'>" +
									"<h5 class='sale'>" + "Opciones:" + "</h5>" +
								"</div>" +
								"<div class='l_p_text'>" +
									"<ul>" +
										"<li class='p_icon'><a href='/merchandising/" + merch.getId() + "'>Ver Detalles</a></li>" +
									"</ul>" +
								"</div>" +
							"</div>" +
						"</div>";
					}
				}
				
//				result += "<div class='col-lg-4 col-sm-6'>" +
//						"<div class='l_product_item'>" +
//							"<h4>" + merch.getName() + "</h4>" +
//							"<h5>" + merch.getPrice() + "€" + "</h5>" +
//							"<div class='l_p_img'>" +
//								"<img src='" + "/assets/img/products/lcdd_mug.png" + "' width='300' height='300'>" +
//								"<h5 class='sale'>" + "Opciones:" + "</h5>" +
//							"</div>" +
//							"<div class='l_p_text'>" +
//								"<ul>" +
//									"<li class='p_icon'><a href='" + "#" +"'>Ver Detalles</a></li>" +
//								"</ul>" +
//							"</div>" +
//						"</div>" +
//					"</div>";
			}
		} else {
			result += "nomore";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/purchaseMerch/{id}"}, 
			method = RequestMethod.POST)
	public String purchaseMerch(Model model, @PathVariable long id) {
		
		Merchandising merchFound = service.findById(id);
		
        if(merchFound == null) {
        	return "redirect:/error";
        }
        
        merchFound.setStock(merchFound.getStock()-1);
        service.save(merchFound);
        model.addAttribute("merch", merchFound);
        
        return "merchandising-template";
	}
	
}
