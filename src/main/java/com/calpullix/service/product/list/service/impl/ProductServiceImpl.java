package com.calpullix.service.product.list.service.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.calpullix.service.product.list.dao.ProductDAO;
import com.calpullix.service.product.list.model.Branch;
import com.calpullix.service.product.list.model.DataSheet;
import com.calpullix.service.product.list.model.DataSheetDetailDTO;
import com.calpullix.service.product.list.model.Product;
import com.calpullix.service.product.list.model.ProductBranchStatus;
import com.calpullix.service.product.list.model.ProductDTO;
import com.calpullix.service.product.list.model.ProductDetailDTO;
import com.calpullix.service.product.list.model.ProductDetailRequestDTO;
import com.calpullix.service.product.list.model.ProductHistory;
import com.calpullix.service.product.list.model.ProductList;
import com.calpullix.service.product.list.model.ProductListRequest;
import com.calpullix.service.product.list.model.ProductListResponseDTO;
import com.calpullix.service.product.list.model.ProductStatus;
import com.calpullix.service.product.list.repository.BranchRepository;
import com.calpullix.service.product.list.repository.DataSheetRepository;
import com.calpullix.service.product.list.repository.ProductBranchRepository;
import com.calpullix.service.product.list.repository.ProductHistoryRepository;
import com.calpullix.service.product.list.repository.ProductNameRowMapper;
import com.calpullix.service.product.list.repository.ProductRepository;
import com.calpullix.service.product.list.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	private static final String QUERY_PRODUCT_NAME = "SELECT id, name FROM Product WHERE name like ? ";
	
	private static final String LIKE_COMODIN = "%";

	private static final String PRODUCT_TOKEN = " ";

	private static final int IMG_WIDTH = 100;

	private static final int IMG_HEIGHT = 100;

	
	@Autowired
	private JdbcTemplate jdbcTemplate; 

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductBranchRepository productBranchRepository;

	@Autowired
	private ProductHistoryRepository productHistoryRepository;

	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private DataSheetRepository dataSheetRepository;

	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

	@Value("${app.pagination-size}")
	private Integer paginationSize;

	public boolean saveImage() throws IOException {
		log.info(":: Resize Image ");
		BufferedImage originalImage = ImageIO
				.read(new File("/Users/juancarlospedrazaalcala/Documents/Images/desodornate.png"));
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizeImageJpg = resizeImage(originalImage, type);
		ImageIO.write(resizeImageJpg, "png",
				new File("/Users/juancarlospedrazaalcala/Documents/Images/desodornate_Two.png"));
		log.info(":: Type image ", type);
		saveImageDB();
		return Boolean.TRUE;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
	}

	public boolean saveImageDB() {
		log.info(":: Saving image service ");
		final ProductDTO product = new ProductDTO();
		product.setName("Articulo primero");
		final File image = new File("/Users/juancarlospedrazaalcala/Documents/Images/desodornate_Two.png");
		log.info(":: Length image {} ", image.length());
		try {
			product.setPicture(Files.readAllBytes(image.toPath()));
			log.info(":: SAVING IMAGE: " + product.getPicture());
		} catch (Exception e) {
			log.error(":: Error {} ", e);
		}
		productDAO.saveProduct(product);
		return Boolean.TRUE;
	}

	@Override
	public ProductDTO getProduct(Integer id) {
		log.info(":: Id product {} ", id);
		return productDAO.getProduct(id);
	}
	
	@SuppressWarnings("unused")
	private ProductListResponseDTO defaultGetProductByFilters(ProductListRequest request) {
		log.info(":: Fallback method {} ", request);
		final ProductListResponseDTO result = new ProductListResponseDTO();
		result.setProductDetail(new ArrayList<>());
		return result;
	}

	//@HystrixCommand(fallbackMethod = "defaultGetProductByFilters")
	@Override
	public ProductListResponseDTO getProductByFilters(ProductListRequest request) {
		log.info(":: Get product list service {} ", request);
		List<Product> products;
		final Product idproduct;
		final Optional<Branch> idbranch = branchRepository.findById(request.getBranchId());
		Pageable pagination = PageRequest.of(request.getPage() - 1, paginationSize);
		int totalRows;
		if (StringUtils.isNotEmpty(request.getProduct())) {
			idproduct = new Product();
			idproduct.setId(getIdProduct(request.getProduct()));
			totalRows = productBranchRepository.countByBranchAndProduct(idbranch.get(), idproduct,
					ProductStatus.ACTIVE.getId());
			products = productBranchRepository.findAllProductBranchByIdbranchAndIdproduct(idbranch.get(), idproduct,
					ProductStatus.ACTIVE.getId(), pagination);
		} else {
			totalRows = productBranchRepository.countByBranch(idbranch.get(), ProductStatus.ACTIVE.getId());
			products = productBranchRepository.findAllProductBranchByIdbranch(idbranch.get(),
					ProductStatus.ACTIVE.getId(), pagination);
		}
		log.info(":: Result getProductByFilters {} {} ", totalRows, products);
		List<BigDecimal> monthlyList = new ArrayList<>();
		List<BigDecimal> dailyList = new ArrayList<>();
		BigDecimal monthlyAverage;
		BigDecimal dailyAerage;
		final Calendar initDate = Calendar.getInstance();
		final Calendar endDate = Calendar.getInstance();
		initDate.set(Calendar.YEAR, initDate.get(Calendar.YEAR) - 1);
		initDate.set(Calendar.MONTH, initDate.get(Calendar.MONTH) - 1);
		initDate.set(Calendar.DATE, 1);
		endDate.set(Calendar.MONTH, endDate.get(Calendar.MONTH) - 1);
		endDate.set(Calendar.DATE, endDate.getActualMinimum(Calendar.DATE));
		for (final Product product : products) {
			log.info(":: Product {} {} {} ", product, formatDate.format(initDate.getTime()),
					formatDate.format(endDate.getTime()));
			monthlyAverage = productBranchRepository
					.getMonthlyAverageSales(idbranch.get(), product, ProductBranchStatus.SOLD.getId(),
							formatDate.format(initDate.getTime()), formatDate.format(endDate.getTime()))
					.setScale(2, RoundingMode.HALF_UP);
			monthlyList.add(monthlyAverage);
			dailyAerage = productBranchRepository
					.getDailyAverageSales(idbranch.get(), product, ProductBranchStatus.SOLD.getId(),
							formatDate.format(initDate.getTime()), formatDate.format(endDate.getTime()))
					.setScale(2, RoundingMode.HALF_UP);
			dailyList.add(dailyAerage);
		}
		log.info(":: AVERAGE MONTHLY, DAILY: {} {} ", monthlyList, dailyList);

		return getResult(products, monthlyList, dailyList, idbranch.get().getName(), totalRows);
	}

	private Integer getIdProduct(final String product) {
		final StringTokenizer token = new StringTokenizer(product, PRODUCT_TOKEN);
		return Integer.valueOf(token.nextToken());
	}

	private ProductListResponseDTO getResult(List<Product> products, List<BigDecimal> monthlyList,
			List<BigDecimal> dailyList, String branch, int totalRows) {
		log.info(":: DTO ProductBranch {} ", products);
		final List<ProductDetailDTO> list = new ArrayList<>();
		final ProductListResponseDTO result = new ProductListResponseDTO();
		ProductDetailDTO productDetail;
		ProductHistory productHistory;
		int index = 0;
		for (final Product item : products) {
			productDetail = new ProductDetailDTO();
			productDetail.setId(item.getId());
			productDetail.setDescription(item.getDescription());
			productDetail.setName(item.getName());
			productDetail.setBrand(item.getBrand().getDescription());
			productDetail.setStatus(ProductStatus.ACTIVE.getDescription());
			productDetail.setBranch(branch);
			productHistory = productHistoryRepository.findByIdproductAndStatusvalue(item, ProductStatus.ACTIVE.getId());
			productDetail.setSalePrice(productHistory.getSaleprice());
			productDetail.setMonthlyAverageSales(monthlyList.get(index));
			productDetail.setDailyAverageSales(dailyList.get(index));
			productDetail.setWeight(item.getWeight().toString() + " " + item.getWeightunit().getDescription());
			productDetail.setTotalRows(totalRows);
			index++;
			list.add(productDetail);
		}
		;
		result.setProductDetail(list);
		log.info(":: End Up product list {} ", result);
		return result;
	}

	@Override
	public ProductDTO getProductDetail(ProductDetailRequestDTO request) {
		log.info(":: Product Detail Service {} ", request);
		final Product product = productRepository.findById(request.getId()).get();
		final ProductHistory productHistory = productHistoryRepository.findByIdproductAndStatusvalue(product,
				ProductStatus.ACTIVE.getId());
		ProductDTO result = new ProductDTO();
		result.setId(product.getId());
		result.setName(product.getName());
		result.setPictureResult(product.getImage());
		result.setIsIndividualPackaging(product.getIndividualPackaging());
		result.setIsCofeprisPermission(product.getCofeprisPermission());
		result.setIsFragileMaterial(product.getFragileMaterial());
		final Branch idbranch = new Branch();
		idbranch.setId(request.getParameter());
		int quantity = productBranchRepository.getQuanityProductsByBranch(idbranch, product,
				ProductBranchStatus.ON_SALE.getId());
		log.info(":: Quantity {} ", quantity);
		result.setQuantity(quantity);
		result.setProvider(product.getProvider().getName());
		result.setPurchasePrice(productHistory.getPurchaseprice());
		final BigDecimal netIncome = productHistory.getSaleprice().subtract(productHistory.getPurchaseprice())
				.setScale(2, RoundingMode.HALF_UP);
		log.info(":: Net Income {} {} {} ", netIncome, productHistory.getSaleprice(),
				netIncome.divide(productHistory.getSaleprice(), 2, RoundingMode.HALF_UP));
		result.setNetIncome(netIncome);
		result.setProfitabilityPercentage(netIncome.divide(productHistory.getSaleprice(), 2, RoundingMode.HALF_UP)
				.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100L)).setScale(2, RoundingMode.HALF_UP));
		result.setTaxes(productHistory.getIva());
		result.setCategory(product.getCategory().getDescription());
		result.setMeasurements(product.getMeasurements());
		result.setProductClassification(
				product.getClassification() == null ? "" : product.getClassification().getDescription());
		return result;
	}

	@Override
	public List<DataSheetDetailDTO> getDataSheetDetail(ProductDetailRequestDTO request) {
		final Product product = new Product();
		product.setId(request.getId());
		final List<DataSheet> dataSheet = dataSheetRepository.
								findAllDataSheetByIdproduct(product);
		List<DataSheetDetailDTO> result = new ArrayList<>();
		DataSheetDetailDTO itemDetail;
		for (final DataSheet item: dataSheet) {
			itemDetail = new DataSheetDetailDTO();
			itemDetail.setComponent(item.getComponent());
			itemDetail.setQuantity(item.getWeight());
			itemDetail.setUnit(item.getWeightunit().getDescription());
			result.add(itemDetail);
		}
		return result;
	}
	
	@Override
	public ProductList getProductList(ProductDetailRequestDTO request) {
		log.info("::  Request getProductList {} ", request);
		final ProductList result = new ProductList();
		List<Product> products = jdbcTemplate.query(QUERY_PRODUCT_NAME,
				new Object[] { LIKE_COMODIN + request.getName() + LIKE_COMODIN },
				new ProductNameRowMapper());
		final List<String> names = new ArrayList<>();
		if (BooleanUtils.negate(products == null)) {
			for (final Product product: products) {
				names.add(product.getId() + "  " + product.getName());
			}
		}
		result.setProducts(names);
		return result;
	}
}
