package aspire.demo.amqpsqltransaction;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "amqp_sql_tx")
@Entity
@Data
public class AmqpSqlTx {
    @Column
    @Id
    private String id;
}
