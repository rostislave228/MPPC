package Rostislav.Config;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Rostislav.DAO.*;


@Configuration
public class DAOConfig {
	@Bean
    public DAOFactory daoFactory() {
        return new DAOFactory();
    }
	
    @Bean(name = "realDAO")
    public IDAO realDAO(DAOFactory daoFactory) {
        try {
			return DAOFactory.getDAOInstance(TypeDAO.MySQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }

    @Bean(name = "proxyDAO")
    public IDAO proxyClientDAO(@Qualifier("realDAO") MySQLDAO realDAO) {
        return new DAOProxy(realDAO, "User");
    }
}
