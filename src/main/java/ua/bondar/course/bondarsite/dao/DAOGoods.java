package ua.bondar.course.bondarsite.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.bondar.course.bondarsite.model.CategoryProduct;
import ua.bondar.course.bondarsite.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class DAOGoods {

    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASSWORD = "1234";
    private Connection connection;

    public List<Product> getGoods(){
        List<Product> list = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM goods");

            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setNameImg(resultSet.getString("urlimage"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(CategoryProduct.getCategoryProduct(resultSet.getString("category")));
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM goods WHERE id=?");
            statement.setBigDecimal(1, new BigDecimal(id));
            ResultSet resultSet = statement.executeQuery();
            Product product = null;
            while(resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setNameImg(resultSet.getString("urlimage"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(CategoryProduct.getCategoryProduct(resultSet.getString("category")));
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

            PreparedStatement ps = connection.prepareStatement("INSERT INTO goods(name,price,urlimage,description,category)" +
                    " VALUES(?,?,?,?,?)");
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getNameImg());
            ps.setString(4, product.getDescription());
            ps.setString(5, CategoryProduct.getStringCategory(product.getCategory()));
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM goods WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
