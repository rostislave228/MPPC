package  Rostislav.DAO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Rostislav.Models.*;

@Component
public class MySQLDAO implements IDAO{
	private static Connection con = null;
	private List<Listener> listeners = new ArrayList<>();
	
	@Override
	public void addListener(Listener l) {
		listeners.add(l);
	}
	@Override
	public void removeListener(Listener l) {
		listeners.remove(l);
	}
	@Override
	public void notifyListeners(String eventType, Object entity) {
		for (Listener obs : listeners) {
			obs.onEvent(eventType, entity);
		}
	}
	@Autowired
    private MySQLDAO() throws SQLException {
    	MySQLDAO.con = MySQLDAO.getConnection();
    }

    public static Connection getConnection() throws SQLException {
        if (con == null) {
            synchronized (MySQLDAO.class) {
                if (con == null) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/printed";
                        String username = "root";
                        String password = "1111";
                        con = DriverManager.getConnection(url, username, password);
                        if (con != null) {
                            System.out.println("Connected to the database!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new SQLException("Connection failed!", ex);
                    }
                }
            }
        }
        return con;
    }
    
    public static MySQLDAO getInstance() throws SQLException {
        return new MySQLDAO();
    }

	public List<PrintedProduct> readAllProducts() {
		List<PrintedProduct> list = new java.util.ArrayList<>(List.of());
		try {
			//Newspapers
			List<Integer> ids = new java.util.ArrayList<>(List.of());
			PreparedStatement ps = con.prepareStatement(
					"SELECT p.id, p.title, p.price, n.date " +
							"FROM printedproduct p " +
							"INNER JOIN newspaper n ON p.id = n.id;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getInt(1));
				Newspaper newspaper = new Newspaper.NewspaperBuilder()
									.id(rs.getInt(1))
									.title(rs.getString(2))
									.price(rs.getDouble(3))
									.date(rs.getString(4)).build();
				list.add(newspaper);
			}

			//EBooks
			ps = con.prepareStatement(
					"SELECT p.id, p.title, p.price, b.author, e.filesize " +
							"FROM printedproduct p " +
							"INNER JOIN book b ON p.id = b.id " +
							"INNER JOIN ebook e ON b.id = e.id;");
			rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getInt(1));
				EBook.EBookBuilder eBookBuilder = new EBook.EBookBuilder();
				eBookBuilder.id(rs.getInt(1))
							.title(rs.getString(2))
							.price(rs.getDouble(3))
							.author(rs.getString(4)).build();
				EBook ebook = eBookBuilder.fileSize(rs.getDouble(5)).build();
				list.add(ebook);
			}

