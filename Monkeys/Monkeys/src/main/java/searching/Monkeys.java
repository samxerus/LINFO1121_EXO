package searching;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Monkeys {

    private static Map<String, Monkey> monkeys;

    private static Map<String, Long> monkeyValues;

    /**
     * Évalue la valeur du singe "root".
     */
    public static long evaluateRoot(List<Monkey> input) {
        monkeys = new HashMap<>();
        monkeyValues = new HashMap<>();

        for (Monkey m : input) {
            monkeys.put(m.name, m);
        }

        return evaluate("root");
    }

    /**
     * Évalue récursivement la valeur d'un singe en stockant la valeur calculée.
     */
    private static long evaluate(String name) {
        if (monkeyValues.containsKey(name)) {
            return monkeyValues.get(name);
        }

        Monkey m = monkeys.get(name);
        long value;

        if (m instanceof YellingMonkey) {
            value = ((YellingMonkey) m).number;
        } else if (m instanceof OperationMonkey) {
            OperationMonkey op = (OperationMonkey) m;
            long left = evaluate(op.leftMonkey);
            long right = evaluate(op.rightMonkey);

            switch (op.op) {
                case '+':
                    value =  left + right;
                    break;
                case '-':
                    value =  left - right;
                    break;
                case '*':
                    value =  left * right;
                    break;
                case '/':
                    value =  left / right;
                    break;
                default:
                    throw new IllegalArgumentException("Opérateur inconnu : " + op.op);
            }
        } else {
            throw new IllegalStateException("Singe inconnu : " + name);
        }

        monkeyValues.put(name, value);
        return value;
    }

    static abstract class Monkey {
        String name;
    }

    static class YellingMonkey extends Monkey {
        int number;

        public YellingMonkey(String name, int number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return name + ": " + number;
        }
    }

    static class OperationMonkey extends Monkey {
        char op;
        String leftMonkey;
        String rightMonkey;

        public OperationMonkey(String name, String left, char op, String right) {
            this.name = name;
            this.leftMonkey = left;
            this.op = op;
            this.rightMonkey = right;
        }

        @Override
        public String toString() {
            return name + ": " + leftMonkey + " " + op + " " + rightMonkey;
        }
    }
}



/**
 * Solution initialle sans utilisee un arbre complexité O(n)
 * code plus complexe à comprendre
 */
/*
public class Monkeys {
    public static HashMap<String, Long> monkeyValues;
    public static HashMap<String, List<Monkey>> waitedMonkey;

    public static long evaluateRoot(List<Monkey> input) {

        monkeyValues = new HashMap<>();
        waitedMonkey = new HashMap<>();

        for (Monkey currM : input) {
            if (checkMonkey(currM)) {
                return monkeyValues.get("root");
            }
        }

        return -1;
    }

    public static boolean checkMonkey(Monkey monkey) {
        if (monkey instanceof YellingMonkey) {
            monkeyValues.put(monkey.name, (long) ((YellingMonkey) monkey).number);
            if( (monkey.name).equals("root")) return true;
            if (waitedMonkey.containsKey(monkey.name)) {
                for (Monkey m : waitedMonkey.get(monkey.name)) {
                    if(checkMonkey(m)){
                        return true;
                    }
                }
            }
            return false;
        }
        if (monkey instanceof OperationMonkey) {
            String leftMonkey = ((OperationMonkey) monkey).leftMonkey;
            String rigthMonkey = ((OperationMonkey) monkey).rightMonkey;
            Long rigthValue = monkeyValues.get(rigthMonkey);
            Long leftValue = monkeyValues.get(leftMonkey);
            if (rigthValue != null && leftValue != null) {
                long result;
                switch (((OperationMonkey) monkey).op) {
                    case '+':
                        result =  leftValue + rigthValue;
                        break;
                    case '-':
                        result =  leftValue - rigthValue;
                        break;
                    case '*':
                        result =  leftValue * rigthValue;
                        break;
                    case '/':
                        result =  leftValue / rigthValue;
                        break;
                    default:
                        throw new IllegalArgumentException("Opérateur inconnu : " + ((OperationMonkey) monkey).op);
                }
                monkeyValues.put(monkey.name,result);
                if( (monkey.name).equals("root")) return true;
                if (waitedMonkey.containsKey(monkey.name)) {
                    for (Monkey m : waitedMonkey.get(monkey.name)) {
                        if(checkMonkey(m)){
                            return true;
                        }
                    }
                }
                return false;
            } else {
                if(((OperationMonkey) monkey).waiting){
                    return false;
                }
                if (rigthValue == null) {
                    List<Monkey> wait = waitedMonkey.get(rigthMonkey);
                    if (wait == null) {
                        wait = new ArrayList<>();
                    }
                    wait.add(monkey);
                    waitedMonkey.put(rigthMonkey, wait);
                }

                if (leftValue == null) {
                    List<Monkey> wait = waitedMonkey.get(leftMonkey);
                    if (wait == null) {
                        wait = new ArrayList<>();
                    }
                    wait.add(monkey);
                    waitedMonkey.put(leftMonkey, wait);
                }
                ((OperationMonkey) monkey).waiting = true;
            }

        }
        return false;
    }





    static class Monkey {
        String name;
    }
    static class YellingMonkey extends Monkey {
        int number;
        public YellingMonkey(String name,int number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return name+": "+number;
        }
    }
    static class OperationMonkey extends Monkey {
        char op;
        String leftMonkey;
        String rightMonkey;
        Boolean waiting;
        public OperationMonkey(String name, String left, char op, String right) {
            this.name = name;
            this.leftMonkey = left;
            this.op = op;
            this.rightMonkey = right;
            this.waiting = false;

        }

        @Override
        public String toString() {
            return name+": "+leftMonkey+" "+op+" "+rightMonkey;
        }
    }


}
*/