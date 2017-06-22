package test.com.rhb.sas2.repository;

import org.junit.Test;

import com.rhb.sas2.domain.Stock;
import com.rhb.sas2.repository.StockCrepository;
import com.rhb.sas2.repository.StockQrepository;
import com.rhb.sas2.repository.dao.StockCrepository_Mongodb;
import com.rhb.sas2.repository.dao.StockQrepository_Mongodb;

public class TestStockMongodb {
	
	@Test
	public void test(){
		StockCrepository cud = new StockCrepository_Mongodb();
		StockQrepository query = new StockQrepository_Mongodb();
		
		String stockNo = "300022";
		
		cud.create(stockNo); //增加
		
		Stock stock = cud.getStock(stockNo); //查找
		if(stock !=null){
			System.out.println(stock);
		}else{
			System.out.println("can NOT find stock of stockNo=" + stockNo);
		}
		
		String stockName_new = "吉峰农机";
		cud.rename(stockNo, stockName_new); //修改
		stock = cud.getStock(stockNo); //查找
		if(stock !=null){
			System.out.println(stock);
		}else{
			System.out.println("can NOT find stock of stockNo=" + stockNo);
		}
		
		
		 
		cud.delete(stockNo); // 删除
	}
}
