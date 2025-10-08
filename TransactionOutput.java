package MyBlockChain;

import java.security.PublicKey;

public class TransactionOutput {
    public String Id;
    public PublicKey reciepient;
    public float value;
    public String parentTransactionId;

    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;

        this.Id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient)+Float.toString(value)+parentTransactionId);

    }
    public boolean isMine(PublicKey publicKey){
        return (publicKey == reciepient);
    }
}
