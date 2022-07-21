package ua.bondar.course.bondarsite.dao;

import org.springframework.stereotype.Component;
import ua.bondar.course.bondarsite.model.item.CategoryProduct;
import ua.bondar.course.bondarsite.model.item.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@Component
public class DAOProduct {

    //@Value("${spring.datasource.url}")
    private String URL;
    //@Value("${spring.datasource.username}")
    private String USER = "postgres";
    //@Value("${spring.datasource.password}")
    private String PASSWORD;

    private Connection connection;

    public List<Product> getGoods(){
        List<Product> list = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");

            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setIdPhoto(resultSet.getString("name_img"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(CategoryProduct.valueOf(resultSet.getString("category")));
                list.add(product);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Product getProductById(Long id){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id=?");
            statement.setBigDecimal(1, new BigDecimal(id));
            ResultSet resultSet = statement.executeQuery();
            Product product = null;
            while(resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setIdPhoto(resultSet.getString("name_img"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(CategoryProduct.valueOf(resultSet.getString("category")));
            }
            statement.close();
            resultSet.close();
            return product;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setProduct(Product product){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);

            PreparedStatement ps = connection.prepareStatement("INSERT INTO product(name,price,name_img,description,category)" +
                    " VALUES(?,?,?,?,?)");
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getIdPhoto());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getCategory().name());
            ps.executeUpdate();
            ps.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void deleteProductById(Long id){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
