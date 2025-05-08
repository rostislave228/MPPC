package Rostislav.DAO;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DAOFactory {
	private static IDAO dao = null;
    public static IDAO getDAOInstance(TypeDAO type) throws SQLException{
           	if(type == TypeDAO.MySQL){
				dao = MySQLDAO.getInstance();
			} 
		return dao;
	}       
}