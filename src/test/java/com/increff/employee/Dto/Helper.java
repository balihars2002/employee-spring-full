//public class BrandDtoTest extends AbstractUnitTest{
//
//    @Autowired
//    BrandDto brandDto;
//
//
//
//    @Test
//    public void addBrandTest() throws APIException {
//        BrandForm form = new BrandForm();
//        form.setName("brand");
//        form.setCategory("category");
//        form.setDisable(true);
//        form.setAddress("address");
//        brandDto.addBrand(form);
//        List<BrandData> data = brandDto.getAll();
//        assertEquals("brand",data.get(0).getName());
//        assertEquals("category",data.get(0).getCategory());
//        assertEquals("address",data.get(0).getAddress());
//        assertEquals(true,data.get(0).isDisable());
//    }
//    //    @Test
////    public void checkBrandAndCategoryTest()  {
////            Boolean flag = false;
////            String name = null;
////            String category = null;
////            try{
////                brandDto.checkBrandAndCategory(name,category);
////            }catch (APIException e) {
////                flag = true;
////            }
////            assertEquals(true,flag);
////    }
//    @Test
//    public void getBrandTest() throws APIException {
//        BrandForm form = new BrandForm();
//        form.setName("brand");
//        form.setCategory("category");
//        form.setDisable(true);
//        form.setAddress("address");
//        brandDto.addBrand(form);
//        List<BrandData> dataList = brandDto.getAll();
//        BrandData data = brandDto.getBrand(dataList.get(0).getId());
//        assertEquals("brand",data.getName());
//        assertEquals("category",data.getCategory());
//        assertEquals("address",data.getAddress());
//        assertEquals(true,data.isDisable());
//        data = brandDto.getBrand("brand","category");
//        assertEquals("brand",data.getName());
//        assertEquals("category",data.getCategory());
//        assertEquals("address",data.getAddress());
//        assertEquals(true,data.isDisable());
//    }
//    @Test
//    public void getAllTest() throws APIException {
//        BrandForm firstForm = new BrandForm();
//        firstForm.setName("brand A");
//        firstForm.setCategory("category A");
//        firstForm.setDisable(true);
//        firstForm.setAddress("address A");
//        brandDto.addBrand(firstForm);
//        BrandForm secondForm = new BrandForm();
//        secondForm.setName(" brand B  ");
//        secondForm.setCategory("  category B");
//        secondForm.setDisable(true);
//        secondForm.setAddress("  address B");
//        brandDto.addBrand(secondForm);
//        List<BrandData> data = brandDto.getAll();
//        assertEquals(2,data.size());
//        assertEquals("brand a",data.get(0).getName());
//        assertEquals("category a",data.get(0).getCategory());
//        assertEquals("address a",data.get(0).getAddress());
//        assertEquals(true,data.get(0).isDisable());
//        assertEquals("brand b",data.get(1).getName());
//        assertEquals("category b",data.get(1).getCategory());
//        assertEquals("address b",data.get(1).getAddress());
//        assertEquals(true,data.get(1).isDisable());
//
//    }
//
//    @Test
//    public void deleteBrandTest() throws APIException {
//        BrandForm form = new BrandForm();
//        form.setName("brand");
//        form.setCategory("category");
//        form.setDisable(true);
//        form.setAddress("address");
//        brandDto.addBrand(form);
//        List<BrandData> dataList = brandDto.getAll();
//        brandDto.deleteBrand(dataList.get(0).getId());
//        dataList = brandDto.getAll();
//        assertEquals(0,dataList.size());
//    }
//
//    @Test
//    public void updateBrandTest() throws APIException {
//        BrandForm form = new BrandForm();
//        form.setName("brand");
//        form.setCategory("category");
//        form.setDisable(true);
//        form.setAddress("address");
//        brandDto.addBrand(form);
//        List<BrandData> dataList = brandDto.getAll();
//        int id = dataList.get(0).getId();
//        form.setAddress("new Address");
//        brandDto.updateBrand(id,form);
//        BrandData data = brandDto.getBrand(id);
//        assertEquals(form.getAddress(),data.getAddress());
//    }
//
////    @Test
////    public void getCheckTest() throws APIException {
////        String name = "name";
////        String category = "category";
////        Boolean flag = false;
////        try{
////            brandDto.getCheck(name,category);
////        } catch (APIException e) {
////            flag = true;
////        }
////        assertEquals(true,flag);
////
////        flag = false;
////        try{
////            brandDto.getCheck(1);
////        } catch (APIException e) {
////            flag = true;
////        }
////        assertEquals(true,flag);
////    }
//
//
//
//}
//4:04
//@Before
//public void init() throws APIException {
//        BrandForm form = new BrandForm();
//        form.setName("brand");
//        form.setCategory("category");
//        form.setDisable(true);
//        form.setAddress("address");
//        brandDto.addBrand(form);
//        ProductForm productForm = new ProductForm();
//        productForm.setBarcode(" barcode ");
//        productForm.setBrand_name(" brand ");
//        productForm.setCategory(" category ");
//        productForm.setName(" product ");
//        productForm.setMrp(1.0);
//        productDto.add(productForm);
//        }
//        4:04
//@Test
//public void addCorrectProductTest() throws APIException {
//        List<ProductData> list = productDto.getAll();
//        assertEquals("barcode",list.get(0).getBarcode());
//        assertEquals("product",list.get(0).getName());
//        assertEquals("brand",list.get(0).getBrand_name());
//        assertEquals("category",list.get(0).getCategory());
//        assertEquals((Double) 1.0, list.get(0).getMrp());
//        }
//
//@Test
//public void addWrongProductTest() throws APIException {
//        ProductForm productForm = new ProductForm();
//        productForm.setBarcode("  ");
//        productForm.setBrand_name(" brand ");
//        productForm.setCategory(" category ");
//        productForm.setName(" product ");
//        productForm.setMrp(1.0);
//        Boolean flag = false;
//        try {
//        productDto.add(productForm);
//        }catch (APIException e){
//        flag = true;
//        }
//        assertEquals(true,flag);
//        }
//
//@Test
//public void getByCorrectIdTest() throws APIException {
//        List<ProductData> list = productDto.getAll();
//        int id = list.get(0).getId();
//        ProductData data = productDto.get(id);
//        assertEquals("barcode",data.getBarcode());
//        assertEquals("product",data.getName());
//        assertEquals("brand",data.getBrand_name());
//        assertEquals("category",data.getCategory());
//        assertEquals((Double) 1.0, data.getMrp());
//        }
//
//@Test
//public void getByWrongIdTest() throws APIException {
//        int id = 0;
//        Boolean flag = false;
//        try{
//        ProductData data = productDto.get(id);
//        } catch (APIException e) {
//        flag = true;
//        }
//        assertEquals(true,flag);
//        }
//
//@Test
//public void getByCorrectBarcodeTest() throws APIException {
//        List<ProductData> list = productDto.getAll();
//        String barcode = list.get(0).getBarcode();
//        ProductData data = productDto.get(barcode);
//        assertEquals("barcode",data.getBarcode());
//        assertEquals("product",data.getName());
//        assertEquals("brand",data.getBrand_name());
//        assertEquals("category",data.getCategory());
//        assertEquals((Double) 1.0, data.getMrp());
//        }
//
//@Test
//public void getByWrongBarcodeTest() throws APIException {
//        String barcode = "wrong barcode";
//        Boolean flag = false;
//        try{
//        ProductData data = productDto.get(barcode);
//        } catch (APIException e) {
//        flag = true;
//        }
//        assertEquals(true,flag);
//        }
//
//@Test
//public void deleteProductTest() throws APIException {
//        List<ProductData> list = productDto.getAll();
//        int id = list.get(0).getId();
//        productDto.delete(id);
//        list = productDto.getAll();
//        assertEquals(0,list.size());
//        }
//
//@Test
//public void updateProductTest() throws APIException {
//        List<ProductData> list = productDto.getAll();
//        int id = list.get(0).getId();
//        String newProductName = " New Product ";
//        String newBarcode = " New Barcode ";
//        Double newMrp = 2.0;
//        ProductForm form = new ProductForm();
//        form.setName(newProductName);
//        form.setBarcode(newBarcode);
//        form.setMrp(newMrp);
//        productDto.update(id,form);
//        ProductData data = productDto.get(id);
//        assertEquals("new barcode",data.getBarcode());
//        assertEquals("new product",data.getName());
//        assertEquals("brand",data.getBrand_name());
//        assertEquals("category",data.getCategory());
//        assertEquals((Double) 2.0, data.getMrp());
//
//        }