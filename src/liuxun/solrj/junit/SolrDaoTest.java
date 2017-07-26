package liuxun.solrj.junit;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import liuxun.solrj.bean.Product;
import liuxun.solrj.dao.SolrDao;

public class SolrDaoTest {

    private SolrDao solrDao = new SolrDao();
    
	@Test
	public void testAddDefaultField() throws SolrServerException, IOException {
		solrDao.addDefaultField();
	}

	@Test
	public void testAddDynamicField() throws SolrServerException, IOException {
		solrDao.addDynamicField();
	}

	@Test
	public void testAddIndex() throws SolrServerException, IOException {
		for(int i=1;i<=2;i++){
			Product product=new Product();
			product.setId(String.valueOf(i));
			product.setName("苹果（Apple）iPhone 5c 16G版 3G手机（白色）电信版");
			product.setDesc("选择“购机送费版”北京用户享全新4G合约套餐资费全网最低！");
			product.setPrice(9f);
			solrDao.addIndex(product);
		}
	}

	@Test
	public void testUpdateIndex() throws IOException, SolrServerException {
		Product product=new Product();
		product.setId(String.valueOf(2));
		product.setName("毛衣的毛衣黑色毛衣");
		product.setDesc("女士专用 三点不漏..");
		product.setPrice(9f);
		solrDao.updateIndex(product);
	}

	@Test
	public void testDelIndex() throws SolrServerException, IOException {
		solrDao.delIndex("2");
	}

	@Test
	public void testFindIndex() throws SolrServerException {
		solrDao.findIndex();
	}

}
