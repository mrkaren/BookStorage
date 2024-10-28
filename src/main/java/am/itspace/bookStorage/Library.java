package am.itspace.bookStorage;


import am.itspace.bookStorage.model.Author;
import am.itspace.bookStorage.model.Book;
import am.itspace.bookStorage.model.Gender;
import am.itspace.bookStorage.storage.AuthorService;
import am.itspace.bookStorage.storage.BookService;
import am.itspace.bookStorage.util.DateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Library implements LibraryCommands {

    private static Scanner scanner = new Scanner(System.in);
    private static BookService bookService = new BookService();
    private static AuthorService authorService = new AuthorService();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            LibraryCommands.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_AUTHOR:
                    addAuthor();
                    break;
                case PRINT_ALL_BOOKS:
                    System.out.println(bookService.getAllBooks());
                    break;
                case PRINT_ALL_AUTHORS:
                    System.out.println(authorService.getAllAuthors());
                    break;
                case SEARCH_BOOK_BY_NAME:
                    searchBookByName();
                    break;
                case UPDATE_BOOK:
                    updateBook();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case SEARCH_BY_PRICE:
                    searchBookByPrice();
                    break;
                case SEARCH_BOOK_BY_AUTHOR:
                    searchBookByAuthor();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }

    }

    private static void searchBookByAuthor() {
        System.out.println(authorService.getAllAuthors());
        System.out.println("Please choose author ID");
        int authorId = Integer.parseInt(scanner.nextLine());
        Author author = authorService.getAuthorById(authorId);
        if (author != null) {
            bookService.searchByAuthor(author);
        }
    }

    private static void addAuthor() {
        System.out.println("Please input name, surname, phone, dateOfBirthday(01-10-2000), gender(MALE, FEMALE)");
        String authorDataStr = scanner.nextLine();
        String[] authorDataArr = authorDataStr.split(",");
        if (authorDataArr.length == 5) {
            try {
                Author author = new Author();
                author.setName(authorDataArr[0]);
                author.setSurname(authorDataArr[1]);
                author.setPhone(authorDataArr[2]);
                author.setDateOfBirthday(DateUtil.fromStringToDate(authorDataArr[3]));
                author.setGender(Gender.valueOf(authorDataArr[4]));
                authorService.add(author);
                System.out.println("Author added!!!");
            } catch (ParseException e) {
                System.err.println("Date of Birthday is incorrect!");
            } catch (IllegalArgumentException e) {
                System.out.println("Gender is invalid. Allowed values are MALE or FEMALE");
            }
        }

    }

    private static void searchBookByPrice() {
        System.out.println("Please input min-max prices");
        String pricesStr = scanner.nextLine();
        String[] pricesArray = pricesStr.split("-");
        if (pricesArray.length == 2) {
            try {
                double min = Double.parseDouble(pricesArray[0]);
                double max = Double.parseDouble(pricesArray[1]);
                bookService.searchByPrice(min, max);
            } catch (NumberFormatException e) {
                System.out.println("Please input only digits");
            }
        }
    }

    private static void deleteBook() {
        System.out.println(bookService.getAllBooks());
        System.out.println("Please input book ID");
        int bookId = Integer.parseInt(scanner.nextLine());
        Book bookById = bookService.getBookById(bookId);
        if (bookById == null) {
            System.out.println("Book with " + bookId + " id does not exists!!!");
            return;
        }
        bookService.deleteBook(bookId);
    }

    private static void updateBook() {
        System.out.println(bookService.getAllBooks());
        System.out.println("Please input book ID");
        int bookId = Integer.parseInt(scanner.nextLine());
        Book bookById = bookService.getBookById(bookId);
        if (bookById != null) {
            System.out.println(authorService.getAllAuthors());
            System.out.println("Please choose author ID");
            int authorId = Integer.parseInt(scanner.nextLine());
            Author author = authorService.getAuthorById(authorId);
            if (author != null) {
                System.out.println("Please input book's new title");
                String title = scanner.nextLine();
                System.out.println("Please input book's new qty");
                String qtyStr = scanner.nextLine();
                int qty = Integer.parseInt(qtyStr);
                System.out.println("Please input book's new price");
                String priceStr = scanner.nextLine();
                if (title != null && !title.isEmpty()) {
                    bookById.setTitle(title);
                }
                bookById.setAuthor(author);
                if (priceStr != null && !priceStr.isEmpty()) {
                    bookById.setPrice(Double.parseDouble(priceStr));
                }
                if (!qtyStr.isEmpty()) {
                    bookById.setQty(qty);
                }

                System.out.println("Update was successfully");
            }
        }
    }

    private static void searchBookByName() {
        System.out.println("Please input book keyword");
        String keyword = scanner.nextLine();
        bookService.searchByBookName(keyword);
    }

    private static void addBook() {
        System.out.println(authorService.getAllAuthors());
        System.out.println("Please choose author ID");
        int authorId = Integer.parseInt(scanner.nextLine());
        Author author = authorService.getAuthorById(authorId);
        if (author != null) {
            System.out.println("Please input book title");
            String title = scanner.nextLine();
            System.out.println("Please input book price");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.println("Please input qty");
            int qty = Integer.parseInt(scanner.nextLine());
            Book book = new Book(title, author, price, qty, new Date());
            bookService.add(book);
            System.out.println("Book added!");

        } else {
            System.out.println("Wrong author id");
        }


    }
}