			//Books
			ps = con.prepareStatement(
					"SELECT p.id, p.title, p.price, b.author " +
							"FROM printedproduct p " +
							"INNER JOIN book b ON p.id = b.id;");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (ids.contains(rs.getInt(1))) {
					continue;
				} else {
					ids.add(rs.getInt(1));
					Book book = new Book.BookBuilder()
							.id(rs.getInt(1))
							.title(rs.getString(2))
							.price(rs.getDouble(3))
							.author(rs.getString(4))
							.build();
					list.add(book);
				}
			}

			//Printed Products
			ps = con.prepareStatement("SELECT p.id, p.title, p.price FROM printedproduct p;");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (ids.contains(rs.getInt(1))) {
					continue;
				} else {
					ids.add(rs.getInt(1));
					PrintedProduct printedProduct = new PrintedProduct.PrintedProductBuilder()
							.id(rs.getInt(1))
							.title(rs.getString(2))
							.price(rs.getDouble(3))
							.build();
					
					list.add(printedProduct);
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		list.sort(Comparator.comparingInt(PrintedProduct::getId));
		return list;
	}

	public void deleteProduct(int Id) {
		try {
			PreparedStatement ps = con.prepareStatement("DELETE FROM printedproduct where id = " + Id);
			ps.executeUpdate();
			System.out.println("Видалено\n");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		notifyListeners("PRINTED_PRODUCT_DELETED", Id);
	}
	public PrintedProduct getProductById(int id) {
		PrintedProduct temp = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM printedproduct WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				temp = new PrintedProduct.PrintedProductBuilder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.price(rs.getDouble("price"))
						.build();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return temp;
	}
	public void updateProduct(int Id,double Price, String Title) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE printedproduct SET title = '" + Title + "' WHERE id = " + Id);
			ps.executeUpdate();
			ps = con.prepareStatement("UPDATE printedproduct SET price = " + Price + " WHERE id = " + Id);
			ps.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		notifyListeners("PRINTED_PRODUCT_UPDATED", Id);
	}
	public void createPrintedProduct(PrintedProduct p) {
		try{
			PreparedStatement ps = con.prepareStatement("SELECT id FROM printedproduct ORDER BY id DESC LIMIT 1");
			ResultSet rs=ps.executeQuery();
			int nextId = 0;
			while (rs.next()) {
				nextId = rs.getInt(1) + 1;
			}
			String SQL = "INSERT INTO printedproduct (id, title, price)"
					+ "VALUES('" + nextId + "', '" + p.getTitle() + "', '" +
					p.getPrice() + "')";
			con.createStatement().executeUpdate(SQL);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		notifyListeners("PRINTED_PRODUCT_ADDED", p);
	}
	public void createBook(Book b) {
		try{
			PreparedStatement ps = con.prepareStatement("SELECT id FROM printedproduct ORDER BY id DESC LIMIT 1");
			ResultSet rs=ps.executeQuery();
			int nextId = 0;
			while (rs.next()) {
				nextId = rs.getInt(1) + 1;
			}
			String SQL = "INSERT INTO printedproduct (id, title, price)"
					+ "VALUES('" + nextId + "', '" + b.getTitle() + "', '" +
					b.getPrice() + "')";
			con.createStatement().executeUpdate(SQL);
			SQL = "INSERT INTO book (id, author)"
					+ "VALUES('" + nextId + "', '" + b.getAuthor() + "')";
			con.createStatement().executeUpdate(SQL);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		notifyListeners("BOOK_ADDED", b);
	}
	public void createEBook(EBook eb) {
		try{
			PreparedStatement ps = con.prepareStatement("SELECT id FROM printedproduct ORDER BY id DESC LIMIT 1");
			ResultSet rs=ps.executeQuery();
			int nextId = 0;
			while (rs.next()) {
				nextId = rs.getInt(1) + 1;
			}
			String SQL = "INSERT INTO printedproduct (id, title, price)"
					+ "VALUES('" + nextId + "', '" + eb.getTitle() + "', '" +
					eb.getPrice() + "')";
			con.createStatement().executeUpdate(SQL);
			SQL = "INSERT INTO book (id, author)"
					+ "VALUES('" + nextId + "', '" + eb.getAuthor() + "')";
			con.createStatement().executeUpdate(SQL);
			SQL = "INSERT INTO ebook (id, FileSize)"
					+ "VALUES('" + nextId + "', '" + eb.getFileSize() + "')";
			con.createStatement().executeUpdate(SQL);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		notifyListeners("EBOOK_ADDED", eb);
	}
	public void createNewsPaper(Newspaper nw) {
		try{
			PreparedStatement ps = con.prepareStatement("SELECT id FROM printedproduct ORDER BY id DESC LIMIT 1");
			ResultSet rs=ps.executeQuery();
			int nextId = 0;
			while (rs.next()) {
				nextId = rs.getInt(1) + 1;
			}
			String SQL = "INSERT INTO printedproduct (id, title, price)"
					+ "VALUES('" + nextId + "', '" + nw.getTitle() + "', '" +
					nw.getPrice() + "')";
			con.createStatement().executeUpdate(SQL);
			SQL = "INSERT INTO newspaper (id, date)"
					+ "VALUES('" + nextId + "', '" + nw.getDate() + "')";
			con.createStatement().executeUpdate(SQL);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		notifyListeners("NEWSPAPER_ADDED", nw);
	}
	@Override
	public boolean authenticate(String username, String password) {
        String SQL = "SELECT user_name FROM users WHERE user_name = ? AND pass = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
	@Override
	public void register(User user) {
		this.addUser(user);
	}
	
	@Override
    public void addUser(User user) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT id FROM printedproduct ORDER BY id DESC LIMIT 1");
			ResultSet rs=ps.executeQuery();
			int nextId = 0;
			while (rs.next()) {
				nextId = rs.getInt(1) + 1;
			}
	        String SQL = "INSERT INTO users (id, user_name, pass, roles_id)"
	        		+ "VALUES('" + nextId + "', '" + user.getUsername()  + "', '" + user.getPassword() + "', 2)";
			
	        con.createStatement().executeUpdate(SQL);
	    } catch (SQLException throwables) {
	        throwables.printStackTrace();
	    }
		notifyListeners("USER_ADDED", user);
    }
	@Override
	public User getUser(String username, String password) {
		String SQL = "SELECT * FROM users WHERE user_name = ? AND pass = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
            	User user = new User(resultSet.getInt("id"),
            			resultSet.getString("user_name"),resultSet.getString("pass"));
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
	}
	@Override
	public List<UserProduct> getProductsByUserId(int id) {
		List<UserProduct> pplist = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT prod_id, title, price, user_id, user_name, pass FROM\r\n"
            		+ "(SELECT pp.id AS prod_id, pp.title, pp.price, u.id AS user_id, user_name, pass\r\n"
            		+ "FROM users u INNER JOIN cart c ON u.id = c.users_id\r\n"
            		+ "INNER JOIN printedproduct pp ON c.pp_id = pp.id) AS sub WHERE user_id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
            	User user = new User(resultSet.getInt("user_id"),
            			resultSet.getString("user_name"),resultSet.getString("pass"));
            	PrintedProduct temp = new PrintedProduct.PrintedProductBuilder()
						.id(resultSet.getInt("prod_id"))
						.title(resultSet.getString("title"))
						.price(resultSet.getDouble("price"))
						.build();
            	
            	
            	UserProduct list = new UserProduct(user, temp);
            	pplist.add(list);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return pplist;
	}	
	
}