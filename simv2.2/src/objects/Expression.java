package objects;

import java.util.List;

public class Expression {
    private Expression subExpression;
    private List<Operand> operands;
    private Operation operation;

    public Expression getSubExpression() {
        return subExpression;
    }

    public void setSubExpression(Expression subExpression) {
        this.subExpression = subExpression;
    }

    public List<Operand> getOperands() {
        return operands;
    }

    public void setOperands(List<Operand> operands) {
        this.operands = operands;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
