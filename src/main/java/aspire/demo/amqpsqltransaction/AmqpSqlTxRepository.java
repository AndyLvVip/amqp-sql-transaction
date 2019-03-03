package aspire.demo.amqpsqltransaction;

import org.springframework.data.repository.CrudRepository;

public interface AmqpSqlTxRepository extends CrudRepository<AmqpSqlTx, String> {
}
