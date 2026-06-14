package fei.vehicleservice.config;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author clemente
 */
public class MyBatisUtil {

    private static final String RESOURCE = "mybatis/mybatis-config.xml";
    private static final String ENVIRONMENT = "development";

    public static SqlSession getSession() {
        SqlSession session = null;
        try {
            Reader reader = Resources.getResourceAsReader(RESOURCE);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, ENVIRONMENT);
            session = sqlMapper.openSession();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return session;
    }
}
