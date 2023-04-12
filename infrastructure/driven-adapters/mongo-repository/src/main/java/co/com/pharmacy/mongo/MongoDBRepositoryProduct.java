package co.com.pharmacy.mongo;

import co.com.pharmacy.mongo.data.ProductData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryProduct extends ReactiveMongoRepository<ProductData, String>{
}
