package com.javassem.controller;

import com.javassem.domain.ShopVO;
import com.javassem.service.ShopService;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javassem.domain.ParkVO;
import com.javassem.domain.ShopVO;
import com.javassem.service.ShopService;


@Controller
@RequestMapping({"user"})
public class ShopController {

	
	@Autowired
	private ShopService shopService;
	  
	@Autowired
	  private SqlSessionTemplate mybatis;	
	
	// 일자리 찾기 업체 가져오기.
	@RequestMapping(value="/user/storeClose.do", method=RequestMethod.GET)
	public String select(ShopVO vo, Model m,String searchCondition, String searchKeyword ){
		
		HashMap map = new HashMap();
		map.put("searchCondition", searchCondition);
		map.put("searchKeyword", searchKeyword);
		
		List<ShopVO> list = shopService.getShopList(map);
		m.addAttribute("ShopList", list);
		return "user/storeClose" ;
	}
	

	@RequestMapping(value = {"/user/userSupport.do"}, method=RequestMethod.GET)
	public void getShop(@RequestParam int board_owner_seq, ShopVO vo, Model m){
		vo.setBoard_owner_seq(board_owner_seq);
		
		ShopVO result = this.shopService.getShop(vo);
		m.addAttribute("shop", result);
	}
	
	@RequestMapping(value = {"support.do"}, method=RequestMethod.POST)
	public String getShop2(
			
			@RequestParam int board_owner_seq,
			@RequestParam String shopname,
			@RequestParam String normal_emergency,
			@RequestParam String shopaddr,
			@RequestParam String shoppay,
			@RequestParam String jobDate,
			@RequestParam String startTime,
			@RequestParam String endTime,
			ShopVO vo, Model m){
			
			vo.setBoard_owner_seq(board_owner_seq);
			vo.setShopname(shopname);
			vo.setNormal_emergency(normal_emergency);
			vo.setShopaddr(shopaddr);
			vo.setShoppay(shoppay);
			vo.setJobDate(jobDate);
			vo.setJobTime_start(startTime);
			vo.setJobTime_end(endTime);
			
			mybatis.insert("ShopDAO.getShop2", vo);
			return "redirect:user/storeClose.do";
		
	}	
	
	}
	


    public ShopController() {
    }

    @RequestMapping({"storeClose.do"})
    public void select(ShopVO vo, Model m) {
        List<ShopVO> list = this.shopService.ShopList(vo);
        m.addAttribute("ShopList", list);
    }
}
