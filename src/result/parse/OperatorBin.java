package result.parse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phantom
 */
public class OperatorBin {
    private List<String> operator = new ArrayList<>();
    public OperatorBin() {
    }

    public List<String> getOperator() {
        return operator;
    }

    public void setOperator(List<String> operator) {
        this.operator = operator;
    }

    public void add (String element){operator.add(element);}

}
