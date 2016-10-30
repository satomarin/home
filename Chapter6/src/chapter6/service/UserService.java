package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;

import chapter6.beans.User;
import chapter6.dao.UserDao;
import chapter6.utils.CipherUtil;

public class UserService {

	public void register(User user){

		Connection connection = null;
		try {
			connection = getConnection();
			System.out.println(user.getPassword());

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


}
