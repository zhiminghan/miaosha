package com.imooc.miaosha.service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.vo.GoodsVo;

@Service
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;

	ReentrantLock lockA=new ReentrantLock();
	ReentrantLock lockB=new ReentrantLock();


	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public boolean reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		int ret = goodsDao.reduceStock(g);
		return ret > 0;
	}

	public void resetStock(List<GoodsVo> goodsList) {
		for(GoodsVo goods : goodsList ) {
			MiaoshaGoods g = new MiaoshaGoods();
			g.setGoodsId(goods.getId());
			g.setStockCount(goods.getStockCount());
			goodsDao.resetStock(g);
		}
	}

//java deadlock
	public void a(){

		try {
			lockA.lock();
			Thread.sleep(5000);
			lockB.lock();

		}catch (Throwable e){

		}finally {
			lockB.unlock();
			lockA.unlock();
		}

	}

	public void b(){

		try {
			lockB.lock();
			Thread.sleep(10000);
			lockA.lock();

		}catch (Throwable e){

		}finally {
			lockA.unlock();
			lockB.unlock();
		}

	}

	public static void main(String[] args) {
		final GoodsService goodsService=new GoodsService();

		new Thread(new Runnable() {
			@Override
			public void run() {
				goodsService.a();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				goodsService.b();
			}
		}).start();
	}

}
