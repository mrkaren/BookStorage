package am.itspace.bookStorage.storage;


import am.itspace.bookStorage.db.DBConnectionProvider;
import am.itspace.bookStorage.model.Author;
import am.itspace.bookStorage.model.Book;
import am.itspace.bookStorage.util.DateUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private AuthorService authorService = new AuthorService();

    public void add(Book book) {
        String sql = """
                INSERT INTO book(title, price,qty,author_id,created_at)
                VALUES ('%s', '%f', '%d','%d','%s');
                """.formatted(
                book.getTitle(),
                book.getPrice(),
                book.getQty(),
                book.getAuthor().getId(),
                DateUtil.fromDateToSqlDateTimeString(book.getCreatedAt()));

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int anInt = generatedKeys.getInt(1);
                book.setId(anInt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> getAllBooks() {
        String sql = "SELECT * from book";
        List<Book> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQty(resultSet.getInt("qty"));
                book.setCreatedAt(DateUtil.fromSqlStringToDateTime(resultSet.getString("created_at")));
                book.setAuthor(authorService.getAuthorById(resultSet.getInt("author_id")));
                result.add(book);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> searchByBookName(String keyword) {
        String sql = "SELECT * from book WHERE title LIKE '%" + keyword + "%'";
        List<Book> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQty(resultSet.getInt("qty"));
                book.setCreatedAt(DateUtil.fromSqlStringToDateTime(resultSet.getString("created_at")));
                book.setAuthor(authorService.getAuthorById(resultSet.getInt("author_id")));
                result.add(book);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Book getBookById(int id) {
        String sql = "SELECT * from book WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQty(resultSet.getInt("qty"));
                book.setCreatedAt(DateUtil.fromSqlStringToDateTime(resultSet.getString("created_at")));
                book.setAuthor(authorService.getAuthorById(resultSet.getInt("author_id")));
                return book;
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBook(int bookId) {
        String sql = "DELETE FROM book WHERE id = " + bookId;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> searchByPrice(double min, double max) {
        String sql = "SELECT * from book WHERE price BETWEEN " + min + " AND " + max;
        List<Book> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQty(resultSet.getInt("qty"));
                book.setCreatedAt(DateUtil.fromSqlStringToDateTime(resultSet.getString("created_at")));
                book.setAuthor(authorService.getAuthorById(resultSet.getInt("author_id")));
                result.add(book);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> searchByAuthor(Author author) {
        String sql = "SELECT * from book WHERE author_id = " + author.getId();
        List<Book> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQty(resultSet.getInt("qty"));
                book.setCreatedAt(DateUtil.fromSqlStringToDateTime(resultSet.getString("created_at")));
                book.setAuthor(authorService.getAuthorById(resultSet.getInt("author_id")));
                result.add(book);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
