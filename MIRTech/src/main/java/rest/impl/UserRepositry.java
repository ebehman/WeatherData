package rest.impl;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import rest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositry extends CassandraRepository<User,String> {
    @Query("truncate table \"USER\"")
    void truncate();
    User findByuserID(String id);
}