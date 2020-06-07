package com.xxxx.order.service.impl;

import com.xxxx.common.enums.OrderStatus;
import com.xxxx.common.enums.PayStatus;
import com.xxxx.common.enums.PromTypeStatus;
import com.xxxx.common.enums.SendStatus;
import com.xxxx.common.enums.ShipStatus;
import com.xxxx.common.pojo.Admin;
import com.xxxx.common.result.BaseResult;
import com.xxxx.order.mapper.OrderGoodsMapper;
import com.xxxx.order.mapper.OrderMapper;
import com.xxxx.order.pojo.Order;
import com.xxxx.order.pojo.OrderExample;
import com.xxxx.order.pojo.OrderGoods;
import com.xxxx.order.service.OrderService;
import com.xxxx.rpc.pojo.CartVo;
import com.xxxx.rpc.vo.CartResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderGoodsMapper orderGoodsMapper;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Value("${redis.order.increment}")
	private String orderIncrement;

	/**
	 * 保存订单
	 *
	 * @param cartResult
	 * @param admin
	 * @return
	 */
	@Override
	public BaseResult orderSave(CartResult cartResult, Admin admin) {
		Order order = new Order();
		//订单编号，格式 shop_年月日时分秒_自增id
		String orderSn = "shop_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + "_" + getIncrement(orderIncrement);
		order.setOrderSn(orderSn);
		//用户id
		order.setUserId(admin.getAdminId().intValue());
		//订单状态
		order.setOrderStatus(OrderStatus.no_confirm.getStatus());
		//发货状态
		order.setShippingStatus(ShipStatus.no_send.getStatus());
		//支付状态
		order.setPayStatus(PayStatus.no_pay.getStatus());
		//商品总价
		order.setGoodsPrice(cartResult.getTotalPrice());
		//应付金额
		order.setOrderAmount(cartResult.getTotalPrice());
		//订单总价
		order.setTotalAmount(cartResult.getTotalPrice());
		//下单时间
		Long addTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
		order.setAddTime(addTime.intValue());
		int result = orderMapper.insertSelective(order);
		//存储成功，存订单商品表
		if (result == 1) {
			List<OrderGoods> orderGoodsList = new ArrayList<>();
			List<CartVo> cartList = cartResult.getCartList();
			for (CartVo cartVo : cartList) {
				OrderGoods orderGoods = new OrderGoods();
				//订单ID
				orderGoods.setOrderId(order.getOrderId());
				//商品ID
				orderGoods.setGoodsId(cartVo.getGoodsId());
				//商品名称
				orderGoods.setGoodsName(cartVo.getGoodsName());
				//商品数量
				orderGoods.setGoodsNum(cartVo.getGoodsNum().shortValue());
				//商品价格
				orderGoods.setMarketPrice(cartVo.getMarketPrice());
				//订单方式
				orderGoods.setPromType(PromTypeStatus.normal.getStatus());
				//发货状态
				orderGoods.setIsSend(SendStatus.no_pay.getStatus());
				orderGoodsList.add(orderGoods);
			}
			//批量插入
			result = orderGoodsMapper.insertOrderGoodsBatch(orderGoodsList);
			if (result > 0) {
				BaseResult baseResult = BaseResult.success();
				baseResult.setMessage(orderSn);
				return baseResult;
			}
		}
		return BaseResult.error();
	}


	/**
	 * 根据订单号查询订单
	 * @param orderSn
	 * @return
	 */
	@Override
	public Order selectOrderByOrderSn(String orderSn) {
		OrderExample example = new OrderExample();
		example.createCriteria().andOrderSnEqualTo(orderSn);
		List<Order> list = orderMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)||list.size()>1){
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取redis自增id
	 *
	 * @param key
	 * @return
	 */
	private Long getIncrement(String key) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		return entityIdCounter.getAndIncrement();
	}
}