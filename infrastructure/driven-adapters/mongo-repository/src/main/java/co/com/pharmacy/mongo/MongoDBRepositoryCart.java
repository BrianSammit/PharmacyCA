package co.com.pharmacy.mongo;

import co.com.pharmacy.mongo.data.CartData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryCart extends ReactiveMongoRepository<CartData, String> {
}
