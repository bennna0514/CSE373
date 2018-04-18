package calculator.ast;
import calculator.interpreter.Environment;
import calculator.errors.EvaluationError;
import calculator.gui.ImageDrawer;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
//import misc.exceptions.NotYetImplementedException;
/**
 * All of the static methods in this class are given the exact same parameters for
 * consistency. You can often ignore some of these parameters when implementing your
 * methods.
 *
 * Some of these methods should be recursive. You may want to consider using public-private
 * pairs in some cases.
 */
public class ExpressionManipulators {
    /**
     * Accepts an 'toDouble(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'toDouble'.
     * - The 'node' parameter has exactly one child: the AstNode to convert into a double.
     *
     * Postconditions:
     *
     * - Returns a number AstNode containing the computed double.
     *
     * For example, if this method receives the AstNode corresponding to
     * 'toDouble(3 + 4)', this method should return the AstNode corresponding
     * to '7'.
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if any of the expressions uses an unknown operation.
     */
    public static AstNode handleToDouble(Environment env, AstNode node) {
        
        return new AstNode(toDoubleHelper(env.getVariables(), node.getChildren().get(0)));
    }
    private static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
        // There are three types of nodes, so we have three cases.
        if (node.isNumber()) {
            return node.getNumericValue();
            
            //throw new NotYetImplementedException();
        } else if (node.isVariable()) {
            
            /*when implementing toDoubleHelper, is check if the name of any given
             * variable is contained within that dictionary. If it isn't,
             * you throw an exception. If it is, look up what the corresponding
             * expression stored in the dictionary is and attempt to convert that to a double.*/
            if (!variables.containsKey(node.getName())) {
                throw new EvaluationError("Variable " + node.getName() + " is unknown.");
            }
            
            else {
                //return variables.get(node.getName()).getNumericValue();1
                return toDoubleHelper(variables, variables.get(node.getName()));
                //Need to recurse through it, not necessarily a numeric value
                //toDouble or simplify or new method?
            }
            //throw new NotYetImplementedException();
        } else {
            String name = node.getName();
            
            if (name.equals("+")) {
                return toDoubleHelper(variables, node.getChildren().get(0)) + 
                        toDoubleHelper(variables, node.getChildren().get(1));
            }
            
            
            else if (name.equals("-")){
                return toDoubleHelper(variables, node.getChildren().get(0)) - 
                        toDoubleHelper(variables, node.getChildren().get(1));
            }
            
            
            else if (name.equals("*")){
                return toDoubleHelper(variables, node.getChildren().get(0)) * 
                        toDoubleHelper(variables, node.getChildren().get(1));
            }
            
            
            else if (name.equals("/")){
                return toDoubleHelper(variables, node.getChildren().get(0)) / 
                        toDoubleHelper(variables, node.getChildren().get(1));
            }
            
            
            else if (name.equals(":=")){
                variables.put(node.getChildren().get(0).getName(), node.getChildren().get(1));
            }
            
            
            else if (name.equals("sin")){
                return Math.sin(toDoubleHelper(variables, node.getChildren().get(0)));
            }
            
            
            else if (name.equals("cos")){
                return Math.cos(toDoubleHelper(variables, node.getChildren().get(0)));
            }
            
            else if (name.equals("^")){
                return Math.pow(toDoubleHelper(variables, node.getChildren().get(0)),
                        toDoubleHelper(variables, node.getChildren().get(1)));
            }
            else if (name.equals("negate")) {
                return toDoubleHelper(variables, node.getChildren().get(0)) * -1;
                
            }
            else {
                throw new EvaluationError("Unknown Operation");
            }
            
        }
        return 0;
    }
    /**
     * Accepts a 'simplify(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'simplify'.
     * - The 'node' parameter has exactly one child: the AstNode to simplify
     *
     * Postconditions:
     *
     * - Returns an AstNode containing the simplified inner parameter.
     *
     * For example, if we received the AstNode corresponding to the expression
     * "simplify(3 + 4)", you would return the AstNode corresponding to the
     * number "7".
     *
     * Note: there are many possible simplifications we could implement here,
     * but you are only required to implement a single one: constant folding.
     *
     * That is, whenever you see expressions of the form "NUM + NUM", or
     * "NUM - NUM", or "NUM * NUM", simplify them.
     */
    public static AstNode handleSimplify(Environment env, AstNode node) {
        // Try writing this one on your own!
        // Hint 1: Your code will likely be structured roughly similarly
        //         to your "handleToDouble" method
        // Hint 2: When you're implementing constant folding, you may want
        //         to call your "handleToDouble" method in some way
        return simplifyHelper(env.getVariables(), node.getChildren().get(0));
        
        
        //node parameter has exactly one child, the node that needs to be simplified.
        //return simplifyHelper(env.getVariables(),node.getChildren());
        //throw new NotYetImplementedException();
    }
    
    /*
    if(!variables.containsKey(node.getChildren().get(0).getName())) {
        
        if(!variables.containsKey(node.getChildren().get(1).getName())) {
            return node;
        }
        
    }
    */
 
    
    public static AstNode simplifyHelper(IDictionary<String, AstNode> variables, AstNode node) {
        //AstNode store = null;
        if (node.isNumber()) {
            return node;
        }
        
        /*
         *     If the variable is defined, then look up what its definition is.
         *     Simplify the definition (basically, make a recursive call),
         *     then replace the variable with the simplified result.
         *     If the variable isn't defined, leave it alone.
         * */
        else if (node.isVariable()) {
            
            if (!variables.containsKey(node.getName())) {
                return node;
            }
            else {
                //return variables.get(node.getName());
                //variables.
                //store = new AstNode(toDoubleHelper(variables, node));
                //Need to grab the node inside of the dictionary and recurse through it with simplifyHelper
                AstNode value = variables.get(node.getName());
                
                return simplifyHelper(variables, value);
                
            }
            
            
        }
        
        else if (node.isOperation()) {
            String name = node.getName();
            //
            /*for basic operations, num + num, num - num, num * num, simplify them.
             * return a node that adds them together. (toDouble to convert math)
             */
            
            //failing here because I use toDoubleHelper which returns evaluation
            //error on unknown variables. I need to check children or
            //pass through a different method
            /*
             * Only simplify if both children are numbers, so include in if?
             * contains key or isnumber?
             *
             * Probably containskey, then deal with 4 situations,
             * [0] && [1] is key; [0] && [1] is not key; [0] is key, [1] is not; [0] is not key, [0] is key
             * actually need 2 checks
             * */
            
            //make a case for negates?
            
            //nonspecial cases, not constant folding
            if (name.equals("/") || name.equals("^")) {
                AstNode childZero = simplifyHelper(variables, node.getChildren().get(0));
                AstNode childOne = simplifyHelper(variables, node.getChildren().get(1));
                IList<AstNode> children = new DoubleLinkedList<>();
                children.add(childZero);
                children.add(childOne);
                AstNode temp = new AstNode(name, children);
                return temp;
            }
            
            //single child cases
            if (name.equals("sin") || name.equals("negate") ||name.equals("cos")) {
                AstNode singleChild = simplifyHelper(variables, node.getChildren().get(0));
                IList<AstNode> children = new DoubleLinkedList<>();
                children.add(singleChild);
                return new AstNode(name, children);
            }
            //constant folding cases
            if (name.equals("+") || name.equals("-") || name.equals("*")) {
                
                AstNode childZero = simplifyHelper(variables, node.getChildren().get(0));
                AstNode childOne = simplifyHelper(variables, node.getChildren().get(1));
                IList<AstNode> children = new DoubleLinkedList<>();
                children.add(childZero);
                children.add(childOne);
                AstNode temp = new AstNode(name, children);
                
                if (childZero.isNumber() && childOne.isNumber()) {
                    
                    return  new AstNode(toDoubleHelper(variables, temp));
                }
                return temp;
               
            
            }
            //else if()
            //if()
            /*
             * deal with || name.equals("sin") || name.equals name.equals("/")
             * */
            
        }
        return node;
        
    }
    /**
     * Accepts a 'plot(exprToPlot, var, varMin, varMax, step)' AstNode and
     * generates the corresponding plot. Returns some arbitrary AstNode.
     *
     * Example 1:
     *
     * >>> plot(3 * x, x, 2, 5, 0.5)
     *
     * This method will receive the AstNode corresponding to 'plot(3 * x, x, 2, 5, 0.5)'.
     * Your 'handlePlot' method is then responsible for plotting the equation
     * "3 * x", varying "x" from 2 to 5 in increments of 0.5.
     *
     * In this case, this means you'll be plotting the following points:
     *
     * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
     *
     * ---
     *
     * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
     * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
     *
     * >>> c := 4
     * 4
     * >>> step := 0.01
     * 0.01
     * >>> plot(a^2 + c*a + a, a, -10, 10, step)
     *
     * ---
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if varMin > varMax
     * @throws EvaluationError  if 'var' was already defined
     * @throws EvaluationError  if 'step' is zero or negative
     */
    public static AstNode plot(Environment env, AstNode node) {
        //throw new NotYetImplementedException();
        // Note: every single function we add MUST return an
        // AST node that your "simplify" function is capable of handling.
        // However, your "simplify" function doesn't really know what to do
        // with "plot" functions (and what is the "plot" function supposed to
        // evaluate to anyways?) so we'll settle for just returning an
        // arbitrary number.
        //
        // When working on this method, you should uncomment the following line:
        IDictionary<String, AstNode> variables = env.getVariables(); 
        IList<AstNode> children = node.getChildren();
        ImageDrawer result = env.getImageDrawer();
        IList<Double> xValues = new DoubleLinkedList<>();
        IList<Double> yValues = new DoubleLinkedList<>();
        AstNode expression = children.get(0);
        AstNode variable = children.get(1);
        AstNode minVar = children.get(2);
        AstNode maxVar = children.get(3);
        AstNode step = children.get(4);
        //breaks here because you're calling numeric value of AstNode 
        //when it's a variable or operation
        Double getMin = toDoubleHelper(variables, minVar);
        //Double getMin = simplifyHelper(variable, minVar)
        //Double getMin = minVar.getNumericValue();
        //Double getMax = maxVar.getNumericValue();
        Double getMax = toDoubleHelper(variables, maxVar);
        Double getStep = toDoubleHelper(variables, step);
        //Double getStep = step.getNumericValue();
        
        
        //Need to check if I need to get toDoubleHelper of getMax
        
        
        //Undefined variable exception throw missing
        /*
         * for min, max, and step
         * They can be any arbitrary operation node. 
         * (If you're wondering how to deal with these cases, 
         * here's a hint: try taking a look at the helper methods you've implemented so far.)
         * */
        
        if (getMin > getMax) {
            throw new EvaluationError("Minimum variable is larger than the maximum.");
        }
        if (variables.containsKey(variable.getName())) {
            throw new EvaluationError("Variable was previously defined.");
        }
        //if (step.getNumericValue() <= 0) {
        if (getStep <= 0) {
            throw new EvaluationError("Step cannot be zero or negative.");
        }
        
        for (double i = getMin; i <= getMax; i+= getStep) {
             xValues.add(i);
             variables.put(variable.getName(), new AstNode(i));
             yValues.add(toDoubleHelper(variables, expression));
             //yValues.add(simplifyHelper(variables,expression).getNumericValue());
             variables.remove(variable.getName());
        }
        
        result.drawScatterPlot("Plot", "x", "y", xValues, yValues);                
        return new AstNode(1);
    }
}
