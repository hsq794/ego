package com.xxxx.rpc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xxxx.common.pojo.Admin;
import com.xxxx.common.result.BaseResult;
import com.xxxx.common.utils.JsonUtil;
import com.xxxx.rpc.pojo.CartVo;
import com.xxxx.rpc.service.CartService;
import com.xxxx.rpc.vo.CartResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service(interfaceClass = CartService.class)
@Component
public class CartServiceImpl implements CartService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Value("${user.cart}")
	private String userCart;

	private HashOperations<String, String, String> hashOperations = null;

	/**
	 * 加入购物车
	 *
	 * @param cartVo
	 * @param admin
	 * @return
	 */
	@Override
	public BaseResult addCart(CartVo cartVo, Admin admin) {
		//如果用户信息不存在，直接返回
		if (null == admin || null == admin.getAdminId()) {
			return BaseResult.error();
		}
		Short userId = admin.getAdminId();
		//查询当前用户的购物车信息
		hashOperations = redisTemplate.opsForHash();
		Map<String, String> cartMap = hashOperations.entries(userCart + ":" + admin.getAdminId());
		//如果购物车信息不为空，修改购物车信息
		if (!CollectionUtils.isEmpty(cartMap)){
			//根据商品id获取购物车信息
			String cartStr = cartMap.get(String.valueOf(cartVo.getGoodsId()));
			//如果商品存在，修改商品数量和价格
			if (!StringUtils.isEmpty(cartStr)){
				//获取购物车信息
				CartVo cart = JsonUtil.jsonStr2Object(cartStr, CartVo.class);
				//修改商品数量和价格
				cart.setGoodsNum(cart.getGoodsNum()+cartVo.getGoodsNum());
				cart.setMarketPrice(cartVo.getMarketPrice());
				//重新添加至map，覆盖之前的商品信息
				cartMap.put(String.valueOf(cart.getGoodsId()),JsonUtil.object2JsonStr(cart));
			}else {
				//如果商品不存在，新增购物车信息
				cartMap.put(String.valueOf(cartVo.getGoodsId()),JsonUtil.object2JsonStr(cartVo));
			}
		}else {
			//如果购物车信息为空，新增购物车信息
			cartMap = new HashMap<>();
			cartMap.put(String.valueOf(cartVo.getGoodsId()),JsonUtil.object2JsonStr(cartVo));
		}
		hashOperations.putAll(userCart+":"+userId,cartMap);
		return BaseResult.success();
	}


	/**
	 * 获取购物车数量
	 *
	 * @param admin
	 * @return
	 */
	@Override
	public Integer getCartNum(Admin admin) {
		//如果用户信息不存在，直接返回
		if (null == admin || null == admin.getAdminId()) {
			return 0;
		}
		hashOperations = redisTemplate.opsForHash();
		Map<String, String> cartMap = hashOperations.entries(userCart + ":" + admin.getAdminId());
		Integer result = 0;
		//如果购物车信息不为空，累加数量
		if (!CollectionUtils.isEmpty(cartMap)) {
			result = cartMap.values().stream().mapToInt(e -> {
				CartVo cartVo = JsonUtil.jsonStr2Object(e, CartVo.class);
				return cartVo.getGoodsNum();
			}).sum();
		}
		return result;
	}

	/**
	 * 获取购物车列表
	 * @param admin
	 * @return
	 */
	@Override
	public CartResult getCartList(Admin admin) {
		//如果用户信息不存在，直接返回
		if (null == admin || null == admin.getAdminId()) {
			return null;
		}
		CartResult cartResult = null;
		hashOperations = redisTemplate.opsForHash();
		Map<String, String> cartMap = hashOperations.entries(userCart + ":" + admin.getAdminId());
		//判断是否存在购物车信息
		if (!CollectionUtils.isEmpty(cartMap)){
			//如果存在
			cartResult = new CartResult();
			//购物车列表
			List<CartVo> cartList =
					cartMap.values().stream().map(e -> JsonUtil.jsonStr2Object(e, CartVo.class)).collect(Collectors.toList());
			//总价
			BigDecimal totalPrice =
					cartList.stream().map(e -> e.getMarketPrice().multiply(new BigDecimal(String.valueOf(e.getGoodsNum())))).reduce(BigDecimal.ZERO, BigDecimal::add);
			//保留小数点后2位，四舍五入
			totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
			cartResult.setCartList(cartList);
			cartResult.setTotalPrice(totalPrice);
		}
		return cartResult;
	}

	/**
	 * 清除购物车
	 * @param admin
	 * @return
	 */
	@Override
	public BaseResult clearCart(Admin admin) {
		//如果用户信息不存在，直接返回
		if (null == admin || null == admin.getAdminId()) {
			return null;
		}
		hashOperations = redisTemplate.opsForHash();
		Map<String, String> cartMap = hashOperations.entries(userCart + ":" + admin.getAdminId());
		if (CollectionUtils.isEmpty(cartMap)){
			return BaseResult.error();
		}
		//清除redis
		redisTemplate.delete(userCart+":"+admin.getAdminId());
		return BaseResult.success();
	}
}