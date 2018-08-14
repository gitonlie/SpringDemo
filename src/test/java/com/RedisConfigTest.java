package com;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.entity.User;
import com.service.IUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@Transactional(transactionManager="transactionManager")
public class RedisConfigTest {

	@Autowired
	private IUserService userService;
	@Test
	public void test() throws IOException{
		//ResourceBundle resourceBundle = ResourceBundle.getBundle("log4j");
		User user = new User();
		user.setId(20L);
		user = userService.queryUserInfo(user);
		
	}
}
