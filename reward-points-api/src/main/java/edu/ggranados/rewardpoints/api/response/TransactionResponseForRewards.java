package edu.ggranados.rewardpoints.api.response;

import edu.ggranados.rewardpoints.api.entity.Transaction;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseForRewards extends TransactionResponse {

    @ApiModelProperty(value = "Client Identification")
    private String clientId;
    @ApiModelProperty(value = "Transaction entering date")
    private Date date;
    @ApiModelProperty(value = "Reward points applicable flag")
    private Boolean applicable;

    public TransactionResponseForRewards(Transaction t){
        this.id = t.getId();
        this.clientId = t.getClientId();
        this.amount = t.getAmount();
        this.date = t.getDate();
        this.applicable = t.getApplicable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponseForRewards that = (TransactionResponseForRewards) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
