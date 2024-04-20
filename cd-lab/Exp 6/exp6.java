import java.util.*;

public class Main {
    private static final String[] rules = {"S'->S", "S->L=R", "S->R", "L->*R", "L->id", "R->L"};
    private static final String[] terminals = {"id", "=", "*", "$"};
    private static final String[] nonTerminals = {"S'", "S", "L", "R"};
    private static final String augmentedGrammar = "S'->S";
    private static final Map<Integer, Set<String>> lr0Items = new HashMap<>();
    private static final Map<String, Map<String, String>> parsingTable = new HashMap<>();

    private static Set<String> findFollow(String symbol) {
        Set<String> followSet = new HashSet<>();
        if ("S'".equals(symbol)) followSet.add("$");

        for (String rule : rules) {
            String[] parts = rule.split("->");
            String[] symbols = parts[1].split("");

            for (int i = 0; i < symbols.length; i++) {
                if (symbols[i].equals(symbol)) {
                    Set<String> firstSet = findFirst(symbols[i + 1]);
                    followSet.addAll(firstSet);
                    if (firstSet.contains("e") && i == symbols.length - 1 && !parts[0].equals(symbol)) {
                        followSet.addAll(findFollow(parts[0]));
                    } else {
                        followSet.remove("e");
                    }
                }
            }
        }

        return followSet;
    }

    private static Set<String> findFirst(String symbol) {
        Set<String> firstSet = new HashSet<>();
        if (Arrays.asList(terminals).contains(symbol)) firstSet.add(symbol);
        for (String rule : rules) {
            String[] parts = rule.split("->");
            if (parts[0].equals(symbol)) {
                String[] symbols = parts[1].split("");
                firstSet.addAll(symbols[0].equals("e") ? Set.of("e") : findFirst(symbols[0]));
            }
        }
        return firstSet;
    }

    private static Set<String> goTo(Set<String> items, String symbol) {
        Set<String> nextState = new HashSet<>();
        for (String item : items) {
            String[] parts = item.split(",");
            int position = Integer.parseInt(parts[1]);
            String[] symbols = parts[0].split("->")[1].split("");
            if (position < symbols.length && symbols[position].equals(symbol)) {
                nextState.add(parts[0] + "," + (position + 1));
            }
        }
        return closure(nextState);
    }

    private static int getStateNumber(Set<String> items) {
        return lr0Items.entrySet().stream()
                .filter(entry -> entry.getValue().equals(items))
                .findFirst().orElseGet(() -> {
                    int newState = lr0Items.size();
                    lr0Items.put(newState, items);
                    return Map.entry(newState, items);
                }).getKey();
    }

    private static void constructLR0Items() {
        Set<String> initialItem = new HashSet<>(Collections.singletonList(augmentedGrammar + ",0"));
        lr0Items.put(0, closure(initialItem));

        Queue<Integer> pendingStates = new LinkedList<>(Collections.singletonList(0));

        while (!pendingStates.isEmpty()) {
            int state = pendingStates.poll();
            Set<String> items = lr0Items.get(state);
            for (String symbol : terminals) {
                Set<String> nextState = goTo(items, symbol);
                if (!nextState.isEmpty()) {
                    int nextStateNumber = getStateNumber(nextState);
                    if (!lr0Items.containsKey(nextStateNumber)) pendingStates.add(nextStateNumber);
                    parsingTable.computeIfAbsent(String.valueOf(state), k -> new HashMap<>())
                            .put(symbol, String.valueOf(nextStateNumber));
                }
            }
            for (String symbol : nonTerminals) {
                Set<String> nextState = goTo(items, symbol);
                if (!nextState.isEmpty()) {
                    int nextStateNumber = getStateNumber(nextState);
                    if (!lr0Items.containsKey(nextStateNumber)) pendingStates.add(nextStateNumber);
                    parsingTable.computeIfAbsent(String.valueOf(state), k -> new HashMap<>())
                            .put(symbol, String.valueOf(nextStateNumber));
                }
            }
        }
    }

    private static Set<String> closure(Set<String> items) {
        Set<String> closureItems = new HashSet<>(items);
        Queue<String> pendingItems = new LinkedList<>(items);
        while (!pendingItems.isEmpty()) {
            String item = pendingItems.poll();
            String[] parts = item.split(",");
            int position = Integer.parseInt(parts[1]);
            String[] symbols = parts[0].split("->")[1].split("");
            if (position < symbols.length && Arrays.asList(nonTerminals).contains(symbols[position])) {
                for (String rule : rules) {
                    String[] ruleParts = rule.split("->");
                    if (ruleParts[0].equals(symbols[position])) {
                        String newItem = rule + ",0";
                        if (!closureItems.contains(newItem)) {
                            closureItems.add(newItem);
                            pendingItems.add(newItem);
                        }
                    }
                }
            }
        }
        return closureItems;
    }

    public static void main(String[] args) {
        constructLR0Items();
        System.out.println("LR(0) Collection of Items:");
        lr0Items.forEach((key, value) -> System.out.println("State " + key + ": " + value));
        System.out.println("\nParsing Table:");
        parsingTable.forEach((key, value) -> System.out.println("State " + key + ": " + value));
    }
}
