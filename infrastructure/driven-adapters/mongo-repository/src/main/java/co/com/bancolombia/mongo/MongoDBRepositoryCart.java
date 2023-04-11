package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.data.CartData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryCart extends ReactiveMongoRepository<CartData, String> {
}
