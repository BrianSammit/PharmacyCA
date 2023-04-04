package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.data.ProductData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepository extends ReactiveMongoRepository<ProductData, String>{
}
