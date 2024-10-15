package sptech.school;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexBanco {
    private JdbcTemplate conexBanco;

    public ConexBanco(){
        BasicDataSource configBanco = new BasicDataSource();

        configBanco.setUrl("jdbc:mysql://100.26.111.8:3306/aevus");
        configBanco.setUsername("devaevus");
        configBanco.setPassword("aevus100");

        conexBanco = new JdbcTemplate();
    }

    public JdbcTemplate getConexaoBanco(){
        return conexBanco;
    }

}
