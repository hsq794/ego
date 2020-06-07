package com.xxxx.protal;
import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.pojo.Admin;
import com.xxxx.rpc.pojo.CartVo;
import com.xxxx.rpc.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车测试类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@SpringBootTest
public class CartServiceTest {

	@Reference(interfaceClass = CartService.class)
	private CartService cartService;

	/**
	 * 添加购物车
	 */
	@Test
	public void testAddCart(){
		Admin admin = new Admin();
		admin.setAdminId((short) 1);
		CartVo cartVo = new CartVo();
		cartVo.setGoodsId(23456);
		cartVo.setGoodsName("JAVA核心技术卷");
		cartVo.setMarketPrice(new BigDecimal("300"));
		cartVo.setGoodsNum(20);
		cartVo.setAddTime(new Date());
		cartService.addCart(cartVo,admin);
	}

	/**
	 * 查询购物车数量
	 */
	@Test
	public void testGetCartNum(){
		Admin admin = new Admin();
		admin.setAdminId((short) 1);
		Integer num = cartService.getCartNum(admin);
		System.out.println(num);
	}

}