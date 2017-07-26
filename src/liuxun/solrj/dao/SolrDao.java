package liuxun.solrj.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import liuxun.solrj.bean.Product;

/**
 * 使用solrJ 向solr 提交请求，增删改查， solrJ 底层页是发送http 协议...
 * 
 * @author liuxun
 */
public class SolrDao {
	// 在solr 当中有一些默认的字段和动态字段规则，配置在文件/example/solr/collection1/conf/schema.xml

	// 添加默认索引属性
	public void addDefaultField() throws SolrServerException, IOException {
		// 声明要连接solr服务器的地址
		String url = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(url);
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "默认情况下必须添加的字段，用来区分文档的唯一标识");
		doc.addField("name", "默认的名称属性字段");
		doc.addField("description", "默认的表示描述信息的字段");
		solr.add(doc);

		solr.commit();
	}

	// 添加动态索引属性
	public void addDynamicField() throws SolrServerException, IOException {
		// 声明要连接solr服务器的地址
		String url = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(url);

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "adwweqwewe");
		doc.addField("nam_s", "动态字段的StringField类型格式为*_s");
		doc.addField("desc_s", "动态字段的TextField类型格式为*_t");
		solr.add(doc);
		solr.commit();
	}

	// 添加索引
	public void addIndex(Product product) throws SolrServerException, IOException {
		// 声明要连接solr服务器的地址
		String url = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(url);
		solr.addBean(product);
		solr.commit();
	}

	// 更新索引
	public void updateIndex(Product product) throws IOException, SolrServerException {
		// 声明要连接solr服务器的地址
		String url = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(url);
		solr.addBean(product);
		solr.commit();
	}

	// 删除索引
	public void delIndex(String id) throws SolrServerException, IOException {
		// 声明要连接solr服务器的地址
		String url = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(url);
		solr.deleteById(id);
		// solr.deleteByQuery("id:*");
		solr.commit();
	}

	// 查找索引
	public void findIndex() throws SolrServerException {
		// 声明要连接solr服务器的地址
		String url = "http://localhost:8983/solr";
		SolrServer solr = new HttpSolrServer(url);

		// 查询条件
		SolrQuery solrParams = new SolrQuery();
		solrParams.setStart(0);
		solrParams.setRows(10);
		solrParams.setQuery("name:苹果 +description:全新4G");
		// 开启高亮
		solrParams.setHighlight(true);
		solrParams.setHighlightSimplePre("<font color='red'>");
		solrParams.setHighlightSimplePost("</font>");

		// 设置高亮的字段
		solrParams.setParam("hl.fl", "name,description");
		// SolrParams是SolrQuery的子类
		QueryResponse queryResponse = solr.query(solrParams);

		// (一)获取查询的结果集合
		SolrDocumentList solrDocumentList = queryResponse.getResults();

		// (二)获取高亮的结果集
		// 第一个Map的键是文档的ID，第二个Map的键是高亮显示的字段名
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println("=====" + solrDocument.toString());
			Map<String, List<String>> fieldsMap = highlighting.get(solrDocument.get("id"));
			List<String> highname = fieldsMap.get("name");
			List<String> highdesc = fieldsMap.get("description");
			System.out.println("highname======" + highname);
			System.out.println("highdesc=====" + highdesc);
		}

		// (三) 将响应结果封装到Bean
		List<Product> products = queryResponse.getBeans(Product.class);

		System.out.println(products + "+++++++++++");
		for (Product product : products) {
			System.out.println(product);
		}
	}
}
